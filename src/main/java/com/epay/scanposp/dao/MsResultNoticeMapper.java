package com.epay.scanposp.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.epay.scanposp.common.base.BaseDao;
import com.epay.scanposp.common.base.MyBatisRepository;
import com.epay.scanposp.entity.MsResultNotice;
import com.epay.scanposp.entity.MsResultNoticeExample;
@MyBatisRepository
public interface MsResultNoticeMapper extends BaseDao<MsResultNotice, MsResultNoticeExample>{
    int countByExample(MsResultNoticeExample example);

    int deleteByExample(MsResultNoticeExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(MsResultNotice record);

    int insertSelective(MsResultNotice record);

    List<MsResultNotice> selectByExample(MsResultNoticeExample example);

    MsResultNotice selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") MsResultNotice record, @Param("example") MsResultNoticeExample example);

    int updateByExample(@Param("record") MsResultNotice record, @Param("example") MsResultNoticeExample example);

    int updateByPrimaryKeySelective(MsResultNotice record);

    int updateByPrimaryKey(MsResultNotice record);
}