package com.epay.scanposp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.epay.scanposp.common.base.BaseService;
import com.epay.scanposp.dao.PayResultNoticeMapper;
import com.epay.scanposp.entity.PayResultNotice;
import com.epay.scanposp.entity.PayResultNoticeExample;

@Service
@Transactional
public class PayResultNoticeService extends BaseService<PayResultNotice,PayResultNoticeExample> {
	@Autowired
	private PayResultNoticeMapper payResultNoticeMapper;
	
	@Autowired
	public void setPayResultNoticeMapper(PayResultNoticeMapper payResultNoticeMapper) {
		super.setDao(payResultNoticeMapper);
		this.payResultNoticeMapper = payResultNoticeMapper;
	}

}
