package com.epay.scanposp.tigger;

import java.math.BigDecimal;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.epay.scanposp.common.constant.MSConfig;
import com.epay.scanposp.common.constant.SysConfig;
import com.epay.scanposp.common.utils.CommonUtil;
import com.epay.scanposp.common.utils.DateUtil;
import com.epay.scanposp.common.utils.XmlConvertUtil;
import com.epay.scanposp.common.utils.ms.CryptoUtil;
import com.epay.scanposp.common.utils.ms.HttpClient4Util;
import com.epay.scanposp.common.utils.ms.MSCommonUtil;
import com.epay.scanposp.controller.DebitNoteController;
import com.epay.scanposp.entity.DebitNote;
import com.epay.scanposp.entity.DebitNoteExample;
import com.epay.scanposp.entity.MemberInfo;
import com.epay.scanposp.entity.RoutewayDraw;
import com.epay.scanposp.service.DebitNoteExtendService;
import com.epay.scanposp.service.DebitNoteService;
import com.epay.scanposp.service.MemberInfoService;
import com.epay.scanposp.service.MsbillService;
import com.epay.scanposp.service.RoutewayDrawService;
import com.epay.scanposp.service.TradeDetailService;

public class DrawCashBeanTigger {
	private static Logger logger = LoggerFactory.getLogger(DebitNoteController.class);
	@Resource
	private DebitNoteService debitNoteService;
	
	@Resource
	private DebitNoteExtendService debitNoteExtendService;
	
	@Resource
	private TradeDetailService tradeDetailService;
	
	@Resource
	private RoutewayDrawService routewayDrawService;
	
	@Resource
	private MsbillService msbillService;
	
	@Resource
	private MemberInfoService memberInfoService;
	
	
	public void drawCash(){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.add(Calendar.MINUTE, -15);
		Date startDate = calendar.getTime();
		calendar.add(Calendar.MINUTE, -15);
		Date endDate = calendar.getTime();
		Map<String,Object> paramMap = new HashMap<String,Object>();
//		paramMap.put("createDateStart", startDate);
		paramMap.put("createDateEnd", endDate);
		paramMap.put("status", 1);
		paramMap.put("respCode", "000000");
		List<DebitNote> debitNotes = debitNoteExtendService.getDebitMerchantInfoDistinct(paramMap);
		MemberInfo memberInfo=null;
		for(DebitNote debitNote : debitNotes){
			//System.out.println(debitNote.getMemberId()+"====="+ debitNote.getMemberCode() +"======="+ debitNote.getMerchantCode());
			memberInfo=	memberInfoService.selectByPrimaryKey(debitNote.getMemberId());
			msWithdraw(debitNote.getMemberId(), debitNote.getMemberCode(), debitNote.getMerchantCode(),memberInfo.getT0TradeRate());
		}
		
	}
	
	
	public void msWithdraw(Integer memberId,String memberCode,String merchantCode,BigDecimal memberTradeRate) {
		try {
			
			String orderCode =CommonUtil.getOrderCode();
			//创建提现明细
			RoutewayDraw routewayDraw=new RoutewayDraw();
			routewayDraw.setCreateDate(new Date());
			routewayDraw.setDelFlag("0");
			routewayDraw.setMemberCode(memberCode);
			routewayDraw.setMemberId(memberId);
			routewayDraw.setMemberTradeRate(memberTradeRate);
			routewayDraw.setOrderCode(orderCode);
			routewayDraw.setPtSerialNo(orderCode);
			routewayDraw.setReqDate(DateUtil.getDateTimeStr(new Date()));
			routewayDrawService.insertSelective(routewayDraw);
			
			
			String callBack = SysConfig.serverUrl + "/debitNote/msWithdrawNotify";
			//System.out.println("-------------通知地址------" + callBack + "------------");

			// 调用支付通道
			
			String serverUrl = MSConfig.msServerUrl;
			PublicKey yhPubKey = null;
			if (serverUrl.startsWith("https://ipay")) {
				yhPubKey = CryptoUtil.getRSAPublicKey(true);
			} else {
				yhPubKey = CryptoUtil.getRSAPublicKey(false);
			}
			PrivateKey hzfPriKey = CryptoUtil.getRSAPrivateKey();
			String tranCode = "SMZF021";
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
			sBuilder.append("<merchantCode>" + merchantCode + "</merchantCode>");
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
			nvps.add(new BasicNameValuePair("callBack", callBack));
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
			JSONObject respJSONObject = XmlConvertUtil.documentToJSONObjectFilter(resXml);
			//System.out.println(respJSONObject);
			
			routewayDraw.setRespCode(respJSONObject.getString("respCode"));
			routewayDraw.setRespDate(respJSONObject.getString("respDate"));
			routewayDraw.setRespMsg(respJSONObject.getString("respMsg"));
			routewayDraw.setRespType(respJSONObject.getString("respType"));
			routewayDrawService.updateByPrimaryKeySelective(routewayDraw);
			
		}  catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error("商户："+memberId+"提现失败"+e.getMessage());
		}
	}
}
