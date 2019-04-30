package org.jeecgframework.web.system.notice.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.exception.BusinessException;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.constant.GlobalConstants;
import org.jeecgframework.core.util.MutiLangUtil;
import org.jeecgframework.core.util.MyBeanUtils;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.tag.core.easyui.TagUtil;
import org.jeecgframework.web.system.notice.entity.NoticeEntity;
import org.jeecgframework.web.system.pojo.base.NoticeAuthorityRoleEntity;
import org.jeecgframework.web.system.pojo.base.NoticeAuthorityUserEntity;
import org.jeecgframework.web.system.pojo.base.NoticeReadUserEntity;
import org.jeecgframework.web.system.role.entity.RoleEntity;
import org.jeecgframework.web.system.user.entity.UserEntity;
import org.jeecgframework.web.system.service.NoticeAuthorityRoleService;
import org.jeecgframework.web.system.service.NoticeAuthorityUserService;
import org.jeecgframework.web.system.notice.service.NoticeService;
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * 通知公告
 * @author alax
 */
@Controller
@RequestMapping("/noticeController")
public class NoticeController extends BaseController {
    private SystemService systemService;

    private ExecutorService executor = Executors.newCachedThreadPool();

    @Autowired
    private NoticeService noticeService;
    @Autowired
    private NoticeAuthorityRoleService noticeAuthorityRoleService;
    @Autowired
    private NoticeAuthorityUserService noticeAuthorityUserService;

    @Autowired
    public void setSystemService(SystemService systemService) {
        this.systemService = systemService;
    }

    /**
     * 取得用户可读通知公告
     * @param user
     * @param req
     * @return
     */
    @RequestMapping(params = "getNoticeList")
    @ResponseBody
    public AjaxJson getNoticeList(Integer isRead, HttpServletRequest req) {
        AjaxJson j = new AjaxJson();
        try {
            UserEntity user = ResourceUtil.getSessionUser();

            String sql = "SELECT notice.*,noticeRead.is_read as is_read FROM t_s_notice notice "
                    + "LEFT JOIN t_s_notice_read_user noticeRead ON  notice.id = noticeRead.notice_id "
                    + "WHERE noticeRead.del_flag = 0 and noticeRead.user_id = ? ";
            sql += " and noticeRead.is_read = ? ";
            sql += " ORDER BY noticeRead.create_time DESC ";
            if (isRead == null || !(isRead == 1 || isRead == 0)) {
                isRead = 0;
            }
            List<Map<String, Object>> noticeList = systemService.findForJdbcParam(sql, 1, 10, user.getId(), isRead.intValue());

            //将List转换成JSON存储
            JSONArray result = new JSONArray();
            if (noticeList != null && noticeList.size() > 0) {
                for (int i = 0; i < noticeList.size(); i++) {
                    JSONObject jsonParts = new JSONObject();
                    jsonParts.put("id", noticeList.get(i).get("id"));
                    jsonParts.put("noticeTitle", noticeList.get(i).get("notice_title"));
                    result.add(jsonParts);
                }
            }

            Map<String, Object> attrs = new HashMap<String, Object>();
            attrs.put("noticeList", result);

            String tip = MutiLangUtil.getLang("notice.tip");
            attrs.put("tip", tip);
            String seeAll = MutiLangUtil.getLang("notice.seeAll");
            attrs.put("seeAll", seeAll);
            j.setAttributes(attrs);

            //获取通知公告总数
            String sql2 = "SELECT count(notice.id) FROM t_s_notice notice "
                    + "LEFT JOIN t_s_notice_read_user noticeRead ON  notice.id = noticeRead.notice_id "
                    + "WHERE noticeRead.del_flag = 0 and noticeRead.user_id = ? "
                    + "and noticeRead.is_read = 0";
            List<Map<String, Object>> resultList2 = systemService.findForJdbc(sql2, user.getId());

            Object count = resultList2.get(0).get("count");
            j.setObj(count);
        } catch (Exception e) {
            j.setSuccess(false);
            e.printStackTrace();
        }
        return j;
    }

    /**
     * 通知公告列表（阅读）
     * @param request
     * @return
     */
    @RequestMapping(params = "noticeList")
    public ModelAndView noticeList(HttpServletRequest request) {
        return new ModelAndView("system/notice/noticeList");
    }

    /**
     * 通知公告详情
     * @param request
     * @return
     */
    @RequestMapping(params = "goNotice")
    public ModelAndView noticeInfo(NoticeEntity notice, HttpServletRequest request) {
        if (StringUtil.isNotEmpty(notice.getId())) {
            notice = this.systemService.getById(NoticeEntity.class, notice.getId());
            request.setAttribute("notice", notice);
            UserEntity user = ResourceUtil.getSessionUser();
            String hql = "from NoticeReadUserEntity where noticeId = ? and userId = ?";
            List<NoticeReadUserEntity> noticeReadList = systemService.findHql(hql, notice.getId(), user.getId());
            if (noticeReadList != null && !noticeReadList.isEmpty()) {
                NoticeReadUserEntity readUser = noticeReadList.get(0);
                if (readUser.getIsRead() == 0) {
                    readUser.setIsRead(1);
                    systemService.saveOrUpdate(readUser);
                }
            }
        }
        return new ModelAndView("system/notice/noticeinfo");
    }

    /**
     * easyui AJAX请求数据
     * 构建列表数据
     * @param request
     * @param response
     * @param dataGrid
     * @param user
     */
    @RequestMapping(params = "datagrid")
    public void datagrid(NoticeEntity notice, HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
//			CriteriaQuery cq = new CriteriaQuery(NoticeEntity.class, dataGrid);
//			//查询条件组装器
//			org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, notice, request.getParameterMap());
//			this.noticeService.getDataGridReturn(cq, true);

        UserEntity user = ResourceUtil.getSessionUser();
        String sql = "SELECT notice.*,noticeRead.is_read as is_read FROM t_s_notice notice "
                + " LEFT JOIN t_s_notice_read_user noticeRead ON  notice.id = noticeRead.notice_id "
                + " WHERE noticeRead.del_flag = 0 and noticeRead.user_id = ? "
                + " ORDER BY noticeRead.is_read asc,noticeRead.create_time DESC ";

        List<Map<String, Object>> resultList = systemService.findForJdbcParam(sql, dataGrid.getPage(), dataGrid.getRows(), user.getId());

        //将List转换成JSON存储
        List<Map<String, Object>> noticeList = new ArrayList<Map<String, Object>>();
        if (resultList != null && resultList.size() > 0) {
            for (int i = 0; i < resultList.size(); i++) {
                Map<String, Object> obj = resultList.get(i);
                Map<String, Object> n = new HashMap<String, Object>();
                n.put("id", String.valueOf(obj.get("id")));
                n.put("noticeTitle", String.valueOf(obj.get("notice_title")));
                n.put("noticeContent", String.valueOf(obj.get("notice_content")));
                n.put("createTime", String.valueOf(obj.get("create_time")));
                n.put("isRead", String.valueOf(obj.get("is_read")));
                noticeList.add(n);
            }
        }

        dataGrid.setResults(noticeList);

        String getCountSql = "SELECT count(notice.id) as count FROM t_s_notice notice LEFT JOIN t_s_notice_read_user noticeRead ON  notice.id = noticeRead.notice_id "
                + "WHERE noticeRead.del_flag = 0 and noticeRead.user_id = ? and noticeRead.is_read = 0";
        List<Map<String, Object>> resultList2 = systemService.findForJdbc(getCountSql, user.getId());

        Object count = resultList2.get(0).get("count");
        dataGrid.setTotal(Integer.valueOf(count.toString()));
        TagUtil.datagrid(response, dataGrid);
    }

    /**
     * 阅读通知公告
     * @param user
     * @param req
     * @return
     */
    @RequestMapping(params = "updateNoticeRead")
    @ResponseBody
    public AjaxJson updateNoticeRead(String noticeId, HttpServletRequest req) {
        AjaxJson j = new AjaxJson();
        try {
//			TSUser user = ResourceUtil.getSessionUser();
//			NoticeReadUserEntity readUser = new NoticeReadUserEntity();
//			readUser.setNoticeId(noticeId);
//			readUser.setUserId(user.getId());
//			readUser.setCreateTime(new Date());
//			systemService.saveOrUpdate(readUser);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return j;
    }

    /**
     * 通知公告列表(管理) 页面跳转
     * @return
     */
    @RequestMapping(params = "tSNotice")
    public ModelAndView tSNotice(HttpServletRequest request) {
        return new ModelAndView("system/notice/tSNoticeList");
    }

    /**
     * easyui AJAX请求数据
     * @param request
     * @param response
     * @param dataGrid
     * @param user
     */

    @RequestMapping(params = "datagrid2")
    public void datagrid2(NoticeEntity tSNotice, HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
        CriteriaQuery cq = new CriteriaQuery(NoticeEntity.class, dataGrid);
        //查询条件组装器
        org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tSNotice, request.getParameterMap());
        try {
            //自定义追加查询条件
        } catch (Exception e) {
            throw new BusinessException(e.getMessage());
        }
        cq.add();
        this.noticeService.getDataGridReturn(cq, true);
        TagUtil.datagrid(response, dataGrid);
    }

    /**
     * 删除通知公告
     * @return
     */
    @RequestMapping(params = "doDel")
    @ResponseBody
    public AjaxJson doDel(NoticeEntity tSNotice, HttpServletRequest request) {
        String message = null;
        AjaxJson j = new AjaxJson();
        tSNotice = systemService.getById(NoticeEntity.class, tSNotice.getId());
        message = "通知公告删除成功";
        try {
            if ("2".equals(tSNotice.getNoticeLevel())) {
                String sql = "delete from t_s_notice_authority_role where notice_id = ?";
                systemService.executeSql(sql, tSNotice.getId());
            } else if ("3".equals(tSNotice.getNoticeLevel())) {
                String sql = "delete from t_s_notice_authority_user where notice_id = ?";
                systemService.executeSql(sql, tSNotice.getId());
            }
            String sql = "delete from t_s_notice_read_user where notice_id = ?";
            systemService.executeSql(sql, tSNotice.getId());
            noticeService.deleteNotice(tSNotice);
            systemService.addLog(message, GlobalConstants.LOG_TYPE_DELETE, GlobalConstants.LOG_LEVEL_INFO);
        } catch (Exception e) {
            e.printStackTrace();
            message = "通知公告删除失败";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }

    /**
     * 批量删除通知公告
     * @return
     */
    @RequestMapping(params = "doBatchDel")
    @ResponseBody
    public AjaxJson doBatchDel(String ids, HttpServletRequest request) {
        String message = null;
        AjaxJson j = new AjaxJson();
        message = "通知公告删除成功";
        try {
            for (String id : ids.split(",")) {
                NoticeEntity tSNotice = systemService.getById(NoticeEntity.class, id);
                noticeService.deleteNotice(tSNotice);
                systemService.addLog(message, GlobalConstants.LOG_TYPE_DELETE, GlobalConstants.LOG_LEVEL_INFO);
            }
        } catch (Exception e) {
            e.printStackTrace();
            message = "通知公告删除失败";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }


    /**
     * 添加通知公告
     * @param ids
     * @return
     */
    @RequestMapping(params = "doAdd")
    @ResponseBody
    public AjaxJson doAdd(NoticeEntity tSNotice, HttpServletRequest request) {
        String message = null;
        AjaxJson j = new AjaxJson();
        message = "通知公告添加成功";
        try {
            Serializable noticeSerializable = noticeService.add(tSNotice);
            if ("1".equals(tSNotice.getNoticeLevel())) {
                //全员进行授权
                final String noticeId = noticeSerializable.toString();
                executor.execute(new Runnable() {
                    @Override
                    public void run() {
                        List<UserEntity> userList = systemService.findHql("from UserEntity");
                        for (UserEntity user : userList) {
                            String hql = "from NoticeReadUserEntity where noticeId = ? and userId = ?";
                            List<NoticeReadUserEntity> noticeReadList = systemService.findHql(hql, noticeId, user.getId());
                            if (noticeReadList == null || noticeReadList.isEmpty()) {
                                NoticeReadUserEntity readUser = new NoticeReadUserEntity();
                                readUser.setCreateTime(new Date());
                                readUser.setNoticeId(noticeId);
                                readUser.setUserId(user.getId());
                                systemService.add(readUser);
                            } else {
                                for (NoticeReadUserEntity readUser : noticeReadList) {
                                    if (readUser.getDelFlag() == 1) {
                                        readUser.setDelFlag(0);
                                        systemService.update(readUser);
                                    }
                                }
                            }
                        }
                        userList.clear();
                    }
                });
            }
            if ("2".equals(tSNotice.getNoticeLevel())) {
                clearRole(tSNotice.getId(), request);
                String roleid[] = request.getParameter("roleid").split(",");
                for (int i = 0; i < roleid.length; i++) {
                    NoticeAuthorityRoleEntity noticeAuthorityRole = new NoticeAuthorityRoleEntity();
                    noticeAuthorityRole.setNoticeId(tSNotice.getId());
                    RoleEntity role = new RoleEntity();
                    role.setId(roleid[i]);
                    noticeAuthorityRole.setRole(role);
                    this.noticeAuthorityRoleService.saveTSNoticeAuthorityRole(noticeAuthorityRole);
                }
            } else if ("3".endsWith(tSNotice.getNoticeLevel())) {
                clearUser(tSNotice.getId(), request);
                String userid[] = request.getParameter("userid").split(",");
                for (int i = 0; i < userid.length; i++) {
                    NoticeAuthorityUserEntity noticeAuthorityUser = new NoticeAuthorityUserEntity();
                    noticeAuthorityUser.setNoticeId(tSNotice.getId());
                    UserEntity tsUser = new UserEntity();
                    tsUser.setId(userid[i]);
                    noticeAuthorityUser.setUser(tsUser);
                    this.noticeAuthorityUserService.saveNoticeAuthorityUser(noticeAuthorityUser);
                }
            }
            systemService.addLog(message, GlobalConstants.LOG_TYPE_INSERT, GlobalConstants.LOG_LEVEL_INFO);
        } catch (Exception e) {
            e.printStackTrace();
            message = "通知公告添加失败";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }

    /**
     * 更新通知公告
     * @param ids
     * @return
     */
    @RequestMapping(params = "doUpdate")
    @ResponseBody
    public AjaxJson doUpdate(NoticeEntity tSNotice, HttpServletRequest request) {
        String message = null;
        AjaxJson j = new AjaxJson();
        message = "通知公告更新成功";
        NoticeEntity t = noticeService.getById(NoticeEntity.class, tSNotice.getId());

        try {
            if ("1".equals(tSNotice.getNoticeLevel()) && !t.getNoticeLevel().equals(tSNotice.getNoticeLevel())) {
                clearRole(tSNotice.getId(), request);
                clearUser(tSNotice.getId(), request);
                //授权级别发生变更，之前为全员授权处理方案，此时应删除之前的数据信息
                final String noticeId = tSNotice.getId();
                executor.execute(new Runnable() {

                    @Override
                    public void run() {
                        List<UserEntity> userList = systemService.findHql("from UserEntity");
                        for (UserEntity user : userList) {
                            String hql = "from NoticeReadUserEntity where noticeId = ? and userId = ?";
                            List<NoticeReadUserEntity> noticeReadList = systemService.findHql(hql, noticeId, user.getId());
                            if (noticeReadList == null || noticeReadList.isEmpty()) {
                                NoticeReadUserEntity readUser = new NoticeReadUserEntity();
                                readUser.setCreateTime(new Date());
                                readUser.setNoticeId(noticeId);
                                readUser.setUserId(user.getId());
                                systemService.add(readUser);
                            } else {
                                for (NoticeReadUserEntity readUser : noticeReadList) {
                                    if (readUser.getDelFlag() == 1) {
                                        readUser.setDelFlag(0);
                                        systemService.update(readUser);
                                    }
                                }
                            }
                        }
                        userList.clear();
                    }
                });
            } else if (!"1".equals(tSNotice.getNoticeLevel()) && "1".equals(t.getNoticeLevel())) {
                String sql = "delete from t_s_notice_read_user where notice_id = ?";
                systemService.executeSql(sql, t.getId());
            }
            MyBeanUtils.copyBeanNotNull2Bean(tSNotice, t);
            noticeService.saveOrUpdate(t);

            if ("2".endsWith(tSNotice.getNoticeLevel())) {
                clearRole(tSNotice.getId(), request);
                clearUser(tSNotice.getId(), request);

                String roleid[] = request.getParameter("roleid").split(",");
                for (int i = 0; i < roleid.length; i++) {
                    NoticeAuthorityRoleEntity noticeAuthorityRole = new NoticeAuthorityRoleEntity();
                    noticeAuthorityRole.setNoticeId(tSNotice.getId());
                    RoleEntity role = new RoleEntity();
                    role.setId(roleid[i]);
                    noticeAuthorityRole.setRole(role);
                    this.noticeAuthorityRoleService.saveTSNoticeAuthorityRole(noticeAuthorityRole);
                }
            } else if ("3".equals(tSNotice.getNoticeLevel())) {
                clearRole(tSNotice.getId(), request);
                clearUser(tSNotice.getId(), request);

                String userid[] = request.getParameter("userid").split(",");
                for (int i = 0; i < userid.length; i++) {
                    NoticeAuthorityUserEntity noticeAuthorityUser = new NoticeAuthorityUserEntity();
                    noticeAuthorityUser.setNoticeId(tSNotice.getId());
                    UserEntity tsUser = new UserEntity();
                    tsUser.setId(userid[i]);
                    noticeAuthorityUser.setUser(tsUser);
                    this.noticeAuthorityUserService.saveNoticeAuthorityUser(noticeAuthorityUser);
                }
            }

            systemService.addLog(message, GlobalConstants.LOG_TYPE_UPDATE, GlobalConstants.LOG_LEVEL_INFO);
        } catch (Exception e) {
            e.printStackTrace();
            message = "通知公告更新失败";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }

    private void clearUser(String id, HttpServletRequest request) {
        NoticeAuthorityUserEntity user = new NoticeAuthorityUserEntity();
        user.setNoticeId(id);
        List<NoticeAuthorityUserEntity> users = systemService.findByExample(NoticeAuthorityUserEntity.class.getName(), user);
        for (int i = 0; i < users.size(); i++) {
            this.noticeAuthorityUserService.doDelNoticeAuthorityUser(users.get(i));
        }

    }

    private void clearRole(String id, HttpServletRequest request) {
        NoticeAuthorityRoleEntity role = new NoticeAuthorityRoleEntity();
        role.setNoticeId(id);
        List<NoticeAuthorityRoleEntity> roles = systemService.findByExample(NoticeAuthorityRoleEntity.class.getName(), role);
        for (int i = 0; i < roles.size(); i++) {
            this.noticeAuthorityRoleService.doDelTSNoticeAuthorityRole(roles.get(i));
        }

    }

    /**
     * 通知公告新增页面跳转
     * @return
     */
    @RequestMapping(params = "goAdd")
    public ModelAndView goAdd(NoticeEntity tSNotice, HttpServletRequest req) {
        if (StringUtil.isNotEmpty(tSNotice.getId())) {
            tSNotice = noticeService.getById(NoticeEntity.class, tSNotice.getId());
            req.setAttribute("tSNoticePage", tSNotice);
        }
        return new ModelAndView("system/notice/tSNotice-add");
    }

    /**
     * 通知公告编辑页面跳转
     * @return
     */
    @RequestMapping(params = "goUpdate")
    public ModelAndView goUpdate(NoticeEntity tSNotice, HttpServletRequest req) {
        if (StringUtil.isNotEmpty(tSNotice.getId())) {
            tSNotice = noticeService.getById(NoticeEntity.class, tSNotice.getId());
            if (tSNotice.getNoticeTerm() == null) {
                tSNotice.setNoticeTerm(new Date());
            }
            req.setAttribute("tSNoticePage", tSNotice);

            if (tSNotice.getNoticeLevel().equals("2")) {
                NoticeAuthorityRoleEntity role = new NoticeAuthorityRoleEntity();
                role.setNoticeId(tSNotice.getId());
                List<NoticeAuthorityRoleEntity> roles = systemService.findByExample(NoticeAuthorityRoleEntity.class.getName(), role);
                StringBuffer rolesid = new StringBuffer();
                StringBuffer rolesName = new StringBuffer();
                for (int i = 0; i < roles.size(); i++) {
                    rolesid.append(roles.get(i).getRole().getId() + ",");
                    rolesName.append(roles.get(i).getRole().getRoleName() + ",");
                }
                req.setAttribute("rolesid", rolesid);
                req.setAttribute("rolesName", rolesName);
            } else if (tSNotice.getNoticeLevel().equals("3")) {
                NoticeAuthorityUserEntity user = new NoticeAuthorityUserEntity();
                user.setNoticeId(tSNotice.getId());

                List<NoticeAuthorityUserEntity> users = systemService.findByExample(NoticeAuthorityUserEntity.class.getName(), user);
                StringBuffer usersid = new StringBuffer();
                StringBuffer usersName = new StringBuffer();
                for (int i = 0; i < users.size(); i++) {
                    usersid.append(users.get(i).getUser().getId() + ",");
                    usersName.append(users.get(i).getUser().getUserName() + ",");
                }
                req.setAttribute("usersid", usersid);
                req.setAttribute("usersName", usersName);
            }
        }
        return new ModelAndView("system/notice/tSNotice-update");
    }
}