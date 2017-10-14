package com.epay.scanposp.common.base;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.epay.scanposp.common.page.Page;
import com.epay.scanposp.common.utils.Constant;
import com.epay.scanposp.common.utils.Servlets;


public abstract class AjaxPaginationController<T,E> extends BaseController<T, E> {
	
	private static Logger logger = LoggerFactory.getLogger(AjaxPaginationController.class);
	

	/**
	 * 跳转list页面
	 * URL:/
	 * @param model
	 * @return 列表页
	 */
	@RequestMapping
	public String list(Model model, HttpServletRequest request) {
		//Map<String, Object> searchParams = Servlets.getParametersStartingWith(request, "search_");
		model.addAttribute("moduleUrl", getControllerContext());
		model.addAttribute("ajaxUrl", getControllerContext() + "/ajaxBody");
		//model.addAttribute("searchParams", Servlets.encodeParameterStringWithPrefix(searchParams, "search_"));
		logger.debug("URL:{};Controller:{}",getControllerContext() + "/" + getListPageName());
		return getControllerContext() + "/" + getListPageName();
	}
	
	/**
	 * ajax构造table body
	 * @param pageNumber 当前页码
	 * @param pageSize 每页记录数
	 * @param sortType 排序
	 * @param request
	 * @return Page对象
	 */
	@RequestMapping(value = "ajaxBody")
	@ResponseBody
	public Page<T> ajaxBody(@RequestParam(value = "page", defaultValue = "1") int pageNumber,
			@RequestParam(value = "page.size", defaultValue = Constant.PAGINATION_SIZE) int pageSize,
			@RequestParam(value = "sortType", defaultValue = "auto") String sortType, HttpServletRequest request) {
		Map<String, Object> searchParams = Servlets.getParametersStartingWith(request, "search_");
		setExtraSearchParam(searchParams);
		Page<T> entitys = service.getEntityByPage(searchParams, pageNumber, pageSize, sortType);
		entitys.setBody(buildTableBodyForAjax(entitys));
		return entitys;
	}
	
	/**
	 * 生成HTML Tbody
	 * @param page 分页对象
	 * @return
	 */
	protected abstract String buildTableBodyForAjax(Page<T> page);
	
	/**
	 * 手动设置查询参数
	 * @param searchParams
	 */
	protected void setExtraSearchParam(Map<String, Object> searchParams){
		
	}
	
}
