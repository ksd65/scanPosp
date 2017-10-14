package com.epay.scanposp.dao;

import com.epay.scanposp.common.base.BaseDao;
import com.epay.scanposp.common.base.MyBatisRepository;
import com.epay.scanposp.entity.RegisterResultNotice;
import com.epay.scanposp.entity.RegisterResultNoticeExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
@MyBatisRepository
public interface RegisterResultNoticeMapper extends BaseDao<RegisterResultNotice, RegisterResultNoticeExample>{
    int countByExample(RegisterResultNoticeExample example);

    int deleteByExample(RegisterResultNoticeExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(RegisterResultNotice record);

    int insertSelective(RegisterResultNotice record);

    List<RegisterResultNotice> selectByExample(RegisterResultNoticeExample example);

    RegisterResultNotice selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") RegisterResultNotice record, @Param("example") RegisterResultNoticeExample example);

    int updateByExample(@Param("record") RegisterResultNotice record, @Param("example") RegisterResultNoticeExample example);

    int updateByPrimaryKeySelective(RegisterResultNotice record);

    int updateByPrimaryKey(RegisterResultNotice record);
}