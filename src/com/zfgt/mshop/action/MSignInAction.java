package com.zfgt.mshop.action;

import java.io.IOException;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.apache.struts2.config.Namespace;
import org.apache.struts2.config.ParentPackage;

import com.zfgt.common.action.BaseAction;
import com.zfgt.common.util.QwyUtil;
import com.zfgt.mshop.bean.MSignInBean;
import com.zfgt.orm.UsersLogin;

@ParentPackage("struts-default")
@Namespace("/Product/User")
public class MSignInAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static Logger log = Logger.getLogger(MSignInAction.class);

	@Resource
	private MSignInBean bean;

	/**
	 * 签到
	 * 
	 * @return
	 */
	public String saveMSignIn() {
		String json = "";
		try {
			UsersLogin usersLogin = (UsersLogin) getRequest().getSession().getAttribute("usersLogin");
			Map<String, Object> map = bean.saveMSignIn(usersLogin.getUsersId(), "1");
			if (!QwyUtil.isNullAndEmpty(map) && map.get("end").equals("ok")) {
				json = QwyUtil.getJSONString("ok", "签到成功");
			} else if (!QwyUtil.isNullAndEmpty(map) && map.get("end").equals("no")) {
				json = QwyUtil.getJSONString("err", map.get("message") + "");
			} else {
				json = QwyUtil.getJSONString("err", "签到失败");
			}
		} catch (Exception e) {
			e.printStackTrace();
			json = QwyUtil.getJSONString("err", "签到操作失败");
		}
		try {
			QwyUtil.printJSON(getResponse(), json);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
