package com.epay.scanposp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.epay.scanposp.common.base.BaseService;
import com.epay.scanposp.dao.PayRouteMapper;
import com.epay.scanposp.entity.PayRoute;
import com.epay.scanposp.entity.PayRouteExample;

@Service
@Transactional
public class PayRouteService extends BaseService<PayRoute,PayRouteExample> {
	@Autowired
	private PayRouteMapper payRouteMapper;
	@Autowired
	public void setPayRouteMapper(PayRouteMapper payRouteMapper) {
		super.setDao(payRouteMapper);
		this.payRouteMapper = payRouteMapper;
	}
	
}
