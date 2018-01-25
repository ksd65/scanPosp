package com.epay.scanposp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.epay.scanposp.common.base.BaseService;
import com.epay.scanposp.dao.MemberIpWhiteListMapper;
import com.epay.scanposp.entity.MemberIpWhiteList;
import com.epay.scanposp.entity.MemberIpWhiteListExample;

@Service
@Transactional
public class MemberIpWhiteListService extends BaseService<MemberIpWhiteList,MemberIpWhiteListExample> {
	@Autowired
	private MemberIpWhiteListMapper memberIpWhiteListMapper;
	@Autowired
	public void setMemberIpWhiteListMapper(MemberIpWhiteListMapper memberIpWhiteListMapper) {
		super.setDao(memberIpWhiteListMapper);
		this.memberIpWhiteListMapper = memberIpWhiteListMapper;
	}
	
}
