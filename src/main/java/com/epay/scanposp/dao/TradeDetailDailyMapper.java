package com.epay.scanposp.dao;

import java.util.List;
import java.util.Map;

import com.epay.scanposp.common.base.BaseDao;
import com.epay.scanposp.common.base.MyBatisRepository;
import com.epay.scanposp.entity.TradeDetail;
import com.epay.scanposp.entity.TradeDetailDaily;
import com.epay.scanposp.entity.TradeDetailDailyExample;
@MyBatisRepository
public interface TradeDetailDailyMapper extends BaseDao<TradeDetailDaily, TradeDetailDailyExample> {
    int countByExample(TradeDetailDailyExample example);

    int insert(TradeDetail record);

    int insertSelective(TradeDetail record);

    List<TradeDetailDaily> selectByExample(TradeDetailDailyExample example);

    TradeDetailDaily selectByPrimaryKey(Integer id);

    Double countTradeDetailDaily(Map<String, Object> paramMap);
    
}