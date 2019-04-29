package org.jeecgframework.web.system.depart.entity;

import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.jeecgframework.core.common.controller.CustomJsonDateDeserializer;
import org.jeecgframework.core.common.entity.IdEntity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 部门机构表
 * @author DELL
 * @date 2019-04-29
 * @version V1.0
 */
@Entity
@Table(name = "t_s_depart")
public class DepartEntity extends IdEntity implements Serializable {

	/**
	 * 部门名称
	 */
	private String departname;

	/**
	 * 部门描述
	 */
	private String description;

	/**
	 * 机构编码
	 */
    private String orgCode;

	/**
	 * 机构类型
	 */
    private String orgType;

	/**
	 * 电话
	 */
	private String mobile;

	/**
	 * 传真
	 */
	private String fax;

	/**
	 * 地址
	 */
	private String address;

	/**
	 * 排序
	 */
	private String departOrder;

	/**
	 * 创建人名称
	 */
	private String createName;

	/**
	 * 创建人登录名称
	 */
	private String createBy;

	/**
	 * 创建日期
	 */
	private Date createDate;

	/**
	 * 更新人名称
	 */
	private String updateName;

	/**
	 * 更新人登录名称
	 */
	private String updateBy;

	/**
	 * 更新日期
	 */
	private Date updateDate;

	/**
	 * 所属部门
	 */
	private String sysOrgCode;

	/**
	 * 所属公司
	 */
	private String sysCompanyCode;

	/**
	 * 上级部门
	 */
	private DepartEntity TSPDepart;

	/**
	 * 下级部门
	 */
	private List<DepartEntity> TSDeparts = new ArrayList<>();

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "parentdepartid")
	public DepartEntity getTSPDepart() {
		return this.TSPDepart;
	}

	public void setTSPDepart(DepartEntity TSPDepart) {
		this.TSPDepart = TSPDepart;
	}

	@Column(name ="create_name",nullable=true,length=50)
	public String getCreateName(){
		return this.createName;
	}

	public void setCreateName(String createName){
		this.createName = createName;
	}

	@Column(name ="create_by",nullable=true,length=50)
	public String getCreateBy(){
		return this.createBy;
	}

	public void setCreateBy(String createBy){
		this.createBy = createBy;
	}

	@Column(name ="create_date",nullable=true,length=20)
	public Date getCreateDate(){
		return this.createDate;
	}

	@JsonDeserialize(using = CustomJsonDateDeserializer.class)
	public void setCreateDate(Date createDate){
		this.createDate = createDate;
	}

	@Column(name ="update_name",nullable=true,length=50)
	public String getUpdateName(){
		return this.updateName;
	}

	public void setUpdateName(String updateName){
		this.updateName = updateName;
	}

	@Column(name ="update_by",nullable=true,length=50)
	public String getUpdateBy(){
		return this.updateBy;
	}

	public void setUpdateBy(String updateBy){
		this.updateBy = updateBy;
	}

	@Column(name ="update_date",nullable=true,length=20)
	@JsonDeserialize(using = CustomJsonDateDeserializer.class)
	public Date getUpdateDate(){
		return this.updateDate;
	}

	public void setUpdateDate(Date updateDate){
		this.updateDate = updateDate;
	}

	@Column(name ="sys_org_code",nullable=true,length=50)
	public String getSysOrgCode(){
		return this.sysOrgCode;
	}

	public void setSysOrgCode(String sysOrgCode){
		this.sysOrgCode = sysOrgCode;
	}

	@Column(name ="sys_company_code",nullable=true,length=50)
	public String getSysCompanyCode(){
		return this.sysCompanyCode;
	}

	public void setSysCompanyCode(String sysCompanyCode){
		this.sysCompanyCode = sysCompanyCode;
	}

	@Column(name = "departname", nullable = false, length = 100)
	public String getDepartname() {
		return this.departname;
	}

	public void setDepartname(String departname) {
		this.departname = departname;
	}

	@Column(name = "description", length = 500)
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "TSPDepart")
	public List<DepartEntity> getTSDeparts() {
		return TSDeparts;
	}

	public void setTSDeparts(List<DepartEntity> tSDeparts) {
		TSDeparts = tSDeparts;
	}

    @Column(name = "org_code", length = 64)
    public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    @Column(name = "org_type", length = 1)
    public String getOrgType() {
        return orgType;
    }

    public void setOrgType(String orgType) {
        this.orgType = orgType;
    }

	@Column(name = "mobile", length = 32)
	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	@Column(name = "fax", length = 32)
	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	@Column(name = "address", length = 100)
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
	@Column(name="depart_order")
	public String getDepartOrder() {
		return departOrder;
	}

	public void setDepartOrder(String departOrder) {
		this.departOrder = departOrder;
	}
	
	
	
}