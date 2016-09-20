package com.zfgt.admin.login.action;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.ServletContext;

import org.apache.struts2.config.Namespace;
import org.apache.struts2.config.ParentPackage;
import org.apache.struts2.config.Result;
import org.apache.struts2.config.Results;

import com.zfgt.admin.login.bean.LoginBackgroundBean;
import com.zfgt.common.action.BaseAction;
import com.zfgt.common.bean.PlatformBean;
import com.zfgt.common.util.DESEncrypt;
import com.zfgt.common.util.QwyUtil;
import com.zfgt.orm.Modul;
import com.zfgt.orm.Platform;
import com.zfgt.orm.RolesRight;
import com.zfgt.orm.UsersAdmin;

/**后台管理员登录Action层<br>
 * @author qwy
 *
 * 2015-4-16下午11:52:17
 */


@SuppressWarnings("serial")
@ParentPackage("struts-default")
@Namespace("/Product")
@Results({ 
	//发布产品页面
	@Result(name = "loginOk", value = "/Product/Admin/login/welcome.jsp"),
	//后台登录页面
	@Result(name = "loginFail", value = "/Product/loginBackground.jsp" ,type=org.apache.struts2.dispatcher.ServletRedirectResult.class)
})
public class LoginBackgroundAction extends BaseAction{
	
	@Resource
	private LoginBackgroundBean bean;
	@Resource
	private PlatformBean platformBean;
	private UsersAdmin users;
	private String oldPassword;
	private String newPassword;
	private String newPassword2;
	/**后台管理员登录;
	 * @return "login"
	 */
	public String login(){
		String status = "err";
		try {
			System.out.println("admin-login");
			users = bean.getUsers(users);
			if(!QwyUtil.isNullAndEmpty(users)){
				//登录成功
				System.out.println("管理员登录成功");
				status = "ok";
				getRequest().setAttribute("status", status);
				getRequest().getSession().setAttribute("usersAdmin", users);
				Platform platform=platformBean.getPlatform();
				getRequest().getSession().setAttribute("platform", platform);
				return "loginOk";
			}
			status = "not";
			System.out.println("管理员登录失败"); 
		} catch (Exception e) {
			log.error("操作异常: ",e);
			status = "err";
		}
		getRequest().setAttribute("status", status);
		return "loginFail";
	}
	/**
	 * @author 覃文勇
	 * @createTime 2015-8-13上午11:36:10
	 */	
	public String adminLogin(){
		String json="";
		try {
			System.out.println("admin-login");
			if(QwyUtil.isNullAndEmpty(users)){
				json=QwyUtil.getJSONString("err", "用户名或密码错误");
			}else{
				if(QwyUtil.isNullAndEmpty(users.getUsername())||QwyUtil.isNullAndEmpty(users.getPassword())){
					json=QwyUtil.getJSONString("err", "用户名或密码不能为空");
				}else{
					users=bean.getUsersAdmin(users);
					if(!QwyUtil.isNullAndEmpty(users)){
					if("0".equals(users.getUserStatus())){
						json=QwyUtil.getJSONString("ok", "管理员登录成功");
						//登录成功
						System.out.println("管理员登录成功");
						getRequest().getSession().setAttribute("usersAdmin", users);
						Platform platform=platformBean.getPlatform();
						getRequest().getSession().setAttribute("platform", platform);
					    }else{
						   json=QwyUtil.getJSONString("err", "该用户已被禁用，请联系超级管理员——文勇欧巴");
					         }
					}else{
						json=QwyUtil.getJSONString("err", "用户名或密码错误");
					}
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			log.error("操作异常: ",e);
		}
		try {
			QwyUtil.printJSON(getResponse(), json);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	/**退出登录
	 * @return
	 */
	public String exitLogin(){
		getRequest().getSession().setAttribute("usersAdmin", null);
		return "loginFail";
	}
	
	/**修改登录密码
	 * @return
	 */
	public String mobifyPassword(){
		System.out.println("修改密码");
		String json="";
		try {
			log.info("修改账号密码");
			UsersAdmin users = (UsersAdmin)getRequest().getSession().getAttribute("usersAdmin");
			if(QwyUtil.isNullAndEmpty(users)){
				json = QwyUtil.getJSONString("noLogin", "管理员未登录");
				QwyUtil.printJSON(getResponse(), json);
				//管理员没有登录;
				return null;
			}
			if (!(DESEncrypt.jieMiPassword(users.getPassword())).equals(oldPassword)) {
				System.out.println("原密码错误");
				json = QwyUtil.getJSONString("err", "原密码错误");
			} else if (!newPassword.equals(newPassword2)) {
				json = QwyUtil.getJSONString("err", "两次密码不一致");
			} else if (oldPassword.equals(newPassword)) {
				json = QwyUtil.getJSONString("err", "新旧密码不能相同");
			} else {
				boolean isOk = bean.modifyPassword(users,newPassword2);
				if (isOk) {
					getRequest().getSession().setAttribute("usersLogin", null);
					json = QwyUtil.getJSONString("ok", "密码修改成功,请重新登录");
				} else {
					json = QwyUtil.getJSONString("err", "密码修改失败");
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			QwyUtil.printJSON(getResponse(), json);
		} catch (IOException e) {
			log.error("操作异常: ",e);
		}
		return null;
	}
	
	
	/**获取用户的权限模块;
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String getRolesRight(){
		try {
			UsersAdmin users = (UsersAdmin)getRequest().getSession().getAttribute("usersAdmin");
			ServletContext servletContext = getRequest().getServletContext();
			if(!QwyUtil.isNullAndEmpty(users)){
				//登录成功 
				System.out.println("管理员登录成功");
				//一级菜单;
				List<Modul> firstModul = null;
				//用户拥有的权限;
				List<Modul> userModul = null;
				if(users.getUserType().intValue()==-1){
					List<Modul> listModul = (List<Modul>)servletContext.getAttribute("listModul");
					firstModul = getFirstMenu(listModul);
					userModul = listModul;
				}else{
					
					List<RolesRight> firstRolesRight = getFirstRolesRight(users.getId());
					List<RolesRight> listRolesRight = getRolesRight(users.getId());
					firstModul = packModul(firstRolesRight);
					userModul = packModul(listRolesRight);
				}
				getRequest().setAttribute("firstModul", firstModul);
				getRequest().setAttribute("userModul", userModul);
				/*Map<Integer,List<RolesRight>> map = new HashMap<Integer,List<RolesRight>>();
				for (RolesRight rolesRight : listRolesRight) {
					
					List<RolesRight> newList = null;
					Object obj = map.get(rolesRight.getModulId().intValue());
					if(QwyUtil.isNullAndEmpty(obj)){
						newList = new ArrayList<RolesRight>();
					}else{
						newList = (List<RolesRight>)obj;
					}
					newList.add(rolesRight);
					map.put(rolesRight.getModulId().intValue(), newList);
				}
				getRequest().setAttribute("mapRolesRight", map);*/
				return "loginOk";
			}
			System.out.println("管理员登录失败"); 
		} catch (Exception e) {
			log.error("操作异常: ",e);
		}
		return "loginFail";
	}
	
	@SuppressWarnings("unchecked")
	private List<RolesRight> getRolesRight(Long usersId) {
		ServletContext servletContext = getRequest().getServletContext();
		List<RolesRight> firstRolesRight = (List<RolesRight>) servletContext.getAttribute("listRolesRight");
		if(!QwyUtil.isNullAndEmpty(firstRolesRight)){
			List<RolesRight> newFirstRolesRight = new ArrayList<RolesRight>();
			for (RolesRight rolesRight : firstRolesRight) {
				if(rolesRight.getUsersAdminId().longValue()==usersId)
					newFirstRolesRight.add(rolesRight);
			}
			return newFirstRolesRight;
		}
		return null;
	}
	@SuppressWarnings("unchecked")
	private List<RolesRight> getFirstRolesRight(long usersId){
		ServletContext servletContext = getRequest().getServletContext();
		List<RolesRight> firstRolesRight = (List<RolesRight>) servletContext.getAttribute("firstRolesRight");
		if(!QwyUtil.isNullAndEmpty(firstRolesRight)){
			List<RolesRight> newFirstRolesRight = new ArrayList<RolesRight>();
			for (RolesRight rolesRight : firstRolesRight) {
				if(rolesRight.getUsersAdminId().longValue()==usersId)
					newFirstRolesRight.add(rolesRight);
			}
			return newFirstRolesRight;
		}
		return null;
	}
	
	/**获取用户的模块权限;
	 * @param list
	 * @return
	 */
	public List<Modul> packModul(List<RolesRight> list){
		if(QwyUtil.isNullAndEmpty(list))
			return null;
		List<Modul> newList = new ArrayList<Modul>();
		for (RolesRight rolesRight : list) {
			newList.add(rolesRight.getModul());
		}
		return newList;
	}
	
	/**获取权限的一级菜单
	 * @param list
	 * @return
	 */
	public List<Modul> getFirstMenu(List<Modul> list){
		if(QwyUtil.isNullAndEmpty(list))
			return null;
		List<Modul> newList = new ArrayList<Modul>();
		for (Modul m : list) {
			if(m.getType().equals("1"))
				newList.add(m);
		}
		return newList;
	}
	
	
	
	public UsersAdmin getUsers() {
		return users;
	}
	public void setUsers(UsersAdmin users) {
		this.users = users;
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

	

	
	
}
