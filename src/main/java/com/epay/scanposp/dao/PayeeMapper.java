package com.epay.scanposp.dao;

import java.util.List;

import com.epay.scanposp.common.base.BaseDao;
import com.epay.scanposp.common.base.MyBatisRepository;
import com.epay.scanposp.entity.Payee;
import com.epay.scanposp.entity.PayeeExample;
@MyBatisRepository
public interface PayeeMapper extends BaseDao<Payee, PayeeExample>{
    int countByExample(PayeeExample example);

    int deleteByExample(PayeeExample example);

    int deleteByPrimaryKey(Integer id);

    List<Payee> selectByExample(PayeeExample example);

    Payee selectByPrimaryKey(Integer id);

    int updateByPrimaryKey(Payee record);
}