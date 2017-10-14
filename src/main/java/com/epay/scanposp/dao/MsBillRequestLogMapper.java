package com.epay.scanposp.dao;

import com.epay.scanposp.common.base.BaseDao;
import com.epay.scanposp.common.base.MyBatisRepository;
import com.epay.scanposp.entity.MsBillRequestLog;
import com.epay.scanposp.entity.MsBillRequestLogExample;

import java.util.List;

import org.apache.ibatis.annotations.Param;
@MyBatisRepository
public interface MsBillRequestLogMapper extends BaseDao<MsBillRequestLog, MsBillRequestLogExample> {
    int countByExample(MsBillRequestLogExample example);

    int deleteByExample(MsBillRequestLogExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(MsBillRequestLog record);

    int insertSelective(MsBillRequestLog record);

    List<MsBillRequestLog> selectByExample(MsBillRequestLogExample example);

    MsBillRequestLog selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") MsBillRequestLog record, @Param("example") MsBillRequestLogExample example);

    int updateByExample(@Param("record") MsBillRequestLog record, @Param("example") MsBillRequestLogExample example);

    int updateByPrimaryKeySelective(MsBillRequestLog record);

    int updateByPrimaryKey(MsBillRequestLog record);
}