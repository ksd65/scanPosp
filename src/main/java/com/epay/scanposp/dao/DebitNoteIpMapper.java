package com.epay.scanposp.dao;

import java.util.List;
import java.util.Map;

import com.epay.scanposp.common.base.BaseDao;
import com.epay.scanposp.common.base.MyBatisRepository;
import com.epay.scanposp.entity.DebitNoteIp;
import com.epay.scanposp.entity.DebitNoteIpExample;
@MyBatisRepository
public interface DebitNoteIpMapper extends BaseDao<DebitNoteIp, DebitNoteIpExample>{
    int countByExample(DebitNoteIpExample example);

    int insert(DebitNoteIp record);

    int insertSelective(DebitNoteIp record);

    List<DebitNoteIp> selectByExample(DebitNoteIpExample example);

    DebitNoteIp selectByPrimaryKey(Integer id);

    public Integer countDebitNoteIpByCondition(Map<String, Object> paramMap);
}