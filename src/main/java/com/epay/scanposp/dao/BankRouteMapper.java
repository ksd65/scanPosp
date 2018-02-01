package com.epay.scanposp.dao;

import java.util.List;

import com.epay.scanposp.common.base.BaseDao;
import com.epay.scanposp.common.base.MyBatisRepository;
import com.epay.scanposp.entity.BankRoute;
import com.epay.scanposp.entity.BankRouteExample;

@MyBatisRepository
public interface BankRouteMapper extends BaseDao<BankRoute, BankRouteExample>{
    
    List<BankRoute> selectByExample(BankRouteExample example);

    BankRoute selectByPrimaryKey(Integer id);

}