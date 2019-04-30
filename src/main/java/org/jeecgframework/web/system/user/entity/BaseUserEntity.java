package org.jeecgframework.web.system.user.entity;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.jeecgframework.core.common.entity.IdEntity;
import org.jeecgframework.web.system.depart.entity.DepartEntity;
import org.jeecgframework.web.system.pojo.base.UserOrgEntity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 系统用户父类表
 * @author DELL
 * @date 2019-04-29
 * @version V1.0
 */
@Entity
@Table(name = "t_s_base_user")
@Inheritance(strategy = InheritanceType.JOINED)
public class BaseUserEntity extends IdEntity implements Serializable {

	/**
	 * 用户名
	 */
	private String userName;

	/**
	 * 真实姓名
	 */
	private String realName;

	/**
	 * 用户使用浏览器类型
	 */
	private String browser;

	/**
	 * 角色编码,多个角色编码用逗号分隔
	 */
	private String userKey;

	/**
	 * 用户密码
	 */
	private String password;

	/**
	 * 是否同步工作流引擎
	 */
	private Short activitiSync;

	/**
	 * 用户状态(0:禁用,1:在线,2:离线)
	 */
	private Short status;

	/**
	 * 状态(0:未删除,1:已删除)
	 */
	private Short deleteFlag;

	/**
	 * 签名文件
	 */
	private byte[] signature;

	/**
	 * 英文名
	 */
	private String userNameEn;

	/**
	 * 组织机构编码,多个组织机构编码用逗号分隔
	 */
	private String departid;

	/**
	 * 用户所属组织机构
	 */
	private List<UserOrgEntity> userOrgList = new ArrayList<>();

	/**
	 * 用户当前部门
	 */
	private DepartEntity currentDepart = new DepartEntity();

	@Column(name = "departid",length=32)
	public String getDepartid(){
		return departid;
	}

	public void setDepartid(String departid){
		this.departid = departid;
	}

	@Column(name = "signature",length=3000)
	public byte[] getSignature() {
		return signature;
	}

	public void setSignature(byte[] signature) {
		this.signature = signature;
	}

	@Column(name = "browser", length = 20)
	public String getBrowser() {
		return browser;
	}

	public void setBrowser(String browser) {
		this.browser = browser;
	}

	@Column(name = "userkey", length = 200)
	public String getUserKey() {
		return userKey;
	}

	public void setUserKey(String userKey) {
		this.userKey = userKey;
	}

	@Column(name = "status")
	public Short getStatus() {
		return this.status;
	}

	public void setStatus(Short status) {
		this.status = status;
	}

	public Short getActivitiSync() {
		return activitiSync;
	}

	@Column(name = "activitisync")
	public void setActivitiSync(Short activitiSync) {
		this.activitiSync = activitiSync;
	}

	@Column(name = "password", length = 100)
	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Column(name = "username", nullable = false)
	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	@Column(name = "realname", length = 50)
	public String getRealName() {
		return this.realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

    @Transient
    public DepartEntity getCurrentDepart() {
        return currentDepart;
    }

    public void setCurrentDepart(DepartEntity currentDepart) {
        this.currentDepart = currentDepart;
    }

    @JsonIgnore
    @OneToMany(mappedBy = "tsUser")
    public List<UserOrgEntity> getUserOrgList() {
        return userOrgList;
    }

    public void setUserOrgList(List<UserOrgEntity> userOrgList) {
        this.userOrgList = userOrgList;
    }

	public void setDeleteFlag(Short deleteFlag) {
		this.deleteFlag = deleteFlag;
	}
	
	@Column(name = "delete_flag")
	public Short getDeleteFlag() {
		return deleteFlag;
	}

	
	@Column(name = "user_name_en")
	public String getUserNameEn() {
		return userNameEn;
	}
	
	public void setUserNameEn(String userNameEn) {
		this.userNameEn = userNameEn;
	}

}