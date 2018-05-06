package com.epay.scanposp.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.epay.scanposp.common.base.BaseService;
import com.epay.scanposp.dao.RoutewayDrawProfitMapper;
import com.epay.scanposp.entity.RoutewayDrawProfit;
import com.epay.scanposp.entity.RoutewayDrawProfitExample;

@Service
@Transactional
public class RoutewayDrawProfitService extends BaseService<RoutewayDrawProfit,RoutewayDrawProfitExample> {
	@Autowired
	private RoutewayDrawProfitMapper routewayDrawProfitMapper;
	@Autowired
	public void setRoutewayDrawProfitMapper(RoutewayDrawProfitMapper routewayDrawProfitMapper) {
		super.setDao(routewayDrawProfitMapper);
		this.routewayDrawProfitMapper = routewayDrawProfitMapper;
	}
	
	public List<Map<String,Object>> getDrawList(Map<String,Object> param){
		return routewayDrawProfitMapper.getDrawList(param);
	}

}
