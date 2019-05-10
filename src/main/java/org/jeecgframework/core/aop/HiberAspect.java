package org.jeecgframework.core.aop;

import org.hibernate.EmptyInterceptor;
import org.hibernate.type.Type;
import org.jeecgframework.core.constant.DataBaseConstant;
import org.jeecgframework.core.constant.DictConstants;
import org.jeecgframework.core.util.ResourceUtils;
import org.jeecgframework.core.util.oConvertUtils;
import org.jeecgframework.web.system.pojo.base.TSUser;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Date;

/**
 * hibernate拦截器: 实现创建人,创建时间,创建人名称,修改人,修改时间,修改人名称自动注入
 * @author Administrator
 * @date 2019-05-10
 * @version V1.0
 */
@Component
public class HiberAspect extends EmptyInterceptor {

    @Override
    public boolean onSave(Object entity, Serializable id, Object[] state, String[] propertyNames, Type[] types) {
        TSUser currentUser = null;
        try {
            currentUser = ResourceUtils.getSessionUser();
        } catch (RuntimeException e) {
        }

        if (null == currentUser) {
            return true;
        }

        try {
            // 添加数据
            for (int index=0; index<propertyNames.length; index++) {
                if (DataBaseConstant.CREATE_BY.equals(propertyNames[index]) || DataBaseConstant.CREATE_ACCOUNT.equals(propertyNames[index])) {
                    // 创建人账号
                    if (oConvertUtils.isEmpty(state[index])) {
                        state[index] = ResourceUtils.getUserSystemData(DataBaseConstant.SYS_USER_CODE);
                    }
                    continue;
                } else if (DataBaseConstant.CREATE_NAME.equals(propertyNames[index])) {
                    // 创建人姓名
                    if (oConvertUtils.isEmpty(state[index])) {
                        state[index] = ResourceUtils.getUserSystemData(DataBaseConstant.SYS_USER_NAME);
                    }
                    continue;
                } else if (DataBaseConstant.CREATE_DATE.equals(propertyNames[index]) || DataBaseConstant.CREATE_TIME.equals(propertyNames[index])) {
                    // 创建时间
                    if (oConvertUtils.isEmpty(state[index])) {
                        state[index] = new Date();
                    }
                    continue;
                } else if (DataBaseConstant.SYS_USER_CODE.equals(propertyNames[index])) {
                    // 系统用户编码
                    if (oConvertUtils.isEmpty(state[index])) {
                        state[index] = ResourceUtils.getUserSystemData(DataBaseConstant.SYS_USER_CODE);
                    }
                    continue;
                } else if (DataBaseConstant.SYS_ORG_CODE.equals(propertyNames[index])) {
                    // 所属机构编码
                    if (oConvertUtils.isEmpty(state[index])) {
                        state[index] = ResourceUtils.getUserSystemData(DataBaseConstant.SYS_ORG_CODE);
                    }
                    continue;
                } else if (DataBaseConstant.SYS_COMPANY_CODE.equals(propertyNames[index])) {
                    // 所属公司编码
                    if (oConvertUtils.isEmpty(state[index])) {
                        state[index] = ResourceUtils.getUserSystemData(DataBaseConstant.SYS_COMPANY_CODE);
                    }
                    continue;
                } else if (DataBaseConstant.BPM_STATUS.equals(propertyNames[index])) {
                    // 业务流程状态
                    if (oConvertUtils.isEmpty(state[index])) {
                        // 未提交
                        state[index] = String.valueOf(1);
                    }
                    continue;
                } else if (DataBaseConstant.STATE_FLAG.equals(propertyNames[index])) {
                    // 业务流程状态
                    if (oConvertUtils.isEmpty(state[index])) {
                        // 未提交
                        state[index] = DictConstants.SF_N;
                    }
                    continue;
                }
            }
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
        return true;
    }


    @Override
    public boolean onFlushDirty(Object entity, Serializable id, Object[] currentState, Object[] previousState, String[] propertyNames, Type[] types) {
        TSUser currentUser = null;
        try {
            currentUser = ResourceUtils.getSessionUser();
        } catch (RuntimeException e1) {
        }

        if (null == currentUser) {
            return true;
        }

        // 添加数据
        for (int index = 0; index < propertyNames.length; index++) {
            if (DataBaseConstant.UPDATE_BY.equals(propertyNames[index]) || DataBaseConstant.UPDATE_ACCOUNT.equals(propertyNames[index])) {
                // 修改人账号
                currentState[index] = ResourceUtils.getUserSystemData(DataBaseConstant.SYS_USER_CODE);
                continue;
            } else if (DataBaseConstant.UPDATE_NAME.equals(propertyNames[index])) {
                // 修改人姓名
                currentState[index] = ResourceUtils.getUserSystemData(DataBaseConstant.SYS_USER_NAME);
                continue;
            } else if (DataBaseConstant.UPDATE_DATE.equals(propertyNames[index]) || DataBaseConstant.UPDATE_TIME.equals(propertyNames[index])) {
                // 修改时间
                currentState[index] = new Date();
                continue;
            }
        }
        return true;
    }
}
