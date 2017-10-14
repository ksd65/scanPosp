package com.epay.scanposp.dao;

import com.epay.scanposp.common.base.BaseDao;
import com.epay.scanposp.common.base.MyBatisRepository;
import com.epay.scanposp.entity.StTradeDetailAll;
import com.epay.scanposp.entity.StTradeDetailAllExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
@MyBatisRepository
public interface StTradeDetailAllMapper extends BaseDao<StTradeDetailAll, StTradeDetailAllExample>{
    int countByExample(StTradeDetailAllExample example);

    int deleteByExample(StTradeDetailAllExample example);

    int insert(StTradeDetailAll record);

    int insertSelective(StTradeDetailAll record);

    List<StTradeDetailAll> selectByExample(StTradeDetailAllExample example);

    int updateByExampleSelective(@Param("record") StTradeDetailAll record, @Param("example") StTradeDetailAllExample example);

    int updateByExample(@Param("record") StTradeDetailAll record, @Param("example") StTradeDetailAllExample example);
}