package com.epay.scanposp.dao;

import java.util.List;

import com.epay.scanposp.common.base.BaseDao;
import com.epay.scanposp.common.base.MyBatisRepository;
import com.epay.scanposp.entity.MemberIpBlackList;
import com.epay.scanposp.entity.MemberIpBlackListExample;
@MyBatisRepository
public interface MemberIpBlackListMapper extends BaseDao<MemberIpBlackList, MemberIpBlackListExample>{
    
    List<MemberIpBlackList> selectByExample(MemberIpBlackListExample example);

    
}