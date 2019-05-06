package org.jeecgframework.web.system.core.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.jeecgframework.web.system.dict.service.DictService;
import org.jeecgframework.web.system.service.DynamicDataSourceService;
import org.jeecgframework.web.system.service.MutiLangService;
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 * 系统初始化监听器
 * @author DELL
 * @date 2019-05-06
 * @version V1.0
 */
public class InitListener  implements ServletContextListener {

	@Override
	public void contextInitialized(ServletContextEvent event) {
		WebApplicationContext webApplicationContext = WebApplicationContextUtils.getWebApplicationContext(event.getServletContext());
		SystemService systemService = (SystemService) webApplicationContext.getBean("systemService");
		MutiLangService mutiLangService = (MutiLangService) webApplicationContext.getBean("mutiLangService");
		DictService dictService = (DictService) webApplicationContext.getBean("dictService");
		DynamicDataSourceService dynamicDataSourceService = (DynamicDataSourceService) webApplicationContext.getBean("dynamicDataSourceService");
		
		// 初始化数据字典,对数据字典进行缓存
		dictService.initAllTypeGroups();

		//
		systemService.initAllTSIcons();

		// 加载多语言内容
		mutiLangService.initAllMutiLang();
		
		// 加载配置的数据源信息
		dynamicDataSourceService.initDynamicDataSource();
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {

	}

}
