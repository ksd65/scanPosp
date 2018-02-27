package com.epay.scanposp.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.epay.scanposp.common.base.BaseDao;
import com.epay.scanposp.common.base.MyBatisRepository;
import com.epay.scanposp.entity.PayQrCodeTemp;
import com.epay.scanposp.entity.PayQrCodeTempExample;
@MyBatisRepository
public interface PayQrCodeTempMapper extends BaseDao<PayQrCodeTemp, PayQrCodeTempExample>{
    int countByExample(PayQrCodeTempExample example);

    int deleteByExample(PayQrCodeTempExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(PayQrCodeTemp record);

    int insertSelective(PayQrCodeTemp record);

    List<PayQrCodeTemp> selectByExample(PayQrCodeTempExample example);

    PayQrCodeTemp selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") PayQrCodeTemp record, @Param("example") PayQrCodeTempExample example);

    int updateByExample(@Param("record") PayQrCodeTemp record, @Param("example") PayQrCodeTempExample example);

    int updateByPrimaryKeySelective(PayQrCodeTemp record);

    int updateByPrimaryKey(PayQrCodeTemp record);
    
    int delete(PayQrCodeTemp record);
}