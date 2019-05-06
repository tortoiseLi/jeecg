package org.jeecgframework.web.system.service;
import java.io.Serializable;

import org.jeecgframework.core.common.service.CommonService;
import org.jeecgframework.web.system.core.NoticeAuthorityRoleEntity;

public interface NoticeAuthorityRoleService extends CommonService{
	
 	public <T> void delete(T entity);
 	
 	public <T> Serializable save(T entity);
 	
 	public <T> void saveOrUpdate(T entity);
 	
 	/**
	 * 默认按钮-sql增强-新增操作
	 * @param id
	 * @return
	 */
 	public boolean doAddSql(NoticeAuthorityRoleEntity t);
 	/**
	 * 默认按钮-sql增强-更新操作
	 * @param id
	 * @return
	 */
 	public boolean doUpdateSql(NoticeAuthorityRoleEntity t);
 	/**
	 * 默认按钮-sql增强-删除操作
	 * @param id
	 * @return
	 */
 	public boolean doDelSql(NoticeAuthorityRoleEntity t);
 	
 	public boolean checkAuthorityRole(String noticeId, String roleid);
 	
 	public void saveTSNoticeAuthorityRole(NoticeAuthorityRoleEntity noticeAuthorityRole);
 	
 	public void doDelTSNoticeAuthorityRole(NoticeAuthorityRoleEntity noticeAuthorityRole);
}
