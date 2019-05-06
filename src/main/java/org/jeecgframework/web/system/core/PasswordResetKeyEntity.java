package org.jeecgframework.web.system.core;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 密码重置表
 * @author DELL
 * @version V1.0
 * @date 2019-04-29
 */
@Entity
@Table(name = "t_s_password_resetkey")
public class PasswordResetKeyEntity implements Serializable {

    /**
     * ID
     */
    private String id;

    /**
     * 创建人名称
     */
    private String createName;

    /**
     * 创建人登录名称
     */
    private String createBy;

    /**
     * 创建日期
     */
    private Date createDate;

    /**
     * 用户名
     */
    private String username;

    /**
     * 邮箱地址
     */
    private String email;

    /**
     * 是否已重置
     */
    private Integer isReset;


    @Id
    @GeneratedValue(generator = "paymentableGenerator")
    @GenericGenerator(name = "paymentableGenerator", strategy = "uuid")
    @Column(name = "id", nullable = false, length = 36)
    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Column(name = "create_name", nullable = true, length = 50)
    public String getCreateName() {
        return this.createName;
    }

    public void setCreateName(String createName) {
        this.createName = createName;
    }

    @Column(name = "create_by", nullable = true, length = 50)
    public String getCreateBy() {
        return this.createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    @Column(name = "create_date", nullable = true, length = 20)
    public Date getCreateDate() {
        return this.createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    @Column(name = "username", nullable = true, length = 100)
    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Column(name = "email", nullable = true, length = 100)
    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Column(name = "is_reset", nullable = true, length = 2)
    public Integer getIsReset() {
        return this.isReset;
    }


    public void setIsReset(Integer isReset) {
        this.isReset = isReset;
    }
}
