package com.epay.scanposp.dao;

import com.epay.scanposp.common.base.BaseDao;
import com.epay.scanposp.common.base.MyBatisRepository;
import com.epay.scanposp.entity.MsPayBill;
import com.epay.scanposp.entity.MsPayBillExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

@MyBatisRepository
public interface MsPayBillMapper extends BaseDao<MsPayBill ,MsPayBillExample> {
    int countByExample(MsPayBillExample example);

    int deleteByExample(MsPayBillExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(MsPayBill record);

    int insertSelective(MsPayBill record);

    List<MsPayBill> selectByExample(MsPayBillExample example);

    MsPayBill selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") MsPayBill record, @Param("example") MsPayBillExample example);

    int updateByExample(@Param("record") MsPayBill record, @Param("example") MsPayBillExample example);

    int updateByPrimaryKeySelective(MsPayBill record);

    int updateByPrimaryKey(MsPayBill record);
}