package com.epay.scanposp.dao;

import com.epay.scanposp.common.base.BaseDao;
import com.epay.scanposp.common.base.MyBatisRepository;
import com.epay.scanposp.entity.SysArea;
import com.epay.scanposp.entity.SysAreaExample;
import com.epay.scanposp.entity.VerifyCode;
import com.epay.scanposp.entity.VerifyCodeExample;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

@MyBatisRepository
public interface VerifyCodeMapper extends BaseDao<VerifyCode, VerifyCodeExample> {
    int countByExample(VerifyCodeExample example);

    int deleteByExample(VerifyCodeExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(VerifyCode record);

    int insertSelective(VerifyCode record);

    List<VerifyCode> selectByExample(VerifyCodeExample example);

    VerifyCode selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") VerifyCode record, @Param("example") VerifyCodeExample example);

    int updateByExample(@Param("record") VerifyCode record, @Param("example") VerifyCodeExample example);

    int updateByPrimaryKeySelective(VerifyCode record);

    int updateByPrimaryKey(VerifyCode record);
    
    int updateCodeStateByType(Map<String,Object> paramMap);
}