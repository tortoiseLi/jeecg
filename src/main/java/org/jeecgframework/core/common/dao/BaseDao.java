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
	 * 通过hql查询list
	 * @param <T>
	 * @param hql
	 * @return
	 */
	<T> List<T> findListByHql(String hql);

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
	DataTableReturn getDataTableReturn(final CriteriaQuery cq,
									   final boolean isOffset);

	/**
	 * 返回easyui datagrid模型
	 *
	 * @param cq
	 * @param isOffset
	 * @return
	 */

	void getDataGridReturn(CriteriaQuery cq,
						   final boolean isOffset);


	/**
	 * 执行SQL
	 */
	Integer executeSql(String sql, List<Object> param);

	/**
	 * 执行SQL
	 */
	Integer executeSql(String sql, Object... param);

	/**
	 * 执行SQL 使用:name占位符
	 */
	Integer executeSql(String sql, Map<String, Object> param);
	/**
	 * 执行SQL 使用:name占位符,并返回插入的主键值
	 */
	Object executeSqlReturnKey(String sql, Map<String, Object> param);
	/**
	 * 通过JDBC查找对象集合 使用指定的检索标准检索数据返回数据
	 */
	List<Map<String, Object>> findForJdbc(String sql, Object... objs);

	/**
	 * 通过JDBC查找对象集合 使用指定的检索标准检索数据返回数据
	 */
	Map<String, Object> findOneForJdbc(String sql, Object... objs);

	/**
	 * 通过JDBC查找对象集合,带分页 使用指定的检索标准检索数据并分页返回数据
	 */
	List<Map<String, Object>> findForJdbc(String sql, int page, int rows);

	/**
	 * 通过JDBC查找对象集合,带分页 使用指定的检索标准检索数据并分页返回数据
	 */
	<T> List<T> findObjForJdbc(String sql, int page, int rows,
							   Class<T> clazz);

	/**
	 * 使用指定的检索标准检索数据并分页返回数据-采用预处理方式
	 *
	 * @param criteria
	 * @param firstResult
	 * @param maxResults
	 * @return
	 * @throws DataAccessException
	 */
	List<Map<String, Object>> findForJdbcParam(String sql, int page,
											   int rows, Object... objs);

	/**
	 * 使用指定的检索标准检索数据并分页返回数据For JDBC
	 */
	Long getCountForJdbc(String sql);

	/**
	 * 使用指定的检索标准检索数据并分页返回数据For JDBC-采用预处理方式
	 *
	 */
	Long getCountForJdbcParam(String sql, Object[] objs);

	/**
	 * 通过hql 查询语句查找对象
	 *
	 * @param <T>
	 * @param query
	 * @return
	 */
	<T> List<T> findHql(String hql, Object... param);

	/**
	 * 执行HQL语句操作更新
	 *
	 * @param hql
	 * @return
	 */
	Integer executeHql(String hql);

	<T> List<T> pageList(DetachedCriteria dc, int firstResult,
						 int maxResult);

	<T> List<T> findByDetached(DetachedCriteria dc);

	/**
	 * 执行存储过程
	 * @param execute
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
