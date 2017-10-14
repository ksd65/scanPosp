package com.epay.scanposp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.epay.scanposp.common.base.BaseService;
import com.epay.scanposp.dao.AccountLogMapper;
import com.epay.scanposp.entity.AccountLog;
import com.epay.scanposp.entity.AccountLogExample;

@Service
@Transactional
public class AccountLogService extends BaseService<AccountLog,AccountLogExample> {
	@Autowired
	private AccountLogMapper accountLogMapper;
	
	@Autowired
	public void setAccountLogMapper(AccountLogMapper accountLogMapper) {
		super.setDao(accountLogMapper);
		this.accountLogMapper = accountLogMapper;
	}

}
