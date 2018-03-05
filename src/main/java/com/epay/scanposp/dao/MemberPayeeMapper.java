package com.epay.scanposp.dao;

import java.util.List;
import java.util.Map;

import com.epay.scanposp.common.base.BaseDao;
import com.epay.scanposp.common.base.MyBatisRepository;
import com.epay.scanposp.entity.MemberPayee;
import com.epay.scanposp.entity.MemberPayeeExample;
@MyBatisRepository
public interface MemberPayeeMapper extends BaseDao<MemberPayee, MemberPayeeExample>{
    int countByExample(MemberPayeeExample example);

    int deleteByExample(MemberPayeeExample example);

    int deleteByPrimaryKey(Integer id);

    List<MemberPayee> selectByExample(MemberPayeeExample example);

    MemberPayee selectByPrimaryKey(Integer id);

    List<MemberPayee> selectByMap(Map<String,Object> param);
    
}