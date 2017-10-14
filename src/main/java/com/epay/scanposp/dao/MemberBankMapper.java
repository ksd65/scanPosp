package com.epay.scanposp.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.epay.scanposp.common.base.BaseDao;
import com.epay.scanposp.common.base.MyBatisRepository;
import com.epay.scanposp.entity.MemberBank;
import com.epay.scanposp.entity.MemberBankExample;
@MyBatisRepository
public interface MemberBankMapper extends BaseDao<MemberBank, MemberBankExample>{
    int countByExample(MemberBankExample example);

    int deleteByExample(MemberBankExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(MemberBank record);

    int insertSelective(MemberBank record);

    List<MemberBank> selectByExample(MemberBankExample example);

    MemberBank selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") MemberBank record, @Param("example") MemberBankExample example);

    int updateByExample(@Param("record") MemberBank record, @Param("example") MemberBankExample example);

    int updateByPrimaryKeySelective(MemberBank record);

    int updateByPrimaryKey(MemberBank record);
}