package org.jeecgframework.web.system.service;
import java.io.Serializable;

import org.jeecgframework.core.common.service.CommonService;
import org.jeecgframework.web.system.pojo.base.CompanyPositionEntity;

public interface CompanyPositionService extends CommonService{
	
 	public void delete(CompanyPositionEntity entity) throws Exception;
 	
 	public Serializable save(CompanyPositionEntity entity) throws Exception;
 	
 	public void saveOrUpdate(CompanyPositionEntity entity) throws Exception;
 	
}
