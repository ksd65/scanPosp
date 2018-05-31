package com.epay.scanposp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.epay.scanposp.dao.JobRunMapper;
import com.epay.scanposp.entity.JobRun;

@Service
@Transactional
public class JobRunService {
	@Autowired
	private JobRunMapper jobRunMapper;
	
	
	public int  insertJobRun(JobRun jobRun) {
		return jobRunMapper.insertJobRun(jobRun);
	}
}
