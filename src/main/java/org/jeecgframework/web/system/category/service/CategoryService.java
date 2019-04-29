package org.jeecgframework.web.system.category.service;

import org.jeecgframework.core.common.service.CommonService;
import org.jeecgframework.web.system.category.entity.CategoryEntity;

public interface CategoryService extends CommonService{
	/**
	 * 保存分类管理
	 * @param category
	 */
	void saveCategory(CategoryEntity category);

}
