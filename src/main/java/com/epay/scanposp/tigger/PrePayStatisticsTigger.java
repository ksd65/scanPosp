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
import com.epay.scanposp.entity.PrePayStatistics;
import com.epay.scanposp.entity.PrePayStatisticsExample;
import com.epay.scanposp.service.PrePayStatisticsService;
import com.epay.scanposp.service.TradeDetailService;

public class PrePayStatisticsTigger {
	
	private static Logger logger = LoggerFactory.getLogger(PrePayStatisticsTigger.class);
	
	
	@Autowired
	private PrePayStatisticsService prePayStatisticsService;
	
	@Autowired
	private TradeDetailService tradeDetailService;
	
	public void prePayStatistics(){
		
		try{
			logger.info("预收款金额更新定时。。。");
			PrePayStatisticsExample prePayStatisticsExample = new PrePayStatisticsExample();
			prePayStatisticsExample.createCriteria().andDelFlagEqualTo("0");
			List<PrePayStatistics> prePayStatisticsList =  prePayStatisticsService.selectByExample(prePayStatisticsExample);
			if(prePayStatisticsList!=null && prePayStatisticsList.size()>0){
				for(PrePayStatistics prePayStatistics : prePayStatisticsList){
					BigDecimal hisTradeMoney =  prePayStatistics.getHisTradeMoney();
					BigDecimal hisUsePreMoney =  prePayStatistics.getHisUsePreMoney();
					
					String day = DateUtil.getBeforeDate(1,"yyyyMMdd");
					Map<String,Object> paramMap = new HashMap<String, Object>();
					paramMap.put("memberId", prePayStatistics.getMemberId());
					paramMap.put("txnDate", day);
					paramMap.put("preType", "1");
					Double yesRateMoney = tradeDetailService.countTransactionRateByCondition(paramMap);
					yesRateMoney = yesRateMoney == null ? 0 : yesRateMoney;//前一天交易费率
					
					Double yesTradeMoney = tradeDetailService.countTransactionMoneyByCondition(paramMap);
					yesTradeMoney = yesTradeMoney == null ? 0 : yesTradeMoney;//前一天交易金额
					
					prePayStatistics.setHisTradeMoney(hisTradeMoney.add(new BigDecimal(yesTradeMoney)));
					prePayStatistics.setHisUsePreMoney(hisUsePreMoney.add(new BigDecimal(yesRateMoney)));
					prePayStatistics.setUpdateDate(new Date());
					prePayStatisticsService.updateByPrimaryKeySelective(prePayStatistics);
				}
			}
			
		}catch(Exception e){
			logger.error(e.getMessage());
		}
	}
}
