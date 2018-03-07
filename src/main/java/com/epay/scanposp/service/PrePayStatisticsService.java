package com.epay.scanposp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.epay.scanposp.common.base.BaseService;
import com.epay.scanposp.dao.PrePayStatisticsMapper;
import com.epay.scanposp.entity.PrePayStatistics;
import com.epay.scanposp.entity.PrePayStatisticsExample;

@Service
@Transactional
public class PrePayStatisticsService extends BaseService<PrePayStatistics,PrePayStatisticsExample> {
	@Autowired
	private PrePayStatisticsMapper prePayStatisticsMapper;
	@Autowired
	public void setPrePayStatisticsMapper(PrePayStatisticsMapper prePayStatisticsMapper) {
		super.setDao(prePayStatisticsMapper);
		this.prePayStatisticsMapper = prePayStatisticsMapper;
	}
	
}
