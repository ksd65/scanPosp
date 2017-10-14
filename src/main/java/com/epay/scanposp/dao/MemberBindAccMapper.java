package com.epay.scanposp.dao;

import com.epay.scanposp.common.base.BaseDao;
import com.epay.scanposp.common.base.MyBatisRepository;
import com.epay.scanposp.entity.MemberBindAcc;
import com.epay.scanposp.entity.MemberBindAccExample;
 

import java.util.List;
import org.apache.ibatis.annotations.Param;
@MyBatisRepository
public interface MemberBindAccMapper extends BaseDao<MemberBindAcc, MemberBindAccExample> {
    int countByExample(MemberBindAccExample example);

    int deleteByExample(MemberBindAccExample example);

    int insert(MemberBindAcc record);

    int insertSelective(MemberBindAcc record);

    List<MemberBindAcc> selectByExample(MemberBindAccExample example);

    int updateByExampleSelective(@Param("record") MemberBindAcc record, @Param("example") MemberBindAccExample example);

    int updateByExample(@Param("record") MemberBindAcc record, @Param("example") MemberBindAccExample example);
}