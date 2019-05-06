package org.jeecgframework.web.system.black.service.impl;

import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.jeecgframework.web.system.black.entity.BlackListEntity;
import org.jeecgframework.web.system.black.service.BlackListService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;

@Service("tsBlackListService")
@Transactional
public class BlackListServiceImpl extends CommonServiceImpl implements BlackListService {

	
 	@Override
	public void delete(BlackListEntity entity) throws Exception{
 		super.delete(entity);
 	}
 	
 	@Override
	public Serializable save(BlackListEntity entity) throws Exception{
 		Serializable t = super.add(entity);
 		return t;
 	}
 	
 	@Override
	public void saveOrUpdate(BlackListEntity entity) throws Exception{
 		super.saveOrUpdate(entity);
 	}
 	
}