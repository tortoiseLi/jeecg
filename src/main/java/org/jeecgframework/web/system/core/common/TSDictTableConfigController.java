package org.jeecgframework.web.system.core.common;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.exception.BusinessException;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.constant.GlobalConstants;
import org.jeecgframework.core.util.MyBeanUtils;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.entity.vo.NormalExcelConstants;
import org.jeecgframework.tag.core.easyui.TagUtil;
import org.jeecgframework.web.system.dict.entity.DictTableConfigEntity;
import org.jeecgframework.web.system.core.cache.service.CacheService;
import org.jeecgframework.web.system.service.SystemService;
import org.jeecgframework.web.system.service.DictTableConfigService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;


/**   
 * @Title: 字典表授权配置  
 * @Description: 字典表授权配置
 * @author onlineGenerator
 * @date 2018-07-10 15:30:22
 * @version V1.0   
 *
 */
@Controller
@RequestMapping("/tSDictTableConfigController")
public class TSDictTableConfigController extends BaseController {
	private static final Logger logger = LoggerFactory.getLogger(TSDictTableConfigController.class);

	@Autowired
	private DictTableConfigService tSDictTableConfigService;
	@Autowired
	private SystemService systemService;
	@Autowired
	private CacheService cacheService;
	private static final String dictCacheKey = "dictTableConfigCache";
	


	/**
	 * 字典表授权配置列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "list")
	public ModelAndView list(HttpServletRequest request) {
		return new ModelAndView("system/dicttable/tSDictTableConfigList");
	}

	/**
	 * easyui AJAX请求数据
	 * 
	 * @param request
	 * @param response
	 * @param dataGrid
	 * @param user
	 */

	@RequestMapping(params = "datagrid")
	public void datagrid(DictTableConfigEntity tSDictTableConfig,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(DictTableConfigEntity.class, dataGrid);
		//查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tSDictTableConfig, request.getParameterMap());
		try{
		//自定义追加查询条件
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		cq.add();
		this.tSDictTableConfigService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}
	
	/**
	 * 删除字典表授权配置
	 * 
	 * @return
	 */
	@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(DictTableConfigEntity tSDictTableConfig, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		tSDictTableConfig = systemService.getById(DictTableConfigEntity.class, tSDictTableConfig.getId());
		message = "字典表授权配置删除成功";
		try{
			cacheService.clean(dictCacheKey);
			tSDictTableConfigService.delete(tSDictTableConfig);
			systemService.addLog(message, GlobalConstants.LOG_TYPE_DELETE, GlobalConstants.LOG_LEVEL_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "字典表授权配置删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 批量删除字典表授权配置
	 * 
	 * @return
	 */
	 @RequestMapping(params = "doBatchDel")
	@ResponseBody
	public AjaxJson doBatchDel(String ids,HttpServletRequest request){
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "字典表授权配置删除成功";
		try{
			cacheService.clean(dictCacheKey);
			for(String id:ids.split(",")){
				DictTableConfigEntity tSDictTableConfig = systemService.getById(DictTableConfigEntity.class,
				id
				);
				tSDictTableConfigService.delete(tSDictTableConfig);
				systemService.addLog(message, GlobalConstants.LOG_TYPE_DELETE, GlobalConstants.LOG_LEVEL_INFO);
			}
		}catch(Exception e){
			e.printStackTrace();
			message = "字典表授权配置删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}


	/**
	 * 添加字典表授权配置
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doAdd")
	@ResponseBody
	public AjaxJson doAdd(DictTableConfigEntity tSDictTableConfig, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "字典表授权配置添加成功";
		try{
			cacheService.clean(dictCacheKey);
			tSDictTableConfigService.save(tSDictTableConfig);
			systemService.addLog(message, GlobalConstants.LOG_TYPE_INSERT, GlobalConstants.LOG_LEVEL_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "字典表授权配置添加失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 更新字典表授权配置
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doUpdate")
	@ResponseBody
	public AjaxJson doUpdate(DictTableConfigEntity tSDictTableConfig, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "字典表授权配置更新成功";
		DictTableConfigEntity t = tSDictTableConfigService.getById(DictTableConfigEntity.class, tSDictTableConfig.getId());
		try {
			cacheService.clean(dictCacheKey);
			MyBeanUtils.copyBeanNotNull2Bean(tSDictTableConfig, t);
			tSDictTableConfigService.saveOrUpdate(t);
			systemService.addLog(message, GlobalConstants.LOG_TYPE_UPDATE, GlobalConstants.LOG_LEVEL_INFO);
		} catch (Exception e) {
			e.printStackTrace();
			message = "字典表授权配置更新失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	

	/**
	 * 字典表授权配置新增页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goAdd")
	public ModelAndView goAdd(DictTableConfigEntity tSDictTableConfig, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tSDictTableConfig.getId())) {
			tSDictTableConfig = tSDictTableConfigService.getById(DictTableConfigEntity.class, tSDictTableConfig.getId());
			req.setAttribute("tSDictTableConfigPage", tSDictTableConfig);
		}
		return new ModelAndView("system/dicttable/tSDictTableConfig-add");
	}
	/**
	 * 字典表授权配置编辑页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goUpdate")
	public ModelAndView goUpdate(DictTableConfigEntity tSDictTableConfig, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tSDictTableConfig.getId())) {
			tSDictTableConfig = tSDictTableConfigService.getById(DictTableConfigEntity.class, tSDictTableConfig.getId());
			req.setAttribute("tSDictTableConfigPage", tSDictTableConfig);
		}
		return new ModelAndView("system/dicttable/tSDictTableConfig-update");
	}
	
	/**
	 * 导入功能跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "upload")
	public ModelAndView upload(HttpServletRequest req) {
		req.setAttribute("controller_name","tSDictTableConfigController");
		return new ModelAndView("common/upload/pub_excel_upload");
	}
	
	/**
	 * 导出excel
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXls")
	public String exportXls(DictTableConfigEntity tSDictTableConfig,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		CriteriaQuery cq = new CriteriaQuery(DictTableConfigEntity.class, dataGrid);
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tSDictTableConfig, request.getParameterMap());
		List<DictTableConfigEntity> tSDictTableConfigs = this.tSDictTableConfigService.getListByCriteriaQuery(cq,false);
		modelMap.put(NormalExcelConstants.FILE_NAME,"字典表授权配置");
		modelMap.put(NormalExcelConstants.CLASS,DictTableConfigEntity.class);
		modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("字典表授权配置列表", "导出人:"+ResourceUtil.getSessionUser().getRealName(),
			"导出信息"));
		modelMap.put(NormalExcelConstants.DATA_LIST,tSDictTableConfigs);
		return NormalExcelConstants.JEECG_EXCEL_VIEW;
	}
	/**
	 * 导出excel 使模板
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXlsByT")
	public String exportXlsByT(DictTableConfigEntity tSDictTableConfig,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
    	modelMap.put(NormalExcelConstants.FILE_NAME,"字典表授权配置");
    	modelMap.put(NormalExcelConstants.CLASS,DictTableConfigEntity.class);
    	modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("字典表授权配置列表", "导出人:"+ResourceUtil.getSessionUser().getRealName(),
    	"导出信息"));
    	modelMap.put(NormalExcelConstants.DATA_LIST,new ArrayList());
    	return NormalExcelConstants.JEECG_EXCEL_VIEW;
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(params = "importExcel", method = RequestMethod.POST)
	@ResponseBody
	public AjaxJson importExcel(HttpServletRequest request, HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
		for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {
			MultipartFile file = entity.getValue();// 获取上传文件对象
			ImportParams params = new ImportParams();
			params.setTitleRows(2);
			params.setHeadRows(1);
			params.setNeedSave(true);
			try {
				List<DictTableConfigEntity> listTSDictTableConfigEntitys = ExcelImportUtil.importExcel(file.getInputStream(),DictTableConfigEntity.class,params);
				for (DictTableConfigEntity tSDictTableConfig : listTSDictTableConfigEntitys) {
					tSDictTableConfigService.save(tSDictTableConfig);
				}
				j.setMsg("文件导入成功！");
			} catch (Exception e) {
				j.setMsg("文件导入失败！");
				logger.error(e.getMessage());
			}finally{
				try {
					file.getInputStream().close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return j;
	}
	
	
}
