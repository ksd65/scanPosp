package com.epay.scanposp.dao;

import com.epay.scanposp.common.base.BaseDao;
import com.epay.scanposp.common.base.MyBatisRepository;
import com.epay.scanposp.entity.MsSettleFailRecord;
import com.epay.scanposp.entity.MsSettleFailRecordExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
@MyBatisRepository
public interface MsSettleFailRecordMapper extends BaseDao<MsSettleFailRecord, MsSettleFailRecordExample>{
    int countByExample(MsSettleFailRecordExample example);

    int deleteByExample(MsSettleFailRecordExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(MsSettleFailRecord record);

    int insertSelective(MsSettleFailRecord record);

    List<MsSettleFailRecord> selectByExample(MsSettleFailRecordExample example);

    MsSettleFailRecord selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") MsSettleFailRecord record, @Param("example") MsSettleFailRecordExample example);

    int updateByExample(@Param("record") MsSettleFailRecord record, @Param("example") MsSettleFailRecordExample example);

    int updateByPrimaryKeySelective(MsSettleFailRecord record);

    int updateByPrimaryKey(MsSettleFailRecord record);
}