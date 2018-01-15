package com.epay.scanposp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.epay.scanposp.common.base.BaseService;
import com.epay.scanposp.dao.RoutewayAccountMapper;
import com.epay.scanposp.entity.RoutewayAccount;
import com.epay.scanposp.entity.RoutewayAccountExample;

@Service
@Transactional
public class RoutewayAccountService extends BaseService<RoutewayAccount,RoutewayAccountExample> {
	@Autowired
	private RoutewayAccountMapper routewayAccountMapper;
	
	@Autowired
	public void setRoutewayAccountMapper(RoutewayAccountMapper routewayAccountMapper) {
		super.setDao(routewayAccountMapper);
		this.routewayAccountMapper = routewayAccountMapper;
	}
}
