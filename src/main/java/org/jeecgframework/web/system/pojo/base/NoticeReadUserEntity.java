package org.jeecgframework.web.system.pojo.base;

import org.jeecgframework.core.common.entity.IdEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * 通知公告已读用户表
 * @author DELL
 * @version V1.0
 * @date 2019-04-29
 */
@Entity
@Table(name = "t_s_notice_read_user")
public class NoticeReadUserEntity extends IdEntity implements Serializable {

    /**
     * 通告ID
     */
    private String noticeId;

    /**
     * 用户ID
     */
    private String userId;

    /**
     * 是否已经阅读,默认值未阅读
     */
    private Integer isRead = 0;

    /**
     * 是否删除,默认值未删除
     */
    private Integer delFlag = 0;

    /**
     * 创建时间
     */
    private Date createTime;

    public void setNoticeId(String noticeId) {
        this.noticeId = noticeId;
    }

    @Column(name = "notice_id", nullable = true)
    public String getNoticeId() {
        return noticeId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Column(name = "user_id", nullable = true)
    public String getUserId() {
        return userId;
    }

    @Column(name = "create_time", nullable = true)
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Column(name = "is_read", nullable = false)
    public Integer getIsRead() {
        return isRead;
    }

    public void setIsRead(Integer isRead) {
        this.isRead = isRead;
    }

    @Column(name = "del_flag", nullable = false)
    public Integer getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(Integer delFlag) {
        this.delFlag = delFlag;
    }
}