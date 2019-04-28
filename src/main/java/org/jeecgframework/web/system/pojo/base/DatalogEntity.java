package org.jeecgframework.web.system.pojo.base;

import org.jeecgframework.core.common.entity.IdEntity;
import org.jeecgframework.poi.excel.annotation.Excel;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @Title: Entity
 * @Description: 数据日志
 * @author onlineGenerator
 * @date 2015-06-28 14:02:26
 * @version V1.0
 *
 */
@Entity
@Table(name = "t_s_data_log", schema = "")
@SuppressWarnings("serial")
public class DatalogEntity extends IdEntity implements java.io.Serializable {
	/**创建人名称*/
	private String createName;
	/**创建人登录名称*/
	private String createBy;
	/**创建日期*/
	private java.util.Date createDate;
	/**更新人名称*/
	private String updateName;
	/**更新人登录名称*/
	private String updateBy;
	/**更新日期*/
	private java.util.Date updateDate;
	/**所属部门*/
	private String sysOrgCode;
	/**所属公司*/
	private String sysCompanyCode;
	/**表名*/
	@Excel(name="表名")
	private String tableName;
	/**数据ID*/
	@Excel(name="数据ID")
	private String dataId;
	/**数据内容*/
	@Excel(name="数据内容")
	private String dataContent;
	/**版本号*/
	@Excel(name="版本号")
	private Integer versionNumber;

	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  创建人名称
	 */
	@Column(name ="CREATE_NAME",nullable=true,length=50)
	public String getCreateName(){
		return this.createName;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  创建人名称
	 */
	public void setCreateName(String createName){
		this.createName = createName;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  创建人登录名称
	 */
	@Column(name ="CREATE_BY",nullable=true,length=50)
	public String getCreateBy(){
		return this.createBy;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  创建人登录名称
	 */
	public void setCreateBy(String createBy){
		this.createBy = createBy;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  创建日期
	 */
	@Column(name ="CREATE_DATE",nullable=true,length=20)
	public java.util.Date getCreateDate(){
		return this.createDate;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  创建日期
	 */
	public void setCreateDate(java.util.Date createDate){
		this.createDate = createDate;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  更新人名称
	 */
	@Column(name ="UPDATE_NAME",nullable=true,length=50)
	public String getUpdateName(){
		return this.updateName;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  更新人名称
	 */
	public void setUpdateName(String updateName){
		this.updateName = updateName;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  更新人登录名称
	 */
	@Column(name ="UPDATE_BY",nullable=true,length=50)
	public String getUpdateBy(){
		return this.updateBy;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  更新人登录名称
	 */
	public void setUpdateBy(String updateBy){
		this.updateBy = updateBy;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  更新日期
	 */
	@Column(name ="UPDATE_DATE",nullable=true,length=20)
	public java.util.Date getUpdateDate(){
		return this.updateDate;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  更新日期
	 */
	public void setUpdateDate(java.util.Date updateDate){
		this.updateDate = updateDate;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  所属部门
	 */
	@Column(name ="SYS_ORG_CODE",nullable=true,length=50)
	public String getSysOrgCode(){
		return this.sysOrgCode;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  所属部门
	 */
	public void setSysOrgCode(String sysOrgCode){
		this.sysOrgCode = sysOrgCode;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  所属公司
	 */
	@Column(name ="SYS_COMPANY_CODE",nullable=true,length=50)
	public String getSysCompanyCode(){
		return this.sysCompanyCode;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  所属公司
	 */
	public void setSysCompanyCode(String sysCompanyCode){
		this.sysCompanyCode = sysCompanyCode;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  表名
	 */
	@Column(name ="TABLE_NAME",nullable=true,length=32)
	public String getTableName(){
		return this.tableName;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  表名
	 */
	public void setTableName(String tableName){
		this.tableName = tableName;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  数据ID
	 */
	@Column(name ="DATA_ID",nullable=true,length=32)
	public String getDataId(){
		return this.dataId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  数据ID
	 */
	public void setDataId(String dataId){
		this.dataId = dataId;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  数据内容
	 */
	@Column(name ="DATA_CONTENT",nullable=true,length=32)
	public String getDataContent(){
		return this.dataContent;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  数据内容
	 */
	public void setDataContent(String dataContent){
		this.dataContent = dataContent;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  版本号
	 */
	@Column(name ="VERSION_NUMBER",nullable=true,length=4)
	public Integer getVersionNumber(){
		return this.versionNumber;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  版本号
	 */
	public void setVersionNumber(Integer versionNumber){
		this.versionNumber = versionNumber;
	}
}
