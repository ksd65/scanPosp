package com.epay.scanposp.common.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * ajax session过期拦截器
 * @author ghq
 *
 */
public class AjaxSessionFilter implements Filter {

	@Override
	public void destroy() {
		
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) resp;
		if (request.getHeader("x-requested-with") != null
				&& "XMLHttpRequest".equalsIgnoreCase(request.getHeader("X-Requested-With"))) {
			HttpSession session = request.getSession(false);
			if(session == null){
				response.setHeader("sessionstatus", "timeout");//在响应头设置session状态  
				response.setHeader("loginPath", request.getContextPath() + "/");//在响应头设置登录路径
                return;  
			}
		}
		chain.doFilter(request, response);
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		
	}

}
