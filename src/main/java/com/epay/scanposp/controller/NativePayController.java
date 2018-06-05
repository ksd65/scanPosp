package com.epay.scanposp.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.epay.scanposp.common.constant.ESKConfig;
import com.epay.scanposp.common.constant.SysConfig;
import com.epay.scanposp.common.utils.CommonUtil;
import com.epay.scanposp.common.utils.DeviceRequestUtil;
import com.epay.scanposp.common.utils.Probability;
import com.epay.scanposp.common.utils.StringUtil;
import com.epay.scanposp.common.utils.ValidateUtil;
import com.epay.scanposp.common.utils.constant.PayTypeConstant;
import com.epay.scanposp.common.utils.constant.RouteCodeConstant;
import com.epay.scanposp.common.utils.epaySecurityUtil.EpaySignUtil;
import com.epay.scanposp.common.utils.esk.Key;
import com.epay.scanposp.common.utils.ms.CryptoUtil;
import com.epay.scanposp.common.utils.ms.HttpClient4Util;
import com.epay.scanposp.common.utils.ms.MSCommonUtil;
import com.epay.scanposp.entity.Account;
import com.epay.scanposp.entity.AccountExample;
import com.epay.scanposp.entity.DebitNote;
import com.epay.scanposp.entity.DebitNoteExample;
import com.epay.scanposp.entity.DebitNoteIp;
import com.epay.scanposp.entity.EpayCode;
import com.epay.scanposp.entity.EpayCodeExample;
import com.epay.scanposp.entity.MemberInfo;
import com.epay.scanposp.entity.MemberInfoExample;
import com.epay.scanposp.entity.MemberMerchantCode;
import com.epay.scanposp.entity.MemberMerchantCodeExample;
import com.epay.scanposp.entity.MemberPayType;
import com.epay.scanposp.entity.MemberPayTypeExample;
import com.epay.scanposp.entity.PayResultNotice;
import com.epay.scanposp.entity.PayResultNoticeExample;
import com.epay.scanposp.entity.PayTypeDefault;
import com.epay.scanposp.entity.PayTypeDefaultExample;
import com.epay.scanposp.entity.PayTypeRule;
import com.epay.scanposp.entity.PayTypeRuleExample;
import com.epay.scanposp.entity.SysOffice;
import com.epay.scanposp.entity.SysOfficeExample;
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


@Controller
public class NativePayController {
	private static Logger logger = LoggerFactory.getLogger(NativePayController.class);

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
	@RequestMapping("/api/pay/nativePay")
	public JSONObject pay(HttpServletRequest request) {
		JSONObject requestPRM = (JSONObject) request.getAttribute("requestPRM");
		JSONObject reqDataJson = requestPRM.getJSONObject("reqData");// 获取请求参数
		
		JSONObject result = new JSONObject();  
		
		
		String payMoney = reqDataJson.getString("payMoney");
		String payType = reqDataJson.getString("payType");
		String orderNum = reqDataJson.getString("orderNum");
		String memberCode = reqDataJson.getString("memberCode");
		String ip = reqDataJson.getString("ip");
		String callbackUrl = reqDataJson.getString("callbackUrl");
		String signStr = reqDataJson.getString("signStr");
		String userId = reqDataJson.getString("userId");
		
		StringBuilder srcStr = new StringBuilder();
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
		
		
		if(signStr == null || "".equals(signStr)){ 
			result.put("returnCode", "0007");
			result.put("returnMsg", "缺少签名信息");
			return result;
		}
		
		if(!"1".equals(payType)){
			result.put("returnCode", "0007");
			result.put("returnMsg", "支付方式不正确");
			return result;
		}
		srcStr.append("&payType="+payType);
		String userAgent = "";
		if(reqDataJson.containsKey("userAgent")){
			userAgent = reqDataJson.getString("userAgent");
		}
		result = validMemberInfoForNitive(memberCode, orderNum, payMoney, "3", payType, srcStr.toString(), signStr, callbackUrl ,ip ,userId,userAgent);
		
		return result;

	}
	
	
	
	public JSONObject validMemberInfoForNitive(String memberCode,String orderNum,String payMoney,String platformType,String payType,String signOrginalStr,String signedStr,String callbackUrl,String clientIp,String userId,String userAgent){
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
				result.put("returnCode", "0011");
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
		String payTypeStr = "";
		if("1".equals(payType)){
			payTypeStr = "WX";
		}else if("2".equals(payType)){
			payTypeStr = "ZFB";
		}else if("3".equals(payType)){
			payTypeStr = "QQ";
		}else if("5".equals(payType)){
			payTypeStr = "JD";
		}
		
		MemberPayTypeExample memberPayTypeExample = new MemberPayTypeExample();
		memberPayTypeExample.createCriteria().andMemberIdEqualTo(memberInfo.getId()).andPayMethodEqualTo(PayTypeConstant.PAY_METHOD_GZHZF).andPayTypeEqualTo(payTypeStr).andDelFlagEqualTo("0");
		List<MemberPayType> memberPayTypeList =  memberPayTypeService.selectByExample(memberPayTypeExample);
		if(memberPayTypeList==null || memberPayTypeList.size()==0){
			result.put("returnCode", "0008");
			result.put("returnMsg", "对不起，商户未开通该支付权限");
			return result;
		}
		
		MemberPayType memberPayType = memberPayTypeList.get(0);
		
		Map<String,String> rtMap  = commonUtilService.getRouteCodeAndAisleType(memberInfo.getId(),PayTypeConstant.PAY_METHOD_GZHZF,payTypeStr);
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
				result.put("returnMsg", "对不起，商户编码不存在");
				return result;
			}
		}else if("2".equals(payType)){
			if(merchantCode.getZfbMerchantCode()==null || "".equals(merchantCode.getZfbMerchantCode())){
				result.put("returnCode", "0008");
				result.put("returnMsg", "对不起，商户编码不存在");
				return result;
			}
		}else if("3".equals(payType)){
			if(merchantCode.getQqMerchantCode()==null || "".equals(merchantCode.getQqMerchantCode())){
				result.put("returnCode", "0008");
				result.put("returnMsg", "对不起，商户编码不存在");
				return result;
			}
		}else if("5".equals(payType)){
			if(merchantCode.getJdMerchantCode()==null || "".equals(merchantCode.getJdMerchantCode())){
				result.put("returnCode", "0008");
				result.put("returnMsg", "对不起，商户编码不存在");
				return result;
			}
		}
		
		
		boolean merchantFlag = false;
		PayTypeDefaultExample payTypeDefaultExample = new PayTypeDefaultExample();
		payTypeDefaultExample.createCriteria().andPayMethodEqualTo(PayTypeConstant.PAY_METHOD_GZHZF).andPayTypeEqualTo(payTypeStr).andMemberIdEqualTo(memberInfo.getId()).andDelFlagEqualTo("0");
		List<PayTypeDefault> payTypeDefaultList = payTypeDefaultService.selectByExample(payTypeDefaultExample);
		if(payTypeDefaultList == null || payTypeDefaultList.size()==0){
			payTypeDefaultExample = new PayTypeDefaultExample();
			payTypeDefaultExample.createCriteria().andPayMethodEqualTo(PayTypeConstant.PAY_METHOD_GZHZF).andPayTypeEqualTo(payTypeStr).andMemberIdEqualTo(0).andDelFlagEqualTo("0");
			payTypeDefaultList = payTypeDefaultService.selectByExample(payTypeDefaultExample);
		}
		
		if(payTypeDefaultList == null || payTypeDefaultList.size()==0){//走概率计算
			PayTypeRuleExample payTypeRuleExample = new PayTypeRuleExample();
			
			boolean memberType = false;
			payTypeRuleExample.createCriteria().andPayMethodEqualTo(PayTypeConstant.PAY_METHOD_GZHZF).andPayTypeEqualTo(payTypeStr).andMemberIdEqualTo(memberInfo.getId()).andDelFlagEqualTo("0");
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
				payTypeRuleExample.createCriteria().andPayMethodEqualTo(PayTypeConstant.PAY_METHOD_GZHZF).andPayTypeEqualTo(payTypeStr).andMemberIdEqualTo(ruleMemberId).andRuleTypeEqualTo("3").andMinMoneyLessThan(new BigDecimal(payMoney)).andMaxMoneyGreaterThanOrEqualTo(new BigDecimal(payMoney)).andBeginTimeLessThanOrEqualTo(time).andEndTimeGreaterThanOrEqualTo(time).andDelFlagEqualTo("0");//小数点金额概率规则
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
				payTypeRuleExample.createCriteria().andPayMethodEqualTo(PayTypeConstant.PAY_METHOD_GZHZF).andPayTypeEqualTo(payTypeStr).andMemberIdEqualTo(ruleMemberId).andRuleTypeEqualTo("2").andMinMoneyLessThan(new BigDecimal(payMoney)).andMaxMoneyGreaterThanOrEqualTo(new BigDecimal(payMoney)).andBeginTimeLessThanOrEqualTo(time).andEndTimeGreaterThanOrEqualTo(time).andDelFlagEqualTo("0");//金额概率规则
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
				payTypeRuleExample.createCriteria().andPayMethodEqualTo(PayTypeConstant.PAY_METHOD_GZHZF).andPayTypeEqualTo(payTypeStr).andMemberIdEqualTo(ruleMemberId).andRuleTypeEqualTo("1").andBeginTimeLessThanOrEqualTo(time).andEndTimeGreaterThanOrEqualTo(time).andDelFlagEqualTo("0");//默认规则
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
		if(!EpaySignUtil.checksign(sysOffice.getPublicKeyRsa(), signOrginalStr, signedStr)){//by linxf 测试屏蔽
			result.put("returnCode", "0004");
			result.put("returnMsg", "签名校验错误，请检查签名参数是否正确");
			return result;
		}
		
		
		
		if(RouteCodeConstant.ESK_ROUTE_CODE.equals(routeCode)){
			if("1".equals(payType)){
				memberInfo.setSettleType("1");
				result = eskWxPay(platformType,memberInfo,memberPayType, payMoney, orderNum,clientIp, callbackUrl , merchantCode,userAgent,routeCode,aisleType,userId );
			}
		}
		
		return result;
	}
	
	/**
	 * 易收款微信支付
	 * 
	 * @param request
	 * @return
	 */

	public JSONObject eskWxPay(String platformType,MemberInfo memberInfo,MemberPayType memberPayType,String payMoney,String orderNumOuter,String ip,String callbackUrl,MemberMerchantCode merchantCode,String userAgent,String routeCode,String aisleType,String userId) {
		JSONObject result = new JSONObject();
		JSONObject resData = new JSONObject();
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
			debitNote.setTxnMethod(PayTypeConstant.PAY_METHOD_GZHZF);
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
			
			String configName = "SINGLE_MEMBER_LIMIT_"+memberInfo.getId()+"_003_WX";
			JSONObject memResult = commonUtilService.checkLimitMoney(configName, new BigDecimal(payMoney));
			if(null != memResult){
				debitNote.setStatus("9");
				debitNoteService.insertSelective(debitNote);
				return memResult;
			} 
			
			configName = "SINGLE_MIN_"+routeCode+"_003_WX";
			JSONObject checkResult = commonUtilService.checkMinMoney(configName, new BigDecimal(payMoney));
			if(null != checkResult){
				debitNote.setStatus("5");
				debitNoteService.insertSelective(debitNote);
				return checkResult;
			}
			
			configName = "SINGLE_LIMIT_"+routeCode+"_003_WX";
			JSONObject limitResult = commonUtilService.checkLimitMoney(configName, new BigDecimal(payMoney));
			if(null != limitResult){
				debitNote.setStatus("4");
				debitNoteService.insertSelective(debitNote);
				return limitResult;
			}
			
			JSONObject timeResult = commonUtilService.checkLimitIpFail(PayTypeConstant.PAY_METHOD_GZHZF, PayTypeConstant.PAY_TYPE_WX, memberInfo.getId(), ip,routeCode,"");
			if(null != timeResult){
				debitNote.setStatus("12");
				debitNoteService.insertSelective(debitNote);
				return timeResult;
			}
			
			JSONObject ipResult = commonUtilService.checkLimitIp(PayTypeConstant.PAY_METHOD_GZHZF, PayTypeConstant.PAY_TYPE_WX, memberInfo.getId(), routeCode, merchantCode.getWxMerchantCode(), ip);
			if(null != ipResult){
				debitNote.setStatus("6");
				debitNoteService.insertSelective(debitNote);
				return ipResult;
			}
			
			JSONObject mResult = commonUtilService.checkLimitMerchantMoney(routeCode,merchantCode.getWxMerchantCode());
			if(null != mResult){
				debitNote.setStatus("7");
				debitNoteService.insertSelective(debitNote);
				return mResult;
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
			
			String tranCode = "003";
			String charset = "utf-8";
			
			JSONObject reqData = new JSONObject();
			reqData.put("merchantCode", merchantCode.getWxMerchantCode());
			reqData.put("orderNumber", orderCode);
			reqData.put("tranCode", tranCode);
			reqData.put("userId", userId);
			reqData.put("aisleType", aisleType);
			reqData.put("totalAmount", payMoney);
			reqData.put("subject", memberInfo.getName() + " 收款");
			reqData.put("PayType", "1");
			reqData.put("callback", callBack);
			reqData.put("desc", memberInfo.getName() + " 收款");
			reqData.put("terminalId", ip);
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
				
				result.put("returnCode", "4004");
				result.put("returnMsg", "调用微信支付接口返回失败("+respJSONObject.getString("respMsg")+")");
			}
			
			try{
		        DebitNoteIp debitNoteIp = new DebitNoteIp();
		        debitNoteIp.setMemberId(memberInfo.getId());
		        debitNoteIp.setMerchantCode(merchantCode.getWxMerchantCode());
		        debitNoteIp.setOrderCode(orderCode);
		        debitNoteIp.setRouteId(routeCode);
		        debitNoteIp.setTxnMethod(PayTypeConstant.PAY_METHOD_GZHZF);
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
	
}
