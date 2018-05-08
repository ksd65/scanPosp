package com.epay.scanposp.service;

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
	

}
