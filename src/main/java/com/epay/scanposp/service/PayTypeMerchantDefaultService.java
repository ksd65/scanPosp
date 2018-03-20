package com.epay.scanposp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.epay.scanposp.common.base.BaseService;
import com.epay.scanposp.dao.PayTypeMerchantDefaultMapper;
import com.epay.scanposp.entity.PayTypeMerchantDefault;
import com.epay.scanposp.entity.PayTypeMerchantDefaultExample;

@Service
@Transactional
public class PayTypeMerchantDefaultService extends BaseService<PayTypeMerchantDefault,PayTypeMerchantDefaultExample> {
	@Autowired
	private PayTypeMerchantDefaultMapper payTypeMerchantDefaultMapper;
	@Autowired
	public void setPayTypeMerchantDefaultMapper(PayTypeMerchantDefaultMapper payTypeMerchantDefaultMapper) {
		super.setDao(payTypeMerchantDefaultMapper);
		this.payTypeMerchantDefaultMapper = payTypeMerchantDefaultMapper;
	}
	
}
