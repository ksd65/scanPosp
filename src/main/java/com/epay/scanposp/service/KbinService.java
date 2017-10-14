package com.epay.scanposp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.epay.scanposp.common.base.BaseService;
import com.epay.scanposp.dao.KbinMapper;
import com.epay.scanposp.entity.Kbin;
import com.epay.scanposp.entity.KbinExample;

@Service
@Transactional
public class KbinService extends BaseService<Kbin,KbinExample> {
	@Autowired
	private KbinMapper kbinMapper;
	
	@Autowired
	public void setKbinMapper(KbinMapper kbinMapper) {
		super.setDao(kbinMapper);
		this.kbinMapper = kbinMapper;
	}

}
