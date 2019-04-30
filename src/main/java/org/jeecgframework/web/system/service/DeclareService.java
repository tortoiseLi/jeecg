package org.jeecgframework.web.system.service;



import org.jeecgframework.core.common.service.CommonService;
import org.jeecgframework.web.system.attachment.entity.AttachmentEntity;

import java.util.List;

/**
 * 
 * @author  张代浩
 *
 */
public interface DeclareService extends CommonService{
	
	public List<AttachmentEntity> getAttachmentsByCode(String businessKey, String description);
	
}
