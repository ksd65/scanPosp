package com.epay.scanposp.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.net.URLEncoder;
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
import com.epay.scanposp.common.constant.CJConfig;
import com.epay.scanposp.common.constant.DDBConfig;
import com.epay.scanposp.common.constant.ESKConfig;
import com.epay.scanposp.common.constant.HLBConfig;
import com.epay.scanposp.common.constant.MSConfig;
import com.epay.scanposp.common.constant.POSPConfig;
import com.epay.scanposp.common.constant.RFConfig;
import com.epay.scanposp.common.constant.SDConfig;
import com.epay.scanposp.common.constant.SysConfig;
import com.epay.scanposp.common.constant.TBConfig;
import com.epay.scanposp.common.constant.TLConfig;
import com.epay.scanposp.common.constant.WxConfig;
import com.epay.scanposp.common.constant.XFConfig;
import com.epay.scanposp.common.constant.ZHZFConfig;
import com.epay.scanposp.common.excep.ArgException;
import com.epay.scanposp.common.utils.CommonUtil;
import com.epay.scanposp.common.utils.DateUtil;
import com.epay.scanposp.common.utils.DeviceRequestUtil;
import com.epay.scanposp.common.utils.EnvironmentUtil;
import com.epay.scanposp.common.utils.HttpUtil;
import com.epay.scanposp.common.utils.JsonBeanReleaseUtil;
import com.epay.scanposp.common.utils.Probability;
import com.epay.scanposp.common.utils.SecurityUtil;
import com.epay.scanposp.common.utils.StringUtil;
import com.epay.scanposp.common.utils.ValidateUtil;
import com.epay.scanposp.common.utils.XmlConvertUtil;
import com.epay.scanposp.common.utils.cj.entity.QueryRequestEntity;
import com.epay.scanposp.common.utils.cj.entity.QueryResponseEntity;
import com.epay.scanposp.common.utils.cj.util.Bean2QueryStrUtil;
import com.epay.scanposp.common.utils.cj.util.BeanToMapUtil;
import com.epay.scanposp.common.utils.cj.util.HttpURLConection;
import com.epay.scanposp.common.utils.cj.util.MD5Util;
import com.epay.scanposp.common.utils.cj.util.SignatureUtil;
import com.epay.scanposp.common.utils.constant.MSPayWayConstant;
import com.epay.scanposp.common.utils.constant.PayTypeConstant;
import com.epay.scanposp.common.utils.constant.RouteCodeConstant;
import com.epay.scanposp.common.utils.constant.TranCodeConstant;
import com.epay.scanposp.common.utils.ddb.HttpClientUtil;
import com.epay.scanposp.common.utils.epaySecurityUtil.EpaySignUtil;
import com.epay.scanposp.common.utils.esk.Key;
import com.epay.scanposp.common.utils.hlb.Disguiser;
import com.epay.scanposp.common.utils.hx.HxUtils;
import com.epay.scanposp.common.utils.ms.CryptoUtil;
import com.epay.scanposp.common.utils.ms.HttpClient4Util;
import com.epay.scanposp.common.utils.ms.MSCommonUtil;
import com.epay.scanposp.common.utils.posp.ApiUtils;
import com.epay.scanposp.common.utils.sand.SandpayClient;
import com.epay.scanposp.common.utils.sand.SandpayRequestHead;
import com.epay.scanposp.common.utils.sand.SandpayResponseHead;
import com.epay.scanposp.common.utils.sand.util.CertUtil;
import com.epay.scanposp.common.utils.sand.util.PacketTool;
import com.epay.scanposp.common.utils.tb.SignUtil;
import com.epay.scanposp.common.utils.tl.HttpService;
import com.epay.scanposp.common.utils.zhzf.HttpUtils;
import com.epay.scanposp.common.utils.zhzf.MD5;
import com.epay.scanposp.entity.Account;
import com.epay.scanposp.entity.AccountExample;
import com.epay.scanposp.entity.BusinessCategory;
import com.epay.scanposp.entity.DebitNote;
import com.epay.scanposp.entity.DebitNoteExample;
import com.epay.scanposp.entity.DebitNoteIp;
import com.epay.scanposp.entity.EpayCode;
import com.epay.scanposp.entity.EpayCodeExample;
import com.epay.scanposp.entity.EskNotice;
import com.epay.scanposp.entity.MemberInfo;
import com.epay.scanposp.entity.MemberInfoExample;
import com.epay.scanposp.entity.MemberInfoExample.Criteria;
import com.epay.scanposp.entity.MemberIpRule;
import com.epay.scanposp.entity.MemberIpRuleExample;
import com.epay.scanposp.entity.MemberIpWhiteList;
import com.epay.scanposp.entity.MemberIpWhiteListExample;
import com.epay.scanposp.entity.MemberMerchantCode;
import com.epay.scanposp.entity.MemberMerchantCodeExample;
import com.epay.scanposp.entity.MemberMerchantKey;
import com.epay.scanposp.entity.MemberMerchantKeyExample;
import com.epay.scanposp.entity.MemberPayType;
import com.epay.scanposp.entity.MemberPayTypeExample;
import com.epay.scanposp.entity.MemberPayee;
import com.epay.scanposp.entity.MsResultNotice;
import com.epay.scanposp.entity.MsResultNoticeExample;
import com.epay.scanposp.entity.PayQrCode;
import com.epay.scanposp.entity.PayQrCodeTemp;
import com.epay.scanposp.entity.PayQrCodeTotal;
import com.epay.scanposp.entity.PayResultNotice;
import com.epay.scanposp.entity.PayResultNoticeExample;
import com.epay.scanposp.entity.PayType;
import com.epay.scanposp.entity.PayTypeDefault;
import com.epay.scanposp.entity.PayTypeDefaultExample;
import com.epay.scanposp.entity.PayTypeExample;
import com.epay.scanposp.entity.PayTypeRule;
import com.epay.scanposp.entity.PayTypeRuleExample;
import com.epay.scanposp.entity.Payee;
import com.epay.scanposp.entity.RoutewayDraw;
import com.epay.scanposp.entity.RoutewayDrawExample;
import com.epay.scanposp.entity.SysCommonConfig;
import com.epay.scanposp.entity.SysCommonConfigExample;
import com.epay.scanposp.entity.SysOffice;
import com.epay.scanposp.entity.SysOfficeExample;
import com.epay.scanposp.entity.TradeDetail;
import com.epay.scanposp.entity.TradeDetailDaily;
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
import com.epay.scanposp.service.CommonUtilService;
import com.epay.scanposp.service.DebitNoteIpService;
import com.epay.scanposp.service.DebitNoteService;
import com.epay.scanposp.service.EpayCodeService;
import com.epay.scanposp.service.EskNoticeService;
import com.epay.scanposp.service.MemberInfoService;
import com.epay.scanposp.service.MemberIpRuleService;
import com.epay.scanposp.service.MemberIpWhiteListService;
import com.epay.scanposp.service.MemberMerchantCodeService;
import com.epay.scanposp.service.MemberMerchantKeyService;
import com.epay.scanposp.service.MemberOpenidService;
import com.epay.scanposp.service.MemberPayTypeService;
import com.epay.scanposp.service.MemberPayeeService;
import com.epay.scanposp.service.MsResultNoticeService;
import com.epay.scanposp.service.MsbillService;
import com.epay.scanposp.service.PayQrCodeService;
import com.epay.scanposp.service.PayQrCodeTempService;
import com.epay.scanposp.service.PayResultNoticeLogService;
import com.epay.scanposp.service.PayResultNoticeService;
import com.epay.scanposp.service.PayResultNotifyService;
import com.epay.scanposp.service.PayRouteService;
import com.epay.scanposp.service.PayTypeDefaultService;
import com.epay.scanposp.service.PayTypeRuleService;
import com.epay.scanposp.service.PayTypeService;
import com.epay.scanposp.service.PayeeService;
import com.epay.scanposp.service.RoutewayDrawService;
import com.epay.scanposp.service.SysCommonConfigService;
import com.epay.scanposp.service.SysOfficeConfigOemService;
import com.epay.scanposp.service.SysOfficeExtendService;
import com.epay.scanposp.service.SysOfficeService;
import com.epay.scanposp.service.TradeDetailDailyService;
import com.epay.scanposp.service.TradeDetailService;
import com.epay.scanposp.service.TradeVolumnDailyService;
import com.epay.scanposp.thread.WeixinTemplateSendThread;
import com.google.gson.Gson;


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
	private TradeDetailDailyService tradeDetailDailyService;
	
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
	
	@Resource
	private DebitNoteIpService debitNoteIpService;
	
	@Resource
	private MemberIpWhiteListService memberIpWhiteListService;
	
	@Resource
	private MemberIpRuleService memberIpRuleService;
	
	@Autowired
	private CommonUtilService commonUtilService;
	
	@Resource
	private PayQrCodeService payQrCodeService;
	
	@Resource
	private PayQrCodeTempService payQrCodeTempService;
	
	@Resource
	private MemberPayeeService memberPayeeService;
	
	@Resource
	private PayeeService payeeService;
	
	@Autowired
	private MemberPayTypeService memberPayTypeService;
	
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
			
			if(RouteCodeConstant.ESK_ROUTE_CODE.equals(routeCode)||RouteCodeConstant.ESKXF_ROUTE_CODE.equals(routeCode)||RouteCodeConstant.ESKHLB_ROUTE_CODE.equals(routeCode)){
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
			
			}else if(RouteCodeConstant.POSP_ROUTE_CODE.equals(routeCode)){
				
				MemberMerchantKeyExample memberMerchantKeyExample = new MemberMerchantKeyExample();
		        memberMerchantKeyExample.createCriteria().andRouteCodeEqualTo(routeCode).andMerchantCodeEqualTo(debitNote.getMerchantCode()).andDelFlagEqualTo("0");
		        List<MemberMerchantKey> keyList = memberMerchantKeyService.selectByExample(memberMerchantKeyExample);
		        if(keyList == null || keyList.size()!=1){
		        	result.put("returnCode", "0008");
					result.put("returnMsg", "商户私钥未配置");
					return result;
		        }
		        MemberMerchantKey merchantKey = keyList.get(0);
		        
		        String serverUrl = POSPConfig.msServerUrl;
		        
		        
		        Map<String, String> params = new HashMap<String, String>();
				params.put("order_no", debitNote.getOrderCode());
				
				params = ApiUtils.formatRequestParams("charge.query", params, merchantKey.getAppId(), merchantKey.getPrivateKey());
				logger.info("POSP网关订单查询请求报文[{}]", JSONObject.fromObject(params).toString());
				String respString = com.epay.scanposp.common.utils.posp.HttpUtils.doPost(serverUrl, params, 60000, 60000);
				logger.info("POSP网关订单查询返回报文[{}]", new Object[] { respString });
				
		       
		        
		        JSONObject resultObj = JSONObject.fromObject(respString);
		        
		        String result_code = resultObj.getString("code");
		        String result_msg = resultObj.getString("message");
		        
		        String result_content = "";
		        if(resultObj.containsKey("data")){
		        	result_content = resultObj.getString("data");
		        }
		        if("0".equals(result_code)){
		        	JSONObject bizObj = JSONObject.fromObject(result_content);
		        	JSONObject resEntity = new JSONObject();
		        	//if("00".equals(bizObj.getString("failure_code"))){
	        		if(bizObj.getInt("status")==20&&bizObj.getInt("paid")==1){
	        			resEntity.put("oriRespType", "S");
	        			resEntity.put("oriRespCode", "000000");
	        			resEntity.put("oriRespMsg", "支付成功");
	        			resEntity.put("totalAmount", String.valueOf(Float.parseFloat(bizObj.getString("amount"))/100.0));
	        		}else if(bizObj.getInt("status")==10&&bizObj.getInt("paid")==0){
	        			resEntity.put("oriRespType", "R");
	        			resEntity.put("oriRespCode", "000001");
	        			resEntity.put("oriRespMsg", "未支付");
	        		}else{
	        			resEntity.put("oriRespType", "E");
	        			resEntity.put("oriRespCode", "000002");
	        			resEntity.put("oriRespMsg", "支付失败");
					}
	        		result.put("resData", resEntity);
		        	
		        }else{
		        	result.put("returnCode", "0012");
					result.put("returnMsg", result_msg);
		        }
		    }else if(RouteCodeConstant.RF_ROUTE_CODE.equals(routeCode)){
		    	MemberMerchantKeyExample memberMerchantKeyExample = new MemberMerchantKeyExample();
		        memberMerchantKeyExample.createCriteria().andRouteCodeEqualTo(routeCode).andMerchantCodeEqualTo(debitNote.getMerchantCode()).andDelFlagEqualTo("0");
		        List<MemberMerchantKey> keyList = memberMerchantKeyService.selectByExample(memberMerchantKeyExample);
		        if(keyList == null || keyList.size()!=1){
		        	result.put("returnCode", "0008");
					result.put("returnMsg", "商户私钥未配置");
					return result;
		        }
		        MemberMerchantKey merchantKey = keyList.get(0);
		        
		        JSONObject reqData = new JSONObject();
				reqData.put("AppKey", debitNote.getMerchantCode());
				reqData.put("OrderNum", debitNote.getOrderCode());
				
				
				String str = StringUtil.orderedKey(reqData);
				String privateKey = merchantKey.getPrivateKey();
				String signData = EpaySignUtil.signSha1(privateKey, str);
				reqData.put("SignStr", signData);
				
				logger.info("瑞付订单查询请求报文[{}]", new Object[] { JSON.toJSONString(reqData) });
				
				String url = RFConfig.msServerUrl + "/order";
				
				String resultMsg = HttpUtil.sendPostRequest(url, reqData.toString());
				logger.info("瑞付订单请求返回报文[{}]", new Object[] { resultMsg });
				
				
		        JSONObject resultObj = JSONObject.fromObject(resultMsg);
		        String result_code = resultObj.getString("ReturnCode");
		        String result_msg = resultObj.getString("ReturnMsg");
		        
		        if("0000".equals(result_code)){
		        	String trade_state = resultObj.getString("RespType");
		        	JSONObject resEntity = new JSONObject();
		        	if("2".equals(trade_state)){
		        		resEntity.put("oriRespType", "S");
		        		resEntity.put("oriRespCode", "000000");
		        		resEntity.put("oriRespMsg", "支付成功");
		        		resEntity.put("totalAmount", resultObj.getString("PayMoney"));

					}else if("1".equals(trade_state)){
						resEntity.put("oriRespType", "R");
						resEntity.put("oriRespCode", "000001");
						resEntity.put("oriRespMsg", "支付中");
					}else{
						resEntity.put("oriRespType", "E");
						resEntity.put("oriRespCode", "000002");
						resEntity.put("oriRespMsg", "支付失败");
					}
		        	result.put("resData", resEntity);
		        }else{
		        	result.put("returnCode", "0012");
					result.put("returnMsg", result_msg);
		        }
		    }else if(RouteCodeConstant.CJ_ROUTE_CODE.equals(routeCode)){
		    	MemberMerchantKeyExample memberMerchantKeyExample = new MemberMerchantKeyExample();
		        memberMerchantKeyExample.createCriteria().andRouteCodeEqualTo(routeCode).andMerchantCodeEqualTo(debitNote.getMerchantCode()).andDelFlagEqualTo("0");
		        List<MemberMerchantKey> keyList = memberMerchantKeyService.selectByExample(memberMerchantKeyExample);
		        if(keyList == null || keyList.size()!=1){
		        	result.put("returnCode", "0008");
					result.put("returnMsg", "商户私钥未配置");
					return result;
		        }
		        MemberMerchantKey merchantKey = keyList.get(0);
		        
		        String serverUrl = CJConfig.msServerUrl+"/quickPayAction/query.action";
		        
		        QueryRequestEntity reqEntity = new QueryRequestEntity();
		        reqEntity.setV_version("1.0.0.0");
		        reqEntity.setV_mid(debitNote.getMerchantCode());
		        reqEntity.setV_oid(debitNote.getOrderCode());
		        reqEntity.setV_type("1");
		        String sign = SignatureUtil.getSign(CommonUtil.beanToMap(reqEntity), merchantKey.getPrivateKey(), logger);
		        reqEntity.setV_sign(sign);
		        Bean2QueryStrUtil bean = new Bean2QueryStrUtil();
				String postStr = bean.bean2QueryStr(reqEntity);
				logger.info("畅捷快捷支付订单查询请求参数[{}]",postStr );
				String respStr = HttpURLConection.httpURLConnectionPOST(serverUrl, postStr);
				logger.info("畅捷快捷支付订单查询返回报文[{}]", new Object[] { respStr });
		        
				if(StringUtils.isNotEmpty(respStr)){
					Gson gson = new Gson();
					QueryResponseEntity respEntity = gson.fromJson(respStr, QueryResponseEntity.class);
					String v_code = respEntity.getV_code();
					Map map = BeanToMapUtil.convertBean(respEntity);
					if(SignatureUtil.checkSign(map, merchantKey.getPrivateKey(), logger)) {
						String v_status = JSONObject.fromObject(respStr).getString("v_status_code");
						JSONObject resEntity = new JSONObject();
						if("00".equals(v_code)&&"0000".equals(v_status)){
							resEntity.put("oriRespType", "S");
							resEntity.put("oriRespCode", "000000");
							resEntity.put("oriRespMsg", "支付成功");
							resEntity.put("totalAmount", respEntity.getV_txnAmt());
		        		}else if("1002".equals(v_status)){
		        			resEntity.put("oriRespType", "R");
		        			resEntity.put("oriRespCode", "000001");
		        			resEntity.put("oriRespMsg", "支付中");
		        		}else{
		        			resEntity.put("oriRespType", "E");
		        			resEntity.put("oriRespCode", "000002");
		        			resEntity.put("oriRespMsg", "支付失败");
						}
						result.put("resData", resEntity);
					}else{
						result.put("returnCode", "0012");
						result.put("returnMsg", "出参验签失败");
					}
				}
		    }else if(RouteCodeConstant.CJWG_ROUTE_CODE.equals(routeCode)){
		    	MemberMerchantKeyExample memberMerchantKeyExample = new MemberMerchantKeyExample();
		        memberMerchantKeyExample.createCriteria().andRouteCodeEqualTo(routeCode).andMerchantCodeEqualTo(debitNote.getMerchantCode()).andDelFlagEqualTo("0");
		        List<MemberMerchantKey> keyList = memberMerchantKeyService.selectByExample(memberMerchantKeyExample);
		        if(keyList == null || keyList.size()!=1){
		        	result.put("returnCode", "0008");
					result.put("returnMsg", "商户私钥未配置");
					return result;
		        }
		        MemberMerchantKey merchantKey = keyList.get(0);
		        
		        String serverUrl = CJConfig.gateServerUrl+"/gateWay/gateWay_query.action";
		        
		        QueryRequestEntity reqEntity = new QueryRequestEntity();
		        reqEntity.setV_version("1.0.0.0");
		        reqEntity.setV_mid(debitNote.getMerchantCode());
		        reqEntity.setV_oid(debitNote.getOrderCode());
		        reqEntity.setV_type("0");
		        String sign = SignatureUtil.getSign(CommonUtil.beanToMap(reqEntity), merchantKey.getPrivateKey(), logger);
		        reqEntity.setV_sign(sign);
		        Bean2QueryStrUtil bean = new Bean2QueryStrUtil();
				String postStr = bean.bean2QueryStr(reqEntity);
				logger.info("畅捷快捷支付订单查询请求参数[{}]",postStr );
				String respStr = HttpURLConection.httpURLConnectionPOST(serverUrl, postStr);
				logger.info("畅捷快捷支付订单查询返回报文[{}]", new Object[] { respStr });
		        
				if(StringUtils.isNotEmpty(respStr)){
					Gson gson = new Gson();
					QueryResponseEntity respEntity = gson.fromJson(respStr, QueryResponseEntity.class);
					String v_code = respEntity.getV_code();
					Map map = BeanToMapUtil.convertBean(respEntity);
				//	if(SignatureUtil.checkSign(map, merchantKey.getPrivateKey(), logger)) {
						String v_status = JSONObject.fromObject(respStr).getString("v_status_code");
						JSONObject resEntity = new JSONObject();
						if("00".equals(v_code)&&"0000".equals(v_status)){
							resEntity.put("oriRespType", "S");
							resEntity.put("oriRespCode", "000000");
							resEntity.put("oriRespMsg", "支付成功");
							resEntity.put("totalAmount", respEntity.getV_txnAmt());
		        		}else if("1002".equals(v_status)){
		        			resEntity.put("oriRespType", "R");
		        			resEntity.put("oriRespCode", "000001");
		        			resEntity.put("oriRespMsg", "支付中");
		        		}else{
		        			resEntity.put("oriRespType", "E");
		        			resEntity.put("oriRespCode", "000002");
		        			resEntity.put("oriRespMsg", "支付失败");
						}
						result.put("resData", resEntity);
				//	}else{
				//		result.put("returnCode", "0012");
				//		result.put("returnMsg", "出参验签失败");
				//	}
				}
		    }else{

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
	
	
	private JSONObject checkLimitMerchantMoney(String routeId,String merchantCode){
		
		JSONObject result = new JSONObject();
		String configName = "LIMIT_MERCHANT_"+routeId+"_"+merchantCode;
		String value = "";
		SysCommonConfigExample sysCommonConfigExample = new SysCommonConfigExample();
		sysCommonConfigExample.or().andNameEqualTo(configName).andDelFlagEqualTo("0");
		List<SysCommonConfig> sysCommonConfig = sysCommonConfigService.selectByExample(sysCommonConfigExample);
		if (sysCommonConfig != null && sysCommonConfig.size() > 0) {
			value = sysCommonConfig.get(0).getValue();
		}
		if(!"".equals(value)){
			BigDecimal merchantLimit = new BigDecimal(value);
			if (null != merchantLimit && merchantLimit.compareTo(BigDecimal.ZERO) > 0){
				Map<String,Object> param = new HashMap<String, Object>();
				param.put("merchantCode", merchantCode);
				param.put("routeId", routeId);
				param.put("txnDate", DateUtil.getDateFormat(new Date(), "yyyyMMdd"));
				Double count = tradeDetailDailyService.countTradeDetailDaily(param);
				count = count == null ? 0 : count;
				if (new BigDecimal(count).compareTo(merchantLimit) > 0) {
					result.put("returnCode", "4004");
					result.put("returnMsg", "已超过商户当日交易总金额，无法交易");
					return result;
				}
			}
		}
		return null;
	}
	
	private JSONObject checkLimitCounts(String routeId){
		
		JSONObject result = new JSONObject();
		String configName = "LIMIT_ROUTE_SECONDS_"+routeId;
		String value = "",value1 = "";
		SysCommonConfigExample sysCommonConfigExample = new SysCommonConfigExample();
		sysCommonConfigExample.or().andNameEqualTo(configName).andDelFlagEqualTo("0");
		List<SysCommonConfig> sysCommonConfig = sysCommonConfigService.selectByExample(sysCommonConfigExample);
		if (sysCommonConfig != null && sysCommonConfig.size() > 0) {
			value = sysCommonConfig.get(0).getValue();
		}
		
		configName = "LIMIT_ROUTE_TIMES_"+routeId;
		sysCommonConfigExample = new SysCommonConfigExample();
		sysCommonConfigExample.or().andNameEqualTo(configName).andDelFlagEqualTo("0");
		sysCommonConfig = sysCommonConfigService.selectByExample(sysCommonConfigExample);
		if (sysCommonConfig != null && sysCommonConfig.size() > 0) {
			value1 = sysCommonConfig.get(0).getValue();
		}
		if(!"".equals(value) && !"".equals(value1)){
			
			Map<String,Object> param = new HashMap<String, Object>();
			param.put("routeId", routeId);
			param.put("seconds", value);
			Integer count = debitNoteIpService.countDebitNoteIpByCondition(param);
			count = count == null ? 0 : count;
			
			BigDecimal limitCount = new BigDecimal(value1);
			if (null != limitCount && limitCount.compareTo(BigDecimal.ZERO) > 0){
				if (new BigDecimal(count).compareTo(limitCount) > 0) {
					result.put("returnCode", "4004");
					result.put("returnMsg", "交易过于频繁，已超过商户限制交易次数，请稍后再试");
					return result;
				}
			}
		}
		return null;
	}
	
	private JSONObject checkMinMoney(String configName, BigDecimal tradeMoney){
		
		JSONObject result = new JSONObject();
		
		String value = "";
		SysCommonConfigExample sysCommonConfigExample = new SysCommonConfigExample();
		sysCommonConfigExample.or().andNameEqualTo(configName).andDelFlagEqualTo("0");
		List<SysCommonConfig> sysCommonConfig = sysCommonConfigService.selectByExample(sysCommonConfigExample);
		if (sysCommonConfig != null && sysCommonConfig.size() > 0) {
			value = sysCommonConfig.get(0).getValue();
		}
		if(!"".equals(value)){
			BigDecimal singleMin = new BigDecimal(value);
			if (null != singleMin && singleMin.compareTo(BigDecimal.ZERO) > 0){
				if (tradeMoney.compareTo(singleMin) < 0) {
					result.put("returnCode", "4004");
					result.put("returnMsg", "单笔交易最小金额为"+singleMin+"元,当前交易已小于最小金额");
					return result;
				}
			}
		}
		return null;
	}
	
	private JSONObject checkLimitIp(String payMethod, String payType, int memberId, String routeCode, String merchantCode, String ip){
		
		JSONObject result = new JSONObject();
		MemberIpWhiteListExample memberIpWhiteListExample = new MemberIpWhiteListExample();
		memberIpWhiteListExample.createCriteria().andPayMethodEqualTo(payMethod).andPayTypeEqualTo(payType).andMemberIdEqualTo(memberId).andDelFlagEqualTo("0");
		List<MemberIpWhiteList> memberIpWhiteList = memberIpWhiteListService.selectByExample(memberIpWhiteListExample);
		if(memberIpWhiteList == null || memberIpWhiteList.size()==0){
			boolean flag = false;
			MemberIpRuleExample memberIpRuleExample = new MemberIpRuleExample();
			memberIpRuleExample.createCriteria().andMemberIdEqualTo(memberId).andRouteCodeEqualTo(routeCode).andMerchantCodeEqualTo(merchantCode).andDelFlagEqualTo("0");
			List<MemberIpRule> memberIpRuleList = memberIpRuleService.selectByExample(memberIpRuleExample);
			if(memberIpRuleList ==null || memberIpRuleList.size()==0){
				memberIpRuleExample = new MemberIpRuleExample();
				memberIpRuleExample.createCriteria().andMemberIdEqualTo(0).andRouteCodeEqualTo(routeCode).andMerchantCodeEqualTo(merchantCode).andDelFlagEqualTo("0");
				memberIpRuleList = memberIpRuleService.selectByExample(memberIpRuleExample);
			}
			
			if(memberIpRuleList !=null && memberIpRuleList.size()>0){
				flag = true;
				for(MemberIpRule rule : memberIpRuleList){
					int seconds = rule.getSeconds();
					int limitTimes = rule.getLimitTimes();
					Map<String,Object> param = new HashMap<String, Object>();
					param.put("memberId", memberId);
					param.put("routeId", routeCode);
					param.put("merchantCode", merchantCode);
					param.put("ip", ip);
					param.put("seconds", seconds);
					Integer count = debitNoteIpService.countDebitNoteIpByCondition(param);
					count = count == null ? 0 : count;
					if(count >= limitTimes){
						logger.info("超过子商户IP访问次数，规则："+seconds+"秒"+limitTimes+"次，访问"+count);
						result.put("returnCode", "4004");
						result.put("returnMsg", "您的IP访问过于频繁，请稍后再访问");
						return result;
					}
				}
			}
			
			if(!flag){
				memberIpRuleExample = new MemberIpRuleExample();
				memberIpRuleExample.createCriteria().andMemberIdEqualTo(memberId).andRouteCodeEqualTo(routeCode).andPayMethodEqualTo(payMethod).andPayTypeEqualTo(payType).andDelFlagEqualTo("0");
				List<MemberIpRule> memberIpRouteRuleList = memberIpRuleService.selectByExample(memberIpRuleExample);
				
				if(memberIpRouteRuleList ==null || memberIpRouteRuleList.size()==0){
					memberIpRuleExample = new MemberIpRuleExample();
					memberIpRuleExample.createCriteria().andMemberIdEqualTo(0).andRouteCodeEqualTo(routeCode).andPayMethodEqualTo(payMethod).andPayTypeEqualTo(payType).andDelFlagEqualTo("0");
					memberIpRouteRuleList = memberIpRuleService.selectByExample(memberIpRuleExample);
				}
				if(memberIpRouteRuleList !=null && memberIpRouteRuleList.size()>0){
					flag = true;
					for(MemberIpRule rule : memberIpRouteRuleList){
						int seconds = rule.getSeconds();
						int limitTimes = rule.getLimitTimes();
						Map<String,Object> param = new HashMap<String, Object>();
						param.put("memberId", memberId);
						param.put("routeId", routeCode);
						param.put("txnMethod", payMethod);
						String payTypeStr = "1";
						if("WX".equals(payType)){
							payTypeStr = "1";
						}else if("QQ".equals(payType)){
							payTypeStr = "3";
						}else if("ZFB".equals(payType)){
							payTypeStr = "2";
						}else if("JD".equals(payType)){
							payTypeStr = "5";
						}
						param.put("txnType", payTypeStr);
						param.put("ip", ip);
						param.put("seconds", seconds);
						Integer count = debitNoteIpService.countDebitNoteIpByCondition(param);
						count = count == null ? 0 : count;
						if(count >= limitTimes){
							logger.info("超过通道IP访问次数，规则："+seconds+"秒"+limitTimes+"次，访问"+count);
							result.put("returnCode", "4004");
							result.put("returnMsg", "您的IP访问过于频繁，请稍后再访问");
							return result;
						}
					}
				}
			}
			
			if(!flag){
				memberIpRuleExample = new MemberIpRuleExample();
				memberIpRuleExample.createCriteria().andMemberIdEqualTo(memberId).andRouteCodeEqualTo("0").andPayMethodEqualTo(payMethod).andPayTypeEqualTo(payType).andDelFlagEqualTo("0");
				List<MemberIpRule> memberIpPayRuleList = memberIpRuleService.selectByExample(memberIpRuleExample);
				
				if(memberIpPayRuleList ==null || memberIpPayRuleList.size()==0){
					memberIpRuleExample = new MemberIpRuleExample();
					memberIpRuleExample.createCriteria().andMemberIdEqualTo(0).andRouteCodeEqualTo("0").andPayMethodEqualTo(payMethod).andPayTypeEqualTo(payType).andDelFlagEqualTo("0");
					memberIpPayRuleList = memberIpRuleService.selectByExample(memberIpRuleExample);
				}
				if(memberIpPayRuleList !=null && memberIpPayRuleList.size()>0){
					flag = true;
					for(MemberIpRule rule : memberIpPayRuleList){
						int seconds = rule.getSeconds();
						int limitTimes = rule.getLimitTimes();
						Map<String,Object> param = new HashMap<String, Object>();
						param.put("memberId", memberId);
						param.put("txnMethod", payMethod);
						String payTypeStr = "1";
						if("WX".equals(payType)){
							payTypeStr = "1";
						}else if("QQ".equals(payType)){
							payTypeStr = "3";
						}else if("ZFB".equals(payType)){
							payTypeStr = "2";
						}else if("JD".equals(payType)){
							payTypeStr = "5";
						}
						param.put("txnType", payTypeStr);
						param.put("ip", ip);
						param.put("seconds", seconds);
						Integer count = debitNoteIpService.countDebitNoteIpByCondition(param);
						count = count == null ? 0 : count;
						if(count >= limitTimes){
							logger.info("超过支付方式IP访问次数，规则："+seconds+"秒"+limitTimes+"次，访问"+count);
							result.put("returnCode", "4004");
							result.put("returnMsg", "您的IP访问过于频繁，请稍后再访问");
							return result;
						}
					}
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
		
		String payType = "";
		if(reqDataJson.containsKey("payType")){
			payType = reqDataJson.getString("payType");
		}
		if(payType != null && !"".equals(payType)){
			srcStr.append("&payType="+payType);
		}else{
			payType = "1";
		}
		
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
		
		String userAgent = "";
		if(reqDataJson.containsKey("userAgent")){
			userAgent = reqDataJson.getString("userAgent");
		}
		
		result = validMemberInfoForH5(memberCode, orderNum, payMoney, "3",  srcStr.toString(), signStr, callbackUrl,sceneInfo,ip,payType,userAgent);
		
		
		
		return result;
	}
	
	public JSONObject validMemberInfoForH5(String memberCode,String orderNum,String payMoney,String platformType,String signOrginalStr,String signedStr,String callbackUrl,String sceneInfo,String ip,String payType,String userAgent){
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
		String payTypeStr = PayTypeConstant.PAY_TYPE_WX;
		if("3".equals(payType)){
			payTypeStr = PayTypeConstant.PAY_TYPE_QQ;
		}else if("5".equals(payType)){
			payTypeStr = PayTypeConstant.PAY_TYPE_JD;
		}else if("2".equals(payType)){
			payTypeStr = PayTypeConstant.PAY_TYPE_ZFB;
		}
		
		MemberPayTypeExample memberPayTypeExample = new MemberPayTypeExample();
		memberPayTypeExample.createCriteria().andMemberIdEqualTo(memberInfo.getId()).andPayMethodEqualTo(PayTypeConstant.PAY_METHOD_H5).andPayTypeEqualTo(payTypeStr).andDelFlagEqualTo("0");
		List<MemberPayType> memberPayTypeList =  memberPayTypeService.selectByExample(memberPayTypeExample);
		if(memberPayTypeList==null || memberPayTypeList.size()==0){
			result.put("returnCode", "0008");
			result.put("returnMsg", "对不起，商户未开通该支付权限");
			return result;
		}
		
		MemberPayType memberPayType = memberPayTypeList.get(0);
		
		Map<String,String> rtMap  = getRouteCodeAndAisleType(memberInfo.getId(),PayTypeConstant.PAY_METHOD_H5,payTypeStr);
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
		if("1".equals(payType)){
			if(merchantCode.getWxMerchantCode()==null || "".equals(merchantCode.getWxMerchantCode())){
				result.put("returnCode", "0008");
				result.put("returnMsg", "对不起，商户未开通微信H5支付权限");
				return result;
			}
		}else if("3".equals(payType)){
			if(merchantCode.getQqMerchantCode()==null || "".equals(merchantCode.getQqMerchantCode())){
				result.put("returnCode", "0008");
				result.put("returnMsg", "对不起，商户未开通QQH5支付权限");
				return result;
			}
		}else if("2".equals(payType)){
			if(merchantCode.getZfbMerchantCode()==null || "".equals(merchantCode.getZfbMerchantCode())){
				result.put("returnCode", "0008");
				result.put("returnMsg", "对不起，商户未开通支付宝H5支付权限");
				return result;
			}
		}
		
		boolean merchantFlag = false;
		PayTypeDefaultExample payTypeDefaultExample = new PayTypeDefaultExample();
		payTypeDefaultExample.createCriteria().andPayMethodEqualTo(PayTypeConstant.PAY_METHOD_H5).andPayTypeEqualTo(payTypeStr).andMemberIdEqualTo(memberInfo.getId()).andDelFlagEqualTo("0");
		List<PayTypeDefault> payTypeDefaultList = payTypeDefaultService.selectByExample(payTypeDefaultExample);
		if(payTypeDefaultList == null || payTypeDefaultList.size()==0){
			payTypeDefaultExample = new PayTypeDefaultExample();
			payTypeDefaultExample.createCriteria().andPayMethodEqualTo(PayTypeConstant.PAY_METHOD_H5).andPayTypeEqualTo(payTypeStr).andMemberIdEqualTo(0).andDelFlagEqualTo("0");
			payTypeDefaultList = payTypeDefaultService.selectByExample(payTypeDefaultExample);
		}
		
		if(payTypeDefaultList == null || payTypeDefaultList.size()==0){//走概率计算
			PayTypeRuleExample payTypeRuleExample = new PayTypeRuleExample();
			
			boolean memberType = false;
			payTypeRuleExample.createCriteria().andPayMethodEqualTo(PayTypeConstant.PAY_METHOD_H5).andPayTypeEqualTo(payTypeStr).andMemberIdEqualTo(memberInfo.getId()).andDelFlagEqualTo("0");
			List<PayTypeRule> memPayTypeRuleList = payTypeRuleService.selectByExample(payTypeRuleExample);
			if(memPayTypeRuleList !=null && memPayTypeRuleList.size()>0){
				memberType = true;
			}
			
			String time = DateFormatUtils.format(new Date(), "HHmmss");
			Integer ruleMemberId = 0;
			if(memberType){
				logger.info(orderNum+"进入指定商户规则概率计算");
				ruleMemberId = memberInfo.getId();
			}
			if(StringUtil.isRealDouble(payMoney)){//订单金额有小数点
				payTypeRuleExample = new PayTypeRuleExample();
				payTypeRuleExample.createCriteria().andPayMethodEqualTo(PayTypeConstant.PAY_METHOD_H5).andPayTypeEqualTo(payTypeStr).andMemberIdEqualTo(ruleMemberId).andRuleTypeEqualTo("3").andMinMoneyLessThan(new BigDecimal(payMoney)).andMaxMoneyGreaterThanOrEqualTo(new BigDecimal(payMoney)).andBeginTimeLessThanOrEqualTo(time).andEndTimeGreaterThanOrEqualTo(time).andDelFlagEqualTo("0");//小数点金额概率规则
				payTypeRuleExample.setOrderByClause(" id asc ");
				List<PayTypeRule> payTypeRuleList = payTypeRuleService.selectByExample(payTypeRuleExample);
				if(payTypeRuleList !=null && payTypeRuleList.size()>0){
					logger.info(orderNum+"进入小数点规则概率计算");
					List<Map<String,Object>> rateList=new ArrayList<>();
		            for(int i=0;i<payTypeRuleList.size();i++){
						Map<String, Object> rateMap = new HashMap<String, Object>();
						rateMap.put("rate", payTypeRuleList.get(i).getRuleRate().doubleValue());
						rateList.add(rateMap);
					}
		            int index = Probability.getProbabilityRange(rateList);
		            if(index >= payTypeRuleList.size()){
		            	logger.info(orderNum+"不在小数点规则概率计算范围内");
		            }else{
		            	PayTypeRule payTypeRule = payTypeRuleList.get(index);
		            	logger.info(orderNum+"小数点规则概率计算商户编码："+payTypeRule.getMerchantCode()+"，通道编码："+payTypeRule.getRouteCode());
		            	if("1".equals(payType)){
		            		merchantCode.setWxMerchantCode(payTypeRule.getMerchantCode());
		            	}else if("3".equals(payType)){
		            		merchantCode.setQqMerchantCode(payTypeRule.getMerchantCode());
		            	}
		            	routeCode = payTypeRule.getRouteCode();
		            	aisleType = payTypeRule.getAisleType();
		            	merchantFlag = true;
		            }
				}
			}
			
			if(!merchantFlag){
				payTypeRuleExample = new PayTypeRuleExample();
				payTypeRuleExample.createCriteria().andPayMethodEqualTo(PayTypeConstant.PAY_METHOD_H5).andPayTypeEqualTo(payTypeStr).andMemberIdEqualTo(ruleMemberId).andRuleTypeEqualTo("2").andMinMoneyLessThan(new BigDecimal(payMoney)).andMaxMoneyGreaterThanOrEqualTo(new BigDecimal(payMoney)).andBeginTimeLessThanOrEqualTo(time).andEndTimeGreaterThanOrEqualTo(time).andDelFlagEqualTo("0");//金额概率规则
				payTypeRuleExample.setOrderByClause(" id asc ");
				List<PayTypeRule> payTypeRuleList = payTypeRuleService.selectByExample(payTypeRuleExample);
				if(payTypeRuleList !=null && payTypeRuleList.size()>0){
					logger.info(orderNum+"进入金额规则概率计算");
					List<Map<String,Object>> rateList=new ArrayList<>();
		            for(int i=0;i<payTypeRuleList.size();i++){
						Map<String, Object> rateMap = new HashMap<String, Object>();
						rateMap.put("rate", payTypeRuleList.get(i).getRuleRate().doubleValue());
						rateList.add(rateMap);
					}
		            int index = Probability.getProbabilityRange(rateList);
		            if(index >= payTypeRuleList.size()){
		            	logger.info(orderNum+"不在金额规则概率计算范围内");
		            }else{
		            	PayTypeRule payTypeRule = payTypeRuleList.get(index);
		            	logger.info(orderNum+"金额规则概率计算商户编码："+payTypeRule.getMerchantCode()+"，通道编码："+payTypeRule.getRouteCode());
		            	if("1".equals(payType)){
		            		merchantCode.setWxMerchantCode(payTypeRule.getMerchantCode());
		            	}else if("3".equals(payType)){
		            		merchantCode.setQqMerchantCode(payTypeRule.getMerchantCode());
		            	}
		            	routeCode = payTypeRule.getRouteCode();
		            	aisleType = payTypeRule.getAisleType();
		            	merchantFlag = true;
		            }
				}
			}
			
			if(!merchantFlag){
				payTypeRuleExample = new PayTypeRuleExample();
				payTypeRuleExample.createCriteria().andPayMethodEqualTo(PayTypeConstant.PAY_METHOD_H5).andPayTypeEqualTo(payTypeStr).andMemberIdEqualTo(ruleMemberId).andRuleTypeEqualTo("1").andBeginTimeLessThanOrEqualTo(time).andEndTimeGreaterThanOrEqualTo(time).andDelFlagEqualTo("0");//默认规则
				payTypeRuleExample.setOrderByClause(" id asc ");
				List<PayTypeRule> payTypeRuleList = payTypeRuleService.selectByExample(payTypeRuleExample);
				if(payTypeRuleList !=null && payTypeRuleList.size()>0){
					logger.info(orderNum+"进入默认规则概率计算");
					List<Map<String,Object>> rateList=new ArrayList<>();
		            for(int i=0;i<payTypeRuleList.size();i++){
						Map<String, Object> rateMap = new HashMap<String, Object>();
						rateMap.put("rate", payTypeRuleList.get(i).getRuleRate().doubleValue());
						rateList.add(rateMap);
					}
		            int index = Probability.getProbabilityRange(rateList);
		            if(index >= payTypeRuleList.size()){
		            	logger.info(orderNum+"不在默认规则概率计算范围内，退出");
		            }else{
		            	PayTypeRule payTypeRule = payTypeRuleList.get(index);
		            	logger.info(orderNum+"默认规则概率计算商户编码："+payTypeRule.getMerchantCode()+"，通道编码："+payTypeRule.getRouteCode());
		            	if("1".equals(payType)){
		            		merchantCode.setWxMerchantCode(payTypeRule.getMerchantCode());
		            	}else if("3".equals(payType)){
		            		merchantCode.setQqMerchantCode(payTypeRule.getMerchantCode());
		            	}
		            	routeCode = payTypeRule.getRouteCode();
		            	aisleType = payTypeRule.getAisleType();
		            	merchantFlag = true;
		            }
				}
			}
			if(!merchantFlag){
				logger.info(orderNum+"没有轮询到商户编码，退出");
				result.put("returnCode", "0008");
				result.put("returnMsg", "通道维护中，请稍后再试");
				return result;
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
			memberInfo.setSettleType("1");
			if("1".equals(payType)){
				result = tbH5Pay(platformType,memberInfo,memberPayType, payMoney, orderNum,sceneInfo,ip, callbackUrl , merchantCode,userAgent );
			}
		}else if(RouteCodeConstant.ESK_ROUTE_CODE.equals(routeCode)){
			memberInfo.setSettleType("1");
			if("1".equals(payType)){
				result = eskH5Pay(platformType,memberInfo,memberPayType, payMoney, orderNum,sceneInfo,ip, callbackUrl , merchantCode,userAgent,routeCode,aisleType );
			}else if("3".equals(payType)){
				result = esk001H5Pay(platformType,memberInfo,memberPayType, payMoney, orderNum,sceneInfo,ip, callbackUrl , merchantCode,userAgent,routeCode,aisleType,payType );
			}else if("5".equals(payType)){
				result = esk001H5Pay(platformType,memberInfo,memberPayType, payMoney, orderNum,sceneInfo,ip, callbackUrl , merchantCode,userAgent,routeCode,aisleType,payType );
			}
		}else if(RouteCodeConstant.XF_ROUTE_CODE.equals(routeCode)){
			result = xfH5Pay(platformType,memberInfo,memberPayType, payMoney, orderNum,sceneInfo,ip, callbackUrl , merchantCode );
		}else if(RouteCodeConstant.RF_ROUTE_CODE.equals(routeCode)){
			memberInfo.setSettleType("0");
			if("1".equals(payType)){
				result = rfH5Pay(platformType,memberInfo,memberPayType, payMoney, orderNum,sceneInfo,ip, callbackUrl , merchantCode,userAgent );
			}
		}else if(RouteCodeConstant.ESKXF_ROUTE_CODE.equals(routeCode)){
			memberInfo.setSettleType("0");
			if("1".equals(payType)){
				result = eskH5Pay(platformType,memberInfo,memberPayType, payMoney, orderNum,sceneInfo,ip, callbackUrl , merchantCode,userAgent,routeCode,aisleType );
			}else if("3".equals(payType)){
				result = esk001H5Pay(platformType,memberInfo,memberPayType, payMoney, orderNum,sceneInfo,ip, callbackUrl , merchantCode,userAgent ,routeCode,aisleType,payType);
			}
		}else if(RouteCodeConstant.ZHZF_ROUTE_CODE.equals(routeCode)){
			memberInfo.setSettleType("0");
			if("3".equals(payType)){
				result = zhzfH5Pay(platformType,memberInfo,memberPayType, payMoney, orderNum,sceneInfo,ip, callbackUrl , merchantCode,userAgent );
			}
		}else if(RouteCodeConstant.POSP_ROUTE_CODE.equals(routeCode)){
			memberInfo.setSettleType("0");
			if("1".equals(payType)){
				result = pospH5Pay(platformType,memberInfo,memberPayType, payMoney, orderNum,sceneInfo,ip, callbackUrl , merchantCode,userAgent );
			}
		}else if(RouteCodeConstant.ESKHLB_ROUTE_CODE.equals(routeCode)){
			memberInfo.setSettleType("0");
			if("3".equals(payType)){
				result = esk001H5Pay(platformType,memberInfo,memberPayType, payMoney, orderNum,sceneInfo,ip, callbackUrl , merchantCode,userAgent ,routeCode,aisleType,payType);
			}
		}else if(RouteCodeConstant.WW_ROUTE_CODE.equals(routeCode)){
			memberInfo.setSettleType("0");
			if("1".equals(payType)||"3".equals(payType)){
				result = wwH5Pay(platformType,memberInfo,memberPayType, payMoney, orderNum,sceneInfo,ip, callbackUrl , merchantCode,userAgent ,payType);
			}
		}else if(RouteCodeConstant.HLB_ROUTE_CODE.equals(routeCode)){
			memberInfo.setSettleType("0");
			if("3".equals(payType)){
				result = hlbH5Pay(platformType,memberInfo,memberPayType, payMoney, orderNum,sceneInfo,ip, callbackUrl , merchantCode,userAgent ,routeCode,aisleType,payType);
			}
		}else if(RouteCodeConstant.DDB_ROUTE_CODE.equals(routeCode)){//多得宝
			memberInfo.setSettleType("1");
			if("1".equals(payType)||"3".equals(payType)){
				result = ddbH5Pay(platformType,memberInfo, memberPayType,payMoney, orderNum,sceneInfo,ip, callbackUrl , merchantCode,userAgent,routeCode,aisleType,payType );
			}
		}else if(RouteCodeConstant.GRSM_ROUTE_CODE.equals(routeCode)){//多得宝
			memberInfo.setSettleType("1");
			if("2".equals(payType)){
				result = grsmH5Pay(platformType,payType,memberInfo,memberPayType, payMoney, orderNum, callbackUrl , merchantCode ,routeCode,aisleType,ip);
			}
		}else if(RouteCodeConstant.TLH5_ROUTE_CODE.equals(routeCode)){
			memberInfo.setSettleType("0");
			if("3".equals(payType)){
				result = tlH5Pay(platformType,memberInfo,memberPayType, payMoney, orderNum,sceneInfo,ip, callbackUrl , merchantCode,userAgent ,routeCode,aisleType,payType);
			}
		}else if(RouteCodeConstant.SD_ROUTE_CODE.equals(routeCode)){
			memberInfo.setSettleType("0");
			if("1".equals(payType)){
				result = sdH5Pay(platformType,memberInfo,memberPayType, payMoney, orderNum,sceneInfo,ip, callbackUrl , merchantCode,userAgent ,routeCode,aisleType,payType);
			}
		}else if(RouteCodeConstant.TLWD_ROUTE_CODE.equals(routeCode)){
			memberInfo.setSettleType("0");
			if("2".equals(payType)||"3".equals(payType)){
				result = tlWdH5Pay(platformType,memberInfo,memberPayType, payMoney, orderNum,sceneInfo,ip, callbackUrl , merchantCode,userAgent ,routeCode,aisleType,payType);
			}
		}
		result.put("routeCode", routeCode);
		return result;
	}
	
	
	
	public JSONObject tbH5Pay(String platformType,MemberInfo memberInfo,MemberPayType memberPayType,String payMoney,String orderNumOuter,String sceneInfo,String ip,String callbackUrl,MemberMerchantCode merchantCode,String userAgent) {
		JSONObject result = new JSONObject();
		String orderCode = "";
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
			orderCode = CommonUtil.getOrderCode();
			
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
			debitNote.setUserAgent(userAgent.length()>1000?userAgent.substring(0, 1000):userAgent);
			debitNote.setBrowser(DeviceRequestUtil.getBrowser(userAgent));
			debitNote.setDeviceInfo(DeviceRequestUtil.getDevice(userAgent));
			
			debitNote.setSettleType(memberInfo.getSettleType());
			debitNote.setIp(ip);
			if("0".equals(memberInfo.getSettleType())){
				debitNote.setTradeRate(memberPayType.getT0TradeRate());
			}else{
				debitNote.setTradeRate(memberPayType.getT1TradeRate());
			}
			String configName = "SINGLE_MEMBER_LIMIT_"+memberInfo.getId()+"_006_WX";
			JSONObject memResult = checkLimitMoney(configName, new BigDecimal(payMoney));
			if(null != memResult){
				debitNote.setStatus("9");
				debitNoteService.insertSelective(debitNote);
				return memResult;
			}
			
			configName = "SINGLE_MIN_1007_006_WX";
			JSONObject checkResult = checkMinMoney(configName, new BigDecimal(payMoney));
			if(null != checkResult){
				debitNote.setStatus("5");
				debitNoteService.insertSelective(debitNote);
				return checkResult;
			}
			
			configName = "SINGLE_LIMIT_1007_006_WX";
			JSONObject limitResult = checkLimitMoney(configName, new BigDecimal(payMoney));
			if(null != limitResult){
				debitNote.setStatus("4");
				debitNoteService.insertSelective(debitNote);
				return limitResult;
			}
			
			JSONObject timeResult = commonUtilService.checkLimitIpFail(PayTypeConstant.PAY_METHOD_H5, PayTypeConstant.PAY_TYPE_WX, memberInfo.getId(), ip);
			if(null != timeResult){
				debitNote.setStatus("12");
				debitNoteService.insertSelective(debitNote);
				return timeResult;
			}
			
			JSONObject ipResult = checkLimitIp(PayTypeConstant.PAY_METHOD_H5, PayTypeConstant.PAY_TYPE_WX, memberInfo.getId(), RouteCodeConstant.TB_ROUTE_CODE, merchantCode.getWxMerchantCode(), ip);
			if(null != ipResult){
				debitNote.setStatus("6");
				debitNoteService.insertSelective(debitNote);
				return ipResult;
			}
			
			JSONObject mResult = checkLimitMerchantMoney(RouteCodeConstant.TB_ROUTE_CODE,merchantCode.getWxMerchantCode());
			if(null != mResult){
				debitNote.setStatus("7");
				debitNoteService.insertSelective(debitNote);
				return mResult;
			}
			
			JSONObject tResult = checkLimitCounts(RouteCodeConstant.TB_ROUTE_CODE);
			if(null != tResult){
				debitNote.setStatus("8");
				debitNoteService.insertSelective(debitNote);
				return tResult;
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
	        if("".equals(resultMsg)||"Read timed out".equals(resultMsg)||"connect timed out".equals(resultMsg)){
	        	DebitNoteExample debitNoteExample = new DebitNoteExample();
				debitNoteExample.createCriteria().andOrderCodeEqualTo(orderCode);
				List<DebitNote> debitNotes = debitNoteService.selectByExample(debitNoteExample);
				if (debitNotes != null && debitNotes.size() > 0) {
					DebitNote debitNote_1 = debitNotes.get(0);
					debitNote_1.setStatus("10");
					debitNote_1.setUpdateDate(new Date());
					debitNote_1.setRespMsg(resultMsg.length()>250?resultMsg.substring(0, 250):resultMsg);
					debitNoteService.updateByPrimaryKey(debitNote_1);
				}
				result.put("returnCode", "0009");
				result.put("returnMsg", "接口调用失败："+resultMsg);
				return result;
	        }
	        
	        com.alibaba.fastjson.JSONObject resultObj = com.alibaba.fastjson.JSONObject.parseObject(resultMsg);
	        String result_code = resultObj.getString("result_code");
	        if("200".equals(result_code)){
	        	com.alibaba.fastjson.JSONObject data = (com.alibaba.fastjson.JSONObject)resultObj.get("data");
	        	String payUrl = data.getString("payUrl");
	        	result.put("payUrl", payUrl);
	        	result.put("returnCode", "0000");
				result.put("returnMsg", "成功");
	        }else{
	        	String result_message = resultObj.getString("result_message");
	        	result.put("returnCode", "0009");
				result.put("returnMsg", result_message);
				
				DebitNoteExample debitNoteExample = new DebitNoteExample();
				debitNoteExample.createCriteria().andOrderCodeEqualTo(orderCode);
				List<DebitNote> debitNotes = debitNoteService.selectByExample(debitNoteExample);
				if (debitNotes != null && debitNotes.size() > 0) {
					DebitNote debitNote_1 = debitNotes.get(0);
					debitNote_1.setStatus("2");
					debitNote_1.setUpdateDate(new Date());
					if(!"".equals(result_message)){
						debitNote_1.setRespMsg(result_message.length()>250?result_message.substring(0, 250):result_message);
					}
					debitNoteService.updateByPrimaryKey(debitNote_1);
				}
			}
	        try{
		        DebitNoteIp debitNoteIp = new DebitNoteIp();
		        debitNoteIp.setMemberId(memberInfo.getId());
		        debitNoteIp.setMerchantCode(merchantCode.getWxMerchantCode());
		        debitNoteIp.setOrderCode(orderCode);
		        debitNoteIp.setRouteId(RouteCodeConstant.TB_ROUTE_CODE);
		        debitNoteIp.setTxnMethod(PayTypeConstant.PAY_METHOD_H5);
		        debitNoteIp.setTxnType("1");
		        debitNoteIp.setIp(ip);
		        debitNoteIp.setCreateDate(debitNote.getCreateDate());
				debitNoteIpService.insertSelective(debitNoteIp);
	        }catch(Exception ex){
	        	logger.error(ex.getMessage());
	        }
        } catch (Exception e) {
			logger.error(e.getMessage());
			DebitNoteExample debitNoteExample = new DebitNoteExample();
			debitNoteExample.createCriteria().andOrderCodeEqualTo(orderCode);
			List<DebitNote> debitNotes = debitNoteService.selectByExample(debitNoteExample);
			if (debitNotes != null && debitNotes.size() > 0) {
				DebitNote debitNote_1 = debitNotes.get(0);
				debitNote_1.setStatus("11");
				debitNote_1.setUpdateDate(new Date());
				debitNote_1.setRespMsg(e.getMessage().length()>250?e.getMessage().substring(0, 250):e.getMessage());
				debitNoteService.updateByPrimaryKey(debitNote_1);
			}
			result.put("returnCode", "0096");
			result.put("returnMsg", e.getMessage());
			return result;
		}
		return result;
	}
	
	
	public JSONObject eskH5Pay(String platformType,MemberInfo memberInfo,MemberPayType memberPayType,String payMoney,String orderNumOuter,String sceneInfo,String ip,String callbackUrl,MemberMerchantCode merchantCode,String userAgent,String routeCode,String aisleType) {
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
			debitNote.setRouteId(routeCode);
			debitNote.setStatus("0");
			debitNote.setTxnMethod(PayTypeConstant.PAY_METHOD_H5);
			debitNote.setTxnType("1");
			debitNote.setMemberCode(memberInfo.getWxMemberCode());
			debitNote.setMerchantCode(merchantCode.getWxMerchantCode());
			debitNote.setIp(ip);
			debitNote.setUserAgent(userAgent.length()>1000?userAgent.substring(0, 1000):userAgent);
			debitNote.setBrowser(DeviceRequestUtil.getBrowser(userAgent));
			debitNote.setDeviceInfo(DeviceRequestUtil.getDevice(userAgent));
			debitNote.setSettleType(memberInfo.getSettleType());
			if("0".equals(memberInfo.getSettleType())){
				debitNote.setTradeRate(memberPayType.getT0TradeRate());
			}else{
				debitNote.setTradeRate(memberPayType.getT1TradeRate());
			}
			
			String configName = "SINGLE_MEMBER_LIMIT_"+memberInfo.getId()+"_006_WX";
			JSONObject memResult = checkLimitMoney(configName, new BigDecimal(payMoney));
			if(null != memResult){
				debitNote.setStatus("9");
				debitNoteService.insertSelective(debitNote);
				return memResult;
			} 
			
			configName = "SINGLE_MIN_"+routeCode+"_006_WX";
			JSONObject checkResult = checkMinMoney(configName, new BigDecimal(payMoney));
			if(null != checkResult){
				debitNote.setStatus("5");
				debitNoteService.insertSelective(debitNote);
				return checkResult;
			}
			
			configName = "SINGLE_LIMIT_"+routeCode+"_006_WX";
			JSONObject limitResult = checkLimitMoney(configName, new BigDecimal(payMoney));
			if(null != limitResult){
				debitNote.setStatus("4");
				debitNoteService.insertSelective(debitNote);
				return limitResult;
			}
			
			JSONObject timeResult = commonUtilService.checkLimitIpFail(PayTypeConstant.PAY_METHOD_H5, PayTypeConstant.PAY_TYPE_WX, memberInfo.getId(), ip);
			if(null != timeResult){
				debitNote.setStatus("12");
				debitNoteService.insertSelective(debitNote);
				return timeResult;
			}
			
			JSONObject ipResult = checkLimitIp(PayTypeConstant.PAY_METHOD_H5, PayTypeConstant.PAY_TYPE_WX, memberInfo.getId(), routeCode, merchantCode.getWxMerchantCode(), ip);
			if(null != ipResult){
				debitNote.setStatus("6");
				debitNoteService.insertSelective(debitNote);
				return ipResult;
			}
			
			JSONObject mResult = checkLimitMerchantMoney(routeCode,merchantCode.getWxMerchantCode());
			if(null != mResult){
				debitNote.setStatus("7");
				debitNoteService.insertSelective(debitNote);
				return mResult;
			}
			
			JSONObject tResult = checkLimitCounts(routeCode);
			if(null != tResult){
				debitNote.setStatus("8");
				debitNoteService.insertSelective(debitNote);
				return tResult;
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
			reqData.put("aisleType", aisleType);
			reqData.put("totalAmount", payMoney);
			reqData.put("subject", memberInfo.getName() + " 收款");
			reqData.put("PayType", "1");
			reqData.put("callback", callBack);
			reqData.put("desc", memberInfo.getName() + " 收款");
			reqData.put("terminalId", ip);
			reqData.put("sceneType", "2");
			reqData.put("wapName", "聚合支付");
			reqData.put("onlyId", ip);
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
					result.put("payUrl", respJSONObject.getString("pay_url")+"&redirecturl="+SysConfig.frontUrl+"/debitNote/payCallBack?orderCode="+orderCode);
					//result.put("payUrl", respJSONObject.getString("pay_url"));
					result.put("returnCode", "0000");
					result.put("returnMsg", "成功");
					
				}else{
					result.put("returnCode", "0009");
					result.put("returnMsg", "获取微信信息失败");
				}
			}else{
				
				String result_message = respJSONObject.getString("respMsg");
	        	result.put("returnCode", "0009");
				result.put("returnMsg", result_message);
				
				DebitNoteExample debitNoteExample = new DebitNoteExample();
				debitNoteExample.createCriteria().andOrderCodeEqualTo(orderCode);
				List<DebitNote> debitNotes = debitNoteService.selectByExample(debitNoteExample);
				if (debitNotes != null && debitNotes.size() > 0) {
					DebitNote debitNote_1 = debitNotes.get(0);
					debitNote_1.setStatus("2");
					debitNote_1.setUpdateDate(new Date());
					if(!"".equals(result_message)){
						debitNote_1.setRespMsg(result_message.length()>250?result_message.substring(0, 250):result_message);
					}
					debitNoteService.updateByPrimaryKey(debitNote_1);
				}
			}
			try{
		        DebitNoteIp debitNoteIp = new DebitNoteIp();
		        debitNoteIp.setMemberId(memberInfo.getId());
		        debitNoteIp.setMerchantCode(merchantCode.getWxMerchantCode());
		        debitNoteIp.setOrderCode(orderCode);
		        debitNoteIp.setRouteId(routeCode);
		        debitNoteIp.setTxnMethod(PayTypeConstant.PAY_METHOD_H5);
		        debitNoteIp.setTxnType("1");
		        debitNoteIp.setIp(ip);
		        debitNoteIp.setCreateDate(debitNote.getCreateDate());
				debitNoteIpService.insertSelective(debitNoteIp);
	        }catch(Exception ex){
	        	logger.error(ex.getMessage());
	        }
			
        } catch (Exception e) {
			logger.error(e.getMessage());
			result.put("returnCode", "0096");
			result.put("returnMsg", e.getMessage());
			return result;
		}
		return result;
	}
	
	
	public JSONObject esk001H5Pay(String platformType,MemberInfo memberInfo,MemberPayType memberPayType,String payMoney,String orderNumOuter,String sceneInfo,String ip,String callbackUrl,MemberMerchantCode merchantCode,String userAgent,String routeCode,String aisleType,String payType) {
		JSONObject result = new JSONObject();
		try {
			String merCode = "";
			String memberCode = "";
			String payTypeStr = "";
			String eskPayType = "";
			if("3".equals(payType)){
				merCode = merchantCode.getQqMerchantCode();
				memberCode = memberInfo.getQqMemberCode();
				payTypeStr = PayTypeConstant.PAY_TYPE_QQ;
				eskPayType = "3";
			}else if("5".equals(payType)){
				merCode = merchantCode.getJdMerchantCode();
				memberCode = memberInfo.getJdMemberCode();
				payTypeStr = PayTypeConstant.PAY_TYPE_JD;
				eskPayType = "4";
			}
			// 插入一条收款记录
			String orderCode = CommonUtil.getOrderCode();
			
			DebitNote debitNote = new DebitNote();
			debitNote.setCreateDate(new Date());
			debitNote.setMemberId(memberInfo.getId());
			debitNote.setMoney(new BigDecimal(payMoney));
			debitNote.setOrderCode(orderCode);
			debitNote.setOrderNumOuter(orderNumOuter);
			debitNote.setRouteId(routeCode);
			debitNote.setStatus("0");
			debitNote.setTxnMethod(PayTypeConstant.PAY_METHOD_H5);
			debitNote.setTxnType(payType);
			debitNote.setMemberCode(memberCode);
			debitNote.setMerchantCode(merCode);
			debitNote.setIp(ip);
			debitNote.setUserAgent(userAgent.length()>1000?userAgent.substring(0, 1000):userAgent);
			debitNote.setBrowser(DeviceRequestUtil.getBrowser(userAgent));
			debitNote.setDeviceInfo(DeviceRequestUtil.getDevice(userAgent));
			debitNote.setSettleType(memberInfo.getSettleType());
			if("0".equals(memberInfo.getSettleType())){
				debitNote.setTradeRate(memberPayType.getT0TradeRate());
			}else{
				debitNote.setTradeRate(memberPayType.getT1TradeRate());
			}
			
			String configName = "SINGLE_MEMBER_LIMIT_"+memberInfo.getId()+"_006_"+payTypeStr;
			JSONObject memResult = checkLimitMoney(configName, new BigDecimal(payMoney));
			if(null != memResult){
				debitNote.setStatus("9");
				debitNoteService.insertSelective(debitNote);
				return memResult;
			} 
			
			configName = "SINGLE_MIN_"+routeCode+"_006_"+payTypeStr;
			JSONObject checkResult = checkMinMoney(configName, new BigDecimal(payMoney));
			if(null != checkResult){
				debitNote.setStatus("5");
				debitNoteService.insertSelective(debitNote);
				return checkResult;
			}
			
			configName = "SINGLE_LIMIT_"+routeCode+"_006_"+payTypeStr;
			JSONObject limitResult = checkLimitMoney(configName, new BigDecimal(payMoney));
			if(null != limitResult){
				debitNote.setStatus("4");
				debitNoteService.insertSelective(debitNote);
				return limitResult;
			}
			
			JSONObject timeResult = commonUtilService.checkLimitIpFail(PayTypeConstant.PAY_METHOD_H5, payTypeStr, memberInfo.getId(), ip);
			if(null != timeResult){
				debitNote.setStatus("12");
				debitNoteService.insertSelective(debitNote);
				return timeResult;
			}
			
			JSONObject ipResult = checkLimitIp(PayTypeConstant.PAY_METHOD_H5, payTypeStr, memberInfo.getId(), routeCode, merCode, ip);
			if(null != ipResult){
				debitNote.setStatus("6");
				debitNoteService.insertSelective(debitNote);
				return ipResult;
			}
			
			JSONObject mResult = checkLimitMerchantMoney(routeCode,merCode);
			if(null != mResult){
				debitNote.setStatus("7");
				debitNoteService.insertSelective(debitNote);
				return mResult;
			}
			
			JSONObject tResult = checkLimitCounts(routeCode);
			if(null != tResult){
				debitNote.setStatus("8");
				debitNoteService.insertSelective(debitNote);
				return tResult;
			}
			
			debitNoteService.insertSelective(debitNote);
			
			PayResultNotice payResultNotice = new PayResultNotice();
			payResultNotice.setOrderCode(debitNote.getOrderCode());
			payResultNotice.setOrderNumOuter(orderNumOuter);
			payResultNotice.setPayMoney(debitNote.getMoney());
			payResultNotice.setMemberCode(memberInfo.getCode());
			payResultNotice.setPayType(payType);
			
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
			String tranCode = "001";
			String charset = "utf-8";
			
			JSONObject reqData = new JSONObject();
			reqData.put("merchantCode", merCode);
			reqData.put("scene", "1");
			reqData.put("orderNumber", orderCode);
			reqData.put("tranCode", tranCode);
			reqData.put("aisleType", aisleType);
			reqData.put("totalAmount", payMoney);
			reqData.put("subject", memberInfo.getName() + " 收款");
			reqData.put("PayType", eskPayType);
			reqData.put("callback", callBack);
			reqData.put("desc", memberInfo.getName() + " 收款");
			reqData.put("terminalId", ip);
			reqData.put("onlyId", ip);
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
				if(respJSONObject.containsKey("qrCode")&&!"".equals(respJSONObject.getString("qrCode"))){
					result.put("payUrl", respJSONObject.getString("qrCode"));
					//result.put("payUrl", respJSONObject.getString("pay_url"));
					result.put("returnCode", "0000");
					result.put("returnMsg", "成功");
					
				}else{
					result.put("returnCode", "0009");
					result.put("returnMsg", "获取信息失败");
				}
			}else{
				
				String result_message = respJSONObject.getString("respMsg");
	        	result.put("returnCode", "0009");
				result.put("returnMsg", result_message);
				
				DebitNoteExample debitNoteExample = new DebitNoteExample();
				debitNoteExample.createCriteria().andOrderCodeEqualTo(orderCode);
				List<DebitNote> debitNotes = debitNoteService.selectByExample(debitNoteExample);
				if (debitNotes != null && debitNotes.size() > 0) {
					DebitNote debitNote_1 = debitNotes.get(0);
					debitNote_1.setStatus("2");
					debitNote_1.setUpdateDate(new Date());
					if(!"".equals(result_message)){
						debitNote_1.setRespMsg(result_message.length()>250?result_message.substring(0, 250):result_message);
					}
					debitNoteService.updateByPrimaryKey(debitNote_1);
				}
			}
			try{
		        DebitNoteIp debitNoteIp = new DebitNoteIp();
		        debitNoteIp.setMemberId(memberInfo.getId());
		        debitNoteIp.setMerchantCode(merCode);
		        debitNoteIp.setOrderCode(orderCode);
		        debitNoteIp.setRouteId(routeCode);
		        debitNoteIp.setTxnMethod(PayTypeConstant.PAY_METHOD_H5);
		        debitNoteIp.setTxnType(payType);
		        debitNoteIp.setIp(ip);
		        debitNoteIp.setCreateDate(debitNote.getCreateDate());
				debitNoteIpService.insertSelective(debitNoteIp);
	        }catch(Exception ex){
	        	logger.error(ex.getMessage());
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
	
	public JSONObject xfH5Pay(String platformType,MemberInfo memberInfo,MemberPayType memberPayType,String payMoney,String orderNumOuter,String sceneInfo,String ip,String callbackUrl,MemberMerchantCode merchantCode) {
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
				debitNote.setTradeRate(memberPayType.getT0TradeRate());
			}else{
				debitNote.setTradeRate(memberPayType.getT1TradeRate());
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
	
	public JSONObject rfH5Pay(String platformType,MemberInfo memberInfo,MemberPayType memberPayType,String payMoney,String orderNumOuter,String sceneInfo,String ip,String callbackUrl,MemberMerchantCode merchantCode,String userAgent) {
		JSONObject result = new JSONObject();
		try {
			MemberMerchantKeyExample memberMerchantKeyExample = new MemberMerchantKeyExample();
	        memberMerchantKeyExample.createCriteria().andRouteCodeEqualTo(RouteCodeConstant.RF_ROUTE_CODE).andMerchantCodeEqualTo(merchantCode.getWxMerchantCode()).andDelFlagEqualTo("0");
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
			debitNote.setRouteId(RouteCodeConstant.RF_ROUTE_CODE);
			debitNote.setStatus("0");
			debitNote.setTxnMethod(PayTypeConstant.PAY_METHOD_H5);
			debitNote.setTxnType("1");
			debitNote.setMemberCode(memberInfo.getWxMemberCode());
			debitNote.setMerchantCode(merchantCode.getWxMerchantCode());
			
			debitNote.setSettleType("0");
			debitNote.setIp(ip);
			debitNote.setUserAgent(userAgent.length()>1000?userAgent.substring(0, 1000):userAgent);
			debitNote.setBrowser(DeviceRequestUtil.getBrowser(userAgent));
			debitNote.setDeviceInfo(DeviceRequestUtil.getDevice(userAgent));
			debitNote.setTradeRate(memberPayType.getT0TradeRate());
			
			String configName = "SINGLE_MEMBER_LIMIT_"+memberInfo.getId()+"_006_WX";
			JSONObject memResult = checkLimitMoney(configName, new BigDecimal(payMoney));
			if(null != memResult){
				debitNote.setStatus("9");
				debitNoteService.insertSelective(debitNote);
				return memResult;
			}
			
			configName = "SINGLE_MIN_1008_006_WX";
			JSONObject checkResult = checkMinMoney(configName, new BigDecimal(payMoney));
			if(null != checkResult){
				debitNote.setStatus("5");
				debitNoteService.insertSelective(debitNote);
				return checkResult;
			}
			
			configName = "SINGLE_LIMIT_1008_006_WX";
			JSONObject limitResult = checkLimitMoney(configName, new BigDecimal(payMoney));
			if(null != limitResult){
				debitNote.setStatus("4");
				debitNoteService.insertSelective(debitNote);
				return limitResult;
			}
			
			JSONObject timeResult = commonUtilService.checkLimitIpFail(PayTypeConstant.PAY_METHOD_H5, PayTypeConstant.PAY_TYPE_WX, memberInfo.getId(), ip);
			if(null != timeResult){
				debitNote.setStatus("12");
				debitNoteService.insertSelective(debitNote);
				return timeResult;
			}
			
			JSONObject ipResult = checkLimitIp(PayTypeConstant.PAY_METHOD_H5, PayTypeConstant.PAY_TYPE_WX, memberInfo.getId(), RouteCodeConstant.RF_ROUTE_CODE, merchantCode.getWxMerchantCode(), ip);
			if(null != ipResult){
				debitNote.setStatus("6");
				debitNoteService.insertSelective(debitNote);
				return ipResult;
			}
			
			JSONObject mResult = checkLimitMerchantMoney(RouteCodeConstant.RF_ROUTE_CODE,merchantCode.getWxMerchantCode());
			if(null != mResult){
				debitNote.setStatus("7");
				debitNoteService.insertSelective(debitNote);
				return mResult;
			}
			
			JSONObject tResult = checkLimitCounts(RouteCodeConstant.RF_ROUTE_CODE);
			if(null != tResult){
				debitNote.setStatus("8");
				debitNoteService.insertSelective(debitNote);
				return tResult;
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
			//String callBack = SysConfig.serverUrl + "/debitNote/tbWxH5PayNotify";
			
			
			//System.out.println("-------------通知地址------" + callBack + "------------");
			
			
			
			JSONObject reqData = new JSONObject();
			reqData.put("AppKey", merchantCode.getWxMerchantCode());
			reqData.put("OrderNum", orderCode);
			reqData.put("PayMoney", payMoney);
			reqData.put("PayType", "11");//微信h5
			reqData.put("UserIp", ip);
			reqData.put("SuccessUrl", "http://www.johutech.com/johuPay/debitNote/payCallBack?orderCode="+orderCode);
			reqData.put("CancelUrl", "http://www.johutech.com/johuPay");
			
			String srcStr = StringUtil.orderedKey(reqData);
			String privateKey = merchantKey.getPrivateKey();
			String signData = EpaySignUtil.signSha1(privateKey, srcStr);
			reqData.put("SignStr", signData);
			
			logger.info("瑞付H5支付请求报文[{}]", new Object[] { JSON.toJSONString(reqData) });
			
			String url = RFConfig.msServerUrl + "/h5_pay";
			
			String resultMsg = HttpUtil.sendPostRequest(url, reqData.toString());
			logger.info("瑞付H5返回请求报文[{}]", new Object[] { resultMsg });
			
			
	        JSONObject resultObj = JSONObject.fromObject(resultMsg);
	        String result_code = resultObj.getString("ReturnCode");
	        String result_msg = resultObj.getString("ReturnMsg");
	      //  String AppKey = resultObj.getString("AppKey");
	      //  String OrderNum = resultObj.getString("OrderNum");
	        boolean signCheck = true;
	        if("0000".equals(result_code)){
	        	String SignStr = resultObj.getString("SignStr");
		        resultObj.remove("SignStr");
		        String rtSrcStr = StringUtil.orderedKey(resultObj);
		        signCheck = EpaySignUtil.checksignSha1(merchantKey.getPublicKey(), rtSrcStr, SignStr);
	        }
	        
	        
	        
	        if(signCheck && "0000".equals(result_code)){
	        	String payUrl = resultObj.getString("PayUrl");
	        	result.put("payUrl", payUrl);
	        	result.put("returnCode", "0000");
				result.put("returnMsg", "成功");
	        }else{
	        	if(!signCheck){
	        		result_msg = "出参验签失败";
	        	}
	        	result.put("returnCode", "0009");
				result.put("returnMsg", result_msg);
				DebitNoteExample debitNoteExample = new DebitNoteExample();
				debitNoteExample.createCriteria().andOrderCodeEqualTo(orderCode);
				List<DebitNote> debitNotes = debitNoteService.selectByExample(debitNoteExample);
				if (debitNotes != null && debitNotes.size() > 0) {
					DebitNote debitNote_1 = debitNotes.get(0);
					debitNote_1.setStatus("2");
					debitNote_1.setUpdateDate(new Date());
					if(!"".equals(result_msg)){
						debitNote_1.setRespMsg(result_msg.length()>250?result_msg.substring(0, 250):result_msg);
					}
					debitNoteService.updateByPrimaryKey(debitNote_1);
				}
	        }
	        
	        try{
		        DebitNoteIp debitNoteIp = new DebitNoteIp();
		        debitNoteIp.setMemberId(memberInfo.getId());
		        debitNoteIp.setMerchantCode(merchantCode.getWxMerchantCode());
		        debitNoteIp.setOrderCode(orderCode);
		        debitNoteIp.setRouteId(RouteCodeConstant.TB_ROUTE_CODE);
		        debitNoteIp.setTxnMethod(PayTypeConstant.PAY_METHOD_H5);
		        debitNoteIp.setTxnType("1");
		        debitNoteIp.setIp(ip);
		        debitNoteIp.setCreateDate(debitNote.getCreateDate());
				debitNoteIpService.insertSelective(debitNoteIp);
	        }catch(Exception ex){
	        	logger.error(ex.getMessage());
	        }
        } catch (Exception e) {
			logger.error(e.getMessage());
			result.put("returnCode", "0096");
			result.put("returnMsg", e.getMessage());
			return result;
		}
		return result;
	}
	
	
	
	@RequestMapping("/debitNote/rfH5PayNotify")
	public void rfH5PayNotify(HttpServletRequest request,HttpServletResponse response){
		String respString = "{\"respon\": \"0000\"}";
		String res = "";
		try {
			Map<String,String> params = new HashMap<String,String>();
			String responseStr = HttpUtil.getPostString(request);
		//	String responseStr = "{\"UserInfo\":null,\"OrderNum\":\"20180110104925390468\",\"PayNum\":\"010000012201801101049273000005\",\"PayType\":\"11\",\"PayTime\":\"20180110104943\",\"PayMoney\":\"2\",\"RespType\":\"2\",\"AppKey\":\"tDWiOfuHidvVp5vtWYEAdv9v4mKPFvrQISl6uD\",\"ReturnCode\":\"0000\",\"SignStr\":\"Rd8xQPxR6nxg8VJsqN3LyPAgC5S8kvlQJjcY1bEtSsboSDO3NgeepzmhXSP5NDFVssuxrXBKf4coTnsR3gOdV+noVNTfGrPzuQzllypEYuVk7B9Tr7FEgRFxo9ZSacF9QzHbpIr/7w/IWQrRrBxOW06rl2cMfE0qWoT4XalKxBs=\"}";
			logger.info("rfH5PayNotify解密回调返回报文[{}]",  responseStr );
			JSONObject resJo = JSONObject.fromObject(responseStr);
			String reqMsgId = "";
			String result_code = "";
			String result_message = "";
			String channelNo = "";
			String respType = "";
			String payMoney = "";
			String userInfo = "";
			if(resJo.containsKey("UserInfo")){
				userInfo = resJo.getString("UserInfo");
				if(userInfo == null || "".equals(userInfo) ||"null".equals(userInfo)){
					userInfo = "";
				}
				params.put("UserInfo", userInfo);
			}
			if(resJo.containsKey("OrderNum")){
				reqMsgId = resJo.getString("OrderNum");
				params.put("OrderNum", reqMsgId);
			}
			if(resJo.containsKey("PayNum")){
				channelNo = resJo.getString("PayNum");
				params.put("PayNum", channelNo);
			}
			if(resJo.containsKey("PayType")){
				String payType = resJo.getString("PayType");
				params.put("PayType", payType);
			}
			if(resJo.containsKey("PayMoney")){
				payMoney = resJo.getString("PayMoney");
				params.put("PayMoney", payMoney);
			}
			if(resJo.containsKey("PayTime")){
				String payTime = resJo.getString("PayTime");
				params.put("PayTime", payTime);
			}
			
			if(resJo.containsKey("RespType")){
				respType = resJo.getString("RespType");
				params.put("RespType", respType);
			}
			if(resJo.containsKey("ReturnCode")){
				result_code = resJo.getString("ReturnCode");
				params.put("ReturnCode", result_code);
			}
			if(resJo.containsKey("AppKey")){
				String appKey = resJo.getString("AppKey");
				params.put("AppKey", appKey);
			}
			String signStr = resJo.getString("SignStr");
			String srcStr = StringUtil.orderedKey(params);
			if("".equals(userInfo)){
				srcStr +="&UserInfo=";
			}
			System.out.println("（接收）排序后的参数串："+srcStr);
			
			
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
	        memberMerchantKeyExample.createCriteria().andRouteCodeEqualTo(RouteCodeConstant.RF_ROUTE_CODE).andMerchantCodeEqualTo(debitNote.getMerchantCode()).andDelFlagEqualTo("0");
	        List<MemberMerchantKey> keyList = memberMerchantKeyService.selectByExample(memberMerchantKeyExample);
	        if(keyList == null || keyList.size()!=1){
	        	res = "商户私钥未配置";
	            respString = "fail";
	            response.getWriter().write(respString);
	    		return;
	        }
		
	        boolean checksign = EpaySignUtil.checksignSha1(keyList.get(0).getPublicKey(), srcStr, signStr);
			if (!checksign){
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
				
				if("2".equals(respType)&&"0000".equals(result_code)){
					result_message = "交易成功";
				}
				
				if (debitNote != null ) {
					//DebitNote debitNote = debitNotes.get(0);
					debitNote.setRespCode(result_code);
					debitNote.setRespMsg(result_message);
					
					//新增一条交易明细记录
					TradeDetail tradeDetail=new TradeDetail();
					tradeDetail.setTxnDate(DateUtil.getDateFormat(new Date(), "yyyyMMdd"));
					tradeDetail.setMemberId(debitNote.getMemberId());
					tradeDetail.setMerchantCode(debitNote.getMerchantCode());
					tradeDetail.setChannelNo(channelNo);
					
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
					if ("2".equals(respType)&&"0000".equals(result_code)) {
						debitNote.setStatus("1");
						tradeDetail.setRespType("S");
						tradeDetail.setRespCode("000000");
					}else if("1".equals(respType)){
						debitNote.setStatus("3");
						tradeDetail.setRespType("R");
						tradeDetail.setRespCode(result_code);
					}else{
						debitNote.setStatus("2");
						tradeDetail.setRespType("E");
						tradeDetail.setRespCode(result_code);
					}
					debitNote.setUpdateDate(new Date());
					debitNoteService.updateByPrimaryKey(debitNote);
					
					MsResultNotice msResultNotice = new MsResultNotice();
					msResultNotice.setMoney(new BigDecimal(payMoney));
					msResultNotice.setOrderCode(reqMsgId);
					msResultNotice.setPtSerialNo(channelNo);
					
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
						if ("2".equals(respType)&&"0000".equals(result_code)) {
							payResultNotice.setRespType("2");
							payResultNotice.setResultCode("0000");
							payResultNotice.setResultMessage("交易成功");
						}else if("1".equals(respType)){
							payResultNotice.setRespType("1");
							payResultNotice.setResultCode("0009");   
							payResultNotice.setResultMessage("交易正在处理中...");
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
					if ("2".equals(respType)&&"0000".equals(result_code)) {
						tradeDetail.setCardType(msResultNotice.getCardType());
						tradeDetailService.insertSelective(tradeDetail);
						
						
						TradeDetailDaily tradeDetailDaily = new TradeDetailDaily();
						
						tradeDetailDaily.setTxnDate(DateUtil.getDateFormat(new Date(), "yyyyMMdd"));
						tradeDetailDaily.setMemberId(debitNote.getMemberId());
						tradeDetailDaily.setMerchantCode(debitNote.getMerchantCode());
						tradeDetailDaily.setMoney(debitNote.getMoney());
						tradeDetailDaily.setOrderCode(debitNote.getOrderCode());
						tradeDetailDaily.setRouteId(debitNote.getRouteId());
						tradeDetailDaily.setDelFlag("0");
						tradeDetailDaily.setCreateDate(new Date());
						tradeDetailDailyService.insertSelective(tradeDetailDaily);
					}
					
					if(payResultNoticeList.size() > 0){
						payResultNotifyService.notify(payResultNotice);
					
					}
					
				}
			}
	        response.getWriter().write(respString);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
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
						
						
						TradeDetailDaily tradeDetailDaily = new TradeDetailDaily();
						
						tradeDetailDaily.setTxnDate(DateUtil.getDateFormat(new Date(), "yyyyMMdd"));
						tradeDetailDaily.setMemberId(debitNote.getMemberId());
						tradeDetailDaily.setMerchantCode(debitNote.getMerchantCode());
						tradeDetailDaily.setMoney(debitNote.getMoney());
						tradeDetailDaily.setOrderCode(debitNote.getOrderCode());
						tradeDetailDaily.setRouteId(debitNote.getRouteId());
						tradeDetailDaily.setDelFlag("0");
						tradeDetailDaily.setCreateDate(new Date());
						tradeDetailDailyService.insertSelective(tradeDetailDaily);
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
			
			TradeDetailDaily tradeDetailDaily = new TradeDetailDaily();
			
			tradeDetailDaily.setTxnDate(DateUtil.getDateFormat(new Date(), "yyyyMMdd"));
			tradeDetailDaily.setMemberId(15);
			tradeDetailDaily.setMerchantCode("12222");
			tradeDetailDaily.setMoney(new BigDecimal(12));
			tradeDetailDaily.setOrderCode("2222");
			tradeDetailDaily.setRouteId("1007");
			tradeDetailDaily.setDelFlag("0");
			tradeDetailDaily.setCreateDate(new Date());
			tradeDetailDailyService.insertSelective(tradeDetailDaily);
			
			//EskNotice notice = new EskNotice();
			//notice.setNoticeData("111111111");
			//notice.setOrderNumber("201700000222");
			//eskNoticeService.insertNotice(notice);
			response.getWriter().write("000000");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	@RequestMapping("/test/testRfH5")
	public void testRfH5(HttpServletRequest request,HttpServletResponse response){
		try {
			String orderCode = CommonUtil.getOrderCode();
			
			
			
			
			
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
			reqData.put("AppKey", "tDWiOfuHidvVp5vtWYEAdv9v4mKPFvrQISl6uD");
			reqData.put("OrderNum", orderCode);
			reqData.put("Amount", "2");
			reqData.put("Account_no", "6226661702569637");//收款账号
			reqData.put("Account_name", "林晓锋");//开户人
			reqData.put("Bank_general_name", "中国光大银行");
			reqData.put("Bank_name", "中国光大银行股份有限公司福州铜盘支行");
			reqData.put("Bank_code", "303391000063");
			reqData.put("Code", "CEB");
			
			String srcStr = StringUtil.orderedKey(reqData);
			String privateKey = "MIICeAIBADANBgkqhkiG9w0BAQEFAASCAmIwggJeAgEAAoGBAN2BWWpp51mOCRU5SaKSX+75QuvRte2bM1kTOOB/y/U0beL/+Qgwf+gtKMtgFOepuNoM/HHD2eg6RZvJWWSyPXaIm2qVhm592v7B2T53Pt/aC5DGVAl9SMfYMTUpOUG/8Be6Gq1oHXGXTzAAZOmzlm5vAD83VKOd0hL31d3RCTV7AgMBAAECgYEArS1B4SanT6byhvthDI9wHYAXyBMPL5NVk+CpaSvBJBY3i3AhHTv95GHaMRcPgQd6lE/u1msO5LcaUPAcydNopNy7ppJZrLElekmxu/SvVErWgkbooSpBac+t7o0jb9JcJBuoyMoATGdNNhZtpst3foUQi97SeLW/ddQCxGS8MRECQQD76ZlT1UxkhqyLCirjiyse7afSanOuQFOQRy4rwN0mjH9e89V8wrYtomkKGK2EUm436QujiMe9y8AJC9AtJWs1AkEA4RlxqFWiyhE+Zyckkn8XE7yvwhW7aAlc+5UE7SHpj78TLQgQZL0oMa2rahK7TgBZ4sci33VdjDneKX4jOgaD7wJAXzE1xG0csfwGJYBRFq0XPVe3DBc34YfjS+jp9JSrvQ5obzwa10tIxlPR94O9xfvUNIJ26HQeboUY6xIwt26lZQJBAKBSM+411/TdZTmo2lZwqCoJiJDOU6TcjlotH84ZCjW0XF8FUE+/naIMVHr/DmKWw25OcJsBB3i5Om8JBOPuAgcCQQC7WmovBTDJ0+jrsAh9n/Po7QKTJOtvchX2/LlL8i/+Dvf1ghuQA0H4xvd/agUyPq9jlbisKLnhEUx3vSC33NqI";
			String signData = EpaySignUtil.signSha1(privateKey, srcStr);
			reqData.put("SignStr", signData);
			
			
			System.out.println("待加密数据: "+reqData);
			//http://pay.ruifuzhifu.com/api.php/Rpay/h5_pay
			//http:// pay.ruifuzhifu.com/api.php/Rpay/PayNew
			String ret = HttpUtil.sendPostRequest("http://pay.ruifuzhifu.com/api.php/Rpay/PayNew", reqData.toString());
			System.out.println("======="+ret);
			JSONObject obj = JSONObject.fromObject(ret);
			System.out.println(obj.get("ReturnMsg"));
			response.getWriter().write("000000");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping("/test/testRf")
	public void testRf(HttpServletRequest request,HttpServletResponse response){
		try {
			String orderCode = CommonUtil.getOrderCode();
			
			
			
			
			
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
			reqData.put("AppKey", "tDWiOfuHidvVp5vtWYEAdv9v4mKPFvrQISl6uD");
			reqData.put("OrderNum", "20180111151401149801");
			
			
			String srcStr = StringUtil.orderedKey(reqData);
			String privateKey = "MIICeAIBADANBgkqhkiG9w0BAQEFAASCAmIwggJeAgEAAoGBAN2BWWpp51mOCRU5SaKSX+75QuvRte2bM1kTOOB/y/U0beL/+Qgwf+gtKMtgFOepuNoM/HHD2eg6RZvJWWSyPXaIm2qVhm592v7B2T53Pt/aC5DGVAl9SMfYMTUpOUG/8Be6Gq1oHXGXTzAAZOmzlm5vAD83VKOd0hL31d3RCTV7AgMBAAECgYEArS1B4SanT6byhvthDI9wHYAXyBMPL5NVk+CpaSvBJBY3i3AhHTv95GHaMRcPgQd6lE/u1msO5LcaUPAcydNopNy7ppJZrLElekmxu/SvVErWgkbooSpBac+t7o0jb9JcJBuoyMoATGdNNhZtpst3foUQi97SeLW/ddQCxGS8MRECQQD76ZlT1UxkhqyLCirjiyse7afSanOuQFOQRy4rwN0mjH9e89V8wrYtomkKGK2EUm436QujiMe9y8AJC9AtJWs1AkEA4RlxqFWiyhE+Zyckkn8XE7yvwhW7aAlc+5UE7SHpj78TLQgQZL0oMa2rahK7TgBZ4sci33VdjDneKX4jOgaD7wJAXzE1xG0csfwGJYBRFq0XPVe3DBc34YfjS+jp9JSrvQ5obzwa10tIxlPR94O9xfvUNIJ26HQeboUY6xIwt26lZQJBAKBSM+411/TdZTmo2lZwqCoJiJDOU6TcjlotH84ZCjW0XF8FUE+/naIMVHr/DmKWw25OcJsBB3i5Om8JBOPuAgcCQQC7WmovBTDJ0+jrsAh9n/Po7QKTJOtvchX2/LlL8i/+Dvf1ghuQA0H4xvd/agUyPq9jlbisKLnhEUx3vSC33NqI";
			String signData = EpaySignUtil.signSha1(privateKey, srcStr);
			reqData.put("SignStr", signData);
			
			
			System.out.println("待加密数据: "+reqData);
			//http://pay.ruifuzhifu.com/api.php/Rpay/h5_pay
			//http:// pay.ruifuzhifu.com/api.php/Rpay/PayNew
			String ret = HttpUtil.sendPostRequest("http://pay.ruifuzhifu.com/api.php/Rpay/payout", reqData.toString());
			System.out.println("======="+ret);
			JSONObject obj = JSONObject.fromObject(ret);
			System.out.println(obj.get("Res"));
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
	
	
	@ResponseBody
	@RequestMapping("/api/debitNote/testWxH5Pay")
	public JSONObject testWxH5Pay(HttpServletRequest request,HttpServletResponse response){
		JSONObject requestPRM = (JSONObject) request.getAttribute("requestPRM");
		JSONObject reqDataJson = requestPRM.getJSONObject("reqData");// 获取请求参数
		System.out.println("请求参数==="+reqDataJson.toString());
		String payMoney = reqDataJson.getString("payMoney");
		String orderNum = reqDataJson.getString("orderNum");
		String memberCode = reqDataJson.getString("memberCode");
		String callbackUrl = reqDataJson.getString("callbackUrl");
		String sceneInfo = reqDataJson.getString("sceneInfo");
		String ip = reqDataJson.getString("ip");
		String merchantCode = reqDataJson.getString("merchantCode");
		String routeCode = reqDataJson.getString("routeCode");
		
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
		
		if(!"9010000988".equals(memberCode)){
			result.put("returnCode", "0007");
			result.put("returnMsg", "商户错误");
			return result;
		}
		
		if(merchantCode == null || "".equals(merchantCode)){
			result.put("returnCode", "0007");
			result.put("returnMsg", "通道商户号缺失");
			return result;
		}
		srcStr.append("&merchantCode="+merchantCode);
		
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
		
		String payType = "";
		if(reqDataJson.containsKey("payType")){
			payType = reqDataJson.getString("payType");
		}
		if(payType != null && !"".equals(payType)){
			srcStr.append("&payType="+payType);
		}else{
			payType = "1";
		}
		
		if(routeCode == null || "".equals(routeCode)){
			result.put("returnCode", "0007");
			result.put("returnMsg", "通道编码缺失");
			return result;
		}
		srcStr.append("&routeCode="+routeCode);
		
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
		
		String userAgent = "";
		if(reqDataJson.containsKey("userAgent")){
			userAgent = reqDataJson.getString("userAgent");
		}
		
		result = testValidMemberInfoForH5(memberCode, orderNum, payMoney, "3",  srcStr.toString(), signStr, callbackUrl,sceneInfo,ip,payType,merchantCode,routeCode,userAgent);
		
		
		
		return result;
	}
		
	public JSONObject testValidMemberInfoForH5(String memberCode,String orderNum,String payMoney,String platformType,String signOrginalStr,String signedStr,String callbackUrl,String sceneInfo,String ip,String payType,String merCode,String rCode,String userAgent){
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
		String payTypeStr = PayTypeConstant.PAY_TYPE_WX;
		if("3".equals(payType)){
			payTypeStr = PayTypeConstant.PAY_TYPE_QQ;
		}
		
		MemberPayTypeExample memberPayTypeExample = new MemberPayTypeExample();
		memberPayTypeExample.createCriteria().andMemberIdEqualTo(memberInfo.getId()).andPayMethodEqualTo(PayTypeConstant.PAY_METHOD_H5).andPayTypeEqualTo(payTypeStr).andDelFlagEqualTo("0");
		List<MemberPayType> memberPayTypeList =  memberPayTypeService.selectByExample(memberPayTypeExample);
		if(memberPayTypeList==null || memberPayTypeList.size()==0){
			result.put("returnCode", "0008");
			result.put("returnMsg", "对不起，商户未开通该支付权限");
			return result;
		}
		
		MemberPayType memberPayType = memberPayTypeList.get(0);
		
		Map<String,String> rtMap  = getRouteCodeAndAisleType(memberInfo.getId(),PayTypeConstant.PAY_METHOD_H5,payTypeStr);
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
		merchantCode.setRouteCode(rCode);
		if("1".equals(payType)){
			merchantCode.setWxMerchantCode(merCode);
		}else if("3".equals(payType)){
			merchantCode.setQqMerchantCode(merCode);
		}
		routeCode = rCode;
		if("1002".equals(routeCode)){
			aisleType = "2";
		}else if("1010".equals(routeCode)){
			aisleType = "5";
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
			if("1".equals(payType)){
				result = tbH5Pay(platformType,memberInfo,memberPayType, payMoney, orderNum,sceneInfo,ip, callbackUrl , merchantCode,userAgent );
			}
			result.put("routeCode", routeCode);
		}else if(RouteCodeConstant.ESK_ROUTE_CODE.equals(routeCode)){
			if("1".equals(payType)){
				result = eskH5Pay(platformType,memberInfo,memberPayType, payMoney, orderNum,sceneInfo,ip, callbackUrl , merchantCode,userAgent,RouteCodeConstant.ESK_ROUTE_CODE,aisleType );
			}else if("3".equals(payType)){
				result = esk001H5Pay(platformType,memberInfo,memberPayType, payMoney, orderNum,sceneInfo,ip, callbackUrl , merchantCode,userAgent,RouteCodeConstant.ESK_ROUTE_CODE,aisleType,payType );
			}
			result.put("routeCode", routeCode);
		}else if(RouteCodeConstant.XF_ROUTE_CODE.equals(routeCode)){
			result = xfH5Pay(platformType,memberInfo,memberPayType, payMoney, orderNum,sceneInfo,ip, callbackUrl , merchantCode );
			result.put("routeCode", routeCode);
		}else if(RouteCodeConstant.RF_ROUTE_CODE.equals(routeCode)){
			if("1".equals(payType)){
				result = rfH5Pay(platformType,memberInfo,memberPayType, payMoney, orderNum,sceneInfo,ip, callbackUrl , merchantCode,userAgent );
			}
			result.put("routeCode", routeCode);
		}else if(RouteCodeConstant.ESKXF_ROUTE_CODE.equals(routeCode)){
			if("1".equals(payType)){
				result = eskH5Pay(platformType,memberInfo,memberPayType, payMoney, orderNum,sceneInfo,ip, callbackUrl , merchantCode,userAgent,RouteCodeConstant.ESKXF_ROUTE_CODE,aisleType );
			}else if("3".equals(payType)){
				result = esk001H5Pay(platformType,memberInfo,memberPayType, payMoney, orderNum,sceneInfo,ip, callbackUrl , merchantCode,userAgent ,RouteCodeConstant.ESKXF_ROUTE_CODE,aisleType,payType);
			}
			result.put("routeCode", routeCode);
		}
		
		return result;
	}	
		
	
	//综合支付qqh5支付
	public JSONObject zhzfH5Pay(String platformType,MemberInfo memberInfo,MemberPayType memberPayType,String payMoney,String orderNumOuter,String sceneInfo,String ip,String callbackUrl,MemberMerchantCode merchantCode,String userAgent) {
		JSONObject result = new JSONObject();
		try {
			MemberMerchantKeyExample memberMerchantKeyExample = new MemberMerchantKeyExample();
	        memberMerchantKeyExample.createCriteria().andRouteCodeEqualTo(RouteCodeConstant.ZHZF_ROUTE_CODE).andMerchantCodeEqualTo(merchantCode.getQqMerchantCode()).andDelFlagEqualTo("0");
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
			debitNote.setRouteId(RouteCodeConstant.ZHZF_ROUTE_CODE);
			debitNote.setStatus("0");
			debitNote.setTxnMethod(PayTypeConstant.PAY_METHOD_H5);
			debitNote.setTxnType("3");
			debitNote.setMemberCode(memberInfo.getQqMemberCode());
			debitNote.setMerchantCode(merchantCode.getQqMerchantCode());
			
			debitNote.setSettleType("0");
			debitNote.setIp(ip);
			debitNote.setUserAgent(userAgent.length()>1000?userAgent.substring(0, 1000):userAgent);
			debitNote.setBrowser(DeviceRequestUtil.getBrowser(userAgent));
			debitNote.setDeviceInfo(DeviceRequestUtil.getDevice(userAgent));
			debitNote.setTradeRate(memberPayType.getT0TradeRate());
			
			String configName = "SINGLE_MEMBER_LIMIT_"+memberInfo.getId()+"_006_QQ";
			JSONObject memResult = checkLimitMoney(configName, new BigDecimal(payMoney));
			if(null != memResult){
				debitNote.setStatus("9");
				debitNoteService.insertSelective(debitNote);
				return memResult;
			}
			
			configName = "SINGLE_MIN_"+RouteCodeConstant.ZHZF_ROUTE_CODE+"_006_QQ";
			JSONObject checkResult = checkMinMoney(configName, new BigDecimal(payMoney));
			if(null != checkResult){
				debitNote.setStatus("5");
				debitNoteService.insertSelective(debitNote);
				return checkResult;
			}
			
			configName = "SINGLE_LIMIT_"+RouteCodeConstant.ZHZF_ROUTE_CODE+"_006_QQ";
			JSONObject limitResult = checkLimitMoney(configName, new BigDecimal(payMoney));
			if(null != limitResult){
				debitNote.setStatus("4");
				debitNoteService.insertSelective(debitNote);
				return limitResult;
			}
			
			JSONObject timeResult = commonUtilService.checkLimitIpFail(PayTypeConstant.PAY_METHOD_H5, PayTypeConstant.PAY_TYPE_QQ, memberInfo.getId(), ip);
			if(null != timeResult){
				debitNote.setStatus("12");
				debitNoteService.insertSelective(debitNote);
				return timeResult;
			}
			
			JSONObject ipResult = checkLimitIp(PayTypeConstant.PAY_METHOD_H5, PayTypeConstant.PAY_TYPE_QQ, memberInfo.getId(), RouteCodeConstant.ZHZF_ROUTE_CODE, merchantCode.getQqMerchantCode(), ip);
			if(null != ipResult){
				debitNote.setStatus("6");
				debitNoteService.insertSelective(debitNote);
				return ipResult;
			}
			
			JSONObject mResult = checkLimitMerchantMoney(RouteCodeConstant.ZHZF_ROUTE_CODE,merchantCode.getQqMerchantCode());
			if(null != mResult){
				debitNote.setStatus("7");
				debitNoteService.insertSelective(debitNote);
				return mResult;
			}
			
			JSONObject tResult = checkLimitCounts(RouteCodeConstant.ZHZF_ROUTE_CODE);
			if(null != tResult){
				debitNote.setStatus("8");
				debitNoteService.insertSelective(debitNote);
				return tResult;
			}
			
			debitNoteService.insertSelective(debitNote);
			
			PayResultNotice payResultNotice = new PayResultNotice();
			payResultNotice.setOrderCode(debitNote.getOrderCode());
			payResultNotice.setOrderNumOuter(orderNumOuter);
			payResultNotice.setPayMoney(debitNote.getMoney());
			payResultNotice.setMemberCode(memberInfo.getCode());
			payResultNotice.setPayType("3");
			
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
			
			
			String callBack = SysConfig.serverUrl + "/debitNote/zhzfH5PayNotify";
			String serverUrl = ZHZFConfig.msServerUrl + "/dopay";
			
			TreeMap<String, Object> map = new TreeMap<>();
			TreeMap<String, Object> map2 = new TreeMap<>();
			map.put("mch_id", merchantCode.getQqMerchantCode());
			map.put("out_order_no", orderCode);
			map.put("pay_platform", "SQPAY");
			map.put("payment_fee", String.valueOf((int)(((new BigDecimal(payMoney)).floatValue())*100)));
			map.put("body", memberInfo.getName()+" 收款");
			map.put("notify_url", callBack);
			map.put("bill_create_ip", ip);
			
			String biz_content = JSONObject.fromObject(map).toString();
			String strPre = "biz_content=" + biz_content + "&key=" + merchantKey.getPrivateKey();
			String sign = MD5.MD5Encode(strPre).toUpperCase();
			
			map2.put("biz_content", biz_content);
			map2.put("signature", sign);
			map2.put("sign_type", "MD5");
			logger.info("综合支付H5支付请求报文[{}]", map2.toString());
			
				
			String respStr = HttpUtils.httpSend(serverUrl,map2);
			
			logger.info("综合支付H5返回请求报文[{}]", new Object[] { respStr });
			
			
	        JSONObject resultObj = JSONObject.fromObject(respStr);
	        String result_code = resultObj.getString("ret_code");
	        String result_msg = "";
	        if(resultObj.containsKey("ret_msg")){
	        	result_msg = resultObj.getString("ret_msg");
	        }
	        String result_content = "";
	        if(resultObj.containsKey("biz_content")){
	        	result_content = resultObj.getString("biz_content");
	        }
	        boolean signCheck = true;
	        if("0".equals(result_code)){
	        	JSONObject bizObj = JSONObject.fromObject(result_content);
	        	TreeMap<String, Object> map1 = new TreeMap<>();
	        	map1.put("mch_id", bizObj.getString("mch_id"));
	        	map1.put("order_no", bizObj.getString("order_no"));
	        	map1.put("out_order_no", bizObj.getString("out_order_no"));
	        	map1.put("pay_param", bizObj.getString("pay_param"));
	        	map1.put("payment_fee", bizObj.getString("payment_fee"));
	        	result_content = JSONObject.fromObject(map1).toString();
	        	
	        	String str = "biz_content="+result_content+"&key=" + merchantKey.getPrivateKey();
	        	String strSign = MD5.MD5Encode(str).toUpperCase();
	        	if(!strSign.equals(resultObj.getString("signature"))){
	        		signCheck = false;
	        	}
	        }
	        
	        if("0".equals(result_code)&&signCheck){
	        	JSONObject bizObj = JSONObject.fromObject(result_content);
	        	String payUrl = bizObj.getString("pay_param");
	        	result.put("payUrl", payUrl.trim());
	        	result.put("returnCode", "0000");
				result.put("returnMsg", "成功");
	        }else{
	        	if(!signCheck){
	        		result_msg = "出参验签失败";
	        	}
	        	result.put("returnCode", "0009");
				result.put("returnMsg", result_msg);
				DebitNoteExample debitNoteExample = new DebitNoteExample();
				debitNoteExample.createCriteria().andOrderCodeEqualTo(orderCode);
				List<DebitNote> debitNotes = debitNoteService.selectByExample(debitNoteExample);
				if (debitNotes != null && debitNotes.size() > 0) {
					DebitNote debitNote_1 = debitNotes.get(0);
					debitNote_1.setStatus("2");
					debitNote_1.setUpdateDate(new Date());
					if(!"".equals(result_msg)){
						debitNote_1.setRespMsg(result_msg.length()>250?result_msg.substring(0, 250):result_msg);
					}
					debitNoteService.updateByPrimaryKey(debitNote_1);
				}
	        }
	        
	        try{
		        DebitNoteIp debitNoteIp = new DebitNoteIp();
		        debitNoteIp.setMemberId(memberInfo.getId());
		        debitNoteIp.setMerchantCode(merchantCode.getQqMerchantCode());
		        debitNoteIp.setOrderCode(orderCode);
		        debitNoteIp.setRouteId(RouteCodeConstant.ZHZF_ROUTE_CODE);
		        debitNoteIp.setTxnMethod(PayTypeConstant.PAY_METHOD_H5);
		        debitNoteIp.setTxnType("3");
		        debitNoteIp.setIp(ip);
		        debitNoteIp.setCreateDate(debitNote.getCreateDate());
				debitNoteIpService.insertSelective(debitNoteIp);
	        }catch(Exception ex){
	        	logger.error(ex.getMessage());
	        }
        } catch (Exception e) {
			logger.error(e.getMessage());
			result.put("returnCode", "0096");
			result.put("returnMsg", e.getMessage());
			return result;
		}
		return result;
	}
	
	
	@RequestMapping("/debitNote/zhzfH5PayNotify")
	public void zhzfH5PayNotify(HttpServletRequest request,HttpServletResponse response) {
		String respString = "SUCCESS";
		String res = "";
		try {
			String responseStr = HttpUtil.getPostString(request);
			//String responseStr = "{\"ret_code\":\"0\",\"ret_msg\":\"交易成功\",\"signature\":\"7A23583A97ABFCB3FC1EDFF3FD160515\",\"biz_content\":{\"create_time\":\"2018-01-30 12:05:21\",\"mch_id\":\"10003862\",\"order_no\":\"81020180130120521818577538560202\",\"out_order_no\":\"20180130120521911276\",\"pay_platform\":\"SQPAY\",\"pay_time\":\"2018-01-30 12:05:44\",\"pay_type\":\"NATIVE\",\"payment_fee\":\"100\"}}";
			logger.info("zhzfH5PayNotify解密回调返回报文[{}]",  responseStr );
			JSONObject resJo = JSONObject.fromObject(responseStr);
			
			String result_code = resJo.getString("ret_code");
			String result_message = resJo.getString("ret_msg");
			String sign = resJo.getString("signature");
			String data = resJo.getString("biz_content");
			
            JSONObject map = JSONObject.fromObject(data);
        	String reqMsgId = map.getString("out_order_no");
        	
        	TreeMap<String, Object> bizmap = new TreeMap<String, Object>();
        	bizmap.put("create_time", map.getString("create_time"));
        	bizmap.put("mch_id", map.getString("mch_id"));
        	bizmap.put("order_no", map.getString("order_no"));
        	bizmap.put("out_order_no", map.getString("out_order_no"));
        	bizmap.put("pay_platform", map.getString("pay_platform"));
        	bizmap.put("pay_time", map.getString("pay_time"));
        	if(map.containsKey("pay_type")){
        		bizmap.put("pay_type", map.getString("pay_type"));
        	}
        	bizmap.put("payment_fee", map.getString("payment_fee"));
        	if(map.containsKey("transaction_id")){
        		bizmap.put("transaction_id", map.getString("transaction_id"));
        	}
        	String result_content = JSONObject.fromObject(bizmap).toString();
        	
        	
        	
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
                logger.info(res);
                response.getWriter().write(respString);
        		return;
				
			}
			DebitNote debitNote = debitNotes_tmp.get(0);
        	MemberMerchantKeyExample memberMerchantKeyExample = new MemberMerchantKeyExample();
            memberMerchantKeyExample.createCriteria().andRouteCodeEqualTo(RouteCodeConstant.ZHZF_ROUTE_CODE).andMerchantCodeEqualTo(debitNote.getMerchantCode()).andDelFlagEqualTo("0");
            List<MemberMerchantKey> keyList = memberMerchantKeyService.selectByExample(memberMerchantKeyExample);
            if(keyList == null || keyList.size()!=1){
            	res = "商户私钥未配置";
                respString = "fail";
                logger.info(res);
                response.getWriter().write(respString);
        		return;
            }
            
            String str = "biz_content="+result_content+"&key=" + keyList.get(0).getPrivateKey();
        	String strSign = MD5.MD5Encode(str).toUpperCase();
            if (!sign.equals(strSign)){
        	   System.out.println("待加密串："+str);
        	   System.out.println("加密结果："+strSign);
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
				if (debitNote != null ) {
					debitNote.setRespCode(result_code);
					debitNote.setRespMsg(result_message);
					
					//新增一条交易明细记录
					TradeDetail tradeDetail=new TradeDetail();
					tradeDetail.setTxnDate(DateUtil.getDateFormat(new Date(), "yyyyMMdd"));
					tradeDetail.setMemberId(debitNote.getMemberId());
					tradeDetail.setMerchantCode(debitNote.getMerchantCode());
					if(map.containsKey("order_no")){
						tradeDetail.setChannelNo(map.getString("order_no"));
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
					if ("0".equals(result_code)) {
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
					msResultNotice.setMoney(new BigDecimal(map.getString("payment_fee")));
					msResultNotice.setOrderCode(reqMsgId);
					if(map.containsKey("order_no")){
						msResultNotice.setPtSerialNo(map.getString("order_no"));
					}
					if(map.containsKey("transaction_id")){
						msResultNotice.setChannelNo(map.getString("transaction_id"));
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
						if ("0".equals(result_code)) {
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
					if ("0".equals(result_code)) {
						tradeDetail.setCardType(msResultNotice.getCardType());
						tradeDetailService.insertSelective(tradeDetail);
						
						
						TradeDetailDaily tradeDetailDaily = new TradeDetailDaily();
						
						tradeDetailDaily.setTxnDate(DateUtil.getDateFormat(new Date(), "yyyyMMdd"));
						tradeDetailDaily.setMemberId(debitNote.getMemberId());
						tradeDetailDaily.setMerchantCode(debitNote.getMerchantCode());
						tradeDetailDaily.setMoney(debitNote.getMoney());
						tradeDetailDaily.setOrderCode(debitNote.getOrderCode());
						tradeDetailDaily.setRouteId(debitNote.getRouteId());
						tradeDetailDaily.setDelFlag("0");
						tradeDetailDaily.setCreateDate(new Date());
						tradeDetailDailyService.insertSelective(tradeDetailDaily);
					}
					
					
					
					if(payResultNoticeList.size() > 0){
						payResultNotifyService.notify(payResultNotice);
						
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
	
	//posp网关支付
	public JSONObject pospH5Pay(String platformType,MemberInfo memberInfo,MemberPayType memberPayType,String payMoney,String orderNumOuter,String sceneInfo,String ip,String callbackUrl,MemberMerchantCode merchantCode,String userAgent) {
		JSONObject result = new JSONObject();
		try {
			
			String merCode = merchantCode.getWxMerchantCode();
			String routeCode = RouteCodeConstant.POSP_ROUTE_CODE;
			MemberMerchantKeyExample memberMerchantKeyExample = new MemberMerchantKeyExample();
	        memberMerchantKeyExample.createCriteria().andRouteCodeEqualTo(RouteCodeConstant.POSP_ROUTE_CODE).andMerchantCodeEqualTo(merCode).andDelFlagEqualTo("0");
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
			debitNote.setRouteId(routeCode);
			debitNote.setStatus("0");
			debitNote.setTxnMethod(PayTypeConstant.PAY_METHOD_H5);
			debitNote.setTxnType("1");
			debitNote.setMemberCode(memberInfo.getWxMemberCode());
			debitNote.setMerchantCode(merCode);
			
			debitNote.setSettleType("0");
			debitNote.setIp(ip);
			debitNote.setUserAgent(userAgent.length()>1000?userAgent.substring(0, 1000):userAgent);
			debitNote.setBrowser(DeviceRequestUtil.getBrowser(userAgent));
			debitNote.setDeviceInfo(DeviceRequestUtil.getDevice(userAgent));
			debitNote.setTradeRate(memberPayType.getT0TradeRate());
			
			String configName = "SINGLE_MEMBER_LIMIT_"+memberInfo.getId()+"_006_WX";
			JSONObject memResult = checkLimitMoney(configName, new BigDecimal(payMoney));
			if(null != memResult){
				debitNote.setStatus("9");
				debitNoteService.insertSelective(debitNote);
				return memResult;
			}
			
			configName = "SINGLE_MIN_"+routeCode+"_006_WX";
			JSONObject checkResult = checkMinMoney(configName, new BigDecimal(payMoney));
			if(null != checkResult){
				debitNote.setStatus("5");
				debitNoteService.insertSelective(debitNote);
				return checkResult;
			}
			
			configName = "SINGLE_LIMIT_"+routeCode+"_006_WX";
			JSONObject limitResult = checkLimitMoney(configName, new BigDecimal(payMoney));
			if(null != limitResult){
				debitNote.setStatus("4");
				debitNoteService.insertSelective(debitNote);
				return limitResult;
			}
			
			JSONObject timeResult = commonUtilService.checkLimitIpFail(PayTypeConstant.PAY_METHOD_H5, PayTypeConstant.PAY_TYPE_WX, memberInfo.getId(), ip);
			if(null != timeResult){
				debitNote.setStatus("12");
				debitNoteService.insertSelective(debitNote);
				return timeResult;
			}
			
			JSONObject ipResult = checkLimitIp(PayTypeConstant.PAY_METHOD_H5, PayTypeConstant.PAY_TYPE_WX, memberInfo.getId(), routeCode, merCode, ip);
			if(null != ipResult){
				debitNote.setStatus("6");
				debitNoteService.insertSelective(debitNote);
				return ipResult;
			}
			
			JSONObject mResult = checkLimitMerchantMoney(routeCode,merCode);
			if(null != mResult){
				debitNote.setStatus("7");
				debitNoteService.insertSelective(debitNote);
				return mResult;
			}
			
			JSONObject tResult = checkLimitCounts(routeCode);
			if(null != tResult){
				debitNote.setStatus("8");
				debitNoteService.insertSelective(debitNote);
				return tResult;
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
			
			
			String callBack = SysConfig.serverUrl + "/debitNote/pospH5PayNotify";
			String serverUrl = POSPConfig.msServerUrl;
			
			
			Map<String, String> params = new HashMap<String, String>();
			
			// - 必填参数
			params.put("order_no", orderCode);
			params.put("amount", String.valueOf((int)(((new BigDecimal(payMoney)).floatValue())*100)));
			params.put("subject", memberInfo.getName()+" 收款");
			params.put("mer_id", merCode);
			params.put("channel", "wx_h5");
			
			// - 可选参数
			params.put("body", memberInfo.getName()+" 收款");
			params.put("notify_url", callBack);
			params.put("return_url", "http://pay1.hlqlb.cn:8692/pay/debitNote/payCallBack?orderCode="+orderCode);
			params.put("client_ip", ip);
			
			params = ApiUtils.formatRequestParams("charge.create", params, merchantKey.getAppId(), merchantKey.getPrivateKey());
			
			logger.info("POSP网关H5支付请求报文[{}]", JSON.toJSONString(params));
			
			String respStr = com.epay.scanposp.common.utils.posp.HttpUtils.doPost(serverUrl, params, 60000, 60000);
			
			logger.info("POSP网关H5支付返回报文[{}]", new Object[] { respStr });
			
			
	        JSONObject resultObj = JSONObject.fromObject(respStr);
	        String result_code = resultObj.getString("code");
	        String result_msg = resultObj.getString("message");
	        
	        String result_content = "";
	        if(resultObj.containsKey("data")){
	        	result_content = resultObj.getString("data");
	        }
	        boolean flag = false;
	        if("0".equals(result_code)){
	        	JSONObject bizObj = JSONObject.fromObject(result_content);
	        	if("0".equals(bizObj.getString("failure_code"))){
	        		if(bizObj.getInt("status")==10){
	        			String wx_h5 = JSONObject.fromObject(bizObj.getString("credential")).getString("wx_h5");
	        			String payUrl = JSONObject.fromObject(wx_h5).getString("pay_url");
	        			result.put("payUrl", payUrl.trim());
	    	        	result.put("returnCode", "0000");
	    				result.put("returnMsg", "成功");
	    				flag = true;
	        		}else{
	        			result_msg = "该订单不是待支付状态";
	        		}
	        	}else{
	        		result_msg = bizObj.getString("failure_msg");
	        	}
	        	
	        }
	        if(!flag){
	        	result.put("returnCode", "0009");
				result.put("returnMsg", result_msg);
				DebitNoteExample debitNoteExample = new DebitNoteExample();
				debitNoteExample.createCriteria().andOrderCodeEqualTo(orderCode);
				List<DebitNote> debitNotes = debitNoteService.selectByExample(debitNoteExample);
				if (debitNotes != null && debitNotes.size() > 0) {
					DebitNote debitNote_1 = debitNotes.get(0);
					debitNote_1.setStatus("2");
					debitNote_1.setUpdateDate(new Date());
					if(!"".equals(result_msg)){
						debitNote_1.setRespMsg(result_msg.length()>250?result_msg.substring(0, 250):result_msg);
					}
					debitNoteService.updateByPrimaryKey(debitNote_1);
				}
	        }
	        
	        try{
		        DebitNoteIp debitNoteIp = new DebitNoteIp();
		        debitNoteIp.setMemberId(memberInfo.getId());
		        debitNoteIp.setMerchantCode(merCode);
		        debitNoteIp.setOrderCode(orderCode);
		        debitNoteIp.setRouteId(routeCode);
		        debitNoteIp.setTxnMethod(PayTypeConstant.PAY_METHOD_H5);
		        debitNoteIp.setTxnType("1");
		        debitNoteIp.setIp(ip);
		        debitNoteIp.setCreateDate(debitNote.getCreateDate());
				debitNoteIpService.insertSelective(debitNoteIp);
	        }catch(Exception ex){
	        	logger.error(ex.getMessage());
	        }
        } catch (Exception e) {
			logger.error(e.getMessage());
			result.put("returnCode", "0096");
			result.put("returnMsg", e.getMessage());
			return result;
		}
		return result;
	}
	
	@RequestMapping("/debitNote/pospH5PayNotify")
	public void pospH5PayNotify(HttpServletRequest request,HttpServletResponse response) {
		JSONObject rtObj = new JSONObject();
		try {
			String method = request.getParameter("method");
			String data = request.getParameter("data");
			String sign_type =  request.getParameter("sign_type");
			String sign =  request.getParameter("sign");
			
			Map<String,String> rtMap = new HashMap<String, String>();
			rtMap.put("method", method);
			rtMap.put("data", data);
			rtMap.put("sign_type", sign_type);
			rtMap.put("sign", sign);
			
			logger.info("pospH5PayNotify回调返回报文[{}]",  JSONObject.fromObject(rtMap).toString() );
			
			JSONObject map = JSONObject.fromObject(data);
        	String reqMsgId = map.getString("order_no");
        	
        	TradeDetailExample tradeDetailExample = new TradeDetailExample();
        	tradeDetailExample.or().andOrderCodeEqualTo(reqMsgId);
        	List<TradeDetail> tradeDetailList = tradeDetailService.selectByExample(tradeDetailExample);
        	if(tradeDetailList!=null && tradeDetailList.size()>0){
        		rtObj.put("code", 0);
        		rtObj.put("message", "接收成功");
        		response.getWriter().write(rtObj.toString());
        		return;
        	}
        	
        	DebitNoteExample debitNoteExample = new DebitNoteExample();
			debitNoteExample.createCriteria().andOrderCodeEqualTo(reqMsgId);
			List<DebitNote> debitNotes_tmp = debitNoteService.selectByExample(debitNoteExample);
			if (debitNotes_tmp == null || debitNotes_tmp.size() == 0) {
				rtObj.put("code", 1);
        		rtObj.put("message", "订单不存在");
				logger.info(rtObj.toString());
                response.getWriter().write(rtObj.toString());
        		return;
				
			}
			DebitNote debitNote = debitNotes_tmp.get(0);
        	MemberMerchantKeyExample memberMerchantKeyExample = new MemberMerchantKeyExample();
            memberMerchantKeyExample.createCriteria().andRouteCodeEqualTo(RouteCodeConstant.POSP_ROUTE_CODE).andMerchantCodeEqualTo(debitNote.getMerchantCode()).andDelFlagEqualTo("0");
            List<MemberMerchantKey> keyList = memberMerchantKeyService.selectByExample(memberMerchantKeyExample);
            if(keyList == null || keyList.size()!=1){
            	rtObj.put("code", 1);
        		rtObj.put("message", "商户私钥未配置");
				logger.info(rtObj.toString());
                response.getWriter().write(rtObj.toString());
        		return;
            }
            
            String str = keyList.get(0).getPrivateKey() + data + keyList.get(0).getPrivateKey();
    		String sign_ = "";
    		try {
    			sign_ = ApiUtils.byte2hex(ApiUtils.md5(str));
    		} catch (Exception e) {
    		}
    		
    		if (!StringUtils.equals(sign_, sign)) {
    			System.out.println("待加密串："+str);
         	    System.out.println("加密结果："+sign_);
    			rtObj.put("code", 1);
        		rtObj.put("message", "验证签名不通过");
				logger.info(rtObj.toString());
                response.getWriter().write(rtObj.toString());
        		return;
    		}
        	
        	String result_code = map.getString("status");
        	String result_message = map.getString("failure_msg");
        	MsResultNoticeExample msResultNoticeExample = new MsResultNoticeExample();
			msResultNoticeExample.or().andOrderCodeEqualTo(reqMsgId);
			List<MsResultNotice> msResultNoticeList = msResultNoticeService.selectByExample(msResultNoticeExample);
			if(null == msResultNoticeList || msResultNoticeList.size() == 0){
				if (debitNote != null ) {
					debitNote.setRespCode(result_code);
					debitNote.setRespMsg(result_message);
					
					//新增一条交易明细记录
					TradeDetail tradeDetail=new TradeDetail();
					tradeDetail.setTxnDate(DateUtil.getDateFormat(new Date(), "yyyyMMdd"));
					tradeDetail.setMemberId(debitNote.getMemberId());
					tradeDetail.setMerchantCode(debitNote.getMerchantCode());
					if(map.containsKey("transaction_no")){
						tradeDetail.setChannelNo(map.getString("transaction_no"));
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
					if ("20".equals(result_code)&&map.getInt("paid")==1) {
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
					msResultNotice.setMoney(new BigDecimal(map.getString("amount")));
					msResultNotice.setOrderCode(reqMsgId);
					if(map.containsKey("transaction_no")){
						msResultNotice.setPtSerialNo(map.getString("transaction_no"));
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
						if ("20".equals(result_code)&&map.getInt("paid")==1) {
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
					if ("20".equals(result_code)&&map.getInt("paid")==1) {
						tradeDetail.setCardType(msResultNotice.getCardType());
						tradeDetailService.insertSelective(tradeDetail);
						
						
						TradeDetailDaily tradeDetailDaily = new TradeDetailDaily();
						
						tradeDetailDaily.setTxnDate(DateUtil.getDateFormat(new Date(), "yyyyMMdd"));
						tradeDetailDaily.setMemberId(debitNote.getMemberId());
						tradeDetailDaily.setMerchantCode(debitNote.getMerchantCode());
						tradeDetailDaily.setMoney(debitNote.getMoney());
						tradeDetailDaily.setOrderCode(debitNote.getOrderCode());
						tradeDetailDaily.setRouteId(debitNote.getRouteId());
						tradeDetailDaily.setDelFlag("0");
						tradeDetailDaily.setCreateDate(new Date());
						tradeDetailDailyService.insertSelective(tradeDetailDaily);
					}
					
					
					
					if(payResultNoticeList.size() > 0){
						payResultNotifyService.notify(payResultNotice);
						
					}
					
				}
			}	
            
        } catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}
		try {
			rtObj.put("code", 0);
    		rtObj.put("message", "接收成功");
    		response.getWriter().write(rtObj.toString());
		} catch (IOException e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}

	}
	//微微聚合支付
	public JSONObject wwH5Pay(String platformType,MemberInfo memberInfo,MemberPayType memberPayType,String payMoney,String orderNumOuter,String sceneInfo,String ip,String callbackUrl,MemberMerchantCode merchantCode,String userAgent,String payType) {
		JSONObject result = new JSONObject();
		try {
			
			String merCode = "";
			String payTypeStr = "";
			if("1".equals(payType)){
				merCode = merchantCode.getWxMerchantCode();
				payTypeStr = "WX";
			}else if("3".equals(payType)){
				merCode = merchantCode.getQqMerchantCode();
				payTypeStr = "QQ";
			}
			String routeCode = RouteCodeConstant.WW_ROUTE_CODE;
			
			MemberMerchantKeyExample memberMerchantKeyExample = new MemberMerchantKeyExample();
	        memberMerchantKeyExample.createCriteria().andRouteCodeEqualTo(routeCode).andMerchantCodeEqualTo(merCode).andDelFlagEqualTo("0");
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
			debitNote.setRouteId(routeCode);
			debitNote.setStatus("0");
			debitNote.setTxnMethod(PayTypeConstant.PAY_METHOD_H5);
			debitNote.setTxnType(payType);
			if("1".equals(payType)){
				debitNote.setMemberCode(memberInfo.getWxMemberCode());
				debitNote.setTradeRate(memberPayType.getT0TradeRate());
			}else if("3".equals(payType)){
				debitNote.setMemberCode(memberInfo.getQqMemberCode());
				debitNote.setTradeRate(memberPayType.getT0TradeRate());
			}
			debitNote.setMerchantCode(merCode);
			debitNote.setSettleType("0");
			debitNote.setIp(ip);
			debitNote.setUserAgent(userAgent.length()>1000?userAgent.substring(0, 1000):userAgent);
			debitNote.setBrowser(DeviceRequestUtil.getBrowser(userAgent));
			debitNote.setDeviceInfo(DeviceRequestUtil.getDevice(userAgent));
			
			
			String configName = "SINGLE_MEMBER_LIMIT_"+memberInfo.getId()+"_006_"+payTypeStr;
			JSONObject memResult = checkLimitMoney(configName, new BigDecimal(payMoney));
			if(null != memResult){
				debitNote.setStatus("9");
				debitNoteService.insertSelective(debitNote);
				return memResult;
			}
			
			configName = "SINGLE_MIN_"+routeCode+"_006_"+payTypeStr;
			JSONObject checkResult = checkMinMoney(configName, new BigDecimal(payMoney));
			if(null != checkResult){
				debitNote.setStatus("5");
				debitNoteService.insertSelective(debitNote);
				return checkResult;
			}
			
			configName = "SINGLE_LIMIT_"+routeCode+"_006_"+payTypeStr;
			JSONObject limitResult = checkLimitMoney(configName, new BigDecimal(payMoney));
			if(null != limitResult){
				debitNote.setStatus("4");
				debitNoteService.insertSelective(debitNote);
				return limitResult;
			}
			
			JSONObject timeResult = commonUtilService.checkLimitIpFail(PayTypeConstant.PAY_METHOD_H5, payTypeStr, memberInfo.getId(), ip);
			if(null != timeResult){
				debitNote.setStatus("12");
				debitNoteService.insertSelective(debitNote);
				return timeResult;
			}
			
			JSONObject ipResult = checkLimitIp(PayTypeConstant.PAY_METHOD_H5, payTypeStr, memberInfo.getId(), routeCode, merCode, ip);
			if(null != ipResult){
				debitNote.setStatus("6");
				debitNoteService.insertSelective(debitNote);
				return ipResult;
			}
			
			JSONObject mResult = checkLimitMerchantMoney(routeCode,merCode);
			if(null != mResult){
				debitNote.setStatus("7");
				debitNoteService.insertSelective(debitNote);
				return mResult;
			}
			
			JSONObject tResult = checkLimitCounts(routeCode);
			if(null != tResult){
				debitNote.setStatus("8");
				debitNoteService.insertSelective(debitNote);
				return tResult;
			}
			
			debitNoteService.insertSelective(debitNote);
			
			PayResultNotice payResultNotice = new PayResultNotice();
			payResultNotice.setOrderCode(debitNote.getOrderCode());
			payResultNotice.setOrderNumOuter(orderNumOuter);
			payResultNotice.setPayMoney(debitNote.getMoney());
			payResultNotice.setMemberCode(memberInfo.getCode());
			payResultNotice.setPayType(payType);
			
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
			
			
			String callBack = SysConfig.serverUrl + "/debitNote/wwH5PayNotify";
			String serverUrl = "http://47.97.175.195:8692/pay/payment/toH5";
			
			
			Map<String, String> params = new HashMap<String, String>();
			
			params.put("memberCode", merCode);
			params.put("orderNum", orderCode);
			params.put("payMoney", payMoney);
			params.put("payType", payType);
			params.put("sceneInfo", sceneInfo);
			params.put("ip", ip);
			params.put("callbackUrl", callBack);
			
			String srcStr = StringUtil.orderedKey(params);
			String privateKey = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQCSmKhAhwJSYMjkOptHm7fcxICZBtLgAFvT+ybg6wmO2Yc44z2soin//wFrtwh4tN9ocvcIrTsfVV1+WSkmXpjGFh6aM5n7KzozfnpXlVKZzIC3BRAc9N/JjofixyHXo2U8UT8b+HqjZNqngivfsHs9kyZ41/Q7QDZtbiO/0l3e6J5xay4pzTYyyB1+s/Njc4BILPnfJQZ4Gia8r1Cz4/79fqLIyzBfgOpU/FdfWOO9QCXhViHGYVSA6ZrAU/uG6pqyRBD4FBd0lzaYh1hYO/8EZi6VvAU8YlX3W8sPO8+NpVg9gzdtOXoY+IKX7yNUO50VLFGyH4BJAeWu+HzKUY/HAgMBAAECggEASFpkIIUCFlqCjHKIlU078BBkUCLYDYrfx75bsAMRUpn2bZNqqzvWcu0PbHSID9tlsI2dc4Dcf1iIroWfE7r4uDvOmtkBFMB/F09DXtrqS9JtbOdozoHluftJAhq6j02r95s6BLoc/WwXpzlOIjdefEOWZ2petqe4tUX5CwHvJsb0+5RU41OPRKlx5IxLYCIwif4LpZlgGsUhuevnq1Y4AUrT3D90ovTsB5GS9Upwc90umWVZvxxmoE8d+DrRkOUXqMR373sNixZsecapaBYJtDP1VgGtB7g/y9jLTFmoNAFEmxB3JBCo1mTx+FCizVnjGlTxRgtw81t6DBXQ5K7kEQKBgQDT+VC43Y2mf16IqIXJvc+AvxEpt1WzoYYVDGOVYDxH7cQT2YDoOjLMt0qcZsNrCt+PwGd8MsJIklJx0vkCcW+0BoulrsYtsvNt4JhF67htTiNlQ9vZtDdaasiHI1uRcTAdmYC7Gn5pevfjh2/0vG+3j0rRxxzZMTSG+2KSzetlJQKBgQCxCzTPPXXps7ETb5OD8n49tbm9iyjSfyllAN4at6mmwvfSnEHmv17vF1OHWKdoV9rgYJCJBIsKgHSSVi8AhpZ8gf6ZQPRon5+0I7AMRsAt9OX4PKAWLv5toHu4AEmgcVIRf7SnT2R1R8TA9bm1T/m7u6q8J6ETZ0qUsnqJxjPrewKBgQDIbvluJEh5g/xOehMvhKQ91+0RpgKKfRP8uR88sqetV9zM7CBg9g7s1e/d3amwgLKOCd+QZ6qf1oPQjy3Xqo12C65mNtG7ind1kSZLDy3vZTBA455xx1+Dxm7goYVHDGDzzB8WTGH1uUnGQl2Ra9CzH/IeVAkqMcW6UN+Pcti/ZQKBgQCBuxt0bK31lSXBfzYSLuQQphQ37zv3mxSjHZKi80hOmXlti/DCfCK9glNDz1PEovQRDNF8haa5CE3jWWHRuc2V6M++TxmJfMZTAv+iz0lNo7HMR+hquP7ZKArgVt2cws0HY5PmMcgEJJXpa2YslTEDPs4qV8OxazP8aBhg0qsC9wKBgHUvp5qsK0BI2oPUNe6tegL8sKrP8dCFLlEeq7ZOft/j5FVjUzpPP47FiiKZ1AY00zPxhWTg+sZOOEBrePutN435XTJRd29W+cG8uZSWHh8vmYLal3P7j6sPpHpjAMEv9Hs+DamG64/o0rXCDSJUYgILOyqWm1b57x2z9DRz8ttv";
	 		String signstr = EpaySignUtil.sign(privateKey, srcStr);
			result.putAll(params);
			result.put("signStr", signstr);
			result.put("payUrl", serverUrl);
			result.put("returnCode", "0000");
			result.put("returnMsg", "成功");
	        
	        try{
		        DebitNoteIp debitNoteIp = new DebitNoteIp();
		        debitNoteIp.setMemberId(memberInfo.getId());
		        debitNoteIp.setMerchantCode(merCode);
		        debitNoteIp.setOrderCode(orderCode);
		        debitNoteIp.setRouteId(routeCode);
		        debitNoteIp.setTxnMethod(PayTypeConstant.PAY_METHOD_H5);
		        debitNoteIp.setTxnType(payType);
		        debitNoteIp.setIp(ip);
		        debitNoteIp.setCreateDate(debitNote.getCreateDate());
				debitNoteIpService.insertSelective(debitNoteIp);
	        }catch(Exception ex){
	        	logger.error(ex.getMessage());
	        }
        } catch (Exception e) {
			logger.error(e.getMessage());
			result.put("returnCode", "0096");
			result.put("returnMsg", e.getMessage());
			return result;
		}
		return result;
	}
	
	@RequestMapping("/debitNote/wwH5PayNotify")
	public void wwH5PayNotify(HttpServletRequest request,HttpServletResponse response) {
		JSONObject rtObj = new JSONObject();
		try {
			
			Map<String,String> params = new HashMap<String,String>();
			String responseStr = HttpUtil.getPostString(request);
			JSONObject resJo = JSONObject.fromObject(responseStr);
			logger.info("wwH5PayNotify回调返回报文[{}]",  responseStr );
			if(resJo.containsKey("memberCode")){
				String memberCode = resJo.getString("memberCode");
				params.put("memberCode", memberCode);
			}
			if(resJo.containsKey("orderNum")){
				String orderNum = resJo.getString("orderNum");
				params.put("orderNum", orderNum);
			}
			if(resJo.containsKey("payNum")){
				String payNum = resJo.getString("payNum");
				params.put("payNum", payNum);
			}
			if(resJo.containsKey("payType")){
				String payType = resJo.getString("payType");
				params.put("payType", payType);
			}
			if(resJo.containsKey("payMoney")){
				String payMoney = resJo.getString("payMoney");
				params.put("payMoney", payMoney);
			}
			if(resJo.containsKey("payTime")){
				String payTime = resJo.getString("payTime");
				params.put("payTime", payTime);
			}
			if(resJo.containsKey("platformType")){
				String platformType = resJo.getString("platformType");
				params.put("platformType", platformType);
			}
			if(resJo.containsKey("interfaceType")){
				String interfaceType = resJo.getString("interfaceType");
				params.put("interfaceType", interfaceType);
			}
			if(resJo.containsKey("respType")){
				String respType = resJo.getString("respType");
				params.put("respType", respType);
			}
			if(resJo.containsKey("resultCode")){
				String resultCode = resJo.getString("resultCode");
				params.put("resultCode", resultCode);
			}
			if(resJo.containsKey("resultMsg")){
				String resultMsg = resJo.getString("resultMsg");
				params.put("resultMsg", resultMsg);
			}
			String signStr = resJo.getString("signStr");
			String srcStr = StringUtil.orderedKey(params);
			System.out.println("（接收）排序后的参数串："+srcStr);
			String pubKey = SysConfig.platPublicKey;
			
			
			String reqMsgId = resJo.getString("orderNum");
        	
        	TradeDetailExample tradeDetailExample = new TradeDetailExample();
        	tradeDetailExample.or().andOrderCodeEqualTo(reqMsgId);
        	List<TradeDetail> tradeDetailList = tradeDetailService.selectByExample(tradeDetailExample);
        	if(tradeDetailList!=null && tradeDetailList.size()>0){
        		rtObj.put("resCode", "0000");
        		response.getWriter().write(rtObj.toString());
        		return;
        	}
        	
        	DebitNoteExample debitNoteExample = new DebitNoteExample();
			debitNoteExample.createCriteria().andOrderCodeEqualTo(reqMsgId);
			List<DebitNote> debitNotes_tmp = debitNoteService.selectByExample(debitNoteExample);
			if (debitNotes_tmp == null || debitNotes_tmp.size() == 0) {
				rtObj.put("resCode", "0001");
				logger.info(rtObj.toString());
                response.getWriter().write(rtObj.toString());
        		return;
				
			}
			DebitNote debitNote = debitNotes_tmp.get(0);
        	
    		if (!EpaySignUtil.checksign(pubKey, srcStr, signStr)) {
    			rtObj.put("resCode", "0002");
				logger.info(rtObj.toString());
                response.getWriter().write(rtObj.toString());
        		return;
    		}
        	
        	String result_code = resJo.getString("resultCode");
        	String result_message = resJo.getString("resultMsg");
        	String respType = resJo.getString("respType");
        	MsResultNoticeExample msResultNoticeExample = new MsResultNoticeExample();
			msResultNoticeExample.or().andOrderCodeEqualTo(reqMsgId);
			List<MsResultNotice> msResultNoticeList = msResultNoticeService.selectByExample(msResultNoticeExample);
			if(null == msResultNoticeList || msResultNoticeList.size() == 0){
				if (debitNote != null ) {
					debitNote.setRespCode(result_code);
					debitNote.setRespMsg(result_message);
					
					//新增一条交易明细记录
					TradeDetail tradeDetail=new TradeDetail();
					tradeDetail.setTxnDate(DateUtil.getDateFormat(new Date(), "yyyyMMdd"));
					tradeDetail.setMemberId(debitNote.getMemberId());
					tradeDetail.setMerchantCode(debitNote.getMerchantCode());
					if(resJo.containsKey("payNum")){
						tradeDetail.setChannelNo(resJo.getString("payNum"));
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
					if ("0000".equals(result_code)&&"2".equals(respType)) {
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
					msResultNotice.setMoney(new BigDecimal(resJo.getString("payMoney")));
					msResultNotice.setOrderCode(reqMsgId);
					if(resJo.containsKey("payNum")){
						msResultNotice.setPtSerialNo(resJo.getString("payNum"));
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
						if ("0000".equals(result_code)&&"2".equals(respType)) {
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
					if ("0000".equals(result_code)&&"2".equals(respType)) {
						tradeDetail.setCardType(msResultNotice.getCardType());
						tradeDetailService.insertSelective(tradeDetail);
						
						
						TradeDetailDaily tradeDetailDaily = new TradeDetailDaily();
						
						tradeDetailDaily.setTxnDate(DateUtil.getDateFormat(new Date(), "yyyyMMdd"));
						tradeDetailDaily.setMemberId(debitNote.getMemberId());
						tradeDetailDaily.setMerchantCode(debitNote.getMerchantCode());
						tradeDetailDaily.setMoney(debitNote.getMoney());
						tradeDetailDaily.setOrderCode(debitNote.getOrderCode());
						tradeDetailDaily.setRouteId(debitNote.getRouteId());
						tradeDetailDaily.setDelFlag("0");
						tradeDetailDaily.setCreateDate(new Date());
						tradeDetailDailyService.insertSelective(tradeDetailDaily);
					}
					
					
					
					if(payResultNoticeList.size() > 0){
						payResultNotifyService.notify(payResultNotice);
						
					}
					
				}
			}	
            
        } catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}
		try {
			rtObj.put("resCode", "0000");
    		response.getWriter().write(rtObj.toString());
		} catch (IOException e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}

	}
	
	
	public JSONObject hlbH5Pay(String platformType,MemberInfo memberInfo,MemberPayType memberPayType,String payMoney,String orderNumOuter,String sceneInfo,String ip,String callbackUrl,MemberMerchantCode merchantCode,String userAgent,String routeCode,String aisleType,String payType) {
		JSONObject result = new JSONObject();
		try {
			String merCode = "";
			String memberCode = "";
			String payTypeStr = "";
			String eskPayType = "";
			if("3".equals(payType)){
				merCode = merchantCode.getQqMerchantCode();
				memberCode = memberInfo.getQqMemberCode();
				payTypeStr = PayTypeConstant.PAY_TYPE_QQ;
				eskPayType = "QQPAY";
			}
			
			MemberMerchantKeyExample memberMerchantKeyExample = new MemberMerchantKeyExample();
	        memberMerchantKeyExample.createCriteria().andRouteCodeEqualTo(routeCode).andMerchantCodeEqualTo(merCode).andDelFlagEqualTo("0");
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
			debitNote.setRouteId(routeCode);
			debitNote.setStatus("0");
			debitNote.setTxnMethod(PayTypeConstant.PAY_METHOD_H5);
			debitNote.setTxnType(payType);
			debitNote.setMemberCode(memberCode);
			debitNote.setMerchantCode(merCode);
			debitNote.setIp(ip);
			debitNote.setUserAgent(userAgent.length()>1000?userAgent.substring(0, 1000):userAgent);
			debitNote.setBrowser(DeviceRequestUtil.getBrowser(userAgent));
			debitNote.setDeviceInfo(DeviceRequestUtil.getDevice(userAgent));
			debitNote.setSettleType(memberInfo.getSettleType());
			if("0".equals(memberInfo.getSettleType())){
				debitNote.setTradeRate(memberPayType.getT0TradeRate());
			}else{
				debitNote.setTradeRate(memberPayType.getT1TradeRate());
			}
			
			String configName = "SINGLE_MEMBER_LIMIT_"+memberInfo.getId()+"_006_"+payTypeStr;
			JSONObject memResult = checkLimitMoney(configName, new BigDecimal(payMoney));
			if(null != memResult){
				debitNote.setStatus("9");
				debitNoteService.insertSelective(debitNote);
				return memResult;
			} 
			
			configName = "SINGLE_MIN_"+routeCode+"_006_"+payTypeStr;
			JSONObject checkResult = checkMinMoney(configName, new BigDecimal(payMoney));
			if(null != checkResult){
				debitNote.setStatus("5");
				debitNoteService.insertSelective(debitNote);
				return checkResult;
			}
			
			JSONObject timeResult = commonUtilService.checkLimitIpFail(PayTypeConstant.PAY_METHOD_H5, payTypeStr, memberInfo.getId(), ip);
			if(null != timeResult){
				debitNote.setStatus("12");
				debitNoteService.insertSelective(debitNote);
				return timeResult;
			}
			
			configName = "SINGLE_LIMIT_"+routeCode+"_006_"+payTypeStr;
			JSONObject limitResult = checkLimitMoney(configName, new BigDecimal(payMoney));
			if(null != limitResult){
				debitNote.setStatus("4");
				debitNoteService.insertSelective(debitNote);
				return limitResult;
			}
			
			JSONObject ipResult = checkLimitIp(PayTypeConstant.PAY_METHOD_H5, payTypeStr, memberInfo.getId(), routeCode, merCode, ip);
			if(null != ipResult){
				debitNote.setStatus("6");
				debitNoteService.insertSelective(debitNote);
				return ipResult;
			}
			
			JSONObject mResult = checkLimitMerchantMoney(routeCode,merCode);
			if(null != mResult){
				debitNote.setStatus("7");
				debitNoteService.insertSelective(debitNote);
				return mResult;
			}
			
			JSONObject tResult = checkLimitCounts(routeCode);
			if(null != tResult){
				debitNote.setStatus("8");
				debitNoteService.insertSelective(debitNote);
				return tResult;
			}
			
			debitNoteService.insertSelective(debitNote);
			
			PayResultNotice payResultNotice = new PayResultNotice();
			payResultNotice.setOrderCode(debitNote.getOrderCode());
			payResultNotice.setOrderNumOuter(orderNumOuter);
			payResultNotice.setPayMoney(debitNote.getMoney());
			payResultNotice.setMemberCode(memberInfo.getCode());
			payResultNotice.setPayType(payType);
			
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
			
			String callBack = SysConfig.serverUrl + "/cashierDesk/hlbPayNotify";
			String serverUrl = HLBConfig.msServerUrl;
			String charset = "utf-8";
			
			Map<String,String> sPara = new HashMap<String,String>();
			String goodsName = memberInfo.getName() + " 收款";
			sPara.put("P1_bizType","AppPay");
			sPara.put("P2_orderId",orderCode);
			sPara.put("P3_customerNumber",merCode);
			sPara.put("P4_payType","SCAN");//SWIPE:刷卡(被扫) SCAN:扫码(主扫)
			sPara.put("P5_orderAmount",payMoney);
			sPara.put("P6_currency","CNY");
			sPara.put("P7_authcode","1");//payType为刷卡(被扫)时传入一组字符串(付款码),主扫支付类型传入1即可
			sPara.put("P8_appType",eskPayType);
			sPara.put("P9_notifyUrl",callBack);
			sPara.put("P10_successToUrl","");
			sPara.put("P11_orderIp",ip);
			sPara.put("P12_goodsName",goodsName);
			sPara.put("P13_goodsDetail","");
			sPara.put("P14_desc","");
			
			String split = "&";
			StringBuffer sb = new StringBuffer();
			sb.append(split).append("AppPay").append(split).append(orderCode).append(split)
					.append(merCode).append(split).append("SCAN").append(split).append(payMoney)
					.append(split).append("CNY");
			sb.append(split).append("1");
			sb.append(split).append(eskPayType);
			sb.append(split).append(callBack);
			sb.append(split).append("");
			sb.append(split).append(ip);
			sb.append(split).append(goodsName);
			sb.append(split).append("");
			sb.append(split).append("");
			sb.append(split).append(merchantKey.getPrivateKey());
			
			String sign = Disguiser.disguiseMD5(sb.toString());
			sPara.put("sign",sign);
			logger.info("合利宝H5请求数据[{}]", new Object[] { JSONObject.fromObject(sPara).toString() });
			
			List<NameValuePair> nvps = new LinkedList<NameValuePair>();
			List<String> keys = new ArrayList<String>(sPara.keySet());
			
			for (int i = 0; i < keys.size(); i++) {
				 String name=(String) keys.get(i);
				 String value=(String) sPara.get(name);
				if(value!=null && !"".equals(value)){
					nvps.add(new BasicNameValuePair(name, value));
				}
			}
			
			byte[] b = HttpClient4Util.getInstance().doPost(serverUrl, null, nvps);
			String respStr = new String(b, charset);
			logger.info("合利宝H5返回报文[{}]", new Object[] { respStr });
			
			JSONObject jsonObject = JSONObject.fromObject(respStr);
			
			String resultCode = jsonObject.getString("rt2_retCode");
			String resultMsg = jsonObject.getString("rt3_retMsg");
			boolean flag = false;
			if ("0000".equals(resultCode)) {
				sb = new StringBuffer();
				sb.append(split).append(jsonObject.getString("rt1_bizType"));
				sb.append(split).append(jsonObject.getString("rt2_retCode"));
				sb.append(split).append(jsonObject.getString("rt4_customerNumber"));
				sb.append(split).append(jsonObject.getString("rt5_orderId"));
				sb.append(split).append(jsonObject.getString("rt6_serialNumber"));
				sb.append(split).append(jsonObject.getString("rt7_payType"));
				sb.append(split).append(jsonObject.getString("rt8_qrcode"));
				sb.append(split).append(jsonObject.getString("rt9_wapurl"));
				sb.append(split).append(jsonObject.getString("rt10_orderAmount"));
				sb.append(split).append(jsonObject.getString("rt11_currency"));
				sb.append(split).append(merchantKey.getPrivateKey());
				sign = Disguiser.disguiseMD5(sb.toString());
				if(!sign.equals(jsonObject.getString("sign"))){
					resultMsg = "验签失败";
				}else{
					result.put("payUrl", jsonObject.getString("rt8_qrcode"));
					result.put("returnCode", "0000");
					result.put("returnMsg", "成功");
					flag = true;
				}
			}
			if(!flag){
				result.put("returnCode", "0009");
				result.put("returnMsg", resultMsg);
				
				DebitNoteExample debitNoteExample = new DebitNoteExample();
				debitNoteExample.createCriteria().andOrderCodeEqualTo(orderCode);
				List<DebitNote> debitNotes = debitNoteService.selectByExample(debitNoteExample);
				if (debitNotes != null && debitNotes.size() > 0) {
					DebitNote debitNote_1 = debitNotes.get(0);
					debitNote_1.setStatus("2");
					debitNote_1.setUpdateDate(new Date());
					debitNote_1.setRespCode(resultCode);
					if(!"".equals(resultMsg)){
						debitNote_1.setRespMsg(resultMsg.length()>250?resultMsg.substring(0, 250):resultMsg);
					}
					debitNoteService.updateByPrimaryKey(debitNote_1);
				}
			}
			
			try{
		        DebitNoteIp debitNoteIp = new DebitNoteIp();
		        debitNoteIp.setMemberId(memberInfo.getId());
		        debitNoteIp.setMerchantCode(merCode);
		        debitNoteIp.setOrderCode(orderCode);
		        debitNoteIp.setRouteId(routeCode);
		        debitNoteIp.setTxnMethod(PayTypeConstant.PAY_METHOD_H5);
		        debitNoteIp.setTxnType(payType);
		        debitNoteIp.setIp(ip);
		        debitNoteIp.setCreateDate(debitNote.getCreateDate());
				debitNoteIpService.insertSelective(debitNoteIp);
	        }catch(Exception ex){
	        	logger.error(ex.getMessage());
	        }
			
        } catch (Exception e) {
			logger.error(e.getMessage());
			result.put("returnCode", "0096");
			result.put("returnMsg", e.getMessage());
			return result;
		}
		return result;
	}
	//多得宝h5支付
	public JSONObject ddbH5Pay(String platformType,MemberInfo memberInfo,MemberPayType memberPayType,String payMoney,String orderNumOuter,String sceneInfo,String ip,String callbackUrl,MemberMerchantCode merchantCode,String userAgent,String routeCode,String aisleType,String payType) {
		JSONObject result = new JSONObject();
		try {
			String merCode = "";
			String memberCode = "";
			String payTypeStr = "";
			String eskPayType = "";
			if("3".equals(payType)){
				merCode = merchantCode.getQqMerchantCode();
				memberCode = memberInfo.getQqMemberCode();
				payTypeStr = PayTypeConstant.PAY_TYPE_QQ;
				eskPayType = "qq_h5api";
			}else if("1".equals(payType)){
				merCode = merchantCode.getWxMerchantCode();
				memberCode = memberInfo.getWxMemberCode();
				payTypeStr = PayTypeConstant.PAY_TYPE_WX;
				eskPayType = "weixin_h5api";
			}
			
			MemberMerchantKeyExample memberMerchantKeyExample = new MemberMerchantKeyExample();
	        memberMerchantKeyExample.createCriteria().andRouteCodeEqualTo(routeCode).andMerchantCodeEqualTo(merCode).andDelFlagEqualTo("0");
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
			debitNote.setRouteId(routeCode);
			debitNote.setStatus("0");
			debitNote.setTxnMethod(PayTypeConstant.PAY_METHOD_H5);
			debitNote.setTxnType(payType);
			debitNote.setMemberCode(memberCode);
			debitNote.setMerchantCode(merCode);
			debitNote.setIp(ip);
			debitNote.setUserAgent(userAgent.length()>1000?userAgent.substring(0, 1000):userAgent);
			debitNote.setBrowser(DeviceRequestUtil.getBrowser(userAgent));
			debitNote.setDeviceInfo(DeviceRequestUtil.getDevice(userAgent));
			debitNote.setSettleType(memberInfo.getSettleType());
			if("0".equals(memberInfo.getSettleType())){
				debitNote.setTradeRate(memberPayType.getT0TradeRate());
			}else{
				debitNote.setTradeRate(memberPayType.getT1TradeRate());
			}
			
			String configName = "SINGLE_MEMBER_LIMIT_"+memberInfo.getId()+"_006_"+payTypeStr;
			JSONObject memResult = checkLimitMoney(configName, new BigDecimal(payMoney));
			if(null != memResult){
				debitNote.setStatus("9");
				debitNoteService.insertSelective(debitNote);
				return memResult;
			} 
			
			configName = "SINGLE_MIN_"+routeCode+"_006_"+payTypeStr;
			JSONObject checkResult = checkMinMoney(configName, new BigDecimal(payMoney));
			if(null != checkResult){
				debitNote.setStatus("5");
				debitNoteService.insertSelective(debitNote);
				return checkResult;
			}
			
			configName = "SINGLE_LIMIT_"+routeCode+"_006_"+payTypeStr;
			JSONObject limitResult = checkLimitMoney(configName, new BigDecimal(payMoney));
			if(null != limitResult){
				debitNote.setStatus("4");
				debitNoteService.insertSelective(debitNote);
				return limitResult;
			}
			
			JSONObject timeResult = commonUtilService.checkLimitIpFail(PayTypeConstant.PAY_METHOD_H5, payTypeStr, memberInfo.getId(), ip);
			if(null != timeResult){
				debitNote.setStatus("12");
				debitNoteService.insertSelective(debitNote);
				return timeResult;
			}
			
			JSONObject ipResult = checkLimitIp(PayTypeConstant.PAY_METHOD_H5, payTypeStr, memberInfo.getId(), routeCode, merCode, ip);
			if(null != ipResult){
				debitNote.setStatus("6");
				debitNoteService.insertSelective(debitNote);
				return ipResult;
			}
			
			JSONObject mResult = checkLimitMerchantMoney(routeCode,merCode);
			if(null != mResult){
				debitNote.setStatus("7");
				debitNoteService.insertSelective(debitNote);
				return mResult;
			}
			
			JSONObject tResult = checkLimitCounts(routeCode);
			if(null != tResult){
				debitNote.setStatus("8");
				debitNoteService.insertSelective(debitNote);
				return tResult;
			}
			
			debitNoteService.insertSelective(debitNote);
			
			PayResultNotice payResultNotice = new PayResultNotice();
			payResultNotice.setOrderCode(debitNote.getOrderCode());
			payResultNotice.setOrderNumOuter(orderNumOuter);
			payResultNotice.setPayMoney(debitNote.getMoney());
			payResultNotice.setMemberCode(memberInfo.getCode());
			payResultNotice.setPayType(payType);
			
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
			
			
			
			String callBack = SysConfig.serverUrl + "/debitNote/ddbPayNotify";
			// 调用支付通道
			String serverUrl = DDBConfig.msServerUrl;
			
			String signType = "RSA-S";
			String charset = "utf-8";
			String version = "V3.3";
			String orderTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
			String product_name = memberInfo.getName() + "收款";
			
			Map<String, String> reqMap = new HashMap<String, String>();
			reqMap.put("merchant_code", merCode);
			reqMap.put("service_type", eskPayType);
			reqMap.put("notify_url", callBack);
			reqMap.put("interface_version", version);
			reqMap.put("client_ip", ip);
			reqMap.put("sign_type", signType);
			reqMap.put("order_no", orderCode);
			reqMap.put("order_time", orderTime);
			reqMap.put("order_amount", payMoney);
			reqMap.put("product_name", product_name);
			
			StringBuffer signSrc= new StringBuffer();
			signSrc.append("client_ip=").append(ip).append("&");	
			signSrc.append("interface_version=").append(version).append("&");
			signSrc.append("merchant_code=").append(merCode).append("&");				
			signSrc.append("notify_url=").append(callBack).append("&");	
			signSrc.append("order_amount=").append(payMoney).append("&");
			signSrc.append("order_no=").append(orderCode).append("&");
			signSrc.append("order_time=").append(orderTime).append("&");
			signSrc.append("product_name=").append(product_name).append("&");
			signSrc.append("service_type=").append(eskPayType);		
				
			String signInfo = signSrc.toString();
			//String sign = RSAWithSoftware.signByPrivateKey(signInfo,merchantKey.getPrivateKey());	// 签名   signInfo签名参数排序，  merchant_private_key商户私钥  				
			String sign = EpaySignUtil.sign(merchantKey.getPrivateKey(), signInfo);
			reqMap.put("sign", sign);	
			logger.info("多得宝h5支付请求报文[{}]", new Object[] { JSONObject.fromObject(reqMap).toString() });
			
			String respStr = new HttpClientUtil().doPost(serverUrl, reqMap, charset);	
			
			logger.info("多得宝h5支付返回报文[{}]", new Object[] { respStr });
			
			String resp_code = HxUtils.getNodeText(respStr, "resp_code");
			String result_message = HxUtils.getNodeText(respStr, "resp_desc");
			boolean flag = false;
			if("SUCCESS".equals(resp_code)){
				String result_code = HxUtils.getNodeText(respStr, "result_code");
				if("0".equals(result_code)){
					result.put("payUrl",URLDecoder.decode(HxUtils.getNodeText(respStr, "payURL"), "UTF-8"));
					result.put("returnCode", "0000");
					result.put("returnMsg", "成功");
					flag = true;
				}else{
					result_message = HxUtils.getNodeText(respStr, "result_desc");
				}
			}
			if(!flag){
				result.put("returnCode", "0009");
				result.put("returnMsg", result_message);
				DebitNoteExample debitNoteExample = new DebitNoteExample();
				debitNoteExample.createCriteria().andOrderCodeEqualTo(orderCode);
				List<DebitNote> debitNotes = debitNoteService.selectByExample(debitNoteExample);
				if (debitNotes != null && debitNotes.size() > 0) {
					DebitNote debitNote_1 = debitNotes.get(0);
					debitNote_1.setStatus("2");
					debitNote_1.setUpdateDate(new Date());
					if(!"".equals(result_message)){
						debitNote_1.setRespMsg(result_message.length()>250?result_message.substring(0, 250):result_message);
					}
					debitNoteService.updateByPrimaryKey(debitNote_1);
				}
			}
			try{
		        DebitNoteIp debitNoteIp = new DebitNoteIp();
		        debitNoteIp.setMemberId(memberInfo.getId());
		        debitNoteIp.setMerchantCode(merCode);
		        debitNoteIp.setOrderCode(orderCode);
		        debitNoteIp.setRouteId(routeCode);
		        debitNoteIp.setTxnMethod(PayTypeConstant.PAY_METHOD_H5);
		        debitNoteIp.setTxnType(payType);
		        debitNoteIp.setIp(ip);
		        debitNoteIp.setCreateDate(debitNote.getCreateDate());
				debitNoteIpService.insertSelective(debitNoteIp);
	        }catch(Exception ex){
	        	logger.error(ex.getMessage());
	        }
			
        } catch (Exception e) {
			logger.error(e.getMessage());
			result.put("returnCode", "0096");
			result.put("returnMsg", e.getMessage());
			return result;
		}
		return result;
	}
	
	
	//个人扫码支付
	public JSONObject grsmH5Pay(String platformType,String payType,MemberInfo memberInfo,MemberPayType memberPayType,String payMoney,String orderNumOuter,String callbackUrl,MemberMerchantCode merchantCode,String routeCode,String aisleType,String ip) {
		JSONObject result = new JSONObject();
		try {
			String payTypeStr = "";
			String payMethod = PayTypeConstant.PAY_METHOD_H5;
			String merCode = "";
			BigDecimal tradeRate = null;
			if("2".equals(payType)){
				payTypeStr = "ZFB";
				merCode = merchantCode.getZfbMerchantCode();
			}
			if("0".equals(memberInfo.getSettleType())){
				tradeRate = memberPayType.getT0TradeRate();
			}else{
				tradeRate = memberPayType.getT1TradeRate();
			}
			// 插入一条收款记录
			String orderCode = CommonUtil.getOrderCode();
			
			DebitNote debitNote = new DebitNote();
			debitNote.setCreateDate(new Date());
			debitNote.setMemberId(memberInfo.getId());
			debitNote.setMoney(new BigDecimal(payMoney));
			debitNote.setOrderCode(orderCode);
			debitNote.setOrderNumOuter(orderNumOuter);
			debitNote.setRouteId(routeCode);
			debitNote.setStatus("0");
			debitNote.setIp(ip);
			debitNote.setTxnMethod(payMethod);
			debitNote.setTxnType(payType);
			debitNote.setMerchantCode(merCode);
			if("2".equals(payType)){
				debitNote.setMemberCode(memberInfo.getZfbMemberCode());
			}
			debitNote.setSettleType(memberInfo.getSettleType());
			debitNote.setTradeRate(tradeRate);
			
			
			String configName = "SINGLE_MEMBER_LIMIT_"+memberInfo.getId()+"_"+payMethod+"_"+payTypeStr;
			JSONObject memResult = checkLimitMoney(configName, new BigDecimal(payMoney));
			if(null != memResult){
				debitNote.setStatus("9");
				debitNoteService.insertSelective(debitNote);
				return memResult;
			} 
			
			configName = "SINGLE_MIN_"+routeCode+"_"+payMethod+"_"+payTypeStr;
			JSONObject checkResult = checkMinMoney(configName, new BigDecimal(payMoney));
			if(null != checkResult){
				debitNote.setStatus("5");
				debitNoteService.insertSelective(debitNote);
				return checkResult;
			}
			
			configName = "SINGLE_LIMIT_"+routeCode+"_"+payMethod+"_"+payTypeStr;
			JSONObject limitResult = checkLimitMoney(configName, new BigDecimal(payMoney));
			if(null != limitResult){
				debitNote.setStatus("4");
				debitNoteService.insertSelective(debitNote);
				return limitResult;
			}
			
			JSONObject ipResult = checkLimitIp(PayTypeConstant.PAY_METHOD_H5, payTypeStr, memberInfo.getId(), routeCode, merCode, ip);
			if(null != ipResult){
				debitNote.setStatus("6");
				debitNoteService.insertSelective(debitNote);
				return ipResult;
			}
			
			JSONObject mResult = checkLimitMerchantMoney(routeCode,merCode);
			if(null != mResult){
				debitNote.setStatus("7");
				debitNoteService.insertSelective(debitNote);
				return mResult;
			}
			
			JSONObject preResult = commonUtilService.checkPrePayMoney(memberInfo.getId(), tradeRate ,payMethod, payTypeStr, routeCode, new BigDecimal(payMoney));
			debitNote.setPreType(preResult.getString("preType"));
			if(!"0000".equals(preResult.getString("returnCode"))){
				debitNote.setStatus("2");
				debitNote.setRespMsg(preResult.getString("returnMsg"));
				debitNoteService.insertSelective(debitNote);
				return preResult;
			}
			
			//取超出限额的收款人
			List<PayQrCodeTotal> exceedList = commonUtilService.getExceedPayeeList(new BigDecimal(payMoney));
			List<PayQrCodeTotal> exceedCountsList = commonUtilService.getExceedCountsPayeeList();
			List<Integer> payeeList = new ArrayList<Integer>();
			List<Integer> includePayeeList = new ArrayList<Integer>();
			if(exceedList!=null && exceedList.size()>0){
				for(PayQrCodeTotal total:exceedList){
					payeeList.add(total.getPayeeId());
				}
			}
			if(exceedCountsList!=null && exceedCountsList.size()>0){
				for(PayQrCodeTotal total:exceedCountsList){
					payeeList.add(total.getPayeeId());
				}
			}
		/*	PayQrCodeExample payQrCodeExample = new PayQrCodeExample();
			if(payeeList!=null && payeeList.size()>0){
				payQrCodeExample.createCriteria().andMoneyEqualTo(new BigDecimal(payMoney)).andPayTypeEqualTo(payType).andStatusEqualTo("0").andDelFlagEqualTo("0").andPayeeIdNotIn(payeeList);
			}else{
				payQrCodeExample.createCriteria().andMoneyEqualTo(new BigDecimal(payMoney)).andPayTypeEqualTo(payType).andStatusEqualTo("0").andDelFlagEqualTo("0");
			}
			payQrCodeExample.setOrderByClause(" create_date asc");
			payQrCodeExample.setLimitStart(0);
			payQrCodeExample.setLimitSize(10);
			
			List<PayQrCode> qrCodeList = payQrCodeService.selectByExample(payQrCodeExample);
			*/
			Map<String,Object> param = new HashMap<String, Object>();
			param.put("memberId", memberInfo.getId());
			param.put("payType", payType);
			param.put("memberType", "1");
			List<MemberPayee> memberPayeeList = memberPayeeService.selectByMap(param);//专用收款人
			if(memberPayeeList != null && memberPayeeList.size()>0){
				for(MemberPayee memberPayee : memberPayeeList){
					includePayeeList.add(memberPayee.getPayeeId());
				}
			}
			param = new HashMap<String, Object>();
			param.put("memberId", memberInfo.getId());
			param.put("payType", payType);
			param.put("memberType", "2");
			List<MemberPayee> otherMemberPayeeList = memberPayeeService.selectByMap(param);//其他商户专用收款人
			if(otherMemberPayeeList !=null && otherMemberPayeeList.size()>0){
				for(MemberPayee memberPayee : otherMemberPayeeList){
					payeeList.add(memberPayee.getPayeeId());
				}
			}
			
			param = new HashMap<String, Object>();
			param.put("money", new BigDecimal(payMoney));
			param.put("payType", payType);
			param.put("tradeDate", DateUtil.getDateStr());
			param.put("limitStart", 0);
			param.put("limitSize", 10);
			if(payeeList!=null && payeeList.size()>0){
				param.put("payeeList", payeeList);
			}
			if(includePayeeList != null && includePayeeList.size()>0){
				param.put("memberPayeeList", includePayeeList);
			}
			List<PayQrCode> qrCodeList = payQrCodeService.selectByMap(param);
			
			PayQrCode payQrCode = null;
			if(qrCodeList!=null && qrCodeList.size()>0){
				for(PayQrCode qrcode :qrCodeList){
					try{
						PayQrCodeTemp payQrCodeTemp = new PayQrCodeTemp();
						payQrCodeTemp.setQrCodeId(qrcode.getId());
						payQrCodeTempService.insertSelective(payQrCodeTemp);
						payQrCode = qrcode;
						break;
					}catch(Exception e){
						//e.printStackTrace();
					}
					
				}
			}
			if(payQrCode == null){
				debitNote.setStatus("2");
				debitNote.setRespMsg("没有对应金额的收款码，无法支付");
				debitNoteService.insertSelective(debitNote);
				result.put("returnCode", "0008");
				result.put("returnMsg", "没有对应金额的收款码，无法支付");
				return result;
			}
			
			Payee payee = payeeService.selectByPrimaryKey(payQrCode.getPayeeId());
			if(payee == null){
				debitNote.setStatus("2");
				debitNote.setRespMsg("收款人不存在，无法支付");
				debitNoteService.insertSelective(debitNote);
				result.put("returnCode", "0008");
				result.put("returnMsg", "收款人不存在，无法支付");
				return result;
			}
			debitNote.setPayQrCodeId(payQrCode.getId());
			debitNote.setPayeeId(payQrCode.getPayeeId());
			debitNote.setPayAccount(payee.getPayAccount());
			debitNote.setPayUserName(payee.getUserName());
			debitNote.setRemarks(payQrCode.getQrCodeRemark());
			debitNoteService.insertSelective(debitNote);
			
			payQrCode.setStatus("1");
			payQrCode.setUpdateDate(new Date());
			payQrCodeService.updateByPrimaryKey(payQrCode);
			
			PayResultNotice payResultNotice = new PayResultNotice();
			payResultNotice.setOrderCode(debitNote.getOrderCode());
			payResultNotice.setOrderNumOuter(orderNumOuter);
			payResultNotice.setPayMoney(debitNote.getMoney());
			payResultNotice.setMemberCode(memberInfo.getCode());
			payResultNotice.setPayType(payType);
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
			
			result.put("payUrl", payQrCode.getQrCode());
			result.put("returnCode", "0000");
			result.put("returnMsg", "成功");
			
			try{
		        DebitNoteIp debitNoteIp = new DebitNoteIp();
		        debitNoteIp.setMemberId(memberInfo.getId());
		        debitNoteIp.setMerchantCode(merCode);
		        debitNoteIp.setOrderCode(orderCode);
		        debitNoteIp.setRouteId(routeCode);
		        debitNoteIp.setTxnMethod(PayTypeConstant.PAY_METHOD_H5);
		        debitNoteIp.setTxnType(payType);
		        debitNoteIp.setIp(ip);
		        debitNoteIp.setCreateDate(debitNote.getCreateDate());
				debitNoteIpService.insertSelective(debitNoteIp);
	        }catch(Exception ex){
	        	logger.error(ex.getMessage());
	        }
		} catch (Exception e) {
			logger.error(e.getMessage());
			result.put("returnCode", "0096");
			result.put("returnMsg", e.getMessage());
			return result;
		}
		return result;
	}
	
	public JSONObject tlH5Pay(String platformType,MemberInfo memberInfo,MemberPayType memberPayType,String payMoney,String orderNumOuter,String sceneInfo,String ip,String callbackUrl,MemberMerchantCode merchantCode,String userAgent,String routeCode,String aisleType,String payType) {
		JSONObject result = new JSONObject();
		try {
			String merCode = "";
			String memberCode = "";
			String payTypeStr = "";
			String eskPayType = "";
			if("3".equals(payType)){
				merCode = merchantCode.getQqMerchantCode();
				memberCode = memberInfo.getQqMemberCode();
				payTypeStr = PayTypeConstant.PAY_TYPE_QQ;
				eskPayType = "PAY_QWALLET_NATIVE";
			}
			
			MemberMerchantKeyExample memberMerchantKeyExample = new MemberMerchantKeyExample();
	        memberMerchantKeyExample.createCriteria().andRouteCodeEqualTo(RouteCodeConstant.TLH5_ROUTE_CODE).andMerchantCodeEqualTo(merCode).andDelFlagEqualTo("0");
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
			debitNote.setRouteId(routeCode);
			debitNote.setStatus("0");
			debitNote.setTxnMethod(PayTypeConstant.PAY_METHOD_H5);
			debitNote.setTxnType(payType);
			debitNote.setMemberCode(memberCode);
			debitNote.setMerchantCode(merCode);
			debitNote.setIp(ip);
			debitNote.setUserAgent(userAgent.length()>1000?userAgent.substring(0, 1000):userAgent);
			debitNote.setBrowser(DeviceRequestUtil.getBrowser(userAgent));
			debitNote.setDeviceInfo(DeviceRequestUtil.getDevice(userAgent));
			debitNote.setSettleType(memberInfo.getSettleType());
			if("0".equals(memberInfo.getSettleType())){
				debitNote.setTradeRate(memberPayType.getT0TradeRate());
			}else{
				debitNote.setTradeRate(memberPayType.getT1TradeRate());
			}
			
			String configName = "SINGLE_MEMBER_LIMIT_"+memberInfo.getId()+"_006_"+payTypeStr;
			JSONObject memResult = checkLimitMoney(configName, new BigDecimal(payMoney));
			if(null != memResult){
				debitNote.setStatus("9");
				debitNoteService.insertSelective(debitNote);
				return memResult;
			} 
			
			configName = "SINGLE_MIN_"+routeCode+"_006_"+payTypeStr;
			JSONObject checkResult = checkMinMoney(configName, new BigDecimal(payMoney));
			if(null != checkResult){
				debitNote.setStatus("5");
				debitNoteService.insertSelective(debitNote);
				return checkResult;
			}
			
			configName = "SINGLE_LIMIT_"+routeCode+"_006_"+payTypeStr;
			JSONObject limitResult = checkLimitMoney(configName, new BigDecimal(payMoney));
			if(null != limitResult){
				debitNote.setStatus("4");
				debitNoteService.insertSelective(debitNote);
				return limitResult;
			}
			
			JSONObject timeResult = commonUtilService.checkLimitIpFail(PayTypeConstant.PAY_METHOD_H5, payTypeStr, memberInfo.getId(), ip);
			if(null != timeResult){
				debitNote.setStatus("12");
				debitNoteService.insertSelective(debitNote);
				return timeResult;
			}
			
			JSONObject ipResult = checkLimitIp(PayTypeConstant.PAY_METHOD_H5, payTypeStr, memberInfo.getId(), routeCode, merCode, ip);
			if(null != ipResult){
				debitNote.setStatus("6");
				debitNoteService.insertSelective(debitNote);
				return ipResult;
			}
			
			JSONObject mResult = checkLimitMerchantMoney(routeCode,merCode);
			if(null != mResult){
				debitNote.setStatus("7");
				debitNoteService.insertSelective(debitNote);
				return mResult;
			}
			
			JSONObject tResult = checkLimitCounts(routeCode);
			if(null != tResult){
				debitNote.setStatus("8");
				debitNoteService.insertSelective(debitNote);
				return tResult;
			}
			
			debitNoteService.insertSelective(debitNote);
			
			PayResultNotice payResultNotice = new PayResultNotice();
			payResultNotice.setOrderCode(debitNote.getOrderCode());
			payResultNotice.setOrderNumOuter(orderNumOuter);
			payResultNotice.setPayMoney(debitNote.getMoney());
			payResultNotice.setMemberCode(memberInfo.getCode());
			payResultNotice.setPayType(payType);
			
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
			
			String serverUrl = TLConfig.msServerUrl;
			
			JSONObject reqData = new JSONObject();
			reqData.put("ACTION_NAME", eskPayType);
			JSONObject reqBody = new JSONObject();
			reqBody.put("COM_ID", merchantKey.getAppId());
			reqBody.put("USER_INFO_ID", merchantKey.getMerchantCode());
			reqBody.put("OUT_TRADE_NO", orderCode);
			reqBody.put("BODY", memberInfo.getName() + "收款");
			reqBody.put("AMOUNT", payMoney);
			reqBody.put("NONCE_STR", new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
			
			String srcStr = StringUtil.orderedKey(reqBody)+"&KEY="+merchantKey.getPrivateKey();
			//logger.info("Sign Before MD5: {}", srcStr);
			String sign = MD5Util.MD5Encode(srcStr).toUpperCase();
			reqBody.put("SIGN", sign);
			
			reqData.put("ACTION_INFO", reqBody.toString());
			
			logger.info("通联扫码请求数据[{}]", new Object[] { reqData.toString() });
			
			String respStr = HttpUtil.sendPostRequest(serverUrl, reqData.toString());
			
			logger.info("通联扫码返回报文[{}]", new Object[] { respStr });
			
			JSONObject jsonObject = JSONObject.fromObject(respStr);
			String result_message = "";
			String result_code = "";
			boolean flag = false;
			if("000000".equals(jsonObject.getString("ACTION_RETURN_CODE"))){
				JSONObject respJSONObject =  JSONObject.fromObject(jsonObject.get("ACTION_INFO"));
				
				String signed = respJSONObject.getString("SIGN");
				respJSONObject.remove("SIGN");
				srcStr = StringUtil.orderedKeyObj(respJSONObject)+"&KEY="+merchantKey.getPrivateKey();
				logger.info("Sign Before MD5: {}", srcStr);
				sign = MD5Util.MD5Encode(srcStr).toUpperCase();
				
				if(sign.equals(signed)){
					String resultCode = respJSONObject.getString("RESULT_CODE");
					if("0".equals(resultCode)){
						result.put("payUrl", respJSONObject.get("CODE_URL").toString());
						result.put("returnCode", "0000");
						result.put("returnMsg", "成功");
						flag = true;
					}else{
						if(respJSONObject.containsKey("ERR_MSG")){
							result_message = respJSONObject.getString("ERR_MSG");
						}
						if(respJSONObject.containsKey("ERR_CODE")){
							result_code = respJSONObject.getString("ERR_CODE");
						}
					}
				}else{
					result_message = "出参签名验证失败";
				}
			}else{
				result_message = jsonObject.getString("MESSAGE");
				result_code = jsonObject.getString("ACTION_RETURN_CODE");
			}
			
			if (!flag) {
				result.put("returnCode", "0009");
				result.put("returnMsg", result_message);
				
				DebitNoteExample debitNoteExample = new DebitNoteExample();
				debitNoteExample.createCriteria().andOrderCodeEqualTo(orderCode);
				List<DebitNote> debitNotes = debitNoteService.selectByExample(debitNoteExample);
				if (debitNotes != null && debitNotes.size() > 0) {
					DebitNote debitNote_1 = debitNotes.get(0);
					debitNote_1.setStatus("2");
					debitNote_1.setUpdateDate(new Date());
					if(!"".equals(result_message)){
						debitNote_1.setRespMsg(result_message.length()>250?result_message.substring(0, 250):result_message);
					}
					debitNoteService.updateByPrimaryKey(debitNote_1);
				}
			}
			try{
		        DebitNoteIp debitNoteIp = new DebitNoteIp();
		        debitNoteIp.setMemberId(memberInfo.getId());
		        debitNoteIp.setMerchantCode(merCode);
		        debitNoteIp.setOrderCode(orderCode);
		        debitNoteIp.setRouteId(routeCode);
		        debitNoteIp.setTxnMethod(PayTypeConstant.PAY_METHOD_H5);
		        debitNoteIp.setTxnType(payType);
		        debitNoteIp.setIp(ip);
		        debitNoteIp.setCreateDate(debitNote.getCreateDate());
				debitNoteIpService.insertSelective(debitNoteIp);
	        }catch(Exception ex){
	        	logger.error(ex.getMessage());
	        }
			
        } catch (Exception e) {
			logger.error(e.getMessage());
			result.put("returnCode", "0096");
			result.put("returnMsg", e.getMessage());
			return result;
		}
		return result;
	}
	
	//多得宝h5支付
	public JSONObject tlWdH5Pay(String platformType,MemberInfo memberInfo,MemberPayType memberPayType,String payMoney,String orderNumOuter,String sceneInfo,String ip,String callbackUrl,MemberMerchantCode merchantCode,String userAgent,String routeCode,String aisleType,String payType) {
		JSONObject result = new JSONObject();
		try {
			String merCode = "";
			String memberCode = "";
			String payTypeStr = "";
			String eskPayType = "";
			if("3".equals(payType)){
				merCode = merchantCode.getQqMerchantCode();
				memberCode = memberInfo.getQqMemberCode();
				payTypeStr = PayTypeConstant.PAY_TYPE_QQ;
				eskPayType = "0001";
			}else if("2".equals(payType)){
				merCode = merchantCode.getZfbMerchantCode();
				memberCode = memberInfo.getZfbMemberCode();
				payTypeStr = PayTypeConstant.PAY_TYPE_ZFB;
				eskPayType = "0003";
			}
			
			MemberMerchantKeyExample memberMerchantKeyExample = new MemberMerchantKeyExample();
	        memberMerchantKeyExample.createCriteria().andRouteCodeEqualTo(routeCode).andMerchantCodeEqualTo(merCode).andDelFlagEqualTo("0");
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
			debitNote.setRouteId(routeCode);
			debitNote.setStatus("0");
			debitNote.setTxnMethod(PayTypeConstant.PAY_METHOD_H5);
			debitNote.setTxnType(payType);
			debitNote.setMemberCode(memberCode);
			debitNote.setMerchantCode(merCode);
			debitNote.setIp(ip);
			debitNote.setUserAgent(userAgent.length()>1000?userAgent.substring(0, 1000):userAgent);
			debitNote.setBrowser(DeviceRequestUtil.getBrowser(userAgent));
			debitNote.setDeviceInfo(DeviceRequestUtil.getDevice(userAgent));
			debitNote.setSettleType(memberInfo.getSettleType());
			if("0".equals(memberInfo.getSettleType())){
				debitNote.setTradeRate(memberPayType.getT0TradeRate());
			}else{
				debitNote.setTradeRate(memberPayType.getT1TradeRate());
			}
			
			String configName = "SINGLE_MEMBER_LIMIT_"+memberInfo.getId()+"_006_"+payTypeStr;
			JSONObject memResult = checkLimitMoney(configName, new BigDecimal(payMoney));
			if(null != memResult){
				debitNote.setStatus("9");
				debitNoteService.insertSelective(debitNote);
				return memResult;
			} 
			
			configName = "SINGLE_MIN_"+routeCode+"_006_"+payTypeStr;
			JSONObject checkResult = checkMinMoney(configName, new BigDecimal(payMoney));
			if(null != checkResult){
				debitNote.setStatus("5");
				debitNoteService.insertSelective(debitNote);
				return checkResult;
			}
			
			configName = "SINGLE_LIMIT_"+routeCode+"_006_"+payTypeStr;
			JSONObject limitResult = checkLimitMoney(configName, new BigDecimal(payMoney));
			if(null != limitResult){
				debitNote.setStatus("4");
				debitNoteService.insertSelective(debitNote);
				return limitResult;
			}
			
			JSONObject timeResult = commonUtilService.checkLimitIpFail(PayTypeConstant.PAY_METHOD_H5, payTypeStr, memberInfo.getId(), ip);
			if(null != timeResult){
				debitNote.setStatus("12");
				debitNoteService.insertSelective(debitNote);
				return timeResult;
			}
			
			JSONObject ipResult = checkLimitIp(PayTypeConstant.PAY_METHOD_H5, payTypeStr, memberInfo.getId(), routeCode, merCode, ip);
			if(null != ipResult){
				debitNote.setStatus("6");
				debitNoteService.insertSelective(debitNote);
				return ipResult;
			}
			
			JSONObject mResult = checkLimitMerchantMoney(routeCode,merCode);
			if(null != mResult){
				debitNote.setStatus("7");
				debitNoteService.insertSelective(debitNote);
				return mResult;
			}
			
			JSONObject mResult1 = commonUtilService.checkLimitMerchantMoney(routeCode,merCode,payTypeStr);
			if(null != mResult1){
				debitNote.setStatus("7");
				debitNoteService.insertSelective(debitNote);
				return mResult1;
			}
			
			JSONObject tResult = checkLimitCounts(routeCode);
			if(null != tResult){
				debitNote.setStatus("8");
				debitNoteService.insertSelective(debitNote);
				return tResult;
			}
			
			debitNoteService.insertSelective(debitNote);
			
			PayResultNotice payResultNotice = new PayResultNotice();
			payResultNotice.setOrderCode(debitNote.getOrderCode());
			payResultNotice.setOrderNumOuter(orderNumOuter);
			payResultNotice.setPayMoney(debitNote.getMoney());
			payResultNotice.setMemberCode(memberInfo.getCode());
			payResultNotice.setPayType(payType);
			
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
			
			
			
			String callBack = SysConfig.serverUrl + "/cashierDesk/tlWdPayNotify";
			// 调用支付通道
			String orderDesc = memberInfo.getName() + "收款";
			String serverUrl = TLConfig.msServerUrlNew;
			
			JSONObject reqData = new JSONObject();
			reqData.put("versionId", "001");
			reqData.put("businessType", "1100");
			reqData.put("transChanlName", eskPayType);
			reqData.put("merId", merchantKey.getMerchantCode());
			reqData.put("orderId", orderCode);
			reqData.put("transDate", new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
			reqData.put("transAmount", payMoney);
			reqData.put("backNotifyUrl", callBack);
			reqData.put("orderDesc", URLEncoder.encode(orderDesc,"GBK"));
			
			String srcStr = StringUtil.orderedKey(reqData)+"&key="+merchantKey.getPrivateKey();
			logger.info("Sign Before MD5: {}", srcStr);
			String sign = MD5Util.MD5Encode(srcStr).toUpperCase();
			
			reqData.put("signType", "MD5");
			reqData.put("signData", sign);
			
			logger.info("新通联扫码请求数据[{}]", new Object[] { reqData.toString() });
			HttpService hs = new HttpService();
			String respStr = hs.POSTReturnString(serverUrl, reqData, "GBK");
			logger.info("新通联扫码返回报文[{}]", new Object[] { respStr });
			
			JSONObject jsonObject = JSONObject.fromObject(respStr);
			String result_message = "";
			String result_code = jsonObject.getString("status");
			boolean flag = false;
			if("00".equals(result_code)){
				String signed = jsonObject.getString("signData");
				jsonObject.remove("signData");
				srcStr = StringUtil.orderedKeyObj(jsonObject)+"&key="+merchantKey.getPrivateKey();
				logger.info("Sign Before MD5: {}", srcStr);
				sign = MD5Util.MD5Encode(srcStr).toUpperCase();
				if(sign.equals(signed)){
					String refCode = jsonObject.getString("refCode");
					if("01".equals(refCode)){
						result.put("payUrl", jsonObject.get("codeUrl").toString());
						result.put("returnCode", "0000");
						result.put("returnMsg", "成功");
						flag = true;
					}else{
						result_message = URLDecoder.decode(jsonObject.getString("refMsg"), "GBK");
					}
				}else{
					result_message = "出参签名验证失败";
				}
			}else{
				result_message = URLDecoder.decode(jsonObject.getString("refMsg"), "GBK");
			}
			
			if(!flag){
				result.put("returnCode", "0009");
				result.put("returnMsg", result_message);
				DebitNoteExample debitNoteExample = new DebitNoteExample();
				debitNoteExample.createCriteria().andOrderCodeEqualTo(orderCode);
				List<DebitNote> debitNotes = debitNoteService.selectByExample(debitNoteExample);
				if (debitNotes != null && debitNotes.size() > 0) {
					DebitNote debitNote_1 = debitNotes.get(0);
					debitNote_1.setStatus("2");
					debitNote_1.setUpdateDate(new Date());
					if(!"".equals(result_message)){
						debitNote_1.setRespMsg(result_message.length()>250?result_message.substring(0, 250):result_message);
					}
					debitNoteService.updateByPrimaryKey(debitNote_1);
				}
			}
			try{
		        DebitNoteIp debitNoteIp = new DebitNoteIp();
		        debitNoteIp.setMemberId(memberInfo.getId());
		        debitNoteIp.setMerchantCode(merCode);
		        debitNoteIp.setOrderCode(orderCode);
		        debitNoteIp.setRouteId(routeCode);
		        debitNoteIp.setTxnMethod(PayTypeConstant.PAY_METHOD_H5);
		        debitNoteIp.setTxnType(payType);
		        debitNoteIp.setIp(ip);
		        debitNoteIp.setCreateDate(debitNote.getCreateDate());
				debitNoteIpService.insertSelective(debitNoteIp);
	        }catch(Exception ex){
	        	logger.error(ex.getMessage());
	        }
			
        } catch (Exception e) {
			logger.error(e.getMessage());
			result.put("returnCode", "0096");
			result.put("returnMsg", e.getMessage());
			return result;
		}
		return result;
	}
	
	
	
	public JSONObject sdH5Pay(String platformType,MemberInfo memberInfo,MemberPayType memberPayType,String payMoney,String orderNumOuter,String sceneInfo,String ip,String callbackUrl,MemberMerchantCode merchantCode,String userAgent,String routeCode,String aisleType,String payType) {
		JSONObject result = new JSONObject();
		try {
			String merCode = "";
			String memberCode = "";
			String payTypeStr = "";
			String eskPayType = "";
			if("1".equals(payType)){
				merCode = merchantCode.getWxMerchantCode();
				memberCode = memberInfo.getWxMemberCode();
				payTypeStr = PayTypeConstant.PAY_TYPE_WX;
			}
			
			MemberMerchantKeyExample memberMerchantKeyExample = new MemberMerchantKeyExample();
	        memberMerchantKeyExample.createCriteria().andRouteCodeEqualTo(RouteCodeConstant.SD_ROUTE_CODE).andMerchantCodeEqualTo(merCode).andDelFlagEqualTo("0");
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
			debitNote.setRouteId(routeCode);
			debitNote.setStatus("0");
			debitNote.setTxnMethod(PayTypeConstant.PAY_METHOD_H5);
			debitNote.setTxnType(payType);
			debitNote.setMemberCode(memberCode);
			debitNote.setMerchantCode(merCode);
			debitNote.setIp(ip);
			debitNote.setUserAgent(userAgent.length()>1000?userAgent.substring(0, 1000):userAgent);
			debitNote.setBrowser(DeviceRequestUtil.getBrowser(userAgent));
			debitNote.setDeviceInfo(DeviceRequestUtil.getDevice(userAgent));
			debitNote.setSettleType(memberInfo.getSettleType());
			if("0".equals(memberInfo.getSettleType())){
				debitNote.setTradeRate(memberPayType.getT0TradeRate());
			}else{
				debitNote.setTradeRate(memberPayType.getT1TradeRate());
			}
			
			String configName = "SINGLE_MEMBER_LIMIT_"+memberInfo.getId()+"_006_"+payTypeStr;
			JSONObject memResult = checkLimitMoney(configName, new BigDecimal(payMoney));
			if(null != memResult){
				debitNote.setStatus("9");
				debitNoteService.insertSelective(debitNote);
				return memResult;
			} 
			
			configName = "SINGLE_MIN_"+routeCode+"_006_"+payTypeStr;
			JSONObject checkResult = checkMinMoney(configName, new BigDecimal(payMoney));
			if(null != checkResult){
				debitNote.setStatus("5");
				debitNoteService.insertSelective(debitNote);
				return checkResult;
			}
			
			configName = "SINGLE_LIMIT_"+routeCode+"_006_"+payTypeStr;
			JSONObject limitResult = checkLimitMoney(configName, new BigDecimal(payMoney));
			if(null != limitResult){
				debitNote.setStatus("4");
				debitNoteService.insertSelective(debitNote);
				return limitResult;
			}
			
			JSONObject timeResult = commonUtilService.checkLimitIpFail(PayTypeConstant.PAY_METHOD_H5, payTypeStr, memberInfo.getId(), ip);
			if(null != timeResult){
				debitNote.setStatus("12");
				debitNoteService.insertSelective(debitNote);
				return timeResult;
			}
			
			JSONObject ipResult = checkLimitIp(PayTypeConstant.PAY_METHOD_H5, payTypeStr, memberInfo.getId(), routeCode, merCode, ip);
			if(null != ipResult){
				debitNote.setStatus("6");
				debitNoteService.insertSelective(debitNote);
				return ipResult;
			}
			
			JSONObject mResult = checkLimitMerchantMoney(routeCode,merCode);
			if(null != mResult){
				debitNote.setStatus("7");
				debitNoteService.insertSelective(debitNote);
				return mResult;
			}
			
			JSONObject tResult = checkLimitCounts(routeCode);
			if(null != tResult){
				debitNote.setStatus("8");
				debitNoteService.insertSelective(debitNote);
				return tResult;
			}
			
			debitNoteService.insertSelective(debitNote);
			
			PayResultNotice payResultNotice = new PayResultNotice();
			payResultNotice.setOrderCode(debitNote.getOrderCode());
			payResultNotice.setOrderNumOuter(orderNumOuter);
			payResultNotice.setPayMoney(debitNote.getMoney());
			payResultNotice.setMemberCode(memberInfo.getCode());
			payResultNotice.setPayType(payType);
			
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
			
			String serverUrl = SDConfig.msServerUrl;
			
			
			String callBack = SysConfig.serverUrl + "/debitNote/sdPayNotify";
			String frontUrl = SysConfig.frontUrl + "/debitNote/sdResult";
			String charset = "utf-8";
			
			CertUtil.init("classpath:"+EnvironmentUtil.propertyPath + "sdkey/" + merchantKey.getPublicKey(), 
					"classpath:"+EnvironmentUtil.propertyPath + "sdkey/" + merchantKey.getPrivateKey(), merchantKey.getPrivateKeyPassword());
			
			SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
			
			SandpayRequestHead head = new SandpayRequestHead();
			SandOrderPayRequestBody body = new SandOrderPayRequestBody();
			SandOrderPayRequest req = new SandOrderPayRequest();
			
			req.setHead(head);
			req.setBody(body);
			
			PacketTool.setDefaultRequestHead(head, "sandpay.trade.pay", "00000025", merchantCode.getWxMerchantCode());
			
			body.setOrderCode(orderCode);
			body.setTotalAmount("000000000101");
			body.setSubject( memberInfo.getName() + " 收款");
			body.setBody( memberInfo.getName() + " 收款");
			body.setPayMode("sand_wxh5");
			JSONObject payExtra = new JSONObject();
			payExtra.put("ip", ip);
			payExtra.put("sceneInfo", sceneInfo);
			body.setPayExtra(payExtra.toString());
			body.setClientIp(ip);
			body.setNotifyUrl(callBack);
			body.setFrontUrl(frontUrl);
			if("0".equals(memberInfo.getSettleType())){
				body.setClearCycle("2");
			}else{
				body.setClearCycle("0");
			}
			
			SandOrderPayResponse res =  SandpayClient.execute(req, serverUrl);
			
			SandpayResponseHead respHead = res.getHead();
			
			logger.info(respHead.getRespCode()+"====="+respHead.getRespMsg());
			
			
			
			
			
			
			
			try{
		        DebitNoteIp debitNoteIp = new DebitNoteIp();
		        debitNoteIp.setMemberId(memberInfo.getId());
		        debitNoteIp.setMerchantCode(merCode);
		        debitNoteIp.setOrderCode(orderCode);
		        debitNoteIp.setRouteId(routeCode);
		        debitNoteIp.setTxnMethod(PayTypeConstant.PAY_METHOD_H5);
		        debitNoteIp.setTxnType(payType);
		        debitNoteIp.setIp(ip);
		        debitNoteIp.setCreateDate(debitNote.getCreateDate());
				debitNoteIpService.insertSelective(debitNoteIp);
	        }catch(Exception ex){
	        	logger.error(ex.getMessage());
	        }
			
        } catch (Exception e) {
			logger.error(e.getMessage());
			result.put("returnCode", "0096");
			result.put("returnMsg", e.getMessage());
			return result;
		}
		return result;
	}
}
