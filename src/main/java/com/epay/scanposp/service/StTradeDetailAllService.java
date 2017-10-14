package com.epay.scanposp.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.epay.scanposp.common.base.BaseService;
import com.epay.scanposp.dao.StTradeDetailAllMapper;
 
import com.epay.scanposp.entity.StTradeDetailAll;
import com.epay.scanposp.entity.StTradeDetailAllExample;
 

@Service
@Transactional
public class StTradeDetailAllService extends BaseService<StTradeDetailAll,StTradeDetailAllExample>  {
	@Autowired
	private StTradeDetailAllMapper stTradeDetailAllMapper;
	
	 

	@Autowired
	public void setStTradeDetailAllMapper(StTradeDetailAllMapper stTradeDetailAllMapper) {
		super.setDao(stTradeDetailAllMapper);
		this.stTradeDetailAllMapper = stTradeDetailAllMapper;
	}
}
