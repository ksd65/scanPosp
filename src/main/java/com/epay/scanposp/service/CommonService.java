package com.epay.scanposp.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.epay.scanposp.dao.CommonServiceMapper;

@Service
@Transactional
public class CommonService {
	@Autowired
	private CommonServiceMapper commonServiceMapper;
	
	@Autowired
	public void setCommonServiceMapper(CommonServiceMapper commonServiceMapper) {
		this.commonServiceMapper = commonServiceMapper;
	}
	
	public String getNextSequenceVal(String sequenceType){
		return commonServiceMapper.getNextSequenceVal(sequenceType);
	}
	
	public Double countTransactionMoneyByCondition(Map<String,Object> paramMap){
		return commonServiceMapper.countTransactionMoneyByCondition(paramMap);
	}
	public Double countTransactionMoneyByConditionHis(Map<String,Object> paramMap){
		return commonServiceMapper.countTransactionMoneyByConditionHis(paramMap);
	}
	
	public Double countDrawMoneyByCondition(Map<String,Object> paramMap){
		return commonServiceMapper.countDrawMoneyByCondition(paramMap);
	}
	
	public Double countUnDrawMoneyByCondition(Map<String,Object> paramMap){
		return commonServiceMapper.countUnDrawMoneyByCondition(paramMap);
	}

	public Integer moveToHis(Map<String,Object> paramMap) {
		return commonServiceMapper.moveToHis(paramMap);
	}

	public Double countMlDrawMoneyByCondition(Map<String, Object> paramMap) {
		return commonServiceMapper.countMlDrawMoneyByCondition(paramMap);
	}
}
