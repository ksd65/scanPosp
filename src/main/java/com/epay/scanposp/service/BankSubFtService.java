package com.epay.scanposp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.epay.scanposp.common.base.BaseService;
import com.epay.scanposp.dao.BankSubFtMapper;
import com.epay.scanposp.entity.BankSubFt;
import com.epay.scanposp.entity.BankSubFtExample;

@Service
@Transactional
public class BankSubFtService extends BaseService<BankSubFt,BankSubFtExample> {
	@Autowired
	private BankSubFtMapper bankSubFtMapper;
	
	@Autowired
	public void setBankSubMapper(BankSubFtMapper bankSubFtMapper) {
		super.setDao(bankSubFtMapper);
		this.bankSubFtMapper = bankSubFtMapper;
	}

}
