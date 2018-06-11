package com.epay.scanposp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.epay.scanposp.dao.RoutewayDrawTempMapper;
import com.epay.scanposp.entity.RoutewayDrawTemp;

@Service
@Transactional
public class RoutewayDrawTempService {
	@Autowired
	private RoutewayDrawTempMapper routewayDrawTempMapper;
	
	
	public int  insertRoutewayDrawTemp(RoutewayDrawTemp example) {
		return routewayDrawTempMapper.insertRoutewayDrawTemp(example);
	}
	
	public int  deleteRoutewayDrawTemp(RoutewayDrawTemp example) {
		return routewayDrawTempMapper.deleteRoutewayDrawTemp(example);
	}
	
}
