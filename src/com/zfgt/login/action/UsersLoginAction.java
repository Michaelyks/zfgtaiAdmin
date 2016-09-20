package com.zfgt.login.action;

import java.io.IOException;
import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.apache.struts2.config.Namespace;
import org.apache.struts2.config.ParentPackage;
import org.apache.struts2.config.Result;
import org.apache.struts2.config.Results;

import com.alibaba.fastjson.JSON;
import com.zfgt.common.action.BaseAction;
import com.zfgt.common.util.DESEncrypt;
import com.zfgt.common.util.QwyUtil;
import com.zfgt.login.bean.UsersLoginBean;
import com.zfgt.orm.Users;
import com.zfgt.orm.UsersLogin;

/**用户登录
 * @author qwy
 *
 * @createTime 2015-4-18下午4:46:53
 */
@SuppressWarnings("serial")
@ParentPackage("struts-default")
@Namespace("/Product")
@Results({ @Result(name = "index", value = "/Product/index.jsp"),
	       @Result(name = "login", value = "/Product/login.jsp")    
})
public class UsersLoginAction extends BaseAction {
	private static Logger log = Logger.getLogger(UsersLoginAction.class); 
	@Resource
	private UsersLoginBean bean;
	private Users users;
	private String url;
	/**用户登录;
	 * @return null
	 */
	public String usersLogin(){
		String json = "";
		JSONObject jsonThree = new JSONObject();
		try {
			if(QwyUtil.isNullAndEmpty(users)){
				json = QwyUtil.getJSONString("err", "用户名或密码错误");
			}else{
				users = bean.findUsersByUsernameAndPassword(users);
				if(QwyUtil.isNullAndEmpty(users)){
					//没找到该用户;
					json = QwyUtil.getJSONString("err", "用户名或密码错误");
				}else{
					Date lastTime = users.getLastTime();
					if(QwyUtil.isNullAndEmpty(lastTime)){
						lastTime = users.getInsertTime();
					}
					UsersLogin usersLogin = new UsersLogin(users.getId(), DESEncrypt.jieMiUsername(users.getUsername()),users.getUserType(), users.getUsersInfo().getLeftMoney());
					usersLogin.setIsBindBank(users.getUsersInfo().getIsBindBank());
					getRequest().getSession().setAttribute("usersLogin", usersLogin);
					//getRequest().getSession().setAttribute("users", users);
					//重新把当前登录时间,设置进去;
					users.setLastTime(new Date());
					bean.saveUsers(users);
					json = QwyUtil.getJSONString("ok", "登录成功;上次登录时间: "+QwyUtil.fmyyyyMMddHHmmss.format(lastTime));
					//获取cookies
					Cookie[] cookies=request.getCookies();
					String url="";					
					for(Cookie cookie : cookies){ 
						if("url".equals(cookie.getName())){		    
							url=cookie.getValue();
							cookie.setMaxAge(0); //删除该Cookie 
							cookie.setPath("/"); 
							response.addCookie(cookie); 
						}
					}
					if(!QwyUtil.isNullAndEmpty(url)){
						String URL= "/Product/login.jsp";
						if(!url.contains(URL)){
		                       JSONObject jsonURL=new JSONObject();	
		                       jsonURL.put("url", url);
		                       jsonThree.putAll(jsonURL);
						}
					}
				}
			}
			JSONObject jsonObject=JSONObject.fromObject(json);
			jsonThree.putAll(jsonObject);
		} catch (Exception e) {
			log.error("操作异常: ",e);
			json = QwyUtil.getJSONString("err", "登录失败");
			JSONObject jsonObject=JSONObject.fromObject(json);
			jsonThree.putAll(jsonObject);
		}
		try {
			System.out.println(jsonThree);
			QwyUtil.printJSON(getResponse(), jsonThree.toString());
		} catch (IOException e) {
			log.error("操作异常: ",e);
		}
		
		return null;
	}
	
	
	/**保存当前URL到cookie;
	 * @return
	 */
	public String saveUrl(){
		try {			
			if(!QwyUtil.isNullAndEmpty(url)){
				//产生一个cookie对象

				Cookie cookie = new Cookie("url",url);

				cookie.setMaxAge(60);//设置cookie有效期，以秒为单位

				cookie.setPath("/");

				response.addCookie(cookie);//响应


			}
			return "login";	
											
		} catch (Exception e) {
			log.error("操作异常: ",e);
			e.printStackTrace();			
		}	
		return "login";	
	}

	public Users getUsers() {
		return users;
	}

	public void setUsers(Users users) {
		this.users = users;
	}


	public String getUrl() {
		return url;
	}


	public void setUrl(String url) {
		this.url = url;
	}


}
