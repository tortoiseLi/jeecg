package org.jeecgframework.web.system.pojo.base;
// default package

import org.jeecgframework.core.common.entity.IdEntity;

import javax.persistence.*;

/**
 * 通知公告用户授权表
 *  @author  alax
 */
@Entity
@Table(name = "t_s_notice_authority_user")
@SuppressWarnings("serial")
public class NoticeAuthorityUserEntity extends IdEntity implements java.io.Serializable {

	private String noticeId;// 通告ID
	private UserEntity user;//用户
	
	public void setNoticeId(String noticeId) {
		this.noticeId = noticeId;
	}
	
	@Column(name ="notice_id",nullable=true)
	public String getNoticeId() {
		return noticeId;
	}

	public void setUser(UserEntity user) {
		this.user = user;
	}
	
	@ManyToOne
	@JoinColumn(name="user_id")
	public UserEntity getUser() {
		return user;
	}

	}