package org.jeecgframework.web.system.dict.entity;

/**
 * 数据字典
 * @author DELL
 * @date 2019-04-29
 * @version V1.0
 */
public class DictEntity {

	/**
	 * 数据字典编码
	 */
	private String typecode;

	/**
	 * 数据字典名称
	 */
	private String typename;
	
	public String getTypecode() {
		return typecode;
	}

	public void setTypecode(String typecode) {
		this.typecode = typecode;
	}

	public String getTypename() {
		return typename;
	}

	public void setTypename(String typename) {
		this.typename = typename;
	}
}
