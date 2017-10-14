package com.epay.scanposp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.epay.scanposp.common.base.BaseService;
import com.epay.scanposp.dao.SysCommonConfigMapper;
import com.epay.scanposp.entity.SysCommonConfig;
import com.epay.scanposp.entity.SysCommonConfigExample;

@Service
@Transactional
public class SysCommonConfigService extends BaseService<SysCommonConfig,SysCommonConfigExample> {
	@Autowired
	private SysCommonConfigMapper sysCommonConfigMapper;
	
	@Autowired
	public void setSysCommonConfigMapper(SysCommonConfigMapper sysCommonConfigMapper) {
		super.setDao(sysCommonConfigMapper);
		this.sysCommonConfigMapper = sysCommonConfigMapper;
	}

}
