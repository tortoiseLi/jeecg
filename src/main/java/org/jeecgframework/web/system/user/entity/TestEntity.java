package org.jeecgframework.web.system.user.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 系统用户表
 * @author DELL
 * @version V1.0
 * @date 2019-04-29
 */
@Entity
@Table(name = "test")
public class TestEntity implements Serializable {

    private String id;
    private String strStr1;
    private String strStr2;
    private String strStr3;
    private String strStr4;
    private String strStr5;

    @Id
    @GeneratedValue(generator = "paymentableGenerator")
    @GenericGenerator(name = "paymentableGenerator", strategy = "uuid")
    @Column(name ="id",nullable=false,length=32)
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Column(name = "str_str1")
    public String getStrStr1() {
        return strStr1;
    }

    public void setStrStr1(String strStr1) {
        this.strStr1 = strStr1;
    }

    @Column(name = "str_str2")
    public String getStrStr2() {
        return strStr2;
    }

    public void setStrStr2(String strStr2) {
        this.strStr2 = strStr2;
    }

    @Column(name = "str_str3")
    public String getStrStr3() {
        return strStr3;
    }

    public void setStrStr3(String strStr3) {
        this.strStr3 = strStr3;
    }

    @Column(name = "str_str4")
    public String getStrStr4() {
        return strStr4;
    }

    public void setStrStr4(String strStr4) {
        this.strStr4 = strStr4;
    }

    @Column(name = "str_str5")
    public String getStrStr5() {
        return strStr5;
    }

    public void setStrStr5(String strStr5) {
        this.strStr5 = strStr5;
    }
}