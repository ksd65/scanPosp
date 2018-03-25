package com.epay.scanposp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.epay.scanposp.common.base.BaseService;
import com.epay.scanposp.dao.MemberPayTypeMapper;
import com.epay.scanposp.entity.MemberPayType;
import com.epay.scanposp.entity.MemberPayTypeExample;

@Service
@Transactional
public class MemberPayTypeService extends BaseService<MemberPayType,MemberPayTypeExample> {
	@Autowired
	private MemberPayTypeMapper memberPayTypeMapper;
	@Autowired
	public void setMemberPayTypeMapper(MemberPayTypeMapper memberPayTypeMapper) {
		super.setDao(memberPayTypeMapper);
		this.memberPayTypeMapper = memberPayTypeMapper;
	}
	
	public int updateDelFlagByExample(MemberPayTypeExample example){
		return memberPayTypeMapper.updateDelFlagByExample(example);
	}
}
