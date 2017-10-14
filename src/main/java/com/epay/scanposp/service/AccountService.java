package com.epay.scanposp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.epay.scanposp.common.base.BaseService;
import com.epay.scanposp.dao.AccountMapper;
import com.epay.scanposp.entity.Account;
import com.epay.scanposp.entity.AccountExample;

@Service
@Transactional
public class AccountService extends BaseService<Account,AccountExample> {
	@Autowired
	private AccountMapper accountMapper;
	
	@Autowired
	public void setAccountMapper(AccountMapper accountMapper) {
		super.setDao(accountMapper);
		this.accountMapper = accountMapper;
	}
}
