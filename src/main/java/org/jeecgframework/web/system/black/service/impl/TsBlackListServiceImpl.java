package org.jeecgframework.web.system.black.service.impl;

import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.jeecgframework.web.system.black.entity.TsBlackListEntity;
import org.jeecgframework.web.system.black.service.TsBlackListServiceI;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;

@Service("tsBlackListService")
@Transactional
public class TsBlackListServiceImpl extends CommonServiceImpl implements TsBlackListServiceI {

	
 	@Override
	public void delete(TsBlackListEntity entity) throws Exception{
 		super.delete(entity);
 	}
 	
 	@Override
	public Serializable save(TsBlackListEntity entity) throws Exception{
 		Serializable t = super.add(entity);
 		return t;
 	}
 	
 	@Override
	public void saveOrUpdate(TsBlackListEntity entity) throws Exception{
 		super.saveOrUpdate(entity);
 	}
 	
}