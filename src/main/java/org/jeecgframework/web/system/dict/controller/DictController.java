package org.jeecgframework.web.system.dict.controller;

import org.apache.commons.lang.StringUtils;
import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.constant.GlobalConstants;
import org.jeecgframework.core.util.ListUtils;
import org.jeecgframework.core.util.MutiLangUtil;
import org.jeecgframework.tag.core.easyui.TagUtil;
import org.jeecgframework.tag.vo.datatable.SortDirection;
import org.jeecgframework.web.system.dict.entity.TypeEntity;
import org.jeecgframework.web.system.dict.entity.TypeGroupEntity;
import org.jeecgframework.web.system.dict.service.DictService;
import org.jeecgframework.web.system.service.MutiLangService;
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 数据字典Controller
 * @author DELL
 * @date 2019-05-06
 * @version V1.0
 */
@Controller
@RequestMapping("/dictController")
public class DictController extends BaseController {

	@Autowired
	private DictService dictService;
	@Autowired
	private MutiLangService mutiLangService;
	@Autowired
	private SystemService systemService;

	/**
	 * 跳转数据字典类型分组列表页面
	 * @return
	 */
	@RequestMapping(params = "typeGroupList")
	public ModelAndView typeGroupList() {
		return new ModelAndView("system/dict/typeGroupList");
	}

	/**
	 * 获取数据字典类型分组列表数据
	 * @param request
	 * @param response
	 * @param dataGrid
	 */
	@RequestMapping(params = "typeGroupGrid")
	public void typeGroupGrid(HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(TypeGroupEntity.class, dataGrid);

		// 数据字典编码
		String code = request.getParameter("code");
		if(StringUtils.isNotBlank(code)) {
			cq.like("code", "%" + code + "%");
		}

		// 数据字典名称
		String name = request.getParameter("name");
		if (StringUtils.isNotBlank(name)) {
			cq.like("name", "%" + name + "%");
		}

		cq.add();

		this.dictService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * 删除类型分组
	 *
	 * @return
	 */
	@RequestMapping(params = "delTypeGroup")
	@ResponseBody
	public AjaxJson delTypeGroup(TypeGroupEntity typeGroupEntity) {
		String message = "删除成功";
		AjaxJson j = new AjaxJson();
		typeGroupEntity = dictService.getById(TypeGroupEntity.class, typeGroupEntity.getId());
		if (ListUtils.isNullOrEmpty(typeGroupEntity.getTSTypes())) {
			systemService.delete(typeGroupEntity);
			systemService.addLog(message, GlobalConstants.LOG_TYPE_DELETE, GlobalConstants.LOG_LEVEL_INFO);
			//刷新缓存
			dictService.refreshTypeGroupCache();
		} else {
			message = "类型分组: " + mutiLangService.getLang(typeGroupEntity.getName()) + " 下有类型信息，不能删除！";
		}

		j.setMsg(message);
		return j;
	}

	/**
	 * 类型分组列表页面跳转
	 *
	 * @return
	 */
	@RequestMapping(params = "aouTypeGroup")
	public ModelAndView aouTypeGroup(TypeGroupEntity typegroup, HttpServletRequest req) {
		if (typegroup.getId() != null) {
			typegroup = systemService.getById(TypeGroupEntity.class, typegroup.getId());
			req.setAttribute("typegroup", typegroup);
		}
		return new ModelAndView("system/dict/typegroup");
	}

	/**
	 * 刷新字典分组缓存&字典缓存
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "refreshTypeGroupAndTypes")
	@ResponseBody
	public AjaxJson refreshTypeGroupAndTypes(HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		try{
			dictService.refreshTypeAndTypeGroupCache();
			message = mutiLangService.getLang("common.refresh.success");
		} catch (Exception e) {
			message = mutiLangService.getLang("common.refresh.fail");
		}
		j.setMsg(message);
		return j;
	}

	/**
	 * 跳转数据字典类型列表页面
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "typeList")
	public ModelAndView goTypeGrid(HttpServletRequest request) {
		String typeGroupId = request.getParameter("typeGroupId");
		request.setAttribute("typeGroupId", typeGroupId);
		return new ModelAndView("system/dict/typeList");
	}

	/**
	 * 获取数据字典类型列表数据
	 * @param request
	 * @param response
	 * @param dataGrid
	 */
	@RequestMapping(params = "typeGrid")
	public void typeGrid(HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		String typeGroupId = request.getParameter("typeGroupId");
		CriteriaQuery cq = new CriteriaQuery(TypeEntity.class, dataGrid);
		cq.eq("TSTypegroup.id", typeGroupId);

		cq.addOrder("sort", SortDirection.asc);

		cq.add();
		this.systemService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}
}
