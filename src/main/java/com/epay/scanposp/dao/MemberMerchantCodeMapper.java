package com.epay.scanposp.dao;

import java.util.List;

import com.epay.scanposp.common.base.BaseDao;
import com.epay.scanposp.common.base.MyBatisRepository;
import com.epay.scanposp.entity.MemberMerchantCode;
import com.epay.scanposp.entity.MemberMerchantCodeExample;
@MyBatisRepository
public interface MemberMerchantCodeMapper extends BaseDao<MemberMerchantCode, MemberMerchantCodeExample>{
    
    List<MemberMerchantCode> selectByExample(MemberMerchantCodeExample example);
    
    int insertSelective(MemberMerchantCode record);
    
    MemberMerchantCode selectByPrimaryKey(Integer id);

    int updateByPrimaryKey(MemberMerchantCode record);
    
}