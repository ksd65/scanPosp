package com.epay.scanposp.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.epay.scanposp.common.base.BaseService;
import com.epay.scanposp.dao.IpBlackListMapper;
import com.epay.scanposp.entity.IpBlackList;
import com.epay.scanposp.entity.IpBlackListExample;

@Service
@Transactional
public class IpBlackListService extends BaseService<IpBlackList,IpBlackListExample> {
	@Autowired
	private IpBlackListMapper ipBlackListMapper;
	@Autowired
	public void setIpBlackListMapper(IpBlackListMapper ipBlackListMapper) {
		super.setDao(ipBlackListMapper);
		this.ipBlackListMapper = ipBlackListMapper;
	}
	
	public int countBlack(Map<String,Object> param){
		return ipBlackListMapper.countBlack(param);
	}

	public List<Map<String,Object>> countTempBlack(Map<String,Object> param){
		return ipBlackListMapper.countTempBlack(param);
	}
}
