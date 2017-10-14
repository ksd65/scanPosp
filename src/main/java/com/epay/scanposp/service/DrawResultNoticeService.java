package com.epay.scanposp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.epay.scanposp.common.base.BaseService;
import com.epay.scanposp.dao.DrawResultNoticeMapper;
import com.epay.scanposp.dao.PayResultNoticeMapper;
import com.epay.scanposp.entity.DrawResultNotice;
import com.epay.scanposp.entity.DrawResultNoticeExample;

@Service
@Transactional
public class DrawResultNoticeService extends BaseService<DrawResultNotice,DrawResultNoticeExample> {
	@Autowired
	private DrawResultNoticeMapper drawResultNoticeMapper;
	
	@Autowired
	public void setDrawResultNoticeMapper(DrawResultNoticeMapper drawResultNoticeMapper) {
		super.setDao(drawResultNoticeMapper);
		this.drawResultNoticeMapper = drawResultNoticeMapper;
	}

}
