package com.epay.scanposp.dao;

import com.epay.scanposp.common.base.BaseDao;
import com.epay.scanposp.common.base.MyBatisRepository;
import com.epay.scanposp.entity.AccountLog;
import com.epay.scanposp.entity.AccountLogExample;

import java.util.List;

import org.apache.ibatis.annotations.Param;

@MyBatisRepository
public interface AccountLogMapper extends BaseDao<AccountLog, AccountLogExample>{
    int countByExample(AccountLogExample example);

    int deleteByExample(AccountLogExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(AccountLog record);

    int insertSelective(AccountLog record);

    List<AccountLog> selectByExample(AccountLogExample example);

    AccountLog selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") AccountLog record, @Param("example") AccountLogExample example);

    int updateByExample(@Param("record") AccountLog record, @Param("example") AccountLogExample example);

    int updateByPrimaryKeySelective(AccountLog record);

    int updateByPrimaryKey(AccountLog record);
}