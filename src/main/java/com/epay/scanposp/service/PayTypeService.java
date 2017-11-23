package com.epay.scanposp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.epay.scanposp.common.base.BaseService;
import com.epay.scanposp.dao.PayTypeMapper;
import com.epay.scanposp.entity.PayType;
import com.epay.scanposp.entity.PayTypeExample;

@Service
@Transactional
public class PayTypeService extends BaseService<PayType,PayTypeExample> {
	@Autowired
	private PayTypeMapper payTypeMapper;
	@Autowired
	public void setPayTypeMapper(PayTypeMapper payTypeMapper) {
		super.setDao(payTypeMapper);
		this.payTypeMapper = payTypeMapper;
	}
	
}
