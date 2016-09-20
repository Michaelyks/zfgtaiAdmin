package com.zfgt.account.action;

import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.apache.struts2.config.Namespace;
import org.apache.struts2.config.ParentPackage;
import org.apache.struts2.config.Result;
import org.apache.struts2.config.Results;

import com.zfgt.account.bean.InvestorsRecordBean;
import com.zfgt.common.util.PageUtil;
import com.zfgt.common.util.QwyUtil;
import com.zfgt.orm.Investors;
import com.zfgt.orm.Users;
import com.zfgt.orm.UsersLogin;
import com.zfgt.product.action.IndexAction;
/**
 * 用户查询用户的投资记录
 * @author zlq
 *@date 2015-4-27
 *
 */
@SuppressWarnings("serial")
@ParentPackage("struts-default")
@Namespace("/Product/User")
//理财产品
@Results({ 
	@Result(name = "investorsRecord", value = "/Product/User/myInvestors.jsp"),
	@Result(name = "myInvestorsRunning", value = "/Product/User/myInvestorsRunning.jsp"),
	@Result(name = "login", value = "/Product/login.jsp")
})
public class InvestorsRecordAction extends IndexAction {
	private static Logger log = Logger.getLogger(InvestorsRecordAction.class);
	@Resource
	private InvestorsRecordBean bean;
	private Integer currentPage = 1;//当前页
	private Integer pageCount;//总页数
	private Integer pageSize = 20;
	/**获取投资记录;
	 * @return
	 */
	public String getInvestorsRecords(){
		log.info("分页获取投资记录");
		try {
			UsersLogin usersLogin=(UsersLogin)getRequest().getSession().getAttribute("usersLogin");
			if(QwyUtil.isNullAndEmpty(usersLogin)){
				return "login";
			}
			String[] status={"1","2","3"};
			String pageUrl = getRequest().getServletContext().getContextPath()+"/Product/User/investorsRecord!getInvestorsRecords.action";
			loadInvestorsByStatus(status, usersLogin.getUsersId(), pageUrl);
		} catch (Exception e) {
			log.error("InvestorsRecord.getInvestorsRecords",e);
			log.error("操作异常: ",e);
		}
		return "investorsRecord";
	}
	
	/**获取用户的投资流水记录;
	 * 没有支付成功的;
	 * @return
	 */
	public String getInvestorsRunningRecords(){
		log.info("分页获取投资记录");
		try {
			UsersLogin usersLogin=(UsersLogin)getRequest().getSession().getAttribute("usersLogin");
			if(QwyUtil.isNullAndEmpty(usersLogin)){
				return "login";
			}
			String[] status={"0"};
			String pageUrl = getRequest().getServletContext().getContextPath()+"/Product/User/investorsRecord!getInvestorsRunningRecords.action";
			loadInvestorsByStatus(status, usersLogin.getUsersId(), pageUrl);
		} catch (Exception e) {
			log.error("InvestorsRecord.getInvestorsRunningRecords",e);
			log.error("操作异常: ",e);
		}
		return "myInvestorsRunning";
	}
	
	/**根据状态来获取投资列表;
	 * @param status 状态;
	 * @param uid 用户id
	 * @param pageUrl 分页的url
	 */
	public void loadInvestorsByStatus(String[] status,long uid,String pageUrl){
		PageUtil<Investors> pageUtil = getInvestorsByPageUtil(currentPage, pageSize, status,uid);
		StringBuffer url = new StringBuffer();
		url.append(pageUrl);
		pageUtil.setPageUrl(url.toString());
		if(!QwyUtil.isNullAndEmpty(pageUtil)){
			List<Investors> investorsList = pageUtil.getList();
			System.out.println(investorsList.size());
			getRequest().setAttribute("investorsList",investorsList);
			getRequest().setAttribute("pageSize",pageSize);
			getRequest().setAttribute("pageCount", pageUtil.getPageCount());
			getRequest().setAttribute("currentPage", currentPage>currentPage?pageUtil.getPageCount():currentPage);
			getRequest().setAttribute("pageUtil", pageUtil);		
		}
	}
	
	/**根据分页对象获取投资记录
	 * @param currentPage 当前页数
	 * @param pageSize 显示条数
	 * @param status 查询状态
	 * @return
	 */
	public PageUtil<Investors> getInvestorsByPageUtil(int currentPage,int pageSize,String[] status,long uid){
		PageUtil<Investors> pageUtil = new PageUtil<Investors>();
		pageUtil.setCurrentPage(currentPage);
		pageUtil.setPageSize(pageSize);
		pageUtil = bean.getInvestorsByPageUtil(pageUtil,status,uid);
		return pageUtil;
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

	public Integer getPageCount() {
		return pageCount;
	}

	public void setPageCount(Integer pageCount) {
		this.pageCount = pageCount;
	}

	
	
}
