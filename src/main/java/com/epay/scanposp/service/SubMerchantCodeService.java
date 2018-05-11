package com.epay.scanposp.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.epay.scanposp.common.base.BaseService;
import com.epay.scanposp.dao.SubMerchantCodeMapper;
import com.epay.scanposp.entity.SubMerchantCode;
import com.epay.scanposp.entity.SubMerchantCodeExample;

@Service
@Transactional
public class SubMerchantCodeService extends BaseService<SubMerchantCode,SubMerchantCodeExample> {
	@Autowired
	private SubMerchantCodeMapper subMerchantCodeMapper;
	@Autowired
	public void setSubMerchantCodeMapper(SubMerchantCodeMapper subMerchantCodeMapper) {
		super.setDao(subMerchantCodeMapper);
		this.subMerchantCodeMapper = subMerchantCodeMapper;
	}
	
	public List<SubMerchantCode> selectByMap(Map<String,Object> param){
		return subMerchantCodeMapper.selectByMap(param);
	}
	
}
