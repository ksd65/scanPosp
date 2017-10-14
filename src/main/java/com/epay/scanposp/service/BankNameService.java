package com.epay.scanposp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.epay.scanposp.common.base.BaseService;
import com.epay.scanposp.dao.BankNameMapper;
import com.epay.scanposp.dao.KbinMapper;
import com.epay.scanposp.entity.BankName;
import com.epay.scanposp.entity.BankNameExample;
import com.epay.scanposp.entity.Kbin;
import com.epay.scanposp.entity.KbinExample;

@Service
@Transactional
public class BankNameService extends BaseService<BankName,BankNameExample> {
	@Autowired
	private BankNameMapper bankNameMapper;
	
	@Autowired
	public void setBankNameMapper(BankNameMapper bankNameMapper) {
		super.setDao(bankNameMapper);
		this.bankNameMapper = bankNameMapper;
	}

}
