package org.jeecgframework.core.common.service;

import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.hibernate.qbc.HqlQuery;
import org.jeecgframework.core.common.hibernate.qbc.PageList;
import org.jeecgframework.core.common.model.common.DbTable;
import org.jeecgframework.core.common.model.common.UploadFile;
import org.jeecgframework.core.common.model.json.ComboTree;
import org.jeecgframework.core.common.model.json.ImportFile;
import org.jeecgframework.core.common.model.json.TreeGrid;
import org.jeecgframework.tag.vo.datatable.DataTableReturn;
import org.jeecgframework.tag.vo.easyui.Autocomplete;
import org.jeecgframework.tag.vo.easyui.ComboTreeModel;
import org.jeecgframework.tag.vo.easyui.TreeGridModel;
import org.jeecgframework.web.system.pojo.base.DepartEntity;
import org.springframework.dao.DataAccessException;

import javax.servlet.http.HttpServletResponse;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * 通用Service
 * @author DELL
 * @version V1.0
 * @date 2019-04-29
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
     * 通过属性称获取实体带排序
     * @param <T>
     * @param clas
     * @return
     */
    <T> List<T> findByPropertyisOrder(Class<T> entityClass, String propertyName, Object value, boolean isAsc);




    /**
     * cq方式分页
     * @param cq
     * @param isOffset
     * @return
     */
    PageList getPageList(final CriteriaQuery cq, final boolean isOffset);

    /**
     * 返回DataTableReturn模型
     * @param cq
     * @param isOffset
     * @return
     */
    DataTableReturn getDataTableReturn(final CriteriaQuery cq, final boolean isOffset);

    /**
     * 返回easyui datagrid模型
     * @param cq
     * @param isOffset
     * @return
     */
    void getDataGridReturn(CriteriaQuery cq, final boolean isOffset);


    /**
     * hqlQuery方式分页
     * @param cq
     * @param isOffset
     * @return
     */
    PageList getPageList(final HqlQuery hqlQuery, final boolean needParameter);

    /**
     * sqlQuery方式分页
     * @param cq
     * @param isOffset
     * @return
     */
    PageList getPageListBySql(final HqlQuery hqlQuery, final boolean isToEntity);

    Session getSession();

    List findByExample(final String entityName, final Object exampleEntity);

    /**
     * 通过cq获取全部实体
     * @param <T>
     * @param cq
     * @return
     */
    <T> List<T> getListByCriteriaQuery(final CriteriaQuery cq, Boolean ispage);

    /**
     * 文件上传
     * @param request
     */
    <T> T uploadFile(UploadFile uploadFile);

    HttpServletResponse viewOrDownloadFile(UploadFile uploadFile);

    /**
     * 生成XML文件
     * @param fileName XML全路径
     */
    HttpServletResponse createXml(ImportFile importFile);

    /**
     * 解析XML文件
     * @param fileName XML全路径
     */
    void parserXml(String fileName);

    List<ComboTree> comTree(List<DepartEntity> all, ComboTree comboTree);

    /**
     * 根据模型生成JSON
     * @param all       全部对象
     * @param in        已拥有的对象
     * @param recursive 是否递归加载所有子节点
     * @return List<ComboTree>
     */
    List<ComboTree> ComboTree(List all, ComboTreeModel comboTreeModel, List in, boolean recursive);


    /**
     * 构建树形数据表
     * @param all
     * @param treeGridModel
     * @return
     */
    List<TreeGrid> treegrid(List<?> all, TreeGridModel treeGridModel);

    /**
     * 获取自动完成列表
     * @param <T>
     * @return
     */
    <T> List<T> getAutoList(Autocomplete autocomplete);

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
     * 执行SQL 使用:name占位符,并返回执行后的主键值
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
     */
    Long getCountForJdbcParam(String sql, Object... objs);

    /**
     * 通过hql 查询语句查找对象
     * @param <T>
     * @param query
     * @return
     */
    <T> List<T> findHql(String hql, Object... param);

    <T> List<T> pageList(DetachedCriteria dc, int firstResult,
                         int maxResult);

    <T> List<T> findByDetached(DetachedCriteria dc);

    /**
     * 执行存储过程
     * @param executeSql
     * @param params
     * @return
     */
    <T> List<T> executeProcedure(String procedureSql, Object... params);

}
