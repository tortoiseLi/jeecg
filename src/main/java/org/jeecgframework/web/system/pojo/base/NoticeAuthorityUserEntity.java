package org.jeecgframework.web.system.pojo.base;

import org.jeecgframework.core.common.entity.IdEntity;
import org.jeecgframework.web.system.user.entity.UserEntity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 通知公告用户授权表
 * @author DELL
 * @version V1.0
 * @date 2019-04-29
 */
@Entity
@Table(name = "t_s_notice_authority_user")
public class NoticeAuthorityUserEntity extends IdEntity implements Serializable {

    /**
     * 通告ID
     */
    private String noticeId;

    /**
     * 用户
     */
    private UserEntity user;

    public void setNoticeId(String noticeId) {
        this.noticeId = noticeId;
    }

    @Column(name = "notice_id", nullable = true)
    public String getNoticeId() {
        return noticeId;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    @ManyToOne
    @JoinColumn(name = "user_id")
    public UserEntity getUser() {
        return user;
    }

}