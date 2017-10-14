package com.epay.scanposp.dao;

import com.epay.scanposp.common.base.BaseDao;
import com.epay.scanposp.common.base.MyBatisRepository;
import com.epay.scanposp.entity.DrawResultNotice;
import com.epay.scanposp.entity.DrawResultNoticeExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
@MyBatisRepository
public interface DrawResultNoticeMapper extends BaseDao<DrawResultNotice, DrawResultNoticeExample>{
    int countByExample(DrawResultNoticeExample example);

    int deleteByExample(DrawResultNoticeExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(DrawResultNotice record);

    int insertSelective(DrawResultNotice record);

    List<DrawResultNotice> selectByExample(DrawResultNoticeExample example);

    DrawResultNotice selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") DrawResultNotice record, @Param("example") DrawResultNoticeExample example);

    int updateByExample(@Param("record") DrawResultNotice record, @Param("example") DrawResultNoticeExample example);

    int updateByPrimaryKeySelective(DrawResultNotice record);

    int updateByPrimaryKey(DrawResultNotice record);
}