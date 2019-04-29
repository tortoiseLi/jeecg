package org.jeecgframework.web.system.pojo.base;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 部门管理员设置表
 * @author DELL
 * @date 2019-04-29
 * @version V1.0
 */
@Entity
@Table(name = "t_s_depart_auth_group")
public class DepartAuthGroupEntity implements Serializable {

	/**
	 * ID
	 */
	private String id;

	/**
	 * 权限组名称
	 */
	private String groupName;

	/**
	 * 部门ID
	 */
	private String deptId;

	/**
	 * 部门编码
	 */
	private String deptCode;

	/**
	 * 部门名称
	 */
	private String deptName;

	/**
	 * 类型(1:公司,2:部门,4:供应商)
	 */
	private String departType;

	/**
	 * 级别
	 */
	private Integer level;

	/**
	 * 创建人
	 */
	private String createName;

	/**
	 * 创建人ID
	 */
	private String createBy;

	/**
	 * 创建时间
	 */
	private Date createDate;

	/**
	 * 修改人
	 */
	private String updateName;

	/**
	 * 修改人ID
	 */
	private String updateBy;

	/**
	 * 修改时间
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

	@Id
	@GeneratedValue(generator = "paymentableGenerator")
	@GenericGenerator(name = "paymentableGenerator", strategy = "uuid")
	@Column(name ="id",nullable=false,length=36)
	public String getId(){
		return this.id;
	}

	public void setId(String id){
		this.id = id;
	}

	@Column(name ="group_name",nullable=true,length=100)
	public String getGroupName(){
		return this.groupName;
	}

	public void setGroupName(String groupName){
		this.groupName = groupName;
	}

	@Column(name ="dept_id",nullable=true,length=36)
	public String getDeptId(){
		return this.deptId;
	}

	public void setDeptId(String deptId){
		this.deptId = deptId;
	}

	@Column(name ="dept_code",nullable=true,length=50)
	public String getDeptCode(){
		return this.deptCode;
	}

	public void setDeptCode(String deptCode){
		this.deptCode = deptCode;
	}

	@Column(name ="dept_name",nullable=true,length=100)
	public String getDeptName(){
		return this.deptName;
	}

	public void setDeptName(String deptName){
		this.deptName = deptName;
	}

	@Column(name ="depart_type",nullable=true,length=2)
	public String getDepartType(){
		return this.departType;
	}

	public void setDepartType(String departType){
		this.departType = departType;
	}

	@Column(name ="[level]",nullable=true,length=10)
	public Integer getLevel(){
		return this.level;
	}

	public void setLevel(Integer level){
		this.level = level;
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

	@Column(name ="create_date",nullable=true)
	public Date getCreateDate(){
		return this.createDate;
	}

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

	@Column(name ="update_date",nullable=true)
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
}
