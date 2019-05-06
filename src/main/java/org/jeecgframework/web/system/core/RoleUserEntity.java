package org.jeecgframework.web.system.core;

import org.jeecgframework.core.common.entity.IdEntity;
import org.jeecgframework.web.system.role.entity.RoleEntity;
import org.jeecgframework.web.system.user.entity.UserEntity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 角色用户表
 * @author DELL
 * @version V1.0
 * @date 2019-04-29
 */
@Entity
@Table(name = "t_s_role_user")
public class RoleUserEntity extends IdEntity implements Serializable {

    /**
     * 用户
     */
    private UserEntity TSUser;

    /**
     * 角色
     */
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