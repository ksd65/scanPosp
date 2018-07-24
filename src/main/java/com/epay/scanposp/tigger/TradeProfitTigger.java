package com.epay.scanposp.tigger;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.epay.scanposp.common.utils.DateUtil;
import com.epay.scanposp.common.utils.constant.PayTypeConstant;
import com.epay.scanposp.common.utils.constant.RouteCodeConstant;
import com.epay.scanposp.entity.JobRun;
import com.epay.scanposp.entity.MemberDrawRoute;
import com.epay.scanposp.entity.MemberDrawRouteExample;
import com.epay.scanposp.entity.MemberPlatFee;
import com.epay.scanposp.entity.MemberPlatFeeExample;
import com.epay.scanposp.entity.RoutewayDrawProfit;
import com.epay.scanposp.entity.TradeProfit;
import com.epay.scanposp.entity.TradeProfitExample;
import com.epay.scanposp.service.CommonService;
import com.epay.scanposp.service.JobRunService;
import com.epay.scanposp.service.MemberDrawRouteService;
import com.epay.scanposp.service.MemberInfoService;
import com.epay.scanposp.service.MemberPlatFeeService;
import com.epay.scanposp.service.RoutewayDrawProfitService;
import com.epay.scanposp.service.TradeProfitService;

public class TradeProfitTigger {
	
	private static Logger logger = LoggerFactory.getLogger(TradeProfitTigger.class);
	
	@Autowired
	private CommonService commonService;
	
	@Autowired
	private MemberInfoService memberInfoService;
	
	@Autowired
	private TradeProfitService tradeProfitService;
	
	@Autowired
	private MemberPlatFeeService memberPlatFeeService;
	
	@Autowired
	private MemberDrawRouteService memberDrawRouteService;
	
	@Autowired
	private RoutewayDrawProfitService routewayDrawProfitService;
	
	@Autowired
	private JobRunService jobRunService;
	
	public void tradeProfit(){
		
		try{
			logger.info("商户通道交易额更新定时。。。");
			String yesterday = DateUtil.getBeforeDate(1, "yyyyMMdd");
			JobRun jobRun = new JobRun();
			jobRun.setTxnDate(yesterday);
			jobRun.setJobType("TradeProfitTigger");
			jobRunService.insertJobRun(jobRun);
			
			Map<String,Object> paramMap = new HashMap<String, Object>();
			paramMap.put("txnDate", yesterday);
			List<Map<String,Object>> tradeList = tradeProfitService.getTradeList(paramMap);
			if(tradeList!=null && tradeList.size()>0){
				for(Map<String,Object> tradeMap : tradeList){
					Integer memberId  = ((Long)tradeMap.get("member_id")).intValue();
					String routeId  = (String)tradeMap.get("route_id");
					String merchantCode  = (String)tradeMap.get("merchant_code");
					String txnMethod  = (String)tradeMap.get("txn_method");
					String txnType  = (String)tradeMap.get("txn_type");
					BigDecimal tradeMoney  = (BigDecimal)tradeMap.get("trade_money");
					BigDecimal memberCost  = (BigDecimal)tradeMap.get("member_cost");//商户成本
					System.out.println(memberId+" "+routeId+" "+" "+merchantCode+" "+txnMethod+" "+txnType+" "+tradeMoney.doubleValue()+" "+memberCost.doubleValue());
					if(routeId.equals(RouteCodeConstant.YS_ROUTE_CODE)||routeId.equals(RouteCodeConstant.ML_ROUTE_CODE)){
						continue;
					}
					MemberPlatFeeExample memberPlatFeeExample = new MemberPlatFeeExample();
					memberPlatFeeExample.createCriteria().andMemberIdEqualTo(memberId).andPayTypeEqualTo(txnMethod).andPayModeEqualTo(txnType).andRouteCodeEqualTo(routeId).andMerchantCodeEqualTo(merchantCode);
					List<MemberPlatFee> feeList = memberPlatFeeService.selectByExample(memberPlatFeeExample);
					if(feeList==null || feeList.size()==0){
						memberPlatFeeExample = new MemberPlatFeeExample();
						memberPlatFeeExample.createCriteria().andMemberIdEqualTo(memberId).andPayTypeEqualTo(txnMethod).andPayModeEqualTo(txnType).andRouteCodeEqualTo(routeId);
						feeList = memberPlatFeeService.selectByExample(memberPlatFeeExample);
						merchantCode = "";
					}
					BigDecimal platFee = new BigDecimal(0);
					BigDecimal agentFee = new BigDecimal(0);
					BigDecimal memberFee = new BigDecimal(0);
					BigDecimal realPlatFee = new BigDecimal(0);
					BigDecimal agentFeeLevel2 = new BigDecimal(0);
					String agentOfficeId = "";
					if(feeList!=null && feeList.size()>0){
						MemberPlatFee memberPlatFee = feeList.get(0);
						platFee = memberPlatFee.getPlatFee();
						agentFee = memberPlatFee.getAgentFee();
						agentFeeLevel2 = memberPlatFee.getAgentFeeLevel2();
						memberFee = memberPlatFee.getMemberFee();
						agentOfficeId = memberPlatFee.getAgentOfficeId();
						realPlatFee = memberPlatFee.getPlatFee();
						if(routeId.equals(RouteCodeConstant.TLWD_ROUTE_CODE)){//新通联支付宝h5
							if(txnMethod.equals(PayTypeConstant.PAY_METHOD_H5)&&"2".equals(txnType)){
								realPlatFee = platFee.add(new BigDecimal(0.012));
							}
						}else if(routeId.equals(RouteCodeConstant.TLKJ_ROUTE_CODE)){
							//realPlatFee = platFee.add(agentFee.subtract(platFee).divide(new BigDecimal(2)));
							realPlatFee = platFee.add(new BigDecimal(0.0001));
							memberCost = tradeMoney.multiply(memberFee);
						}
					}
					
					Double drawPer = 1d;//分润比例
					MemberDrawRouteExample memberDrawRouteExample = new MemberDrawRouteExample();
					memberDrawRouteExample.createCriteria().andMemberIdEqualTo(memberId).andRouteCodeEqualTo(routeId).andDelFlagEqualTo("0");
					List<MemberDrawRoute> routeList = memberDrawRouteService.selectByExample(memberDrawRouteExample);
					if(routeList==null||routeList.size()==0){
						memberDrawRouteExample = new MemberDrawRouteExample();
						memberDrawRouteExample.createCriteria().andMemberIdEqualTo(0).andRouteCodeEqualTo(routeId).andDelFlagEqualTo("0");
						routeList = memberDrawRouteService.selectByExample(memberDrawRouteExample);
					}
					if(routeList!=null&&routeList.size()>0){
						drawPer = routeList.get(0).getD0Percent().doubleValue();
					}
					
					BigDecimal platCost = tradeMoney.multiply(platFee);//平台成本
					BigDecimal realPlatCost = tradeMoney.multiply(realPlatFee);//实际平台成本
					BigDecimal agentCost = tradeMoney.multiply(agentFee);//代理商成本
					BigDecimal agentCostLevel2 = tradeMoney.multiply(agentFeeLevel2);//二级代理商成本
					BigDecimal agentProfitAll = memberCost.subtract(agentCost);//代理商收益
					BigDecimal agentProfit = agentProfitAll.multiply(new BigDecimal(drawPer));//代理商可分润
					BigDecimal agentProfitD1 = agentProfitAll.subtract(agentProfit);//代理商D1分润
					TradeProfitExample tradeProfitExample = new TradeProfitExample();
					if("".equals(merchantCode)){
						tradeProfitExample.createCriteria().andMemberIdEqualTo(memberId).andRouteCodeEqualTo(routeId).andTxnDateEqualTo(yesterday).andTxnMethodEqualTo(txnMethod).andTxnTypeEqualTo(txnType).andDelFlagEqualTo("0");
					}else{
						tradeProfitExample.createCriteria().andMemberIdEqualTo(memberId).andRouteCodeEqualTo(routeId).andMerchantCodeEqualTo(merchantCode).andTxnDateEqualTo(yesterday).andTxnMethodEqualTo(txnMethod).andTxnTypeEqualTo(txnType).andDelFlagEqualTo("0");
					}
					List<TradeProfit> profitList = tradeProfitService.selectByExample(tradeProfitExample);
					if(profitList!=null && profitList.size()>0){
						TradeProfit profit = profitList.get(0);
						profit.setTradeMoney(tradeMoney.add(profit.getTradeMoney()));
						profit.setPlatCost(platCost.add(profit.getPlatCost()));
						profit.setAgentCost(agentCost.add(profit.getAgentCost()));
						profit.setMemberCost(memberCost.add(profit.getMemberCost()));
						profit.setAgentProfit(agentProfit.add(profit.getAgentProfit()));
						profit.setAgentProfitD1(agentProfitD1.add(profit.getAgentProfitD1()));
						profit.setRealPlatCost(realPlatCost.add(profit.getRealPlatCost()));
						profit.setAgentCostLevel2(agentCostLevel2.add(profit.getAgentCostLevel2()));
						profit.setUpdateDate(new Date());
						tradeProfitService.updateByPrimaryKey(profit);
					}else{
						TradeProfit profit = new TradeProfit();
						profit.setMemberId(memberId);
						profit.setTxnDate(yesterday);
						profit.setTxnMethod(txnMethod);
						profit.setTxnType(txnType);
						profit.setRouteCode(routeId);
						profit.setMerchantCode(merchantCode);
						profit.setTradeMoney(tradeMoney);
						profit.setPlatTradeRate(platFee);
						profit.setAgentTradeRate(agentFee);
						profit.setMemberTradeRate(memberFee);
						profit.setPlatCost(platCost);
						profit.setAgentCost(agentCost);
						profit.setMemberCost(memberCost);
						profit.setDrawPer(new BigDecimal(drawPer));
						profit.setAgentProfit(agentProfit);
						profit.setAgentProfitD1(agentProfitD1);
						profit.setAgentOfficeId(agentOfficeId);
						profit.setCreateDate(new Date());
						profit.setDelFlag("0");
						profit.setRealPlatTradeRate(realPlatFee);
						profit.setRealPlatCost(realPlatCost);
						profit.setAgentTradeRateLevel2(agentFeeLevel2);
						profit.setAgentCostLevel2(agentCostLevel2);
						tradeProfitService.insertSelective(profit);
					}
					
				} 
			}
		
			paramMap = new HashMap<String, Object>();
			paramMap.put("txnDate", yesterday);
			
			List<Map<String,Object>> drawList = routewayDrawProfitService.getDrawList(paramMap);
			if(drawList!=null && drawList.size()>0){
				for(Map<String,Object> drawMap : drawList){
					Integer memberId  = ((Long)drawMap.get("member_id")).intValue();
					String routeId  = (String)drawMap.get("route_code");
					BigDecimal money  = (BigDecimal)drawMap.get("money");
					BigDecimal drawProfit  = (BigDecimal)drawMap.get("drawProfit");
					System.out.println(memberId+" "+routeId+" "+" "+money+" "+drawProfit);
					if(routeId.equals(RouteCodeConstant.YS_ROUTE_CODE)){
						continue;
					}
					
					
					MemberPlatFeeExample memberPlatFeeExample = new MemberPlatFeeExample();
					memberPlatFeeExample.createCriteria().andMemberIdEqualTo(memberId).andRouteCodeEqualTo(routeId);
					List<MemberPlatFee> feeList = memberPlatFeeService.selectByExample(memberPlatFeeExample);
					String agentOfficeId = "";
					if(feeList!=null && feeList.size()>0){
						MemberPlatFee memberPlatFee = feeList.get(0);
						agentOfficeId = memberPlatFee.getAgentOfficeId();
					}
					
					RoutewayDrawProfit routewayDrawProfit = new RoutewayDrawProfit();
					routewayDrawProfit.setMemberId(memberId);
					routewayDrawProfit.setTxnDate(yesterday);
					routewayDrawProfit.setRouteCode(routeId);
					routewayDrawProfit.setMoney(money);
					routewayDrawProfit.setProfit(drawProfit);
					routewayDrawProfit.setAgentOfficeId(agentOfficeId);
					routewayDrawProfit.setCreateDate(new Date());
					routewayDrawProfit.setDelFlag("0");
					routewayDrawProfitService.insertSelective(routewayDrawProfit);
					
				}
			}
		
		}catch(Exception e){
			logger.error(e.getMessage());
		}
	}
}
