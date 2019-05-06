package org.jeecgframework.web.system.service.impl;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.jeecgframework.core.common.exception.BusinessException;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.jeecgframework.web.system.core.NoticeAuthorityRoleEntity;
import org.jeecgframework.web.system.core.NoticeReadUserEntity;
import org.jeecgframework.web.system.core.RoleUserEntity;
import org.jeecgframework.web.system.service.NoticeAuthorityRoleService;
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("noticeAuthorityRoleService")
@Transactional
public class NoticeAuthorityRoleServiceImpl extends CommonServiceImpl implements NoticeAuthorityRoleService {

	@Autowired
	private SystemService systemService;
	
	private ExecutorService executor = Executors.newCachedThreadPool();
	
 	public <T> void delete(T entity) {
 		super.delete(entity);
 		//执行删除操作配置的sql增强
		this.doDelSql((NoticeAuthorityRoleEntity)entity);
 	}
 	
 	public <T> Serializable save(T entity) {
 		Serializable t = super.add(entity);
 		//执行新增操作配置的sql增强
 		this.doAddSql((NoticeAuthorityRoleEntity)entity);
 		return t;
 	}
 	
 	public <T> void saveOrUpdate(T entity) {
 		super.saveOrUpdate(entity);
 		//执行更新操作配置的sql增强
 		this.doUpdateSql((NoticeAuthorityRoleEntity)entity);
 	}
 	
 	/**
	 * 默认按钮-sql增强-新增操作
	 * @param id
	 * @return
	 */
 	public boolean doAddSql(NoticeAuthorityRoleEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-更新操作
	 * @param id
	 * @return
	 */
 	public boolean doUpdateSql(NoticeAuthorityRoleEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-删除操作
	 * @param id
	 * @return
	 */
 	public boolean doDelSql(NoticeAuthorityRoleEntity t){
	 	return true;
 	}
 	
 	/**
	 * 替换sql中的变量
	 * @param sql
	 * @return
	 */
 	public String replaceVal(String sql,NoticeAuthorityRoleEntity t){
 		sql  = sql.replace("#{id}",String.valueOf(t.getId()));
 		sql  = sql.replace("#{notice_id}",String.valueOf(t.getNoticeId()));
 		sql  = sql.replace("#{role_id}",String.valueOf(t.getRole().getId()));
 		sql  = sql.replace("#{UUID}",UUID.randomUUID().toString());
 		return sql;
 	}
 	
 	/**
 	 * 检查通知公告授权角色是否存在
 	 */
 	public boolean checkAuthorityRole(String noticeId, String roleid) {
		CriteriaQuery cq = new CriteriaQuery(NoticeAuthorityRoleEntity.class);
		//查询条件组装器
		cq.eq("role.id", roleid);
		cq.eq("noticeId", noticeId);
		cq.add();
		List<NoticeAuthorityRoleEntity> rlist =   this.getListByCriteriaQuery(cq, false);
		if(rlist.size()==0){
			return false;
		}else{
			return true;
		}
	}

	@Override
	public void saveTSNoticeAuthorityRole(
			NoticeAuthorityRoleEntity noticeAuthorityRole) {
			if(this.checkAuthorityRole(noticeAuthorityRole.getNoticeId(), noticeAuthorityRole.getRole().getId())){
				throw new BusinessException("该角色已授权，请勿重复操作。");
			}else{
				final String noticeId = noticeAuthorityRole.getNoticeId();
				final String roleId = noticeAuthorityRole.getRole().getId();
				executor.execute(new Runnable() {
	
					@Override
					public void run() {
						String hql = "from RoleUserEntity roleUser where roleUser.TSRole.id = ?";
						List<RoleUserEntity> roleUserList = systemService.findHql(hql,roleId);
						for (RoleUserEntity roleUser : roleUserList) {
							String userId = roleUser.getTSUser().getId();
							String noticeReadHql = "from NoticeReadUserEntity where noticeId = ? and userId = ?";
							List<NoticeReadUserEntity> noticeReadList = systemService.findHql(noticeReadHql,noticeId,userId);
							if(noticeReadList == null || noticeReadList.isEmpty()){
								//未授权过的消息，添加授权记录
								NoticeReadUserEntity noticeRead = new NoticeReadUserEntity();
								noticeRead.setNoticeId(noticeId);
								noticeRead.setUserId(userId);
								noticeRead.setCreateTime(new Date());
								systemService.add(noticeRead);
							}else if(noticeReadList.size() > 0){
								for (NoticeReadUserEntity readUser : noticeReadList) {
									if(readUser.getDelFlag() == 1){
										readUser.setDelFlag(0);
										systemService.update(readUser);
									}
								}
							}
						}
						roleUserList.clear();
					}
				});
				this.save(noticeAuthorityRole);
		}
	}

	@Override
	public void doDelTSNoticeAuthorityRole(
			NoticeAuthorityRoleEntity noticeAuthorityRole) {
		noticeAuthorityRole = systemService.getById(NoticeAuthorityRoleEntity.class, noticeAuthorityRole.getId());
		final String noticeId = noticeAuthorityRole.getNoticeId();
		final String roleId = noticeAuthorityRole.getRole().getId();
		executor.execute(new Runnable() {
			
			@Override
			public void run() {
				String hql = "from RoleUserEntity roleUser where roleUser.TSRole.id = ?";
				List<RoleUserEntity> roleUserList = systemService.findHql(hql,roleId);
				List<NoticeReadUserEntity> deleteList = new ArrayList<NoticeReadUserEntity>();
				List<NoticeReadUserEntity> updateList = new ArrayList<NoticeReadUserEntity>();
				for (RoleUserEntity roleUser : roleUserList) {
					String userId = roleUser.getTSUser().getId();
					String noticeReadHql = "from NoticeReadUserEntity where noticeId = ? and userId = ?";
					List<NoticeReadUserEntity> noticeReadList = systemService.findHql(noticeReadHql,noticeId,userId);
					if(noticeReadList != null && noticeReadList.size() > 0){
						for (NoticeReadUserEntity readUser : noticeReadList) {
							if(readUser.getIsRead() == 1){
								readUser.setDelFlag(1);
								updateList.add(readUser);
							}else if(readUser.getIsRead() == 0){
								deleteList.add(readUser);
							}
						}
					}
				}
				for (NoticeReadUserEntity tsNoticeReadUser : updateList) {
					systemService.update(tsNoticeReadUser);
				}
				for (NoticeReadUserEntity readUser : deleteList) {
					systemService.delete(readUser);
				}
				updateList.clear();
				deleteList.clear();
				roleUserList.clear();
			}
		});
		this.delete(noticeAuthorityRole);
	}
}