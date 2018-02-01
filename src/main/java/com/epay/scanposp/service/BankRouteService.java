package com.epay.scanposp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.epay.scanposp.common.base.BaseService;
import com.epay.scanposp.dao.BankRouteMapper;
import com.epay.scanposp.entity.BankRoute;
import com.epay.scanposp.entity.BankRouteExample;

@Service
@Transactional
public class BankRouteService extends BaseService<BankRoute,BankRouteExample> {
	@Autowired
	private BankRouteMapper bankRouteMapper;
	
	@Autowired
	public void setBankRouteMapper(BankRouteMapper bankRouteMapper) {
		super.setDao(bankRouteMapper);
		this.bankRouteMapper = bankRouteMapper;
	}

}
