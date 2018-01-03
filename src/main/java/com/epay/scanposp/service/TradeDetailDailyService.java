package com.epay.scanposp.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.epay.scanposp.common.base.BaseService;
import com.epay.scanposp.dao.TradeDetailDailyMapper;
import com.epay.scanposp.entity.TradeDetailDaily;
import com.epay.scanposp.entity.TradeDetailDailyExample;

@Service
@Transactional
public class TradeDetailDailyService extends BaseService<TradeDetailDaily,TradeDetailDailyExample> {
	@Autowired
	private TradeDetailDailyMapper tradeDetailDailyMapper;
	
	@Autowired
	public void setTradeDetailDailyMapper(TradeDetailDailyMapper tradeDetailDailyMapper) {
		super.setDao(tradeDetailDailyMapper);
		this.tradeDetailDailyMapper = tradeDetailDailyMapper;
	}

	public Double countTradeDetailDaily(Map<String, Object> paramMap){
		return tradeDetailDailyMapper.countTradeDetailDaily(paramMap);
	}
}
