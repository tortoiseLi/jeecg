package org.jeecgframework.web.system.pojo.base;

import org.jeecgframework.core.common.entity.IdEntity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 权限接口表
 * @author DELL
 * @date 2019-04-29
 * @version V1.0
 */
@Entity
@Table(name = "t_s_interface")
@org.hibernate.annotations.Proxy(lazy = false)
public class InterfaceEntity extends IdEntity implements Serializable {

	/**
	 * 权限编码
	 */
	private String interfaceCode;

	/**
	 * 权限名称
	 */
	private String interfaceName;

	/**
	 * 排序
	 */
	private String interfaceOrder;

	/**
	 * URL
	 */
	private String interfaceUrl;

	/**
	 * 请求方式
	 */
	private String interfaceMethod;

	/**
	 * 权限等级
	 */
	private Short interfaceLevel;

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
	 * 部门
	 */
	private String sysOrgCode;

	/**
	 * 公司
	 */
	private String sysCompanyCode;

	/**
	 * 父级权限
	 */
	private  InterfaceEntity tSInterface;

	/**
	 * 子权限
	 */
	private List<InterfaceEntity> tSInterfaces = new ArrayList<>();
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "tSInterface")
	public List<InterfaceEntity> getTSInterfaces() {
		return tSInterfaces;
	}

	public void setTSInterfaces(List<InterfaceEntity> tSInterfaces) {
		this.tSInterfaces = tSInterfaces;
	}

	@Column(name = "interface_level")
	public Short getInterfaceLevel() {
		return this.interfaceLevel;
	}

	public void setInterfaceLevel(Short interfaceLevel) {
		this.interfaceLevel = interfaceLevel;
	}
	
	public boolean hasSubInterface(Map<Integer, List<InterfaceEntity>> map) {
		if(map.containsKey(this.getInterfaceLevel()+1)){
			return hasSubInterface(map.get(this.getInterfaceLevel()+1));
		}
		return false;
	}

	public boolean hasSubInterface(List<InterfaceEntity> tsInterface) {
		for (InterfaceEntity f : tsInterface) {
			if(f.gettSInterface()!=null){
				if(f.gettSInterface().getId().equals(this.getId())){
					return true;
				}
			}
			
		}
		return false;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "parent_interface_id")
	public InterfaceEntity gettSInterface() {
		return this.tSInterface;
	}

	public void settSInterface(InterfaceEntity tSInterface) {
		this.tSInterface = tSInterface;
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

	@Column(name ="interface_name",nullable=false,length=50)
	public String getInterfaceName(){
		return this.interfaceName;
	}

	public void setInterfaceName(String interfaceName){
		this.interfaceName = interfaceName;
	}

	@Column(name ="interface_order",nullable=true,length=255)
	public String getInterfaceOrder(){
		return this.interfaceOrder;
	}

	public void setInterfaceOrder(String interfaceOrder){
		this.interfaceOrder = interfaceOrder;
	}

	@Column(name ="interface_url",nullable=true,length=500)
	public String getInterfaceUrl(){
		if(this.getTSInterfaces() != null && this.getTSInterfaces().size() > 0){
			return "";
		}
		return this.interfaceUrl;
	}

	public void setInterfaceUrl(String interfaceUrl){
		this.interfaceUrl = interfaceUrl;
	}

	@Column(name ="sys_org_code",nullable=false,length=50)
	public String getSysOrgCode() {
		return sysOrgCode;
	}

	public void setSysOrgCode(String sysOrgCode) {
		this.sysOrgCode = sysOrgCode;
	}
	@Column(name ="sys_company_code",nullable=false,length=50)
	public String getSysCompanyCode() {
		return sysCompanyCode;
	}

	public void setSysCompanyCode(String sysCompanyCode) {
		this.sysCompanyCode = sysCompanyCode;
	}

	@Column(name ="interface_code",nullable=false)
	public String getInterfaceCode() {
		return interfaceCode;
	}

	public void setInterfaceCode(String interfaceCode) {
		this.interfaceCode = interfaceCode;
	}

	@Column(name ="interface_method",nullable=false)
	public String getInterfaceMethod() {
		return interfaceMethod;
	}

	public void setInterfaceMethod(String interfaceMethod) {
		this.interfaceMethod = interfaceMethod;
	}

}
