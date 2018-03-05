package com.epay.scanposp.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.epay.scanposp.common.base.BaseService;
import com.epay.scanposp.dao.MemberPayeeMapper;
import com.epay.scanposp.entity.MemberPayee;
import com.epay.scanposp.entity.MemberPayeeExample;

@Service
@Transactional
public class MemberPayeeService extends BaseService<MemberPayee,MemberPayeeExample> {
	@Autowired
	private MemberPayeeMapper memberPayeeMapper;
	
	@Autowired
	public void setMemberPayeeMapper(MemberPayeeMapper memberPayeeMapper) {
		super.setDao(memberPayeeMapper);
		this.memberPayeeMapper = memberPayeeMapper;
	}
	
	public List<MemberPayee> selectByMap(Map<String,Object> param){
		return memberPayeeMapper.selectByMap(param);
	}
	
}
