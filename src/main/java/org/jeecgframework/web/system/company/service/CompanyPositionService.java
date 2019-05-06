package org.jeecgframework.web.system.company.service;
import java.io.Serializable;

import org.jeecgframework.core.common.service.CommonService;
import org.jeecgframework.web.system.company.entity.CompanyPositionEntity;

public interface CompanyPositionService extends CommonService{
	
 	void delete(CompanyPositionEntity entity) throws Exception;
 	
 	Serializable save(CompanyPositionEntity entity) throws Exception;
 	
 	void saveOrUpdate(CompanyPositionEntity entity) throws Exception;
 	
}
