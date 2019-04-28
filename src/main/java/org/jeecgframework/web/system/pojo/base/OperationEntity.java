package org.jeecgframework.web.system.pojo.base;

import org.jeecgframework.core.common.entity.IdEntity;

import javax.persistence.*;
import javax.swing.*;

/**
 * 权限操作表
 *  @author  张代浩
 */
@Entity
@Table(name = "t_s_operation")
public class OperationEntity extends IdEntity implements java.io.Serializable {
	private String operationname;
	private String operationcode;
	private String operationicon;
	private Short status;
	private IconEntity TSIcon = new IconEntity();
	private FunctionEntity TSFunction = new FunctionEntity();
	private String processnodeId;//流程节点id
	
	private Short operationType;//0隐藏 1禁用
	
	@Column(name = "operationtype")
	public Short getOperationType() {
		return operationType;
	}

	public void setOperationType(Short operationType) {
		this.operationType = operationType;
	}

	@Column(name = "operationname", length = 50)
	public String getOperationname() {
		return this.operationname;
	}

	public void setOperationname(String operationname) {
		this.operationname = operationname;
	}

	@Column(name = "operationcode", length = 50)
	public String getOperationcode() {
		return this.operationcode;
	}

	public void setOperationcode(String operationcode) {
		this.operationcode = operationcode;
	}

	@Column(name = "operationicon", length = 100)
	public String getOperationicon() {
		return this.operationicon;
	}

	public void setOperationicon(String operationicon) {
		this.operationicon = operationicon;
	}

	@Column(name = "status")
	public Short getStatus() {
		return this.status;
	}

	public void setStatus(Short status) {
		this.status = status;
	}
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "iconid")
	public IconEntity getTSIcon() {
		return TSIcon;
	}

	public void setTSIcon(IconEntity tSIcon) {
		TSIcon = tSIcon;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "functionid")
	public FunctionEntity getTSFunction() {
		return TSFunction;
	}

	public void setTSFunction(FunctionEntity tSFunction) {
		TSFunction = tSFunction;
	}
	
	@Column(name ="PROCESSNODE_ID",nullable=true,length=32)
	public String getProcessnodeId() {
		return processnodeId;
	}

	public void setProcessnodeId(String processnodeId) {
		this.processnodeId = processnodeId;
	}

	@Override
    public boolean equals(Object obj) {  
        if(this == obj)  
            return false;  
        if(obj == null)  
            return false;  
        if(getClass() != obj.getClass() )  
            return false;  
        OperationEntity other = (OperationEntity)obj;
        if (getId().equals(other.getId())){
        	return true; 
        }else {
        	return false;  
        }
    } 

	@Override
	public int hashCode(){
		String in = super.getId() + operationname;
		return in.hashCode();
	}

}