package com.epay.scanposp.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.epay.scanposp.common.base.BaseService;
import com.epay.scanposp.dao.SubMerchantBlackListMapper;
import com.epay.scanposp.entity.SubMerchantBlackList;
import com.epay.scanposp.entity.SubMerchantBlackListExample;

@Service
@Transactional
public class SubMerchantBlackListService extends BaseService<SubMerchantBlackList,SubMerchantBlackListExample> {
	@Autowired
	private SubMerchantBlackListMapper subMerchantBlackListMapper;
	@Autowired
	public void setSubMerchantBlackListMapper(SubMerchantBlackListMapper subMerchantBlackListMapper) {
		super.setDao(subMerchantBlackListMapper);
		this.subMerchantBlackListMapper = subMerchantBlackListMapper;
	}
	

	public List<Map<String,Object>> getSubMerchantFailIpCount(Map<String,Object> param){
		return subMerchantBlackListMapper.getSubMerchantFailIpCount(param);
	}
	
	public List<SubMerchantBlackList> getSubMerchantBlackToday(Map<String,Object> param){
		return subMerchantBlackListMapper.getSubMerchantBlackToday(param);
	}
	
	public int getSubMerchantBlackCount(Map<String,Object> param){
		return subMerchantBlackListMapper.getSubMerchantBlackCount(param);
	}
}
