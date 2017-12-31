package com.epay.scanposp.dao;

import java.util.List;

import com.epay.scanposp.common.base.BaseDao;
import com.epay.scanposp.common.base.MyBatisRepository;
import com.epay.scanposp.entity.MemberIpRule;
import com.epay.scanposp.entity.MemberIpRuleExample;
@MyBatisRepository
public interface MemberIpRuleMapper extends BaseDao<MemberIpRule, MemberIpRuleExample>{
    
    List<MemberIpRule> selectByExample(MemberIpRuleExample example);

    
}