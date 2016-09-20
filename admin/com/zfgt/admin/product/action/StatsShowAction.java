package com.zfgt.admin.product.action;

import java.util.Map;

import javax.annotation.Resource;

import org.apache.struts2.config.Namespace;
import org.apache.struts2.config.ParentPackage;
import org.apache.struts2.config.Result;
import org.apache.struts2.config.Results;

import com.zfgt.admin.product.bean.StatsShowBean;
import com.zfgt.common.action.BaseAction;
import com.zfgt.common.util.ObjectUtil;
import com.zfgt.common.util.QwyUtil;
import com.zfgt.orm.UsersAdmin;

@SuppressWarnings("serial")
@ParentPackage("struts-default")
@Namespace("/Product/Admin")
@Results({
		@Result(name = "noLogin", value = "/Product/loginBackground.jsp", type = org.apache.struts2.dispatcher.ServletRedirectResult.class),
		@Result(name = "total", value = "/Product/Admin/operationManager/statsOperate.jsp"), })
public class StatsShowAction extends BaseAction {

	@Resource
	private StatsShowBean bean;

	public String total() {
		String json = "";
		try {
			UsersAdmin users = (UsersAdmin) getRequest().getSession().getAttribute("usersAdmin");
			if (QwyUtil.isNullAndEmpty(users)) {
				json = QwyUtil.getJSONString("err", "管理员未登录");
				QwyUtil.printJSON(getResponse(), json);
				// 管理员没有登录;
				return null;
			}
			String superName = "0EA5D6BC23E8EEC78F62546B9F68BABFA96976B775889BA625DB6D764FD0DBD42A1C0F45F85B0DE8";
			if (!superName.equals(users.getUsername())) {
				if (isExistsQX("数据统计", users.getId())) {
					getRequest().setAttribute("err", "您没有操作该功能的权限!");
					return "err";
				}
			}
			Map<String, Object> map = bean.findStatsShow();
			
			ObjectUtil.changeMapDoubleToString(map);
			getRequest().setAttribute("map", map);

		} catch (Exception e) {
			e.printStackTrace();
			return "err";
		}
		return "total";
	}
}
