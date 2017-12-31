package com.epay.scanposp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.epay.scanposp.common.base.BaseService;
import com.epay.scanposp.dao.MemberIpRuleMapper;
import com.epay.scanposp.entity.MemberIpRule;
import com.epay.scanposp.entity.MemberIpRuleExample;

@Service
@Transactional
public class MemberIpRuleService extends BaseService<MemberIpRule,MemberIpRuleExample> {
	@Autowired
	private MemberIpRuleMapper memberIpRuleMapper;
	@Autowired
	public void setMemberIpRuleMapper(MemberIpRuleMapper memberIpRuleMapper) {
		super.setDao(memberIpRuleMapper);
		this.memberIpRuleMapper = memberIpRuleMapper;
	}
	
}
