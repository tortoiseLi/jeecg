package org.jeecgframework.web.system.pojo.base;
// default package

import org.jeecgframework.core.common.entity.IdEntity;

import javax.persistence.*;

/**
 * 通知公告角色授权表
 *  @author  alax
 */
@Entity
@Table(name = "t_s_notice_authority_role")
@SuppressWarnings("serial")
public class NoticeAuthorityRoleEntity extends IdEntity implements java.io.Serializable {

	private String noticeId;// 通告ID
	private RoleEntity role;//
	
	public void setNoticeId(String noticeId) {
		this.noticeId = noticeId;
	}
	
	@Column(name ="notice_id",nullable=true)
	public String getNoticeId() {
		return noticeId;
	}

	public void setRole(RoleEntity role) {
		this.role = role;
	}
	
	@ManyToOne
	@JoinColumn(name="role_id")
	public RoleEntity getRole() {
		return role;
	}


	}