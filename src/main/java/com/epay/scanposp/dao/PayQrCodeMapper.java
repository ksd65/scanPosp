package com.epay.scanposp.dao;

import java.util.List;
import java.util.Map;

import com.epay.scanposp.common.base.BaseDao;
import com.epay.scanposp.common.base.MyBatisRepository;
import com.epay.scanposp.entity.PayQrCode;
import com.epay.scanposp.entity.PayQrCodeExample;
@MyBatisRepository
public interface PayQrCodeMapper extends BaseDao<PayQrCode, PayQrCodeExample>{
    int countByExample(PayQrCodeExample example);

    int deleteByExample(PayQrCodeExample example);

    int deleteByPrimaryKey(Integer id);

    List<PayQrCode> selectByExample(PayQrCodeExample example);

    PayQrCode selectByPrimaryKey(Integer id);

    int updateByPrimaryKey(PayQrCode record);
    
    List<PayQrCode> selectByMap(Map<String,Object> param);
}