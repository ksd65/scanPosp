package com.epay.scanposp.dao;

import com.epay.scanposp.common.base.BaseDao;
import com.epay.scanposp.common.base.MyBatisRepository;
import com.epay.scanposp.entity.Kbin;
import com.epay.scanposp.entity.KbinExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

@MyBatisRepository
public interface KbinMapper extends BaseDao<Kbin, KbinExample>{
    int countByExample(KbinExample example);

    int deleteByExample(KbinExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Kbin record);

    int insertSelective(Kbin record);

    List<Kbin> selectByExample(KbinExample example);

    Kbin selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Kbin record, @Param("example") KbinExample example);

    int updateByExample(@Param("record") Kbin record, @Param("example") KbinExample example);

    int updateByPrimaryKeySelective(Kbin record);

    int updateByPrimaryKey(Kbin record);
}