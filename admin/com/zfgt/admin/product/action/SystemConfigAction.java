package com.zfgt.admin.product.action;

import javax.annotation.Resource;

import org.apache.struts2.config.Namespace;
import org.apache.struts2.config.ParentPackage;
import org.apache.struts2.config.Result;
import org.apache.struts2.config.Results;

import com.zfgt.common.action.BaseAction;
import com.zfgt.common.bean.SystemConfigBean;
import com.zfgt.common.util.QwyUtil;
import com.zfgt.orm.SystemConfig;
import com.zfgt.orm.UsersAdmin;

/**
 * 后台管理--系统配置
 * 
 * @author 曾礼强
 * @createTime 2015年6月15日 10:30:47
 */
@SuppressWarnings("serial")
@ParentPackage("struts-default")
@Namespace("/Product/Admin")
// 发布产品页面
@Results({ @Result(name = "systemConfig", value = "/Product/Admin/systemConfig/systemConfig.jsp") })
public class SystemConfigAction extends BaseAction {
	@Resource
	SystemConfigBean bean;
	private SystemConfig config;

	/**
	 * 跳转到系统配置详细信息
	 */
	public String toSystemConfig() {
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
				if (isExistsQX("系统配置", users.getId())) {
					getRequest().setAttribute("err", "您没有操作该功能的权限!");
					return "err";
				}
			}
			config = bean.findSystemConfig();
			getRequest().setAttribute("config", config);
			return "systemConfig";
		} catch (Exception e) {
			log.error("操作异常: ", e);
			json = QwyUtil.getJSONString("err", "查询记录异常");
		}
		return null;
	}

	/**
	 * 修改系统配置文件
	 * 
	 * @return
	 */
	public String updateSystemConfig() {
		try {
			String json = "";
			UsersAdmin users = (UsersAdmin) getRequest().getSession().getAttribute("usersAdmin");
			if (QwyUtil.isNullAndEmpty(users)) {
				json = QwyUtil.getJSONString("err", "管理员未登录");
				QwyUtil.printJSON(getResponse(), json);
				// 管理员没有登录;
				return null;
			}
			if (!QwyUtil.isNullAndEmpty(config)) {
				if (bean.updateSystemconfig(config)) {
					System.out.println("修改成功!");
					request.setAttribute("isOk", "ok");
					return "systemConfig";
				}
			}
			System.out.println("修改失败!");
		} catch (Exception e) {
			e.printStackTrace();
		}
		request.setAttribute("isOk", "no");
		return "systemConfig";
	}

	public SystemConfig getConfig() {
		return config;
	}

	public void setConfig(SystemConfig config) {
		this.config = config;
	}

}
