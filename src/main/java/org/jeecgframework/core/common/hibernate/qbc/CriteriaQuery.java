package org.jeecgframework.core.common.hibernate.qbc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.hibernate.type.Type;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.core.util.oConvertUtils;
import org.jeecgframework.tag.vo.datatable.DataTables;
import org.jeecgframework.tag.vo.datatable.SortDirection;
import org.jeecgframework.tag.vo.datatable.SortInfo;

/**
 * CriteriaQuery类是对hibernate QBC查询方法的封装，需要的参数是当前操作的实体类
 * @author DELL
 * @date 2019-05-01
 * @version V1.0
 */
public class CriteriaQuery {

	/**
	 * 当前页
	 */
	private int curPage = 1;

	/**
	 * 分页条数
	 */
	private int pageSize = 10;

	/**
	 * 请求的action地址
	 */
	private String myAction;

	/**
	 * 请求的form名称
	 */
	private String myForm;

	/**
	 * 自定义查询条件集合
	 */
	private CriterionList criterionList=new CriterionList();

	/**
	 * jquery datatable控件生成查询条件集合
	 */
	private CriterionList jqcriterionList=new CriterionList();

	/**
	 * 翻页工具条样式
	 */
	private int isUseImage = 0;

	/**
	 *
	 */
	private DetachedCriteria detachedCriteria;

	/**
	 *
	 */
	private Map<String, Object> map;

	/**
	 * 排序字段
	 */
	private Map<String, Object> orderMap;

	/**
	 * 对同一字段进行第二次重命名查询时值设置FASLE不保存重命名查询条件
	 */
	private boolean flag = true;

	/**
	 * 查询需要显示的字段
	 */
	private String field="";

	/**
	 *
	 */
	private Class<?> entityClass;

	/**
	 * 结果集
	 */
	private List<?> results;

	/**
	 * 总条数
	 */
	private int total;

	/**
	 * 保存创建的aliasName 防止重复创建
	 */
	private List<String> alias = new ArrayList<>();

	/**
	 *
	 */
	private DataGrid dataGrid;

	/**
	 *
	 */
	private DataTables dataTables;
	
	public List<?> getResults() {
		return results;
	}

	public void setResults(List<?> results) {
		this.results = results;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public DataTables getDataTables() {
		return dataTables;
	}

	public void setDataTables(DataTables dataTables) {
		this.dataTables = dataTables;
	}

	public DataGrid getDataGrid() {
		return dataGrid;
	}

	public void setDataGrid(DataGrid dataGrid) {
		this.dataGrid = dataGrid;
	}

	public Class<?> getEntityClass() {
		return entityClass;
	}

	public void setEntityClass(Class<?> entityClass) {
		this.entityClass = entityClass;
	}
	public CriterionList getJqcriterionList() {
		return jqcriterionList;
	}

	public void setJqcriterionList(CriterionList jqcriterionList) {
		this.jqcriterionList = jqcriterionList;
	}

	public CriteriaQuery(Class<?> c) {
		this.detachedCriteria = DetachedCriteria.forClass(c);
		this.map = new HashMap<>();
		this.orderMap = new LinkedHashMap<>();
	}

	public CriteriaQuery(Class<?> c, int curPage, String myAction, String myForm) {
		this.curPage = curPage;
		this.myAction = myAction;
		this.myForm = myForm;
		this.detachedCriteria = DetachedCriteria.forClass(c);
	}

	public CriteriaQuery(Class<?> c, int curPage, String myAction) {
		this.myAction = myAction;
		this.curPage = curPage;
		this.detachedCriteria = DetachedCriteria.forClass(c);
		this.map = new HashMap<>();
		this.orderMap = new LinkedHashMap<>();
	}

	public CriteriaQuery(Class<?> entityClass, int curPage) {
		this.curPage = curPage;
		this.detachedCriteria = DetachedCriteria.forClass(entityClass);
		this.map = new HashMap<>();
	}
	public CriteriaQuery(Class<?> entityClass,DataGrid dg) {
		this.curPage = dg.getPage();
		this.detachedCriteria = DetachedCriteria.forClass(entityClass);
		this.field=dg.getField();
		this.entityClass=entityClass;
		this.dataGrid=dg;
		this.pageSize=dg.getRows();
		this.map = new HashMap<>();
		this.orderMap = new LinkedHashMap<String, Object>();
	}
	
	public CriteriaQuery(Class entityClass,DataTables dataTables) {
		this.curPage = dataTables.getDisplayStart();
		this.detachedCriteria = DetachedCriteria.forClass(entityClass);
		this.field=dataTables.getsColumns();
		this.entityClass=entityClass;
		this.dataTables=dataTables;
		this.pageSize=dataTables.getDisplayLength();
		this.map = new HashMap<>();
		this.orderMap = new LinkedHashMap<String, Object>();
		addJqCriteria(dataTables);
	}

	public CriteriaQuery(Class c, int pageSize, int curPage,
			String myAction, String myForm) {
		this.pageSize = pageSize;
		this.curPage = curPage;
		this.myAction = myAction;
		this.myForm = myForm;
		this.detachedCriteria = DetachedCriteria.forClass(c);
	}

	public void add(Criterion c) {
		detachedCriteria.add(c);
	}

	public void add() {
		for (int i = 0; i < getCriterionList().size(); i++) {
			add(getCriterionList().getParas(i));
		}
		getCriterionList().removeAll(getCriterionList());
	}

	public void addJqCriteria(DataTables dataTables) {
		// 查询关键字
		String search=dataTables.getSearch();

		// 排序字段
		SortInfo[] sortInfo=dataTables.getSortColumns();

		// 字段
		String[] sColumns=dataTables.getsColumns().split(",");
		if (StringUtils.isNotBlank(search)) {
			for(String string : sColumns) {
				if(string.indexOf("_") == -1) {
					jqcriterionList.addPara(Restrictions.like(string, "%" + search + "%"));
				}
			}
			add(getOrCriterion(jqcriterionList));
		}

		if(sortInfo.length>0) {
			for (SortInfo sortInfo2 : sortInfo) {
				addOrder(""+sColumns[sortInfo2.getColumnId()]+"",sortInfo2.getSortOrder());
			}
		}
	}

	public void createCriteria(String name) {
		detachedCriteria.createCriteria(name);
	}

	public void createCriteria(String name, String value) {
		detachedCriteria.createCriteria(name, value);
	}

	public void createAlias(String name, String value) {
		if(!alias.contains(name)){
			detachedCriteria.createAlias(name, value);
			alias.add(name);
		}
	}

	public void setResultTransformer(Class<?> class1) {
		detachedCriteria.setResultTransformer(Transformers.aliasToBean(class1));
	}

	public void setProjection(Property property) {
		detachedCriteria.setProjection(property);
	}

	public Criterion and(CriteriaQuery query, int source, int dest) {
		return Restrictions.and(query.getCriterionList().getParas(source), query.getCriterionList().getParas(dest));
	}

	public Criterion and(Criterion c, CriteriaQuery query, int source) {
		return Restrictions.and(c, query.getCriterionList().getParas(source));
	}

	public Criterion getOrCriterion(CriterionList list) {
		Criterion c1;
		Criterion c2;
		Criterion c3 = null;
		c1=list.getParas(0);
		for (int i = 1; i < list.size(); i++) {
			c2=list.getParas(i);
			c3=getOr(c1, c2);
			c1=c3;
		}
		return c3;
	}

	public Criterion getOr(Criterion c1,Criterion c2) {
		return Restrictions.or(c1, c2);
	}
	
	public Criterion and(Criterion c1, Criterion c2)

	{
		return Restrictions.and(c1, c2);
	}

	public Criterion or(CriteriaQuery query, int source, int dest) {
		return Restrictions.or(query.getCriterionList().getParas(source), query.getCriterionList().getParas(dest));
	}

	public Criterion or(Criterion c, CriteriaQuery query, int source) {
		return Restrictions.or(c, query.getCriterionList().getParas(source));
	}

	public void or(Criterion c1, Criterion c2) {
		this.detachedCriteria.add(Restrictions.or(c1, c2));
	}

	public void addOrder(String ordername, SortDirection ordervalue) {
		orderMap.put(ordername,ordervalue);
	}

	public void setOrder(Map<String, Object> map) {
		for (Map.Entry<String, Object> entry : map.entrySet()) {
			judgecreateAlias(entry.getKey());
			if (SortDirection.asc.equals(entry.getValue())) {
				detachedCriteria.addOrder(Order.asc(entry.getKey()));
			} else {
				detachedCriteria.addOrder(Order.desc(entry.getKey()));
			}
		}
	}
	
	public void judgecreateAlias(String entitys) {
		String[] aliass = entitys.split("\\.");
		for (int i = 0 ;i<aliass.length-1;i++){
			createAlias(aliass[i], aliass[i]);
		}
	}

	public Map<String, Object> getOrderMap() {
		return orderMap;
	}

	public void setOrderMap(Map<String, Object> orderMap) {
		this.orderMap = orderMap;
	}

	public void eq(String keyname, Object keyvalue) {
		if (keyvalue != null && keyvalue != "") {
			criterionList.addPara(Restrictions.eq(keyname, keyvalue));
			if (flag) {
				this.put(keyname, keyvalue);
			}
			flag = true;
		}
	}

	public void notEq(String keyName, Object keyValue) {
		if (null != keyValue && "" != keyValue) {
			criterionList.addPara(Restrictions.ne(keyName, keyValue));
			if (flag) {
				this.put(keyName, keyValue);
			}
			flag = true;
		}
	}

	public void like(String keyName, Object keyValue) {
		if (null != keyValue && "" !=  keyValue) {
			criterionList.addPara(Restrictions.like(keyName, keyValue));
			if (flag) {
				this.put(keyName, keyValue);
			}
			flag = true;
		}
	}

	public void gt(String keyName, Object keyValue) {
		if (null != keyValue && "" != keyValue) {
			criterionList.addPara(Restrictions.gt(keyName, keyValue));
			if (flag) {
				this.put(keyName, keyValue);
			}
			flag = true;
		}
	}

	public void lt(String keyName, Object keyValue) {
		if (null != keyValue && "" != keyValue) {
			criterionList.addPara(Restrictions.lt(keyName, keyValue));
			if (flag) {
				this.put(keyName, keyValue);
			}
			flag = true;
		}
	}

	public void le(String keyName, Object keyValue) {
		if (null != keyValue && "" != keyValue) {
			criterionList.addPara(Restrictions.le(keyName, keyValue));
			if (flag) {
				this.put(keyName, keyValue);
			}
			flag = true;
		}
	}

	public void ge(String keyName, Object keyValue) {
		if (null != keyValue && "" != keyValue) {
			criterionList.addPara(Restrictions.ge(keyName, keyValue));
			if (flag) {
				this.put(keyName, keyValue);
			}
			flag = true;
		}
	}

	public void in(String keyName, Object[] keyValue) {
		if (null != keyValue && keyValue.length>0 && "" != keyValue[0]) {
			criterionList.addPara(Restrictions.in(keyName, keyValue));
		}
	}

	public void isNull(String keyName) {
		criterionList.addPara(Restrictions.isNull(keyName));
	}

	public void isNotNull(String keyName) {
		criterionList.addPara(Restrictions.isNotNull(keyName));
	}

	public void put(String keyName, Object keyValue) {
		if (null != keyValue && "" != keyValue) {
			map.put(keyName, keyValue);
		}
	}

	public void between(String keyName, Object keyValueBegin, Object keyValueEnd) {
		Criterion c = null;

		if (oConvertUtils.isNotEmpty(keyValueBegin) && oConvertUtils.isNotEmpty(keyValueEnd)) {
			c = Restrictions.between(keyName, keyValueBegin, keyValueEnd);
		} else if (oConvertUtils.isNotEmpty(keyValueBegin)) {
			c = Restrictions.ge(keyName, keyValueBegin);
		} else if (oConvertUtils.isNotEmpty(keyValueEnd)) {
			c = Restrictions.le(keyName, keyValueEnd);
		}
		criterionList.add(c);
	}

	public void sql(String sql) {
		Restrictions.sqlRestriction(sql);
	}

	public void sql(String sql, Object[] objects, Type[] type) {
		Restrictions.sqlRestriction(sql, objects, type);
	}

	public void sql(String sql, Object objects, Type type) {
		Restrictions.sqlRestriction(sql, objects, type);
	}

	public Integer getCurPage() {
		return curPage;
	}

	public void setCurPage(Integer curPage) {
		this.curPage = curPage;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public String getMyAction() {
		return myAction;
	}

	public void setMyAction(String myAction) {
		this.myAction = myAction;
	}

	public String getMyForm() {
		return myForm;
	}

	public void setMyForm(String myForm) {
		this.myForm = myForm;
	}

	public CriterionList getCriterionList() {
		return criterionList;
	}

	public void setCriterionList(CriterionList criterionList) {
		this.criterionList = criterionList;
	}

	public DetachedCriteria getDetachedCriteria() {
		return detachedCriteria;
	}

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}

	public void setDetachedCriteria(DetachedCriteria detachedCriteria) {
		this.detachedCriteria = detachedCriteria;
	}

	public int getIsUseImage() {
		return isUseImage;
	}

	public void setIsUseImage(int isUseImage) {
		this.isUseImage = isUseImage;
	}

	public Map<String, Object> getMap() {
		return map;
	}

	public void setMap(Map<String, Object> map) {
		this.map = map;
	}

	public boolean isFlag() {
		return flag;
	}

	public void setFlag(boolean flag) {
		this.flag = flag;
	}

	public void clear(){
		criterionList.clear();
		jqcriterionList.clear();
		alias.clear();
		if(map!=null){map.clear();}
		if(null != orderMap) {
			orderMap.clear();
		}
		entityClass=null;

		dataGrid = null;
		dataTables = null;
		detachedCriteria = null;

		criterionList = null;
		jqcriterionList = null;
		jqcriterionList = null;
		map = null;
		orderMap = null;
		alias = null;
		field = null;
	}
}
