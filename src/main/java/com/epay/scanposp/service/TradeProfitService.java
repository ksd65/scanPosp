package com.epay.scanposp.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.epay.scanposp.common.base.BaseService;
import com.epay.scanposp.dao.TradeProfitMapper;
import com.epay.scanposp.entity.TradeProfit;
import com.epay.scanposp.entity.TradeProfitExample;

@Service
@Transactional
public class TradeProfitService extends BaseService<TradeProfit,TradeProfitExample> {
	@Autowired
	private TradeProfitMapper tradeProfitMapper;
	@Autowired
	public void setTradeProfitMapper(TradeProfitMapper tradeProfitMapper) {
		super.setDao(tradeProfitMapper);
		this.tradeProfitMapper = tradeProfitMapper;
	}
	
	public List<Map<String,Object>> getTradeList(Map<String,Object> param){
		return tradeProfitMapper.getTradeList(param);
	}

}
