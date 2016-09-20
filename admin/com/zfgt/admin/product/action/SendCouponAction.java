package com.zfgt.admin.product.action;

import java.io.IOException;
import java.util.Date;

import javax.annotation.Resource;

import org.apache.struts2.config.Namespace;
import org.apache.struts2.config.ParentPackage;
import org.apache.struts2.config.Result;
import org.apache.struts2.config.Results;

import com.zfgt.admin.product.bean.SendCouponBean;
import com.zfgt.common.action.BaseAction;
import com.zfgt.common.util.PageUtil;
import com.zfgt.common.util.QwyUtil;
import com.zfgt.login.bean.RegisterUserBean;
import com.zfgt.orm.Coupon;
import com.zfgt.orm.Users;
import com.zfgt.orm.UsersAdmin;
import com.zfgt.orm.UsersInfo;

/**
 * 后天管理--发送红包;
 * 
 * @author qwy
 *
 * @createTime 2015-4-27上午11:44:54
 */
@SuppressWarnings("serial")
@ParentPackage("struts-default")
@Namespace("/Product/Admin")
// 发布产品页面
@Results({ @Result(name = "login", value = "/Product/loginBackground.jsp", type = org.apache.struts2.dispatcher.ServletRedirectResult.class),
		@Result(name = "coupon", value = "/Product/Admin/fundsManager/sendCoupon.jsp"), @Result(name = "couponRecord", value = "/Product/Admin/fundsManager/couponRecord.jsp"),
		@Result(name = "err", value = "/Product/Admin/err.jsp") })
public class SendCouponAction extends BaseAction {
	private Coupon con;
	private String overTime;
	private String useTime = QwyUtil.fmMMddyyyy.format(new Date());
	private String username;
	@Resource
	private SendCouponBean bean;
	private String isBindBank;
	private Integer currentPage = 1;
	private Integer pageSize = 50;
	private String insertTime;
	private String status;
	@Resource
	private RegisterUserBean registerUserBean;

	/**
	 * 管理员发送红包给用户;
	 * 
	 * @return
	 */
	public String sendHongBao() {
		String json = "";
		try {
			UsersAdmin users = (UsersAdmin) getRequest().getSession().getAttribute("usersAdmin");
			if (QwyUtil.isNullAndEmpty(users)) {
				json = QwyUtil.getJSONString("err", "管理员未登录");
				QwyUtil.printJSON(getResponse(), json);
				// 管理员没有登录;
				return null;
			}
			Users us = bean.getUsersByUsername(username);
			if (QwyUtil.isNullAndEmpty(us)) {
				// 没有找到这个用户;
				json = QwyUtil.getJSONString("err", "用户名不存在");
				QwyUtil.printJSON(getResponse(), json);
				return null;
			}
			con.setMoney(QwyUtil.calcNumber(con.getMoney(), 100, "*").doubleValue());
			if (!QwyUtil.isNullAndEmpty(overTime))
				con.setOverTime(QwyUtil.fmyyyyMMddHHmmss.parse(overTime + " 00:00:00"));
			boolean isSend = bean.sendHongBao(us.getId(), con.getMoney(), con.getOverTime(), con.getType(), users.getId(), con.getNote());
			if (isSend) {
				// 发送成功;
				json = QwyUtil.getJSONString("ok", "发放红包成功");
			} else {
				json = QwyUtil.getJSONString("err", "发放红包失败");
			}
		} catch (Exception e) {
			log.error("操作异常: ", e);
			json = QwyUtil.getJSONString("err", "发放红包异常");
		}
		try {
			QwyUtil.printJSON(getResponse(), json);
		} catch (IOException e) {
			log.error("操作异常: ", e);
		}
		return null;
	}

	/**
	 * 投资券记录;
	 * 
	 * @return
	 */
	public String couponRecord() {
		String json = "";
		try {
			UsersAdmin users = (UsersAdmin) getRequest().getSession().getAttribute("usersAdmin");
			if (QwyUtil.isNullAndEmpty(users)) {
				json = QwyUtil.getJSONString("err", "管理员未登录");
				QwyUtil.printJSON(getResponse(), json);
				// 管理员没有登录;
				return "login";
			}
			String superName = "0EA5D6BC23E8EEC78F62546B9F68BABFA96976B775889BA625DB6D764FD0DBD42A1C0F45F85B0DE8";
			if (!superName.equals(users.getUsername())) {
				if (isExistsQX("投资卷记录", users.getId())) {
					getRequest().setAttribute("err", "您没有操作该功能的权限!");
					return "err";
				}
			}
			PageUtil<Coupon> pageUtil = new PageUtil<Coupon>();
			pageUtil.setCurrentPage(currentPage);
			pageUtil.setPageSize(pageSize);
			StringBuffer url = new StringBuffer();
			url.append(getRequest().getServletContext().getContextPath());
			url.append("/Product/Admin/sendCoupon!couponRecord.action?username=" + username);
			if (!QwyUtil.isNullAndEmpty(insertTime)) {
				url.append("&insertTime=");
				url.append(insertTime);
			}
			url.append("&useTime=");
			url.append(useTime);
			pageUtil.setPageUrl(url.toString());
			pageUtil = bean.findCoupons(pageUtil, insertTime, useTime, username, status);
			if (!QwyUtil.isNullAndEmpty(insertTime)) {
				getRequest().setAttribute("insertTime", insertTime);
			}
			if (!QwyUtil.isNullAndEmpty(username)) {
				getRequest().setAttribute("username", username);
			}
			if (!QwyUtil.isNullAndEmpty(useTime)) {
				getRequest().setAttribute("useTime", useTime);
			}
			if (!QwyUtil.isNullAndEmpty(pageUtil)) {
				getRequest().setAttribute("pageUtil", pageUtil);
			}
		} catch (Exception e) {
			log.error("操作异常: ", e);
			json = QwyUtil.getJSONString("err", "投资券记录异常");
		}
		return "couponRecord";
	}

	/**
	 * 管理员发送红包给用户;
	 * 
	 * @return
	 */
	public String sendUnbindHongBao() {
		String json = "";
		try {
			UsersAdmin users = (UsersAdmin) getRequest().getSession().getAttribute("usersAdmin");
			if (QwyUtil.isNullAndEmpty(users)) {
				json = QwyUtil.getJSONString("err", "管理员未登录");
				QwyUtil.printJSON(getResponse(), json);
				// 管理员没有登录;
				return null;
			}
			con.setMoney(QwyUtil.calcNumber(con.getMoney(), 100, "*").doubleValue());
			if (!QwyUtil.isNullAndEmpty(overTime))
				con.setOverTime(QwyUtil.fmyyyyMMddHHmmss.parse(overTime + " 00:00:00"));
			boolean isSend = bean.sendHongBao(isBindBank, con.getMoney(), con.getOverTime(), con.getType(), users.getId(), con.getNote());
			if (isSend) {
				// 发送成功;
				json = QwyUtil.getJSONString("ok", "发放红包成功");
			} else {
				json = QwyUtil.getJSONString("err", "发放红包失败");
			}
		} catch (Exception e) {
			log.error("操作异常: ", e);
			json = QwyUtil.getJSONString("err", "发放红包异常");
		}
		try {
			QwyUtil.printJSON(getResponse(), json);
		} catch (IOException e) {
			log.error("操作异常: ", e);
		}
		return null;
	}

	/**
	 * 获取用户的真实姓名;
	 * 
	 * @return
	 */
	public String getRealNameByUsername() {
		String json = "";
		try {
			UsersAdmin users = (UsersAdmin) getRequest().getSession().getAttribute("usersAdmin");
			if (QwyUtil.isNullAndEmpty(users)) {
				json = QwyUtil.getJSONString("err", "管理员未登录");
				QwyUtil.printJSON(getResponse(), json);
				// 管理员没有登录;
				return null;
			}
			if (QwyUtil.isNullAndEmpty(username)) {
				json = QwyUtil.getJSONString("err", "用户名不能为空");
				QwyUtil.printJSON(getResponse(), json);
				return null;
			}
			Users user = registerUserBean.getUsersByUsername(username);
			if (QwyUtil.isNullAndEmpty(user)) {
				json = QwyUtil.getJSONString("err", "用户名不存在");
				QwyUtil.printJSON(getResponse(), json);
				return null;
			}
			UsersInfo ui = user.getUsersInfo();
			if (QwyUtil.isNullAndEmpty(ui)) {
				json = QwyUtil.getJSONString("err", "用户信息不存在");
				QwyUtil.printJSON(getResponse(), json);
				return null;
			}
			String realName = ui.getRealName();
			json = QwyUtil.getJSONString("ok", realName);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage(), e);
			json = QwyUtil.getJSONString("err", "服务器异常");
		}
		try {
			QwyUtil.printJSON(getResponse(), json);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 群发投资券
	 * 
	 * @return
	 */
	public String sendCouponGroup() {
		String json = "";
		try {
			UsersAdmin admin = (UsersAdmin) getRequest().getSession().getAttribute("usersAdmin");
			if (QwyUtil.isNullAndEmpty(admin)) {
				json = QwyUtil.getJSONString("err", "管理员未登录");
				QwyUtil.printJSON(getResponse(), json);
				// 管理员没有登录;
				return null;
			}
			if (!QwyUtil.isNullAndEmpty(username)) {
				if (!QwyUtil.isNullAndEmpty(con)) {
					if (!QwyUtil.isNullAndEmpty(con.getMoney())) {
						con.setMoney(QwyUtil.calcNumber(con.getMoney(), 100, "*").doubleValue());
						if (!QwyUtil.isNullAndEmpty(overTime)) {
							con.setOverTime(QwyUtil.fmyyyyMMddHHmmss.parse(overTime + " 00:00:00"));
						}
						String[] strArray = null;
						strArray = username.split(",");
						for (int i = 0; i < strArray.length; i++) {
							if (!QwyUtil.verifyPhone(strArray[i].trim())) {
								json = QwyUtil.getJSONString("error", "用户名输入有误：" + strArray[i].trim());
								break;
							} else {
								Users users = bean.getUsersByUsername(strArray[i].trim());
								if (users == null) {
									json = QwyUtil.getJSONString("error", "该用户名不存在：" + strArray[i].trim() + "。之前的用户名已成功发送！！");
									break;
								} else {
									// String note=new String
									// (con.getNote().getBytes("ISO-8859-1"),"UTF-8");
									bean.sendHongBao(users.getId(), con.getMoney(), con.getOverTime(), con.getType(), admin.getId(), con.getNote());
								}
							}
						}
						if ("".equals(json)) {
							json = QwyUtil.getJSONString("ok", "投资券群发成功");
						}
					} else {
						json = QwyUtil.getJSONString("error", "投资券金额不能为空");
					}

				} else {
					json = QwyUtil.getJSONString("error", "投资券不能为空");

				}
			} else {
				json = QwyUtil.getJSONString("error", "用户名不能为空");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			QwyUtil.printJSON(getResponse(), json);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public Coupon getCon() {
		return con;
	}

	public void setCon(Coupon con) {
		this.con = con;
	}

	public String getOverTime() {
		return overTime;
	}

	public void setOverTime(String overTime) {
		this.overTime = overTime;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getIsBindBank() {
		return isBindBank;
	}

	public void setIsBindBank(String isBindBank) {
		this.isBindBank = isBindBank;
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

	public String getUseTime() {
		return useTime;
	}

	public void setUseTime(String useTime) {
		this.useTime = useTime;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
