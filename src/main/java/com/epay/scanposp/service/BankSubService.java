package com.epay.scanposp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.epay.scanposp.common.base.BaseService;
import com.epay.scanposp.dao.BankSubMapper;
import com.epay.scanposp.entity.BankSub;
import com.epay.scanposp.entity.BankSubExample;

@Service
@Transactional
public class BankSubService extends BaseService<BankSub,BankSubExample> {
	@Autowired
	private BankSubMapper bankSubMapper;
	
	@Autowired
	public void setBankSubMapper(BankSubMapper bankSubMapper) {
		super.setDao(bankSubMapper);
		this.bankSubMapper = bankSubMapper;
	}

}
