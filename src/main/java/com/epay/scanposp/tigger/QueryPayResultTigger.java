package com.epay.scanposp.tigger;

import java.math.BigDecimal;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import com.epay.scanposp.common.constant.MSConfig;
import com.epay.scanposp.common.excep.ArgException;
import com.epay.scanposp.common.utils.CommonUtil;
import com.epay.scanposp.common.utils.DateUtil;
import com.epay.scanposp.common.utils.XmlConvertUtil;
import com.epay.scanposp.common.utils.constant.TranCodeConstant;
import com.epay.scanposp.common.utils.ms.CryptoUtil;
import com.epay.scanposp.common.utils.ms.HttpClient4Util;
import com.epay.scanposp.common.utils.ms.MSCommonUtil;
import com.epay.scanposp.entity.DebitNote;
import com.epay.scanposp.entity.DebitNoteExample;
import com.epay.scanposp.entity.MsResultNotice;
import com.epay.scanposp.entity.MsResultNoticeExample;
import com.epay.scanposp.entity.PayResultNotice;
import com.epay.scanposp.entity.PayResultNoticeExample;
import com.epay.scanposp.entity.TradeDetail;
import com.epay.scanposp.entity.TradeDetailExample;
import com.epay.scanposp.service.DebitNoteService;
import com.epay.scanposp.service.MsResultNoticeService;
import com.epay.scanposp.service.PayResultNoticeService;
import com.epay.scanposp.service.SysOfficeExtendService;
import com.epay.scanposp.service.TradeDetailService;
import com.epay.scanposp.thread.PayResultNoticeThread;

public class QueryPayResultTigger {
	
	private static Logger logger = LoggerFactory.getLogger(QueryPayResultTigger.class);
	
	@Resource
	private PayResultNoticeService payResultNoticeService;
	
	@Resource
	private SysOfficeExtendService sysOfficeExtendService;
	@Resource
	private DebitNoteService debitNoteService;
	@Resource
	private TradeDetailService tradeDetailService;
	@Resource
	private MsResultNoticeService msResultNoticeService;
	@Resource
	ThreadPoolTaskExecutor threadPoolTaskExecutor;
	
	public void queryPayResult(){
		DebitNoteExample debitNoteExample = new DebitNoteExample();
		Calendar c = Calendar.getInstance();
		c.add(Calendar.MINUTE, -10);//10分钟之前一个小时的记录
		Calendar c2 = Calendar.getInstance();
		c2.add(Calendar.MINUTE, -70);
		
		debitNoteExample.or().andStatusEqualTo("0").andCreateDateLessThanOrEqualTo(c.getTime()).andCreateDateGreaterThanOrEqualTo(c2.getTime());
		debitNoteExample.setOrderByClause(" id asc ");
		List<DebitNote> debitNotes = debitNoteService.selectByExample(debitNoteExample);
		
		for(DebitNote debitNote : debitNotes){
			msTransactionQuery(debitNote);
		}
		
	}
	
	public JSONObject msTransactionQuery(DebitNote debitNote) {
		JSONObject result = new JSONObject();
		try {
			String reqMsgId=CommonUtil.getOrderCode();

			String serverUrl = MSConfig.msServerUrl;
			PublicKey yhPubKey = null;
			if (serverUrl.startsWith("https://ipay")) {
				yhPubKey = CryptoUtil.getRSAPublicKey(true);
			} else {
				yhPubKey = CryptoUtil.getRSAPublicKey(false);
			}
			PrivateKey hzfPriKey = CryptoUtil.getRSAPrivateKey();
			String tranCode = TranCodeConstant.JYCX;//"SMZF006";
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
			sBuilder.append("<oriReqMsgId>" + debitNote.getOrderCode() + "</oriReqMsgId>");
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
			
			if("0".equals(debitNote.getSettleType())){
				nvps.add(new BasicNameValuePair("cooperator", MSConfig.cooperator_t0));
			}else{
				nvps.add(new BasicNameValuePair("cooperator", MSConfig.cooperator_t1));
			}
			nvps.add(new BasicNameValuePair("signData", signData));
			nvps.add(new BasicNameValuePair("tranCode", tranCode));
			nvps.add(new BasicNameValuePair("reqMsgId", reqMsgId));
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
			JSONObject respJSONObject = XmlConvertUtil.documentToJSONObjectFilter(resXml);
			System.out.println(respJSONObject);
			if (respJSONObject.containsKey("resEntity")) {
				JSONObject resEntity = respJSONObject.getJSONObject("resEntity");
				String respCode = resEntity.get("oriRespCode").toString();
				String respMsg = resEntity.get("oriRespMsg").toString();
				String respType = resEntity.get("oriRespType").toString();
				if(!"S".equals(respType)){//若不是交易成功的状态，则不做处理
					return null;
				}
				debitNote.setRespCode(respCode);
				debitNote.setRespMsg(respMsg);
				
				//新增一条交易明细记录
				TradeDetail tradeDetail=new TradeDetail();
				tradeDetail.setTxnDate(DateUtil.getDateFormat(new Date(), "yyyyMMdd"));
				tradeDetail.setMemberId(debitNote.getMemberId());
				tradeDetail.setMerchantCode(debitNote.getMerchantCode());
				tradeDetail.setMemberCode(debitNote.getMemberCode());
				tradeDetail.setMoney(debitNote.getMoney());
				tradeDetail.setPtSerialNo(debitNote.getOrderCode());
				tradeDetail.setOrderCode(debitNote.getOrderCode());
				tradeDetail.setReqDate(DateUtil.getDateTimeStr(debitNote.getCreateDate()));
				tradeDetail.setRespCode(respCode);
				tradeDetail.setRespDate(DateUtil.getDateTimeStr(new Date()));
				tradeDetail.setRespMsg(respMsg);
				tradeDetail.setRouteId(debitNote.getRouteId());
				tradeDetail.setTxnType(debitNote.getTxnType());
				tradeDetail.setDelFlag("0");
				tradeDetail.setCreateDate(new Date());
				if ("S".equals(respType) && "000000".equals(respCode)) {
					debitNote.setStatus("1");
					tradeDetail.setRespType("S");
					tradeDetail.setSettleType("1");
				}else if("200002".equals(respCode)){
					debitNote.setStatus("3");
					tradeDetail.setRespType("R");
				}else{
					debitNote.setStatus("2");
					tradeDetail.setRespType("E");
				}
				debitNoteService.updateByPrimaryKey(debitNote);
				//插入民生结果通知表
				MsResultNotice msResultNotice = new MsResultNotice();
				msResultNotice.setMoney(new BigDecimal(resEntity.getString("totalAmount")));
				msResultNotice.setOrderCode(debitNote.getOrderCode());
				msResultNotice.setPtSerialNo(respJSONObject.getString("smzfMsgId"));
				msResultNotice.setRespDate(respJSONObject.getString("respDate"));
				msResultNotice.setRespType(respJSONObject.getString("respType"));
				msResultNotice.setRespCode(respJSONObject.getString("respCode"));
				msResultNotice.setRespMsg(respJSONObject.getString("respMsg"));
				if(resEntity.containsKey("payType") && null != resEntity.getString("payType")){
					msResultNotice.setCardType(resEntity.getString("payType"));
				}
				if(resEntity.containsKey("payTime") && null != resEntity.getString("payTime")){
					tradeDetail.setPayTime(resEntity.get("payTime").toString());
					msResultNotice.setPayTime(resEntity.getString("payTime"));
				}
				if(resEntity.containsKey("settleDate") && null != resEntity.getString("settleDate")){
					tradeDetail.setBalanceDate(resEntity.get("settleDate").toString());
					msResultNotice.setBalanceDate(resEntity.getString("settleDate"));
				}
				if(resEntity.containsKey("channelNo") && null != resEntity.getString("channelNo")){
					tradeDetail.setChannelNo(resEntity.get("channelNo").toString());
					msResultNotice.setChannelNo(resEntity.getString("channelNo"));
				}
				if(resEntity.containsKey("isClearOrCancel") && null != resEntity.getString("isClearOrCancel")){
					msResultNotice.setSettleCancelFlag(resEntity.getString("isClearOrCancel"));
				}
				
				MsResultNoticeExample msResultNoticeExample = new MsResultNoticeExample();
				msResultNoticeExample.or().andOrderCodeEqualTo(debitNote.getOrderCode());
				List<MsResultNotice> msResults = msResultNoticeService.selectByExample(msResultNoticeExample);
				if(msResults!=null && msResults.size()>0){
				}else{
					msResultNoticeService.insertSelective(msResultNotice);
				}
				//查询是否需要通知第三方回调
				PayResultNoticeExample payResultNoticeExample = new PayResultNoticeExample();
				payResultNoticeExample.or().andOrderCodeEqualTo(debitNote.getOrderCode()).andCountsLessThan(10);
				List<PayResultNotice> payResultNoticeList = payResultNoticeService.selectByExample(payResultNoticeExample);
				if(payResultNoticeList.size()>0){
					PayResultNotice payResultNotice = payResultNoticeList.get(0);
					if("1".equals(payResultNotice.getStatus())||"2".equals(payResultNotice.getStatus())){//没有发送过回调通知的才做
						payResultNotice.setResultMessage(debitNote.getRespMsg());
						payResultNotice.setStatus("2");
						payResultNotice.setPayTime(msResultNotice.getPayTime());
						if ("S".equals(respType) && "000000".equals(respCode)) {
							payResultNotice.setRespType("2");
							payResultNotice.setResultCode("0000");
							payResultNotice.setResultMessage("交易成功");
						}else if("200002".equals(respCode)){
							payResultNotice.setRespType("1");
							payResultNotice.setResultCode("0009");   
							payResultNotice.setResultMessage("交易正在处理中...");
						}else{
							payResultNotice.setRespType("3");
							payResultNotice.setResultCode("0003");   
							payResultNotice.setResultMessage("交易失败");
						}
						//获取民生通知后即向商户提供结果通知 (后续若因其他因素需多次通知商户,则由相应的定时任务完成)
						PayResultNoticeThread payResultNoyiceThread = new PayResultNoticeThread(payResultNoticeService, sysOfficeExtendService, payResultNotice);
						threadPoolTaskExecutor.execute(payResultNoyiceThread);
					}
					
					tradeDetail.setInterfaceType(payResultNotice.getInterfaceType());
					tradeDetail.setPlatformType(payResultNotice.getPlatformType());
					tradeDetail.setOrderNumOuter(payResultNotice.getOrderNumOuter());
				}
				tradeDetail.setCardType(msResultNotice.getCardType());
				if ("S".equals(respType) && "000000".equals(respCode)) {//交易成功才插入数据
					TradeDetailExample tradeDetailExample = new TradeDetailExample();
					tradeDetailExample.or().andOrderCodeEqualTo(debitNote.getOrderCode());
					List<TradeDetail> trades = tradeDetailService.selectByExample(tradeDetailExample);
					if(trades!=null && trades.size()>0){
					}else{
						tradeDetailService.insertSelective(tradeDetail);
					}
				}
			}
		} catch (ArgException e) {
			result.put("returnCode", "4004");
			result.put("returnMsg", e.getMessage());
			logger.info(e.getMessage());
			return result;
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			result.put("returnCode", "4004");
			result.put("returnMsg", "请求失败");
			return result;
		}
		result.put("returnCode", "0000");
		result.put("returnMsg", "成功");
		return result;
	}
}
