package com.epay.scanposp.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.epay.scanposp.common.constant.CJConfig;
import com.epay.scanposp.common.constant.ESKConfig;
import com.epay.scanposp.common.constant.HXConfig;
import com.epay.scanposp.common.constant.MLConfig;
import com.epay.scanposp.common.constant.SysConfig;
import com.epay.scanposp.common.constant.TLKJConfig;
import com.epay.scanposp.common.constant.WWConfig;
import com.epay.scanposp.common.constant.YSConfig;
import com.epay.scanposp.common.constant.YZFConfig;
import com.epay.scanposp.common.excep.ArgException;
import com.epay.scanposp.common.utils.CommonUtil;
import com.epay.scanposp.common.utils.DateUtil;
import com.epay.scanposp.common.utils.HttpUtil;
import com.epay.scanposp.common.utils.StringUtil;
import com.epay.scanposp.common.utils.ValidateUtil;
import com.epay.scanposp.common.utils.cj.entity.ConsumeRequestEntity;
import com.epay.scanposp.common.utils.cj.entity.ConsumeResponseEntity;
import com.epay.scanposp.common.utils.cj.entity.MessAgeResponseEntity;
import com.epay.scanposp.common.utils.cj.entity.MessageRequestEntity;
import com.epay.scanposp.common.utils.cj.util.Bean2QueryStrUtil;
import com.epay.scanposp.common.utils.cj.util.BeanToMapUtil;
import com.epay.scanposp.common.utils.cj.util.HttpURLConection;
import com.epay.scanposp.common.utils.cj.util.SignatureUtil;
import com.epay.scanposp.common.utils.constant.PayTypeConstant;
import com.epay.scanposp.common.utils.constant.RouteCodeConstant;
import com.epay.scanposp.common.utils.epaySecurityUtil.EpaySignUtil;
import com.epay.scanposp.common.utils.esk.Key;
import com.epay.scanposp.common.utils.ms.CryptoUtil;
import com.epay.scanposp.common.utils.ms.HttpClient4Util;
import com.epay.scanposp.common.utils.ms.MSCommonUtil;
import com.epay.scanposp.common.utils.tlkj.AesTool;
import com.epay.scanposp.common.utils.tlkj.MD5Util;
import com.epay.scanposp.common.utils.tlkj.RandomTools;
import com.epay.scanposp.common.utils.ys.HttpUtils;
import com.epay.scanposp.common.utils.ys.SwpHashUtil;
import com.epay.scanposp.common.utils.yzf.AESTool;
import com.epay.scanposp.common.utils.yzf.Base64Utils;
import com.epay.scanposp.common.utils.yzf.EncryptUtil;
import com.epay.scanposp.common.utils.yzf.RSATool;
import com.epay.scanposp.entity.Account;
import com.epay.scanposp.entity.AccountExample;
import com.epay.scanposp.entity.Bank;
import com.epay.scanposp.entity.BankExample;
import com.epay.scanposp.entity.BankRoute;
import com.epay.scanposp.entity.BankRouteExample;
import com.epay.scanposp.entity.DebitNote;
import com.epay.scanposp.entity.DebitNoteExample;
import com.epay.scanposp.entity.EpayCode;
import com.epay.scanposp.entity.EpayCodeExample;
import com.epay.scanposp.entity.MemberBindAccDtl;
import com.epay.scanposp.entity.MemberBindAccDtlExample;
import com.epay.scanposp.entity.MemberInfo;
import com.epay.scanposp.entity.MemberInfoExample;
import com.epay.scanposp.entity.MemberMerchantCode;
import com.epay.scanposp.entity.MemberMerchantCodeExample;
import com.epay.scanposp.entity.MemberMerchantKey;
import com.epay.scanposp.entity.MemberMerchantKeyExample;
import com.epay.scanposp.entity.MemberPayType;
import com.epay.scanposp.entity.MemberPayTypeExample;
import com.epay.scanposp.entity.MsResultNotice;
import com.epay.scanposp.entity.MsResultNoticeExample;
import com.epay.scanposp.entity.PayResultNotice;
import com.epay.scanposp.entity.PayResultNoticeExample;
import com.epay.scanposp.entity.QuickPaySms;
import com.epay.scanposp.entity.QuickPaySmsExample;
import com.epay.scanposp.entity.SysCommonConfig;
import com.epay.scanposp.entity.SysCommonConfigExample;
import com.epay.scanposp.entity.SysOffice;
import com.epay.scanposp.entity.SysOfficeExample;
import com.epay.scanposp.entity.TradeDetail;
import com.epay.scanposp.entity.TradeDetailDaily;
import com.epay.scanposp.entity.TradeDetailExample;
import com.epay.scanposp.service.AccountService;
import com.epay.scanposp.service.BankRouteService;
import com.epay.scanposp.service.BankService;
import com.epay.scanposp.service.CommonService;
import com.epay.scanposp.service.CommonUtilService;
import com.epay.scanposp.service.DebitNoteIpService;
import com.epay.scanposp.service.DebitNoteService;
import com.epay.scanposp.service.EpayCodeService;
import com.epay.scanposp.service.MemberBankService;
import com.epay.scanposp.service.MemberBindAccDtlService;
import com.epay.scanposp.service.MemberInfoService;
import com.epay.scanposp.service.MemberMerchantCodeService;
import com.epay.scanposp.service.MemberMerchantKeyService;
import com.epay.scanposp.service.MemberPayTypeService;
import com.epay.scanposp.service.MsResultNoticeService;
import com.epay.scanposp.service.PayResultNoticeService;
import com.epay.scanposp.service.PayResultNotifyService;
import com.epay.scanposp.service.PayTypeService;
import com.epay.scanposp.service.QuickPaySmsService;
import com.epay.scanposp.service.RoutewayDrawService;
import com.epay.scanposp.service.SysCommonConfigService;
import com.epay.scanposp.service.SysOfficeExtendService;
import com.epay.scanposp.service.SysOfficeService;
import com.epay.scanposp.service.TradeDetailDailyService;
import com.epay.scanposp.service.TradeDetailService;
import com.google.gson.Gson;
@Controller
public class QuickPayController {
	
	private static Logger logger = LoggerFactory.getLogger(QuickPayController.class);
	
	@Resource
	private SysOfficeExtendService sysOfficeExtendService;
	
	@Resource
	private MemberInfoService memberInfoService;
	
	@Resource
	private AccountService accountService;

	@Resource
	private DebitNoteService debitNoteService;
	
	@Resource
	private DebitNoteIpService debitNoteIpService;
	
	@Resource
	private TradeDetailDailyService tradeDetailDailyService;
	
	@Resource
	private EpayCodeService epayCodeService;
	
	@Autowired
	private PayTypeService payTypeService;
	
	@Autowired
	private MemberMerchantCodeService memberMerchantCodeService;
	
	@Resource
	private SysOfficeService sysOfficeService;
	
	@Resource
	private PayResultNoticeService payResultNoticeService;
	
	@Resource
	private MsResultNoticeService msResultNoticeService;
	
	@Autowired
	private MemberMerchantKeyService memberMerchantKeyService;
	
	@Autowired
	private MemberBankService memberBankService;
	
	@Autowired
	private RoutewayDrawService routewayDrawService;
	
	@Autowired
	private CommonService commonService;
	
	@Resource
	private PayResultNotifyService payResultNotifyService;
	
	@Autowired
	private SysCommonConfigService sysCommonConfigService;
	
	@Autowired
	private CommonUtilService commonUtilService;
	
	@Autowired
	private QuickPaySmsService quickPaySmsService;
	
	@Resource
	private TradeDetailService tradeDetailService;

	@Resource
	private MemberBindAccDtlService memberBindAccDtlService;
	
	@Autowired
	private BankRouteService bankRouteService;
	
	@Autowired
	private BankService bankService;
	
	@Autowired
	private MemberPayTypeService memberPayTypeService;
	
	
	/**
	 * 快捷支付短信发送接口
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/quickPay/sendSms")
	public JSONObject sendSms(HttpServletRequest request,HttpServletResponse response){
		String accountName = request.getParameter("accountName");
		String accountType = request.getParameter("accountType");
		String bankAccount = request.getParameter("bankAccount");
		String bankCvv = request.getParameter("bankCvv");
		String bankYxq = request.getParameter("bankYxq");
		String callbackUrl = request.getParameter("callbackUrl");
		//String certNo = request.getParameter("certNo");
		String goodsName = request.getParameter("goodsName");
		String memberCode = request.getParameter("memberCode");
		String orderNum = request.getParameter("orderNum");
		String payMoney = request.getParameter("payMoney");
		String tel = request.getParameter("tel");
		String signStr = request.getParameter("signStr");
		Map<String,String> obj = new HashMap<String, String>();
		obj.put("accountName", accountName);
		obj.put("accountType", accountType);
		obj.put("bankAccount", bankAccount);
		obj.put("bankCvv", bankCvv);
		obj.put("bankYxq", bankYxq);
		obj.put("callbackUrl", callbackUrl);
		obj.put("goodsName", goodsName);
		obj.put("memberCode", memberCode);
		obj.put("orderNum", orderNum);
		obj.put("payMoney", payMoney);
		obj.put("tel", tel);
		obj.put("signStr", signStr);
		logger.info("快捷支付短信发送接口接收参数[{}]",JSONObject.fromObject(obj).toString() );
		
		StringBuilder srcStr = new StringBuilder();
		JSONObject result = new JSONObject();
		
		if(accountName == null || "".equals(accountName)){
			result.put("returnCode", "0007");
			result.put("returnMsg", "账户名称缺失");
			return CommonUtil.signReturn(result);
		}
		srcStr.append("accountName="+accountName);
		
		if(accountType == null || "".equals(accountType)){
			result.put("returnCode", "0007");
			result.put("returnMsg", "账户类型缺失");
			return CommonUtil.signReturn(result);
		}
		if(!"1".equals(accountType)&&!"2".equals(accountType)){
			result.put("returnCode", "0007");
			result.put("returnMsg", "账户类型不正确");
			return CommonUtil.signReturn(result);
		}
		
		srcStr.append("&accountType="+accountType);
		
		if(bankAccount == null || "".equals(bankAccount)){
			result.put("returnCode", "0007");
			result.put("returnMsg", "银行卡号缺失");
			return CommonUtil.signReturn(result);
		}
		srcStr.append("&bankAccount="+bankAccount);
		
		if("2".equals(accountType)){//信用卡
			if(bankCvv == null || "".equals(bankCvv)){
				result.put("returnCode", "0007");
				result.put("returnMsg", "账户类型为信用卡，卡末三位缺失");
				return CommonUtil.signReturn(result);
			}
			if(bankYxq == null || "".equals(bankYxq)){
				result.put("returnCode", "0007");
				result.put("returnMsg", "账户类型为信用卡，卡有效期缺失");
				return CommonUtil.signReturn(result);
			}
		}
		
		if(bankCvv != null && !"".equals(bankCvv)){
			srcStr.append("&bankCvv="+bankCvv);
		}
		
		if(bankYxq != null && !"".equals(bankYxq)){
			srcStr.append("&bankYxq="+bankYxq);
		}
		
		if(callbackUrl == null || "".equals(callbackUrl)){
			result.put("returnCode", "0007");
			result.put("returnMsg", "支付结果通知回调地址缺失");
			return result;
		}
		srcStr.append("&callbackUrl="+callbackUrl);
		
		if(goodsName != null && !"".equals(goodsName)){
			srcStr.append("&goodsName="+goodsName);
		}
		
		if(memberCode == null || "".equals(memberCode)){
			result.put("returnCode", "0007");
			result.put("returnMsg", "商户号缺失");
			return CommonUtil.signReturn(result);
		}
		srcStr.append("&memberCode="+memberCode);
		
		if(orderNum == null || "".equals(orderNum)){
			result.put("returnCode", "0007");
			result.put("returnMsg", "商户订单号缺失");
			return CommonUtil.signReturn(result);
		}
		if(orderNum.length() > 32){
			result.put("returnCode", "0007");
			result.put("returnMsg", "订单号长度不能超过32位");
			return CommonUtil.signReturn(result);
		}
		srcStr.append("&orderNum="+orderNum);
		
		if(null == payMoney || !ValidateUtil.isDoubleT(payMoney) || Double.parseDouble(payMoney)<=0){
			result.put("returnCode", "0007");
			result.put("returnMsg", "支付金额输入不正确");
			return CommonUtil.signReturn(result);
		}
		srcStr.append("&payMoney="+payMoney);
		
		if(tel == null || "".equals(tel)){
			result.put("returnCode", "0007");
			result.put("returnMsg", "手机号缺失");
			return CommonUtil.signReturn(result);
		}
		srcStr.append("&tel="+tel);
		
		if(signStr == null || "".equals(signStr)){ 
			result.put("returnCode", "0007");
			result.put("returnMsg", "缺少签名信息");
			return CommonUtil.signReturn(result);
		}
		result = validMemberInfoForQuickSms(memberCode, orderNum, payMoney,accountType, accountName,bankAccount,bankCvv,bankYxq, tel,goodsName,callbackUrl,  srcStr.toString(), signStr);
		
		return CommonUtil.signReturn(result);
	}
	
	
	public JSONObject validMemberInfoForQuickSms(String memberCode,String orderNum,String payMoney,String accountType,String accountName,String bankAccount,String bankCvv,String bankYxq,String tel,String goodsName,String callbackUrl,String signOrginalStr,String signedStr){
		JSONObject result = new JSONObject();
		try{
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
			
			Map<String,String> rtMap  = commonUtilService.getRouteCodeAndAisleType(memberInfo.getId(),PayTypeConstant.PAY_METHOD_YL,PayTypeConstant.PAY_TYPE_KJ);
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
				result.put("returnMsg", "对不起，该商户未开通快捷支付的权限");
				return result;
			}
			MemberMerchantCode merchantCode = merchantCodes.get(0);
			if (StringUtils.isBlank(merchantCode.getKjMerchantCode())) {
				result.put("returnCode", "0008");
				result.put("returnMsg", "对不起，该商户未开通快捷支付的权限");
				return result;
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
			
			System.out.println(signOrginalStr);
			if(!EpaySignUtil.checksign(sysOffice.getPublicKeyRsa(), signOrginalStr, signedStr)){//by linxf 测试屏蔽
				result.put("returnCode", "0004");
				result.put("returnMsg", "签名校验错误，请检查签名参数是否正确");
				return result;
			}
		
			
			if(RouteCodeConstant.CJ_ROUTE_CODE.equals(routeCode)){
				memberInfo.setSettleType("1");
				result = cjQuickPaySms("3",memberInfo,merchantCode,payMoney,orderNum,accountType,accountName,bankAccount,bankCvv,bankYxq,tel,callbackUrl,goodsName,routeCode,aisleType);
			}
		}catch(Exception e){
			logger.info(e.getMessage());
			result.put("returnCode", "0096");
			result.put("returnMsg", e.getMessage());
		}
		return result;
	}
	
	/**
	 * 畅捷快捷支付短信发送
	 * @param platformType
	 * @param memberInfo
	 * @param merchantCode
	 * @param payMoney
	 * @param orderNumOuter
	 * @param accountName
	 * @param bankAccount
	 * @param bankCvv
	 * @param bankYxq
	 * @param tel
	 * @param callbackUrl
	 * @param goodsName
	 * @param routeCode
	 * @param aisleType
	 * @return
	 */
	public JSONObject cjQuickPaySms(String platformType,MemberInfo memberInfo,MemberMerchantCode merchantCode,String payMoney,String orderNumOuter,String accountType,String accountName,String bankAccount,String bankCvv,String bankYxq,String tel,String callbackUrl,String goodsName,String routeCode,String aisleType) {
		JSONObject result = new JSONObject();
		try {
			String payType = "9";
			String payTypeStr = PayTypeConstant.PAY_TYPE_KJ;
			String payMethod = PayTypeConstant.PAY_METHOD_YL;
			String merCode = merchantCode.getKjMerchantCode();
			BigDecimal tradeRate = null;
			if("0".equals(memberInfo.getSettleType())){
				tradeRate = merchantCode.getKjT0TradeRate();
			}else{
				tradeRate = merchantCode.getKjT1TradeRate();
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
			
			QuickPaySms quickPaySms = new QuickPaySms();
			quickPaySms.setCreateDate(new Date());
			quickPaySms.setMemberId(memberInfo.getId());
			quickPaySms.setMoney(new BigDecimal(payMoney));
			quickPaySms.setOrderCode(orderCode);
			quickPaySms.setOrderNumOuter(orderNumOuter);
			quickPaySms.setRouteId(routeCode);
			quickPaySms.setStatus("0");
			quickPaySms.setTxnMethod(payMethod);
			quickPaySms.setTxnType(payType);
			quickPaySms.setMerchantCode(merCode);
			quickPaySms.setMemberCode(memberInfo.getWxMemberCode());
			quickPaySms.setSettleType(memberInfo.getSettleType());
			quickPaySms.setTradeRate(tradeRate);
			
			quickPaySms.setAccountType(accountType);
			quickPaySms.setBankAccount(bankAccount);
			quickPaySms.setAccountName(accountName);
			quickPaySms.setTel(tel);
			quickPaySms.setReturnUrl(callbackUrl);
			
			String configName = "SINGLE_MEMBER_LIMIT_"+memberInfo.getId()+"_"+payMethod+"_"+payTypeStr;
			JSONObject memResult = commonUtilService.checkLimitMoney(configName, new BigDecimal(payMoney));
			if(null != memResult){
				quickPaySms.setStatus("9");
				quickPaySmsService.insertSelective(quickPaySms);
				return memResult;
			} 
			
			configName = "SINGLE_MIN_"+routeCode+"_"+payMethod+"_"+payTypeStr;
			JSONObject checkResult = commonUtilService.checkMinMoney(configName, new BigDecimal(payMoney));
			if(null != checkResult){
				quickPaySms.setStatus("5");
				quickPaySmsService.insertSelective(quickPaySms);
				return checkResult;
			}
			
			configName = "SINGLE_LIMIT_"+routeCode+"_"+payMethod+"_"+payTypeStr;
			JSONObject limitResult = commonUtilService.checkLimitMoney(configName, new BigDecimal(payMoney));
			if(null != limitResult){
				quickPaySms.setStatus("4");
				quickPaySmsService.insertSelective(quickPaySms);
				return limitResult;
			}
			
			configName = "PLAT_TRADE_RATE_"+routeCode;
			String platTradeRate = "";
			SysCommonConfigExample sysCommonConfigExample = new SysCommonConfigExample();
			sysCommonConfigExample.or().andNameEqualTo(configName).andDelFlagEqualTo("0");
			List<SysCommonConfig> sysCommonConfig = sysCommonConfigService.selectByExample(sysCommonConfigExample);
			if (sysCommonConfig != null && sysCommonConfig.size() > 0) {
				platTradeRate = sysCommonConfig.get(0).getValue();
			}
			if("".equals(platTradeRate)){
	            result.put("returnCode", "0008");
				result.put("returnMsg", "平台交易费率未配置");
				return result;
	        }
			
			configName = "MIN_PLAT_TRADE_FEE_"+routeCode;
			String minTradeFee = "";
			sysCommonConfigExample = new SysCommonConfigExample();
			sysCommonConfigExample.or().andNameEqualTo(configName).andDelFlagEqualTo("0");
			sysCommonConfig = sysCommonConfigService.selectByExample(sysCommonConfigExample);
			if (sysCommonConfig != null && sysCommonConfig.size() > 0) {
				minTradeFee = sysCommonConfig.get(0).getValue();
			}
			if("".equals(minTradeFee)){
	            result.put("returnCode", "0008");
				result.put("returnMsg", "平台最低手续费未配置");
				return result;
	        }
			
			if(new BigDecimal(minTradeFee).compareTo(new BigDecimal(payMoney).multiply(tradeRate))>0){
				quickPaySms.setStatus("5");
				quickPaySmsService.insertSelective(quickPaySms);
				result.put("returnCode", "4004");
				result.put("returnMsg", "小于平台最低交易金额");
				return result;
			}
			
			quickPaySmsService.insertSelective(quickPaySms);
			
			String callBack = SysConfig.serverUrl + "/quickPay/cjPayNotify";
			String serverUrl = CJConfig.msServerUrl+"/quickPayAction/message.action";
			String version = "1.0.0.0";	
			if(StringUtils.isBlank(goodsName)){
				goodsName = memberInfo.getName()+" 收款";
			}
			
			MessageRequestEntity reqEntity = new MessageRequestEntity();
			reqEntity.setV_version(version);
			reqEntity.setV_mid(merCode);
			reqEntity.setV_oid(orderCode);
			reqEntity.setV_time(new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
			reqEntity.setV_txnAmt(payMoney);
			reqEntity.setV_realName(accountName);
			reqEntity.setV_cardNo(bankAccount);
			reqEntity.setV_accountType(accountType);
			reqEntity.setV_type("1");//D0:0    T1：1
			reqEntity.setV_productDesc(goodsName);
			reqEntity.setV_phone(tel);
			reqEntity.setV_notify_url(callBack);
			reqEntity.setV_url(CJConfig.frontUrl+"?orderCode="+orderCode);
			reqEntity.setV_cvn2(bankCvv);
			reqEntity.setV_expired(bankYxq);
			reqEntity.setV_attach(goodsName);
			reqEntity.setV_userFee(platTradeRate);
			String sign = SignatureUtil.getSign(CommonUtil.beanToMap(reqEntity), merchantKey.getPrivateKey(), logger);
			reqEntity.setV_sign(sign);
			Bean2QueryStrUtil bean = new Bean2QueryStrUtil();
			String postStr = bean.bean2QueryStr(reqEntity);
			logger.info("畅捷快捷支付短信发送请求参数[{}]",postStr );
			String respStr = HttpURLConection.httpURLConnectionPOST(serverUrl, postStr);
			logger.info("畅捷快捷支付短信发送返回报文[{}]", new Object[] { respStr });
			String resultMsg = "";
			String code = "";
			boolean flag = false;
			if(StringUtils.isNotEmpty(respStr)){
				Gson gson = new Gson();
				MessAgeResponseEntity respEntity = gson.fromJson(respStr, MessAgeResponseEntity.class);
				code = respEntity.getV_code();
				Map map = BeanToMapUtil.convertBean(respEntity);
				if("00".equals(code)){
					if(SignatureUtil.checkSign(map, merchantKey.getPrivateKey(), logger)) {
						result.put("returnCode", "0000");
						result.put("returnMsg", "成功");
						flag = true;
					}else{
						resultMsg = "出参验签失败";
					}
				}else{
					resultMsg = respEntity.getV_msg();
				}
			}else{
				resultMsg = "接口调用失败";
			}
			if(!flag)
			{
				result.put("returnCode", "0003");
				result.put("returnMsg", resultMsg);
				
				QuickPaySmsExample quickPaySmsExample = new QuickPaySmsExample();
				quickPaySmsExample.createCriteria().andOrderCodeEqualTo(orderCode);
				List<QuickPaySms> quickPays = quickPaySmsService.selectByExample(quickPaySmsExample);
				if (quickPays != null && quickPays.size() > 0) {
					QuickPaySms quickPay_1 = quickPays.get(0);
					quickPay_1.setStatus("2");
					quickPay_1.setUpdateDate(new Date());
					quickPay_1.setRespCode(code);
					if(!"".equals(resultMsg)){
						quickPay_1.setRespMsg(resultMsg.length()>250?resultMsg.substring(0, 250):resultMsg);
					}
					quickPaySmsService.updateByPrimaryKey(quickPay_1);
				}
			}
			
		} catch (Exception e) {
			logger.error(e.getMessage());
			result.put("returnCode", "0096");
			result.put("returnMsg", e.getMessage());
			return result;
		}
		return result;
	}
	
	/**
	 * 快捷支付
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/api/quickPay/toPay")
	public JSONObject toPay(HttpServletRequest request,HttpServletResponse response){
		JSONObject requestPRM = (JSONObject) request.getAttribute("requestPRM");
		JSONObject reqDataJson = requestPRM.getJSONObject("reqData");// 获取请求参数
		logger.info("请求参数==="+reqDataJson.toString());
		String orderNum = "";
		if(reqDataJson.containsKey("orderNum")){
			orderNum = reqDataJson.getString("orderNum");
		}
		String memberCode = "";
		if(reqDataJson.containsKey("memberCode")){
			memberCode = reqDataJson.getString("memberCode");
		}
		String smsCode = "";
		if(reqDataJson.containsKey("smsCode")){
			smsCode = reqDataJson.getString("smsCode");
		}
		
		String signStr = "";
		if(reqDataJson.containsKey("signStr")){
			signStr = reqDataJson.getString("signStr");
		}
		
		StringBuilder srcStr = new StringBuilder();
		JSONObject result = new JSONObject();
		
		if(memberCode == null || "".equals(memberCode)){
			result.put("returnCode", "0007");
			result.put("returnMsg", "商户号缺失");
			return result;
		}
		srcStr.append("memberCode="+memberCode);
		
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
		
		if(smsCode == null || "".equals(smsCode)){
			result.put("returnCode", "0007");
			result.put("returnMsg", "短信验证码缺失");
			return result;
		}
		srcStr.append("&smsCode="+smsCode);
		
		if(signStr == null || "".equals(signStr)){ 
			result.put("returnCode", "0007");
			result.put("returnMsg", "缺少签名信息");
			return result;
		}
		
		result = validMemberInfoToQuickPay(memberCode, orderNum,smsCode, srcStr.toString(), signStr);
		
		return result;
	}
	
	public JSONObject validMemberInfoToQuickPay(String memberCode,String orderNum,String smsCode,String signOrginalStr,String signedStr){
		JSONObject result = new JSONObject();
		try{
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
			
			QuickPaySmsExample quickPaySmsExample = new QuickPaySmsExample();
			quickPaySmsExample.createCriteria().andOrderNumOuterEqualTo(orderNum).andStatusEqualTo("0");
			quickPaySmsExample.setOrderByClause(" create_date desc ");
			List<QuickPaySms> quickPaySmsList = quickPaySmsService.selectByExample(quickPaySmsExample);
			if(quickPaySmsList == null || quickPaySmsList.size()==0){
				result.put("returnCode", "0002");
				result.put("returnMsg", "订单号不存在");
				return result;
			}
			
			QuickPaySms quickPaySms = quickPaySmsList.get(0);
			String routeCode = quickPaySms.getRouteId();
			
			SysOffice sysOffice = sysOfficeList.get(0);
			if(!EpaySignUtil.checksign(sysOffice.getPublicKeyRsa(), signOrginalStr, signedStr)){//by linxf 测试屏蔽
				result.put("returnCode", "0004");
				result.put("returnMsg", "签名校验错误，请检查签名参数是否正确");
				return result;
			}
		
			
			if(RouteCodeConstant.CJ_ROUTE_CODE.equals(routeCode)){
				result = cjQuickPay("3",memberInfo,quickPaySms,smsCode);
			}
		}catch(Exception e){
			logger.info(e.getMessage());
			result.put("returnCode", "0096");
			result.put("returnMsg", e.getMessage());
		}
		return result;
	}
	/**
	 * 畅捷快捷支付
	 * @param platformType
	 * @param memberInfo
	 * @param quickPaySms
	 * @param smsCode
	 * @return
	 */
	public JSONObject cjQuickPay(String platformType,MemberInfo memberInfo,QuickPaySms quickPaySms,String smsCode) {
		JSONObject result = new JSONObject();
		try {
			String merCode = quickPaySms.getMerchantCode();
			String routeCode = quickPaySms.getRouteId();
			
			MemberMerchantKeyExample memberMerchantKeyExample = new MemberMerchantKeyExample();
	        memberMerchantKeyExample.createCriteria().andRouteCodeEqualTo(routeCode).andMerchantCodeEqualTo(merCode).andDelFlagEqualTo("0");
	        List<MemberMerchantKey> keyList = memberMerchantKeyService.selectByExample(memberMerchantKeyExample);
	        if(keyList == null || keyList.size()!=1){
	            result.put("returnCode", "0008");
				result.put("returnMsg", "商户私钥未配置");
				return result;
	        }
	        MemberMerchantKey merchantKey = keyList.get(0);
	        
	        
	        DebitNote debitNote = new DebitNote();
			debitNote.setCreateDate(new Date());
			debitNote.setMemberId(quickPaySms.getMemberId());
			debitNote.setMoney(quickPaySms.getMoney());
			debitNote.setOrderCode(quickPaySms.getOrderCode());
			debitNote.setOrderNumOuter(quickPaySms.getOrderNumOuter());
			debitNote.setRouteId(routeCode);
			debitNote.setStatus("0");
			debitNote.setTxnMethod(quickPaySms.getTxnMethod());
			debitNote.setTxnType(quickPaySms.getTxnType());
			debitNote.setMerchantCode(merCode);
			debitNote.setMemberCode(quickPaySms.getMemberCode());
			debitNote.setSettleType(quickPaySms.getSettleType());
			debitNote.setTradeRate(quickPaySms.getTradeRate());
			
			debitNoteService.insertSelective(debitNote);
			
			PayResultNotice payResultNotice = new PayResultNotice();
			payResultNotice.setOrderCode(debitNote.getOrderCode());
			payResultNotice.setOrderNumOuter(debitNote.getOrderNumOuter());
			payResultNotice.setPayMoney(debitNote.getMoney());
			payResultNotice.setMemberCode(memberInfo.getCode());
			payResultNotice.setPayType(quickPaySms.getTxnType());
			payResultNotice.setReturnUrl(quickPaySms.getReturnUrl());
			payResultNotice.setStatus("1");
			payResultNotice.setCreateDate(new Date());
			payResultNotice.setPlatformType(platformType);
			if("3".equals(platformType)){
				payResultNotice.setInterfaceType("2");
			}else{
				payResultNotice.setInterfaceType("1");
			}
			payResultNoticeService.insertSelective(payResultNotice);
	        
	        String reqTime = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
			ConsumeRequestEntity consume = new ConsumeRequestEntity();
			consume.setV_version("1.0.0.0");
			consume.setV_mid(merCode);
			consume.setV_oid(quickPaySms.getOrderCode());
			consume.setV_smsCode(smsCode);
			consume.setV_time(reqTime);
			consume.setV_type("1");
			String sign = SignatureUtil.getSign(CommonUtil.beanToMap(consume), merchantKey.getPrivateKey(), logger);
			result.put("version", "1.0.0.0");
			result.put("merchantCode", merCode);
			result.put("orderCode", quickPaySms.getOrderCode());
			result.put("time", reqTime);
			result.put("type", "1");
			result.put("signStr", sign);
			result.put("payUrl", CJConfig.msServerUrl+"/quickPayAction/pay.action");
			result.put("returnCode", "0000");
			result.put("returnMsg", "成功");
		} catch (Exception e) {
			logger.error(e.getMessage());
			result.put("returnCode", "0096");
			result.put("returnMsg", e.getMessage());
			return result;
		}
		return result;
	}
	
	
	/**
	 * 畅捷快捷支付回调
	 * @param request
	 * @param response
	 */
	@RequestMapping("/quickPay/cjPayNotify")
	public void cjPayNotify(HttpServletRequest request,HttpServletResponse response) {
		JSONObject rtObj = new JSONObject();
		try {
						
			String responseStr = HttpUtil.getPostString(request);
		//	String responseStr = "v_status_msg=支付成功&v_code=00&v_msg=请求成功&v_status=0000&v_mid=10034275500&v_oid=20180314105813466351&v_time=20180314105813&v_sign=8FAE574505D6369869F632B406A6C155&v_txnAmt=2";
		//	String responseStr = "v_mid=10034272896&v_oid=20180308141922977802&v_txnAmt=2&v_code=00&v_msg=支付成功&v_time=1421&v_attach=苹果&v_status=0000&v_sign=BE4D7688F61C50DB7DFDA6E2D17BA908";
		//	String responseStr = "v_mid=10034272896&v_oid=20180212145220045041&v_txnAmt=2&v_code=00&v_msg=支付成功&v_time=&v_attach=苹果&v_status=0000&sign=";
			logger.info("cjPayNotify回调返回报文[{}]",  responseStr );
			
			String[] arr = responseStr.split("&");
			Map<String,String> resJo = new HashMap<String, String>();
			for(int i=0;i<arr.length;i++){
				String str = arr[i];
				String[] arr1 = str.split("=");
				if(arr1.length==2){
					resJo.put(arr1[0], arr1[1]);
				}
			}
			String v_code = resJo.get("v_code");
			String v_status = resJo.get("v_status");
			String v_msg = resJo.get("v_msg");
			String v_txnAmt = resJo.get("v_txnAmt");
			String v_mid = resJo.get("v_mid");
			String v_oid = resJo.get("v_oid");
			String v_attach =  resJo.get("v_attach");
			String v_time =  resJo.get("v_time");
			String v_sign =  resJo.get("v_sign");
			String v_status_msg = resJo.get("v_status_msg");
			
			String reqMsgId = v_oid;
        	
        	TradeDetailExample tradeDetailExample = new TradeDetailExample();
        	tradeDetailExample.or().andOrderCodeEqualTo(reqMsgId);
        	List<TradeDetail> tradeDetailList = tradeDetailService.selectByExample(tradeDetailExample);
        	if(tradeDetailList!=null && tradeDetailList.size()>0){
        		rtObj.put("success", "true");
        		response.getWriter().write(rtObj.toString());
        		return;
        	}
        	
        	DebitNoteExample debitNoteExample = new DebitNoteExample();
			debitNoteExample.createCriteria().andOrderCodeEqualTo(reqMsgId);
			List<DebitNote> debitNotes_tmp = debitNoteService.selectByExample(debitNoteExample);
			if (debitNotes_tmp == null || debitNotes_tmp.size() == 0) {
				rtObj.put("success", "false");
				logger.info(rtObj.toString()+" 订单不存在");
                response.getWriter().write(rtObj.toString());
        		return;
				
			}
			DebitNote debitNote = debitNotes_tmp.get(0);
        	MemberMerchantKeyExample memberMerchantKeyExample = new MemberMerchantKeyExample();
            memberMerchantKeyExample.createCriteria().andRouteCodeEqualTo(debitNote.getRouteId()).andMerchantCodeEqualTo(debitNote.getMerchantCode()).andDelFlagEqualTo("0");
            List<MemberMerchantKey> keyList = memberMerchantKeyService.selectByExample(memberMerchantKeyExample);
            if(keyList == null || keyList.size()!=1){
            	rtObj.put("success", "false");
        		logger.info(rtObj.toString()+" 商户私钥未配置");
                response.getWriter().write(rtObj.toString());
        		return;
            }
            ConsumeResponseEntity respEntity = new ConsumeResponseEntity();
            respEntity.setV_code(v_code);
            respEntity.setV_status(v_status);
            respEntity.setV_msg(v_msg);
            respEntity.setV_txnAmt(v_txnAmt);
            respEntity.setV_mid(v_mid);
            respEntity.setV_oid(v_oid);
            respEntity.setV_attach(v_attach);
            respEntity.setV_sign(v_sign);
            respEntity.setV_time(v_time);
            respEntity.setV_status_msg(v_status_msg);
            
            Map map = BeanToMapUtil.convertBean(respEntity);
			if(!SignatureUtil.checkSign(map, keyList.get(0).getPrivateKey(), logger)) {
				rtObj.put("success", "false");
				logger.info(rtObj.toString()+" 验证签名不通过");
                response.getWriter().write(rtObj.toString());
        		return;
			}
            
            
        	
        	String result_message = v_msg;
        	MsResultNoticeExample msResultNoticeExample = new MsResultNoticeExample();
			msResultNoticeExample.or().andOrderCodeEqualTo(reqMsgId);
			List<MsResultNotice> msResultNoticeList = msResultNoticeService.selectByExample(msResultNoticeExample);
			if(null == msResultNoticeList || msResultNoticeList.size() == 0){
				if (debitNote != null ) {
					debitNote.setRespCode(v_status);
					debitNote.setRespMsg(result_message);
					
					//新增一条交易明细记录
					TradeDetail tradeDetail=new TradeDetail();
					tradeDetail.setTxnDate(DateUtil.getDateFormat(new Date(), "yyyyMMdd"));
					tradeDetail.setMemberId(debitNote.getMemberId());
					tradeDetail.setMerchantCode(debitNote.getMerchantCode());
					
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
					if ("00".equals(v_code)&&"0000".equals(v_status)) {
						debitNote.setStatus("1");
						tradeDetail.setRespType("S");
						tradeDetail.setRespCode("000000");
					}else if("1002".equals(v_status)){
						debitNote.setStatus("3");
						tradeDetail.setRespType("R");
						tradeDetail.setRespCode(v_status);
					}else{
						debitNote.setStatus("2");
						tradeDetail.setRespType("E");
						tradeDetail.setRespCode(v_status);
					}
					debitNote.setUpdateDate(new Date());
					debitNoteService.updateByPrimaryKey(debitNote);
					
					MsResultNotice msResultNotice = new MsResultNotice();
					msResultNotice.setMoney(new BigDecimal(v_txnAmt));
					msResultNotice.setOrderCode(reqMsgId);
					
					//SimpleDateFormat dateSimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					msResultNotice.setRespDate(DateUtil.getDateTimeStr(new Date()));
					msResultNotice.setRespType("");
					msResultNotice.setRespCode(v_status);
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
						if ("00".equals(v_code)&&"0000".equals(v_status)) {
							payResultNotice.setRespType("2");
							payResultNotice.setResultCode("0000");
							payResultNotice.setResultMessage("交易成功");
						}else if("1002".equals(v_status)){
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
					if ("00".equals(v_code)&&"0000".equals(v_status)) {
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
	
	
	@ResponseBody
	@RequestMapping("/quickPay/toPay")
	public JSONObject toQuickPay(HttpServletRequest request,HttpServletResponse response){
		String accountName = request.getParameter("accountName");
		String accountType = request.getParameter("accountType");
		String bankAccount = request.getParameter("bankAccount");
		String bankCvv = request.getParameter("bankCvv");
		String bankYxq = request.getParameter("bankYxq");
		String callbackUrl = request.getParameter("callbackUrl");
		String certNo = request.getParameter("certNo");
		String goodsName = request.getParameter("goodsName");
		String memberCode = request.getParameter("memberCode");
		String orderNum = request.getParameter("orderNum");
		String payMoney = request.getParameter("payMoney");
		String tel = request.getParameter("tel");
		String ip = request.getParameter("ip");
		
		String signStr = request.getParameter("signStr");
		
		StringBuilder srcStr = new StringBuilder();
		JSONObject result = new JSONObject();
		
		if(accountName == null || "".equals(accountName)){
			result.put("returnCode", "0007");
			result.put("returnMsg", "账户名称缺失");
			return CommonUtil.signReturn(result);
		}
		srcStr.append("accountName="+accountName);
		
		if(accountType == null || "".equals(accountType)){
			result.put("returnCode", "0007");
			result.put("returnMsg", "账户类型缺失");
			return CommonUtil.signReturn(result);
		}
		srcStr.append("&accountType="+accountType);
		
		if(bankAccount == null || "".equals(bankAccount)){
			result.put("returnCode", "0007");
			result.put("returnMsg", "银行卡号缺失");
			return CommonUtil.signReturn(result);
		}
		srcStr.append("&bankAccount="+bankAccount);
		
		if(bankCvv != null && !"".equals(bankCvv)){
			srcStr.append("&bankCvv="+bankCvv);
		}
		
		if(bankYxq != null && !"".equals(bankYxq)){
			srcStr.append("&bankYxq="+bankYxq);
		}
		
		if(callbackUrl == null || "".equals(callbackUrl)){
			result.put("returnCode", "0007");
			result.put("returnMsg", "支付结果通知回调地址缺失");
			return result;
		}
		srcStr.append("callbackUrl="+callbackUrl);
		
		if(certNo == null || "".equals(certNo)){
			result.put("returnCode", "0007");
			result.put("returnMsg", "身份证号码缺失");
			return CommonUtil.signReturn(result);
		}
		srcStr.append("&certNo="+certNo);
		
		if(goodsName != null && !"".equals(goodsName)){
			srcStr.append("&goodsName="+goodsName);
		}
		
		if(memberCode == null || "".equals(memberCode)){
			result.put("returnCode", "0007");
			result.put("returnMsg", "商户号缺失");
			return CommonUtil.signReturn(result);
		}
		srcStr.append("&memberCode="+memberCode);
		
		if(orderNum == null || "".equals(orderNum)){
			result.put("returnCode", "0007");
			result.put("returnMsg", "商户订单号缺失");
			return CommonUtil.signReturn(result);
		}
		if(orderNum.length() > 32){
			result.put("returnCode", "0007");
			result.put("returnMsg", "订单号长度不能超过32位");
			return CommonUtil.signReturn(result);
		}
		srcStr.append("&orderNum="+orderNum);
		
		if(null == payMoney || !ValidateUtil.isDoubleT(payMoney) || Double.parseDouble(payMoney)<=0){
			result.put("returnCode", "0007");
			result.put("returnMsg", "支付金额输入不正确");
			return CommonUtil.signReturn(result);
		}
		srcStr.append("&payMoney="+payMoney);
		
		if(tel == null || "".equals(tel)){
			result.put("returnCode", "0007");
			result.put("returnMsg", "手机号缺失");
			return CommonUtil.signReturn(result);
		}
		srcStr.append("&tel="+tel);
		
		if(signStr == null || "".equals(signStr)){ 
			result.put("returnCode", "0007");
			result.put("returnMsg", "缺少签名信息");
			return CommonUtil.signReturn(result);
		}
		result = validMemberInfoForQuickPay(memberCode, orderNum, payMoney, accountType,accountName,bankAccount,bankCvv,bankYxq, certNo,tel,goodsName,callbackUrl,  srcStr.toString(), signStr,ip);
		
		return CommonUtil.signReturn(result);
	}
	
	
	public JSONObject validMemberInfoForQuickPay(String memberCode,String orderNum,String payMoney,String accountType,String accountName,String bankAccount,String bankCvv,String bankYxq,String certNo ,String tel,String goodsName,String callbackUrl,String signOrginalStr,String signedStr,String ip){
		JSONObject result = new JSONObject();
		try{
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
			
			MemberPayTypeExample memberPayTypeExample = new MemberPayTypeExample();
			memberPayTypeExample.createCriteria().andMemberIdEqualTo(memberInfo.getId()).andPayMethodEqualTo(PayTypeConstant.PAY_METHOD_YL).andPayTypeEqualTo(PayTypeConstant.PAY_TYPE_KJ).andDelFlagEqualTo("0");
			List<MemberPayType> memberPayTypeList =  memberPayTypeService.selectByExample(memberPayTypeExample);
			if(memberPayTypeList==null || memberPayTypeList.size()==0){
				result.put("returnCode", "0008");
				result.put("returnMsg", "对不起，商户未开通该支付权限");
				return result;
			}
			
			MemberPayType memberPayType = memberPayTypeList.get(0);
			
			Map<String,String> rtMap  = commonUtilService.getRouteCodeAndAisleType(memberInfo.getId(),PayTypeConstant.PAY_METHOD_YL,PayTypeConstant.PAY_TYPE_KJ);
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
			
			SysOfficeExample sysOfficeExample = new SysOfficeExample();
			sysOfficeExample.or().andIdEqualTo(epayCodeList.get(0).getOfficeId()).andAgtTypeEqualTo("3");
			List<SysOffice> sysOfficeList = sysOfficeService.selectByExample(sysOfficeExample);
			if(null == sysOfficeList || sysOfficeList.size() == 0){
				result.put("returnCode", "0008");
				result.put("returnMsg", "该商户归属机构暂不可用，请确认后重试");
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
//			
			if(!EpaySignUtil.checksign(sysOffice.getPublicKeyRsa(), signOrginalStr, signedStr)){//by linxf 测试屏蔽
				result.put("returnCode", "0004");
				result.put("returnMsg", "签名校验错误，请检查签名参数是否正确");
				return result;
			}
		
			
			if(RouteCodeConstant.YZF_ROUTE_CODE.equals(routeCode)){
				result = yzfQuickPay("3",memberInfo,merchantCode,payMoney,orderNum,accountName,bankAccount,bankCvv,bankYxq,certNo,tel,callbackUrl,goodsName,routeCode,aisleType);
			}else if(RouteCodeConstant.HX_ROUTE_CODE.equals(routeCode)){
				memberInfo.setSettleType("1");
				result = quickPayHx("3",memberInfo, payMoney, orderNum, callbackUrl , merchantCode ,routeCode, "" ,goodsName);
				result.put("routeCode", routeCode);
			}else if(RouteCodeConstant.ML_ROUTE_CODE.equals(routeCode)){
				memberInfo.setSettleType("1");
				result = mlQuickPay("3",memberInfo,merchantCode,memberPayType,payMoney,orderNum,accountType,accountName,bankAccount,bankCvv,bankYxq,certNo,tel,callbackUrl,goodsName,routeCode,aisleType);
				result.put("routeCode", routeCode);
			}else if(RouteCodeConstant.ESKKJ_ROUTE_CODE.equals(routeCode)){
				memberInfo.setSettleType("0");
				result = eskQuickPay("3",memberInfo,merchantCode,memberPayType,payMoney,orderNum,accountType,accountName,bankAccount,bankCvv,bankYxq,certNo,tel,callbackUrl,goodsName,routeCode,aisleType,ip);
				result.put("routeCode", routeCode);
			}
			

		}catch(Exception e){
			logger.info(e.getMessage());
			result.put("returnCode", "0096");
			result.put("returnMsg", e.getMessage());
		}
		return result;
	}
	
	public JSONObject yzfQuickPay(String platformType,MemberInfo memberInfo,MemberMerchantCode merchantCode,String payMoney,String orderNumOuter,String accountName,String bankAccount,String bankCvv,String bankYxq,String certNo,String tel,String callbackUrl,String goodsName,String routeCode,String aisleType) {
		JSONObject result = new JSONObject();
		try {
			String payType = "9";
			String payTypeStr = PayTypeConstant.PAY_TYPE_KJ;
			String payMethod = PayTypeConstant.PAY_METHOD_YL;
			String merCode = merchantCode.getKjMerchantCode();
			
			
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
			debitNote.setTxnMethod(payMethod);
			debitNote.setTxnType(payType);
			debitNote.setMerchantCode(merCode);
			debitNote.setMemberCode(memberInfo.getWxMemberCode());
			debitNote.setSettleType(memberInfo.getSettleType());
			if("0".equals(memberInfo.getSettleType())){
				debitNote.setTradeRate(merchantCode.getKjT0TradeRate());
			}else{
				debitNote.setTradeRate(merchantCode.getKjT1TradeRate());
			}
			
			
			String configName = "SINGLE_MEMBER_LIMIT_"+memberInfo.getId()+"_"+payMethod+"_"+payTypeStr;
			JSONObject memResult = commonUtilService.checkLimitMoney(configName, new BigDecimal(payMoney));
			if(null != memResult){
				debitNote.setStatus("9");
				debitNoteService.insertSelective(debitNote);
				return memResult;
			} 
			
			configName = "SINGLE_MIN_"+routeCode+"_"+payMethod+"_"+payTypeStr;
			JSONObject checkResult = commonUtilService.checkMinMoney(configName, new BigDecimal(payMoney));
			if(null != checkResult){
				debitNote.setStatus("5");
				debitNoteService.insertSelective(debitNote);
				return checkResult;
			}
			
			configName = "SINGLE_LIMIT_"+routeCode+"_"+payMethod+"_"+payTypeStr;
			JSONObject limitResult = commonUtilService.checkLimitMoney(configName, new BigDecimal(payMoney));
			if(null != limitResult){
				debitNote.setStatus("4");
				debitNoteService.insertSelective(debitNote);
				return limitResult;
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
			
			String action = "SdkNocardOrderPayNoSms";
			String callBack = SysConfig.serverUrl + "/quickPay/yzfPayNotify";
			
			String serverUrl = YZFConfig.msServerUrl;
			String charset = "UTF-8";
			String version = "2.0";	
			String merNo = merCode;//商户号	
			String orgNo = merchantKey.getAppId();//机构
			String privateKeyStr = merchantKey.getPrivateKey();//RSA私钥
			String md5Key = merchantKey.getPrivateKeyPassword();
			String aesKey = MSCommonUtil.generateLenString(16);
			
			byte[] keyBytes = Base64Utils.decode(privateKeyStr);  
		    PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);  
		    KeyFactory keyFactory = KeyFactory.getInstance("RSA");  
		    PrivateKey privateK = keyFactory.generatePrivate(pkcs8KeySpec);  
			byte[] RSAKeyBy=RSATool.encrypt(aesKey.getBytes("UTF-8"),privateK);
			String encryptkey= EncryptUtil.encodeBase64(RSAKeyBy);
			
			
			JSONObject contextjson = new JSONObject();
	     	
			contextjson.put("linkId",orderCode);//订单流水号
	     	contextjson.put("orderType","10");//订单类型
	     	contextjson.put("amount",String.valueOf((new BigDecimal(payMoney)).multiply(new BigDecimal(100)).intValue()));//消费金额
	     	contextjson.put("bankNo",bankAccount);//银行卡号
	     	contextjson.put("bankAccount",accountName);//银行账户
	     	contextjson.put("bankPhone",tel);//绑定手机号码
	     	contextjson.put("bankCert",certNo);//绑定身份证号
	     	contextjson.put("bankCvv",bankCvv);//信用卡后三位
	     	contextjson.put("bankYxq",bankYxq);//信用卡有效期
	     	contextjson.put("notifyUrl",callBack);//异步通知地址
	     	contextjson.put("goodsName",goodsName);//商品名称
	     	
	     	String aesData=AESTool.encrypt(contextjson.toString(),aesKey);
			
			logger.info("快捷支付请求未加密参数[{}]",contextjson.toString() );
			
			
			String data	= aesData;//EncryptUtil.getBase64(aesData);			
	     	
	     	String signData = version+orgNo+merNo+action+data+md5Key;
	    	String sign	= EncryptUtil.MD5(signData, 1);
	    	
	     	
	    	List<NameValuePair> nvps = new LinkedList<NameValuePair>();
			nvps.add(new BasicNameValuePair("version", version));
			nvps.add(new BasicNameValuePair("merNo", merNo));
			nvps.add(new BasicNameValuePair("orgNo", orgNo));
			nvps.add(new BasicNameValuePair("action", action));
			nvps.add(new BasicNameValuePair("data", aesData));
			nvps.add(new BasicNameValuePair("encryptkey", encryptkey));
			nvps.add(new BasicNameValuePair("sign", sign));
			byte[] b = HttpClient4Util.getInstance().doPost(serverUrl, null, nvps);
			
			String respStr = new String(b, charset);
			logger.info("快捷支付返回报文[{}]", new Object[] { respStr });
			
			JSONObject jsonObj = JSONObject.fromObject(respStr);
			byte[] encryptkey_RES=EncryptUtil.decodeBase64(jsonObj.get("encryptkey").toString());
			
			byte[] key_RES_B=RSATool.decrypt(encryptkey_RES,privateK);
			String aes_res_key=new String (key_RES_B);
			logger.info("快捷支付返回报文解密AES key[{}]", new Object[] { aes_res_key });
			String data_RES=jsonObj.get("data").toString();
			String resData=AESTool.decrypt(data_RES, aes_res_key);
			logger.info("快捷支付返回报文解密结果[{}]", new Object[] { resData });
			
			JSONObject resObj = JSONObject.fromObject(resData);
			String code = resObj.getString("code");
			
			if("0000".equals(code)||"0100".equals(code)){
				result.put("orderNo", resObj.getString("orderNo"));
				result.put("returnCode", "0000");
				result.put("returnMsg", "成功");
			}else{
				String resultMsg = resObj.getString("msg");
				result.put("returnCode", "0003");
				result.put("returnMsg", resultMsg);
				
				DebitNoteExample debitNoteExample = new DebitNoteExample();
				debitNoteExample.createCriteria().andOrderCodeEqualTo(orderCode);
				List<DebitNote> debitNotes = debitNoteService.selectByExample(debitNoteExample);
				if (debitNotes != null && debitNotes.size() > 0) {
					DebitNote debitNote_1 = debitNotes.get(0);
					debitNote_1.setStatus("2");
					debitNote_1.setUpdateDate(new Date());
					debitNote_1.setRespCode(code);
					if(!"".equals(resultMsg)){
						debitNote_1.setRespMsg(resultMsg.length()>250?resultMsg.substring(0, 250):resultMsg);
					}
					debitNoteService.updateByPrimaryKey(debitNote_1);
				}
			}
			
		} catch (Exception e) {
			logger.error(e.getMessage());
			result.put("returnCode", "0096");
			result.put("returnMsg", e.getMessage());
			return result;
		}
		return result;
	}
	
	
	

	public JSONObject orderQuery(HttpServletRequest request,HttpServletResponse response) {
		JSONObject result = new JSONObject();
		try {
			String orderNum = request.getParameter("orderNum");
			String memberCode = request.getParameter("memberCode");
			String signStr = request.getParameter("signStr");
			
			StringBuilder srcStr = new StringBuilder();
			
			if(memberCode == null || "".equals(memberCode)){
				result.put("returnCode", "0007");
				result.put("returnMsg", "商户号缺失");
				return CommonUtil.signReturn(result);
			}
			srcStr.append("memberCode="+memberCode);
			if(orderNum == null || "".equals(orderNum)){
				result.put("returnCode", "0007");
				result.put("returnMsg", "商户订单号缺失");
				return CommonUtil.signReturn(result);
			}
			if(orderNum.length() > 32){
				result.put("returnCode", "0007");
				result.put("returnMsg", "订单号长度不能超过32位");
				return CommonUtil.signReturn(result);
			}
			srcStr.append("&orderNum="+orderNum);
			
			if(signStr == null || "".equals(signStr)){ 
				result.put("returnCode", "0007");
				result.put("returnMsg", "缺少签名信息");
				return CommonUtil.signReturn(result);
			}
			MemberInfoExample memberInfoExample = new MemberInfoExample();
			memberInfoExample.or().andCodeEqualTo(memberCode).andDelFlagEqualTo("0");
			List<MemberInfo> memberInfoList = memberInfoService.selectByExample(memberInfoExample);
			if(null == memberInfoList || memberInfoList.size() == 0){
				result.put("returnCode", "0001");
				result.put("returnMsg", "商户信息不存在");
				return CommonUtil.signReturn(result);
			}
			MemberInfo memberInfo = memberInfoList.get(0);
			if(!"0".equals(memberInfo.getStatus())){
				if("4".equals(memberInfo.getStatus())){
					result.put("returnCode", "0008");
					result.put("returnMsg", "该商户未进行认证，暂时无法交易");
					return CommonUtil.signReturn(result);
				}
				result.put("returnCode", "0008");
				result.put("returnMsg", "对不起，该商户暂不可用");
				return CommonUtil.signReturn(result);
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
				return CommonUtil.signReturn(result);
			}
			
			
			SysOfficeExample sysOfficeExample = new SysOfficeExample();
			sysOfficeExample.or().andIdEqualTo(epayCodeList.get(0).getOfficeId()).andAgtTypeEqualTo("3");
			List<SysOffice> sysOfficeList = sysOfficeService.selectByExample(sysOfficeExample);
			if(null == sysOfficeList || sysOfficeList.size() == 0){
				result.put("returnCode", "0008");
				result.put("returnMsg", "该商户暂未支持收银台功能，请确认后重试");
				return CommonUtil.signReturn(result);
			}
			
			
			SysOffice sysOffice = sysOfficeList.get(0);
			
		/*	if(!EpaySignUtil.checksign(sysOffice.getPublicKeyRsa(), srcStr.toString(), signStr)){
				result.put("returnCode", "0004");
				result.put("returnMsg", "签名校验错误，请检查签名参数是否正确");
				return CommonUtil.signReturn(result);
			}*/
			
			DebitNoteExample debitNoteExample=new DebitNoteExample();
			debitNoteExample.createCriteria().andOrderNumOuterEqualTo(orderNum);
			List<DebitNote> debitNotes=debitNoteService.selectByExample(debitNoteExample);
			if(debitNotes==null||debitNotes.size()==0){
				throw new ArgException("支付单不存在!");
			}
			
			DebitNote debitNote=debitNotes.get(0);
			
			result.put("orderCode", debitNote.getOrderCode());
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			result.put("orderTime", format.format(debitNote.getCreateDate()));
			
			String routeCode = debitNote.getRouteId();
			
			if(RouteCodeConstant.YZF_ROUTE_CODE.equals(routeCode)){
				MemberMerchantKeyExample memberMerchantKeyExample = new MemberMerchantKeyExample();
		        memberMerchantKeyExample.createCriteria().andRouteCodeEqualTo(routeCode).andMerchantCodeEqualTo(debitNote.getMerchantCode()).andDelFlagEqualTo("0");
		        List<MemberMerchantKey> keyList = memberMerchantKeyService.selectByExample(memberMerchantKeyExample);
		        if(keyList == null || keyList.size()!=1){
		        	result.put("returnCode", "0008");
					result.put("returnMsg", "商户私钥未配置");
					return CommonUtil.signReturn(result);
		        }
		        MemberMerchantKey merchantKey = keyList.get(0);
		        
		        
		        String serverUrl = YZFConfig.msServerUrl;
				String charset = "UTF-8";
				String version = "2.0";	
				String action = "SdkOrderQuery";
				String merNo = debitNote.getMerchantCode();//商户号	
				String orgNo = merchantKey.getAppId();//机构
				String privateKeyStr = merchantKey.getPrivateKey();//RSA私钥
				String md5Key = merchantKey.getPrivateKeyPassword();
				String aesKey = MSCommonUtil.generateLenString(16);
				
				byte[] keyBytes = Base64Utils.decode(privateKeyStr);  
			    PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);  
			    KeyFactory keyFactory = KeyFactory.getInstance("RSA");  
			    PrivateKey privateK = keyFactory.generatePrivate(pkcs8KeySpec);  
				byte[] RSAKeyBy=RSATool.encrypt(aesKey.getBytes("UTF-8"),privateK);
				String encryptkey= EncryptUtil.encodeBase64(RSAKeyBy);
				
				JSONObject contextjson = new JSONObject();
		     	contextjson.put("linkId",debitNote.getOrderCode());
		     	
		     	String aesData=AESTool.encrypt(contextjson.toString(),aesKey);
				
				logger.info("订单查询未加密参数[{}]",contextjson.toString() );
				
				
		     	String data	= aesData;
		     	
		     	String signData = version+orgNo+merNo+action+data+md5Key;
		    	String sign	= EncryptUtil.MD5(signData, 1);
		    	
		     	List<NameValuePair> nvps = new LinkedList<NameValuePair>();
				nvps.add(new BasicNameValuePair("version", version));
				nvps.add(new BasicNameValuePair("merNo", merNo));
				nvps.add(new BasicNameValuePair("orgNo", orgNo));
				nvps.add(new BasicNameValuePair("action", action));
				nvps.add(new BasicNameValuePair("data", aesData));
				nvps.add(new BasicNameValuePair("encryptkey", encryptkey));
				nvps.add(new BasicNameValuePair("sign", sign));
				byte[] b = HttpClient4Util.getInstance().doPost(serverUrl, null, nvps);
				
				String respStr = new String(b, charset);
				logger.info("订单查询返回报文[{}]", new Object[] { respStr });
				
				JSONObject jsonObj = JSONObject.fromObject(respStr);
				byte[] encryptkey_RES=EncryptUtil.decodeBase64(jsonObj.get("encryptkey").toString());
				
				byte[] key_RES_B=RSATool.decrypt(encryptkey_RES,privateK);
				String aes_res_key=new String (key_RES_B);
				String data_RES=jsonObj.get("data").toString();
				String resData=AESTool.decrypt(data_RES, aes_res_key);
				logger.info("订单查询返回报文解密结果[{}]", new Object[] { resData });
				
				JSONObject resObj = JSONObject.fromObject(resData);
				String code = resObj.getString("code");
			}
		} catch (ArgException e) {
			result.put("returnCode", "4004");
			result.put("returnMsg", e.getMessage());
			logger.info(e.getMessage());
			return CommonUtil.signReturn(result);
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			result.put("returnCode", "4004");
			result.put("returnMsg", "请求失败");
			return CommonUtil.signReturn(result);
		}
		result.put("returnCode", "0000");
		result.put("returnMsg", "成功");
		return CommonUtil.signReturn(result);
	}
	
	
	/**
	 * 快捷绑卡
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/quickPay/bindCard")
	public JSONObject bindCard(HttpServletRequest request,HttpServletResponse response){
		Map<String,String> inparam = new HashMap<String, String>();
		Enumeration<String> pNames=request.getParameterNames();
		while(pNames.hasMoreElements()){
		    String name=(String)pNames.nextElement();
		    String value=request.getParameter(name);
		    inparam.put(name, value);
		}
		logger.info("快捷绑卡下游入参[{}]",  JSONObject.fromObject(inparam).toString() );
		String accountName = request.getParameter("accountName");
		String accountType = request.getParameter("accountType");
		String bankAccount = request.getParameter("bankAccount");
		String bankCode = request.getParameter("bankCode");
		String bankCvv = request.getParameter("bankCvv");
		String bankYxq = request.getParameter("bankYxq");
		String memberCode = request.getParameter("memberCode");
		String orderNum = request.getParameter("orderNum");
		String tel = request.getParameter("tel");
		String certNbr = request.getParameter("certNbr");
		String signStr = request.getParameter("signStr");
		
		StringBuilder srcStr = new StringBuilder();
		JSONObject result = new JSONObject();
		
		if(accountName == null || "".equals(accountName)){
			result.put("returnCode", "0007");
			result.put("returnMsg", "账户名称缺失");
			return CommonUtil.signReturn(result);
		}
		srcStr.append("accountName="+accountName);
		
		if(accountType == null || "".equals(accountType)){
			result.put("returnCode", "0007");
			result.put("returnMsg", "账户类型缺失");
			return CommonUtil.signReturn(result);
		}
		if(!"1".equals(accountType)&&!"2".equals(accountType)){
			result.put("returnCode", "0007");
			result.put("returnMsg", "账户类型不正确");
			return CommonUtil.signReturn(result);
		}
		
		srcStr.append("&accountType="+accountType);
		
		if(bankAccount == null || "".equals(bankAccount)){
			result.put("returnCode", "0007");
			result.put("returnMsg", "银行卡号缺失");
			return CommonUtil.signReturn(result);
		}
		srcStr.append("&bankAccount="+bankAccount);
		
		if(bankCode == null || "".equals(bankCode)){
			result.put("returnCode", "0007");
			result.put("returnMsg", "银行编码缺失");
			return CommonUtil.signReturn(result);
		}
		srcStr.append("&bankCode="+bankCode);
		
		if("2".equals(accountType)){//信用卡
			if(bankCvv == null || "".equals(bankCvv)){
				result.put("returnCode", "0007");
				result.put("returnMsg", "账户类型为信用卡，卡末三位缺失");
				return CommonUtil.signReturn(result);
			}
			if(bankYxq == null || "".equals(bankYxq)){
				result.put("returnCode", "0007");
				result.put("returnMsg", "账户类型为信用卡，卡有效期缺失");
				return CommonUtil.signReturn(result);
			}
		}
		
		if(bankCvv != null && !"".equals(bankCvv)){
			srcStr.append("&bankCvv="+bankCvv);
		}
		
		if(bankYxq != null && !"".equals(bankYxq)){
			srcStr.append("&bankYxq="+bankYxq);
		}
		
		if(certNbr != null && !"".equals(certNbr)){
			srcStr.append("&certNbr="+certNbr);
		}
		
		if(memberCode == null || "".equals(memberCode)){
			result.put("returnCode", "0007");
			result.put("returnMsg", "商户号缺失");
			return CommonUtil.signReturn(result);
		}
		srcStr.append("&memberCode="+memberCode);
		
		if(orderNum == null || "".equals(orderNum)){
			result.put("returnCode", "0007");
			result.put("returnMsg", "商户订单号缺失");
			return CommonUtil.signReturn(result);
		}
		if(orderNum.length() > 32){
			result.put("returnCode", "0007");
			result.put("returnMsg", "订单号长度不能超过32位");
			return CommonUtil.signReturn(result);
		}
		srcStr.append("&orderNum="+orderNum);
		
		
		if(tel == null || "".equals(tel)){
			result.put("returnCode", "0007");
			result.put("returnMsg", "手机号缺失");
			return CommonUtil.signReturn(result);
		}
		srcStr.append("&tel="+tel);
		
		if(signStr == null || "".equals(signStr)){ 
			result.put("returnCode", "0007");
			result.put("returnMsg", "缺少签名信息");
			return CommonUtil.signReturn(result);
		}
		result = validMemberInfoForBindCard(memberCode, orderNum, accountType, accountName,bankAccount,bankCvv,bankYxq, tel,bankCode,certNbr,  srcStr.toString(), signStr);
		
		return CommonUtil.signReturn(result);
	}
	
	
	public JSONObject validMemberInfoForBindCard(String memberCode,String orderNum,String accountType,String accountName,String bankAccount,String bankCvv,String bankYxq,String tel,String bankCode,String certNbr,String signOrginalStr,String signedStr){
		JSONObject result = new JSONObject();
		try{
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
			
			SysOfficeExample sysOfficeExample = new SysOfficeExample();
			sysOfficeExample.or().andIdEqualTo(epayCodeList.get(0).getOfficeId()).andAgtTypeEqualTo("3");
			List<SysOffice> sysOfficeList = sysOfficeService.selectByExample(sysOfficeExample);
			if(null == sysOfficeList || sysOfficeList.size() == 0){
				result.put("returnCode", "0008");
				result.put("returnMsg", "该商户机构信息不完整，请确认后重试");
				return result;
			}
			
			
			MemberBindAccDtlExample memberBindAccDtlExample = new MemberBindAccDtlExample();
			memberBindAccDtlExample.or().andOrderNumEqualTo(orderNum);
			List<MemberBindAccDtl> memberBindAccDtlList = memberBindAccDtlService.selectByExample(memberBindAccDtlExample);
			if(null != memberBindAccDtlList && memberBindAccDtlList.size() > 0){
				result.put("returnCode", "0002");
				result.put("returnMsg", "该笔订单号已存在，请勿重复发起");
				return result;
			}
			
			SysOffice sysOffice = sysOfficeList.get(0);
			
			if(!EpaySignUtil.checksign(sysOffice.getPublicKeyRsa(), signOrginalStr, signedStr)){//by linxf 测试屏蔽
				result.put("returnCode", "0004");
				result.put("returnMsg", "签名校验错误，请检查签名参数是否正确");
				return result;
			}
		
			String bankName = "";
			Integer bankId = 0 ;
			BankExample bankExample = new BankExample();
			bankExample.createCriteria().andCodeEqualTo(bankCode);
			List<Bank> bankList =  bankService.selectByExample(bankExample);
			if(bankList!=null&&bankList.size()>0){
				bankId = bankList.get(0).getId();
				bankName = bankList.get(0).getName();
			}
			
			String routeCode = memberInfo.getWxRouteId();
			
			MemberBindAccDtl accDtl = new MemberBindAccDtl();
			accDtl.setMemberId(memberInfo.getId());
			accDtl.setAcc(bankAccount);
			accDtl.setAccountType(accountType);
			accDtl.setBankId(String.valueOf(bankId ));
			accDtl.setBankCode(bankCode);
			accDtl.setBankName(bankName);
			accDtl.setName(accountName);
			accDtl.setCertNbr(certNbr);
			accDtl.setCvv(bankCvv);
			accDtl.setExpireTime(bankYxq);
			accDtl.setOrderNum(orderNum);
			accDtl.setMobilePhone(tel);
			accDtl.setRouteCode(routeCode);
			accDtl.setCreateDate(new Date());
			
			JSONObject cardObj = null;
			if(routeCode.equals(RouteCodeConstant.YS_ROUTE_CODE)){
				cardObj = bindCardYs(memberInfo,routeCode,bankAccount,tel,bankCvv,bankYxq,bankCode);
			}else if(routeCode.equals(RouteCodeConstant.WW_ROUTE_CODE)){
				cardObj = bindCardWw(memberInfo,routeCode,bankAccount,tel,bankCvv,bankYxq,bankCode,accountName);
			}else if(routeCode.equals(RouteCodeConstant.ML_ROUTE_CODE)){
				cardObj = bindCardMl(memberInfo,routeCode,bankAccount,tel,bankCvv,bankYxq,bankCode,accountName,certNbr);
			}else if(routeCode.equals(RouteCodeConstant.TLKJ_ROUTE_CODE)){
				cardObj = bindCardTlkj(memberInfo,routeCode,bankAccount,tel,bankCvv,bankYxq,bankCode,accountName,certNbr,accountType);
			}
			
			if(cardObj.containsKey("orderCode")){
				String orderCode = cardObj.getString("orderCode");
				accDtl.setOrderCode(orderCode);
			}
			if("0000".equals(cardObj.getString("returnCode"))){
				accDtl.setRespCode("0");
				result.put("returnCode", "0000");
				if(routeCode.equals(RouteCodeConstant.ML_ROUTE_CODE)||routeCode.equals(RouteCodeConstant.TLKJ_ROUTE_CODE)){
					result.put("returnMsg", cardObj.getString("returnMsg"));
					result.put("PAY_INFO", cardObj.getString("PAY_INFO"));
					result.put("routeCode", routeCode);
				}else{
					String bankCardToken = cardObj.getString("bankCardToken");
					accDtl.setBankCardToken(bankCardToken);
					result.put("returnMsg", "绑卡成功");
					result.put("bankCardToken", bankCardToken);
				}
				
			}else{
				String resultMsg = cardObj.getString("returnMsg");
				accDtl.setRespCode("1");
				accDtl.setRespMsg(resultMsg);
				result.put("returnCode", "0003");
				result.put("returnMsg", resultMsg);
			}
			memberBindAccDtlService.insertSelective(accDtl);
		}catch(Exception e){
			logger.info(e.getMessage());
			result.put("returnCode", "0096");
			result.put("returnMsg", e.getMessage());
		}
		return result;
	}
	
	
	//易生快捷绑卡
	public JSONObject bindCardYs(MemberInfo memberInfo,String routeCode,String bankAccount,String tel,String bankCvv,String bankYxq,String bankCode){
		JSONObject result = new JSONObject();
		try{
			String orderCode = CommonUtil.getOrderCode();
			String serverUrl = YSConfig.msServerUrl+"/swp/dh/card_token.do";
			String orgNo = YSConfig.orgNo;//机构
			String privateKey = YSConfig.privateKey;
			
			
			MemberMerchantCodeExample memberMerchantCodeExample = new MemberMerchantCodeExample();
			memberMerchantCodeExample.createCriteria().andMemberIdEqualTo(memberInfo.getId()).andRouteCodeEqualTo(routeCode).andDelFlagEqualTo("0");
			
			List<MemberMerchantCode> merchantCodes = memberMerchantCodeService.selectByExample(memberMerchantCodeExample);
			if (merchantCodes == null || merchantCodes.size() != 1) {
				result.put("returnCode", "0008");
				result.put("returnMsg", "对不起，商户编码不存在");
				return result;
			}
			MemberMerchantCode merchantCode = merchantCodes.get(0);
			
			BankRouteExample bankRouteExample = new BankRouteExample();
			bankRouteExample.createCriteria().andCodeEqualTo(bankCode).andRouteCodeEqualTo(routeCode).andDelFlagEqualTo("0");
			List<BankRoute> list = bankRouteService.selectByExample(bankRouteExample);
			if(list==null || list.size()!=1){
				result.put("returnCode", "0008");
				result.put("returnMsg", "对不起，银行编码不正确或不支持当前银行");
				return result;
			}
			bankCode = list.get(0).getRouteBankCode();
			
			Map<String,String> param = new HashMap<String, String>();
			param.put("sp_id", orgNo);
			param.put("mch_id", YSConfig.defaultMerNo);
			param.put("out_trade_no", orderCode);
			param.put("sub_mch_id",merchantCode.getKjMerchantCode() );
			param.put("acc_no", bankAccount);
			param.put("mobile", tel);
			param.put("cvn2", bankCvv);
			param.put("expired", bankYxq);
			param.put("bankcode", bankCode);
			
			Date t = new Date();
			Calendar cal = Calendar.getInstance();
			cal.setTime(t);
			long sys_timestamp = cal.getTimeInMillis();
			param.put("timestamp", String.valueOf(sys_timestamp));
			
			String srcStr = StringUtil.orderedKey(param)+"&key="+privateKey;
			String sign = SwpHashUtil.getSign(srcStr, privateKey, "SHA256");
			param.put("sign", sign);
			String paramStr = StringUtil.orderedKey(param) + "&sign="+sign;
			logger.info("快捷绑卡参数[{}]",JSONObject.fromObject(param).toString() );
			
			HttpResponse httpResponse =HttpUtils.doPost(serverUrl, "", paramStr, "application/x-www-form-urlencoded; charset=UTF-8");
			String respStr = EntityUtils.toString(httpResponse.getEntity());
			logger.info("快捷绑卡返回报文[{}]", new Object[] { respStr });
			
			JSONObject resObj = JSONObject.fromObject(respStr);
			
			String code = resObj.getString("status");
			if("SUCCESS".equals(code)){
				String resSign = resObj.getString("sign");
				resObj.remove("sign");
				srcStr = StringUtil.orderedKey(resObj)+"&key="+privateKey;
				if(resSign.equals(SwpHashUtil.getSign(srcStr, privateKey, "SHA256"))){
					String trade_state = resObj.getString("trade_state");
					if("SUCCESS".equals(trade_state)){
						result.put("returnCode", "0000");
						result.put("returnMsg", "成功");
						result.put("bankCardToken", resObj.get("swpaccid"));
					}else{
						result.put("returnCode", "0003");
						result.put("returnMsg", resObj.getString("trade_state_desc"));
					}
				}else{
					result.put("returnCode", "0003");
					result.put("returnMsg", "商户进件出参验签失败");
				}
			}else{
				result.put("returnCode", "0003");
				result.put("returnMsg", resObj.getString("message"));
			}
			result.put("orderCode", orderCode);
		}catch(Exception e){
			logger.info(e.getMessage(), e);
			result.put("returnCode", "0096");
			result.put("returnMsg", e.getMessage());
		}
		return result;
	}
	/**
	 * 绑卡快捷支付
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/quickPay/toCardPay")
	public JSONObject toCardPay(HttpServletRequest request,HttpServletResponse response){
		Map<String,String> inparam = new HashMap<String, String>();
		Enumeration<String> pNames=request.getParameterNames();
		while(pNames.hasMoreElements()){
		    String name=(String)pNames.nextElement();
		    String value=request.getParameter(name);
		    inparam.put(name, value);
		}
		logger.info("绑卡快捷支付下游入参[{}]",  JSONObject.fromObject(inparam).toString() );
		
		String orderNum = request.getParameter("orderNum");
		String memberCode = request.getParameter("memberCode");
		String payMoney = request.getParameter("payMoney");
		String bankCardToken = request.getParameter("bankCardToken");
		String callbackUrl = request.getParameter("callbackUrl");
		String signStr = request.getParameter("signStr");
		
		StringBuilder srcStr = new StringBuilder();
		JSONObject result = new JSONObject();
		
		if(bankCardToken == null || "".equals(bankCardToken)){
			result.put("returnCode", "0007");
			result.put("returnMsg", "绑定银行卡标识号缺失");
			return result;
		}
		srcStr.append("bankCardToken="+bankCardToken);
		
		if(callbackUrl == null || "".equals(callbackUrl)){
			result.put("returnCode", "0007");
			result.put("returnMsg", "支付结果通知回调地址缺失");
			return result;
		}
		srcStr.append("&callbackUrl="+callbackUrl);
		
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
		
		if(null == payMoney || !ValidateUtil.isDoubleT(payMoney) || Double.parseDouble(payMoney)<=0){
			result.put("returnCode", "0007");
			result.put("returnMsg", "支付金额输入不正确");
			return result;
		}
		srcStr.append("&payMoney="+payMoney);
		
		if(signStr == null || "".equals(signStr)){ 
			result.put("returnCode", "0007");
			result.put("returnMsg", "缺少签名信息");
			return result;
		}
		
		result = validMemberInfoToCardQuickPay(memberCode, orderNum,payMoney,bankCardToken,callbackUrl, "","","","","","","","",srcStr.toString(), signStr);
		
		return CommonUtil.signReturn(result);
	}
	
	public JSONObject validMemberInfoToCardQuickPay(String memberCode,String orderNum,String payMoney,String bankCardToken,String callbackUrl,String accountType,String bankAccount,String tel,String bankCvv,String bankYxq,String accountName,String certNbr,String tradeRate,String signOrginalStr,String signedStr){
		JSONObject result = new JSONObject();
		try{
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
			
			SysOfficeExample sysOfficeExample = new SysOfficeExample();
			sysOfficeExample.or().andIdEqualTo(epayCodeList.get(0).getOfficeId()).andAgtTypeEqualTo("3");
			List<SysOffice> sysOfficeList = sysOfficeService.selectByExample(sysOfficeExample);
			if(null == sysOfficeList || sysOfficeList.size() == 0){
				result.put("returnCode", "0008");
				result.put("returnMsg", "该商户机构信息不完整，请确认后重试");
				return result;
			}
			
			String routeCode = memberInfo.getWxRouteId();
			if(!routeCode.equals(RouteCodeConstant.TLKJ_ROUTE_CODE)){
				if(accountName == null || "".equals(accountName)){
					result.put("returnCode", "0007");
					result.put("returnMsg", "账户名称缺失");
					return CommonUtil.signReturn(result);
				}
				if("2".equals(accountType)){//信用卡
					if(bankCvv == null || "".equals(bankCvv)){
						result.put("returnCode", "0007");
						result.put("returnMsg", "账户类型为信用卡，卡末三位缺失");
						return CommonUtil.signReturn(result);
					}
					if(bankYxq == null || "".equals(bankYxq)){
						result.put("returnCode", "0007");
						result.put("returnMsg", "账户类型为信用卡，卡有效期缺失");
						return CommonUtil.signReturn(result);
					}
				}
				if(certNbr == null || "".equals(certNbr)){
					result.put("returnCode", "0007");
					result.put("returnMsg", "身份证号码缺失");
					return result;
				}
				
				if(tel == null || "".equals(tel)){
					result.put("returnCode", "0007");
					result.put("returnMsg", "手机号缺失");
					return CommonUtil.signReturn(result);
				}
			}else{
				if(tradeRate == null || "".equals(tradeRate)){
					result.put("returnCode", "0007");
					result.put("returnMsg", "交易费率缺失");
					return CommonUtil.signReturn(result);
				}
			}
			
			if(!RouteCodeConstant.ML_ROUTE_CODE.equals(routeCode)&&!RouteCodeConstant.TLKJ_ROUTE_CODE.equals(routeCode)){
				MemberBindAccDtlExample memberBindAccDtlExample = new MemberBindAccDtlExample();
				memberBindAccDtlExample.or().andMemberIdEqualTo(memberInfo.getId()).andBankCardTokenEqualTo(bankCardToken).andRespCodeEqualTo("0").andDelFlagEqualTo("0");
				List<MemberBindAccDtl> memberBindAccDtlList = memberBindAccDtlService.selectByExample(memberBindAccDtlExample);
				if(null == memberBindAccDtlList || memberBindAccDtlList.size() == 0){
					result.put("returnCode", "0008");
					result.put("returnMsg", "对不起，绑定银行卡标识号不存在");
					return result;
				}
			}
			
			PayResultNoticeExample payResultNoticeExample = new PayResultNoticeExample();
			payResultNoticeExample.or().andMemberCodeEqualTo(memberCode).andOrderNumOuterEqualTo(orderNum).andStatusNotEqualTo("1");
			List<PayResultNotice> PayResultNoticeList = payResultNoticeService.selectByExample(payResultNoticeExample);
			if(null != PayResultNoticeList && PayResultNoticeList.size() > 0){
				result.put("returnCode", "0002");
				result.put("returnMsg", "该笔订单已支付，请勿重复支付");
				return result;
			}
			
			MemberPayTypeExample memberPayTypeExample = new MemberPayTypeExample();
			memberPayTypeExample.createCriteria().andMemberIdEqualTo(memberInfo.getId()).andPayMethodEqualTo(PayTypeConstant.PAY_METHOD_YL).andPayTypeEqualTo(PayTypeConstant.PAY_TYPE_KJ).andDelFlagEqualTo("0");
			List<MemberPayType> memberPayTypeList =  memberPayTypeService.selectByExample(memberPayTypeExample);
			if(memberPayTypeList==null || memberPayTypeList.size()==0){
				result.put("returnCode", "0008");
				result.put("returnMsg", "对不起，该商户未开通快捷支付的权限");
				return result;
			}
			MemberPayType memberPayType = memberPayTypeList.get(0);
			
			MemberMerchantCodeExample memberMerchantCodeExample = new MemberMerchantCodeExample();
			memberMerchantCodeExample.createCriteria().andMemberIdEqualTo(memberInfo.getId()).andRouteCodeEqualTo(routeCode).andDelFlagEqualTo("0");
			List<MemberMerchantCode> merchantCodes = memberMerchantCodeService.selectByExample(memberMerchantCodeExample);
			if (merchantCodes == null || merchantCodes.size() != 1) {
				result.put("returnCode", "0008");
				result.put("returnMsg", "对不起，该商户未开通快捷支付的权限");
				return result;
			}
			MemberMerchantCode merchantCode = merchantCodes.get(0);
			if (StringUtils.isBlank(merchantCode.getKjMerchantCode())) {
				result.put("returnCode", "0008");
				result.put("returnMsg", "对不起，该商户未开通快捷支付的权限");
				return result;
			}
			
			SysOffice sysOffice = sysOfficeList.get(0);
			if(!EpaySignUtil.checksign(sysOffice.getPublicKeyRsa(), signOrginalStr, signedStr)){
				result.put("returnCode", "0004");
				result.put("returnMsg", "签名校验错误，请检查签名参数是否正确");
				return result;
			}
		
			
			if(RouteCodeConstant.YS_ROUTE_CODE.equals(routeCode)){
				memberInfo.setSettleType("0");
				result = ysQuickPay("3",memberInfo,merchantCode,orderNum,payMoney,callbackUrl,bankCardToken,routeCode);
			}else if(RouteCodeConstant.WW_ROUTE_CODE.equals(routeCode)){
				memberInfo.setSettleType("0");
				result = wwQuickPay("3",memberInfo,merchantCode,orderNum,payMoney,callbackUrl,bankCardToken,routeCode);
			}else if(RouteCodeConstant.ML_ROUTE_CODE.equals(routeCode)){
				memberInfo.setSettleType("0");
				result = mlCardQuickPay("3",memberInfo,merchantCode,memberPayType,orderNum,payMoney,callbackUrl,routeCode,accountType,bankAccount,tel,bankCvv,bankYxq,accountName,certNbr);
			}else if(RouteCodeConstant.TLKJ_ROUTE_CODE.equals(routeCode)){
				memberInfo.setSettleType("0");
				if(Double.valueOf(tradeRate)<memberPayType.getT0TradeRate().doubleValue()){
					result.put("returnCode", "4004");
					result.put("returnMsg", "交易费率小于商户最低费率");
					return result;
				}
				
				result = tlkjCardQuickPay("3",memberInfo,merchantCode,memberPayType,orderNum,payMoney,callbackUrl,routeCode,accountType,bankAccount,tel,bankCvv,bankYxq,accountName,certNbr,tradeRate);
			}
		}catch(Exception e){
			logger.info(e.getMessage());
			result.put("returnCode", "0096");
			result.put("returnMsg", e.getMessage());
		}
		return result;
	}
	
	public JSONObject ysQuickPay(String platformType,MemberInfo memberInfo,MemberMerchantCode merchantCode,String orderNum,String payMoney,String callbackUrl,String bankCardToken,String routeCode) {
		JSONObject result = new JSONObject();
		try {
			String merCode = merchantCode.getKjMerchantCode();
			String orderCode = CommonUtil.getOrderCode();
			String payType = "9";
			
	        DebitNote debitNote = new DebitNote();
			debitNote.setCreateDate(new Date());
			debitNote.setMemberId(memberInfo.getId());
			debitNote.setMoney(new BigDecimal(payMoney));
			debitNote.setOrderCode(orderCode);
			debitNote.setOrderNumOuter(orderNum);
			debitNote.setRouteId(routeCode);
			debitNote.setStatus("0");
			debitNote.setTxnMethod(PayTypeConstant.PAY_METHOD_YL);
			debitNote.setTxnType(payType);
			debitNote.setMerchantCode(merCode);
			debitNote.setMemberCode(memberInfo.getWxMemberCode());
			debitNote.setSettleType(memberInfo.getSettleType());
			if("0".equals(memberInfo.getSettleType())){
				debitNote.setTradeRate(merchantCode.getKjT0TradeRate());
			}else{
				debitNote.setTradeRate(merchantCode.getKjT1TradeRate());
			}
			
			String configName = "SINGLE_MIN_"+routeCode+"_"+PayTypeConstant.PAY_METHOD_YL+"_"+PayTypeConstant.PAY_TYPE_KJ;
			JSONObject checkResult = commonUtilService.checkMinMoney(configName, new BigDecimal(payMoney));
			if(null != checkResult){
				debitNote.setStatus("5");
				debitNoteService.insertSelective(debitNote);
				return checkResult;
			}
			
			configName = "SINGLE_LIMIT_"+routeCode+"_"+PayTypeConstant.PAY_METHOD_YL+"_"+PayTypeConstant.PAY_TYPE_KJ;
			JSONObject limitResult = commonUtilService.checkLimitMoney(configName, new BigDecimal(payMoney));
			if(null != limitResult){
				debitNote.setStatus("4");
				debitNoteService.insertSelective(debitNote);
				return limitResult;
			}
			
			debitNoteService.insertSelective(debitNote);
			
			PayResultNotice payResultNotice = new PayResultNotice();
			payResultNotice.setOrderCode(debitNote.getOrderCode());
			payResultNotice.setOrderNumOuter(debitNote.getOrderNumOuter());
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
			
			String serverUrl = YSConfig.msServerUrl+"/swp/dh/token_consume.do";
			String amount = String.valueOf((new BigDecimal(payMoney)).multiply(new BigDecimal(100)).intValue());
			String callBack = SysConfig.serverUrl + "/quickPay/ysPayNotify";
			String privateKey = YSConfig.privateKey;
			
			Map<String,String> param = new HashMap<String, String>();
			param.put("sp_id", YSConfig.orgNo);
			param.put("mch_id", YSConfig.defaultMerNoQpay);
			param.put("out_trade_no", orderCode);
			param.put("swpaccid",bankCardToken);
			param.put("total_amt", amount);
			param.put("notifyurl", callBack);
			
			Date t = new Date();
			Calendar cal = Calendar.getInstance();
			cal.setTime(t);
			long sys_timestamp = cal.getTimeInMillis();
			param.put("timestamp", String.valueOf(sys_timestamp));
			
			String srcStr = StringUtil.orderedKey(param)+"&key="+privateKey;
			String sign = SwpHashUtil.getSign(srcStr, privateKey, "SHA256");
			String paramStr = StringUtil.orderedKey(param) + "&sign="+sign;
			logger.info("易生快捷支付参数[{}]",paramStr );

			HttpResponse httpResponse =HttpUtils.doPost(serverUrl, "", paramStr, "application/x-www-form-urlencoded; charset=UTF-8");
			String respStr = EntityUtils.toString(httpResponse.getEntity());
			logger.info("易生快捷支付返回报文[{}]", new Object[] { respStr });
			
			JSONObject resObj = JSONObject.fromObject(respStr);
			String code = resObj.getString("status");
			String resultMsg = "";
			boolean flag = false;
			if("SUCCESS".equals(code)){
				String resSign = resObj.getString("sign");
				resObj.remove("sign");
				srcStr = StringUtil.orderedKey(resObj)+"&key="+privateKey;
				if(resSign.equals(SwpHashUtil.getSign(srcStr, privateKey, "SHA256"))){
					String trade_state = resObj.getString("trade_state");
					if("SUCCESS".equals(trade_state)||"PROCESSING".equals(trade_state)){
						result.put("returnCode", "0000");
						result.put("returnMsg", "成功");
						flag = true;
					}else{
						resultMsg = resObj.getString("trade_state_desc");
					}
				}else{
					resultMsg = "快捷支付出参验签失败";
				}
			}else{
				resultMsg = resObj.getString("message");
			}
			if(!flag){
				DebitNoteExample debitNoteExample = new DebitNoteExample();
				debitNoteExample.createCriteria().andOrderCodeEqualTo(orderCode);
				List<DebitNote> debitNotes = debitNoteService.selectByExample(debitNoteExample);
				if (debitNotes != null && debitNotes.size() > 0) {
					DebitNote debitNote_1 = debitNotes.get(0);
					debitNote_1.setStatus("2");
					debitNote_1.setUpdateDate(new Date());
					if(!"".equals(resultMsg)){
						debitNote_1.setRespMsg(resultMsg.length()>250?resultMsg.substring(0, 250):resultMsg);
					}
					debitNoteService.updateByPrimaryKey(debitNote_1);
				}
				result.put("returnCode", "0003");
				result.put("returnMsg", resultMsg);
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			result.put("returnCode", "0096");
			result.put("returnMsg", e.getMessage());
			return result;
		}
		return result;
	}

	
	/**
	 * 易生快捷支付回调
	 * @param request
	 * @param response
	 */
	@RequestMapping("/quickPay/ysPayNotify")
	public void ysPayNotify(HttpServletRequest request,HttpServletResponse response) {
		try {
			Map<String,String> param = new HashMap<String, String>();
			Enumeration pNames=request.getParameterNames();
			while(pNames.hasMoreElements()){
			    String name=(String)pNames.nextElement();
			    String value=request.getParameter(name);
			    param.put(name, value);
			}
			logger.info("ysPayNotify回调返回报文[{}]",  JSONObject.fromObject(param).toString() );
			String out_trade_no = param.get("out_trade_no");
			String sys_trade_no = param.get("sys_trade_no");
			String trade_state = param.get("trade_state");
			String total_fee = param.get("total_fee");
			String sign = param.get("sign");
			param.remove("sign");
			String privateKey = YSConfig.privateKey;
			String srcStr = StringUtil.orderedKey(param)+"&key="+privateKey;
			if(!sign.equals(SwpHashUtil.getSign(srcStr, privateKey, "SHA256"))){
				logger.info("验证签名不通过");
                response.getWriter().write("FAIL");
        		return;
			}
			
			String reqMsgId = out_trade_no;
        	
        	TradeDetailExample tradeDetailExample = new TradeDetailExample();
        	tradeDetailExample.or().andOrderCodeEqualTo(reqMsgId);
        	List<TradeDetail> tradeDetailList = tradeDetailService.selectByExample(tradeDetailExample);
        	if(tradeDetailList!=null && tradeDetailList.size()>0){
        		response.getWriter().write("SUCCESS");
        		return;
        	}
        	
        	DebitNoteExample debitNoteExample = new DebitNoteExample();
			debitNoteExample.createCriteria().andOrderCodeEqualTo(reqMsgId);
			List<DebitNote> debitNotes_tmp = debitNoteService.selectByExample(debitNoteExample);
			if (debitNotes_tmp == null || debitNotes_tmp.size() == 0) {
				logger.info("订单不存在");
                response.getWriter().write("FAIL");
        		return;
				
			}
			DebitNote debitNote = debitNotes_tmp.get(0);
        	
			
        	
        	String result_message = "";
        	MsResultNoticeExample msResultNoticeExample = new MsResultNoticeExample();
			msResultNoticeExample.or().andOrderCodeEqualTo(reqMsgId);
			List<MsResultNotice> msResultNoticeList = msResultNoticeService.selectByExample(msResultNoticeExample);
			if(null == msResultNoticeList || msResultNoticeList.size() == 0){
				if (debitNote != null ) {
					debitNote.setRespCode(trade_state);
					debitNote.setRespMsg(result_message);
					
					//新增一条交易明细记录
					TradeDetail tradeDetail=new TradeDetail();
					tradeDetail.setTxnDate(DateUtil.getDateFormat(new Date(), "yyyyMMdd"));
					tradeDetail.setMemberId(debitNote.getMemberId());
					tradeDetail.setMerchantCode(debitNote.getMerchantCode());
					
					tradeDetail.setMemberCode(debitNote.getMemberCode());
					tradeDetail.setMoney(debitNote.getMoney());
					tradeDetail.setPayTime(DateUtil.getDateTimeStr(debitNote.getCreateDate()));
					tradeDetail.setPtSerialNo(debitNote.getOrderCode());
					tradeDetail.setOrderCode(debitNote.getOrderCode());
					tradeDetail.setChannelNo(sys_trade_no);
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
					if ("SUCCESS".equals(trade_state)) {
						debitNote.setStatus("1");
						tradeDetail.setRespType("S");
						tradeDetail.setRespCode("000000");
					}else{
						debitNote.setStatus("2");
						tradeDetail.setRespType("E");
						tradeDetail.setRespCode(trade_state);
					}
					debitNote.setUpdateDate(new Date());
					debitNoteService.updateByPrimaryKey(debitNote);
					
					MsResultNotice msResultNotice = new MsResultNotice();
					msResultNotice.setMoney(new BigDecimal(total_fee).divide(new BigDecimal(100)));
					msResultNotice.setOrderCode(reqMsgId);
					msResultNotice.setPtSerialNo(sys_trade_no);
					
					//SimpleDateFormat dateSimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					msResultNotice.setRespDate(DateUtil.getDateTimeStr(new Date()));
					msResultNotice.setRespType("");
					msResultNotice.setRespCode(trade_state);
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
						if ("SUCCESS".equals(trade_state)) {
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
					if ("SUCCESS".equals(trade_state)) {
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
			response.getWriter().write("SUCCESS");
		} catch (IOException e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}

	}
	
	
	//微微快捷绑卡
	public JSONObject bindCardWw(MemberInfo memberInfo,String routeCode,String bankAccount,String tel,String bankCvv,String bankYxq,String bankCode,String accountName){
		JSONObject result = new JSONObject();
		try{
			String orderCode = CommonUtil.getOrderCode();
			String serverUrl = WWConfig.msServerUrl+"/quickPay/bindCard";
			String privateKey = WWConfig.privateKey;
			
			
			MemberMerchantCodeExample memberMerchantCodeExample = new MemberMerchantCodeExample();
			memberMerchantCodeExample.createCriteria().andMemberIdEqualTo(memberInfo.getId()).andRouteCodeEqualTo(routeCode).andDelFlagEqualTo("0");
			
			List<MemberMerchantCode> merchantCodes = memberMerchantCodeService.selectByExample(memberMerchantCodeExample);
			if (merchantCodes == null || merchantCodes.size() != 1) {
				result.put("returnCode", "0008");
				result.put("returnMsg", "对不起，商户编码不存在");
				return result;
			}
			MemberMerchantCode merchantCode = merchantCodes.get(0);
			
			Map<String,String> param = new HashMap<String, String>();
			param.put("orderNum", orderCode);
			param.put("memberCode",merchantCode.getKjMerchantCode() );
			param.put("accountType", "2");
			param.put("bankAccount", bankAccount);
			param.put("accountName", accountName);
			param.put("tel", tel);
			param.put("bankCvv", bankCvv);
			param.put("bankYxq", bankYxq);
			param.put("bankCode", bankCode);
			
			String srcStr = StringUtil.orderedKey(param);
			String sign = EpaySignUtil.sign(privateKey, srcStr);
			param.put("signStr", sign);
			logger.info("微微快捷绑卡参数[{}]",JSONObject.fromObject(param).toString() );
			
			List<NameValuePair> nvps = new LinkedList<NameValuePair>();
			List<String> keys = new ArrayList<String>(param.keySet());
			for (int i = 0; i < keys.size(); i++) {
				String name=(String) keys.get(i);
				String value=(String) param.get(name);
				if(value!=null && !"".equals(value)){
					nvps.add(new BasicNameValuePair(name, value));
				}
			}
			
			byte[] b = HttpClient4Util.getInstance().doPost(serverUrl, null, nvps);
			String respStr = new String(b, "utf-8");
			JSONObject resObj = JSONObject.fromObject(respStr);
			logger.info("微微快捷绑卡返回报文[{}]", new Object[] { respStr });
			
			String code = resObj.getString("returnCode");
			String resSign = resObj.getString("signStr");
			resObj.remove("signStr");
			srcStr = StringUtil.orderedKey(resObj);
			if(EpaySignUtil.checksign(WWConfig.platPublicKey, srcStr, resSign)){
				if("0000".equals(code)){
					result.put("returnCode", "0000");
					result.put("returnMsg", "成功");
					result.put("bankCardToken", resObj.get("bankCardToken"));
				}else{
					result.put("returnCode", "0003");
					result.put("returnMsg", resObj.getString("returnMsg"));
				}
			}else{
				result.put("returnCode", "0003");
				result.put("returnMsg", "快捷费率修改出参验签失败");
			}
			
			result.put("orderCode", orderCode);
		}catch(Exception e){
			logger.info(e.getMessage(), e);
			result.put("returnCode", "0096");
			result.put("returnMsg", e.getMessage());
		}
		return result;
	}
	
	public JSONObject wwQuickPay(String platformType,MemberInfo memberInfo,MemberMerchantCode merchantCode,String orderNum,String payMoney,String callbackUrl,String bankCardToken,String routeCode) {
		JSONObject result = new JSONObject();
		try {
			String merCode = merchantCode.getKjMerchantCode();
			String orderCode = CommonUtil.getOrderCode();
			String payType = "9";
			
	        DebitNote debitNote = new DebitNote();
			debitNote.setCreateDate(new Date());
			debitNote.setMemberId(memberInfo.getId());
			debitNote.setMoney(new BigDecimal(payMoney));
			debitNote.setOrderCode(orderCode);
			debitNote.setOrderNumOuter(orderNum);
			debitNote.setRouteId(routeCode);
			debitNote.setStatus("0");
			debitNote.setTxnMethod(PayTypeConstant.PAY_METHOD_YL);
			debitNote.setTxnType(payType);
			debitNote.setMerchantCode(merCode);
			debitNote.setMemberCode(memberInfo.getWxMemberCode());
			debitNote.setSettleType(memberInfo.getSettleType());
			if("0".equals(memberInfo.getSettleType())){
				debitNote.setTradeRate(merchantCode.getKjT0TradeRate());
			}else{
				debitNote.setTradeRate(merchantCode.getKjT1TradeRate());
			}
			
			String configName = "SINGLE_MIN_"+routeCode+"_"+PayTypeConstant.PAY_METHOD_YL+"_"+PayTypeConstant.PAY_TYPE_KJ;
			JSONObject checkResult = commonUtilService.checkMinMoney(configName, new BigDecimal(payMoney));
			if(null != checkResult){
				debitNote.setStatus("5");
				debitNoteService.insertSelective(debitNote);
				return checkResult;
			}
			
			configName = "SINGLE_LIMIT_"+routeCode+"_"+PayTypeConstant.PAY_METHOD_YL+"_"+PayTypeConstant.PAY_TYPE_KJ;
			JSONObject limitResult = commonUtilService.checkLimitMoney(configName, new BigDecimal(payMoney));
			if(null != limitResult){
				debitNote.setStatus("4");
				debitNoteService.insertSelective(debitNote);
				return limitResult;
			}
			
			debitNoteService.insertSelective(debitNote);
			
			PayResultNotice payResultNotice = new PayResultNotice();
			payResultNotice.setOrderCode(debitNote.getOrderCode());
			payResultNotice.setOrderNumOuter(debitNote.getOrderNumOuter());
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
			
			String serverUrl = WWConfig.msServerUrl+"/quickPay/toCardPay";
			String callBack = SysConfig.serverUrl + "/debitNote/wwH5PayNotify";
			String privateKey = WWConfig.privateKey;
			
			Map<String,String> param = new HashMap<String, String>();
			param.put("memberCode", merCode);
			param.put("orderNum", orderCode);
			param.put("bankCardToken",bankCardToken);
			param.put("payMoney", payMoney);
			param.put("callbackUrl", callBack);
			
			String srcStr = StringUtil.orderedKey(param);
			String sign = EpaySignUtil.sign(privateKey, srcStr);
			param.put("signStr", sign);
			logger.info("微微快捷支付参数[{}]",JSONObject.fromObject(param).toString() );
			
			List<NameValuePair> nvps = new LinkedList<NameValuePair>();
			List<String> keys = new ArrayList<String>(param.keySet());
			for (int i = 0; i < keys.size(); i++) {
				String name=(String) keys.get(i);
				String value=(String) param.get(name);
				if(value!=null && !"".equals(value)){
					nvps.add(new BasicNameValuePair(name, value));
				}
			}
			
			byte[] b = HttpClient4Util.getInstance().doPost(serverUrl, null, nvps);
			String respStr = new String(b, "utf-8");
			JSONObject resObj = JSONObject.fromObject(respStr);

			logger.info("微微快捷支付返回报文[{}]", new Object[] { respStr });
			
			String resultMsg = "";
			boolean flag = false;
			String code = resObj.getString("returnCode");
			String resSign = resObj.getString("signStr");
			resObj.remove("signStr");
			srcStr = StringUtil.orderedKey(resObj);
			if(EpaySignUtil.checksign(WWConfig.platPublicKey, srcStr, resSign)){
				if("0000".equals(code)){
					result.put("returnCode", "0000");
					result.put("returnMsg", "成功");
					flag = true;
				}else{
					resultMsg = resObj.getString("returnMsg");
				}
			}else{
				resultMsg = "快捷支付出参验签失败";
			}
			
			if(!flag){
				DebitNoteExample debitNoteExample = new DebitNoteExample();
				debitNoteExample.createCriteria().andOrderCodeEqualTo(orderCode);
				List<DebitNote> debitNotes = debitNoteService.selectByExample(debitNoteExample);
				if (debitNotes != null && debitNotes.size() > 0) {
					DebitNote debitNote_1 = debitNotes.get(0);
					debitNote_1.setStatus("2");
					debitNote_1.setUpdateDate(new Date());
					if(!"".equals(resultMsg)){
						debitNote_1.setRespMsg(resultMsg.length()>250?resultMsg.substring(0, 250):resultMsg);
					}
					debitNoteService.updateByPrimaryKey(debitNote_1);
				}
				result.put("returnCode", "0003");
				result.put("returnMsg", resultMsg);
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			result.put("returnCode", "0096");
			result.put("returnMsg", e.getMessage());
			return result;
		}
		return result;
	}
	
	
	@ResponseBody
	@RequestMapping("/api/quickPayH5/toPay")
	public JSONObject toPayH5(HttpServletRequest request,HttpServletResponse response){
		JSONObject requestPRM = (JSONObject) request.getAttribute("requestPRM");
		JSONObject reqDataJson = requestPRM.getJSONObject("reqData");// 获取请求参数
		logger.info("请求参数==="+reqDataJson.toString());
		String payMoney = "";
		if(reqDataJson.containsKey("payMoney")){
			payMoney = reqDataJson.getString("payMoney");
		}
		String orderNum = "";
		if(reqDataJson.containsKey("orderNum")){
			orderNum = reqDataJson.getString("orderNum");
		}
		String memberCode = "";
		if(reqDataJson.containsKey("memberCode")){
			memberCode = reqDataJson.getString("memberCode");
		}
		String callbackUrl = "";
		if(reqDataJson.containsKey("callbackUrl")){
			callbackUrl = reqDataJson.getString("callbackUrl");
		}
		
		String goodsName = "";
		if(reqDataJson.containsKey("goodsName")){
			goodsName = reqDataJson.getString("goodsName");
		}
		
		String signStr = "";
		if(reqDataJson.containsKey("signStr")){
			signStr = reqDataJson.getString("signStr");
		}
		
		String accountName = "";
		if(reqDataJson.containsKey("accountName")){
			accountName = reqDataJson.getString("accountName");
		}
		
		String bankAccount = "";
		if(reqDataJson.containsKey("bankAccount")){
			bankAccount = reqDataJson.getString("bankAccount");
		}
		
		String certNo = "";
		if(reqDataJson.containsKey("certNo")){
			certNo = reqDataJson.getString("certNo");
		}
		
		String tel = "";
		if(reqDataJson.containsKey("tel")){
			tel = reqDataJson.getString("tel");
		}
		
		String ip = "";
		if(reqDataJson.containsKey("ip")){
			ip = reqDataJson.getString("ip");
		}
		
		StringBuilder srcStr = new StringBuilder();
		JSONObject result = new JSONObject();
		if(null == payMoney || !ValidateUtil.isDoubleT(payMoney) || Double.parseDouble(payMoney)<=0){
			result.put("returnCode", "0005");
			result.put("returnMsg", "支付金额输入不正确");
			return result;
		}
		
		if(accountName!=null && !"".equals(accountName)){
			srcStr.append("accountName="+accountName+"&");
		}
		
		if(bankAccount!=null && !"".equals(bankAccount)){
			srcStr.append("bankAccount="+bankAccount+"&");
		}
		
		if(callbackUrl == null || "".equals(callbackUrl)){
			result.put("returnCode", "0007");
			result.put("returnMsg", "平台支付结果通知回调地址缺失");
			return result;
		}
		srcStr.append("callbackUrl="+callbackUrl);
		
		if(certNo!=null && !"".equals(certNo)){
			srcStr.append("&certNo="+certNo);
		}
		
		if(goodsName == null || "".equals(goodsName)){
			result.put("returnCode", "0007");
			result.put("returnMsg", "商品名称缺失");
			return result;
		}
		
		srcStr.append("&goodsName="+goodsName);
		
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
		
		if(tel!=null && !"".equals(tel)){
			srcStr.append("&tel="+tel);
		}
		
		if(signStr == null || "".equals(signStr)){ 
			result.put("returnCode", "0007");
			result.put("returnMsg", "缺少签名信息");
			return result;
		}
		
		result = validMemberInfoForQuickPay(memberCode, orderNum, payMoney, "",accountName, bankAccount, "", "", certNo, tel, goodsName, callbackUrl, srcStr.toString(), signStr,ip);
		return result;
	}
	
	//环迅快捷支付
	public JSONObject quickPayHx(String platformType,MemberInfo memberInfo,String payMoney,String orderNumOuter,String callbackUrl,MemberMerchantCode merchantCode,String routeCode,String bankCode,String goodsName) {
		JSONObject result = new JSONObject();
		try {
			String merCode = merchantCode.getKjMerchantCode();
			// 插入一条收款记录
			String orderCode = CommonUtil.getOrderCode();
			
			DebitNote debitNote = new DebitNote();
			debitNote.setCreateDate(new Date());
			debitNote.setMemberId(memberInfo.getId());
			debitNote.setMoney(new BigDecimal(payMoney));
			debitNote.setOrderCode(orderCode);
			debitNote.setOrderNumOuter(orderNumOuter);
			debitNote.setRouteId(RouteCodeConstant.HX_ROUTE_CODE);
			debitNote.setStatus("0");
			debitNote.setTxnMethod(PayTypeConstant.PAY_METHOD_YL);
			debitNote.setTxnType("9");
			debitNote.setMemberCode(memberInfo.getWxMemberCode());
			debitNote.setMerchantCode(merCode);
			
			debitNote.setSettleType(memberInfo.getSettleType());
			if("0".equals(memberInfo.getSettleType())){
				debitNote.setTradeRate(merchantCode.getKjT0TradeRate());
			}else{
				debitNote.setTradeRate(merchantCode.getKjT1TradeRate());
			}
			
			String configName = "SINGLE_MIN_1014_005_KJ";
			JSONObject checkResult = commonUtilService.checkMinMoney(configName, new BigDecimal(payMoney));
			if(null != checkResult){
				debitNote.setStatus("5");
				debitNoteService.insertSelective(debitNote);
				return checkResult;
			}
			
			configName = "SINGLE_LIMIT_1014_005_KJ";
			JSONObject limitResult = commonUtilService.checkLimitMoney(configName, new BigDecimal(payMoney));
			if(null != limitResult){
				debitNote.setStatus("4");
				debitNoteService.insertSelective(debitNote);
				return limitResult;
			}
			
			
			debitNoteService.insertSelective(debitNote);
			
			PayResultNotice payResultNotice = new PayResultNotice();
			payResultNotice.setOrderCode(debitNote.getOrderCode());
			payResultNotice.setOrderNumOuter(orderNumOuter);
			payResultNotice.setPayMoney(debitNote.getMoney());
			payResultNotice.setMemberCode(memberInfo.getCode());
			payResultNotice.setPayType("9");
			
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
			
			String callBack = SysConfig.serverUrl + "/bankPay/hxPayNotify";
			
			if(StringUtil.isEmpty(goodsName)){
				goodsName = memberInfo.getName()+" 收款";
			}
			
			result.put("payUrl", HXConfig.wapPayURL);
			result.put("privateKey", HXConfig.privateKey);
			result.put("merchantCode", merCode);
			result.put("merchantName", HXConfig.ipsMerName);
			result.put("merchantAccount", HXConfig.ipsMerAccount);
			result.put("callBack", callBack);
			result.put("frontUrl", HXConfig.frontUrl);
			result.put("goodsName", goodsName);
			result.put("bankCode", bankCode);
			result.put("orderCode", orderCode);
			result.put("returnCode", "0000");
			result.put("returnMsg", "成功");
			
        } catch (Exception e) {
			logger.error(e.getMessage());
			result.put("returnCode", "0096");
			result.put("returnMsg", e.getMessage());
			return result;
		}
		return result;
	}
	
	
	
	
	public JSONObject mlQuickPay(String platformType,MemberInfo memberInfo,MemberMerchantCode merchantCode,MemberPayType memberPayType,String payMoney,String orderNumOuter,String accountType,String accountName,String bankAccount,String bankCvv,String bankYxq,String certNo,String tel,String callbackUrl,String goodsName,String routeCode,String aisleType) {
		JSONObject result = new JSONObject();
		try {
			String payType = "9";
			String payTypeStr = PayTypeConstant.PAY_TYPE_KJ;
			String payMethod = PayTypeConstant.PAY_METHOD_YL;
			String merCode = merchantCode.getKjMerchantCode();
			
			
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
			debitNote.setTxnMethod(payMethod);
			debitNote.setTxnType(payType);
			debitNote.setMerchantCode(merCode);
			debitNote.setMemberCode(memberInfo.getWxMemberCode());
			debitNote.setSettleType(memberInfo.getSettleType());
			if("0".equals(memberInfo.getSettleType())){
				debitNote.setTradeRate(memberPayType.getT0TradeRate());
			}else{
				debitNote.setTradeRate(memberPayType.getT1TradeRate());
			}
			
			
			String configName = "SINGLE_MEMBER_LIMIT_"+memberInfo.getId()+"_"+payMethod+"_"+payTypeStr;
			JSONObject memResult = commonUtilService.checkLimitMoney(configName, new BigDecimal(payMoney));
			if(null != memResult){
				debitNote.setStatus("9");
				debitNoteService.insertSelective(debitNote);
				return memResult;
			} 
			
			configName = "SINGLE_MIN_"+routeCode+"_"+payMethod+"_"+payTypeStr;
			JSONObject checkResult = commonUtilService.checkMinMoney(configName, new BigDecimal(payMoney));
			if(null != checkResult){
				debitNote.setStatus("5");
				debitNoteService.insertSelective(debitNote);
				return checkResult;
			}
			
			configName = "SINGLE_LIMIT_"+routeCode+"_"+payMethod+"_"+payTypeStr;
			JSONObject limitResult = commonUtilService.checkLimitMoney(configName, new BigDecimal(payMoney));
			if(null != limitResult){
				debitNote.setStatus("4");
				debitNoteService.insertSelective(debitNote);
				return limitResult;
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
			
			String callBack = SysConfig.serverUrl + "/quickPay/mlPayNotify";
			
			String serverUrl = MLConfig.payServerUrl + "/servlet/QuickPay";
			
			String[] obj = merchantKey.getPrivateKeyPassword().split("#");
			
			Map<String,String> contextjson = new HashMap<String, String>();
	     	String amount = new DecimalFormat("0.00").format(Double.valueOf(payMoney));
	     	String orderTime = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
			contextjson.put("ORDER_ID",orderCode);//订单流水号
	     	contextjson.put("ORDER_TIME",orderTime);//
	     	contextjson.put("ORDER_AMT",amount);//消费金额
	     	contextjson.put("USER_TYPE", "02");
	     	contextjson.put("USER_ID", merCode);
	     	contextjson.put("SIGN_TYPE", "03");
	     	contextjson.put("BUS_CODE", "5015");
	     	contextjson.put("PAY_TYPE", "13");
	     	contextjson.put("CCT", "CNY");
	     	contextjson.put("ADD1",bankAccount);//银行卡号
	    
	     	contextjson.put("ID_NO",obj[2]);//绑定身份证号
	     	contextjson.put("PHONE_NO",tel);//绑定手机号码
	     	contextjson.put("BG_URL",callBack);//异步通知地址
	     	contextjson.put("PAGE_URL", SysConfig.frontUrl+"/payment/quickPayMlNotify");
	     	contextjson.put("SETT_ACCT_NO", obj[0]);//结算卡号
	     	contextjson.put("CARD_INST_NAME", obj[1]);//结算银行
	     	contextjson.put("NAME", obj[3]);
	     	//contextjson.put("goodsName",goodsName);//商品名称
	     	
	     	String srcStr = orderCode+amount+orderTime+"13"+"02"+merCode+"5015";
	     	//String srcStr = StringUtil.orderedKey(contextjson)+"&key="+merchantKey.getPrivateKey();
	     	System.out.println("加密源串："+srcStr);
			String sign1 = com.epay.scanposp.common.utils.ml.EncryptUtil.MD5(srcStr, 1).toUpperCase();
			System.out.println("第一次加密结果："+sign1);
			String sign = com.epay.scanposp.common.utils.ml.EncryptUtil.MD5(sign1+merchantKey.getPrivateKey(), 0).toUpperCase();
			System.out.println("第二次加密结果："+sign);
			contextjson.put("SIGN", sign); 
			
            List<String> keys = new ArrayList<String>(contextjson.keySet());
			for (int i = 0; i < keys.size(); i++) {
				String name=(String) keys.get(i);
				String value=(String) contextjson.get(name);
				result.put(name, value);
			}
            
            
            
			logger.info("米联快捷请求数据[{}]", new Object[] { contextjson.toString() });
			
			result.put("payUrl", serverUrl);
			result.put("returnCode", "0000");
			result.put("returnMsg", "成功");
	     	
	   } catch (Exception e) {
			logger.error(e.getMessage());
			result.put("returnCode", "0096");
			result.put("returnMsg", e.getMessage());
			return result;
		}
		return result;
	}
	
	/**
	 * 米联支付回调
	 * @param request
	 * @param response
	 */
	@RequestMapping("/quickPay/mlPayNotify")
	public void mlPayNotify(HttpServletRequest request,HttpServletResponse response) {
		try {
			Map<String,String> param = new HashMap<String, String>();
			Enumeration pNames=request.getParameterNames();
			while(pNames.hasMoreElements()){
			    String name=(String)pNames.nextElement();
			    String value=request.getParameter(name);
			    param.put(name, value);
			}
			
			logger.info("mlPayNotify回调返回报文[{}]",  JSONObject.fromObject(param).toString() );
			String orderId = param.get("ORDER_ID");
			String orderAmt = param.get("ORDER_AMT");
			String orderTime = param.get("ORDER_TIME");
			String userId = param.get("USER_ID");
			String busCode = param.get("BUS_CODE");
			String trade_state = param.get("RESP_CODE");
			String respDesc = param.get("RESP_DESC");
			String respSign = param.get("SIGN");
			
			String reqMsgId = orderId;
        	
        	TradeDetailExample tradeDetailExample = new TradeDetailExample();
        	tradeDetailExample.or().andOrderCodeEqualTo(reqMsgId);
        	List<TradeDetail> tradeDetailList = tradeDetailService.selectByExample(tradeDetailExample);
        	if(tradeDetailList!=null && tradeDetailList.size()>0){
        		response.getWriter().write("SUCCESS");
        		return;
        	}
        	
        	DebitNoteExample debitNoteExample = new DebitNoteExample();
			debitNoteExample.createCriteria().andOrderCodeEqualTo(reqMsgId);
			List<DebitNote> debitNotes_tmp = debitNoteService.selectByExample(debitNoteExample);
			if (debitNotes_tmp == null || debitNotes_tmp.size() == 0) {
				logger.info("订单不存在");
                response.getWriter().write("FAIL");
        		return;
				
			}
			DebitNote debitNote = debitNotes_tmp.get(0);
			
			MemberMerchantKeyExample memberMerchantKeyExample = new MemberMerchantKeyExample();
	        memberMerchantKeyExample.createCriteria().andRouteCodeEqualTo(debitNote.getRouteId()).andMerchantCodeEqualTo(debitNote.getMerchantCode()).andDelFlagEqualTo("0");
	        List<MemberMerchantKey> keyList = memberMerchantKeyService.selectByExample(memberMerchantKeyExample);
	        if(keyList == null || keyList.size()!=1){
	        	logger.info("商户私钥未配置");
                response.getWriter().write("FAIL");
        		return;
	        }
	        MemberMerchantKey merchantKey = keyList.get(0);
        	
	        String srcStr = orderId+orderAmt+orderTime+userId+busCode+trade_state+respDesc;
	     	//System.out.println("出参加密源串："+srcStr);
			String sign1 = com.epay.scanposp.common.utils.ml.EncryptUtil.MD5(srcStr, 1).toUpperCase();
			//System.out.println("出参第一次加密结果："+sign1);
			String sign = com.epay.scanposp.common.utils.ml.EncryptUtil.MD5(sign1+merchantKey.getPrivateKey(), 0).toUpperCase();
			//System.out.println("出参第二次加密结果："+sign);
			
			if(!sign.equals(respSign)){
				logger.info("验证签名不通过");
                response.getWriter().write("FAIL");
        		return;
			}
			
        	
        	String result_message = respDesc;
        	MsResultNoticeExample msResultNoticeExample = new MsResultNoticeExample();
			msResultNoticeExample.or().andOrderCodeEqualTo(reqMsgId);
			List<MsResultNotice> msResultNoticeList = msResultNoticeService.selectByExample(msResultNoticeExample);
			if(null == msResultNoticeList || msResultNoticeList.size() == 0){
				if (debitNote != null ) {
					debitNote.setRespCode(trade_state);
					debitNote.setRespMsg(result_message);
					
					//新增一条交易明细记录
					TradeDetail tradeDetail=new TradeDetail();
					tradeDetail.setTxnDate(DateUtil.getDateFormat(new Date(), "yyyyMMdd"));
					tradeDetail.setMemberId(debitNote.getMemberId());
					tradeDetail.setMerchantCode(debitNote.getMerchantCode());
					
					tradeDetail.setMemberCode(debitNote.getMemberCode());
					tradeDetail.setMoney(debitNote.getMoney());
					tradeDetail.setPayTime(DateUtil.getDateTimeStr(debitNote.getCreateDate()));
					tradeDetail.setPtSerialNo(debitNote.getOrderCode());
					tradeDetail.setOrderCode(debitNote.getOrderCode());
					tradeDetail.setChannelNo("");
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
					if ("0000".equals(trade_state)) {
						debitNote.setStatus("1");
						tradeDetail.setRespType("S");
						tradeDetail.setRespCode("000000");
					}else{
						debitNote.setStatus("2");
						tradeDetail.setRespType("E");
						tradeDetail.setRespCode(trade_state);
					}
					debitNote.setUpdateDate(new Date());
					debitNoteService.updateByPrimaryKey(debitNote);
					
					MsResultNotice msResultNotice = new MsResultNotice();
					msResultNotice.setMoney(new BigDecimal(orderAmt));
					msResultNotice.setOrderCode(reqMsgId);
					msResultNotice.setPtSerialNo("");
					
					//SimpleDateFormat dateSimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					msResultNotice.setRespDate(DateUtil.getDateTimeStr(new Date()));
					msResultNotice.setRespType("");
					msResultNotice.setRespCode(trade_state);
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
						if ("0000".equals(trade_state)) {
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
					if ("0000".equals(trade_state)) {
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
			response.getWriter().write("SUCCESS");
		} catch (IOException e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}

	}
	
	
	public JSONObject eskQuickPay(String platformType,MemberInfo memberInfo,MemberMerchantCode merchantCode,MemberPayType memberPayType,String payMoney,String orderNumOuter,String accountType,String accountName,String bankAccount,String bankCvv,String bankYxq,String certNo,String tel,String callbackUrl,String goodsName,String routeCode,String aisleType,String ip) {
		JSONObject result = new JSONObject();
		try {
			String payType = "9";
			String payTypeStr = PayTypeConstant.PAY_TYPE_KJ;
			String payMethod = PayTypeConstant.PAY_METHOD_YL;
			String merCode = merchantCode.getKjMerchantCode();
			
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
			debitNote.setTxnMethod(payMethod);
			debitNote.setTxnType(payType);
			debitNote.setMerchantCode(merCode);
			debitNote.setMemberCode(memberInfo.getWxMemberCode());
			debitNote.setSettleType(memberInfo.getSettleType());
			if("0".equals(memberInfo.getSettleType())){
				debitNote.setTradeRate(memberPayType.getT0TradeRate());
			}else{
				debitNote.setTradeRate(memberPayType.getT1TradeRate());
			}
			
			
			String configName = "SINGLE_MEMBER_LIMIT_"+memberInfo.getId()+"_"+payMethod+"_"+payTypeStr;
			JSONObject memResult = commonUtilService.checkLimitMoney(configName, new BigDecimal(payMoney));
			if(null != memResult){
				debitNote.setStatus("9");
				debitNoteService.insertSelective(debitNote);
				return memResult;
			} 
			
			configName = "SINGLE_MIN_"+routeCode+"_"+payMethod+"_"+payTypeStr;
			JSONObject checkResult = commonUtilService.checkMinMoney(configName, new BigDecimal(payMoney));
			if(null != checkResult){
				debitNote.setStatus("5");
				debitNoteService.insertSelective(debitNote);
				return checkResult;
			}
			
			configName = "SINGLE_LIMIT_"+routeCode+"_"+payMethod+"_"+payTypeStr;
			JSONObject limitResult = commonUtilService.checkLimitMoney(configName, new BigDecimal(payMoney));
			if(null != limitResult){
				debitNote.setStatus("4");
				debitNoteService.insertSelective(debitNote);
				return limitResult;
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
			String tranCode = "020";
			String charset = "utf-8";
			
			JSONObject reqData = new JSONObject();
			reqData.put("merchantCode", merchantCode.getKjMerchantCode());
			reqData.put("orderNumber", orderCode);
			reqData.put("tranCode", tranCode);
			reqData.put("aisleType", aisleType);
			reqData.put("totalAmount", payMoney);
			reqData.put("subject", memberInfo.getName() + " 收款");
			reqData.put("PayType", "2");
			reqData.put("callback", callBack);
			reqData.put("channelType", "07");
			reqData.put("body", memberInfo.getName() + " 收款");
			reqData.put("terminalId", ip);
			reqData.put("cardNo", bankAccount);
			reqData.put("successUrl", SysConfig.frontUrl+"/debitNote/payCallBack?orderCode="+orderCode);
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
				if(respJSONObject.containsKey("payUrl")&&!"".equals(respJSONObject.getString("payUrl"))){
					result.put("payUrl", respJSONObject.getString("payUrl"));
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
		} catch (Exception e) {
			logger.error(e.getMessage());
			result.put("returnCode", "0096");
			result.put("returnMsg", e.getMessage());
			return result;
		}
		return result;
	}
	
	//易生快捷绑卡
	public JSONObject bindCardMl(MemberInfo memberInfo,String routeCode,String bankAccount,String tel,String bankCvv,String bankYxq,String bankCode,String accountName,String certNbr){
		JSONObject result = new JSONObject();
		try{
			String serverUrl = MLConfig.payServerUrl + "/controller/pay";;
			
			MemberMerchantCodeExample memberMerchantCodeExample = new MemberMerchantCodeExample();
			memberMerchantCodeExample.createCriteria().andMemberIdEqualTo(memberInfo.getId()).andRouteCodeEqualTo(routeCode).andDelFlagEqualTo("0");
			
			List<MemberMerchantCode> merchantCodes = memberMerchantCodeService.selectByExample(memberMerchantCodeExample);
			if (merchantCodes == null || merchantCodes.size() != 1) {
				result.put("returnCode", "0008");
				result.put("returnMsg", "对不起，商户编码不存在");
				return result;
			}
			MemberMerchantCode merchantCode = merchantCodes.get(0);
			String merCode = merchantCode.getKjMerchantCode();
			
			MemberMerchantKeyExample memberMerchantKeyExample = new MemberMerchantKeyExample();
	        memberMerchantKeyExample.createCriteria().andRouteCodeEqualTo(routeCode).andMerchantCodeEqualTo(merCode).andDelFlagEqualTo("0");
	        List<MemberMerchantKey> keyList = memberMerchantKeyService.selectByExample(memberMerchantKeyExample);
	        if(keyList == null || keyList.size()!=1){
	            result.put("returnCode", "0008");
				result.put("returnMsg", "商户私钥未配置");
				return result;
	        }
	        MemberMerchantKey merchantKey = keyList.get(0);
	        
	        String orderCode = "X"+CommonUtil.getOrderCode();
	        String orderTime = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
			
	        String srcStr = orderCode+"02"+merCode+"1011"+"01"+bankAccount+tel;
	     	System.out.println("加密源串："+srcStr);
			String sign1 = com.epay.scanposp.common.utils.ml.EncryptUtil.MD5(srcStr, 1).toUpperCase();
			System.out.println("第一次加密结果："+sign1);
			String sign = com.epay.scanposp.common.utils.ml.EncryptUtil.MD5(sign1+merchantKey.getPrivateKey(), 0).toUpperCase();
			System.out.println("第二次加密结果："+sign);
			
			Map<String,String> param = new HashMap<String, String>();
			param.put("ORDER_ID", orderCode);
			param.put("ORDER_TIME", orderTime);
			param.put("USER_TYPE", "02");
			param.put("USER_ID", merCode);
			param.put("SIGN_TYPE", "03");
			param.put("BUS_CODE", "1011");
			param.put("CHECK_TYPE", "01");
			param.put("ACCT_NO", bankAccount);
			param.put("PHONE_NO", tel);
			param.put("ID_NO", certNbr);
			param.put("NAME", accountName);
			param.put("CVN2", bankCvv);
			param.put("VALID", bankYxq);
			param.put("PAGE_URL", SysConfig.frontUrl+"/payment/bindCardNotify?orderCode="+orderCode);
			param.put("SIGN", sign);
			
			logger.info("米联快捷绑卡参数[{}]",JSONObject.fromObject(param).toString() );
			
			List<NameValuePair> nvps = new LinkedList<NameValuePair>();
			List<String> keys = new ArrayList<String>(param.keySet());
			for (int i = 0; i < keys.size(); i++) {
				String name=(String) keys.get(i);
				String value=(String) param.get(name);
				if(value!=null && !"".equals(value)){
					nvps.add(new BasicNameValuePair(name, value));
				}
			}
            
            byte[] b = HttpClient4Util.getInstance().doPost(serverUrl, null, nvps);
			String respStr = new String(b, "UTF-8");
			logger.info("米联快捷绑卡返回报文[{}]", new Object[] { respStr });
			
			Document reqDoc = DocumentHelper.parseText(respStr);
			Element rootEl = reqDoc.getRootElement();
			
			String code = rootEl.elementText("RESP_CODE");
			if("0000".equals(code)||"00".equals(code)||"0100".equals(code)){
				result.put("returnCode", "0000");
				result.put("returnMsg", rootEl.elementText("RESP_DESC"));
				result.put("PAY_INFO", URLDecoder.decode(rootEl.elementText("PAY_INFO"), "UTF-8"));
			}else{
				result.put("returnCode", "0003");
				result.put("returnMsg", rootEl.elementText("RESP_DESC"));
			}
			
			result.put("orderCode", orderCode);
		}catch(Exception e){
			logger.info(e.getMessage(), e);
			result.put("returnCode", "0096");
			result.put("returnMsg", e.getMessage());
		}
		return result;
	}
	
	@ResponseBody
	@RequestMapping("/quickPay/queryBindCard")
	public JSONObject queryBindCard(HttpServletRequest request,HttpServletResponse response){
		JSONObject result = new JSONObject();
		try {
			Map<String,String> inparam = new HashMap<String, String>();
			Enumeration<String> pNames=request.getParameterNames();
			while(pNames.hasMoreElements()){
			    String name=(String)pNames.nextElement();
			    String value=request.getParameter(name);
			    inparam.put(name, value);
			}
			logger.info("商户绑卡查询下游入参[{}]",  JSONObject.fromObject(inparam).toString() );
			
			String memberCode = request.getParameter("memberCode");
			String bankAccount = request.getParameter("bankAccount");
			String signStr = request.getParameter("signStr");
			
			//待签名字符串
			Map<String,String> params = new HashMap<String,String>();
			if(bankAccount == null || "".equals(bankAccount)){
				result.put("returnCode", "0007");
				result.put("returnMsg", "银行卡号[bankAccount]缺失");
				return CommonUtil.signReturn(result);
			}
			params.put("bankAccount", bankAccount);
			
			if(memberCode == null || "".equals(memberCode)){
				result.put("returnCode", "0007");
				result.put("returnMsg", "商户编号[memberCode]缺失");
				return CommonUtil.signReturn(result);
			}
			params.put("memberCode", memberCode);
			
			if(signStr == null || "".equals(signStr)){
				result.put("returnCode", "0007");
				result.put("returnMsg", "签名[signStr]缺失");
				return CommonUtil.signReturn(result);
			}
			
			String srcStr = StringUtil.orderedKey(params);
			
			MemberInfoExample memberInfoExample = new MemberInfoExample();
			memberInfoExample.or().andCodeEqualTo(memberCode).andDelFlagEqualTo("0");
			List<MemberInfo> memberInfoList = memberInfoService.selectByExample(memberInfoExample);
			if(null == memberInfoList || memberInfoList.size() == 0){
				result.put("returnCode", "0001");
				result.put("returnMsg", "商户信息不存在");
				return CommonUtil.signReturn(result);
			}
			MemberInfo memberInfo = memberInfoList.get(0);
			if(!"0".equals(memberInfo.getStatus())){
				if("4".equals(memberInfo.getStatus())){
					result.put("returnCode", "0008");
					result.put("returnMsg", "该商户未进行认证，暂时无法交易");
					return CommonUtil.signReturn(result);
				}
				result.put("returnCode", "0008");
				result.put("returnMsg", "对不起，该商户暂不可用");
				return CommonUtil.signReturn(result);
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
				return CommonUtil.signReturn(result);
			}
			
			SysOfficeExample sysOfficeExample = new SysOfficeExample();
			sysOfficeExample.or().andIdEqualTo(epayCodeList.get(0).getOfficeId()).andAgtTypeEqualTo("3");
			List<SysOffice> sysOfficeList = sysOfficeService.selectByExample(sysOfficeExample);
			if(null == sysOfficeList || sysOfficeList.size() == 0){
				result.put("returnCode", "0008");
				result.put("returnMsg", "该商户机构信息不完整，请确认后重试");
				return CommonUtil.signReturn(result);
			}
			
			SysOffice sysOffice = sysOfficeList.get(0);
			
			if(!EpaySignUtil.checksign(sysOffice.getPublicKeyRsa(), srcStr, signStr)){//by linxf 测试屏蔽
				result.put("returnCode", "0004");
				result.put("returnMsg", "签名校验错误，请检查签名参数是否正确");
				return CommonUtil.signReturn(result);
			}
			
			String routeCode = memberInfo.getWxRouteId();
			
			MemberMerchantCodeExample memberMerchantCodeExample = new MemberMerchantCodeExample();
			memberMerchantCodeExample.createCriteria().andMemberIdEqualTo(memberInfo.getId()).andRouteCodeEqualTo(routeCode).andDelFlagEqualTo("0");
			
			List<MemberMerchantCode> merchantCodes = memberMerchantCodeService.selectByExample(memberMerchantCodeExample);
			if (merchantCodes == null || merchantCodes.size() != 1) {
				result.put("returnCode", "0008");
				result.put("returnMsg", "对不起，商户编码不存在");
				return CommonUtil.signReturn(result);
			}
			MemberMerchantCode merchantCode = merchantCodes.get(0);
			
			if(routeCode.equals(RouteCodeConstant.ML_ROUTE_CODE)){
				JSONObject resObjb = mlBindCardQuery(merchantCode.getKjMerchantCode(),bankAccount);
				if("0000".equals(resObjb.getString("returnCode"))){
					result.put("returnCode", "0000");
					result.put("returnMsg", "绑卡成功");
				}else{
					result.put("returnCode", "0003");
					result.put("returnMsg", resObjb.getString("returnMsg"));
				}
			}else if(routeCode.equals(RouteCodeConstant.TLKJ_ROUTE_CODE)){
				JSONObject resObjb = tlkjBindCardQuery(merchantCode.getKjMerchantCode(),bankAccount);
				result.put("returnCode", resObjb.getString("returnCode"));
				result.put("returnMsg", resObjb.getString("returnMsg"));
				
			}else{
				result.put("returnCode", "0003");
				result.put("returnMsg", "通道不支持");
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			result.put("returnCode", "0096");
			result.put("returnMsg", e.getMessage());
			return CommonUtil.signReturn(result);
		}
		return CommonUtil.signReturn(result);
	}
	
	public JSONObject mlBindCardQuery(String merCode,String bankAccount){
		JSONObject result = new JSONObject();
		try {
			String serverUrl = MLConfig.serverUrl+"/CardInfoQuery";
			String routeCode = RouteCodeConstant.ML_ROUTE_CODE;
			
			MemberMerchantKeyExample memberMerchantKeyExample = new MemberMerchantKeyExample();
	        memberMerchantKeyExample.createCriteria().andRouteCodeEqualTo(routeCode).andMerchantCodeEqualTo(merCode).andDelFlagEqualTo("0");
	        List<MemberMerchantKey> keyList = memberMerchantKeyService.selectByExample(memberMerchantKeyExample);
	        if(keyList == null || keyList.size()!=1){
	            result.put("returnCode", "0008");
				result.put("returnMsg", "商户私钥未配置");
				return result;
	        }
	        MemberMerchantKey merchantKey = keyList.get(0);
	        
	        String orderCode = "X"+CommonUtil.getOrderCode();
	        String srcStr = orderCode+"02"+merCode+bankAccount;
	     	//String srcStr = StringUtil.orderedKey(contextjson)+"&key="+merchantKey.getPrivateKey();
	     	System.out.println("加密源串："+srcStr);
			String sign1 = com.epay.scanposp.common.utils.ml.EncryptUtil.MD5(srcStr, 1).toUpperCase();
			System.out.println("第一次加密结果："+sign1);
			String sign = com.epay.scanposp.common.utils.ml.EncryptUtil.MD5(sign1+merchantKey.getPrivateKey(), 0).toUpperCase();
			System.out.println("第二次加密结果："+sign);
			
			
			Map<String,String> param = new HashMap<String, String>();
			param.put("ORDER_ID", orderCode);
			param.put("USER_TYPE", "02");
			param.put("USER_ID", merCode);
			param.put("ACCT_NO", bankAccount);
			param.put("SIGN_TYPE", "03");
			param.put("SIGN", sign);
			
			logger.info("米联绑卡查询参数[{}]",JSONObject.fromObject(param).toString() );
			
			List<NameValuePair> nvps = new LinkedList<NameValuePair>();
			List<String> keys = new ArrayList<String>(param.keySet());
			for (int i = 0; i < keys.size(); i++) {
				String name=(String) keys.get(i);
				String value=(String) param.get(name);
				if(value!=null && !"".equals(value)){
					nvps.add(new BasicNameValuePair(name, value));
				}
			}
            
            byte[] b = HttpClient4Util.getInstance().doPost(serverUrl, null, nvps);
			String respStr = new String(b, "UTF-8");
			logger.info("米联绑卡查询返回报文[{}]", new Object[] { respStr });
			
			Document reqDoc = DocumentHelper.parseText(respStr);
			Element rootEl = reqDoc.getRootElement();
			
			String code = rootEl.elementText("RESP_CODE");
			if("0000".equals(code)){
				result.put("returnCode", "0000");
				result.put("returnMsg", "绑卡成功");
			}else{
				result.put("returnCode", "0003");
				result.put("returnMsg", rootEl.elementText("RESP_DESC"));
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			result.put("returnCode", "0096");
			result.put("returnMsg", e.getMessage());
			return result;
		}
		return result;
	}
	
	@ResponseBody
	@RequestMapping("/api/quickPay/queryBindCard")
	public JSONObject msTransactionQuery(HttpServletRequest request,HttpServletResponse response) {
		JSONObject requestPRM = (JSONObject) request.getAttribute("requestPRM");
		JSONObject reqDataJson = requestPRM.getJSONObject("reqData");// 获取请求参数
		JSONObject result = new JSONObject();
		try {
			String orderCode = reqDataJson.getString("orderCode");
			MemberBindAccDtlExample memberBindAccDtlExample = new MemberBindAccDtlExample();
			memberBindAccDtlExample.or().andOrderCodeEqualTo(orderCode);
			List<MemberBindAccDtl> memberBindAccDtlList = memberBindAccDtlService.selectByExample(memberBindAccDtlExample);
			if(null == memberBindAccDtlList || memberBindAccDtlList.size() == 0){
				result.put("returnCode", "0002");
				result.put("returnMsg", "订单号不存在!");
				return result;
			}
			MemberBindAccDtl dtl = memberBindAccDtlList.get(0);
			String routeCode = dtl.getRouteCode();
			
			MemberMerchantCodeExample memberMerchantCodeExample = new MemberMerchantCodeExample();
			memberMerchantCodeExample.createCriteria().andMemberIdEqualTo(dtl.getMemberId()).andRouteCodeEqualTo(routeCode).andDelFlagEqualTo("0");
			List<MemberMerchantCode> merchantCodes = memberMerchantCodeService.selectByExample(memberMerchantCodeExample);
			if (merchantCodes == null || merchantCodes.size() != 1) {
				result.put("returnCode", "0008");
				result.put("returnMsg", "对不起，商户编码不存在");
				return CommonUtil.signReturn(result);
			}
			MemberMerchantCode merchantCode = merchantCodes.get(0);
			
			if(routeCode.equals(RouteCodeConstant.ML_ROUTE_CODE)){
				JSONObject resObjb = mlBindCardQuery(merchantCode.getKjMerchantCode(),dtl.getAcc());
				if("0000".equals(resObjb.getString("returnCode"))){
					result.put("returnCode", "0000");
					result.put("returnMsg", "绑卡成功");
				}else{
					result.put("returnCode", "0003");
					result.put("returnMsg", resObjb.getString("returnMsg"));
				}
			}else if(routeCode.equals(RouteCodeConstant.TLKJ_ROUTE_CODE)){
				JSONObject resObjb = tlkjBindCardQuery(merchantCode.getKjMerchantCode(),dtl.getAcc());
				result.put("returnCode", resObjb.getString("returnCode"));
				result.put("returnMsg", resObjb.getString("returnMsg"));
				
			}else{
				result.put("returnCode", "0003");
				result.put("returnMsg", "通道不支持");
			}
			
		}catch(Exception e){
			logger.error(e.getMessage());
			result.put("returnCode", "0096");
			result.put("returnMsg", e.getMessage());
		}
		return result;
	}
	
	@ResponseBody
	@RequestMapping("/quickPay/toCardPayNew")
	public JSONObject toCardPayNew(HttpServletRequest request,HttpServletResponse response){
		Map<String,String> inparam = new HashMap<String, String>();
		Enumeration<String> pNames=request.getParameterNames();
		while(pNames.hasMoreElements()){
		    String name=(String)pNames.nextElement();
		    String value=request.getParameter(name);
		    inparam.put(name, value);
		}
		logger.info("米联绑卡快捷支付下游入参[{}]",  JSONObject.fromObject(inparam).toString() );
		
		String orderNum = request.getParameter("orderNum");
		String memberCode = request.getParameter("memberCode");
		String payMoney = request.getParameter("payMoney");
		String accountName = request.getParameter("accountName");
		String accountType = request.getParameter("accountType");
		String bankAccount = request.getParameter("bankAccount");
		String tel = request.getParameter("tel");
		String certNbr = request.getParameter("certNbr");
		String bankCvv = request.getParameter("bankCvv");
		String bankYxq = request.getParameter("bankYxq");
		String callbackUrl = request.getParameter("callbackUrl");
		String signStr = request.getParameter("signStr");
		String tradeRate = request.getParameter("tradeRate");
		
		StringBuilder srcStr = new StringBuilder();
		JSONObject result = new JSONObject();
		
		if(accountName != null && !"".equals(accountName)){
			srcStr.append("accountName="+accountName+"&");
		}
		
		if(accountType == null || "".equals(accountType)){
			result.put("returnCode", "0007");
			result.put("returnMsg", "账户类型缺失");
			return CommonUtil.signReturn(result);
		}
		if(!"1".equals(accountType)&&!"2".equals(accountType)){
			result.put("returnCode", "0007");
			result.put("returnMsg", "账户类型不正确");
			return CommonUtil.signReturn(result);
		}
		
		srcStr.append("accountType="+accountType);
		
		if(bankAccount == null || "".equals(bankAccount)){
			result.put("returnCode", "0007");
			result.put("returnMsg", "银行卡号缺失");
			return CommonUtil.signReturn(result);
		}
		srcStr.append("&bankAccount="+bankAccount);
		
		if(bankCvv != null && !"".equals(bankCvv)){
			srcStr.append("&bankCvv="+bankCvv);
		}
		
		if(bankYxq != null && !"".equals(bankYxq)){
			srcStr.append("&bankYxq="+bankYxq);
		}
		
		if(callbackUrl == null || "".equals(callbackUrl)){
			result.put("returnCode", "0007");
			result.put("returnMsg", "支付结果通知回调地址缺失");
			return result;
		}
		srcStr.append("&callbackUrl="+callbackUrl);
		
		if(certNbr != null && !"".equals(certNbr)){
			srcStr.append("&certNbr="+certNbr);
		}
		
		if(memberCode == null || "".equals(memberCode)){
			result.put("returnCode", "0007");
			result.put("returnMsg", "商户号缺失");
			return CommonUtil.signReturn(result);
		}
		srcStr.append("&memberCode="+memberCode);
		
		if(orderNum == null || "".equals(orderNum)){
			result.put("returnCode", "0007");
			result.put("returnMsg", "商户订单号缺失");
			return CommonUtil.signReturn(result);
		}
		if(orderNum.length() > 32){
			result.put("returnCode", "0007");
			result.put("returnMsg", "订单号长度不能超过32位");
			return CommonUtil.signReturn(result);
		}
		srcStr.append("&orderNum="+orderNum);
		
		if(null == payMoney || !ValidateUtil.isDoubleT(payMoney) || Double.parseDouble(payMoney)<=0){
			result.put("returnCode", "0007");
			result.put("returnMsg", "支付金额输入不正确");
			return result;
		}
		srcStr.append("&payMoney="+payMoney);
		
		if(tel != null && !"".equals(tel)){
			srcStr.append("&tel="+tel);
		}
		
		if(tradeRate != null && !"".equals(tradeRate)){
			srcStr.append("&tradeRate="+tradeRate);
		}
		
		if(signStr == null || "".equals(signStr)){ 
			result.put("returnCode", "0007");
			result.put("returnMsg", "缺少签名信息");
			return result;
		}
		
		result = validMemberInfoToCardQuickPay(memberCode, orderNum,payMoney,"",callbackUrl,accountType,bankAccount,tel,bankCvv,bankYxq,accountName,certNbr,tradeRate, srcStr.toString(), signStr);
		
		return CommonUtil.signReturn(result);
	}
	
	public JSONObject mlCardQuickPay(String platformType,MemberInfo memberInfo,MemberMerchantCode merchantCode,MemberPayType memberPayType,String orderNum,String payMoney,String callbackUrl,String routeCode,String accountType,String bankAccount,String tel,String bankCvv,String bankYxq,String accountName,String certNbr) {
		JSONObject result = new JSONObject();
		try {
			String merCode = merchantCode.getKjMerchantCode();
			String payType = "9";
			
			MemberMerchantKeyExample memberMerchantKeyExample = new MemberMerchantKeyExample();
	        memberMerchantKeyExample.createCriteria().andRouteCodeEqualTo(routeCode).andMerchantCodeEqualTo(merCode).andDelFlagEqualTo("0");
	        List<MemberMerchantKey> keyList = memberMerchantKeyService.selectByExample(memberMerchantKeyExample);
	        if(keyList == null || keyList.size()!=1){
	            result.put("returnCode", "0008");
				result.put("returnMsg", "商户私钥未配置");
				return result;
	        }
	        MemberMerchantKey merchantKey = keyList.get(0);
			String orderCode = CommonUtil.getOrderCode();
			
			
	        DebitNote debitNote = new DebitNote();
			debitNote.setCreateDate(new Date());
			debitNote.setMemberId(memberInfo.getId());
			debitNote.setMoney(new BigDecimal(payMoney));
			debitNote.setOrderCode(orderCode);
			debitNote.setOrderNumOuter(orderNum);
			debitNote.setRouteId(routeCode);
			debitNote.setStatus("0");
			debitNote.setTxnMethod(PayTypeConstant.PAY_METHOD_YL);
			debitNote.setTxnType(payType);
			debitNote.setMerchantCode(merCode);
			debitNote.setMemberCode(memberInfo.getWxMemberCode());
			debitNote.setRemarks(bankAccount);
			debitNote.setSettleType(memberInfo.getSettleType());
			if("0".equals(memberInfo.getSettleType())){
				debitNote.setTradeRate(memberPayType.getT0TradeRate());
			}else{
				debitNote.setTradeRate(memberPayType.getT1TradeRate());
			}
			
			String configName = "SINGLE_MIN_"+routeCode+"_"+PayTypeConstant.PAY_METHOD_YL+"_"+PayTypeConstant.PAY_TYPE_KJ;
			JSONObject checkResult = commonUtilService.checkMinMoney(configName, new BigDecimal(payMoney));
			if(null != checkResult){
				debitNote.setStatus("5");
				debitNoteService.insertSelective(debitNote);
				return checkResult;
			}
			
			configName = "SINGLE_LIMIT_"+routeCode+"_"+PayTypeConstant.PAY_METHOD_YL+"_"+PayTypeConstant.PAY_TYPE_KJ;
			JSONObject limitResult = commonUtilService.checkLimitMoney(configName, new BigDecimal(payMoney));
			if(null != limitResult){
				debitNote.setStatus("4");
				debitNoteService.insertSelective(debitNote);
				return limitResult;
			}
			
			debitNoteService.insertSelective(debitNote);
			
			PayResultNotice payResultNotice = new PayResultNotice();
			payResultNotice.setOrderCode(debitNote.getOrderCode());
			payResultNotice.setOrderNumOuter(debitNote.getOrderNumOuter());
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
			
			String serverUrl = MLConfig.payServerUrl + "/controller/pay";
			String callBack = SysConfig.serverUrl + "/quickPay/mlPayNotify";
			String orderTime = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
			String amount = new DecimalFormat("0.00").format(Double.valueOf(payMoney));
			
			String srcStr = orderCode+amount+orderTime+"02"+merCode+"03"+"5029";
	     	System.out.println("加密源串："+srcStr);
			String sign1 = com.epay.scanposp.common.utils.ml.EncryptUtil.MD5(srcStr, 1).toUpperCase();
			System.out.println("第一次加密结果："+sign1);
			String sign = com.epay.scanposp.common.utils.ml.EncryptUtil.MD5(sign1+merchantKey.getPrivateKey(), 0).toUpperCase();
			System.out.println("第二次加密结果："+sign);
				
			
			
			Map<String,String> param = new HashMap<String, String>();
			param.put("ORDER_ID", orderCode);
			param.put("ORDER_AMT", amount);
			param.put("ORDER_TIME", orderTime);
			param.put("USER_TYPE", "02");
			param.put("USER_ID", merCode);
			param.put("SIGN_TYPE", "03");
			param.put("BUS_CODE", "5029");
			param.put("PAY_TYPE", "13");
			param.put("ADD1", bankAccount);
			param.put("ADD2", accountName);
			if("1".equals(accountType)){
				param.put("ADD3", "01");//01借记卡 02 信用卡
			}else if("2".equals(accountType)){
				param.put("ADD3", "02");//01借记卡 02 信用卡
			}
			param.put("ADD4", certNbr);
			param.put("PHONE_NO", tel);
			param.put("CVN2", bankCvv);
			param.put("VALID", bankYxq);
			param.put("BG_URL", callBack);
			param.put("SIGN", sign);
			
			logger.info("米联快捷绑卡参数[{}]",JSONObject.fromObject(param).toString() );
			
			List<NameValuePair> nvps = new LinkedList<NameValuePair>();
			List<String> keys = new ArrayList<String>(param.keySet());
			for (int i = 0; i < keys.size(); i++) {
				String name=(String) keys.get(i);
				String value=(String) param.get(name);
				if(value!=null && !"".equals(value)){
					nvps.add(new BasicNameValuePair(name, value));
				}
			}
            
            byte[] b = HttpClient4Util.getInstance().doPost(serverUrl, null, nvps);
			String respStr = new String(b, "UTF-8");
			logger.info("米联快捷绑卡返回报文[{}]", new Object[] { respStr });
			
			Document reqDoc = DocumentHelper.parseText(respStr);
			Element rootEl = reqDoc.getRootElement();
			
			String code = rootEl.elementText("RESP_CODE");
			String resSign = rootEl.elementText("SIGN");
			String resultMsg = "";
			boolean flag = false;
			
			srcStr = rootEl.elementText("ORDER_ID")+rootEl.elementText("ORDER_AMT")+rootEl.elementText("ORDER_TIME")+rootEl.elementText("USER_TYPE")+rootEl.elementText("USER_ID")+rootEl.elementText("SIGN_TYPE")+rootEl.elementText("BUS_CODE")+rootEl.elementText("RESP_CODE");
	     	System.out.println("出参加密源串："+srcStr);
			sign1 = com.epay.scanposp.common.utils.ml.EncryptUtil.MD5(srcStr, 1).toUpperCase();
			System.out.println("出参第一次加密结果："+sign1);
			sign = com.epay.scanposp.common.utils.ml.EncryptUtil.MD5(sign1+merchantKey.getPrivateKey(), 0).toUpperCase();
			System.out.println("出参第二次加密结果："+sign);
			
			if(resSign.equals(sign)){
				if("0000".equals(code)||"00".equals(code)){
					result.put("returnCode", "0000");
					result.put("returnMsg", "成功");
					flag = true;
				}else{
					resultMsg = rootEl.elementText("RESP_DESC");
				}
			}else{
				resultMsg = "快捷支付出参验签失败";
			}
			
			if(!flag){
				DebitNoteExample debitNoteExample = new DebitNoteExample();
				debitNoteExample.createCriteria().andOrderCodeEqualTo(orderCode);
				List<DebitNote> debitNotes = debitNoteService.selectByExample(debitNoteExample);
				if (debitNotes != null && debitNotes.size() > 0) {
					DebitNote debitNote_1 = debitNotes.get(0);
					debitNote_1.setStatus("2");
					debitNote_1.setUpdateDate(new Date());
					if(!"".equals(resultMsg)){
						debitNote_1.setRespMsg(resultMsg.length()>250?resultMsg.substring(0, 250):resultMsg);
					}
					debitNoteService.updateByPrimaryKey(debitNote_1);
				}
				result.put("returnCode", "0003");
				result.put("returnMsg", resultMsg);
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			result.put("returnCode", "0096");
			result.put("returnMsg", e.getMessage());
			return result;
		}
		return result;
	}
	
	
	
	public JSONObject bindCardTlkj(MemberInfo memberInfo,String routeCode,String bankAccount,String tel,String bankCvv,String bankYxq,String bankCode,String accountName,String certNbr,String accountType){
		JSONObject result = new JSONObject();
		try{
			String serverUrl = TLKJConfig.serverUrl+"/trans/BIND_CARD";
			
			MemberMerchantCodeExample memberMerchantCodeExample = new MemberMerchantCodeExample();
			memberMerchantCodeExample.createCriteria().andMemberIdEqualTo(memberInfo.getId()).andRouteCodeEqualTo(routeCode).andDelFlagEqualTo("0");
			
			List<MemberMerchantCode> merchantCodes = memberMerchantCodeService.selectByExample(memberMerchantCodeExample);
			if (merchantCodes == null || merchantCodes.size() != 1) {
				result.put("returnCode", "0008");
				result.put("returnMsg", "对不起，商户编码不存在");
				return result;
			}
			MemberMerchantCode merchantCode = merchantCodes.get(0);
			String merCode = merchantCode.getKjMerchantCode();
			
			MemberMerchantKeyExample memberMerchantKeyExample = new MemberMerchantKeyExample();
	        memberMerchantKeyExample.createCriteria().andRouteCodeEqualTo(routeCode).andMerchantCodeEqualTo(merCode).andDelFlagEqualTo("0");
	        List<MemberMerchantKey> keyList = memberMerchantKeyService.selectByExample(memberMerchantKeyExample);
	        if(keyList == null || keyList.size()!=1){
	            result.put("returnCode", "0008");
				result.put("returnMsg", "商户私钥未配置");
				return result;
	        }
	        MemberMerchantKey merchantKey = keyList.get(0);
	        
	        String orderCode = RandomTools.getRandomString(32);
	        
			String aesKey = merchantKey.getPrivateKey();
			String md5Key = merchantKey.getPrivateKey();
			
			com.alibaba.fastjson.JSONObject jsonObject=new com.alibaba.fastjson.JSONObject();
			jsonObject.put("agentno", merchantKey.getAppId());
			jsonObject.put("card_no",AesTool.encrypt(bankAccount, aesKey));
			jsonObject.put("account_name", accountName);
			jsonObject.put("id_card", AesTool.encrypt(certNbr, aesKey));
			jsonObject.put("name", accountName);
			jsonObject.put("mobile", AesTool.encrypt(tel, aesKey));
			jsonObject.put("card_type", "1".equals(accountType)?"2":"1");//2借记卡1贷记卡
			jsonObject.put("notify_url",SysConfig.serverUrl+"/quickPay/tlBindCardNotify");
			jsonObject.put("front_url",SysConfig.frontUrl+"/payment/bindCardNotify?orderCode="+orderCode);
			jsonObject.put("nonce_str", orderCode);
			if(!StringUtil.isEmpty(bankCvv)){
				jsonObject.put("cvn2", AesTool.encrypt(bankCvv,aesKey));
			}
			jsonObject.put("expired", bankYxq);
			jsonObject.put("acct_ppe","0");//是否对公户，0：否、1：是
			jsonObject.put("sign", com.epay.scanposp.common.utils.tlkj.MD5Util.getSign(jsonObject, md5Key, 1));
			
			logger.info("通联快捷绑卡参数[{}]",jsonObject.toString() );
			
			String respStr = HttpUtil.sendPostRequest(serverUrl, jsonObject.toString());
			
			
			logger.info("通联快捷绑卡返回报文[{}]", new Object[] { respStr });
			
			JSONObject resObj = JSONObject.fromObject(respStr);
			
			String code = resObj.getString("rspcode");
			if("00".equals(code)){
				result.put("returnCode", "0000");
				result.put("returnMsg", resObj.getString("rspmsg"));
				result.put("PAY_INFO", URLDecoder.decode(resObj.getString("content"), "UTF-8"));
			}else{
				result.put("returnCode", "0003");
				result.put("returnMsg", resObj.getString("rspmsg"));
			}
			
			result.put("orderCode", orderCode);
		}catch(Exception e){
			logger.info(e.getMessage(), e);
			result.put("returnCode", "0096");
			result.put("returnMsg", e.getMessage());
		}
		return result;
	}
	
	
	
	public JSONObject tlkjBindCardQuery(String merCode,String bankAccount){
		JSONObject result = new JSONObject();
		try {
			String serverUrl = TLKJConfig.serverUrl+"/trans/QUERY_BIND_CARD";
			String routeCode = RouteCodeConstant.TLKJ_ROUTE_CODE;
			
			MemberMerchantKeyExample memberMerchantKeyExample = new MemberMerchantKeyExample();
	        memberMerchantKeyExample.createCriteria().andRouteCodeEqualTo(routeCode).andMerchantCodeEqualTo(merCode).andDelFlagEqualTo("0");
	        List<MemberMerchantKey> keyList = memberMerchantKeyService.selectByExample(memberMerchantKeyExample);
	        if(keyList == null || keyList.size()!=1){
	            result.put("returnCode", "0008");
				result.put("returnMsg", "商户私钥未配置");
				return result;
	        }
	        MemberMerchantKey merchantKey = keyList.get(0);
	        
			String aesKey = merchantKey.getPrivateKey();
			String md5Key = merchantKey.getPrivateKey();
			
			com.alibaba.fastjson.JSONObject jsonObject=new com.alibaba.fastjson.JSONObject();
			jsonObject.put("agentno", merchantKey.getAppId());
			jsonObject.put("card_no", AesTool.encrypt(bankAccount, aesKey));
			jsonObject.put("nonce_str", RandomTools.getRandomString(32));
			jsonObject.put("sign", com.epay.scanposp.common.utils.tlkj.MD5Util.getSign(jsonObject, md5Key, 1));
			
			
			
			logger.info("通联绑卡查询参数[{}]",jsonObject.toString() );
			
			String respStr = HttpUtil.sendPostRequest(serverUrl, jsonObject.toString());
			
			logger.info("通联绑卡查询返回报文[{}]", new Object[] { respStr });
			
			JSONObject resObj = JSONObject.fromObject(respStr);
            
            	
			String code = resObj.getString("status");//状态：0、新申请，2、绑卡失败；3、绑卡中；6、绑卡成功
			if("6".equals(code)){
				result.put("returnCode", "0000");
				result.put("returnMsg", "绑卡成功");
			}else if("3".equals(code)){
				result.put("returnCode", "0010");
				result.put("returnMsg", "绑卡中");
			}else if("0".equals(code)){
				result.put("returnCode", "0011");
				result.put("returnMsg", "新申请");
			}else{
				result.put("returnCode", "0012");
				result.put("returnMsg", "绑卡失败");
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			result.put("returnCode", "0096");
			result.put("returnMsg", e.getMessage());
			return result;
		}
		return result;
	}
	
	public JSONObject tlkjCardQuickPay(String platformType,MemberInfo memberInfo,MemberMerchantCode merchantCode,MemberPayType memberPayType,String orderNum,String payMoney,String callbackUrl,String routeCode,String accountType,String bankAccount,String tel,String bankCvv,String bankYxq,String accountName,String certNbr,String tradeRate) {
		JSONObject result = new JSONObject();
		try {
			String merCode = merchantCode.getKjMerchantCode();
			String payType = "9";
			
			MemberMerchantKeyExample memberMerchantKeyExample = new MemberMerchantKeyExample();
	        memberMerchantKeyExample.createCriteria().andRouteCodeEqualTo(routeCode).andMerchantCodeEqualTo(merCode).andDelFlagEqualTo("0");
	        List<MemberMerchantKey> keyList = memberMerchantKeyService.selectByExample(memberMerchantKeyExample);
	        if(keyList == null || keyList.size()!=1){
	            result.put("returnCode", "0008");
				result.put("returnMsg", "商户私钥未配置");
				return result;
	        }
	        MemberMerchantKey merchantKey = keyList.get(0);
			String orderCode = CommonUtil.getOrderCode();
			
			
	        DebitNote debitNote = new DebitNote();
			debitNote.setCreateDate(new Date());
			debitNote.setMemberId(memberInfo.getId());
			debitNote.setMoney(new BigDecimal(payMoney));
			debitNote.setOrderCode(orderCode);
			debitNote.setOrderNumOuter(orderNum);
			debitNote.setRouteId(routeCode);
			debitNote.setStatus("0");
			debitNote.setTxnMethod(PayTypeConstant.PAY_METHOD_YL);
			debitNote.setTxnType(payType);
			debitNote.setMerchantCode(merCode);
			debitNote.setMemberCode(memberInfo.getWxMemberCode());
			debitNote.setRemarks(bankAccount);
			debitNote.setSettleType(memberInfo.getSettleType());
			debitNote.setTradeRate(new BigDecimal(tradeRate));
			
			
			String configName = "SINGLE_MIN_"+routeCode+"_"+PayTypeConstant.PAY_METHOD_YL+"_"+PayTypeConstant.PAY_TYPE_KJ;
			JSONObject checkResult = commonUtilService.checkMinMoney(configName, new BigDecimal(payMoney));
			if(null != checkResult){
				debitNote.setStatus("5");
				debitNoteService.insertSelective(debitNote);
				return checkResult;
			}
			
			configName = "SINGLE_LIMIT_"+routeCode+"_"+PayTypeConstant.PAY_METHOD_YL+"_"+PayTypeConstant.PAY_TYPE_KJ;
			JSONObject limitResult = commonUtilService.checkLimitMoney(configName, new BigDecimal(payMoney));
			if(null != limitResult){
				debitNote.setStatus("4");
				debitNoteService.insertSelective(debitNote);
				return limitResult;
			}
			
			debitNoteService.insertSelective(debitNote);
			
			PayResultNotice payResultNotice = new PayResultNotice();
			payResultNotice.setOrderCode(debitNote.getOrderCode());
			payResultNotice.setOrderNumOuter(debitNote.getOrderNumOuter());
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
			
			String serverUrl = TLKJConfig.serverUrl + "/trans/CUP_QUICK";
			String callBack = SysConfig.serverUrl + "/quickPay/tlkjPayNotify";
			//String amount = new DecimalFormat("0.00").format(Double.valueOf(payMoney));
			String amount = String.valueOf((new BigDecimal(payMoney)).multiply(new BigDecimal(100)).intValue());
			com.alibaba.fastjson.JSONObject jsonObject1=new com.alibaba.fastjson.JSONObject();
			jsonObject1.put("shopno", merCode);
			jsonObject1.put("notify_url", callBack);
			jsonObject1.put("cardattr", "0"+accountType);
			jsonObject1.put("amount", amount);
			jsonObject1.put("rate", debitNote.getTradeRate().doubleValue());
			jsonObject1.put("card_no", bankAccount);
			jsonObject1.put("orderno", orderCode);//订单号
			jsonObject1.put("nonce_str", RandomTools.getRandomString(30));
			jsonObject1.put("sign", com.epay.scanposp.common.utils.tlkj.MD5Util.getSign(jsonObject1, merchantKey.getPrivateKey(),1));
			
			
			logger.info("通联快捷支付参数[{}]",jsonObject1.toString() );
			
			String respStr = HttpUtil.sendPostRequest(serverUrl, jsonObject1.toString());
			
			logger.info("通联快捷支付返回报文[{}]", new Object[] { respStr });
			
			JSONObject resObj = JSONObject.fromObject(respStr);
            
            	
			String code = resObj.getString("rspcode");
			String resultMsg = "";
			boolean flag = false;
			
			if("00".equals(code)){
				result.put("returnCode", "0000");
				result.put("returnMsg", "成功");
				flag = true;
			}else{
				resultMsg = resObj.getString("rspmsg ");
			}
			
			
			if(!flag){
				DebitNoteExample debitNoteExample = new DebitNoteExample();
				debitNoteExample.createCriteria().andOrderCodeEqualTo(orderCode);
				List<DebitNote> debitNotes = debitNoteService.selectByExample(debitNoteExample);
				if (debitNotes != null && debitNotes.size() > 0) {
					DebitNote debitNote_1 = debitNotes.get(0);
					debitNote_1.setStatus("2");
					debitNote_1.setUpdateDate(new Date());
					if(!"".equals(resultMsg)){
						debitNote_1.setRespMsg(resultMsg.length()>250?resultMsg.substring(0, 250):resultMsg);
					}
					debitNoteService.updateByPrimaryKey(debitNote_1);
				}
				result.put("returnCode", "0003");
				result.put("returnMsg", resultMsg);
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			result.put("returnCode", "0096");
			result.put("returnMsg", e.getMessage());
			return result;
		}
		return result;
	}
	
	/**
	 * 通联快捷支付回调
	 * @param request
	 * @param response
	 */
	@RequestMapping("/quickPay/tlkjPayNotify")
	public void tlkjPayNotify(HttpServletRequest request,HttpServletResponse response) {
		try {
			
			String respStr = HttpUtil.getPostString(request);
			
			logger.info("tlkjPayNotify回调返回报文[{}]",  respStr);
			com.alibaba.fastjson.JSONObject obj = com.alibaba.fastjson.JSONObject.parseObject(respStr);
			String orderId = obj.getString("orderno");
			String trade_state = obj.getString("tradestatus");
			String orderAmt = obj.getString("amount");
			String respSign = obj.getString("sign");
			obj.remove("sign");
			String reqMsgId = orderId;
        	
        	TradeDetailExample tradeDetailExample = new TradeDetailExample();
        	tradeDetailExample.or().andOrderCodeEqualTo(reqMsgId);
        	List<TradeDetail> tradeDetailList = tradeDetailService.selectByExample(tradeDetailExample);
        	if(tradeDetailList!=null && tradeDetailList.size()>0){
        		response.getWriter().write("SUCCESS");
        		return;
        	}
        	
        	DebitNoteExample debitNoteExample = new DebitNoteExample();
			debitNoteExample.createCriteria().andOrderCodeEqualTo(reqMsgId);
			List<DebitNote> debitNotes_tmp = debitNoteService.selectByExample(debitNoteExample);
			if (debitNotes_tmp == null || debitNotes_tmp.size() == 0) {
				logger.info("订单不存在");
                response.getWriter().write("FAIL");
        		return;
				
			}
			DebitNote debitNote = debitNotes_tmp.get(0);
			
			MemberMerchantKeyExample memberMerchantKeyExample = new MemberMerchantKeyExample();
	        memberMerchantKeyExample.createCriteria().andRouteCodeEqualTo(debitNote.getRouteId()).andMerchantCodeEqualTo(debitNote.getMerchantCode()).andDelFlagEqualTo("0");
	        List<MemberMerchantKey> keyList = memberMerchantKeyService.selectByExample(memberMerchantKeyExample);
	        if(keyList == null || keyList.size()!=1){
	        	logger.info("商户私钥未配置");
                response.getWriter().write("FAIL");
        		return;
	        }
	        MemberMerchantKey merchantKey = keyList.get(0);
        	
	        if(!com.epay.scanposp.common.utils.tlkj.MD5Util.verifySign(obj, merchantKey.getPrivateKey(), respSign)){
				logger.info("验证签名不通过");
                response.getWriter().write("FAIL");
        		return;
			}
			
        	
        	String result_message = "";
        	MsResultNoticeExample msResultNoticeExample = new MsResultNoticeExample();
			msResultNoticeExample.or().andOrderCodeEqualTo(reqMsgId);
			List<MsResultNotice> msResultNoticeList = msResultNoticeService.selectByExample(msResultNoticeExample);
			if(null == msResultNoticeList || msResultNoticeList.size() == 0){
				if (debitNote != null ) {
					debitNote.setRespCode(trade_state);
					debitNote.setRespMsg(result_message);
					
					//新增一条交易明细记录
					TradeDetail tradeDetail=new TradeDetail();
					tradeDetail.setTxnDate(DateUtil.getDateFormat(new Date(), "yyyyMMdd"));
					tradeDetail.setMemberId(debitNote.getMemberId());
					tradeDetail.setMerchantCode(debitNote.getMerchantCode());
					
					tradeDetail.setMemberCode(debitNote.getMemberCode());
					tradeDetail.setMoney(debitNote.getMoney());
					tradeDetail.setPayTime(DateUtil.getDateTimeStr(debitNote.getCreateDate()));
					tradeDetail.setPtSerialNo(debitNote.getOrderCode());
					tradeDetail.setOrderCode(debitNote.getOrderCode());
					tradeDetail.setChannelNo("");
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
					if ("S".equals(trade_state)) {
						debitNote.setStatus("1");
						tradeDetail.setRespType("S");
						tradeDetail.setRespCode("000000");
					}else{
						debitNote.setStatus("2");
						tradeDetail.setRespType("E");
						tradeDetail.setRespCode(trade_state);
					}
					debitNote.setUpdateDate(new Date());
					debitNoteService.updateByPrimaryKey(debitNote);
					
					MsResultNotice msResultNotice = new MsResultNotice();
					msResultNotice.setMoney(new BigDecimal(orderAmt));
					msResultNotice.setOrderCode(reqMsgId);
					msResultNotice.setPtSerialNo("");
					
					//SimpleDateFormat dateSimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					msResultNotice.setRespDate(DateUtil.getDateTimeStr(new Date()));
					msResultNotice.setRespType("");
					msResultNotice.setRespCode(trade_state);
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
						if ("S".equals(trade_state)) {
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
					if ("S".equals(trade_state)) {
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
			response.getWriter().write("SUCCESS");
		} catch (IOException e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}

	}
	
	@RequestMapping("/quickPay/tlBindCardNotify")
	public void tlBindCardNotify(HttpServletRequest request,HttpServletResponse response) {
		try {
			
			String respStr = HttpUtil.getPostString(request);
			//String respStr = "{\"account_name\":\"林晓锋\",\"acct_ppe\":\"0\",\"agentno\":\"A0000001\",\"card_no\":\"AMgaNC8hAFmHPQz+9wYU4Bqf9jYyEjBUlFLpzPLzgMo=\",\"id_card\":\"Z+kmNGS/llHnw5/nw/O4Sml0cMxPXKgBPNvbPBCSAnA=\",\"mobile\":\"V8BgLs9DeE9WddifbLbc6g==\",\"name\":\"林晓锋\",\"nonce_str\":\"tXqCnk8h0PufhFpE5wzfTedcOUZkPgKQ\",\"sign\":\"284A3359046822960F42CEAAD0D2C050\",\"status\":\"6\"}";
			logger.info("tlBindCardNotify回调返回报文[{}]",  respStr);
			com.alibaba.fastjson.JSONObject respObj = com.alibaba.fastjson.JSONObject.parseObject(respStr);
			String sign = respObj.getString("sign");
			respObj.remove("sign");
			String aesKey = TLKJConfig.privateKey;
			String md5Key = TLKJConfig.privateKey;
			
			if(!MD5Util.verifySign(respObj, md5Key, sign)){
				logger.info("验证签名不通过");
                response.getWriter().write("FAIL");
        		return;
			}
			
			String cardNo = respObj.getString("card_no");
			String certNbr  = respObj.getString("id_card");
			cardNo = AesTool.decrypt(cardNo, aesKey);
			certNbr = AesTool.decrypt(certNbr, aesKey);
			String status = respObj.getString("status");
			if("2".equals(status)||"6".equals(status)){
				MemberBindAccDtlExample memberBindAccDtlExample = new MemberBindAccDtlExample();
				memberBindAccDtlExample.createCriteria().andRouteCodeEqualTo(RouteCodeConstant.TLKJ_ROUTE_CODE).andAccEqualTo(cardNo).andCertNbrEqualTo(certNbr).andRespCodeEqualTo("0").andDelFlagEqualTo("0");
				memberBindAccDtlExample.setOrderByClause(" create_date desc ");
				List<MemberBindAccDtl> list = memberBindAccDtlService.selectByExample(memberBindAccDtlExample);
				if(list!=null&&list.size()>0){
					MemberBindAccDtl memberBindAccDtl = list.get(0);
					if("2".equals(status)){//失败
						memberBindAccDtl.setRespCode("1");
					}else if("6".equals(status)){//成功
						memberBindAccDtl.setRespCode("2");
					}
					memberBindAccDtl.setUpdateDate(new Date());
					memberBindAccDtlService.updateByPrimaryKey(memberBindAccDtl);
				}
			}
			
			
			
			System.out.println(cardNo);
            
        } catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}
		try {
			response.getWriter().write("SUCCESS");
		} catch (IOException e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}

	}
}
