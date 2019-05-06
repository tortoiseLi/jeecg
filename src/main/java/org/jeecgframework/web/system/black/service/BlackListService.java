package org.jeecgframework.web.system.black.service;
import org.jeecgframework.web.system.black.entity.BlackListEntity;
import org.jeecgframework.core.common.service.CommonService;

import java.io.Serializable;

public interface BlackListService extends CommonService{
	
 	void delete(BlackListEntity entity) throws Exception;
 	
 	Serializable save(BlackListEntity entity) throws Exception;
 	
 	void saveOrUpdate(BlackListEntity entity) throws Exception;
 	
}
