package org.jeecgframework.web.system.pojo.base;

/**
 * 组织机构导入对象
 * @author DELL
 * @date 2019-04-29
 * @version V1.0
 */
public class DepartExcelView implements java.io.Serializable {

	/**
	 * ID
	 */
	private String id;

	/**
	 * 机构名称
	 */
	private String departName;

	/**
	 * 机构描述
	 */
	private String description;

	/**
	 * 父机构ID
	 */
	private String parentId;

	/**
	 * 机构类型
	 */
	private String orgType;

	/**
	 * 电话
	 */
	private String mobile;

	/**
	 * 传真
	 */
	private String fax;

	/**
	 * 地址
	 */
	private String address;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDepartName() {
		return departName;
	}

	public void setDepartName(String departName) {
		this.departName = departName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getOrgType() {
		return orgType;
	}

	public void setOrgType(String orgType) {
		this.orgType = orgType;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
}
