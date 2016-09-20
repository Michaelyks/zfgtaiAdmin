package com.zfgt.account.action;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.apache.struts2.config.Namespace;
import org.apache.struts2.config.ParentPackage;
import org.apache.struts2.config.Result;
import org.apache.struts2.config.Results;

import com.zfgt.account.bean.BuyCarBean;
import com.zfgt.common.util.PageUtil;
import com.zfgt.common.util.QwyUtil;
import com.zfgt.orm.InterestDetails;
import com.zfgt.orm.Investors;
import com.zfgt.orm.Product;
import com.zfgt.orm.UsersLogin;
import com.zfgt.product.action.IndexAction;
import com.zfgt.product.bean.ProductCategoryBean;

/**
 * 购物车Action层
 * 
 * @author qwy
 *
 *         2015-04-20 19:45:50
 */
@SuppressWarnings("serial")
@ParentPackage("struts-default")
@Namespace("/Product/User")
// 理财产品
@Results({ @Result(name = "buyCar", value = "/Product/User/buyCar.jsp"), @Result(name = "category", value = "/Product/productCategory.jsp"),
		@Result(name = "redirect", value = "/Product/User/buyCar!loadInvestors.action", type = org.apache.struts2.dispatcher.ServletRedirectResult.class),
		@Result(name = "goPay", value = "/Product/User/pay"), @Result(name = "showPayInfo", value = "/Product/User/showPayInfo.jsp"), @Result(name = "login", value = "/Product/login.jsp"),
		@Result(name = "error", value = "/Product/error.jsp") })
public class BuyCarAction extends IndexAction {
	private static Logger log = Logger.getLogger(BuyCarAction.class);
	private Integer currentPage = 1;
	private Integer pageSize = 20;
	private Investors inv;
	@Resource
	private BuyCarBean bean;
	@Resource
	private ProductCategoryBean categoryBean;

	private List<InterestDetails> listInterest;

	/**
	 * 加载投资列表; 购物车;
	 * 
	 * @return
	 */
	public String loadInvestors() {
		try {
			getRequest().getSession().setAttribute("productCount", 0);// 当前购物车的总数量
			UsersLogin usersLogin = (UsersLogin) getRequest().getSession().getAttribute("usersLogin");
			if (QwyUtil.isNullAndEmpty(usersLogin)) {
				return "login";
			}
			PageUtil<Investors> pageUtil = new PageUtil<Investors>();
			pageUtil.setPageSize(pageSize);
			pageUtil.setCurrentPage(currentPage);
			pageUtil = bean.getInvestorsByPageUtil(usersLogin.getUsersId(), pageUtil, new String[] { "0" });
			if (!QwyUtil.isNullAndEmpty(pageUtil)) {
				getRequest().setAttribute("pageUtil", pageUtil);
				List<Investors> list = pageUtil.getList();
				if (!QwyUtil.isNullAndEmpty(list)) {
					getRequest().setAttribute("investorsList", list);
					getRequest().getSession().setAttribute("productCount", list.size());// 当前购物车的总数量
					double total = 0d;
					for (Investors investors : list) {
						try {
							total = QwyUtil.calcNumber(investors.getInMoney(), total, "+").doubleValue();
						} catch (Exception e) {
							log.error("操作异常: ", e);
						}
					}
					getRequest().setAttribute("productTotal", total);// 当前购物车产品的总额
				}

			}
		} catch (Exception e) {
			log.error("操作异常: ", e);
		}
		return "buyCar";
	}

	/**
	 * 新增投资列表;
	 * 
	 * @return
	 */
	public String addNewInvestors() {
		UsersLogin usersLogin = (UsersLogin) getRequest().getSession().getAttribute("usersLogin");
		if (usersLogin == null) {
			return "login";
		}
		Product pro = categoryBean.getProductById(inv.getProductId(), "0");
		if (QwyUtil.isNullAndEmpty(pro)) {
			return super.loadProduct();
		}
		bean.createNewInvestors(inv.getCopies(), inv.getCopies() * 100, inv.getCoupon(), pro, usersLogin.getUsersId());
		String id = bean.saveObject(inv);
		if (QwyUtil.isNullAndEmpty(id)) {
			return super.loadProduct();
		}
		return "redirect";
	}

	/**
	 * 删除投资列表; 根据投资列表的id;删除
	 * 
	 * @return
	 */
	public String deleteInvestors() {
		UsersLogin usersLogin = (UsersLogin) getRequest().getSession().getAttribute("usersLogin");
		if (usersLogin == null) {
			return "login";
		}
		bean.deleteInvestors(inv.getId());

		return "redirect";
	}

	/**
	 * 确认投资
	 * 
	 * @return
	 */
	public String showPayInfo() {
		try {
			UsersLogin usersLogin = (UsersLogin) getRequest().getSession().getAttribute("usersLogin");
			if (usersLogin == null) {
				getRequest().setAttribute("errMsg", "请登录后再操作!");
				return "error";
			}
			if (QwyUtil.isNullAndEmpty(inv.getCopies())) {
				log.info("请输入投资金额");
				getRequest().setAttribute("errMsg", "请输入投资金额");
				return "error";
			}
			log.info("ajax显示支付信息");
			if (QwyUtil.isNullAndEmpty(inv)) {
				log.info("产品ID无效1");
				getRequest().setAttribute("errMsg", "产品ID无效");
				return "error";
			}
			Product pro = categoryBean.getProductById(inv.getProductId(), "0");
			if (QwyUtil.isNullAndEmpty(pro)) {
				log.info("产品ID无效2");
				getRequest().setAttribute("errMsg", "产品ID无效");
				return "error";
			}

			if (!"0".equals(pro.getProductStatus())) {
				log.info("非【营销中】的产品不能购买");
				getRequest().setAttribute("errMsg", "非【营销中】的产品不能购买");
				return "error";
			}
			// yyyy-MM-dd格式的购买截至时间
			Date endTime = QwyUtil.fmyyyyMMdd.parse(QwyUtil.fmyyyyMMdd.format(pro.getEndTime()));
			Date finishTime = QwyUtil.fmyyyyMMdd.parse(QwyUtil.fmyyyyMMdd.format(pro.getFinishTime()));
			Date nowTime = QwyUtil.fmyyyyMMdd.parse(QwyUtil.fmyyyyMMdd.format(new Date()));
			if (nowTime.getTime() >= endTime.getTime()) {
				log.info("项目到期前3天不能购买");
				getRequest().setAttribute("errMsg", "项目到期前3天不能购买");
				return "error";
			}
			if (nowTime.getTime() >= finishTime.getTime()) {
				log.info("该投资已过期");
				getRequest().setAttribute("errMsg", "该投资已过期");
				return "error";
			}
			if (inv.getCopies() * 100 < pro.getQtje()) {
				log.info("投资金额必须大于等于起投金额");
				getRequest().setAttribute("errMsg", "投资金额必须大于等于起投金额");
				return "error";
			}

			if (inv.getCopies() > pro.getLeftCopies().longValue()) {
				log.info("投资金额不能大于剩余金额");
				getRequest().setAttribute("errMsg", "投资金额不能大于剩余金额");
				return "error";
			}

			if ((inv.getCopies()) % 50 != 0) {
				log.info("投资金额必须为50的整数倍");
				getRequest().setAttribute("errMsg", "投资金额必须为50的整数倍");
				return "error";
			}

			inv = bean.createNewInvestors(inv.getCopies(), inv.getCopies() * 100, inv.getCoupon(), pro, usersLogin.getUsersId());
			listInterest = bean.confirmInvest(inv, pro);
			getRequest().setAttribute("product", pro);
			getRequest().setAttribute("inv", inv);
			getRequest().setAttribute("requestMethod", "confirmInvest");
			getRequest().setAttribute("listInterest", listInterest);
			return "showPayInfo";
		} catch (Exception e) {
			log.error("操作异常: ", e);
			getRequest().setAttribute("errMsg", "操作异常,请刷新页面重新!");
		}
		return "error";
	}

	/**
	 * 确认投资--新手专享
	 * 
	 * @return
	 */
	public String showPayInfoFreshman() {
		try {
			UsersLogin usersLogin = (UsersLogin) getRequest().getSession().getAttribute("usersLogin");
			if (usersLogin == null) {
				getRequest().setAttribute("errMsg", "请登录后再操作!");
				return "error";
			}
			log.info("ajax显示支付信息");
			if (QwyUtil.isNullAndEmpty(inv)) {
				log.info("产品ID无效1");
				getRequest().setAttribute("errMsg", "产品ID无效");
				return "error";
			}
			Product pro = categoryBean.getProductById(inv.getProductId(), "1");
			if (QwyUtil.isNullAndEmpty(pro)) {
				log.info("产品ID无效2");
				getRequest().setAttribute("errMsg", "产品ID无效");
				return "error";
			}
			inv = bean.createNewInvestors(inv.getCopies(), inv.getCopies() * 100, inv.getCoupon(), pro, usersLogin.getUsersId());
			// Investors inv =
			// buyCarBean.createNewInvestors(copies,inMoney,coupon,pro,users.getId());
			// 份数大于10000送收益率+1%
			if (inv.getCopies() >= 10000) {
				inv.setAnnualEarnings(Double.parseDouble(QwyUtil.calcNumber(inv.getAnnualEarnings(), 1, "+") + ""));
			}
			Calendar finishTimeFreshman = QwyUtil.addDaysFromOldDate(inv.getStartTime(), 9);
			pro.setFinishTime(finishTimeFreshman.getTime());
			listInterest = bean.confirmInvest(inv, pro);
			getRequest().setAttribute("product", pro);
			getRequest().setAttribute("inv", inv);
			getRequest().setAttribute("requestMethod", "confirmInvestFreshman");
			getRequest().setAttribute("listInterest", listInterest);
			return "showPayInfo";
		} catch (Exception e) {
			log.error("操作异常: ", e);
			getRequest().setAttribute("errMsg", "操作异常,请刷新页面重新!");
		}
		return "error";
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

	public Investors getInv() {
		return inv;
	}

	public void setInv(Investors inv) {
		this.inv = inv;
	}

}
