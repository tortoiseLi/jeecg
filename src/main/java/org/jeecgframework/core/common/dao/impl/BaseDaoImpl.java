package org.jeecgframework.core.common.dao.impl;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.*;
import org.hibernate.criterion.*;
import org.hibernate.internal.CriteriaImpl;
import org.hibernate.metadata.ClassMetadata;
import org.hibernate.persister.entity.AbstractEntityPersister;
import org.jeecgframework.core.annotation.JeecgEntityTitle;
import org.jeecgframework.core.common.dao.BaseDao;
import org.jeecgframework.core.common.dao.jdbc.JdbcDao;
import org.jeecgframework.core.common.exception.BusinessException;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.hibernate.qbc.PageList;
import org.jeecgframework.core.common.hibernate.qbc.PagerUtil;
import org.jeecgframework.core.common.model.common.DbTable;
import org.jeecgframework.core.util.MyBeanUtils;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.core.util.oConvertUtils;
import org.jeecgframework.tag.vo.datatable.DataTableReturn;
import org.jeecgframework.tag.vo.datatable.SortDirection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.util.Assert;

import java.io.Serializable;
import java.lang.InstantiationException;
import java.lang.reflect.InvocationTargetException;
import java.util.*;


/**
 * DAO层泛型基类接口
 * @author DELL
 * @date 2019-04-28
 * @version V1.0
 */
public abstract class BaseDaoImpl<T> implements BaseDao {

	/**
	 * log4j
	 */
	private static final Logger logger = Logger.getLogger(BaseDaoImpl.class);

	@Autowired
	@Qualifier("sessionFactory")
	private SessionFactory sessionFactory;

	@Override
	public <T> Serializable insert(T entity) {
		try {
			Serializable id = getSession().save(entity);
			return id;
		} catch (RuntimeException e) {
			logger.error("保存实体异常", e);
			throw e;
		}
	}

	@Override
	public <T> void batchInsert(List<T> entityList) {
		try {
			for (int i=0,len=entityList.size(); i<len; i++) {
				getSession().save(entityList.get(i));
				if (i % 1000 == 0) {
					// 1000个对象批量写入数据库才清理缓存
					getSession().flush();
					getSession().clear();
				}
			}
			// 最后页面的数据，进行提交手工清理
			getSession().flush();
			getSession().clear();
		} catch (RuntimeException e) {
			logger.error("批量保存实体异常", e);
			throw e;
		}
	}

	@Override
	public <T> void delete(T entity) {
		try {
			getSession().delete(entity);
		} catch (RuntimeException e) {
			logger.error("删除异常", e);
			throw e;
		}
	}

	@Override
	public void deleteById(Class entityClass, Serializable id) {
		try {
			delete(getById(entityClass, id));
		} catch (RuntimeException e) {
			logger.error("根据ID删除实体异常", e);
			throw e;
		}
	}

	@Override
	public <T> void deleteByCollection(Collection<T> collection) {
		try {
			for (Object entity : collection) {
				getSession().delete(entity);
			}
		} catch (RuntimeException e) {
			logger.error("批量删除实体异常", e);
			throw e;
		}
	}

	@Override
	public <T> void update(T entity) {
		try {
			getSession().update(entity);
		}  catch (RuntimeException e) {
			logger.error("更新实体异常", e);
			throw e;
		}
	}

	@Override
	public int updateBySql(final String sql) {
		try {
			Query query = getSession().createSQLQuery(sql);
			return query.executeUpdate();
		} catch (RuntimeException e) {
			logger.error("根据SQL更新实体异常", e);
			throw e;
		}
	}

	@Override
	public <T> void saveOrUpdate(T entity) {
		try {
			getSession().saveOrUpdate(entity);
		} catch (RuntimeException e) {
			logger.error("添加或更新异常", e);
			throw e;
		}
	}

	@Override
	public <T> T getById(Class<T> entityClass, final Serializable id) {
		try {
			return (T) getSession().get(entityClass, id);
		} catch (RuntimeException e) {
			logger.error("根据ID查询实体异常", e);
			throw e;
		}
	}

	@Override
	public <T> T getByProperty(Class<T> entityClass, String propertyName, Object value) {
		try {
			Assert.hasText(propertyName);
			return (T) createCriteria(entityClass, Restrictions.eq(propertyName, value)).uniqueResult();
		} catch (RuntimeException e) {
			logger.error("根据属性查询实体异常", e);
			throw e;
		}
	}

	@Override
	public Map<String, Object> getBySql(String sql, Object...params) {
		try {
			return this.jdbcTemplate.queryForMap(sql, params);
		} catch (EmptyResultDataAccessException e) {
			logger.error("根据SQL查询Map异常", e);
			throw e;
		}
	}

	@Override
	public <T> T getByHql(String hql, Object...params) {
		try {
			T t = null;
			Query query = getSession().createQuery(hql);

			// 设置查询条件
			if (null != params && params.length > 0) {
				for (int i=0,len=params.length; i<len; i++) {
					query.setParameter(i, params[i]);
				}
			}

			List<T> list = query.list();
			if (list.size() == 1) {
				t = list.get(0);
			} else if (list.size() > 0) {
				throw new BusinessException("查询结果数:" + list.size() + "大于1");
			}
			return t;
		} catch (RuntimeException e) {
			logger.error("根据HQL查询实体异常", e);
			throw e;
		}
	}

	@Override
	public <T> List<T> findList(Class<T> entityClass) {
		try {
			Criteria criteria = createCriteria(entityClass);
			return criteria.list();
		} catch (RuntimeException e) {
			logger.error("查询实体列表异常", e);
			throw e;
		}
	}

	@Override
	public List<T> findListBySql(final String sql) {
		try {
			Query query = getSession().createSQLQuery(sql);
			return query.list();
		} catch (RuntimeException e) {
			logger.error("根据SQL查询实体列表异常", e);
			throw e;
		}
	}

	@Override
	public <T> List<T> findListBySql(String sql, Class<T> entityClass) {
		try {
			List<T> rsList = new ArrayList<>();
			// 封装分页SQL
			List<Map<String, Object>> mapList = jdbcTemplate.queryForList(sql);

			T po;
			for (Map<String, Object> m : mapList) {
				po = entityClass.newInstance();
				MyBeanUtils.copyMap2Bean_Nobig(po, m);
				rsList.add(po);
			}
			return rsList;
		} catch (Exception e) {
			logger.error("根据SQL查询实体列表异常", e);
			throw new RuntimeException(e);
		}
	}

	@Override
	public <T> List<T> findListBySql(String sql, int curPage, int pageSize, Class<T> entityClass) {
		try {
			sql = JdbcDao.jeecgCreatePageSql(sql, curPage, pageSize);
		} catch (RuntimeException e) {
			logger.error("根据SQL查询实体列表异常", e);
			throw e;
		}
		return findListBySql(sql, entityClass);
	}

	@Override
	public <T> List<T> findListByHql(String hql, Object... params) {
		try {
			Query q = getSession().createQuery(hql);

			// 组装查询条件
			if (null != params && params.length > 0) {
				for (int i = 0; i < params.length; i++) {
					q.setParameter(i, params[i]);
				}
			}
			return q.list();
		} catch (RuntimeException e) {
			logger.error("根据HQL查询实体列表异常", e);
			throw e;
		}
	}

	@Override
	public <T> List<T> findListByProperty(Class<T> entityClass,
										  String propertyName, Object value) {
		Assert.hasText(propertyName);
		return (List<T>) createCriteria(entityClass,
				Restrictions.eq(propertyName, value)).list();
	}

	@Override
	public List<T> findListByCriteriaQuery(final CriteriaQuery cq, Boolean isPage) {
		Criteria criteria = cq.getDetachedCriteria().getExecutableCriteria(
				getSession());
		// 判断是否有排序字段
		if (cq.getOrdermap() != null) {
			cq.setOrder(cq.getOrdermap());
		}
		if (isPage){
			criteria.setFirstResult((cq.getCurPage()-1)*cq.getPageSize());
			criteria.setMaxResults(cq.getPageSize());
		}
		return criteria.list();

	}

	@Override
	public List findListByEntity(final String entityName,
								 final Object exampleEntity) {
		Assert.notNull(exampleEntity, "Example entity must not be null");
		Criteria executableCriteria = (entityName != null ? getSession()
				.createCriteria(entityName) : getSession().createCriteria(
				exampleEntity.getClass()));
		executableCriteria.add(Example.create(exampleEntity));
		return executableCriteria.list();
	}

	@Override
	public List<Map<String, Object>> findListMapBySql(String sql) {
		return this.jdbcTemplate.queryForList(sql);
	}

	@Override
	public List<Map<String, Object>> findListMapBySql(String sql, int page,
													  int rows, Object... objs) {
		// 封装分页SQL
		sql = JdbcDao.jeecgCreatePageSql(sql, page, rows);
		return this.jdbcTemplate.queryForList(sql, objs);
	}

	@Override
	public PageList findPageByCriteriaQuery(final CriteriaQuery cq, final boolean isOffset) {

		Criteria criteria = cq.getDetachedCriteria().getExecutableCriteria(
				getSession());
		CriteriaImpl impl = (CriteriaImpl) criteria;
		// 先把Projection和OrderBy条件取出来,清空两者来执行Count操作
		Projection projection = impl.getProjection();
		final int allCounts = ((Long) criteria.setProjection(
				Projections.rowCount()).uniqueResult()).intValue();
		criteria.setProjection(projection);
		if (projection == null) {
			criteria.setResultTransformer(CriteriaSpecification.ROOT_ENTITY);
		}

		// 判断是否有排序字段
		if (cq.getOrdermap() != null) {
			cq.setOrder(cq.getOrdermap());
		}
		int pageSize = cq.getPageSize();// 每页显示数
		int curPageNO = PagerUtil.getcurPageNo(allCounts, cq.getCurPage(),
				pageSize);// 当前页
		int offset = PagerUtil.getOffset(allCounts, curPageNO, pageSize);
		String toolBar = "";
		if (isOffset) {// 是否分页
			criteria.setFirstResult(offset);
			criteria.setMaxResults(cq.getPageSize());
			if (cq.getIsUseimage() == 1) {
				toolBar = PagerUtil.getBar(cq.getMyAction(), cq.getMyForm(),
						allCounts, curPageNO, pageSize, cq.getMap());
			} else {
				toolBar = PagerUtil.getBar(cq.getMyAction(), allCounts,
						curPageNO, pageSize, cq.getMap());
			}
		} else {
			pageSize = allCounts;
		}
		return new PageList(criteria.list(), toolBar, offset, curPageNO,
				allCounts);
	}

	@Override
	public DataTableReturn getDataTableReturn(final CriteriaQuery cq,
											  final boolean isOffset) {

		Criteria criteria = cq.getDetachedCriteria().getExecutableCriteria(
				getSession());
		CriteriaImpl impl = (CriteriaImpl) criteria;
		// 先把Projection和OrderBy条件取出来,清空两者来执行Count操作
		Projection projection = impl.getProjection();
		final int allCounts = ((Long) criteria.setProjection(
				Projections.rowCount()).uniqueResult()).intValue();
		criteria.setProjection(projection);
		if (projection == null) {
			criteria.setResultTransformer(CriteriaSpecification.ROOT_ENTITY);
		}

		// 判断是否有排序字段
		if (cq.getOrdermap() != null) {
			cq.setOrder(cq.getOrdermap());
		}
		int pageSize = cq.getPageSize();// 每页显示数
		int curPageNO = PagerUtil.getcurPageNo(allCounts, cq.getCurPage(),
				pageSize);// 当前页
		int offset = PagerUtil.getOffset(allCounts, curPageNO, pageSize);
		if (isOffset) {// 是否分页
			criteria.setFirstResult(offset);
			criteria.setMaxResults(cq.getPageSize());
		} else {
			pageSize = allCounts;
		}



		return new DataTableReturn(allCounts, allCounts, cq.getDataTables().getEcho(), criteria.list());
	}

	@Override
	public void getDataGridReturn(CriteriaQuery cq, final boolean isOffset) {

		Criteria criteria = cq.getDetachedCriteria().getExecutableCriteria(getSession());
		CriteriaImpl impl = (CriteriaImpl) criteria;
		// 先把Projection和OrderBy条件取出来,清空两者来执行Count操作
		Projection projection = impl.getProjection();

		Object allCountsObj = criteria.setProjection(Projections.rowCount()).uniqueResult();
		final int allCounts;
		if(allCountsObj==null){
			allCounts = 0;
		}else{
			allCounts = ((Long) allCountsObj).intValue();
		}

		criteria.setProjection(projection);
		if (projection == null) {
			criteria.setResultTransformer(CriteriaSpecification.ROOT_ENTITY);
		}

		Map<String, Object> ordermap = cq.getOrdermap();
		if(ordermap==null){
			ordermap = new LinkedHashMap<String, Object>();
		}

		String sort = cq.getDataGrid().getSort();
		if (StringUtils.isNotBlank(sort)) {
			String []sortArr = sort.split(",");
			String []orderArr = cq.getDataGrid().getOrder().split(",");
			if(sortArr.length != orderArr.length && orderArr.length > 0){
				for (int i = 0; i < sortArr.length; i++) {
					if(SortDirection.asc.equals(SortDirection.toEnum(orderArr[0]))){
						ordermap.put(sortArr[i], SortDirection.asc);
					}else{
						ordermap.put(sortArr[i], SortDirection.desc);
					}
				}
			}else if(sortArr.length == orderArr.length){
				for (int i = 0; i < sortArr.length; i++) {
					if(SortDirection.asc.equals(SortDirection.toEnum(orderArr[i]))){
						ordermap.put(sortArr[i], SortDirection.asc);
					}else{
						ordermap.put(sortArr[i], SortDirection.desc);
					}
				}
			}
		}
		if(!ordermap.isEmpty() && ordermap.size()>0){
			cq.setOrder(ordermap);
		}


		int pageSize = cq.getPageSize();// 每页显示数
		int curPageNO = PagerUtil.getcurPageNo(allCounts, cq.getCurPage(),pageSize);// 当前页
		int offset = PagerUtil.getOffset(allCounts, curPageNO, pageSize);
		if (isOffset) {// 是否分页
			criteria.setFirstResult(offset);
			criteria.setMaxResults(cq.getPageSize());
		} else {
			pageSize = allCounts;
		}
		// DetachedCriteriaUtil.selectColumn(cq.getDetachedCriteria(),
		// cq.getField().split(","), cq.getClass1(), false);
		List<?> list = criteria.list();
		cq.getDataGrid().setResults(list);
		cq.getDataGrid().setTotal(allCounts);

		cq.clear();
		cq = null;

		//return new DataGridReturn(allCounts, list);

	}

	@Override
	public Integer executeSql(String sql, List<Object> param) {
		return this.jdbcTemplate.update(sql, param);
	}

	@Override
	public Integer executeSql(String sql, Object... param) {
		return this.jdbcTemplate.update(sql, param);
	}

	@Override
	public Integer executeSql(String sql, Map<String, Object> param) {
		return this.namedParameterJdbcTemplate.update(sql, param);
	}

	@Override
	public Object executeSqlReturnKey(final String sql, Map<String, Object> param) {
		Object keyValue = null;
		KeyHolder keyHolder = null;
		SqlParameterSource sqlp  = new MapSqlParameterSource(param);
		if (StringUtil.isNotEmpty(param.get("id"))) {//表示已经生成过id(UUID),则表示是非序列或数据库自增的形式
			this.namedParameterJdbcTemplate.update(sql,sqlp);
			//--author：zhoujf---start------date:20170216--------for:自定义表单保存数据格sqlserver报错问题
		}else if (StringUtil.isNotEmpty(param.get("ID"))) {//表示已经生成过id(UUID),则表示是非序列或数据库自增的形式
			this.namedParameterJdbcTemplate.update(sql,sqlp);
		}else{//NATIVE or SEQUENCE
			keyHolder = new GeneratedKeyHolder();
			this.namedParameterJdbcTemplate.update(sql,sqlp, keyHolder, new String[]{"id"});
			Number number = keyHolder.getKey();
			if(oConvertUtils.isNotEmpty(number)){
				keyValue = keyHolder.getKey().longValue();
			}
		}
		return keyValue;
	}

	@Override
	public Integer executeHql(String hql) {
		Query q = getSession().createQuery(hql);
		return q.executeUpdate();
	}

	@Override
	public Integer executeHql(String hql, Object...params) {
		Query q = getSession().createQuery(hql);
		if (params != null && params.length > 0) {
			for (int i = 0; i < params.length; i++) {
				q.setParameter(i, params[i]);
			}
		}
		return q.executeUpdate();
	}

	@Override
	public Long getCountBySql(String sql, Object...params) {
		return this.jdbcTemplate.queryForObject(sql, params,Long.class);
	}

	@Override
	public <T> List<T> pageList(DetachedCriteria dc, int firstResult,
								int maxResult) {
		Criteria criteria = dc.getExecutableCriteria(getSession());
		criteria.setResultTransformer(CriteriaSpecification.ROOT_ENTITY);
		criteria.setFirstResult(firstResult);
		criteria.setMaxResults(maxResult);
		return criteria.list();
	}

	@Override
	public <T> List<T> findByDetached(DetachedCriteria dc) {
		return dc.getExecutableCriteria(getSession()).list();
	}

	@Override
	public <T> List<T> executeProcedure(String executeSql,Object... params) {
		SQLQuery sqlQuery = getSession().createSQLQuery(executeSql);

		for(int i=0;i<params.length;i++){
			sqlQuery.setParameter(i, params[i]);
		}

		return sqlQuery.list();
	}

	@Override
	public List<DbTable> findDbTableList() {
		List<DbTable> resultList = new ArrayList<DbTable>();
		SessionFactory factory = getSession().getSessionFactory();
		Map<String, ClassMetadata> metaMap = factory.getAllClassMetadata();
		for (String key : (Set<String>) metaMap.keySet()) {
			DbTable dbTable = new DbTable();
			AbstractEntityPersister classMetadata = (AbstractEntityPersister) metaMap
					.get(key);
			dbTable.setTableName(classMetadata.getTableName());
			dbTable.setEntityName(classMetadata.getEntityName());
			Class<?> c;
			try {
				c = Class.forName(key);
				JeecgEntityTitle t = c.getAnnotation(JeecgEntityTitle.class);
				dbTable.setTableTitle(t != null ? t.name() : "");
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			resultList.add(dbTable);
		}
		return resultList;
	}

	@Override
	public Integer getDbTableSize() {
		SessionFactory factory = getSession().getSessionFactory();
		Map<String, ClassMetadata> metaMap = factory.getAllClassMetadata();
		return metaMap.size();
	}

	@Override
	public Session getSession() {
		return sessionFactory.getCurrentSession();
	}














































































































	private <T> void getProperty(Class entityName) {
		ClassMetadata cm = sessionFactory.getClassMetadata(entityName);
		String[] str = cm.getPropertyNames(); // 获得该类所有的属性名称
		for (int i = 0; i < str.length; i++) {
			String property = str[i];
			String type = cm.getPropertyType(property).getName(); // 获得该名称的类型
			org.jeecgframework.core.util.LogUtil.info(property + "---&gt;" + type);
		}
	}


	public <T> void updateEntitie(String className, Object id) {
		getSession().update(className, id);
		//getSession().flush();
	}


	private <T> Criteria createCriteria(Class<T> entityClass, boolean isAsc, Criterion... criterions) {
		Criteria criteria = createCriteria(entityClass, criterions);
		if (isAsc) {
			criteria.addOrder(Order.asc("asc"));
		} else {
			criteria.addOrder(Order.desc("desc"));
		}
		return criteria;
	}

	private <T> Criteria createCriteria(Class<T> entityClass,
			Criterion... criterions) {
		Criteria criteria = getSession().createCriteria(entityClass);
		for (Criterion c : criterions) {
			criteria.add(c);
		}
		return criteria;
	}

	private <T> Criteria createCriteria(Class<T> entityClass) {
		Criteria criteria = getSession().createCriteria(entityClass);
		return criteria;
	}
	public <T> T findUniqueBy(Class<T> entityClass, String propertyName,
			Object value) {
		Assert.hasText(propertyName);
		return (T) createCriteria(entityClass,
				Restrictions.eq(propertyName, value)).uniqueResult();
	}

	public Query createQuery(Session session, String hql, Object... objects) {
		Query query = session.createQuery(hql);
		if (objects != null) {
			for (int i = 0; i < objects.length; i++) {
				query.setParameter(i, objects[i]);
			}
		}
		return query;
	}

	public <T> int batchInsertsEntitie(List<T> entityList) {
		int num = 0;
		for (int i = 0; i < entityList.size(); i++) {
			insert(entityList.get(i));
			num++;
		}
		return num;
	}


	public List<T> executeQuery(final String hql, final Object[] values) {
		Query query = getSession().createQuery(hql);
		// query.setCacheable(true);
		for (int i = 0; values != null && i < values.length; i++) {
			query.setParameter(i, values[i]);
		}

		return query.list();

	}

	// 使用指定的检索标准获取满足标准的记录数
	public Integer getRowCount(DetachedCriteria criteria) {
		return oConvertUtils.getInt(((Criteria) criteria
				.setProjection(Projections.rowCount())).uniqueResult(), 0);
	}

	public void callableStatementByName(String proc) {
	}

	public int getCount(Class<T> clazz) {

		int count = DataAccessUtils.intResult(getSession().createQuery(
				"select count(*) from " + clazz.getName()).list());
		return count;
	}

	public Integer countByJdbc(String sql, Object... param) {

		return this.jdbcTemplate.queryForObject(sql, param,Integer.class);


	}

	@Autowired
	@Qualifier("jdbcTemplate")
	private JdbcTemplate jdbcTemplate;

	@Autowired
	@Qualifier("namedParameterJdbcTemplate")
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

}
