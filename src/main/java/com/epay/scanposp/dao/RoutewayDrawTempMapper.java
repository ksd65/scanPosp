package com.epay.scanposp.dao;

import com.epay.scanposp.common.base.MyBatisRepository;
import com.epay.scanposp.entity.RoutewayDrawTemp;

@MyBatisRepository
public interface RoutewayDrawTempMapper {
    
    int insertRoutewayDrawTemp(RoutewayDrawTemp example);

    int deleteRoutewayDrawTemp(RoutewayDrawTemp example);
}