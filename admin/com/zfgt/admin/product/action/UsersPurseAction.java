package com.zfgt.admin.product.action;

import javax.annotation.Resource;

import org.apache.struts2.config.Namespace;
import org.apache.struts2.config.ParentPackage;
import org.apache.struts2.config.Result;
import org.apache.struts2.config.Results;

import com.zfgt.admin.product.bean.UsersPurseBean;
import com.zfgt.common.action.BaseAction;
import com.zfgt.common.util.PageUtil;
import com.zfgt.common.util.QwyUtil;
import com.zfgt.orm.CoinPurseFundsRecord;
import com.zfgt.orm.MCoinDayDetail;
import com.zfgt.orm.RollOut;
import com.zfgt.orm.RollOutUsers;
import com.zfgt.orm.ShiftTo;
import com.zfgt.orm.UsersAdmin;

/**
 * 用户零钱包 Action
 * @author 覃文勇
 * @createTime 2015-9-22上午10:02:15
 */
@SuppressWarnings("serial")
@ParentPackage("struts-default")
@Namespace("/Product/Admin")

@Results({ 
	@Result(name = "shift_to", value = "/Product/Admin/fundsManager/usersShiftToRecord.jsp"),
	@Result(name = "roll_out", value = "/Product/Admin/fundsManager/usersRollOutRecord.jsp"),
	@Result(name = "login", value = "/Product/loginBackground.jsp" ,type=org.apache.struts2.dispatcher.ServletRedirectResult.class),
	@Result(name = "err", value = "/Product/error.jsp"),
	@Result(name = "usersCoin", value = "/Product/Admin/operationManager/coinPurseUsers.jsp"),
	
})

public class UsersPurseAction  extends BaseAction {
	@Resource
	private UsersPurseBean bean;
	private Integer currentPage = 1;//当前页
	private Integer pageCount;//总页数
	private Integer pageSize = 50;
	private String username;
	private String insertTime;
	
	/**
	 * 用户零钱包资金流水记录	
	 * @return
	 */
	public String UsersCoinPurseFunds(){
		try {
			request = getRequest();
			UsersAdmin users = (UsersAdmin)getRequest().getSession().getAttribute("usersAdmin");
			if(QwyUtil.isNullAndEmpty(users)){
				//管理员没有登录;
				return "login";
			}
			String superName="0EA5D6BC23E8EEC78F62546B9F68BABFA96976B775889BA625DB6D764FD0DBD42A1C0F45F85B0DE8";
			if(!superName.equals(users.getUsername())){
			   if(isExistsQX("零钱包资金流水", users.getId())){
				getRequest().setAttribute("err", "您没有操作该功能的权限!");
				return "err";
			   }
		    }
			PageUtil<CoinPurseFundsRecord> pageUtil = new PageUtil<CoinPurseFundsRecord>(pageSize,currentPage);
			StringBuffer url = new StringBuffer();
			url.append(getRequest().getServletContext().getContextPath());
			url.append("/Product/Admin/usersPurse!UsersCoinPurseFunds.action?");
			if(!QwyUtil.isNullAndEmpty(username)){
				url.append("username=");
				url.append(username);
			}
			if(!QwyUtil.isNullAndEmpty(insertTime)){
				url.append("&insertTime=");
				url.append(insertTime);
			}
			pageUtil.setPageUrl(url.toString());
			bean.LoadCoinPurseFunds(pageUtil,username,insertTime);
			getRequest().setAttribute("insertTime", insertTime);
			if (!QwyUtil.isNullAndEmpty(pageUtil)) {
				request.setAttribute("pageUtil", pageUtil);
			    request.setAttribute("list", pageUtil.getList());
			}
		} catch (Exception e) {
			// TODO: handle exception
			log.error("操作异常",e);
		}
		return null;
	}
	/**
	 * 用户零钱包转入记录
	 * @return
	 */
	public String loadUsersShiftTo(){
		try {
			request = getRequest();
			UsersAdmin users = (UsersAdmin)getRequest().getSession().getAttribute("usersAdmin");
			if(QwyUtil.isNullAndEmpty(users)){
				//管理员没有登录;
				return "login";
			}
			String superName="0EA5D6BC23E8EEC78F62546B9F68BABFA96976B775889BA625DB6D764FD0DBD42A1C0F45F85B0DE8";
			if(!superName.equals(users.getUsername())){
			   if(isExistsQX("零钱包转入记录", users.getId())){
				getRequest().setAttribute("err", "您没有操作该功能的权限!");
				return "err";
			   }
		    }
			PageUtil<ShiftTo> pageUtil = new PageUtil<ShiftTo>(pageSize,currentPage);
			StringBuffer url = new StringBuffer();
			url.append(getRequest().getServletContext().getContextPath());
			url.append("/Product/Admin/usersPurse!loadUsersShiftTo.action?");
			if(!QwyUtil.isNullAndEmpty(username)){
				url.append("username=");
				url.append(username);
			}
			if(!QwyUtil.isNullAndEmpty(insertTime)){
				url.append("&insertTime=");
				url.append(insertTime);
			}
			pageUtil.setPageUrl(url.toString());
			bean.LoadShiftTo(pageUtil,username,insertTime);
			getRequest().setAttribute("insertTime", insertTime);
			if (!QwyUtil.isNullAndEmpty(pageUtil)) {
				request.setAttribute("pageUtil", pageUtil);
			    request.setAttribute("list", pageUtil.getList());
			}
		}catch (Exception e) {
			// TODO: handle exception
			log.error("操作异常: ",e);
		}
		return "shift_to";
	}
	
	
	public String loadUsersCoinPurse(){
		try {
		request = getRequest();
		UsersAdmin users = (UsersAdmin)getRequest().getSession().getAttribute("usersAdmin");
		if(QwyUtil.isNullAndEmpty(users)){
			//管理员没有登录;
			return "login";
		}
	        String username = request.getParameter("username");
	        
	        PageUtil<RollOutUsers> pageUtil=new PageUtil<RollOutUsers>();
			 pageUtil.setCurrentPage(currentPage);
		     pageUtil.setPageSize(pageSize);
		     
		     StringBuffer url = new StringBuffer();
				url.append(getRequest().getServletContext().getContextPath());
				url.append("/Product/Admin/usersPurse!loadUsersCoinPurse.action?");
			
				if(!QwyUtil.isNullAndEmpty(username)){
					url.append("&username=");
					url.append(username);
				}
				pageUtil.setPageUrl(url.toString());
				bean.loadUsersCoinPurse(pageUtil,username);
				if (!QwyUtil.isNullAndEmpty(pageUtil)) {
					request.setAttribute("pageUtil", pageUtil);
					request.setAttribute("usersname", username);
				    request.setAttribute("list", pageUtil.getList());
				}
				return "usersCoin";
		}catch (Exception e) {
			// TODO: handle exception
			log.error("操作异常: ",e);
		}
		return null;
		
	}
	
	/**
	 * 用户零钱包转出记录
	 * @return
	 */
	public String loadUsersRollOut(){
		try {
			request = getRequest();
			UsersAdmin users = (UsersAdmin)getRequest().getSession().getAttribute("usersAdmin");
			if(QwyUtil.isNullAndEmpty(users)){
				//管理员没有登录;
				return "login";
			}
			String superName="0EA5D6BC23E8EEC78F62546B9F68BABFA96976B775889BA625DB6D764FD0DBD42A1C0F45F85B0DE8";
			if(!superName.equals(users.getUsername())){
			   if(isExistsQX("零钱包转出记录", users.getId())){
				getRequest().setAttribute("err", "您没有操作该功能的权限!");
				return "err";
			   }
		    }
			PageUtil<RollOut> pageUtil = new PageUtil<RollOut>(pageSize,currentPage);
			StringBuffer url = new StringBuffer();
			url.append(getRequest().getServletContext().getContextPath());
			url.append("/Product/Admin/usersPurse!loadUsersRollOut.action?");
			if(!QwyUtil.isNullAndEmpty(username)){
				url.append("username=");
				url.append(username);
			}
			if(!QwyUtil.isNullAndEmpty(insertTime)){
				url.append("&insertTime=");
				url.append(insertTime);
			}
			pageUtil.setPageUrl(url.toString());
			bean.LoadRollOut(pageUtil,username,insertTime);
			getRequest().setAttribute("insertTime", insertTime);
			if (!QwyUtil.isNullAndEmpty(pageUtil)) {
				request.setAttribute("pageUtil", pageUtil);
			    request.setAttribute("list", pageUtil.getList());
			}
		}catch (Exception e) {
			// TODO: handle exception
			log.error("操作异常: ",e);
		}
		return "roll_out";
	}
		public Integer getCurrentPage() {
			return currentPage;
		}
		public void setCurrentPage(Integer currentPage) {
			this.currentPage = currentPage;
		}
		public Integer getPageCount() {
			return pageCount;
		}
		public void setPageCount(Integer pageCount) {
			this.pageCount = pageCount;
		}
		public Integer getPageSize() {
			return pageSize;
		}
		public void setPageSize(Integer pageSize) {
			this.pageSize = pageSize;
		}
		public String getUsername() {
			return username;
		}
		public void setUsername(String username) {
			this.username = username;
		}
		public String getInsertTime() {
			return insertTime;
		}
		public void setInsertTime(String insertTime) {
			this.insertTime = insertTime;
		}


}
