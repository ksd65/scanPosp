package com.epay.scanposp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.epay.scanposp.common.base.BaseService;
import com.epay.scanposp.dao.MlTradeDetailMapper;
import com.epay.scanposp.entity.MlTradeDetail;
import com.epay.scanposp.entity.MlTradeDetailExample;;

@Service
@Transactional
public class MlTradeDetailService extends BaseService<MlTradeDetail,MlTradeDetailExample> {
	@Autowired
	private MlTradeDetailMapper mlTradeDetailMapper; 
	@Autowired
	public void setMlTradeDetailMapper(MlTradeDetailMapper mlTradeDetailMapper) {
		super.setDao(mlTradeDetailMapper);
		this.mlTradeDetailMapper = mlTradeDetailMapper;
	} 
}
 
