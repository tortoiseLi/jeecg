package org.jeecgframework.core.common.dao;

import org.jeecgframework.core.common.model.common.UploadFile;
import org.jeecgframework.core.common.model.json.ComboTree;
import org.jeecgframework.core.common.model.json.ImportFile;
import org.jeecgframework.core.common.model.json.TreeGrid;
import org.jeecgframework.core.extend.template.Template;
import org.jeecgframework.tag.vo.easyui.ComboTreeModel;
import org.jeecgframework.tag.vo.easyui.TreeGridModel;
import org.jeecgframework.web.system.pojo.base.TSDepart;
import org.jeecgframework.web.system.pojo.base.TSUser;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * BaseDao扩展
 * @author DELL
 * @date 2019-05-07
 * @version V1.0
 */
public interface CommonDao extends BaseDao{

	/**
	 * admin账户密码初始化
	 * @param user
	 * @param newPwd
	 */
	void pwdInit(TSUser user, String newPwd);

	/**
	 * 检查用户是否存在
	 * @param user
	 * @return
	 */
	TSUser getUserByUserIdAndUserNameExits(TSUser user);

	/**
	 * 根据账号/密码查询获取用户
	 * @param username
	 * @param password
	 * @return
	 */
	TSUser findUserByAccountAndPassword(String username, String password);

	/**
	 * 获取用户角色
	 * @param user
	 * @return
	 */
	String getUserRole(TSUser user);

	/**
	 * 文件上传
	 * @param uploadFile
	 * @param <T>
	 * @return
	 */
	<T> T uploadFile(UploadFile uploadFile);

	/**
	 * 文件上传或预览
	 * @param uploadFile
	 * @return
	 */
	HttpServletResponse viewOrDownloadFile(UploadFile uploadFile);

	/**
	 * 获取DataSource
	 * @param template
	 * @return
	 */
	Map<Object,Object> getDataSourceMap(Template template);

	/**
	 * 生成XML文件
	 * @param importFile
	 * @return
	 */
	HttpServletResponse createXml(ImportFile importFile);

	/**
	 * 解析XML文件
	 * @param fileName
	 */
	void parserXml(String fileName);

	/**
	 * 部门生成ComboTree
	 * @param all
	 * @param comboTree
	 * @return
	 */
	List<ComboTree> comTree(List<TSDepart> all, ComboTree comboTree);

	/**
	 * 根据模型生成ComboTree
	 * @param all
	 * @param comboTreeModel
	 * @param in
	 * @param recursive
	 * @return
	 */
	List<ComboTree> comboTree(List all, ComboTreeModel comboTreeModel, List in, boolean recursive);

	/**
	 * 生成TreeGrid
	 * @param all
	 * @param treeGridModel
	 * @return
	 */
	List<TreeGrid> treeGrid(List<?> all, TreeGridModel treeGridModel);
}

