package com.epay.scanposp.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.epay.scanposp.common.base.BaseDao;
import com.epay.scanposp.common.base.MyBatisRepository;
import com.epay.scanposp.entity.MemberAliOpenid;
import com.epay.scanposp.entity.MemberAliOpenidExample;

@MyBatisRepository
public interface MemberAliOpenidMapper extends BaseDao<MemberAliOpenid, MemberAliOpenidExample>{
    int countByExample(MemberAliOpenidExample example);

    int deleteByExample(MemberAliOpenidExample example);

    int insert(MemberAliOpenid record);

    int insertSelective(MemberAliOpenid record);

    List<MemberAliOpenid> selectByExample(MemberAliOpenidExample example);

    int updateByExampleSelective(@Param("record") MemberAliOpenid record, @Param("example") MemberAliOpenidExample example);

    int updateByExample(@Param("record") MemberAliOpenid record, @Param("example") MemberAliOpenidExample example);
}