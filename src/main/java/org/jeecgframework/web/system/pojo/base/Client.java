package org.jeecgframework.web.system.pojo.base;

import org.jeecgframework.web.system.function.entity.FunctionEntity;
import org.jeecgframework.web.system.user.entity.UserEntity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;


/**
 * 在线用户对象
 * @author DELL
 * @date 2019-04-29
 * @version V1.0
 */
public class Client implements Serializable {

	/**
	 * 用户
	 */
	private UserEntity user;

	private Map<String, FunctionEntity> functions;

	private Map<Integer, List<FunctionEntity>> functionMap;

	/**
	 * 用户IP
	 */
	private String ip;
	/**
	 * 登录时间
	 */
	private Date logindatetime;

	public UserEntity getUser() {
		return user;
	}

	public void setUser(UserEntity user) {
		this.user = user;
	}

	public Map<Integer, List<FunctionEntity>> getFunctionMap() {
		return functionMap;
	}

	public void setFunctionMap(Map<Integer, List<FunctionEntity>> functionMap) {
		this.functionMap = functionMap;
	}

	public Map<String, FunctionEntity> getFunctions() {
		return functions;
	}

	public void setFunctions(Map<String, FunctionEntity> functions) {
		this.functions = functions;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public Date getLogindatetime() {
		return logindatetime;
	}

	public void setLogindatetime(Date logindatetime) {
		this.logindatetime = logindatetime;
	}

}
