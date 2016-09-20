package com.zfgt.admin.product.action;

import java.io.IOException;
import java.net.URLDecoder;
import java.util.List;

import javax.annotation.Resource;

import org.apache.struts2.config.Namespace;
import org.apache.struts2.config.ParentPackage;
import org.apache.struts2.config.Result;
import org.apache.struts2.config.Results;

import com.zfgt.account.bean.UserInfoBean;
import com.zfgt.admin.product.bean.UsersAdminBean;
import com.zfgt.common.action.BaseAction;
import com.zfgt.common.util.PageUtil;
import com.zfgt.common.util.QwyUtil;
import com.zfgt.login.bean.RegisterUserBean;
import com.zfgt.orm.Age;
import com.zfgt.orm.Bank;
import com.zfgt.orm.PlatUser;
import com.zfgt.orm.Region;
import com.zfgt.orm.UserInfoList;
import com.zfgt.orm.Users;
import com.zfgt.orm.UsersAdmin;
import com.zfgt.orm.UsersInfo;
import com.zfgt.orm.UsersStat;

/**后台管理--注册人数;
 * @author qwy
 *
 * @createTime 2015-4-27下午3:51:34
 */
@SuppressWarnings("serial")
@ParentPackage("struts-default")
@Namespace("/Product/Admin")
	@Results({ @Result(name = "usersStats", value = "/Product/Admin/operationManager/UsersStat.jsp"),
			   @Result(name = "platUser", value = "/Product/Admin/operationManager/platUsers.jsp"),
			   @Result(name = "loadUserInfo", value = "/Product/Admin/operationManager/userInfo.jsp"),
			   @Result(name = "loadUserInfo2", value = "/Product/Admin/operationManager/userInfo2.jsp"),
	           @Result(name = "loadProvince", value = "/Product/Admin/operationManager/provinceStatistics.jsp"),
			   @Result(name = "loadCity", value = "/Product/Admin/operationManager/cityStatistics.jsp"),
			   @Result(name = "loadSex", value = "/Product/Admin/operationManager/sexStatistics.jsp"),
			   @Result(name = "loadAge", value = "/Product/Admin/operationManager/usersAge.jsp"),
			   @Result(name = "err", value = "/Product/Admin/err.jsp"),
			   @Result(name = "searchUserInfo", value = "/Product/Admin/operationManager/searchUserInfo.jsp"),
			   @Result(name = "loadBank", value = "/Product/Admin/operationManager/bankStatistics.jsp")
	
})
public class UserStatAction extends BaseAction {
	@Resource
	UserInfoBean bean;
	@Resource
	RegisterUserBean registerUserBean;
	private Integer currentPage = 1;
	private Integer pageSize = 20;
	private String insertTime;
	private String channel;
	private String username;
	private String isbindbank;
	private String province;
	private String registPlatform;
	private String level;
	private String inMoney2;
	private String inMoney1;
	@Resource
	private UsersAdminBean usersAdminBean;

    private String goPage;


	private static String superName="0EA5D6BC23E8EEC78F62546B9F68BABFA96976B775889BA625DB6D764FD0DBD42A1C0F45F85B0DE8";

	/**
	 * 统计注册人数
	 * @return
	 */
	public String loadUsersStat(){
		List<UsersStat> usersStats;
		String json="";
		try {
			UsersAdmin users = (UsersAdmin) getRequest().getSession()
					.getAttribute("usersAdmin");
			if (QwyUtil.isNullAndEmpty(users)) {
				json = QwyUtil.getJSONString("err", "管理员未登录");
				QwyUtil.printJSON(getResponse(), json);
				//管理员没有登录;
				return null;
			}
			if(!superName.equals(users.getUsername())){
			if(isExistsQX("平台注册人数", users.getId())){
				getRequest().setAttribute("err", "您没有操作该功能的权限!");
				return "err";
			}
			}
			usersStats = bean.findUsersCount(insertTime);
			String registAllCount=bean.findAllUsersCount(insertTime);
			getRequest().setAttribute("list", usersStats);
			getRequest().setAttribute("registAllCount",registAllCount);
			getRequest().setAttribute("insertTime", insertTime);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "usersStats";
	}
	
	/**
	 * 以日期为分组注册人数统计
	 * @return
	 */
	public String platUser(){
		String json = "";
		try {
			UsersAdmin users = (UsersAdmin)getRequest().getSession().getAttribute("usersAdmin");
			if(QwyUtil.isNullAndEmpty(users)){
				json = QwyUtil.getJSONString("err", "管理员未登录");
				QwyUtil.printJSON(getResponse(), json);
				//管理员没有登录;
				return null;
			}
			if(!superName.equals(users.getUsername())){
			if(isExistsQX("注册人数日统计", users.getId())){
				getRequest().setAttribute("err", "您没有操作该功能的权限!");
				return "err";
			}
			}
			PageUtil<PlatUser> pageUtil = new PageUtil<PlatUser>();
			pageUtil.setCurrentPage(currentPage);
			pageUtil.setPageSize(31);
			StringBuffer url = new StringBuffer();
			url.append(getRequest().getServletContext().getContextPath());
			url.append("/Product/Admin/userStat!platUser.action?");
			if(!QwyUtil.isNullAndEmpty(insertTime)){
				url.append("&insertTime=");
				url.append(insertTime);
			}
			pageUtil.setPageUrl(url.toString());
			pageUtil = bean.findUsersCountByDate(pageUtil, insertTime);
			getRequest().setAttribute("insertTime", insertTime);
			if(!QwyUtil.isNullAndEmpty(pageUtil)){
				getRequest().setAttribute("pageUtil", pageUtil);
				getRequest().setAttribute("list", pageUtil.getList());
				return "platUser";
			}
		} catch (Exception e) {
			log.error("操作异常: ",e);
			json = QwyUtil.getJSONString("err", "查询记录异常");
		}
		return "platUser";
	}

	/**
	 * 渠道注册人
	 * @return
	 */
	public String loadUserInfo(){
		String json="";
		try {
			UsersAdmin users = (UsersAdmin)getRequest().getSession().getAttribute("usersAdmin");
			if(QwyUtil.isNullAndEmpty(users)){
				json = QwyUtil.getJSONString("err", "管理员未登录");
				QwyUtil.printJSON(getResponse(), json);
				//管理员没有登录;
				return null;
			}
			if(!superName.equals(users.getUsername())){
			if(isExistsQX("注册用户信息", users.getId())){
				getRequest().setAttribute("err", "您没有操作该功能的权限!");
				return "err";
			}
			}
			PageUtil<UserInfoList> pageUtil = new PageUtil<UserInfoList>();
		
			StringBuffer url = new StringBuffer();
			url.append(getRequest().getServletContext().getContextPath());
			url.append("/Product/Admin/userStat!loadUserInfo.action?");
//			if(!QwyUtil.isNullAndEmpty(username)){
//				username=new String (username.getBytes("ISO-8859-1"),"UTF-8");
//			}
			if(!QwyUtil.isNullAndEmpty(channel)){
				url.append("&channel="+channel);
			}
			if(!QwyUtil.isNullAndEmpty(username)){
				url.append("&username="+username);
			}
			if(!QwyUtil.isNullAndEmpty(insertTime)){
				url.append("&insertTime="+insertTime);
			}
			if(!QwyUtil.isNullAndEmpty(isbindbank)){
				url.append("&isbindbank="+isbindbank);
			}
			if(!QwyUtil.isNullAndEmpty(level)){
				url.append("&level="+level);
			}
			if(!QwyUtil.isNullAndEmpty(inMoney1)){
				url.append("&inMoney1="+inMoney1);
			}
			if(!QwyUtil.isNullAndEmpty(inMoney2)){
				url.append("&inMoney2="+inMoney2);
			}
			if(!QwyUtil.isNullAndEmpty(goPage)){
				//url.append("&goPage="+goPage);
				//if(currentPage < Integer.parseInt(goPage))
				currentPage = Integer.parseInt(goPage);
			}
			
			pageUtil.setCurrentPage(currentPage);
			pageUtil.setPageSize(pageSize);
			pageUtil.setPageUrl(url.toString());
			pageUtil=bean.findUsersByChannel(pageUtil, channel, username, insertTime,isbindbank,level,inMoney1,inMoney2);
			getRequest().setAttribute("pageUtil", pageUtil);
			getRequest().setAttribute("channel", channel);
			getRequest().setAttribute("username", username);
			getRequest().setAttribute("level", level);
			getRequest().setAttribute("inMoney1", inMoney1);
			getRequest().setAttribute("inMoney2", inMoney2);
			getRequest().setAttribute("insertTime", insertTime);
			getRequest().setAttribute("isbindbank", isbindbank);
			getRequest().setAttribute("goPage", goPage);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "loadUserInfo";
	}

	
	/**
	 * 根据省份统计人数
	 * @return
	 */
	public String loadProvince(){
		String json="";
		try {
			UsersAdmin users = (UsersAdmin)getRequest().getSession().getAttribute("usersAdmin");
			if(QwyUtil.isNullAndEmpty(users)){
				json = QwyUtil.getJSONString("err", "管理员未登录");
				QwyUtil.printJSON(getResponse(), json);
				//管理员没有登录;
				return null;
			}
			if(!superName.equals(users.getUsername())){
			if(isExistsQX("地域统计", users.getId())){
				getRequest().setAttribute("err", "您没有操作该功能的权限!");
				return "err";
			}
			}
			PageUtil<Region> pageUtil = new PageUtil<Region>();
			
			pageUtil.setCurrentPage(currentPage);
			
			pageUtil.setPageSize(pageSize);			
			
			StringBuffer url = new StringBuffer();
			
			url.append(getRequest().getServletContext().getContextPath());
			
			url.append("/Product/Admin/userStat!loadProvince.action?");

			pageUtil.setPageUrl(url.toString());
			
			pageUtil=bean.loadProvince(pageUtil);
			
			getRequest().setAttribute("totalCount",bean.getOthsers(null, null));
			getRequest().setAttribute("list",pageUtil.getList());
			getRequest().setAttribute("pageUtil", pageUtil);
            
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "loadProvince";
	}
	
	/**
	 * 根据省份下属城市统计人数
	 * @return
	 */
	public String loadCity(){
		try {
			PageUtil<Region> pageUtil = new PageUtil<Region>();
			pageUtil.setCurrentPage(currentPage);
			pageUtil.setPageSize(pageSize);
			//province=new String(province.getBytes("ISO-8859-1"),"UTF-8");//乱码问题
			StringBuffer url = new StringBuffer();
			url.append(getRequest().getServletContext().getContextPath());
			url.append("/Product/Admin/userStat!loadCity.action?");
			if(!QwyUtil.isNullAndEmpty(province)){
				url.append("&province=");
				url.append(province);
			}
			
			pageUtil.setPageUrl(url.toString());
			
			pageUtil=bean.loadCity(province,pageUtil);
			
			getRequest().setAttribute("list",pageUtil.getList());
		   
            getRequest().setAttribute("pageUtil", pageUtil);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "loadCity";
	}

	
	/**
	 * 将归属地、卡类型为空的设值
	 * @return
	 */
	public String setMobileLocation(){
		String json="";
		try {
			if(registerUserBean.updateMobileLocation()){
				json = QwyUtil.getJSONString("ok", "设置成功");
				QwyUtil.printJSON(getResponse(), json);
				return null;
			}else{
				json = QwyUtil.getJSONString("error", "设置失败");
			}
		} catch (Exception e) {
			json = QwyUtil.getJSONString("error", "系统异常");
			e.printStackTrace();
		}
		try {
			QwyUtil.printJSON(getResponse(), json);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 统计注册用户的性别
	 * @return
	 */
	public String loadSex(){
		String json="";
		try {
			 UsersAdmin admin=(UsersAdmin)getRequest().getSession().getAttribute("usersAdmin");
				if(QwyUtil.isNullAndEmpty(admin)){
					json = QwyUtil.getJSONString("err", "管理员未登录");
					QwyUtil.printJSON(getResponse(), json);
					//管理员没有登录;
					return null;
				}
				if(!superName.equals(admin.getUsername())){
				if(isExistsQX("性别统计", admin.getId())){
					getRequest().setAttribute("err", "您没有操作该功能的权限!");
					return "err";
				}
				}
			List<Age> list=bean.loadSex();

            request.setAttribute("list", list);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "loadSex";
	}
	/**
	 * 用户年龄段分布表
	 * @return
	 */
	public String loadAge(){
		String json="";
		try {
			UsersAdmin users = (UsersAdmin)getRequest().getSession().getAttribute("usersAdmin");
			if(QwyUtil.isNullAndEmpty(users)){
				json = QwyUtil.getJSONString("err", "管理员未登录");
				QwyUtil.printJSON(getResponse(), json);
				//管理员没有登录;
				return null;
			}
			if(!superName.equals(users.getUsername())){
			if(isExistsQX("用户年龄分布表", users.getId())){
				getRequest().setAttribute("err", "您没有操作该功能的权限!");
				return "err";
			}
			}
			List<Age> ageList=bean.loadAge(registPlatform);
			request.setAttribute("list", ageList);
			request.setAttribute("registPlatform", registPlatform);
			
            return "loadAge";
					
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "loadAge";
	}
	/**
	 * 查询用户信息
	 * @return
	 */
	public String searchUserInfo(){
		String json="";
		List<Users> list=null;
		try {
			UsersAdmin users = (UsersAdmin)getRequest().getSession().getAttribute("usersAdmin");
			if(QwyUtil.isNullAndEmpty(users)){
				json = QwyUtil.getJSONString("err", "管理员未登录");
				QwyUtil.printJSON(getResponse(), json);
				//管理员没有登录;
				return null;
			}
			if(!superName.equals(users.getUsername())){
			if(isExistsQX("查询用户信息", users.getId())){
				getRequest().setAttribute("err", "您没有操作该功能的权限!");
				return "err";
			}
			}
			if(!QwyUtil.isNullAndEmpty(username)){
				list=bean.searchUsersInfo(username); 
	            if(list!=null){
	            	getRequest().setAttribute("list", list);
	            }				
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "searchUserInfo";
	}
	/**
	 * 银行卡数据统计
	 * @return
	 */
	@SuppressWarnings("null")
	public String loadbankStatistics(){
		String json="";
		try {
			UsersAdmin users = (UsersAdmin)getRequest().getSession().getAttribute("usersAdmin");
			if(QwyUtil.isNullAndEmpty(users)){
				json = QwyUtil.getJSONString("err", "管理员未登录");
				QwyUtil.printJSON(getResponse(), json);
				//管理员没有登录;
				return null;
			}
			if(!superName.equals(users.getUsername())){
			if(isExistsQX("银行卡数据统计", users.getId())){
				getRequest().setAttribute("err", "您没有操作该功能的权限!");
				return "err";
			}
			}
			List<Age> bankList=bean.loadbank();						
			request.setAttribute("list", bankList);

			
            return "loadBank";
					
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			
			log.error("操作异常", e);
		}
		return "loadBank";
	}
	
	/**获取注册用户信息;根据日期.
	 * @return
	 */
	public String getRegister(){
		List<Users> listUsers = usersAdminBean.loadUsersByInsertTime(insertTime);
		getRequest().setAttribute("listUsers", listUsers);;
		return "loadUserInfo2";
	}
	
	public String getInsertTime() {
		return insertTime;
	}
	public void setInsertTime(String insertTime) {
		this.insertTime = insertTime;
	}

	public Integer getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(Integer currentPage) {
		this.currentPage = currentPage;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getIsbindbank() {
		return isbindbank;
	}

	public void setIsbindbank(String isbindbank) {
		this.isbindbank = isbindbank;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getRegistPlatform() {
		return registPlatform;
	}

	public void setRegistPlatform(String registPlatform) {
		this.registPlatform = registPlatform;
	}
	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public String getInMoney2() {
		return inMoney2;
	}

	public void setInMoney2(String inMoney2) {
		this.inMoney2 = inMoney2;
	}

	public String getInMoney1() {
		return inMoney1;
	}

	public void setInMoney1(String inMoney1) {
		this.inMoney1 = inMoney1;
	}
	public String getGoPage() {
	return goPage;
}

public void setGoPage(String goPage) {
	this.goPage = goPage;
}
	
}
