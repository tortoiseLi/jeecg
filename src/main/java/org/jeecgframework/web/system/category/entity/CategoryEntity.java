package org.jeecgframework.web.system.category.entity;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;
import org.jeecgframework.web.system.pojo.base.IconEntity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 分类管理表
 * @author DELL
 * @date 2019-04-29
 * @version V1.0
 */
@Entity
@Table(name = "t_s_category")
@DynamicUpdate()
@DynamicInsert()
public class CategoryEntity implements Serializable {

	/**
	 * ID
	 */
	private String id;

	/**
	 * 类型名称
	 */
	private String name;

	/**
	 * 类型编码
	 */
	private String code;

	/**
	 * 分类图标
	 */
	private IconEntity icon;

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
	 * 组织
	 */
	private String sysOrgCode;

	/**
	 * 公司
	 */
	private String sysCompanyCode;

	/**
	 * 上级
	 */
	private CategoryEntity parent;

	/**
	 * 父子级List
	 */
	private List<CategoryEntity> list;

	@Id
	@GeneratedValue(generator = "paymentableGenerator")
	@GenericGenerator(name = "paymentableGenerator", strategy = "uuid")
	@Column(name = "ID", nullable = false, length = 36)
	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Column(name = "create_name", nullable = true, length = 50)
	public String getCreateName() {
		return this.createName;
	}

	public void setCreateName(String createName) {
		this.createName = createName;
	}

	@Column(name = "create_by", nullable = true, length = 50)
	public String getCreateBy() {
		return this.createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	@Column(name = "create_date", nullable = true)
	public Date getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	@Column(name = "update_name", nullable = true, length = 50)
	public String getUpdateName() {
		return this.updateName;
	}

	public void setUpdateName(String updateName) {
		this.updateName = updateName;
	}

	@Column(name = "update_by", nullable = true, length = 50)
	public String getUpdateBy() {
		return this.updateBy;
	}

	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}

	@Column(name = "update_date", nullable = true)
	public Date getUpdateDate() {
		return this.updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	@Column(name = "name", nullable = true, length = 32)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "code", nullable = true, length = 32)
	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "parent_code",referencedColumnName = "code")
	public CategoryEntity getParent() {
		return this.parent;
	}

	public void setParent(CategoryEntity parent) {
		this.parent = parent;
	}

	@Column(name = "sys_org_code", nullable = true, length = 15)
	public String getSysOrgCode() {
		return sysOrgCode;
	}

	public void setSysOrgCode(String sysOrgCode) {
		this.sysOrgCode = sysOrgCode;
	}

	@Column(name = "sys_company_code", nullable = true, length = 15)
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
	@JoinColumn(name = "icon_id")
	public IconEntity getIcon() {
		return icon;
	}

	public void setIcon(IconEntity icon) {
		this.icon = icon;
	}
}
