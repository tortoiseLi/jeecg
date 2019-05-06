package org.jeecgframework.web.system.core;

import org.jeecgframework.core.common.entity.IdEntity;
import org.jeecgframework.web.system.function.entity.FunctionEntity;
import org.jeecgframework.web.system.role.entity.RoleEntity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 角色功能表
 * @author DELL
 * @version V1.0
 * @date 2019-04-29
 */
@Entity
@Table(name = "t_s_role_function")
public class RoleFunctionEntity extends IdEntity implements Serializable {

    /**
     * 菜单
     */
    private FunctionEntity TSFunction;

    /**
     * 角色
     */
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