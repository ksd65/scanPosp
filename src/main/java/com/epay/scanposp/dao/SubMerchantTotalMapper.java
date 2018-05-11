package com.epay.scanposp.dao;

import java.util.List;

import com.epay.scanposp.common.base.BaseDao;
import com.epay.scanposp.common.base.MyBatisRepository;
import com.epay.scanposp.entity.SubMerchantTotal;
import com.epay.scanposp.entity.SubMerchantTotalExample;
@MyBatisRepository
public interface SubMerchantTotalMapper extends BaseDao<SubMerchantTotal, SubMerchantTotalExample>{
    int countByExample(SubMerchantTotalExample example);

    int deleteByExample(SubMerchantTotalExample example);

    int deleteByPrimaryKey(Integer id);

    List<SubMerchantTotal> selectByExample(SubMerchantTotalExample example);

    SubMerchantTotal selectByPrimaryKey(Integer id);
    
    int insertSelective(SubMerchantTotal record);

    int updateByPrimaryKey(SubMerchantTotal record);
    
}