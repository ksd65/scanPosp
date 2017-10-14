package com.epay.scanposp.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.epay.scanposp.common.base.BaseDao;
import com.epay.scanposp.common.base.MyBatisRepository;
import com.epay.scanposp.entity.RegionMsg;
import com.epay.scanposp.entity.RegionMsgExample;

@MyBatisRepository
public interface RegionMsgMapper extends BaseDao<RegionMsg, RegionMsgExample>{
    int countByExample(RegionMsgExample example);

    int deleteByExample(RegionMsgExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(RegionMsg record);

    int insertSelective(RegionMsg record);

    List<RegionMsg> selectByExample(RegionMsgExample example);

    RegionMsg selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") RegionMsg record, @Param("example") RegionMsgExample example);

    int updateByExample(@Param("record") RegionMsg record, @Param("example") RegionMsgExample example);

    int updateByPrimaryKeySelective(RegionMsg record);

    int updateByPrimaryKey(RegionMsg record);
}