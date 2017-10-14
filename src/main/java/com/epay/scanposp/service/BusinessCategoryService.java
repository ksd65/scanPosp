package com.epay.scanposp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.epay.scanposp.common.base.BaseService;
import com.epay.scanposp.dao.BusinessCategoryMapper;
import com.epay.scanposp.entity.BusinessCategory;
import com.epay.scanposp.entity.BusinessCategoryExample;

@Service
@Transactional
public class BusinessCategoryService extends BaseService<BusinessCategory,BusinessCategoryExample> {
	@Autowired
	private BusinessCategoryMapper businessCategoryMapper;
	
	@Autowired
	public void setBusinessCategoryMapper(BusinessCategoryMapper businessCategoryMapper) {
		super.setDao(businessCategoryMapper);
		this.businessCategoryMapper = businessCategoryMapper;
	}

}
