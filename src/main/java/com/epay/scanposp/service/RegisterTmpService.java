package com.epay.scanposp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.epay.scanposp.common.base.BaseService;
import com.epay.scanposp.dao.RegisterTmpMapper;
import com.epay.scanposp.entity.RegisterTmp;
import com.epay.scanposp.entity.RegisterTmpExample;

@Service
@Transactional
public class RegisterTmpService extends BaseService<RegisterTmp,RegisterTmpExample> {
	@Autowired
	private RegisterTmpMapper registerTmpMapper;
	@Autowired
	public void setRegisterTmpMapper(RegisterTmpMapper registerTmpMapper) {
		super.setDao(registerTmpMapper);
		this.registerTmpMapper = registerTmpMapper;
	}
	
}
