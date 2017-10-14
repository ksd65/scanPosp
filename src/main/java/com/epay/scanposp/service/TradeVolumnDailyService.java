package com.epay.scanposp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.epay.scanposp.common.base.BaseService;
import com.epay.scanposp.dao.TradeVolumnDailyMapper;
import com.epay.scanposp.entity.TradeVolumnDaily;
import com.epay.scanposp.entity.TradeVolumnDailyExample;

@Service
@Transactional
public class TradeVolumnDailyService extends BaseService<TradeVolumnDaily,TradeVolumnDailyExample> {
	@Autowired
	private TradeVolumnDailyMapper tradeVolumnDailyMapper;
	
	@Autowired
	public void setTradeVolumnDailyMapper(TradeVolumnDailyMapper tradeVolumnDailyMapper) {
		super.setDao(tradeVolumnDailyMapper);
		this.tradeVolumnDailyMapper = tradeVolumnDailyMapper;
	}

}
