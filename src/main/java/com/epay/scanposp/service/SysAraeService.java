package com.epay.scanposp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.epay.scanposp.common.base.BaseService;
import com.epay.scanposp.dao.SysAreaMapper;
import com.epay.scanposp.entity.SysArea;
import com.epay.scanposp.entity.SysAreaExample;

@Service
@Transactional
public class SysAraeService extends BaseService<SysArea,SysAreaExample> {
	@Autowired
	private SysAreaMapper sysAreaMapper;
	
	@Autowired
	public void setSysAreaMapper(SysAreaMapper sysAreaMapper) {
		super.setDao(sysAreaMapper);
		this.sysAreaMapper = sysAreaMapper;
	}

}
