package com.epay.scanposp.dao;

import com.epay.scanposp.common.base.BaseDao;
import com.epay.scanposp.common.base.MyBatisRepository;
import com.epay.scanposp.entity.MemberBindAccDtl;
import com.epay.scanposp.entity.MemberBindAccDtlExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

@MyBatisRepository
public interface MemberBindAccDtlMapper extends BaseDao<MemberBindAccDtl, MemberBindAccDtlExample>{
    int countByExample(MemberBindAccDtlExample example);

    int deleteByExample(MemberBindAccDtlExample example);

    int insert(MemberBindAccDtl record);

    int insertSelective(MemberBindAccDtl record);

    List<MemberBindAccDtl> selectByExample(MemberBindAccDtlExample example);

    int updateByExampleSelective(@Param("record") MemberBindAccDtl record, @Param("example") MemberBindAccDtlExample example);

    int updateByExample(@Param("record") MemberBindAccDtl record, @Param("example") MemberBindAccDtlExample example);
}