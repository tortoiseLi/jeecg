package org.jeecgframework.web.system.pojo.base;

import org.jeecgframework.core.common.entity.IdEntity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 菜单权限表
 * @author DELL
 * @date 2019-04-29
 * @version V1.0
 */
@Entity
@Table(name = "t_s_function")
@org.hibernate.annotations.Proxy(lazy = false)
public class FunctionEntity extends IdEntity implements Serializable {

	/**
	 * 菜单名称
	 */
	private String functionName;

	/**
	 * 菜单等级
	 */
	private Short functionLevel;

	/**
	 * 菜单地址
	 */
	private String functionUrl;

	/**
	 * 菜单地址打开方式
	 */
	private Short functionIframe;

	/**
	 * 菜单排序
	 */
	private String functionOrder;

	/**
	 * 菜单类型
	 */
	private Short functionType;

	/**
	 * 菜单图标样式
	 */
	private String functionIconStyle;

	/**
	 * 创建时间
	 */
	private Date createDate;

	/**
	 * 创建人ID
	 */
	private String createBy;

	/**
	 * 创建人名称
	 */
	private String createName;

	/**
	 * 修改时间
	 */
	private Date updateDate;

	/**
	 * 修改人
	 */
	private String updateBy;

	/**
	 * 修改人名称
	 */
	private String updateName;

	/**
	 * 父菜单
	 */
	private FunctionEntity TSFunction;

	/**
	 * 菜单图标
	 */
	private IconEntity TSIcon = new IconEntity();

	/**
	 * 云桌面菜单图标
	 */
	private IconEntity TSIconDesk;

	@Column(name ="create_date",nullable=true)
	public Date getCreateDate(){
		return this.createDate;
	}

	public void setCreateDate(Date createDate){
		this.createDate = createDate;
	}

	@Column(name ="create_by",nullable=true,length=32)
	public String getCreateBy(){
		return this.createBy;
	}

	public void setCreateBy(String createBy){
		this.createBy = createBy;
	}

	@Column(name ="create_name",nullable=true,length=32)
	public String getCreateName(){
		return this.createName;
	}

	public void setCreateName(String createName){
		this.createName = createName;
	}

	@Column(name ="update_date",nullable=true)
	public Date getUpdateDate(){
		return this.updateDate;
	}

	public void setUpdateDate(Date updateDate){
		this.updateDate = updateDate;
	}

	@Column(name ="update_by",nullable=true,length=32)
	public String getUpdateBy(){
		return this.updateBy;
	}

	public void setUpdateBy(String updateBy){
		this.updateBy = updateBy;
	}

	@Column(name ="update_name",nullable=true,length=32)
	public String getUpdateName(){
		return this.updateName;
	}

	public void setUpdateName(String updateName){
		this.updateName = updateName;
	}
	public boolean hasSubFunction(Map<Integer, List<FunctionEntity>> map) {
		if(map.containsKey(this.getFunctionLevel()+1)){
			return hasSubFunction(map.get(this.getFunctionLevel()+1));
		}
		return false;
	}

	public boolean hasSubFunction(List<FunctionEntity> functions) {
		for (FunctionEntity f : functions) {
			if(f!=null && f.getTSFunction()!=null){
				if(f.getTSFunction().getId().equals(this.getId())){
					return true;
				}
			}
			
		}
		return false;
	}

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "desk_iconid")
    public IconEntity getTSIconDesk() {
        return TSIconDesk;
    }
    public void setTSIconDesk(IconEntity TSIconDesk) {
        this.TSIconDesk = TSIconDesk;
    }

	private List<FunctionEntity> TSFunctions = new ArrayList<>();

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "iconid")
	public IconEntity getTSIcon() {
		return TSIcon;
	}
	public void setTSIcon(IconEntity tSIcon) {
		TSIcon = tSIcon;
	}

    @ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "parentfunctionid")
	public FunctionEntity getTSFunction() {
		return this.TSFunction;
	}

	public void setTSFunction(FunctionEntity TSFunction) {
		this.TSFunction = TSFunction;
	}

	@Column(name = "functionname", nullable = false, length = 50)
	public String getFunctionName() {
		return this.functionName;
	}

	public void setFunctionName(String functionName) {
		this.functionName = functionName;
	}

	@Column(name = "functionlevel")
	public Short getFunctionLevel() {
		return this.functionLevel;
	}

	public void setFunctionLevel(Short functionLevel) {
		this.functionLevel = functionLevel;
	}
	
	@Column(name = "functiontype")
	public Short getFunctionType() {
		return this.functionType;
	}

	public void setFunctionType(Short functionType) {
		this.functionType = functionType;
	}
	
	@Column(name = "functionurl", length = 100)
	public String getFunctionUrl() {
		return this.functionUrl;
	}

	public void setFunctionUrl(String functionUrl) {
		this.functionUrl = functionUrl;
	}
	@Column(name = "functionorder")
	public String getFunctionOrder() {
		return functionOrder;
	}

	public void setFunctionOrder(String functionOrder) {
		this.functionOrder = functionOrder;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "TSFunction")
	public List<FunctionEntity> getTSFunctions() {
		return TSFunctions;
	}

	public void setTSFunctions(List<FunctionEntity> TSFunctions) {
		this.TSFunctions = TSFunctions;
	}

	@Column(name = "functioniframe")
	public Short getFunctionIframe() {
		return functionIframe;
	}

	public void setFunctionIframe(Short functionIframe) {
		this.functionIframe = functionIframe;
	}

	@Column(name = "function_icon_style")
	public String getFunctionIconStyle() {
		return functionIconStyle;
	}

	public void setFunctionIconStyle(String functionIconStyle) {
		this.functionIconStyle = functionIconStyle;
	}

}