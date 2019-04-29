package org.jeecgframework.web.system.user.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.jeecgframework.core.common.service.CommonService;
import org.jeecgframework.web.system.function.entity.FunctionEntity;
import org.jeecgframework.web.system.user.entity.UserEntity;


public interface UserService extends CommonService{

	UserEntity checkUserExits(UserEntity user);

	UserEntity checkUserExits(String username, String password);

	String getUserRole(UserEntity user);

	void pwdInit(UserEntity user, String newPwd);

	/**
	 * 判断这个角色是不是还有用户使用
	 *@Author JueYue
	 *@date   2013-11-12
	 *@param id
	 *@return
	 */
	int getUsersOfThisRole(String id);
	
	/**
	 * 物理删除用户
	 * @param user
	 */
	String trueDel(UserEntity user);

	/**
	 * 添加或者修改用户，添加用户组织机构关联表，用户角色关联表
	 * @param user
	 * @param orgIds
	 * @param roleIds
	 */
	void saveOrUpdate(UserEntity user, String[] orgIds, String[] roleIds);

	
	/**
	 * 获取登录用户权限
	 * @param userId
	 * @return
	 */
	Map<String, FunctionEntity> getLoginUserFunction(String userId);
	
	/**
	 * 获取权限的map
	 * 
	 * @param userid
	 * @return
	 */
	Map<Integer, List<FunctionEntity>> getFunctionMap(String userid);
	
	void saveLoginUserInfo(HttpServletRequest req, UserEntity u, String orgId);
	
	
	boolean isInBlackList(String ip);
	
	/**
	 * shortcut风格菜单图标个性化设置（一级菜单）
	 * @return
	 */
	String getShortcutPrimaryMenu(List<FunctionEntity> primaryMenu);
	
	/**
	 * shortcut风格菜单图标个性化设置（二级菜单）
	 * @return
	 */
	String getShortcutPrimaryMenuDiy(List<FunctionEntity> primaryMenu);
	
	/**
	 * 根据菜单ID找到指定用户拥有的权限的子菜单集合
	 * @author taoYan
	 * @since 2018年10月22日
	 */
	List<FunctionEntity>  getSubFunctionList(String userid, String functionId);

}
