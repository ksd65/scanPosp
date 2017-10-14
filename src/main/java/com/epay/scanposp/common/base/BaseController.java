package com.epay.scanposp.common.base;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.lang.StringUtils;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.epay.scanposp.common.utils.Servlets;
import com.epay.scanposp.common.utils.StringEscape;

/**
 * 提供基础的crud控制处理
 * 
 * @author ghq
 * 
 * @param <T>
 */
public abstract class BaseController<T,E> implements ServletContextAware {

	protected BaseService<T,E> service;

	public void setService(BaseService<T,E> service) {
		this.service = service;
	}

	protected ServletContext servletContext;

	public void setServletContext(ServletContext context) {
		this.servletContext = context;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@InitBinder
    public void initBinder(WebDataBinder binder,HttpServletRequest request) throws Exception  {
    	binder.registerCustomEditor(String.class, new StringEscape(true , true));
    	Map<String, String> pathVariables = (Map) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
    	Iterator<Map.Entry<String, String>> iterator= pathVariables.entrySet().iterator();				
    	while(iterator.hasNext()){			
    		Map.Entry<String, String> entry = iterator.next();			
    		String key = entry.getKey();
    		String value = entry.getValue();
    		pathVariables.put(key, StringEscape.escapeString(true, true, value));
    	}
    }

	/**
	 * 通过反射取得requestMapping value
	 * 
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	protected String getControllerContext() {
		String context = "";
		Class<? extends BaseController> c = this.getClass();
		if (c != null) {
			RequestMapping mapping = c.getAnnotation(RequestMapping.class);
			String[] mappingValues = mapping.value();
			context = mappingValues[0];
			if (context.startsWith("/")) {
				context = context.substring(1);
			}
		}
		return context;
	}

	/**
	 * 列表页文件名 默认为ControllerContext + List
	 * 
	 * @return
	 */
	protected String getListPageName() {
		return getViewName() + "List";
	}

	/**
	 * 新增表单页文件名 默认为ControllerContext + Form
	 * 
	 * @return
	 */
	protected String getCreateFormPageName() {
		return getViewName() + "Form";
	}

	/**
	 * 更新表单页文件名 默认为ControllerContext + Form
	 * 
	 * @return
	 */
	protected String getUpdateFormPageName() {
		return getViewName() + "Form";
	}

	/**
	 * 增加url生成jsp文件名前缀,多级路径合成
	 * 
	 * @return
	 */
	protected String getViewName() {
		String uri = getControllerContext();
		String[] uris = uri.split("/");
		if (uris.length < 2) {
			return uri;
		} else {
			StringBuilder stb = new StringBuilder("");
			for (int i = 0; i < uris.length; i++) {
				String str = uris[i];
				if (StringUtils.isNotEmpty(str) && stb.length() != 0) {
					str = StringUtils.capitalize(str);
				}
				stb.append(str);
			}
			return stb.toString();
		}
	}

	/**
	 * 不分页展示所有记录 URL:/view
	 * 
	 * @param model
	 * @param request
	 * @return
	 */
	// @RequestMapping(value = "view")
	public String view(Model model, ServletRequest request) {
		Map<String, Object> searchParams = Servlets.getParametersStartingWith(
				request, "search_");
		List<T> entitys = service.getEntityByParams(searchParams);
		model.addAttribute("entitys", entitys);
		return getControllerContext() + "/" + getListPageName();
	}

	/**
	 * 跳转新增页面 URL:/create(method=get)
	 * 
	 * @param model
	 * @return 新增页面
	 */
	// @RequestMapping(value = "create", method = RequestMethod.GET)
	public String createForm(Model model) {
		model.addAttribute("action", "create");
		return getControllerContext() + "/" + getCreateFormPageName();
	}

	/**
	 * 新增操作 URL:/create(method=post)
	 * 
	 * @param entity
	 * @param redirectAttributes
	 * @return redirect到列表页
	 */
	// @RequestMapping(value = "create", method = RequestMethod.POST)
	public String create(@Valid T entity, RedirectAttributes redirectAttributes) {
		service.saveEntity(entity);
		redirectAttributes.addFlashAttribute("message", "创建成功");
		return "redirect:/" + getControllerContext();
	}

	/**
	 * 跳转更新页面 URL:/update/{id}(method=get)
	 * 
	 * @param id
	 * @param model
	 * @return 更新页面
	 */
	// @RequestMapping(value = "update/{id}", method = RequestMethod.GET)
	public String updateForm(@PathVariable("id") Long id, Model model) {
		model.addAttribute("entity", service.getEntity(id));
		model.addAttribute("action", "update");
		return getControllerContext() + "/" + getUpdateFormPageName();
	}

	/**
	 * 更新操作 URL:/update(method=post)
	 * 
	 * @param entity
	 * @param redirectAttributes
	 * @return redirect到列表页
	 */
	// @RequestMapping(value = "update", method = RequestMethod.POST)
	public String update(@Valid @ModelAttribute("entity") T entity,
			RedirectAttributes redirectAttributes) {
		service.updateEntity(entity);
		redirectAttributes.addFlashAttribute("message", "更新成功");
		return "redirect:/" + getControllerContext();
	}

	/**
	 * 批量删除操作 URL:/delete
	 * 
	 * @param ids
	 * @param redirectAttributes
	 * @return redirect到列表页
	 */
	// @RequestMapping("delete")
	public String multiDelete(@RequestParam("ids") List<Long> ids,
			RedirectAttributes redirectAttributes) {
		service.multiDeleteEntity(ids);
		redirectAttributes.addFlashAttribute("message", "删除" + ids.size()
				+ "个资源成功");
		return "redirect:/" + getControllerContext();
	}

	/**
	 * 根据id查找实体
	 * 
	 * @param id
	 * @return json对象
	 */
	// @RequestMapping(value = "get/{id}", method = RequestMethod.GET)
	@ResponseBody
	public T get(@PathVariable("id") Long id) {
		return service.getEntity(id);
	}

	/**
	 * 所有RequestMapping方法调用前的Model准备方法, 实现Struts2 Preparable二次部分绑定的效果
	 * 先根据form的id从数据库查出对象,再把Form提交的内容绑定到该对象上。
	 * 因为仅update()方法的form中有id属性，因此仅在update时实际执行.
	 * 
	 * @param id
	 *            主键
	 * @param model
	 */
	@ModelAttribute
	public void bindingEntity(
			@RequestParam(value = "id", defaultValue = "-1") Long id,
			Model model) {
		if (id != -1) {
			model.addAttribute("entity", service.getEntity(id));
		}
	}
}
