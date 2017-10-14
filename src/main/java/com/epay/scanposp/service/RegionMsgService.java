package com.epay.scanposp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.epay.scanposp.common.base.BaseService;
import com.epay.scanposp.dao.RegionMsgMapper;
import com.epay.scanposp.entity.RegionMsg;
import com.epay.scanposp.entity.RegionMsgExample;

@Service
@Transactional
public class RegionMsgService extends BaseService<RegionMsg,RegionMsgExample> {
	@Autowired
	private RegionMsgMapper regionMsgMapper;
	
	@Autowired
	public void setRegionMsgMapper(RegionMsgMapper regionMsgMapper) {
		super.setDao(regionMsgMapper);
		this.regionMsgMapper = regionMsgMapper;
	}

}
