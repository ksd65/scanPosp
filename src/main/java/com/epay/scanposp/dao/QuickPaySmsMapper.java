package com.epay.scanposp.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.epay.scanposp.common.base.BaseDao;
import com.epay.scanposp.common.base.MyBatisRepository;
import com.epay.scanposp.entity.QuickPaySms;
import com.epay.scanposp.entity.QuickPaySmsExample;
@MyBatisRepository
public interface QuickPaySmsMapper extends BaseDao<QuickPaySms, QuickPaySmsExample>{
    int countByExample(QuickPaySmsExample example);

    int deleteByExample(QuickPaySmsExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(QuickPaySms record);

    int insertSelective(QuickPaySms record);

    List<QuickPaySms> selectByExample(QuickPaySmsExample example);

    QuickPaySms selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") QuickPaySms record, @Param("example") QuickPaySmsExample example);

    int updateByExample(@Param("record") QuickPaySms record, @Param("example") QuickPaySmsExample example);

    int updateByPrimaryKeySelective(QuickPaySms record);

    int updateByPrimaryKey(QuickPaySms record);
}