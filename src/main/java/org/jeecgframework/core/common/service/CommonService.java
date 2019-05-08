package org.jeecgframework.core.common.service;

import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.hibernate.qbc.HqlQuery;
import org.jeecgframework.core.common.hibernate.qbc.PageList;
import org.jeecgframework.core.common.model.common.DBTable;
import org.jeecgframework.core.common.model.common.UploadFile;
import org.jeecgframework.core.common.model.json.ComboTree;
import org.jeecgframework.core.common.model.json.ImportFile;
import org.jeecgframework.core.common.model.json.TreeGrid;
import org.jeecgframework.tag.vo.datatable.DataTableReturn;
import org.jeecgframework.tag.vo.easyui.Autocomplete;
import org.jeecgframework.tag.vo.easyui.ComboTreeModel;
import org.jeecgframework.tag.vo.easyui.TreeGridModel;
import org.jeecgframework.web.system.pojo.base.TSDepart;

import javax.servlet.http.HttpServletResponse;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * 基础Service
 * @author Administrator
 * @date 2019-05-08
 * @version V1.0
 */
public interface CommonService {

	/**
	 * 新增
	 * @param entity
	 * @param <T>
	 * @return
	 */
	<T> Serializable add(T entity);

	/**
	 * 批量新增
	 * @param entityList
	 * @param <T>
	 */
	<T> void batchAdd(List<T> entityList);

	/**
	 * 删除
	 * @param entity
	 * @param <T>
	 */
	<T> void delete(T entity);

	/**
	 * 根据实体名称及ID删除
	 * @param entityClass
	 * @param id
	 */
	void deleteById(Class entityClass, Serializable id);

	/**
	 * 删除实体集合
	 * @param <T>
	 * @param collection
	 */
	<T> void deleteCollection(Collection<T> collection);

	/**
	 * 更新指定的实体
	 * @param <T>
	 * @param entity
	 */
	<T> void update(T entity);

	/**
	 * 新增或修改
	 * @param entity
	 * @param <T>
	 */
	<T> void saveOrUpdate(T entity);

	/**
	 * 根据实体类及ID获取实体
	 * @param entityClass
	 * @param id
	 * @param <T>
	 * @return
	 */
	<T> T getById(Class<T> entityClass, Serializable id);

	/**
	 * 根据实体名称和字段名称和字段值获取唯一记录
	 * @param entityClass
	 * @param propertyName
	 * @param value
	 * @param <T>
	 * @return
	 */
	<T> T getByProperty(Class<T> entityClass, String propertyName, Object value);

	/**
	 * 通过jdbc查询唯一记录
	 * @param sql
	 * @param params
	 * @return
	 */
	Map<String, Object> getMapBySql(String sql, Object...params);

	/**
	 * 通过jdbc查询唯一记录
	 * @param sql
	 * @param entityClass
	 * @param params
	 * @param <T>
	 * @return
	 */
	<T> T getBySql(String sql, Class<T> entityClass, Object...params);

	/**
	 * 根据hql查询唯一记录
	 * @param hql
	 * @param params
	 * @param <T>
	 * @return
	 */
	<T> T getByHql(String hql, Object...params);

	/**
	 * 查询全部实体
	 * @param <T>
	 * @param entityClass
	 * @return
	 */
	<T> List<T> findList(final Class<T> entityClass);

	/**
	 * 根据sql查找list
	 * @param sql
	 * @param params
	 * @return
	 */
	List<Object> findListBySqlReObject(final String sql, Object...params);

	/**
	 * 通过jdbc查询list
	 * @param sql
	 * @param entityClass
	 * @param params
	 * @param <T>
	 * @return
	 */
	<T> List<T> findListBySql(String sql, Class<T> entityClass, Object...params);

	/**
	 * 通过jdbc查询list
	 * @param sql
	 * @param curPage
	 * @param pageSize
	 * @param entityClass
	 * @param params
	 * @param <T>
	 * @return
	 */
	<T> List<T> findListBySqlWithPage(String sql, int curPage, int pageSize, Class<T> entityClass, Object...params);

	/**
	 * 通过hql查询list
	 * @param hql
	 * @param params
	 * @param <T>
	 * @return
	 */
	<T> List<T> findListByHql(String hql, Object...params);

	/**
	 * 通过属性查询list
	 * @param entityClass
	 * @param propertyName
	 * @param value
	 * @param <T>
	 * @return
	 */
	<T> List<T> findListByProperty(Class<T> entityClass, String propertyName, Object value);

	/**
	 * 通过属性查询list
	 * @param entityClass
	 * @param propertyName
	 * @param value
	 * @param isAsc
	 * @param <T>
	 * @return
	 */
	<T> List<T> findByPropertyIsOrder(Class<T> entityClass, String propertyName, Object value, boolean isAsc);

	/**
	 * 通过CQ查询list
	 * @param cq
	 * @param <T>
	 * @return
	 */
	<T> List<T> getListByCriteriaQuery(final CriteriaQuery cq);

	/**
	 * 通过CQ查询list
	 * @param cq
	 * @param isPage
	 * @param <T>
	 * @return
	 */
	<T> List<T> findListByCriteriaQuery(final CriteriaQuery cq, Boolean isPage);

	/**
	 * 根据实体查询list
	 * @param entityClass
	 * @param exampleEntity
	 * @return
	 */
	List findListByEntity(final String entityClass, final Object exampleEntity);

	/**
	 * 根据Autocomplete查询list
	 * @param autocomplete
	 * @param <T>
	 * @return
	 */
	<T> List<T> getAutoList(Autocomplete autocomplete);

	/**
	 * 查询实体List
	 * @param dc
	 * @param <T>
	 * @return
	 */
	<T> List<T> findListByDetached(DetachedCriteria dc);

	/**
	 * 查询实体List
	 * @param dc
	 * @param firstResult
	 * @param maxResult
	 * @param <T>
	 * @return
	 */
	<T> List<T> findListByDetachedCriteria(DetachedCriteria dc, int firstResult, int maxResult);

	/**
	 * 通过jdbc查询list
	 * @param sql
	 * @param params
	 * @return
	 */
	List<Map<String, Object>> findListMapBySql(String sql, Object...params);

	/**
	 * 通过jdbc查询list
	 * @param sql
	 * @param curPage
	 * @param pageSize
	 * @param params
	 * @return
	 */
	List<Map<String, Object>> findListMapBySqlWithPage(String sql, int curPage, int pageSize, Object...params);

	/**
	 * 通过CQ查询Page
	 * @param cq
	 * @param isOffset 是否分页
	 * @return
	 */
	PageList findPageByCriteriaQuery(final CriteriaQuery cq, final boolean isOffset);

	/**
	 * 通过HQ查询Page
	 * @param hqlQuery
	 * @param needParameter
	 * @return
	 */
	PageList findPageByHqlQuery(final HqlQuery hqlQuery, final boolean needParameter);

	/**
	 * 通过HQ查询Page
	 * @param hqlQuery
	 * @param isToEntity
	 * @return
	 */
	PageList findPageByHqlQueryReToEntity(final HqlQuery hqlQuery, final boolean isToEntity);

	/**
	 * 返回DataTable模型
	 * @param cq
	 * @param isOffset
	 * @return
	 */
	DataTableReturn getDataTableReturn(final CriteriaQuery cq, final boolean isOffset);

	/**
	 * 返回DataGrid模型
	 * @param cq
	 * @param isOffset
	 */
	void getDataGridReturn(CriteriaQuery cq, final boolean isOffset);

	/**
	 * 执行sql
	 * @param sql
	 * @param params
	 * @return
	 */
	Integer executeSql(String sql, Object...params);

	/**
	 * 执行sql
	 * @param sql
	 * @param paramMap
	 * @return
	 */
	Integer executeSql(String sql, Map<String, Object> paramMap);

	/**
	 * 执行sql,并返回插入的主键值
	 * @param sql
	 * @param paramMap
	 * @return
	 */
	Object executeSqlReturnKey(String sql, Map<String, Object> paramMap);

	/**
	 * 执行hql
	 * @param hql
	 * @param params
	 * @return
	 */
	Integer executeHql(String hql, Object...params);

	/**
	 * 统计数量
	 * @param sql
	 * @param params
	 * @return
	 */
	Long getCountBySql(String sql, Object...params);

	/**
	 * 获取所有数据库表
	 * @return
	 */
	List<DBTable> findDbTableList();

	/**
	 * 获取与表结构对应的所有实体数量
	 * @return
	 */
	Integer getDbTableSize();

	/**
	 * 获取hibernate session
	 * @return
	 */
	Session getSession();

	/**
	 * 文件上传
	 * @param uploadFile
	 * @param <T>
	 * @return
	 */
	<T> T uploadFile(UploadFile uploadFile);

	/**
	 * 文件上传或预览
	 * @param uploadFile
	 * @return
	 */
	HttpServletResponse viewOrDownloadFile(UploadFile uploadFile);

	/**
	 * 生成XML文件
	 * @param importFile
	 * @return
	 */
	HttpServletResponse createXml(ImportFile importFile);

	/**
	 * 解析XML文件
	 * @param fileName
	 */
	void parserXml(String fileName);

	/**
	 * 部门生成ComboTree
	 * @param all
	 * @param comboTree
	 * @return
	 */
	List<ComboTree> comTree(List<TSDepart> all, ComboTree comboTree);

	/**
	 * 根据模型生成ComboTree
	 * @param all
	 * @param comboTreeModel
	 * @param in
	 * @param recursive
	 * @return
	 */
	List<ComboTree> comboTree(List all, ComboTreeModel comboTreeModel, List in, boolean recursive);

	/**
	 * 生成TreeGrid
	 * @param all
	 * @param treeGridModel
	 * @return
	 */
	List<TreeGrid> treeGrid(List<?> all, TreeGridModel treeGridModel);

}
