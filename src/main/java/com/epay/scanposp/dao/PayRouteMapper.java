package com.epay.scanposp.dao;

import java.util.List;

import com.epay.scanposp.common.base.BaseDao;
import com.epay.scanposp.common.base.MyBatisRepository;
import com.epay.scanposp.entity.PayRoute;
import com.epay.scanposp.entity.PayRouteExample;
@MyBatisRepository
public interface PayRouteMapper extends BaseDao<PayRoute, PayRouteExample>{
    
    List<PayRoute> selectByExample(PayRouteExample example);

    
}