package com.epay.scanposp.common.base;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.epay.scanposp.common.page.Page;
import com.epay.scanposp.common.page.PageInterceptor;



/**
 * 把常用的方法抽象到此接口中，避免在多个接口中重复定义
 * @author ghq
 *
 * @param <T>
 */
public interface BaseDao<T,E> {
	String PO_KEY = "po";
	

    int countByExample(E example);

    int deleteByExample(E example);

    int deleteByPrimaryKey(Integer id);

    int insert(T record);

    int insertSelective(T record);

    List<T> selectByExample(E example);

    T selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") T record, @Param("example") E example);

    int updateByExample(@Param("record") T record, @Param("example") E example);

    int updateByPrimaryKeySelective(T record);

    int updateByPrimaryKey(T record);
    
    
    /**
	 * 查找所有
	 * @return
	 */
	List<T> findAll(); 
	
	/**
	 * 按条件查询
	 * @param map 参数map
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	List<T> findByParams(Map map); 
	
	/**
	 * 按条件查询记录数
	 * @param map 参数map
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	Long findCountByParams(Map map); 

	/**
	 * 按主键查询
	 * @param id 主键
	 * @return
	 */
	T findOne(Long id);
	
	/**
	 * 保存
	 * @param t 实体对象
	 */
	void save(T t);
	
	/**
	 * 更新
	 * @param t 实体对象
	 */
	void update(T t);

	/**
	 * 删除
	 * @param id 主键
	 */
	void delete(Long id);

	/**
	 * 分页查询
	 * @param p page对象
	 * @param map 参数map
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	List<T> getPage(@Param(PageInterceptor.PAGE_KEY) Page<T> p, @Param(PO_KEY) Map map);
}
