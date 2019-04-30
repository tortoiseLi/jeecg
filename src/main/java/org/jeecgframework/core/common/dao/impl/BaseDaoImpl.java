package org.jeecgframework.core.common.dao.impl;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.*;
import org.hibernate.criterion.*;
import org.hibernate.internal.CriteriaImpl;
import org.hibernate.metadata.ClassMetadata;
import org.hibernate.persister.entity.AbstractEntityPersister;
import org.hibernate.type.Type;
import org.jeecgframework.core.annotation.JeecgEntityTitle;
import org.jeecgframework.core.common.dao.BaseDao;
import org.jeecgframework.core.common.dao.jdbc.JdbcDao;
import org.jeecgframework.core.common.exception.BusinessException;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.hibernate.qbc.HqlQuery;
import org.jeecgframework.core.common.hibernate.qbc.PageList;
import org.jeecgframework.core.common.hibernate.qbc.PagerUtil;
import org.jeecgframework.core.common.model.common.DbTable;
import org.jeecgframework.core.util.MyBeanUtils;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.core.util.ToEntityUtil;
import org.jeecgframework.core.util.oConvertUtils;
import org.jeecgframework.tag.vo.datatable.DataTableReturn;
import org.jeecgframework.tag.vo.datatable.SortDirection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
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
import java.util.*;


/**
 * DAO层泛型基类接口
 * @author DELL
 * @date 2019-04-28
 * @version V1.0
 */
public abstract class BaseDaoImpl<T> implements BaseDao {

	private static final Logger logger = Logger.getLogger(BaseDaoImpl.class);

	@Autowired
	@Qualifier("sessionFactory")
	private SessionFactory sessionFactory;

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
	public <T> T getByProperty(Class<T> entityClass, String propertyName, Object value) {
		Assert.hasText(propertyName);
		return (T) createCriteria(entityClass,
				Restrictions.eq(propertyName, value)).uniqueResult();
	}

	@Override
	public <T> List<T> findListByProperty(Class<T> entityClass,
									  String propertyName, Object value) {
		Assert.hasText(propertyName);
		return (List<T>) createCriteria(entityClass,
				Restrictions.eq(propertyName, value)).list();
	}


	@Override
	public <T> Serializable insert(T entity) {
		try {
			Serializable id = getSession().save(entity);
			//getSession().flush();
			if (logger.isDebugEnabled()) {
				logger.debug("保存实体成功," + entity.getClass().getName());
			}
			return id;
		} catch (RuntimeException e) {
			logger.error("保存实体异常", e);
			throw e;
		}

	}


	@Override
	public <T> void batchInsert(List<T> entitys) {
		for (int i = 0; i < entitys.size(); i++) {
			getSession().save(entitys.get(i));
			if (i % 1000 == 0) {
				// 1000个对象批量写入数据库，后才清理缓存
				getSession().flush();
				getSession().clear();
			}
		}
		//最后页面的数据，进行提交手工清理
		getSession().flush();
		getSession().clear();
	}



	@Override
	public <T> void saveOrUpdate(T entity) {
		try {
			getSession().saveOrUpdate(entity);
			//getSession().flush();
			if (logger.isDebugEnabled()) {
				logger.debug("添加或更新成功," + entity.getClass().getName());
			}
		} catch (RuntimeException e) {
			logger.error("添加或更新异常", e);
			throw e;
		}
	}


	@Override
	public <T> void delete(T entity) {
		try {
			getSession().delete(entity);
			//getSession().flush();
			if (logger.isDebugEnabled()) {
				logger.debug("删除成功," + entity.getClass().getName());
			}
		} catch (RuntimeException e) {
			logger.error("删除异常", e);
			throw e;
		}
	}


	@Override
	public void deleteById(Class entityName, Serializable id) {
		delete(getById(entityName, id));
		//getSession().flush();
	}


	@Override
	public <T> void deleteByCollection(Collection<T> collection) {
		for (Object entity : collection) {
			getSession().delete(entity);
			//getSession().flush();
		}
	}


	@Override
	public <T> T getById(Class<T> entityClass, final Serializable id) {

		return (T) getSession().get(entityClass, id);

	}



	@Override
	public <T> void update(T entity) {
		getSession().update(entity);
		//getSession().flush();
	}


	public <T> void updateEntitie(String className, Object id) {
		getSession().update(className, id);
		//getSession().flush();
	}



	@Override
	public List<T> findListByHql(final String hql) {

		Query queryObject = getSession().createQuery(hql);
		List<T> list = queryObject.list();

		return list;

	}

	@Override
	public <T> T getByHql(String hql) {
		T t = null;
		Query queryObject = getSession().createQuery(hql);
		List<T> list = queryObject.list();
		if (list.size() == 1) {
			//getSession().flush();
			t = list.get(0);
		} else if (list.size() > 0) {
			throw new BusinessException("查询结果数:" + list.size() + "大于1");
		}
		return t;
	}


	@Override
	public Map<Object, Object> getHashMapbyQuery(String hql) {

		Query query = getSession().createQuery(hql);
		List list = query.list();
		Map<Object, Object> map = new HashMap<Object, Object>();
		for (Iterator iterator = list.iterator(); iterator.hasNext();) {
			Object[] tm = (Object[]) iterator.next();
			map.put(tm[0].toString(), tm[1].toString());
		}
		return map;

	}


	@Override
	public int updateBySql(final String sql) {

		Query querys = getSession().createSQLQuery(sql);
		return querys.executeUpdate();
	}

	@Override
	public List<T> findListBySql(final String sql) {
		Query querys = getSession().createSQLQuery(sql);
		return querys.list();
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

	@Override
	public <T> List<T> findList(Class<T> entityClass) {
		Criteria criteria = createCriteria(entityClass);
		return criteria.list();
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


	@Override
	public List findByExample(final String entityName,
							  final Object exampleEntity) {
		Assert.notNull(exampleEntity, "Example entity must not be null");
		Criteria executableCriteria = (entityName != null ? getSession()
				.createCriteria(entityName) : getSession().createCriteria(
				exampleEntity.getClass()));
		executableCriteria.add(Example.create(exampleEntity));
		return executableCriteria.list();
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

		//DetachedCriteriaUtil.selectColumn(cq.getDetachedCriteria(), cq.getField().split(","), cq.getEntityClass(), false);

		
		return new DataTableReturn(allCounts, allCounts, cq.getDataTables().getEcho(), criteria.list());
	}

	@Override
	public void getDataGridReturn(CriteriaQuery cq, final boolean isOffset) {

		Criteria criteria = cq.getDetachedCriteria().getExecutableCriteria(getSession());
		CriteriaImpl impl = (CriteriaImpl) criteria;
		// 先把Projection和OrderBy条件取出来,清空两者来执行Count操作
		Projection projection = impl.getProjection();

//		final int allCounts = ((Long) criteria.setProjection(Projections.rowCount()).uniqueResult()).intValue();
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


		// 判断是否有排序字段
//		if (!cq.getOrdermap().isEmpty()) {
//			cq.setOrder(cq.getOrdermap());
//		}
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

	@Autowired
	@Qualifier("jdbcTemplate")
	private JdbcTemplate jdbcTemplate;

	@Autowired
	@Qualifier("namedParameterJdbcTemplate")
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	@Override
	public List<Map<String, Object>> findForJdbc(String sql, int page, int rows) {
		// 封装分页SQL
		sql = JdbcDao.jeecgCreatePageSql(sql, page, rows);
		return this.jdbcTemplate.queryForList(sql);
	}

	@Override
	public <T> List<T> findObjForJdbc(String sql, int page, int rows,
									  Class<T> clazz) {
		List<T> rsList = new ArrayList<T>();
		// 封装分页SQL
		sql = JdbcDao.jeecgCreatePageSql(sql, page, rows);
		List<Map<String, Object>> mapList = jdbcTemplate.queryForList(sql);

		T po = null;
		for (Map<String, Object> m : mapList) {
			try {
				po = clazz.newInstance();
				MyBeanUtils.copyMap2Bean_Nobig(po, m);
				rsList.add(po);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return rsList;
	}

	@Override
	public List<Map<String, Object>> findForJdbcParam(String sql, int page,
													  int rows, Object... objs) {
		// 封装分页SQL
		sql = JdbcDao.jeecgCreatePageSql(sql, page, rows);
		return this.jdbcTemplate.queryForList(sql, objs);
	}

	@Override
	public Long getCountForJdbc(String sql) {
		return this.jdbcTemplate.queryForObject(sql,Long.class);
	}

	@Override
	public Long getCountForJdbcParam(String sql, Object[] objs) {
		return this.jdbcTemplate.queryForObject(sql, objs,Long.class);
	}

	@Override
	public List<Map<String, Object>> findForJdbc(String sql, Object... objs) {
		return this.jdbcTemplate.queryForList(sql, objs);
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

	public Integer countByJdbc(String sql, Object... param) {

		return this.jdbcTemplate.queryForObject(sql, param,Integer.class);


	}

	@Override
	public Map<String, Object> findOneForJdbc(String sql, Object... objs) {
		try {
			return this.jdbcTemplate.queryForMap(sql, objs);
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	/**
	 * 通过hql 查询语句查找对象
	 *
	 * @param <T>
	 * @return
	 */
	@Override
	public <T> List<T> findHql(String hql, Object... param) {
		Query q = getSession().createQuery(hql);
		if (param != null && param.length > 0) {
			for (int i = 0; i < param.length; i++) {
				q.setParameter(i, param[i]);
			}
		}
		return q.list();
	}

	/**
	 * 执行HQL语句操作更新
	 *
	 * @param hql
	 * @return
	 */
	@Override
	public Integer executeHql(String hql) {
		Query q = getSession().createQuery(hql);
		return q.executeUpdate();
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

	/**
	 * 离线查询
	 */
	@Override
	public <T> List<T> findByDetached(DetachedCriteria dc) {
		return dc.getExecutableCriteria(getSession()).list();
	}

	/**
	 * 调用存储过程
	 */
	@Override
	@SuppressWarnings({ "unchecked",})
	public <T> List<T> executeProcedure(String executeSql,Object... params) {
		SQLQuery sqlQuery = getSession().createSQLQuery(executeSql);
		
		for(int i=0;i<params.length;i++){
			sqlQuery.setParameter(i, params[i]);
		}
		
		return sqlQuery.list();
	}

}
