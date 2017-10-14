package com.epay.scanposp.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.epay.scanposp.common.base.BaseService; 
import com.epay.scanposp.dao.StTradeDetailMapper; 
import com.epay.scanposp.entity.StTradeDetail;
import com.epay.scanposp.entity.StTradeDetailAll;
import com.epay.scanposp.entity.StTradeDetailExample;   

@Service
@Transactional
public class StTradeDetailService extends BaseService<StTradeDetail,StTradeDetailExample>  {
	@Autowired
	private StTradeDetailMapper stTradeDetailMapper;
	
	 

	@Autowired
	public void setStTradeDetailMapper(StTradeDetailMapper stTradeDetailMapper) {
		super.setDao(stTradeDetailMapper);
		this.stTradeDetailMapper = stTradeDetailMapper;
	}

  
}
