package com.epay.scanposp.dao;

import java.util.List;

import com.epay.scanposp.common.base.BaseDao;
import com.epay.scanposp.common.base.MyBatisRepository;
import com.epay.scanposp.entity.PayTypeMerchantDefault;
import com.epay.scanposp.entity.PayTypeMerchantDefaultExample;
@MyBatisRepository
public interface PayTypeMerchantDefaultMapper extends BaseDao<PayTypeMerchantDefault, PayTypeMerchantDefaultExample>{
    
    List<PayTypeMerchantDefault> selectByExample(PayTypeMerchantDefaultExample example);

    
}