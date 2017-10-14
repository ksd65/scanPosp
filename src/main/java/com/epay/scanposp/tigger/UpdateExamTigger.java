package com.epay.scanposp.tigger;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import net.sf.json.JSONObject;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.epay.scanposp.common.constant.MSConfig;
import com.epay.scanposp.common.constant.WxConfig;
import com.epay.scanposp.common.excep.ArgException;
import com.epay.scanposp.common.utils.CommonUtil;
import com.epay.scanposp.common.utils.XmlConvertUtil;
import com.epay.scanposp.common.utils.constant.TranCodeConstant;
import com.epay.scanposp.common.utils.ms.CryptoUtil;
import com.epay.scanposp.common.utils.ms.HttpClient4Util;
import com.epay.scanposp.common.utils.ms.MSCommonUtil;
import com.epay.scanposp.entity.MemberInfo;
import com.epay.scanposp.entity.MemberInfoExample;
import com.epay.scanposp.entity.MsChildConfig;
import com.epay.scanposp.service.MemberInfoService;
import com.epay.scanposp.service.MsChildConfigService;
/**
 * 商户入驻 未收到回调通知的，主动发起查询
 * @author WYW
 */
public class UpdateExamTigger {
	
	private static Logger logger = LoggerFactory.getLogger(UpdateExamTigger.class);
	
	@Autowired
	private MemberInfoService memberInfoService;
	@Autowired
	private MsChildConfigService msChildConfigService;
	
	public void updateExam(){
		MemberInfoExample memberInfoExample = new MemberInfoExample();
		memberInfoExample.or().andWxMerchantCodeIsNull();
		memberInfoExample.or().andZfbMerchantCodeIsNull();
		memberInfoExample.or().andQqMerchantCodeIsNull();
		memberInfoExample.or().andBdMerchantCodeIsNull();
		memberInfoExample.or().andJdMerchantCodeIsNull();
		memberInfoExample.or().andWxMerchantCodeEqualTo("");
		memberInfoExample.or().andZfbMerchantCodeEqualTo("");
		memberInfoExample.or().andQqMerchantCodeEqualTo("");
		memberInfoExample.or().andBdMerchantCodeEqualTo("");
		memberInfoExample.or().andJdMerchantCodeEqualTo("");
		List<String> values = new ArrayList<String>();
		values.add("0");
		values.add("2");
		memberInfoExample.or().andWxStatusIn(values);
		memberInfoExample.or().andZfbStatusIn(values);
		memberInfoExample.or().andQqStatusIn(values);
		memberInfoExample.or().andBdStatusIn(values);
		memberInfoExample.or().andJdStatusIn(values);
		
		memberInfoExample.or().andWxChannelMerchantCodeIsNull();
		memberInfoExample.or().andZfbChannelMerchantCodeIsNull();
//		memberInfoExample.or().andQqChannelMerchantCodeIsNull();
//		memberInfoExample.or().andBdChannelMerchantCodeIsNull();
		
		memberInfoExample.or().andWxChannelMerchantCodeEqualTo("");
		memberInfoExample.or().andZfbChannelMerchantCodeEqualTo("");
//		memberInfoExample.or().andQqChannelMerchantCodeEqualTo("");
//		memberInfoExample.or().andBdChannelMerchantCodeEqualTo("");
		
		List<MemberInfo> memberInfoList = memberInfoService.selectByExample(memberInfoExample);
		
		//System.out.println(memberInfoList.size());
		for(MemberInfo memberInfo:memberInfoList){
			if("0".equals(memberInfo.getWxStatus()) || StringUtils.isEmpty(memberInfo.getWxMerchantCode()) || StringUtils.isBlank(memberInfo.getWxChannelMerchantCode())){
				JSONObject returnJson = msMerchantInfo(null, memberInfo.getWxMemberCode(), memberInfo);
				if("0000".equals(returnJson.getString("returnCode"))){
					memberInfo.setWxMerchantCode(returnJson.getString("merchantCode"));
					memberInfo.setWxChannelMerchantCode(returnJson.getString("channelMerchantCode"));
					memberInfo.setWxStatus("1");
					msChildMerchant(CommonUtil.getOrderCode(), memberInfo.getWxMerchantCode(), memberInfo, "1", WxConfig.authDirectory);
					msChildMerchant(CommonUtil.getOrderCode(), memberInfo.getWxMerchantCode(), memberInfo, "2", WxConfig.appidSH);
				}else if("4001".equals(returnJson.getString("returnCode"))){
					memberInfo.setWxStatus("2");
				}
			}
			if("0".equals(memberInfo.getZfbStatus()) || StringUtils.isEmpty(memberInfo.getZfbMerchantCode()) || StringUtils.isBlank(memberInfo.getZfbChannelMerchantCode())){
				JSONObject returnJson = msMerchantInfo(null, memberInfo.getZfbMemberCode(), memberInfo);
				if("0000".equals(returnJson.getString("returnCode"))){
					memberInfo.setZfbMerchantCode(returnJson.getString("merchantCode"));
					memberInfo.setZfbChannelMerchantCode(returnJson.getString("channelMerchantCode"));
					memberInfo.setZfbStatus("1");
				}else if("4001".equals(returnJson.getString("returnCode"))){
					memberInfo.setZfbStatus("2");
				}
			}
			if("0".equals(memberInfo.getQqStatus()) || StringUtils.isEmpty(memberInfo.getQqMerchantCode()) || StringUtils.isBlank(memberInfo.getQqChannelMerchantCode())){
				JSONObject returnJson = msMerchantInfo(null, memberInfo.getQqMemberCode(), memberInfo);
				if("0000".equals(returnJson.getString("returnCode"))){
					memberInfo.setQqMerchantCode(returnJson.getString("merchantCode"));
					memberInfo.setQqChannelMerchantCode(returnJson.getString("channelMerchantCode"));
					memberInfo.setQqStatus("1");
				}else if("4001".equals(returnJson.getString("returnCode"))){
					memberInfo.setQqStatus("2");
				}
			}
			if("0".equals(memberInfo.getBdStatus()) || StringUtils.isEmpty(memberInfo.getBdMerchantCode()) || StringUtils.isBlank(memberInfo.getBdChannelMerchantCode())){
				JSONObject returnJson = msMerchantInfo(null, memberInfo.getBdMemberCode(), memberInfo);
				if("0000".equals(returnJson.getString("returnCode"))){
					memberInfo.setBdMerchantCode(returnJson.getString("merchantCode"));
					memberInfo.setBdChannelMerchantCode(returnJson.getString("channelMerchantCode"));
					memberInfo.setBdStatus("1");
				}else if("4001".equals(returnJson.getString("returnCode"))){
					memberInfo.setBdStatus("2");
				}
			}
			if("0".equals(memberInfo.getJdStatus()) || StringUtils.isEmpty(memberInfo.getJdMerchantCode()) || StringUtils.isBlank(memberInfo.getJdChannelMerchantCode())){
				JSONObject returnJson = msMerchantInfo(null, memberInfo.getJdMemberCode(), memberInfo);
				if("0000".equals(returnJson.getString("returnCode"))){
					memberInfo.setJdMerchantCode(returnJson.getString("merchantCode"));
					memberInfo.setJdChannelMerchantCode(returnJson.getString("channelMerchantCode"));
					memberInfo.setJdStatus("1");
				}else if("4001".equals(returnJson.getString("returnCode"))){
					memberInfo.setJdStatus("2");
				}
			}
			memberInfoService.updateByPrimaryKeySelective(memberInfo);
		}
	}
	
	public JSONObject msMerchantInfo(String queryId,String merchantId,MemberInfo memberInfo){
		JSONObject result=new JSONObject();
		String tranCode = TranCodeConstant.SHCX;
		String reqMsgId=CommonUtil.getOrderCode();
		try {
			// 调用支付通道
			String serverUrl = MSConfig.msServerUrl;
			PublicKey yhPubKey = null;
			if (serverUrl.startsWith("https://ipay")) {
				yhPubKey = CryptoUtil.getRSAPublicKey(true);
			} else {
				yhPubKey = CryptoUtil.getRSAPublicKey(false);
			}
			PrivateKey hzfPriKey = CryptoUtil.getRSAPrivateKey();
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
			sBuilder.append("<merchantId>" + merchantId + "</merchantId>");
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
			if("0".equals(memberInfo.getSettleType())){
				nvps.add(new BasicNameValuePair("cooperator", MSConfig.cooperator_t0));
			}else{
				nvps.add(new BasicNameValuePair("cooperator", MSConfig.cooperator_t1));
			}
			nvps.add(new BasicNameValuePair("signData", signData));
			nvps.add(new BasicNameValuePair("tranCode", tranCode));
			nvps.add(new BasicNameValuePair("reqMsgId",reqMsgId ));
			byte[] b = HttpClient4Util.getInstance().doPost(serverUrl, null, nvps);
			// post表单,将响应回来的二维码码字符串转为图片 
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
			logger.info(queryId+"查询商户信息[{}]",  resXml);
			JSONObject respJSONObject = XmlConvertUtil.documentToJSONObjectFilter(resXml);
			if("000000".equals(respJSONObject.getString("respCode"))){
				JSONObject resEntity = respJSONObject.getJSONObject("resEntity");
				String oriRespCode = resEntity.getString("oriRespCode");
				if("000000".equals(oriRespCode)){
					String merchantCode = resEntity.getString("merchantCode");
					String channelMerchantCode = resEntity.getString("channelMerchantCode");
					result.put("returnCode", "0000");
					result.put("merchantCode", merchantCode);
					result.put("channelMerchantCode", channelMerchantCode);
					result.put("returnMsg", "成功");
				}else if("200013".equals(oriRespCode)){//审核失败
					result.put("returnCode", "4001");
					result.put("returnMsg", resEntity.getString("oriRespMsg"));
				}else{
					result.put("returnCode", "4004");
					result.put("returnMsg", resEntity.getString("oriRespMsg"));
				}
			}else{
				result.put("returnCode", "4004");
				result.put("returnMsg", respJSONObject.getString("respMsg"));
			}
		} catch (ArgException e) {
			result.put("returnCode", "4004");
			result.put("returnMsg", "商户信息查询失败");
			logger.info(e.getMessage());
			return result;
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			result.put("returnCode", "4004");
			result.put("returnMsg", "请求失败");
			return result;
		}
		return result;
	}
	
	//绑定授权目录和公众号等
	public JSONObject msChildMerchant(String queryId,String merchantId,MemberInfo memberInfo,String zdlx,String zdsj){
		JSONObject result=new JSONObject();
		String tranCode = TranCodeConstant.SHWXPZ;//"SMZF030";//TranCodeConstant.SHTY;
		String reqMsgId=CommonUtil.getOrderCode();
		
		MsChildConfig msChildConfig = new MsChildConfig();
		msChildConfig.setMemberId(memberInfo.getId());
		msChildConfig.setMemberCode(memberInfo.getCode());
		msChildConfig.setReqMsgId(reqMsgId);
		msChildConfig.setZdlx(zdlx);
		msChildConfig.setZdsj(zdsj);
		msChildConfig.setCreateBy("1");
		msChildConfig.setCreateDate(new Date());
		try {
			Thread.sleep(5000);//沉睡5秒，防止操作过快导致配置失败
			// 调用支付通道
			String serverUrl = MSConfig.msServerUrl;
			PublicKey yhPubKey = null;
			if (serverUrl.startsWith("https://ipay")) {
				yhPubKey = CryptoUtil.getRSAPublicKey(true);
			} else {
				yhPubKey = CryptoUtil.getRSAPublicKey(false);
			}
			PrivateKey hzfPriKey = CryptoUtil.getRSAPrivateKey();
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
			sBuilder.append("<merchantCode>" + merchantId + "</merchantCode>");
			sBuilder.append("<zdlx>" + zdlx + "</zdlx>");
			sBuilder.append("<zdsj>" + zdsj + "</zdsj>");
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
			
			if("0".equals(memberInfo.getSettleType())){
				nvps.add(new BasicNameValuePair("cooperator", MSConfig.cooperator_t0));
			}else{
				nvps.add(new BasicNameValuePair("cooperator", MSConfig.cooperator_t1));
			}
			
			nvps.add(new BasicNameValuePair("signData", signData));
			nvps.add(new BasicNameValuePair("tranCode", tranCode));
			nvps.add(new BasicNameValuePair("reqMsgId",reqMsgId ));
			byte[] b = HttpClient4Util.getInstance().doPost(serverUrl, null, nvps);
			// post表单,将响应回来的二维码码字符串转为图片 
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
			logger.info(queryId+"调用子商户配置[{}]",  resXml);
			JSONObject respJSONObject = XmlConvertUtil.documentToJSONObjectFilter(resXml);
			if("000000".equals(respJSONObject.getString("respCode"))){
				result.put("returnCode", "0000");
				result.put("returnMsg", "成功");
			}else{
				result.put("returnCode", "4004");
				result.put("returnMsg", respJSONObject.getString("respMsg"));
			}
			msChildConfig.setRespType(respJSONObject.getString("respType"));
			msChildConfig.setRespCode(respJSONObject.getString("respCode"));
			msChildConfig.setRespMsg(respJSONObject.getString("respMsg"));
			msChildConfig.setRespDate(respJSONObject.getString("respDate"));
			msChildConfigService.insertSelective(msChildConfig);
		} catch (ArgException e) {
			result.put("returnCode", "4004");
			result.put("returnMsg", "子商户配置失败");
			logger.info(e.getMessage());
			return result;
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			result.put("returnCode", "4004");
			result.put("returnMsg", "请求失败");
			return result;
		}
		return result;
	}
}
