package org.jeecgframework.web.system.pojo.base;

import org.jeecgframework.core.common.entity.IdEntity;
import org.jeecgframework.web.system.role.entity.RoleEntity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 通知公告角色授权表
 * @author DELL
 * @version V1.0
 * @date 2019-04-29
 */
@Entity
@Table(name = "t_s_notice_authority_role")
public class NoticeAuthorityRoleEntity extends IdEntity implements Serializable {

    /**
     * 通告ID
     */
    private String noticeId;

    /**
     * 角色
     */
    private RoleEntity role;

    public void setNoticeId(String noticeId) {
        this.noticeId = noticeId;
    }

    @Column(name = "notice_id", nullable = true)
    public String getNoticeId() {
        return noticeId;
    }

    public void setRole(RoleEntity role) {
        this.role = role;
    }

    @ManyToOne
    @JoinColumn(name = "role_id")
    public RoleEntity getRole() {
        return role;
    }

}