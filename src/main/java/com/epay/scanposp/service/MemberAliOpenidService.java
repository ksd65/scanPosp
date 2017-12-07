package com.epay.scanposp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.epay.scanposp.common.base.BaseService;
import com.epay.scanposp.dao.MemberAliOpenidMapper;
import com.epay.scanposp.entity.MemberAliOpenid;
import com.epay.scanposp.entity.MemberAliOpenidExample;

@Service
@Transactional
public class MemberAliOpenidService extends BaseService<MemberAliOpenid,MemberAliOpenidExample> {
	@Autowired
	private MemberAliOpenidMapper memberAliOpenidMapper;
	
	@Autowired
	public void setMemberOpenidMapper(MemberAliOpenidMapper memberAliOpenidMapper) {
		super.setDao(memberAliOpenidMapper);
		this.memberAliOpenidMapper = memberAliOpenidMapper;
	}

}
