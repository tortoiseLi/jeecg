package org.jeecgframework.core.common.service.impl;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.jeecgframework.core.common.dao.ICommonDao;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.hibernate.qbc.HqlQuery;
import org.jeecgframework.core.common.hibernate.qbc.PageList;
import org.jeecgframework.core.common.model.common.DBTable;
import org.jeecgframework.core.common.model.common.UploadFile;
import org.jeecgframework.core.common.model.json.ComboTree;
import org.jeecgframework.core.common.model.json.ImportFile;
import org.jeecgframework.core.common.model.json.TreeGrid;
import org.jeecgframework.core.common.service.CommonService;
import org.jeecgframework.tag.vo.datatable.DataTableReturn;
import org.jeecgframework.tag.vo.easyui.Autocomplete;
import org.jeecgframework.tag.vo.easyui.ComboTreeModel;
import org.jeecgframework.tag.vo.easyui.TreeGridModel;
import org.jeecgframework.web.system.pojo.base.TSDepart;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("commonService")
@Transactional
public class CommonServiceImpl implements CommonService {
	public ICommonDao commonDao = null;

	/**
	 * 获取所有数据库表
	 * 
	 * @return
	 */
	@Override
	@Transactional(readOnly = true)
	public List<DBTable> getAllDbTableName() {
		return commonDao.findDbTableList();
	}

	@Override
	@Transactional(readOnly = true)
	public Integer getAllDbTableSize() {
		return commonDao.getDbTableSize();
	}

	@Resource
	public void setCommonDao(ICommonDao commonDao) {
		this.commonDao = commonDao;
	}

	
	@Override
	public <T> Serializable save(T entity) {
		return commonDao.insert(entity);
	}

	
	@Override
	public <T> void saveOrUpdate(T entity) {
		commonDao.saveOrUpdate(entity);

	}

	
	@Override
	public <T> void delete(T entity) {
		commonDao.delete(entity);

	}

	/**
	 * 删除实体集合
	 * 
	 * @param <T>
	 * @param entities
	 */
	@Override
	public <T> void deleteAllEntitie(Collection<T> entities) {
		commonDao.deleteCollection(entities);
	}

	/**
	 * 根据实体名获取对象
	 */
	@Override
	@Transactional(readOnly = true)
	public <T> T get(Class<T> class1, Serializable id) {
		return commonDao.getById(class1, id);
	}

	/**
	 * 根据实体名返回全部对象
	 * 
	 * @param <T>
	 * @return
	 */
    @Override
	@Transactional(readOnly = true)
	public <T> List<T> getList(Class clas) {
		return commonDao.findList(clas);
	}

	/**
	 * 根据实体名获取对象
	 */
    @Override
	@Transactional(readOnly = true)
	public <T> T getEntity(Class entityName, Serializable id) {
		return (T) commonDao.getById(entityName, id);
	}

	/**
	 * 根据实体名称和字段名称和字段值获取唯一记录
	 * 
	 * @param <T>
	 * @param entityClass
	 * @param propertyName
	 * @param value
	 * @return
	 */
    @Override
	@Transactional(readOnly = true)
	public <T> T findUniqueByProperty(Class<T> entityClass,
			String propertyName, Object value) {
		return commonDao.getByProperty(entityClass, propertyName, value);
	}

	/**
	 * 按属性查找对象列表.
	 */
    @Override
	@Transactional(readOnly = true)
	public <T> List<T> findByProperty(Class<T> entityClass,
			String propertyName, Object value) {

		return commonDao.findListByProperty(entityClass, propertyName, value);
	}

	/**
	 * 加载全部实体
	 * 
	 * @param <T>
	 * @param entityClass
	 * @return
	 */
    @Override
	@Transactional(readOnly = true)
	public <T> List<T> loadAll(final Class<T> entityClass) {
		return commonDao.findList(entityClass);
	}

    @Transactional(readOnly = true)
	public <T> T singleResult(String hql) {
		return commonDao.getByHql(hql);
	}

	/**
	 * 删除实体主键ID删除对象
	 * 
	 * @param <T>
	 */
	@Override
	public <T> void deleteEntityById(Class entityName, Serializable id) {
		commonDao.deleteById(entityName, id);
	}

	/**
	 * 更新指定的实体
	 * 
	 * @param <T>
	 * @param pojo
	 */
	@Override
	public <T> void updateEntitie(T pojo) {
		commonDao.update(pojo);

	}

	/**
	 * 通过hql 查询语句查找对象
	 * 
	 * @param <T>
	 * @return
	 */
	@Override
	@Transactional(readOnly = true)
	public <T> List<T> findByQueryString(String hql) {
		return commonDao.findListByHql(hql);
	}

	/**
	 * 根据sql更新
	 * 
	 * @return
	 */
	@Override
	public int updateBySqlString(String sql) {
		return commonDao.executeSql(sql);
	}

	/**
	 * 根据sql查找List
	 * 
	 * @param <T>
	 * @param query
	 * @return
	 */
	@Override
	@Transactional(readOnly = true)
	public List<Object> findListbySql(String query) {
		return commonDao.findListBySqlReObject(query);
	}

	/**
	 * 通过属性称获取实体带排序
	 * 
	 * @param <T>
	 * @return
	 */
	@Override
	@Transactional(readOnly = true)
	public <T> List<T> findByPropertyisOrder(Class<T> entityClass,
			String propertyName, Object value, boolean isAsc) {
		return commonDao.findByPropertyIsOrder(entityClass, propertyName,
				value, isAsc);
	}

	/**
	 * 
	 * cq方式分页
	 * 
	 * @param cq
	 * @param isOffset
	 * @return
	 */
	@Override
	@Transactional(readOnly = true)
	public PageList getPageList(final CriteriaQuery cq, final boolean isOffset) {
		return commonDao.findPageByCriteriaQuery(cq, isOffset);
	}

	/**
	 * 返回DataTableReturn模型
	 * 
	 * @param cq
	 * @param isOffset
	 * @return
	 */
	@Override
	@Transactional(readOnly = true)
	public DataTableReturn getDataTableReturn(final CriteriaQuery cq,
			final boolean isOffset) {
		return commonDao.getDataTableReturn(cq, isOffset);
	}

	/**
	 * 返回easyui datagrid模型
	 * 
	 * @param cq
	 * @param isOffset
	 * @return
	 */

	@Override
	@Transactional(readOnly = true)
	public void getDataGridReturn(final CriteriaQuery cq,
			final boolean isOffset) {
		commonDao.getDataGridReturn(cq, isOffset);

	}

	/**
	 * 
	 * hqlQuery方式分页
	 * 
	 * @return
	 */
	@Override
	@Transactional(readOnly = true)
	public PageList getPageList(final HqlQuery hqlQuery,
			final boolean needParameter) {
		return commonDao.findPageByHqlQuery(hqlQuery, needParameter);
	}

	/**
	 * 
	 * sqlQuery方式分页
	 * 
	 * @return
	 */
	@Override
	@Transactional(readOnly = true)
	public PageList getPageListBySql(final HqlQuery hqlQuery,
			final boolean isToEntity) {
		return commonDao.findPageByHqlQueryReToEntity(hqlQuery, isToEntity);
	}

	@Override
	public Session getSession()

	{
		return commonDao.getSession();
	}

	@Override
	@Transactional(readOnly = true)
	public List findByExample(final String entityName,
			final Object exampleEntity) {
		return commonDao.findListByEntity(entityName, exampleEntity);
	}

	/**
	 * 通过cq获取全部实体
	 * 
	 * @param <T>
	 * @param cq
	 * @return
	 */
	@Override
	@Transactional(readOnly = true)
	public <T> List<T> getListByCriteriaQuery(final CriteriaQuery cq,
			Boolean ispage) {
		return commonDao.findListByCriteriaQuery(cq, ispage);
	}

	/**
	 * 通过cq获取全部实体
	 *
	 * @param <T>
	 * @param cq
	 * @return
	 */
	@Override
	@Transactional(readOnly = true)
	public <T> List<T> getListByCriteriaQuery(final CriteriaQuery cq) {
		return commonDao.findListByCriteriaQuery(cq, false);
	}

	/**
	 * 文件上传
	 * 
	 */
	@Override
	public <T> T uploadFile(UploadFile uploadFile) {
		return commonDao.uploadFile(uploadFile);
	}

	@Override
	@Transactional(readOnly = true)
	public HttpServletResponse viewOrDownloadFile(UploadFile uploadFile)

	{
		return commonDao.viewOrDownloadFile(uploadFile);
	}

	/**
	 * 生成XML文件
	 * 
	 * @return
	 */
	@Override
	public HttpServletResponse createXml(ImportFile importFile) {
		return commonDao.createXml(importFile);
	}

	/**
	 * 解析XML文件
	 * 
	 */
	@Override
	public void parserXml(String fileName) {
		commonDao.parserXml(fileName);
	}

	@Override
	@Transactional(readOnly = true)
	public List<ComboTree> comTree(List<TSDepart> all, ComboTree comboTree) {
		return commonDao.comTree(all, comboTree);
	}

	@Override
	@Transactional(readOnly = true)
	public List<ComboTree> ComboTree(List all, ComboTreeModel comboTreeModel, List in, boolean recursive) {
        return commonDao.ComboTree(all, comboTreeModel, in, recursive);
	}

	/**
	 * 构建树形数据表
	 */
	@Override
	@Transactional(readOnly = true)
	public List<TreeGrid> treegrid(List<?> all, TreeGridModel treeGridModel) {
		return commonDao.treegrid(all, treeGridModel);
	}

	/**
	 * 获取自动完成列表
	 * 
	 * @param <T>
	 * @return
	 */
	@Override
	@Transactional(readOnly = true)
	public <T> List<T> getAutoList(Autocomplete autocomplete) {
		StringBuffer sb = new StringBuffer("");
		for (String searchField : autocomplete.getSearchField().split(",")) {
			sb.append("  or " + searchField + " like '%"+ autocomplete.getTrem() + "%' ");
		}
		String hql = "from " + autocomplete.getEntityName() + " where 1!=1 "+ sb.toString();
		return commonDao.getSession().createQuery(hql).setFirstResult(autocomplete.getCurPage() - 1).setMaxResults(autocomplete.getMaxRows()).list();
	}

	
	@Override
	public Integer executeSql(String sql, List<Object> param) {
		return commonDao.executeSql(sql, param);
	}

	
	@Override
	public Integer executeSql(String sql, Object... param) {
		return commonDao.executeSql(sql, param);
	}

	
	@Override
	public Integer executeSql(String sql, Map<String, Object> param) {
		return commonDao.executeSql(sql, param);
	}
	
	@Override
	public Object executeSqlReturnKey(String sql, Map<String, Object> param) {
		return commonDao.executeSqlReturnKey(sql, param);
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<Map<String, Object>> findForJdbc(String sql, int page, int rows) {
		return commonDao.findListMapBySqlWithPage(sql, page, rows);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Map<String, Object>> findForJdbc(String sql, Object... objs) {
		return commonDao.findListMapBySql(sql, objs);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Map<String, Object>> findForJdbcParam(String sql, int page,
			int rows, Object... objs) {
		return commonDao.findListMapBySqlWithPage(sql, page, rows, objs);
	}

	@Override
	@Transactional(readOnly = true)
	public <T> List<T> findObjForJdbc(String sql, int page, int rows,
			Class<T> clazz) {
		return commonDao.findListBySqlWithPage(sql, page, rows, clazz);
	}

	@Override
	@Transactional(readOnly = true)
	public Map<String, Object> findOneForJdbc(String sql, Object... objs) {
		return commonDao.getMapBySql(sql, objs);
	}

	@Override
	@Transactional(readOnly = true)
	public Long getCountForJdbc(String sql) {
		return commonDao.getCountBySql(sql);
	}
	@Override
	@Transactional(readOnly = true)
	public Long getCountForJdbcParam(String sql, Object... objs) {
		return commonDao.getCountBySql(sql,objs);
	}

	
	@Override
	public <T> void batchSave(List<T> entitys) {
		this.commonDao.batchInsert(entitys);
	}

	/**
	 * 通过hql 查询语句查找对象
	 * 
	 * @param <T>
	 * @return
	 */
	@Override
	@Transactional(readOnly = true)
	public <T> List<T> findHql(String hql, Object... param) {
		return this.commonDao.findListByHql(hql, param);
	}

	@Override
	@Transactional(readOnly = true)
	public <T> List<T> pageList(DetachedCriteria dc, int firstResult,
			int maxResult) {
		return this.commonDao.findListByDetachedCriteria(dc, firstResult, maxResult);
	}

	@Override
	@Transactional(readOnly = true)
	public <T> List<T> findByDetached(DetachedCriteria dc) {
		return this.commonDao.findListByDetached(dc);
	}



}
