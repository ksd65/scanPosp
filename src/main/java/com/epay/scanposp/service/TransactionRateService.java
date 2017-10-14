package com.epay.scanposp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.epay.scanposp.common.base.BaseService;
import com.epay.scanposp.dao.TransactionRateMapper;
import com.epay.scanposp.entity.TransactionRate;
import com.epay.scanposp.entity.TransactionRateExample;

@Service
@Transactional
public class TransactionRateService extends BaseService<TransactionRate,TransactionRateExample> {
	@Autowired
	private TransactionRateMapper transactionRateMapper;
	
	@Autowired
	public void setTransactionRateMapper(TransactionRateMapper transactionRateMapper) {
		super.setDao(transactionRateMapper);
		this.transactionRateMapper = transactionRateMapper;
	}

}
