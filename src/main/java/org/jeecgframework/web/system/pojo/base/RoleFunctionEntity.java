package org.jeecgframework.web.system.pojo.base;

import org.jeecgframework.core.common.entity.IdEntity;

import javax.persistence.*;

/**
 * TRoleFunction entity. 
 *  @author  张代浩
 */
@Entity
@Table(name = "t_s_role_function")
public class RoleFunctionEntity extends IdEntity implements java.io.Serializable {
	private FunctionEntity TSFunction;
	private RoleEntity TSRole;
	private String operation;
	private String dataRule;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "functionid")
	public FunctionEntity getTSFunction() {
		return this.TSFunction;
	}

	public void setTSFunction(FunctionEntity TSFunction) {
		this.TSFunction = TSFunction;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "roleid")
	public RoleEntity getTSRole() {
		return this.TSRole;
	}

	public void setTSRole(RoleEntity TSRole) {
		this.TSRole = TSRole;
	}

	@Column(name = "operation", length = 100)
	public String getOperation() {
		return this.operation;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}
	@Column(name = "datarule", length = 100)
	public String getDataRule() {
		return dataRule;
	}

	public void setDataRule(String dataRule) {
		this.dataRule = dataRule;
	}

}