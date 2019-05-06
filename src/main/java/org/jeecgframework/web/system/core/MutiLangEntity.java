package org.jeecgframework.web.system.core;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 多语言表
 * @author DELL
 * @date 2019-04-29
 * @version V1.0
 */
@Entity
@Table(name = "t_s_muti_lang")
@DynamicUpdate()
@DynamicInsert()
public class MutiLangEntity implements Serializable {

	/**
	 * ID
	 */
	private String id;

	/**
	 * 语言主键
	 */
	private String langKey;

	/**
	 * 内容
	 */
	private String langContext;

	/**
	 * 语言
	 */
	private String langCode;

	/**
	 * 创建时间
	 */
	private Date createDate;

	/**
	 * 创建人编号
	 */
	private String createBy;

	/**
	 * 创建人姓名
	 */
	private String createName;

	/**
	 * 更新日期
	 */
	private Date updateDate;

	/**
	 * 更新人编号
	 */
	private String updateBy;

	/**
	 * 更新人姓名
	 */
	private String updateName;

	@Id
	@GeneratedValue(generator = "paymentableGenerator")
	@GenericGenerator(name = "paymentableGenerator", strategy = "uuid")
	@Column(name ="id",nullable=false,length=32)
	public String getId(){
		return this.id;
	}

	public void setId(String id){
		this.id = id;
	}

	@Column(name ="lang_key",nullable=false,length=50)
	public String getLangKey(){
		return this.langKey;
	}

	public void setLangKey(String langKey){
		this.langKey = langKey;
	}

	@Column(name ="lang_context",nullable=false,length=500)
	public String getLangContext(){
		return this.langContext;
	}

	public void setLangContext(String langContext){
		this.langContext = langContext;
	}

	@Column(name ="lang_code",nullable=false,length=50)
	public String getLangCode(){
		return this.langCode;
	}

	public void setLangCode(String langCode){
		this.langCode = langCode;
	}

	@Column(name ="create_date",nullable=false)
	public Date getCreateDate(){
		return this.createDate;
	}

	public void setCreateDate(Date createDate){
		this.createDate = createDate;
	}

	@Column(name ="create_by",nullable=false,length=50)
	public String getCreateBy(){
		return this.createBy;
	}

	public void setCreateBy(String createBy){
		this.createBy = createBy;
	}
	@Column(name ="create_name",nullable=false,length=50)
	public String getCreateName(){
		return this.createName;
	}

	public void setCreateName(String createName){
		this.createName = createName;
	}

	@Column(name ="update_date",nullable=false)
	public Date getUpdateDate(){
		return this.updateDate;
	}

	public void setUpdateDate(Date updateDate){
		this.updateDate = updateDate;
	}

	@Column(name ="update_by",nullable=false,length=50)
	public String getUpdateBy(){
		return this.updateBy;
	}

	public void setUpdateBy(String updateBy){
		this.updateBy = updateBy;
	}

	@Column(name ="update_name",nullable=false,length=50)
	public String getUpdateName(){
		return this.updateName;
	}

	public void setUpdateName(String updateName){
		this.updateName = updateName;
	}
}
