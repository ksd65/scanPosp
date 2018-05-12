package com.epay.scanposp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.epay.scanposp.common.base.BaseService;
import com.epay.scanposp.dao.SubMerchantCodeTempMapper;
import com.epay.scanposp.entity.SubMerchantCodeTemp;
import com.epay.scanposp.entity.SubMerchantCodeTempExample;

@Service
@Transactional
public class SubMerchantCodeTempService extends BaseService<SubMerchantCodeTemp,SubMerchantCodeTempExample> {
	@Autowired
	private SubMerchantCodeTempMapper subMerchantCodeTempMapper;
	
	@Autowired
	public void setSubMerchantCodeTempMapper(SubMerchantCodeTempMapper subMerchantCodeTempMapper) {
		super.setDao(subMerchantCodeTempMapper);
		this.subMerchantCodeTempMapper = subMerchantCodeTempMapper;
	}
	
	public int delete(SubMerchantCodeTemp record){
		return subMerchantCodeTempMapper.delete(record);
	}
}
