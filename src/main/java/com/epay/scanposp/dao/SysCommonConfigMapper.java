package com.epay.scanposp.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.epay.scanposp.common.base.BaseDao;
import com.epay.scanposp.common.base.MyBatisRepository;
import com.epay.scanposp.entity.SysCommonConfig;
import com.epay.scanposp.entity.SysCommonConfigExample;

@MyBatisRepository
public interface SysCommonConfigMapper extends BaseDao<SysCommonConfig, SysCommonConfigExample> {
    int countByExample(SysCommonConfigExample example);

    int deleteByExample(SysCommonConfigExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(SysCommonConfig record);

    int insertSelective(SysCommonConfig record);

    List<SysCommonConfig> selectByExample(SysCommonConfigExample example);

    SysCommonConfig selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") SysCommonConfig record, @Param("example") SysCommonConfigExample example);

    int updateByExample(@Param("record") SysCommonConfig record, @Param("example") SysCommonConfigExample example);

    int updateByPrimaryKeySelective(SysCommonConfig record);

    int updateByPrimaryKey(SysCommonConfig record);
}