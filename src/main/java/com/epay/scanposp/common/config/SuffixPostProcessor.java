package com.epay.scanposp.common.config;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;
/**
 * 设置restful风格url,强制url资源唯一
 * @author ghq
 *
 */
public class SuffixPostProcessor implements BeanPostProcessor {

	@Override
	public Object postProcessAfterInitialization(Object bean, String arg1)
			throws BeansException {
		return bean;
	}

	@Override
	public Object postProcessBeforeInitialization(Object bean, String arg1)
			throws BeansException {
		if (bean instanceof RequestMappingHandlerMapping) {
			((RequestMappingHandlerMapping) bean).setUseSuffixPatternMatch(false);
			((RequestMappingHandlerMapping) bean).setUseTrailingSlashMatch(false);
		}
		return bean;
	}

}
