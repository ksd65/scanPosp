package com.epay.scanposp.common.utils;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;

public class VCodeUtil {
	
	public static final String DEFAULT_CAPTCHA_PARAM = "captcha";
	
	public static boolean checkVCode(HttpServletRequest  request){
		//获取当前验证码
		String currentCaptcha = (String) request.getSession().getAttribute(DEFAULT_CAPTCHA_PARAM);
		//获取用户输入的验证码
		String submitCaptcha = StringUtil.null2Empty(request.getParameter(DEFAULT_CAPTCHA_PARAM));
		if (StringUtils.isEmpty(submitCaptcha) || !StringUtils.equals(currentCaptcha,submitCaptcha.toLowerCase())) {
			return false;
		}
		return true;
	}
}
