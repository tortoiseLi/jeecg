package org.jeecgframework.web.system.service;
import java.io.Serializable;

import org.jeecgframework.core.common.service.CommonService;
import org.jeecgframework.web.system.core.common.InterfaceEnum;
import org.jeecgframework.web.system.core.InterfaceRuleDto;
import org.jeecgframework.web.system.core.InterfaceEntity;

public interface InterfaceService extends CommonService{
	
 	public void delete(InterfaceEntity entity) throws Exception;
 	
 	public Serializable save(InterfaceEntity entity ) throws Exception;
 	
 	public void saveOrUpdate( InterfaceEntity entity) throws Exception;
 	
 	public InterfaceRuleDto getInterfaceRuleByUserNameAndCode(String userName,InterfaceEnum interfaceEnum);
 	
}
