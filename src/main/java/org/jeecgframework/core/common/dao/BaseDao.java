package org.jeecgframework.core.common.dao;

import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.hibernate.qbc.HqlQuery;
import org.jeecgframework.core.common.hibernate.qbc.PageList;
import org.jeecgframework.core.common.model.common.DbTable;
import org.jeecgframework.tag.vo.datatable.DataTableReturn;
import org.springframework.dao.DataAccessException;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * DAO层泛型基类接口
 * @author DELL
 * @date 2019-04-28
 * @version V1.0
 */
public interface BaseDao {

	/**
	 * 新增
	 * @param entity
	 * @param <T>
	 * @return
	 */
	<T> Serializable insert(T entity);

	/**
	 * 批量新增
	 * @param entityList
	 * @param <T>
	 */
	<T> void batchInsert(List<T> entityList);

	/**
	 * 删除
	 * @param entity
	 * @param <T>
	 */
	<T> void delete(T entity);

	/**
	 * 根据实体名称及ID删除
	 * @param entityName
	 * @param id
	 */
	void deleteById(Class entityName, Serializable id);

	/**
	 * 删除实体集合
	 * @param <T>
	 * @param collection
	 */
	<T> void deleteByCollection(Collection<T> collection);

	/**
	 * 更新指定的实体
	 * @param <T>
	 * @param entity
	 */
	<T> void update(T entity);

	/**
	 * 根据sql更新
	 * @param sql
	 * @return
	 */
	int updateBySql(String sql);

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
	 * 根据sql查询唯一记录
	 * @param sql
	 * @param <T>
	 * @return
	 */
	<T> T getBySql(String sql);

	/**
	 * 根据hql查询唯一记录
	 * @param hql
	 * @param <T>
	 * @return
	 */
	<T> T getByHql(String hql);

	/**
	 * 通过jdbc查询唯一记录
	 * @param sql
	 * @param params
	 * @return
	 */
	Map<String, Object> getBySql(String sql, Object... params);

	/**
	 * 查询全部实体
	 * @param <T>
	 * @param entityClass
	 * @return
	 */
	<T> List<T> findList(Class<T> entityClass);

	/**
	 * 根据sql查找list
	 * @param <T>
	 * @param sql
	 * @return
	 */
	<T> List<T> findListBySql(String sql);

	/**
	 * 根据sql查询list
	 * @param sql
	 * @param params
	 * @param <T>
	 * @return
	 */
	<T> List<T> findListBySql(String sql, Object... params);

	/**
	 * 通过hql查询list
	 * @param <T>
	 * @param hql
	 * @return
	 */
	<T> List<T> findListByHql(String hql);

	/**
	 * 通过hql查询list
	 * @param hql
	 * @param param
	 * @param <T>
	 * @return
	 */
	<T> List<T> findListByHql(String hql, Object... param);

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
	 * 通过CQ查询list
	 * @param cq
	 * @param isPage
	 * @param <T>
	 * @return
	 */
	<T> List<T> findListByCriteriaQuery(final CriteriaQuery cq, Boolean isPage);

	/**
	 * 通过jdbc查询list
	 * @param sql
	 * @param params
	 * @return
	 */
	List<Map<String, Object>> findListForJdbc(String sql, Object... params);

	/**
	 * 通过jdbc查询list
	 * @param sql
	 * @param page
	 * @param rows
	 * @return
	 */
	List<Map<String, Object>> findListForJdbc(String sql, int page, int rows);

	/**
	 * 通过jdbc查询list
	 * @param sql
	 * @param clazz
	 * @param <T>
	 * @return
	 */
	<T> List<T> findListForJdbc(String sql, Class<T> clazz);

	/**
	 * 通过jdbc查询list
	 * @param sql
	 * @param page
	 * @param rows
	 * @param clazz
	 * @param <T>
	 * @return
	 */
	<T> List<T> findListForJdbc(String sql, int page, int rows, Class<T> clazz);

	/**
	 * 通过CQ查询Page
	 * @param cq
	 * @param isOffset 是否分页
	 * @return
	 */
	PageList findPageByCriteriaQuery(final CriteriaQuery cq, final boolean isOffset);


	List findByExample(final String entityName, final Object exampleEntity);

	/**
	 * 通过hql 查询语句查找HashMap对象
	 *
	 * @param <T>
	 * @param query
	 * @return
	 */
	Map<Object, Object> getHashMapbyQuery(String query);

	/**
	 * 返回jquery datatables模型
	 *
	 * @param cq
	 * @param isOffset
	 * @return
	 */
	DataTableReturn getDataTableReturn(final CriteriaQuery cq, final boolean isOffset);

	/**
	 * 返回easyui datagrid模型
	 *
	 * @param cq
	 * @param isOffset
	 * @return
	 */
	void getDataGridReturn(CriteriaQuery cq, final boolean isOffset);

	/**
	 * 执行sql
	 * @param sql
	 * @param paramList
	 * @return
	 */
	Integer executeSql(String sql, List<Object> paramList);

	/**
	 * 执行sql
	 * @param sql
	 * @param params
	 * @return
	 */
	Integer executeSql(String sql, Object... params);

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
	 * @return
	 */
	Integer executeHql(String hql);

	/**
	 * 执行hql
	 * @param hql
	 * @return
	 */
	Integer executeHql(String hql, Object...params);

	/**
	 * 统计数量
	 * @param sql
	 * @return
	 */
	Long getCountForJdbc(String sql);

	/**
	 * 统计数量
	 * @param sql
	 * @param params
	 * @return
	 */
	Long getCountForJdbc(String sql, Object[] params);


	/**
	 * 使用指定的检索标准检索数据并分页返回数据-采用预处理方式
	 *
	 * @param criteria
	 * @param firstResult
	 * @param maxResults
	 * @return
	 * @throws DataAccessException
	 */
	List<Map<String, Object>> findForJdbcParam(String sql, int page, int rows, Object... objs);

	<T> List<T> pageList(DetachedCriteria dc, int firstResult, int maxResult);

	<T> List<T> findByDetached(DetachedCriteria dc);

	/**
	 * 执行存储过程
	 * @param procedureSql
	 * @param params
	 * @param <T>
	 * @return
	 */
	<T> List<T> executeProcedure(String procedureSql, Object... params);

	/**
	 * 获取所有数据库表
	 * @return
	 */
	List<DbTable> findDbTableList();

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

}
