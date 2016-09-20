package com.zfgt.account.action;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.apache.struts2.config.Namespace;
import org.apache.struts2.config.ParentPackage;
import org.apache.struts2.config.Result;
import org.apache.struts2.config.Results;

import com.zfgt.account.bean.MyCouponBean;
import com.zfgt.common.action.BaseAction;
import com.zfgt.common.util.PageUtil;
import com.zfgt.common.util.QwyUtil;
import com.zfgt.orm.Coupon;
import com.zfgt.orm.Users;
import com.zfgt.orm.UsersLogin;

/**
 * 我的投资券
 * 
 * @author qwy
 * 
 *         2015-04-20 12:58:29
 */
@SuppressWarnings("serial")
@ParentPackage("struts-default")
@Namespace("/Product/User")
// 理财产品
@Results({ @Result(name = "myCoupon", value = "/Product/User/red_paper.jsp"),
		@Result(name = "login", value = "/Product/login.jsp"),
		@Result(name = "error", value = "/Product/error.jsp") })
public class MyCouponAction extends BaseAction {
	private static Logger log = Logger.getLogger(MyCouponAction.class); 
	@Resource
	private MyCouponBean bean;
	private Integer currentPage = 1;
	private Integer pageSize = 500;

	/**
	 * 获取我的投资券
	 * 
	 * @return
	 */
	public String loadCoupon() {
		request = getRequest();
		try {
			UsersLogin usersLogin = (UsersLogin) getRequest().getSession().getAttribute("usersLogin");
			if (QwyUtil.isNullAndEmpty(usersLogin)) {
				return "login";
			}
			PageUtil<Coupon> pageUtil = new PageUtil<Coupon>(pageSize,
					currentPage);
			bean.loadCoupon(usersLogin.getUsersId(), null, pageUtil);
			if (!QwyUtil.isNullAndEmpty(pageUtil)) {
				List<Coupon> list = pageUtil.getList();
				if (!QwyUtil.isNullAndEmpty(list)) {
					List<Coupon> list0 = chooseCoupon(list, "0");
					//List<Coupon> list1 = chooseCoupon(list, "1");
					List<Coupon> list2 = chooseCoupon(list, "2");
					List<Coupon> list3 = chooseCoupon(list, "3");
					request.setAttribute("list0", list0);
					//request.setAttribute("list1", list1);
					request.setAttribute("list2", list2);
					request.setAttribute("list3", list3);
				}
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.error("操作异常: ",e);
			return "error";
		}
		return "myCoupon";
	}

	/**
	 * 根据状态筛选投资券
	 * 
	 * @param list
	 *            投资券集合
	 * @param status
	 *            状态;
	 * @return
	 */
	public List<Coupon> chooseCoupon(List<Coupon> list, String status) {
		List<Coupon> newList = null;
		if (!QwyUtil.isNullAndEmpty(list)) {
			newList = new ArrayList<Coupon>();
			for (Coupon coupon : list) {
				if (coupon.getStatus().equals(status))
					newList.add(coupon);
			}
		}
		return newList;
	}

}
