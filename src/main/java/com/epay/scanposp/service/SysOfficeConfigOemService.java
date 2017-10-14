package com.epay.scanposp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.epay.scanposp.common.base.BaseService;
import com.epay.scanposp.dao.SysOfficeConfigOemMapper;
import com.epay.scanposp.entity.SysOfficeConfigOem;
import com.epay.scanposp.entity.SysOfficeConfigOemExample;

@Service
@Transactional
public class SysOfficeConfigOemService extends BaseService<SysOfficeConfigOem,SysOfficeConfigOemExample> {
	@Autowired
	private SysOfficeConfigOemMapper sysOfficeConfigOemMapper;

	@Autowired
	public void setSysOfficeConfigOemMapper(SysOfficeConfigOemMapper sysOfficeConfigOemMapper) {
		super.setDao(sysOfficeConfigOemMapper);
		this.sysOfficeConfigOemMapper = sysOfficeConfigOemMapper;
	}
	
}
