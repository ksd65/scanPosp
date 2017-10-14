package com.epay.scanposp.dao;

import java.util.List;
import java.util.Map;

import com.epay.scanposp.common.base.MyBatisRepository;
import com.epay.scanposp.entity.DebitNote;
@MyBatisRepository
public interface DebitNoteExtendMapper {
    
	public List<DebitNote> getDebitMerchantInfoDistinct(Map<String,Object> paramMap) ;

}