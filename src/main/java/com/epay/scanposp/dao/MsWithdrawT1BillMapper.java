package com.epay.scanposp.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.epay.scanposp.common.base.BaseDao;
import com.epay.scanposp.common.base.MyBatisRepository;
import com.epay.scanposp.entity.MsWithdrawT1Bill;
import com.epay.scanposp.entity.MsWithdrawT1BillExample;

@MyBatisRepository
public interface MsWithdrawT1BillMapper extends BaseDao<MsWithdrawT1Bill, MsWithdrawT1BillExample>{
    int countByExample(MsWithdrawT1BillExample example);

    int deleteByExample(MsWithdrawT1BillExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(MsWithdrawT1Bill record);

    int insertSelective(MsWithdrawT1Bill record);

    List<MsWithdrawT1Bill> selectByExample(MsWithdrawT1BillExample example);

    MsWithdrawT1Bill selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") MsWithdrawT1Bill record, @Param("example") MsWithdrawT1BillExample example);

    int updateByExample(@Param("record") MsWithdrawT1Bill record, @Param("example") MsWithdrawT1BillExample example);

    int updateByPrimaryKeySelective(MsWithdrawT1Bill record);

    int updateByPrimaryKey(MsWithdrawT1Bill record);
}