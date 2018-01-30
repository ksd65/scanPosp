package com.epay.scanposp.service;

import java.math.BigDecimal;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.epay.scanposp.common.constant.MSConfig;
import com.epay.scanposp.common.utils.CommonUtil;
import com.epay.scanposp.common.utils.DateUtil;
import com.epay.scanposp.common.utils.StringUtil;
import com.epay.scanposp.common.utils.constant.TranCodeConstant;
import com.epay.scanposp.common.utils.ms.CryptoUtil;
import com.epay.scanposp.common.utils.ms.HttpClient4Util;
import com.epay.scanposp.common.utils.ms.MSCommonUtil;
import com.epay.scanposp.entity.MemberInfo;
import com.epay.scanposp.entity.MemberInfoExample;
import com.epay.scanposp.entity.MemberInfoExample.Criteria;
import com.epay.scanposp.entity.MsBillRequestLog;
import com.epay.scanposp.entity.MsBillRequestLogExample;
import com.epay.scanposp.entity.MsPayBill;
import com.epay.scanposp.entity.MsSettleFailRecord;
import com.epay.scanposp.entity.MsWithdrawBill;
import com.epay.scanposp.entity.MsWithdrawT1Bill;
import com.epay.scanposp.entity.RoutewayDraw;
import com.epay.scanposp.entity.RoutewayDrawExample;
import com.epay.scanposp.entity.TradeDetail;
import com.epay.scanposp.entity.TradeDetailExample;

@Service
@Lazy(false)
public class MsbillService {
	private static Logger logger = LoggerFactory.getLogger(MsbillService.class);

	@Resource
	private MsPayBillService msPayBillService;

	@Resource
	private MsBillRequestLogService msBillRequestLogService;

	@Resource
	private MsWithdrawBillService msWithdrawBillService;
	
	@Resource
	private MsWithdrawT1BillService msWithdrawT1BillService;
	
	@Resource
	private MsSettleFailRecordService msSettleFailRecordService;

	@Resource
	private DebitNoteService debitNoteService;

	@Resource
	private TradeDetailService tradeDetailService;

	@Resource
	private RouteWayService routeWayService;
	
	@Resource
	private RoutewayDrawService routewayDrawService;

	@Resource
	private MemberInfoService memberInfoService;

	//@Scheduled(cron = "0 0/5 10 * * ?")
	public synchronized void getMsPayBillsTask() {
		String settleDate = DateUtil.getDateStr(new Date(new Date().getTime() - 24 * 60 * 60 * 1000));
		getMsPayBillsBySettleDate(settleDate);

	}

	/**
	 * 根据指定的日期获取交易对账
	 */
	public synchronized void getMsPayBillsBySettleDate(String settleDate) {
		MsBillRequestLogExample msBillRequestLogExample = new MsBillRequestLogExample();
		msBillRequestLogExample.createCriteria().andSettleDateEqualTo(settleDate).andBillTypeEqualTo("1");
		List<MsBillRequestLog> msBillRequestLogs = msBillRequestLogService.selectByExample(msBillRequestLogExample);

		if (msBillRequestLogs != null && msBillRequestLogs.size() > 0) {
			for (MsBillRequestLog msBillRequestLog : msBillRequestLogs) {
				if ("S".equals(msBillRequestLog.getRespType())) {
					continue;
				}
				getMsPaybills(settleDate, msBillRequestLog);
			}

		} else {
			MsBillRequestLog msBillRequestLog = new MsBillRequestLog();
			msBillRequestLog.setBillType("1");
			msBillRequestLog.setCreateDate(new Date());
			msBillRequestLog.setSettleType("0");
			msBillRequestLog.setDelFlag("0");
			msBillRequestLogService.insertSelective(msBillRequestLog);
			getMsPaybills(settleDate, msBillRequestLog);
			msBillRequestLog = new MsBillRequestLog();
			msBillRequestLog.setBillType("1");
			msBillRequestLog.setCreateDate(new Date());
			msBillRequestLog.setDelFlag("0");
			msBillRequestLog.setSettleType("1");
			msBillRequestLogService.insertSelective(msBillRequestLog);
			getMsPaybills(settleDate, msBillRequestLog);
		}

	}

	
	
	private void getMsPaybills(String settleDate, MsBillRequestLog msBillRequestLog) {
		try {
			String orderCode = CommonUtil.getOrderCode();

			// 调用支付通道

			String serverUrl = MSConfig.msServerUrl;
			PublicKey yhPubKey = null;
			if (serverUrl.startsWith("https://ipay")) {
				yhPubKey = CryptoUtil.getRSAPublicKey(true);
			} else {
				yhPubKey = CryptoUtil.getRSAPublicKey(false);
			}
			PrivateKey hzfPriKey = CryptoUtil.getRSAPrivateKey();
			String tranCode = "SMZF020";
			String charset = "utf-8";
			StringBuilder sBuilder = new StringBuilder();
			sBuilder.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
			sBuilder.append("<merchant>");
			sBuilder.append("<head>");
			sBuilder.append("<version>1.0.0</version>");
			sBuilder.append("<msgType>01</msgType>");
			sBuilder.append("<reqDate>" + DateFormatUtils.format(new Date(), "yyyyMMddHHmmss") + "</reqDate>");
			sBuilder.append("</head>");
			sBuilder.append("<body>");
			sBuilder.append("<settleDate>" + settleDate + "</settleDate>");
			sBuilder.append("<fileType>" + "1" + "</fileType>");
			sBuilder.append("</body>");
			sBuilder.append("</merchant>");
			String plainXML = sBuilder.toString();
			byte[] plainBytes = plainXML.getBytes(charset);
			String keyStr = MSCommonUtil.generateLenString(16);
			byte[] keyBytes = keyStr.getBytes(charset);
			String encryptData = new String(Base64.encodeBase64((CryptoUtil.AESEncrypt(plainBytes, keyBytes, "AES", "AES/ECB/PKCS5Padding", null))), charset);
			String signData = new String(Base64.encodeBase64(CryptoUtil.digitalSign(plainBytes, hzfPriKey, "SHA1WithRSA")), charset);
			String encrtptKey = new String(Base64.encodeBase64(CryptoUtil.RSAEncrypt(keyBytes, yhPubKey, 2048, 11, "RSA/ECB/PKCS1Padding")), charset);
			List<NameValuePair> nvps = new LinkedList<NameValuePair>();
			nvps.add(new BasicNameValuePair("encryptData", encryptData));
			nvps.add(new BasicNameValuePair("encryptKey", encrtptKey));
			if ("0".equals(msBillRequestLog.getSettleType())) {
				nvps.add(new BasicNameValuePair("cooperator", MSConfig.cooperator_t0));
			} else {
				nvps.add(new BasicNameValuePair("cooperator", MSConfig.cooperator_t1));
			}
			nvps.add(new BasicNameValuePair("signData", signData));
			nvps.add(new BasicNameValuePair("tranCode", tranCode));
			nvps.add(new BasicNameValuePair("reqMsgId", orderCode));
			byte[] b = HttpClient4Util.getInstance().doPost(serverUrl, null, nvps);
			String respStr = new String(b, charset);
			logger.info("返回报文[{}]", new Object[] { respStr });
			JSONObject jsonObject = JSONObject.fromObject(respStr);
			String resEncryptData = jsonObject.getString("encryptData");
			String resEncryptKey = jsonObject.getString("encryptKey");
			byte[] decodeBase64KeyBytes = Base64.decodeBase64(resEncryptKey.getBytes(charset));
			// 解密encryptKey得到merchantAESKey
			byte[] merchantAESKeyBytes = CryptoUtil.RSADecrypt(decodeBase64KeyBytes, hzfPriKey, 2048, 11, "RSA/ECB/PKCS1Padding");
			// 使用base64解码商户请求报文
			byte[] decodeBase64DataBytes = Base64.decodeBase64(resEncryptData.getBytes(charset));
			// 用解密得到的merchantAESKey解密encryptData
			byte[] merchantXmlDataBytes = CryptoUtil.AESDecrypt(decodeBase64DataBytes, merchantAESKeyBytes, "AES", "AES/ECB/PKCS5Padding", null);
			String resXml = new String(merchantXmlDataBytes, charset);

			//System.out.println(XmlConvertUtil.documentToJSONObjectFilter(resXml));

			Document document = DocumentHelper.parseText(resXml);
			Element rootElement = document.getRootElement();

			Element headerElement = rootElement.element("head");

			if ("S".equals(headerElement.element("respType").getText().trim())) {

				Element contentElement = rootElement.element("body").element("content");
				String contentText = contentElement.getText();

				String[] bills = contentText.split("########");

				if (bills.length > 0) {
					String[] payBills = bills[0].split("\n");
					MsPayBill msPayBill;
					for (int i = 0; i < payBills.length; i++) {
						if (!"".equals(payBills[i].trim())) {
							String[] items = payBills[i].split("\\|");
							msPayBill = new MsPayBill();
							msPayBill.setCooperator(items[0]);
							msPayBill.setMerchantCode(items[1]);
							msPayBill.setSmzfMsgId(items[2]);
							msPayBill.setReqMsgId(items[3]);
							msPayBill.setAmount(new BigDecimal(items[4]));
							msPayBill.setSettleDate(items[5]);
							msPayBill.setRespType(items[6]);
							msPayBill.setRespCode(items[7]);
							msPayBill.setRespMsg(items[8]);
							msPayBill.setTransactionType(items[9]);
							msPayBill.setOriReqMsgId(items[10]);
							msPayBill.setFee(new BigDecimal(items[11]));
							msPayBill.setPayWay(items[12]);
							msPayBill.setPayType(items[13]);
							msPayBill.setDrawBatchNo(items[14]);
							msPayBill.setDelFlag("0");
							if (!StringUtil.isEmpty(items[14])) {
								if (!"WTX".equals(items[14])) {
									msPayBill.setSettleType("0");
								}else{
									msPayBill.setSettleType("1");
								}
							}
							MemberInfoExample memberInfoExample = new MemberInfoExample();
							Criteria memberInfoCriteria = memberInfoExample.createCriteria();
							if("WXZF".equals(msPayBill.getPayWay())){
								memberInfoCriteria.andWxMerchantCodeEqualTo(msPayBill.getMerchantCode());
							}else if("ZFBZF".equals(msPayBill.getPayWay())){
								memberInfoCriteria.andZfbMerchantCodeEqualTo(msPayBill.getMerchantCode());
							}else if("QQZF".equals(msPayBill.getPayWay())){
								memberInfoCriteria.andQqMerchantCodeEqualTo(msPayBill.getMerchantCode());
							}else if("BDQB".equals(msPayBill.getPayWay())){
								memberInfoCriteria.andBdMerchantCodeEqualTo(msPayBill.getMerchantCode());
							}
							List<MemberInfo> memberInfoList = memberInfoService.selectByExample(memberInfoExample);
							
							if (memberInfoList.size() == 1) {
								MemberInfo memberInfo = memberInfoList.get(0);
								msPayBill.setMemberId(memberInfo.getId());
								if(msPayBill.getSettleType().equals("1")){

									msPayBill.setMemberTradeRate(memberInfo.getT1TradeRate());
								}else
								{
									msPayBill.setMemberTradeRate(memberInfo.getT0TradeRate());
								}
								msPayBillService.insertSelective(msPayBill);
								if (!StringUtil.isEmpty(items[14])) {
									//对账获取速度慢，因为这张表的数据基本没用到，所以注释掉
//									DebitNoteExample debitNoteExample = new DebitNoteExample();
//									debitNoteExample.createCriteria().andOrderCodeEqualTo(items[3]);
//									List<DebitNote> debitNotes = debitNoteService.selectByExample(debitNoteExample);
//
//									if (debitNotes != null && debitNotes.size() > 0) {
//										DebitNote debitNote = debitNotes.get(0);
//										if (!"WTX".equals(items[14])) {
//											debitNote.setSettleType("0");
//										} else {
//											debitNote.setSettleType("1");
//										}
//										debitNote.setUpdateDate(new Date());
//										debitNoteService.updateByPrimaryKeySelective(debitNote);
//									}

									TradeDetailExample tradeDetailExample = new TradeDetailExample();
									tradeDetailExample.createCriteria().andPtSerialNoEqualTo(items[3]);
									List<TradeDetail> tradeDetails = tradeDetailService
											.selectByExample(tradeDetailExample);

									if (tradeDetails != null && tradeDetails.size() > 0) {
										TradeDetail tradeDetail = tradeDetails.get(0);
										// 查询通道信息，因为通道信息基本不变，这里先写死，若后期想从数据库取，可在循环之前先把通道信息读取到内存，避免多次与数据库交互
//										RouteWayExample routeWayExample = new RouteWayExample();
//										routeWayExample.createCriteria().andRouteIdEqualTo(tradeDetail.getRouteId());
//										List<RouteWay> routeWays = routeWayService.selectByExample(routeWayExample);
//										RouteWay routeWay = routeWays.get(0);
//										String txnCostT0 = routeWay.getTxnCostT0();//0.0027|0.2|0.5
//										String txnCostT1 = routeWay.getTxnCostT1();//0.0025|0.2
//										// T0垫资手比例
//										String t0dzbl = (txnCostT0.split("\\|"))[2];
//										// 通道交易手续费率
//										String t0Tdsxf = (txnCostT0.split("\\|"))[1];
//										String t1Tdsxf = (txnCostT1.split("\\|"))[1];
										// T0垫资手比例
										String t0dzbl = ("0.5");
										
										// 通道交易手续费率
										String t0Tdsxf = ("0.2");
										String t1Tdsxf = ("0.2");

										TradeDetail recode4Update = new TradeDetail();
										recode4Update.setId(tradeDetail.getId());
										if (!"WTX".equals(items[14])) {
											recode4Update.setSettleType("0");
											recode4Update.setD0Money(tradeDetail.getMoney().multiply(
													new BigDecimal(t0dzbl)));
											recode4Update.setT1Money(tradeDetail.getMoney().subtract(
													recode4Update.getD0Money()));
											recode4Update.setD0MemberFee(tradeDetail.getD0Money().multiply(
													memberInfo.getT0TradeRate()));
											recode4Update.setT1MemberFee(tradeDetail.getT1Money().multiply(
													memberInfo.getT1TradeRate()));
											recode4Update.setD0RoutewayFee(tradeDetail.getD0Money().multiply(
													new BigDecimal(t0Tdsxf)));
											recode4Update.setT1RoutewayFee(tradeDetail.getT1Money().multiply(
													new BigDecimal(t1Tdsxf)));
											recode4Update.setMemberTradeRate(memberInfo.getT0TradeRate());
											recode4Update.setMemberDrawFee(memberInfo.getT1DrawFee());
											recode4Update.setMemberSettleMoney(tradeDetail.getMoney().subtract(
													tradeDetail.getMoney().multiply(memberInfo.getT0TradeRate())
															.setScale(2, BigDecimal.ROUND_HALF_UP)));
										} else {
											recode4Update.setSettleType("1");
											recode4Update.setD0Money(new BigDecimal(0));
											recode4Update.setT1Money(tradeDetail.getMoney());
											recode4Update.setD0MemberFee(new BigDecimal(0));
											recode4Update.setT1MemberFee(tradeDetail.getT1Money().multiply(
													memberInfo.getT1TradeRate()));
											recode4Update.setD0RoutewayFee(new BigDecimal(0));
											recode4Update.setT1RoutewayFee(tradeDetail.getT1Money().multiply(
													new BigDecimal(t1Tdsxf)));
											recode4Update.setMemberTradeRate(memberInfo.getT1TradeRate());
											recode4Update.setMemberDrawFee(memberInfo.getT1DrawFee());
											recode4Update.setMemberSettleMoney(tradeDetail.getMoney().subtract(
													tradeDetail.getMoney().multiply(memberInfo.getT1TradeRate())
															.setScale(2, BigDecimal.ROUND_HALF_UP)));
										}
										recode4Update.setCreateDate(new Date());
										tradeDetailService.updateByPrimaryKeySelective(recode4Update);
									}
								}
							}

						}
					}
				}

			}

			msBillRequestLog.setRespType(headerElement.element("respType").getText());
			msBillRequestLog.setRespCode(headerElement.element("respCode").getText());
			msBillRequestLog.setRespMsg(headerElement.element("respMsg").getText());
			msBillRequestLog.setSettleDate(rootElement.element("body").element("settleDate").getText());
			msBillRequestLog.setUpdateDate(new Date());
			msBillRequestLogService.updateByPrimaryKey(msBillRequestLog);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error("获取对账文件失败" + e.getMessage());
		}
	}

	//@Scheduled(cron = "0 0/5 10 * * ?")
	public synchronized void getMsWithdrawBillsT0() {
		String settleDate = DateUtil.getDateStr(new Date(new Date().getTime() - 24 * 60 * 60 * 1000));
		getMsWithdrawBillsT0BySettleDate(settleDate);
	}

	//@Scheduled(cron = "0 0/5 12 * * ?")
	public synchronized void getMsWithdrawBillsT1() {
		String settleDate = DateUtil.getDateStr(new Date(new Date().getTime() - 2*24 * 60 * 60 * 1000));
		getMsWithdrawBillsT1BySettleDate(settleDate);
	}
	
	//@Scheduled(cron = "0 0 10,23 * * ?")
	public synchronized void getMsT1SettleFailSchedule() {
//		System.out.println(DateUtil.getTime());
		getMsT1SettleFail();
	}

	
	/**
	 * 根据指定的日期获取提现对账，主要是用于手工获取
	 * 
	 */
	public void getMsWithdrawBillsT0BySettleDate(String settleDate) {
		try {
			MsBillRequestLogExample msBillRequestLogExample = new MsBillRequestLogExample();
			msBillRequestLogExample.createCriteria().andSettleDateEqualTo(settleDate).andBillTypeEqualTo("2").andSettleTypeEqualTo("0");
			List<MsBillRequestLog> msBillRequestLogs = msBillRequestLogService.selectByExample(msBillRequestLogExample);
			MsBillRequestLog msBillRequestLog;
			if (msBillRequestLogs != null && msBillRequestLogs.size() > 0) {
				msBillRequestLog = msBillRequestLogs.get(0);
				if ("S".equals(msBillRequestLog.getRespType())) {
					return;
				}
			} else {
				msBillRequestLog = new MsBillRequestLog();
				msBillRequestLog.setBillType("2");
				msBillRequestLog.setCreateDate(new Date());
				msBillRequestLog.setDelFlag("0");
				msBillRequestLog.setSettleType("0");
				msBillRequestLogService.insertSelective(msBillRequestLog);
			}

			String orderCode = CommonUtil.getOrderCode();

			// 调用支付通道

			String serverUrl = MSConfig.msServerUrl;
			PublicKey yhPubKey = null;
			if (serverUrl.startsWith("https://ipay")) {
				yhPubKey = CryptoUtil.getRSAPublicKey(true);
			} else {
				yhPubKey = CryptoUtil.getRSAPublicKey(false);
			}
			PrivateKey hzfPriKey = CryptoUtil.getRSAPrivateKey();
			String tranCode = "SMZF024";
			String charset = "utf-8";
			StringBuilder sBuilder = new StringBuilder();
			sBuilder.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
			sBuilder.append("<merchant>");
			sBuilder.append("<head>");
			sBuilder.append("<version>1.0.0</version>");
			sBuilder.append("<msgType>01</msgType>");
			sBuilder.append("<reqDate>" + DateFormatUtils.format(new Date(), "yyyyMMddHHmmss") + "</reqDate>");
			sBuilder.append("</head>");
			sBuilder.append("<body>");
			sBuilder.append("<settleDate>" + settleDate + "</settleDate>");
			sBuilder.append("<fileType>" + "1" + "</fileType>");
			sBuilder.append("</body>");
			sBuilder.append("</merchant>");
			String plainXML = sBuilder.toString();
			byte[] plainBytes = plainXML.getBytes(charset);
			String keyStr = MSCommonUtil.generateLenString(16);
			;
			byte[] keyBytes = keyStr.getBytes(charset);
			String encryptData = new String(Base64.encodeBase64((CryptoUtil.AESEncrypt(plainBytes, keyBytes, "AES", "AES/ECB/PKCS5Padding", null))), charset);
			String signData = new String(Base64.encodeBase64(CryptoUtil.digitalSign(plainBytes, hzfPriKey, "SHA1WithRSA")), charset);
			String encrtptKey = new String(Base64.encodeBase64(CryptoUtil.RSAEncrypt(keyBytes, yhPubKey, 2048, 11, "RSA/ECB/PKCS1Padding")), charset);
			List<NameValuePair> nvps = new LinkedList<NameValuePair>();
			nvps.add(new BasicNameValuePair("encryptData", encryptData));
			nvps.add(new BasicNameValuePair("encryptKey", encrtptKey));
			nvps.add(new BasicNameValuePair("cooperator", MSConfig.cooperator_t0));
			nvps.add(new BasicNameValuePair("signData", signData));
			nvps.add(new BasicNameValuePair("tranCode", tranCode));
			nvps.add(new BasicNameValuePair("reqMsgId", orderCode));
			byte[] b = HttpClient4Util.getInstance().doPost(serverUrl, null, nvps);
			String respStr = new String(b, charset);
			logger.info("直清T0对账返回报文[{}]", new Object[] { respStr });
			JSONObject jsonObject = JSONObject.fromObject(respStr);
			String resEncryptData = jsonObject.getString("encryptData");
			String resEncryptKey = jsonObject.getString("encryptKey");
			byte[] decodeBase64KeyBytes = Base64.decodeBase64(resEncryptKey.getBytes(charset));
			// 解密encryptKey得到merchantAESKey
			byte[] merchantAESKeyBytes = CryptoUtil.RSADecrypt(decodeBase64KeyBytes, hzfPriKey, 2048, 11, "RSA/ECB/PKCS1Padding");
			// 使用base64解码商户请求报文
			byte[] decodeBase64DataBytes = Base64.decodeBase64(resEncryptData.getBytes(charset));
			// 用解密得到的merchantAESKey解密encryptData
			byte[] merchantXmlDataBytes = CryptoUtil.AESDecrypt(decodeBase64DataBytes, merchantAESKeyBytes, "AES", "AES/ECB/PKCS5Padding", null);
			String resXml = new String(merchantXmlDataBytes, charset);

			//System.out.println(XmlConvertUtil.documentToJSONObjectFilter(resXml));

			Document document = DocumentHelper.parseText(resXml);
			Element rootElement = document.getRootElement();

			Element headerElement = rootElement.element("head");

			if ("S".equals(headerElement.element("respType").getText().trim())) {
				Element contentElement = rootElement.element("body").element("content");
				String contentText = contentElement.getText();

				String[] bills = contentText.split("########");

				if (bills.length > 0) {
					String[] payBills = bills[0].split("\n");
					MsWithdrawBill msWithBill;
					for (int i = 0; i < payBills.length; i++) {
						if (!"".equals(payBills[i].trim())) {
							String[] items = payBills[i].split("\\|");
							msWithBill = new MsWithdrawBill();
							msWithBill.setCooperator1(items[0]);
							msWithBill.setMerchantCode(items[1]);
							msWithBill.setSmzfMsgId(items[2]);
							msWithBill.setReqMsgId(items[3]);
							msWithBill.setAccNo(items[4]);
							msWithBill.setAccName(items[5]);
							msWithBill.setDrawAmount(new BigDecimal(items[6]));
							msWithBill.setDrawFee(new BigDecimal(items[7]));
							msWithBill.setTradeFee(new BigDecimal(items[8]));
							msWithBill.setSettleDate(items[9]);
							msWithBill.setRespType(items[10]);
							msWithBill.setRespCode(items[11]);
							msWithBill.setRespMsg(items[12]);
							msWithBill.setCreateDate(new Date());
							msWithBill.setDelFlag("0");
							
							
							RoutewayDrawExample routewayDrawExample=new RoutewayDrawExample();
							routewayDrawExample.createCriteria().andOrderCodeEqualTo(items[3]);
							List<RoutewayDraw> routewayDraws=routewayDrawService.selectByExample(routewayDrawExample);
							if(routewayDraws!=null&&routewayDraws.size()>0){
								msWithBill.setMemberId(routewayDraws.get(0).getMemberId());
								msWithBill.setMemberTradeRate(routewayDraws.get(0).getMemberTradeRate());
							}
							
							msWithdrawBillService.insertSelective(msWithBill);
						}
					}
				}
			}

			msBillRequestLog.setRespType(headerElement.element("respType").getText());
			msBillRequestLog.setRespCode(headerElement.element("respCode").getText());
			msBillRequestLog.setRespMsg(headerElement.element("respMsg").getText());
			msBillRequestLog.setSettleDate(rootElement.element("body").element("settleDate").getText());
			msBillRequestLog.setUpdateDate(new Date());
			msBillRequestLogService.updateByPrimaryKey(msBillRequestLog);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error("获取对账文件失败" + e.getMessage());
		}
	}

	/**
	 * 根据指定的日期获取提现对账，主要是用于手工获取
	 * 
	 */
	public void getMsWithdrawBillsT1BySettleDate(String settleDate) {
		try {
			MsBillRequestLogExample msBillRequestLogExample = new MsBillRequestLogExample();
			msBillRequestLogExample.createCriteria().andSettleDateEqualTo(settleDate).andBillTypeEqualTo("2").andSettleTypeEqualTo("1");
			List<MsBillRequestLog> msBillRequestLogs = msBillRequestLogService.selectByExample(msBillRequestLogExample);
			MsBillRequestLog msBillRequestLog;
			if (msBillRequestLogs != null && msBillRequestLogs.size() > 0) {
				msBillRequestLog = msBillRequestLogs.get(0);
				if ("S".equals(msBillRequestLog.getRespType())) {
					return;
				}
			} else {
				msBillRequestLog = new MsBillRequestLog();
				msBillRequestLog.setBillType("2");
				msBillRequestLog.setCreateDate(new Date());
				msBillRequestLog.setDelFlag("0");
				msBillRequestLog.setSettleType("1");
				msBillRequestLogService.insertSelective(msBillRequestLog);
			}

			String orderCode = CommonUtil.getOrderCode();

			// 调用支付通道

			String serverUrl = MSConfig.msServerUrl;
			PublicKey yhPubKey = null;
			if (serverUrl.startsWith("https://ipay")) {
				yhPubKey = CryptoUtil.getRSAPublicKey(true);
			} else {
				yhPubKey = CryptoUtil.getRSAPublicKey(false);
			}
			PrivateKey hzfPriKey = CryptoUtil.getRSAPrivateKey();
			String tranCode = "SMZF025";
			String charset = "utf-8";
			StringBuilder sBuilder = new StringBuilder();
			sBuilder.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
			sBuilder.append("<merchant>");
			sBuilder.append("<head>");
			sBuilder.append("<version>1.0.0</version>");
			sBuilder.append("<msgType>01</msgType>");
			sBuilder.append("<reqDate>" + DateFormatUtils.format(new Date(), "yyyyMMddHHmmss") + "</reqDate>");
			sBuilder.append("</head>");
			sBuilder.append("<body>");
			sBuilder.append("<settleDate>" + settleDate + "</settleDate>");
			sBuilder.append("<fileType>" + "1" + "</fileType>");
			sBuilder.append("</body>");
			sBuilder.append("</merchant>");
			String plainXML = sBuilder.toString();
			byte[] plainBytes = plainXML.getBytes(charset);
			String keyStr = MSCommonUtil.generateLenString(16);
			;
			byte[] keyBytes = keyStr.getBytes(charset);
			String encryptData = new String(Base64.encodeBase64((CryptoUtil.AESEncrypt(plainBytes, keyBytes, "AES", "AES/ECB/PKCS5Padding", null))), charset);
			String signData = new String(Base64.encodeBase64(CryptoUtil.digitalSign(plainBytes, hzfPriKey, "SHA1WithRSA")), charset);
			String encrtptKey = new String(Base64.encodeBase64(CryptoUtil.RSAEncrypt(keyBytes, yhPubKey, 2048, 11, "RSA/ECB/PKCS1Padding")), charset);
			List<NameValuePair> nvps = new LinkedList<NameValuePair>();
			nvps.add(new BasicNameValuePair("encryptData", encryptData));
			nvps.add(new BasicNameValuePair("encryptKey", encrtptKey));
			nvps.add(new BasicNameValuePair("cooperator", MSConfig.cooperator_t1));
			nvps.add(new BasicNameValuePair("signData", signData));
			nvps.add(new BasicNameValuePair("tranCode", tranCode));
			nvps.add(new BasicNameValuePair("reqMsgId", orderCode));
			byte[] b = HttpClient4Util.getInstance().doPost(serverUrl, null, nvps);
			String respStr = new String(b, charset);
			logger.info("直清T1对账返回报文[{}]", new Object[] { respStr });
			JSONObject jsonObject = JSONObject.fromObject(respStr);
			String resEncryptData = jsonObject.getString("encryptData");
			String resEncryptKey = jsonObject.getString("encryptKey");
			byte[] decodeBase64KeyBytes = Base64.decodeBase64(resEncryptKey.getBytes(charset));
			// 解密encryptKey得到merchantAESKey
			byte[] merchantAESKeyBytes = CryptoUtil.RSADecrypt(decodeBase64KeyBytes, hzfPriKey, 2048, 11, "RSA/ECB/PKCS1Padding");
			// 使用base64解码商户请求报文
			byte[] decodeBase64DataBytes = Base64.decodeBase64(resEncryptData.getBytes(charset));
			// 用解密得到的merchantAESKey解密encryptData
			byte[] merchantXmlDataBytes = CryptoUtil.AESDecrypt(decodeBase64DataBytes, merchantAESKeyBytes, "AES", "AES/ECB/PKCS5Padding", null);
			String resXml = new String(merchantXmlDataBytes, charset);
			logger.info("直清T1对账返回解析[{}]", new Object[] { resXml });
			//System.out.println(XmlConvertUtil.documentToJSONObjectFilter(resXml));

			Document document = DocumentHelper.parseText(resXml);
			Element rootElement = document.getRootElement();

			Element headerElement = rootElement.element("head");

			if ("S".equals(headerElement.element("respType").getText().trim())) {
				Element contentElement = rootElement.element("body").element("content");
				String contentText = contentElement.getText();

				String[] bills = contentText.split("########");

				if (bills.length > 0) {
					String[] payBills = bills[0].split("\n");
					MsWithdrawT1Bill msWithT1Bill;
					for (int i = 0; i < payBills.length; i++) {
						if (!"".equals(payBills[i].trim())) {
							String[] items = payBills[i].split("\\|");
							msWithT1Bill = new MsWithdrawT1Bill();
							msWithT1Bill.setCooperator(items[0]);
							msWithT1Bill.setMerchantCode(items[1]);
							msWithT1Bill.setSmzfMsgId(items[2]);
							msWithT1Bill.setAccNo(items[3]);
							msWithT1Bill.setAccName(items[4]);
							msWithT1Bill.setDrawAmount(new BigDecimal(items[5]));
							msWithT1Bill.setDrawFee(new BigDecimal(items[6]));
							msWithT1Bill.setSettleDate(items[7]);
							msWithT1Bill.setRespType(items[8]);
							msWithT1Bill.setRespCode(items[9]);
							msWithT1Bill.setRespMsg(items[10]);
							msWithT1Bill.setDzlx(items[11]);
							msWithT1Bill.setCreateDate(new Date());
							msWithT1Bill.setDelFlag("0");
//							RoutewayDrawExample routewayDrawExample=new RoutewayDrawExample();
//							routewayDrawExample.createCriteria().andOrderCodeEqualTo(items[3]);
//							List<RoutewayDraw> routewayDraws=routewayDrawService.selectByExample(routewayDrawExample);
//							if(routewayDraws!=null&&routewayDraws.size()>0){
//								msWithT1Bill.setMemberId(routewayDraws.get(0).getMemberId());
//							}
							msWithdrawT1BillService.insertSelective(msWithT1Bill);
						}
					}
				}
			}

			msBillRequestLog.setRespType(headerElement.element("respType").getText());
			msBillRequestLog.setRespCode(headerElement.element("respCode").getText());
			msBillRequestLog.setRespMsg(headerElement.element("respMsg").getText());
			msBillRequestLog.setSettleDate(rootElement.element("body").element("settleDate").getText());
			msBillRequestLog.setUpdateDate(new Date());
			msBillRequestLogService.updateByPrimaryKey(msBillRequestLog);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error("获取对账文件失败" + e.getMessage());
		}
	}
	/**
	 * 商户T1资金清算查询
	 * @param memberCode wx,zfb,qq
	 * @param settleDate
	 */
	public void getMsT1FundSettle(String memberCode,String settleDate) {
		try {
			String orderCode = CommonUtil.getOrderCode();
			// 调用支付通道
			String serverUrl = MSConfig.msServerUrl;
			PublicKey yhPubKey = null;
			if (serverUrl.startsWith("https://ipay")) {
				yhPubKey = CryptoUtil.getRSAPublicKey(true);
			} else {
				yhPubKey = CryptoUtil.getRSAPublicKey(false);
			}
			PrivateKey hzfPriKey = CryptoUtil.getRSAPrivateKey();
			String tranCode = "SMZF026";
			String charset = "utf-8";
			StringBuilder sBuilder = new StringBuilder();
			sBuilder.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
			sBuilder.append("<merchant>");
			sBuilder.append("<head>");
			sBuilder.append("<version>1.0.0</version>");
			sBuilder.append("<msgType>01</msgType>");
			sBuilder.append("<reqDate>" + DateFormatUtils.format(new Date(), "yyyyMMddHHmmss") + "</reqDate>");
			sBuilder.append("</head>");
			sBuilder.append("<body>");
			sBuilder.append("<merchantCode>" + memberCode + "</merchantCode>");
			sBuilder.append("<settleDate>" + settleDate + "</settleDate>");
			sBuilder.append("</body>");
			sBuilder.append("</merchant>");
			String plainXML = sBuilder.toString();
			byte[] plainBytes = plainXML.getBytes(charset);
			String keyStr = MSCommonUtil.generateLenString(16);
			
			byte[] keyBytes = keyStr.getBytes(charset);
			String encryptData = new String(Base64.encodeBase64((CryptoUtil.AESEncrypt(plainBytes, keyBytes, "AES", "AES/ECB/PKCS5Padding", null))), charset);
			String signData = new String(Base64.encodeBase64(CryptoUtil.digitalSign(plainBytes, hzfPriKey, "SHA1WithRSA")), charset);
			String encrtptKey = new String(Base64.encodeBase64(CryptoUtil.RSAEncrypt(keyBytes, yhPubKey, 2048, 11, "RSA/ECB/PKCS1Padding")), charset);
			List<NameValuePair> nvps = new LinkedList<NameValuePair>();
			nvps.add(new BasicNameValuePair("encryptData", encryptData));
			nvps.add(new BasicNameValuePair("encryptKey", encrtptKey));
			nvps.add(new BasicNameValuePair("cooperator", MSConfig.cooperator_t0));
			nvps.add(new BasicNameValuePair("signData", signData));
			nvps.add(new BasicNameValuePair("tranCode", tranCode));
			nvps.add(new BasicNameValuePair("reqMsgId", orderCode));
			byte[] b = HttpClient4Util.getInstance().doPost(serverUrl, null, nvps);
			String respStr = new String(b, charset);
			JSONObject jsonObject = JSONObject.fromObject(respStr);
			String resEncryptData = jsonObject.getString("encryptData");
			String resEncryptKey = jsonObject.getString("encryptKey");
			byte[] decodeBase64KeyBytes = Base64.decodeBase64(resEncryptKey.getBytes(charset));
			// 解密encryptKey得到merchantAESKey
			byte[] merchantAESKeyBytes = CryptoUtil.RSADecrypt(decodeBase64KeyBytes, hzfPriKey, 2048, 11, "RSA/ECB/PKCS1Padding");
			// 使用base64解码商户请求报文
			byte[] decodeBase64DataBytes = Base64.decodeBase64(resEncryptData.getBytes(charset));
			// 用解密得到的merchantAESKey解密encryptData
			byte[] merchantXmlDataBytes = CryptoUtil.AESDecrypt(decodeBase64DataBytes, merchantAESKeyBytes, "AES", "AES/ECB/PKCS5Padding", null);
			String resXml = new String(merchantXmlDataBytes, charset);
			logger.info("T1资金清算查询[{}]", new Object[] { resXml });
			//System.out.println(XmlConvertUtil.documentToJSONObjectFilter(resXml));

			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error("T1资金清算查询失败" + e.getMessage());
		}
	}
	/**资金清算失败存量查询**/
	public void getMsT1SettleFail() {
		try {
			MsBillRequestLog msBillRequestLog;
			msBillRequestLog = new MsBillRequestLog();
			msBillRequestLog.setBillType("3");
			msBillRequestLog.setCreateDate(new Date());
			msBillRequestLog.setDelFlag("0");
			msBillRequestLog.setSettleType("1");
			msBillRequestLogService.insertSelective(msBillRequestLog);
			
			String orderCode = CommonUtil.getOrderCode();
			// 调用支付通道
			String serverUrl = MSConfig.msServerUrl;
			PublicKey yhPubKey = null;
			if (serverUrl.startsWith("https://ipay")) {
				yhPubKey = CryptoUtil.getRSAPublicKey(true);
			} else {
				yhPubKey = CryptoUtil.getRSAPublicKey(false);
			}
			PrivateKey hzfPriKey = CryptoUtil.getRSAPrivateKey();
			String tranCode = TranCodeConstant.ZJQSSBCLCH;
			String charset = "utf-8";
			StringBuilder sBuilder = new StringBuilder();
			sBuilder.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
			sBuilder.append("<merchant>");
			sBuilder.append("<head>");
			sBuilder.append("<version>1.0.0</version>");
			sBuilder.append("<msgType>01</msgType>");
			sBuilder.append("<reqDate>" + DateFormatUtils.format(new Date(), "yyyyMMddHHmmss") + "</reqDate>");
			sBuilder.append("</head>");
			sBuilder.append("<body>");
			sBuilder.append("</body>");
			sBuilder.append("</merchant>");
			String plainXML = sBuilder.toString();
			byte[] plainBytes = plainXML.getBytes(charset);
			String keyStr = MSCommonUtil.generateLenString(16);
			
			byte[] keyBytes = keyStr.getBytes(charset);
			String encryptData = new String(Base64.encodeBase64((CryptoUtil.AESEncrypt(plainBytes, keyBytes, "AES", "AES/ECB/PKCS5Padding", null))), charset);
			String signData = new String(Base64.encodeBase64(CryptoUtil.digitalSign(plainBytes, hzfPriKey, "SHA1WithRSA")), charset);
			String encrtptKey = new String(Base64.encodeBase64(CryptoUtil.RSAEncrypt(keyBytes, yhPubKey, 2048, 11, "RSA/ECB/PKCS1Padding")), charset);
			List<NameValuePair> nvps = new LinkedList<NameValuePair>();
			nvps.add(new BasicNameValuePair("encryptData", encryptData));
			nvps.add(new BasicNameValuePair("encryptKey", encrtptKey));
			nvps.add(new BasicNameValuePair("cooperator", MSConfig.cooperator_t1));
			nvps.add(new BasicNameValuePair("signData", signData));
			nvps.add(new BasicNameValuePair("tranCode", tranCode));
			nvps.add(new BasicNameValuePair("reqMsgId", orderCode));
			byte[] b = HttpClient4Util.getInstance().doPost(serverUrl, null, nvps);
			String respStr = new String(b, charset);
			JSONObject jsonObject = JSONObject.fromObject(respStr);
			String resEncryptData = jsonObject.getString("encryptData");
			String resEncryptKey = jsonObject.getString("encryptKey");
			byte[] decodeBase64KeyBytes = Base64.decodeBase64(resEncryptKey.getBytes(charset));
			// 解密encryptKey得到merchantAESKey
			byte[] merchantAESKeyBytes = CryptoUtil.RSADecrypt(decodeBase64KeyBytes, hzfPriKey, 2048, 11, "RSA/ECB/PKCS1Padding");
			// 使用base64解码商户请求报文
			byte[] decodeBase64DataBytes = Base64.decodeBase64(resEncryptData.getBytes(charset));
			// 用解密得到的merchantAESKey解密encryptData
			byte[] merchantXmlDataBytes = CryptoUtil.AESDecrypt(decodeBase64DataBytes, merchantAESKeyBytes, "AES", "AES/ECB/PKCS5Padding", null);
			String resXml = new String(merchantXmlDataBytes, charset);
			logger.info("资金清算失败存量查询[{}]", new Object[] { resXml });
			//System.out.println(XmlConvertUtil.documentToJSONObjectFilter(resXml));
			Document document = DocumentHelper.parseText(resXml);
			Element rootElement = document.getRootElement();

			Element headerElement = rootElement.element("head");

			if ("S".equals(headerElement.element("respType").getText().trim())) {
				Element contentElement = rootElement.element("body").element("content");
				String contentText = contentElement.getText();

				String[] bills = contentText.split("########");

				if (bills.length > 0) {
					String[] payBills = bills[0].split("\n");
					MsSettleFailRecord msSettleFailRecord;
					for (int i = 0; i < payBills.length; i++) {
						if (!"".equals(payBills[i].trim())) {
							String[] items = payBills[i].split("\\|");
							msSettleFailRecord = new MsSettleFailRecord();
							msSettleFailRecord.setCooperator(items[0]);
							msSettleFailRecord.setTxlx(items[1]);
							msSettleFailRecord.setSmzfMsgId(items[2]);
							msSettleFailRecord.setReqMsgId(items[3]);
							msSettleFailRecord.setQsDate(items[4]);
							msSettleFailRecord.setAccNo(items[5]);
							msSettleFailRecord.setAccName(items[6]);
							msSettleFailRecord.setBankType(items[7]);
							msSettleFailRecord.setBankName(items[8]);
							msSettleFailRecord.setDrawAmount(new BigDecimal(items[9]));
							msSettleFailRecord.setRespType(items[10]);
							msSettleFailRecord.setRespCode(items[11]);
							msSettleFailRecord.setRespMsg(items[12]);
							msSettleFailRecord.setCreateBy("1");
							msSettleFailRecord.setCreateDate(new Date());
							msSettleFailRecordService.insertSelective(msSettleFailRecord);
						}
					}
				}
			}

			msBillRequestLog.setRespType(headerElement.element("respType").getText());
			msBillRequestLog.setRespCode(headerElement.element("respCode").getText());
			msBillRequestLog.setRespMsg(headerElement.element("respMsg").getText());
//			msBillRequestLog.setSettleDate(rootElement.element("body").element("settleDate").getText());
			msBillRequestLog.setUpdateDate(new Date());
			msBillRequestLogService.updateByPrimaryKey(msBillRequestLog);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("资金清算失败存量查询失败" + e.getMessage());
		}
	}

	public static void main(String[] args) {
		// getMsPayBills();
		// String
		// str="<message><head><version>1.0.0</version><msgType>02</msgType><smzfMsgId></smzfMsgId><reqDate>20161220145519</reqDate><respDate>20161220145522</respDate><respType>E</respType><respCode>200114</respCode><respMsg>T0对账文件还未生成</respMsg></head><body><settleDate>20161219</settleDate><fileType>1</fileType><content></content><remark></remark><extend1></extend1><extend2></extend2><extend3></extend3></body></message>"

	}

}
