package com.epay.scanposp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.epay.scanposp.common.base.BaseService;
import com.epay.scanposp.dao.QuickPaySmsMapper;
import com.epay.scanposp.entity.QuickPaySms;
import com.epay.scanposp.entity.QuickPaySmsExample;

@Service
@Transactional
public class QuickPaySmsService extends BaseService<QuickPaySms,QuickPaySmsExample> {
	@Autowired
	private QuickPaySmsMapper quickPaySmsMapper;
	
	@Autowired
	public void setQuickPaySmsMapper(QuickPaySmsMapper quickPaySmsMapper) {
		super.setDao(quickPaySmsMapper);
		this.quickPaySmsMapper = quickPaySmsMapper;
	}
}
