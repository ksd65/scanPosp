package com.epay.scanposp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.epay.scanposp.common.base.BaseService;
import com.epay.scanposp.dao.SysOfficeMapper;
import com.epay.scanposp.entity.SysOffice;
import com.epay.scanposp.entity.SysOfficeExample;

@Service
@Transactional
public class SysOfficeService extends BaseService<SysOffice,SysOfficeExample> {
	@Autowired
	private SysOfficeMapper sysOfficeMapper;
	
	@Autowired
	public void setSysOfficeMapper(SysOfficeMapper sysOfficeMapper) {
		super.setDao(sysOfficeMapper);
		this.sysOfficeMapper = sysOfficeMapper;
	}

}
