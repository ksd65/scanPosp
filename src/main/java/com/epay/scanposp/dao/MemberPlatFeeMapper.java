package com.epay.scanposp.dao;

import java.util.List;

import com.epay.scanposp.common.base.BaseDao;
import com.epay.scanposp.common.base.MyBatisRepository;
import com.epay.scanposp.entity.MemberPlatFee;
import com.epay.scanposp.entity.MemberPlatFeeExample;
@MyBatisRepository
public interface MemberPlatFeeMapper extends BaseDao<MemberPlatFee, MemberPlatFeeExample>{
    
    List<MemberPlatFee> selectByExample(MemberPlatFeeExample example);

}