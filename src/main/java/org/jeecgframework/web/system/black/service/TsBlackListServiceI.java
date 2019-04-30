package org.jeecgframework.web.system.black.service;
import org.jeecgframework.web.system.black.entity.TsBlackListEntity;
import org.jeecgframework.core.common.service.CommonService;

import java.io.Serializable;

public interface TsBlackListServiceI extends CommonService{
	
 	void delete(TsBlackListEntity entity) throws Exception;
 	
 	Serializable save(TsBlackListEntity entity) throws Exception;
 	
 	void saveOrUpdate(TsBlackListEntity entity) throws Exception;
 	
}
