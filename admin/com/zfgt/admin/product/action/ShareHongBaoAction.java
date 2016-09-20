package com.zfgt.admin.product.action;
import javax.annotation.Resource;

import org.apache.struts2.config.Namespace;
import org.apache.struts2.config.ParentPackage;
import org.apache.struts2.config.Result;
import org.apache.struts2.config.Results;

import com.zfgt.admin.product.bean.ShareHongBaoBean;
import com.zfgt.admin.product.dao.ShareHongBaoDao;
import com.zfgt.common.action.BaseAction;
import com.zfgt.common.util.PageUtil;
import com.zfgt.common.util.QwyUtil;
import com.zfgt.orm.UsersAdmin;
@SuppressWarnings("serial")
@ParentPackage("struts-default")
@Namespace("/Product/Admin")
@Results({
	@Result(name = "show", value = "/activity/jqltzq/shareHongBao.jsp"),
	
})

public class ShareHongBaoAction  extends BaseAction  {
	private Integer currentPage = 1;
	private Integer pageSize = 50;
	private String insertTime;
	private String username;

	@Resource
	private ShareHongBaoBean bean;
	/**
	 * 加载红包列表
	 * @return
	 */
	public String showShareHongBao(){
		try {
			request = getRequest();
			UsersAdmin users = (UsersAdmin)getRequest().getSession().getAttribute("usersAdmin");
			if(QwyUtil.isNullAndEmpty(users)){
				return "login";
			}
			
			PageUtil<ShareHongBaoDao> pageUtil = new PageUtil<ShareHongBaoDao>();
			pageUtil.setCurrentPage(currentPage);
			pageUtil.setPageSize(pageSize);
			
			StringBuffer url = new StringBuffer();
			url.append(getRequest().getServletContext().getContextPath());
			url.append("/Product/Admin/shareHongBao!showShareHongBao.action?");
		   if(!QwyUtil.isNullAndEmpty(username)){
				url.append("&username=");
				url.append(username);
			}
		   if(!QwyUtil.isNullAndEmpty(insertTime)){
				url.append("&insertTime=");
				url.append(insertTime);
			}
			pageUtil.setPageUrl(url.toString());
			bean.loadHongBao(username,  insertTime, pageUtil);
			if (!QwyUtil.isNullAndEmpty(pageUtil)) {
				getRequest().setAttribute("pageUtil", pageUtil);
				getRequest().setAttribute("username", username);
				getRequest().setAttribute("insertTime", insertTime);
				getRequest().setAttribute("all", bean.totalAllOperate(username, insertTime));
			    return "show";
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.error("操作异常: ",e);
		}
			return null;
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
	public String getInsertTime() {
		return insertTime;
	}
	public void setInsertTime(String insertTime) {
		this.insertTime = insertTime;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	
}
