package com.epay.scanposp.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.epay.scanposp.dao.CommonServiceMapper;
import com.epay.scanposp.dao.TradeDetailAllMapper;
import com.epay.scanposp.entity.TradeDetail;
import com.epay.scanposp.entity.TradeDetailAll;

@Service
@Transactional
public class TradeDetailAllService {
	@Autowired
	private TradeDetailAllMapper tradeDetailAllMapper;

	@Autowired
	public void setTradeDetailAllMapper(TradeDetailAllMapper tradeDetailAllMapper) {
		this.tradeDetailAllMapper = tradeDetailAllMapper;
	}
	
	public List<TradeDetailAll> selectAllTradeDetail(Map<String, Object> paramMap){
		return tradeDetailAllMapper.selectAllTradeDetail(paramMap);
	}
	public Integer selectAllTradeDetailCount(Map<String, Object> paramMap){
		return tradeDetailAllMapper.selectAllTradeDetailCount(paramMap);
	}
	public List<TradeDetailAll> selectAllTradeDetailHis(Map<String, Object> paramMap){
		return tradeDetailAllMapper.selectAllTradeDetailHis(paramMap);
	}
	public Integer selectAllTradeDetailCountHis(Map<String, Object> paramMap){
		return tradeDetailAllMapper.selectAllTradeDetailCountHis(paramMap);
	}
}
