package com.epay.scanposp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.epay.scanposp.common.base.BaseService;
import com.epay.scanposp.dao.MsChildConfigMapper;
import com.epay.scanposp.entity.MsChildConfig;
import com.epay.scanposp.entity.MsChildConfigExample;

@Service
@Transactional
public class MsChildConfigService extends BaseService<MsChildConfig,MsChildConfigExample> {
	@Autowired
	private MsChildConfigMapper msChildConfigMapper;
	@Autowired
	public void setMemberInfoMapper(MsChildConfigMapper msChildConfigMapper) {
		super.setDao(msChildConfigMapper);
		this.msChildConfigMapper = msChildConfigMapper;
	}
	
}
