package com.epay.scanposp.dao;

import java.util.List;

import com.epay.scanposp.common.base.BaseDao;
import com.epay.scanposp.common.base.MyBatisRepository;
import com.epay.scanposp.entity.TradeDailyTotal;
import com.epay.scanposp.entity.TradeDailyTotalExample;
@MyBatisRepository
public interface TradeDailyTotalMapper extends BaseDao<TradeDailyTotal, TradeDailyTotalExample> {
    int countByExample(TradeDailyTotalExample example);

    int deleteByExample(TradeDailyTotalExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(TradeDailyTotal record);

    int insertSelective(TradeDailyTotal record);

    List<TradeDailyTotal> selectByExample(TradeDailyTotalExample example);

    TradeDailyTotal selectByPrimaryKey(Integer id);

}