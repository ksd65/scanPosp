package com.epay.scanposp.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.epay.scanposp.common.base.BaseService;
import com.epay.scanposp.dao.DebitNoteMapper;
import com.epay.scanposp.entity.DebitNote;
import com.epay.scanposp.entity.DebitNoteExample;

@Service
@Transactional
public class DebitNoteService extends BaseService<DebitNote,DebitNoteExample> {
	@Autowired
	private DebitNoteMapper debitNoteMapper;
	
	@Autowired
	public void setDebitNoteMapper(DebitNoteMapper debitNoteMapper) {
		super.setDao(debitNoteMapper);
		this.debitNoteMapper = debitNoteMapper;
	}
	
	public List<DebitNote> selectNoteOutTimes(Map<String, Object> paramMap){
		return debitNoteMapper.selectNoteOutTimes(paramMap);
	}
	
	public Double countTransactionMoneyByCondition(Map<String,Object> paramMap){
		return debitNoteMapper.countTransactionMoneyByCondition(paramMap);
	}
	
	public Double countTransactionRateByCondition(Map<String,Object> paramMap){
		return debitNoteMapper.countTransactionRateByCondition(paramMap);
	}
	
	public int selectFailCountsWithinTime(Map<String,Object> paramMap){
		return debitNoteMapper.selectFailCountsWithinTime(paramMap);
	}
}
