package com.epay.scanposp.dao;

import com.epay.scanposp.common.base.BaseDao;
import com.epay.scanposp.common.base.MyBatisRepository;
import com.epay.scanposp.entity.DebitNote;
import com.epay.scanposp.entity.DebitNoteExample;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
@MyBatisRepository
public interface DebitNoteMapper extends BaseDao<DebitNote, DebitNoteExample>{
    int countByExample(DebitNoteExample example);

    int deleteByExample(DebitNoteExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(DebitNote record);

    int insertSelective(DebitNote record);

    List<DebitNote> selectByExample(DebitNoteExample example);

    DebitNote selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") DebitNote record, @Param("example") DebitNoteExample example);

    int updateByExample(@Param("record") DebitNote record, @Param("example") DebitNoteExample example);

    int updateByPrimaryKeySelective(DebitNote record);

    int updateByPrimaryKey(DebitNote record);
    
    List<DebitNote> selectNoteOutTimes(Map<String, Object> paramMap);
    
    Double countTransactionMoneyByCondition(Map<String,Object> paramMap);
    
    Double countTransactionRateByCondition(Map<String,Object> paramMap);
}