package org.jeecgframework.web.system.service;
import java.io.Serializable;

import org.jeecgframework.core.common.service.CommonService;
import org.jeecgframework.web.system.dict.entity.DictTableConfigEntity;

/**
 * 字典表授权配置
 * @author zhoujf
 *
 */
public interface DictTableConfigService extends CommonService{
	
 	public void delete(DictTableConfigEntity entity) throws Exception;
 	
 	public Serializable save(DictTableConfigEntity entity) throws Exception;
 	
 	public void saveOrUpdate(DictTableConfigEntity entity) throws Exception;
 	
 	/**
 	 * 校验自定义字典是否授权
 	 * @param dictionary
 	 * @param dictCondition
 	 * @return
 	 */
 	public boolean checkDictAuth(String dictionary,String dictCondition);
 	
 	/**
 	 * 自定义字典转换value为text文本值
 	 * @param dictionary
 	 * @param dictCondition
 	 * @param value  编码值
 	 * @return
 	 */
 	public Object getDictText(String dictionary,String dictCondition,String value);
 	
}
