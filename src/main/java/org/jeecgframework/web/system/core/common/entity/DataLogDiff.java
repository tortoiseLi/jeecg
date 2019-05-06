package org.jeecgframework.web.system.core.common.entity;

import java.io.Serializable;

/**
 * 数据版本比较
 * @author DELL
 * @date 2019-04-29
 * @version V1.0
 */
public class DataLogDiff implements Serializable {

	/**
	 * 名称
	 */
	private String name;

	/**
	 * 数据一
	 */
	private String value1;

	/**
	 * 数据二
	 */
	private String value2;

	/**
	 * 差异
	 */
	private String diff;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue1() {
		return value1;
	}

	public void setValue1(String value1) {
		this.value1 = value1;
	}

	public String getValue2() {
		return value2;
	}

	public void setValue2(String value2) {
		this.value2 = value2;
	}

	public void setDiff(String diff) {
		this.diff = diff;
	}

	public String getDiff() {
		return diff;
	}
}
