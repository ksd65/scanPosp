package com.epay.scanposp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.epay.scanposp.dao.PayResultNoticeLogMapper;
import com.epay.scanposp.entity.PayResultNoticeLog;

@Service
@Transactional
public class PayResultNoticeLogService {
	@Autowired
	private PayResultNoticeLogMapper payResultNoticeLogMapper;
	
	
	public int  insertNoticeLog(PayResultNoticeLog notice) {
		return payResultNoticeLogMapper.insertNoticeLog(notice);
	}
}
