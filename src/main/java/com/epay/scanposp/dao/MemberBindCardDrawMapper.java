package com.epay.scanposp.dao;

import java.util.List;
import java.util.Map;

import com.epay.scanposp.common.base.BaseDao;
import com.epay.scanposp.common.base.MyBatisRepository;
import com.epay.scanposp.entity.MemberBindCardDraw;
import com.epay.scanposp.entity.MemberBindCardDrawExample;
@MyBatisRepository
public interface MemberBindCardDrawMapper extends BaseDao<MemberBindCardDraw, MemberBindCardDrawExample>{
    int countByExample(MemberBindCardDrawExample example);

    int deleteByExample(MemberBindCardDrawExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(MemberBindCardDraw record);

    int insertSelective(MemberBindCardDraw record);

    List<MemberBindCardDraw> selectByExample(MemberBindCardDrawExample example);

    MemberBindCardDraw selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(MemberBindCardDraw record);

    int updateByPrimaryKey(MemberBindCardDraw record);
    
    List<Map<String,Object>> getMemberNotBindCard(Map<String,Object> param);
}