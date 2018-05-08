package com.epay.scanposp.dao;

import java.util.List;

import com.epay.scanposp.common.base.BaseDao;
import com.epay.scanposp.common.base.MyBatisRepository;
import com.epay.scanposp.entity.IpBlackList;
import com.epay.scanposp.entity.IpBlackListExample;
@MyBatisRepository
public interface IpBlackListMapper extends BaseDao<IpBlackList, IpBlackListExample>{
    
    List<IpBlackList> selectByExample(IpBlackListExample example);

     
}