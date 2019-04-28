package org.jeecgframework.web.system.pojo.base;

import org.jeecgframework.core.common.entity.IdEntity;

import javax.persistence.*;

/**
 * 用户-组织机构 实体
 * <p/>
 * <p><b>User:</b> zhanggm <a href="mailto:guomingzhang2008@gmail.com">guomingzhang2008@gmail.com</a></p>
 * <p><b>Date:</b> 2014-08-22 15:39</p>
 *
 * @author 张国明
 */
@Entity
@Table(name = "t_s_user_org")
public class UserOrgEntity extends IdEntity implements java.io.Serializable {
    private UserEntity tsUser;
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
