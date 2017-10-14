package com.epay.scanposp.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.epay.scanposp.common.base.BaseDao;
import com.epay.scanposp.common.base.MyBatisRepository;
import com.epay.scanposp.entity.MemberOpenid;
import com.epay.scanposp.entity.MemberOpenidExample;
@MyBatisRepository
public interface MemberOpenidMapper extends BaseDao<MemberOpenid, MemberOpenidExample>{
    int countByExample(MemberOpenidExample example);

    int deleteByExample(MemberOpenidExample example);

    int insert(MemberOpenid record);

    int insertSelective(MemberOpenid record);

    List<MemberOpenid> selectByExample(MemberOpenidExample example);

    int updateByExampleSelective(@Param("record") MemberOpenid record, @Param("example") MemberOpenidExample example);

    int updateByExample(@Param("record") MemberOpenid record, @Param("example") MemberOpenidExample example);
}