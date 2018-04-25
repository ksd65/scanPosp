package com.epay.scanposp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.epay.scanposp.common.base.BaseService;
import com.epay.scanposp.dao.RoutewayDrawLogMapper;
import com.epay.scanposp.entity.RoutewayDrawLog;
import com.epay.scanposp.entity.RoutewayDrawLogExample;

@Service
@Transactional
public class RoutewayDrawLogService extends BaseService<RoutewayDrawLog,RoutewayDrawLogExample> {
	@Autowired
	private RoutewayDrawLogMapper routewayDrawLogMapper;
	
	@Autowired
	public void setRoutewayDrawLogMapper(RoutewayDrawLogMapper routewayDrawLogMapper) {
		super.setDao(routewayDrawLogMapper);
		this.routewayDrawLogMapper = routewayDrawLogMapper;
	}
}
