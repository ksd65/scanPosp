package com.epay.scanposp.dao;

import java.util.List;

import com.epay.scanposp.common.base.BaseDao;
import com.epay.scanposp.common.base.MyBatisRepository;
import com.epay.scanposp.entity.MemberIpWhiteList;
import com.epay.scanposp.entity.MemberIpWhiteListExample;
@MyBatisRepository
public interface MemberIpWhiteListMapper extends BaseDao<MemberIpWhiteList, MemberIpWhiteListExample>{
    
    List<MemberIpWhiteList> selectByExample(MemberIpWhiteListExample example);

    
}