package com.epay.scanposp.common.base;

import java.util.List;
import java.util.Map;

import com.epay.scanposp.common.page.Page;


/**
 * 提供基础的crud、分页查询等服务
 * 
 * @author ghq
 *
 * @param <T>
 */
public abstract class BaseService<T,E> {
	
	protected BaseDao<T,E> dao;
	public void setDao(BaseDao<T,E> dao) {
		this.dao = dao;
	}

	public int countByExample(E example){
		return dao.countByExample(example);
	}

    public int deleteByExample(E example){
    	return dao.deleteByExample(example);
    }

    public int deleteByPrimaryKey(Integer id){
    	return dao.deleteByPrimaryKey(id);
    }

    public int insert(T record){
    	return dao.insert(record);
    }

    public int insertSelective(T record){
    	return dao.insertSelective(record);
    }

    public List<T> selectByExample(E example){
    	return dao.selectByExample(example);
    }

    public T selectByPrimaryKey(Integer id){
    	return dao.selectByPrimaryKey(id);
    }

    public int updateByExampleSelective(T record,E example){
    	return dao.updateByExampleSelective(record, example);
    }

    public int updateByExample(T record,E example){
    	return dao.updateByExample(record, example);
    }

    public int updateByPrimaryKeySelective(T record){
    	return dao.updateByPrimaryKeySelective(record);
    }

    public int updateByPrimaryKey(T record){
    	return dao.updateByPrimaryKey(record);
    }
    
    
    /**
	 * 根据主键获取实体
	 * 
	 * @param id
	 * @return
	 */
	public T getEntity(Long id) {
		return dao.findOne(id);
	}

	/**
	 * 获取所有实体List
	 * 
	 * @return
	 */
	public List<T> getAllEntity() {
		return dao.findAll();
	}

	/**
	 * 根据查询参数获取实体List
	 * 
	 * @param searchParams
	 * @return
	 */
	public List<T> getEntityByParams(Map<String, Object> searchParams) {
		return dao.findByParams(searchParams);
	}

	/**
	 * 根据查询参数获取记录数目
	 * 
	 * @param searchParams
	 * @return
	 */
	public Long getCountByParams(Map<String, Object> searchParams) {
		return dao.findCountByParams(searchParams);
	}

	/**
	 * 新增实体
	 * 
	 * @param entity
	 */
	public void saveEntity(T entity) {
		dao.save(entity);
	}

	/**
	 * 更新实体
	 * 
	 * @param entity
	 */
	public void updateEntity(T entity) {
		dao.update(entity);
	}

	/**
	 * 根据主键删除实体
	 * 
	 * @param id
	 */
	public void deleteEntity(Long id) {
		dao.delete(id);
	}

	/**
	 * 根据主键批量删除实体
	 * 
	 * @param ids
	 */
	public void multiDeleteEntity(List<Long> ids) {
		for (Long id : ids) {
			dao.delete(id);
		}
	}

	/**
	 * 分页查询
	 * 
	 * @param searchParams
	 *            查询参数
	 * @param pageNumber
	 *            当前页
	 * @param pageSize
	 *            每页数量
	 * @param sortType
	 *            排序类型
	 * @param type
	 *            分页类型
	 * @return
	 */
	public Page<T> getEntityByPage(Map<String, Object> searchParams, int pageNumber, int pageSize, String sortType) {
		Page<T> p = buildPage(pageNumber, pageSize);
		p.setResult(dao.getPage(p, searchParams));
		return p;
	}

	/**
	 * 构建分页对象
	 * 
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	private Page<T> buildPage(int pageNumber, int pageSize) {
		Page<T> page = new Page<T>();
		page.setCurrentPage(pageNumber);
		page.setSize(pageSize);
		return page;
	}


}
