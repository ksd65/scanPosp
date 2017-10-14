package com.epay.scanposp.dao;

import java.util.Map;

import com.epay.scanposp.common.base.MyBatisRepository;
import com.epay.scanposp.entity.SysOffice;
@MyBatisRepository
public interface SysOfficeExtendMapper {
	SysOffice getSysOfficeByOrderCode(Map<String,Object> paramMap);
	SysOffice getSysOfficeByMemberCode(Map<String,Object> paramMap);
}