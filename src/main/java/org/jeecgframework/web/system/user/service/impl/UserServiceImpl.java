package org.jeecgframework.web.system.user.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.jeecgframework.core.constant.GlobalConstants;
import org.jeecgframework.core.util.BrowserUtils;
import org.jeecgframework.core.util.ContextHolderUtils;
import org.jeecgframework.core.util.DateUtils;
import org.jeecgframework.core.util.IpUtil;
import org.jeecgframework.core.util.NumberComparator;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.core.util.oConvertUtils;
import org.jeecgframework.web.system.depart.entity.DepartEntity;
import org.jeecgframework.web.system.log.operate.entity.LogEntity;
import org.jeecgframework.web.system.core.manager.ClientManager;
import org.jeecgframework.web.system.core.common.entity.Client;
import org.jeecgframework.web.system.function.entity.FunctionEntity;
import org.jeecgframework.web.system.role.entity.RoleEntity;
import org.jeecgframework.web.system.core.RoleUserEntity;
import org.jeecgframework.web.system.user.entity.UserEntity;
import org.jeecgframework.web.system.core.UserOrgEntity;
import org.jeecgframework.web.system.service.MutiLangService;
import org.jeecgframework.web.system.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 
 * @author  张代浩
 *
 */
@Service("userService")
public class UserServiceImpl extends CommonServiceImpl implements UserService {
	private Logger log = Logger.getLogger(UserServiceImpl.class);
	@Autowired
	private MutiLangService mutiLangService;
	@Resource
	private ClientManager clientManager;
	
	@Override
	@Transactional(readOnly = true)
	public UserEntity checkUserExits(UserEntity user){
		return this.commonDao.getUserByUserIdAndUserNameExits(user);
	}
	
	@Override
	@Transactional(readOnly = true)
	public UserEntity checkUserExits(String username,String password){
		return this.commonDao.findUserByAccountAndPassword(username,password);
	}
	
	@Override
	@Transactional(readOnly = true)
	public String getUserRole(UserEntity user){
		return this.commonDao.getUserRole(user);
	}
	
	@Override
	public void pwdInit(UserEntity user, String newPwd) {
			this.commonDao.pwdInit(user,newPwd);
	}
	
	@Override
	@Transactional(readOnly = true)
	public int getUsersOfThisRole(String id) {
		Criteria criteria = getSession().createCriteria(RoleUserEntity.class);
		criteria.add(Restrictions.eq("TSRole.id", id));
		int allCounts = ((Long) criteria.setProjection(
				Projections.rowCount()).uniqueResult()).intValue();
		return allCounts;
	}
	
	@Override
	public String trueDel(UserEntity user) {
		String message = "";
		List<RoleUserEntity> roleUser = this.commonDao.findListByProperty(RoleUserEntity.class, "TSUser.id", user.getId());
		if (!user.getStatus().equals(GlobalConstants.USER_ADMIN)) {
			if (roleUser.size()>0) {
				// 删除用户时先删除用户和角色关系表
				delRoleUser(user);
				this.commonDao.executeSql("delete from t_s_user_org where user_id=?", user.getId()); // 删除 用户-机构 数据
                this.commonDao.delete(user);
				message = "用户：" + user.getUserName() + "删除成功";
				this.addLog(message, GlobalConstants.LOG_TYPE_DELETE, GlobalConstants.LOG_LEVEL_INFO);
			} else {
				this.commonDao.delete(user);
				message = "用户：" + user.getUserName() + "删除成功";
			}
		} else {
			message = "超级管理员不可删除";
		}
		return message;
	}
	
	private void delRoleUser(UserEntity user) {
		// 同步删除用户角色关联表
		List<RoleUserEntity> roleUserList = this.commonDao.findListByProperty(RoleUserEntity.class, "TSUser.id", user.getId());
		if (roleUserList.size() >= 1) {
			for (RoleUserEntity tRoleUser : roleUserList) {
				this.commonDao.delete(tRoleUser);
			}
		}
	}
	
	/**
	 * 添加日志
	 */
	private void addLog(String logcontent, Short loglevel, Short operatetype) {
		HttpServletRequest request = ContextHolderUtils.getRequest();
		String broswer = BrowserUtils.checkBrowse(request);
		LogEntity log = new LogEntity();
		log.setLogcontent(logcontent);
		log.setLoglevel(loglevel);
		log.setOperatetype(operatetype);
		log.setNote(oConvertUtils.getIp());
		log.setBroswer(broswer);
		log.setOperatetime(DateUtils.gettimestamp());
//		log.setTSUser(ResourceUtil.getSessionUser());
		/*start chenqian 201708031TASK #2317 【改造】系统日志表，增加两个字段，避免关联查询 [操作人账号] [操作人名字]*/
		UserEntity u = ResourceUtil.getSessionUser();
		log.setUserid(u.getId());
		log.setUsername(u.getUserName());
		log.setRealname(u.getRealName());
		/*update-end--Author chenqian 201708031TASK #2317 【改造】系统日志表，增加两个字段，避免关联查询 [操作人账号] [操作人名字]*/
		commonDao.insert(log);
	}

	@Override
	public void saveOrUpdate(UserEntity user, String[] orgIds, String[] roleIds) {
		if(StringUtil.isNotEmpty(user.getId())){
			commonDao.executeSql("delete from t_s_user_org where user_id=?", user.getId());
			this.commonDao.update(user);
			List<RoleUserEntity> ru = commonDao.findListByProperty(RoleUserEntity.class, "TSUser.id", user.getId());
			commonDao.deleteByCollection(ru);
		}else{
			this.commonDao.insert(user);
		}
		saveUserOrgList(user,orgIds);
		saveRoleUser(user,roleIds);
	}
	
	/**
     * 保存 用户-组织机构 关系信息
     * @param user user
     * @param orgIds 组织机构id数组
     */
	private void saveUserOrgList(UserEntity user,String[] orgIds) {
        if(orgIds!=null && orgIds.length>0){
        	List<UserOrgEntity> userOrgList = new ArrayList<UserOrgEntity>();
        	for (String orgId : orgIds) {
        		if(StringUtils.isBlank(orgId))continue;
				DepartEntity depart = new DepartEntity();
        		depart.setId(orgId);

				UserOrgEntity userOrg = new UserOrgEntity();
        		userOrg.setTsUser(user);
        		userOrg.setTsDepart(depart);
        		
        		userOrgList.add(userOrg);
        	}
        	if (!userOrgList.isEmpty()) {
        		commonDao.batchInsert(userOrgList);
        	}
        }
    }

	/**
	 * 保存 用户角色关联表信息
	 * @param user
	 * @param roleIds
	 */
	private void saveRoleUser(UserEntity user, String[] roleIds) {
		if(roleIds!=null && roleIds.length>0){
			for (int i = 0; i < roleIds.length; i++) {
				if(StringUtils.isBlank(roleIds[i]))continue;
				RoleUserEntity rUser = new RoleUserEntity();
				RoleEntity role = commonDao.getById(RoleEntity.class, roleIds[i]);
				rUser.setTSRole(role);
				rUser.setTSUser(user);
				commonDao.insert(rUser);
			}
		}
	}


	/**
	 * 获取用户菜单列表
	 * 
	 * @param userId 用户ID
	 * @return
	 */
	@Override
	@Transactional(readOnly = true)
	public Map<String, FunctionEntity> getLoginUserFunction(String userId) {
		Map<String, FunctionEntity> loginActionlist = new HashMap<String, FunctionEntity>();
		//查询用户角色对应的授权菜单
		StringBuilder hqlsb1 = new StringBuilder("select distinct f from FunctionEntity f,RoleFunctionEntity rf,RoleUserEntity ru  ").append("where ru.TSRole.id=rf.TSRole.id and rf.TSFunction.id=f.id and ru.TSUser.id=? ");
		//查询用户组织机构授权的菜单
		StringBuilder hqlsb2 = new StringBuilder("select distinct c from FunctionEntity c,RoleFunctionEntity rf,RoleOrgEntity b,UserOrgEntity a ").append("where a.tsDepart.id=b.tsDepart.id and b.tsRole.id=rf.TSRole.id and rf.TSFunction.id=c.id and a.tsUser.id=?");
		List<FunctionEntity> list1 = this.findHql(hqlsb1.toString(), userId);
		List<FunctionEntity> list2 = this.findHql(hqlsb2.toString(), userId);
		for (FunctionEntity function : list1) {
			loginActionlist.put(function.getId(), function);
		}
		for (FunctionEntity function : list2) {
			loginActionlist.put(function.getId(), function);
		}
		
		list1.clear();
		list2.clear();
		list1 = null;
		list2 = null;
		return loginActionlist;
	}
	
	/**
	 * 根据用户ID，获取登录用户的权限Map
	 * 
	 * @param userid 	用户ID
	 * @return
	 */
	@Override
	@Transactional(readOnly = true)
	public Map<Integer, List<FunctionEntity>> getFunctionMap(String userid) {
		HttpSession session = ContextHolderUtils.getSession();
		Client client = clientManager.getClient(session.getId());
		if (client.getFunctionMap() == null || client.getFunctionMap().size() == 0) {
			log.debug("---------【从数据库中】---获取登录用户菜单--------------userid: "+ userid);
			Map<Integer, List<FunctionEntity>> functionMap = new HashMap<Integer, List<FunctionEntity>>();
			//获取用户授权菜单
			Map<String, FunctionEntity> loginFunctionslist = this.getLoginUserFunction(userid);
			if (loginFunctionslist.size() > 0) {
				Collection<FunctionEntity> allFunctions = loginFunctionslist.values();
				for (FunctionEntity function : allFunctions) {
				    //权限类型菜单不在首页加载
		            if(GlobalConstants.FUNCTION_TYPE_FROM.intValue() == function.getFunctionType().intValue()){
						continue;
					}
		            //菜单层级
		            int level = function.getFunctionLevel();
					if (!functionMap.containsKey(level)) {
						functionMap.put(level,new ArrayList<FunctionEntity>());
					}
					functionMap.get(level).add(function);
				}
				// 菜单栏排序
				Collection<List<FunctionEntity>> c = functionMap.values();
				for (List<FunctionEntity> list : c) {
					for (FunctionEntity function : list) {
						//如果有子级菜单 则地址设为空
						if(function.hasSubFunction(functionMap))function.setFunctionUrl("");
					}
					Collections.sort(list, new NumberComparator());
				}
			}
			loginFunctionslist.clear();
			loginFunctionslist = null;
			client.setFunctionMap(functionMap);
			return functionMap;
		}else{
			log.debug("------------【从Session 缓存中】获取登录用户菜单--------------userid: "+ userid);
			return client.getFunctionMap();
		}
	}
	
	
	/**
     * 保存登录用户信息，并将当前登录用户的组织机构赋值到用户实体中；
     * @param req 		request
     * @param user 		当前登录用户
     * @param orgId 	机构ID
     */
    @Override
	public void saveLoginUserInfo(HttpServletRequest req, UserEntity user, String orgId) {
    	String message = null;
		DepartEntity currentDepart = this.getById(DepartEntity.class, orgId);
        user.setCurrentDepart(currentDepart);

        HttpSession session = ContextHolderUtils.getSession();
		user.setDepartid(orgId);
		session.setAttribute(ResourceUtil.LOCAL_CLINET_USER, user);
        message = mutiLangService.getLang("common.user") + ": " + user.getUserName() + "["+ currentDepart.getDepartname() + "]" + mutiLangService.getLang("common.login.success");
        
        //IE列表操作按钮的样式
        String browserType = "";
        Cookie[] cookies = req.getCookies();
        for (int i = 0; i < cookies.length; i++) {
			Cookie cookie = cookies[i];
			if("BROWSER_TYPE".equals(cookie.getName())){
				browserType = cookie.getValue();
			}
		}
        session.setAttribute("brower_type", browserType);
        
        //【基础权限】切换用户，用户分拥有不同的权限，切换用户权限错误问题
        //当前session为空 或者 当前session的用户信息与刚输入的用户信息一致时，则更新Client信息
        Client clientOld = clientManager.getClient(session.getId());
		if(clientOld == null || clientOld.getUser() ==null ||user.getUserName().equals(clientOld.getUser().getUserName())){
			Client client = new Client();
	        client.setIp(IpUtil.getIpAddr(req));
	        client.setLogindatetime(new Date());
	        client.setUser(user);
	        clientManager.addClinet(session.getId(), client);
		} else {//如果不一致，则注销session并通过session=req.getSession(true)初始化session
			Client client = new Client();
			clientManager.removeClinet(session.getId());
			session.invalidate();
			session = req.getSession(true);//session初始化
			session.setAttribute(ResourceUtil.LOCAL_CLINET_USER, user);
			session.setAttribute("randCode",req.getParameter("randCode"));//保存验证码
			clientManager.addClinet(session.getId(), client);
		}
        
        // 添加登陆日志
        this.addLog(message, GlobalConstants.LOG_TYPE_LOGIN, GlobalConstants.LOG_LEVEL_INFO);
    }
    
	/**
	 * 判断访问IP是否在黑名单
	 */
	@Override
	public boolean isInBlackList(String ip){
		Long orgNum = this.getCountForJdbcParam("select count(*) from t_s_black_list where ip = ?",ip);
		return orgNum!=0?true:false;
	}

	/**
	 * shortcut风格菜单图标个性化设置（一级菜单）
	 * @return
	 */
	@Override
	@Transactional(readOnly = true)
	public String getShortcutPrimaryMenu(List<FunctionEntity> primaryMenu) {
		String floor = "";
		for (FunctionEntity function : primaryMenu) {
			if (function.getFunctionLevel() == 0) {
				String lang_key = function.getFunctionName();
				String lang_context = mutiLangService.getLang(lang_key);
				lang_context = lang_context.trim();
				
				if ("业务申请".equals(lang_context)) {
					String ss = "<div style='width:67px;position: absolute;top:39px;text-align:center;color:#909090;font-size:13px;'><span style='letter-spacing:-1px;'>" + lang_context + "</span></div>";
					floor += " <li style='position: relative;'>" + ss + "<img class='imag1' src='plug-in/login/images/ywsq.png' /> " + " <img class='imag2' src='plug-in/login/images/ywsq-up.png' style='display: none;' /></li> ";
				} else if ("个人办公".equals(lang_context)) {

					String ss = "<div style='width:67px;position: absolute;top:39px;text-align:center;color:#909090;font-size:13px;'><span style='letter-spacing:-1px;'>" + lang_context + "</span></div>";
					floor += " <li style='position: relative;'>" + ss + "<img class='imag1' src='plug-in/login/images/grbg.png' /> " + " <img class='imag2' src='plug-in/login/images/grbg-up.png' style='display: none;' /></li> ";
				} else if ("流程管理".equals(lang_context)) {

					String ss = "<div style='width:67px;position: absolute;top:39px;text-align:center;color:#909090;font-size:13px;'><span style='letter-spacing:-1px;'>" + lang_context + "</span></div>";
					floor += " <li style='position: relative;'>" + ss + "<img class='imag1' src='plug-in/login/images/lcsj.png' /> " + " <img class='imag2' src='plug-in/login/images/lcsj-up.png' style='display: none;' /></li> ";
				} else if ("Online 开发".equals(lang_context)) {

					floor += " <li><img class='imag1' src='plug-in/login/images/online.png' /> " + " <img class='imag2' src='plug-in/login/images/online_up.png' style='display: none;' />" + " </li> ";
				} else if ("自定义表单".equals(lang_context)) {

					String ss = "<div style='width:67px;position: absolute;top:39px;text-align:center;color:#909090;font-size:13px;'><span style='letter-spacing:-1px;'>" + lang_context + "</span></div>";
					floor += " <li style='position: relative;'>" + ss + "<img class='imag1' src='plug-in/login/images/zdybd.png' /> " + " <img class='imag2' src='plug-in/login/images/zdybd-up.png' style='display: none;' /></li> ";
				} else if ("系统监控".equals(lang_context)) {

					floor += " <li><img class='imag1' src='plug-in/login/images/xtjk.png' /> " + " <img class='imag2' src='plug-in/login/images/xtjk_up.png' style='display: none;' />" + " </li> ";
				} else if ("统计报表".equals(lang_context)) {

					floor += " <li><img class='imag1' src='plug-in/login/images/tjbb.png' /> " + " <img class='imag2' src='plug-in/login/images/tjbb_up.png' style='display: none;' />" + " </li> ";
				} else if ("消息中间件".equals(lang_context)) {
					String ss = "<div style='width:67px;position: absolute;top:39px;text-align:center;color:#909090;font-size:13px;'><span style='letter-spacing:-1px;'>" + lang_context + "</span></div>";
					floor += " <li style='position: relative;'>" + ss + "<img class='imag1' src='plug-in/login/images/msg.png' /> " + " <img class='imag2' src='plug-in/login/images/msg_up.png' style='display: none;' /></li> ";
				} else if ("系统管理".equals(lang_context)) {

					floor += " <li><img class='imag1' src='plug-in/login/images/xtgl.png' /> " + " <img class='imag2' src='plug-in/login/images/xtgl_up.png' style='display: none;' />" + " </li> ";
				} else if ("常用示例".equals(lang_context)) {

					floor += " <li><img class='imag1' src='plug-in/login/images/cysl.png' /> " + " <img class='imag2' src='plug-in/login/images/cysl_up.png' style='display: none;' />" + " </li> ";
				} else if (lang_context.contains("消息推送")) {

					String s = "<div style='width:67px;position: absolute;top:39px;text-align:center;color:#909090;font-size:13px;'>消息推送</div>";
					floor += " <li style='position: relative;'>" + s + "<img class='imag1' src='plug-in/login/images/msg.png' /> " + " <img class='imag2' src='plug-in/login/images/msg_up.png' style='display: none;' /></li> ";
				} else {
					// 其他的为默认通用的图片模式
					String s = "";
					if (lang_context.length() >= 5 && lang_context.length() < 7) {
						s = "<div style='width:67px;position: absolute;top:39px;text-align:center;color:#909090;font-size:13px;'><span style='letter-spacing:-1px;'>" + lang_context + "</span></div>";
					} else if (lang_context.length() < 5) {
						s = "<div style='width:67px;position: absolute;top:39px;text-align:center;color:#909090;font-size:13px;'>" + lang_context + "</div>";
					} else if (lang_context.length() >= 7) {
						s = "<div style='width:67px;position: absolute;top:39px;text-align:center;color:#909090;font-size:13px;'><span style='letter-spacing:-1px;'>" + lang_context.substring(0, 6) + "</span></div>";
					}
					floor += " <li style='position: relative;'>" + s + "<img class='imag1' src='plug-in/login/images/default.png' /> " + " <img class='imag2' src='plug-in/login/images/default_up.png' style='display: none;' />" + "</li> ";
				}
			}
		}
		return floor;
	}

	
	/**
	 * shortcut风格菜单图标个性化设置（二级菜单）
	 * @return
	 */
	@Override
	@Transactional(readOnly = true)
	public String getShortcutPrimaryMenuDiy(List<FunctionEntity> primaryMenu) {
		String floor = "";
		if (primaryMenu == null) {
			return floor;
		}
		String menuString = "user.manage role.manage department.manage menu.manage";
		for (FunctionEntity function : primaryMenu) {
			if (menuString.contains(function.getFunctionName())) {
				if (function.getFunctionLevel() == 1) {

					String lang_key = function.getFunctionName();
					String lang_context = mutiLangService.getLang(lang_key);
					if ("申请".equals(lang_key)) {
						lang_context = "申请";
						String s = "";
						s = "<div style='width:67px;position: absolute;top:47px;text-align:center;color:#000000;font-size:12px;'>" + lang_context + "</div>";
						floor += " <li><img class='imag1' src='plug-in/login/images/head_icon1.png' /> " + " <img class='imag2' src='plug-in/login/images/head_icon1.png' style='display: none;' />" + s + " </li> ";
					} else if ("Online 开发".equals(lang_context)) {

						floor += " <li><img class='imag1' src='plug-in/login/images/online.png' /> " + " <img class='imag2' src='plug-in/login/images/online_up.png' style='display: none;' />" + " </li> ";
					} else if ("统计查询".equals(lang_context)) {

						floor += " <li><img class='imag1' src='plug-in/login/images/guanli.png' /> " + " <img class='imag2' src='plug-in/login/images/guanli_up.png' style='display: none;' />" + " </li> ";
					} else if ("系统管理".equals(lang_context)) {

						floor += " <li><img class='imag1' src='plug-in/login/images/xtgl.png' /> " + " <img class='imag2' src='plug-in/login/images/xtgl_up.png' style='display: none;' />" + " </li> ";
					} else if ("常用示例".equals(lang_context)) {

						floor += " <li><img class='imag1' src='plug-in/login/images/cysl.png' /> " + " <img class='imag2' src='plug-in/login/images/cysl_up.png' style='display: none;' />" + " </li> ";
					} else if ("系统监控".equals(lang_context)) {

						floor += " <li><img class='imag1' src='plug-in/login/images/xtjk.png' /> " + " <img class='imag2' src='plug-in/login/images/xtjk_up.png' style='display: none;' />" + " </li> ";
					} else if (lang_context.contains("消息推送")) {
						String s = "<div style='width:67px;position: absolute;top:40px;text-align:center;color:#909090;font-size:12px;'>消息推送</div>";
						floor += " <li style='position: relative;'><img class='imag1' src='plug-in/login/images/msg.png' /> " + " <img class='imag2' src='plug-in/login/images/msg_up.png' style='display: none;' />" + s + "</li> ";
					} else {
						// 其他的为默认通用的图片模式
						String s = "";
						if (lang_context.length() >= 5 && lang_context.length() < 7) {
							s = "<div style='width:67px;position: absolute;top:40px;text-align:center;color:#000000;font-size:12px;'><span style='letter-spacing:-1px;'>" + lang_context + "</span></div>";
						} else if (lang_context.length() < 5) {
							s = "<div style='width:67px;position: absolute;top:40px;text-align:center;color:#000000;font-size:12px;'>" + lang_context + "</div>";
						} else if (lang_context.length() >= 7) {
							s = "<div style='width:67px;position: absolute;top:40px;text-align:center;color:#000000;font-size:12px;'><span style='letter-spacing:-1px;'>" + lang_context.substring(0, 6) + "</span></div>";
						}
						floor += " <li style='position: relative;'><img class='imag1' src='plug-in/login/images/head_icon2.png' /> " + " <img class='imag2' src='plug-in/login/images/default_up.png' style='display: none;' />" + s + "</li> ";
					}
				}
			}
		}
		return floor;
	}

	@Override
	public List<FunctionEntity> getSubFunctionList(String userid, String functionId) {
		Map<String, FunctionEntity> loginActionlist = new HashMap<String, FunctionEntity>();
		//查询用户角色对应的授权菜单
		StringBuilder hqlsb1 = new StringBuilder("select distinct f from TSFunction f,RoleFunctionEntity rf,RoleUserEntity ru  ").append("where ru.TSRole.id=rf.TSRole.id and rf.TSFunction.id=f.id and ru.TSUser.id=? and f.TSFunction.id = ?");
		//查询用户组织机构授权的菜单
		StringBuilder hqlsb2 = new StringBuilder("select distinct c from TSFunction c,RoleFunctionEntity rf,RoleOrgEntity b,UserOrgEntity a ").append("where a.tsDepart.id=b.tsDepart.id and b.tsRole.id=rf.TSRole.id and rf.TSFunction.id=c.id and a.tsUser.id=? and c.TSFunction.id = ?");
		List<FunctionEntity> list1 = this.findHql(hqlsb1.toString(), userid,functionId);
		List<FunctionEntity> list2 = this.findHql(hqlsb2.toString(), userid,functionId);
		for (FunctionEntity function : list1) {
			loginActionlist.put(function.getId(), function);
		}
		for (FunctionEntity function : list2) {
			loginActionlist.put(function.getId(), function);
		}
		list1.clear();
		list2.clear();
		list1 = null;
		list2 = null;
		List<FunctionEntity> list = new ArrayList<FunctionEntity>(loginActionlist.values());
		Collections.sort(list, new NumberComparator());
		return list;
	}

}
