package com.epay.scanposp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.epay.scanposp.common.base.BaseService;
import com.epay.scanposp.dao.MemberMerchantCodeMapper;
import com.epay.scanposp.entity.MemberMerchantCode;
import com.epay.scanposp.entity.MemberMerchantCodeExample;

@Service
@Transactional
public class MemberMerchantCodeService extends BaseService<MemberMerchantCode,MemberMerchantCodeExample> {
	@Autowired
	private MemberMerchantCodeMapper memberMerchantCodeMapper;
	@Autowired
	public void setMemberMerchantCodeMapper(MemberMerchantCodeMapper memberMerchantCodeMapper) {
		super.setDao(memberMerchantCodeMapper);
		this.memberMerchantCodeMapper = memberMerchantCodeMapper;
	}
	
	public int updateDelFlagByExample(MemberMerchantCodeExample example){
		return memberMerchantCodeMapper.updateDelFlagByExample(example);
	}
}
