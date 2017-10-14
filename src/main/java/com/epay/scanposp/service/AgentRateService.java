package com.epay.scanposp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.epay.scanposp.common.base.BaseService;
import com.epay.scanposp.dao.AgentRateMapper;
import com.epay.scanposp.entity.AgentRate;
import com.epay.scanposp.entity.AgentRateExample;

@Service
@Transactional
public class AgentRateService extends BaseService<AgentRate,AgentRateExample> {
	@Autowired
	private AgentRateMapper agentRateMapper;
	
	@Autowired
	public void setAgentRateMapper(AgentRateMapper agentRateMapper) {
		super.setDao(agentRateMapper);
		this.agentRateMapper = agentRateMapper;
	}

}
