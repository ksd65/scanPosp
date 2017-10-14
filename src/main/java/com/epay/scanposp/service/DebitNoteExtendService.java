package com.epay.scanposp.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.epay.scanposp.dao.DebitNoteExtendMapper;
import com.epay.scanposp.entity.DebitNote;

@Service
@Transactional
public class DebitNoteExtendService {
	@Autowired
	private DebitNoteExtendMapper debitNoteExtendMapper;
	
	@Autowired
	public void setDebitNoteExtendMapper(DebitNoteExtendMapper debitNoteExtendMapper) {
		this.debitNoteExtendMapper = debitNoteExtendMapper;
	}
	
	public List<DebitNote> getDebitMerchantInfoDistinct(Map<String,Object> paramMap) {
		return debitNoteExtendMapper.getDebitMerchantInfoDistinct(paramMap);
	}

}
