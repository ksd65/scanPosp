package com.epay.scanposp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.epay.scanposp.common.base.BaseService;
import com.epay.scanposp.dao.PrePayMemberMapper;
import com.epay.scanposp.entity.PrePayMember;
import com.epay.scanposp.entity.PrePayMemberExample;

@Service
@Transactional
public class PrePayMemberService extends BaseService<PrePayMember,PrePayMemberExample> {
	@Autowired
	private PrePayMemberMapper prePayMemberMapper;
	@Autowired
	public void setPrePayMemberMapper(PrePayMemberMapper prePayMemberMapper) {
		super.setDao(prePayMemberMapper);
		this.prePayMemberMapper = prePayMemberMapper;
	}
	
}
