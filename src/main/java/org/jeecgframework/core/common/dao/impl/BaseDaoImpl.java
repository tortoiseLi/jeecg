package org.jeecgframework.core.common.dao.impl;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.*;
import org.hibernate.internal.CriteriaImpl;
import org.hibernate.metadata.ClassMetadata;
import org.hibernate.persister.entity.AbstractEntityPersister;
import org.jeecgframework.core.annotation.JeecgEntityTitle;
import org.jeecgframework.core.common.dao.BaseDao;
import org.jeecgframework.core.common.dao.jdbc.JdbcDao;
import org.jeecgframework.core.common.exception.BusinessException;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.hibernate.qbc.HqlQuery;
import org.jeecgframework.core.common.hibernate.qbc.PageList;
import org.jeecgframework.core.common.hibernate.qbc.PagerUtil;
import org.jeecgframework.core.common.model.common.DBTable;
import org.jeecgframework.core.util.MyBeanUtils;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.core.util.ToEntityUtil;
import org.jeecgframework.core.util.oConvertUtils;
import org.jeecgframework.tag.vo.datatable.DataTableReturn;
import org.jeecgframework.tag.vo.datatable.SortDirection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.util.Assert;

import java.io.Serializable;
import java.util.*;

/**
 * DAO层泛型基类
 * @param <T>
 * @author DELL
 * @date 2019-05-06
 * @version V1.0
 */
@SuppressWarnings("hiding")
public abstract class BaseDaoImpl<T> implements BaseDao {

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
		for (int i=0; i<entityList.size(); i++) {
			getSession().save(entityList.get(i));
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
		delete(getById(entityClass, id));
	}

	@Override
	public <T> void deleteCollection(Collection<T> collection) {
		for (Object entity: collection) {
			getSession().delete(entity);
		}
	}

	@Override
	public <T> void update(T entity) {
		getSession().update(entity);
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
		return (T) getSession().get(entityClass, id);
	}

	@Override
	public <T> T getByProperty(Class<T> entityClass, String propertyName, Object value) {
		Assert.hasText(propertyName);
		return (T) createCriteria(entityClass, Restrictions.eq(propertyName, value)).uniqueResult();
	}

	@Override
	public Map<String, Object> getMapBySql(String sql, Object...params) {
		try {
			return this.jdbcTemplate.queryForMap(sql, params);
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	@Override
	public <T> T getBySql(String sql, Class<T> entityClass, Object...params) {
		Map<String, Object> map = getMapBySql(sql, params);

		if (null == map) {
			return null;
		}

		// 处理结果,封装到entity
		T entity = null;
		try {
			entity = entityClass.newInstance();
			MyBeanUtils.copyMap2Bean_Nobig(entity, map);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return entity;
	}

	@Override
	public <T> T getByHql(String hql, Object...params) {
		T entity = null;

		Query query = getSession().createQuery(hql);
		if (null != params && params.length > 0) {
			for (int i=0; i<params.length; i++) {
				query.setParameter(i, params[i]);
			}
		}

		List<T> list = query.list();
		if (list.size() == 1) {
			entity = list.get(0);
		} else if (list.size() > 0) {
			throw new BusinessException("查询结果数:" + list.size() + "大于1");
		}
		return entity;
	}

	@Override
	public <T> List<T> findList(final Class<T> entityClass) {
		Criteria criteria = createCriteria(entityClass);
		return criteria.list();
	}

	@Override
	public List<Object> findListBySqlReObject(final String sql, Object...params) {
		Query query = getSession().createSQLQuery(sql);

		// 拼接参数
		if (null != params) {
			for (int i=0; i<params.length; i++) {
				query.setParameter(i, params[i]);
			}
		}

		return query.list();
	}

	@Override
	public <T> List<T> findListBySql(String sql, Class<T> entityClass, Object...params) {
		List<T> resultList = new ArrayList<>();
		List<Map<String, Object>> mapList = jdbcTemplate.queryForList(sql, params);

		// 循环处理结果,封装到entity
		T entity;
		for (Map<String, Object> m: mapList) {
			try {
				entity = entityClass.newInstance();
				MyBeanUtils.copyMap2Bean_Nobig(entity, m);
				resultList.add(entity);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return resultList;
	}

	@Override
	public <T> List<T> findListBySqlWithPage(String sql, int curPage, int pageSize, Class<T> entityClass, Object...params) {
		// 封装分页SQL
		sql = JdbcDao.jeecgCreatePageSql(sql, curPage, pageSize);
		return findListBySql(sql, entityClass, params);
	}

	@Override
	public <T> List<T> findListByHql(String hql, Object...params) {
		Query query = getSession().createQuery(hql);
		if (null != params && params.length > 0) {
			for (int i=0; i<params.length; i++) {
				query.setParameter(i, params[i]);
			}
		}
		return query.list();
	}

	@Override
	public <T> List<T> findListByProperty(Class<T> entityClass, String propertyName, Object value) {
		Assert.hasText(propertyName);
		return (List<T>) createCriteria(entityClass, Restrictions.eq(propertyName, value)).list();
	}

	@Override
	public <T> List<T> findByPropertyIsOrder(Class<T> entityClass, String propertyName, Object value, boolean isAsc) {
		Assert.hasText(propertyName);
		return createCriteria(entityClass, isAsc, Restrictions.eq(propertyName, value)).list();
	}

	@Override
	public List<T> findListByCriteriaQuery(final CriteriaQuery cq, Boolean isPage) {
		Criteria criteria = cq.getDetachedCriteria().getExecutableCriteria(getSession());

		// 判断是否有排序字段
		if (null != cq.getOrdermap()) {
			cq.setOrder(cq.getOrdermap());
		}

		// 判断是否分页
		if (isPage){
			criteria.setFirstResult((cq.getCurPage()-1)*cq.getPageSize());
			criteria.setMaxResults(cq.getPageSize());
		}
		return criteria.list();
	}

	@Override
	public List findListByEntity(final String entityName, final Object exampleEntity) {
		Assert.notNull(exampleEntity, "Example entity must not be null");
		Criteria executableCriteria = (entityName != null ? getSession().createCriteria(entityName) : getSession().createCriteria(exampleEntity.getClass()));
		executableCriteria.add(Example.create(exampleEntity));
		return executableCriteria.list();
	}

	@Override
	public <T> List<T> findListByDetached(DetachedCriteria dc) {
		return dc.getExecutableCriteria(getSession()).list();
	}

	@Override
	public <T> List<T> findListByDetachedCriteria(DetachedCriteria dc, int firstResult, int maxResult) {
		Criteria criteria = dc.getExecutableCriteria(getSession());
		criteria.setResultTransformer(CriteriaSpecification.ROOT_ENTITY);
		criteria.setFirstResult(firstResult);
		criteria.setMaxResults(maxResult);
		return criteria.list();
	}

	@Override
	public List<Map<String, Object>> findListMapBySql(String sql, Object...params) {
		return this.jdbcTemplate.queryForList(sql, params);
	}

	@Override
	public List<Map<String, Object>> findListMapBySqlWithPage(String sql, int curPage, int pageSize, Object...params) {
		// 封装分页SQL
		sql = JdbcDao.jeecgCreatePageSql(sql, curPage, pageSize);
		return this.jdbcTemplate.queryForList(sql, params);
	}

	@Override
	public PageList findPageByCriteriaQuery(final CriteriaQuery cq, final boolean isOffset) {
		Criteria criteria = cq.getDetachedCriteria().getExecutableCriteria(getSession());
		CriteriaImpl impl = (CriteriaImpl) criteria;

		// 先把Projection和OrderBy条件取出来,清空两者来执行Count操作
		Projection projection = impl.getProjection();
		final int allCounts = ((Long) criteria.setProjection(Projections.rowCount()).uniqueResult()).intValue();
		criteria.setProjection(projection);
		if (null == projection) {
			criteria.setResultTransformer(CriteriaSpecification.ROOT_ENTITY);
		}

		// 判断是否有排序字段
		if (null != cq.getOrdermap()) {
			cq.setOrder(cq.getOrdermap());
		}

		// 分页条数及当前页
		int pageSize = cq.getPageSize();
		int curPageNO = PagerUtil.getcurPageNo(allCounts, cq.getCurPage(),pageSize);

		// 判断是否分页
		int offset = PagerUtil.getOffset(allCounts, curPageNO, pageSize);
		String toolBar = "";
		if (isOffset) {
			criteria.setFirstResult(offset);
			criteria.setMaxResults(cq.getPageSize());
			if (cq.getIsUseimage() == 1) {
				toolBar = PagerUtil.getBar(cq.getMyAction(), cq.getMyForm(), allCounts, curPageNO, pageSize, cq.getMap());
			} else {
				toolBar = PagerUtil.getBar(cq.getMyAction(), allCounts, curPageNO, pageSize, cq.getMap());
			}
		}
		return new PageList(criteria.list(), toolBar, offset, curPageNO, allCounts);
	}

	@Override
	public PageList findPageByHqlQuery(final HqlQuery hqlQuery, final boolean needParameter) {
		Query query = getSession().createQuery(hqlQuery.getQueryString());
		if (needParameter) {
			query.setParameters(hqlQuery.getParam(), hqlQuery.getTypes());
		}

		int allCounts = query.list().size();
		int curPageNO = hqlQuery.getCurPage();
		int offset = PagerUtil.getOffset(allCounts, curPageNO, hqlQuery.getPageSize());
		String toolBar = PagerUtil.getBar(hqlQuery.getMyaction(), allCounts, curPageNO, hqlQuery.getPageSize(), hqlQuery.getMap());
		query.setFirstResult(offset);
		query.setMaxResults(hqlQuery.getPageSize());
		return new PageList(query.list(), toolBar, offset, curPageNO, allCounts);
	}

	@Override
	public PageList findPageByHqlQueryReToEntity(final HqlQuery hqlQuery, final boolean isToEntity) {
		Query query = getSession().createSQLQuery(hqlQuery.getQueryString());

		int allCounts = query.list().size();
		int curPageNO = hqlQuery.getCurPage();
		int offset = PagerUtil.getOffset(allCounts, curPageNO, hqlQuery.getPageSize());
		query.setFirstResult(offset);
		query.setMaxResults(hqlQuery.getPageSize());
		List list;
		if (isToEntity) {
			list = ToEntityUtil.toEntityList(query.list(), hqlQuery.getClass1(), hqlQuery.getDataGrid().getField().split(","));
		} else {
			list = query.list();
		}
		return new PageList(hqlQuery, list, offset, curPageNO, allCounts);
	}

	@Override
	public DataTableReturn getDataTableReturn(final CriteriaQuery cq, final boolean isOffset) {
		Criteria criteria = cq.getDetachedCriteria().getExecutableCriteria(getSession());
		CriteriaImpl impl = (CriteriaImpl) criteria;

		// 先把Projection和OrderBy条件取出来,清空两者来执行Count操作
		Projection projection = impl.getProjection();
		final int allCounts = ((Long) criteria.setProjection(Projections.rowCount()).uniqueResult()).intValue();
		criteria.setProjection(projection);
		if (null == projection) {
			criteria.setResultTransformer(CriteriaSpecification.ROOT_ENTITY);
		}

		// 判断是否有排序字段
		if (null != cq.getOrdermap()) {
			cq.setOrder(cq.getOrdermap());
		}

		// 分页条数及当前页
		int pageSize = cq.getPageSize();
		int curPageNO = PagerUtil.getcurPageNo(allCounts, cq.getCurPage(), pageSize);

		// 判断是否分页
		int offset = PagerUtil.getOffset(allCounts, curPageNO, pageSize);
		if (isOffset) {
			criteria.setFirstResult(offset);
			criteria.setMaxResults(cq.getPageSize());
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
		if(null == allCountsObj){
			allCounts = 0;
		}else{
			allCounts = ((Long) allCountsObj).intValue();
		}

		criteria.setProjection(projection);
		if (projection == null) {
			criteria.setResultTransformer(CriteriaSpecification.ROOT_ENTITY);
		}

		// 排序字段
		Map<String, Object> orderMap = cq.getOrdermap();
		if(null == orderMap){
			orderMap = new LinkedHashMap<>();
		}

		String sort = cq.getDataGrid().getSort();
		if (StringUtils.isNotBlank(sort)) {
			String[] sortArray = sort.split(",");
			String[] orderArray = cq.getDataGrid().getOrder().split(",");
			if (sortArray.length != orderArray.length && orderArray.length > 0) {
				for (int i=0; i<sortArray.length; i++) {
					if(SortDirection.asc.equals(SortDirection.toEnum(orderArray[0]))){
						orderMap.put(sortArray[i], SortDirection.asc);
					}else{
						orderMap.put(sortArray[i], SortDirection.desc);
					}
				}
			} else if (sortArray.length == orderArray.length) {
				for (int i = 0; i < sortArray.length; i++) {
					if (SortDirection.asc.equals(SortDirection.toEnum(orderArray[i]))) {
						orderMap.put(sortArray[i], SortDirection.asc);
					} else {
						orderMap.put(sortArray[i], SortDirection.desc);
					}
				}
			}
		}

		if(!orderMap.isEmpty() && orderMap.size()>0){
			cq.setOrder(orderMap);
		}

		// 分页条数及当前页
		int pageSize = cq.getPageSize();
		int curPageNO = PagerUtil.getcurPageNo(allCounts, cq.getCurPage(),pageSize);

		// 判断是否分页
		int offset = PagerUtil.getOffset(allCounts, curPageNO, pageSize);
		if (isOffset) {
			criteria.setFirstResult(offset);
			criteria.setMaxResults(cq.getPageSize());
		}

		List<?> list = criteria.list();
		cq.getDataGrid().setResults(list);
		cq.getDataGrid().setTotal(allCounts);

		cq.clear();
		cq = null;
	}

	@Override
	public Integer executeSql(String sql, Object...params) {
		return this.jdbcTemplate.update(sql, params);
	}

	@Override
	public Integer executeSql(String sql, Map<String, Object> paramMap) {
		return this.namedParameterJdbcTemplate.update(sql, paramMap);
	}

	@Override
	public Object executeSqlReturnKey(final String sql, Map<String, Object> paramMap) {
		Object keyValue = null;
		KeyHolder keyHolder;
		SqlParameterSource sqlParameterSource  = new MapSqlParameterSource(paramMap);

		if (StringUtil.isNotEmpty(paramMap.get("id")) || StringUtil.isNotEmpty(paramMap.get("ID"))) {
			// 表示已经生成过id(UUID),则表示是非序列或数据库自增的形式
			this.namedParameterJdbcTemplate.update(sql,sqlParameterSource);
		} else {
			// NATIVE or SEQUENCE
			keyHolder = new GeneratedKeyHolder();
			this.namedParameterJdbcTemplate.update(sql,sqlParameterSource, keyHolder, new String[]{"id"});
			Number number = keyHolder.getKey();
			if(oConvertUtils.isNotEmpty(number)){
				keyValue = keyHolder.getKey().longValue();
			}
		}
		return keyValue;
	}

	@Override
	public Integer executeHql(String hql, Object...params) {
		Query query = getSession().createQuery(hql);
		if (null != params && params.length > 0) {
			for (int i=0; i<params.length; i++) {
				query.setParameter(i, params[i]);
			}
		}
		return query.executeUpdate();
	}

	@Override
	public Long getCountBySql(String sql, Object...params) {
		return this.jdbcTemplate.queryForObject(sql, params, Long.class);
	}

	@Override
	public List<DBTable> findDbTableList() {
		List<DBTable> resultList = new ArrayList<>();
		SessionFactory factory = getSession().getSessionFactory();
		Map<String, ClassMetadata> metaMap = factory.getAllClassMetadata();
		for (String key: metaMap.keySet()) {
			DBTable dbTable = new DBTable();
			AbstractEntityPersister classMetadata = (AbstractEntityPersister) metaMap.get(key);
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
		// 事务必须是开启的(Required)，否则获取不到
		return sessionFactory.getCurrentSession();
	}

	/**
	 * 创建Criteria对象,有排序功能
	 * @param entityClass
	 * @param isAsc
	 * @param criterion
	 * @param <T>
	 * @return
	 */
	private <T> Criteria createCriteria(Class<T> entityClass, boolean isAsc, Criterion...criterion) {
		Criteria criteria = createCriteria(entityClass, criterion);
		if (isAsc) {
			criteria.addOrder(Order.asc("asc"));
		} else {
			criteria.addOrder(Order.desc("desc"));
		}
		return criteria;
	}

	/**
	 * 创建Criteria对象带属性比较
	 * @param entityClass
	 * @param criterion
	 * @param <T>
	 * @return
	 */
	private <T> Criteria createCriteria(Class<T> entityClass, Criterion... criterion) {
		Criteria criteria = getSession().createCriteria(entityClass);
		for (Criterion c : criterion) {
			criteria.add(c);
		}
		return criteria;
	}

	/**
	 * 创建单一Criteria对象
	 * @param entityClass
	 * @param <T>
	 * @return
	 */
	private <T> Criteria createCriteria(Class<T> entityClass) {
		Criteria criteria = getSession().createCriteria(entityClass);
		return criteria;
	}

	@Autowired
	@Qualifier("jdbcTemplate")
	private JdbcTemplate jdbcTemplate;

	@Autowired
	@Qualifier("namedParameterJdbcTemplate")
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

}
