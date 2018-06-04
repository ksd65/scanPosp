package com.epay.scanposp.dao;

import java.util.List;
import java.util.Map;

import com.epay.scanposp.common.base.BaseDao;
import com.epay.scanposp.common.base.MyBatisRepository;
import com.epay.scanposp.entity.SubMerchantBlackList;
import com.epay.scanposp.entity.SubMerchantBlackListExample;
@MyBatisRepository
public interface SubMerchantBlackListMapper extends BaseDao<SubMerchantBlackList, SubMerchantBlackListExample>{
    
    List<SubMerchantBlackList> selectByExample(SubMerchantBlackListExample example);

    List<Map<String,Object>> getSubMerchantFailIpCount(Map<String,Object> param);
    
    List<SubMerchantBlackList> getSubMerchantBlackToday(Map<String,Object> param);
    
    int getSubMerchantBlackCount(Map<String,Object> param);
}