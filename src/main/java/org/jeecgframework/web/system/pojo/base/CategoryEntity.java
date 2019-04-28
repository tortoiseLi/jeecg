package org.jeecgframework.web.system.pojo.base;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;

/**
 * @Title: Entity
 * @Description: 分类管理
 * @author JueYue
 * @date 2014-09-16 21:50:55
 * @version V1.0
 * 
 */
@Entity
@Table(name = "t_s_category", schema = "")
@DynamicUpdate(true)
@DynamicInsert(true)
public class CategoryEntity implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	/** id */
	private String id;
	/** 类型名称 */
	private String name;
	/** 类型编码 */
	private String code;
	/** 分类图标 */
	private IconEntity icon;
	/** 创建人名称 */
	private String createName;
	/** 创建人登录名称 */
	private String createBy;
	/** 创建日期 */
	private java.util.Date createDate;
	/** 更新人名称 */
	private String updateName;
	/** 更新人登录名称 */
	private String updateBy;
	/** 更新日期 */
	private java.util.Date updateDate;
	/** 组织 */
	private String sysOrgCode;
	/** 公司 */
	private String sysCompanyCode;
	/** 上级 */
	private CategoryEntity parent;

	private List<CategoryEntity> list;

	/**
	 * 方法: 取得java.lang.String
	 *
	 * @return: java.lang.String id
	 */

	@Id
	@GeneratedValue(generator = "paymentableGenerator")
	@GenericGenerator(name = "paymentableGenerator", strategy = "uuid")
	@Column(name = "ID", nullable = false, length = 36)
	public String getId() {
		return this.id;
	}

	/**
	 * 方法: 设置java.lang.String
	 *
	 * @param: java.lang.String id
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * 方法: 取得java.lang.String
	 *
	 * @return: java.lang.String 创建人名称
	 */
	@Column(name = "CREATE_NAME", nullable = true, length = 50)
	public String getCreateName() {
		return this.createName;
	}

	/**
	 * 方法: 设置java.lang.String
	 *
	 * @param: java.lang.String 创建人名称
	 */
	public void setCreateName(String createName) {
		this.createName = createName;
	}

	/**
	 * 方法: 取得java.lang.String
	 *
	 * @return: java.lang.String 创建人登录名称
	 */
	@Column(name = "CREATE_BY", nullable = true, length = 50)
	public String getCreateBy() {
		return this.createBy;
	}

	/**
	 * 方法: 设置java.lang.String
	 *
	 * @param: java.lang.String 创建人登录名称
	 */
	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	/**
	 * 方法: 取得java.util.Date
	 *
	 * @return: java.util.Date 创建日期
	 */
	@Column(name = "CREATE_DATE", nullable = true)
	public java.util.Date getCreateDate() {
		return this.createDate;
	}

	/**
	 * 方法: 设置java.util.Date
	 *
	 * @param: java.util.Date 创建日期
	 */
	public void setCreateDate(java.util.Date createDate) {
		this.createDate = createDate;
	}

	/**
	 * 方法: 取得java.lang.String
	 *
	 * @return: java.lang.String 更新人名称
	 */
	@Column(name = "UPDATE_NAME", nullable = true, length = 50)
	public String getUpdateName() {
		return this.updateName;
	}

	/**
	 * 方法: 设置java.lang.String
	 *
	 * @param: java.lang.String 更新人名称
	 */
	public void setUpdateName(String updateName) {
		this.updateName = updateName;
	}

	/**
	 * 方法: 取得java.lang.String
	 *
	 * @return: java.lang.String 更新人登录名称
	 */
	@Column(name = "UPDATE_BY", nullable = true, length = 50)
	public String getUpdateBy() {
		return this.updateBy;
	}

	/**
	 * 方法: 设置java.lang.String
	 *
	 * @param: java.lang.String 更新人登录名称
	 */
	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}

	/**
	 * 方法: 取得java.util.Date
	 *
	 * @return: java.util.Date 更新日期
	 */
	@Column(name = "UPDATE_DATE", nullable = true)
	public java.util.Date getUpdateDate() {
		return this.updateDate;
	}

	/**
	 * 方法: 设置java.util.Date
	 *
	 * @param: java.util.Date 更新日期
	 */
	public void setUpdateDate(java.util.Date updateDate) {
		this.updateDate = updateDate;
	}

	/**
	 * 方法: 取得java.lang.String
	 *
	 * @return: java.lang.String 类型名称
	 */
	@Column(name = "NAME", nullable = true, length = 32)
	public String getName() {
		return this.name;
	}

	/**
	 * 方法: 设置java.lang.String
	 *
	 * @param: java.lang.String 类型名称
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 方法: 取得java.lang.String
	 *
	 * @return: java.lang.String 类型编码
	 */
	@Column(name = "CODE", nullable = true, length = 32)
	public String getCode() {
		return this.code;
	}

	/**
	 * 方法: 设置java.lang.String
	 *
	 * @param: java.lang.String 类型编码
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * 方法: 取得TSCategoryEntity
	 *
	 * @return: TSCategoryEntity 上级code
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PARENT_CODE",referencedColumnName = "code")
	public CategoryEntity getParent() {
		return this.parent;
	}

	/**
	 * 方法: 设置TSCategoryEntity
	 *
	 * @param: TSCategoryEntity 上级
	 */
	public void setParent(CategoryEntity parent) {
		this.parent = parent;
	}

	@Column(name = "SYS_ORG_CODE", nullable = true, length = 15)
	public String getSysOrgCode() {
		return sysOrgCode;
	}

	public void setSysOrgCode(String sysOrgCode) {
		this.sysOrgCode = sysOrgCode;
	}

	@Column(name = "SYS_COMPANY_CODE", nullable = true, length = 15)
	public String getSysCompanyCode() {
		return sysCompanyCode;
	}

	public void setSysCompanyCode(String sysCompanyCode) {
		this.sysCompanyCode = sysCompanyCode;
	}

	@OneToMany(cascade = CascadeType.REMOVE, mappedBy = "parent")
	public List<CategoryEntity> getList() {
		return list;
	}

	public void setList(List<CategoryEntity> list) {
		this.list = list;
	}

	@ManyToOne()
	@JoinColumn(name = "ICON_ID")
	public IconEntity getIcon() {
		return icon;
	}

	public void setIcon(IconEntity icon) {
		this.icon = icon;
	}
}
