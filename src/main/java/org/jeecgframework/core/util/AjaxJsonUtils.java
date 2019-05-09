package org.jeecgframework.core.util;

import org.jeecgframework.core.common.model.json.AjaxJson;

import java.util.Map;

/**
 * AjaxJson工具类
 * @author DELL
 * @version V1.0
 * @date 2019/5/9
 */
public class AjaxJsonUtils {

    public static AjaxJson generateAjax(boolean success, String msg, Map<String, Object> attributes, Object... objects) {
        AjaxJson ajaxJson = new AjaxJson();
        ajaxJson.setSuccess(success);
        ajaxJson.setMsg(msg);
        if (objects != null && objects.length > 0) {
            if (objects.length == 1) {
                ajaxJson.setObj(objects[0]);
            } else {
                ajaxJson.setObj(objects);
            }
        } else {
            ajaxJson.setObj((Object)null);
        }

        ajaxJson.setAttributes(attributes);
        return ajaxJson;
    }

    public static AjaxJson generateDataAjax(Object... objects) {
        return generateSuccessAjax(objects);
    }

    public static AjaxJson generateSuccessAjax(Object... objects) {
        return generateAjax(true, "success", (Map)null, objects);
    }

    public static AjaxJson generateSuccessAjax(Map<String, Object> attributes, Object... objects) {
        return generateAjax(true, "success", attributes, objects);
    }

    public static AjaxJson generateSuccessAjax(String message, Object objects) {
        return generateAjax(true, message, (Map)null, objects);
    }

    public static AjaxJson generateFailsAjax(String message, Object... objects) {
        return generateAjax(false, message, (Map)null, objects);
    }

    public static AjaxJson generateFailsAjax(String message, Map<String, Object> attributes, Object... objects) {
        return generateAjax(false, message, attributes, objects);
    }

    public static AjaxJson generateBoolAjax(boolean bool, String message) {
        return bool ? generateSuccessAjax(message) : generateFailsAjax(message);
    }

    public static AjaxJson generateBoolAjax(boolean bool, String message, Object... objects) {
        return generateBoolAjax(bool, message, (Map)null, objects);
    }

    public static AjaxJson generateBoolAjax(boolean bool, String message, Map<String, Object> attributes, Object... objects) {
        return bool ? generateSuccessAjax(attributes, objects) : generateFailsAjax(message, attributes, objects);
    }
}
