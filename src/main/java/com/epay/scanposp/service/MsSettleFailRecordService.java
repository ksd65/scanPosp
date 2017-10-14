package com.epay.scanposp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.epay.scanposp.common.base.BaseService;
import com.epay.scanposp.dao.MsSettleFailRecordMapper;
import com.epay.scanposp.entity.MsSettleFailRecord;
import com.epay.scanposp.entity.MsSettleFailRecordExample;

@Service
@Transactional
public class MsSettleFailRecordService extends BaseService<MsSettleFailRecord, MsSettleFailRecordExample> {
	@Autowired
	private MsSettleFailRecordMapper msSettleFailRecordMapper;

	@Autowired
	public void setMsWithdrawBillMapper(MsSettleFailRecordMapper msSettleFailRecordMapper) {
		super.setDao(msSettleFailRecordMapper);
		this.msSettleFailRecordMapper = msSettleFailRecordMapper;
	}

}
