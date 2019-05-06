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
import org.jeecgframework.core.common.model.common.DbTable;
import org.jeecgframework.core.common.model.common.UploadFile;
import org.jeecgframework.core.common.model.json.ComboTree;
import org.jeecgframework.core.common.model.json.ImportFile;
import org.jeecgframework.core.common.model.json.TreeGrid;
import org.jeecgframework.core.common.service.CommonService;
import org.jeecgframework.tag.vo.datatable.DataTableReturn;
import org.jeecgframework.tag.vo.easyui.Autocomplete;
import org.jeecgframework.tag.vo.easyui.ComboTreeModel;
import org.jeecgframework.tag.vo.easyui.TreeGridModel;
import org.jeecgframework.web.system.depart.entity.DepartEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 通用Service
 * @author DELL
 * @version V1.0
 * @date 2019-04-29
 */
@Service("commonService")
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
	public void deleteById(Class entityName, Serializable id) {
		commonDao.deleteById(entityName, id);
	}

	@Override
	public <T> void deleteByCollection(Collection<T> collection) {
		commonDao.deleteByCollection(collection);
	}

	@Override
	public <T> void update(T entity) {
		commonDao.update(entity);
	}

	@Override
	public int updateBySql(String sql) {
		return commonDao.updateBySql(sql);
	}

	@Override
	public <T> void saveOrUpdate(T entity) {
		commonDao.saveOrUpdate(entity);
	}

	@Override
	public <T> T getById(Class<T> entityClass, Serializable id) {
		return commonDao.getById(entityClass, id);
	}

	@Override
	public <T> T getByProperty(Class<T> entityClass, String propertyName, Object value) {
		return commonDao.getByProperty(entityClass, propertyName, value);
	}

	@Override
	public <T> T getBySql(String sql) {
		return null;
	}

	@Override
	public <T> T getByHql(String hql) {
		return commonDao.getByHql(hql);
	}

	@Override
	public <T> List<T> findList(Class<T> entityClass) {
		return commonDao.findList(entityClass);
	}

	@Override
	public <T> List<T> findListBySql(String sql) {
		return commonDao.findListBySql(sql);
	}

	@Override
	public <T> List<T> findListByHql(String hql) {
		return commonDao.findListByHql(hql);
	}

	@Override
	public <T> List<T> findListByProperty(Class<T> entityClass, String propertyName, Object value) {
		return commonDao.findListByProperty(entityClass, propertyName, value);
	}

	@Override
	public List<DbTable> findDbTableList() {
		return commonDao.findDbTableList();
	}

	@Override
	public Integer getDbTableSize() {
		return commonDao.getDbTableSize();
	}























	@Resource
	public void setCommonDao(CommonDao commonDao) {
		this.commonDao = commonDao;
	}

	


	


	


	/**
	 * 删除实体集合
	 * 
	 * @param <T>
	 * @param collection
	 */


	/**
	 * 根据实体名获取对象
	 */


	/**
	 * 根据实体名称和字段名称和字段值获取唯一记录
	 * 
	 * @param <T>
	 * @param entityClass
	 * @param propertyName
	 * @param value
	 * @return
	 */


	/**
	 * 按属性查找对象列表.
	 */


	/**
	 * 加载全部实体
	 * 
	 * @param <T>
	 * @param entityClass
	 * @return
	 */






	/**
	 * 删除实体主键ID删除对象
	 * 

	 */


	/**
	 * 更新指定的实体
	 * 
	 * @param <T>
	 * @param pojo
	 */


	/**
	 * 通过hql 查询语句查找对象
	 * 
	 * @param <T>
	 * @param hql
	 * @return
	 */


	/**
	 * 根据sql更新
	 * 
	 * @param sql
	 * @return
	 */


	/**
	 * 根据sql查找List
	 * 
	 * @param <T>
	 * @param sql
	 * @return
	 */



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
	 *            XML全路径
	 * @return
	 */
	@Override
	public HttpServletResponse createXml(ImportFile importFile) {
		return commonDao.createXml(importFile);
	}

	/**
	 * 解析XML文件
	 * 
	 * @param fileName
	 *            XML全路径
	 */
	@Override
	public void parserXml(String fileName) {
		commonDao.parserXml(fileName);
	}

	@Override
	@Transactional(readOnly = true)
	public List<ComboTree> comTree(List<DepartEntity> all, ComboTree comboTree) {
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
		return commonDao.findListMapBySql(sql, page, rows);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Map<String, Object>> findForJdbc(String sql, Object... objs) {
		return commonDao.findListMapBySql(sql);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Map<String, Object>> findForJdbcParam(String sql, int page, int rows, Object... objs) {
		return commonDao.findListMapBySql(sql, page, rows);
	}

	@Override
	@Transactional(readOnly = true)
	public <T> List<T> findObjForJdbc(String sql, int page, int rows, Class<T> clazz) {
		return commonDao.findListBySql(sql, page, rows, clazz);
	}

	@Override
	@Transactional(readOnly = true)
	public Map<String, Object> findOneForJdbc(String sql, Object... objs) {
		return commonDao.getBySql(sql, objs);
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
		return this.commonDao.pageList(dc, firstResult, maxResult);
	}

	@Override
	@Transactional(readOnly = true)
	public <T> List<T> findByDetached(DetachedCriteria dc) {
		return this.commonDao.findByDetached(dc);
	}

	/**
	 * 调用存储过程
	 */
	@Override
	public <T> List<T> executeProcedure(String procedureSql, Object... params) {
		return this.commonDao.executeProcedure(procedureSql, params);
	}

}
