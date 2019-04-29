package org.jeecgframework.web.system.pojo.base;

import org.jeecgframework.core.common.entity.IdEntity;
import org.jeecgframework.web.system.depart.entity.DepartEntity;
import org.jeecgframework.web.system.role.entity.RoleEntity;

import javax.persistence.*;

/**
 * 角色组织机构表
 * @author DELL
 * @version V1.0
 * @date 2019-04-29
 */
@Entity
@Table(name = "t_s_role_org")
public class RoleOrgEntity extends IdEntity implements java.io.Serializable {

    /**
     * 部门
     */
    private DepartEntity tsDepart;

    /**
     * 角色
     */
    private RoleEntity tsRole;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "org_id")
    public DepartEntity getTsDepart() {
        return tsDepart;
    }

    public void setTsDepart(DepartEntity tsDepart) {
        this.tsDepart = tsDepart;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "role_id")
    public RoleEntity getTsRole() {
        return tsRole;
    }

    public void setTsRole(RoleEntity tsRole) {
        this.tsRole = tsRole;
    }
}
