package com.zfgt.admin.product.action;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import net.sf.jasperreports.engine.JasperPrint;

import org.apache.log4j.Logger;
import org.apache.struts2.config.Namespace;
import org.apache.struts2.config.ParentPackage;
import org.apache.struts2.config.Result;
import org.apache.struts2.config.Results;

import com.zfgt.admin.product.bean.InterestDetailsBean;
import com.zfgt.common.action.BaseAction;
import com.zfgt.common.util.PageUtil;
import com.zfgt.common.util.QwyUtil;
import com.zfgt.orm.InterestDetails;
import com.zfgt.orm.UsersAdmin;

@SuppressWarnings("serial")
@ParentPackage("struts-default")
@Namespace("/Product/Admin")
// 产品结算记录
@Results({
		@Result(name = "interestDetailsRecord", value = "/Product/Admin/productManager/ClearingDetails.jsp"),
		@Result(name = "findInvertorsByProduct", value = "/Product/Admin/productManager/fxmx.jsp"),
		@Result(name = "err", value = "/Product/Admin/err.jsp")
})
public class InterestDetailsAction extends BaseAction{
	
	@Resource
	private InterestDetailsBean interestDetailsBean;

	private InterestDetails interestDetails;
	private Integer currentPage = 1;
	private Integer pageSize = 20;
	private String productId;
	private String username;
	private String finishTime;
	protected static Logger log = Logger.getLogger(BaseAction.class);
	
	
	/**
	 * 结算详单
	 * @return
	 */
	public String findInvertors(){
		String json = "";
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
			if(isExistsQX("结算详单", users.getId())){
				getRequest().setAttribute("err", "您没有操作该功能的权限!");
				return "err";
			}
			}
			//根据状态来加载提现的记录;
			PageUtil<InterestDetails> pageUtil = new PageUtil<InterestDetails>();
			pageUtil.setCurrentPage(currentPage);
			pageUtil.setPageSize(pageSize);
			StringBuffer url = new StringBuffer();
			url.append(getRequest().getServletContext().getContextPath());
			url.append("/Product/Admin/interestDetails!findInvertors.action?");
			pageUtil.setPageUrl(url.toString());
			pageUtil = interestDetailsBean.finInterestDetailses(pageUtil, null, "3");
			if(!QwyUtil.isNullAndEmpty(pageUtil)){
				getRequest().setAttribute("pageUtil", pageUtil);
				return "interestDetailsRecord";
			}
		} catch (Exception e) {
			log.error("操作异常: ",e);
			json = QwyUtil.getJSONString("err", "查询记录异常");
		}
		return null;
	}
	
	
	/**
	 * 利息明细
	 * @return
	 */
	public String findInvertorsByProduct(){
		String json = "";
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
			if(isExistsQX("付息明细表", users.getId())){
				getRequest().setAttribute("err", "您没有操作该功能的权限!");
				return "err";
			}
			}
			//根据状态来加载提现的记录;
			PageUtil<InterestDetails> pageUtil = new PageUtil<InterestDetails>();
			pageUtil.setCurrentPage(currentPage);
			pageUtil.setPageSize(pageSize);
			StringBuffer url = new StringBuffer();
			url.append(getRequest().getServletContext().getContextPath());
			url.append("/Product/Admin/interestDetails!findInvertorsByProduct.action?");
			if(!QwyUtil.isNullAndEmpty(username)){
				url.append("&username="+username);
			}
			if(!QwyUtil.isNullAndEmpty(productId)){
				url.append("&productId="+productId);
			}
			if(!QwyUtil.isNullAndEmpty(finishTime)){
				url.append("&finishTime="+finishTime);
			}
			pageUtil.setPageUrl(url.toString());
			getRequest().setAttribute("username", username);
			getRequest().setAttribute("productId", productId);
			getRequest().setAttribute("finishTime", finishTime);
			pageUtil = interestDetailsBean.finInterestDetailses(pageUtil, username, productId,finishTime);
			if(!QwyUtil.isNullAndEmpty(pageUtil)){
				getRequest().setAttribute("pageUtil", pageUtil);
				return "findInvertorsByProduct";
			}
		} catch (Exception e) {
			log.error("操作异常: ",e);
			json = QwyUtil.getJSONString("err", "查询记录异常");
		}
		return "findInvertorsByProduct";
	}

	
	/**
	 * 导出产品表格
	 */
	public String iportFXMXTable(){
		List<JasperPrint> list = null;
		String name = QwyUtil.fmyyyyMMddHHmmss3.format(new Date())+"_product";
		try {
			String filePath = request.getServletContext().getRealPath("/WEB-INF/classes/fxmx.jasper");
			System.out.println("iportTable报表路径: "+filePath);
			list=interestDetailsBean.getFXMXJasperPrintList(username, productId,finishTime,filePath);
			doIreport(list, name);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public InterestDetails getInterestDetails() {
		return interestDetails;
	}

	public void setInterestDetails(InterestDetails interestDetails) {
		this.interestDetails = interestDetails;
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


	public String getProductId() {
		return productId;
	}


	public void setProductId(String productId) {
		this.productId = productId;
	}


	public String getUsername() {
		return username;
	}


	public void setUsername(String username) {
		this.username = username;
	}


	public String getFinishTime() {
		return finishTime;
	}


	public void setFinishTime(String finishTime) {
		this.finishTime = finishTime;
	}
	
}
