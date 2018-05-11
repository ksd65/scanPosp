package com.epay.scanposp.dao;

import java.util.List;

import com.epay.scanposp.common.base.BaseDao;
import com.epay.scanposp.common.base.MyBatisRepository;
import com.epay.scanposp.entity.DebitNoteSub;
import com.epay.scanposp.entity.DebitNoteSubExample;
@MyBatisRepository
public interface DebitNoteSubMapper extends BaseDao<DebitNoteSub, DebitNoteSubExample>{
    int countByExample(DebitNoteSubExample example);

    int insert(DebitNoteSub record);

    int insertSelective(DebitNoteSub record);

    List<DebitNoteSub> selectByExample(DebitNoteSubExample example);

    DebitNoteSub selectByPrimaryKey(Integer id);

    
}