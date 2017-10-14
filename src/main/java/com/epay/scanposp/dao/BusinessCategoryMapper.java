package com.epay.scanposp.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.epay.scanposp.common.base.BaseDao;
import com.epay.scanposp.common.base.MyBatisRepository;
import com.epay.scanposp.entity.BusinessCategory;
import com.epay.scanposp.entity.BusinessCategoryExample;
@MyBatisRepository
public interface BusinessCategoryMapper extends BaseDao<BusinessCategory, BusinessCategoryExample>{
    int countByExample(BusinessCategoryExample example);

    int deleteByExample(BusinessCategoryExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(BusinessCategory record);

    int insertSelective(BusinessCategory record);

    List<BusinessCategory> selectByExample(BusinessCategoryExample example);

    BusinessCategory selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") BusinessCategory record, @Param("example") BusinessCategoryExample example);

    int updateByExample(@Param("record") BusinessCategory record, @Param("example") BusinessCategoryExample example);

    int updateByPrimaryKeySelective(BusinessCategory record);

    int updateByPrimaryKey(BusinessCategory record);
}