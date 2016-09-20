package com.zfgt.admin.webView.action;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.apache.struts2.config.Namespace;
import org.apache.struts2.config.ParentPackage;
import org.apache.struts2.config.Result;
import org.apache.struts2.config.Results;

import com.zfgt.admin.webView.bean.StatisticsBean;
import com.zfgt.common.util.PageUtil;
import com.zfgt.common.util.QwyUtil;
import com.zfgt.orm.CoinPurseProfit;
import com.zfgt.orm.UsersAdmin;
import com.zfgt.product.action.IndexAction;

/**
 * 曲线统计图
 * @author oi
 *
 */
@ParentPackage("struts-default")
@Namespace("/Product")
@Results({ 
	@Result(name = "userProfit",value = "/Product/Admin/operationManager/profitDiagrams.jsp"),
})
public class StatisticsAction extends IndexAction{
	
	private static final long serialVersionUID = -4826001872012106340L;
	private static Logger log = Logger.getLogger(StatisticsAction.class);
	@Resource StatisticsBean bean;
	
	private String usersId;
	private String insertTime;
	private Integer currentPage = 1;


	private Integer pageSize = 20;

	public String queryAllUserProfit(){
		String json = "";
		try{
			request = getRequest();
			UsersAdmin users = (UsersAdmin)getRequest().getSession().getAttribute("usersAdmin");
			if(QwyUtil.isNullAndEmpty(users)){
				return "login";
			}
			if(usersId!=null && !usersId.equals("usersId")){
				PageUtil<CoinPurseProfit> pageUtil = new PageUtil<CoinPurseProfit>();
				pageUtil.setCurrentPage(currentPage);
				pageUtil.setPageSize(pageSize);
				StringBuffer url = new StringBuffer();
				url.append(getRequest().getServletContext().getContextPath());
				url.append("/Product/statistics!queryAllUserProfit.action?");
				if(!QwyUtil.isNullAndEmpty(insertTime)){
					url.append("&insertTime=");
					url.append(insertTime);
				}
				//if(!QwyUtil.isNullAndEmpty(username)){
					url.append("&usersId=");
					url.append(usersId);
				//}
				pageUtil.setPageUrl(url.toString());
				
				pageUtil = bean.queryAllUserProfit(Long.valueOf(usersId),insertTime,pageUtil);
				getRequest().setAttribute("usersId", usersId);
				getRequest().setAttribute("insertTime", insertTime);
				if (!QwyUtil.isNullAndEmpty(pageUtil)) {
					getRequest().setAttribute("pageUtil", pageUtil);
					getRequest().setAttribute("list", pageUtil.getList());
					getRequest().setAttribute("quertProfit", bean.quertProfit(Long.valueOf(usersId)));
					
				}
			}else {
				json=QwyUtil.getJSONString("noLogin", "请先登陆");
				QwyUtil.printJSON(response, json);
			}
		}catch (Exception e) {
			log.error("操作异常: ",e);
		}
		return "userProfit";
	}

	/**
	 * 查询用户七天收益
	 * @return
	 */
	public String queryUserProfit(){
		String json = "";
		try{
			if(usersId!=null && !usersId.equals("usersId")){
				json = bean.queryUserProfit(Long.valueOf(usersId));
				QwyUtil.printJSON(response, json);
			}else {
				json=QwyUtil.getJSONString("noLogin", "请先登陆");
				QwyUtil.printJSON(response, json);
			}
		}catch (Exception e) {
			log.error("操作异常: ",e);
		}
		return null;
	}

	public String getUsersId() {
		return usersId;
	}

	public void setUsersId(String usersId) {
		this.usersId = usersId;
	}
	public String getInsertTime() {
		return insertTime;
	}
	public void setInsertTime(String insertTime) {
		this.insertTime = insertTime;
	}	public Integer getCurrentPage() {
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
}
