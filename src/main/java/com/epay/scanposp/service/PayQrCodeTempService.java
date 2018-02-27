package com.epay.scanposp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.epay.scanposp.common.base.BaseService;
import com.epay.scanposp.dao.PayQrCodeTempMapper;
import com.epay.scanposp.entity.PayQrCodeTemp;
import com.epay.scanposp.entity.PayQrCodeTempExample;

@Service
@Transactional
public class PayQrCodeTempService extends BaseService<PayQrCodeTemp,PayQrCodeTempExample> {
	@Autowired
	private PayQrCodeTempMapper payQrCodeTempMapper;
	
	@Autowired
	public void setPayQrCodeTempMapper(PayQrCodeTempMapper payQrCodeTempMapper) {
		super.setDao(payQrCodeTempMapper);
		this.payQrCodeTempMapper = payQrCodeTempMapper;
	}
	
	public int delete(PayQrCodeTemp record){
		return payQrCodeTempMapper.delete(record);
	}
}
