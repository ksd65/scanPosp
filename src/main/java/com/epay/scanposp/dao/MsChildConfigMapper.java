package com.epay.scanposp.dao;

import com.epay.scanposp.common.base.BaseDao;
import com.epay.scanposp.common.base.MyBatisRepository;
import com.epay.scanposp.entity.MsChildConfig;
import com.epay.scanposp.entity.MsChildConfigExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

@MyBatisRepository
public interface MsChildConfigMapper extends BaseDao<MsChildConfig, MsChildConfigExample>{
    int countByExample(MsChildConfigExample example);

    int deleteByExample(MsChildConfigExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(MsChildConfig record);

    int insertSelective(MsChildConfig record);

    List<MsChildConfig> selectByExample(MsChildConfigExample example);

    MsChildConfig selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") MsChildConfig record, @Param("example") MsChildConfigExample example);

    int updateByExample(@Param("record") MsChildConfig record, @Param("example") MsChildConfigExample example);

    int updateByPrimaryKeySelective(MsChildConfig record);

    int updateByPrimaryKey(MsChildConfig record);
}