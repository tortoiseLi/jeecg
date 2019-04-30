package org.jeecgframework.web.system.dict.entity;

import org.jeecgframework.core.common.entity.IdEntity;
import org.jeecgframework.web.system.dict.entity.TypeEntity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 通用类型组字典表
 * @author DELL
 * @version V1.0
 * @date 2019-04-29
 */
@Entity
@Table(name = "t_s_typegroup")
public class TypeGroupEntity extends IdEntity implements Serializable {

    /**
     * 字典组编码
     */
    private String typegroupcode;

    /**
     * 字典组名称
     */
    private String typegroupname;

    /**
     * 创建时间
     */
    private Date createDate;

    /**
     * 创建用户
     */
    private String createName;

    /**
     * 字典List
     */
    private List<TypeEntity> TSTypes = new ArrayList<>();

    @Column(name = "typegroupname", length = 50)
    public String getTypegroupname() {
        return this.typegroupname;
    }

    public void setTypegroupname(String typegroupname) {
        this.typegroupname = typegroupname;
    }

    @Column(name = "typegroupcode", length = 50)
    public String getTypegroupcode() {
        return this.typegroupcode;
    }

    public void setTypegroupcode(String typegroupcode) {
        this.typegroupcode = typegroupcode;
    }

    @Column(name = "create_date")
    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    @Column(name = "create_name", length = 36)
    public String getCreateName() {
        return createName;
    }

    public void setCreateName(String createName) {
        this.createName = createName;
    }

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "TSTypegroup")
    public List<TypeEntity> getTSTypes() {
        return this.TSTypes;
    }

    public void setTSTypes(List<TypeEntity> TSTypes) {
        this.TSTypes = TSTypes;
    }

}