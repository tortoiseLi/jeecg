package org.jeecgframework.core.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * 
 * @author  张代浩
 *
 */
public class ApplicationContextUtils implements ApplicationContextAware {

	private static ApplicationContext context;

	@Override
	public void setApplicationContext(ApplicationContext context) throws BeansException {
		ApplicationContextUtils.context = context;
	}

	public static ApplicationContext getContext() {
		return context;
	}
}
