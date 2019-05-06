package org.jeecgframework.web.system.user.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.criterion.Property;
import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.model.common.UploadFile;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.model.json.ComboBox;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.common.model.json.ValidForm;
import org.jeecgframework.core.constant.GlobalConstants;
import org.jeecgframework.core.enums.SysThemesEnum;
import org.jeecgframework.core.util.ExceptionUtil;
import org.jeecgframework.core.util.IpUtil;
import org.jeecgframework.core.util.ListtoMenu;
import org.jeecgframework.core.util.LogUtil;
import org.jeecgframework.core.util.MyBeanUtils;
import org.jeecgframework.core.util.PasswordUtil;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.core.util.RoletoJson;
import org.jeecgframework.core.util.SetListSort;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.core.util.SysThemesUtil;
import org.jeecgframework.core.util.oConvertUtils;
import org.jeecgframework.minidao.pojo.MiniDaoPage;
import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.entity.vo.NormalExcelConstants;
import org.jeecgframework.tag.core.easyui.TagUtil;
import org.jeecgframework.web.system.core.common.DepartAuthGroupDao;
import org.jeecgframework.web.system.depart.entity.DepartEntity;
import org.jeecgframework.web.system.core.manager.ClientManager;
import org.jeecgframework.web.system.core.InterroleEntity;
import org.jeecgframework.web.system.core.InterroleUserEntity;
import org.jeecgframework.web.system.function.entity.FunctionEntity;
import org.jeecgframework.web.system.role.entity.RoleEntity;
import org.jeecgframework.web.system.core.RoleFunctionEntity;
import org.jeecgframework.web.system.core.RoleUserEntity;
import org.jeecgframework.web.system.user.entity.UserEntity;
import org.jeecgframework.web.system.core.UserOrgEntity;
import org.jeecgframework.web.system.service.SystemService;
import org.jeecgframework.web.system.user.service.UserService;
import org.jeecgframework.web.system.util.OrgConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;


/**
 * @ClassName: UserController
 * @Description: TODO(用户管理处理类)
 * @author 张代浩
 */
@Controller
@RequestMapping("/userController")
public class UserController extends BaseController {
	private static final Logger logger = Logger.getLogger(UserController.class);

	private UserService userService;
	private SystemService systemService;
	@Resource
	private ClientManager clientManager;
	@Autowired
	private DepartAuthGroupDao departAuthGroupDao;

	@Autowired
	public void setSystemService(SystemService systemService) {
		this.systemService = systemService;
	}

	@Autowired
	public void setUserService(UserService userService) {
		this.userService = userService;
	}


	/**
	 * 菜单列表
	 * 
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(params = "menu")
	public void menu(HttpServletRequest request, HttpServletResponse response) {
		SetListSort sort = new SetListSort();
		UserEntity u = ResourceUtil.getSessionUser();
		// 登陆者的权限
		Set<FunctionEntity> loginActionlist = new HashSet<FunctionEntity>();// 已有权限菜单
		List<RoleUserEntity> rUsers = systemService.findListByProperty(RoleUserEntity.class, "TSUser.id", u.getId());
		for (RoleUserEntity ru : rUsers) {
			RoleEntity role = ru.getTSRole();
			List<RoleFunctionEntity> roleFunctionList = systemService.findListByProperty(RoleFunctionEntity.class, "TSRole.id", role.getId());
			if (roleFunctionList.size() > 0) {
				for (RoleFunctionEntity roleFunction : roleFunctionList) {
					FunctionEntity function = (FunctionEntity) roleFunction.getTSFunction();
					loginActionlist.add(function);
				}
			}
		}
		List<FunctionEntity> bigActionlist = new ArrayList<FunctionEntity>();// 一级权限菜单
		List<FunctionEntity> smailActionlist = new ArrayList<FunctionEntity>();// 二级权限菜单
		if (loginActionlist.size() > 0) {
			for (FunctionEntity function : loginActionlist) {
				if (function.getFunctionLevel() == 0) {
					bigActionlist.add(function);
				} else if (function.getFunctionLevel() == 1) {
					smailActionlist.add(function);
				}
			}
		}
		// 菜单栏排序
		Collections.sort(bigActionlist, sort);
		Collections.sort(smailActionlist, sort);
		String logString = ListtoMenu.getMenu(bigActionlist, smailActionlist);
		// request.setAttribute("loginMenu",logString);
		try {
			response.getWriter().write(logString);
			response.getWriter().flush();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			try {
				response.getWriter().close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	
	/**
	 * 用户列表页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "user")
	public String user(HttpServletRequest request) {
		// 给部门查询条件中的下拉框准备数据
		List<DepartEntity> departList = systemService.findList(DepartEntity.class);
		request.setAttribute("departsReplace", RoletoJson.listToReplaceStr(departList, "departname", "id"));
		departList.clear();
		return "system/user/userList";
	}

	@RequestMapping(params = "interfaceUser")
	public String interfaceUser(HttpServletRequest request) {
		// 给部门查询条件中的下拉框准备数据
		List<DepartEntity> departList = systemService.findList(DepartEntity.class);
		request.setAttribute("departsReplace", RoletoJson.listToReplaceStr(departList, "departname", "id"));
		departList.clear();
		return "system/user/interfaceUserList";
	}
	
	@RequestMapping(params = "interfaceUserDatagrid")
	public void interfaceUserDatagrid(UserEntity user,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
        CriteriaQuery cq = new CriteriaQuery(UserEntity.class, dataGrid);
        //查询条件组装器
        org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, user);
        Short[] userstate = new Short[]{GlobalConstants.USER_NORMAL, GlobalConstants.USER_ADMIN, GlobalConstants.USER_FORBIDDEN};
        cq.in("status", userstate);
        cq.eq("deleteFlag", GlobalConstants.DELETE_NORMAL);
        cq.eq("userType", GlobalConstants.USER_TYPE_INTERFACE);
        cq.add();
        this.systemService.getDataGridReturn(cq, true);
        List<UserEntity> cfeList = new ArrayList<UserEntity>();
        for (Object o : dataGrid.getResults()) {
            if (o instanceof UserEntity) {
                UserEntity cfe = (UserEntity) o;
                if (cfe.getId() != null && !"".equals(cfe.getId())) {
                    List<InterroleUserEntity> roleUser = systemService.findListByProperty(InterroleUserEntity.class, "TSUser.id", cfe.getId());
                    if (roleUser.size() > 0) {
                        String roleName = "";
                        for (InterroleUserEntity ru : roleUser) {
                            roleName += ru.getInterroleEntity().getRoleName() + ",";
                        }
                        roleName = roleName.substring(0, roleName.length() - 1);
                        cfe.setUserKey(roleName);
                    }
                }
                cfeList.add(cfe);
            }
        }
        TagUtil.datagrid(response, dataGrid);
    }
	
	@RequestMapping(params = "delInterfaceUser")
	@ResponseBody
	public AjaxJson delInterfaceUser(@RequestParam(required = true) String userid) {
		AjaxJson ajaxJson = new AjaxJson();
		try {
			UserEntity user = this.userService.getById(UserEntity.class, userid);
			if(user!=null) {
				String sql = "delete from t_s_interrole_user where user_id = ?";
				this.systemService.executeSql(sql, userid);
				this.userService.delete(user);
				ajaxJson.setMsg("删除成功");
			}else {
				ajaxJson.setMsg("用户不存在");
			}
			
		} catch (Exception e) {
			LogUtil.log("删除失败", e.getMessage());
			ajaxJson.setSuccess(false);
			ajaxJson.setMsg(e.getMessage());
		}
		return ajaxJson;
	}


	/**
	 * 用户信息
	 * 
	 * @return
	 */
	@RequestMapping(params = "userinfo")
	public String userinfo(HttpServletRequest request) {
		UserEntity user = ResourceUtil.getSessionUser();
		request.setAttribute("user", user);
		return "system/user/userinfo";
	}

	/**
	 * 修改密码
	 * 
	 * @return
	 */
	@RequestMapping(params = "changepassword")
	public String changepassword(HttpServletRequest request) {
		UserEntity user = ResourceUtil.getSessionUser();
		request.setAttribute("user", user);
		return "system/user/changepassword";
	}
	@RequestMapping(params = "changeportrait")
	public String changeportrait(HttpServletRequest request) {
		UserEntity user = ResourceUtil.getSessionUser();
		request.setAttribute("user", user);
		return "system/user/changeportrait";
	}
	/**
	 * 修改密码
	 *
	 * @return
	 */
	@RequestMapping(params = "saveportrait")
	@ResponseBody
	public AjaxJson saveportrait(HttpServletRequest request,String fileName) {
		AjaxJson j = new AjaxJson();
		UserEntity user = ResourceUtil.getSessionUser();
		user.setPortrait(fileName);
		j.setMsg("修改成功");
		try {
			systemService.update(user);
		} catch (Exception e) {
			j.setMsg("修改失败");
			e.printStackTrace();
		}
		return j;
	}


	/**
	 * 修改密码
	 * 
	 * @return
	 */
	@RequestMapping(params = "savenewpwd")
	@ResponseBody
	public AjaxJson savenewpwd(HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		UserEntity user = ResourceUtil.getSessionUser();
		logger.info("["+IpUtil.getIpAddr(request)+"][修改密码] start");
		String password = oConvertUtils.getString(request.getParameter("password"));
		String newpassword = oConvertUtils.getString(request.getParameter("newpassword"));
		String pString = PasswordUtil.encrypt(user.getUserName(), password, PasswordUtil.getStaticSalt());
		if (!pString.equals(user.getPassword())) {
			j.setMsg("原密码不正确");
			j.setSuccess(false);
		} else {
			try {
				user.setPassword(PasswordUtil.encrypt(user.getUserName(), newpassword, PasswordUtil.getStaticSalt()));
			} catch (Exception e) {
				e.printStackTrace();
			}
			systemService.update(user);
			j.setMsg("修改成功");
			logger.info("["+IpUtil.getIpAddr(request)+"][修改密码]修改成功 userId:"+user.getUserName());

		}
		return j;
	}
	

	/**
	 * 
	 * 跳转重置用户密码页面
	 * @author Chj
	 */
	
	@RequestMapping(params = "changepasswordforuser")
	public ModelAndView changepasswordforuser(UserEntity user, HttpServletRequest req) {
		logger.info("["+IpUtil.getIpAddr(req)+"][跳转重置用户密码页面]["+user.getUserName()+"]");
		if (StringUtil.isNotEmpty(user.getId())) {
			user = systemService.getById(UserEntity.class, user.getId());
			req.setAttribute("user", user);
			idandname(req, user);
			//System.out.println(user.getPassword()+"-----"+user.getRealName());
		}
		return new ModelAndView("system/user/adminchangepwd");
	}
	
	
	
	/**
	 * 重置密码
	 * @param req
	 * @return
	 */
	@RequestMapping(params = "savenewpwdforuser")
	@ResponseBody
	public AjaxJson savenewpwdforuser(HttpServletRequest req) {
		logger.info("["+IpUtil.getIpAddr(req)+"][重置密码] start");
		String message = null;
		AjaxJson j = new AjaxJson();
		String id = oConvertUtils.getString(req.getParameter("id"));
		String password = oConvertUtils.getString(req.getParameter("password"));
		
		if (StringUtil.isNotEmpty(id)) {
			UserEntity users = systemService.getById(UserEntity.class,id);
			if("admin".equals(users.getUserName()) && !"admin".equals(ResourceUtil.getSessionUser().getUserName())){
				message = "超级管理员[admin]，只有admin本人可操作，其他人无权限!";
				logger.info("["+IpUtil.getIpAddr(req)+"]"+message);
				j.setMsg(message);
				return j;
			}
			
			//System.out.println(users.getUserName());
			users.setPassword(PasswordUtil.encrypt(users.getUserName(), password, PasswordUtil.getStaticSalt()));
			users.setStatus(GlobalConstants.USER_NORMAL);
			users.setActivitiSync(users.getActivitiSync());
			systemService.update(users);
			message = "用户: " + users.getUserName() + "密码重置成功";
			systemService.addLog(message, GlobalConstants.LOG_TYPE_UPDATE, GlobalConstants.LOG_LEVEL_INFO);
			logger.info("["+IpUtil.getIpAddr(req)+"][重置密码]"+message);
		} 
		
		j.setMsg(message);

		return j;
	}
	/**
	 * 锁定账户
	
	 * 
	 * @author pu.chen
	 */
	@RequestMapping(params = "lock")
	@ResponseBody
	public AjaxJson lock(String id, HttpServletRequest req) {
		AjaxJson j = new AjaxJson();
		String message = null;
		UserEntity user = systemService.getById(UserEntity.class, id);
		if("admin".equals(user.getUserName())){
			message = "超级管理员[admin]不可操作";
			j.setMsg(message);
			return j;
		}
		String lockValue=req.getParameter("lockvalue");

		user.setStatus(new Short(lockValue));
		try{
		userService.update(user);
		if("0".equals(lockValue)){
			message = "用户：" + user.getUserName() + "锁定成功!";
		}else if("1".equals(lockValue)){
			message = "用户：" + user.getUserName() + "激活成功!";
		}
		systemService.addLog(message, GlobalConstants.LOG_TYPE_UPDATE, GlobalConstants.LOG_LEVEL_INFO);
		logger.info("["+IpUtil.getIpAddr(req)+"][锁定账户]"+message);
		}catch(Exception e){
			message = "操作失败!";
		}
		j.setMsg(message);
		return j;
	}
	

	/**
	 * 得到角色列表
	 * 
	 * @return
	 */
	@RequestMapping(params = "role")
	@ResponseBody
	public List<ComboBox> role(HttpServletResponse response, HttpServletRequest request, ComboBox comboBox) {
		String id = request.getParameter("id");
		List<ComboBox> comboBoxs = new ArrayList<ComboBox>();
		List<RoleEntity> roles = new ArrayList<RoleEntity>();
		if (StringUtil.isNotEmpty(id)) {
			List<RoleUserEntity> roleUser = systemService.findListByProperty(RoleUserEntity.class, "TSUser.id", id);
			if (roleUser.size() > 0) {
				for (RoleUserEntity ru : roleUser) {
					roles.add(ru.getTSRole());
				}
			}
		}
		List<RoleEntity> roleList = systemService.findList(RoleEntity.class);
		comboBoxs = TagUtil.getComboBox(roleList, roles, comboBox);

		roleList.clear();
		roles.clear();

		return comboBoxs;
	}

	/**
	 * 得到部门列表
	 * 
	 * @return
	 */
	@RequestMapping(params = "depart")
	@ResponseBody
	public List<ComboBox> depart(HttpServletResponse response, HttpServletRequest request, ComboBox comboBox) {
		String id = request.getParameter("id");
		List<ComboBox> comboBoxs = new ArrayList<ComboBox>();
		List<DepartEntity> departs = new ArrayList();
		if (StringUtil.isNotEmpty(id)) {
			UserEntity user = systemService.getById(UserEntity.class, id);
//			if (user.getTSDepart() != null) {
//				TSDepart depart = systemService.get(TSDepart.class, user.getTSDepart().getId());
//				departs.add(depart);
//			}
            // todo zhanggm 获取指定用户的组织机构列表
            List<DepartEntity[]> resultList = systemService.findHql("from DepartEntity d,UserOrgEntity uo where d.id=uo.orgId and uo.id=?", id);
            for (DepartEntity[] departArr : resultList) {
                departs.add(departArr[0]);
            }
        }
		List<DepartEntity> departList = systemService.findList(DepartEntity.class);
		comboBoxs = TagUtil.getComboBox(departList, departs, comboBox);
		return comboBoxs;
	}

	/**
	 * easyuiAJAX用户列表请求数据 
	 * @param request
	 * @param response
	 * @param dataGrid
	 */
	@RequestMapping(params = "datagridByOrgCode")
	public void datagridByOrgCode(UserEntity user,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		Map<String, Map<String, Object>> extMap = new HashMap<String, Map<String, Object>>();
		String departid = request.getParameter("orgIds");
		
		// Minidao查询条件
		if (oConvertUtils.isNotEmpty(user.getUserName())) {
			user.setUserName(user.getUserName().replace("*", "%"));
		}
		if (oConvertUtils.isNotEmpty(user.getRealName())) {
			user.setRealName(user.getRealName().replace("*", "%"));
		}

		//获取公司编码
		String orgCode = null;
		if (oConvertUtils.isNotEmpty(departid)) {
			DepartEntity tsdepart = this.systemService.getById(DepartEntity.class, departid);
			orgCode = tsdepart.getOrgCode();
		}
		
		//查询机构下用户列表
		MiniDaoPage<UserEntity> list = departAuthGroupDao.getUserByDepartCode(dataGrid.getPage(), dataGrid.getRows(),orgCode , user);
		dataGrid.setTotal(list.getTotal());
		dataGrid.setResults(list.getResults());

		//获取用户的部门名称
		for (UserEntity u : list.getResults()) {
			if (oConvertUtils.isNotEmpty(u.getId())) {
				List<String> depNames = departAuthGroupDao.getUserDepartNames(u.getId());
				// 此为针对原来的行数据，拓展的新字段
				Map<String,Object> m = new HashMap<String,Object>();
				m.put("orgNames", depNames != null ? depNames.toArray() : null);
				extMap.put(u.getId(), m);
			}
		}
		TagUtil.datagrid(response, dataGrid, extMap);
    }
	
	/**
	 * easyuiAJAX用户列表请求数据 
	 * @param request
	 * @param response
	 * @param dataGrid
	 */
	@RequestMapping(params = "datagrid")
	public void datagrid(UserEntity user,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
        CriteriaQuery cq = new CriteriaQuery(UserEntity.class, dataGrid);
        //查询条件组装器
        org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, user);

        Short[] userstate = new Short[]{GlobalConstants.USER_NORMAL, GlobalConstants.USER_ADMIN, GlobalConstants.USER_FORBIDDEN};
        cq.in("status", userstate);
        cq.eq("deleteFlag", GlobalConstants.DELETE_NORMAL);
        cq.eq("userType", GlobalConstants.USER_TYPE_SYSTEM);//用户列表不显示接口类型的用户
        
        String orgIds = request.getParameter("orgIds");
        List<String> orgIdList = extractIdListByComma(orgIds);
        // 获取 当前组织机构的用户信息
        if (!CollectionUtils.isEmpty(orgIdList)) {
            CriteriaQuery subCq = new CriteriaQuery(UserOrgEntity.class);
            subCq.setProjection(Property.forName("tsUser.id"));
            subCq.in("tsDepart.id", orgIdList.toArray());
            subCq.add();

            cq.add(Property.forName("id").in(subCq.getDetachedCriteria()));
        }

        cq.add();
        this.systemService.getDataGridReturn(cq, true);
        List<UserEntity> cfeList = new ArrayList<UserEntity>();
        for (Object o : dataGrid.getResults()) {
            if (o instanceof UserEntity) {
				UserEntity cfe = (UserEntity) o;
                if (cfe.getId() != null && !"".equals(cfe.getId())) {
                    List<RoleUserEntity> roleUser = systemService.findListByProperty(RoleUserEntity.class, "TSUser.id", cfe.getId());
                    if (roleUser.size() > 0) {
                        String roleName = "";
                        for (RoleUserEntity ru : roleUser) {
                            roleName += ru.getTSRole().getRoleName() + ",";
                        }
                        roleName = roleName.substring(0, roleName.length() - 1);
                        cfe.setUserKey(roleName);
                    }
                }
                cfeList.add(cfe);
            }
        }
        TagUtil.datagrid(response, dataGrid);
    }
	
	/**
	 * 用户删除选择对话框
	 * 
	 * @return
	 */
	@RequestMapping(params = "deleteDialog")
	public String deleteDialog(UserEntity user,HttpServletRequest request) {
		request.setAttribute("user", user);
		return "system/user/user-delete";
	} 
	
	@RequestMapping(params = "delete")
	@ResponseBody
	public AjaxJson delete(UserEntity user, @RequestParam String deleteType, HttpServletRequest req) {
		
		if (deleteType.equals("delete")) {
			return this.del(user, req);
		}else if (deleteType.equals("deleteTrue")) {
			return this.trueDel(user, req);
		}else{
			AjaxJson j = new AjaxJson();
			
			j.setMsg("删除逻辑参数异常,请重试.");
			return j;
		}
	}

	
	/**
	 * 用户信息录入和更新
	 * 
	 * @param user
	 * @param req
	 * @return
	 */
	@RequestMapping(params = "del")
	@ResponseBody
	public AjaxJson del(UserEntity user, HttpServletRequest req) {
		String message = null;
		AjaxJson j = new AjaxJson();
		if("admin".equals(user.getUserName())){
			message = "超级管理员[admin]不可删除";
			j.setMsg(message);
			return j;
		}
		user = systemService.getById(UserEntity.class, user.getId());
//		List<RoleUserEntity> roleUser = systemService.findByProperty(RoleUserEntity.class, "TSUser.id", user.getId());
		if (!user.getStatus().equals(GlobalConstants.USER_ADMIN)) {

			user.setDeleteFlag(GlobalConstants.DELETE_FORBIDDEN);
			userService.update(user);
			message = "用户：" + user.getUserName() + "删除成功";
			logger.info("["+IpUtil.getIpAddr(req)+"][逻辑删除用户]"+message);

			
/**
			if (roleUser.size()>0) {
				// 删除用户时先删除用户和角色关系表
				delRoleUser(user);

                systemService.executeSql("delete from t_s_user_org where user_id=?", user.getId()); // 删除 用户-机构 数据

                userService.delete(user);
				message = "用户：" + user.getUserName() + "删除成功";
				systemService.addLog(message, GlobalConstants.LOG_TYPE_DELETE, GlobalConstants.LOG_LEVEL_INFO);
			} else {
				userService.delete(user);
				message = "用户：" + user.getUserName() + "删除成功";
			}
**/	
		} else {
			message = "超级管理员不可删除";
		}

		j.setMsg(message);
		return j;
	}
	
	/**
	 * 真实删除
	 * @param user
	 * @param req
	 * @return
	 */
	@RequestMapping(params = "trueDel")
	@ResponseBody
	public AjaxJson trueDel(UserEntity user, HttpServletRequest req) {
		String message = null;
		AjaxJson j = new AjaxJson();
		if("admin".equals(user.getUserName())){
			message = "超级管理员[admin]不可删除";
			j.setMsg(message);
			return j;
		}
		user = systemService.getById(UserEntity.class, user.getId());

		/*List<RoleUserEntity> roleUser = systemService.findByProperty(RoleUserEntity.class, "TSUser.id", user.getId());
		if (!user.getStatus().equals(GlobalConstants.USER_ADMIN)) {
			if (roleUser.size()>0) {
				// 删除用户时先删除用户和角色关系表
				delRoleUser(user);
                systemService.executeSql("delete from t_s_user_org where user_id=?", user.getId()); // 删除 用户-机构 数据
                userService.delete(user);
				message = "用户：" + user.getUserName() + "删除成功";
				systemService.addLog(message, GlobalConstants.LOG_TYPE_DELETE, GlobalConstants.LOG_LEVEL_INFO);
			} else {
				userService.delete(user);
				message = "用户：" + user.getUserName() + "删除成功";
			}
		} else {
			message = "超级管理员不可删除";
		}*/
		
		try {
			message = userService.trueDel(user);
			logger.info("["+IpUtil.getIpAddr(req)+"][真实删除用户]"+message);
		} catch (Exception e) {
			e.printStackTrace();
			message ="删除失败";
		}


		j.setMsg(message);
		return j;
	}

	/*public void delRoleUser(TSUser user) {
		// 同步删除用户角色关联表
		List<RoleUserEntity> roleUserList = systemService.findByProperty(RoleUserEntity.class, "TSUser.id", user.getId());
		if (roleUserList.size() >= 1) {
			for (RoleUserEntity tRoleUser : roleUserList) {
				systemService.delete(tRoleUser);
			}
		}
	}*/
	/**
	 * 检查用户名
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "checkUser")
	@ResponseBody
	public ValidForm checkUser(HttpServletRequest request) {
		ValidForm v = new ValidForm();
		String userName=oConvertUtils.getString(request.getParameter("param"));
		String code=oConvertUtils.getString(request.getParameter("code"));
		List<UserEntity> roles=systemService.findListByProperty(UserEntity.class,"userName",userName);
		if(roles.size()>0&&!code.equals(userName))
		{
			v.setInfo("用户名已存在");
			v.setStatus("n");
		}
		return v;
	}

	/**
	 * 检查用户邮箱
	 * @param request
	 * @return
	 */
	@RequestMapping(params="checkUserEmail")
	@ResponseBody
	public ValidForm checkUserEmail(HttpServletRequest request){
		ValidForm validForm = new ValidForm();
		String email=oConvertUtils.getString(request.getParameter("param"));
		String code=oConvertUtils.getString(request.getParameter("code"));
		List<UserEntity> userList=systemService.findListByProperty(UserEntity.class,"email",email);
		if(userList.size()>0&&!code.equals(email))
		{
			validForm.setInfo("邮箱已绑定相关用户信息");
			validForm.setStatus("n");
		}
		return validForm;
	}

	
	/**
	 * 用户录入
	 * 
	 * @param user
	 * @param req
	 * @return
	 */
	@RequestMapping(params = "saveUser")
	@ResponseBody
	public AjaxJson saveUser(HttpServletRequest req, UserEntity user) {
		String message = null;
		AjaxJson j = new AjaxJson();

		Short logType=GlobalConstants.LOG_TYPE_UPDATE;
		// 得到用户的角色
		String roleid = oConvertUtils.getString(req.getParameter("roleid"));
		String orgid=oConvertUtils.getString(req.getParameter("orgIds"));
		if (StringUtil.isNotEmpty(user.getId())) {
			UserEntity users = systemService.getById(UserEntity.class, user.getId());
			users.setEmail(user.getEmail());
			users.setOfficePhone(user.getOfficePhone());
			users.setMobilePhone(user.getMobilePhone());
			users.setDevFlag(user.getDevFlag());
			users.setRealName(user.getRealName());
			users.setStatus(GlobalConstants.USER_NORMAL);
			users.setActivitiSync(user.getActivitiSync());
			this.userService.saveOrUpdate(users, orgid.split(","), roleid.split(","));
			message = "用户: " + users.getUserName() + "更新成功";
		} else {
			UserEntity users = systemService.getByProperty(UserEntity.class, "userName",user.getUserName());
			if (users != null) {
				message = "用户: " + users.getUserName() + "已经存在";
			} else {
				user.setPassword(PasswordUtil.encrypt(user.getUserName(), oConvertUtils.getString(req.getParameter("password")), PasswordUtil.getStaticSalt()));
				user.setStatus(GlobalConstants.USER_NORMAL);
				user.setDeleteFlag(GlobalConstants.DELETE_NORMAL);
				//默认添加为系统用户
				user.setUserType(GlobalConstants.USER_TYPE_SYSTEM);
				this.userService.saveOrUpdate(user, orgid.split(","), roleid.split(","));				
				message = "用户: " + user.getUserName() + "添加成功";
				logType=GlobalConstants.LOG_TYPE_INSERT;
			}
		}
		systemService.addLog(message, logType, GlobalConstants.LOG_LEVEL_INFO);
		j.setMsg(message);
		logger.info("["+IpUtil.getIpAddr(req)+"][添加编辑用户]"+message);
		return j;

	}

    /**
     * 保存 用户-组织机构 关系信息
     * @param request request
     * @param user user
     */
    private void saveUserOrgList(HttpServletRequest request, UserEntity user) {
        String orgIds = oConvertUtils.getString(request.getParameter("orgIds"));

        List<UserOrgEntity> userOrgList = new ArrayList<UserOrgEntity>();
        List<String> orgIdList = extractIdListByComma(orgIds);
        for (String orgId : orgIdList) {
			DepartEntity depart = new DepartEntity();
            depart.setId(orgId);

			UserOrgEntity userOrg = new UserOrgEntity();
            userOrg.setTsUser(user);
            userOrg.setTsDepart(depart);

            userOrgList.add(userOrg);
        }
        if (!userOrgList.isEmpty()) {
            systemService.batchAdd(userOrgList);
        }
    }


    protected void saveRoleUser(UserEntity user, String roleidstr) {
		String[] roleids = roleidstr.split(",");
		for (int i = 0; i < roleids.length; i++) {
			RoleUserEntity rUser = new RoleUserEntity();
			RoleEntity role = systemService.getById(RoleEntity.class, roleids[i]);
			rUser.setTSRole(role);
			rUser.setTSUser(user);
			systemService.add(rUser);

		}
	}

	/**
	 * 用户选择角色跳转页面
	 * 
	 * @return
	 */
	@RequestMapping(params = "roles")
	public ModelAndView roles(HttpServletRequest request) {
		//--author：zhoujf-----start----date:20150531--------for: 编辑用户，选择角色,弹出的角色列表页面，默认没选中
		ModelAndView mv = new ModelAndView("system/user/users");
		String ids = oConvertUtils.getString(request.getParameter("ids"));
		mv.addObject("ids", ids);
		return mv;
	}

	/**
	 * 角色显示列表
	 * 
	 * @param request
	 * @param response
	 * @param dataGrid
	 */
	@RequestMapping(params = "datagridRole")
	public void datagridRole(RoleEntity tsRole, HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(RoleEntity.class, dataGrid);
		//查询条件组装器

		cq.eq("roleType", OrgConstants.SYSTEM_ROLE_TYPE);//默认只查询系统角色

		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tsRole);
		this.systemService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * easyuiAJAX请求数据： 用户选择角色列表
	 * 
	 * @param request
	 * @param response
	 * @param dataGrid
	 * @param user
	 */
	@RequestMapping(params = "addorupdate")
	public ModelAndView addorupdate(UserEntity user, HttpServletRequest req) {

		/*		List<TSDepart> departList = new ArrayList<TSDepart>();
		String departid = oConvertUtils.getString(req.getParameter("departid"));
		if(!StringUtil.isEmpty(departid)){
			departList.add((TSDepart)systemService.getEntity(TSDepart.class,departid));
		}else {
			departList.addAll((List)systemService.getList(TSDepart.class));
		}
		req.setAttribute("departList", departList);*/

        List<String> orgIdList = new ArrayList<String>();
		DepartEntity tsDepart = new DepartEntity();
		if (StringUtil.isNotEmpty(user.getId())) {
			user = systemService.getById(UserEntity.class, user.getId());
			
			req.setAttribute("user", user);
			idandname(req, user);
			getOrgInfos(req, user);

		}else{
			//组织机构关联用户录入
			String departid = oConvertUtils.getString(req.getParameter("departid"));
			if(StringUtils.isNotEmpty(departid)){
				DepartEntity depart = systemService.getById(DepartEntity.class,departid);
				if(depart!=null){
					req.setAttribute("orgIds", depart.getId()+",");
					req.setAttribute("departname", depart.getDepartname()+",");
				}
			}
			//角色管理关联用户录入
			String roleId = oConvertUtils.getString(req.getParameter("roleId"));
			if(StringUtils.isNotEmpty(roleId)){
				RoleEntity tsRole = systemService.getById(RoleEntity.class,roleId);
				if(tsRole!=null){
					req.setAttribute("id", roleId);
					req.setAttribute("roleName", tsRole.getRoleName());
				}
			}
		}

		req.setAttribute("tsDepart", tsDepart);
        //req.setAttribute("orgIdList", JSON.toJSON(orgIdList));


        return new ModelAndView("system/user/user");
	}

	/**
	 * 添加、编辑接口用户
	 * 
	 * @param request
	 * @param response
	 * @param dataGrid
	 * @param user
	 */
	@RequestMapping(params = "addorupdateInterfaceUser")
	public ModelAndView addorupdateInterfaceUser(UserEntity user, HttpServletRequest req) {

		if (StringUtil.isNotEmpty(user.getId())) {
			user = systemService.getById(UserEntity.class, user.getId());
			req.setAttribute("user", user);
			interfaceroleidandname(req, user);
		}else{
			String roleId = req.getParameter("roleId");
			if(StringUtils.isNotBlank(roleId)) {
				InterroleEntity role = systemService.getById(InterroleEntity.class, roleId);
				req.setAttribute("roleId", roleId);
				req.setAttribute("roleName", role.getRoleName());
			}
		}

        return new ModelAndView("system/user/interfaceUser");
	}
	
	public void interfaceroleidandname(HttpServletRequest req, UserEntity user) {
		List<InterroleUserEntity> roleUsers = systemService.findListByProperty(InterroleUserEntity.class, "TSUser.id", user.getId());
		String roleId = "";
		String roleName = "";
		if (roleUsers.size() > 0) {
			for (InterroleUserEntity interroleUserEntity : roleUsers) {
				roleId += interroleUserEntity.getInterroleEntity().getId() + ",";
				roleName += interroleUserEntity.getInterroleEntity().getRoleName() + ",";
			}
		}
		req.setAttribute("roleId", roleId);
		req.setAttribute("roleName", roleName);

	}
	
	/**
	 * 接口用户录入
	 * 
	 * @param user
	 * @param req
	 * @return
	 */
	@RequestMapping(params = "saveInterfaceUser")
	@ResponseBody
	public AjaxJson saveInterfaceUser(HttpServletRequest req, UserEntity user) {
		String message = null;
		AjaxJson j = new AjaxJson();
		// 得到用户的角色
		String roleid = oConvertUtils.getString(req.getParameter("roleid"));
		String password = oConvertUtils.getString(req.getParameter("password"));
		if (StringUtil.isNotEmpty(user.getId())) {
			UserEntity users = systemService.getById(UserEntity.class, user.getId());
			users.setEmail(user.getEmail());
			users.setOfficePhone(user.getOfficePhone());
			users.setMobilePhone(user.getMobilePhone());
			users.setDevFlag(user.getDevFlag());

			user.setPassword(PasswordUtil.encrypt(user.getUserName(), password, PasswordUtil.getStaticSalt()));

//            systemService.executeSql("delete from t_s_user_org where user_id=?", user.getId());
//            saveUserOrgList(req, user);
//            users.setTSDepart(user.getTSDepart());

			users.setRealName(user.getRealName());
			users.setStatus(GlobalConstants.USER_NORMAL);
			users.setActivitiSync(user.getActivitiSync());
			
			users.setUserNameEn(user.getUserNameEn());
			users.setUserType(user.getUserType());
//			users.setPersonType(user.getPersonType());
			users.setSex(user.getSex());
			users.setEmpNo(user.getEmpNo());
			users.setCitizenNo(user.getCitizenNo());
			users.setFax(user.getFax());
			users.setAddress(user.getAddress());
			users.setPost(user.getPost());
			users.setMemo(user.getMemo());
			
			systemService.update(users);
			List<RoleUserEntity> ru = systemService.findListByProperty(RoleUserEntity.class, "TSUser.id", user.getId());
			systemService.deleteByCollection(ru);//TODO ?
			message = "用户: " + users.getUserName() + "更新成功";
//			if (StringUtil.isNotEmpty(roleid)) {
//				saveInterfaceRoleUser(users, roleid);
//			}
			systemService.addLog(message, GlobalConstants.LOG_TYPE_UPDATE, GlobalConstants.LOG_LEVEL_INFO);
		} else {
			UserEntity users = systemService.getByProperty(UserEntity.class, "userName",user.getUserName());
			if (users != null) {
				message = "用户: " + users.getUserName() + "已经存在";
			} else {
				user.setPassword(PasswordUtil.encrypt(user.getUserName(), password, PasswordUtil.getStaticSalt()));
//				if (user.getTSDepart().equals("")) {
//					user.setTSDepart(null);
//				}
				user.setStatus(GlobalConstants.USER_NORMAL);
				user.setDeleteFlag(GlobalConstants.DELETE_NORMAL);
				systemService.add(user);
                // todo zhanggm 保存多个组织机构
//                saveUserOrgList(req, user);
				message = "用户: " + user.getUserName() + "添加成功";
				if (StringUtil.isNotEmpty(roleid)) {
					saveInterfaceRoleUser(user, roleid);
				}
				systemService.addLog(message, GlobalConstants.LOG_TYPE_INSERT, GlobalConstants.LOG_LEVEL_INFO);
			}

		}
		j.setMsg(message);
		logger.info("["+IpUtil.getIpAddr(req)+"][添加编辑用户]"+message);
		return j;
	}
	
	protected void saveInterfaceRoleUser(UserEntity user, String roleidstr) {
		String[] roleids = roleidstr.split(",");
		for (int i = 0; i < roleids.length; i++) {
			InterroleUserEntity rUser = new InterroleUserEntity();
			InterroleEntity role = systemService.getById(InterroleEntity.class, roleids[i]);
			rUser.setInterroleEntity(role);
			rUser.setTSUser(user);
			systemService.add(rUser);
		}
	}


    /**
     * 用户的登录后的组织机构选择页面
     * @param request request
     * @return 用户选择组织机构页面
     */
	@RequestMapping(params = "userOrgSelect")
	public ModelAndView userOrgSelect(HttpServletRequest request) {
		List<DepartEntity> orgList = new ArrayList<DepartEntity>();
		String userId = oConvertUtils.getString(request.getParameter("userId"));

        List<Object[]> orgArrList = systemService.findHql("from DepartEntity d,UserOrgEntity uo where d.id=uo.tsDepart.id and uo.tsUser.id=?", new String[]{userId});
        for (Object[] departs : orgArrList) {
            orgList.add((DepartEntity) departs[0]);
        }
        request.setAttribute("orgList", orgList);

		UserEntity user = systemService.getById(UserEntity.class, userId);
        request.setAttribute("user", user);

		return new ModelAndView("system/user/userOrgSelect");
    }


	public void idandname(HttpServletRequest req, UserEntity user) {
		List<RoleUserEntity> roleUsers = systemService.findListByProperty(RoleUserEntity.class, "TSUser.id", user.getId());
		String roleId = "";
		String roleName = "";
		if (roleUsers.size() > 0) {
			for (RoleUserEntity tRoleUser : roleUsers) {
				roleId += tRoleUser.getTSRole().getId() + ",";
				roleName += tRoleUser.getTSRole().getRoleName() + ",";
			}
		}
		req.setAttribute("id", roleId);
		req.setAttribute("roleName", roleName);

	}
	
	public void getOrgInfos(HttpServletRequest req, UserEntity user) {
		List<UserOrgEntity> tSUserOrgs = systemService.findListByProperty(UserOrgEntity.class, "tsUser.id", user.getId());
		String orgIds = "";
		String departname = "";
		if (tSUserOrgs.size() > 0) {
			for (UserOrgEntity tSUserOrg : tSUserOrgs) {
				orgIds += tSUserOrg.getTsDepart().getId() + ",";
				departname += tSUserOrg.getTsDepart().getDepartname() + ",";
			}
		}
		req.setAttribute("orgIds", orgIds);
		req.setAttribute("departname", departname);

	}
	
	/**
	 * 根据部门和角色选择用户跳转页面
	 */
	@RequestMapping(params = "choose")
	public String choose(HttpServletRequest request) {
		List<RoleEntity> roles = systemService.findList(RoleEntity.class);
		request.setAttribute("roleList", roles);
		return "system/membership/checkuser";
	}

	/**
	 * 部门和角色选择用户的panel跳转页面
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "chooseUser")
	public String chooseUser(HttpServletRequest request) {
		String departid = request.getParameter("departid");
		String roleid = request.getParameter("roleid");
		request.setAttribute("roleid", roleid);
		request.setAttribute("departid", departid);
		return "system/membership/userlist";
	}

	/**
	 * 部门和角色选择用户的用户显示列表
	 * 
	 * @param request
	 * @param response
	 * @param dataGrid
	 */
	@RequestMapping(params = "datagridUser")
	public void datagridUser(HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		String departid = request.getParameter("departid");
		String roleid = request.getParameter("roleid");
		CriteriaQuery cq = new CriteriaQuery(UserEntity.class, dataGrid);
		if (departid.length() > 0) {
			cq.eq("TDepart.departid", oConvertUtils.getInt(departid, 0));
			cq.add();
		}
		String userid = "";
		if (roleid.length() > 0) {
			List<RoleUserEntity> roleUsers = systemService.findListByProperty(RoleUserEntity.class, "TRole.roleid", oConvertUtils.getInt(roleid, 0));
			if (roleUsers.size() > 0) {
				for (RoleUserEntity tRoleUser : roleUsers) {
					userid += tRoleUser.getTSUser().getId() + ",";
				}
			}
			cq.in("userid", oConvertUtils.getInts(userid.split(",")));
			cq.add();
		}
		this.systemService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * 根据部门和角色选择用户跳转页面
	 */
	@RequestMapping(params = "roleDepart")
	public String roleDepart(HttpServletRequest request) {
		List<RoleEntity> roles = systemService.findList(RoleEntity.class);
		request.setAttribute("roleList", roles);
		return "system/membership/roledepart";
	}

	/**
	 * 部门和角色选择用户的panel跳转页面
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "chooseDepart")
	public ModelAndView chooseDepart(HttpServletRequest request) {
		String nodeid = request.getParameter("nodeid");
		ModelAndView modelAndView = null;
		if (nodeid.equals("role")) {
			modelAndView = new ModelAndView("system/membership/users");
		} else {
			modelAndView = new ModelAndView("system/membership/departList");
		}
		return modelAndView;
	}

	/**
	 * 部门和角色选择用户的用户显示列表
	 * 
	 * @param request
	 * @param response
	 * @param dataGrid
	 */
	@RequestMapping(params = "datagridDepart")
	public void datagridDepart(HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(DepartEntity.class, dataGrid);
		systemService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}

//	/**
//	 * 测试 【Datatable 数据列表】
//	 * 
//	 * @param user
//	 * @param req
//	 * @return
//	 */
//	@RequestMapping(params = "test")
//	public void test(HttpServletRequest request, HttpServletResponse response) {
//		String jString = request.getParameter("_dt_json");
//		DataTables dataTables = new DataTables(request);
//		CriteriaQuery cq = new CriteriaQuery(TSUser.class, dataTables);
//		String username = request.getParameter("userName");
//		if (username != null) {
//			cq.like("userName", username);
//			cq.add();
//		}
//		DataTableReturn dataTableReturn = systemService.getDataTableReturn(cq, true);
//		TagUtil.datatable(response, dataTableReturn, "id,userName,mobilePhone,TSDepart_departname");
//	}

	/**
	 * 用户列表页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "index")
	public String index() {
		return "bootstrap/main";
	}

	/**
	 * 用户列表页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "main")
	public String main() {
		return "bootstrap/test";
	}

	/**
	 * 测试
	 * 
	 * @return
	 */
	@RequestMapping(params = "testpage")
	public String testpage(HttpServletRequest request) {
		return "test/test";
	}

	/**
	 * 设置签名跳转页面
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "addsign")
	public ModelAndView addsign(HttpServletRequest request) {
		String id = request.getParameter("id");
		request.setAttribute("id", id);
		return new ModelAndView("system/user/usersign");
	}

	/**
	 * 用户录入
	 * 
	 * @param user
	 * @param req
	 * @return
	 */

	@RequestMapping(params = "savesign", method = RequestMethod.POST)
	@ResponseBody
	public AjaxJson savesign(HttpServletRequest req) {
		String message = null;
		UploadFile uploadFile = new UploadFile(req);
		String id = uploadFile.get("id");
		UserEntity user = systemService.getById(UserEntity.class, id);
		uploadFile.setRealPath("signatureFile");
		uploadFile.setCusPath("signature");
		uploadFile.setByteField("signature");
		uploadFile.setBasePath("resources");
		uploadFile.setRename(false);
		uploadFile.setObject(user);
		AjaxJson j = new AjaxJson();
		message = user.getUserName() + "设置签名成功";
		systemService.uploadFile(uploadFile);
		systemService.addLog(message, GlobalConstants.LOG_TYPE_INSERT, GlobalConstants.LOG_LEVEL_INFO);
		j.setMsg(message);

		return j;
	}
	/**
	 * 测试组合查询功能
	 * @param user
	 * @param request
	 * @param response
	 * @param dataGrid
	 */
	@RequestMapping(params = "testSearch")
	public void testSearch(UserEntity user, HttpServletRequest request,HttpServletResponse response,DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(UserEntity.class, dataGrid);
		if(user.getUserName()!=null){
			cq.like("userName", user.getUserName());
		}
		if(user.getRealName()!=null){
			cq.like("realName", user.getRealName());
		}
		cq.add();
		this.systemService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}
	@RequestMapping(params = "changestyle")
	public String changeStyle(HttpServletRequest request) {
		UserEntity user = ResourceUtil.getSessionUser();
		if(user==null){
			return "login/login";
		}
//		String indexStyle = "shortcut";
//		String cssTheme="";
//		Cookie[] cookies = request.getCookies();
//		for (Cookie cookie : cookies) {
//			if(cookie==null || StringUtils.isEmpty(cookie.getName())){
//				continue;
//			}
//			if(cookie.getName().equalsIgnoreCase("JEECGINDEXSTYLE")){
//				indexStyle = cookie.getValue();
//			}
//			if(cookie.getName().equalsIgnoreCase("JEECGCSSTHEME")){
//				cssTheme = cookie.getValue();
//			}
//		}
		SysThemesEnum sysThemesEnum = SysThemesUtil.getSysTheme(request);
		request.setAttribute("indexStyle", sysThemesEnum.getStyle());
//		request.setAttribute("cssTheme", cssTheme);
		return "system/user/changestyle";
	}
	/**
	* @Title: saveStyle
	* @Description: 修改首页样式
	* @param request
	* @return AjaxJson    
	* @throws
	 */
	@RequestMapping(params = "savestyle")
	@ResponseBody
	public AjaxJson saveStyle(HttpServletRequest request,HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		j.setSuccess(Boolean.FALSE);
		UserEntity user = ResourceUtil.getSessionUser();
		if(user!=null){
			String indexStyle = request.getParameter("indexStyle");
//			String cssTheme = request.getParameter("cssTheme");

//			if(StringUtils.isNotEmpty(cssTheme)){
//				Cookie cookie4css = new Cookie("JEECGCSSTHEME", cssTheme);
//				cookie4css.setMaxAge(3600*24*30);
//				response.addCookie(cookie4css);
//				logger.info("cssTheme:"+cssTheme);
//			}else if("ace".equals(indexStyle)){
//				Cookie cookie4css = new Cookie("JEECGCSSTHEME", "metro");
//				cookie4css.setMaxAge(3600*24*30);
//				response.addCookie(cookie4css);
//				logger.info("cssTheme:metro");

//			}else {
//				Cookie cookie4css = new Cookie("JEECGCSSTHEME", "");
//				cookie4css.setMaxAge(3600*24*30);
//				response.addCookie(cookie4css);
//				logger.info("cssTheme:default");
//			}

			
			if(StringUtils.isNotEmpty(indexStyle)){
				Cookie cookie = new Cookie("JEECGINDEXSTYLE", indexStyle);
				//设置cookie有效期为一个月
				cookie.setMaxAge(3600*24*30);
				response.addCookie(cookie);
				logger.debug(" ----- 首页样式: indexStyle ----- "+indexStyle);
				j.setSuccess(Boolean.TRUE);
				j.setMsg("样式修改成功，请刷新页面");
			}

			try {
				clientManager.getClient().getFunctions().clear();
			} catch (Exception e) {
			}

		}else{
			j.setMsg("请登录后再操作");
		}
		return j;
	}

	/**
	 * 导入功能跳转
	 *
	 * @return
	 */
	@RequestMapping(params = "upload")
	public ModelAndView upload(HttpServletRequest req) {
		req.setAttribute("controller_name","userController");
		return new ModelAndView("common/upload/pub_excel_upload");
	}

	/**
	 * 导出excel
	 *
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXls")
	public String exportXls(UserEntity tsUser,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		CriteriaQuery cq = new CriteriaQuery(UserEntity.class, dataGrid);
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tsUser, request.getParameterMap());
		List<UserEntity> tsUsers = this.userService.getListByCriteriaQuery(cq,false);
		//导出的时候处理一下组织机构编码和角色编码
		for(int i=0;i<tsUsers.size();i++){
			UserEntity user = tsUsers.get(i);
			//托管
			systemService.getSession().evict(user);
			String id = user.getId();

			String queryRole = "select * from t_s_role where id in (select roleid from t_s_role_user where userid=:userid)";
			List<RoleEntity> roles = systemService.getSession().createSQLQuery(queryRole).addEntity(RoleEntity.class).setString("userid",id).list();
			String roleCodes = "";
			for(RoleEntity role:roles){
				roleCodes += ","+role.getRoleCode();
			}
			user.setUserKey(roleCodes.replaceFirst(",", ""));
			String queryDept = "select * from t_s_depart where id in (select org_id from t_s_user_org where user_id=:userid)";
			List<DepartEntity> departs = systemService.getSession().createSQLQuery(queryDept).addEntity(DepartEntity.class).setString("userid",id).list();
			String departCodes = "";
			for(DepartEntity depart:departs){
				departCodes += ","+depart.getOrgCode();
			}
			user.setDepartid(departCodes.replaceFirst(",", ""));

		}
		modelMap.put(NormalExcelConstants.FILE_NAME,"用户表");
		modelMap.put(NormalExcelConstants.CLASS,UserEntity.class);
		modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("用户表列表", "导出人:"+ResourceUtil.getSessionUser().getRealName(),
				"导出信息"));
		modelMap.put(NormalExcelConstants.DATA_LIST,tsUsers);
		return NormalExcelConstants.JEECG_EXCEL_VIEW;
	}

	/**
	 * 导出excel 使模板
	 *
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXlsByT")
	public String exportXlsByT(UserEntity tsUser,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		modelMap.put(NormalExcelConstants.FILE_NAME,"用户表");
		modelMap.put(NormalExcelConstants.CLASS,UserEntity.class);
		modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("用户表列表", "导出人:"+ResourceUtil.getSessionUser().getRealName(),
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
				List<UserEntity> tsUsers = ExcelImportUtil.importExcel(file.getInputStream(),UserEntity.class,params);
				for (UserEntity tsUser : tsUsers) {
					String username = tsUser.getUserName();
					if(username==null||username.equals("")){
						j.setMsg("用户名为必填字段，导入失败");
						return j;
					}

					tsUser.setStatus(new Short("1"));
					tsUser.setDevFlag("0");
					tsUser.setDeleteFlag(new Short("0"));
					String roleCodes = tsUser.getUserKey();
					String deptCodes = tsUser.getDepartid();

					tsUser.setPassword(PasswordUtil.encrypt(username, "123456", PasswordUtil.getStaticSalt()));
					tsUser.setUserType(GlobalConstants.USER_TYPE_SYSTEM);//导入用户 在用户管理列表不显示

					if((roleCodes==null||roleCodes.equals(""))||(deptCodes==null||deptCodes.equals(""))){
						List<UserEntity> users = systemService.findListByProperty(UserEntity.class,"userName",username);
						if(users.size()!=0){
							//用户存在更新
							UserEntity user = users.get(0);
							MyBeanUtils.copyBeanNotNull2Bean(tsUser,user);
							user.setDepartid(null);
							systemService.saveOrUpdate(user);
						}else{
							tsUser.setDepartid(null);
							systemService.add(tsUser);
						}
					}else{
						String[] roles = roleCodes.split(",");
						String[] depts = deptCodes.split(",");
						boolean flag = true;
						//判断组织机构编码和角色编码是否存在，如果不存在，也不能导入
						for(String roleCode:roles){
							List<RoleEntity> roleList = systemService.findListByProperty(RoleEntity.class,"roleCode",roleCode);
							if(roleList.size()==0){
								flag = false;
							}
						}

						for(String deptCode:depts){
							List<DepartEntity> departList = systemService.findListByProperty(DepartEntity.class,"orgCode",deptCode);
							if(departList.size()==0){
								flag = false;
							}
						}

						if(flag){
							//判断用户是否存在
							List<UserEntity> users = systemService.findListByProperty(UserEntity.class,"userName",username);
							if(users.size()!=0){
								//用户存在更新
								UserEntity user = users.get(0);
								MyBeanUtils.copyBeanNotNull2Bean(tsUser,user);
								user.setDepartid(null);
								systemService.saveOrUpdate(user);

								String id = user.getId();
								systemService.executeSql("delete from t_s_role_user where userid = ?",id);
								for(String roleCode:roles){
									//根据角色编码得到roleid
									List<RoleEntity> roleList = systemService.findListByProperty(RoleEntity.class,"roleCode",roleCode);
									RoleUserEntity tsRoleUser = new RoleUserEntity();
									tsRoleUser.setTSUser(user);
									tsRoleUser.setTSRole(roleList.get(0));
									systemService.add(tsRoleUser);
								}

								systemService.executeSql("delete from t_s_user_org where user_id = ?",id);
								for(String orgCode:depts){
									//根据角色编码得到roleid
									List<DepartEntity> departList = systemService.findListByProperty(DepartEntity.class,"orgCode",orgCode);
									UserOrgEntity tsUserOrg = new UserOrgEntity();
									tsUserOrg.setTsDepart(departList.get(0));
									tsUserOrg.setTsUser(user);
									systemService.add(tsUserOrg);
								}
							}else{
								//不存在则保存
								//TSUser user = users.get(0);
								tsUser.setDepartid(null);
								systemService.add(tsUser);
								for(String roleCode:roles){
									//根据角色编码得到roleid
									List<RoleEntity> roleList = systemService.findListByProperty(RoleEntity.class,"roleCode",roleCode);
									RoleUserEntity tsRoleUser = new RoleUserEntity();
									tsRoleUser.setTSUser(tsUser);
									tsRoleUser.setTSRole(roleList.get(0));
									systemService.add(tsRoleUser);
								}

								for(String orgCode:depts){
									//根据角色编码得到roleid
									List<DepartEntity> departList = systemService.findListByProperty(DepartEntity.class,"orgCode",orgCode);
									UserOrgEntity tsUserOrg = new UserOrgEntity();
									tsUserOrg.setTsDepart(departList.get(0));
									tsUserOrg.setTsUser(tsUser);
									systemService.add(tsUserOrg);
								}
							}
							j.setMsg("文件导入成功！");
						}else {
							j.setMsg("组织机构编码和角色编码不能匹配");
						}
					}
				}
			} catch (Exception e) {
				j.setMsg("文件导入失败！");
				logger.error(ExceptionUtil.getExceptionMessage(e));
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


	/**
	 * 选择用户跳转页面
	 *
	 * @return
	 */
	@RequestMapping(params = "userSelect")
	public String userSelect() {
		return "system/user/userSelect";
	}
	
	/**
	 * 添加、编辑我的机构用户
	 * 
	 * @param request
	 * @param response
	 * @param dataGrid
	 * @param user
	 */
	@RequestMapping(params = "addorupdateMyOrgUser")
	public ModelAndView addorupdateMyOrgUser(UserEntity user, HttpServletRequest req) {
        List<String> orgIdList = new ArrayList<String>();
		DepartEntity tsDepart = new DepartEntity();
		if (StringUtil.isNotEmpty(user.getId())) {
			user = systemService.getById(UserEntity.class, user.getId());
			
			req.setAttribute("user", user);
			idandname(req, user);
			getOrgInfos(req, user);
		}else{
			String departid = oConvertUtils.getString(req.getParameter("departid"));
			DepartEntity org = systemService.getById(DepartEntity.class,departid);
			req.setAttribute("orgIds", departid);
			req.setAttribute("departname", org.getDepartname());
		}
		req.setAttribute("tsDepart", tsDepart);
        return new ModelAndView("system/user/myOrgUser");
	}
}