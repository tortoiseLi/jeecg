package org.jeecgframework.web.system.dict.entity;

import org.jeecgframework.core.common.entity.IdEntity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 通用类型组字典表
 * @author DELL
 * @version V1.0
 * @date 2019-04-29
 */
@Entity
@Table(name = "t_s_type_group")
public class TypeGroupEntity extends IdEntity implements Serializable {

    /**
     * 字典组编码
     */
    private String code;

    /**
     * 字典组名称
     */
    private String name;

    /**
     * 创建人账号
     */
    private String createAccount;

    /**
     * 创建人姓名
     */
    private String createName;

    /**
     * 创建时间
     */
    private String createTime;

    /**
     * 修改人账号
     */
    private String updateAccount;

    /**
     * 修改人姓名
     */
    private String updateName;

    /**
     * 修改时间
     */
    private String updateTime;

    /**
     * 创建人/修改人组织机构编码
     */
    private String orgCode;

    /**
     * 数据状态
     */
    private String stateFlag;

    /**
     * 字典List
     */
    private List<TypeEntity> TSTypes = new ArrayList<>();

    @Column(name = "name", length = 50)
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "code", length = 50)
    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Column(name = "create_account")
    public String getCreateAccount() {
        return createAccount;
    }

    public void setCreateAccount(String createAccount) {
        this.createAccount = createAccount;
    }

    @Column(name = "create_name")
    public String getCreateName() {
        return createName;
    }

    public void setCreateName(String createName) {
        this.createName = createName;
    }

    @Column(name = "create_time")
    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    @Column(name = "update_account")
    public String getUpdateAccount() {
        return updateAccount;
    }

    public void setUpdateAccount(String updateAccount) {
        this.updateAccount = updateAccount;
    }

    @Column(name = "update_name")
    public String getUpdateName() {
        return updateName;
    }

    public void setUpdateName(String updateName) {
        this.updateName = updateName;
    }

    @Column(name = "update_time")
    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    @Column(name = "org_code")
    public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    @Column(name = "state_flag")
    public String getStateFlag() {
        return stateFlag;
    }

    public void setStateFlag(String stateFlag) {
        this.stateFlag = stateFlag;
    }

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "TSTypegroup")
    public List<TypeEntity> getTSTypes() {
        return this.TSTypes;
    }

    public void setTSTypes(List<TypeEntity> TSTypes) {
        this.TSTypes = TSTypes;
    }

}