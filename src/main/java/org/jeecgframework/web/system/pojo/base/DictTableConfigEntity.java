package org.jeecgframework.web.system.pojo.base;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 字典表授权配置
 * @author DELL
 * @date 2019-04-29
 * @version V1.0
 */
@Entity
@Table(name = "t_s_dict_table_config")
public class DictTableConfigEntity implements Serializable {

	/**
	 * ID
	 */
	private String id;

	/**
	 * 表名
	 */
	private String tableName;

	/**
	 * 值字段名
	 */
	private String valueCol;

	/**
	 * 文本字段名
	 */
	private String textCol;

	/**
	 * 字典表查询条件
	 */
	private String dictCondition;

	/**
	 * 是否启用
	 */
	private String isvalid;

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

	@Column(name ="table_name",nullable=true,length=100)
	public String getTableName(){
		return this.tableName;
	}

	public void setTableName(String tableName){
		this.tableName = tableName;
	}

	@Column(name ="value_col",nullable=true,length=50)
	public String getValueCol(){
		return this.valueCol;
	}

	public void setValueCol(String valueCol){
		this.valueCol = valueCol;
	}

	@Column(name ="text_col",nullable=true,length=50)
	public String getTextCol(){
		return this.textCol;
	}

	public void setTextCol(String textCol){
		this.textCol = textCol;
	}

	@Column(name ="isvalid",nullable=true,length=32)
	public String getIsvalid(){
		return this.isvalid;
	}

	public void setIsvalid(String isvalid){
		this.isvalid = isvalid;
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

	@Column(name ="dict_condition")
	public String getDictCondition() {
		return dictCondition;
	}

	public void setDictCondition(String dictCondition) {
		this.dictCondition = dictCondition;
	}
	
	
}
