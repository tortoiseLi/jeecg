package org.jeecgframework.core.common.service.impl;

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

import javax.annotation.Resource;
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
@Service("commonService")
@Transactional(rollbackFor = Exception.class)
public class CommonServiceImpl implements CommonService {

	public CommonDao commonDao = null;

	@Override
	public <T> Serializable add(T entity) {
		return commonDao.insert(entity);
	}

	@Override
	public <T> void batchAdd(List<T> entityList) {
		this.commonDao.batchInsert(entityList);
	}

	@Override
	public <T> void delete(T entity) {
		commonDao.delete(entity);
	}

	@Override
	public void deleteById(Class entityClass, Serializable id) {
		commonDao.deleteById(entityClass, id);
	}

	@Override
	public <T> void deleteCollection(Collection<T> entityList) {
		commonDao.deleteCollection(entityList);
	}

	@Override
	public <T> void update(T entity) {
		commonDao.update(entity);
	}

	@Override
	public <T> void saveOrUpdate(T entity) {
		commonDao.saveOrUpdate(entity);
	}

	@Override
	@Transactional(readOnly = true, rollbackFor = Exception.class)
	public <T> T getById(Class<T> entityClass, Serializable id) {
		return commonDao.getById(entityClass, id);
	}

	@Override
	@Transactional(readOnly = true, rollbackFor = Exception.class)
	public <T> T getByProperty(Class<T> entityClass, String propertyName, Object value) {
		return commonDao.getByProperty(entityClass, propertyName, value);
	}

	@Override
	@Transactional(readOnly = true, rollbackFor = Exception.class)
	public Map<String, Object> getMapBySql(String sql, Object...params) {
		return commonDao.getMapBySql(sql, params);
	}

	@Override
	@Transactional(readOnly = true, rollbackFor = Exception.class)
	public <T> T getBySql(String sql, Class<T> entityClass, Object...params) {
		return commonDao.getBySql(sql, entityClass, params);
	}

	@Override
	@Transactional(readOnly = true, rollbackFor = Exception.class)
	public <T> T getByHql(String hql, Object...params) {
		return commonDao.getByHql(hql, params);
	}

	@Override
	@Transactional(readOnly = true, rollbackFor = Exception.class)
	public <T> List<T> findList(final Class<T> entityClass) {
		return commonDao.findList(entityClass);
	}

	@Override
	@Transactional(readOnly = true, rollbackFor = Exception.class)
	public List<Object> findListBySqlReObject(final String sql, Object...params) {
		return commonDao.findListBySqlReObject(sql, params);
	}

	@Override
	@Transactional(readOnly = true, rollbackFor = Exception.class)
	public <T> List<T> findListBySql(String sql, Class<T> entityClass, Object... params) {
		return commonDao.findListBySql(sql,entityClass, params);
	}

	@Override
	@Transactional(readOnly = true, rollbackFor = Exception.class)
	public <T> List<T> findListBySqlWithPage(String sql, int curPage, int pageSize, Class<T> entityClass, Object...params) {
		return commonDao.findListBySqlWithPage(sql, curPage, pageSize, entityClass, params);
	}

	@Override
	@Transactional(readOnly = true, rollbackFor = Exception.class)
	public <T> List<T> findListByHql(String hql, Object...params) {
		return this.commonDao.findListByHql(hql, params);
	}

	@Override
	@Transactional(readOnly = true, rollbackFor = Exception.class)
	public <T> List<T> findListByProperty(Class<T> entityClass, String propertyName, Object value) {
		return commonDao.findListByProperty(entityClass, propertyName, value);
	}

	@Override
	@Transactional(readOnly = true, rollbackFor = Exception.class)
	public <T> List<T> findListByCriteriaQuery(final CriteriaQuery cq) {
		return commonDao.findListByCriteriaQuery(cq, false);
	}

	@Override
	@Transactional(readOnly = true, rollbackFor = Exception.class)
	public <T> List<T> findListByCriteriaQuery(final CriteriaQuery cq, Boolean isPage) {
		return commonDao.findListByCriteriaQuery(cq, isPage);
	}

	@Override
	@Transactional(readOnly = true, rollbackFor = Exception.class)
	public List findListByEntity(final String entityName, final Object exampleEntity) {
		return commonDao.findListByEntity(entityName, exampleEntity);
	}

	@Override
	@Transactional(readOnly = true, rollbackFor = Exception.class)
	public <T> List<T> getAutoList(Autocomplete autocomplete) {
		StringBuffer sb = new StringBuffer();
		for (String searchField : autocomplete.getSearchField().split(",")) {
			sb.append("  or " + searchField + " like '%"+ autocomplete.getTrem() + "%' ");
		}
		String hql = "from " + autocomplete.getEntityName() + " where 1!=1 "+ sb.toString();
		return commonDao.getSession().createQuery(hql).setFirstResult(autocomplete.getCurPage() - 1).setMaxResults(autocomplete.getMaxRows()).list();
	}

	@Override
	@Transactional(readOnly = true, rollbackFor = Exception.class)
	public <T> List<T> findListByDetached(DetachedCriteria dc) {
		return this.commonDao.findListByDetached(dc);
	}

	@Override
	@Transactional(readOnly = true, rollbackFor = Exception.class)
	public <T> List<T> findListByDetachedCriteria(DetachedCriteria dc, int firstResult, int maxResult) {
		return this.commonDao.findListByDetachedCriteria(dc, firstResult, maxResult);
	}

	@Override
	@Transactional(readOnly = true, rollbackFor = Exception.class)
	public List<Map<String, Object>> findListMapBySql(String sql, Object...params) {
		return commonDao.findListMapBySql(sql, params);
	}

	@Override
	@Transactional(readOnly = true, rollbackFor = Exception.class)
	public List<Map<String, Object>> findListMapBySqlWithPage(String sql, int curPage, int pageSize, Object...params) {
		return commonDao.findListMapBySqlWithPage(sql, curPage, pageSize, params);
	}

	@Override
	@Transactional(readOnly = true, rollbackFor = Exception.class)
	public PageList findPageByCriteriaQuery(final CriteriaQuery cq, final boolean isOffset) {
		return commonDao.findPageByCriteriaQuery(cq, isOffset);
	}

	@Override
	@Transactional(readOnly = true, rollbackFor = Exception.class)
	public PageList findPageByHqlQuery(final HqlQuery hqlQuery, final boolean needParameter) {
		return commonDao.findPageByHqlQuery(hqlQuery, needParameter);
	}

	@Override
	@Transactional(readOnly = true, rollbackFor = Exception.class)
	public PageList findPageByHqlQueryReToEntity(final HqlQuery hqlQuery, final boolean isToEntity) {
		return commonDao.findPageByHqlQueryReToEntity(hqlQuery, isToEntity);
	}

	@Override
	@Transactional(readOnly = true, rollbackFor = Exception.class)
	public DataTableReturn getDataTableReturn(final CriteriaQuery cq, final boolean isOffset) {
		return commonDao.getDataTableReturn(cq, isOffset);
	}

	@Override
	@Transactional(readOnly = true, rollbackFor = Exception.class)
	public void getDataGridReturn(final CriteriaQuery cq, final boolean isOffset) {
		commonDao.getDataGridReturn(cq, isOffset);
	}

	@Override
	public Integer executeSql(String sql, Object...params) {
		return commonDao.executeSql(sql, params);
	}

	@Override
	public Integer executeSql(String sql, Map<String, Object> paramMap) {
		return commonDao.executeSql(sql, paramMap);
	}

	@Override
	public Object executeSqlReturnKey(String sql, Map<String, Object> paramMap) {
		return commonDao.executeSqlReturnKey(sql, paramMap);
	}

	@Override
	public Integer executeHql(String hql, Object...params) {
		return commonDao.executeHql(hql, params);
	}

	@Override
	@Transactional(readOnly = true, rollbackFor = Exception.class)
	public Long getCountBySql(String sql, Object...params) {
		return commonDao.getCountBySql(sql, params);
	}

	@Override
	@Transactional(readOnly = true, rollbackFor = Exception.class)
	public List<DBTable> findDbTableList() {
		return commonDao.findDbTableList();
	}

	@Override
	@Transactional(readOnly = true, rollbackFor = Exception.class)
	public Integer getDbTableSize() {
		return commonDao.getDbTableSize();
	}

	@Override
	public Session getSession() {
		return commonDao.getSession();
	}

	@Override
	public <T> T uploadFile(UploadFile uploadFile) {
		return commonDao.uploadFile(uploadFile);
	}

	@Override
	@Transactional(readOnly = true, rollbackFor = Exception.class)
	public HttpServletResponse viewOrDownloadFile(UploadFile uploadFile) {
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
	@Transactional(readOnly = true, rollbackFor = Exception.class)
	public List<ComboTree> comTree(List<TSDepart> all, ComboTree comboTree) {
		return commonDao.comTree(all, comboTree);
	}

	@Override
	@Transactional(readOnly = true, rollbackFor = Exception.class)
	public List<ComboTree> comboTree(List all, ComboTreeModel comboTreeModel, List in, boolean recursive) {
		return commonDao.comboTree(all, comboTreeModel, in, recursive);
	}

	@Override
	@Transactional(readOnly = true, rollbackFor = Exception.class)
	public List<TreeGrid> treeGrid(List<?> all, TreeGridModel treeGridModel) {
		return commonDao.treeGrid(all, treeGridModel);
	}

	@Resource
	public void setCommonDao(CommonDao commonDao) {
		this.commonDao = commonDao;
	}

}
