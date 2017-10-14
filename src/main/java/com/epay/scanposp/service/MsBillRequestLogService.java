package com.epay.scanposp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.epay.scanposp.common.base.BaseService;
import com.epay.scanposp.dao.MsBillRequestLogMapper;
import com.epay.scanposp.entity.MsBillRequestLog;
import com.epay.scanposp.entity.MsBillRequestLogExample;

@Service
@Transactional
public class MsBillRequestLogService extends BaseService<MsBillRequestLog,MsBillRequestLogExample> {
	@Autowired
	private MsBillRequestLogMapper msBillRequestLogMapper;
	
	@Autowired
	public void setMsBillRequestLogMapper(MsBillRequestLogMapper msBillRequestLogMapper) {
		super.setDao(msBillRequestLogMapper);
		this.msBillRequestLogMapper = msBillRequestLogMapper;
	}

}
