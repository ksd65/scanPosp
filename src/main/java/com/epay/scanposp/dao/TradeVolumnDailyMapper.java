package com.epay.scanposp.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.epay.scanposp.common.base.BaseDao;
import com.epay.scanposp.common.base.MyBatisRepository;
import com.epay.scanposp.entity.TradeVolumnDaily;
import com.epay.scanposp.entity.TradeVolumnDailyExample;
@MyBatisRepository
public interface TradeVolumnDailyMapper extends BaseDao<TradeVolumnDaily, TradeVolumnDailyExample> {
    int countByExample(TradeVolumnDailyExample example);

    int deleteByExample(TradeVolumnDailyExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(TradeVolumnDaily record);

    int insertSelective(TradeVolumnDaily record);

    List<TradeVolumnDaily> selectByExample(TradeVolumnDailyExample example);

    TradeVolumnDaily selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") TradeVolumnDaily record, @Param("example") TradeVolumnDailyExample example);

    int updateByExample(@Param("record") TradeVolumnDaily record, @Param("example") TradeVolumnDailyExample example);

    int updateByPrimaryKeySelective(TradeVolumnDaily record);

    int updateByPrimaryKey(TradeVolumnDaily record);
}