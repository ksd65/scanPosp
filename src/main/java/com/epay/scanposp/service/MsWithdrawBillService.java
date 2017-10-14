package com.epay.scanposp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.epay.scanposp.common.base.BaseService;
import com.epay.scanposp.dao.MsWithdrawBillMapper;
import com.epay.scanposp.entity.MsWithdrawBill;
import com.epay.scanposp.entity.MsWithdrawBillExample;

@Service
@Transactional
public class MsWithdrawBillService extends BaseService<MsWithdrawBill, MsWithdrawBillExample> {
	@Autowired
	private MsWithdrawBillMapper msWithdrawBillMapper;

	@Autowired
	public void setMsWithdrawBillMapper(MsWithdrawBillMapper msWithdrawBillMapper) {
		super.setDao(msWithdrawBillMapper);
		this.msWithdrawBillMapper = msWithdrawBillMapper;
	}

}
