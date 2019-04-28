package org.jeecgframework.web.system.pojo.base;

import org.jeecgframework.core.common.entity.IdEntity;
import org.jeecgframework.poi.excel.annotation.Excel;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**   
 * @Title: Entity
 * @Description: 权限接口
 * @author onlineGenerator
 * @date 2017-11-26 11:28:16
 * @version V1.0   
 *
 */
@Entity
@Table(name = "t_s_interface", schema = "")
@org.hibernate.annotations.Proxy(lazy = false)
@SuppressWarnings("serial")
public class InterfaceEntity extends IdEntity implements java.io.Serializable {
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
	/**权限编码*/
	@Excel(name="权限编码",width=15)
	private String interfaceCode;
	/**权限名称*/
	@Excel(name="权限名称",width=15)
	private String interfaceName;
	/**排序*/
	@Excel(name="排序",width=15)
	private String interfaceOrder;
	/**URL*/
	@Excel(name="URL",width=15)
	private String interfaceUrl;
	/**请求方式*/
	@Excel(name="请求方式",width=15)
	private String interfaceMethod;
	/**权限等级*/
	private Short interfaceLevel;
	/**部门*/
	private String sysOrgCode;
	/**公司*/
	private String sysCompanyCode;


	/**父级权限*/
	private InterfaceEntity tSInterface;
	private List<InterfaceEntity> tSInterfaces = new ArrayList<InterfaceEntity>();

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

	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  创建人名称
	 */

	@Column(name ="create_name",nullable=true,length=50)
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

	@Column(name ="create_by",nullable=true,length=50)
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

	@Column(name ="create_date",nullable=true,length=20)
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

	@Column(name ="update_name",nullable=true,length=50)
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

	@Column(name ="update_by",nullable=true,length=50)
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

	@Column(name ="update_date",nullable=true,length=20)
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
	 *@return: java.lang.String  权限名称
	 */

	@Column(name ="interface_name",nullable=false,length=50)
	public String getInterfaceName(){
		return this.interfaceName;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  权限名称
	 */
	public void setInterfaceName(String interfaceName){
		this.interfaceName = interfaceName;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  排序
	 */

	@Column(name ="interface_order",nullable=true,length=255)
	public String getInterfaceOrder(){
		return this.interfaceOrder;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  排序
	 */
	public void setInterfaceOrder(String interfaceOrder){
		this.interfaceOrder = interfaceOrder;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  URL
	 */

	@Column(name ="interface_url",nullable=true,length=500)
	public String getInterfaceUrl(){
		if(this.getTSInterfaces() != null && this.getTSInterfaces().size() > 0){
			return "";
		}
		return this.interfaceUrl;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  URL
	 */
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

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		/*builder.append("TSInterfaceEntity [createName=");
		builder.append(createName);
		builder.append(", createBy=");
		builder.append(createBy);
		builder.append(", createDate=");
		builder.append(createDate);
		builder.append(", updateName=");
		builder.append(updateName);
		builder.append(", updateBy=");
		builder.append(updateBy);
		builder.append(", updateDate=");
		builder.append(updateDate);
		builder.append(", interfaceCode=");
		builder.append(interfaceCode);
		builder.append(", interfaceName=");
		builder.append(interfaceName);
		builder.append(", interfaceOrder=");
		builder.append(interfaceOrder);
		builder.append(", interfaceUrl=");
		builder.append(interfaceUrl);
		builder.append(", interfaceMethod=");
		builder.append(interfaceMethod);
		builder.append(", interfaceLevel=");
		builder.append(interfaceLevel);
		builder.append(", sysOrgCode=");
		builder.append(sysOrgCode);
		builder.append(", sysCompanyCode=");
		builder.append(sysCompanyCode);
		builder.append(", tSInterface=");
		builder.append(tSInterface);
		builder.append(", tSInterfaces=");
		builder.append(tSInterfaces);
		builder.append("]");*/
		return builder.toString();
	}
 
	
}
