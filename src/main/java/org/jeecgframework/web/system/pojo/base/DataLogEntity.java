package org.jeecgframework.web.system.pojo.base;

import org.jeecgframework.core.common.entity.IdEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * 数据日志表
 * @author DELL
 * @date 2019-04-29
 * @version V1.0
 */
@Entity
@Table(name = "t_s_data_log")
public class DataLogEntity extends IdEntity implements Serializable {

	/**
	 * 表名
	 */
	private String tableName;

	/**
	 * 数据ID
	 */
	private String dataId;

	/**
	 * 数据内容
	 */
	private String dataContent;

	/**
	 * 版本号
	 */
	private Integer versionNumber;

	/**
	 * 所属部门
	 */
	private String sysOrgCode;

	/**
	 * 所属公司
	 */
	private String sysCompanyCode;

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

	@Column(name ="table_name",nullable=true,length=32)
	public String getTableName(){
		return this.tableName;
	}

	public void setTableName(String tableName){
		this.tableName = tableName;
	}

	@Column(name ="data_id",nullable=true,length=32)
	public String getDataId(){
		return this.dataId;
	}

	public void setDataId(String dataId){
		this.dataId = dataId;
	}

	@Column(name ="data_content",nullable=true,length=32)
	public String getDataContent(){
		return this.dataContent;
	}

	public void setDataContent(String dataContent){
		this.dataContent = dataContent;
	}

	@Column(name ="version_number",nullable=true,length=4)
	public Integer getVersionNumber(){
		return this.versionNumber;
	}

	public void setVersionNumber(Integer versionNumber){
		this.versionNumber = versionNumber;
	}
}
