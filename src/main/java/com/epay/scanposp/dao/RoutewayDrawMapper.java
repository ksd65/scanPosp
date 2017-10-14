package com.epay.scanposp.dao;

import com.epay.scanposp.common.base.BaseDao;
import com.epay.scanposp.common.base.MyBatisRepository;
import com.epay.scanposp.entity.RoutewayDraw;
import com.epay.scanposp.entity.RoutewayDrawExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
@MyBatisRepository
public interface RoutewayDrawMapper extends BaseDao<RoutewayDraw, RoutewayDrawExample>{
    int countByExample(RoutewayDrawExample example);

    int deleteByExample(RoutewayDrawExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(RoutewayDraw record);

    int insertSelective(RoutewayDraw record);

    List<RoutewayDraw> selectByExample(RoutewayDrawExample example);

    RoutewayDraw selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") RoutewayDraw record, @Param("example") RoutewayDrawExample example);

    int updateByExample(@Param("record") RoutewayDraw record, @Param("example") RoutewayDrawExample example);

    int updateByPrimaryKeySelective(RoutewayDraw record);

    int updateByPrimaryKey(RoutewayDraw record);
}