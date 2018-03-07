package com.epay.scanposp.dao;

import java.util.List;

import com.epay.scanposp.common.base.BaseDao;
import com.epay.scanposp.common.base.MyBatisRepository;
import com.epay.scanposp.entity.PrePayStatistics;
import com.epay.scanposp.entity.PrePayStatisticsExample;
@MyBatisRepository
public interface PrePayStatisticsMapper extends BaseDao<PrePayStatistics, PrePayStatisticsExample>{
    int countByExample(PrePayStatisticsExample example);

    int deleteByExample(PrePayStatisticsExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(PrePayStatistics record);

    int insertSelective(PrePayStatistics record);

    List<PrePayStatistics> selectByExample(PrePayStatisticsExample example);

    PrePayStatistics selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(PrePayStatistics record);

    int updateByPrimaryKey(PrePayStatistics record);
    
}