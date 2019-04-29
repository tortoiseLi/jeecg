package org.jeecgframework.web.system.pojo.base;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 部门管理员关联表
 * @author DELL
 * @date 2019-04-29
 * @version V1.0
 */
@Entity
@Table(name = "t_s_depart_authg_manager")
public class DepartAuthgManagerEntity implements Serializable {

	/**
	 * ID
	 */
	private String id;

	/**
	 * 权限组ID
	 */
	private String groupId;

	/**
	 * 用户ID
	 */
	private String userId;

	/**
	 * 权限组类型
	 */
	private Integer type;

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

	@Column(name ="group_id",nullable=true,length=36)
	public String getGroupId(){
		return this.groupId;
	}

	public void setGroupId(String groupId){
		this.groupId = groupId;
	}

	@Column(name ="user_id",nullable=true,length=36)
	public String getUserId(){
		return this.userId;
	}

	public void setUserId(String userId){
		this.userId = userId;
	}

	@Column(name ="type",nullable=true,length=3)
	public Integer getType(){
		return this.type;
	}

	public void setType(Integer type){
		this.type = type;
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
