package com.epay.scanposp.dao;

import com.epay.scanposp.common.base.MyBatisRepository;
import com.epay.scanposp.entity.JobRun;

@MyBatisRepository
public interface JobRunMapper {
    
    int insertJobRun(JobRun example);

    
}