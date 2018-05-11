package com.epay.scanposp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.epay.scanposp.common.base.BaseService;
import com.epay.scanposp.dao.SubMerchantTotalMapper;
import com.epay.scanposp.entity.SubMerchantTotal;
import com.epay.scanposp.entity.SubMerchantTotalExample;

@Service
@Transactional
public class SubMerchantTotalService extends BaseService<SubMerchantTotal,SubMerchantTotalExample> {
	@Autowired
	private SubMerchantTotalMapper subMerchantTotalMapper;
	
	@Autowired
	public void setSubMerchantTotalMapper(SubMerchantTotalMapper subMerchantTotalMapper) {
		super.setDao(subMerchantTotalMapper);
		this.subMerchantTotalMapper = subMerchantTotalMapper;
	}
	
	
}
