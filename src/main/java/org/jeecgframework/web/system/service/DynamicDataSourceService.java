package org.jeecgframework.web.system.service;

import java.util.List;

import org.jeecgframework.web.system.data.source.entity.DynamicDataSourceEntity;

public interface DynamicDataSourceService {

	public List<DynamicDataSourceEntity> initDynamicDataSource();

	public void refleshCache();


	public DynamicDataSourceEntity getDynamicDataSourceEntityForDbKey(String dbKey);


}
