package com.epay.scanposp.dao;

import java.util.List;

import com.epay.scanposp.common.base.BaseDao;
import com.epay.scanposp.common.base.MyBatisRepository;
import com.epay.scanposp.entity.MemberDrawRoute;
import com.epay.scanposp.entity.MemberDrawRouteExample;
@MyBatisRepository
public interface MemberDrawRouteMapper extends BaseDao<MemberDrawRoute, MemberDrawRouteExample>{
    
    List<MemberDrawRoute> selectByExample(MemberDrawRouteExample example);

    
}