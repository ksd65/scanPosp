package com.epay.scanposp.dao;

import java.util.List;
import java.util.Map;

import com.epay.scanposp.common.base.BaseDao;
import com.epay.scanposp.common.base.MyBatisRepository;
import com.epay.scanposp.entity.RoutewayDrawProfit;
import com.epay.scanposp.entity.RoutewayDrawProfitExample;
@MyBatisRepository
public interface RoutewayDrawProfitMapper extends BaseDao<RoutewayDrawProfit, RoutewayDrawProfitExample>{
    
    List<RoutewayDrawProfit> selectByExample(RoutewayDrawProfitExample example);

    List<Map<String,Object>> getDrawList(Map<String,Object> param);
}