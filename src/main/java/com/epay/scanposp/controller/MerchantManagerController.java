package com.epay.scanposp.controller;

import java.math.BigDecimal;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.epay.scanposp.common.constant.MSConfig;
import com.epay.scanposp.common.constant.WxConfig;
import com.epay.scanposp.common.excep.ArgException;
import com.epay.scanposp.common.utils.CommonUtil;
import com.epay.scanposp.common.utils.XmlConvertUtil;
import com.epay.scanposp.common.utils.constant.TranCodeConstant;
import com.epay.scanposp.common.utils.ms.CryptoUtil;
import com.epay.scanposp.common.utils.ms.HttpClient4Util;
import com.epay.scanposp.common.utils.ms.MSCommonUtil;
import com.epay.scanposp.entity.MemberBank;
import com.epay.scanposp.entity.MemberBankExample;
import com.epay.scanposp.entity.MemberInfo;
import com.epay.scanposp.entity.MemberInfoExample;
import com.epay.scanposp.entity.MsChildConfig;
import com.epay.scanposp.service.MemberBankService;
import com.epay.scanposp.service.MemberInfoService;
import com.epay.scanposp.service.MsChildConfigService;

@Controller
@RequestMapping("api/merchantManager")
public class MerchantManagerController {
	private static Logger logger = LoggerFactory.getLogger(MerchantManagerController.class);
	
	@Autowired
	private MemberInfoService memberInfoService ;
	
	@Autowired
	private MemberBankService memberBankService;
	
	@Autowired
	private MsChildConfigService msChildConfigService;
	
	// TODO 接口异常  待测试
	@ResponseBody
	@RequestMapping("/modify")
	public String merchantModify(Model model, HttpServletRequest request) {
		JSONObject requestPRM = (JSONObject) request.getAttribute("requestPRM");
		JSONObject reqDataJson = requestPRM.getJSONObject("reqData");// 获取请求参数
		JSONObject result=new JSONObject();
		
		if(!reqDataJson.containsKey("memberID")){
			result.put("returnCode", "4004");
			result.put("returnMsg", "缺少用户ID信息");
			return result.toString();
		}
		int memberID = reqDataJson.getInt("memberID");
		MemberInfo memberInfo = memberInfoService.selectByPrimaryKey(memberID);
		if(null == memberInfo){
			result.put("returnCode", "4004");
			result.put("returnMsg", "用户不存在");
			return result.toString();
		}
		
		MemberBankExample memberBankExample = new MemberBankExample();
		memberBankExample.or().andMemberIdEqualTo(memberID).andDelFlagEqualTo("0");
		List<MemberBank> memberBankList = memberBankService.selectByExample(memberBankExample);
		if(memberBankList.size() == 0 || memberBankList.size()>1){
			result.put("returnCode", "4004");
			result.put("returnMsg", "用户银行卡信息缺失或错误");
			return result.toString();
		}
		MemberBank memberBankInfo = memberBankList.get(0);
		
		if(reqDataJson.containsKey("province")){
			memberBankInfo.setProvince(reqDataJson.getString("province"));
		}
		if(reqDataJson.containsKey("city")){
			memberBankInfo.setCity(reqDataJson.getString("city"));
		}
		if(reqDataJson.containsKey("bankId")){
			memberBankInfo.setBankId(reqDataJson.getString("bankId"));
		}
		if(reqDataJson.containsKey("subId")){
			memberBankInfo.setSubId(reqDataJson.getString("subId"));
		}
		
		/*
		BankSubExample bankSubExample = new BankSubExample();
		bankSubExample.or().andBankIdEqualTo(Integer.parseInt(memberBankInfo.getBankId())).andSubIdEqualTo(memberBankInfo.getSubId());
		List<BankSub> bankSubList = bankSubService.selectByExample(bankSubExample);
		if(null == bankSubList || bankSubList.size()>1){
			result.put("returnCode", "4004");
			result.put("returnMsg", "银行支行信息配置错误");
			return result.toString();
		}
		memberBankInfo.setBankOpen(bankSubList.get(0).getSubName());
		*/
		
		//商铺简称
		if(reqDataJson.containsKey("shortName")){
			memberInfo.setShortName(reqDataJson.getString("shortName"));
		}
		//商户地址
		if(reqDataJson.containsKey("merchantAddress")){
			memberInfo.setAddr(reqDataJson.getString("merchantAddress"));
		}
		//省
		if(reqDataJson.containsKey("provinceCode")){
			memberInfo.setProvince(reqDataJson.getString("provinceCode"));
		}
		//市
		if(reqDataJson.containsKey("cityCode")){
			memberInfo.setCity(reqDataJson.getString("cityCode"));
		}
		//区县
		if(reqDataJson.containsKey("districtCode")){
			memberInfo.setCounty(reqDataJson.getString("districtCode"));
		}
		//身份证号码
		String idCard = null;
		if(reqDataJson.containsKey("idCard")){
			idCard = reqDataJson.getString("idCard");
			memberInfo.setCertNbr(idCard);
		}
		//联系人姓名
		String contactName = null;
		if(reqDataJson.containsKey("contactName")){
			contactName = reqDataJson.getString("contactName");
			memberInfo.setContact(contactName);
		}
		//联系人类型
		if(reqDataJson.containsKey("contactType")){
			memberInfo.setContactType(reqDataJson.getString("contactType"));
		}
		//客服电话
		if(reqDataJson.containsKey("servicePhone")){
			memberInfo.setLoginCode(reqDataJson.getString("servicePhone"));
			memberInfo.setMobilePhone(reqDataJson.getString("servicePhone"));
		}
		//收款人账户
		if(reqDataJson.containsKey("accNo")){
			memberInfo.setCardNbr(reqDataJson.getString("accNo"));
			memberBankInfo.setAccountNumber(reqDataJson.getString("accNo"));
		}
		//收款人姓名
		if(reqDataJson.containsKey("accName")){
			memberInfo.setContact(reqDataJson.getString("accName"));
			memberBankInfo.setAccountName(reqDataJson.getString("accName"));
		}
		//结算方式
		if(reqDataJson.containsKey("settleType")){
			memberInfo.setSettleType(reqDataJson.getString("settleType"));
			memberBankInfo.setSettleType(reqDataJson.getString("settleType"));
		}
		if(reqDataJson.containsKey("t0drawFee")){
			BigDecimal bd = new BigDecimal(reqDataJson.getString("t0drawFee"));
			memberInfo.setT0DrawFee(bd);
		}
		if(reqDataJson.containsKey("t0tradeRate")){
			BigDecimal bd = new BigDecimal(reqDataJson.getString("t0tradeRate"));
			memberInfo.setT0TradeRate(bd);
		}
		if(reqDataJson.containsKey("t1drawFee")){
			BigDecimal bd = new BigDecimal(reqDataJson.getString("t1drawFee"));
			memberInfo.setT1DrawFee(bd);
		}
		if(reqDataJson.containsKey("t1tradeRate")){
			BigDecimal bd = new BigDecimal(reqDataJson.getString("t1tradeRate"));
			memberInfo.setT1TradeRate(bd);
		}
		
		JSONObject wxMerchantCodeResult = null;
		JSONObject zfbMerchantCodeResult = null;
		JSONObject qqMerchantCodeResult = null;
		JSONObject bdMerchantCodeResult = null;
		JSONObject jdMerchantCodeResult = null;
		String wxReturnCode = null;
		String zfbReturnCode = null;
		String qqReturnCode = null;
		String bdReturnCode = null;
		String jdReturnCode = null;
		
		if(StringUtils.isNotEmpty(memberInfo.getWxMemberCode())){
			wxMerchantCodeResult = modifyMsMerchantInfo(memberInfo.getWxMemberCode(),contactName,memberInfo,memberBankInfo,"WX");
			wxReturnCode = wxMerchantCodeResult.getString("returnCode");
		}else{
			wxReturnCode = "0000";
		}
		if(StringUtils.isNotEmpty(memberInfo.getZfbMemberCode())){
			zfbMerchantCodeResult = modifyMsMerchantInfo(memberInfo.getZfbMemberCode(),contactName,memberInfo,memberBankInfo,"ZFB");
			zfbReturnCode = zfbMerchantCodeResult.getString("returnCode");
		}else{
			zfbReturnCode = "0000";
		}
		if(StringUtils.isNotEmpty(memberInfo.getQqMemberCode())){
			qqMerchantCodeResult = modifyMsMerchantInfo(memberInfo.getQqMemberCode(),contactName,memberInfo,memberBankInfo,"QQ");
			qqReturnCode = qqMerchantCodeResult.getString("returnCode");
		}else{
			qqReturnCode = "0000";
		}
		if(StringUtils.isNotEmpty(memberInfo.getBdMemberCode())){
			bdMerchantCodeResult = modifyMsMerchantInfo(memberInfo.getBdMemberCode(),contactName,memberInfo,memberBankInfo,"BD");
			bdReturnCode = bdMerchantCodeResult.getString("returnCode");
		}else{
			bdReturnCode = "0000";
		}
		if(StringUtils.isNotEmpty(memberInfo.getJdMemberCode())){
			jdMerchantCodeResult = modifyMsMerchantInfo(memberInfo.getJdMemberCode(),contactName,memberInfo,memberBankInfo,"JD");
			jdReturnCode = jdMerchantCodeResult.getString("returnCode");
		}else{
			jdReturnCode = "0000";
		}
		
		//支付宝接口暂时不能正常调用   仅开放微信商户信息的修改以供测试
//		if("0000".equals(wxMerchantCodeResult.getString("returnCode"))){
//			wxMerchantCode = wxMerchantCodeResult.getString("merchantCode");
//		}else{
//			result.put("returnCode", "4001");
//			result.put("returnMsg", "商户信息修改失败,请重试");
//			return result.toString();
//		}
		//正式应使用该段逻辑
		if("0000".equals(wxReturnCode) && "0000".equals(zfbReturnCode) && "0000".equals(qqReturnCode) && "0000".equals(bdReturnCode)){
//			wxMerchantCode = wxMerchantCodeResult.getString("merchantCode");
//			zfbMerchantCode = zfbMerchantCodeResult.getString("merchantCode");
		}else if(!"0000".equals(wxReturnCode) && !"0000".equals(zfbReturnCode) && !"0000".equals(qqReturnCode) && !"0000".equals(bdReturnCode)){
			result.put("returnCode", "4001");
			result.put("returnMsg", "商户信息修改失败,请重试");
			return result.toString();
		}else{
			/*
			MemberInfo memberStatusUpdate = new MemberInfo();
			memberStatusUpdate.setId(memberInfo.getId());
			memberStatusUpdate.setStatus("2");
			memberInfoService.updateByPrimaryKeySelective(memberStatusUpdate);
			result.put("returnCode", "4002");
			result.put("returnMsg", "商户信息修改失败,将暂时停止交易");
			return result.toString();
			*/
		}
//		memberInfo.setWxMerchantCode(wxMerchantCode);
//		memberInfo.setZfbMerchantCode(zfbMerchantCode);
		
		memberInfoService.updateByPrimaryKey(memberInfo);
		memberBankService.updateByPrimaryKeySelective(memberBankInfo);
		
		result.put("returnCode", "0000");
		result.put("returnMsg", "商户信息修改成功");
		return result.toString();
	}
	
	/**
	 * 民生修改商户信息接口
	 * @param merchantId
	 * @param contactName
	 * @param memberInfo
	 * @param memberBank
	 * @return
	 */
	public JSONObject modifyMsMerchantInfo(String merchantId,String contactName,MemberInfo memberInfo,MemberBank memberBank,String txnType){
		JSONObject result=new JSONObject();
		String tranCode = TranCodeConstant.SHXG;
		String reqMsgId=CommonUtil.getOrderCode();
//		String merchantId = null;
//		if(MSPayWayConstant.WXPAY.equals(payWay)){
//			merchantId = memberInfo.getWxMemberCode();
//		}else if(MSPayWayConstant.ZFBZF.equals(payWay)){
//			merchantId = memberInfo.getZfbMemberCode();
//		}
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
			sBuilder.append("<shortName>" + memberInfo.getShortName() + "</shortName>");
			sBuilder.append("<merchantAddress>" + memberInfo.getAddr() + "</merchantAddress>");
			sBuilder.append("<provinceCode>"+memberInfo.getProvince()+"</provinceCode>");
			sBuilder.append("<cityCode>"+memberInfo.getCity()+"</cityCode>");
			sBuilder.append("<districtCode>"+memberInfo.getCounty()+"</districtCode>");
			sBuilder.append("<servicePhone>" + memberInfo.getMobilePhone() + "</servicePhone>");
			sBuilder.append("<contactName>"+memberInfo.getContact()+"</contactName>");
			sBuilder.append("<contactType>"+memberInfo.getContactType()+"</contactType>");
			if(txnType.equals("ZFB")){
				sBuilder.append("<category>"+"2015063000020189"+"</category>");
			}
			sBuilder.append("<idCard>"+memberInfo.getCertNbr()+"</idCard>");
			sBuilder.append("<accNo>" + memberInfo.getCardNbr() + "</accNo>");
			sBuilder.append("<accName>" + memberInfo.getContact() + "</accName>");
			if("1".equals(memberInfo.getSettleType())){
				sBuilder.append("<bankType>" + memberBank.getSubId() + "</bankType>");
				sBuilder.append("<bankName>" + memberBank.getBankOpen() + "</bankName>");
			}
			//if("0".equals(memberInfo.getSettleType())){
			sBuilder.append("<t0drawFee>" + memberInfo.getT0DrawFee() + "</t0drawFee>");
			sBuilder.append("<t0tradeRate>" + memberInfo.getT0TradeRate() + "</t0tradeRate>");
			//}
			sBuilder.append("<t1drawFee>" + memberInfo.getT1DrawFee() + "</t1drawFee>");
			sBuilder.append("<t1tradeRate>" + memberInfo.getT1TradeRate() + "</t1tradeRate>");
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
			if("000000".equals(respJSONObject.getString("respCode"))){
				JSONObject resEntity = respJSONObject.getJSONObject("resEntity");
				String merchantCode = resEntity.getString("merchantCode");
				result.put("returnCode", "0000");
				result.put("merchantCode", merchantCode);
				result.put("returnMsg", "成功");
			}else{
				result.put("returnCode", "4004");
				result.put("returnMsg", respJSONObject.getString("respMsg"));
			}
			logger.info("resXml[{}]", new Object[] { resXml });
		} catch (ArgException e) {
			result.put("returnCode", "4004");
			result.put("returnMsg", "商户入驻失败");
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
	/**
	 * 查询商户信息，Test用
	 */
	@ResponseBody
	@RequestMapping("/merchantInfo")
	public String queryMsMerchantInfo(Model model, HttpServletRequest request){
		JSONObject requestPRM = (JSONObject) request.getAttribute("requestPRM");
		JSONObject reqDataJson = requestPRM.getJSONObject("reqData");// 获取请求参数
		JSONObject result=new JSONObject();
		
		if(!reqDataJson.containsKey("memberID")){
			result.put("returnCode", "4004");
			result.put("returnMsg", "缺少用户ID信息");
			return result.toString();
		}
		int memberID = reqDataJson.getInt("memberID");
		MemberInfo memberInfo = memberInfoService.selectByPrimaryKey(memberID);
		if(null == memberInfo){
			result.put("returnCode", "4004");
			result.put("returnMsg", "用户不存在");
			return result.toString();
		}
		
		JSONObject wxMerchantCodeResult = null;
		JSONObject zfbMerchantCodeResult = null;
		JSONObject qqMerchantCodeResult = null;
		JSONObject bdMerchantCodeResult = null;
		JSONObject jdMerchantCodeResult = null;
		String wxReturnCode = null;
		String zfbReturnCode = null;
		String qqReturnCode = null;
		String bdReturnCode = null;
		String jdReturnCode = null;
		String queryId=CommonUtil.getOrderCode();//记录标识 便于查找日志
		
		if(StringUtils.isNotEmpty(memberInfo.getWxMemberCode())){
			wxMerchantCodeResult = msMerchantInfo(queryId,memberInfo.getWxMemberCode(),memberInfo);
			wxReturnCode = wxMerchantCodeResult.getString("returnCode");
		}else{
			wxReturnCode = "0000";
		}
		if(StringUtils.isNotEmpty(memberInfo.getZfbMemberCode())){
			zfbMerchantCodeResult = msMerchantInfo(queryId,memberInfo.getZfbMemberCode(),memberInfo);
			zfbReturnCode = zfbMerchantCodeResult.getString("returnCode");
		}else{
			zfbReturnCode = "0000";
		}
		if(StringUtils.isNotEmpty(memberInfo.getQqMemberCode())){
			qqMerchantCodeResult = msMerchantInfo(queryId,memberInfo.getQqMemberCode(),memberInfo);
			qqReturnCode = qqMerchantCodeResult.getString("returnCode");
		}else{
			qqReturnCode = "0000";
		}
		if(StringUtils.isNotEmpty(memberInfo.getBdMemberCode())){
			bdMerchantCodeResult = msMerchantInfo(queryId,memberInfo.getBdMemberCode(),memberInfo);
			bdReturnCode = bdMerchantCodeResult.getString("returnCode");
		}else{
			bdReturnCode = "0000";
		}
		if(StringUtils.isNotEmpty(memberInfo.getJdMemberCode())){
			jdMerchantCodeResult = msMerchantInfo(queryId,memberInfo.getJdMemberCode(),memberInfo);
			jdReturnCode = jdMerchantCodeResult.getString("returnCode");
		}else{
			jdReturnCode = "0000";
		}
		result.put("returnCode", "0000");
		result.put("queryId", queryId);
		result.put("returnMsg", "商户信息查询成功");
		return result.toString();
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
				String merchantCode = resEntity.getString("merchantCode");
				result.put("returnCode", "0000");
				result.put("merchantCode", merchantCode);
				result.put("returnMsg", "成功");
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
	
	@ResponseBody
	@RequestMapping("/stopMerchant")
	public String stopMsMerchant(Model model, HttpServletRequest request){
		JSONObject requestPRM = (JSONObject) request.getAttribute("requestPRM");
		JSONObject reqDataJson = requestPRM.getJSONObject("reqData");// 获取请求参数
		JSONObject result=new JSONObject();
		
		if(!reqDataJson.containsKey("memberCode")){
			result.put("returnCode", "4004");
			result.put("returnMsg", "缺少用户编码信息");
			return result.toString();
		}
//		String txnType = reqDataJson.getString("txnType");////1:微信 2:支付宝 3:qq钱包
		String memberCode = reqDataJson.getString("memberCode");
		MemberInfoExample memberInfoExample = new MemberInfoExample();
		memberInfoExample.or().andCodeEqualTo(memberCode);
		List<MemberInfo> members = memberInfoService.selectByExample(memberInfoExample);
		MemberInfo memberInfo = null;
		if(members==null || members.size()==0){
			result.put("returnCode", "4004");
			result.put("returnMsg", "用户不存在");
			return result.toString();
		}else{
			memberInfo = members.get(0);
		}
		JSONObject wxMerchantCodeResult = null;
		JSONObject zfbMerchantCodeResult = null;
		JSONObject qqMerchantCodeResult = null;
		JSONObject bdMerchantCodeResult = null;
		String queryId=CommonUtil.getOrderCode();//记录标识 便于查找日志
		
		if(StringUtils.isNotBlank(memberInfo.getWxMemberCode())){
			wxMerchantCodeResult = msStopMerchant(queryId, memberInfo.getWxMemberCode(), memberInfo);
		}
		if(StringUtils.isNotBlank(memberInfo.getZfbMemberCode())){
			zfbMerchantCodeResult = msStopMerchant(queryId,memberInfo.getZfbMemberCode(),memberInfo);
		}
		if(StringUtils.isNotBlank(memberInfo.getQqMemberCode())){
			qqMerchantCodeResult = msStopMerchant(queryId,memberInfo.getQqMemberCode(),memberInfo);
		}
		if(StringUtils.isNotBlank(memberInfo.getBdMemberCode())){
			bdMerchantCodeResult = msStopMerchant(queryId,memberInfo.getBdMemberCode(),memberInfo);
		}
		StringBuffer sb = new StringBuffer();
		if(wxMerchantCodeResult!=null){
			if("0000".equals(wxMerchantCodeResult.getString("returnCode"))){
				sb.append("微信停用成功，");
			}else{
				sb.append("微信:"+wxMerchantCodeResult.getString("returnMsg"));
			}
		}
		if(zfbMerchantCodeResult!=null){
			if("0000".equals(zfbMerchantCodeResult.getString("returnCode"))){
				sb.append("支付宝停用成功，");
			}else{
				sb.append(",支付宝:"+zfbMerchantCodeResult.getString("returnMsg"));
			}
		}
		if(qqMerchantCodeResult!=null){
			if("0000".equals(qqMerchantCodeResult.getString("returnCode"))){
				sb.append("qq停用成功，");
			}else{
				sb.append(",qq:"+qqMerchantCodeResult.getString("returnMsg"));
			}
		}
		if(bdMerchantCodeResult!=null){
			if("0000".equals(bdMerchantCodeResult.getString("returnCode"))){
				sb.append("百度钱包停用成功，");
			}else{
				sb.append(",百度钱包:"+bdMerchantCodeResult.getString("returnMsg"));
			}
		}
		memberInfo.setStatus("1");//停用
		memberInfoService.updateByExampleSelective(memberInfo, memberInfoExample);
		result.put("returnCode", "0000");
		result.put("queryId", queryId);
		result.put("returnMsg", sb.toString());
		return result.toString();
	}
	
	public JSONObject msStopMerchant(String queryId,String merchantId,MemberInfo memberInfo){
		JSONObject result=new JSONObject();
		String tranCode = TranCodeConstant.SHTY;
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
			logger.info(queryId+"调用商户停用[{}]",  resXml);
			JSONObject respJSONObject = XmlConvertUtil.documentToJSONObjectFilter(resXml);
			if("000000".equals(respJSONObject.getString("respCode"))){
				result.put("returnCode", "0000");
				result.put("returnMsg", "成功");
			}else{
				result.put("returnCode", "4004");
				result.put("returnMsg", respJSONObject.getString("respMsg"));
			}
		} catch (ArgException e) {
			result.put("returnCode", "4004");
			result.put("returnMsg", "商户停用失败");
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
	/**Test.java用**/
	@ResponseBody
	@RequestMapping("/childMerchant")
	public String childMsMerchant(Model model, HttpServletRequest request){
		JSONObject requestPRM = (JSONObject) request.getAttribute("requestPRM");
		JSONObject reqDataJson = requestPRM.getJSONObject("reqData");// 获取请求参数
		JSONObject result=new JSONObject();
		
//		String txnType = reqDataJson.getString("txnType");////1:微信 2:支付宝 3:qq钱包
		List<MemberInfo> members;
		if(reqDataJson.containsKey("memberCode")){
			String memberCode = reqDataJson.getString("memberCode");
			MemberInfoExample memberInfoExample = new MemberInfoExample();
			memberInfoExample.or().andCodeEqualTo(memberCode);
			members = memberInfoService.selectByExample(memberInfoExample);
		}else{
			MemberInfoExample memberInfoExample = new MemberInfoExample();
			memberInfoExample.or().andStatusEqualTo("0").andDelFlagEqualTo("0");
			members = memberInfoService.selectByExample(memberInfoExample);
		}
		String queryId=CommonUtil.getOrderCode();//记录标识 便于查找日志
		for(MemberInfo memberInfo:members){
			msChildMerchant(queryId, memberInfo.getWxMerchantCode(), memberInfo, "1", WxConfig.authDirectory);
			msChildMerchant(queryId, memberInfo.getWxMerchantCode(), memberInfo, "2", WxConfig.appidSH);
		}
		StringBuffer sb = new StringBuffer();
		result.put("returnCode", "0000");
		result.put("queryId", queryId);
		result.put("returnMsg", sb.toString());
		return result.toString();
	}
	public JSONObject msChildMerchant(String queryId,String merchantId,MemberInfo memberInfo,String zdlx,String zdsj){
		JSONObject result=new JSONObject();
		String tranCode = TranCodeConstant.SHWXPZ;//"SMZF030";
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
	@ResponseBody
	@RequestMapping("/queryChild")
	public String queryChild(Model model, HttpServletRequest request){
		JSONObject requestPRM = (JSONObject) request.getAttribute("requestPRM");
		JSONObject reqDataJson = requestPRM.getJSONObject("reqData");// 获取请求参数
		JSONObject result=new JSONObject();
		
		List<MemberInfo> members;
		String oriReqMsgId = reqDataJson.getString("oriReqMsgId");
		if(reqDataJson.containsKey("memberCode")){
			String memberCode = reqDataJson.getString("memberCode");
			MemberInfoExample memberInfoExample = new MemberInfoExample();
			memberInfoExample.or().andCodeEqualTo(memberCode);
			members = memberInfoService.selectByExample(memberInfoExample);
		}else{
			result.put("msg", "缺少memberCode");
			return result.toString();
		}
		String queryId=CommonUtil.getOrderCode();//记录标识 便于查找日志
		for(MemberInfo memberInfo:members){
			result = msQueryChild(queryId, oriReqMsgId, memberInfo);
		}
		return result.toString();
	}
	public JSONObject msQueryChild(String queryId,String oriReqMsgId,MemberInfo memberInfo){
		JSONObject result=new JSONObject();
		String tranCode = TranCodeConstant.SHWXPZCH;
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
			sBuilder.append("<oriReqMsgId>" + oriReqMsgId + "</oriReqMsgId>");
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
			logger.info(queryId+"调用子商户配置查询[{}]",  resXml);
			JSONObject respJSONObject = XmlConvertUtil.documentToJSONObjectFilter(resXml);
			if("000000".equals(respJSONObject.getString("respCode"))){
				result.put("returnCode", "0000");
				result.put("returnMsg", "成功");
				result.put("retXml", resXml);
			}else{
				result.put("returnCode", "4004");
				result.put("returnMsg", respJSONObject.getString("respMsg"));
			}
		} catch (ArgException e) {
			result.put("returnCode", "4004");
			result.put("returnMsg", "子商户配置查询失败");
			logger.info(e.getMessage());
			return result;
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			result.put("returnCode", "4004");
			result.put("returnMsg", "查询失败，请检查流水号是否正确");
			return result;
		}
		return result;
	}
	
}
