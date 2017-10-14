package com.epay.scanposp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.epay.scanposp.common.base.BaseService;
import com.epay.scanposp.dao.MsWithdrawT1BillMapper;
import com.epay.scanposp.entity.MsWithdrawT1Bill;
import com.epay.scanposp.entity.MsWithdrawT1BillExample;

@Service
@Transactional
public class MsWithdrawT1BillService extends BaseService<MsWithdrawT1Bill,MsWithdrawT1BillExample> {
	@Autowired
	private MsWithdrawT1BillMapper msWithdrawT1BillMapper;
	
	@Autowired
	public void setBankMapper(MsWithdrawT1BillMapper msWithdrawT1BillMapper) {
		super.setDao(msWithdrawT1BillMapper);
		this.msWithdrawT1BillMapper = msWithdrawT1BillMapper;
	}
}
