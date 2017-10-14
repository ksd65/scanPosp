package com.epay.scanposp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.epay.scanposp.dao.MemberInfoMoreMapper;
import com.epay.scanposp.entity.MemberInfoMore;

@Service
@Transactional
public class MemberInfoMoreService {
	@Autowired
	private MemberInfoMoreMapper memberInfoMoreMapper;
	
	@Autowired
	public void setMemberInfoMoreMapper(MemberInfoMoreMapper memberInfoMoreMapper) {
		this.memberInfoMoreMapper = memberInfoMoreMapper;
	}
	
	public MemberInfoMore selectMemberInfoMoreByMember(int memberID){
		return memberInfoMoreMapper.selectMemberInfoMoreByMember(memberID);
	}
}
