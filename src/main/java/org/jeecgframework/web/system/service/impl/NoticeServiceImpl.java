package org.jeecgframework.web.system.service.impl;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.jeecgframework.web.system.pojo.base.NoticeEntity;
import org.jeecgframework.web.system.pojo.base.NoticeAuthorityRoleEntity;
import org.jeecgframework.web.system.pojo.base.NoticeAuthorityUserEntity;
import org.jeecgframework.web.system.pojo.base.NoticeReadUserEntity;
import org.jeecgframework.web.system.pojo.base.UserEntity;
import org.jeecgframework.web.system.service.NoticeService;
import org.springframework.stereotype.Service;

/**
 * 通知公告Service接口实现类
 * @author  alax
 *
 */
@Service("noticeService")
public class NoticeServiceImpl extends CommonServiceImpl implements NoticeService{
	
	/**
	 * 
	 * @param noticeTilte 标题
	 * @param noticeContent 内容
	 * @param noticeType 类型
	 * @param noticeLevel 级别
	 * @param noticeTerm 期限
	 * @param createUser 创建者
	 * @return
	 */
	@Override
	public String addNotice(String noticeTitle, String noticeContent,
							String noticeType, String noticeLevel, Date noticeTerm,
							String createUser) {
		String noticeId=null;
		NoticeEntity notice = new NoticeEntity();
		notice.setNoticeTitle(noticeTitle);
		notice.setNoticeContent(noticeContent);
		notice.setNoticeType(noticeType);
		notice.setNoticeLevel(noticeLevel);
		notice.setNoticeTerm(noticeTerm);
		notice.setCreateUser(createUser);
		notice.setCreateTime(new Date());
		this.save(notice);
		noticeId = notice.getId();
		return noticeId;
	}

    /**
     * 追加通告授权用户
     */
	@Override
	public void addNoticeAuthorityUser(String noticeId, String userid) {
		if(noticeId != null && userid!=null){
			NoticeAuthorityUserEntity entity = new  NoticeAuthorityUserEntity();
			entity.setNoticeId(noticeId);
			UserEntity tsuser = new UserEntity();
			tsuser.setId(userid);
			entity.setUser(tsuser);
			this.saveOrUpdate(entity);
		}
	}
	
	@Override
	public <T> void delete(T entity) {

		NoticeEntity notice = (NoticeEntity)entity;
		super.deleteByCollection(super.findListByProperty(NoticeReadUserEntity.class, "noticeId", notice.getId()));
		super.deleteByCollection(super.findListByProperty(NoticeAuthorityUserEntity.class, "noticeId", notice.getId()));
		super.deleteByCollection(super.findListByProperty(NoticeAuthorityRoleEntity.class, "noticeId", notice.getId()));
		super.delete(notice);
 		//执行删除操作配置的sql增强
		this.doDelSql(notice);

 	}
 	
 	@Override
	public <T> Serializable save(T entity) {
 		Serializable t = super.add(entity);
 		//执行新增操作配置的sql增强
 		this.doAddSql((NoticeEntity)entity);
 		return t;
 	}
 	
 	@Override
	public <T> void saveOrUpdate(T entity) {
 		super.saveOrUpdate(entity);
 		//执行更新操作配置的sql增强
 		this.doUpdateSql((NoticeEntity)entity);
 	}
 	
 	/**
	 * 默认按钮-sql增强-新增操作
	 * @return
	 */
 	public boolean doAddSql(NoticeEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-更新操作
	 * @return
	 */
 	public boolean doUpdateSql(NoticeEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-删除操作
	 * @return
	 */
 	public boolean doDelSql(NoticeEntity t){
	 	return true;
 	}
 	
 	/**
	 * 替换sql中的变量
	 * @param sql
	 * @return
	 */
 	public String replaceVal(String sql,NoticeEntity t){
 		sql  = sql.replace("#{id}",String.valueOf(t.getId()));
 		sql  = sql.replace("#{notice_title}",String.valueOf(t.getNoticeTitle()));
 		sql  = sql.replace("#{notice_content}",String.valueOf(t.getNoticeContent()));
 		sql  = sql.replace("#{notice_type}",String.valueOf(t.getNoticeType()));
 		sql  = sql.replace("#{notice_level}",String.valueOf(t.getNoticeLevel()));
 		sql  = sql.replace("#{notice_term}",String.valueOf(t.getNoticeTerm()));
 		sql  = sql.replace("#{create_user}",String.valueOf(t.getCreateUser()));
 		sql  = sql.replace("#{create_time}",String.valueOf(t.getCreateTime()));
 		sql  = sql.replace("#{UUID}",UUID.randomUUID().toString());
 		return sql;
 	}
}
