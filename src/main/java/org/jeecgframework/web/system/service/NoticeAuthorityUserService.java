package org.jeecgframework.web.system.service;
import java.io.Serializable;

import org.jeecgframework.core.common.service.CommonService;
import org.jeecgframework.web.system.core.NoticeAuthorityUserEntity;

public interface NoticeAuthorityUserService extends CommonService{
	
 	public <T> void delete(T entity);
 	
 	public <T> Serializable save(T entity);
 	
 	public <T> void saveOrUpdate(T entity);
 	
 	/**
	 * 默认按钮-sql增强-新增操作
	 * @param id
	 * @return
	 */
 	public boolean doAddSql(NoticeAuthorityUserEntity t);
 	/**
	 * 默认按钮-sql增强-更新操作
	 * @param id
	 * @return
	 */
 	public boolean doUpdateSql(NoticeAuthorityUserEntity t);
 	/**
	 * 默认按钮-sql增强-删除操作
	 * @param id
	 * @return
	 */
 	public boolean doDelSql(NoticeAuthorityUserEntity t);
 	
 	public boolean checkAuthorityUser(String noticeId, String userid);
 	
 	public void saveNoticeAuthorityUser(NoticeAuthorityUserEntity noticeAuthorityUser);
 	
 	public void doDelNoticeAuthorityUser(NoticeAuthorityUserEntity noticeAuthorityUser);
}
