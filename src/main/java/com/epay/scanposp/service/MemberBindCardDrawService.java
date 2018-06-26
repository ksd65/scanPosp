package com.epay.scanposp.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.epay.scanposp.common.base.BaseService;
import com.epay.scanposp.dao.MemberBindCardDrawMapper;
import com.epay.scanposp.entity.MemberBindCardDraw;
import com.epay.scanposp.entity.MemberBindCardDrawExample;

@Service
@Transactional
public class MemberBindCardDrawService extends BaseService<MemberBindCardDraw,MemberBindCardDrawExample> {
	@Autowired
	private MemberBindCardDrawMapper memberBindCardDrawMapper;
	
	@Autowired
	public void setMemberBindCardDrawMapper(MemberBindCardDrawMapper memberBindCardDrawMapper) {
		super.setDao(memberBindCardDrawMapper);
		this.memberBindCardDrawMapper = memberBindCardDrawMapper;
	}

    public List<Map<String,Object>> getMemberNotBindCard(Map<String,Object> param){
    	return memberBindCardDrawMapper.getMemberNotBindCard(param);
    }

}
