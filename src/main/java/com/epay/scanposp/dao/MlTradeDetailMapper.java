package com.epay.scanposp.dao;

import com.epay.scanposp.common.base.BaseDao;
import com.epay.scanposp.common.base.MyBatisRepository;
import com.epay.scanposp.entity.MlTradeDetail;
import com.epay.scanposp.entity.MlTradeDetailExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
@MyBatisRepository
public interface MlTradeDetailMapper extends BaseDao<MlTradeDetail,MlTradeDetailExample>{
    int countByExample(MlTradeDetailExample example);

    int deleteByExample(MlTradeDetailExample example);

    int insert(MlTradeDetail record);

    int insertSelective(MlTradeDetail record);

    List<MlTradeDetail> selectByExample(MlTradeDetailExample example);

    int updateByExampleSelective(@Param("record") MlTradeDetail record, @Param("example") MlTradeDetailExample example);

    int updateByExample(@Param("record") MlTradeDetail record, @Param("example") MlTradeDetailExample example);
}