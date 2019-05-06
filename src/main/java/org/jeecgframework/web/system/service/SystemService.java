package org.jeecgframework.web.system.service;

import java.util.List;
import java.util.Set;

import org.jeecgframework.web.system.core.OperationEntity;
import org.jeecgframework.web.system.function.entity.FunctionEntity;
import org.jeecgframework.web.system.icon.entity.IconEntity;
import org.jeecgframework.core.common.service.CommonService;
import org.jeecgframework.web.system.user.entity.UserEntity;

/**
 * 系统Service
 * @author DELL
 * @date 2019-05-06
 * @version V1.0
 */
public interface SystemService extends CommonService{

	/**
	 * 登陆用户检查
	 * @param user
	 * @return
	 * @throws Exception
	 */
	UserEntity checkUserExits(UserEntity user) throws Exception;
	/**
	 * 日志添加
	 * @param LogContent 内容
	 * @param loglevel 级别
	 * @param operatetype 类型
	 * @param TUser 操作人
	 */
	void addLog(String LogContent, Short operatetype, Short loglevel);


	
	/**
	 * 获取页面控件权限控制的
	 * JS片段
	 * @param out
	 */
	String getAuthFilterJS();
	

	/**
	 * 刷新菜单
	 *
	 * @param id
	 */
	void flushRoleFunciton(String id, FunctionEntity newFunciton);

    /**
     * 生成组织机构编码
     * @param id 组织机构主键
     * @param pid 组织机构的父级主键
     * @return 组织机构编码
     */
	String generateOrgCode(String id, String pid);

	/**
	  * 根据角色id 和 菜单Id 获取 具有操作权限的数据规则
	  * @Description: TODO
	  * @param @param roleId
	  * @param @param functionId
	  * @param @return    设定文件
	  * @return Set<String>    返回类型
	  * @throws
	 */

	Set<String> getDataRuleIdsByRoleIdAndFunctionId(String roleId, String functionId);
		
	/**
	 * 根据角色ID 和 菜单Id 获取 具有操作权限的按钮Codes
	 * @param roleId
	 * @param functionId
	 * @return
	 */
	Set<String> getOperationCodesByRoleIdAndFunctionId(String roleId, String functionId);

	
	/**
	 * 加载所有图标
	 * @return
	 */
	void initAllTSIcons();

	/**
	 * 更新图标
	 * @param icon
	 */
	void upTSIcons(IconEntity icon);
	/**
	 * 删除图标
	 * @param icon
	 */
	void delTSIcons(IconEntity icon);

	/**
	 * 添加数据日志
	 * @param tableName		操作表名
	 * @param dataId		数据ID
	 * @param dataContent	内容(JSON格式)
	 */

	void addDataLog(String tableName, String dataId, String dataContent);

	/***
	 * 获取二级管理员页面控件权限授权配置【二级管理员后台权限配置功能】
	 * @param groupId 部门角色组ID
	 * @param functionId 选中菜单ID
	 * @Param type 0:部门管理员组/1:部门角色
	 */
	Set<String> getDepartAuthGroupOperationSet(String groupId, String functionId, String type);
	
	/***
	 * 获取二级管理员数据权限授权配置【二级管理员后台权限配置功能】
	 * @param groupId 部门角色组ID
	 * @param functionId 选中菜单ID
	 * @Param type  0:部门管理员组/1:部门角色
	 */
	Set<String> getDepartAuthGroupDataRuleSet(String groupId, String functionId, String type);

	
	/**
	 * 【AuthInterceptor】获取登录用户的数据权限Ids
	 * @param userId
	 * @param functionId
	 * @return
	 */

	Set<String> getLoginDataRuleIdsByUserId(String userId, String functionId, String orgId);

	
	/***
	 * 【AuthInterceptor】获取登录用户的页面控件权限
	 */

	List<OperationEntity> getLoginOperationsByUserId(String userId, String functionId, String orgId);

	
	/**
	 * 【AuthInterceptor】判断是否有菜单访问权限
	 */
	boolean loginUserIsHasMenuAuth(String requestPath, String clickFunctionId, String userid, String orgId);
	
	/**
	 * 【AuthInterceptor】通过请求地址，获取数据库对应的菜单ID
	 * @param requestPath
	 * @return
	 */
	String getFunctionIdByUrl(String requestPath, String menuPath);
	
}
