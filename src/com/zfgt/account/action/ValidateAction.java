package com.zfgt.account.action;

import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.struts2.config.Namespace;
import org.apache.struts2.config.ParentPackage;
import org.apache.struts2.config.Result;
import org.apache.struts2.config.Results;

import com.zfgt.account.bean.ValidateBean;
import com.zfgt.common.action.BaseAction;
import com.zfgt.common.util.DESEncrypt;
import com.zfgt.common.util.QwyUtil;
import com.zfgt.orm.Account;
import com.zfgt.orm.Users;
import com.zfgt.orm.UsersInfo;
import com.zfgt.orm.UsersLogin;
/**用户信息-认证资料模块;<br>
 * 绑定用户的邮箱;<br>
 * 绑定用户的手机;<br>
 * 绑定用户的银行卡<br>
 * 认证用户的真实姓名<br>
 * @author qwy
 *
 * @createTime 2015-05-19 14:50:30
 */
@SuppressWarnings("serial")
@ParentPackage("struts-default")
@Namespace("/Product/User")
//理财产品
@Results({ 
	@Result(name = "validate", value = "/Product/User/validate.jsp"),
	@Result(name = "login", value = "/Product/login.jsp"),
	@Result(name = "error", value = "/Product/error.jsp")
	
})
public class ValidateAction extends BaseAction{
	private static Logger log = Logger.getLogger(ValidateAction.class); 
	@Resource
	private ValidateBean bean;
	
	private HttpServletRequest request;
	private Account account;
	
	private String validateCode;//绑卡验证码;
	
	

	
	/**获取绑定资料的信息;
	 * @return
	 */
	public String getValidataInfo(){
		try {
			request = getRequest();
			UsersLogin usersLogin  = (UsersLogin)request.getSession().getAttribute("usersLogin");
			if(QwyUtil.isNullAndEmpty(usersLogin)){
				return "login";
			}
			//获取最新的Users;
			Users us = bean.getUsersById(usersLogin.getUsersId());
			UsersInfo ui = us.getUsersInfo();
			if("1".equals(ui.getIsBindBank())){
				//绑定了银行卡;并且进行了实名认证;
				Account account = bean.getAccountByUsersId(usersLogin.getUsersId(),null);
				if(!QwyUtil.isNullAndEmpty(account)){
					request.setAttribute("cardLast", account.getCardLast());
					request.setAttribute("relName", account.getBankAccountName());
					request.setAttribute("idcard", QwyUtil.replaceStringToX(DESEncrypt.jieMiIdCard(account.getIdcard())));
					request.setAttribute("bankName", account.getBankName());
				}
			}
			
			if("1".equals(ui.getIsVerifyEmail())){
				//绑定了邮箱;
				request.setAttribute("email", QwyUtil.replaceStringToX(DESEncrypt.jieMiUsername(ui.getEmail())));
			}
			
			if("1".equals(ui.getIsVerifyPhone())){
				//绑定了手机;
				//绑定了邮箱;
				request.setAttribute("phone", QwyUtil.replaceStringToX(DESEncrypt.jieMiUsername(ui.getPhone())));
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.error("操作异常: ",e);
			return "error";
		}
		return "";
	}
	
	
	/**检查银行卡归属地;
	 * @return
	 */
	public String checkBankCard(){
		String json="";
		try {
			request = getRequest();
			UsersLogin usersLogin  = (UsersLogin)request.getSession().getAttribute("usersLogin");
			if(QwyUtil.isNullAndEmpty(usersLogin)){
				json = QwyUtil.getJSONString("noLogin", "请先登录");
			}else{
				if(QwyUtil.isNullAndEmpty(account)){
					json = QwyUtil.getJSONString("error", "银行卡号格式不对");
				}else{
					json = bean.bankCardCheck(account.getBankAccount());
				}
			}
		} catch (Exception e) {
			log.error("操作异常: ",e);
			json = QwyUtil.getJSONString("error", "操作异常");
		}
		try {
			QwyUtil.printJSON(getResponse(), json);
		} catch (IOException e) {
			log.error("操作异常: ",e);
		}
		return null;
	}
	
	/**请求绑卡操作;
	 * @return
	 */
	public String bindCard(){
		String json = "";
		try {
			request = getRequest();
			UsersLogin usersLogin  = (UsersLogin)request.getSession().getAttribute("usersLogin");
			if(QwyUtil.isNullAndEmpty(usersLogin)){
				json = QwyUtil.getJSONString("noLogin", "请先登录");
			}else{
				if(QwyUtil.isNullAndEmpty(account)){
					json = QwyUtil.getJSONString("error", "绑定参数有误");
				}else{
					account.setUsersId(usersLogin.getUsersId());
					String registIp = QwyUtil.getIpAddr(request);
					log.info("绑定请求registIp: "+registIp);
					account.setRegistIp(registIp);
					json = bean.bindCard(account);
				}
			}
		} catch (Exception e) {
			log.error("操作异常: ",e);
			json = QwyUtil.getJSONString("error", "绑定异常");
		}
		try {
			QwyUtil.printJSON(getResponse(), json);
		} catch (IOException e) {
			log.error("操作异常: ",e);
		}
		return null;
	}
	
	
	/**确认绑卡;<br>
	 * 输入验证码;
	 * @return
	 */
	public String confirmBindCard(){
		String json = "";
		try {
			request = getRequest();
			UsersLogin usersLogin  = (UsersLogin)request.getSession().getAttribute("usersLogin");
			if(QwyUtil.isNullAndEmpty(usersLogin)){
				json = QwyUtil.getJSONString("noLogin", "请先登录");
			}else{
				if(QwyUtil.isNullAndEmpty(validateCode)){
					json = QwyUtil.getJSONString("error", "请输入验证码");
				}else{
					if(QwyUtil.isNullAndEmpty(account)){
						json = QwyUtil.getJSONString("error", "绑定参数有误");
					}else{
						account.setUsersId(usersLogin.getUsersId());
						String registIp = QwyUtil.getIpAddr(request);
						log.info("绑定请求registIp: "+registIp);
						account.setRegistIp(registIp);
						json = bean.confirmBindCard(account,validateCode);
						Users newUsers = bean.getUsersById(usersLogin.getUsersId());
						String isBindBank = newUsers.getUsersInfo().getIsBindBank();
						usersLogin.setIsBindBank(isBindBank);
						request.getSession().setAttribute("usersLogin", usersLogin);
					}
				}
			}
		} catch (Exception e) {
			log.error("操作异常: ",e);
			json = QwyUtil.getJSONString("error", "绑定异常");
		}
		try {
			QwyUtil.printJSON(getResponse(), json);
		} catch (IOException e) {
			log.error("操作异常: ",e);
		}
		
		return null;
	}
	
	/**
	 * @return
	 */
	public String directBindPay(){
		return null;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}
	
	public String getValidateCode() {
		return validateCode;
	}

	public void setValidateCode(String validateCode) {
		this.validateCode = validateCode;
	}

	public static void main(String[] args) {
		System.out.println("xcf".toUpperCase());
	}
	
	
}
