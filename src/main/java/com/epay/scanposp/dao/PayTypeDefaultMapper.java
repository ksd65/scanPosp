package com.epay.scanposp.dao;

import java.util.List;

import com.epay.scanposp.common.base.BaseDao;
import com.epay.scanposp.common.base.MyBatisRepository;
import com.epay.scanposp.entity.PayTypeDefault;
import com.epay.scanposp.entity.PayTypeDefaultExample;
@MyBatisRepository
public interface PayTypeDefaultMapper extends BaseDao<PayTypeDefault, PayTypeDefaultExample>{
    
    List<PayTypeDefault> selectByExample(PayTypeDefaultExample example);

    
}