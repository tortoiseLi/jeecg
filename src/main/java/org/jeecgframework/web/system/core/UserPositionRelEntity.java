package org.jeecgframework.web.system.core;

import org.hibernate.annotations.GenericGenerator;
import org.jeecgframework.poi.excel.annotation.Excel;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 用户公司职务关联表
 * @author DELL
 * @version V1.0
 * @date 2019-04-29
 */
@Entity
@Table(name = "t_s_user_position_rel")
public class UserPositionRelEntity implements Serializable {

    /**
     * ID
     */
    private String id;

    /**
     * 用户ID
     */
    private String userId;

    /**
     * 职务ID
     */
    private String positionId;

    /**
     * 公司ID
     */
    @Excel(name = "公司ID", width = 15)
    private String companyId;

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

    @Column(name = "user_id", nullable = true, length = 32)
    public String getUserId() {
        return this.userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Column(name = "position_id", nullable = true, length = 32)
    public String getPositionId() {
        return this.positionId;
    }

    public void setPositionId(String positionId) {
        this.positionId = positionId;
    }

    @Column(name = "company_id", nullable = true, length = 32)
    public String getCompanyId() {
        return this.companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }
}
