package com.epay.scanposp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.epay.scanposp.common.base.BaseService;
import com.epay.scanposp.dao.RoutewayDrawMapper;
import com.epay.scanposp.entity.RoutewayDraw;
import com.epay.scanposp.entity.RoutewayDrawExample;

@Service
@Transactional
public class RoutewayDrawService extends BaseService<RoutewayDraw,RoutewayDrawExample> {
	@Autowired
	private RoutewayDrawMapper routewayDrawMapper;
	
	@Autowired
	public void setRoutewayDrawMapper(RoutewayDrawMapper routewayDrawMapper) {
		super.setDao(routewayDrawMapper);
		this.routewayDrawMapper = routewayDrawMapper;
	}
}
