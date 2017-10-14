package com.epay.scanposp.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.epay.scanposp.common.base.BaseDao;
import com.epay.scanposp.common.base.MyBatisRepository;
import com.epay.scanposp.entity.AgentRate;
import com.epay.scanposp.entity.AgentRateExample;

@MyBatisRepository
public interface AgentRateMapper extends BaseDao<AgentRate, AgentRateExample>{
    int countByExample(AgentRateExample example);

    int deleteByExample(AgentRateExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(AgentRate record);

    int insertSelective(AgentRate record);

    List<AgentRate> selectByExample(AgentRateExample example);

    AgentRate selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") AgentRate record, @Param("example") AgentRateExample example);

    int updateByExample(@Param("record") AgentRate record, @Param("example") AgentRateExample example);

    int updateByPrimaryKeySelective(AgentRate record);

    int updateByPrimaryKey(AgentRate record);
}