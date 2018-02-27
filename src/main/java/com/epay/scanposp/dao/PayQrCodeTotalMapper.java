package com.epay.scanposp.dao;

import java.util.List;

import com.epay.scanposp.common.base.BaseDao;
import com.epay.scanposp.common.base.MyBatisRepository;
import com.epay.scanposp.entity.PayQrCodeTotal;
import com.epay.scanposp.entity.PayQrCodeTotalExample;
@MyBatisRepository
public interface PayQrCodeTotalMapper extends BaseDao<PayQrCodeTotal, PayQrCodeTotalExample>{
    int countByExample(PayQrCodeTotalExample example);

    int deleteByExample(PayQrCodeTotalExample example);

    int deleteByPrimaryKey(Integer id);

    List<PayQrCodeTotal> selectByExample(PayQrCodeTotalExample example);

    PayQrCodeTotal selectByPrimaryKey(Integer id);
    
    int insertSelective(PayQrCodeTotal record);

    int updateByPrimaryKey(PayQrCodeTotal record);
}