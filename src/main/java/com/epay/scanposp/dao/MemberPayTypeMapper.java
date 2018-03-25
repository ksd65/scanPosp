package com.epay.scanposp.dao;

import java.util.List;

import com.epay.scanposp.common.base.BaseDao;
import com.epay.scanposp.common.base.MyBatisRepository;
import com.epay.scanposp.entity.MemberPayType;
import com.epay.scanposp.entity.MemberPayTypeExample;
@MyBatisRepository
public interface MemberPayTypeMapper extends BaseDao<MemberPayType, MemberPayTypeExample>{
    
    List<MemberPayType> selectByExample(MemberPayTypeExample example);

    int updateDelFlagByExample(MemberPayTypeExample example);
}