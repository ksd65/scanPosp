package com.epay.scanposp.dao;

import java.util.List;
import java.util.Map;

import com.epay.scanposp.common.base.MyBatisRepository;
import com.epay.scanposp.entity.TradeDetail;
import com.epay.scanposp.entity.TradeDetailAll;

@MyBatisRepository
public interface TradeDetailAllMapper {
	public List<TradeDetailAll> selectAllTradeDetail(Map<String, Object> paramMap);
	public Integer selectAllTradeDetailCount(Map<String, Object> paramMap);
	public List<TradeDetailAll> selectAllTradeDetailHis(Map<String, Object> paramMap);
	public Integer selectAllTradeDetailCountHis(Map<String, Object> paramMap);
}