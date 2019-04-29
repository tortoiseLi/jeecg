package org.jeecgframework.core.common.dao;


import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.jeecgframework.web.system.depart.entity.DepartEntity;
import org.jeecgframework.web.system.user.entity.UserEntity;

import org.jeecgframework.core.common.model.common.UploadFile;
import org.jeecgframework.core.common.model.json.ComboTree;
import org.jeecgframework.core.common.model.json.ImportFile;
import org.jeecgframework.core.common.model.json.TreeGrid;
import org.jeecgframework.core.extend.template.Template;
import org.jeecgframework.tag.vo.easyui.ComboTreeModel;
import org.jeecgframework.tag.vo.easyui.TreeGridModel;

public interface CommonDao extends BaseDao {
	
	
	/**
	 * admin账户密码初始化
	 * @param user
	 */
	void pwdInit(UserEntity user, String newPwd);
	/**
	 * 检查用户是否存在
	 * */
	UserEntity getUserByUserIdAndUserNameExits(UserEntity user);
	UserEntity findUserByAccountAndPassword(String username, String password);
	String getUserRole(UserEntity user);
	/**
	 * 文件上传
	 */
	<T> T  uploadFile(UploadFile uploadFile);
	/**
	 * 文件上传或预览
	 * @param uploadFile
	 * @return
	 */
	HttpServletResponse viewOrDownloadFile(UploadFile uploadFile);

	Map<Object,Object> getDataSourceMap(Template template);
	/**
	 * 生成XML文件
	 * @param fileName XML全路径
	 */
	HttpServletResponse createXml(ImportFile importFile);
	/**
	 * 解析XML文件
	 * @param fileName XML全路径
	 */
	void parserXml(String fileName);
	List<ComboTree> comTree(List<DepartEntity> all, ComboTree comboTree);

	/**
     * 根据模型生成ComboTree JSON
     *
     * @param all 全部对象
     * @param comboTreeModel 模型
     * @param in 已拥有的对象
     * @param recursive 是否递归加载所有子节点
     * @return List<ComboTree>
     */
	List<ComboTree> ComboTree(List all, ComboTreeModel comboTreeModel, List in, boolean recursive);

	List<TreeGrid> treegrid(List<?> all, TreeGridModel treeGridModel);
}

