package com.epay.scanposp.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.epay.scanposp.common.base.BaseDao;
import com.epay.scanposp.common.base.MyBatisRepository;
import com.epay.scanposp.entity.RegisterTmp;
import com.epay.scanposp.entity.RegisterTmpExample;
@MyBatisRepository
public interface RegisterTmpMapper extends BaseDao<RegisterTmp, RegisterTmpExample>{
    int countByExample(RegisterTmpExample example);

    int deleteByExample(RegisterTmpExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(RegisterTmp record);

    int insertSelective(RegisterTmp record);

    List<RegisterTmp> selectByExample(RegisterTmpExample example);

    RegisterTmp selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") RegisterTmp record, @Param("example") RegisterTmpExample example);

    int updateByExample(@Param("record") RegisterTmp record, @Param("example") RegisterTmpExample example);

    int updateByPrimaryKeySelective(RegisterTmp record);

    int updateByPrimaryKey(RegisterTmp record);
}