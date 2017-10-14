package com.epay.scanposp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.epay.scanposp.common.base.BaseService;
import com.epay.scanposp.dao.MlTradeDetailAllMapper;
import com.epay.scanposp.dao.MlTradeDetailMapper;
import com.epay.scanposp.entity.MlTradeDetail;
import com.epay.scanposp.entity.MlTradeDetailAll;
import com.epay.scanposp.entity.MlTradeDetailAllExample;
import com.epay.scanposp.entity.MlTradeDetailExample;;

@Service
@Transactional
public class MlTradeDetailAllService extends BaseService<MlTradeDetailAll,MlTradeDetailAllExample> {
	@Autowired
	private MlTradeDetailAllMapper mlTradeDetailAllMapper; 
	@Autowired
	public void setMlTradeDetailAllMapper(MlTradeDetailAllMapper mlTradeDetailAllMapper) {
		super.setDao(mlTradeDetailAllMapper);
		this.mlTradeDetailAllMapper = mlTradeDetailAllMapper;
	} 
}
 
