package com.epay.scanposp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.epay.scanposp.common.base.BaseService;
import com.epay.scanposp.dao.MemberBindAccDtlMapper; 
import com.epay.scanposp.entity.MemberBindAccDtl;
import com.epay.scanposp.entity.MemberBindAccDtlExample; 

@Service
@Transactional
public class MemberBindAccDtlService extends BaseService<MemberBindAccDtl,MemberBindAccDtlExample> {
	@Autowired
	private MemberBindAccDtlMapper memberBindAccDtlMapper; 
	@Autowired
	public void setMemberBindAccDtlMapper(MemberBindAccDtlMapper memberBindAccDtlMapper) {
		super.setDao(memberBindAccDtlMapper);
		this.memberBindAccDtlMapper = memberBindAccDtlMapper;
	} 
}
