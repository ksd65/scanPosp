package com.epay.scanposp.dao;

import com.epay.scanposp.common.base.MyBatisRepository;
import com.epay.scanposp.entity.EskNotice;

@MyBatisRepository
public interface EskNoticeMapper {
    
    int insertNotice(EskNotice example);

    
}