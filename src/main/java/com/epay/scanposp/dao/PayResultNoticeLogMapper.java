package com.epay.scanposp.dao;

import com.epay.scanposp.common.base.MyBatisRepository;
import com.epay.scanposp.entity.PayResultNoticeLog;

@MyBatisRepository
public interface PayResultNoticeLogMapper {
    
    int insertNoticeLog(PayResultNoticeLog example);

    
}