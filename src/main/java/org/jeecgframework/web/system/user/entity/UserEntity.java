package org.jeecgframework.web.system.user.entity;

import org.jeecgframework.web.system.pojo.base.BaseUserEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * 系统用户表
 * @author DELL
 * @version V1.0
 * @date 2019-04-29
 */
@Entity
@Table(name = "t_s_user")
@PrimaryKeyJoinColumn(name = "id")
public class UserEntity extends BaseUserEntity implements Serializable {

    /**
     * 签名文件
     */
    private String signatureFile;

    /**
     * 手机号码
     */
    private String mobilePhone;

    /**
     * 办公电话
     */
    private String officePhone;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 头像
     */
    private String portrait;

    /**
     * 开发权限标志
     */
    private String devFlag;

    /**
     * 用户类型(1:系统用户,2接口用户)
     */
    private String userType;

    /**
     * 人员类型
     */
    private String personType;

    /**
     * 性别
     */
    private String sex;

    /**
     * 工号
     */
    private String empNo;

    /**
     * 身份证号
     */
    private String citizenNo;

    /**
     * 传真
     */
    private String fax;

    /**
     * 联系地址
     */
    private String address;

    /**
     * 邮编
     */
    private String post;

    /**
     * 备注
     */
    private String memo;

    /**
     * 创建时间
     */
    private Date createDate;

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
    private Date updateDate;

    /**
     * 修改人
     */
    private String updateBy;

    /**
     * 修改人名称
     */
    private String updateName;

    @Column(name = "dev_flag", length = 2)
    public String getDevFlag() {
        return devFlag;
    }

    public void setDevFlag(String devFlag) {
        this.devFlag = devFlag;
    }

    @Column(name = "signatureFile", length = 100)
    public String getSignatureFile() {
        return this.signatureFile;
    }

    public void setSignatureFile(String signatureFile) {
        this.signatureFile = signatureFile;
    }

    @Column(name = "mobilePhone", length = 30)
    public String getMobilePhone() {
        return this.mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    @Column(name = "officePhone", length = 20)
    public String getOfficePhone() {
        return this.officePhone;
    }

    public void setOfficePhone(String officePhone) {
        this.officePhone = officePhone;
    }

    @Column(name = "email", length = 50)
    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    @Column(name = "portrait", length = 100)
    public String getPortrait() {
        return portrait;
    }

    public void setPortrait(String portrait) {
        this.portrait = portrait;
    }

    @Column(name = "user_type")
    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    @Column(name = "person_type")
    public String getPersonType() {
        return personType;
    }

    public void setPersonType(String personType) {
        this.personType = personType;
    }

    @Column(name = "sex")
    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    @Column(name = "emp_no")
    public String getEmpNo() {
        return empNo;
    }

    public void setEmpNo(String empNo) {
        this.empNo = empNo;
    }

    @Column(name = "citizen_no")
    public String getCitizenNo() {
        return citizenNo;
    }

    public void setCitizenNo(String citizenNo) {
        this.citizenNo = citizenNo;
    }

    @Column(name = "fax")
    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    @Column(name = "address")
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Column(name = "post")
    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }

    @Column(name = "memo")
    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

}