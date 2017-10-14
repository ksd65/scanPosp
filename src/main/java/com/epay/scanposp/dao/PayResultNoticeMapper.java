package com.epay.scanposp.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.epay.scanposp.common.base.BaseDao;
import com.epay.scanposp.common.base.MyBatisRepository;
import com.epay.scanposp.entity.PayResultNotice;
import com.epay.scanposp.entity.PayResultNoticeExample;
@MyBatisRepository
public interface PayResultNoticeMapper extends BaseDao<PayResultNotice, PayResultNoticeExample>{
    int countByExample(PayResultNoticeExample example);

    int deleteByExample(PayResultNoticeExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(PayResultNotice record);

    int insertSelective(PayResultNotice record);

    List<PayResultNotice> selectByExample(PayResultNoticeExample example);

    PayResultNotice selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") PayResultNotice record, @Param("example") PayResultNoticeExample example);

    int updateByExample(@Param("record") PayResultNotice record, @Param("example") PayResultNoticeExample example);

    int updateByPrimaryKeySelective(PayResultNotice record);

    int updateByPrimaryKey(PayResultNotice record);
}