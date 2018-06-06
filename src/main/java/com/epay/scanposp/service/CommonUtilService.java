package com.epay.scanposp.service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.epay.scanposp.common.constant.SysConfig;
import com.epay.scanposp.common.utils.DateUtil;
import com.epay.scanposp.common.utils.constant.PayTypeConstant;
import com.epay.scanposp.entity.DebitNoteIp;
import com.epay.scanposp.entity.DebitNoteIpExample;
import com.epay.scanposp.entity.IpBlackList;
import com.epay.scanposp.entity.IpBlackListExample;
import com.epay.scanposp.entity.MemberIpRule;
import com.epay.scanposp.entity.MemberIpRuleExample;
import com.epay.scanposp.entity.MemberIpWhiteList;
import com.epay.scanposp.entity.MemberIpWhiteListExample;
import com.epay.scanposp.entity.PayQrCodeTotal;
import com.epay.scanposp.entity.PayQrCodeTotalExample;
import com.epay.scanposp.entity.PayType;
import com.epay.scanposp.entity.PayTypeExample;
import com.epay.scanposp.entity.PrePayMember;
import com.epay.scanposp.entity.PrePayMemberExample;
import com.epay.scanposp.entity.PrePayStatistics;
import com.epay.scanposp.entity.PrePayStatisticsExample;
import com.epay.scanposp.entity.SubMerchantBlackList;
import com.epay.scanposp.entity.SubMerchantBlackListExample;
import com.epay.scanposp.entity.SubMerchantCode;
import com.epay.scanposp.entity.SubMerchantCodeExample;
import com.epay.scanposp.entity.SubMerchantTotal;
import com.epay.scanposp.entity.SubMerchantTotalExample;
import com.epay.scanposp.entity.SysCommonConfig;
import com.epay.scanposp.entity.SysCommonConfigExample;

@Service
public class CommonUtilService {
	
	private static Logger logger = LoggerFactory.getLogger(CommonUtilService.class);
	
	@Resource
	private DebitNoteIpService debitNoteIpService;
	
	@Resource
	private MemberIpWhiteListService memberIpWhiteListService;
	
	@Resource
	private MemberIpRuleService memberIpRuleService;
	
	@Autowired
	private SysCommonConfigService sysCommonConfigService;
	
	@Resource
	private TradeDetailDailyService tradeDetailDailyService;
	
	@Resource
	private PayTypeService payTypeService;
	
	@Resource
	private PayQrCodeTotalService payQrCodeTotalService;
	
	@Resource
	private PrePayMemberService prePayMemberService;
	
	@Resource
	private PrePayStatisticsService prePayStatisticsService;
	
	@Resource
	private TradeDetailService tradeDetailService;
	
	@Resource
	private DebitNoteService debitNoteService;
	
	@Resource
	private SubMerchantCodeService subMerchantCodeService;
	
	@Resource
	private IpBlackListService ipBlackListService;
	
	@Resource
	private SubMerchantBlackListService subMerchantBlackListService;
	
	@Resource
	private SubMerchantTotalService subMerchantTotalService;
	
	public JSONObject checkLimitMoney(String configName, BigDecimal tradeMoney){
		
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
	
	public JSONObject checkMinMoney(String configName, BigDecimal tradeMoney){
		
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
	
	public JSONObject checkLimitMerchantMoney(String routeId,String merchantCode){
		
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
	
	/**
	 * 验证限制某种支付方式失败后一定配置时间内不允许请求
	 * @param payMethod
	 * @param payType
	 * @param memberId
	 * @param ip
	 * @return
	 */
	public JSONObject checkLimitIpFail(String payMethod, String payType, int memberId, String ip,String routeCode,String ipReal){
		JSONObject result = new JSONObject();
		String configName = "IP_BLACK_TIME_"+routeCode+"_"+payType;
		String txnType = transPayType(payType);
		String second = "";
		SysCommonConfigExample sysCommonConfigExample = new SysCommonConfigExample();
		sysCommonConfigExample.or().andNameEqualTo(configName).andDelFlagEqualTo("0");
		List<SysCommonConfig> sysCommonConfig = sysCommonConfigService.selectByExample(sysCommonConfigExample);
		if (sysCommonConfig != null && sysCommonConfig.size() > 0) {
			second = sysCommonConfig.get(0).getValue();
		}
		if(!"".equals(second)){
			Map<String,Object> param = new HashMap<String, Object>();
			param.put("ip", ip);
			param.put("routeCode", routeCode);
			param.put("txnType", txnType);
			param.put("seconds", second);
			int blackCount = ipBlackListService.countBlack(param);
			if(blackCount>0){
				result.put("returnCode", "4004");
				result.put("returnMsg", "IP"+ip+"无支付权限");
				return result;
			}
		}
		if(PayTypeConstant.PAY_METHOD_SMZF.equals(payMethod)&&"".equals(ipReal)){
			return null;
		}
		configName = "LIMIT_FAIL_TIMES_"+routeCode+"_"+payType;
		String value = "";
		sysCommonConfigExample = new SysCommonConfigExample();
		sysCommonConfigExample.or().andNameEqualTo(configName).andDelFlagEqualTo("0");
		sysCommonConfig = sysCommonConfigService.selectByExample(sysCommonConfigExample);
		if (sysCommonConfig != null && sysCommonConfig.size() > 0) {
			value = sysCommonConfig.get(0).getValue();
		}
		configName = "LIMIT_FAIL_COUNT_"+routeCode+"_"+payType;
		String count = "";
		sysCommonConfigExample = new SysCommonConfigExample();
		sysCommonConfigExample.or().andNameEqualTo(configName).andDelFlagEqualTo("0");
		sysCommonConfig = sysCommonConfigService.selectByExample(sysCommonConfigExample);
		if (sysCommonConfig != null && sysCommonConfig.size() > 0) {
			count = sysCommonConfig.get(0).getValue();
		}
		if(!"".equals(value)&&!"".equals(count)){
			Map<String,Object> param = new HashMap<String, Object>();
			param.put("ip", ip);
			param.put("routeCode", routeCode);
			param.put("txnType", txnType);
			param.put("seconds", value);
			int counts = debitNoteService.selectFailCountsWithinTime(param);
			if(counts>=Integer.parseInt(count)){
				IpBlackList ipBlackList = new IpBlackList();
				ipBlackList.setTxnType(txnType);
				//ipBlackList.setTxnMethod(payMethod);
				ipBlackList.setIp(ip);
				ipBlackList.setMemberId(memberId);
				ipBlackList.setRouteCode(routeCode);
				ipBlackList.setBlackType("1");
				ipBlackList.setCreateDate(new Date());
				ipBlackList.setDelFlag("0");
				ipBlackListService.insertSelective(ipBlackList);
				result.put("returnCode", "4004");
				result.put("returnMsg", "IP"+ip+"无支付权限");
				return result;
			}
		}
		return null;
	}
	
	private String transPayType(String payType){
		Map<String,String> obj = new HashMap<String, String>();
		obj.put("WX","1");
		obj.put("ZFB","2");
		obj.put("QQ","3");
		obj.put("BD","4");
		obj.put("JD","5");
		obj.put("YL","8");
		obj.put("KJ","9");
		return obj.get(payType);
	}
	
	public JSONObject checkLimitIp(String payMethod, String payType, int memberId, String routeCode, String merchantCode, String ip){
		
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
	
	public Map<String,String> getRouteCodeAndAisleType(Integer memberId,String payMethod, String payType  ){
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
	
	//取超出限额的收款人
	public List<PayQrCodeTotal> getExceedPayeeList( BigDecimal tradeMoney){
		
		String value = "";
		SysCommonConfigExample sysCommonConfigExample = new SysCommonConfigExample();
		sysCommonConfigExample.or().andNameEqualTo("SINGLE_PAYEE_LIMIT").andDelFlagEqualTo("0");
		List<SysCommonConfig> sysCommonConfig = sysCommonConfigService.selectByExample(sysCommonConfigExample);
		if (sysCommonConfig != null && sysCommonConfig.size() > 0) {
			value = sysCommonConfig.get(0).getValue();
		}
		if(!"".equals(value) && !"0".equals(value)){
			String tradeDate = DateUtil.getDateFormat(new Date(), "yyyyMMdd");
			BigDecimal singleLimit = new BigDecimal(value);
			PayQrCodeTotalExample payQrCodeTotalExample = new PayQrCodeTotalExample();
			payQrCodeTotalExample.createCriteria().andTradeDateEqualTo(tradeDate).andTotalMoneyGreaterThan(singleLimit.subtract(tradeMoney)).andDelFlagEqualTo("0");
			List<PayQrCodeTotal> list = payQrCodeTotalService.selectByExample(payQrCodeTotalExample);
			
			return list;
		}
		return null;
	}
	//取超出次数的收款人
	public List<PayQrCodeTotal> getExceedCountsPayeeList( ){
		
		String value = "";
		SysCommonConfigExample sysCommonConfigExample = new SysCommonConfigExample();
		sysCommonConfigExample.or().andNameEqualTo("SINGLE_PAYEE_COUNTS_LIMIT").andDelFlagEqualTo("0");
		List<SysCommonConfig> sysCommonConfig = sysCommonConfigService.selectByExample(sysCommonConfigExample);
		if (sysCommonConfig != null && sysCommonConfig.size() > 0) {
			value = sysCommonConfig.get(0).getValue();
		}
		if(!"".equals(value) && !"0".equals(value)){
			String tradeDate = DateUtil.getDateFormat(new Date(), "yyyyMMdd");
			Integer singleLimit = new Integer(value);
			PayQrCodeTotalExample payQrCodeTotalExample = new PayQrCodeTotalExample();
			payQrCodeTotalExample.createCriteria().andTradeDateEqualTo(tradeDate).andCountsGreaterThan(singleLimit-1).andDelFlagEqualTo("0");
			List<PayQrCodeTotal> list = payQrCodeTotalService.selectByExample(payQrCodeTotalExample);
			
			return list;
		}
		return null;
	}
	
	public JSONObject checkPrePayMoney(Integer memberId,BigDecimal tradeRate, String payMethod,String payTypeStr,String routeCode, BigDecimal tradeMoney){
		
		JSONObject result = new JSONObject();
		
		PrePayMemberExample prePayMemberExample = new PrePayMemberExample();
		prePayMemberExample.createCriteria().andMemberIdEqualTo(memberId).andPayMethodEqualTo(payMethod).andPayTypeEqualTo(payTypeStr).andRouteCodeEqualTo(routeCode).andDelFlagEqualTo("0");
		List<PrePayMember> prePayMemberList = prePayMemberService.selectByExample(prePayMemberExample);
		if(prePayMemberList==null || prePayMemberList.size()==0){
			prePayMemberExample = new PrePayMemberExample();
			prePayMemberExample.createCriteria().andMemberIdEqualTo(0).andPayMethodEqualTo(payMethod).andPayTypeEqualTo(payTypeStr).andRouteCodeEqualTo(routeCode).andDelFlagEqualTo("0");
			prePayMemberList = prePayMemberService.selectByExample(prePayMemberExample);
		}
		if(prePayMemberList!=null && prePayMemberList.size()>0){//预收款模式
			result.put("preType", "1");
			PrePayStatisticsExample prePayStatisticsExample = new PrePayStatisticsExample();
			prePayStatisticsExample.createCriteria().andMemberIdEqualTo(memberId).andDelFlagEqualTo("0");
			List<PrePayStatistics> prePayStatisticsServiceList = prePayStatisticsService.selectByExample(prePayStatisticsExample);
			if(prePayStatisticsServiceList==null||prePayStatisticsServiceList.size()==0){
				result.put("returnCode", "0008");
				result.put("returnMsg", "预收款金额不足，无法支付");
				return result;
			}
			PrePayStatistics prePayStatistics = prePayStatisticsServiceList.get(0);
			BigDecimal preMoney = prePayStatistics.getPreMoney();
			//BigDecimal totalMoney = preMoney.divide(tradeRate);
			//BigDecimal hisTradeMoney = prePayStatistics.getHisTradeMoney();
			BigDecimal hisUsePreMoney  = prePayStatistics.getHisUsePreMoney();
			BigDecimal thisRate = tradeMoney.multiply(tradeRate);
			
			String day = DateUtil.getDateStr(new Date());
			Map<String,Object> paramMap = new HashMap<String, Object>();
			paramMap.put("memberId", memberId);
			paramMap.put("txnDate", day);
			paramMap.put("preType", "1");
			Double todayRateMoney = tradeDetailService.countTransactionRateByCondition(paramMap);
			todayRateMoney = todayRateMoney == null ? 0 : todayRateMoney;//当天交易费率
			
			paramMap = new HashMap<String, Object>();
			paramMap.put("memberId", memberId);
			paramMap.put("preType", "1");
			paramMap.put("status", "0");
			paramMap.put("qrorderDealStatus", "0");
			Double undealRate = debitNoteService.countTransactionRateByCondition(paramMap);
			undealRate = undealRate == null ? 0 : undealRate;//未处理金额费率
			
			if(preMoney.compareTo(hisUsePreMoney.add(new BigDecimal(todayRateMoney)).add(new BigDecimal(undealRate)).add(thisRate))<0){
				result.put("returnCode", "0008");
				result.put("returnMsg", "预收款金额不足，无法支付");
				return result;
			}
		}else{
			result.put("preType", "0");
		}
		result.put("returnCode", "0000");
		return result;
	}
	
	public JSONObject checkSingleDrawLimit(String configName, BigDecimal drawMoney){
		
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
				if (drawMoney.compareTo(singleLimit) > 0) {
					result.put("returnCode", "4004");
					result.put("returnMsg", "代付单笔限额为"+singleLimit+"元,当前金额已超过");
					return result;
				}
			}
		}
		return null;
	}

	public JSONObject checkLimitMerchantMoney(String routeId,String merchantCode,String payType){
		
		JSONObject result = new JSONObject();
		String configName = "LIMIT_MERCHANT_"+routeId+"_"+merchantCode+"_"+payType;
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
				String txnType = transPayType(payType);
				param.put("txnType", txnType);
				Double count = tradeDetailDailyService.countTradeDetailDaily(param);
				count = count == null ? 0 : count;
				if (new BigDecimal(count).compareTo(merchantLimit) >= 0) {
					result.put("returnCode", "4004");
					result.put("returnMsg", "已超过商户当日交易总金额，无法交易");
					return result;
				}
			}
		}
		return null;
	}
	
	
	public List<SubMerchantCode> getSubMerchantCodeList( String routeCode,BigDecimal tradeMoney){
		
		String value = "60";
		SysCommonConfigExample sysCommonConfigExample = new SysCommonConfigExample();
		sysCommonConfigExample.or().andNameEqualTo("SUB_MERCHANT_CODE_TIME_"+routeCode).andDelFlagEqualTo("0");
		List<SysCommonConfig> sysCommonConfig = sysCommonConfigService.selectByExample(sysCommonConfigExample);
		if (sysCommonConfig != null && sysCommonConfig.size() > 0) {
			value = sysCommonConfig.get(0).getValue();
		}
		
		String limitMoney = "";
		sysCommonConfigExample = new SysCommonConfigExample();
		sysCommonConfigExample.or().andNameEqualTo("LIMIT_SUB_MERCHANT_MONEY_"+routeCode).andDelFlagEqualTo("0");
		sysCommonConfig = sysCommonConfigService.selectByExample(sysCommonConfigExample);
		if (sysCommonConfig != null && sysCommonConfig.size() > 0) {
			limitMoney = sysCommonConfig.get(0).getValue();
		}
		
		if(!"".equals(value) && !"0".equals(value)&&!"".equals(limitMoney)){
			String tradeDate = DateUtil.getDateFormat(new Date(), "yyyyMMdd");
			String nowTime = DateUtil.getDateFormat(new Date(), "HHmmss");
			Map<String,Object> param = new HashMap<String, Object>();
			param.put("routeCode", routeCode);
			param.put("nowTime", nowTime);
			param.put("tradeDate", tradeDate);
			param.put("seconds", value);
			param.put("totalMoney", new BigDecimal(limitMoney).subtract(tradeMoney));
			List<SubMerchantCode> list = subMerchantCodeService.selectByMap(param);
			return list;
		}
		return null;
	}
	//IP取子商户
	public JSONObject getIpSubMerchant(String payMethod, String payType, Integer memberId ,String routeCode, String ip,String ipReal){
		JSONObject result = new JSONObject();
		String txnType = transPayType(payType);
		String configName = "IP_BLACK_TIME_"+routeCode+"_"+payType;
		String second = "";
		SysCommonConfigExample sysCommonConfigExample = new SysCommonConfigExample();
		sysCommonConfigExample.or().andNameEqualTo(configName).andDelFlagEqualTo("0");
		List<SysCommonConfig> sysCommonConfig = sysCommonConfigService.selectByExample(sysCommonConfigExample);
		if (sysCommonConfig != null && sysCommonConfig.size() > 0) {
			second = sysCommonConfig.get(0).getValue();
		}
		if(!"".equals(second)){
			Map<String,Object> param = new HashMap<String, Object>();
			param.put("ip", ip);
			param.put("routeCode", routeCode);
			param.put("txnType", txnType);
			param.put("seconds", second);
			int blackCount = ipBlackListService.countBlack(param);
			if(blackCount>0){
				result.put("returnCode", "4004");
				result.put("returnMsg", "IP"+ip+"无支付权限");
				return result;
			}
		}
		if(PayTypeConstant.PAY_METHOD_SMZF.equals(payMethod)&&"".equals(ipReal)){//扫码支付  
			return null;
		}
		configName = "LIMIT_CONTINUE_FAIL_COUNTS_"+routeCode+"_"+payType;
		String value = "";
		sysCommonConfigExample = new SysCommonConfigExample();
		sysCommonConfigExample.or().andNameEqualTo(configName).andDelFlagEqualTo("0");
		sysCommonConfig = sysCommonConfigService.selectByExample(sysCommonConfigExample);
		if (sysCommonConfig != null && sysCommonConfig.size() > 0) {
			value = sysCommonConfig.get(0).getValue();
		}
		
		String subMerchantCode = "";
		Map<String,Object> param = new HashMap<String, Object>();
		param.put("ip", ip);
		param.put("routeId", routeCode);
		param.put("txnType", txnType);
		param.put("createDate", DateUtil.getDateFormat(new Date(), "yyyy-MM-dd")+" 00:00:00");
		List<DebitNoteIp> debitNoteIpList = debitNoteIpService.selectByIp(param);//取黑名单后最近的订单
		int count = 0;
		if(debitNoteIpList!=null&&debitNoteIpList.size()>0){
			DebitNoteIp debitNoteIp = debitNoteIpList.get(0);
			if("1".equals(debitNoteIp.getStatus())){//成功
				return null;
			}
			for(int i=0;i<debitNoteIpList.size();i++){
				debitNoteIp = debitNoteIpList.get(i);
				if("1".equals(debitNoteIp.getStatus())){//成功
					break;
				}
				if("0".equals(debitNoteIp.getStatus())||"2".equals(debitNoteIp.getStatus())){
					subMerchantCode = debitNoteIp.getSubMerchantCode();
					count++;
				}
				
			}
		}else{//判断是否有在临时黑名单
			IpBlackListExample ipBlackListExample = new IpBlackListExample();
			ipBlackListExample.createCriteria().andIpEqualTo(ip).andRouteCodeEqualTo(routeCode).andTxnTypeEqualTo(txnType).andBlackTypeEqualTo("1").andDelFlagEqualTo("0");
			ipBlackListExample.setOrderByClause(" create_date DESC");
			List<IpBlackList> ipBlackList = ipBlackListService.selectByExample(ipBlackListExample);
			if(ipBlackList == null || ipBlackList.size() == 0){
				return null;
			}
			
			//取临时黑名单前最新的订单
			DebitNoteIpExample debitNoteIpExample = new DebitNoteIpExample();
			debitNoteIpExample.createCriteria().andIpEqualTo(ip).andRouteIdEqualTo(routeCode).andTxnTypeEqualTo(txnType).andCreateDateLessThanOrEqualTo(ipBlackList.get(0).getCreateDate());
			debitNoteIpExample.setOrderByClause(" create_date DESC");
			List<DebitNoteIp> noteIpList = debitNoteIpService.selectByExample(debitNoteIpExample);
			if(noteIpList == null || noteIpList.size() == 0){
				return null;
			}
			subMerchantCode = noteIpList.get(0).getSubMerchantCode();
		}
		if(!"".equals(value)){
			if(count>=Integer.parseInt(value)){
				IpBlackList ipBlackList = new IpBlackList();
				ipBlackList.setTxnType(txnType);
				//ipBlackList.setTxnMethod(payMethod);
				ipBlackList.setIp(ip);
				ipBlackList.setMemberId(memberId);
				ipBlackList.setRouteCode(routeCode);
				ipBlackList.setBlackType("1");
				ipBlackList.setCreateDate(new Date());
				ipBlackList.setDelFlag("0");
				ipBlackListService.insertSelective(ipBlackList);
				result.put("returnCode", "4004");
				result.put("returnMsg", "IP"+ip+"无支付权限");
				return result;
			}
		}
		if(!"".equals(subMerchantCode)){
			Map<String,Object> param1 = new HashMap<String, Object>();
			param1.put("subMerchantCode", subMerchantCode);
			param1.put("tradeDate", DateUtil.getDateFormat(new Date(), "yyyyMMdd"));
			int counts = subMerchantBlackListService.getSubMerchantBlackCount(param1);
			if(counts>0){//在商户黑名单中，重新取
				return null;
			}
			SubMerchantCodeExample subMerchantCodeExample = new SubMerchantCodeExample();
			subMerchantCodeExample.createCriteria().andSubMerchantCodeEqualTo(subMerchantCode).andDelFlagEqualTo("0");
			List<SubMerchantCode> subList = subMerchantCodeService.selectByExample(subMerchantCodeExample);
			if(subList!=null && subList.size()>0){
				result.put("returnCode", "0000");
				result.put("subMerchantCode", subMerchantCode);
				result.put("name", subList.get(0).getName());
				return result;
			}
		}
		
		return null;
	}
	//判断子商户黑名单
	public JSONObject checkSubMerchantBlackList(String subMerchantCode){
		JSONObject result = new JSONObject();
		
		SubMerchantBlackListExample subMerchantBlackListExample = new SubMerchantBlackListExample();
		subMerchantBlackListExample.createCriteria().andBlackTypeEqualTo("2").andSubMerchantCodeEqualTo(subMerchantCode).andDelFlagEqualTo("0");
		List<SubMerchantBlackList> bllist = subMerchantBlackListService.selectByExample(subMerchantBlackListExample);
		if(bllist !=null && bllist.size()>0){//商户永久黑名单
			result.put("returnCode", "4001");
			result.put("returnMsg", "交易权限不足");
			return result;
		}
		
		subMerchantBlackListExample = new SubMerchantBlackListExample();
		subMerchantBlackListExample.createCriteria().andBlackTypeEqualTo("1").andSubMerchantCodeEqualTo(subMerchantCode).andTradeDateEqualTo(new SimpleDateFormat("yyyyMMdd").format(new Date())).andDelFlagEqualTo("0");
		bllist = subMerchantBlackListService.selectByExample(subMerchantBlackListExample);
		if(bllist !=null && bllist.size()>0){//商户临时黑名单
			result.put("returnCode", "4002");
			result.put("returnMsg", "交易权限不足");
			return result;
		}
		return null;
	}
	//判断子商户限额
	public JSONObject checkSubMerchantLimitMoney(String subMerchantCode ,String tradeMoney , String routeCode){
		JSONObject result = new JSONObject();
		
		String limitMoney = "";
		SysCommonConfigExample sysCommonConfigExample = new SysCommonConfigExample();
		sysCommonConfigExample.or().andNameEqualTo("LIMIT_SUB_MERCHANT_MONEY_"+routeCode).andDelFlagEqualTo("0");
		List<SysCommonConfig> sysCommonConfig = sysCommonConfigService.selectByExample(sysCommonConfigExample);
		if (sysCommonConfig != null && sysCommonConfig.size() > 0) {
			limitMoney = sysCommonConfig.get(0).getValue();
		}
		
		if(!"".equals(limitMoney)){
			SubMerchantTotalExample subMerchantTotalExample = new SubMerchantTotalExample();
			subMerchantTotalExample.createCriteria().andSubMerchantCodeEqualTo(subMerchantCode).andTradeDateEqualTo(new SimpleDateFormat("yyyyMMdd").format(new Date())).andDelFlagEqualTo("0");
			List<SubMerchantTotal> totalList = subMerchantTotalService.selectByExample(subMerchantTotalExample);
			if(totalList!=null&&totalList.size()>0){
				if(totalList.get(0).getTotalMoney().add(new BigDecimal(tradeMoney)).compareTo(new BigDecimal(limitMoney))>0){
					result.put("returnCode", "4004");
					result.put("returnMsg", "交易权限不足");
					return result;
				}
			}
		}
		return null;
	}
	
	public JSONObject checkLimitMoney(String payMethod,String payType,String routeCode,String memberCode, BigDecimal tradeMoney){
		
		JSONObject result = new JSONObject();
		String configName =  "SINGLE_LIMIT_"+routeCode+"_"+payMethod+"_"+payType+"_"+memberCode;
		String value = "";
		SysCommonConfigExample sysCommonConfigExample = new SysCommonConfigExample();
		sysCommonConfigExample.or().andNameEqualTo(configName).andDelFlagEqualTo("0");
		List<SysCommonConfig> sysCommonConfig = sysCommonConfigService.selectByExample(sysCommonConfigExample);
		if (sysCommonConfig != null && sysCommonConfig.size() > 0) {
			value = sysCommonConfig.get(0).getValue();
		}
		if("".equals(value)){
			configName =  "SINGLE_LIMIT_"+routeCode+"_"+payMethod+"_"+payType;
			sysCommonConfigExample = new SysCommonConfigExample();
			sysCommonConfigExample.or().andNameEqualTo(configName).andDelFlagEqualTo("0");
			sysCommonConfig = sysCommonConfigService.selectByExample(sysCommonConfigExample);
			if (sysCommonConfig != null && sysCommonConfig.size() > 0) {
				value = sysCommonConfig.get(0).getValue();
			}
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
	
	public JSONObject checkMinMoney(String payMethod,String payType,String routeCode,String memberCode, BigDecimal tradeMoney){
		
		JSONObject result = new JSONObject();
		
		String configName =  "SINGLE_MIN_"+routeCode+"_"+payMethod+"_"+payType+"_"+memberCode;
		String value = "";
		SysCommonConfigExample sysCommonConfigExample = new SysCommonConfigExample();
		sysCommonConfigExample.or().andNameEqualTo(configName).andDelFlagEqualTo("0");
		List<SysCommonConfig> sysCommonConfig = sysCommonConfigService.selectByExample(sysCommonConfigExample);
		if (sysCommonConfig != null && sysCommonConfig.size() > 0) {
			value = sysCommonConfig.get(0).getValue();
		}
		if("".equals(value)){
			configName =  "SINGLE_MIN_"+routeCode+"_"+payMethod+"_"+payType;
			sysCommonConfigExample = new SysCommonConfigExample();
			sysCommonConfigExample.or().andNameEqualTo(configName).andDelFlagEqualTo("0");
			sysCommonConfig = sysCommonConfigService.selectByExample(sysCommonConfigExample);
			if (sysCommonConfig != null && sysCommonConfig.size() > 0) {
				value = sysCommonConfig.get(0).getValue();
			}
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
}
