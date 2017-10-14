package com.epay.scanposp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.epay.scanposp.common.base.BaseService;
import com.epay.scanposp.dao.RouteWayMapper;
import com.epay.scanposp.entity.RouteWay;
import com.epay.scanposp.entity.RouteWayExample;

@Service
@Transactional
public class RouteWayService extends BaseService<RouteWay,RouteWayExample> {
	@Autowired
	private RouteWayMapper routeWayMapper;
	
	@Autowired
	public void setRouteWayMapper(RouteWayMapper routeWayMapper) {
		super.setDao(routeWayMapper);
		this.routeWayMapper = routeWayMapper;
	}
}
