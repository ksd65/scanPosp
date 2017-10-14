package com.epay.scanposp.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.epay.scanposp.common.base.BaseDao;
import com.epay.scanposp.common.base.MyBatisRepository;
import com.epay.scanposp.entity.TradeDetail;
import com.epay.scanposp.entity.TradeDetailExample;
@MyBatisRepository
public interface TradeDetailMapper extends BaseDao<TradeDetail, TradeDetailExample> {
    int countByExample(TradeDetailExample example);

    int deleteByExample(TradeDetailExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(TradeDetail record);

    int insertSelective(TradeDetail record);

    List<TradeDetail> selectByExample(TradeDetailExample example);

    TradeDetail selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") TradeDetail record, @Param("example") TradeDetailExample example);

    int updateByExample(@Param("record") TradeDetail record, @Param("example") TradeDetailExample example);

    int updateByPrimaryKeySelective(TradeDetail record);

    int updateByPrimaryKey(TradeDetail record);
}