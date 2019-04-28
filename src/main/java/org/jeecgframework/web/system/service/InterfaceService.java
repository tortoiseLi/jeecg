package org.jeecgframework.web.system.service;
import java.io.Serializable;

import org.jeecgframework.core.common.service.CommonService;
import org.jeecgframework.web.system.enums.InterfaceEnum;
import org.jeecgframework.web.system.pojo.base.InterfaceRuleDto;
import org.jeecgframework.web.system.pojo.base.TSInterfaceEntity;

public interface InterfaceService extends CommonService{
	
 	void delete(TSInterfaceEntity entity) throws Exception;
 	
 	Serializable save(TSInterfaceEntity entity) throws Exception;
 	
 	void saveOrUpdate(TSInterfaceEntity entity) throws Exception;
 	
 	InterfaceRuleDto getInterfaceRuleByUserNameAndCode(String userName, InterfaceEnum interfaceEnum);
 	
}
