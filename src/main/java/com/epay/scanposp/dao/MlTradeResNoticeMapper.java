package com.epay.scanposp.dao;

import com.epay.scanposp.common.base.BaseDao;
import com.epay.scanposp.common.base.MyBatisRepository;
import com.epay.scanposp.entity.MlTradeResNotice;
import com.epay.scanposp.entity.MlTradeResNoticeExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
@MyBatisRepository
public interface MlTradeResNoticeMapper extends BaseDao< MlTradeResNotice, MlTradeResNoticeExample>{
    int countByExample(MlTradeResNoticeExample example);

    int deleteByExample(MlTradeResNoticeExample example);

    int insert(MlTradeResNotice record);

    int insertSelective(MlTradeResNotice record);

    List<MlTradeResNotice> selectByExample(MlTradeResNoticeExample example);

    int updateByExampleSelective(@Param("record") MlTradeResNotice record, @Param("example") MlTradeResNoticeExample example);

    int updateByExample(@Param("record") MlTradeResNotice record, @Param("example") MlTradeResNoticeExample example);
}