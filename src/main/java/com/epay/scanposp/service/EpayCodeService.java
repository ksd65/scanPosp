package com.epay.scanposp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.epay.scanposp.common.base.BaseService;
import com.epay.scanposp.dao.EpayCodeMapper;
import com.epay.scanposp.entity.EpayCode;
import com.epay.scanposp.entity.EpayCodeExample;

@Service
@Transactional
public class EpayCodeService extends BaseService<EpayCode,EpayCodeExample> {
	@Autowired
	private EpayCodeMapper epayCodeMapper;
	@Autowired
	public void setEpayCodeMapper(EpayCodeMapper epayCodeMapper) {
		super.setDao(epayCodeMapper);
		this.epayCodeMapper = epayCodeMapper;
	}
}
