package org.jeecgframework.web.system.service;

import java.util.List;
import java.util.Map;

import org.jeecgframework.core.common.service.CommonService;
import org.jeecgframework.minidao.pojo.MiniDaoPage;

/**
 * 二级管理员设置
 * @author ShaoQing
 *
 */
public interface DepartAuthGroupService extends CommonService {
	
	MiniDaoPage<Map<String,Object>> getDepartAuthGroupList(int page, int rows);
	
	List<Map<String,Object>> chkDepartAuthGroupList(String userId);
	
	MiniDaoPage<Map<String,Object>> getDepartAuthGroupByUserId(int page, int rows, String userId);
	
	MiniDaoPage<Map<String,Object>> getDepartAuthRole(int page, int rows, String userId);
	
	List<Map<String,Object>> chkDepartAuthRole();
}
