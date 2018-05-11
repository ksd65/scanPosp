package com.epay.scanposp.dao;

import java.util.List;
import java.util.Map;

import com.epay.scanposp.common.base.BaseDao;
import com.epay.scanposp.common.base.MyBatisRepository;
import com.epay.scanposp.entity.SubMerchantCode;
import com.epay.scanposp.entity.SubMerchantCodeExample;
@MyBatisRepository
public interface SubMerchantCodeMapper extends BaseDao<SubMerchantCode, SubMerchantCodeExample>{
    
    List<SubMerchantCode> selectByExample(SubMerchantCodeExample example);

    List<SubMerchantCode> selectByMap(Map<String,Object> param);
    
}