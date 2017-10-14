package com.epay.scanposp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.epay.scanposp.common.base.BaseService;
import com.epay.scanposp.dao.MlTradeResNoticeMapper;
import com.epay.scanposp.entity.MlTradeResNotice;
import com.epay.scanposp.entity.MlTradeResNoticeExample;;

@Service
@Transactional
public class MlTradeResNoticeService extends BaseService<MlTradeResNotice,MlTradeResNoticeExample> {
	@Autowired
	private MlTradeResNoticeMapper mlTradeResNoticeMapper; 
	@Autowired
	public void setMlTradeDetailAllMapper(MlTradeResNoticeMapper MlTradeResNotice) {
		super.setDao(mlTradeResNoticeMapper);
		this.mlTradeResNoticeMapper = mlTradeResNoticeMapper;
	} 
}
 
