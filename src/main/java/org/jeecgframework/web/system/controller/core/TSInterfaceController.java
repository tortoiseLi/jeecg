package org.jeecgframework.web.system.controller.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.model.json.ComboTree;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.common.model.json.TreeGrid;
import org.jeecgframework.core.constant.GlobalConstants;
import org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil;
import org.jeecgframework.core.util.MutiLangUtil;
import org.jeecgframework.core.util.MyBeanUtils;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.core.util.oConvertUtils;
import org.jeecgframework.tag.core.easyui.TagUtil;
import org.jeecgframework.tag.vo.datatable.SortDirection;
import org.jeecgframework.tag.vo.easyui.ComboTreeModel;
import org.jeecgframework.tag.vo.easyui.TreeGridModel;
import org.jeecgframework.web.system.function.entity.FunctionEntity;
import org.jeecgframework.web.system.pojo.base.IconEntity;
import org.jeecgframework.web.system.pojo.base.InterfaceDdataRuleEntity;
import org.jeecgframework.web.system.pojo.base.InterfaceEntity;
import org.jeecgframework.web.system.pojo.base.OperationEntity;
import org.jeecgframework.web.system.pojo.base.RoleFunctionEntity;
import org.jeecgframework.web.system.service.SystemService;
import org.jeecgframework.web.system.service.InterfaceService;
import org.jeecgframework.web.system.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * 接口授权类
 * 
 * @author baiyu
 */
@Controller
@RequestMapping("/interfaceController")
public class TSInterfaceController extends BaseController {
	/**
	 * Logger for this class
	 */
	@SuppressWarnings("unused")
	private static final Logger logger = Logger.getLogger(TSInterfaceController.class);
	private UserService userService;
	private SystemService systemService;

	@Autowired
	public void setSystemService(SystemService systemService) {
		this.systemService = systemService;
	}

	public UserService getUserService() {
		return userService;

	}

	@Autowired
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	@Autowired
	InterfaceService tsService;
	

	/**
	 * 权限列表页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "interface")
	public ModelAndView function(ModelMap model) {
		return new ModelAndView("system/tsinterface/interfaceList");
	}

	/**
	 * 操作列表页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "operation")
	public ModelAndView operation(HttpServletRequest request, String functionId) {
		request.setAttribute("functionId", functionId);
		return new ModelAndView("system/operation/operationList");
	}

	/**
	 * 数据规则列表页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "dataRule")
	public ModelAndView operationData(HttpServletRequest request, String interfaceId) {
		request.setAttribute("interfaceId", interfaceId);
		return new ModelAndView("system/tsinterface/ruleDataList");
	}

	/**
	 * easyuiAJAX请求数据
	 * 
	 * @param request
	 * @param response
	 * @param dataGrid
	 */

	@RequestMapping(params = "datagrid")
	public void datagrid(HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(FunctionEntity.class, dataGrid);
		this.systemService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * easyuiAJAX请求数据
	 * 
	 * @param request
	 * @param response
	 * @param dataGrid
	 */
	@RequestMapping(params = "opdategrid")
	public void opdategrid(HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(OperationEntity.class, dataGrid);
		String functionId = oConvertUtils.getString(request.getParameter("functionId"));
		cq.eq("TSFunction.id", functionId);
		cq.add();
		this.systemService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * 删除权限
	 * 
	 * @param interface
	 * @return
	 */
 
	@RequestMapping(params = "del")
	@ResponseBody
	public AjaxJson del(InterfaceEntity tsInterface, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();

		tsInterface = systemService.getById(InterfaceEntity.class, tsInterface.getId());
		List<InterfaceEntity> ts = tsInterface.getTSInterfaces();
		if(ts!=null&&ts.size()>0){
			 message = MutiLangUtil.getLang("common.menu.del.fail");
		}else{
			String hql =" from InterfaceDdataRuleEntity where TSInterface.id = ?";
			List<Object> findByQueryString = systemService.findHql(hql,tsInterface.getId());
			if(findByQueryString!=null&&findByQueryString.size()>0){
				 message = MutiLangUtil.getLang("common.menu.del.fail");
			}else{
				String sql = "delete from t_s_interface where id = ?";
				systemService.executeSql(sql, tsInterface.getId());

				String interroleSql = "delete from t_s_interrole_interface where interface_id = ?";
				systemService.executeSql(interroleSql, tsInterface.getId());

				message = MutiLangUtil.paramDelSuccess("common.menu");
				systemService.addLog(message, GlobalConstants.LOG_TYPE_DELETE, GlobalConstants.LOG_LEVEL_INFO);
			}
		}

		j.setMsg(message);
		return j;
	}

	/**
	 * 删除操作 
	 * 
	 * @param operation
	 * @return
	 */
	@RequestMapping(params = "delop")
	@ResponseBody
	public AjaxJson delop(OperationEntity operation, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		operation = systemService.getById(OperationEntity.class, operation.getId());
		message = MutiLangUtil.paramDelSuccess("common.operation");
		userService.delete(operation);
		String operationId = operation.getId();
		String hql = "from RoleFunctionEntity rolefun where rolefun.operation like '%" + operationId + "%'";
		List<RoleFunctionEntity> roleFunctions = userService.findListByHql(hql);
		for (RoleFunctionEntity roleFunction : roleFunctions) {
			String newOper = roleFunction.getOperation().replace(operationId + ",", "");
			if (roleFunction.getOperation().length() == newOper.length()) {
				newOper = roleFunction.getOperation().replace(operationId, "");
			}
			roleFunction.setOperation(newOper);
			userService.update(roleFunction);
		}
		systemService.addLog(message, GlobalConstants.LOG_TYPE_DELETE, GlobalConstants.LOG_LEVEL_INFO);
		j.setMsg(message);
		return j;
	}

	/**
	 * 递归更新子权限的InterfaceLevel
	 * 
	 * @param FunctionEntity
	 * @param parent
	 */
	private void updateSubFunction(List<InterfaceEntity> subInterface, InterfaceEntity parent) {
		if (subInterface.size() == 0) {// 没有子权限是结束
			return;
		} else {
			for (InterfaceEntity tsInterface : subInterface) {
				tsInterface.setInterfaceLevel(Short.valueOf(parent.getInterfaceLevel() + 1 + ""));
				systemService.saveOrUpdate(tsInterface);
				List<InterfaceEntity> subInterface1 = systemService.findListByProperty(InterfaceEntity.class, "tSInterface.id",
						tsInterface.getId());
				updateSubFunction(subInterface1, tsInterface);
			}
		}
	}

	/**
	 * 权限录入
	 * 
	 * @param function
	 * @return
	 */
	@RequestMapping(params = "saveInterface")
	@ResponseBody
	public AjaxJson saveInterface(InterfaceEntity tsInterface, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		tsInterface.setInterfaceUrl(tsInterface.getInterfaceUrl().trim());
		String functionOrder = tsInterface.getInterfaceOrder();
		if (StringUtils.isEmpty(functionOrder)) {
			tsInterface.setInterfaceOrder("0");
		}
		if (tsInterface.gettSInterface().getId().equals("")) {
			tsInterface.settSInterface(null);
		} else {
			InterfaceEntity parent = systemService.getById(InterfaceEntity.class, tsInterface.gettSInterface().getId());
			tsInterface.setInterfaceLevel(Short.valueOf(parent.getInterfaceLevel() + 1 + ""));
		}
		if (StringUtil.isNotEmpty(tsInterface.getId())) {
			message = MutiLangUtil.paramUpdSuccess("common.menu");
			InterfaceEntity t = systemService.getById(InterfaceEntity.class, tsInterface.getId());
			try {
				MyBeanUtils.copyBeanNotNull2Bean(tsInterface, t);
				userService.saveOrUpdate(t);
			} catch (Exception e) {
				e.printStackTrace();
			}
			systemService.addLog(message, GlobalConstants.LOG_TYPE_UPDATE, GlobalConstants.LOG_LEVEL_INFO);
			List<InterfaceEntity> subinterface = systemService.findListByProperty(InterfaceEntity.class, "tSInterface.id",
					tsInterface.getId());
			updateSubFunction(subinterface, tsInterface);
		} else {
			if (tsInterface.getInterfaceLevel().equals(GlobalConstants.FUNCTION_LEVEL_ONE)) {
				@SuppressWarnings("unused")
				List<InterfaceEntity> interfaceList = systemService.findListByProperty(InterfaceEntity.class, "interfaceLevel",
						GlobalConstants.FUNCTION_LEVEL_ONE);
				tsInterface.setInterfaceOrder(tsInterface.getInterfaceOrder());
			} else {
				@SuppressWarnings("unused")
				List<InterfaceEntity> interfaceList = systemService.findListByProperty(InterfaceEntity.class, "interfaceLevel",
						GlobalConstants.FUNCTION_LEVEL_TWO);
				tsInterface.setInterfaceOrder(tsInterface.getInterfaceOrder());
			}
			message = MutiLangUtil.paramAddSuccess("common.menu");
			systemService.add(tsInterface);
			systemService.addLog(message, GlobalConstants.LOG_TYPE_INSERT, GlobalConstants.LOG_LEVEL_INFO);
		}
		j.setMsg(message);
		return j;
	}

	/**
	 * 操作录入
	 * 
	 * @param operation
	 * @return
	 */
	@RequestMapping(params = "saveop")
	@ResponseBody
	public AjaxJson saveop(OperationEntity operation, HttpServletRequest request) {
		String message = null;
		String pid = request.getParameter("TSFunction.id");
		if (pid.equals("")) {
			operation.setTSFunction(null);
		}
		AjaxJson j = new AjaxJson();
		if (StringUtil.isNotEmpty(operation.getId())) {
			message = MutiLangUtil.paramUpdSuccess("common.operation");
			userService.saveOrUpdate(operation);
			systemService.addLog(message, GlobalConstants.LOG_TYPE_UPDATE, GlobalConstants.LOG_LEVEL_INFO);
		} else {
			message = MutiLangUtil.paramAddSuccess("common.operation");
			userService.add(operation);
			systemService.addLog(message, GlobalConstants.LOG_TYPE_INSERT, GlobalConstants.LOG_LEVEL_INFO);
		}

		j.setMsg(message);
		return j;
	}

	/**
	 * 权限列表页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "addorupdate")
	public ModelAndView addorupdate(InterfaceEntity tsInterface, HttpServletRequest req) {
		String interfaceid = req.getParameter("id");
		List<InterfaceEntity> interfacelist = systemService.findList(InterfaceEntity.class);
		req.setAttribute("flist", interfacelist);
		if (interfaceid != null) {
			tsInterface = systemService.getById(InterfaceEntity.class, interfaceid);
			req.setAttribute("tsInterface", tsInterface);
		}
		if (tsInterface.gettSInterface() != null && tsInterface.gettSInterface().getId() != null) {
			tsInterface.setInterfaceLevel((short) 1);
			tsInterface.settSInterface(
					(InterfaceEntity) systemService.getById(InterfaceEntity.class, tsInterface.gettSInterface().getId()));
			req.setAttribute("tsInterface", tsInterface);
		}
		return new ModelAndView("system/tsinterface/interface");
	}

	/**
	 * 操作列表页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "addorupdateop")
	public ModelAndView addorupdateop(OperationEntity operation, HttpServletRequest req) {
		List<IconEntity> iconlist = systemService.findList(IconEntity.class);
		req.setAttribute("iconlist", iconlist);
		if (operation.getId() != null) {
			operation = systemService.getById(OperationEntity.class, operation.getId());
			req.setAttribute("operation", operation);
		}
		String functionId = oConvertUtils.getString(req.getParameter("functionId"));
		req.setAttribute("functionId", functionId);
		return new ModelAndView("system/operation/operation");
	}

	/**
	 * 权限列表
	 */
	@RequestMapping(params = "interfaceGrid")
	@ResponseBody
	public List<TreeGrid> interfaceGrid(HttpServletRequest request, TreeGrid treegrid, Integer type) {
		CriteriaQuery cq = new CriteriaQuery(InterfaceEntity.class);
		String selfId = request.getParameter("selfId");
		if (selfId != null) {
			cq.notEq("id", selfId);
		}
		if (treegrid.getId() != null) {
			cq.eq("tSInterface.id", treegrid.getId());
		}
		if (treegrid.getId() == null) {
			cq.isNull("tSInterface");
		}
		cq.addOrder("interfaceOrder", SortDirection.asc);
		cq.add();

		// 获取装载数据权限的条件HQL
		cq = HqlGenerateUtil.getDataAuthorConditionHql(cq, new InterfaceEntity());
		cq.add();

		List<InterfaceEntity> interfaceList = systemService.getListByCriteriaQuery(cq, false);
		List<TreeGrid> treeGrids = new ArrayList<TreeGrid>();
		TreeGridModel treeGridModel = new TreeGridModel();
		treeGridModel.setTextField("interfaceName");
		treeGridModel.setParentText("TSInterface_interfaceName");
		treeGridModel.setParentId("TSInterface_id");
		treeGridModel.setSrc("interfaceUrl");
		treeGridModel.setIdField("id");
		treeGridModel.setChildList("tSInterfaces");
		Map<String, Object> fieldMap = new HashMap<String, Object>();
		fieldMap.put("interfaceCode", "interfaceCode");
		fieldMap.put("interfaceMethod", "interfaceMethod");
		treeGridModel.setFieldMap(fieldMap);
		// 添加排序字段
		treeGridModel.setOrder("interfaceOrder");
		treeGrids = systemService.treegrid(interfaceList, treeGridModel);
		MutiLangUtil.setMutiTree(treeGrids);
		return treeGrids;
	}
	
	

	/**
	 * 父级权限下拉菜单
	 */
	@RequestMapping(params = "setPInterface")
	@ResponseBody
	public List<ComboTree> setPInterface(HttpServletRequest request, ComboTree comboTree) {
		CriteriaQuery cq = new CriteriaQuery(InterfaceEntity.class);
		if (null != request.getParameter("selfId")) {
			cq.notEq("id", request.getParameter("selfId"));
		}
		if (comboTree.getId() != null) {
			cq.eq("tSInterface.id", comboTree.getId());
		}
		if (comboTree.getId() == null) {
			cq.isNull("tSInterface");
		}
		cq.add();
		List<InterfaceEntity> interfaceList = systemService.getListByCriteriaQuery(cq, false);
		List<ComboTree> comboTrees = new ArrayList<ComboTree>();
		ComboTreeModel comboTreeModel = new ComboTreeModel("id", "interfaceName", "tsInterfaces");
		comboTrees = systemService.ComboTree(interfaceList, comboTreeModel, null, false);
		MutiLangUtil.setMutiTree(comboTrees);
		return comboTrees;
	}

	/**
	 * 添加修改权限数据
	 */
	@RequestMapping(params = "addorupdaterule")
	public ModelAndView addorupdaterule(InterfaceDdataRuleEntity operation, HttpServletRequest req) {
		if (operation.getId() != null) {
			operation = systemService.getById(InterfaceDdataRuleEntity.class, operation.getId());
			req.setAttribute("operation", operation);
		}
		String interfaceId = oConvertUtils.getString(req.getParameter("interfaceId"));
		req.setAttribute("interfaceId", interfaceId);
		return new ModelAndView("system/tsinterface/ruleData");
	}

	/**
	 * 接口权限规则的列表界面 
	 */
	@RequestMapping(params = "ruledatagrid")
	public void ruledategrid(HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(InterfaceDdataRuleEntity.class, dataGrid);
		String interfaceId = oConvertUtils.getString(request.getParameter("interfaceId"));
		cq.eq("TSInterface.id", interfaceId);
		cq.add();
		//TODO
		this.systemService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 *删除接口权限规则 operation 
	 */
	@RequestMapping(params = "delrule")
	@ResponseBody
	public AjaxJson delrule(InterfaceDdataRuleEntity operation, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		operation = systemService.getById(InterfaceDdataRuleEntity.class, operation.getId());
		message = MutiLangUtil.paramDelSuccess("common.operation");
		userService.delete(operation);
		systemService.addLog(message, GlobalConstants.LOG_TYPE_DELETE, GlobalConstants.LOG_LEVEL_INFO);
		j.setMsg(message);
		return j;
	}

	/**
	 * saverule保存规则权限值  
	 */
	@RequestMapping(params = "saverule")
	@ResponseBody
	public AjaxJson saverule(InterfaceDdataRuleEntity operation, HttpServletRequest request)throws Exception {
		String message = null;
		AjaxJson j = new AjaxJson();
		InterfaceEntity interfaceEntity = systemService.getById(InterfaceEntity.class, operation.getTSInterface().getId());
		if(interfaceEntity!=null){
			if (StringUtil.isNotEmpty(operation.getId())) {
				message = MutiLangUtil.paramUpdSuccess("common.operation");
				userService.saveOrUpdate(operation);
				systemService.addLog(message, GlobalConstants.LOG_TYPE_UPDATE, GlobalConstants.LOG_LEVEL_INFO);
			} else {
				if (justHaveDataRule(operation) == 0) {
					message = MutiLangUtil.paramAddSuccess("common.operation");
					userService.add(operation);
					systemService.addLog(message, GlobalConstants.LOG_TYPE_INSERT, GlobalConstants.LOG_LEVEL_INFO);
				} else {
					message = "操作字段规则已存在";
				}
			}
		}else{
			message = "操作失败";
		}
		j.setMsg(message);
		return j;
	}

	public int justHaveDataRule(InterfaceDdataRuleEntity dataRule) {

		String column = dataRule.getRuleColumn();
		if(oConvertUtils.isEmpty(column)){
			return 0;
		}
		String sql = "SELECT count(1) FROM t_s_interface_datarule WHERE interface_id = ? AND rule_column = ? AND rule_conditions = ?";
		Long count = this.systemService.getCountForJdbcParam(sql, dataRule.getTSInterface().getId(),column,dataRule.getRuleConditions());
		return count.intValue();

	}

}
