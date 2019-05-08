package org.jeecgframework.core.common.service.impl;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.jeecgframework.core.common.dao.CommonDao;
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

	public CommonDao commonDao = null;

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
	public void setCommonDao(CommonDao commonDao) {
		this.commonDao = commonDao;
	}

	@Override
	public <T> Serializable add(T entity) {
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

	@Override
	public <T> void deleteCollection(Collection<T> entities) {
		commonDao.deleteCollection(entities);
	}

	@Override
	@Transactional(readOnly = true)
	public <T> T getById(Class<T> class1, Serializable id) {
		return commonDao.getById(class1, id);
	}

    @Override
	@Transactional(readOnly = true)
	public <T> List<T> getList(Class clas) {
		return commonDao.findList(clas);
	}

    @Override
	@Transactional(readOnly = true)
	public <T> T findUniqueByProperty(Class<T> entityClass,
			String propertyName, Object value) {
		return commonDao.getByProperty(entityClass, propertyName, value);
	}

    @Override
	@Transactional(readOnly = true)
	public <T> List<T> findByProperty(Class<T> entityClass,
			String propertyName, Object value) {

		return commonDao.findListByProperty(entityClass, propertyName, value);
	}

    @Override
	@Transactional(readOnly = true)
	public <T> List<T> loadAll(final Class<T> entityClass) {
		return commonDao.findList(entityClass);
	}

    @Override
	@Transactional(readOnly = true)
	public <T> T singleResult(String hql) {
		return commonDao.getByHql(hql);
	}

	@Override
	public void deleteById(Class entityName, Serializable id) {
		commonDao.deleteById(entityName, id);
	}

	@Override
	public <T> void update(T pojo) {
		commonDao.update(pojo);

	}

	@Override
	@Transactional(readOnly = true)
	public <T> List<T> findByQueryString(String hql) {
		return commonDao.findListByHql(hql);
	}

	@Override
	public int updateBySqlString(String sql) {
		return commonDao.executeSql(sql);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Object> findListbySql(String query) {
		return commonDao.findListBySqlReObject(query);
	}

	@Override
	@Transactional(readOnly = true)
	public <T> List<T> findByPropertyisOrder(Class<T> entityClass,
			String propertyName, Object value, boolean isAsc) {
		return commonDao.findByPropertyIsOrder(entityClass, propertyName, value, isAsc);
	}

	@Override
	@Transactional(readOnly = true)
	public PageList getPageList(final CriteriaQuery cq, final boolean isOffset) {
		return commonDao.findPageByCriteriaQuery(cq, isOffset);
	}

	@Override
	@Transactional(readOnly = true)
	public DataTableReturn getDataTableReturn(final CriteriaQuery cq,
			final boolean isOffset) {
		return commonDao.getDataTableReturn(cq, isOffset);
	}

	@Override
	@Transactional(readOnly = true)
	public void getDataGridReturn(final CriteriaQuery cq,
			final boolean isOffset) {
		commonDao.getDataGridReturn(cq, isOffset);

	}

	@Override
	@Transactional(readOnly = true)
	public PageList getPageList(final HqlQuery hqlQuery,
			final boolean needParameter) {
		return commonDao.findPageByHqlQuery(hqlQuery, needParameter);
	}

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

	@Override
	@Transactional(readOnly = true)
	public <T> List<T> getListByCriteriaQuery(final CriteriaQuery cq,
			Boolean ispage) {
		return commonDao.findListByCriteriaQuery(cq, ispage);
	}

	@Override
	@Transactional(readOnly = true)
	public <T> List<T> getListByCriteriaQuery(final CriteriaQuery cq) {
		return commonDao.findListByCriteriaQuery(cq, false);
	}

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

	@Override
	public HttpServletResponse createXml(ImportFile importFile) {
		return commonDao.createXml(importFile);
	}

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
	public List<ComboTree> comboTree(List all, ComboTreeModel comboTreeModel, List in, boolean recursive) {
        return commonDao.comboTree(all, comboTreeModel, in, recursive);
	}

	@Override
	@Transactional(readOnly = true)
	public List<TreeGrid> treeGrid(List<?> all, TreeGridModel treeGridModel) {
		return commonDao.treeGrid(all, treeGridModel);
	}

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
	public <T> void batchAdd(List<T> entitys) {
		this.commonDao.batchInsert(entitys);
	}

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
