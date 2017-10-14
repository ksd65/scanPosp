package com.epay.scanposp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.epay.scanposp.common.base.BaseService;
import com.epay.scanposp.dao.PayResultNoticeMapper;
import com.epay.scanposp.dao.RegisterResultNoticeMapper;
import com.epay.scanposp.entity.RegisterResultNotice;
import com.epay.scanposp.entity.RegisterResultNoticeExample;

@Service
@Transactional
public class RegisterResultNoticeService extends BaseService<RegisterResultNotice,RegisterResultNoticeExample> {
	@Autowired
	private RegisterResultNoticeMapper registerResultNoticeMapper;
	
	@Autowired
	public void setRegisterResultNoticeMapper(RegisterResultNoticeMapper registerResultNoticeMapper) {
		super.setDao(registerResultNoticeMapper);
		this.registerResultNoticeMapper = registerResultNoticeMapper;
	}

}
