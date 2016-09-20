package com.zfgt.admin.product.action;

import java.io.IOException;

import javax.annotation.Resource;

import org.apache.struts2.config.Namespace;
import org.apache.struts2.config.ParentPackage;
import org.apache.struts2.config.Result;
import org.apache.struts2.config.Results;

import com.zfgt.admin.product.bean.CheckTxsqBean;
import com.zfgt.common.action.BaseAction;
import com.zfgt.common.util.PageUtil;
import com.zfgt.common.util.QwyUtil;
import com.zfgt.orm.TxRecord;
import com.zfgt.orm.UsersAdmin;

/**后台管理--审核提现申请;
 * @author qwy
 *
 * @createTime 2015-4-27下午3:51:34
 */
@SuppressWarnings("serial")
@ParentPackage("struts-default")
@Namespace("/Product/Admin")
//发布产品页面
@Results({ @Result(name = "txsq", value = "/Product/Admin/fundsManager/txsq.jsp"),
	       @Result(name = "err", value = "/Product/Admin/err.jsp")
})
public class CheckTxsqAction extends BaseAction {
	
	private PageUtil<TxRecord> pageUtil;
	private Integer currentPage = 1;
	private Integer pageSize = 50;
	private String status = "all";
	private String name="";
	private String insertTime;
	private String txId;
	@Resource
	private CheckTxsqBean bean;
	
	
	/**加载提现申请的用户
	 * @return
	 */
	public String loadTxsq(){
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
				if(isExistsQX("提现记录", users.getId())){
					getRequest().setAttribute("err", "您没有操作该功能的权限!");
					return "err";
				}
			}
			//根据状态来加载提现的记录;
			PageUtil<TxRecord> pageUtil = new PageUtil<TxRecord>();
			pageUtil.setCurrentPage(currentPage);
			pageUtil.setPageSize(pageSize);
			StringBuffer url = new StringBuffer();
			url.append(getRequest().getServletContext().getContextPath());
			url.append("/Product/Admin/checkTxsq!loadTxsq.action?status="
					+ status);
			if (!QwyUtil.isNullAndEmpty(name)) {
				url.append("&name=");
				url.append(name);
			}
			if (!QwyUtil.isNullAndEmpty(insertTime)) {
				url.append("&insertTime=");
				url.append(insertTime);
			}
			pageUtil.setPageUrl(url.toString());
			pageUtil = bean.loadTxRecord(pageUtil, status,  name,insertTime);
			if (!QwyUtil.isNullAndEmpty(pageUtil)) {
				getRequest().setAttribute("pageUtil", pageUtil);
				getRequest().setAttribute("name", name);
				getRequest().setAttribute("insertTime", insertTime);
				getRequest().setAttribute("txRecordList", pageUtil.getList());
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error("操作异常: ",e);
		}
		return "txsq";
	}
	
	/**加载提现申请的用户
	 * @return
	 */
	public String requestTx(){
		String json = "";
		try {
			UsersAdmin users = (UsersAdmin) getRequest().getSession().getAttribute("usersAdmin");
			if (QwyUtil.isNullAndEmpty(users)) {
				json = QwyUtil.getJSONString("err", "管理员未登录");
				QwyUtil.printJSON(getResponse(), json);
				//管理员没有登录;
				return null;
			}
			
			if(bean.requestTx(txId)){
				json = QwyUtil.getJSONString("ok", "请求成功");
				QwyUtil.printJSON(getResponse(), json);
				//管理员没有登录;
				return null;
			}else{
				json = QwyUtil.getJSONString("ok", "请求失败");
				QwyUtil.printJSON(getResponse(), json);
				//管理员没有登录;
				return null;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			log.error("操作异常: ",e);
		}
		json = QwyUtil.getJSONString("error", "失败");
		try {
			QwyUtil.printJSON(getResponse(), json);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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

	public String getTxId() {
		return txId;
	}

	public void setTxId(String txId) {
		this.txId = txId;
	}
	
	

}
