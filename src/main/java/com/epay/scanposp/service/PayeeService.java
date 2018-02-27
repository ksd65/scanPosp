package com.epay.scanposp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.epay.scanposp.common.base.BaseService;
import com.epay.scanposp.dao.PayeeMapper;
import com.epay.scanposp.entity.Payee;
import com.epay.scanposp.entity.PayeeExample;

@Service
@Transactional
public class PayeeService extends BaseService<Payee,PayeeExample> {
	@Autowired
	private PayeeMapper payeeMapper;
	
	@Autowired
	public void setPayeeMapper(PayeeMapper payeeMapper) {
		super.setDao(payeeMapper);
		this.payeeMapper = payeeMapper;
	}
}
