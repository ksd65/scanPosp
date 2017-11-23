package com.epay.scanposp.dao;

import java.util.List;

import com.epay.scanposp.common.base.BaseDao;
import com.epay.scanposp.common.base.MyBatisRepository;
import com.epay.scanposp.entity.PayType;
import com.epay.scanposp.entity.PayTypeExample;
@MyBatisRepository
public interface PayTypeMapper extends BaseDao<PayType, PayTypeExample>{
    
    List<PayType> selectByExample(PayTypeExample example);

    
}