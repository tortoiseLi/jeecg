package org.jeecgframework.web.system.pojo.base;

import org.jeecgframework.core.common.entity.IdEntity;

import javax.persistence.*;

/**
 * 接口角色用户
 * @author DELL
 * @date 2019-04-29
 * @version V1.0
 */
@Entity
@Table(name = "t_s_interrole_user")
public class InterroleUserEntity extends IdEntity implements java.io.Serializable {

	/**
	 * 用户
	 */
	private UserEntity TSUser;

	/**
	 *
	 */
	private InterroleEntity interroleEntity;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "user_id")
	public UserEntity getTSUser() {
		return this.TSUser;
	}

	public void setTSUser(UserEntity TSUser) {
		this.TSUser = TSUser;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "interrole_id")
	public InterroleEntity getInterroleEntity() {
		return interroleEntity;
	}

	public void setInterroleEntity(InterroleEntity interroleEntity) {
		this.interroleEntity = interroleEntity;
	}
}