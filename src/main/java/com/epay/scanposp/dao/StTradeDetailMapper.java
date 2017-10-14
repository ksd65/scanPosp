package com.epay.scanposp.dao;

import com.epay.scanposp.common.base.BaseDao;
import com.epay.scanposp.common.base.MyBatisRepository;
import com.epay.scanposp.entity.StTradeDetail;
import com.epay.scanposp.entity.StTradeDetailExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
@MyBatisRepository
public interface StTradeDetailMapper extends BaseDao<StTradeDetail, StTradeDetailExample>{
    int countByExample(StTradeDetailExample example);

    int deleteByExample(StTradeDetailExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(StTradeDetail record);

    int insertSelective(StTradeDetail record);

    List<StTradeDetail> selectByExample(StTradeDetailExample example);

    StTradeDetail selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") StTradeDetail record, @Param("example") StTradeDetailExample example);

    int updateByExample(@Param("record") StTradeDetail record, @Param("example") StTradeDetailExample example);

    int updateByPrimaryKeySelective(StTradeDetail record);

    int updateByPrimaryKey(StTradeDetail record);
}