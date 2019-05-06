package org.jeecgframework.web.system.dict.entity;

import org.jeecgframework.core.common.entity.IdEntity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 通用类型字典表
 * @author DELL
 * @version V1.0
 * @date 2019-04-29
 */
@Entity
@Table(name = "t_s_type")
public class TypeEntity extends IdEntity implements Serializable {

    /**
     * 类型编码
     */
    private String code;

    /**
     * 类型名称
     */
    private String name;

    /**
     * 序号
     */
    private String sort;

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
     * 父类型
     */
    private TypeEntity TSType;

    /**
     * 类型分组
     */
    private TypeGroupEntity TSTypegroup;

    /**
     * 子类型
     */
    private List<TypeEntity> TSTypes = new ArrayList();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "typegroupid")
    public TypeGroupEntity getTSTypegroup() {
        return this.TSTypegroup;
    }

    public void setTSTypegroup(TypeGroupEntity TSTypegroup) {
        this.TSTypegroup = TSTypegroup;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "typepid")
    public TypeEntity getTSType() {
        return this.TSType;
    }

    public void setTSType(TypeEntity TSType) {
        this.TSType = TSType;
    }

    @Column(name = "code", length = 50)
    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Column(name = "name", length = 50)
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
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

    @Column(name = "sort", length = 3)
    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "TSType")
    public List<TypeEntity> getTSTypes() {
        return this.TSTypes;
    }

    public void setTSTypes(List<TypeEntity> TSTypes) {
        this.TSTypes = TSTypes;
    }

}