package com.epay.scanposp.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.epay.scanposp.common.base.BaseDao;
import com.epay.scanposp.common.base.MyBatisRepository;
import com.epay.scanposp.entity.RoutewayAccount;
import com.epay.scanposp.entity.RoutewayAccountExample;
@MyBatisRepository
public interface RoutewayAccountMapper extends BaseDao<RoutewayAccount, RoutewayAccountExample>{
    int countByExample(RoutewayAccountExample example);

    int deleteByExample(RoutewayAccountExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(RoutewayAccount record);

    int insertSelective(RoutewayAccount record);

    List<RoutewayAccount> selectByExample(RoutewayAccountExample example);

    RoutewayAccount selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") RoutewayAccount record, @Param("example") RoutewayAccountExample example);

    int updateByExample(@Param("record") RoutewayAccount record, @Param("example") RoutewayAccountExample example);

    int updateByPrimaryKeySelective(RoutewayAccount record);

    int updateByPrimaryKey(RoutewayAccount record);
}