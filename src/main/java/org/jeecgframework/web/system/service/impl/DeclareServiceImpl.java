package org.jeecgframework.web.system.service.impl;

import org.jeecgframework.web.system.attachment.entity.AttachmentEntity;
import org.jeecgframework.web.system.service.DeclareService;

import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service("declareService")
@Transactional
public class DeclareServiceImpl extends CommonServiceImpl implements DeclareService {

	@Override
	public List<AttachmentEntity> getAttachmentsByCode(String businessKey, String description)
	{

		String hql="from AttachmentEntity t where t.businessKey= ? and t.description = ?";
		return commonDao.findHql(hql,businessKey,description);

	}
	
}
