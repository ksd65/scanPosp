package com.epay.scanposp.dao;

import com.epay.scanposp.common.base.BaseDao;
import com.epay.scanposp.common.base.MyBatisRepository;
import com.epay.scanposp.entity.MlTradeDetailAll;
import com.epay.scanposp.entity.MlTradeDetailAllExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
@MyBatisRepository
public interface MlTradeDetailAllMapper extends BaseDao<MlTradeDetailAll, MlTradeDetailAllExample> {
    int countByExample(MlTradeDetailAllExample example);

    int deleteByExample(MlTradeDetailAllExample example);

    int insert(MlTradeDetailAll record);

    int insertSelective(MlTradeDetailAll record);

    List<MlTradeDetailAll> selectByExample(MlTradeDetailAllExample example);

    int updateByExampleSelective(@Param("record") MlTradeDetailAll record, @Param("example") MlTradeDetailAllExample example);

    int updateByExample(@Param("record") MlTradeDetailAll record, @Param("example") MlTradeDetailAllExample example);
}