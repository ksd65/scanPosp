package com.epay.scanposp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.epay.scanposp.dao.EskNoticeMapper;
import com.epay.scanposp.entity.EskNotice;

@Service
@Transactional
public class EskNoticeService {
	@Autowired
	private EskNoticeMapper eskNoticeMapper;
	
	
	public int  insertNotice(EskNotice notice) {
		return eskNoticeMapper.insertNotice(notice);
	}
}
