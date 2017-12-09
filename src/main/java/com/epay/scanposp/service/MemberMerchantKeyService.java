package com.epay.scanposp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.epay.scanposp.common.base.BaseService;
import com.epay.scanposp.dao.MemberMerchantKeyMapper;
import com.epay.scanposp.entity.MemberMerchantKey;
import com.epay.scanposp.entity.MemberMerchantKeyExample;

@Service
@Transactional
public class MemberMerchantKeyService extends BaseService<MemberMerchantKey,MemberMerchantKeyExample> {
	@Autowired
	private MemberMerchantKeyMapper memberMerchantKeyMapper;
	@Autowired
	public void setMemberMerchantKeyMapper(MemberMerchantKeyMapper memberMerchantKeyMapper) {
		super.setDao(memberMerchantKeyMapper);
		this.memberMerchantKeyMapper = memberMerchantKeyMapper;
	}
	
}
