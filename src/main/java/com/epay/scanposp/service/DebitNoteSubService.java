package com.epay.scanposp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.epay.scanposp.common.base.BaseService;
import com.epay.scanposp.dao.DebitNoteSubMapper;
import com.epay.scanposp.entity.DebitNoteSub;
import com.epay.scanposp.entity.DebitNoteSubExample;

@Service
@Transactional
public class DebitNoteSubService extends BaseService<DebitNoteSub,DebitNoteSubExample> {
	@Autowired
	private DebitNoteSubMapper debitNoteSubMapper;
	
	@Autowired
	public void setDebitNoteSubMapper(DebitNoteSubMapper debitNoteSubMapper) {
		super.setDao(debitNoteSubMapper);
		this.debitNoteSubMapper = debitNoteSubMapper;
	}
	
}
