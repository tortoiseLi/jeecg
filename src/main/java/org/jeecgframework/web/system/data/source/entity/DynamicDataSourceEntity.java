package org.jeecgframework.web.system.data.source.entity;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 数据源配置表
 * @author DELL
 * @date 2019-04-29
 * @version V1.0
 */
@Entity
@Table(name = "t_s_data_source")
@DynamicUpdate()
@DynamicInsert()
public class DynamicDataSourceEntity implements Serializable {
		
	/**
	 * ID
	 */
	private String id;

	/**
	 * 数据源key
	 */
	private String dbKey;

	/**
	 * 描述
	 */
	private String description;

	/**
	 * 驱动类
	 */
	private String driverClass;

	/**
	 * 路径
	 */
	private String url;

	/**
	 * 用户名
	 */
	private String dbUser;

	/**
	 * 密码
	 */
	private String dbPassword;

	/**
	 * 数据库类型
	 */
	private String dbType;

	/**
	 * 数据库名称
	 */
	private String dbName;

	@Id
	@GeneratedValue(generator = "paymentableGenerator")
	@GenericGenerator(name = "paymentableGenerator", strategy = "uuid")
	@Column(name ="id",nullable=false,precision=36,length=36)
	public String getId(){
		return this.id;
	}

	public void setId(String id){
		this.id = id;
	}

	@Column(name ="db_key",nullable=false,precision=50,length=50)
	public String getDbKey(){
		return this.dbKey;
	}

	public void setDbKey(String dbKey){
		this.dbKey = dbKey;
	}

	@Column(name ="description",nullable=false,precision=50,length=50)
	public String getDescription(){
		return this.description;
	}

	public void setDescription(String description){
		this.description = description;
	}

	@Column(name ="driver_class",nullable=false,precision=50,length=50)
	public String getDriverClass(){
		return this.driverClass;
	}

	public void setDriverClass(String driverClass){
		this.driverClass = driverClass;
	}

	@Column(name ="url",nullable=false,precision=100,length=100)
	public String getUrl(){
		return this.url;
	}

	public void setUrl(String url){
		this.url = url;
	}

	@Column(name ="db_user",nullable=false,precision=50,length=50)
	public String getDbUser(){
		return this.dbUser;
	}

	public void setDbUser(String dbUser){
		this.dbUser = dbUser;
	}

	@Column(name ="db_password",nullable=true,precision=50,length=50)
	public String getDbPassword(){
		return this.dbPassword;
	}

	public void setDbPassword(String dbPassword){
		this.dbPassword = dbPassword;
	}

	@Column(name ="db_type",nullable=true,precision=50,length=50)
	public String getDbType(){
		return this.dbType;
	}

	public void setDbType(String dbType){
		this.dbType = dbType;
	}

	@Column(name ="db_name",nullable=true,precision=50,length=50)
	public String getDbName() {
		return dbName;
	}

	public void setDbName(String dbName) {
		this.dbName = dbName;
	}

}
