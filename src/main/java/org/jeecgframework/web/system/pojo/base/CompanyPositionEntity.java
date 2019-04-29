package org.jeecgframework.web.system.pojo.base;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 职务管理表
 * @author DELL
 * @date 2019-04-29
 * @version V1.0
 */
@Entity
@Table(name = "t_s_company_position")
public class CompanyPositionEntity implements Serializable {

	/**
	 * ID
	 */
	private String id;

	/**
	 * 公司ID
	 */
	private String companyId;

	/**
	 * 职务编码
	 */
	private String positionCode;

	/**
	 * 职务名称
	 */
	private String positionName;

	/**
	 * 职务英文名
	 */
	private String positionNameEn;

	/**
	 * 职务缩写
	 */
	private String positionNameAbbr;

	/**
	 * 职务级别
	 */
	private String positionLevel;

	/**
	 * 备注
	 */
	private String memo;

	/**
	 * 删除状态
	 */
	private Integer delFlag;

	/**
	 * 创建人名称
	 */
	private String createName;

	/**
	 * 创建人账号
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
	 * 更新人账号
	 */
	private String updateBy;

	/**
	 * 更新日期
	 */
	private Date updateDate;

	/**
	 * 数据所属公司
	 */
	private String sysCompanyCode;

	/**
	 * 数据所属部门
	 */
	private String sysOrgCode;
	
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

	@Column(name ="company_id",nullable=true,length=36)
	public String getCompanyId(){
		return this.companyId;
	}

	public void setCompanyId(String companyId){
		this.companyId = companyId;
	}

	@Column(name ="position_code",nullable=true,length=64)
	public String getPositionCode(){
		return this.positionCode;
	}

	public void setPositionCode(String positionCode){
		this.positionCode = positionCode;
	}

	@Column(name ="position_name",nullable=true,length=100)
	public String getPositionName(){
		return this.positionName;
	}

	public void setPositionName(String positionName){
		this.positionName = positionName;
	}

	@Column(name ="position_name_en",nullable=true,length=255)
	public String getPositionNameEn(){
		return this.positionNameEn;
	}

	public void setPositionNameEn(String positionNameEn){
		this.positionNameEn = positionNameEn;
	}

	@Column(name ="position_name_abbr",nullable=true,length=255)
	public String getPositionNameAbbr(){
		return this.positionNameAbbr;
	}

	public void setPositionNameAbbr(String positionNameAbbr){
		this.positionNameAbbr = positionNameAbbr;
	}

	@Column(name ="position_level",nullable=true,length=50)
	public String getPositionLevel(){
		return this.positionLevel;
	}

	public void setPositionLevel(String positionLevel){
		this.positionLevel = positionLevel;
	}

	@Column(name ="memo",nullable=true,length=500)
	public String getMemo(){
		return this.memo;
	}

	public void setMemo(String memo){
		this.memo = memo;
	}

	@Column(name ="del_flag",nullable=true,length=10)
	public Integer getDelFlag(){
		return this.delFlag;
	}

	public void setDelFlag(Integer delFlag){
		this.delFlag = delFlag;
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

	@Column(name ="sys_company_code",nullable=true,length=50)
	public String getSysCompanyCode(){
		return this.sysCompanyCode;
	}

	public void setSysCompanyCode(String sysCompanyCode){
		this.sysCompanyCode = sysCompanyCode;
	}

	@Column(name ="sys_org_code",nullable=true,length=50)
	public String getSysOrgCode(){
		return this.sysOrgCode;
	}

	public void setSysOrgCode(String sysOrgCode){
		this.sysOrgCode = sysOrgCode;
	}
}
