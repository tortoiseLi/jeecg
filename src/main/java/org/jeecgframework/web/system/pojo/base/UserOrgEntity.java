package org.jeecgframework.web.system.pojo.base;

import org.jeecgframework.core.common.entity.IdEntity;
import org.jeecgframework.web.system.depart.entity.DepartEntity;
import org.jeecgframework.web.system.user.entity.UserEntity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 用户组织机构表
 * @author DELL
 * @version V1.0
 * @date 2019-04-29
 */
@Entity
@Table(name = "t_s_user_org")
public class UserOrgEntity extends IdEntity implements Serializable {

    /**
     * 用户
     */
    private UserEntity tsUser;

    /**
     * 组织机构
     */
    private DepartEntity tsDepart;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    public UserEntity getTsUser() {
        return tsUser;
    }

    public void setTsUser(UserEntity tsDepart) {
        this.tsUser = tsDepart;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "org_id")
    public DepartEntity getTsDepart() {
        return tsDepart;
    }

    public void setTsDepart(DepartEntity tsDepart) {
        this.tsDepart = tsDepart;
    }
}
