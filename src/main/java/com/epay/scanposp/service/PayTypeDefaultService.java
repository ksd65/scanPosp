package com.epay.scanposp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.epay.scanposp.common.base.BaseService;
import com.epay.scanposp.dao.PayTypeDefaultMapper;
import com.epay.scanposp.entity.PayTypeDefault;
import com.epay.scanposp.entity.PayTypeDefaultExample;

@Service
@Transactional
public class PayTypeDefaultService extends BaseService<PayTypeDefault,PayTypeDefaultExample> {
	@Autowired
	private PayTypeDefaultMapper payTypeDefaultMapper;
	@Autowired
	public void setPayTypeDefaultMapper(PayTypeDefaultMapper payTypeDefaultMapper) {
		super.setDao(payTypeDefaultMapper);
		this.payTypeDefaultMapper = payTypeDefaultMapper;
	}
	
}
