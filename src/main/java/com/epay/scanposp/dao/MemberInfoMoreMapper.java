package com.epay.scanposp.dao;

import com.epay.scanposp.common.base.MyBatisRepository;
import com.epay.scanposp.entity.MemberInfoMore;

@MyBatisRepository
public interface MemberInfoMoreMapper {
    
    public MemberInfoMore selectMemberInfoMoreByMember(int memberID);
}