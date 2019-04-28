package org.jeecgframework.web.system.pojo.base;

import org.hibernate.annotations.GenericGenerator;
import org.jeecgframework.poi.excel.annotation.Excel;

import javax.persistence.*;

/**   
 * @Title: Entity
 * @Description: 密码重置
 * @author onlineGenerator
 * @date 2017-09-17 22:18:36
 * @version V1.0   
 *
 */
@Entity
@Table(name = "t_s_password_resetkey", schema = "")
@SuppressWarnings("serial")
public class PasswordResetKeyEntity implements java.io.Serializable {
	/**主键*/
	private String id;
	/**创建人名称*/
	private String createName;
	/**创建人登录名称*/
	private String createBy;
	/**创建日期*/
	private java.util.Date createDate;
	/**用户名*/
	@Excel(name="用户名",width=15)
	private String username;
	/**邮箱地址*/
	@Excel(name="邮箱地址",width=15)
	private String email;
	/**是否已重置*/
	@Excel(name="是否已重置",width=15)
	private Integer isReset;

	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  主键
	 */
	@Id
	@GeneratedValue(generator = "paymentableGenerator")
	@GenericGenerator(name = "paymentableGenerator", strategy = "uuid")

	@Column(name ="ID",nullable=false,length=36)
	public String getId(){
		return this.id;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  主键
	 */
	public void setId(String id){
		this.id = id;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  创建人名称
	 */

	@Column(name ="CREATE_NAME",nullable=true,length=50)
	public String getCreateName(){
		return this.createName;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  创建人名称
	 */
	public void setCreateName(String createName){
		this.createName = createName;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  创建人登录名称
	 */

	@Column(name ="CREATE_BY",nullable=true,length=50)
	public String getCreateBy(){
		return this.createBy;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  创建人登录名称
	 */
	public void setCreateBy(String createBy){
		this.createBy = createBy;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  创建日期
	 */

	@Column(name ="CREATE_DATE",nullable=true,length=20)
	public java.util.Date getCreateDate(){
		return this.createDate;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  创建日期
	 */
	public void setCreateDate(java.util.Date createDate){
		this.createDate = createDate;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  用户名
	 */

	@Column(name ="USERNAME",nullable=true,length=100)
	public String getUsername(){
		return this.username;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  用户名
	 */
	public void setUsername(String username){
		this.username = username;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  邮箱地址
	 */

	@Column(name ="EMAIL",nullable=true,length=100)
	public String getEmail(){
		return this.email;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  邮箱地址
	 */
	public void setEmail(String email){
		this.email = email;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  是否已重置
	 */

	@Column(name ="IS_RESET",nullable=true,length=2)
	public Integer getIsReset(){
		return this.isReset;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  是否已重置
	 */
	public void setIsReset(Integer isReset){
		this.isReset = isReset;
	}
}
