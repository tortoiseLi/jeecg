package org.jeecgframework.web.system.core;

import java.util.List;

/**
 * 接口规则
 * @author DELL
 * @date 2019-04-29
 * @version V1.0
 */
public class InterfaceRuleDto {
	
	/**
	 * 接口编码
	 */
	private String interfaceCode;
	
	/**
	 * 接口数据规则id
	 */
	private String dataRule;
	
	/**
	 * 接口规则
	 */
	private List<InterfaceDdataRuleEntity> interfaceDataRule;

	public String getInterfaceCode() {
		return interfaceCode;
	}

	public void setInterfaceCode(String interfaceCode) {
		this.interfaceCode = interfaceCode;
	}

	public String getDataRule() {
		return dataRule;
	}

	public void setDataRule(String dataRule) {
		this.dataRule = dataRule;
	}

	public List<InterfaceDdataRuleEntity> getInterfaceDataRule() {
		return interfaceDataRule;
	}

	public void setInterfaceDataRule(
			List<InterfaceDdataRuleEntity> interfaceDataRule) {
		this.interfaceDataRule = interfaceDataRule;
	}
}
