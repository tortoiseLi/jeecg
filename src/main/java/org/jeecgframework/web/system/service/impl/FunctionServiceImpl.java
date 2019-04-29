package org.jeecgframework.web.system.service.impl;

import java.util.List;

import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.jeecgframework.core.util.MutiLangUtil;
import org.jeecgframework.web.system.pojo.base.DataRuleEntity;
import org.jeecgframework.web.system.pojo.base.FunctionEntity;
import org.jeecgframework.web.system.pojo.base.OperationEntity;
import org.jeecgframework.web.system.service.FunctionService;
import org.springframework.stereotype.Service;

@Service("functionService")
public class FunctionServiceImpl extends CommonServiceImpl implements FunctionService {

	@Override
	public AjaxJson delFunction(String functionId) {
		AjaxJson j = new AjaxJson();
		FunctionEntity function = this.getEntity(FunctionEntity.class, functionId);
		String message = MutiLangUtil.paramDelSuccess("common.menu");
		//删除角色菜单关系
		this.executeSql("delete from t_s_role_function where functionid=?", functionId);
		//删除权限组和菜单关系
		this.executeSql("delete from t_s_depart_authg_function_rel where auth_id=?", functionId);

		FunctionEntity parent = function.getTSFunction();
		try {

			List<FunctionEntity> listFunction = function.getTSFunctions();
			if (listFunction != null && listFunction.size() > 0) {
				message = "菜单【" + MutiLangUtil.getLang(function.getFunctionName()) + "】存在下级菜单，不能删除";
				j.setMsg(message);
				j.setSuccess(false);
				return j;
			}
			List<OperationEntity> op = this.findHql("from OperationEntity where TSFunction.id = ?", functionId);
			if (op != null && op.size() > 0) {
				message = "菜单【" + MutiLangUtil.getLang(function.getFunctionName()) + "】有设置页面权限，不能删除";
				j.setMsg(message);
				j.setSuccess(false);
				return j;
			}
			List<DataRuleEntity> tsdr = this.findByProperty(DataRuleEntity.class, "TSFunction", function);
			if (tsdr != null && tsdr.size() > 0) {
				message = "菜单【" + MutiLangUtil.getLang(function.getFunctionName()) + "】存在数据规则，不能删除";
				j.setMsg(message);
				j.setSuccess(false);
				return j;
			}

			if (parent != null) {
				parent.getTSFunctions().remove(function);
			}
			this.delete(function);
		} catch (Exception e) {
			if (parent != null) {
				parent.getTSFunctions().add(function);
			}
			e.printStackTrace();
			message = MutiLangUtil.getLang("common.menu.del.fail");
		}
		return j;

	}

}
