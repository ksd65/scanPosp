package com.epay.scanposp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.epay.scanposp.common.base.BaseService;
 
import com.epay.scanposp.dao.MemberBindAccMapper; 
import com.epay.scanposp.entity.MemberBindAcc;
import com.epay.scanposp.entity.MemberBindAccExample;

@Service
@Transactional
public class MemberBindAccService extends BaseService<MemberBindAcc,MemberBindAccExample> {
	@Autowired
	private MemberBindAccMapper memberBindAccMapper; 
	@Autowired
	public void setMemberBindAccMapper(MemberBindAccMapper memberBindAccMapper) {
		super.setDao(memberBindAccMapper);
		this.memberBindAccMapper = memberBindAccMapper;
	} 
}
