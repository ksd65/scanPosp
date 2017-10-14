package com.epay.scanposp.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.epay.scanposp.dao.SysOfficeExtendMapper;
import com.epay.scanposp.entity.SysOffice;

@Service
@Transactional
public class SysOfficeExtendService {
	@Autowired
	private SysOfficeExtendMapper sysOfficeExtendMapper;
	
	@Autowired
	public void setSysOfficeExtendMapper(SysOfficeExtendMapper sysOfficeExtendMapper) {
		this.sysOfficeExtendMapper = sysOfficeExtendMapper;
	}

	public SysOffice getSysOfficeByOrderCode(Map<String,Object> paramMap){
		return sysOfficeExtendMapper.getSysOfficeByOrderCode(paramMap);
	}
	public SysOffice getSysOfficeByMemberCode(Map<String,Object> paramMap){
		return sysOfficeExtendMapper.getSysOfficeByMemberCode(paramMap);
	}
}
