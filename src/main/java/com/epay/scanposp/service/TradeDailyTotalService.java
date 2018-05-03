package com.epay.scanposp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.epay.scanposp.common.base.BaseService;
import com.epay.scanposp.dao.TradeDailyTotalMapper;
import com.epay.scanposp.entity.TradeDailyTotal;
import com.epay.scanposp.entity.TradeDailyTotalExample;

@Service
@Transactional
public class TradeDailyTotalService extends BaseService<TradeDailyTotal,TradeDailyTotalExample> {
	@Autowired
	private TradeDailyTotalMapper tradeDailyTotalMapper;
	
	@Autowired
	public void setTradeDailyTotalMapper(TradeDailyTotalMapper tradeDailyTotalMapper) {
		super.setDao(tradeDailyTotalMapper);
		this.tradeDailyTotalMapper = tradeDailyTotalMapper;
	}

}
