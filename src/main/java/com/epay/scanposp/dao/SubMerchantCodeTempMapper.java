package com.epay.scanposp.dao;

import java.util.List;

import com.epay.scanposp.common.base.BaseDao;
import com.epay.scanposp.common.base.MyBatisRepository;
import com.epay.scanposp.entity.SubMerchantCodeTemp;
import com.epay.scanposp.entity.SubMerchantCodeTempExample;
@MyBatisRepository
public interface SubMerchantCodeTempMapper extends BaseDao<SubMerchantCodeTemp, SubMerchantCodeTempExample>{
    int countByExample(SubMerchantCodeTempExample example);

    int deleteByExample(SubMerchantCodeTempExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(SubMerchantCodeTemp record);

    int insertSelective(SubMerchantCodeTemp record);

    List<SubMerchantCodeTemp> selectByExample(SubMerchantCodeTempExample example);

    SubMerchantCodeTemp selectByPrimaryKey(Integer id);

    int delete(SubMerchantCodeTemp record);
}