package com.epay.scanposp.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.epay.scanposp.common.base.BaseService;
import com.epay.scanposp.dao.VerifyCodeMapper;
import com.epay.scanposp.entity.VerifyCode;
import com.epay.scanposp.entity.VerifyCodeExample;

@Service
@Transactional
public class VerifyCodeService extends BaseService<VerifyCode,VerifyCodeExample> {
	@Autowired
	private VerifyCodeMapper verifyCodeMapper;
	
	@Autowired
	public void setVerifyCodeMapper(VerifyCodeMapper verifyCodeMapper) {
		super.setDao(verifyCodeMapper);
		this.verifyCodeMapper = verifyCodeMapper;
	}

	public int updateCodeStateByType(Map<String,Object> paramMap){
		return verifyCodeMapper.updateCodeStateByType(paramMap);
	}
}
