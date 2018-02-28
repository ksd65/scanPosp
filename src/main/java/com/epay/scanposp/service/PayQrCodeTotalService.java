package com.epay.scanposp.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.epay.scanposp.common.base.BaseService;
import com.epay.scanposp.dao.PayQrCodeTotalMapper;
import com.epay.scanposp.entity.PayQrCodeTotal;
import com.epay.scanposp.entity.PayQrCodeTotalExample;

@Service
@Transactional
public class PayQrCodeTotalService extends BaseService<PayQrCodeTotal,PayQrCodeTotalExample> {
	@Autowired
	private PayQrCodeTotalMapper payQrCodeTotalMapper;
	
	@Autowired
	public void setPayQrCodeTotalMapper(PayQrCodeTotalMapper payQrCodeTotalMapper) {
		super.setDao(payQrCodeTotalMapper);
		this.payQrCodeTotalMapper = payQrCodeTotalMapper;
	}
	
	public List<PayQrCodeTotal> countPayeeOrder(Map<String,String> param){
		return payQrCodeTotalMapper.countPayeeOrder(param);
	}
}
