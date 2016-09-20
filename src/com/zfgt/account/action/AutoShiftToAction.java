package com.zfgt.account.action;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.apache.struts2.config.Namespace;
import org.apache.struts2.config.ParentPackage;
import org.apache.struts2.config.Result;
import org.apache.struts2.config.Results;

import com.zfgt.account.bean.AutoShiftToBean;
import com.zfgt.common.util.QwyUtil;
import com.zfgt.orm.AutoShiftTo;
import com.zfgt.orm.Users;
import com.zfgt.orm.UsersInfo;
import com.zfgt.orm.UsersLogin;
import com.zfgt.product.action.IndexAction;


/**
 * 自动转入Action层
 * @author qwy
 *
 * 2015-04-20 19:45:50
 */
@SuppressWarnings("serial")
@ParentPackage("struts-default")
@Namespace("/Product/User")
//理财产品
@Results({ 
	@Result(name = "login", value = "/Product/login.jsp"),
	@Result(name = "toAutoShiftTo", value = "/Product/coin_purse/autoShiftTo.jsp"),
})
public class AutoShiftToAction extends IndexAction {
	private static Logger log = Logger.getLogger(AutoShiftToAction.class); 
	@Resource
	AutoShiftToBean autoShiftToBean;
	private String type;
	private String leftMoney;
	/**
	 * 自动转入
	 * @return
	 */
	public String autoShiftTo(){
		String json="";
		try {
			UsersLogin usersLogin  = (UsersLogin)getRequest().getSession().getAttribute("usersLogin");
			if(QwyUtil.isNullAndEmpty(usersLogin)){
				json=QwyUtil.getJSONString("noLogin", "登录已失效，请先登录");
				QwyUtil.printJSON(response, json);
				return null;
			}
			if(QwyUtil.isNullAndEmpty(leftMoney)||!QwyUtil.isPrice(leftMoney)){
				json=QwyUtil.getJSONString("error", "输入余额保留金额格式有误");
				QwyUtil.printJSON(response, json);
				return null;
			}else{
				if(!QwyUtil.isNullAndEmpty(autoShiftToBean.saveAutoShiftTo(type,QwyUtil.calcNumber(Double.valueOf(leftMoney), 100, "*").doubleValue(), usersLogin.getUsersId()))){
					json=QwyUtil.getJSONString("ok", "开启成功");
					QwyUtil.printJSON(response, json);
					return null;
				}else{
					json=QwyUtil.getJSONString("error", "开启失败");
					QwyUtil.printJSON(response, json);
					return null;
				}
			}
		} catch (Exception e) {
			log.error("操作异常: ",e);
		}
		return "autoShiftTo";
	}
	
	/**
	 * 自动转入界面
	 * @return
	 */
	public String toAutoShiftTo(){
		String json="";
		try {
			UsersLogin usersLogin  = (UsersLogin)getRequest().getSession().getAttribute("usersLogin");
			if(QwyUtil.isNullAndEmpty(usersLogin)){
				json=QwyUtil.getJSONString("noLogin", "登录已失效，请先登录");
				QwyUtil.printJSON(response, json);
				return null;
			}
			AutoShiftTo autoShiftTo=autoShiftToBean.findAutoShiftToByUsersId(usersLogin.getUsersId());
			if(!QwyUtil.isNullAndEmpty(autoShiftTo)){
				request.setAttribute("type", autoShiftTo.getType());
				request.setAttribute("leftMoney", autoShiftTo.getLeftMoney());
			}else{
				request.setAttribute("type", 1);
				request.setAttribute("leftMoney", 0);
			}
			
		} catch (Exception e) {
			log.error("操作异常: ",e);
		}
		return "toAutoShiftTo";
	}
	
	
	
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getLeftMoney() {
		return leftMoney;
	}
	public void setLeftMoney(String leftMoney) {
		this.leftMoney = leftMoney;
	}
	
	
}
