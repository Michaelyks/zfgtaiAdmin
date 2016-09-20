package com.zfgt.admin.product.action;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import net.sf.jasperreports.engine.JasperPrint;

import org.apache.struts2.config.Namespace;
import org.apache.struts2.config.ParentPackage;
import org.apache.struts2.config.Result;
import org.apache.struts2.config.Results;

import com.zfgt.admin.product.bean.IphoneActivityBean;
import com.zfgt.common.action.BaseAction;
import com.zfgt.common.util.PageUtil;
import com.zfgt.common.util.QwyUtil;
import com.zfgt.orm.IphoneOperateDay;
import com.zfgt.orm.Qdtj;
import com.zfgt.orm.UsersAdmin;


/**IOS渠道统计
 * @author 覃文勇
 * @createTime 2016-3-18上午10:38:06
 */
@SuppressWarnings("serial")
@ParentPackage("struts-default")
@Namespace("/Product/Admin")
	//发布产品页面
	@Results({ 
		@Result(name = "activityStats", value = "/Product/Admin/operationManager/ActivityStat.jsp"),
		@Result(name = "loadIphoneQdtj", value = "/Product/Admin/operationManager/iphoneQdtj.jsp"),
		@Result(name = "loadQdcb", value = "/Product/Admin/operationManager/qdcb.jsp"),
		@Result(name = "err", value = "/Product/Admin/err.jsp"),
		@Result(name = "loadIphoneQdtjDetails", value = "/Product/Admin/operationManager/iphoneQdtjDetails.jsp"),
})
public class IphoneActivityAction extends BaseAction {
	@Resource
	IphoneActivityBean bean;
	private String insertTime;
	private String registChannel;
	private Integer currentPage = 1;
	private Integer pageSize = 50;
	
	/**
	 *  渠道统计（IOS）
	 * @return
	 */
	public String loadIphoneQdtj(){
		String json="";
		try {
			UsersAdmin users = (UsersAdmin)getRequest().getSession().getAttribute("usersAdmin");
			if(QwyUtil.isNullAndEmpty(users)){
				json = QwyUtil.getJSONString("err", "管理员未登录");
				QwyUtil.printJSON(getResponse(), json);
				//管理员没有登录;
				return null;
			}
			getRequest().setAttribute("registChannel", registChannel);
			List<Qdtj> list=bean.getIphoneQdtj(registChannel);
			if(!QwyUtil.isNullAndEmpty(list)){
				getRequest().setAttribute("list", list);
				getRequest().setAttribute("tj", bean.getTotal());
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "loadIphoneQdtj";
	}

	
	public String loadIphoneQdtjDetails(){
		String json="";
		try {
			UsersAdmin users = (UsersAdmin)getRequest().getSession().getAttribute("usersAdmin");
			if(QwyUtil.isNullAndEmpty(users)){
				json = QwyUtil.getJSONString("err", "管理员未登录");
				QwyUtil.printJSON(getResponse(), json);
				//管理员没有登录;
				return null;
			}
			PageUtil<IphoneOperateDay> pageUtil = new PageUtil<IphoneOperateDay>();
			pageUtil.setCurrentPage(currentPage);
			pageUtil.setPageSize(pageSize);
			StringBuffer url = new StringBuffer();
			url.append(getRequest().getServletContext().getContextPath());
			url.append("/Product/Admin/activity!loadIphoneQdtjDetails.action?");
			if(!QwyUtil.isNullAndEmpty(insertTime)){
				url.append("&insertTime=");
				url.append(insertTime);
			}
			if(!QwyUtil.isNullAndEmpty(registChannel)){
				url.append("&registChannel=");
				url.append(registChannel);
			}
			pageUtil.setPageUrl(url.toString());
			if("aisi".equals(registChannel)){
				getRequest().setAttribute("channelName", "爱思助手");
			}
			getRequest().setAttribute("insertTime", insertTime);
			getRequest().setAttribute("registChannel", registChannel);
			pageUtil=bean.getIphoneQdtjDetail(pageUtil,registChannel,insertTime);
			if(!QwyUtil.isNullAndEmpty(pageUtil)&&!QwyUtil.isNullAndEmpty(pageUtil.getList())){
				getRequest().setAttribute("pageUtil", pageUtil);
				getRequest().setAttribute("table", "1");
				getRequest().setAttribute("list", pageUtil.getList());
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "loadIphoneQdtjDetails";
	}
	
	/**
	 * 导出单个渠道统计详情（IOS）
	 */
	public String iportIphoneQdtjDetailTable(){
		List<JasperPrint> list = null;
		String name = QwyUtil.fmyyyyMMddHHmmss3.format(new Date())+"_iphoneQdtjDetails";
		try {
			String filePath = request.getServletContext().getRealPath("/WEB-INF/classes/qdtjDetails.jasper");
			System.out.println("iportTable报表路径: "+filePath);
			list=bean.getQdtjDetailJasperPrintList(registChannel, insertTime,filePath);
			doIreport(list, name);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
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

	public String getRegistChannel() {
		return registChannel;
	}

	public void setRegistChannel(String registChannel) {
		this.registChannel = registChannel;
	}


	
	
}
