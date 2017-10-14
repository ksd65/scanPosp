package com.epay.scanposp.dao;

import com.epay.scanposp.common.base.BaseDao;
import com.epay.scanposp.common.base.MyBatisRepository;
import com.epay.scanposp.entity.BankName;
import com.epay.scanposp.entity.BankNameExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
@MyBatisRepository
public interface BankNameMapper extends BaseDao<BankName, BankNameExample>{
    int countByExample(BankNameExample example);

    int deleteByExample(BankNameExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(BankName record);

    int insertSelective(BankName record);

    List<BankName> selectByExample(BankNameExample example);

    BankName selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") BankName record, @Param("example") BankNameExample example);

    int updateByExample(@Param("record") BankName record, @Param("example") BankNameExample example);

    int updateByPrimaryKeySelective(BankName record);

    int updateByPrimaryKey(BankName record);
}