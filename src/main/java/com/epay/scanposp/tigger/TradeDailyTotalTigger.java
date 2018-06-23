package com.epay.scanposp.tigger;

import java.math.BigDecimal;
import java.util.ArrayList;
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
import com.epay.scanposp.entity.MemberInfo;
import com.epay.scanposp.entity.MemberInfoExample;
import com.epay.scanposp.entity.MemberPayType;
import com.epay.scanposp.entity.MemberPayTypeExample;
import com.epay.scanposp.entity.TradeDailyTotal;
import com.epay.scanposp.service.CommonService;
import com.epay.scanposp.service.JobRunService;
import com.epay.scanposp.service.MemberInfoService;
import com.epay.scanposp.service.MemberPayTypeService;
import com.epay.scanposp.service.TradeDailyTotalService;

public class TradeDailyTotalTigger {
	
	private static Logger logger = LoggerFactory.getLogger(TradeDailyTotalTigger.class);
	
	@Autowired
	private CommonService commonService;
	
	@Autowired
	private MemberInfoService memberInfoService;
	
	@Autowired
	private JobRunService jobRunService;
	
	@Autowired
	private TradeDailyTotalService tradeDailyTotalService;
	
	@Autowired
	private MemberPayTypeService memberPayTypeService;
	
	public void tradeDailyTotal(){
		
		try{
			logger.info("商户通道交易额更新定时。。。");
			
			MemberInfoExample memberInfoExample = new MemberInfoExample();
			memberInfoExample.createCriteria().andDelFlagEqualTo("0").andWxRouteIdNotEqualTo(RouteCodeConstant.YS_ROUTE_CODE);
			List<MemberInfo> memberList = memberInfoService.selectByExample(memberInfoExample);
			if(memberList!=null && memberList.size()>0){
				String yesterday = DateUtil.getBeforeDate(1, "yyyyMMdd");
				
				JobRun jobRun = new JobRun();
				jobRun.setTxnDate(yesterday);
				jobRun.setJobType("TradeDailyTotalTigger");
				jobRunService.insertJobRun(jobRun);
				
				List<String> routeList = new ArrayList<String>();
				routeList.add(RouteCodeConstant.ESKXF_ROUTE_CODE);
				routeList.add(RouteCodeConstant.ESK_ROUTE_CODE);
				routeList.add(RouteCodeConstant.TLWD_ROUTE_CODE);
				routeList.add(RouteCodeConstant.ESKKJ_ROUTE_CODE);
				routeList.add(RouteCodeConstant.ESKWG_ROUTE_CODE);
				routeList.add(RouteCodeConstant.ESKWGD0_ROUTE_CODE);
				routeList.add(RouteCodeConstant.TLKJ_ROUTE_CODE);
				
				if(routeList!=null && routeList.size()>0){
					for(String routeCode:routeList){
						for(MemberInfo member:memberList){
							Integer memberId = member.getId();
						    Map<String,Object> paramMap = new HashMap<String, Object>();
							paramMap = new HashMap<String, Object>();
							paramMap.put("memberId", memberId);
							paramMap.put("routeId", routeCode);
							paramMap.put("startDate", yesterday);
							paramMap.put("endDate", yesterday);
							
							Double tradeMoney = commonService.countTransactionMoneyByCondition(paramMap);
							tradeMoney = tradeMoney == null ? 0 : tradeMoney;
							
							Double settleMoney = 0d;
							if(!routeCode.equals(RouteCodeConstant.TLKJ_ROUTE_CODE)){
								settleMoney = commonService.countTransactionRealMoneyByCondition(paramMap);
							}else{
								MemberPayTypeExample memberPayTypeExample = new MemberPayTypeExample();
								memberPayTypeExample.createCriteria().andMemberIdEqualTo(memberId).andPayMethodEqualTo(PayTypeConstant.PAY_METHOD_YL).andPayTypeEqualTo(PayTypeConstant.PAY_TYPE_KJ).andDelFlagEqualTo("0");
								List<MemberPayType> memberPayTypeList =  memberPayTypeService.selectByExample(memberPayTypeExample);
								if(memberPayTypeList==null || memberPayTypeList.size()==0){
									continue;
								}
								MemberPayType memberPayType = memberPayTypeList.get(0);
								paramMap.put("memberRate", memberPayType.getT0TradeRate());
								settleMoney = commonService.countMemberProfitMoneyByCondition(paramMap);
							}
							settleMoney = settleMoney == null ? 0 : settleMoney;
						
							if(settleMoney!=0||tradeMoney!=0){
								TradeDailyTotal tradeDailyTotal = new TradeDailyTotal();
								tradeDailyTotal.setMemberId(memberId);
								tradeDailyTotal.setTxnDate(yesterday);
								tradeDailyTotal.setRouteCode(routeCode);
								tradeDailyTotal.setTradeMoney(new BigDecimal(tradeMoney));
								tradeDailyTotal.setSettleMoney(new BigDecimal(settleMoney));
								tradeDailyTotal.setDelFlag("0");
								tradeDailyTotal.setCreateDate(new Date());
								tradeDailyTotalService.insertSelective(tradeDailyTotal);
							}
						
						}
					}
				}
				
				
			}
		}catch(Exception e){
			logger.error(e.getMessage());
		}
	}
}
