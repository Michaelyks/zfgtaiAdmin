package com.zfgt.admin.product.action;

import java.io.IOException;

import javax.annotation.Resource;

import org.apache.struts2.config.Namespace;
import org.apache.struts2.config.ParentPackage;
import org.apache.struts2.config.Result;
import org.apache.struts2.config.Results;

import com.zfgt.admin.product.bean.ReflectBean;
import com.zfgt.common.action.BaseAction;
import com.zfgt.common.util.PageUtil;
import com.zfgt.common.util.QwyUtil;
import com.zfgt.orm.Reflect;
import com.zfgt.orm.TxRecord;
import com.zfgt.orm.Users;
import com.zfgt.orm.UsersAdmin;
import com.zfgt.orm.WithdrawLog;

@ParentPackage("struts-default")
@Namespace("/Product/Admin")
@Results({ @Result(name = "list", value = "/Product/Admin/fundsManager/withdrawLogList.jsp") })
public class ReflectAction extends BaseAction {
	@Resource
	private ReflectBean bean;

	private Reflect reflect;

	private Integer currentPage = 1;
	private Integer pageSize = 20;

	private String name = "";

	private String insertTime;

	public String loadTxRecord() {
		String json = "";
		try {
			UsersAdmin usersAdmin = (UsersAdmin) getRequest().getSession()
					.getAttribute("usersAdmin");
			if (QwyUtil.isNullAndEmpty(usersAdmin)) {
				json = QwyUtil.getJSONString("err", "管理员未登录");
				QwyUtil.printJSON(getResponse(), json);
				// 管理员没有登录;
				return null;
			}
			Users users = null;
			boolean isSu = false;
			users = bean.getUsersByUsername(reflect.getUsername());
			TxRecord record = bean.findTxRecord(reflect.getOrderId());
			if(!QwyUtil.isNullAndEmpty(record)){
				json = QwyUtil.getJSONString("error", "商业订单号已存在");
				QwyUtil.printJSON(getResponse(), json);
				return null;
			}
			TxRecord tx = bean.findTxRecord(reflect.getRequestId());			
			if (!QwyUtil.isNullAndEmpty(users) && !QwyUtil.isNullAndEmpty(tx)) {
				if (users.getId().equals(tx.getUsersId())) {
						isSu = bean.updateTx(tx, reflect);
					if (isSu) {
						bean.addLog(usersAdmin.getId(), reflect, tx);
						json = QwyUtil.getJSONString("ok", "修改成功");
					}
				}else{
					json = QwyUtil.getJSONString("error", "帐号和原订单号不匹配");
				}

			}

			else {
				json = QwyUtil.getJSONString("error", "帐号或者原订单号不存在");
			}
		} catch (Exception e) {
			log.error("操作异常: ", e);
			json = QwyUtil.getJSONString("error", "修改错误");
		}
		try {
			QwyUtil.printJSON(getResponse(), json);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public String  loadWithdrawLog() {
		String json = "";
		try {
			UsersAdmin admin = (UsersAdmin) getRequest().getSession()
					.getAttribute("usersAdmin");
			if (QwyUtil.isNullAndEmpty(admin)) {
				json = QwyUtil.getJSONString("err", "管理员未登录");
				QwyUtil.printJSON(getResponse(), json);
				// 管理员没有登录;
				return null;
			}
			PageUtil<WithdrawLog> pageUtil = new PageUtil<WithdrawLog>();
			pageUtil.setCurrentPage(currentPage);
			pageUtil.setPageSize(pageSize);
			StringBuffer url = new StringBuffer();
			url.append(getRequest().getServletContext().getContextPath());
			url.append("/Product/Admin/reflect!loadWithdrawLog.action?");
			if (!QwyUtil.isNullAndEmpty(name)) {
				url.append("&name=");
				url.append(name);
			}
			if (!QwyUtil.isNullAndEmpty(insertTime)) {
				url.append("&insertTime=");
				url.append(insertTime);
			}
			pageUtil.setPageUrl(url.toString());
			pageUtil=bean.findList(pageUtil, name, insertTime);
			if (!QwyUtil.isNullAndEmpty(pageUtil)) {
				getRequest().setAttribute("pageUtil", pageUtil);
				getRequest().setAttribute("name", name);
				getRequest().setAttribute("insertTime", insertTime);
				getRequest().setAttribute("list", pageUtil.getList());
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			log.error("操作异常: ",e);
		}
		return "list";
	}

	public Reflect getReflect() {
		return reflect;
	}

	public void setReflect(Reflect reflect) {
		this.reflect = reflect;
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

}
