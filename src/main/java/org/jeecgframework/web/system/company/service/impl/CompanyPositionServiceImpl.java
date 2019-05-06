package org.jeecgframework.web.system.company.service.impl;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.jeecgframework.core.util.ApplicationContextUtil;
import org.jeecgframework.core.util.MyClassLoader;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.web.cgform.enhance.CgformEnhanceJavaInter;
import org.jeecgframework.web.system.company.entity.CompanyPositionEntity;
import org.jeecgframework.web.system.company.service.CompanyPositionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("companyPositionService")
public class CompanyPositionServiceImpl extends CommonServiceImpl implements CompanyPositionService {

 	@Override
	public void delete(CompanyPositionEntity entity) {
 		super.delete(entity);
 	}
 	
 	@Override
	public Serializable save(CompanyPositionEntity entity) {
 		Serializable t = super.add(entity);
 		return t;
 	}
 	
 	@Override
	public void saveOrUpdate(CompanyPositionEntity entity) {
 		super.saveOrUpdate(entity);
 	}

}