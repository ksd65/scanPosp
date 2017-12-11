package com.epay.scanposp.dao;

import java.util.List;

import com.epay.scanposp.common.base.BaseDao;
import com.epay.scanposp.common.base.MyBatisRepository;
import com.epay.scanposp.entity.BankSubFt;
import com.epay.scanposp.entity.BankSubFtExample;

@MyBatisRepository
public interface BankSubFtMapper extends BaseDao<BankSubFt, BankSubFtExample>{
    int countByExample(BankSubFtExample example);

    int deleteByExample(BankSubFtExample example);

    int deleteByPrimaryKey(Integer id);

    
    List<BankSubFt> selectByExample(BankSubFtExample example);

    BankSubFt selectByPrimaryKey(Integer id);

    
}