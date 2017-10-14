package com.epay.scanposp.dao;

import com.epay.scanposp.common.base.BaseDao;
import com.epay.scanposp.common.base.MyBatisRepository;
import com.epay.scanposp.entity.MsWithdrawBill;
import com.epay.scanposp.entity.MsWithdrawBillExample;

import java.util.List;

import org.apache.ibatis.annotations.Param;
@MyBatisRepository
public interface MsWithdrawBillMapper extends BaseDao<MsWithdrawBill, MsWithdrawBillExample>{
    int countByExample(MsWithdrawBillExample example);

    int deleteByExample(MsWithdrawBillExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(MsWithdrawBill record);

    int insertSelective(MsWithdrawBill record);

    List<MsWithdrawBill> selectByExample(MsWithdrawBillExample example);

    MsWithdrawBill selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") MsWithdrawBill record, @Param("example") MsWithdrawBillExample example);

    int updateByExample(@Param("record") MsWithdrawBill record, @Param("example") MsWithdrawBillExample example);

    int updateByPrimaryKeySelective(MsWithdrawBill record);

    int updateByPrimaryKey(MsWithdrawBill record);
}