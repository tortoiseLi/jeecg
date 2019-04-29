package org.jeecgframework.web.system.pojo.base;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 角色表
 * @author DELL
 * @date 2019-04-29
 * @version V1.0
 */
@Entity
@Table(name = "t_s_interrole")
public class InterroleEntity implements Serializable {

	/**
	 * ID
	 */
	private String id;

	/**
	 * 接口角色编码
	 */
	private String roleCode;

	/**
	 * 接口角色名称
	 */
	private String roleName;

	/**
	 * 创建人ID
	 */
	private String createBy;

	/**
	 * 创建人姓名
	 */
	private String createName;

	/**
	 * 创建时间
	 */
	private Date createDate;

	/**
	 * 修改人ID
	 */
	private String updateBy;

	/**
	 * 修改人姓名
	 */
	private String updateName;

	/**
	 * 修改时间
	 */
	private Date updateDate;

	@Id
	@GeneratedValue(generator = "paymentableGenerator")
	@GenericGenerator(name = "paymentableGenerator", strategy = "uuid")
	@Column(name ="id",nullable=false,length=32)
	public String getId(){
		return this.id;
	}

	public void setId(String id){
		this.id = id;
	}

	@Column(name ="role_code",nullable=true,length=10)
	public String getRoleCode(){
		return this.roleCode;
	}

	public void setRoleCode(String roleCode){
		this.roleCode = roleCode;
	}

	@Column(name ="role_name",nullable=true,length=100)
	public String getRoleName(){
		return this.roleName;
	}

	public void setRoleName(String roleName){
		this.roleName = roleName;
	}

	@Column(name ="update_name",nullable=true,length=32)
	public String getUpdateName(){
		return this.updateName;
	}

	public void setUpdateName(String updateName){
		this.updateName = updateName;
	}

	@Column(name ="update_date",nullable=true)
	public Date getUpdateDate(){
		return this.updateDate;
	}

	public void setUpdateDate(Date updateDate){
		this.updateDate = updateDate;
	}

	@Column(name ="update_by",nullable=true,length=32)
	public String getUpdateBy(){
		return this.updateBy;
	}

	public void setUpdateBy(String updateBy){
		this.updateBy = updateBy;
	}

	@Column(name ="create_name",nullable=true,length=32)
	public String getCreateName(){
		return this.createName;
	}

	public void setCreateName(String createName){
		this.createName = createName;
	}

	@Column(name ="create_date",nullable=true)
	public Date getCreateDate(){
		return this.createDate;
	}

	public void setCreateDate(Date createDate){
		this.createDate = createDate;
	}

	@Column(name ="create_by",nullable=true,length=32)
	public String getCreateBy(){
		return this.createBy;
	}

	public void setCreateBy(String createBy){
		this.createBy = createBy;
	}
}
