package com.epay.scanposp.dao;

import java.util.List;

import com.epay.scanposp.common.base.BaseDao;
import com.epay.scanposp.common.base.MyBatisRepository;
import com.epay.scanposp.entity.PayTypeRule;
import com.epay.scanposp.entity.PayTypeRuleExample;
@MyBatisRepository
public interface PayTypeRuleMapper extends BaseDao<PayTypeRule, PayTypeRuleExample>{
    
    List<PayTypeRule> selectByExample(PayTypeRuleExample example);

    
}