package org.jeecgframework.web.system.black.entity;

import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.hibernate.annotations.GenericGenerator;
import org.jeecgframework.core.common.controller.CustomJsonDateDeserializer;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 黑名单管理
 * @author DELL
 * @date 2019-04-29
 * @version V1.0
 */
@Entity
@Table(name = "t_s_black_list", schema = "")
@SuppressWarnings("serial")
public class BlackListEntity implements Serializable {

	/**
	 * 主键
	 */
	private String id;

	/**
	 * IP地址
	 */
	private String ip;

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
	 * 流程状态
	 */
	private String bpmStatus;

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

	@Column(name ="CREATE_NAME",nullable=true,length=50)
	public String getCreateName(){
		return this.createName;
	}

	public void setCreateName(String createName){
		this.createName = createName;
	}

	@Column(name ="CREATE_BY",nullable=true,length=50)
	public String getCreateBy(){
		return this.createBy;
	}

	public void setCreateBy(String createBy){
		this.createBy = createBy;
	}

	@Column(name ="CREATE_DATE",nullable=true,length=20)
	public java.util.Date getCreateDate(){
		return this.createDate;
	}

	@JsonDeserialize(using = CustomJsonDateDeserializer.class)
	public void setCreateDate(java.util.Date createDate){
		this.createDate = createDate;
	}

	@Column(name ="UPDATE_NAME",nullable=true,length=50)
	public String getUpdateName(){
		return this.updateName;
	}

	public void setUpdateName(String updateName){
		this.updateName = updateName;
	}

	@Column(name ="UPDATE_BY",nullable=true,length=50)
	public String getUpdateBy(){
		return this.updateBy;
	}

	public void setUpdateBy(String updateBy){
		this.updateBy = updateBy;
	}

	@Column(name ="UPDATE_DATE",nullable=true,length=20)
	@JsonDeserialize(using = CustomJsonDateDeserializer.class)
	public java.util.Date getUpdateDate(){
		return this.updateDate;
	}

	public void setUpdateDate(java.util.Date updateDate){
		this.updateDate = updateDate;
	}

	@Column(name ="SYS_ORG_CODE",nullable=true,length=50)
	public String getSysOrgCode(){
		return this.sysOrgCode;
	}

	public void setSysOrgCode(String sysOrgCode){
		this.sysOrgCode = sysOrgCode;
	}

	@Column(name ="SYS_COMPANY_CODE",nullable=true,length=50)
	public String getSysCompanyCode(){
		return this.sysCompanyCode;
	}

	public void setSysCompanyCode(String sysCompanyCode){
		this.sysCompanyCode = sysCompanyCode;
	}

	@Column(name ="BPM_STATUS",nullable=true,length=32)
	public String getBpmStatus(){
		return this.bpmStatus;
	}

	public void setBpmStatus(String bpmStatus){
		this.bpmStatus = bpmStatus;
	}

	@Column(name ="IP",nullable=true,length=32)
	public String getIp(){
		return this.ip;
	}

	public void setIp(String ip){
		this.ip = ip;
	}
}
