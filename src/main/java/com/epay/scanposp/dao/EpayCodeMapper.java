package com.epay.scanposp.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.epay.scanposp.common.base.BaseDao;
import com.epay.scanposp.common.base.MyBatisRepository;
import com.epay.scanposp.entity.EpayCode;
import com.epay.scanposp.entity.EpayCodeExample;
@MyBatisRepository
public interface EpayCodeMapper extends BaseDao<EpayCode, EpayCodeExample>{
    int countByExample(EpayCodeExample example);

    int deleteByExample(EpayCodeExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(EpayCode record);

    int insertSelective(EpayCode record);

    List<EpayCode> selectByExample(EpayCodeExample example);

    EpayCode selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") EpayCode record, @Param("example") EpayCodeExample example);

    int updateByExample(@Param("record") EpayCode record, @Param("example") EpayCodeExample example);

    int updateByPrimaryKeySelective(EpayCode record);

    int updateByPrimaryKey(EpayCode record);
}