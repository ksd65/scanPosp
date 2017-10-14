package com.epay.scanposp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.epay.scanposp.common.base.BaseService;
import com.epay.scanposp.dao.MemberBankMapper;
import com.epay.scanposp.entity.MemberBank;
import com.epay.scanposp.entity.MemberBankExample;

@Service
@Transactional
public class MemberBankService extends BaseService<MemberBank,MemberBankExample> {
	@Autowired
	private MemberBankMapper memberBankMapper;
	
	@Autowired
	public void setMemberBankMapper(MemberBankMapper memberBankMapper) {
		super.setDao(memberBankMapper);
		this.memberBankMapper = memberBankMapper;
	}

}
