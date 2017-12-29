package com.epay.scanposp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.epay.scanposp.common.base.BaseService;
import com.epay.scanposp.dao.PayTypeRuleMapper;
import com.epay.scanposp.entity.PayTypeRule;
import com.epay.scanposp.entity.PayTypeRuleExample;

@Service
@Transactional
public class PayTypeRuleService extends BaseService<PayTypeRule,PayTypeRuleExample> {
	@Autowired
	private PayTypeRuleMapper payTypeRuleMapper;
	@Autowired
	public void setPayTypeRuleMapper(PayTypeRuleMapper payTypeRuleMapper) {
		super.setDao(payTypeRuleMapper);
		this.payTypeRuleMapper = payTypeRuleMapper;
	}
	
}
