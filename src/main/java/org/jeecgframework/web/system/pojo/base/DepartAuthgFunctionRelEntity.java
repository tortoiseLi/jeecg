package org.jeecgframework.web.system.pojo.base;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.jeecgframework.web.system.function.entity.FunctionEntity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 部门权限关联表
 * @author DELL
 * @date 2019-04-29
 * @version V1.0
 */
@Entity
@Table(name = "t_s_depart_authg_function_rel")
public class DepartAuthgFunctionRelEntity implements Serializable {

	/**
	 * ID
	 */
	private String id;

	/**
	 * 权限组ID
	 */
	private DepartAuthGroupEntity tsDepartAuthGroup;

	/**
	 * 权限ID
	 */
	private FunctionEntity tsFunction;

	/**
	 * 页面操作权限
	 */
	private String operation;

	/**
	 * 数据权限
	 */
	private String datarule;

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
	@Column(name ="ID",nullable=false,length=36)
	public String getId(){
		return this.id;
	}

	public void setId(String id){
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "group_id")
	public DepartAuthGroupEntity getTsDepartAuthGroup() {
		return tsDepartAuthGroup;
	}
	
	public void setTsDepartAuthGroup(DepartAuthGroupEntity tsDepartAuthGroup) {
		this.tsDepartAuthGroup = tsDepartAuthGroup;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "auth_id")

	@NotFound(action=NotFoundAction.IGNORE)

	public FunctionEntity getTsFunction() {
		return tsFunction;
	}

	public void setTsFunction(FunctionEntity tsFunction) {
		this.tsFunction = tsFunction;
	}

	@Column(name ="operation",nullable=true,length=2000)
	public String getOperation(){
		return this.operation;
	}

	public void setOperation(String operation){
		this.operation = operation;
	}

	@Column(name ="datarule",nullable=true,length=1000)
	public String getDatarule(){
		return this.datarule;
	}

	public void setDatarule(String datarule){
		this.datarule = datarule;
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
