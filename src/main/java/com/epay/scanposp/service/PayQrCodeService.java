package com.epay.scanposp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.epay.scanposp.common.base.BaseService;
import com.epay.scanposp.dao.PayQrCodeMapper;
import com.epay.scanposp.entity.PayQrCode;
import com.epay.scanposp.entity.PayQrCodeExample;

@Service
@Transactional
public class PayQrCodeService extends BaseService<PayQrCode,PayQrCodeExample> {
	@Autowired
	private PayQrCodeMapper payQrCodeMapper;
	
	@Autowired
	public void setPayQrCodeMapper(PayQrCodeMapper payQrCodeMapper) {
		super.setDao(payQrCodeMapper);
		this.payQrCodeMapper = payQrCodeMapper;
	}
}
