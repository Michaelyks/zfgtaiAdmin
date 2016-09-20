package com.zfgt.admin.product.action;

import javax.annotation.Resource;

import org.apache.struts2.config.Namespace;
import org.apache.struts2.config.ParentPackage;
import org.apache.struts2.config.Result;
import org.apache.struts2.config.Results;

import com.zfgt.admin.product.bean.CZProceduresBean;
import com.zfgt.admin.product.dao.CZProcedures;
import com.zfgt.common.action.BaseAction;
import com.zfgt.common.util.PageUtil;
import com.zfgt.common.util.QwyUtil;
import com.zfgt.orm.UsersAdmin;


@SuppressWarnings("serial")
@ParentPackage("struts-default")
@Namespace("/Product/Admin")
@Results({
	@Result(name = "czProcedures", value = "/Product/Admin/operationManager/czProcedures.jsp")
	
})

public class CZProceduresAction extends BaseAction {
	private String insertTime;
	private Integer pageSize = 31;
	public Integer getPageSize() {
		return pageSize;
	}
	private Integer currentPage = 1;
	public Integer getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(Integer currentPage) {
		this.currentPage = currentPage;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	@Resource
	private CZProceduresBean bean;
	private PageUtil< CZProcedures> pageUtil;
	public String showCZProcedures(){
		request = getRequest();
	
		UsersAdmin users = (UsersAdmin)getRequest().getSession().getAttribute("usersAdmin");
		if(QwyUtil.isNullAndEmpty(users)){
			return "login";
		}
		PageUtil<CZProcedures> pageUtil = new PageUtil<CZProcedures>();
		pageUtil.setCurrentPage(currentPage);
		pageUtil.setPageSize(pageSize);
		StringBuffer url = new StringBuffer();
		url.append(getRequest().getServletContext().getContextPath());
		url.append("/Product/Admin/cZProcedures!showCZProcedures.action?");
		if(!QwyUtil.isNullAndEmpty(insertTime)){
			url.append("&insertTime=");
			url.append(insertTime);
		}
		pageUtil.setPageUrl(url.toString());
		pageUtil = bean.loadCZProcedures(insertTime,pageUtil);
		getRequest().setAttribute("insertTime", insertTime);
		if (!QwyUtil.isNullAndEmpty(pageUtil)) {
			getRequest().setAttribute("pageUtil", pageUtil);
			getRequest().setAttribute("list", pageUtil.getList());
			return "czProcedures";
			}
		return null;
	}

	public String getInsertTime() {
		return insertTime;
	}

	public void setInsertTime(String insertTime) {
		this.insertTime = insertTime;
	}
}
