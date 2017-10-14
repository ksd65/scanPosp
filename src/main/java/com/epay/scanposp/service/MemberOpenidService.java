package com.epay.scanposp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.epay.scanposp.common.base.BaseService;
import com.epay.scanposp.dao.MemberOpenidMapper;
import com.epay.scanposp.entity.MemberOpenid;
import com.epay.scanposp.entity.MemberOpenidExample;

@Service
@Transactional
public class MemberOpenidService extends BaseService<MemberOpenid,MemberOpenidExample> {
	@Autowired
	private MemberOpenidMapper memberOpenidMapper;
	
	@Autowired
	public void setMemberOpenidMapper(MemberOpenidMapper memberOpenidMapper) {
		super.setDao(memberOpenidMapper);
		this.memberOpenidMapper = memberOpenidMapper;
	}

}
