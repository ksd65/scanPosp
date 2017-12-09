package com.epay.scanposp.dao;

import java.util.List;

import com.epay.scanposp.common.base.BaseDao;
import com.epay.scanposp.common.base.MyBatisRepository;
import com.epay.scanposp.entity.MemberMerchantKey;
import com.epay.scanposp.entity.MemberMerchantKeyExample;
@MyBatisRepository
public interface MemberMerchantKeyMapper extends BaseDao<MemberMerchantKey, MemberMerchantKeyExample>{
    
    List<MemberMerchantKey> selectByExample(MemberMerchantKeyExample example);
    
    int insertSelective(MemberMerchantKey record);
    
    MemberMerchantKey selectByPrimaryKey(Integer id);

    
}