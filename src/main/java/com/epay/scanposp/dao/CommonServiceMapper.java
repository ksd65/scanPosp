package com.epay.scanposp.dao;

import java.util.Map;

import com.epay.scanposp.common.base.MyBatisRepository;

@MyBatisRepository
public interface CommonServiceMapper {
	public String getNextSequenceVal(String sequenceType);
	public Double countTransactionMoneyByCondition(Map<String,Object> paramMap);
	public Double countTransactionMoneyByConditionHis(Map<String,Object> paramMap);
	public Double countDrawMoneyByCondition(Map<String,Object> paramMap);
	public Double countUnDrawMoneyByCondition(Map<String,Object> paramMap);
	public Integer moveToHis(Map<String, Object> paramMap);
	public Double countMlDrawMoneyByCondition(Map<String, Object> paramMap);
}