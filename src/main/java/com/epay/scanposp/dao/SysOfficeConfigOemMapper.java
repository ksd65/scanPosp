package com.epay.scanposp.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.epay.scanposp.common.base.BaseDao;
import com.epay.scanposp.common.base.MyBatisRepository;
import com.epay.scanposp.entity.SysOfficeConfigOem;
import com.epay.scanposp.entity.SysOfficeConfigOemExample;

@MyBatisRepository
public interface SysOfficeConfigOemMapper  extends BaseDao<SysOfficeConfigOem, SysOfficeConfigOemExample>{
    int countByExample(SysOfficeConfigOemExample example);

    int deleteByExample(SysOfficeConfigOemExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(SysOfficeConfigOem record);

    int insertSelective(SysOfficeConfigOem record);

    List<SysOfficeConfigOem> selectByExample(SysOfficeConfigOemExample example);

    SysOfficeConfigOem selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") SysOfficeConfigOem record, @Param("example") SysOfficeConfigOemExample example);

    int updateByExample(@Param("record") SysOfficeConfigOem record, @Param("example") SysOfficeConfigOemExample example);

    int updateByPrimaryKeySelective(SysOfficeConfigOem record);

    int updateByPrimaryKey(SysOfficeConfigOem record);
}