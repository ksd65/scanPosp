package com.epay.scanposp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.epay.scanposp.common.base.BaseService;
import com.epay.scanposp.dao.MsPayBillMapper;
import com.epay.scanposp.entity.MsPayBill;
import com.epay.scanposp.entity.MsPayBillExample;

@Service
@Transactional
public class MsPayBillService extends BaseService<MsPayBill,MsPayBillExample> {
	@Autowired
	private MsPayBillMapper msPayBillMapper;
	
	@Autowired
	public void setMsPaybillMapper(MsPayBillMapper msPayBillMapper) {
		super.setDao(msPayBillMapper);
		this.msPayBillMapper = msPayBillMapper;
	}

}
