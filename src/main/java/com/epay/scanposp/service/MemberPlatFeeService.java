package com.epay.scanposp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.epay.scanposp.common.base.BaseService;
import com.epay.scanposp.dao.MemberPlatFeeMapper;
import com.epay.scanposp.entity.MemberPlatFee;
import com.epay.scanposp.entity.MemberPlatFeeExample;

@Service
@Transactional
public class MemberPlatFeeService extends BaseService<MemberPlatFee,MemberPlatFeeExample> {
	@Autowired
	private MemberPlatFeeMapper memberPlatFeeMapper;
	@Autowired
	public void setMemberPlatFeeMapper(MemberPlatFeeMapper memberPlatFeeMapper) {
		super.setDao(memberPlatFeeMapper);
		this.memberPlatFeeMapper = memberPlatFeeMapper;
	}
	
	
}
