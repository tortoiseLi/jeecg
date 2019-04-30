package org.jeecgframework.web.system.log.operate.entity;

import org.jeecgframework.core.common.entity.IdEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * 日志表
 * @author DELL
 * @date 2019-04-29
 * @version V1.0
 */
@Entity
@Table(name = "t_s_log")
public class LogEntity extends IdEntity implements Serializable {

	/**
	 * 用户ID
	 */
	private String userid;

	/**
	 * 用户账号
	 */
	private String username;

	/**
	 * 用户姓名
	 */
	private String realname;

	/**
	 * 日志级别
	 */
	private Short loglevel;

	/**
	 * 操作时间
	 */
	private Date operatetime;

	/**
	 * 操作类型
	 */
	private Short operatetype;

	/**
	 * 操作内容
	 */
	private String logcontent;

	/**
	 * 浏览器类型
	 */
	private String broswer;

	/**
	 * 备注
	 */
	private String note;

	@Column(name = "userid",length = 32)
	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}
 
	@Column(name = "username",length = 10)
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Column(name = "realname",length = 50)
	public String getRealname() {
		return realname;
	}

	public void setRealname(String realname) {
		this.realname = realname;
	}

	@Column(name = "loglevel")
	public Short getLoglevel() {
		return this.loglevel;
	}

	public void setLoglevel(Short loglevel) {
		this.loglevel = loglevel;
	}

	@Column(name = "operatetime", nullable = false, length = 35)
	public Date getOperatetime() {
		return this.operatetime;
	}

	public void setOperatetime(Date operatetime) {
		this.operatetime = operatetime;
	}

	@Column(name = "operatetype")
	public Short getOperatetype() {
		return this.operatetype;
	}

	public void setOperatetype(Short operatetype) {
		this.operatetype = operatetype;
	}

	@Column(name = "logcontent", nullable = false, length = 2000)
	public String getLogcontent() {
		return this.logcontent;
	}

	public void setLogcontent(String logcontent) {
		this.logcontent = logcontent;
	}

	@Column(name = "note", length = 300)
	public String getNote() {
		return this.note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	@Column(name = "broswer", length = 100)
	public String getBroswer() {
		return broswer;
	}

	public void setBroswer(String broswer) {
		this.broswer = broswer;
	}

}