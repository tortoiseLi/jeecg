package org.jeecgframework.core.common.model.common;

import java.io.Serializable;
import java.util.List;

/**
 * 数据库表信息
 * @author DELL
 * @date 2019-04-29
 * @version V1.0
 */
public class DbTable<T> implements Serializable {

	/**
	 * 表名称
	 */
	public String tableName;

	/**
	 * 对应实体名称
	 */
	public String entityName;

	/**
	 * 表头
	 */
	public String tableTitle;

	/**
	 * 对应实体
	 */
	public Class<T> tableEntityClass;

	/**
	 * 表数据
	 */
	public List<T> tableData;

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getEntityName() {
		return entityName;
	}

	public void setEntityName(String entityName) {
		this.entityName = entityName;
	}

	public String getTableTitle() {
		return tableTitle;
	}

	public void setTableTitle(String tableTitle) {
		this.tableTitle = tableTitle;
	}

	public Class<T> getClass1() {
		return tableEntityClass;
	}

	public void setClass1(Class<T> class1) {
		this.tableEntityClass = class1;
	}

	public List<T> getTableData() {
		return tableData;
	}

	public void setTableData(List<T> tableData) {
		this.tableData = tableData;
	}
	
}
