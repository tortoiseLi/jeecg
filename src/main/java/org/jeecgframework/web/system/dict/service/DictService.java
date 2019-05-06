package org.jeecgframework.web.system.dict.service;

import org.jeecgframework.core.common.service.CommonService;
import org.jeecgframework.web.system.dict.entity.DictEntity;
import org.jeecgframework.web.system.dict.entity.TypeEntity;
import org.jeecgframework.web.system.dict.entity.TypeGroupEntity;

import java.util.List;

/**
 * 数据字典Service
 * @author DELL
 * @date 2019-05-06
 * @version V1.0
 */
public interface DictService extends CommonService{

	/**
	 * 查询数据字典列表
	 * @param dicTable 表名称
	 * @param dicCode 编码
	 * @param dicText 名称
	 * @return
	 */
	List<DictEntity> findDictList(String dicTable, String dicCode, String dicText);

	/**
	 * 根据类型编码和类型名称获取Type,如果为空则创建一个
	 * @param typeCode
	 * @param typeName
	 * @param typeGroupEntity
	 * @return
	 */
	TypeEntity getType(String typeCode, String typeName, TypeGroupEntity typeGroupEntity);

	/**
	 * 根据类型分组编码和名称获取TypeGroup,如果为空则创建一个
	 * @param code
	 * @param typeGroupName
	 * @return
	 */
	TypeGroupEntity getTypeGroup(String code, String typeGroupName);

	/**
	 * 根据编码获取字典组
	 * @param code
	 * @return
	 */
	TypeGroupEntity getTypeGroupByCode(String code);

	/**
	 * 对数据字典进行缓存
	 */
	void initAllTypeGroups();

	/**
	 * 刷新字典缓存
	 * @param type
	 */
	void refreshTypeCache(TypeEntity type);

	/**
	 * 刷新字典分组缓存
	 */
	void refreshTypeGroupCache();

	/**
	 * 刷新字典分组缓存&字典缓存
	 */
	void refreshTypeAndTypeGroupCache();

}
