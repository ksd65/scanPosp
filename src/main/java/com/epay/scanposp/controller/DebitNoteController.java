package com.epay.scanposp.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.epay.scanposp.common.constant.ESKConfig;
import com.epay.scanposp.common.constant.MSConfig;
import com.epay.scanposp.common.constant.SDConfig;
import com.epay.scanposp.common.constant.SysConfig;
import com.epay.scanposp.common.constant.TBConfig;
import com.epay.scanposp.common.constant.WxConfig;
import com.epay.scanposp.common.constant.XFConfig;
import com.epay.scanposp.common.excep.ArgException;
import com.epay.scanposp.common.utils.CommonUtil;
import com.epay.scanposp.common.utils.DateUtil;
import com.epay.scanposp.common.utils.EnvironmentUtil;
import com.epay.scanposp.common.utils.HttpUtil;
import com.epay.scanposp.common.utils.JsonBeanReleaseUtil;
import com.epay.scanposp.common.utils.Probability;
import com.epay.scanposp.common.utils.SecurityUtil;
import com.epay.scanposp.common.utils.StringUtil;
import com.epay.scanposp.common.utils.ValidateUtil;
import com.epay.scanposp.common.utils.XmlConvertUtil;
import com.epay.scanposp.common.utils.constant.MSPayWayConstant;
import com.epay.scanposp.common.utils.constant.PayTypeConstant;
import com.epay.scanposp.common.utils.constant.RouteCodeConstant;
import com.epay.scanposp.common.utils.constant.TranCodeConstant;
import com.epay.scanposp.common.utils.epaySecurityUtil.EpaySignUtil;
import com.epay.scanposp.common.utils.esk.Key;
import com.epay.scanposp.common.utils.ms.CryptoUtil;
import com.epay.scanposp.common.utils.ms.HttpClient4Util;
import com.epay.scanposp.common.utils.ms.MSCommonUtil;
import com.epay.scanposp.common.utils.sand.SandpayClient;
import com.epay.scanposp.common.utils.sand.SandpayRequestHead;
import com.epay.scanposp.common.utils.sand.util.CertUtil;
import com.epay.scanposp.common.utils.sand.util.PacketTool;
import com.epay.scanposp.common.utils.tb.SignUtil;
import com.epay.scanposp.entity.Account;
import com.epay.scanposp.entity.AccountExample;
import com.epay.scanposp.entity.BusinessCategory;
import com.epay.scanposp.entity.DebitNote;
import com.epay.scanposp.entity.DebitNoteExample;
import com.epay.scanposp.entity.EpayCode;
import com.epay.scanposp.entity.EpayCodeExample;
import com.epay.scanposp.entity.EskNotice;
import com.epay.scanposp.entity.MemberInfo;
import com.epay.scanposp.entity.MemberInfoExample;
import com.epay.scanposp.entity.MemberInfoExample.Criteria;
import com.epay.scanposp.entity.MemberMerchantCode;
import com.epay.scanposp.entity.MemberMerchantCodeExample;
import com.epay.scanposp.entity.MemberMerchantKey;
import com.epay.scanposp.entity.MemberMerchantKeyExample;
import com.epay.scanposp.entity.MsResultNotice;
import com.epay.scanposp.entity.MsResultNoticeExample;
import com.epay.scanposp.entity.PayResultNotice;
import com.epay.scanposp.entity.PayResultNoticeExample;
import com.epay.scanposp.entity.PayResultNoticeLog;
import com.epay.scanposp.entity.PayRoute;
import com.epay.scanposp.entity.PayRouteExample;
import com.epay.scanposp.entity.PayType;
import com.epay.scanposp.entity.PayTypeDefault;
import com.epay.scanposp.entity.PayTypeDefaultExample;
import com.epay.scanposp.entity.PayTypeExample;
import com.epay.scanposp.entity.PayTypeRule;
import com.epay.scanposp.entity.PayTypeRuleExample;
import com.epay.scanposp.entity.RoutewayDraw;
import com.epay.scanposp.entity.RoutewayDrawExample;
import com.epay.scanposp.entity.SysCommonConfig;
import com.epay.scanposp.entity.SysCommonConfigExample;
import com.epay.scanposp.entity.SysOffice;
import com.epay.scanposp.entity.SysOfficeExample;
import com.epay.scanposp.entity.TradeDetail;
import com.epay.scanposp.entity.TradeDetailExample;
import com.epay.scanposp.entity.TradeVolumnDaily;
import com.epay.scanposp.entity.TradeVolumnDailyExample;
import com.epay.scanposp.request.QrOrderCreateRequest;
import com.epay.scanposp.request.QrOrderCreateRequest.QrOrderCreateRequestBody;
import com.epay.scanposp.request.SandOrderPayRequest;
import com.epay.scanposp.request.SandOrderPayRequest.SandOrderPayRequestBody;
import com.epay.scanposp.response.SandOrderPayResponse;
import com.epay.scanposp.service.AccountService;
import com.epay.scanposp.service.BusinessCategoryService;
import com.epay.scanposp.service.DebitNoteService;
import com.epay.scanposp.service.EpayCodeService;
import com.epay.scanposp.service.EskNoticeService;
import com.epay.scanposp.service.MemberInfoService;
import com.epay.scanposp.service.MemberMerchantCodeService;
import com.epay.scanposp.service.MemberMerchantKeyService;
import com.epay.scanposp.service.MemberOpenidService;
import com.epay.scanposp.service.MsResultNoticeService;
import com.epay.scanposp.service.MsbillService;
import com.epay.scanposp.service.PayResultNoticeLogService;
import com.epay.scanposp.service.PayResultNoticeService;
import com.epay.scanposp.service.PayResultNotifyService;
import com.epay.scanposp.service.PayRouteService;
import com.epay.scanposp.service.PayTypeDefaultService;
import com.epay.scanposp.service.PayTypeRuleService;
import com.epay.scanposp.service.PayTypeService;
import com.epay.scanposp.service.RoutewayDrawService;
import com.epay.scanposp.service.SysCommonConfigService;
import com.epay.scanposp.service.SysOfficeConfigOemService;
import com.epay.scanposp.service.SysOfficeExtendService;
import com.epay.scanposp.service.SysOfficeService;
import com.epay.scanposp.service.TradeDetailService;
import com.epay.scanposp.service.TradeVolumnDailyService;
import com.epay.scanposp.thread.WeixinTemplateSendThread;


@Controller
public class DebitNoteController {
	private static Logger logger = LoggerFactory.getLogger(DebitNoteController.class);

	@Resource
	ThreadPoolTaskExecutor threadPoolTaskExecutor;
	
	@Resource
	private EpayCodeService epayCodeService;

	@Resource
	private MemberInfoService memberInfoService;

	@Resource
	private AccountService accountService;

	@Resource
	private DebitNoteService debitNoteService;
	
	@Resource
	private TradeDetailService tradeDetailService;
	
	@Resource
	private RoutewayDrawService routewayDrawService;

	@Resource
	private SysOfficeService sysOfficeService;
	@Resource
	private MsbillService msbillService;
	
	@Resource
	private MsResultNoticeService msResultNoticeService;
	
	@Resource
	private MemberOpenidService memberOpenidService;
	
	@Resource
	private SysOfficeConfigOemService sysOfficeConfigOemService;
	
	@Resource
	private BusinessCategoryService businessCategoryService;
	
	@Resource
	private TradeVolumnDailyService tradeVolumnDailyService;
	
	@Autowired
	private SysCommonConfigService sysCommonConfigService;
	
	@Autowired
	private EskNoticeService eskNoticeService;
	
	@Autowired
	private PayRouteService payRouteService;
	
	@Autowired
	private MemberMerchantCodeService memberMerchantCodeService;
	
	@Autowired
	private PayTypeService payTypeService;
	
	@Resource
	private PayResultNoticeService payResultNoticeService;
	
	@Resource
	private SysOfficeExtendService sysOfficeExtendService;
	
	@Resource
	private PayResultNoticeLogService payResultNoticeLogService;
	
	@Resource
	private PayResultNotifyService payResultNotifyService;
	
	@Autowired
	private MemberMerchantKeyService memberMerchantKeyService;
	
	@Resource
	private PayTypeRuleService payTypeRuleService;
	
	@Resource
	private PayTypeDefaultService payTypeDefaultService;
	
	@ResponseBody
	@RequestMapping("/api/debitNote/pay")
	public JSONObject pay(HttpServletRequest request) {
		JSONObject requestPRM = (JSONObject) request.getAttribute("requestPRM");
		JSONObject reqDataJson = requestPRM.getJSONObject("reqData");// 获取请求参数
		String appClientType = reqDataJson.getString("appClientType");
		JSONObject result = new JSONObject();  
		/*
		if ("micromessenger".equals(appClientType)) {// 微信扫码支付
			result = msWxScanQrcodePay(reqDataJson, request);
		}
		*/
		
		String epayCode = reqDataJson.getString("epayCode");
		if ("".equals(epayCode)) {
			result.put("returnCode", "4004");
			result.put("returnMsg", "收款码出错");
			return result;
		}
		EpayCodeExample epayCodeExample = new EpayCodeExample();
		epayCodeExample.createCriteria().andPayCodeEqualTo(epayCode).andDelFlagEqualTo("0");
		List<EpayCode> epayCodes = epayCodeService.selectByExample(epayCodeExample);
		if (epayCodes == null || epayCodes.size() == 0) {
			result.put("returnCode", "4004");
			result.put("returnMsg", "收款码不存在");
			return result;
		}
		EpayCode payCode = epayCodes.get(0);
		if(!"5".equals(payCode.getStatus()) && !"7".equals(payCode.getStatus())){
			result.put("returnCode", "4004");
			result.put("returnMsg", "对不起,收款码不可用，如有疑问请与管理员联系");
			return result;
		}
		MemberInfo memberInfo = memberInfoService.selectByPrimaryKey(payCode.getMemberId());
		
		if (memberInfo == null) {
			result.put("returnCode", "4004");
			result.put("returnMsg", "商家不存在");
			return result;
		}
		if(!"0".equals(memberInfo.getDelFlag())){
			result.put("returnCode", "4004");
			result.put("returnMsg", "商家不存在");
			return result;
		}
		if(!"0".equals(memberInfo.getStatus())){
			if("4".equals(memberInfo.getStatus())){
				result.put("returnCode", "4004");
				result.put("returnMsg", "该商户未进行认证，暂时无法交易");
				return result;
			}
			result.put("returnCode", "4004");
			result.put("returnMsg", "商家停用，暂不可交易");
			return result;
		}
		
		if ("micromessenger".equals(appClientType)) {// 微信公众号支付
			Map<String,String> rtMap = 	getRouteCodeAndAisleType(memberInfo.getId(),PayTypeConstant.PAY_METHOD_GZHZF,PayTypeConstant.PAY_TYPE_WX);
			String routeCode = rtMap.get("routeCode");
			if(RouteCodeConstant.ESK_ROUTE_CODE.equals(routeCode)){
				result = eskWxPay(reqDataJson, request, memberInfo, rtMap.get("aisleType"));
			}else if(RouteCodeConstant.XF_ROUTE_CODE.equals(routeCode)){
				result = xfWxPay(reqDataJson, request, memberInfo);
			}else if(RouteCodeConstant.SD_ROUTE_CODE.equals(routeCode)){
				result = sdWxPay(reqDataJson, request, memberInfo);
			}else{
				result = msWxPay(reqDataJson, request);
			}
			
		}else if ("alipay".equals(appClientType)) {// 支付宝
			Map<String,String> rtMap = 	getRouteCodeAndAisleType(memberInfo.getId(),PayTypeConstant.PAY_METHOD_GZHZF,PayTypeConstant.PAY_TYPE_ZFB);
			String routeCode = rtMap.get("routeCode");
			if(RouteCodeConstant.ESK_ROUTE_CODE.equals(routeCode)){
				result = eskAliPay(reqDataJson, request, memberInfo, rtMap.get("aisleType"));
			}else{
				result = msAliPay(reqDataJson, request);
			}
		}else if ("mqqbrowser".equals(appClientType)) {
			result = msQqScanQrcodePay(reqDataJson, request);
//			result = msScanQrcodePayCommon("mqqbrowser",reqDataJson, request);
//			result = msQqPay(reqDataJson, request);
		}else if ("baiduwallet".equals(appClientType)) {
			result = msScanQrcodePayCommon("baiduwallet",reqDataJson, request);    
		}else if ("jdjrpay".equals(appClientType)) {
			result = msScanQrcodePayCommon("jdjrpay",reqDataJson, request);    
		}else {
			result.put("returnCode", "4004");
			result.put("returnMsg", "请用微信、支付宝、手机QQ、百度钱包或京东金融扫码");
		}

		return result;

	}

	@ResponseBody
	@RequestMapping("/api/debitNote/oneCodePay")
	public JSONObject oneCodePay(HttpServletRequest request) {
		JSONObject requestPRM = (JSONObject) request.getAttribute("requestPRM");
		JSONObject reqDataJson = requestPRM.getJSONObject("reqData");// 获取请求参数
		JSONObject result = new JSONObject();
		try{
			String epayCode = reqDataJson.getString("epayCode");
			if ("".equals(epayCode)) {
				throw new ArgException("收款码出错");
			}
			
			EpayCodeExample epayCodeExample = new EpayCodeExample();
			epayCodeExample.createCriteria().andPayCodeEqualTo(epayCode).andDelFlagEqualTo("0");
			List<EpayCode> epayCodes = epayCodeService.selectByExample(epayCodeExample);
			if (epayCodes == null || epayCodes.size() == 0) {
				throw new ArgException("收款码不存在");
			}

			EpayCode payCode = epayCodes.get(0);
			if(!"5".equals(payCode.getStatus()) && !"7".equals(payCode.getStatus())){
				throw new ArgException("对不起,收款码不可用，如有疑问请与管理员联系");
			}

			MemberInfo memberInfo = memberInfoService.selectByPrimaryKey(payCode.getMemberId());

			if (memberInfo == null) {
				throw new ArgException("商家不存在");
			}
			if(!"0".equals(memberInfo.getDelFlag())){
				throw new ArgException("商家不存在");
			}
			if(!"0".equals(memberInfo.getStatus())){
				if("4".equals(memberInfo.getStatus())){
					throw new ArgException("该商户未进行认证，暂时无法交易");
				}
				throw new ArgException("商家停用，暂不可交易");
			}

			AccountExample accountExample = new AccountExample();
			accountExample.createCriteria().andMemberIdEqualTo(memberInfo.getId()).andDelFlagEqualTo("0");
			List<Account> accounts = accountService.selectByExample(accountExample);
			if (accounts == null || accounts.size() != 1) {
				throw new ArgException("会员账户不存在");
			}
			
			
			
			
			String orderCode = CommonUtil.getOrderCode();
			
			

			// 调用支付通道
			String serverUrl = ESKConfig.msServerUrl;
			String tranCode = "005";
			String charset = "utf-8";
			
			JSONObject reqData = new JSONObject();
			reqData.put("merchantCode", memberInfo.getWxMerchantCode());
			reqData.put("orderNumber", orderCode);
			reqData.put("tranCode", tranCode);
			
			System.out.println("待加密数据: "+reqData);
			
			String plainXML = reqData.toString();
			byte[] plainBytes = plainXML.getBytes(charset);
			String keyStr = MSCommonUtil.generateLenString(16);
			;
			byte[] keyBytes = keyStr.getBytes(charset);
			String encryptData = new String(Base64.encodeBase64((CryptoUtil.AESEncrypt(plainBytes, keyBytes, "AES", "AES/ECB/PKCS5Padding", null))), charset);
			String signData = new String(Base64.encodeBase64(Key.rsaSign(plainBytes, ESKConfig.privateKey)), charset);
			String encrtptKey = new String(Base64.encodeBase64(Key.jdkRSA(keyBytes, ESKConfig.yhPublicKey)), charset);
			List<NameValuePair> nvps = new LinkedList<NameValuePair>();
			nvps.add(new BasicNameValuePair("Context", encryptData));
			nvps.add(new BasicNameValuePair("encrtpKey", encrtptKey));
			
			nvps.add(new BasicNameValuePair("signData", signData));
			nvps.add(new BasicNameValuePair("agentId", ESKConfig.agentId));
			byte[] b = HttpClient4Util.getInstance().doPost(serverUrl, null, nvps);
			String respStr = new String(b, charset);
			logger.info("返回报文[{}]", new Object[] { respStr });
			
			JSONObject jsonObject = JSONObject.fromObject(respStr);
			String resEncryptData = jsonObject.getString("Context");
			String resEncryptKey = jsonObject.getString("encrtpKey");
			byte[] decodeBase64KeyBytes = Base64.decodeBase64(resEncryptKey.getBytes(charset));
			byte[] merchantAESKeyBytes = Key.jdkRSA_(decodeBase64KeyBytes, ESKConfig.privateKey);
			// 使用base64解码商户请求报文
			byte[] decodeBase64DataBytes = Base64.decodeBase64(resEncryptData.getBytes(charset));
			// 用解密得到的merchantAESKey解密encryptData
			byte[] merchantXmlDataBytes = CryptoUtil.AESDecrypt(decodeBase64DataBytes, merchantAESKeyBytes, "AES", "AES/ECB/PKCS5Padding", null);
			String resXml = new String(merchantXmlDataBytes, charset);
			JSONObject respJSONObject = JSONObject.fromObject(resXml);
			logger.info("返回报文[{}]",  respJSONObject );
			//[{"orderNumber":"20171026151241725855","tranCode":"005","reqTime":"2017-10-26 15:13:05","apiorderNumber":"820171509001985470750","aisleType":"1","respType":"S","respCode":"000000","respMsg":"成功","qrCode":"https://fch.yiguanjinrong.com/flashchannel/ACodePay?data=8b3fb0961d5cdfe8613ade61466e076503b0753751a6c39054e89ec6e321b2c284246ef2c7d04cb4cd4d3756f103d1fbd3b038baffc513ea6062f2dd3096bbd5","respTime":"2017-10-26 15:13:06"}]

			if("000000".equals(respJSONObject.getString("respCode"))&&"S".equals(respJSONObject.getString("respType"))){
				result.put("returnCode", "0000");
				result.put("returnMsg", "成功");
				result.put("orderNumber", respJSONObject.getString("orderNumber") );
				result.put("qrCode", respJSONObject.getString("qrCode") );
				
				String apiorderNumber = respJSONObject.getString("apiorderNumber");
			}else{
				result.put("returnCode", "0001");
				result.put("returnMsg", respJSONObject.getString("respMsg"));
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
		
		
		return result;

	}
	
	/**
	 * 民生微信支付
	 * 
	 * @param request
	 * @return
	 */

	public JSONObject msWxPay(JSONObject reqDataJson, HttpServletRequest request) {
		JSONObject result = new JSONObject();
		JSONObject resData = new JSONObject();
		try {
			String money = reqDataJson.getString("money");
			String epayCode = reqDataJson.getString("epayCode");
			String remark = reqDataJson.getString("remark");
//			 String userId = "2088502362301099";
			String userId = reqDataJson.getString("userId");
			if ("".equals(money)) {
				throw new ArgException("请输入正确的金额");
			}

			if ("".equals(epayCode)) {
				throw new ArgException("收款码出错");
			}
			
			if(StringUtils.isNotEmpty(remark)){
				if(remark.length() > 20){
					throw new ArgException("备注信息超出限制");
				}
			}

			EpayCodeExample epayCodeExample = new EpayCodeExample();
			epayCodeExample.createCriteria().andPayCodeEqualTo(epayCode).andDelFlagEqualTo("0");
			List<EpayCode> epayCodes = epayCodeService.selectByExample(epayCodeExample);
			if (epayCodes == null || epayCodes.size() == 0) {
				throw new ArgException("收款码不存在");
			}

			EpayCode payCode = epayCodes.get(0);
			if(!"5".equals(payCode.getStatus()) && !"7".equals(payCode.getStatus())){
				throw new ArgException("对不起,收款码不可用，如有疑问请与管理员联系");
			}

			MemberInfo memberInfo = memberInfoService.selectByPrimaryKey(payCode.getMemberId());

			if (memberInfo == null) {
				throw new ArgException("商家不存在");
			}
			if(!"0".equals(memberInfo.getDelFlag())){
				throw new ArgException("商家不存在");
			}
			if(!"0".equals(memberInfo.getStatus())){
				if("4".equals(memberInfo.getStatus())){
					throw new ArgException("该商户未进行认证，暂时无法交易");
				}
				throw new ArgException("商家停用，暂不可交易");
			}

			AccountExample accountExample = new AccountExample();
			accountExample.createCriteria().andMemberIdEqualTo(memberInfo.getId()).andDelFlagEqualTo("0");
			List<Account> accounts = accountService.selectByExample(accountExample);
			if (accounts == null || accounts.size() != 1) {
				throw new ArgException("会员账户不存在");
			}
			
			JSONObject checkResult = checkPayLimit(memberInfo.getId(), new BigDecimal(money), memberInfo.getSingleLimit(), memberInfo.getDayLimit());
			if(null != checkResult){
				return checkResult;
			}
			
			// 插入一条收款记录
			String orderCode = CommonUtil.getOrderCode();
			
			DebitNote debitNote = new DebitNote();
			debitNote.setCreateDate(new Date());
			debitNote.setMemberId(memberInfo.getId());
			debitNote.setMoney(new BigDecimal(money));
			debitNote.setOrderCode(orderCode);
			debitNote.setRouteId(memberInfo.getWxRouteId());
			debitNote.setStatus("0");
			debitNote.setTxnMethod(PayTypeConstant.PAY_METHOD_GZHZF);
			debitNote.setTxnType("1");
			debitNote.setMemberCode(memberInfo.getWxMemberCode());
			debitNote.setMerchantCode(memberInfo.getWxMerchantCode());
			debitNote.setSettleType(memberInfo.getSettleType());
			debitNote.setRemarks(remark);
			debitNoteService.insertSelective(debitNote);

			// String callBack = "http://" + request.getServerName() + ":" +
			// request.getServerPort() + request.getContextPath()+
			// "/debitNote/msNotify";
			String callBack = SysConfig.serverUrl + "/debitNote/msPayNotify";
			// 调用支付通道
			String serverUrl = MSConfig.msServerUrl;
			PublicKey yhPubKey = null;
			if (serverUrl.startsWith("https://ipay")) {
				yhPubKey = CryptoUtil.getRSAPublicKey(true);
			} else {
				yhPubKey = CryptoUtil.getRSAPublicKey(false);
			}
			PrivateKey hzfPriKey = CryptoUtil.getRSAPrivateKey();
			String tranCode = "SMZF010";
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
			sBuilder.append("<merchantCode>" + memberInfo.getWxMerchantCode() + "</merchantCode>");
			sBuilder.append("<totalAmount>" + money + "</totalAmount>");
			sBuilder.append("<subject>" + memberInfo.getName() + " 收款" + "</subject>");
			sBuilder.append("<subAppid>" + WxConfig.appid + "</subAppid>");
			sBuilder.append("<userId>" + userId + "</userId>");
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
			
			if("0".equals(memberInfo.getSettleType())){
				nvps.add(new BasicNameValuePair("cooperator", MSConfig.cooperator_t0));
			}else{
				nvps.add(new BasicNameValuePair("cooperator", MSConfig.cooperator_t1));
			}
			
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
			System.out.println(respJSONObject);
			
			
			resData.put("orderCode", orderCode);
			if (respJSONObject.containsKey("resEntity")) {
				JSONObject resEntity = respJSONObject.getJSONObject("resEntity");
				if (resEntity.containsKey("wxjsapiStr")) {
					JSONObject wxjsapiStr=resEntity.getJSONObject("wxjsapiStr");
					wxjsapiStr.put("package_str", wxjsapiStr.getString("package"));
					resData.put("wxjsapiStr", wxjsapiStr);
					result.put("resData", resData);
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
	
	/**
	 * 易收款微信支付
	 * 
	 * @param request
	 * @return
	 */

	public JSONObject eskWxPay(JSONObject reqDataJson, HttpServletRequest request, MemberInfo memberInfo, String aisleType) {
		JSONObject result = new JSONObject();
		JSONObject resData = new JSONObject();
		try {
			String money = reqDataJson.getString("money");
			String remark = reqDataJson.getString("remark");
//			 String userId = "2088502362301099";
			String userId = reqDataJson.getString("userId");
			if ("".equals(money)) {
				throw new ArgException("请输入正确的金额");
			}

			if(StringUtils.isNotEmpty(remark)){
				if(remark.length() > 20){
					throw new ArgException("备注信息超出限制");
				}
			}

			AccountExample accountExample = new AccountExample();
			accountExample.createCriteria().andMemberIdEqualTo(memberInfo.getId()).andDelFlagEqualTo("0");
			List<Account> accounts = accountService.selectByExample(accountExample);
			if (accounts == null || accounts.size() != 1) {
				throw new ArgException("会员账户不存在");
			}
			JSONObject checkResult = checkPayLimit(memberInfo.getId(), new BigDecimal(money), memberInfo.getSingleLimit(), memberInfo.getDayLimit());
			if(null != checkResult){
				return checkResult;
			}
			
			MemberMerchantCodeExample memberMerchantCodeExample = new MemberMerchantCodeExample();
			memberMerchantCodeExample.createCriteria().andMemberIdEqualTo(memberInfo.getId()).andRouteCodeEqualTo(RouteCodeConstant.ESK_ROUTE_CODE).andAisleTypeEqualTo(aisleType).andDelFlagEqualTo("0");
			List<MemberMerchantCode> merchantCodes = memberMerchantCodeService.selectByExample(memberMerchantCodeExample);
			if (merchantCodes == null || merchantCodes.size() != 1) {
				throw new ArgException("商户编码不存在");
			}
			
			MemberMerchantCode merchantCode = merchantCodes.get(0);
			
			// 插入一条收款记录
			String orderCode = CommonUtil.getOrderCode();
			
			DebitNote debitNote = new DebitNote();
			debitNote.setCreateDate(new Date());
			debitNote.setMemberId(memberInfo.getId());
			debitNote.setMoney(new BigDecimal(money));
			debitNote.setOrderCode(orderCode);
			debitNote.setRouteId(RouteCodeConstant.ESK_ROUTE_CODE);
			debitNote.setStatus("0");
			debitNote.setTxnMethod(PayTypeConstant.PAY_METHOD_GZHZF);
			debitNote.setTxnType("1");
			debitNote.setMemberCode(memberInfo.getWxMemberCode());
			debitNote.setMerchantCode(merchantCode.getWxMerchantCode());
			debitNote.setSettleType(memberInfo.getSettleType());
			debitNote.setRemarks(remark);
			if("0".equals(memberInfo.getSettleType())){
				debitNote.setTradeRate(merchantCode.getT0TradeRate());
			}else{
				debitNote.setTradeRate(merchantCode.getT1TradeRate());
			}
			debitNoteService.insertSelective(debitNote);
			// String callBack = "http://" + request.getServerName() + ":" +
			// request.getServerPort() + request.getContextPath()+
			// "/debitNote/msNotify";
			String callBack = SysConfig.serverUrl + "/debitNote/eskPayNotify";
			// 调用支付通道
			String serverUrl = ESKConfig.msServerUrl;
			//PublicKey yhPubKey = null;
			//yhPubKey = CryptoUtil.getEskRSAPublicKey();
			//yhPubKey = CryptoUtil.getRSAPublicKey(false);
			//PrivateKey hzfPriKey = CryptoUtil.getRSAPrivateKey();
			String tranCode = "003";
			String charset = "utf-8";
			
			JSONObject reqData = new JSONObject();
			reqData.put("merchantCode", merchantCode.getWxMerchantCode());
			reqData.put("orderNumber", orderCode);
			reqData.put("tranCode", tranCode);
			reqData.put("userId", userId);
			reqData.put("aisleType", aisleType);
			reqData.put("totalAmount", money);
			reqData.put("subject", memberInfo.getName() + " 收款");
			reqData.put("PayType", "1");
			reqData.put("callback", callBack);
			reqData.put("desc", memberInfo.getName() + " 收款");
			reqData.put("subAppid", ESKConfig.subAppid);
			System.out.println("待加密数据: "+reqData);
			
			String plainXML = reqData.toString();
			byte[] plainBytes = plainXML.getBytes(charset);
			String keyStr = MSCommonUtil.generateLenString(16);
			;
			byte[] keyBytes = keyStr.getBytes(charset);
			
			//String encryptData = new String(Base64.encodeBase64((Key.jdkAES(plainBytes, keyBytes))));
			String signData = new String(Base64.encodeBase64(Key.rsaSign(plainBytes, ESKConfig.privateKey)), charset);
			String encryptData = new String(Base64.encodeBase64((CryptoUtil.AESEncrypt(plainBytes, keyBytes, "AES", "AES/ECB/PKCS5Padding", null))), charset);
			//String signData = new String(Base64.encodeBase64(CryptoUtil.digitalSign(plainBytes, hzfPriKey, "SHA1WithRSA")), charset);
			String encrtptKey = new String(Base64.encodeBase64(Key.jdkRSA(keyBytes, ESKConfig.yhPublicKey)), charset);

			//String encrtptKey = new String(Base64.encodeBase64(CryptoUtil.RSAEncrypt(keyBytes, yhPubKey, 2048, 11, "RSA/ECB/PKCS1Padding")), charset);
			List<NameValuePair> nvps = new LinkedList<NameValuePair>();
			nvps.add(new BasicNameValuePair("Context", encryptData));
			nvps.add(new BasicNameValuePair("encrtpKey", encrtptKey));
			
			nvps.add(new BasicNameValuePair("signData", signData));
			nvps.add(new BasicNameValuePair("agentId", ESKConfig.agentId));
			byte[] b = HttpClient4Util.getInstance().doPost(serverUrl, null, nvps);
			String respStr = new String(b, charset);
			logger.info("返回报文[{}]", new Object[] { respStr });
			JSONObject jsonObject = JSONObject.fromObject(respStr);
			String resEncryptData = jsonObject.getString("Context");
			String resEncryptKey = jsonObject.getString("encrtpKey");
			byte[] decodeBase64KeyBytes = Base64.decodeBase64(resEncryptKey.getBytes(charset));
			// 解密encryptKey得到merchantAESKey
			//byte[] merchantAESKeyBytes = CryptoUtil.RSADecrypt(decodeBase64KeyBytes, hzfPriKey, 2048, 11, "RSA/ECB/PKCS1Padding");
			byte[] merchantAESKeyBytes = Key.jdkRSA_(decodeBase64KeyBytes, ESKConfig.privateKey);
					

			// 使用base64解码商户请求报文
			byte[] decodeBase64DataBytes = Base64.decodeBase64(resEncryptData.getBytes(charset));
			// 用解密得到的merchantAESKey解密encryptData
			byte[] merchantXmlDataBytes = CryptoUtil.AESDecrypt(decodeBase64DataBytes, merchantAESKeyBytes, "AES", "AES/ECB/PKCS5Padding", null);
			String resXml = new String(merchantXmlDataBytes, charset);
			JSONObject respJSONObject = JSONObject.fromObject(resXml);
			logger.info("返回报文[{}]",  respJSONObject );
			
			resData.put("orderCode", orderCode);
			if("S".equals(respJSONObject.getString("respType"))&&"000000".equals(respJSONObject.getString("respCode"))){
				if(respJSONObject.containsKey("wx_json")&&!"_WX_Pay_FAILED_".equals(respJSONObject.getString("wx_json"))){
					JSONObject wxjsapiStr=respJSONObject.getJSONObject("wx_json");
					wxjsapiStr.put("package_str", wxjsapiStr.getString("package"));
					resData.put("wxjsapiStr", wxjsapiStr);
					result.put("resData", resData);
					result.put("returnCode", "0000");
					result.put("returnMsg", "成功");
					
				}else{
					result.put("returnCode", "4004");
					result.put("returnMsg", "获取微信信息失败");
				}
			}else{
				result.put("returnCode", "4004");
				result.put("returnMsg", "调用微信支付接口返回失败("+respJSONObject.getString("respMsg")+")");
			}
		/*	if (respJSONObject.containsKey("resEntity")) {
				JSONObject resEntity = respJSONObject.getJSONObject("resEntity");
				if (resEntity.containsKey("wxjsapiStr")) {
					JSONObject wxjsapiStr=resEntity.getJSONObject("wxjsapiStr");
					wxjsapiStr.put("package_str", wxjsapiStr.getString("package"));
					resData.put("wxjsapiStr", wxjsapiStr);
					result.put("resData", resData);
				}
			}*/

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
		return result;
	}
	
	/**
	 * 先锋微信支付
	 * 
	 * @param request
	 * @return
	 */

	public JSONObject xfWxPay(JSONObject reqDataJson, HttpServletRequest request,MemberInfo memberInfo) {
		JSONObject result = new JSONObject();
		JSONObject resData = new JSONObject();
		try {
			String money = reqDataJson.getString("money");
			String remark = reqDataJson.getString("remark");
//			 String userId = "2088502362301099";
			String userId = reqDataJson.getString("userId");
			if ("".equals(money)) {
				throw new ArgException("请输入正确的金额");
			}

			
			if(StringUtils.isNotEmpty(remark)){
				if(remark.length() > 20){
					throw new ArgException("备注信息超出限制");
				}
			}

			AccountExample accountExample = new AccountExample();
			accountExample.createCriteria().andMemberIdEqualTo(memberInfo.getId()).andDelFlagEqualTo("0");
			List<Account> accounts = accountService.selectByExample(accountExample);
			if (accounts == null || accounts.size() != 1) {
				throw new ArgException("会员账户不存在");
			}
			JSONObject checkResult = checkPayLimit(memberInfo.getId(), new BigDecimal(money), memberInfo.getSingleLimit(), memberInfo.getDayLimit());
			if(null != checkResult){
				return checkResult;
			}
			// 插入一条收款记录
			String orderCode = CommonUtil.getOrderCode();
			
			DebitNote debitNote = new DebitNote();
			debitNote.setCreateDate(new Date());
			debitNote.setMemberId(memberInfo.getId());
			debitNote.setMoney(new BigDecimal(money));
			debitNote.setOrderCode(orderCode);
			debitNote.setRouteId(RouteCodeConstant.XF_ROUTE_CODE);
			debitNote.setStatus("0");
			debitNote.setTxnMethod(PayTypeConstant.PAY_METHOD_GZHZF);
			debitNote.setTxnType("1");
			debitNote.setMemberCode(memberInfo.getWxMemberCode());
			debitNote.setMerchantCode(memberInfo.getWxMerchantCode());
			debitNote.setSettleType(memberInfo.getSettleType());
			debitNote.setRemarks(remark);
			debitNoteService.insertSelective(debitNote);
			// String callBack = "http://" + request.getServerName() + ":" +
			// request.getServerPort() + request.getContextPath()+
			// "/debitNote/msNotify";
			String callBack = SysConfig.serverUrl + "/debitNote/xfPayNotify";
			
			String returnUrl = "";
			
			// 调用支付通道
			String serverUrl = XFConfig.msServerUrl;
			//PublicKey yhPubKey = null;
			//yhPubKey = CryptoUtil.getEskRSAPublicKey();
			//yhPubKey = CryptoUtil.getRSAPublicKey(false);
			//PrivateKey hzfPriKey = CryptoUtil.getRSAPrivateKey();
			String tranCode = "JH_GZH_PAY";
			String charset = "utf-8";
			String secId = "RSA";
			String version = "4.0.0";
			String merchantId = "";
			String reqSn = "";//先忽略  by linxf
			
			JSONObject data = new JSONObject();
			
			data.put("amount", (int)((new BigDecimal(money)).floatValue()*100));
			data.put("outOrderId", orderCode);
			data.put("productName", memberInfo.getName() + " 收款");
			data.put("noticeUrl", callBack);
			data.put("returnUrl", returnUrl);
			data.put("isLimitCreditPay", "0");//是否支持信用卡 0 无限制（信用卡，借记卡） 1 不支持信用卡
			data.put("payType", "WXPAY");
			data.put("bizType", "200");
			data.put("subMerchantId", memberInfo.getWxMerchantCode());
			data.put("subMerchantName", memberInfo.getName());
			data.put("methodVersion", "1.0");
			data.put("merchantType", "OUT");
			data.put("appId", XFConfig.subAppid);
			data.put("openId", userId);
			data.put("transCur", "156");//人民币：156
			
			System.out.println("待加密业务数据: "+data);
			
			String plainXML = data.toString();
			byte[] plainBytes = plainXML.getBytes(charset);
			String keyStr = MSCommonUtil.generateLenString(16);
			byte[] keyBytes = keyStr.getBytes(charset);
			String encryptData = new String(Base64.encodeBase64((CryptoUtil.AESEncrypt(plainBytes, keyBytes, "AES", "AES/ECB/PKCS5Padding", null))), charset);
			
			JSONObject reqData = new JSONObject();
			reqData.put("service", tranCode);
			reqData.put("secId", secId);
			reqData.put("version", version);
			reqData.put("reqSn", reqSn);
			reqData.put("merchantId", merchantId);
			reqData.put("data", encryptData);
			System.out.println("待加密数据: "+reqData.toString());
		//	String signData = new String(Base64.encodeBase64(Key.rsaSign(plainBytes, ESKConfig.privateKey)), charset);
			String signData = "";
			
			List<NameValuePair> nvps = new LinkedList<NameValuePair>();
			nvps.add(new BasicNameValuePair("service", tranCode));
			nvps.add(new BasicNameValuePair("secId", secId));
			nvps.add(new BasicNameValuePair("version", version));
			nvps.add(new BasicNameValuePair("reqSn", reqSn));
			nvps.add(new BasicNameValuePair("merchantId", merchantId));
			nvps.add(new BasicNameValuePair("data", encryptData));
			nvps.add(new BasicNameValuePair("sign", signData));
			byte[] b = HttpClient4Util.getInstance().doPost(serverUrl, null, nvps);
			String respStr = new String(b, charset);
			logger.info("返回报文[{}]", new Object[] { respStr });
			JSONObject jsonObject = JSONObject.fromObject(respStr);
			String resEncryptData = jsonObject.getString("Context");
			String resEncryptKey = jsonObject.getString("encrtpKey");
			byte[] decodeBase64KeyBytes = Base64.decodeBase64(resEncryptKey.getBytes(charset));
			// 解密encryptKey得到merchantAESKey
			//byte[] merchantAESKeyBytes = CryptoUtil.RSADecrypt(decodeBase64KeyBytes, hzfPriKey, 2048, 11, "RSA/ECB/PKCS1Padding");
			byte[] merchantAESKeyBytes = Key.jdkRSA_(decodeBase64KeyBytes, ESKConfig.privateKey);
					

			// 使用base64解码商户请求报文
			byte[] decodeBase64DataBytes = Base64.decodeBase64(resEncryptData.getBytes(charset));
			// 用解密得到的merchantAESKey解密encryptData
			byte[] merchantXmlDataBytes = CryptoUtil.AESDecrypt(decodeBase64DataBytes, merchantAESKeyBytes, "AES", "AES/ECB/PKCS5Padding", null);
			String resXml = new String(merchantXmlDataBytes, charset);
			JSONObject respJSONObject = JSONObject.fromObject(resXml);
			logger.info("返回报文[{}]",  respJSONObject );
			
			resData.put("orderCode", orderCode);
			if("S".equals(respJSONObject.getString("respType"))&&"000000".equals(respJSONObject.getString("respCode"))){
				if(respJSONObject.containsKey("wx_json")&&!"_WX_Pay_FAILED_".equals(respJSONObject.getString("wx_json"))){
					JSONObject wxjsapiStr=respJSONObject.getJSONObject("wx_json");
					wxjsapiStr.put("package_str", wxjsapiStr.getString("package"));
					resData.put("wxjsapiStr", wxjsapiStr);
					result.put("resData", resData);
					result.put("returnCode", "0000");
					result.put("returnMsg", "成功");
					
				}else{
					result.put("returnCode", "4004");
					result.put("returnMsg", "获取微信信息失败");
				}
			}else{
				result.put("returnCode", "4004");
				result.put("returnMsg", "调用微信支付接口返回失败("+respJSONObject.getString("respMsg")+")");
			}
		/*	if (respJSONObject.containsKey("resEntity")) {
				JSONObject resEntity = respJSONObject.getJSONObject("resEntity");
				if (resEntity.containsKey("wxjsapiStr")) {
					JSONObject wxjsapiStr=resEntity.getJSONObject("wxjsapiStr");
					wxjsapiStr.put("package_str", wxjsapiStr.getString("package"));
					resData.put("wxjsapiStr", wxjsapiStr);
					result.put("resData", resData);
				}
			}*/

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
		return result;
	}
	
	/**
	 * 杉德微信支付
	 * 
	 * @param request
	 * @return
	 */

	public JSONObject sdWxPay(JSONObject reqDataJson, HttpServletRequest request, MemberInfo memberInfo) {
		JSONObject result = new JSONObject();
		JSONObject resData = new JSONObject();
		try {
			String money = reqDataJson.getString("money");
			String remark = reqDataJson.getString("remark");
//			 String userId = "2088502362301099";
			String userId = reqDataJson.getString("userId");
			if ("".equals(money)) {
				throw new ArgException("请输入正确的金额");
			}

			if(StringUtils.isNotEmpty(remark)){
				if(remark.length() > 20){
					throw new ArgException("备注信息超出限制");
				}
			}

			AccountExample accountExample = new AccountExample();
			accountExample.createCriteria().andMemberIdEqualTo(memberInfo.getId()).andDelFlagEqualTo("0");
			List<Account> accounts = accountService.selectByExample(accountExample);
			if (accounts == null || accounts.size() != 1) {
				throw new ArgException("会员账户不存在");
			}
			JSONObject checkResult = checkPayLimit(memberInfo.getId(), new BigDecimal(money), memberInfo.getSingleLimit(), memberInfo.getDayLimit());
			if(null != checkResult){
				return checkResult;
			}
			
			MemberMerchantCodeExample memberMerchantCodeExample = new MemberMerchantCodeExample();
			memberMerchantCodeExample.createCriteria().andMemberIdEqualTo(memberInfo.getId()).andRouteCodeEqualTo(RouteCodeConstant.SD_ROUTE_CODE).andDelFlagEqualTo("0");
			List<MemberMerchantCode> merchantCodes = memberMerchantCodeService.selectByExample(memberMerchantCodeExample);
			if (merchantCodes == null || merchantCodes.size() != 1) {
				throw new ArgException("商户编码不存在");
			}
			
			MemberMerchantCode merchantCode = merchantCodes.get(0);
			
			// 插入一条收款记录
			String orderCode = CommonUtil.getOrderCode();
			
			DebitNote debitNote = new DebitNote();
			debitNote.setCreateDate(new Date());
			debitNote.setMemberId(memberInfo.getId());
			debitNote.setMoney(new BigDecimal(money));
			debitNote.setOrderCode(orderCode);
			debitNote.setRouteId(RouteCodeConstant.SD_ROUTE_CODE);
			debitNote.setStatus("0");
			debitNote.setTxnMethod(PayTypeConstant.PAY_METHOD_GZHZF);
			debitNote.setTxnType("1");
			debitNote.setMemberCode(memberInfo.getWxMemberCode());
			debitNote.setMerchantCode(merchantCode.getWxMerchantCode());
			debitNote.setSettleType(memberInfo.getSettleType());
			debitNote.setRemarks(remark);
			if("0".equals(memberInfo.getSettleType())){
				debitNote.setTradeRate(merchantCode.getT0TradeRate());
			}else{
				debitNote.setTradeRate(merchantCode.getT1TradeRate());
			}
			debitNoteService.insertSelective(debitNote);
			// String callBack = "http://" + request.getServerName() + ":" +
			// request.getServerPort() + request.getContextPath()+
			// "/debitNote/msNotify";
			String callBack = SysConfig.serverUrl + "/debitNote/sdPayNotify";
			String frontUrl = SysConfig.frontUrl + "/debitNote/sdResult";
			// 调用支付通道
			String serverUrl = SDConfig.msServerUrl + "/order/pay";
			//PublicKey yhPubKey = null;
			//yhPubKey = CryptoUtil.getEskRSAPublicKey();
			//yhPubKey = CryptoUtil.getRSAPublicKey(false);
			//PrivateKey hzfPriKey = CryptoUtil.getRSAPrivateKey();
			String tranCode = "003";
			String charset = "utf-8";
			
			CertUtil.init("classpath:"+EnvironmentUtil.propertyPath + "sdkey/cd-qz-gs.cer", 
					"classpath:"+EnvironmentUtil.propertyPath + "sdkey/cd-qz-ss.pfx", "123654");
			
			
			SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
			
			SandpayRequestHead head = new SandpayRequestHead();
			SandOrderPayRequestBody body = new SandOrderPayRequestBody();
			SandOrderPayRequest req = new SandOrderPayRequest();
			
			req.setHead(head);
			req.setBody(body);
			
			PacketTool.setDefaultRequestHead(head, "sandpay.trade.pay", "00000005", merchantCode.getWxMerchantCode());
			
			body.setOrderCode(orderCode);
			body.setTotalAmount("000000000001");
			body.setSubject( memberInfo.getName() + " 收款");
			body.setBody( memberInfo.getName() + " 收款");
			body.setPayMode("sand_wxh5");
			JSONObject payExtra = new JSONObject();
			payExtra.put("appId", SDConfig.subAppid);
			payExtra.put("ip", request.getRemoteAddr());
			payExtra.put("sceneInfo", memberInfo.getName() + " 收款");
			body.setPayExtra(payExtra.toString());
			body.setClientIp(request.getRemoteAddr());
			body.setNotifyUrl(callBack);
			body.setFrontUrl(frontUrl);
			if("0".equals(memberInfo.getSettleType())){
				body.setClearCycle("2");
			}else{
				body.setClearCycle("0");
			}
			
			SandOrderPayResponse res =  SandpayClient.execute(req, serverUrl);
			
		/*	JSONObject headData = new JSONObject();
			headData.put("version", "1.0");
			headData.put("method", "sandpay.trade.pay");
			headData.put("productId", "00000005");
			headData.put("accessType", "1");
			headData.put("mid", merchantCode.getWxMerchantCode());
			headData.put("channelType", "08");
			headData.put("reqTime", format.format(new Date()));
			
			JSONObject bodyData = new JSONObject();
			bodyData.put("orderCode", orderCode);
			bodyData.put("totalAmount", money);
			bodyData.put("subject", memberInfo.getName() + " 收款");
			bodyData.put("body", memberInfo.getName() + " 收款");
			bodyData.put("payMode", "sand_wxh5");
				bodyData.put("payExtra", payExtra.toString());
			bodyData.put("clientIp", request.getRemoteAddr());
			bodyData.put("notifyUrl", callBack);
			bodyData.put("frontUrl", frontUrl);
			if("0".equals(memberInfo.getSettleType())){
				bodyData.put("clearCycle", "2");
			}else{
				bodyData.put("clearCycle", "0");
			}
			
				
			JSONObject reqData = new JSONObject();
			reqData.put("head", headData.toString());
			reqData.put("body", bodyData.toString());
			
			System.out.println("待加密数据: "+reqData);
			
			String plainXML = reqData.toString();
			byte[] plainBytes = plainXML.getBytes(charset);
			String keyStr = MSCommonUtil.generateLenString(16);
			;
			byte[] keyBytes = keyStr.getBytes(charset);
			
			//String encryptData = new String(Base64.encodeBase64((Key.jdkAES(plainBytes, keyBytes))));
			String signData = new String(Base64.encodeBase64(Key.rsaSign(plainBytes, ESKConfig.privateKey)), charset);
			String encryptData = new String(Base64.encodeBase64((CryptoUtil.AESEncrypt(plainBytes, keyBytes, "AES", "AES/ECB/PKCS5Padding", null))), charset);
			//String signData = new String(Base64.encodeBase64(CryptoUtil.digitalSign(plainBytes, hzfPriKey, "SHA1WithRSA")), charset);
			String encrtptKey = new String(Base64.encodeBase64(Key.jdkRSA(keyBytes, ESKConfig.yhPublicKey)), charset);

			//String encrtptKey = new String(Base64.encodeBase64(CryptoUtil.RSAEncrypt(keyBytes, yhPubKey, 2048, 11, "RSA/ECB/PKCS1Padding")), charset);
			List<NameValuePair> nvps = new LinkedList<NameValuePair>();
			nvps.add(new BasicNameValuePair("Context", encryptData));
			nvps.add(new BasicNameValuePair("encrtpKey", encrtptKey));
			
			nvps.add(new BasicNameValuePair("signData", signData));
			nvps.add(new BasicNameValuePair("agentId", ESKConfig.agentId));
			byte[] b = HttpClient4Util.getInstance().doPost(serverUrl, null, nvps);
			String respStr = new String(b, charset);
			logger.info("返回报文[{}]", new Object[] { respStr });
			JSONObject jsonObject = JSONObject.fromObject(respStr);
			String resEncryptData = jsonObject.getString("Context");
			String resEncryptKey = jsonObject.getString("encrtpKey");
			byte[] decodeBase64KeyBytes = Base64.decodeBase64(resEncryptKey.getBytes(charset));
			// 解密encryptKey得到merchantAESKey
			//byte[] merchantAESKeyBytes = CryptoUtil.RSADecrypt(decodeBase64KeyBytes, hzfPriKey, 2048, 11, "RSA/ECB/PKCS1Padding");
			byte[] merchantAESKeyBytes = Key.jdkRSA_(decodeBase64KeyBytes, ESKConfig.privateKey);
					

			// 使用base64解码商户请求报文
			byte[] decodeBase64DataBytes = Base64.decodeBase64(resEncryptData.getBytes(charset));
			// 用解密得到的merchantAESKey解密encryptData
			byte[] merchantXmlDataBytes = CryptoUtil.AESDecrypt(decodeBase64DataBytes, merchantAESKeyBytes, "AES", "AES/ECB/PKCS5Padding", null);
			String resXml = new String(merchantXmlDataBytes, charset);
			JSONObject respJSONObject = JSONObject.fromObject(resXml);
			logger.info("返回报文[{}]",  respJSONObject );
			
			resData.put("orderCode", orderCode);
			if("S".equals(respJSONObject.getString("respType"))&&"000000".equals(respJSONObject.getString("respCode"))){
				if(respJSONObject.containsKey("wx_json")&&!"_WX_Pay_FAILED_".equals(respJSONObject.getString("wx_json"))){
					JSONObject wxjsapiStr=respJSONObject.getJSONObject("wx_json");
					wxjsapiStr.put("package_str", wxjsapiStr.getString("package"));
					resData.put("wxjsapiStr", wxjsapiStr);
					result.put("resData", resData);
					result.put("returnCode", "0000");
					result.put("returnMsg", "成功");
					
				}else{
					result.put("returnCode", "4004");
					result.put("returnMsg", "获取微信信息失败");
				}
			}else{
				result.put("returnCode", "4004");
				result.put("returnMsg", "调用微信支付接口返回失败("+respJSONObject.getString("respMsg")+")");
			}
		*/

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
		return result;
	}
	
	
	
	
	
	/**
	 * 民生微信扫码支付
	 * 
	 * @param request
	 * @return
	 */
	
	public JSONObject msWxScanQrcodePay(JSONObject reqDataJson, HttpServletRequest request) {
		JSONObject result = new JSONObject();
		JSONObject resData = new JSONObject();
		try {
			String money = reqDataJson.getString("money");
			String epayCode = reqDataJson.getString("epayCode");
			if ("".equals(money)) {
				throw new ArgException("请输入正确的金额");
			}
			
			if ("".equals(epayCode)) {
				throw new ArgException("收款码出错");
			}
			
			EpayCodeExample epayCodeExample = new EpayCodeExample();
			epayCodeExample.createCriteria().andPayCodeEqualTo(epayCode).andDelFlagEqualTo("0");
			List<EpayCode> epayCodes = epayCodeService.selectByExample(epayCodeExample);
			if (epayCodes == null || epayCodes.size() == 0) {
				throw new ArgException("收款码不存在");
			}
			
			EpayCode payCode = epayCodes.get(0);
			if(!"5".equals(payCode.getStatus()) && !"7".equals(payCode.getStatus())){
				throw new ArgException("对不起,收款码不可用，如有疑问请与管理员联系");
			}
			
			MemberInfo memberInfo = memberInfoService.selectByPrimaryKey(payCode.getMemberId());
			
			if (memberInfo == null) {
				throw new ArgException("商家不存在");
			}
			if(!"0".equals(memberInfo.getDelFlag())){
				throw new ArgException("商家不存在");
			}
			if(!"0".equals(memberInfo.getStatus())){
				throw new ArgException("商家停用，暂不可交易");
			}
			AccountExample accountExample = new AccountExample();
			accountExample.createCriteria().andMemberIdEqualTo(memberInfo.getId()).andDelFlagEqualTo("0");
			List<Account> accounts = accountService.selectByExample(accountExample);
			if (accounts == null || accounts.size() != 1) {
				throw new ArgException("会员账户不存在");
			}
			
			// 插入一条收款记录
			String orderCode = CommonUtil.getOrderCode();
			
			DebitNote debitNote = new DebitNote();
			debitNote.setCreateDate(new Date());
			debitNote.setMemberId(memberInfo.getId());
			debitNote.setMoney(new BigDecimal(money));
			debitNote.setOrderCode(orderCode);
			debitNote.setRouteId(memberInfo.getWxRouteId());
			debitNote.setStatus("0");
			debitNote.setTxnType("1");
			debitNote.setMemberCode(memberInfo.getWxMemberCode());
			debitNote.setMerchantCode(memberInfo.getWxMerchantCode());
			debitNote.setSettleType(memberInfo.getSettleType());
			debitNoteService.insertSelective(debitNote);
			
			// String callBack = "http://" + request.getServerName() + ":" +
			// request.getServerPort() + request.getContextPath()+
			// "/debitNote/msNotify";
			String callBack = SysConfig.serverUrl + "/debitNote/msPayNotify";
			// 调用支付通道
			String serverUrl = MSConfig.msServerUrl;
			PublicKey yhPubKey = null;
			if (serverUrl.startsWith("https://ipay")) {
				yhPubKey = CryptoUtil.getRSAPublicKey(true);
			} else {
				yhPubKey = CryptoUtil.getRSAPublicKey(false);
			}
			PrivateKey hzfPriKey = CryptoUtil.getRSAPrivateKey();
			String tranCode = "SMZF002";
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
			sBuilder.append("<merchantCode>" + memberInfo.getWxMerchantCode() + "</merchantCode>");
			sBuilder.append("<totalAmount>" + money + "</totalAmount>");
			sBuilder.append("<subject>" + memberInfo.getName() + " 收款" + "</subject>");
			sBuilder.append("<subAppid>" + WxConfig.appid + "</subAppid>");
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
			
			if("0".equals(memberInfo.getSettleType())){
				nvps.add(new BasicNameValuePair("cooperator", MSConfig.cooperator_t0));
			}else{
				nvps.add(new BasicNameValuePair("cooperator", MSConfig.cooperator_t1));
			}
			
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
			System.out.println(respJSONObject);
			resData.put("orderCode", orderCode);
			if (respJSONObject.containsKey("resEntity")) {
				JSONObject resEntity = respJSONObject.getJSONObject("resEntity");
				if (resEntity.containsKey("qrCode")) {
					resData.put("qrCode", SecurityUtil.base64Encode(resEntity.get("qrCode").toString()));
					result.put("resData", resData);
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

	/**
	 * 民生支付宝支付
	 * 
	 * @param request
	 * @return
	 */

	public JSONObject msAliPay(JSONObject reqDataJson, HttpServletRequest request) {
		JSONObject result = new JSONObject();
		JSONObject resData = new JSONObject();
		try {
			String money = reqDataJson.getString("money");
			String epayCode = reqDataJson.getString("epayCode");
//			 String userId = "2088502362301099";
			String userId = reqDataJson.getString("userId");
			String remark = reqDataJson.getString("remark");
			if ("".equals(money)) {
				throw new ArgException("请输入正确的金额");
			}

			if ("".equals(epayCode)) {
				throw new ArgException("收款码出错");
			}
			
			if(StringUtils.isNotEmpty(remark)){
				if(remark.length() > 20){
					throw new ArgException("备注信息超出限制");
				}
			}

			EpayCodeExample epayCodeExample = new EpayCodeExample();
			epayCodeExample.createCriteria().andPayCodeEqualTo(epayCode).andDelFlagEqualTo("0");
			List<EpayCode> epayCodes = epayCodeService.selectByExample(epayCodeExample);
			if (epayCodes == null || epayCodes.size() == 0) {
				throw new ArgException("收款码不存在");
			}

			EpayCode payCode = epayCodes.get(0);
			if(!"5".equals(payCode.getStatus()) && !"7".equals(payCode.getStatus())){
				throw new ArgException("对不起,收款码不可用，如有疑问请与管理员联系");
			}

			MemberInfo memberInfo = memberInfoService.selectByPrimaryKey(payCode.getMemberId());

			if (memberInfo == null) {
				throw new ArgException("商家不存在");
			}
			if(!"0".equals(memberInfo.getDelFlag())){
				throw new ArgException("商家不存在");
			}
			if(!"0".equals(memberInfo.getStatus())){
				if("4".equals(memberInfo.getStatus())){
					throw new ArgException("该商户未进行认证，暂时无法交易");
				}
				throw new ArgException("商家停用，暂不可交易");
			}
			AccountExample accountExample = new AccountExample();
			accountExample.createCriteria().andMemberIdEqualTo(memberInfo.getId()).andDelFlagEqualTo("0");
			List<Account> accounts = accountService.selectByExample(accountExample);
			if (accounts == null || accounts.size() != 1) {
				throw new ArgException("会员账户不存在");
			}
			
			JSONObject checkResult = checkPayLimit(memberInfo.getId(), new BigDecimal(money), memberInfo.getSingleLimit(), memberInfo.getDayLimit());
			if(null != checkResult){
				return checkResult;
			}

			// 插入一条收款记录
			String orderCode = CommonUtil.getOrderCode();
			DebitNote debitNote = new DebitNote();
			debitNote.setCreateDate(new Date());
			debitNote.setMemberId(memberInfo.getId());
			debitNote.setMoney(new BigDecimal(money));
			debitNote.setOrderCode(orderCode);
			debitNote.setRouteId(memberInfo.getZfbRouteId());
			debitNote.setStatus("0");
			debitNote.setTxnMethod(PayTypeConstant.PAY_METHOD_GZHZF);
			debitNote.setTxnType("2");
			debitNote.setMemberCode(memberInfo.getZfbMemberCode());
			debitNote.setMerchantCode(memberInfo.getZfbMerchantCode());
			debitNote.setSettleType(memberInfo.getSettleType());
			debitNote.setRemarks(remark);
			debitNoteService.insertSelective(debitNote);

			// String callBack = "http://" + request.getServerName() + ":" +
			// request.getServerPort() + request.getContextPath()+
			// "/debitNote/msNotify";
			String callBack = SysConfig.serverUrl + "/debitNote/msPayNotify";
			// 调用支付通道
			String serverUrl = MSConfig.msServerUrl;
			PublicKey yhPubKey = null;
			if (serverUrl.startsWith("https://ipay")) {
				yhPubKey = CryptoUtil.getRSAPublicKey(true);
			} else {
				yhPubKey = CryptoUtil.getRSAPublicKey(false);
			}
			PrivateKey hzfPriKey = CryptoUtil.getRSAPrivateKey();
			String tranCode = "SMZF010";
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
			sBuilder.append("<merchantCode>" + memberInfo.getZfbMerchantCode() + "</merchantCode>");
			sBuilder.append("<totalAmount>" + money + "</totalAmount>");
			sBuilder.append("<subject>" + memberInfo.getName() + " 收款" + "</subject>");
			sBuilder.append("<userId>" + userId + "</userId>");
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
			
			if("0".equals(memberInfo.getSettleType())){
				nvps.add(new BasicNameValuePair("cooperator", MSConfig.cooperator_t0));
			}else{
				nvps.add(new BasicNameValuePair("cooperator", MSConfig.cooperator_t1));
			}
			
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
			System.out.println(respJSONObject);
			resData.put("orderCode", orderCode);
			if (respJSONObject.containsKey("resEntity")) {
				JSONObject resEntity = respJSONObject.getJSONObject("resEntity");
				if (resEntity.containsKey("channelNo")) {
					resData.put("channelNo", resEntity.get("channelNo"));
					result.put("resData", resData);
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
	
	
	public JSONObject eskAliPay(JSONObject reqDataJson, HttpServletRequest request, MemberInfo memberInfo, String aisleType) {
		JSONObject result = new JSONObject();
		JSONObject resData = new JSONObject();
		try {
			String money = reqDataJson.getString("money");
			String remark = reqDataJson.getString("remark");
//			 String userId = "2088502362301099";
			String userId = reqDataJson.getString("userId");
			if ("".equals(money)) {
				throw new ArgException("请输入正确的金额");
			}

			if(StringUtils.isNotEmpty(remark)){
				if(remark.length() > 20){
					throw new ArgException("备注信息超出限制");
				}
			}

			AccountExample accountExample = new AccountExample();
			accountExample.createCriteria().andMemberIdEqualTo(memberInfo.getId()).andDelFlagEqualTo("0");
			List<Account> accounts = accountService.selectByExample(accountExample);
			if (accounts == null || accounts.size() != 1) {
				throw new ArgException("会员账户不存在");
			}
			JSONObject checkResult = checkPayLimit(memberInfo.getId(), new BigDecimal(money), memberInfo.getSingleLimit(), memberInfo.getDayLimit());
			if(null != checkResult){
				return checkResult;
			}
			
			MemberMerchantCodeExample memberMerchantCodeExample = new MemberMerchantCodeExample();
			memberMerchantCodeExample.createCriteria().andMemberIdEqualTo(memberInfo.getId()).andRouteCodeEqualTo(RouteCodeConstant.ESK_ROUTE_CODE).andAisleTypeEqualTo(aisleType).andDelFlagEqualTo("0");
			List<MemberMerchantCode> merchantCodes = memberMerchantCodeService.selectByExample(memberMerchantCodeExample);
			if (merchantCodes == null || merchantCodes.size() != 1) {
				throw new ArgException("商户编码不存在");
			}
			
			MemberMerchantCode merchantCode = merchantCodes.get(0);
			
			// 插入一条收款记录
			String orderCode = CommonUtil.getOrderCode();
			
			DebitNote debitNote = new DebitNote();
			debitNote.setCreateDate(new Date());
			debitNote.setMemberId(memberInfo.getId());
			debitNote.setMoney(new BigDecimal(money));
			debitNote.setOrderCode(orderCode);
			debitNote.setRouteId(RouteCodeConstant.ESK_ROUTE_CODE);
			debitNote.setStatus("0");
			debitNote.setTxnMethod(PayTypeConstant.PAY_METHOD_GZHZF);
			debitNote.setTxnType("2");
			debitNote.setMemberCode(memberInfo.getZfbMemberCode());
			debitNote.setMerchantCode(merchantCode.getZfbMerchantCode());
			debitNote.setSettleType(memberInfo.getSettleType());
			debitNote.setRemarks(remark);
			if("0".equals(memberInfo.getSettleType())){
				debitNote.setTradeRate(merchantCode.getT0TradeRate());
			}else{
				debitNote.setTradeRate(merchantCode.getT1TradeRate());
			}
			debitNoteService.insertSelective(debitNote);
			// String callBack = "http://" + request.getServerName() + ":" +
			// request.getServerPort() + request.getContextPath()+
			// "/debitNote/msNotify";
			String callBack = SysConfig.serverUrl + "/debitNote/eskPayNotify";
			// 调用支付通道
			String serverUrl = ESKConfig.msServerUrl;
			//PublicKey yhPubKey = null;
			//yhPubKey = CryptoUtil.getEskRSAPublicKey();
			//yhPubKey = CryptoUtil.getRSAPublicKey(false);
			//PrivateKey hzfPriKey = CryptoUtil.getRSAPrivateKey();
			String tranCode = "003";
			String charset = "utf-8";
			
			JSONObject reqData = new JSONObject();
			reqData.put("merchantCode", merchantCode.getZfbMerchantCode());
			reqData.put("orderNumber", orderCode);
			reqData.put("tranCode", tranCode);
			reqData.put("userId", userId);
			reqData.put("aisleType", aisleType);
			reqData.put("totalAmount", money);
			reqData.put("subject", memberInfo.getName() + " 收款");
			reqData.put("PayType", "0");
			reqData.put("callback", callBack);
			reqData.put("desc", memberInfo.getName() + " 收款");
			//reqData.put("subAppid", ESKConfig.subAppid);
			System.out.println("待加密数据: "+reqData);
			
			String plainXML = reqData.toString();
			byte[] plainBytes = plainXML.getBytes(charset);
			String keyStr = MSCommonUtil.generateLenString(16);
			;
			byte[] keyBytes = keyStr.getBytes(charset);
			
			//String encryptData = new String(Base64.encodeBase64((Key.jdkAES(plainBytes, keyBytes))));
			String signData = new String(Base64.encodeBase64(Key.rsaSign(plainBytes, ESKConfig.privateKey)), charset);
			String encryptData = new String(Base64.encodeBase64((CryptoUtil.AESEncrypt(plainBytes, keyBytes, "AES", "AES/ECB/PKCS5Padding", null))), charset);
			//String signData = new String(Base64.encodeBase64(CryptoUtil.digitalSign(plainBytes, hzfPriKey, "SHA1WithRSA")), charset);
			String encrtptKey = new String(Base64.encodeBase64(Key.jdkRSA(keyBytes, ESKConfig.yhPublicKey)), charset);

			//String encrtptKey = new String(Base64.encodeBase64(CryptoUtil.RSAEncrypt(keyBytes, yhPubKey, 2048, 11, "RSA/ECB/PKCS1Padding")), charset);
			List<NameValuePair> nvps = new LinkedList<NameValuePair>();
			nvps.add(new BasicNameValuePair("Context", encryptData));
			nvps.add(new BasicNameValuePair("encrtpKey", encrtptKey));
			
			nvps.add(new BasicNameValuePair("signData", signData));
			nvps.add(new BasicNameValuePair("agentId", ESKConfig.agentId));
			byte[] b = HttpClient4Util.getInstance().doPost(serverUrl, null, nvps);
			String respStr = new String(b, charset);
			logger.info("返回报文[{}]", new Object[] { respStr });
			JSONObject jsonObject = JSONObject.fromObject(respStr);
			String resEncryptData = jsonObject.getString("Context");
			String resEncryptKey = jsonObject.getString("encrtpKey");
			byte[] decodeBase64KeyBytes = Base64.decodeBase64(resEncryptKey.getBytes(charset));
			// 解密encryptKey得到merchantAESKey
			//byte[] merchantAESKeyBytes = CryptoUtil.RSADecrypt(decodeBase64KeyBytes, hzfPriKey, 2048, 11, "RSA/ECB/PKCS1Padding");
			byte[] merchantAESKeyBytes = Key.jdkRSA_(decodeBase64KeyBytes, ESKConfig.privateKey);
					

			// 使用base64解码商户请求报文
			byte[] decodeBase64DataBytes = Base64.decodeBase64(resEncryptData.getBytes(charset));
			// 用解密得到的merchantAESKey解密encryptData
			byte[] merchantXmlDataBytes = CryptoUtil.AESDecrypt(decodeBase64DataBytes, merchantAESKeyBytes, "AES", "AES/ECB/PKCS5Padding", null);
			String resXml = new String(merchantXmlDataBytes, charset);
			JSONObject respJSONObject = JSONObject.fromObject(resXml);
			logger.info("返回报文[{}]",  respJSONObject );
			
			resData.put("orderCode", orderCode);
			if("S".equals(respJSONObject.getString("respType"))&&"000000".equals(respJSONObject.getString("respCode"))){
				
				if(respJSONObject.containsKey("OrderNo")){
					resData.put("channelNo", respJSONObject.get("OrderNo"));
					result.put("resData", resData);
					result.put("returnCode", "0000");
					result.put("returnMsg", "成功");
					
				}else{
					result.put("returnCode", "4004");
					result.put("returnMsg", "获取支付宝支付信息失败");
				}
			}else{
				result.put("returnCode", "4004");
				result.put("returnMsg", "调用支付宝支付接口返回失败("+respJSONObject.getString("respMsg")+")");
			}
		/*	if (respJSONObject.containsKey("resEntity")) {
				JSONObject resEntity = respJSONObject.getJSONObject("resEntity");
				if (resEntity.containsKey("wxjsapiStr")) {
					JSONObject wxjsapiStr=resEntity.getJSONObject("wxjsapiStr");
					wxjsapiStr.put("package_str", wxjsapiStr.getString("package"));
					resData.put("wxjsapiStr", wxjsapiStr);
					result.put("resData", resData);
				}
			}*/

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
		return result;
	}
	
	
	
	/**
	 * 民生QQ钱包扫码支付
	 * 
	 * @param request
	 * @return
	 */
	
	public JSONObject msQqScanQrcodePay(JSONObject reqDataJson, HttpServletRequest request) {
		JSONObject result = new JSONObject();
		JSONObject resData = new JSONObject();
		try {
			String money = reqDataJson.getString("money");
			String epayCode = reqDataJson.getString("epayCode");
			String remark = reqDataJson.getString("remark");
			if ("".equals(money)) {
				throw new ArgException("请输入正确的金额");
			}
			
			if ("".equals(epayCode)) {
				throw new ArgException("收款码出错");
			}
			
			if(StringUtils.isNotEmpty(remark)){
				if(remark.length() > 20){
					throw new ArgException("备注信息超出限制");
				}
			}
			
			EpayCodeExample epayCodeExample = new EpayCodeExample();
			epayCodeExample.createCriteria().andPayCodeEqualTo(epayCode).andDelFlagEqualTo("0");
			List<EpayCode> epayCodes = epayCodeService.selectByExample(epayCodeExample);
			if (epayCodes == null || epayCodes.size() == 0) {
				throw new ArgException("收款码不存在");
			}
			
			EpayCode payCode = epayCodes.get(0);
			if(!"5".equals(payCode.getStatus()) && !"7".equals(payCode.getStatus())){
				throw new ArgException("对不起,收款码不可用，如有疑问请与管理员联系");
			}
			
			MemberInfo memberInfo = memberInfoService.selectByPrimaryKey(payCode.getMemberId());
			
			if (memberInfo == null) {
				throw new ArgException("商家不存在");
			}
			if(!"0".equals(memberInfo.getDelFlag())){
				throw new ArgException("商家不存在");
			}
			if(!"0".equals(memberInfo.getStatus())){
				if("4".equals(memberInfo.getStatus())){
					throw new ArgException("该商户未进行认证，暂时无法交易");
				}
				throw new ArgException("商家停用，暂不可交易");
			}
			AccountExample accountExample = new AccountExample();
			accountExample.createCriteria().andMemberIdEqualTo(memberInfo.getId()).andDelFlagEqualTo("0");
			List<Account> accounts = accountService.selectByExample(accountExample);
			if (accounts == null || accounts.size() != 1) {
				throw new ArgException("会员账户不存在");
			}
			
			JSONObject checkResult = checkPayLimit(memberInfo.getId(), new BigDecimal(money), memberInfo.getSingleLimit(), memberInfo.getDayLimit());
			if(null != checkResult){
				return checkResult;
			}
			
			// 插入一条收款记录
			String orderCode = CommonUtil.getOrderCode();
			
			DebitNote debitNote = new DebitNote();
			debitNote.setCreateDate(new Date());
			debitNote.setMemberId(memberInfo.getId());
			debitNote.setMoney(new BigDecimal(money));
			debitNote.setOrderCode(orderCode);
			debitNote.setRouteId(memberInfo.getQqRouteId());
			debitNote.setStatus("0");
			debitNote.setTxnType("3");
			debitNote.setMemberCode(memberInfo.getQqMemberCode());
			debitNote.setMerchantCode(memberInfo.getQqMerchantCode());
			debitNote.setSettleType(memberInfo.getSettleType());
			debitNote.setRemarks(remark);
			debitNoteService.insertSelective(debitNote);
			
			// String callBack = "http://" + request.getServerName() + ":" +
			// request.getServerPort() + request.getContextPath()+
			// "/debitNote/msNotify";
			String callBack = SysConfig.serverUrl + "/debitNote/msPayNotify";
			// 调用支付通道
			String serverUrl = MSConfig.msServerUrl;
			PublicKey yhPubKey = null;
			if (serverUrl.startsWith("https://ipay")) {
				yhPubKey = CryptoUtil.getRSAPublicKey(true);
			} else {
				yhPubKey = CryptoUtil.getRSAPublicKey(false);
			}
			PrivateKey hzfPriKey = CryptoUtil.getRSAPrivateKey();
			String tranCode = "SMZF002";
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
			sBuilder.append("<merchantCode>" + memberInfo.getQqMerchantCode() + "</merchantCode>");
			sBuilder.append("<totalAmount>" + money + "</totalAmount>");
			sBuilder.append("<subject>" + memberInfo.getName() + " 收款" + "</subject>");
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
			
			if("0".equals(memberInfo.getSettleType())){
				nvps.add(new BasicNameValuePair("cooperator", MSConfig.cooperator_t0));
			}else{
				nvps.add(new BasicNameValuePair("cooperator", MSConfig.cooperator_t1));
			}
			
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
			System.out.println(respJSONObject);
			resData.put("orderCode", orderCode);
			if (respJSONObject.containsKey("resEntity")) {
				JSONObject resEntity = respJSONObject.getJSONObject("resEntity");
				if (resEntity.containsKey("qrCode")) {
					resData.put("qrCode", SecurityUtil.base64Encode(resEntity.get("qrCode").toString()));
					result.put("resData", resData);
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
	
	/**
	 * 民生qq钱包/百度钱包/京东钱包通用扫码支付
	 * 
	 * @param request
	 * @return
	 */
	
	public JSONObject msScanQrcodePayCommon(String payWay,JSONObject reqDataJson, HttpServletRequest request) {
		JSONObject result = new JSONObject();
		JSONObject resData = new JSONObject();
		try {
			String money = reqDataJson.getString("money");
			String epayCode = reqDataJson.getString("epayCode");
			String remark = reqDataJson.getString("remark");
			if ("".equals(money)) {
				throw new ArgException("请输入正确的金额");
			}
			
			if ("".equals(epayCode)) {
				throw new ArgException("收款码出错");
			}
			
			if(StringUtils.isNotEmpty(remark)){
				if(remark.length() > 20){
					throw new ArgException("备注信息超出长度限制");
				}
			}
			
			EpayCodeExample epayCodeExample = new EpayCodeExample();
			epayCodeExample.createCriteria().andPayCodeEqualTo(epayCode).andDelFlagEqualTo("0");
			List<EpayCode> epayCodes = epayCodeService.selectByExample(epayCodeExample);
			if (epayCodes == null || epayCodes.size() == 0) {
				throw new ArgException("收款码不存在");
			}
			
			EpayCode payCode = epayCodes.get(0);
			if(!"5".equals(payCode.getStatus()) && !"7".equals(payCode.getStatus())){
				throw new ArgException("对不起,收款码不可用，如有疑问请与管理员联系");
			}
			
			MemberInfo memberInfo = memberInfoService.selectByPrimaryKey(payCode.getMemberId());
			
			if (memberInfo == null) {
				throw new ArgException("商家不存在");
			}
			if(!"0".equals(memberInfo.getDelFlag())){
				throw new ArgException("商家不存在");
			}
			if(!"0".equals(memberInfo.getStatus())){
				if("4".equals(memberInfo.getStatus())){
					throw new ArgException("该商户未进行认证，暂时无法交易");
				}
				throw new ArgException("商家停用，暂不可交易");
			}
			AccountExample accountExample = new AccountExample();
			accountExample.createCriteria().andMemberIdEqualTo(memberInfo.getId()).andDelFlagEqualTo("0");
			List<Account> accounts = accountService.selectByExample(accountExample);
			if (accounts == null || accounts.size() != 1) {
				throw new ArgException("会员账户不存在");
			}
			
			JSONObject checkResult = checkPayLimit(memberInfo.getId(), new BigDecimal(money), memberInfo.getSingleLimit(), memberInfo.getDayLimit());
			if(null != checkResult){
				return checkResult;
			}
			
			// 插入一条收款记录
			String orderCode = CommonUtil.getOrderCode();
			String merchantCode = null;
			
			DebitNote debitNote = new DebitNote();
			debitNote.setCreateDate(new Date());
			debitNote.setMemberId(memberInfo.getId());
			debitNote.setMoney(new BigDecimal(money));
			debitNote.setOrderCode(orderCode);
			debitNote.setStatus("0");
			if("mqqbrowser".equals(payWay)){
				debitNote.setRouteId(memberInfo.getQqRouteId());
				debitNote.setTxnType("3");
				debitNote.setMemberCode(memberInfo.getQqMemberCode());
				merchantCode = memberInfo.getQqMerchantCode();
			}else if("baiduwallet".equals(payWay)){
				debitNote.setRouteId(memberInfo.getBdRouteId());
				debitNote.setTxnType("4");
				debitNote.setMemberCode(memberInfo.getBdMemberCode());
				merchantCode = memberInfo.getBdMerchantCode();
			}else if("jdjrpay".equals(payWay)){
				debitNote.setRouteId(memberInfo.getJdRouteId());
				debitNote.setTxnType("5");
				debitNote.setMemberCode(memberInfo.getJdMemberCode());
				merchantCode = memberInfo.getJdMerchantCode();
			}
			debitNote.setMerchantCode(merchantCode);
			debitNote.setSettleType(memberInfo.getSettleType());
			debitNote.setRemarks(remark);
			debitNoteService.insertSelective(debitNote);
			
			// String callBack = "http://" + request.getServerName() + ":" +
			// request.getServerPort() + request.getContextPath()+
			// "/debitNote/msNotify";
			String callBack = SysConfig.serverUrl + "/debitNote/msPayNotify";
			// 调用支付通道
			String serverUrl = MSConfig.msServerUrl;
			PublicKey yhPubKey = null;
			if (serverUrl.startsWith("https://ipay")) {
				yhPubKey = CryptoUtil.getRSAPublicKey(true);
			} else {
				yhPubKey = CryptoUtil.getRSAPublicKey(false);
			}
			PrivateKey hzfPriKey = CryptoUtil.getRSAPrivateKey();
			String tranCode = "SMZF002";
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
			sBuilder.append("<totalAmount>" + money + "</totalAmount>");
			sBuilder.append("<subject>" + memberInfo.getName() + " 收款" + "</subject>");
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
			
			if("0".equals(memberInfo.getSettleType())){
				nvps.add(new BasicNameValuePair("cooperator", MSConfig.cooperator_t0));
			}else{
				nvps.add(new BasicNameValuePair("cooperator", MSConfig.cooperator_t1));
			}
			
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
			System.out.println(respJSONObject);
			resData.put("orderCode", orderCode);
			if (respJSONObject.containsKey("resEntity")) {
				JSONObject resEntity = respJSONObject.getJSONObject("resEntity");
				if (resEntity.containsKey("qrCode")) {
					resData.put("qrCode", SecurityUtil.base64Encode(resEntity.get("qrCode").toString()));
					result.put("resData", resData);
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

	@RequestMapping("/debitNote/msPayNotify")
	public void msPayNotify(HttpServletRequest request,HttpServletResponse response) {
		try {
			String resEncryptData = request.getParameter("encryptData");

			String resEncryptKey = request.getParameter("encryptKey");
			String charset = "utf-8";
			PrivateKey hzfPriKey = CryptoUtil.getRSAPrivateKey();
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

			JSONObject resEntity = respJSONObject.getJSONObject("resEntity");
			String reqMsgId = resEntity.getString("reqMsgId");
			MsResultNoticeExample msResultNoticeExample = new MsResultNoticeExample();
			msResultNoticeExample.or().andOrderCodeEqualTo(reqMsgId);
			List<MsResultNotice> msResultNoticeList = msResultNoticeService.selectByExample(msResultNoticeExample);
			if(null == msResultNoticeList || msResultNoticeList.size() == 0){
				DebitNoteExample debitNoteExample = new DebitNoteExample();
				debitNoteExample.createCriteria().andOrderCodeEqualTo(reqMsgId);
				List<DebitNote> debitNotes = debitNoteService.selectByExample(debitNoteExample);

				if (debitNotes != null && debitNotes.size() > 0) {
					String tradeDate = DateUtil.getDateFormat(new Date(), "yyyyMMdd");
					DebitNote debitNote = debitNotes.get(0);
					debitNote.setRespCode(respJSONObject.get("respCode").toString());
					debitNote.setRespMsg(respJSONObject.get("respMsg").toString());
					
					//新增一条交易明细记录
					TradeDetail tradeDetail=new TradeDetail();
					tradeDetail.setTxnDate(tradeDate);
//					tradeDetail.setTxnDate(DateUtil.getDateFormat(new Date(), "yyyyMMdd"));
					tradeDetail.setMemberId(debitNote.getMemberId());
					tradeDetail.setMerchantCode(debitNote.getMerchantCode());
					tradeDetail.setBalanceDate(resEntity.get("settleDate").toString());
					tradeDetail.setChannelNo(resEntity.get("channelNo").toString());
					tradeDetail.setMemberCode(debitNote.getMemberCode());
					tradeDetail.setMoney(debitNote.getMoney());
					tradeDetail.setPayTime(resEntity.get("payTime").toString());
					tradeDetail.setPtSerialNo(debitNote.getOrderCode());
					tradeDetail.setOrderCode(debitNote.getOrderCode());
					tradeDetail.setReqDate(DateUtil.getDateTimeStr(debitNote.getCreateDate()));
					tradeDetail.setRespCode(respJSONObject.get("respCode").toString());
					tradeDetail.setRespDate(DateUtil.getDateTimeStr(new Date()));
					tradeDetail.setRespMsg(respJSONObject.get("respMsg").toString());
					tradeDetail.setTxnMethod(debitNote.getTxnMethod());
					tradeDetail.setRouteId(debitNote.getRouteId());
					tradeDetail.setTxnType(debitNote.getTxnType());
					tradeDetail.setRemarks(debitNote.getRemarks());
					tradeDetail.setDelFlag("0");
					tradeDetail.setCreateDate(new Date());
					if ("S".equals(respJSONObject.get("respType")) && "000000".equals(respJSONObject.get("respCode"))) {
						debitNote.setStatus("1");
						tradeDetail.setRespType("S");
					}else if("200002".equals(respJSONObject.get("respCode"))){
						debitNote.setStatus("3");
						tradeDetail.setRespType("R");
					}else{
						debitNote.setStatus("2");
						tradeDetail.setRespType("E");
					}
					debitNote.setUpdateDate(new Date());
					debitNoteService.updateByPrimaryKey(debitNote);
					
					MsResultNotice msResultNotice = new MsResultNotice();
					msResultNotice.setMoney(new BigDecimal(resEntity.getString("totalAmount")));
					msResultNotice.setOrderCode(reqMsgId);
					msResultNotice.setPtSerialNo(respJSONObject.getString("smzfMsgId"));
					msResultNotice.setRespDate(respJSONObject.getString("respDate"));
					msResultNotice.setRespType(respJSONObject.getString("respType"));
					msResultNotice.setRespCode(respJSONObject.getString("respCode"));
					msResultNotice.setRespMsg(respJSONObject.getString("respMsg"));
					if(resEntity.containsKey("payType") && null != resEntity.getString("payType")){
						msResultNotice.setCardType(resEntity.getString("payType"));
					}
					if(resEntity.containsKey("payTime") && null != resEntity.getString("payTime")){
						msResultNotice.setPayTime(resEntity.getString("payTime"));
					}
					msResultNotice.setBalanceDate(resEntity.getString("settleDate"));
					if(resEntity.containsKey("payTime") && null != resEntity.getString("payTime")){
						msResultNotice.setChannelNo(resEntity.getString("channelNo"));
					}
					if(resEntity.containsKey("isClearOrCancel") && null != resEntity.getString("isClearOrCancel")){
						msResultNotice.setSettleCancelFlag(resEntity.getString("isClearOrCancel"));
					}
					msResultNotice.setCreateDate(new Date());
					msResultNoticeService.insertSelective(msResultNotice);
					
					String nowTime = new SimpleDateFormat("HH:mm:ss").format(new Date());
					//更改逻辑  只有交易成功的记录才写进交易记录表  单独写在此处防止后面再次更改逻辑会不理解
//					if ("S".equals(respJSONObject.get("respType")) && "000000".equals(respJSONObject.get("respCode"))) {
//						tradeDetail.setCardType(msResultNotice.getCardType());
//						tradeDetailService.insertSelective(tradeDetail);
//					}
					//如果是T0交易则发起提现请求
					if ("S".equals(respJSONObject.get("respType")) && "000000".equals(respJSONObject.get("respCode"))) {
							//该段为即时提现功能的处理
						/*
						if("0".equals(debitNote.getSettleType())){
							if(DateUtil.dateCompare(nowTime,"09:00:00","HH:mm:ss")==1&&DateUtil.dateCompare(nowTime,"20:00:00","HH:mm:ss")==-1){
								logger.debug("----------T0 提现-------------");
								System.out.println("----------T0 提现-------------");
								tradeDetail.setSettleType("0");
								msWithdraw(debitNote.getMemberId(),debitNote.getMemberCode(),debitNote.getMerchantCode());
							}else{
								debitNote.setSettleType("1");
								tradeDetail.setSettleType("1");
								debitNoteService.updateByPrimaryKey(debitNote);
							}
						}else{
							tradeDetail.setSettleType("1");
						}
						*/
						tradeDetail.setSettleType("1");
						tradeDetail.setCardType(msResultNotice.getCardType());
						tradeDetailService.insertSelective(tradeDetail);//先保存交易记录，防止微信消息发送失败，丢失交易记录
						/*
						if("0".equals(debitNote.getSettleType())){
							tradeDetail.setSettleType("0");
						}else{
							tradeDetail.setSettleType("1");
						}
						*/
						//交易成功，则增加该笔交易到日交易额中
						TradeVolumnDailyExample tradeVolumnDailyExample = new TradeVolumnDailyExample();
						tradeVolumnDailyExample.or().andMemberIdEqualTo(tradeDetail.getMemberId()).andTradeDateEqualTo(tradeDate);
						List<TradeVolumnDaily> tradeVolumnDailyList = tradeVolumnDailyService.selectByExample(tradeVolumnDailyExample);
						TradeVolumnDaily tradeVolumnDaily = null;
						if(tradeVolumnDailyList.size() > 0){
							tradeVolumnDaily = tradeVolumnDailyList.get(0);
							tradeVolumnDaily.setTotalMoney(tradeVolumnDaily.getTotalMoney().add(tradeDetail.getMoney()));
							tradeVolumnDaily.setTradeCount(tradeVolumnDaily.getTradeCount() + 1);
							tradeVolumnDaily.setUpdateDate(new Date());
							tradeVolumnDailyService.updateByPrimaryKeySelective(tradeVolumnDaily);
						}else{
							tradeVolumnDaily = new TradeVolumnDaily();
							tradeVolumnDaily.setMemberId(tradeDetail.getMemberId());
							tradeVolumnDaily.setTotalMoney(tradeDetail.getMoney());
							tradeVolumnDaily.setTradeCount(1);
							tradeVolumnDaily.setTradeDate(tradeDate);
							tradeVolumnDaily.setCreateDate(new Date());
							tradeVolumnDaily.setUpdateDate(new Date());
							tradeVolumnDailyService.insertSelective(tradeVolumnDaily);
						}
						
						//发送微信模板消息
						System.out.println("模板消息getPoolSize====" + threadPoolTaskExecutor.getPoolSize());
						WeixinTemplateSendThread weixinTemplateSendThread = new WeixinTemplateSendThread(epayCodeService, memberOpenidService, sysOfficeConfigOemService, memberInfoService, sysOfficeService, debitNote, msResultNotice.getPayTime());
						threadPoolTaskExecutor.execute(weixinTemplateSendThread);
						System.out.println("模板消息getActiveCount====" + threadPoolTaskExecutor.getActiveCount());
					}
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}
		try {
			response.getWriter().write("000000");
		} catch (IOException e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}

	}
	
	
	//易收款公众号支付回调
	@RequestMapping("/debitNote/eskPayNotify")
	public void eskPayNotify(HttpServletRequest request,HttpServletResponse response) {
		try {
			String resEncryptData = request.getParameter("Context");
			
			String resEncryptKey = request.getParameter("encrtpKey");
			logger.info("eskPayNotify回调返回报文resEncryptData{}，resEncryptKey{}",  resEncryptData, resEncryptKey);
			String charset = "utf-8";
			byte[] decodeBase64KeyBytes = Base64.decodeBase64(resEncryptKey.getBytes(charset));
			// 解密encryptKey得到merchantAESKey  屏蔽by linxf 测试
			//byte[] merchantAESKeyBytes = CryptoUtil.RSADecrypt(decodeBase64KeyBytes, hzfPriKey, 2048, 11, "RSA/ECB/PKCS1Padding");
			// 使用base64解码商户请求报文
			byte[] merchantAESKeyBytes = Key.jdkRSA_(decodeBase64KeyBytes, ESKConfig.privateKey);
			byte[] decodeBase64DataBytes = Base64.decodeBase64(resEncryptData.getBytes(charset));
			// 用解密得到的merchantAESKey解密encryptData
			byte[] merchantXmlDataBytes = CryptoUtil.AESDecrypt(decodeBase64DataBytes, merchantAESKeyBytes, "AES", "AES/ECB/PKCS5Padding", null);
			String resXml = new String(merchantXmlDataBytes, charset);
			JSONObject respJSONObject = JSONObject.fromObject(resXml);
			logger.info("eskPayNotify解密回调返回报文[{}]",  respJSONObject );

	
			
			String reqMsgId = respJSONObject.getString("orderNumber");
			
			MsResultNoticeExample msResultNoticeExample = new MsResultNoticeExample();
			msResultNoticeExample.or().andOrderCodeEqualTo(reqMsgId);
			List<MsResultNotice> msResultNoticeList = msResultNoticeService.selectByExample(msResultNoticeExample);
			if(null == msResultNoticeList || msResultNoticeList.size() == 0){
				DebitNoteExample debitNoteExample = new DebitNoteExample();
				debitNoteExample.createCriteria().andOrderCodeEqualTo(reqMsgId);
				List<DebitNote> debitNotes = debitNoteService.selectByExample(debitNoteExample);

				if (debitNotes != null && debitNotes.size() > 0) {
					DebitNote debitNote = debitNotes.get(0);
					debitNote.setRespCode(respJSONObject.get("respCode").toString());
					debitNote.setRespMsg(respJSONObject.get("respMsg").toString());
					
					//新增一条交易明细记录
					TradeDetail tradeDetail=new TradeDetail();
					tradeDetail.setTxnDate(DateUtil.getDateFormat(new Date(), "yyyyMMdd"));
					tradeDetail.setMemberId(debitNote.getMemberId());
					tradeDetail.setMerchantCode(debitNote.getMerchantCode());
					if(respJSONObject.containsKey("settleDate") && null != respJSONObject.getString("settleDate")){
						tradeDetail.setBalanceDate(respJSONObject.get("settleDate").toString());
					}
					if(respJSONObject.containsKey("OrderNo") && null != respJSONObject.getString("OrderNo")){
						tradeDetail.setChannelNo(respJSONObject.get("OrderNo").toString());
					}
					tradeDetail.setMemberCode(debitNote.getMemberCode());
					tradeDetail.setMoney(debitNote.getMoney());
					if(respJSONObject.containsKey("payTime") && null != respJSONObject.getString("payTime")){
						tradeDetail.setPayTime(respJSONObject.get("payTime").toString());
					}else{
						tradeDetail.setPayTime(DateUtil.getDateTimeStr(debitNote.getCreateDate()));
					}
					tradeDetail.setPtSerialNo(debitNote.getOrderCode());
					tradeDetail.setOrderCode(debitNote.getOrderCode());
					tradeDetail.setReqDate(DateUtil.getDateTimeStr(debitNote.getCreateDate()));
					tradeDetail.setRespCode(respJSONObject.get("respCode").toString());
					tradeDetail.setRespDate(DateUtil.getDateTimeStr(new Date()));
					tradeDetail.setRespMsg(respJSONObject.get("respMsg").toString());
					tradeDetail.setRouteId(debitNote.getRouteId());
					tradeDetail.setTxnMethod(debitNote.getTxnMethod());
					tradeDetail.setTxnType(debitNote.getTxnType());
					tradeDetail.setMemberTradeRate(debitNote.getTradeRate());
					tradeDetail.setDelFlag("0");
					tradeDetail.setCreateDate(new Date());
					if ("S".equals(respJSONObject.get("respType")) && "000000".equals(respJSONObject.get("respCode"))) {
						debitNote.setStatus("1");
						tradeDetail.setRespType("S");
					}else if("200002".equals(respJSONObject.get("respCode"))){
						debitNote.setStatus("3");
						tradeDetail.setRespType("R");
					}else{
						debitNote.setStatus("2");
						tradeDetail.setRespType("E");
					}
					debitNote.setUpdateDate(new Date());
					debitNoteService.updateByPrimaryKey(debitNote);
					
					MsResultNotice msResultNotice = new MsResultNotice();
					msResultNotice.setMoney(new BigDecimal(respJSONObject.getString("buyerPayAmount")));
					msResultNotice.setOrderCode(reqMsgId);
					msResultNotice.setPtSerialNo(respJSONObject.getString("OrderNo"));
					String respTime = respJSONObject.getString("respTime");
					SimpleDateFormat dateSimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					msResultNotice.setRespDate(DateUtil.getDateTimeStr(dateSimpleDateFormat.parse(respTime)));
					msResultNotice.setRespType(respJSONObject.getString("respType"));
					msResultNotice.setRespCode(respJSONObject.getString("respCode"));
					msResultNotice.setRespMsg(respJSONObject.getString("respMsg"));
					if(respJSONObject.containsKey("payType") && null != respJSONObject.getString("payType")){
						msResultNotice.setCardType(respJSONObject.getString("payType"));
					}
					if(respJSONObject.containsKey("payTime") && null != respJSONObject.getString("payTime")){
						msResultNotice.setPayTime(respJSONObject.getString("payTime"));
					}
					if(respJSONObject.containsKey("settleDate") && null != respJSONObject.getString("settleDate")){
						msResultNotice.setBalanceDate(respJSONObject.getString("settleDate"));
					}
					if(respJSONObject.containsKey("payTime") && null != respJSONObject.getString("payTime")){
						msResultNotice.setChannelNo(respJSONObject.getString("channelNo"));
					}
					if(respJSONObject.containsKey("isClearOrCancel") && null != respJSONObject.getString("isClearOrCancel")){
						msResultNotice.setSettleCancelFlag(respJSONObject.getString("isClearOrCancel"));
					}
					msResultNotice.setCreateDate(new Date());
					msResultNotice.setUpdateDate(new Date());
					msResultNoticeService.insertSelective(msResultNotice);
					
					
					
					
					String tradeDate = DateUtil.getDateFormat(new Date(), "yyyyMMdd");
					String nowTime = new SimpleDateFormat("HH:mm:ss").format(new Date());
					//更改逻辑  只有交易成功的记录才写进交易记录表  单独写在此处防止后面再次更改逻辑会不理解
//					if ("S".equals(respJSONObject.get("respType")) && "000000".equals(respJSONObject.get("respCode"))) {
//						tradeDetail.setCardType(msResultNotice.getCardType());
//						tradeDetailService.insertSelective(tradeDetail);
//					}
					//如果是T0交易则发起提现请求
					if ("S".equals(respJSONObject.get("respType")) && "000000".equals(respJSONObject.get("respCode"))) {
						
						if("0".equals(debitNote.getSettleType())){
							tradeDetail.setSettleType("0");
						}else{
							tradeDetail.setSettleType("1");
						}
						tradeDetail.setCardType(msResultNotice.getCardType());
						tradeDetailService.insertSelective(tradeDetail);//先保存交易记录，防止微信消息发送失败，丢失交易记录
						
						//交易成功，则增加该笔交易到日交易额中
						TradeVolumnDailyExample tradeVolumnDailyExample = new TradeVolumnDailyExample();
						tradeVolumnDailyExample.or().andMemberIdEqualTo(tradeDetail.getMemberId()).andTradeDateEqualTo(tradeDate);
						List<TradeVolumnDaily> tradeVolumnDailyList = tradeVolumnDailyService.selectByExample(tradeVolumnDailyExample);
						TradeVolumnDaily tradeVolumnDaily = null;
						if(tradeVolumnDailyList.size() > 0){
							tradeVolumnDaily = tradeVolumnDailyList.get(0);
							tradeVolumnDaily.setTotalMoney(tradeVolumnDaily.getTotalMoney().add(tradeDetail.getMoney()));
							tradeVolumnDaily.setTradeCount(tradeVolumnDaily.getTradeCount() + 1);
							tradeVolumnDaily.setUpdateDate(new Date());
							tradeVolumnDailyService.updateByPrimaryKeySelective(tradeVolumnDaily);
						}else{
							tradeVolumnDaily = new TradeVolumnDaily();
							tradeVolumnDaily.setMemberId(tradeDetail.getMemberId());
							tradeVolumnDaily.setTotalMoney(tradeDetail.getMoney());
							tradeVolumnDaily.setTradeCount(1);
							tradeVolumnDaily.setTradeDate(tradeDate);
							tradeVolumnDaily.setCreateDate(new Date());
							tradeVolumnDaily.setUpdateDate(new Date());
							tradeVolumnDailyService.insertSelective(tradeVolumnDaily);
						}
						
						//发送微信模板消息
						System.out.println("模板消息getPoolSize====" + threadPoolTaskExecutor.getPoolSize());
						WeixinTemplateSendThread weixinTemplateSendThread = new WeixinTemplateSendThread(epayCodeService, memberOpenidService, sysOfficeConfigOemService, memberInfoService, sysOfficeService, debitNote, msResultNotice.getPayTime());
						threadPoolTaskExecutor.execute(weixinTemplateSendThread);
						System.out.println("模板消息getActiveCount====" + threadPoolTaskExecutor.getActiveCount());
					}
					
				}
			}
			
			
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}
		try {
			response.getWriter().write("000000");
		} catch (IOException e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}

	}
	
	
	
	
	
	/**
	 * app提现，微信 支付宝只提现一种
	 * @param request
	 * @param response
	 */
	@ResponseBody
	@RequestMapping("/api/debitNote/autoDraw")
	public String autoDraw(HttpServletRequest request,HttpServletResponse response) {
		JSONObject requestPRM = (JSONObject) request.getAttribute("requestPRM");
		JSONObject reqDataJson = requestPRM.getJSONObject("reqData");// 获取请求参数
		JSONObject errorResult = new JSONObject();
		Map<String,Object> result = new HashMap<String, Object>();
		if(!reqDataJson.containsKey("memberCode")){
			errorResult.put("returnCode", "4004");
			errorResult.put("returnMsg", "缺少用户编号");
			return errorResult.toString();
		}
		String memberCode = reqDataJson.getString("memberCode");
		String txnType = reqDataJson.getString("txnType");
		MemberInfoExample memberInfoExample = new MemberInfoExample();
		Criteria memberInfoCriteria = memberInfoExample.createCriteria();
		memberInfoCriteria.andCodeEqualTo(memberCode);
		List<MemberInfo> members = memberInfoService.selectByExample(memberInfoExample);
		MemberInfo memberInfo = null;
		BigDecimal memberTradeRate=new BigDecimal(0);
		if(members!=null && members.size()>0){
			memberInfo = members.get(0);
			
		}
		if(null == memberInfo){
			errorResult.put("returnCode", "4004");
			errorResult.put("returnMsg", "用户不存在");
			return errorResult.toString();
		}
		if (null != memberInfo && "1".equals(memberInfo.getDrawStatus())) {
			errorResult.put("returnCode", "4004");
			errorResult.put("returnMsg", "您当前的商户等级未开启D0提现权限，请联系客服提交材料获取权限。");
			return errorResult.toString();
		}
		Integer memberId = memberInfo.getId();
		memberTradeRate=memberInfo.getT0TradeRate();
		String ret="";
		if("1".equals(txnType)){
			ret = doDraw(memberId, memberInfo.getWxMemberCode(), memberInfo.getWxMerchantCode(),memberTradeRate);//微信提现
		}else if("2".equals(txnType)){
			ret = doDraw(memberId, memberInfo.getZfbMemberCode(), memberInfo.getZfbMerchantCode(),memberTradeRate);//支付宝提现
		}else if("3".equals(txnType)){
			ret = doDraw(memberId, memberInfo.getQqMemberCode(), memberInfo.getQqMerchantCode(),memberTradeRate);//qq钱包提现
		}else if("4".equals(txnType)){
			ret = doDraw(memberId, memberInfo.getBdMemberCode(), memberInfo.getBdMerchantCode(),memberTradeRate);//百度钱包提现
		}else if("5".equals(txnType)){
			ret = doDraw(memberId, memberInfo.getJdMemberCode(), memberInfo.getJdMerchantCode(),memberTradeRate);//京东金融提现
		}
		
		if("00".equals(ret)){
			result.put("returnCode", "0000");
		}else{
			result.put("returnCode", "4002");
		}
		result.put("returnMsg", ret);
		return JsonBeanReleaseUtil.beanToJson(result, "yyyy-MM-dd HH:mm:ss");
	}
	/**
	 * 手动调用提现，微信 支付宝一起提现。Test.java用
	 * @param request
	 * @param response
	 */
	@ResponseBody
	@RequestMapping("/api/debitNote/handleDraw")
	public String handleDraw(HttpServletRequest request,HttpServletResponse response) {
		JSONObject requestPRM = (JSONObject) request.getAttribute("requestPRM");
		JSONObject reqDataJson = requestPRM.getJSONObject("reqData");// 获取请求参数
		Map<String,Object> result = new HashMap<String, Object>();
		if(!reqDataJson.containsKey("memberId")){
			result.put("returnCode", "4004");
			result.put("returnMsg", "缺少用户ID信息");
			return result.toString();
		}
		Integer memberId = reqDataJson.getInt("memberId");
		String txnType = reqDataJson.getString("txnType");
		MemberInfo memberInfo = memberInfoService.selectByPrimaryKey(memberId);
		if(null == memberInfo){
			result.put("returnCode", "4004");
			result.put("returnMsg", "用户不存在");
			return result.toString();
		}
		String wxMerchantCode = memberInfo.getWxMerchantCode();
		String zfbMerchantCode = memberInfo.getZfbMerchantCode();
		String qqMerchantCode = memberInfo.getQqMerchantCode();
		String bdMerchantCode = memberInfo.getBdMerchantCode();
		String jdMerchantCode = memberInfo.getJdMerchantCode();
		String retWx = doDraw(memberId, memberInfo.getWxMemberCode(), wxMerchantCode,memberInfo.getT0TradeRate());//微信提现
		String retZfb = doDraw(memberId, memberInfo.getZfbMemberCode(), zfbMerchantCode,memberInfo.getT0TradeRate());//支付宝提现
		String retQq = doDraw(memberId, memberInfo.getQqMemberCode(), qqMerchantCode,memberInfo.getT0TradeRate());//qq提现
		String retBd = doDraw(memberId, memberInfo.getBdMemberCode(), bdMerchantCode,memberInfo.getT0TradeRate());//百度钱包提现
		String retJd = doDraw(memberId, memberInfo.getJdMemberCode(), jdMerchantCode,memberInfo.getT0TradeRate());//百度钱包提现
		
		String ret = "微信-" + retWx + "。 " + "支付宝-" + retZfb + "。 " + "QQ-" + retQq + "。 " + "百度钱包-" + retBd + "。 " + "京东金融-" + retJd;
		if("00".equals(retWx) && "00".equals(retZfb)){
			result.put("returnCode", "0000");
		}else{
			result.put("returnCode", "4002");
		}
		result.put("returnMsg", ret);
		return JsonBeanReleaseUtil.beanToJson(result, "yyyy-MM-dd HH:mm:ss");
	}
	
	public String doDraw(Integer memberId,String memberCode,String merchantCode,BigDecimal memberTradeRate){
		try {
			String orderCode =CommonUtil.getOrderCode();
			//创建提现明细
			RoutewayDraw routewayDraw=new RoutewayDraw();
			routewayDraw.setCreateDate(new Date());
			routewayDraw.setDelFlag("0");
			routewayDraw.setMemberCode(memberCode);
			routewayDraw.setMemberId(memberId);
			routewayDraw.setMemberTradeRate(memberTradeRate);
			routewayDraw.setMerchantCode(merchantCode);
			routewayDraw.setOrderCode(orderCode);
			routewayDraw.setPtSerialNo(orderCode);
			routewayDraw.setReqDate(DateUtil.getDateTimeStr(new Date()));
			routewayDrawService.insertSelective(routewayDraw);
			
			
			String callBack = SysConfig.serverUrl + "/debitNote/msWithdrawNotify";
			System.out.println("-------------通知地址------" + callBack + "------------");
			
			// 调用支付通道
			
			String serverUrl = MSConfig.msServerUrl;
			PublicKey yhPubKey = null;
			if (serverUrl.startsWith("https://ipay")) {
				yhPubKey = CryptoUtil.getRSAPublicKey(true);
			} else {
				yhPubKey = CryptoUtil.getRSAPublicKey(false);
			}
			PrivateKey hzfPriKey = CryptoUtil.getRSAPrivateKey();
			String tranCode = TranCodeConstant.SHTX;//"SMZF021";
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
			System.out.println(respJSONObject);
			
			routewayDraw.setRespCode(respJSONObject.getString("respCode"));
			routewayDraw.setRespDate(respJSONObject.getString("respDate"));
			routewayDraw.setRespMsg(respJSONObject.getString("respMsg"));
			routewayDraw.setRespType(respJSONObject.getString("respType"));
			routewayDrawService.updateByPrimaryKeySelective(routewayDraw);
			
			return respJSONObject.getString("respCode");
		}  catch (Exception e) {
			e.printStackTrace();
			logger.error("商户："+memberId+"提现失败"+e.getMessage());
			return "失败："+e.getMessage();
		}
	}
	
	@Deprecated
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
			System.out.println("-------------通知地址------" + callBack + "------------");

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
			System.out.println(respJSONObject);
			
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
	
	
	@RequestMapping("/debitNote/msWithdrawNotify")
	public void msWithdrawNotify(HttpServletRequest request,HttpServletResponse response) {
		try {
			String resEncryptData = request.getParameter("encryptData");

			String resEncryptKey = request.getParameter("encryptKey");
			String charset = "utf-8";
			PrivateKey hzfPriKey = CryptoUtil.getRSAPrivateKey();
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

			JSONObject resEntity = respJSONObject.getJSONObject("resEntity");
			String reqMsgId = resEntity.getString("reqMsgId");
			RoutewayDrawExample routewayDrawExample = new RoutewayDrawExample();
			routewayDrawExample.createCriteria().andPtSerialNoEqualTo(reqMsgId);
			List<RoutewayDraw> routewayDraws = routewayDrawService.selectByExample(routewayDrawExample);

			if (routewayDraws != null && routewayDraws.size() > 0) {
				RoutewayDraw routewayDraw=routewayDraws.get(0);
				routewayDraw.setRespCode(respJSONObject.getString("respCode"));
				routewayDraw.setRespDate(respJSONObject.getString("respDate"));
				routewayDraw.setRespMsg(respJSONObject.getString("respMsg"));
				routewayDraw.setRespType(respJSONObject.getString("respType"));
				routewayDraw.setDrawamount(new BigDecimal(resEntity.getString("drawAmount")));
				routewayDraw.setDrawfee(new BigDecimal(resEntity.getString("drawFee")));
				routewayDraw.setTradefee(new BigDecimal(resEntity.getString("tradeFee")));
				routewayDraw.setSettleDate(resEntity.getString("settleDate"));
				routewayDraw.setUpdateDate(new Date());
				routewayDrawService.updateByPrimaryKey(routewayDraw);
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error("提现通知处理失败:"+e.getMessage());
		}
		
		try {
			response.getWriter().write("000000");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


	}
	
	@ResponseBody
	@RequestMapping("/api/debitNote/msTransactionQuery")
	public JSONObject msTransactionQuery(HttpServletRequest request,HttpServletResponse response) {
		JSONObject requestPRM = (JSONObject) request.getAttribute("requestPRM");
		JSONObject reqDataJson = requestPRM.getJSONObject("reqData");// 获取请求参数
		JSONObject result = new JSONObject();
		JSONObject resData = new JSONObject();
		try {
			String reqMsgId=CommonUtil.getOrderCode();
			String orderCode = reqDataJson.getString("orderCode");
			// 调用支付通道

			DebitNoteExample debitNoteExample=new DebitNoteExample();
			debitNoteExample.createCriteria().andOrderCodeEqualTo(orderCode);
			List<DebitNote> debitNotes=debitNoteService.selectByExample(debitNoteExample);
			if(debitNotes==null||debitNotes.size()==0){
				throw new ArgException("支付单不存在!");
			}
			
			DebitNote debitNote=debitNotes.get(0);
			
			MemberInfo memberInfo = memberInfoService.selectByPrimaryKey(debitNote.getMemberId());
			String routeCode = debitNote.getRouteId();
			if(!RouteCodeConstant.ESK_ROUTE_CODE.equals(routeCode)){
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
				sBuilder.append("<oriReqMsgId>" + orderCode + "</oriReqMsgId>");
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
						result.put("resData", resEntity);
				}
			}else{
				
				// 调用支付通道
				String serverUrl = ESKConfig.msServerUrl;
			//	PrivateKey hzfPriKey = CryptoUtil.getRSAPrivateKey();
				String tranCode = "004";
				String charset = "utf-8";
				
				JSONObject reqData = new JSONObject();
				reqData.put("oriOrderNumber", orderCode);
				reqData.put("tranCode", tranCode);
				
				System.out.println("待加密数据: "+reqData);
				
				String plainXML = reqData.toString();
				byte[] plainBytes = plainXML.getBytes(charset);
				String keyStr = MSCommonUtil.generateLenString(16);
				;
				byte[] keyBytes = keyStr.getBytes(charset);
				String encryptData = new String(Base64.encodeBase64((CryptoUtil.AESEncrypt(plainBytes, keyBytes, "AES", "AES/ECB/PKCS5Padding", null))), charset);
				String signData = new String(Base64.encodeBase64(Key.rsaSign(plainBytes, ESKConfig.privateKey)), charset);
				String encrtptKey = new String(Base64.encodeBase64(Key.jdkRSA(keyBytes, ESKConfig.yhPublicKey)), charset);
				List<NameValuePair> nvps = new LinkedList<NameValuePair>();
				nvps.add(new BasicNameValuePair("Context", encryptData));
				nvps.add(new BasicNameValuePair("encrtpKey", encrtptKey));
				
				nvps.add(new BasicNameValuePair("signData", signData));
				nvps.add(new BasicNameValuePair("agentId", ESKConfig.agentId));
				byte[] b = HttpClient4Util.getInstance().doPost(serverUrl, null, nvps);
				String respStr = new String(b, charset);
				logger.info("返回报文[{}]", new Object[] { respStr });
				
				JSONObject jsonObject = JSONObject.fromObject(respStr);
				String resEncryptData = jsonObject.getString("Context");
				String resEncryptKey = jsonObject.getString("encrtpKey");
				byte[] decodeBase64KeyBytes = Base64.decodeBase64(resEncryptKey.getBytes(charset));
				// 解密encryptKey得到merchantAESKey
				//先用对方给的私钥调试 by linxf
				//byte[] merchantAESKeyBytes = CryptoUtil.RSADecrypt(decodeBase64KeyBytes, hzfPriKey, 2048, 11, "RSA/ECB/PKCS1Padding");
				byte[] merchantAESKeyBytes = Key.jdkRSA_(decodeBase64KeyBytes, ESKConfig.privateKey);
				// 使用base64解码商户请求报文
				byte[] decodeBase64DataBytes = Base64.decodeBase64(resEncryptData.getBytes(charset));
				// 用解密得到的merchantAESKey解密encryptData
				byte[] merchantXmlDataBytes = CryptoUtil.AESDecrypt(decodeBase64DataBytes, merchantAESKeyBytes, "AES", "AES/ECB/PKCS5Padding", null);
				String resXml = new String(merchantXmlDataBytes, charset);
				JSONObject respJSONObject = JSONObject.fromObject(resXml);
				logger.info("返回报文[{}]",  respJSONObject );
				
				
				JSONObject resEntity = new JSONObject();
				if("S".equals(respJSONObject.getString("respType"))&&"000000".equals(respJSONObject.getString("respCode"))){
				//	String merchantCode = respJSONObject.getString("merchantCode");
					resEntity.put("oriRespType", respJSONObject.getString("oriRespType"));
					resEntity.put("oriRespCode", respJSONObject.getString("oriRespCode"));
					if("S".equals(respJSONObject.getString("oriRespType"))&&"000000".equals(respJSONObject.getString("oriRespCode"))){
						resEntity.put("totalAmount", respJSONObject.getString("buyerPayAmount"));
					}
				}else{
					resEntity.put("oriRespType", "E");
				}
				result.put("resData", resEntity);
			}
			result.put("certNbr", memberInfo.getCertNbr());
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
	@ResponseBody
	@RequestMapping("/api/debitNote/msDrawQuery")
	public JSONObject msDrawQuery(HttpServletRequest request,HttpServletResponse response) {
		JSONObject requestPRM = (JSONObject) request.getAttribute("requestPRM");
		JSONObject reqDataJson = requestPRM.getJSONObject("reqData");// 获取请求参数
		JSONObject result = new JSONObject();
		JSONObject resData = new JSONObject();
		try {
			String reqMsgId=CommonUtil.getOrderCode();
			String orderCode = reqDataJson.getString("orderCode");
			// 调用支付通道
			
			RoutewayDrawExample routewayDrawExample = new RoutewayDrawExample();
			routewayDrawExample.createCriteria().andOrderCodeEqualTo(orderCode);
			List<RoutewayDraw> routeways = routewayDrawService.selectByExample(routewayDrawExample);
			if(routeways==null||routeways.size()==0){
				throw new ArgException("提现记录不存在!");
			}
			
			RoutewayDraw routeway=routeways.get(0);
			
			MemberInfo memberInfo = memberInfoService.selectByPrimaryKey(routeway.getMemberId());
			
			String serverUrl = MSConfig.msServerUrl;
			PublicKey yhPubKey = null;
			if (serverUrl.startsWith("https://ipay")) {
				yhPubKey = CryptoUtil.getRSAPublicKey(true);
			} else {
				yhPubKey = CryptoUtil.getRSAPublicKey(false);
			}
			PrivateKey hzfPriKey = CryptoUtil.getRSAPrivateKey();
			String tranCode = TranCodeConstant.TXJJCX;//"SMZF022";
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
			sBuilder.append("<oriReqMsgId>" + orderCode + "</oriReqMsgId>");
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
			
			if("0".equals(memberInfo.getSettleType())){
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
				result.put("resData", resEntity);
			}
//			result.put("certNbr", memberInfo.getCertNbr());
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
	
	
	
	@RequestMapping("/debitNote/getPayBill")
	public void getPayBill(HttpServletRequest request,HttpServletResponse response) {
		msbillService.getMsPayBillsBySettleDate(request.getParameter("settleDate"));
	}
	@RequestMapping("/debitNote/getWithdrawbill")
	public void getWithdrawbill(HttpServletRequest request,HttpServletResponse response) {
		msbillService.getMsWithdrawBillsT1BySettleDate(request.getParameter("settleDate"));
		msbillService.getMsWithdrawBillsT0BySettleDate(request.getParameter("settleDate"));
	}
	
	/**
	 * 用于测试退款功能  测试环境
	 * @param reqDataJson
	 * @param request
	 * @return
	 */
//	@RequestMapping("/debitTest/testDrawbackMoney")
	public JSONObject msDrawbackMoney() {
		JSONObject result = new JSONObject();
		JSONObject resData = new JSONObject();
		try {
			String money = "0.01";
			String epayCode = "E000000888";
			String callBackOrderCode = "20170328232442158457";
			String orderCode = CommonUtil.getOrderCode();
			EpayCodeExample epayCodeExample = new EpayCodeExample();
			epayCodeExample.createCriteria().andPayCodeEqualTo(epayCode).andDelFlagEqualTo("0");
			List<EpayCode> epayCodes = epayCodeService.selectByExample(epayCodeExample);
			if (epayCodes == null || epayCodes.size() == 0) {
				throw new ArgException("收款码不存在");
			}
			
			EpayCode payCode = epayCodes.get(0);
			if(!"5".equals(payCode.getStatus()) && !"7".equals(payCode.getStatus())){
				throw new ArgException("对不起,收款码不可用，如有疑问请与管理员联系");
			}
			
			MemberInfo memberInfo = memberInfoService.selectByPrimaryKey(payCode.getMemberId());
			String callBack = SysConfig.serverUrl + "/debitNote/msPayNotify";
			// 调用支付通道
			String serverUrl = MSConfig.msServerUrl;
			PublicKey yhPubKey = null;
			if (serverUrl.startsWith("https://ipay")) {
				yhPubKey = CryptoUtil.getRSAPublicKey(true);
			} else {
				yhPubKey = CryptoUtil.getRSAPublicKey(false);
			}
			PrivateKey hzfPriKey = CryptoUtil.getRSAPrivateKey();
			String tranCode = "SMZF004";
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
			sBuilder.append("<oriReqMsgId>"+callBackOrderCode+"</oriReqMsgId>");
			sBuilder.append("<refundAmount>" + money + "</refundAmount>");
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
			
			if("0".equals(memberInfo.getSettleType())){
				nvps.add(new BasicNameValuePair("cooperator", MSConfig.cooperator_t0));
			}else{
				nvps.add(new BasicNameValuePair("cooperator", MSConfig.cooperator_t1));
			}
			
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
			System.out.println(respJSONObject);
			resData.put("orderCode", orderCode);
			if (respJSONObject.containsKey("resEntity")) {
				JSONObject resEntity = respJSONObject.getJSONObject("resEntity");
				String resDataStr = resEntity.toString();
				result.put("resData", resEntity.toString());
				System.out.println(">>>>>>>>>>>"+resDataStr);
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
	/**
	 * 商户T1资金清算查询
	 */
	@ResponseBody
	@RequestMapping(value="/api/debitNote/getMsT1FundSettle")
	public JSONObject getMsT1FundSettle(HttpServletRequest request,HttpServletResponse response) {
		JSONObject requestPRM = (JSONObject) request.getAttribute("requestPRM");
		JSONObject reqDataJson = requestPRM.getJSONObject("reqData");// 获取请求参数
		JSONObject result = new JSONObject();
		String memberID = reqDataJson.getString("memberID");
		if(StringUtils.isEmpty(memberID)){
			result.put("returnCode", "4002");
			result.put("returnMsg", "缺少memberId");
			return result;
		}
		if(StringUtils.isEmpty(memberID)){
			result.put("returnCode", "4002");
			result.put("returnMsg", "缺少对账日期");
			return result;
		}
		String settleDate = reqDataJson.getString("settleDate");
		MemberInfo memberInfo = memberInfoService.selectByPrimaryKey(Integer.parseInt(memberID));
		String wxMemberCode = memberInfo.getWxMemberCode();
		String zfbMemberCode = memberInfo.getZfbMemberCode();
		String qqMemberCode = memberInfo.getQqMemberCode();
		String bdMemberCode = memberInfo.getBdMemberCode();
		String jdMemberCode = memberInfo.getJdMemberCode();
		
		if(StringUtils.isNotEmpty(wxMemberCode)){
			msbillService.getMsT1FundSettle(wxMemberCode, settleDate);
		}
		if(StringUtils.isNotEmpty(zfbMemberCode)){
			msbillService.getMsT1FundSettle(zfbMemberCode, settleDate);
		}
		if(StringUtils.isNotEmpty(qqMemberCode)){
			msbillService.getMsT1FundSettle(qqMemberCode, settleDate);
		}
		if(StringUtils.isNotEmpty(bdMemberCode)){
			msbillService.getMsT1FundSettle(bdMemberCode, settleDate);
		}
		if(StringUtils.isNotEmpty(jdMemberCode)){
			msbillService.getMsT1FundSettle(jdMemberCode, settleDate);
		}
		result.put("returnCode", "0000");
		result.put("returnMsg", "成功");
		return result;
	}
	
	/**
	 * 民生QQ钱包支付  暂未开通，不使用
	 * 
	 * @param request
	 * @return
	 */

	public JSONObject msQqPay(JSONObject reqDataJson, HttpServletRequest request) {
		JSONObject result = new JSONObject();
		JSONObject resData = new JSONObject();
		try {
			String money = reqDataJson.getString("money");
			String epayCode = reqDataJson.getString("epayCode");
//			 String userId = "2088502362301099";
			String userId = reqDataJson.getString("userId");
			if ("".equals(money)) {
				throw new ArgException("请输入正确的金额");
			}

			if ("".equals(epayCode)) {
				throw new ArgException("收款码出错");
			}

			EpayCodeExample epayCodeExample = new EpayCodeExample();
			epayCodeExample.createCriteria().andPayCodeEqualTo(epayCode).andDelFlagEqualTo("0");
			List<EpayCode> epayCodes = epayCodeService.selectByExample(epayCodeExample);
			if (epayCodes == null || epayCodes.size() == 0) {
				throw new ArgException("收款码不存在");
			}

			EpayCode payCode = epayCodes.get(0);
			if(!"5".equals(payCode.getStatus()) && !"7".equals(payCode.getStatus())){
				throw new ArgException("对不起,收款码不可用，如有疑问请与管理员联系");
			}

			MemberInfo memberInfo = memberInfoService.selectByPrimaryKey(payCode.getMemberId());

			if (memberInfo == null) {
				throw new ArgException("商家不存在");
			}
			if(!"0".equals(memberInfo.getDelFlag())){
				throw new ArgException("商家不存在");
			}
			if(!"0".equals(memberInfo.getStatus())){
				throw new ArgException("商家停用，暂不可交易");
			}
			AccountExample accountExample = new AccountExample();
			accountExample.createCriteria().andMemberIdEqualTo(memberInfo.getId()).andDelFlagEqualTo("0");
			List<Account> accounts = accountService.selectByExample(accountExample);
			if (accounts == null || accounts.size() != 1) {
				throw new ArgException("会员账户不存在");
			}

			// 插入一条收款记录
			String orderCode = CommonUtil.getOrderCode();
			DebitNote debitNote = new DebitNote();
			debitNote.setCreateDate(new Date());
			debitNote.setMemberId(memberInfo.getId());
			debitNote.setMoney(new BigDecimal(money));
			debitNote.setOrderCode(orderCode);
			debitNote.setRouteId(memberInfo.getZfbRouteId());
			debitNote.setStatus("0");
			debitNote.setTxnType("3");
			debitNote.setMemberCode(memberInfo.getQqMemberCode());
			debitNote.setMerchantCode(memberInfo.getQqMerchantCode());
			debitNote.setSettleType(memberInfo.getSettleType());
			debitNoteService.insertSelective(debitNote);

			// String callBack = "http://" + request.getServerName() + ":" +
			// request.getServerPort() + request.getContextPath()+
			// "/debitNote/msNotify";
			String callBack = SysConfig.serverUrl + "/debitNote/msPayNotify";
			// 调用支付通道
			String serverUrl = MSConfig.msServerUrl;
			PublicKey yhPubKey = null;
			if (serverUrl.startsWith("https://ipay")) {
				yhPubKey = CryptoUtil.getRSAPublicKey(true);
			} else {
				yhPubKey = CryptoUtil.getRSAPublicKey(false);
			}
			PrivateKey hzfPriKey = CryptoUtil.getRSAPrivateKey();
			String tranCode = "SMZF010";
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
			sBuilder.append("<merchantCode>" + memberInfo.getQqMerchantCode() + "</merchantCode>");
			sBuilder.append("<totalAmount>" + money + "</totalAmount>");
			sBuilder.append("<subject>" + memberInfo.getName() + " 收款" + "</subject>");
			//sBuilder.append("<userId>" + userId + "</userId>");
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
			/*
			if("0".equals(memberInfo.getSettleType())){
				nvps.add(new BasicNameValuePair("cooperator", MSConfig.cooperator_t0));
			}else{
				nvps.add(new BasicNameValuePair("cooperator", MSConfig.cooperator_t1));
			}
			*/
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
			System.out.println(respJSONObject);
			resData.put("orderCode", orderCode);
			if (respJSONObject.containsKey("resEntity")) {
				JSONObject resEntity = respJSONObject.getJSONObject("resEntity");
//				if (resEntity.containsKey("channelNo")) {
//					resData.put("channelNo", resEntity.get("channelNo"));
//					result.put("resData", resData);
//				}
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
	
	private SysOffice getTopAgentOffice(String officeId){
		SysOfficeExample sysOfficeExample = new SysOfficeExample();
		sysOfficeExample.or().andIdEqualTo(officeId);
		List<SysOffice> sysOfficeList = sysOfficeService.selectByExample(sysOfficeExample);
		if(sysOfficeList.size()>0){
			SysOffice sysOffice = sysOfficeList.get(0);
			if("1".equals(sysOffice.getAgtGrade())){//返回顶级即1级代理商的信息
				return sysOffice;
			}else{
				return getTopAgentOffice(sysOffice.getParentId());
			}
		}
		return null;
	}
	
public JSONObject testRegisterMsAccount(String payWay ,String bankType ,String bankName) {
		
		JSONObject result = new JSONObject();
		String reqMsgId=CommonUtil.getOrderCode();
		BusinessCategory  businessCategory = businessCategoryService.selectByPrimaryKey(1);
		MemberInfo memberInfo = memberInfoService.selectByPrimaryKey(1046);
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
			String tranCode = TranCodeConstant.SHRZ;
			String charset = "utf-8";
			String merchantId = "";
			
			String categoryVal = "";
			if(MSPayWayConstant.WXPAY.equals(payWay)){
				merchantId = memberInfo.getWxMemberCode();
				categoryVal = businessCategory.getWxCategoryId();
			}else if(MSPayWayConstant.ZFBZF.equals(payWay)){
				merchantId = memberInfo.getZfbMemberCode();
				categoryVal = businessCategory.getZfbCategoryId();
			}else if(MSPayWayConstant.QQZF.equals(payWay)){
				merchantId = memberInfo.getQqMemberCode();
				categoryVal = businessCategory.getQqCategoryId();
			}else if(MSPayWayConstant.BDZF.equals(payWay)){
				merchantId = memberInfo.getBdMemberCode();
				categoryVal = businessCategory.getBdCategoryId();
			}else if(MSPayWayConstant.JDZF.equals(payWay)){
				merchantId = memberInfo.getJdMemberCode();
				categoryVal = businessCategory.getJdCategoryId();
			}
			
			String callBack = SysConfig.serverUrl + "/api/registerLogin/msExamNotify";
			System.out.println("-------------注册审核结果通知地址------" + callBack + "------------");
			
			StringBuilder sBuilder = new StringBuilder();
			sBuilder.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
			sBuilder.append("<merchant>");
			sBuilder.append("<head>");
			sBuilder.append("<version>1.0.0</version>");
			sBuilder.append("<msgType>01</msgType>");
			sBuilder.append("<reqDate>" + DateFormatUtils.format(new Date(), "yyyyMMddHHmmss") + "</reqDate>");
			sBuilder.append("</head>");
			sBuilder.append("<body>");
			sBuilder.append("<payWay>" + payWay + "</payWay>");
			sBuilder.append("<merchantId>" + merchantId + "</merchantId>");
			sBuilder.append("<merchantName>" + memberInfo.getName() + "</merchantName>");
			sBuilder.append("<shortName>" + memberInfo.getShortName() + "</shortName>");
			sBuilder.append("<merchantAddress>" + memberInfo.getAddr() + "</merchantAddress>");
			sBuilder.append("<servicePhone>" + memberInfo.getMobilePhone() + "</servicePhone>");
			sBuilder.append("<accNo>" + memberInfo.getCardNbr() + "</accNo>");
			sBuilder.append("<accName>" + memberInfo.getContact() + "</accName>");
			sBuilder.append("<category>" + categoryVal + "</category>");
			sBuilder.append("<idCard>" + memberInfo.getCertNbr() + "</idCard>");
			//if("0".equals(memberInfo.getSettleType())){
			sBuilder.append("<t0drawFee>" + memberInfo.getT0DrawFee() + "</t0drawFee>");
			sBuilder.append("<t0tradeRate>" + memberInfo.getT0TradeRate() + "</t0tradeRate>");
			//}
			sBuilder.append("<t1drawFee>" + memberInfo.getT1DrawFee() + "</t1drawFee>");
			sBuilder.append("<t1tradeRate>" + memberInfo.getT1TradeRate() + "</t1tradeRate>");
			sBuilder.append("<bankType>" + bankType + "</bankType>");
			sBuilder.append("<bankName>" + bankName + "</bankName>");
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
//				nvps.add(new BasicNameValuePair("cooperator", "SMZF_FZRF_HD_T0"));
			}else{
				nvps.add(new BasicNameValuePair("cooperator", MSConfig.cooperator_t1));
//				nvps.add(new BasicNameValuePair("cooperator", "SMZF_FZRF_HD_T1"));
			}
			
			nvps.add(new BasicNameValuePair("signData", signData));
			nvps.add(new BasicNameValuePair("tranCode", tranCode));
			nvps.add(new BasicNameValuePair("reqMsgId",reqMsgId ));
			nvps.add(new BasicNameValuePair("callBack", callBack));
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
			logger.info(payWay+"返回报文[{}]", new Object[] { resXml });
			JSONObject respJSONObject = XmlConvertUtil.documentToJSONObjectFilter(resXml);
			if("000000".equals(respJSONObject.getString("respCode"))){
				JSONObject resEntity = respJSONObject.getJSONObject("resEntity");
				String merchantCode = resEntity.getString("merchantCode");
				result.put("returnCode", "0000");
				result.put("merchantCode", merchantCode);
				result.put("returnMsg", "成功");
			}else if("200012".equals(respJSONObject.getString("respCode"))){
				result.put("returnCode", "200012");
				result.put("returnMsg", respJSONObject.getString("respMsg"));
			}else{
				result.put("returnCode", "4004");
				result.put("returnMsg", respJSONObject.getString("respMsg"));
			}
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
	 * 若singleLimit或dayLimit未设置值，则认为未设置限额，即不对该项进行校验 
	 * @param memberId
	 * @param tradeMoney
	 * @param singleLimit
	 * @param dayLimit
	 * @return
	 */
	private JSONObject checkLimitMoney(String configName, BigDecimal tradeMoney){
		
		JSONObject result = new JSONObject();
		
		String value = "";
		SysCommonConfigExample sysCommonConfigExample = new SysCommonConfigExample();
		sysCommonConfigExample.or().andNameEqualTo(configName).andDelFlagEqualTo("0");
		List<SysCommonConfig> sysCommonConfig = sysCommonConfigService.selectByExample(sysCommonConfigExample);
		if (sysCommonConfig != null && sysCommonConfig.size() > 0) {
			value = sysCommonConfig.get(0).getValue();
		}
		if(!"".equals(value)){
			BigDecimal singleLimit = new BigDecimal(value);
			if (null != singleLimit && singleLimit.compareTo(BigDecimal.ZERO) > 0){
				if (tradeMoney.compareTo(singleLimit) > 0) {
					result.put("returnCode", "4004");
					result.put("returnMsg", "单笔交易限额为"+singleLimit+"元,当前交易已超出限额");
					return result;
				}
			}
		}
		return null;
	}
	
	private JSONObject checkPayLimit(Integer memberId, BigDecimal tradeMoney, BigDecimal singleLimit, BigDecimal dayLimit){
		
		JSONObject result = new JSONObject();
		if (null != singleLimit && singleLimit.compareTo(BigDecimal.ZERO) > 0){
			if (tradeMoney.compareTo(singleLimit) > 0) {
				result.put("returnCode", "4004");
				result.put("returnMsg", "您的单笔交易限额为"+singleLimit+"元,当前交易已超出限额,请重新输入");
				return result;
			}
		}
		
		if (null != dayLimit && dayLimit.compareTo(BigDecimal.ZERO) > 0){
			String tradeDate = DateUtil.getDateFormat(new Date(), "yyyyMMdd");
			TradeVolumnDailyExample tradeVolumnDailyExample = new TradeVolumnDailyExample();
			tradeVolumnDailyExample.or().andMemberIdEqualTo(memberId).andTradeDateEqualTo(tradeDate);
			List<TradeVolumnDaily> tradeVolumnDailyList = tradeVolumnDailyService.selectByExample(tradeVolumnDailyExample);
			if(tradeVolumnDailyList.size() > 0){
				TradeVolumnDaily tradeVolumnDaily = tradeVolumnDailyList.get(0);
				if (dayLimit.compareTo(tradeMoney.add(tradeVolumnDaily.getTotalMoney())) < 0) {
					double remindTradeMoney = dayLimit.subtract(tradeVolumnDaily.getTotalMoney()).doubleValue();
					result.put("returnCode", "4004");
					if(remindTradeMoney > 0){
						result.put("returnMsg", "您的日交易限额仅剩" + remindTradeMoney + "元，当前交易已超出限额");
					}else{
						result.put("returnMsg", "抱歉,您本日的交易额度已用完,请联系客服提高额度");
					}
					return result;
				}
			}else{
				if (tradeMoney.compareTo(dayLimit) > 0) {
					result.put("returnCode", "4004");
					result.put("returnMsg", "您的日交易限额为" + dayLimit + "元，当前交易已超出限额");
					return result;
				}
			}
		}
		
		return null;
	}
	
	
	
	/**
	 * 易收款商户入驻审核结果异步通知
	 * @param request
	 * @param response
	 */
	@RequestMapping("/registerLogin/eskExamNotify")
	public void eskExamNotify(HttpServletRequest request,HttpServletResponse response) {
		try {
			String resEncryptData = request.getParameter("Context");
			
			String resEncryptKey = request.getParameter("encrtpKey");
			logger.info("eskExamNotify回调返回报文resEncryptData{}，resEncryptKey{}",  resEncryptData, resEncryptKey);
			String charset = "utf-8";
			byte[] decodeBase64KeyBytes = Base64.decodeBase64(resEncryptKey.getBytes(charset));
			// 解密encryptKey得到merchantAESKey  屏蔽by linxf 测试
			//byte[] merchantAESKeyBytes = CryptoUtil.RSADecrypt(decodeBase64KeyBytes, hzfPriKey, 2048, 11, "RSA/ECB/PKCS1Padding");
			// 使用base64解码商户请求报文
			byte[] merchantAESKeyBytes = Key.jdkRSA_(decodeBase64KeyBytes, ESKConfig.privateKey);
			byte[] decodeBase64DataBytes = Base64.decodeBase64(resEncryptData.getBytes(charset));
			// 用解密得到的merchantAESKey解密encryptData
			byte[] merchantXmlDataBytes = CryptoUtil.AESDecrypt(decodeBase64DataBytes, merchantAESKeyBytes, "AES", "AES/ECB/PKCS5Padding", null);
			String resXml = new String(merchantXmlDataBytes, charset);
			JSONObject respJSONObject = JSONObject.fromObject(resXml);
			logger.info("eskExamNotify解密回调返回报文[{}]",  respJSONObject );
			
			String orderNumber = "";
			String merchantCode = "";
			
			if(respJSONObject.containsKey("orderNumber")){
				orderNumber = respJSONObject.getString("orderNumber");
			}
			
			if(respJSONObject.containsKey("merchantCode")){
				merchantCode = respJSONObject.getString("merchantCode");
			}

			EskNotice notice = new EskNotice();
			notice.setNoticeData(resXml);
			notice.setOrderNumber(orderNumber);
			eskNoticeService.insertNotice(notice);
			
			MemberInfoExample memberInfoExample = new MemberInfoExample();
			Criteria memberInfoCriteria = memberInfoExample.createCriteria();
			memberInfoCriteria.andOrderNoEqualTo(orderNumber);
			
			if("S".equals(respJSONObject.getString("respType"))&&"000000".equals(respJSONObject.getString("respCode"))){
				MemberInfo member = new MemberInfo();
				member.setWxMerchantCode(merchantCode);
				member.setZfbMerchantCode(merchantCode);
				member.setWxStatus("1");
				member.setZfbStatus("1");
				member.setStatus("0");
				memberInfoService.updateByExampleSelective(member, memberInfoExample);
				configSubAccount(merchantCode);
			}
			
			

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("注册审核结果通知处理失败:"+e.getMessage());
		}
		
		try {
			response.getWriter().write("000000");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * 易收款异步通知
	 * @param request
	 * @param response
	 */
	@RequestMapping("/eskCommonNotify")
	public void eskCommonNotify(HttpServletRequest request,HttpServletResponse response) {
		try {
			String resEncryptData = request.getParameter("Context");
			
			String resEncryptKey = request.getParameter("encrtpKey");
			logger.info("eskCommonNotify回调返回报文resEncryptData{}，resEncryptKey{}",  resEncryptData, resEncryptKey);
			String charset = "utf-8";
			byte[] decodeBase64KeyBytes = Base64.decodeBase64(resEncryptKey.getBytes(charset));
			// 解密encryptKey得到merchantAESKey  屏蔽by linxf 测试
			//byte[] merchantAESKeyBytes = CryptoUtil.RSADecrypt(decodeBase64KeyBytes, hzfPriKey, 2048, 11, "RSA/ECB/PKCS1Padding");
			// 使用base64解码商户请求报文
			byte[] merchantAESKeyBytes = Key.jdkRSA_(decodeBase64KeyBytes, ESKConfig.privateKey);
			byte[] decodeBase64DataBytes = Base64.decodeBase64(resEncryptData.getBytes(charset));
			// 用解密得到的merchantAESKey解密encryptData
			byte[] merchantXmlDataBytes = CryptoUtil.AESDecrypt(decodeBase64DataBytes, merchantAESKeyBytes, "AES", "AES/ECB/PKCS5Padding", null);
			String resXml = new String(merchantXmlDataBytes, charset);
			JSONObject respJSONObject = JSONObject.fromObject(resXml);
			logger.info("eskCommonNotify解密回调返回报文[{}]",  respJSONObject );
			
			/*String orderNumber = "";
			String merchantCode = "";
			
			if(respJSONObject.containsKey("orderNumber")){
				orderNumber = respJSONObject.getString("orderNumber");
			}
			
			if(respJSONObject.containsKey("merchantCode")){
				merchantCode = respJSONObject.getString("merchantCode");
			}

			EskNotice notice = new EskNotice();
			notice.setNoticeData(resXml);
			notice.setOrderNumber(orderNumber);
			eskNoticeService.insertNotice(notice);
			
			MemberInfoExample memberInfoExample = new MemberInfoExample();
			Criteria memberInfoCriteria = memberInfoExample.createCriteria();
			memberInfoCriteria.andOrderNoEqualTo(orderNumber);
			
			if("S".equals(respJSONObject.getString("respType"))&&"000000".equals(respJSONObject.getString("respCode"))){
				MemberInfo member = new MemberInfo();
				member.setWxMerchantCode(merchantCode);
				member.setZfbMerchantCode(merchantCode);
				member.setWxStatus("1");
				member.setZfbStatus("1");
				member.setStatus("0");
				memberInfoService.updateByExampleSelective(member, memberInfoExample);
			}
			*/
			

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("注册审核结果通知处理失败:"+e.getMessage());
		}
		
		try {
			response.getWriter().write("000000");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@ResponseBody
	@RequestMapping("/api/orderQuery")
	public JSONObject orderQuery(HttpServletRequest request,HttpServletResponse response) {
		
		JSONObject requestPRM = (JSONObject) request.getAttribute("requestPRM");
		JSONObject reqDataJson = requestPRM.getJSONObject("reqData");// 获取请求参数
		JSONObject result = new JSONObject();
		JSONObject resData = new JSONObject();
		try {
			String reqMsgId=CommonUtil.getOrderCode();
			String orderCode = reqDataJson.getString("orderCode");
			// 调用支付通道
			String serverUrl = ESKConfig.msServerUrl;
		//	PrivateKey hzfPriKey = CryptoUtil.getRSAPrivateKey();
			String tranCode = "004";
			String charset = "utf-8";
			
			
			JSONObject reqData = new JSONObject();
			reqData.put("oriOrderNumber", orderCode);
			reqData.put("tranCode", tranCode);
			
			System.out.println("待加密数据: "+reqData);
			
			String plainXML = reqData.toString();
			byte[] plainBytes = plainXML.getBytes(charset);
			String keyStr = MSCommonUtil.generateLenString(16);
			;
			byte[] keyBytes = keyStr.getBytes(charset);
			String encryptData = new String(Base64.encodeBase64((CryptoUtil.AESEncrypt(plainBytes, keyBytes, "AES", "AES/ECB/PKCS5Padding", null))), charset);
			//先用对方给的私钥调试 by linxf
			//String signData = new String(Base64.encodeBase64(CryptoUtil.digitalSign(plainBytes, hzfPriKey, "SHA1WithRSA")), charset);
			String signData = new String(Base64.encodeBase64(Key.rsaSign(plainBytes, ESKConfig.privateKey)), charset);
			String encrtptKey = new String(Base64.encodeBase64(Key.jdkRSA(keyBytes, ESKConfig.yhPublicKey)), charset);
			List<NameValuePair> nvps = new LinkedList<NameValuePair>();
			nvps.add(new BasicNameValuePair("Context", encryptData));
			nvps.add(new BasicNameValuePair("encrtpKey", encrtptKey));
			
			nvps.add(new BasicNameValuePair("signData", signData));
			nvps.add(new BasicNameValuePair("agentId", ESKConfig.agentId));
			byte[] b = HttpClient4Util.getInstance().doPost(serverUrl, null, nvps);
			String respStr = new String(b, charset);
			logger.info("返回报文[{}]", new Object[] { respStr });
			
			JSONObject jsonObject = JSONObject.fromObject(respStr);
			String resEncryptData = jsonObject.getString("Context");
			String resEncryptKey = jsonObject.getString("encrtpKey");
			byte[] decodeBase64KeyBytes = Base64.decodeBase64(resEncryptKey.getBytes(charset));
			// 解密encryptKey得到merchantAESKey
			//先用对方给的私钥调试 by linxf
			//byte[] merchantAESKeyBytes = CryptoUtil.RSADecrypt(decodeBase64KeyBytes, hzfPriKey, 2048, 11, "RSA/ECB/PKCS1Padding");
			byte[] merchantAESKeyBytes = Key.jdkRSA_(decodeBase64KeyBytes, ESKConfig.privateKey);
			// 使用base64解码商户请求报文
			byte[] decodeBase64DataBytes = Base64.decodeBase64(resEncryptData.getBytes(charset));
			// 用解密得到的merchantAESKey解密encryptData
			byte[] merchantXmlDataBytes = CryptoUtil.AESDecrypt(decodeBase64DataBytes, merchantAESKeyBytes, "AES", "AES/ECB/PKCS5Padding", null);
			String resXml = new String(merchantXmlDataBytes, charset);
			JSONObject respJSONObject = JSONObject.fromObject(resXml);
			logger.info("返回报文[{}]",  respJSONObject );
			
			if("000000".equals(respJSONObject.getString("respCode"))){
			//	String merchantCode = respJSONObject.getString("merchantCode");
				
				result.put("returnCode", "0000");
				result.put("orderNumber", orderCode);
			//	result.put("merchantCode", merchantCode);
				
				result.put("returnMsg", "成功");
			}else{
				result.put("returnCode", "4004");
				result.put("returnMsg", respJSONObject.getString("respMsg"));
			}
		} catch (ArgException e) {
			result.put("returnCode", "4004");
			result.put("returnMsg", "子商户号配置失败");
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
	
	
	
	//@ResponseBody
	//@RequestMapping("/configSubAccount")
	public JSONObject configSubAccount(String merchantCode) {
			
		JSONObject result = new JSONObject();
		//String reqMsgId=CommonUtil.getOrderCode();
		try {
			// 调用支付通道
			String serverUrl = ESKConfig.msServerUrl;
		//	PrivateKey hzfPriKey = CryptoUtil.getRSAPrivateKey();
			String tranCode = "006";
			String charset = "utf-8";
			
			
			String orderCode = CommonUtil.getOrderCode();
			
			JSONObject reqData = new JSONObject();
			reqData.put("orderNumber", orderCode);
			reqData.put("tranCode", tranCode);
			reqData.put("merchantCode", merchantCode);
			reqData.put("zdlx", "1");
			reqData.put("zdsj", ESKConfig.jsDomain);
			
			System.out.println("待加密数据: "+reqData);
			
			String plainXML = reqData.toString();
			byte[] plainBytes = plainXML.getBytes(charset);
			String keyStr = MSCommonUtil.generateLenString(16);
			;
			byte[] keyBytes = keyStr.getBytes(charset);
			String encryptData = new String(Base64.encodeBase64((CryptoUtil.AESEncrypt(plainBytes, keyBytes, "AES", "AES/ECB/PKCS5Padding", null))), charset);
			//先用对方给的私钥调试 by linxf
			//String signData = new String(Base64.encodeBase64(CryptoUtil.digitalSign(plainBytes, hzfPriKey, "SHA1WithRSA")), charset);
			String signData = new String(Base64.encodeBase64(Key.rsaSign(plainBytes, ESKConfig.privateKey)), charset);
			String encrtptKey = new String(Base64.encodeBase64(Key.jdkRSA(keyBytes, ESKConfig.yhPublicKey)), charset);
			List<NameValuePair> nvps = new LinkedList<NameValuePair>();
			nvps.add(new BasicNameValuePair("Context", encryptData));
			nvps.add(new BasicNameValuePair("encrtpKey", encrtptKey));
			
			nvps.add(new BasicNameValuePair("signData", signData));
			nvps.add(new BasicNameValuePair("agentId", ESKConfig.agentId));
			byte[] b = HttpClient4Util.getInstance().doPost(serverUrl, null, nvps);
			String respStr = new String(b, charset);
			logger.info("返回报文[{}]", new Object[] { respStr });
			
			JSONObject jsonObject = JSONObject.fromObject(respStr);
			String resEncryptData = jsonObject.getString("Context");
			String resEncryptKey = jsonObject.getString("encrtpKey");
			byte[] decodeBase64KeyBytes = Base64.decodeBase64(resEncryptKey.getBytes(charset));
			// 解密encryptKey得到merchantAESKey
			//先用对方给的私钥调试 by linxf
			//byte[] merchantAESKeyBytes = CryptoUtil.RSADecrypt(decodeBase64KeyBytes, hzfPriKey, 2048, 11, "RSA/ECB/PKCS1Padding");
			byte[] merchantAESKeyBytes = Key.jdkRSA_(decodeBase64KeyBytes, ESKConfig.privateKey);
			// 使用base64解码商户请求报文
			byte[] decodeBase64DataBytes = Base64.decodeBase64(resEncryptData.getBytes(charset));
			// 用解密得到的merchantAESKey解密encryptData
			byte[] merchantXmlDataBytes = CryptoUtil.AESDecrypt(decodeBase64DataBytes, merchantAESKeyBytes, "AES", "AES/ECB/PKCS5Padding", null);
			String resXml = new String(merchantXmlDataBytes, charset);
			JSONObject respJSONObject = JSONObject.fromObject(resXml);
			logger.info("返回报文[{}]",  respJSONObject );
			
			if("000000".equals(respJSONObject.getString("respCode"))){
			//	String merchantCode = respJSONObject.getString("merchantCode");
				
				result.put("returnCode", "0000");
				result.put("orderNumber", orderCode);
			//	result.put("merchantCode", merchantCode);
				
				result.put("returnMsg", "成功");
			}else{
				result.put("returnCode", "4004");
				result.put("returnMsg", respJSONObject.getString("respMsg"));
			}
		} catch (ArgException e) {
			result.put("returnCode", "4004");
			result.put("returnMsg", "子商户号配置失败");
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
	@RequestMapping("/api/debitNote/wxH5Pay")
	public JSONObject wxH5Pay(HttpServletRequest request,HttpServletResponse response){
		JSONObject requestPRM = (JSONObject) request.getAttribute("requestPRM");
		JSONObject reqDataJson = requestPRM.getJSONObject("reqData");// 获取请求参数
		System.out.println("请求参数==="+reqDataJson.toString());
		String payMoney = reqDataJson.getString("payMoney");
		String orderNum = reqDataJson.getString("orderNum");
		String memberCode = reqDataJson.getString("memberCode");
		String callbackUrl = reqDataJson.getString("callbackUrl");
		String sceneInfo = reqDataJson.getString("sceneInfo");
		String ip = reqDataJson.getString("ip");
		
		String signStr = reqDataJson.getString("signStr");
		
		StringBuilder srcStr = new StringBuilder();
		JSONObject result = new JSONObject();
		if(null == payMoney || !ValidateUtil.isDoubleT(payMoney) || Double.parseDouble(payMoney)<=0){
			result.put("returnCode", "0005");
			result.put("returnMsg", "支付金额输入不正确");
			return result;
		}
		if(callbackUrl == null || "".equals(callbackUrl)){
			result.put("returnCode", "0007");
			result.put("returnMsg", "平台支付结果通知回调地址缺失");
			return result;
		}
		srcStr.append("callbackUrl="+callbackUrl);
		
		if(ip == null || "".equals(ip)){
			result.put("returnCode", "0007");
			result.put("returnMsg", "IP缺失");
			return result;
		}
		srcStr.append("&ip="+ip);
		
		if(memberCode == null || "".equals(memberCode)){
			result.put("returnCode", "0007");
			result.put("returnMsg", "商户号缺失");
			return result;
		}
		srcStr.append("&memberCode="+memberCode);
		
		if(orderNum == null || "".equals(orderNum)){
			result.put("returnCode", "0007");
			result.put("returnMsg", "商户订单号缺失");
			return result;
		}
		if(orderNum.length() > 32){
			result.put("returnCode", "0007");
			result.put("returnMsg", "订单号长度不能超过32位");
			return result;
		}
		srcStr.append("&orderNum="+orderNum);
		srcStr.append("&payMoney="+payMoney);
		
		if(sceneInfo == null || "".equals(sceneInfo)){
			result.put("returnCode", "0007");
			result.put("returnMsg", "场景信息缺失");
			return result;
		}
		srcStr.append("&sceneInfo="+sceneInfo);
		
		if(signStr == null || "".equals(signStr)){ 
			result.put("returnCode", "0007");
			result.put("returnMsg", "缺少签名信息");
			return result;
		}
		
		
		
		result = validMemberInfoForH5(memberCode, orderNum, payMoney, "3",  srcStr.toString(), signStr, callbackUrl,sceneInfo,ip);
		
		return result;
	}
	
	public JSONObject validMemberInfoForH5(String memberCode,String orderNum,String payMoney,String platformType,String signOrginalStr,String signedStr,String callbackUrl,String sceneInfo,String ip){
		JSONObject result = new JSONObject();
		
		MemberInfoExample memberInfoExample = new MemberInfoExample();
		memberInfoExample.or().andCodeEqualTo(memberCode).andDelFlagEqualTo("0");
		List<MemberInfo> memberInfoList = memberInfoService.selectByExample(memberInfoExample);
		if(null == memberInfoList || memberInfoList.size() == 0){
			result.put("returnCode", "0001");
			result.put("returnMsg", "商户信息不存在");
			return result;
		}
		MemberInfo memberInfo = memberInfoList.get(0);
		if(!"0".equals(memberInfo.getStatus())){
			if("4".equals(memberInfo.getStatus())){
				result.put("returnCode", "0008");
				result.put("returnMsg", "该商户未进行认证，暂时无法交易");
				return result;
			}
			result.put("returnCode", "0008");
			result.put("returnMsg", "对不起，该商户暂不可用");
			return result;
		}
		
		AccountExample accountExample = new AccountExample();
		accountExample.createCriteria().andMemberIdEqualTo(memberInfo.getId()).andDelFlagEqualTo("0");
		List<Account> accounts = accountService.selectByExample(accountExample);
		if (accounts == null || accounts.size() != 1) {
			result.put("returnCode", "0008");
			result.put("returnMsg", "会员账户不存在");
			return result;
		}
		
		EpayCodeExample epayCodeExample = new EpayCodeExample();
		List<String> values = new ArrayList<String>();
		values.add("5");
		values.add("7");
		epayCodeExample.or().andMemberIdEqualTo(memberInfo.getId()).andStatusIn(values);
		List<EpayCode> epayCodeList = epayCodeService.selectByExample(epayCodeExample);
		if(null == epayCodeList || epayCodeList.size() == 0){
			result.put("returnCode", "0008");
			result.put("returnMsg", "对不起，该商户暂不可用");
			return result;
		}
		
		
		Map<String,String> rtMap  = getRouteCodeAndAisleType(memberInfo.getId(),PayTypeConstant.PAY_METHOD_H5,PayTypeConstant.PAY_TYPE_WX);
		String routeCode = rtMap.get("routeCode");
		String aisleType = rtMap.get("aisleType");
		MemberMerchantCodeExample memberMerchantCodeExample = new MemberMerchantCodeExample();
		if(aisleType !=null &&!"".equals(aisleType)){
			memberMerchantCodeExample.createCriteria().andMemberIdEqualTo(memberInfo.getId()).andRouteCodeEqualTo(routeCode).andAisleTypeEqualTo(aisleType).andDelFlagEqualTo("0");
		}else{
			memberMerchantCodeExample.createCriteria().andMemberIdEqualTo(memberInfo.getId()).andRouteCodeEqualTo(routeCode).andDelFlagEqualTo("0");
		}
		List<MemberMerchantCode> merchantCodes = memberMerchantCodeService.selectByExample(memberMerchantCodeExample);
		if (merchantCodes == null || merchantCodes.size() != 1) {
			result.put("returnCode", "0008");
			result.put("returnMsg", "对不起，商户编码不存在");
			return result;
		}
		
		MemberMerchantCode merchantCode = merchantCodes.get(0);
		
		
		PayTypeDefaultExample payTypeDefaultExample = new PayTypeDefaultExample();
		payTypeDefaultExample.createCriteria().andPayMethodEqualTo(PayTypeConstant.PAY_METHOD_H5).andPayTypeEqualTo(PayTypeConstant.PAY_TYPE_WX).andMemberIdEqualTo(memberInfo.getId()).andDelFlagEqualTo("0");
		List<PayTypeDefault> payTypeDefaultList = payTypeDefaultService.selectByExample(payTypeDefaultExample);
		if(payTypeDefaultList != null && payTypeDefaultList.size()>0){//走概率计算
			PayTypeRuleExample payTypeRuleExample = new PayTypeRuleExample();
			payTypeRuleExample.createCriteria().andPayMethodEqualTo(PayTypeConstant.PAY_METHOD_H5).andPayTypeEqualTo(PayTypeConstant.PAY_TYPE_WX).andDelFlagEqualTo("0");
			payTypeRuleExample.setOrderByClause(" id asc ");
			List<PayTypeRule> payTypeRuleList = payTypeRuleService.selectByExample(payTypeRuleExample);
			if(payTypeRuleList !=null && payTypeRuleList.size()>0){
				List<Map<String,Object>> rateList=new ArrayList<>();
	            for(int i=0;i<payTypeRuleList.size();i++){
					Map<String, Object> rateMap = new HashMap<String, Object>();
					rateMap.put("rate", payTypeRuleList.get(i).getRuleRate().doubleValue());
					rateList.add(rateMap);
				}
	            int index = Probability.getProbabilityRange(rateList);
	            if(index >= payTypeRuleList.size()){
	            	logger.info("不在概率计算范围内，走默认商户，商户编码："+merchantCode.getWxMerchantCode()+"，通道编码："+routeCode);
	            }else{
	            	PayTypeRule payTypeRule = payTypeRuleList.get(index);
	            	logger.info("概率计算商户编码："+payTypeRule.getMerchantCode()+"，通道编码："+payTypeRule.getRouteCode());
	            	merchantCode.setWxMerchantCode(payTypeRule.getMerchantCode());
	            	routeCode = payTypeRule.getRouteCode();
	            	aisleType = payTypeRule.getAisleType();
	            }
			}
			
		}
		
		
		
		SysOfficeExample sysOfficeExample = new SysOfficeExample();
		sysOfficeExample.or().andIdEqualTo(epayCodeList.get(0).getOfficeId()).andAgtTypeEqualTo("3");
		List<SysOffice> sysOfficeList = sysOfficeService.selectByExample(sysOfficeExample);
		if(null == sysOfficeList || sysOfficeList.size() == 0){
			result.put("returnCode", "0008");
			result.put("returnMsg", "该商户暂未支持收银台功能，请确认后重试");
			return result;
		}
		
		PayResultNoticeExample payResultNoticeExample = new PayResultNoticeExample();
		payResultNoticeExample.or().andMemberCodeEqualTo(memberCode).andOrderNumOuterEqualTo(orderNum).andStatusNotEqualTo("1");
		List<PayResultNotice> PayResultNoticeList = payResultNoticeService.selectByExample(payResultNoticeExample);
		if(null != PayResultNoticeList && PayResultNoticeList.size() > 0){
			result.put("returnCode", "0002");
			result.put("returnMsg", "该笔订单已支付，请勿重复支付");
			return result;
		}
		
		SysOffice sysOffice = sysOfficeList.get(0);
//		String singedStr = EpaySignUtil.sign(sysOffice.getPrivateKeyRsa(), signOrginalStr);
//		System.out.println(singedStr);  
		if(!EpaySignUtil.checksign(sysOffice.getPublicKeyRsa(), signOrginalStr, signedStr)){//by linxf 测试屏蔽
			result.put("returnCode", "0004");
			result.put("returnMsg", "签名校验错误，请检查签名参数是否正确");
			return result;
		}
		
		//校验交易额是否超出限制
	    /*
		JSONObject checkResult = checkPayLimit(memberInfo.getId(), new BigDecimal(payMoney), memberInfo.getSingleLimit(), memberInfo.getDayLimit());
		if(null != checkResult){
			return checkResult;
		}
		*/
		
		if(RouteCodeConstant.TB_ROUTE_CODE.equals(routeCode)){
			result = tbH5Pay(platformType,memberInfo, payMoney, orderNum,sceneInfo,ip, callbackUrl , merchantCode );
			result.put("routeCode", routeCode);
		}else if(RouteCodeConstant.ESK_ROUTE_CODE.equals(routeCode)){
			result = eskH5Pay(platformType,memberInfo, payMoney, orderNum,sceneInfo,ip, callbackUrl , merchantCode );
			result.put("routeCode", routeCode);
		}else if(RouteCodeConstant.XF_ROUTE_CODE.equals(routeCode)){
			result = xfH5Pay(platformType,memberInfo, payMoney, orderNum,sceneInfo,ip, callbackUrl , merchantCode );
			result.put("routeCode", routeCode);
		}
		
		return result;
	}
	
	
	public JSONObject tbH5Pay(String platformType,MemberInfo memberInfo,String payMoney,String orderNumOuter,String sceneInfo,String ip,String callbackUrl,MemberMerchantCode merchantCode) {
		JSONObject result = new JSONObject();
		try {
			MemberMerchantKeyExample memberMerchantKeyExample = new MemberMerchantKeyExample();
	        memberMerchantKeyExample.createCriteria().andRouteCodeEqualTo(RouteCodeConstant.TB_ROUTE_CODE).andMerchantCodeEqualTo(merchantCode.getWxMerchantCode()).andDelFlagEqualTo("0");
	        List<MemberMerchantKey> keyList = memberMerchantKeyService.selectByExample(memberMerchantKeyExample);
	        if(keyList == null || keyList.size()!=1){
	            result.put("returnCode", "0008");
				result.put("returnMsg", "商户私钥未配置");
				return result;
	        }
	        MemberMerchantKey merchantKey = keyList.get(0);
			
			// 插入一条收款记录
			String orderCode = CommonUtil.getOrderCode();
			
			DebitNote debitNote = new DebitNote();
			debitNote.setCreateDate(new Date());
			debitNote.setMemberId(memberInfo.getId());
			debitNote.setMoney(new BigDecimal(payMoney));
			debitNote.setOrderCode(orderCode);
			debitNote.setOrderNumOuter(orderNumOuter);
			debitNote.setRouteId(RouteCodeConstant.TB_ROUTE_CODE);
			debitNote.setStatus("0");
			debitNote.setTxnMethod(PayTypeConstant.PAY_METHOD_H5);
			debitNote.setTxnType("1");
			debitNote.setMemberCode(memberInfo.getWxMemberCode());
			debitNote.setMerchantCode(merchantCode.getWxMerchantCode());
			
			debitNote.setSettleType(memberInfo.getSettleType());
			debitNote.setIp(ip);
			if("0".equals(memberInfo.getSettleType())){
				debitNote.setTradeRate(merchantCode.getT0TradeRate());
			}else{
				debitNote.setTradeRate(merchantCode.getT1TradeRate());
			}
			String configName = "SINGLE_LIMIT_1007_006_WX";
			JSONObject checkResult = checkLimitMoney(configName, new BigDecimal(payMoney));
			if(null != checkResult){
				debitNote.setStatus("4");
				debitNoteService.insertSelective(debitNote);
				return checkResult;
			}
			
			debitNoteService.insertSelective(debitNote);
			
			PayResultNotice payResultNotice = new PayResultNotice();
			payResultNotice.setOrderCode(debitNote.getOrderCode());
			payResultNotice.setOrderNumOuter(orderNumOuter);
			payResultNotice.setPayMoney(debitNote.getMoney());
			payResultNotice.setMemberCode(memberInfo.getCode());
			payResultNotice.setPayType("1");
			
			payResultNotice.setReturnUrl(callbackUrl);
			payResultNotice.setStatus("1");
			payResultNotice.setCreateDate(new Date());
			payResultNotice.setPlatformType(platformType);
			if("3".equals(platformType)){
				payResultNotice.setInterfaceType("2");
			}else{
				payResultNotice.setInterfaceType("1");
			}
			payResultNoticeService.insertSelective(payResultNotice);
			
			// String callBack = "http://" + request.getServerName() + ":" +
			// request.getServerPort() + request.getContextPath()+
			// "/debitNote/msNotify";
			String callBack = SysConfig.serverUrl + "/debitNote/tbWxH5PayNotify";
			
			
			//System.out.println("-------------通知地址------" + callBack + "------------");
			
			
			
			
			TreeMap<String, String> map = new TreeMap<>();
	        map.put("app_id",merchantKey.getAppId());
	        map.put("method","openapi.payment.order.h5");
	        map.put("format","json");
	        map.put("sign_method","md5");
	        map.put("nonce",new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
	        map.put("version","1.0");
	        
	        HashMap<String, Object> content = new HashMap<String, Object>();
	        content.put("merchant_order_sn", orderCode);
	        content.put("mchid", merchantCode.getWxMerchantCode());
	        content.put("total_fee",payMoney);
	       // content.put("store_id",TestConstants.storeId);
	        content.put("scene_info",sceneInfo);
	        content.put("ip",ip);
	        content.put("call_back_url", callBack);
	        
	        String bizContent = JSON.toJSONString(content);

	        map.put("biz_content",bizContent);

	        String secret = merchantKey.getPrivateKey();
	        String sign = SignUtil.createSign(map, secret);
	        map.put("sign",sign);
	        logger.info("H5支付请求报文[{}]", new Object[] { JSON.toJSONString(map) });
	        String resultMsg = HttpUtil.sendPostRequest(TBConfig.msServerUrl, JSON.toJSONString(map));
	        logger.info("H5返回请求报文[{}]", new Object[] { resultMsg });
	        
	        com.alibaba.fastjson.JSONObject resultObj = com.alibaba.fastjson.JSONObject.parseObject(resultMsg);
	        String result_code = resultObj.getString("result_code");
	        if("200".equals(result_code)){
	        	com.alibaba.fastjson.JSONObject data = (com.alibaba.fastjson.JSONObject)resultObj.get("data");
	        	String payUrl = data.getString("payUrl");
	        	result.put("payUrl", payUrl);
	        	result.put("returnCode", "0000");
				result.put("returnMsg", "成功");
	        }else{
	        	result.put("returnCode", "0009");
				result.put("returnMsg", resultObj.getString("result_message"));
	        }
	        
			
			
        } catch (Exception e) {
			logger.error(e.getMessage());
			result.put("returnCode", "0096");
			result.put("returnMsg", e.getMessage());
			return result;
		}
		return result;
	}
	
	
	public JSONObject eskH5Pay(String platformType,MemberInfo memberInfo,String payMoney,String orderNumOuter,String sceneInfo,String ip,String callbackUrl,MemberMerchantCode merchantCode) {
		JSONObject result = new JSONObject();
		try {
			// 插入一条收款记录
			String orderCode = CommonUtil.getOrderCode();
			
			DebitNote debitNote = new DebitNote();
			debitNote.setCreateDate(new Date());
			debitNote.setMemberId(memberInfo.getId());
			debitNote.setMoney(new BigDecimal(payMoney));
			debitNote.setOrderCode(orderCode);
			debitNote.setOrderNumOuter(orderNumOuter);
			debitNote.setRouteId(RouteCodeConstant.ESK_ROUTE_CODE);
			debitNote.setStatus("0");
			debitNote.setTxnMethod(PayTypeConstant.PAY_METHOD_H5);
			debitNote.setTxnType("1");
			debitNote.setMemberCode(memberInfo.getWxMemberCode());
			debitNote.setMerchantCode(merchantCode.getWxMerchantCode());
			debitNote.setIp(ip);
			debitNote.setSettleType(memberInfo.getSettleType());
			if("0".equals(memberInfo.getSettleType())){
				debitNote.setTradeRate(merchantCode.getT0TradeRate());
			}else{
				debitNote.setTradeRate(merchantCode.getT1TradeRate());
			}
			
			String configName = "SINGLE_LIMIT_1002_006_WX";
			JSONObject checkResult = checkLimitMoney(configName, new BigDecimal(payMoney));
			if(null != checkResult){
				debitNote.setStatus("4");
				debitNoteService.insertSelective(debitNote);
				return checkResult;
			}
			
			debitNoteService.insertSelective(debitNote);
			
			PayResultNotice payResultNotice = new PayResultNotice();
			payResultNotice.setOrderCode(debitNote.getOrderCode());
			payResultNotice.setOrderNumOuter(orderNumOuter);
			payResultNotice.setPayMoney(debitNote.getMoney());
			payResultNotice.setMemberCode(memberInfo.getCode());
			payResultNotice.setPayType("1");
			
			payResultNotice.setReturnUrl(callbackUrl);
			payResultNotice.setStatus("1");
			payResultNotice.setCreateDate(new Date());
			payResultNotice.setPlatformType(platformType);
			if("3".equals(platformType)){
				payResultNotice.setInterfaceType("2");
			}else{
				payResultNotice.setInterfaceType("1");
			}
			payResultNoticeService.insertSelective(payResultNotice);
			
			
			
			String callBack = SysConfig.serverUrl + "/cashierDesk/eskPayNotify";
			// 调用支付通道
			String serverUrl = ESKConfig.msServerUrl;
			//PublicKey yhPubKey = null;
			//yhPubKey = CryptoUtil.getEskRSAPublicKey();
			//yhPubKey = CryptoUtil.getRSAPublicKey(false);
			//PrivateKey hzfPriKey = CryptoUtil.getRSAPrivateKey();
			String tranCode = "009";
			String charset = "utf-8";
			
			JSONObject reqData = new JSONObject();
			reqData.put("merchantCode", merchantCode.getWxMerchantCode());
			reqData.put("orderNumber", orderCode);
			reqData.put("tranCode", tranCode);
			reqData.put("aisleType", "2");
			reqData.put("totalAmount", payMoney);
			reqData.put("subject", memberInfo.getName() + " 收款");
			reqData.put("PayType", "1");
			reqData.put("callback", callBack);
			reqData.put("desc", memberInfo.getName() + " 收款");
			reqData.put("terminalId", ip);
			System.out.println("待加密数据: "+reqData);
			
			String plainXML = reqData.toString();
			byte[] plainBytes = plainXML.getBytes(charset);
			String keyStr = MSCommonUtil.generateLenString(16);
			;
			byte[] keyBytes = keyStr.getBytes(charset);
			
			//String encryptData = new String(Base64.encodeBase64((Key.jdkAES(plainBytes, keyBytes))));
			String signData = new String(Base64.encodeBase64(Key.rsaSign(plainBytes, ESKConfig.privateKey)), charset);
			String encryptData = new String(Base64.encodeBase64((CryptoUtil.AESEncrypt(plainBytes, keyBytes, "AES", "AES/ECB/PKCS5Padding", null))), charset);
			//String signData = new String(Base64.encodeBase64(CryptoUtil.digitalSign(plainBytes, hzfPriKey, "SHA1WithRSA")), charset);
			String encrtptKey = new String(Base64.encodeBase64(Key.jdkRSA(keyBytes, ESKConfig.yhPublicKey)), charset);

			//String encrtptKey = new String(Base64.encodeBase64(CryptoUtil.RSAEncrypt(keyBytes, yhPubKey, 2048, 11, "RSA/ECB/PKCS1Padding")), charset);
			List<NameValuePair> nvps = new LinkedList<NameValuePair>();
			nvps.add(new BasicNameValuePair("Context", encryptData));
			nvps.add(new BasicNameValuePair("encrtpKey", encrtptKey));
			
			nvps.add(new BasicNameValuePair("signData", signData));
			nvps.add(new BasicNameValuePair("agentId", ESKConfig.agentId));
			byte[] b = HttpClient4Util.getInstance().doPost(serverUrl, null, nvps);
			String respStr = new String(b, charset);
			logger.info("返回报文[{}]", new Object[] { respStr });
			JSONObject jsonObject = JSONObject.fromObject(respStr);
			String resEncryptData = jsonObject.getString("Context");
			String resEncryptKey = jsonObject.getString("encrtpKey");
			byte[] decodeBase64KeyBytes = Base64.decodeBase64(resEncryptKey.getBytes(charset));
			// 解密encryptKey得到merchantAESKey
			//byte[] merchantAESKeyBytes = CryptoUtil.RSADecrypt(decodeBase64KeyBytes, hzfPriKey, 2048, 11, "RSA/ECB/PKCS1Padding");
			byte[] merchantAESKeyBytes = Key.jdkRSA_(decodeBase64KeyBytes, ESKConfig.privateKey);
					

			// 使用base64解码商户请求报文
			byte[] decodeBase64DataBytes = Base64.decodeBase64(resEncryptData.getBytes(charset));
			// 用解密得到的merchantAESKey解密encryptData
			byte[] merchantXmlDataBytes = CryptoUtil.AESDecrypt(decodeBase64DataBytes, merchantAESKeyBytes, "AES", "AES/ECB/PKCS5Padding", null);
			String resXml = new String(merchantXmlDataBytes, charset);
			JSONObject respJSONObject = JSONObject.fromObject(resXml);
			logger.info("返回报文[{}]",  respJSONObject );
			
			
			if("R".equals(respJSONObject.getString("respType"))&&"555555".equals(respJSONObject.getString("respCode"))){
				if(respJSONObject.containsKey("pay_url")&&!"".equals(respJSONObject.getString("pay_url"))){
					result.put("payUrl", respJSONObject.getString("pay_url")+"&redirecturl=http://www.johutech.com/johuPay/debitNote/payCallBack?orderCode="+orderCode);
					//result.put("payUrl", respJSONObject.getString("pay_url"));
					result.put("returnCode", "0000");
					result.put("returnMsg", "成功");
					
				}else{
					result.put("returnCode", "0009");
					result.put("returnMsg", "获取微信信息失败");
				}
			}else{
				result.put("returnCode", "0009");
				result.put("returnMsg", "调用微信H5支付接口返回失败("+respJSONObject.getString("respMsg")+")");
			}
			
        } catch (Exception e) {
			logger.error(e.getMessage());
			result.put("returnCode", "0096");
			result.put("returnMsg", e.getMessage());
			return result;
		}
		return result;
	}
	
	
	public JSONObject eskH5Pay008(String platformType,MemberInfo memberInfo,String payMoney,String orderNumOuter,String sceneInfo,String ip,String callbackUrl,MemberMerchantCode merchantCode) {
		JSONObject result = new JSONObject();
		try {
			// 插入一条收款记录
			String orderCode = CommonUtil.getOrderCode();
			
			DebitNote debitNote = new DebitNote();
			debitNote.setCreateDate(new Date());
			debitNote.setMemberId(memberInfo.getId());
			debitNote.setMoney(new BigDecimal(payMoney));
			debitNote.setOrderCode(orderCode);
			debitNote.setOrderNumOuter(orderNumOuter);
			debitNote.setRouteId(RouteCodeConstant.ESK_ROUTE_CODE);
			debitNote.setStatus("0");
			debitNote.setTxnMethod(PayTypeConstant.PAY_METHOD_H5);
			debitNote.setTxnType("1");
			debitNote.setMemberCode(memberInfo.getWxMemberCode());
			debitNote.setMerchantCode(merchantCode.getWxMerchantCode());
			
			debitNote.setSettleType(memberInfo.getSettleType());
			if("0".equals(memberInfo.getSettleType())){
				debitNote.setTradeRate(merchantCode.getT0TradeRate());
			}else{
				debitNote.setTradeRate(merchantCode.getT1TradeRate());
			}
			
			debitNoteService.insertSelective(debitNote);
			
			PayResultNotice payResultNotice = new PayResultNotice();
			payResultNotice.setOrderCode(debitNote.getOrderCode());
			payResultNotice.setOrderNumOuter(orderNumOuter);
			payResultNotice.setPayMoney(debitNote.getMoney());
			payResultNotice.setMemberCode(memberInfo.getCode());
			payResultNotice.setPayType("1");
			
			payResultNotice.setReturnUrl(callbackUrl);
			payResultNotice.setStatus("1");
			payResultNotice.setCreateDate(new Date());
			payResultNotice.setPlatformType(platformType);
			if("3".equals(platformType)){
				payResultNotice.setInterfaceType("2");
			}else{
				payResultNotice.setInterfaceType("1");
			}
			payResultNoticeService.insertSelective(payResultNotice);
			
			
			
			String callBack = SysConfig.serverUrl + "/cashierDesk/eskPayNotify";
			// 调用支付通道
			String serverUrl = ESKConfig.msServerUrl;
			//PublicKey yhPubKey = null;
			//yhPubKey = CryptoUtil.getEskRSAPublicKey();
			//yhPubKey = CryptoUtil.getRSAPublicKey(false);
			//PrivateKey hzfPriKey = CryptoUtil.getRSAPrivateKey();
			String tranCode = "008";
			String charset = "utf-8";
			
			JSONObject reqData = new JSONObject();
			reqData.put("merchantCode", merchantCode.getWxMerchantCode());
			reqData.put("orderNumber", orderCode);
			reqData.put("tranCode", tranCode);
			reqData.put("aisleType", "2");
			reqData.put("totalAmount", payMoney);
			reqData.put("subject", memberInfo.getName() + " 收款");
			reqData.put("PayType", "1");
			reqData.put("callback", callBack);
			reqData.put("desc", memberInfo.getName() + " 收款");
			reqData.put("terminalId", ip);
			reqData.put("subMchId", "83035710");
			
			JSONObject sceneData = new JSONObject();
			sceneData.put("type", "wap");
			sceneData.put("wap_url", "www.tianyuesk.com");
			sceneData.put("wap_name", "聚合支付");
			reqData.put("sceneInfo", sceneData.toString());
			
			System.out.println("待加密数据: "+reqData);
			
			String plainXML = reqData.toString();
			byte[] plainBytes = plainXML.getBytes(charset);
			String keyStr = MSCommonUtil.generateLenString(16);
			;
			byte[] keyBytes = keyStr.getBytes(charset);
			
			//String encryptData = new String(Base64.encodeBase64((Key.jdkAES(plainBytes, keyBytes))));
			String signData = new String(Base64.encodeBase64(Key.rsaSign(plainBytes, ESKConfig.privateKey)), charset);
			String encryptData = new String(Base64.encodeBase64((CryptoUtil.AESEncrypt(plainBytes, keyBytes, "AES", "AES/ECB/PKCS5Padding", null))), charset);
			//String signData = new String(Base64.encodeBase64(CryptoUtil.digitalSign(plainBytes, hzfPriKey, "SHA1WithRSA")), charset);
			String encrtptKey = new String(Base64.encodeBase64(Key.jdkRSA(keyBytes, ESKConfig.yhPublicKey)), charset);

			//String encrtptKey = new String(Base64.encodeBase64(CryptoUtil.RSAEncrypt(keyBytes, yhPubKey, 2048, 11, "RSA/ECB/PKCS1Padding")), charset);
			List<NameValuePair> nvps = new LinkedList<NameValuePair>();
			nvps.add(new BasicNameValuePair("Context", encryptData));
			nvps.add(new BasicNameValuePair("encrtpKey", encrtptKey));
			
			nvps.add(new BasicNameValuePair("signData", signData));
			nvps.add(new BasicNameValuePair("agentId", ESKConfig.agentId));
			byte[] b = HttpClient4Util.getInstance().doPost(serverUrl, null, nvps);
			String respStr = new String(b, charset);
			logger.info("返回报文[{}]", new Object[] { respStr });
			JSONObject jsonObject = JSONObject.fromObject(respStr);
			String resEncryptData = jsonObject.getString("Context");
			String resEncryptKey = jsonObject.getString("encrtpKey");
			byte[] decodeBase64KeyBytes = Base64.decodeBase64(resEncryptKey.getBytes(charset));
			// 解密encryptKey得到merchantAESKey
			//byte[] merchantAESKeyBytes = CryptoUtil.RSADecrypt(decodeBase64KeyBytes, hzfPriKey, 2048, 11, "RSA/ECB/PKCS1Padding");
			byte[] merchantAESKeyBytes = Key.jdkRSA_(decodeBase64KeyBytes, ESKConfig.privateKey);
					

			// 使用base64解码商户请求报文
			byte[] decodeBase64DataBytes = Base64.decodeBase64(resEncryptData.getBytes(charset));
			// 用解密得到的merchantAESKey解密encryptData
			byte[] merchantXmlDataBytes = CryptoUtil.AESDecrypt(decodeBase64DataBytes, merchantAESKeyBytes, "AES", "AES/ECB/PKCS5Padding", null);
			String resXml = new String(merchantXmlDataBytes, charset);
			JSONObject respJSONObject = JSONObject.fromObject(resXml);
			logger.info("返回报文[{}]",  respJSONObject );
			
			
			if("R".equals(respJSONObject.getString("respType"))&&"555555".equals(respJSONObject.getString("respCode"))){
				if(respJSONObject.containsKey("pay_url")&&!"".equals(respJSONObject.getString("pay_url"))){
					result.put("payUrl", respJSONObject.getString("pay_url")+"&redirecturl=http://www.johutech.com/johuPay/debitNote/payCallBack?orderCode="+orderCode);
					//result.put("payUrl", respJSONObject.getString("pay_url"));
					result.put("returnCode", "0000");
					result.put("returnMsg", "成功");
					
				}else{
					result.put("returnCode", "0009");
					result.put("returnMsg", "获取微信信息失败");
				}
			}else{
				result.put("returnCode", "0009");
				result.put("returnMsg", "调用微信H5支付接口返回失败("+respJSONObject.getString("respMsg")+")");
			}
			
        } catch (Exception e) {
			logger.error(e.getMessage());
			result.put("returnCode", "0096");
			result.put("returnMsg", e.getMessage());
			return result;
		}
		return result;
	}
	
	public JSONObject xfH5Pay(String platformType,MemberInfo memberInfo,String payMoney,String orderNumOuter,String sceneInfo,String ip,String callbackUrl,MemberMerchantCode merchantCode) {
		JSONObject result = new JSONObject();
		try {
			// 插入一条收款记录
			String orderCode = CommonUtil.getOrderCode();
			
			DebitNote debitNote = new DebitNote();
			debitNote.setCreateDate(new Date());
			debitNote.setMemberId(memberInfo.getId());
			debitNote.setMoney(new BigDecimal(payMoney));
			debitNote.setOrderCode(orderCode);
			debitNote.setOrderNumOuter(orderNumOuter);
			debitNote.setRouteId(RouteCodeConstant.XF_ROUTE_CODE);
			debitNote.setStatus("0");
			debitNote.setTxnMethod(PayTypeConstant.PAY_METHOD_H5);
			debitNote.setTxnType("1");
			debitNote.setMemberCode(memberInfo.getWxMemberCode());
			debitNote.setMerchantCode(merchantCode.getWxMerchantCode());
			
			debitNote.setSettleType(memberInfo.getSettleType());
			if("0".equals(memberInfo.getSettleType())){
				debitNote.setTradeRate(merchantCode.getT0TradeRate());
			}else{
				debitNote.setTradeRate(merchantCode.getT1TradeRate());
			}
			
			debitNoteService.insertSelective(debitNote);
			
			PayResultNotice payResultNotice = new PayResultNotice();
			payResultNotice.setOrderCode(debitNote.getOrderCode());
			payResultNotice.setOrderNumOuter(orderNumOuter);
			payResultNotice.setPayMoney(debitNote.getMoney());
			payResultNotice.setMemberCode(memberInfo.getCode());
			payResultNotice.setPayType("1");
			
			payResultNotice.setReturnUrl(callbackUrl);
			payResultNotice.setStatus("1");
			payResultNotice.setCreateDate(new Date());
			payResultNotice.setPlatformType(platformType);
			if("3".equals(platformType)){
				payResultNotice.setInterfaceType("2");
			}else{
				payResultNotice.setInterfaceType("1");
			}
			payResultNoticeService.insertSelective(payResultNotice);
			
			String callBack = SysConfig.serverUrl + "/debitNote/xfPayNotify";
			
			String returnUrl = XFConfig.pageUrl;
			// 调用支付通道
			String serverUrl = XFConfig.msServerUrl;
			
			String tranCode = "JH_H5_PAY";
			String charset = "utf-8";
			String secId = "RSA";
			String version = "4.0.0";
			String merchantId = merchantCode.getWxMerchantCode();
			String reqSn = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date())+merchantId;
			
			JSONObject data = new JSONObject();
			
			data.put("amount", (int)((new BigDecimal(payMoney)).floatValue()*100));
			data.put("outOrderId", orderCode);
			data.put("productName", memberInfo.getName() + " 收款");
			data.put("noticeUrl", callBack);
			data.put("returnUrl", returnUrl);
			data.put("isLimitCreditPay", "0");//是否支持信用卡 0 无限制（信用卡，借记卡） 1 不支持信用卡
			data.put("payType", "WXPAY");
			data.put("bizType", "200");
			data.put("subMerchantId", memberInfo.getWxMerchantCode());
			data.put("subMerchantName", memberInfo.getName());
			data.put("methodVersion", "1.0");
			data.put("merchantType", "OUT");
			data.put("sceneType", "2");//场景类型：0:IOS,1:Android,2:Wap
			data.put("terminalIp", ip);
			data.put("transCur", "156");//人民币：156
			data.put("wapUrl", "http://www.johutech.com/johuPay");
			data.put("wapName", "聚合支付");
			
			System.out.println("待加密业务数据: "+data);
			
			String plainXML = data.toString();
			byte[] plainBytes = plainXML.getBytes(charset);
			//String keyStr = XFConfig.privateKey;
			String keyStr = MSCommonUtil.generateLenString(16);
			byte[] keyBytes = keyStr.getBytes(charset);
			String encryptData = new String((CryptoUtil.AESEncrypt(plainBytes, keyBytes, "AES", "AES/ECB/PKCS5Padding", null)));
			
			JSONObject reqData = new JSONObject();
			reqData.put("service", tranCode);
			reqData.put("secId", secId);
			reqData.put("version", version);
			reqData.put("reqSn", reqSn);
			reqData.put("merchantId", merchantId);
			reqData.put("data", encryptData);
			String dataStr = StringUtil.orderedKey(reqData);
			System.out.println("待加密数据: "+dataStr);
			String md5Str = com.epay.scanposp.common.utils.xf.SignUtil.md5Sign(dataStr, charset);
			System.out.println("md5加密后数据: "+md5Str);
			String signData = new String(Base64.encodeBase64(com.epay.scanposp.common.utils.xf.SignUtil.rsaSign(md5Str.getBytes(charset), XFConfig.privateKey)), charset);
			System.out.println("签名: "+signData);
			
			List<NameValuePair> nvps = new LinkedList<NameValuePair>();
			nvps.add(new BasicNameValuePair("service", tranCode));
			nvps.add(new BasicNameValuePair("secId", secId));
			nvps.add(new BasicNameValuePair("version", version));
			nvps.add(new BasicNameValuePair("reqSn", reqSn));
			nvps.add(new BasicNameValuePair("merchantId", merchantId));
			nvps.add(new BasicNameValuePair("data", encryptData));
			nvps.add(new BasicNameValuePair("sign", signData));
			byte[] b = HttpClient4Util.getInstance().doPost(serverUrl, null, nvps);
			String respStr = new String(b, charset);
			logger.info("返回报文[{}]", new Object[] { respStr });
				// 用解密得到的merchantAESKey解密encryptData
			byte[] merchantXmlDataBytes = CryptoUtil.AESDecrypt(respStr.getBytes(charset), keyBytes, "AES", "AES/ECB/PKCS5Padding", null);
			String resXml = new String(merchantXmlDataBytes, charset);
			logger.info("返回报文[{}]",  resXml );
			
        } catch (Exception e) {
			logger.error(e.getMessage());
			result.put("returnCode", "0096");
			result.put("returnMsg", e.getMessage());
			return result;
		}
		return result;
	}
	
	@RequestMapping("/debitNote/tbWxH5PayNotify")
	public void tbWxH5PayNotify(HttpServletRequest request,HttpServletResponse response) {
		String respString = "success";
		String res = "";
		try {
			String result_code = request.getParameter("result_code");
			String result_message = request.getParameter("result_message");
			String sign = request.getParameter("sign");
			String data = request.getParameter("data");
			TreeMap<String, Object> rtmap = new TreeMap<String, Object>();
			rtmap.put("data", data);
			rtmap.put("result_code", result_code);
			rtmap.put("result_message", result_message);
			rtmap.put("sign", sign);
			logger.info("tbWxH5PayNotify解密回调返回报文[{}]",  JSON.toJSONString(rtmap) );
			rtmap.remove("sign");
			
            JSONObject map = JSONObject.fromObject(data);
        	String reqMsgId = map.getString("merchant_order_sn");
        	TradeDetailExample tradeDetailExample = new TradeDetailExample();
        	tradeDetailExample.or().andOrderCodeEqualTo(reqMsgId);
        	List<TradeDetail> tradeDetailList = tradeDetailService.selectByExample(tradeDetailExample);
        	if(tradeDetailList!=null && tradeDetailList.size()>0){
        		response.getWriter().write(respString);
        		return;
        	}
        	
        	DebitNoteExample debitNoteExample = new DebitNoteExample();
			debitNoteExample.createCriteria().andOrderCodeEqualTo(reqMsgId);
			List<DebitNote> debitNotes_tmp = debitNoteService.selectByExample(debitNoteExample);
			if (debitNotes_tmp == null || debitNotes_tmp.size() == 0) {
				res = "订单不存在";
                respString = "fail";
                response.getWriter().write(respString);
        		return;
				
			}
			DebitNote debitNote = debitNotes_tmp.get(0);
        	MemberMerchantKeyExample memberMerchantKeyExample = new MemberMerchantKeyExample();
            memberMerchantKeyExample.createCriteria().andRouteCodeEqualTo(RouteCodeConstant.TB_ROUTE_CODE).andMerchantCodeEqualTo(debitNote.getMerchantCode()).andDelFlagEqualTo("0");
            List<MemberMerchantKey> keyList = memberMerchantKeyService.selectByExample(memberMerchantKeyExample);
            if(keyList == null || keyList.size()!=1){
            	res = "商户私钥未配置";
                respString = "fail";
                response.getWriter().write(respString);
        		return;
            }
            
            String mySign = SignUtil.createSign(rtmap, keyList.get(0).getPrivateKey());
			if (!sign.equals(mySign)){
				res = "验证签名不通过";
                respString = "fail";
                logger.info(res);
                response.getWriter().write(respString);
        		return;
            }
            
        	MsResultNoticeExample msResultNoticeExample = new MsResultNoticeExample();
			msResultNoticeExample.or().andOrderCodeEqualTo(reqMsgId);
			List<MsResultNotice> msResultNoticeList = msResultNoticeService.selectByExample(msResultNoticeExample);
			if(null == msResultNoticeList || msResultNoticeList.size() == 0){
				//debitNoteExample.createCriteria().andOrderCodeEqualTo(reqMsgId);
				//List<DebitNote> debitNotes = debitNoteService.selectByExample(debitNoteExample);
				if (debitNote != null ) {
					//DebitNote debitNote = debitNotes.get(0);
					debitNote.setRespCode(result_code);
					debitNote.setRespMsg(result_message);
					
					//新增一条交易明细记录
					TradeDetail tradeDetail=new TradeDetail();
					tradeDetail.setTxnDate(DateUtil.getDateFormat(new Date(), "yyyyMMdd"));
					tradeDetail.setMemberId(debitNote.getMemberId());
					tradeDetail.setMerchantCode(debitNote.getMerchantCode());
					if(map.containsKey("trade_no")){
						tradeDetail.setChannelNo(map.getString("trade_no"));
					}
					tradeDetail.setMemberCode(debitNote.getMemberCode());
					tradeDetail.setMoney(debitNote.getMoney());
					tradeDetail.setPayTime(DateUtil.getDateTimeStr(debitNote.getCreateDate()));
					tradeDetail.setPtSerialNo(debitNote.getOrderCode());
					tradeDetail.setOrderCode(debitNote.getOrderCode());
					tradeDetail.setReqDate(DateUtil.getDateTimeStr(debitNote.getCreateDate()));
					//tradeDetail.setRespCode(respJSONObject.get("respCode").toString());
					tradeDetail.setRespDate(DateUtil.getDateTimeStr(new Date()));
					tradeDetail.setRespMsg(result_message);
					tradeDetail.setRouteId(debitNote.getRouteId());
					tradeDetail.setTxnMethod(debitNote.getTxnMethod());
					tradeDetail.setTxnType(debitNote.getTxnType());
					tradeDetail.setMemberTradeRate(debitNote.getTradeRate());
					tradeDetail.setDelFlag("0");
					tradeDetail.setCreateDate(new Date());
					if ("200".equals(result_code)) {
						debitNote.setStatus("1");
						tradeDetail.setRespType("S");
						tradeDetail.setRespCode("000000");
					}else{
						debitNote.setStatus("2");
						tradeDetail.setRespType("E");
						tradeDetail.setRespCode(result_code);
					}
					debitNote.setUpdateDate(new Date());
					debitNoteService.updateByPrimaryKey(debitNote);
					
					MsResultNotice msResultNotice = new MsResultNotice();
					msResultNotice.setMoney(new BigDecimal(map.getString("total_fee")));
					msResultNotice.setOrderCode(reqMsgId);
					if(map.containsKey("trade_no")){
						msResultNotice.setPtSerialNo(map.getString("trade_no"));
					}
					//SimpleDateFormat dateSimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					msResultNotice.setRespDate(DateUtil.getDateTimeStr(new Date()));
					msResultNotice.setRespType("");
					msResultNotice.setRespCode(result_code);
					msResultNotice.setRespMsg(result_message);
					msResultNotice.setPayTime(DateUtil.getDateTimeStr(new Date()));
					msResultNotice.setCreateDate(new Date());
					msResultNotice.setUpdateDate(new Date());
					msResultNoticeService.insertSelective(msResultNotice);
					
					if("0".equals(debitNote.getSettleType())){
						tradeDetail.setSettleType("0");
					}else{
						tradeDetail.setSettleType("1");
					}
					PayResultNoticeExample payResultNoticeExample = new PayResultNoticeExample();
					payResultNoticeExample.or().andOrderCodeEqualTo(reqMsgId);
					List<PayResultNotice> payResultNoticeList = payResultNoticeService.selectByExample(payResultNoticeExample);
					PayResultNotice payResultNotice = null;
					if(payResultNoticeList.size()>0){
						payResultNotice = payResultNoticeList.get(0);
						payResultNotice.setResultMessage(debitNote.getRespMsg());
						payResultNotice.setStatus("2");
						payResultNotice.setPayTime(msResultNotice.getPayTime());
						payResultNotice.setUpdateDate(new Date());
						if ("200".equals(result_code)) {
							payResultNotice.setRespType("2");
							payResultNotice.setResultCode("0000");
							payResultNotice.setResultMessage("交易成功");
						}else{
							payResultNotice.setRespType("3");
							payResultNotice.setResultCode("0003");   
							payResultNotice.setResultMessage("交易失败："+result_message);
						}
						
						tradeDetail.setInterfaceType(payResultNotice.getInterfaceType());
						tradeDetail.setPlatformType(payResultNotice.getPlatformType());
						tradeDetail.setOrderNumOuter(payResultNotice.getOrderNumOuter());
					}
					//更改逻辑  只有交易成功的记录才写进交易记录表  单独写在此处防止后面再次更改逻辑会不理解
					if ("200".equals(result_code)) {
						tradeDetail.setCardType(msResultNotice.getCardType());
						tradeDetailService.insertSelective(tradeDetail);
					}
					
					
					
					if(payResultNoticeList.size() > 0){
						payResultNotifyService.notify(payResultNotice);
						//更新状态是触发器可以读取该条数据以执行通知任务
					/*	payResultNoticeService.updateByPrimaryKeySelective(payResultNotice);
						//获取民生通知后即向商户提供结果通知 (后续若因其他因素需多次通知商户,则由相应的定时任务完成)
						System.out.println("getPoolSize====" + threadPoolTaskExecutor.getPoolSize());
						PayResultNoticeThread payResultNoyiceThread = new PayResultNoticeThread(payResultNoticeService, sysOfficeExtendService, payResultNotice);
						threadPoolTaskExecutor.execute(payResultNoyiceThread);
						System.out.println("getActiveCount====" + threadPoolTaskExecutor.getActiveCount());
						//new Thread(payResultNoyiceThread).start();
					*/
					}
					
				}
			}	
            
        } catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}
		try {
			response.getWriter().write(respString);
		} catch (IOException e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}

	}
	
	
	
	
	
	private Map<String,String> getRouteCodeAndAisleType(Integer memberId,String payMethod, String payType  ){
		Map<String,String> map = new HashMap<String, String>();
		String routeCode = SysConfig.passCode;
		String aisleType = "";
		PayTypeExample payTypeExample = new PayTypeExample();
		payTypeExample.createCriteria().andMemberIdEqualTo(memberId).andPayMethodEqualTo(payMethod).andPayTypeEqualTo(payType).andDelFlagEqualTo("0");
		List<PayType> payTypeList = payTypeService.selectByExample(payTypeExample);
		if(payTypeList == null || payTypeList.size() == 0){
			payTypeExample = new PayTypeExample();
			payTypeExample.createCriteria().andMemberIdEqualTo(0).andPayMethodEqualTo(payMethod).andPayTypeEqualTo(payType).andDelFlagEqualTo("0");
			payTypeList = payTypeService.selectByExample(payTypeExample);
			
		}
		if(payTypeList != null && payTypeList.size() > 0){
			routeCode = payTypeList.get(0).getRouteCode();
			aisleType = payTypeList.get(0).getAisleType();
		}
		map.put("routeCode", routeCode);
		map.put("aisleType", aisleType);
		return map;
	}
	
	
	
	
	@RequestMapping("/testT/testNotice")
	public void testNotice(HttpServletRequest request,HttpServletResponse response){
		try {
		//	PayRouteExample payRouteExample=new PayRouteExample();
		//	payRouteExample.createCriteria().andRouteCodeEqualTo(RouteCodeConstant.ESK_ROUTE_CODE).andTranCodeEqualTo("003").andDelFlagEqualTo("1");
		//	List<PayRoute> list = payRouteService.selectByExample(payRouteExample);
		//	System.out.println(list.size());
			
			PayResultNoticeLog payResultNoticeLog = new PayResultNoticeLog();
			payResultNoticeLog.setMemberCode("11111");
			payResultNoticeLog.setOrderCode("22222");
			payResultNoticeLog.setOrderNumOuter("33333");
			payResultNoticeLog.setReturnUrl("http://");
			payResultNoticeLog.setNotifyContent("444444");
			payResultNoticeLog.setReceiveContent("55555");
			payResultNoticeLogService.insertNoticeLog(payResultNoticeLog);
			
			//EskNotice notice = new EskNotice();
			//notice.setNoticeData("111111111");
			//notice.setOrderNumber("201700000222");
			//eskNoticeService.insertNotice(notice);
			response.getWriter().write("000000");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	@RequestMapping("/testT/getMerchatCode")
	public void testTT(){
		String payWay = MSPayWayConstant.BDZF;
		String bankType = "103567081121";
		String bankName = "中国农业银行股份有限公司怀化新都支行";
		JSONObject testRegisterMsAccount = testRegisterMsAccount(payWay, bankType, bankName);
		System.out.println(testRegisterMsAccount.toString());
	}
	
	
	
		@ResponseBody
		@RequestMapping("/testQrPay")
		public JSONObject testQrPay(HttpServletRequest request,HttpServletResponse response) {
				
			JSONObject result = new JSONObject();
			//String reqMsgId=CommonUtil.getOrderCode();
			try {
				
				
				
				String orderCode = CommonUtil.getOrderCode();
				
				
				String callBack = SysConfig.serverUrl + "/debitNote/sdPayNotify";
				String frontUrl = SysConfig.frontUrl + "/debitNote/sdResult";
				// 调用支付通道
				String serverUrl = "https://cashier.sandpay.com.cn/qr/api/order/create";
				//PublicKey yhPubKey = null;
				//yhPubKey = CryptoUtil.getEskRSAPublicKey();
				//yhPubKey = CryptoUtil.getRSAPublicKey(false);
				//PrivateKey hzfPriKey = CryptoUtil.getRSAPrivateKey();
				
				
				CertUtil.init("classpath:"+EnvironmentUtil.propertyPath + "sdkey/cd-qz-gs.cer", 
						"classpath:"+EnvironmentUtil.propertyPath + "sdkey/cd-qz-ss.pfx", "123654");
				
				
				SandpayRequestHead head = new SandpayRequestHead();
				QrOrderCreateRequestBody body = new QrOrderCreateRequestBody();
				
				QrOrderCreateRequest req = new QrOrderCreateRequest();
				req.setHead(head);
				req.setBody(body);
				
				
				PacketTool.setDefaultRequestHead(head, "sandpay.trade.precreate", "00000005", "19268240");
				body.setPayTool("0402");  //支付工具 0401：支付宝扫码 0402：微信扫码
				body.setOrderCode(orderCode);  //商户订单号
				body.setLimitPay("1");  //限定支付方式 支付工具为微信扫码有效 1-限定不能使用信用卡
				body.setTotalAmount("000000000001");  //订单金额
				body.setSubject("话费充值");  //订单标题
				body.setBody("用户购买话费0.01");  //订单描述
				body.setTxnTimeOut("20171205170000");  //订单超时时间
				body.setStoreId("");  //商户门店编号
				body.setTerminalId("");  //商户终端编号
				body.setOperatorId("");  //操作员编号
				body.setNotifyUrl(callBack);  //异步通知地址
				body.setBizExtendParams("");  //业务扩展参数
				body.setMerchExtendParams("");  //商户扩展参数
				body.setExtend("");  //扩展域
				
				SandOrderPayResponse res =  SandpayClient.execute(req, serverUrl);
				
				
				
				
				
			} catch (ArgException e) {
				result.put("returnCode", "4004");
				result.put("returnMsg", "子商户号配置失败");
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
