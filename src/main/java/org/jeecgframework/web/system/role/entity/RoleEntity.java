package org.jeecgframework.web.system.role.entity;

import org.jeecgframework.core.common.entity.IdEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 角色表
 * @author DELL
 * @version V1.0
 * @date 2019-04-29
 */
@Entity
@Table(name = "t_s_role")
public class RoleEntity extends IdEntity implements java.io.Serializable {

    /**
     * 角色编码
     */
    private String roleCode;

    /**
     * 角色名称
     */
    private String roleName;

    /**
     * 部门权限组ID
     */
    private String departAgId;

    /**
     * 角色类型1部门角色/0系统角色
     */
    private String roleType;

    /**
     * 创建时间
     */
    private java.util.Date createDate;

    /**
     * 创建人ID
     */
    private String createBy;

    /**
     * 创建人名称
     */
    private String createName;

    /**
     * 修改时间
     */
    private java.util.Date updateDate;

    /**
     * 修改人
     */
    private String updateBy;

    /**
     * 修改人名称
     */
    private String updateName;

    @Column(name = "rolename", nullable = false, length = 100)
    public String getRoleName() {
        return this.roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    @Column(name = "rolecode", length = 10)
    public String getRoleCode() {
        return this.roleCode;
    }

    public void setRoleCode(String roleCode) {
        this.roleCode = roleCode;
    }

    @Column(name = "create_date", nullable = true)
    public java.util.Date getCreateDate() {
        return this.createDate;
    }

    public void setCreateDate(java.util.Date createDate) {
        this.createDate = createDate;
    }

    @Column(name = "create_by", nullable = true, length = 32)
    public String getCreateBy() {
        return this.createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    @Column(name = "create_name", nullable = true, length = 32)
    public String getCreateName() {
        return this.createName;
    }

    public void setCreateName(String createName) {
        this.createName = createName;
    }

    @Column(name = "update_date", nullable = true)
    public java.util.Date getUpdateDate() {
        return this.updateDate;
    }

    public void setUpdateDate(java.util.Date updateDate) {
        this.updateDate = updateDate;
    }

    @Column(name = "update_by", nullable = true, length = 32)
    public String getUpdateBy() {
        return this.updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    @Column(name = "update_name", nullable = true, length = 32)
    public String getUpdateName() {
        return this.updateName;
    }

    public void setUpdateName(String updateName) {
        this.updateName = updateName;
    }

    @Column(name = "depart_ag_id", nullable = true, length = 32)
    public String getDepartAgId() {
        return departAgId;
    }

    public void setDepartAgId(String departAgId) {
        this.departAgId = departAgId;
    }

    @Column(name = "role_type", nullable = true, length = 32)
    public String getRoleType() {
        return roleType;
    }

    public void setRoleType(String roleType) {
        this.roleType = roleType;
    }
}