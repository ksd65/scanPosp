package com.epay.scanposp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.epay.scanposp.common.base.BaseService;
 
import com.epay.scanposp.dao.BuAreaCodeMapper; 
import com.epay.scanposp.entity.BuAreaCode;
import com.epay.scanposp.entity.BuAreaCodeExample;

@Service
@Transactional
public class BuAreaCodeService extends BaseService<BuAreaCode,BuAreaCodeExample> {
	@Autowired
	private BuAreaCodeMapper buAreaCodeMapper; 
	@Autowired
	public void setBuAreaCodeMapper(BuAreaCodeMapper buAreaCodeMapper) {
		super.setDao(buAreaCodeMapper);
		this.buAreaCodeMapper = buAreaCodeMapper;
	} 
}
