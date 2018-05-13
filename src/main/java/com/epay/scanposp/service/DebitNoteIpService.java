package com.epay.scanposp.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.epay.scanposp.common.base.BaseService;
import com.epay.scanposp.dao.DebitNoteIpMapper;
import com.epay.scanposp.entity.DebitNoteIp;
import com.epay.scanposp.entity.DebitNoteIpExample;

@Service
@Transactional
public class DebitNoteIpService extends BaseService<DebitNoteIp,DebitNoteIpExample> {
	@Autowired
	private DebitNoteIpMapper debitNoteIpMapper;
	
	@Autowired
	public void setDebitNoteIpMapper(DebitNoteIpMapper debitNoteIpMapper) {
		super.setDao(debitNoteIpMapper);
		this.debitNoteIpMapper = debitNoteIpMapper;
	}
	
	public Integer countDebitNoteIpByCondition(Map<String,Object> paramMap) {
		return debitNoteIpMapper.countDebitNoteIpByCondition(paramMap);
	}
	
	public List<DebitNoteIp> selectByIp(Map<String,Object> paramMap) {
		return debitNoteIpMapper.selectByIp(paramMap);
	}
}
