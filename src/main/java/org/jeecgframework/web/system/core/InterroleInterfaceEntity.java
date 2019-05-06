package org.jeecgframework.web.system.core;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 接口权限
 * @author DELL
 * @date 2019-04-29
 * @version V1.0
 */
@Entity
@Table(name = "t_s_interrole_interface")
public class InterroleInterfaceEntity implements Serializable {

	/**
	 * ID
	 */
	private String id;

	/**
	 * 接口数据
	 */
	private String dataRule;

	/**
	 * 角色
	 */
	private InterroleEntity interroleEntity;

	/**
	 * 接口权限
	 */
	private InterfaceEntity interfaceEntity;

	@Id
	@GeneratedValue(generator = "paymentableGenerator")
	@GenericGenerator(name = "paymentableGenerator", strategy = "uuid")
	@Column(name = "id", nullable = false, length = 32)
	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Column(name = "data_rule", length = 1000)
	public String getDataRule() {
		return this.dataRule;
	}

	public void setDataRule(String dataRule) {
		this.dataRule = dataRule;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "interface_id")
	@NotFound(action=NotFoundAction.IGNORE)
	public InterfaceEntity getInterfaceEntity() {
		return this.interfaceEntity;
	}

	public void setInterfaceEntity(InterfaceEntity tSInterfaceEntity) {
		this.interfaceEntity = tSInterfaceEntity;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "interrole_id")
	public InterroleEntity getInterroleEntity() {
		return this.interroleEntity;
	}

	public void setInterroleEntity(InterroleEntity interroleEntity) {
		this.interroleEntity = interroleEntity;
	}

}
