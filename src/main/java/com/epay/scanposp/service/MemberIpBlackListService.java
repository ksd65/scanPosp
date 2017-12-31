package com.epay.scanposp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.epay.scanposp.common.base.BaseService;
import com.epay.scanposp.dao.MemberIpBlackListMapper;
import com.epay.scanposp.entity.MemberIpBlackList;
import com.epay.scanposp.entity.MemberIpBlackListExample;

@Service
@Transactional
public class MemberIpBlackListService extends BaseService<MemberIpBlackList,MemberIpBlackListExample> {
	@Autowired
	private MemberIpBlackListMapper memberIpBlackListMapper;
	@Autowired
	public void setMemberIpBlackListMapper(MemberIpBlackListMapper memberIpBlackListMapper) {
		super.setDao(memberIpBlackListMapper);
		this.memberIpBlackListMapper = memberIpBlackListMapper;
	}
	
}
