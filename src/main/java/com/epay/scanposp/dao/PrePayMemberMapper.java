package com.epay.scanposp.dao;

import java.util.List;

import com.epay.scanposp.common.base.BaseDao;
import com.epay.scanposp.common.base.MyBatisRepository;
import com.epay.scanposp.entity.PrePayMember;
import com.epay.scanposp.entity.PrePayMemberExample;
@MyBatisRepository
public interface PrePayMemberMapper extends BaseDao<PrePayMember, PrePayMemberExample>{
    
    List<PrePayMember> selectByExample(PrePayMemberExample example);

    
}