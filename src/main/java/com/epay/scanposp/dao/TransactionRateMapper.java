package com.epay.scanposp.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.epay.scanposp.common.base.BaseDao;
import com.epay.scanposp.common.base.MyBatisRepository;
import com.epay.scanposp.entity.TransactionRate;
import com.epay.scanposp.entity.TransactionRateExample;
@MyBatisRepository
public interface TransactionRateMapper extends BaseDao<TransactionRate, TransactionRateExample>{
    int countByExample(TransactionRateExample example);

    int deleteByExample(TransactionRateExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(TransactionRate record);

    int insertSelective(TransactionRate record);

    List<TransactionRate> selectByExample(TransactionRateExample example);

    TransactionRate selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") TransactionRate record, @Param("example") TransactionRateExample example);

    int updateByExample(@Param("record") TransactionRate record, @Param("example") TransactionRateExample example);

    int updateByPrimaryKeySelective(TransactionRate record);

    int updateByPrimaryKey(TransactionRate record);
}