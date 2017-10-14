package com.epay.scanposp.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.epay.scanposp.common.base.BaseDao;
import com.epay.scanposp.common.base.MyBatisRepository;
import com.epay.scanposp.entity.BankSub;
import com.epay.scanposp.entity.BankSubExample;

@MyBatisRepository
public interface BankSubMapper extends BaseDao<BankSub, BankSubExample>{
    int countByExample(BankSubExample example);

    int deleteByExample(BankSubExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(BankSub record);

    int insertSelective(BankSub record);

    List<BankSub> selectByExample(BankSubExample example);

    BankSub selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") BankSub record, @Param("example") BankSubExample example);

    int updateByExample(@Param("record") BankSub record, @Param("example") BankSubExample example);

    int updateByPrimaryKeySelective(BankSub record);

    int updateByPrimaryKey(BankSub record);
}