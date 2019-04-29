package org.jeecgframework.web.system.pojo.base;

import org.jeecgframework.core.common.entity.IdEntity;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;


/**
 * 项目附件父表(其他附件表需继承该表)
 * @author DELL
 * @date 2019-04-29
 * @version V1.0
 */
@Entity
@Table(name = "t_s_attachment")
@Inheritance(strategy = InheritanceType.JOINED)
public class AttachmentEntity extends IdEntity implements Serializable {

	/**
	 * 业务类ID
	 */
	private String businessKey;

	/**
	 * 子类名称全路径
	 */
	private String subclassname;

	/**
	 * 附件名称
	 */
	private String attachmenttitle;

	/**
	 * 附件内容
	 */
	private byte[] attachmentcontent;

	/**
	 * 附件物理路径
	 */
	private String realpath;

	/**
	 * 创建日期
	 */
	private Timestamp createdate;

	/**
	 *
	 */
	private String note;

	/**
	 * swf格式路径
	 */
	private String swfpath;

	/**
	 * 扩展名
	 */
	private String extend;

	/**
	 * 创建人
	 */
	private UserEntity TSUser;
	
	@Column(name = "extend", length = 32)
	public String getExtend() {
		return extend;
	}

	public void setExtend(String extend) {
		this.extend = extend;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "userid")
	public UserEntity getTSUser() {
		return this.TSUser;
	}

	public void setTSUser(UserEntity TSUser) {
		this.TSUser = TSUser;
	}

	@Column(name = "businesskey", length = 32)
	public String getBusinessKey() {
		return businessKey;
	}

	public void setBusinessKey(String businessKey) {
		this.businessKey = businessKey;
	}

	@Column(name = "attachmenttitle", length = 100)
	public String getAttachmenttitle() {
		return this.attachmenttitle;
	}

	public void setAttachmenttitle(String attachmenttitle) {
		this.attachmenttitle = attachmenttitle;
	}

	@Column(name = "attachmentcontent",length=3000)
	@Lob
	public byte[] getAttachmentcontent() {
		return this.attachmentcontent;
	}

	public void setAttachmentcontent(byte[] attachmentcontent) {
		this.attachmentcontent = attachmentcontent;
	}

	@Column(name = "realpath", length = 100)
	public String getRealpath() {
		return this.realpath;
	}

	public void setRealpath(String realpath) {
		this.realpath = realpath;
	}

	@Column(name = "createdate", length = 35)
	public Timestamp getCreatedate() {
		return createdate;
	}

	public void setCreatedate(Timestamp createdate) {
		this.createdate = createdate;
	}

	@Column(name = "note", length = 300)
	public String getNote() {
		return this.note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	@Column(name = "swfpath", length = 300)
	public String getSwfpath() {
		return this.swfpath;
	}

	public void setSwfpath(String swfpath) {
		this.swfpath = swfpath;
	}
	@Column(name = "subclassname", length = 300)
	public String getSubclassname() {
		return subclassname;
	}

	public void setSubclassname(String subclassname) {
		this.subclassname = subclassname;
	}

}