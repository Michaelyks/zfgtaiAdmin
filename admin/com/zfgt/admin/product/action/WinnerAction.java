package com.zfgt.admin.product.action;

import javax.annotation.Resource;

import org.apache.struts2.config.Namespace;
import org.apache.struts2.config.ParentPackage;
import org.apache.struts2.config.Result;
import org.apache.struts2.config.Results;

import com.zfgt.admin.product.bean.WinnerBean;
import com.zfgt.common.action.BaseAction;
import com.zfgt.common.util.PageUtil;
import com.zfgt.common.util.QwyUtil;
import com.zfgt.login.bean.RegisterUserBean;
import com.zfgt.orm.UsersAdmin;
import com.zfgt.orm.Winner;

@SuppressWarnings("serial")
@ParentPackage("struts-default")
@Namespace("/Product/Admin")
@Results({
	@Result(name = "winner", value = "/Product/Admin/operationManager/winner.jsp"),
	
})
public class WinnerAction extends BaseAction  {
	private String username;
	private String insertTime;
	public String getInsertTime() {
		return insertTime;
	}
	public void setInsertTime(String insertTime) {
		this.insertTime = insertTime;
	}

	@Resource
	private WinnerBean bean;
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	
	private PageUtil< Winner> pageUtil;
	private Integer currentPage = 1;
	public Integer getCurrentPage() {
		return currentPage;
	}
	public void setCurrentPage(Integer currentPage) {
		this.currentPage = currentPage;
	}

	private Integer pageSize = 50;
	
	public Integer getPageSize() {
		return pageSize;
	}
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	public String showWinner(){
		request = getRequest();
		UsersAdmin users = (UsersAdmin)getRequest().getSession().getAttribute("usersAdmin");
		if(QwyUtil.isNullAndEmpty(users)){
			return "login";
		}
		
		PageUtil<Winner> pageUtil = new PageUtil<Winner>();
		pageUtil.setCurrentPage(currentPage);
		pageUtil.setPageSize(pageSize);
		
		StringBuffer url = new StringBuffer();
		url.append(getRequest().getServletContext().getContextPath());
		url.append("/Product/Admin/winner!showWinner.action?");
		if(!QwyUtil.isNullAndEmpty(insertTime)){
			url.append("&insertTime=");
			url.append(insertTime);
		}
		if(!QwyUtil.isNullAndEmpty(username)){
			url.append("&username=");
			url.append(username);
		}
		pageUtil.setPageUrl(url.toString());
		pageUtil = bean.loadWinner(username, insertTime, pageUtil);
		getRequest().setAttribute("name", username);
		getRequest().setAttribute("insertTime", insertTime);
		if (!QwyUtil.isNullAndEmpty(pageUtil)) {
			getRequest().setAttribute("pageUtil", pageUtil);
			getRequest().setAttribute("list", pageUtil.getList());
			return "winner";
		}
		
		return null;
		
	}

}
