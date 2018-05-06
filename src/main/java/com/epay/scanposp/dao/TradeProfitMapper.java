package com.epay.scanposp.dao;

import java.util.List;
import java.util.Map;

import com.epay.scanposp.common.base.BaseDao;
import com.epay.scanposp.common.base.MyBatisRepository;
import com.epay.scanposp.entity.TradeProfit;
import com.epay.scanposp.entity.TradeProfitExample;
@MyBatisRepository
public interface TradeProfitMapper extends BaseDao<TradeProfit, TradeProfitExample>{
    
    List<TradeProfit> selectByExample(TradeProfitExample example);

    List<Map<String,Object>> getTradeList(Map<String,Object> param);
    
}