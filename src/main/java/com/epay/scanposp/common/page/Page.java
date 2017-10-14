package com.epay.scanposp.common.page;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * 分页工具类
 * @author ghq
 *
 * @param <T>
 */
public class Page<T> {
	
	/**每页显示几条*/
	private int size = 20;
	
	/**总条数*/
	private int total = 0; 
	
	/**当前页*/
	private int currentPage = 0; 
	
	/**当前页起始索引*/
	private int currentResult = 0; 
	
	/**当前页结束索引*/
	private int currentEndResult = 0;

	/**存放结果集*/
	private List<T> result = new ArrayList<T>();
	
	/**存放构造的tbody*/
	private String body;


	@JsonIgnore
	public List<T> getResult() {
		if (result == null) {
			return new ArrayList<T>();
		}
		return result;
	}

	public void setResult(List<T> result) {
		this.result = result;
	}

	/**
	 * 
	 * <p>
	 * 获取总页数
	 * </p>
	 * 
	 * @return
	 */
	public int getTotalPage() {
		if (total % size == 0) {
			return total / size;
		}
		return total / size + 1;
	}

	/**
	 * 
	 * <p>
	 * 获取总条数
	 * </p>
	 * 
	 * @return
	 */
	public int getTotal() {
		return total;
	}

	/**
	 * 
	 * <p>
	 * 设置总条数
	 * </p>
	 * 
	 * @param total
	 */
	public void setTotal(int total) {
		this.total = total;
	}

	/**
	 * 当前页数
	 * @return
	 */
	public int getCurrentPage() {
/*
 * app端以瀑布流形式叠加显示，故注释掉此段代码
		if (currentPage <= 0) {
			currentPage = 1;
		}
		if (currentPage > getTotalPage()) {
			currentPage = getTotalPage();
		}
 * 
 */
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		if (size == 0) {
			size = 10;
		}
		this.size = size;
	}

	public int getCurrentResult() {
		currentResult = (getCurrentPage() - 1) * getSize();
		if (currentResult < 0) {
			currentResult = 0;
		}
		return currentResult;
	}

	public void setCurrentResult(int currentResult) {
		this.currentResult = currentResult;
	}
	
	public boolean hasPreviousPage(){
		return currentPage > 1 ? true : false;
	}
	
	public boolean hasNextPage(){
		return currentPage < getTotalPage() ? true : false;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public int getCurrentEndResult() {
		if(getCurrentPage() != getTotalPage()){
			this.currentEndResult = getSize() * getCurrentPage();
		}else{
			this.currentEndResult = getTotal();
		}
		return currentEndResult;
	}

	public void setCurrentEndResult(int currentEndResult) {
		this.currentEndResult = currentEndResult;
	}
	
	
}