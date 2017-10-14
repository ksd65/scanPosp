package com.epay.scanposp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.epay.scanposp.common.base.BaseService;
import com.epay.scanposp.dao.TradeDetailMapper;
import com.epay.scanposp.entity.TradeDetail;
import com.epay.scanposp.entity.TradeDetailExample;

@Service
@Transactional
public class TradeDetailService extends BaseService<TradeDetail,TradeDetailExample> {
	@Autowired
	private TradeDetailMapper tradeDetailMapper;
	
	@Autowired
	public void setTradeDetailMapper(TradeDetailMapper tradeDetailMapper) {
		super.setDao(tradeDetailMapper);
		this.tradeDetailMapper = tradeDetailMapper;
	}

}
