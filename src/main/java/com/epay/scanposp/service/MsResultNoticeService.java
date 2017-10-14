package com.epay.scanposp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.epay.scanposp.common.base.BaseService;
import com.epay.scanposp.dao.MsResultNoticeMapper;
import com.epay.scanposp.dao.PayResultNoticeMapper;
import com.epay.scanposp.entity.MsResultNotice;
import com.epay.scanposp.entity.MsResultNoticeExample;
import com.epay.scanposp.entity.PayResultNotice;
import com.epay.scanposp.entity.PayResultNoticeExample;

@Service
@Transactional
public class MsResultNoticeService extends BaseService<MsResultNotice,MsResultNoticeExample> {
	@Autowired
	private MsResultNoticeMapper msResultNoticeMapper;
	
	@Autowired
	public void setMsResultNoticeMapper(MsResultNoticeMapper msResultNoticeMapper) {
		super.setDao(msResultNoticeMapper);
		this.msResultNoticeMapper = msResultNoticeMapper;
	}

}
