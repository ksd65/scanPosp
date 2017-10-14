package com.epay.scanposp.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.epay.scanposp.common.base.BaseDao;
import com.epay.scanposp.common.base.MyBatisRepository;
import com.epay.scanposp.entity.RouteWay;
import com.epay.scanposp.entity.RouteWayExample;
@MyBatisRepository
public interface RouteWayMapper extends BaseDao<RouteWay, RouteWayExample>{
    int countByExample(RouteWayExample example);

    int deleteByExample(RouteWayExample example);

    int insert(RouteWay record);

    int insertSelective(RouteWay record);

    List<RouteWay> selectByExample(RouteWayExample example);

    int updateByExampleSelective(@Param("record") RouteWay record, @Param("example") RouteWayExample example);

    int updateByExample(@Param("record") RouteWay record, @Param("example") RouteWayExample example);
}