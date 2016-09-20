package com.zfgt.admin.product.action;

import javax.annotation.Resource;

import org.apache.struts2.config.Namespace;
import org.apache.struts2.config.ParentPackage;
import org.apache.struts2.config.Result;
import org.apache.struts2.config.Results;

import com.zfgt.account.bean.FundRecordBean;
import com.zfgt.common.action.BaseAction;
import com.zfgt.common.util.PageUtil;
import com.zfgt.common.util.QwyUtil;
import com.zfgt.orm.FundRecord;
import com.zfgt.orm.TxRecord;
import com.zfgt.orm.Users;
import com.zfgt.orm.UsersAdmin;
@SuppressWarnings("serial")
@ParentPackage("struts-default")
@Namespace("/Product/Admin")

@Results({ @Result(name = "zjls", value = "/Product/Admin/fundsManager/fundRecord.jsp"),
	       @Result(name = "err", value = "/Product/Admin/err.jsp")
})
public class FundRecordAction extends BaseAction {
	private PageUtil<FundRecord> pageUtil;
	private Integer currentPage = 1;
	private Integer pageSize = 20;
	private String status = "all";
	private String name="";
	private String insertTime;
	


	@Resource
	private FundRecordBean bean;
	

	public String loadFund(){

		try {
			//根据状态来加载提现的记录;
			PageUtil<FundRecord> pageUtil = new PageUtil<FundRecord>();

			pageUtil.setCurrentPage(currentPage);
			pageUtil.setPageSize(pageSize);		
			
			StringBuffer url = new StringBuffer();
			url.append(getRequest().getServletContext().getContextPath());
			url.append("/Product/Admin/fundRecord!loadFund.action?");
			if (!QwyUtil.isNullAndEmpty(name)) {
				url.append("&name=");
				url.append(name);
			}
			
			pageUtil.setPageUrl(url.toString());
			pageUtil = bean.loadFundRecord(name, null, null,null,null,pageUtil);
			if (!QwyUtil.isNullAndEmpty(pageUtil)) {
				getRequest().setAttribute("pageUtil", pageUtil);
				getRequest().setAttribute("fundRecordList", pageUtil.getList());
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error("操作异常: ",e);
		}
		return "zjls";
	
	}

	
	
	public String queryFundRecord(){
        String json="";
		try {
			UsersAdmin users = (UsersAdmin)getRequest().getSession().getAttribute("usersAdmin");
			if(QwyUtil.isNullAndEmpty(users)){
				json = QwyUtil.getJSONString("err", "管理员未登录");
				QwyUtil.printJSON(getResponse(), json);
				//管理员没有登录;
				return null;
			}
			String superName="0EA5D6BC23E8EEC78F62546B9F68BABFA96976B775889BA625DB6D764FD0DBD42A1C0F45F85B0DE8";
			if(!superName.equals(users.getUsername())){
			if(isExistsQX("用户资金流水", users.getId())){
				getRequest().setAttribute("err", "您没有操作该功能的权限!");
				return "err";
			}
			}
			//根据状态来加载提现的记录;
			PageUtil<FundRecord> pageUtil = new PageUtil<FundRecord>();
			pageUtil.setCurrentPage(currentPage);
			pageUtil.setPageSize(pageSize);
			StringBuffer url = new StringBuffer();
			url.append(getRequest().getServletContext().getContextPath());
			url.append("/Product/Admin/fundRecord!queryFundRecord.action?");
			if (!QwyUtil.isNullAndEmpty(name)) {
				url.append("&name=");
				url.append(name);
			}
			if (!QwyUtil.isNullAndEmpty(insertTime)) {
				url.append("&insertTime=");
				url.append(insertTime);
			}
			pageUtil.setPageUrl(url.toString());
			pageUtil = bean.loadFundRecord(name, null,  null,null,null,pageUtil);
			if (!QwyUtil.isNullAndEmpty(pageUtil)) {
				getRequest().setAttribute("pageUtil", pageUtil);
				getRequest().setAttribute("name", name);
				getRequest().setAttribute("insertTime", insertTime);
				getRequest().setAttribute("fundRecordList", pageUtil.getList());
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error("操作异常: ",e);
		}
		return "zjls";
	
	}


	public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
	}


	public FundRecordBean getBean() {
		return bean;
	}


	public void setBean(FundRecordBean bean) {
		this.bean = bean;
	}
	
	public PageUtil<FundRecord> getPageUtil() {
		return pageUtil;
	}


	public void setPageUtil(PageUtil<FundRecord> pageUtil) {
		this.pageUtil = pageUtil;
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
	
	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getInsertTime() {
		return insertTime;
	}


	public void setInsertTime(String insertTime) {
		this.insertTime = insertTime;
	}

	
}
