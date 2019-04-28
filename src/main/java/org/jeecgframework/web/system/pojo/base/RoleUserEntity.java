package org.jeecgframework.web.system.pojo.base;

import org.jeecgframework.core.common.entity.IdEntity;

import javax.persistence.*;

/**
 * TSRoleUser entity.
 * @author  张代浩
 */
@Entity
@Table(name = "t_s_role_user")
public class RoleUserEntity extends IdEntity implements java.io.Serializable {
	private UserEntity TSUser;
	private RoleEntity TSRole;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "userid")
	public UserEntity getTSUser() {
		return this.TSUser;
	}

	public void setTSUser(UserEntity TSUser) {
		this.TSUser = TSUser;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "roleid")
	public RoleEntity getTSRole() {
		return this.TSRole;
	}

	public void setTSRole(RoleEntity TSRole) {
		this.TSRole = TSRole;
	}

}