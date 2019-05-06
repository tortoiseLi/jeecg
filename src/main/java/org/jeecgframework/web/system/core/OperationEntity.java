package org.jeecgframework.web.system.core;

import org.jeecgframework.core.common.entity.IdEntity;
import org.jeecgframework.web.system.function.entity.FunctionEntity;
import org.jeecgframework.web.system.icon.entity.IconEntity;

import javax.persistence.*;

/**
 * 权限操作表
 * @author DELL
 * @version V1.0
 * @date 2019-04-29
 */
@Entity
@Table(name = "t_s_operation")
public class OperationEntity extends IdEntity implements java.io.Serializable {

    /**
     * 操作名称
     */
    private String operationname;

    /**
     * 操作编码
     */
    private String operationcode;

    /**
     * 操作图标
     */
    private String operationicon;

    /**
     * 状态
     */
    private Short status;

    /**
     * 图标
     */
    private IconEntity TSIcon = new IconEntity();

    /**
     * 菜单
     */
    private FunctionEntity TSFunction = new FunctionEntity();

    /**
     * 流程节点ID
     */
    private String processnodeId;

    /**
     * 操作类型(0隐藏,1禁用)
     */
    private Short operationType;

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

    @Column(name = "processnode_id", nullable = true, length = 32)
    public String getProcessnodeId() {
        return processnodeId;
    }

    public void setProcessnodeId(String processnodeId) {
        this.processnodeId = processnodeId;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return false;
        }

        if (obj == null) {
            return false;
        }

        if (getClass() != obj.getClass()) {
            return false;
        }

        OperationEntity other = (OperationEntity) obj;
        if (getId().equals(other.getId())) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        String in = super.getId() + operationname;
        return in.hashCode();
    }

}