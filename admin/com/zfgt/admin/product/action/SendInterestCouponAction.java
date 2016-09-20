package com.zfgt.admin.product.action;

import java.io.IOException;
import java.util.Date;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.config.Namespace;
import org.apache.struts2.config.ParentPackage;
import org.apache.struts2.config.Result;
import org.apache.struts2.config.Results;

import com.zfgt.admin.product.bean.SendInterestCouponBean;
import com.zfgt.common.action.BaseAction;
import com.zfgt.common.util.PageUtil;
import com.zfgt.common.util.QwyUtil;
import com.zfgt.login.bean.RegisterUserBean;
import com.zfgt.orm.InterestCoupon;
import com.zfgt.orm.Users;
import com.zfgt.orm.UsersAdmin;

/**
 * 加息券后台管理
 * 
 * @author ChenJinHao
 *
 */
@SuppressWarnings("serial")
@ParentPackage("struts-default")
@Namespace("/Product/Admin")
// 发布产品页面
@Results({ @Result(name = "login", value = "/Product/loginBackground.jsp", type = org.apache.struts2.dispatcher.ServletRedirectResult.class),
		@Result(name = "coupon", value = "/Product/Admin/fundsManager/sendInterestCoupon.jsp"), @Result(name = "interestCouponRecord", value = "/Product/Admin/fundsManager/interestCouponRecord.jsp"),
		@Result(name = "err", value = "/Product/Admin/err.jsp") })
public class SendInterestCouponAction extends BaseAction {

	protected static Logger log = Logger.getLogger(SendInterestCouponAction.class);

	@Resource
	private SendInterestCouponBean bean;

	@Resource
	RegisterUserBean registerUserBean;

	private Map<String, String> paramMap;

	private int currentPage = 1;

	private int pageSize = 100;

	private String useTime;
	private String username;
	private String insertTime;
	private String status;

	/**
	 * 管理员发送红包给用户;
	 * 
	 * @return
	 */
	public String sendInterestCoupon() {
		String json = "";
		try {
			UsersAdmin usersAdmin = (UsersAdmin) getRequest().getSession().getAttribute("usersAdmin");
			if (QwyUtil.isNullAndEmpty(usersAdmin)) {
				json = QwyUtil.getJSONString("err", "管理员未登录");
				QwyUtil.printJSON(getResponse(), json);
				// 管理员没有登录;
				return null;
			}

			String userName = paramMap.get("userName");
			Double interestRate = QwyUtil.converStrToDouble(paramMap.get("interestRate"), null);
			String overTimeStr = paramMap.get("overTime");
			Integer couponType = QwyUtil.converStrToInt(paramMap.get("type"), null);
			String note = paramMap.get("note");
			int isAlways = 0;

			if (StringUtils.isBlank(userName)) {
				json = QwyUtil.getJSONString("err", "用户名未填写");
				QwyUtil.printJSON(getResponse(), json);
				return null;
			}

			Users us = registerUserBean.getUsersByUsername(userName);
			if (QwyUtil.isNullAndEmpty(us)) {
				// 没有找到这个用户;
				json = QwyUtil.getJSONString("err", "用户名不存在");
				QwyUtil.printJSON(getResponse(), json);
				return null;
			}

			if (interestRate == null) {
				json = QwyUtil.getJSONString("err", "加息率未填写");
				QwyUtil.printJSON(getResponse(), json);
				return null;
			} else if (interestRate <= 0 || interestRate > 3) {
				json = QwyUtil.getJSONString("err", "加息率的范围为0.1%-3%");
				QwyUtil.printJSON(getResponse(), json);
				return null;
			} else if (Double.compare(((Long) ((Double) (interestRate * 10)).longValue()).doubleValue() / 10, interestRate) != 0) {
				json = QwyUtil.getJSONString("err", "加息率的单位为0.1%");
				QwyUtil.printJSON(getResponse(), json);
				return null;
			}

			Date overTime = null;
			if (StringUtils.isBlank(overTimeStr)) {
				isAlways = 1;
			} else {
				overTime = QwyUtil.fmyyyyMMddHHmmss.parse(overTimeStr + " 00:00:00");
			}

			if (couponType == null) {
				json = QwyUtil.getJSONString("err", "类型未选择");
				QwyUtil.printJSON(getResponse(), json);
				return null;
			}

			InterestCoupon interestCoupon = new InterestCoupon(us.getId(), couponType, interestRate, overTime, isAlways, note, usersAdmin.getId());

			boolean isSend = bean.sendInterestCoupon(interestCoupon);
			if (isSend) {
				// 发送成功;
				json = QwyUtil.getJSONString("ok", "发放成功");
			} else {
				json = QwyUtil.getJSONString("err", "发放失败");
			}
		} catch (Exception e) {
			log.error("操作异常: ", e);
			json = QwyUtil.getJSONString("err", "发放异常");
		}
		try {
			QwyUtil.printJSON(getResponse(), json);
		} catch (IOException e) {
			log.error("操作异常: ", e);
		}
		return null;
	}

	/**
	 * 
	 * @return
	 */
	public String interestCouponRecord() {

		String json = "";
		try {
			UsersAdmin usersAdmin = (UsersAdmin) getRequest().getSession().getAttribute("usersAdmin");
			if (QwyUtil.isNullAndEmpty(usersAdmin)) {
				json = QwyUtil.getJSONString("err", "管理员未登录");
				QwyUtil.printJSON(getResponse(), json);
				// 管理员没有登录;
				return "login";
			}
			PageUtil<InterestCoupon> pageUtil = new PageUtil<InterestCoupon>();
			pageUtil.setCurrentPage(currentPage);
			pageUtil.setPageSize(pageSize);
			StringBuffer url = new StringBuffer();
			url.append(getRequest().getServletContext().getContextPath());
			url.append("/Product/Admin/sendInterestCoupon!interestCouponRecord.action?username=" + username);
			if (!QwyUtil.isNullAndEmpty(insertTime)) {
				url.append("&insertTime=");
				url.append(insertTime);
			}
			url.append("&useTime=");
			url.append(useTime);
			url.append("&status=");
			url.append(status);
			pageUtil.setPageUrl(url.toString());
			pageUtil = bean.findInterestCoupons(pageUtil, insertTime, useTime, username, status);
			if (!QwyUtil.isNullAndEmpty(insertTime)) {
				getRequest().setAttribute("insertTime", insertTime);
			}
			if (!QwyUtil.isNullAndEmpty(useTime)) {
				getRequest().setAttribute("useTime", useTime);
			}
			if (!QwyUtil.isNullAndEmpty(username)) {
				getRequest().setAttribute("username", username);
			}
			if (!QwyUtil.isNullAndEmpty(pageUtil)) {
				getRequest().setAttribute("pageUtil", pageUtil);
			}
		} catch (Exception e) {
			log.error("操作异常: ", e);
			json = QwyUtil.getJSONString("err", "投资券记录异常");
		}
		return "interestCouponRecord";
	}

	/**
	 * 群发投资券
	 * 
	 * @return
	 */
	public String sendInterestCouponGroup() {
		String json = "";
		try {
			UsersAdmin usersAdmin = (UsersAdmin) getRequest().getSession().getAttribute("usersAdmin");
			if (QwyUtil.isNullAndEmpty(usersAdmin)) {
				json = QwyUtil.getJSONString("err", "管理员未登录");
				QwyUtil.printJSON(getResponse(), json);
				// 管理员没有登录;
				return null;
			}

			String userName = paramMap.get("userName");
			userName = userName.trim().replace("\r\n", "");

			Pattern pattern = Pattern.compile("^[(\\d{11},{1})+|(\\d{11},{1})+\\d{11}]");
			Matcher matcher = pattern.matcher(userName);
			boolean b = matcher.matches();
			if(b){
				json = QwyUtil.getJSONString("err", "用户列表格式错误");
				QwyUtil.printJSON(getResponse(), json);
				// 管理员没有登录;
				return null;
			}

			Double interestRate = QwyUtil.converStrToDouble(paramMap.get("interestRate"), null);
			Integer num = QwyUtil.converStrToInt(paramMap.get("num"), null);
			String overTimeStr = paramMap.get("overTime");
			String note = paramMap.get("note");
			Integer couponType = QwyUtil.converStrToInt(paramMap.get("type"), null);
			int isAlways = 0;

			if (QwyUtil.isNullAndEmpty(userName)) {
				json = QwyUtil.getJSONString("err", "用户名不能为空");
				QwyUtil.printJSON(getResponse(), json);
				return null;
			}

			if (QwyUtil.isNullAndEmpty(interestRate)) {
				json = QwyUtil.getJSONString("err", "加息券金额不能为空");
				QwyUtil.printJSON(getResponse(), json);
				return null;
			}

			if (QwyUtil.isNullAndEmpty(num)) {
				json = QwyUtil.getJSONString("err", "加息券数量不能为空");
				QwyUtil.printJSON(getResponse(), json);
				return null;
			} else if (num <= 0) {
				json = QwyUtil.getJSONString("err", "加息券数量异常");
				QwyUtil.printJSON(getResponse(), json);
				return null;
			}

			if (interestRate == null) {
				json = QwyUtil.getJSONString("err", "加息率未填写");
				QwyUtil.printJSON(getResponse(), json);
				return null;
			} else if (interestRate <= 0 || interestRate > 3) {
				json = QwyUtil.getJSONString("err", "加息率的范围为0.1%-3%");
				QwyUtil.printJSON(getResponse(), json);
				return null;
			} else if (Double.compare(((Long) ((Double) (interestRate * 10)).longValue()).doubleValue() / 10, interestRate) != 0) {
				json = QwyUtil.getJSONString("err", "加息率的单位为0.1%");
				QwyUtil.printJSON(getResponse(), json);
				return null;
			}

			Date overTime = null;
			if (StringUtils.isBlank(overTimeStr)) {
				isAlways = 1;
			} else {
				overTime = QwyUtil.fmyyyyMMddHHmmss.parse(overTimeStr + " 00:00:00");
			}

			if (couponType == null) {
				json = QwyUtil.getJSONString("err", "类型未选择");
				QwyUtil.printJSON(getResponse(), json);
				return null;
			}

			String[] strArray = null;
			strArray = userName.split(",");
			for (int i = 0; i < strArray.length; i++) {
				if (!QwyUtil.verifyPhone(strArray[i].trim())) {
					json = QwyUtil.getJSONString("err", "用户名输入有误：" + strArray[i].trim());
					break;
				} else {
					Users users = registerUserBean.getUsersByUsername(strArray[i].trim());
					if (users == null) {
						json = QwyUtil.getJSONString("err", "该用户名不存在：" + strArray[i].trim() + "。之前的用户名已成功发送！！");
						break;
					} else {
						for (int x = 0; x < num; x++) {
							InterestCoupon interestCoupon = new InterestCoupon(users.getId(), couponType, interestRate, overTime, isAlways, note, usersAdmin.getId());
							bean.sendInterestCoupon(interestCoupon);
						}
					}
				}
			}
			if ("".equals(json)) {
				json = QwyUtil.getJSONString("ok", "投资券群发成功");
			}

		} catch (Exception e) {
			e.printStackTrace();
			json = QwyUtil.getJSONString("err", "操作失败");
		}
		try {
			QwyUtil.printJSON(getResponse(), json);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 
	 * @return
	 */
	public String sendInterestCouponUnbind() {
		String json = "";
		try {
			UsersAdmin usersAdmin = (UsersAdmin) getRequest().getSession().getAttribute("usersAdmin");
			if (QwyUtil.isNullAndEmpty(usersAdmin)) {
				json = QwyUtil.getJSONString("err", "管理员未登录");
				QwyUtil.printJSON(getResponse(), json);
				// 管理员没有登录;
				return null;
			}

			Integer isBindBank = QwyUtil.converStrToInt(paramMap.get("isBindBank"), null);
			Double interestRate = QwyUtil.converStrToDouble(paramMap.get("interestRate"), null);
			String overTimeStr = paramMap.get("overTime");
			Integer couponType = QwyUtil.converStrToInt(paramMap.get("type"), null);
			String note = paramMap.get("note");
			int isAlways = 0;

			if (interestRate == null) {
				json = QwyUtil.getJSONString("err", "加息率未填写");
				QwyUtil.printJSON(getResponse(), json);
				return null;
			} else if (interestRate <= 0 || interestRate > 3) {
				json = QwyUtil.getJSONString("err", "加息率的范围为0.1%-3%");
				QwyUtil.printJSON(getResponse(), json);
				return null;
			} else if (Double.compare(((Long) ((Double) (interestRate * 10)).longValue()).doubleValue() / 10, interestRate) != 0) {
				json = QwyUtil.getJSONString("err", "加息率的单位为0.1%");
				QwyUtil.printJSON(getResponse(), json);
				return null;
			}

			Date overTime = null;
			if (StringUtils.isBlank(overTimeStr)) {
				isAlways = 1;
			} else {
				overTime = QwyUtil.fmyyyyMMddHHmmss.parse(overTimeStr + " 00:00:00");
			}

			if (couponType == null) {
				json = QwyUtil.getJSONString("err", "类型未选择");
				QwyUtil.printJSON(getResponse(), json);
				return null;
			}

			boolean isSend = bean.sendInterestCouponByType(isBindBank, couponType, interestRate, overTime, isAlways, note, usersAdmin.getId());

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

	public Map<String, String> getParamMap() {
		return paramMap;
	}

	public void setParamMap(Map<String, String> paramMap) {
		this.paramMap = paramMap;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public String getUseTime() {
		return useTime;
	}

	public void setUseTime(String useTime) {
		this.useTime = useTime;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getInsertTime() {
		return insertTime;
	}

	public void setInsertTime(String insertTime) {
		this.insertTime = insertTime;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
