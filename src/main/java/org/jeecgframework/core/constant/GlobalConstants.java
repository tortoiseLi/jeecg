package org.jeecgframework.core.constant;

import org.jeecgframework.core.util.ResourceUtil;


/**
 * 全局变量定义
 * @author DELL
 * @version V1.0
 * @date 2019-04-30
 */
public final class GlobalConstants {

    /**
     * 保存用户到SESSION
     */
    public static final String USER_SESSION = "USER_SESSION";

    /******************************** 人员状态 ********************************/

    /**
     * 人员状态_正常
     */
    public static final Short USER_NORMAL = 1;

    /**
     * 人员状态_禁用
     */
    public static final Short USER_FORBIDDEN = 0;

    /**
     * 人员类型_超级管理员
     */
    public static final Short USER_ADMIN = -1;

    /******************************** 用户类型 ********************************/

    /**
     * 用户类型_系统类型用户
     */
    public static final String USER_TYPE_SYSTEM = "1";

    /**
     * 用户类型_接口类型用户
     */
    public static final String USER_TYPE_INTERFACE = "2";

    /******************************** 逻辑删除标记 ********************************/

    /**
     * 逻辑删除标记_删除
     */
    public static final Short DELETE_FORBIDDEN = 1;

    /**
     * 逻辑删除标记_未删除
     */
    public static final Short DELETE_NORMAL = 0;

    /******************************** 日志级别 ********************************/

    /**
     * 日志级别定义_信息
     */
    public static final Short LOG_LEVEL_INFO = 1;

    /**
     * 日志级别定义_警告
     */
    public static final Short LOG_LEAVE_WARRING = 2;

    /**
     * 日志级别定义_错误
     */
    public static final Short LOG_LEAVE_ERROR = 3;

    /******************************** 日志类型 ********************************/

    /**
     * 日志类型_登陆
     */
    public static final Short LOG_TYPE_LOGIN = 1;

    /**
     * 日志类型_退出
     */
    public static final Short LOG_TYPE_EXIT = 2;

    /**
     * 日志类型_插入
     */
    public static final Short LOG_TYPE_INSERT = 3;

    /**
     * 日志类型_删除
     */
    public static final Short LOG_TYPE_DELETE = 4;

    /**
     * 日志类型_更新
     */
    public static final Short LOG_TYPE_UPDATE = 5;

    /**
     * 日志类型_上传
     */
    public static final Short LOG_TYPE_UPLOAD = 6;

    /**
     * 日志类型_其他
     */
    public static final Short LOG_TYPE_OTHER = 7;

    /******************************** 权限等级 ********************************/

    /**
     * 权限等级_一级权限
     */
    public static final Short FUNCTION_LEVEL_ONE = 0;

    /**
     * 权限等级_二级权限
     */
    public static final Short FUNCTION_LEVEL_TWO = 1;

    /**
     * 权限类型_菜单
     */
    public static final Short FUNCTION_TYPE_PAGE = 0;

    /******************************** 权限类型 ********************************/

    /**
     * 权限类型_权限类型(权限使用，不作为菜单首页加载)
     */
    public static final Short FUNCTION_TYPE_FROM = 1;

    /**
     * 没有勾选的操作code
     */
    public static final String NO_AUTO_OPERATION_CODES = "noauto_operationCodes";

    /**
     * 勾选的操作code
     */
    public static final String OPERATION_CODES = "operationCodes";

    /**
     * 权限类型_页面
     */
    public static final Short OPERATION_TYPE_HIDE = 0;

    /**
     * 权限类型_表单或者弹出
     */
    public static final Short OPERATION_TYPE_DISABLED = 1;

    /**
     * 数据权限_菜单数据规则集合
     */
    public static final String MENU_DATA_AUTHOR_RULES = "MENU_DATA_AUTHOR_RULES";

    /**
     * 数据权限_菜单数据规则sql
     */
    public static final String MENU_DATA_AUTHOR_RULE_SQL = "MENU_DATA_AUTHOR_RULE_SQL";

    /**
     * 配置系统是否开启按钮权限控制
     */
    public static boolean BUTTON_AUTHORITY_CHECK = false;

    /**
     * rest接口 list最大当前条数
     */
    public static Integer MAX_PAGE_SIZE = 2000;

    /**
     * 类注解&系统缓存[临时缓存]
     */
    public static String SYSTEM_BASE_CACHE = "systemBaseCache";

    /**
     * UI标签[临时缓存]
     */
    public static String TAG_CACHE = "tagCache";

    /**
     * 字典\国际化\在线用户列表 [永久缓存]
     */
    public static String FOREVER_CACHE = "foreverCache";

    /**
     * 登录用户访问权限缓存[临时缓存]
     */
    public static String SYS_AUTH_CACHE = "sysAuthCache";

    static {
        String button_authority_jeecg = ResourceUtil.getSessionattachmenttitle("button.authority.jeecg");
        if ("true".equals(button_authority_jeecg)) {
            BUTTON_AUTHORITY_CHECK = true;
        }
    }

}
