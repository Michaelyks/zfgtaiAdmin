package com.zfgt.admin.product.action;

import java.text.ParseException;

import javax.annotation.Resource;

import org.apache.struts2.config.Namespace;
import org.apache.struts2.config.ParentPackage;
import org.apache.struts2.config.Result;
import org.apache.struts2.config.Results;

import com.sun.org.apache.bcel.internal.generic.GETSTATIC;
import com.zfgt.admin.product.bean.ZeroPurseBean;
import com.zfgt.common.action.BaseAction;
import com.zfgt.common.util.PageUtil;
import com.zfgt.common.util.QwyUtil;
import com.zfgt.orm.UsersAdmin;
import com.zfgt.orm.ZeroPurse;

@SuppressWarnings("serial")
@ParentPackage("struts-default")
@Namespace("/Product/Admin")
@Results({
	@Result(name = "zeroPurse", value = "/Product/Admin/operationManager/zeroPurse.jsp"),
	
})
public class ZeroPurseAction extends BaseAction {
	@Resource
	private ZeroPurseBean bean;
	
	private String username;
	private PageUtil<ZeroPurse> pageUtil;
	private String usersId = "";
	private Integer currentPage = 1;
	private Integer pageSize = 20;
	
	private String bankAccount;
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getUsersId() {
		return usersId;
	}
	public void setUsersId(String usersId) {
		this.usersId = usersId;
	}
	public Integer getCurrentPage() {
		return currentPage;
	}
	public void setCurrentPage(Integer currentPage) {
		this.currentPage = currentPage;
	}
	public String getBankAccount() {
		return bankAccount;
	}
	public void setBankAccount(String bankAccount) {
		this.bankAccount = bankAccount;
	}
	public Integer getPageSize() {
		return pageSize;
	}
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	
	public String zeroPurseId(){
		try{

		String userId = bean.loadZeroPurseId(username,usersId);
		if (!QwyUtil.isNullAndEmpty(userId)) {
			QwyUtil.printJSON(response, userId);
		}
	//	getRequest().setAttribute("zeroUserId", usersId);
		//getRequest().setAttribute("username", username);
		}catch (Exception e) {
			log.error("操作异常: ",e);
		}
		return null;
	}
	
	public String showZeroPurse(){
		try {
		request = getRequest();
		UsersAdmin users = (UsersAdmin)getRequest().getSession().getAttribute("usersAdmin");
		if(QwyUtil.isNullAndEmpty(users)){
			return "login";
		}
		
		PageUtil<ZeroPurse> pageUtil = new PageUtil<ZeroPurse>();
		pageUtil.setCurrentPage(currentPage);
		pageUtil.setPageSize(pageSize);
		StringBuffer url = new StringBuffer();
		url.append(getRequest().getServletContext().getContextPath());
		url.append("/Product/Admin/zeroPurse!showZeroPurse.action?");
		if(!QwyUtil.isNullAndEmpty(username)){
			url.append("&username=");
			url.append(username);
		}
		if(!QwyUtil.isNullAndEmpty(usersId)){
			url.append("&usersId=");
			url.append(usersId);
		}
		if(!QwyUtil.isNullAndEmpty(bankAccount)){
			url.append("&bankAccount=");
			url.append(bankAccount);
		}
		
		pageUtil.setPageUrl(url.toString());
		pageUtil = bean.loadZeroPurse(username, usersId,bankAccount, pageUtil);
		getRequest().setAttribute("bankAccount", bankAccount);
		getRequest().setAttribute("username", username);
		getRequest().setAttribute("usersId", usersId);
		if (!QwyUtil.isNullAndEmpty(pageUtil)) {
			getRequest().setAttribute("pageUtil", pageUtil);
			getRequest().setAttribute("list", pageUtil.getList());
			
			return "zeroPurse";
		}
	} catch (Exception e) {
		// TODO Auto-generated catch block
		log.error("操作异常: ",e);
	}
		return null;
		
	}
}
