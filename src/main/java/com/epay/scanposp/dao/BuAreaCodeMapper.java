package com.epay.scanposp.dao;

import com.epay.scanposp.common.base.BaseDao;
import com.epay.scanposp.common.base.MyBatisRepository;
import com.epay.scanposp.entity.BuAreaCode;
import com.epay.scanposp.entity.BuAreaCodeExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
@MyBatisRepository
public interface BuAreaCodeMapper extends BaseDao<BuAreaCode,BuAreaCodeExample>{
    int countByExample(BuAreaCodeExample example);

    int deleteByExample(BuAreaCodeExample example);

    int insert(BuAreaCode record);

    int insertSelective(BuAreaCode record);

    List<BuAreaCode> selectByExample(BuAreaCodeExample example);

    int updateByExampleSelective(@Param("record") BuAreaCode record, @Param("example") BuAreaCodeExample example);

    int updateByExample(@Param("record") BuAreaCode record, @Param("example") BuAreaCodeExample example);
}