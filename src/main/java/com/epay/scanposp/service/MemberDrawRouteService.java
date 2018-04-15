package com.epay.scanposp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.epay.scanposp.common.base.BaseService;
import com.epay.scanposp.dao.MemberDrawRouteMapper;
import com.epay.scanposp.entity.MemberDrawRoute;
import com.epay.scanposp.entity.MemberDrawRouteExample;

@Service
@Transactional
public class MemberDrawRouteService extends BaseService<MemberDrawRoute,MemberDrawRouteExample> {
	@Autowired
	private MemberDrawRouteMapper memberDrawRouteMapper;
	@Autowired
	public void setMemberDrawRouteMapper(MemberDrawRouteMapper memberDrawRouteMapper) {
		super.setDao(memberDrawRouteMapper);
		this.memberDrawRouteMapper = memberDrawRouteMapper;
	}
	
}
