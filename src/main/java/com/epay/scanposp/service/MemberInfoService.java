package com.epay.scanposp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.epay.scanposp.common.base.BaseService;
import com.epay.scanposp.dao.MemberInfoMapper;
import com.epay.scanposp.entity.MemberInfo;
import com.epay.scanposp.entity.MemberInfoExample;

@Service
@Transactional
public class MemberInfoService extends BaseService<MemberInfo,MemberInfoExample> {
	@Autowired
	private MemberInfoMapper memberInfoMapper;
	@Autowired
	public void setMemberInfoMapper(MemberInfoMapper memberInfoMapper) {
		super.setDao(memberInfoMapper);
		this.memberInfoMapper = memberInfoMapper;
	}
	
}
