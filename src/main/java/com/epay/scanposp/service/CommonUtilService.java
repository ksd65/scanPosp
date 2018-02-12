package com.epay.scanposp.service;

import java.math.BigDecimal;
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
import com.epay.scanposp.entity.MemberIpRule;
import com.epay.scanposp.entity.MemberIpRuleExample;
import com.epay.scanposp.entity.MemberIpWhiteList;
import com.epay.scanposp.entity.MemberIpWhiteListExample;
import com.epay.scanposp.entity.PayType;
import com.epay.scanposp.entity.PayTypeExample;
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

}