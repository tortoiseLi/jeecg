package org.jeecgframework.web.system.core;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 定时任务管理表
 * @author DELL
 * @version V1.0
 * @date 2019-04-29
 */
@Entity
@Table(name = "t_s_timetask")
@DynamicUpdate()
@DynamicInsert()
public class TimeTaskEntity implements Serializable {

    /**
     * ID
     */
    private String id;

    /**
     * 任务ID
     */
    private String taskId;

    /**
     * 任务描述
     */
    private String taskDescribe;

    /**
     * CRON表达式
     */
    private String cronExpression;

    /**
     * 是否生效(0:未生效,1:生效)
     */
    private String isEffect;

    /**
     * 是否运行(0停止,1运行)
     */
    private String isStart;

    /**
     * 任务类名
     **/
    private String className;

    /**
     * 运行任务的服务器IP
     **/
    private String runServerIp;

    /**
     * 远程主机(域名/IP+项目路径)
     **/
    private String runServer;

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
     * 修改人ID
     */
    private String updateBy;

    /**
     * 修改人名称
     */
    private String updateName;

    @Column(name = "run_server", nullable = false, length = 300)
    public String getRunServer() {
        return runServer;
    }

    public void setRunServer(String runServer) {
        this.runServer = runServer;
    }

    @Column(name = "run_server_ip", nullable = false, length = 15)
    public String getRunServerIp() {
        return runServerIp;
    }

    public void setRunServerIp(String runServerIp) {
        this.runServerIp = runServerIp;
    }

    @Column(name = "class_name", nullable = false, length = 300)
    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    @Id
    @GeneratedValue(generator = "paymentableGenerator")
    @GenericGenerator(name = "paymentableGenerator", strategy = "uuid")
    @Column(name = "id", nullable = false, length = 32)
    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Column(name = "task_id", nullable = false, length = 100)
    public String getTaskId() {
        return this.taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    @Column(name = "task_describe", nullable = false, length = 50)
    public String getTaskDescribe() {
        return this.taskDescribe;
    }

    public void setTaskDescribe(String taskDescribe) {
        this.taskDescribe = taskDescribe;
    }

    @Column(name = "cron_expression", nullable = false, length = 100)
    public String getCronExpression() {
        return this.cronExpression;
    }

    public void setCronExpression(String cronExpression) {
        this.cronExpression = cronExpression;
    }

    @Column(name = "is_effect", nullable = false, length = 1)
    public String getIsEffect() {
        return this.isEffect;
    }

    public void setIsEffect(String isEffect) {
        this.isEffect = isEffect;
    }

    @Column(name = "is_start", nullable = false, length = 1)
    public String getIsStart() {
        return this.isStart;
    }

    public void setIsStart(String isStart) {
        this.isStart = isStart;
    }

    @Column(name = "create_date", nullable = true)
    public Date getCreateDate() {
        return this.createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    @Column(name = "create_by", nullable = true, length = 32)
    public String getCreateBy() {
        return this.createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    @Column(name = "create_name", nullable = true, length = 32)
    public String getCreateName() {
        return this.createName;
    }

    public void setCreateName(String createName) {
        this.createName = createName;
    }

    @Column(name = "update_date", nullable = true)
    public Date getUpdateDate() {
        return this.updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    @Column(name = "update_by", nullable = true, length = 32)
    public String getUpdateBy() {
        return this.updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    @Column(name = "update_name", nullable = true, length = 32)
    public String getUpdateName() {
        return this.updateName;
    }

    public void setUpdateName(String updateName) {
        this.updateName = updateName;
    }
}
