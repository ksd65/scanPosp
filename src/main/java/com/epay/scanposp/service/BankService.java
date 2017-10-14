package com.epay.scanposp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.epay.scanposp.common.base.BaseService;
import com.epay.scanposp.dao.BankMapper;
import com.epay.scanposp.entity.Bank;
import com.epay.scanposp.entity.BankExample;

@Service
@Transactional
public class BankService extends BaseService<Bank,BankExample> {
	@Autowired
	private BankMapper bankMapper;
	
	@Autowired
	public void setBankMapper(BankMapper bankMapper) {
		super.setDao(bankMapper);
		this.bankMapper = bankMapper;
	}
}
