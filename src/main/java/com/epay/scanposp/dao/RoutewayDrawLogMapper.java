package com.epay.scanposp.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.epay.scanposp.common.base.BaseDao;
import com.epay.scanposp.common.base.MyBatisRepository;
import com.epay.scanposp.entity.RoutewayDrawLog;
import com.epay.scanposp.entity.RoutewayDrawLogExample;
@MyBatisRepository
public interface RoutewayDrawLogMapper extends BaseDao<RoutewayDrawLog, RoutewayDrawLogExample>{
    int countByExample(RoutewayDrawLogExample example);

    int deleteByExample(RoutewayDrawLogExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(RoutewayDrawLog record);

    int insertSelective(RoutewayDrawLog record);

    List<RoutewayDrawLog> selectByExample(RoutewayDrawLogExample example);

    RoutewayDrawLog selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") RoutewayDrawLog record, @Param("example") RoutewayDrawLogExample example);

    int updateByExample(@Param("record") RoutewayDrawLog record, @Param("example") RoutewayDrawLogExample example);

    int updateByPrimaryKeySelective(RoutewayDrawLog record);

    int updateByPrimaryKey(RoutewayDrawLog record);
}