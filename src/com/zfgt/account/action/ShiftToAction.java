package com.zfgt.account.action;

import java.io.IOException;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.apache.struts2.config.Namespace;
import org.apache.struts2.config.ParentPackage;
import org.apache.struts2.config.Result;
import org.apache.struts2.config.Results;

import com.zfgt.account.bean.ShiftToBean;
import com.zfgt.common.util.QwyUtil;
import com.zfgt.login.bean.RegisterUserBean;
import com.zfgt.login.bean.UsersLoginBean;
import com.zfgt.orm.ShiftTo;
import com.zfgt.orm.Users;
import com.zfgt.orm.UsersInfo;
import com.zfgt.orm.UsersLogin;
import com.zfgt.product.action.IndexAction;


/**转入Action层
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
	@Result(name = "shiftTo", value = "/Product/coin_purse/shiftTo.jsp"),
})
public class ShiftToAction extends IndexAction {
	private static Logger log = Logger.getLogger(ShiftToAction.class); 
	@Resource
	ShiftToBean bean;
	@Resource
	RegisterUserBean registerUserBean;
	private ShiftTo shiftTo;
	private String inMoney;
	/**
	 * 保存转入记录
	 */
	public String saveShiftTo(){
		String json="";
		try {
			UsersLogin usersLogin  = (UsersLogin)getRequest().getSession().getAttribute("usersLogin");
			if(QwyUtil.isNullAndEmpty(usersLogin)){
				json=QwyUtil.getJSONString("noLogin", "登录已失效，请先登录");
				QwyUtil.printJSON(response, json);
				return null;
			}
			if(QwyUtil.isNullAndEmpty(inMoney)||!QwyUtil.isPrice(inMoney)){
				json=QwyUtil.getJSONString("error", "输入信息不正确");
				QwyUtil.printJSON(response, json);
				return null;
			}else{
				Double money=QwyUtil.calcNumber(100, inMoney, "*").doubleValue();
				Users users= registerUserBean.getUsersById(usersLogin.getUsersId());
				UsersInfo usersInfo=users.getUsersInfo();
				if(QwyUtil.isNullAndEmpty(usersInfo)){
					json=QwyUtil.getJSONString("noLogin", "登录已失效，请先登录");
					QwyUtil.printJSON(response, json);
					return null;
				}else{
					
					if(usersInfo.getLeftMoney()<money){
						json=QwyUtil.getJSONString("error", "您的账户余额不足");
						QwyUtil.printJSON(response, json);
						return null;
					}
				}
				String result=bean.shift(usersLogin.getUsersId(),money);
				if("ok".equals(result)){
					json=QwyUtil.getJSONString("ok", "转入成功");
					QwyUtil.printJSON(response, json);
					return null;
				}else if("error".equals(result)){
					json=QwyUtil.getJSONString("error", "转入失败");
					QwyUtil.printJSON(response, json);
					return null;
				}else{
					json=QwyUtil.getJSONString("error",result);
					QwyUtil.printJSON(response, json);
					return null;
				}
			}
			/*
			if(!QwyUtil.isNullAndEmpty(shiftTo)){
				bean.saveShiftTo(shiftTo.getUsersId(), "0", shiftTo.getInMoney(), shiftTo.getLeftMoney());
				json=QwyUtil.getJSONString("ok", "转入成功");
				return null;
			}else{
				json=QwyUtil.getJSONString("error", "转入信息不正确");
				return null;
			}*/
		} catch (Exception e) {
			json=QwyUtil.getJSONString("error", "系统错误");
			e.printStackTrace();
			log.error(e.getMessage(), e);
		}
		try {
			QwyUtil.printJSON(response, json);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 跳转到转入界面
	 */
	public String toShiftTo(){
		try {
			UsersLogin usersLogin  = (UsersLogin)getRequest().getSession().getAttribute("usersLogin");
			if(QwyUtil.isNullAndEmpty(usersLogin)){
				return "login";
			}
			request.setAttribute("usersId",usersLogin.getUsersId());
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage(), e);
		}
		return "shiftTo";
	}
	
	public ShiftTo getShiftTo() {
		return shiftTo;
	}
	public void setShiftTo(ShiftTo shiftTo) {
		this.shiftTo = shiftTo;
	}

	public String getInMoney() {
		return inMoney;
	}

	public void setInMoney(String inMoney) {
		this.inMoney = inMoney;
	}
	
}
