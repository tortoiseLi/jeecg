package org.jeecgframework.web.system.dict.service.impl;

import org.apache.commons.lang.StringUtils;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.jeecgframework.core.constant.GlobalConstants;
import org.jeecgframework.core.util.MutiLangUtil;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.web.system.core.common.JeecgDictDao;
import org.jeecgframework.web.system.dict.entity.DictEntity;
import org.jeecgframework.web.system.dict.entity.TypeEntity;
import org.jeecgframework.web.system.dict.entity.TypeGroupEntity;
import org.jeecgframework.web.system.dict.service.DictService;
import org.jeecgframework.web.system.core.cache.service.CacheService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 数据字典Service
 * @author DELL
 * @date 2019-05-06
 * @version V1.0
 */
@Service("dictService")
public class DictServiceImpl extends CommonServiceImpl implements DictService {

	private static final Logger logger = LoggerFactory.getLogger(DictServiceImpl.class);
	
	@Autowired
	private JeecgDictDao jeecgDictDao;
	@Autowired
	private CacheService cacheService;

	@Override
	public List<DictEntity> findDictList(String dicTable, String dicCode, String dicText){
		List<DictEntity> dictEntityList;

		if (StringUtils.isBlank(dicTable)) {
			// 无字典表,使用系统字典表
			dictEntityList = jeecgDictDao.querySystemDict(dicCode);
			for (DictEntity dictEntity: dictEntityList) {
				dictEntity.setTypename(MutiLangUtil.getLang(dictEntity.getTypename()));
			}
		}else {
			dicText = StringUtil.isEmpty(dicText, dicCode);
			dictEntityList = jeecgDictDao.queryCustomDict(dicTable, dicCode, dicText);
		}
		return dictEntityList;
	}

	@Override
	public TypeEntity getType(String typeCode, String typeName, TypeGroupEntity typeGroupEntity) {
		List<TypeEntity> ls = commonDao.findListByHql("from TypeEntity where code = ? and typegroupid = ?",typeCode,typeGroupEntity.getId());
		TypeEntity actType;
		if (ls == null || ls.size()==0) {
			actType = new TypeEntity();
			actType.setCode(typeCode);
			actType.setName(typeName);
			actType.setTSTypegroup(typeGroupEntity);
			commonDao.insert(actType);
		}else{
			actType = ls.get(0);
		}
		return actType;

	}

	@Override
	@Transactional(readOnly = true)
	public TypeGroupEntity getTypeGroup(String code, String typgroupename) {
		TypeGroupEntity tsTypegroup = commonDao.getByProperty(TypeGroupEntity.class, "code", code);
		if (tsTypegroup == null) {
			tsTypegroup = new TypeGroupEntity();
			tsTypegroup.setCode(code);
			tsTypegroup.setName(typgroupename);
			commonDao.insert(tsTypegroup);
		}
		return tsTypegroup;
	}

	@Override
	@Transactional(readOnly = true)
	public TypeGroupEntity getTypeGroupByCode(String code) {
		TypeGroupEntity tsTypegroup = commonDao.getByProperty(TypeGroupEntity.class, "code", code);
		return tsTypegroup;
	}

	@Override
	@Transactional(readOnly = true)
	public void initAllTypeGroups() {
		List<TypeGroupEntity> typeGroups = this.commonDao.findList(TypeGroupEntity.class);
		Map<String, TypeGroupEntity> typeGroupsList = new HashMap<String, TypeGroupEntity>();
		Map<String, List<TypeEntity>> typesList = new HashMap<String, List<TypeEntity>>();
		for (TypeGroupEntity tsTypegroup : typeGroups) {
			tsTypegroup.setTSTypes(null);
			typeGroupsList.put(tsTypegroup.getCode().toLowerCase(), tsTypegroup);
			List<TypeEntity> types = this.commonDao.findListByHql("from TypeEntity where TSTypegroup.id = ? order by sort" , tsTypegroup.getId());
			for(TypeEntity t:types){
				t.setTSType(null);
				t.setTSTypegroup(null);
				t.setTSTypes(null);
			}
			typesList.put(tsTypegroup.getCode().toLowerCase(), types);
		}
		
		cacheService.put(GlobalConstants.FOREVER_CACHE,ResourceUtil.DICT_TYPE_GROUPS_KEY,typeGroupsList);
		cacheService.put(GlobalConstants.FOREVER_CACHE,ResourceUtil.DICT_TYPES_KEY,typesList);
		
		logger.info("  ------ 初始化字典组 【系统缓存】-----------typeGroupsList-----size: [{}]",typeGroupsList.size());
		logger.info("  ------ 初始化字典 【系统缓存】-----------typesList-----size: [{}]",typesList.size());
	}

	@Override
	@Transactional(readOnly = true)
	public void refreshTypeCache(TypeEntity type) {
		Map<String, List<TypeEntity>> typesList = null;
		TypeGroupEntity result = null;
		Object obj = cacheService.get(GlobalConstants.FOREVER_CACHE,ResourceUtil.DICT_TYPES_KEY);
		if(obj!=null){
			typesList = (Map<String, List<TypeEntity>>) obj;
		}else{
			typesList = new HashMap<String, List<TypeEntity>>();
		}
		TypeGroupEntity tsTypegroup = type.getTSTypegroup();
		TypeGroupEntity typeGroupEntity = this.commonDao.getById(TypeGroupEntity.class, tsTypegroup.getId());

		List<TypeEntity> tempList = this.commonDao.findListByHql("from TypeEntity where TSTypegroup.id = ? order by orderNum" , tsTypegroup.getId());
		List<TypeEntity> types = new ArrayList<TypeEntity>();
		for(TypeEntity t:tempList){
			TypeEntity tt = new TypeEntity();
			tt.setTSType(null);
			tt.setTSTypegroup(null);
			tt.setTSTypes(null);
			tt.setId(t.getId());
			tt.setSort(t.getSort());
			tt.setCode(t.getCode());
			tt.setName(t.getName());
			types.add(tt);
		}

		typesList.put(typeGroupEntity.getCode().toLowerCase(), types);
		cacheService.put(GlobalConstants.FOREVER_CACHE,ResourceUtil.DICT_TYPES_KEY,typesList);
		logger.info("  ------ 重置字典缓存【系统缓存】  ----------- code: [{}] ",typeGroupEntity.getCode().toLowerCase());
	}

	@Override
	@Transactional(readOnly = true)
	public void refreshTypeGroupCache() {
		Map<String, TypeGroupEntity> typeGroupsList = new HashMap<String, TypeGroupEntity>();
		List<TypeGroupEntity> typeGroups = this.commonDao.findList(TypeGroupEntity.class);
		for (TypeGroupEntity tsTypegroup : typeGroups) {
			typeGroupsList.put(tsTypegroup.getCode().toLowerCase(), tsTypegroup);
		}
		cacheService.put(GlobalConstants.FOREVER_CACHE,ResourceUtil.DICT_TYPE_GROUPS_KEY,typeGroupsList);
		logger.info("  ------ 重置字典分组缓存&字典缓存【系统缓存】  ------ refleshTypeGroupCach --------  ");
	}
	
	/**
	 * 刷新字典分组缓存&字典缓存
	 */
	@Override
	@Transactional(readOnly = true)
	public void refreshTypeAndTypeGroupCache() {
		logger.info("  ------ 重置字典分组缓存&字典缓存【系统缓存】  ------ refreshTypeGroupAndTypes --------  ");
		this.initAllTypeGroups();
	}

}
