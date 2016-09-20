package com.zfgt.account.action;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Date;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.apache.struts2.config.Namespace;
import org.apache.struts2.config.ParentPackage;
import org.apache.struts2.config.Result;
import org.apache.struts2.config.Results;

import com.zfgt.account.bean.UserInfoBean;
import com.zfgt.common.action.BaseAction;
import com.zfgt.common.util.DESEncrypt;
import com.zfgt.common.util.EmailUtil;
import com.zfgt.common.util.MailInfo;
import com.zfgt.common.util.QwyUtil;
import com.zfgt.login.bean.RegisterUserBean;
import com.zfgt.orm.Users;
import com.zfgt.orm.UsersInfo;
import com.zfgt.orm.UsersLogin;

/**用户信息
 * @author qwy
 *
 * 2015-4-18上午1:10:54
 */
@SuppressWarnings("serial")
@ParentPackage("struts-default")
@Namespace("/Product/User")
//理财产品
@Results({ @Result(name = "userInfo", value = "/Product/User/userInfo.jsp"),
	@Result(name = "login", value = "/Product/login.jsp"),
	@Result(name = "modifypassword", value = "/Product/login.jsp")
	
})
public class UserInfoAction extends BaseAction {
	private static Logger log = Logger.getLogger(UserInfoAction.class);
	@Resource
	private UserInfoBean bean;
	@Resource
	private RegisterUserBean rubean;
	@Resource
	private MailInfo mailInfo;

	
	private String oldPassword="";
	private String newPassword="";
	private String newPassword2="";
	
	private String oldPayPassword="";
	private String newPayPassword="";
	private String newPayPassword2="";
	private Users users;
	private UsersInfo usersInfo;
	private String email;
	
	private String code;
	/**获取用户信息;
	 * @return
	 */
	public String getUserInfo(){
		try {
			log.info("获取用户信息;");
			UsersLogin usersLogin  = (UsersLogin)getRequest().getSession().getAttribute("usersLogin");
			if(QwyUtil.isNullAndEmpty(usersLogin)){
				return "login";
			}
			Users users = bean.getUserById(usersLogin.getUsersId());
			UsersInfo usersInfo = users.getUsersInfo();
			getRequest().setAttribute("usersInfo", usersInfo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.error("操作异常: ",e);
			log.error("UserInfoAction.getUserInfo",e);
		}
		return "userInfo";
	}
	/**修改用户密码;
	 * @return
	 */
	public String modifyPassword(){
		System.out.println("修改密码");
		String json="";
		try{
			log.info("修改账号密码");
			UsersLogin usersLogin  = (UsersLogin)getRequest().getSession().getAttribute("usersLogin");
			if(QwyUtil.isNullAndEmpty(usersLogin)){
				json = QwyUtil.getJSONString("noLogin","登录超时,请重新登录");
				QwyUtil.printJSON(getResponse(), json);
				return null;
			}
			Users users1 = bean.getUserById(usersLogin.getUsersId());
			if (!(DESEncrypt.jieMiPassword(users1.getPassword())).equals(oldPassword)) {
				System.out.println("原密码错误");
				json = QwyUtil.getJSONString("err", "原密码错误");
			} else if (!newPassword.equals(newPassword2)) {
				json = QwyUtil.getJSONString("err", "两次密码不一致");
			} else if (oldPassword.equals(newPassword)) {
				json = QwyUtil.getJSONString("err", "新旧密码不能相同");
			} else {
				boolean isOk = rubean.modifyPassword(users1.getId(),newPassword2);
				if (isOk) {
					getRequest().getSession().setAttribute("usersLogin", null);
					json = QwyUtil.getJSONString("ok", "密码修改成功,请重新登录");
				} else {
					json = QwyUtil.getJSONString("err", "密码修改失败");
				}
			}
		}catch (Exception e) {
			log.error("操作异常: ",e);
			log.error("UserInfoAction.modifyPassword",e);
		}
		try {
			QwyUtil.printJSON(getResponse(), json);
		} catch (IOException e) {
			log.error("操作异常: ",e);
		}
		return null;
	}
	
	/**设置用户交易密码;
	 * @return
	 */
	public String setPayPassword(){
		System.out.println("设置用户交易密码");
		String json="";
		try{
			log.info("设置用户交易密码");
			UsersLogin usersLogin  = (UsersLogin)getRequest().getSession().getAttribute("usersLogin");
			if(QwyUtil.isNullAndEmpty(usersLogin)){
				json = QwyUtil.getJSONString("noLogin","登录超时,请重新登录");
				QwyUtil.printJSON(getResponse(), json);
				return null;
			}

			Users users1 = bean.getUserById(usersLogin.getUsersId());
			if(!QwyUtil.isOnlyNumber(newPayPassword)||newPayPassword.length()!=6){
				json = QwyUtil.getJSONString("err", "请输入6位纯数字的交易密码");
			}else if(!QwyUtil.isNullAndEmpty(users1.getPayPassword())) {
				json = QwyUtil.getJSONString("err", "交易密码已存在，请勿随意修改");
			} else if (!newPayPassword.equals(newPayPassword2)) {
				json = QwyUtil.getJSONString("err", "两次交易密码不一致");
			} else {
				boolean isOk = rubean.modifyPayPassword(users1.getId(),newPayPassword2);
				if (isOk) {
					json = QwyUtil.getJSONString("ok", "交易密码设置成功,请牢记!");
				} else {
					json = QwyUtil.getJSONString("err", "交易密码设置失败,请联系客服!");
				}
			}
					
			
		}catch (Exception e) {
			log.error("操作异常: ",e);
			log.error("UserInfoAction.modifyPayPassword",e);
		}
		try {
			QwyUtil.printJSON(getResponse(), json);
		} catch (IOException e) {
			log.error("操作异常: ",e);
		}
		return null;
	}	
	
	/**设置修改用户交易密码;
	 * @return
	 */
	public String modifyPayPassword(){
		System.out.println("设置修改用户交易密码");
		String json="";
		try{
			log.info("设置修改用户交易密码");
			UsersLogin usersLogin  = (UsersLogin)getRequest().getSession().getAttribute("usersLogin");
			if(QwyUtil.isNullAndEmpty(usersLogin)){
				json = QwyUtil.getJSONString("noLogin","登录超时,请重新登录");
				QwyUtil.printJSON(getResponse(), json);
				return null;
			}
			if(!QwyUtil.verifyPhone(users.getUsername())){
				json = QwyUtil.getJSONString("err", "手机号码输入有误");
			}else{
				if(!QwyUtil.isNullAndEmpty(code)&&code.equals(rubean.getYzm(users.getUsername(), "3"))){
					Users users1 = bean.getUserById(usersLogin.getUsersId());
					if(!QwyUtil.isOnlyNumber(newPayPassword)||newPayPassword.length()!=6){
						json = QwyUtil.getJSONString("err", "请输入6位纯数字的交易密码");
					}else if((DESEncrypt.jieMiPassword(users1.getPayPassword())).equals(newPayPassword)) {
						json = QwyUtil.getJSONString("err", "新旧交易密码不能相同");
					} else if (!newPayPassword.equals(newPayPassword2)) {
						json = QwyUtil.getJSONString("err", "两次交易密码不一致");
					} else {
						boolean isOk = rubean.modifyPayPassword(users1.getId(),newPayPassword2);
						if (isOk) {
							json = QwyUtil.getJSONString("ok", "交易密码修改成功,请牢记!");
						} else {
							json = QwyUtil.getJSONString("err", "交易密码修改失败,请联系客服!");
						}
					}
				}else{
					json = QwyUtil.getJSONString("err", "验证码输入有误");
				}
			}
			
		}catch (Exception e) {
			log.error("操作异常: ",e);
			log.error("UserInfoAction.modifyPayPassword",e);
		}
		try {
			QwyUtil.printJSON(getResponse(), json);
		} catch (IOException e) {
			log.error("操作异常: ",e);
		}
		return null;
	}
	
	
	
	
	
	
	/**
	 *修改昵称
	 * @return
	 * 
	 */
	public String modifyNickName(){
		String json="";
		log.info("修改昵称");
		if(users==null){
			return "login";
		}
		try {
			String nname = URLDecoder.decode(users.getUsersInfo().getNickName(), "utf-8");
			System.out.println(nname);
			UsersLogin usersLogin = (UsersLogin)getRequest().getSession().getAttribute("usersLogin");
			Users users = bean.getUserById(usersLogin.getUsersId());
			UsersInfo userInfo= users.getUsersInfo();
			userInfo.setNickName(nname);
			userInfo.setUpdateTime(new Date());
			UsersInfo ui=bean.saveOrUpdateUserInfo(userInfo);
			getRequest().getSession().setAttribute("users", ui.getUsers());
			json = QwyUtil.getJSONString("success", "昵称修改成功");
			QwyUtil.printJSON(getResponse(), json);
		} catch (IOException e) {
			log.error("操作异常: ",e);
			log.error("UserInfoAction.modifyNickName",e);
		}
		return null;
	}
	/**
	 *修改真实姓名
	 * @return
	 * @throws IOException 
	 * 
	 */
	public String modifyRealName() throws IOException{
		String json="";
		log.info("修改真实姓名");
		if(users==null){
			return "login";
		}
		UsersInfo userInfo=users.getUsersInfo();
		if(userInfo==null){
			return "login";
		}
		try {
			String rrame = URLDecoder.decode(users.getUsersInfo().getRealName(), "utf-8");
			UsersLogin usersLogin = (UsersLogin)getRequest().getSession().getAttribute("usersLogin");
			Users users = bean.getUserById(usersLogin.getUsersId());
			UsersInfo ui = users.getUsersInfo();
			ui.setRealName(rrame);
			ui.setUpdateTime(new Date());
			UsersInfo usersInfo=bean.saveOrUpdateUserInfo(ui);
			getRequest().getSession().setAttribute("users", usersInfo.getUsers());
			json = QwyUtil.getJSONString("success", "真实姓名修改成功");
			QwyUtil.printJSON(getResponse(), json);
		} catch (IOException e) {
			log.error("操作异常: ",e);
			log.error("UserInfoAction.modifyRealName",e);
			json = QwyUtil.getJSONString("err", "真实姓名修改失败");
			QwyUtil.printJSON(getResponse(), json);
		}
		return null;
	}
	/**发送邮件
	 *
	 * @return
	 */
	public String sendEmail(){
		System.out.println("进来");
		String json="";
		try {
			String addressTo=URLDecoder.decode(email, "utf-8");
			System.out.println(addressTo);
			UsersLogin usersLogin = (UsersLogin)getRequest().getSession().getAttribute("usersLogin");
			Users users1 = bean.getUserById(usersLogin.getUsersId());
			UsersInfo userInfo1=users1.getUsersInfo();
			//判断该邮箱是否被绑定
			UsersInfo userInfo2=bean.isEmailband(addressTo);
			if(QwyUtil.isNullAndEmpty(users1)){
				json = QwyUtil.getJSONString("err", "发送邮件失败");
			}else
				if(!QwyUtil.isNullAndEmpty(userInfo2)){
					json = QwyUtil.getJSONString("err", "该邮箱已经被绑定");
			}else
				if("1".equals(userInfo1.getIsVerifyEmail())&&!QwyUtil.isNullAndEmpty(userInfo1.getEmail())){
					json = QwyUtil.getJSONString("err", "该账号已绑定邮箱");
					System.out.println("该账号已绑定邮箱");
					log.info("该账号已绑定邮箱");
			}
			else{
			MailInfo mailInfo = new MailInfo();  
			mailInfo.setMailServerHost("smtp.163.com");  
			mailInfo.setMailServerPort("25");  
			mailInfo.setValidate(true);  
			mailInfo.setUsername("15013628196@163.com");  
			mailInfo.setPassword("Iloveyou851");// 您的邮箱密码  
			mailInfo.setFromAddress("15013628196@163.com");  
			mailInfo.setToAddress(addressTo); 
			mailInfo.setSubject("万国会员激活");  
			StringBuffer demo = new StringBuffer();  
			demo.append("<!DOCTYPE html PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\" \"http://www.w3.org/TR/html4/loose.dtd\">")  
			.append("<html>")  
			.append("<head>")  
			.append("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">")  
			.append("<title>中泰理财会员激活</title>")  
			.append("<style type=\"text/css\">")  
			.append(".test{font-family:\"Microsoft Yahei\";font-size: 18px;color: red;}")  
			.append("</style>")  
			.append("</head>")  
			.append("<body>")  
			.append("<body>")
			.append("<a>欢迎来到中泰理财邮箱验证,点击链接激活邮箱:</a><a href='http://192.168.0.116:8088/wgtz/Product/index!checkEmail.action?email=")
			.append(email)
			.append("&zlq=")
			.append(users1.getId())
			.append("'>http://192.168.0.116:8088/wgtz/Product/index!checkEmail.action?email=")
			.append(email)
			.append("&zlq=")
			.append(users1.getId())
			.append("</a>")
			.append("</body>")  
			.append("</html>");
			mailInfo.setContent(demo.toString());
			EmailUtil.sendHtmlMail(mailInfo);// 发送html格式  
			json = QwyUtil.getJSONString("success", "发送邮箱成功,请注意查收！");
			}
		} catch (UnsupportedEncodingException e) {
			System.err.println("异常了");
			log.error("操作异常: ",e);
			log.error("UserInfoAction.sendEmail",e);
			json = QwyUtil.getJSONString("err", "邮箱发送失败");
			try {
			QwyUtil.printJSON(getResponse(), json);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}finally{
			try {
				System.out.println("结束");
				QwyUtil.printJSON(getResponse(), json);
			} catch (IOException e) {
				log.error("操作异常: ",e);
			}
		}
		return null;
	}
	/**
	 * 点击邮件进行激活
	 * @return
	 */
	public String checkEmail(){
		System.out.println("进入邮箱验证");
		String json="";
		try {
			UsersLogin usersLogin = (UsersLogin)getRequest().getSession().getAttribute("usersLogin");
			Users users1 = bean.getUserById(usersLogin.getUsersId());
			UsersInfo userInfo=users1.getUsersInfo();
			userInfo.setIsVerifyEmail("1");
			String addressTo=URLDecoder.decode(email, "utf-8");
			userInfo.setEmail(DESEncrypt.jiaMiUsername(addressTo));
			bean.saveOrUpdateUserInfo(userInfo);
			if(QwyUtil.isNullAndEmpty(users1.getUsername())){
				users1.setUsername(DESEncrypt.jiaMiUsername(email));
				System.out.println("手机注册 绑定邮箱");
				log.info("手机注册 绑定邮箱");
			}
			json = QwyUtil.getJSONString("success", "邮箱验证通过");
			
		} catch (Exception e) {
			log.error("操作异常: ",e);
			log.error("UserInfoAction.checkEmail",e);
			json = QwyUtil.getJSONString("err", "邮箱验证未通过");
		}finally{
			try {
				QwyUtil.printJSON(getResponse(), json);
				System.out.println("结束");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				log.error("操作异常: ",e);
			}
		}
		return null;
		
	}
	/**
	 * 通过用户id查询用户信息
	 * @return
	 */
	public String queryUsersInfoByid(){
		
		log.info("根据用户id查询用户信息");
		try{
			UsersLogin usersLogin = (UsersLogin)getRequest().getSession().getAttribute("usersLogin");
			if(QwyUtil.isNullAndEmpty(usersLogin)){
				return "login"; 
			}
			Users users = bean.getUserById(usersLogin.getUsersId());
			UsersInfo  usersInfo=users.getUsersInfo();
			String isPwd="no";
			if(!QwyUtil.isNullAndEmpty(users.getPayPassword())){
				isPwd="ok";
			}
			request.getSession().setAttribute("isPwd", isPwd);
			request.getSession().setAttribute("usersInfo", usersInfo);
			
		}catch(Exception e){
			log.error("操作异常: ",e);
			log.error("UserInfoAction.queryUsersInfoByid",e);
		}
		return "userInfo";
	}
	
	public static Logger getLog() {
		return log;
	}
	public static void setLog(Logger log) {
		UserInfoAction.log = log;
	}
	public UserInfoBean getBean() {
		return bean;
	}
	public void setBean(UserInfoBean bean) {
		this.bean = bean;
	}
	public RegisterUserBean getRubean() {
		return rubean;
	}
	public void setRubean(RegisterUserBean rubean) {
		this.rubean = rubean;
	}
	public Users getUsers() {
		return users;
	}
	public void setUsers(Users users) {
		this.users = users;
	}
	public UsersInfo getUsersInfo() {
		return usersInfo;
	}
	public void setUsersInfo(UsersInfo usersInfo) {
		this.usersInfo = usersInfo;
	}
	public MailInfo getMailInfo() {
		return mailInfo;
	}
	public void setMailInfo(MailInfo mailInfo) {
		this.mailInfo = mailInfo;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	
	
	public String getOldPassword() {
		return oldPassword;
	}
	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}
	public String getNewPassword() {
		return newPassword;
	}
	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}
	public String getNewPassword2() {
		return newPassword2;
	}
	public void setNewPassword2(String newPassword2) {
		this.newPassword2 = newPassword2;
	}
	public String getOldPayPassword() {
		return oldPayPassword;
	}
	public void setOldPayPassword(String oldPayPassword) {
		this.oldPayPassword = oldPayPassword;
	}
	public String getNewPayPassword() {
		return newPayPassword;
	}
	public void setNewPayPassword(String newPayPassword) {
		this.newPayPassword = newPayPassword;
	}
	public String getNewPayPassword2() {
		return newPayPassword2;
	}
	public void setNewPayPassword2(String newPayPassword2) {
		this.newPayPassword2 = newPayPassword2;
	}

	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	

	

}
