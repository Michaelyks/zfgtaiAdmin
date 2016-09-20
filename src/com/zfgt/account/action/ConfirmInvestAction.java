package com.zfgt.account.action;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.apache.struts2.config.Namespace;
import org.apache.struts2.config.ParentPackage;
import org.apache.struts2.config.Result;
import org.apache.struts2.config.Results;

import com.zfgt.account.bean.BuyCarBean;
import com.zfgt.account.bean.ConfirmInvestBean;
import com.zfgt.account.bean.MyAccountBean;
import com.zfgt.account.bean.MyCouponBean;
import com.zfgt.account.bean.ValidateBean;
import com.zfgt.common.util.DESEncrypt;
import com.zfgt.common.util.PageUtil;
import com.zfgt.common.util.QwyUtil;
import com.zfgt.orm.Account;
import com.zfgt.orm.BankCard;
import com.zfgt.orm.Coupon;
import com.zfgt.orm.Investors;
import com.zfgt.orm.Product;
import com.zfgt.orm.Users;
import com.zfgt.orm.UsersInfo;
import com.zfgt.orm.UsersLogin;
import com.zfgt.product.action.IndexAction;
import com.zfgt.product.bean.ProductCategoryBean;


/**确认投资的Action层;
 * @author qwy
 *
 * @createTime 2015-04-23 10:10:10
 */
@SuppressWarnings("serial")
@ParentPackage("struts-default")
@Namespace("/Product/User")
//理财产品
@Results({ 
	@Result(name = "payOnline", value = "/Product/User/payOnline.jsp"),
	@Result(name = "login", value = "/Product/login.jsp"),
	@Result(name = "error", value = "/Product/error.jsp"),
	@Result(name = "index", value = "/Product/index!loadProduct.action", type=org.apache.struts2.dispatcher.ServletRedirectResult.class)
})
public class ConfirmInvestAction extends IndexAction {
	private static Logger log = Logger.getLogger(ConfirmInvestAction.class); 
	@Resource
	private MyAccountBean myAccountBean;
	private Investors inv;
	@Resource
	private ProductCategoryBean categoryBean;
	@Resource
	private BuyCarBean buyCarBean;
	@Resource
	private ConfirmInvestBean bean;
	@Resource
	private ValidateBean validateBean;
	@Resource
	private MyCouponBean myCouponBean;
	private String couponId;
	private String payPassword;
	/**
	 * 产品ID
	 */
	private String productId;
	/**
	 * 红包的使用率 10%
	 */
	private double couponRate = 0.1;
	
	private String bankMoney = "0";
	
	/**确认投资,跳转到支付页面;（基金和理财产品）
	 * @return
	 */
	public String confirmInvest(){
		try {
			request = getRequest();
			UsersLogin usersLogin = (UsersLogin)request.getSession().getAttribute("usersLogin");
			if(QwyUtil.isNullAndEmpty(usersLogin)){
				return "login";
			}
			Product pro = categoryBean.getProductById(productId,null);
			if(QwyUtil.isNullAndEmpty(pro)){
				log.info("产品ID无效2");
				return "payOnline";
			}
			//重新获取Users实体;
			Users users = myAccountBean.getUsersById(usersLogin.getUsersId());
			//查询是否设置了交易密码
			String isPwd="no";
			if(!QwyUtil.isNullAndEmpty(users.getPayPassword())){
				isPwd="ok";
			}
			getRequest().setAttribute("isPwd", isPwd);
			/*inv = buyCarBean.createNewInvestors(inv,pro,users.getId());
			String id = buyCarBean.saveObject(inv);
			System.out.println("投资列表Id: "+id);*/
			getRequest().setAttribute("product", pro);
			double inMoney = QwyUtil.calcNumber(inv.getCopies(), 100, "*").doubleValue();
			getRequest().setAttribute("inv", inv);
			//用户信息表
			UsersInfo usersInfo = users.getUsersInfo();
			//用户可用余额
			double leftMoney = usersInfo.getLeftMoney();
			leftMoney = QwyUtil.jieQuFa(leftMoney, 0);
			request.setAttribute("leftMoney", leftMoney);
			//用户红包可用余额;(分为单位)
			//double coupon = myAccountBean.getCouponCost(users.getId(),"0");
			//本次购买可使用的红包金额
			//修改于2015-08-28 16:28:25;不再一次性使用所有投资券
			/*double useCoupon = QwyUtil.calcNumber(coupon, 1, "*").doubleValue();
			useCoupon = QwyUtil.jieQuFa(useCoupon, 0);
			request.setAttribute("useCoupon", useCoupon);*/
			//使用账户余额;(已经除去了红包之后的余额)
			double useCoupon = 0;
			double useAccountMoney = QwyUtil.calcNumber(inMoney, useCoupon, "-").doubleValue();
			//如果投资券可以足够支付所有金额,则自己支付0元;
			useAccountMoney = useAccountMoney<=0?0:useAccountMoney;
			//账户可用余额是否比支付的余额要少;是: 则显示所有的账户余额; 否:则显示支付余额;
			useAccountMoney =  leftMoney>=useAccountMoney?useAccountMoney:leftMoney;
			request.setAttribute("useAccountMoney", useAccountMoney);
			//自己可以支付的总金额(包括红包)
			double myPay = QwyUtil.calcNumber(useAccountMoney, useCoupon, "+").doubleValue();
			//还需要支付的金额;
			double needPay = QwyUtil.calcNumber(inMoney, myPay, "-").doubleValue();
			request.setAttribute("needPay", needPay);
			//request.setAttribute("listBankCard", validateBean.getBindBankCard(users.getId()));
			PageUtil<Coupon> pageUtil = new PageUtil<Coupon>(1000,1);
			pageUtil = myCouponBean.loadCoupon(usersLogin.getUsersId(), "0", pageUtil);
			if(!QwyUtil.isNullAndEmpty(pageUtil)){
				List<Coupon> listCoupon = pageUtil.getList();
				if(!QwyUtil.isNullAndEmpty(listCoupon)){
					request.setAttribute("listCoupon", listCoupon);
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.error("操作异常: ",e);
			return "error";
		}
		return "payOnline";
	}
	
	/**确认投资,跳转到支付页面;--新手专享
	 * @return
	 */
	public String confirmInvestFreshman(){
		try {
			request = getRequest();
			UsersLogin usersLogin  = (UsersLogin)request.getSession().getAttribute("usersLogin");
			if(QwyUtil.isNullAndEmpty(usersLogin)){
				return "login";
			}
			Product pro = categoryBean.getProductById(productId,null);
			if(QwyUtil.isNullAndEmpty(pro)){
				log.info("产品ID无效2");
				//getRequest().setAttribute("errMsg", "产品ID无效");
				return "payOnline";
			}
			double inMoney = QwyUtil.calcNumber(inv.getCopies(), 100, "*").doubleValue();
			Users users = categoryBean.getUsersById(usersLogin.getUsersId());
		/*	String id = buyCarBean.saveObject(inv);
			System.out.println("投资列表Id: "+id);*/
			getRequest().setAttribute("product", pro);
			getRequest().setAttribute("inv", inv);
			//用户信息表
			UsersInfo usersInfo = users.getUsersInfo();
			//查询是否设置了交易密码
			String isPwd="no";
			if(!QwyUtil.isNullAndEmpty(users.getPayPassword())){
				isPwd="ok";
			}
			getRequest().setAttribute("isPwd", isPwd);
			//用户可用余额
			double leftMoney = usersInfo.getLeftMoney();
			leftMoney = QwyUtil.jieQuFa(leftMoney, 0);
			request.setAttribute("leftMoney", leftMoney);
			//本次购买可使用的红包金额
			double useCoupon = 0D;
			useCoupon = QwyUtil.jieQuFa(useCoupon, 0);
			request.setAttribute("useCoupon", useCoupon);
			//使用账户余额;(已经除去了红包之后的余额)
			double useAccountMoney = QwyUtil.calcNumber(inMoney, useCoupon, "-").doubleValue();
			//账户可用余额是否比支付的余额要少;是: 则显示所有的账户余额; 否:则显示支付余额;
			useAccountMoney =  leftMoney>=useAccountMoney?useAccountMoney:leftMoney;
			request.setAttribute("useAccountMoney", useAccountMoney);
			//自己可以支付的总金额(包括红包)
			double myPay = QwyUtil.calcNumber(useAccountMoney, useCoupon, "+").doubleValue();
			//还需要支付的金额;
			double needPay = QwyUtil.calcNumber(inMoney, myPay, "-").doubleValue();
			request.setAttribute("needPay", needPay);
			request.setAttribute("listBankCard", validateBean.getBindBankCard(users.getId()));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.error("操作异常: ",e);
			return "error";
		}
		return "payOnline";
	}
//	public String confirmInvestFreshman(){
//		try {
//			request = getRequest();
//			UsersLogin usersLogin  = (UsersLogin)request.getSession().getAttribute("usersLogin");
//			if(QwyUtil.isNullAndEmpty(usersLogin)){
//				return "login";
//			}
//			Product pro = categoryBean.getProductById(productId,null);
//			if(QwyUtil.isNullAndEmpty(pro)){
//				log.info("产品ID无效2");
//				getRequest().setAttribute("errMsg", "产品ID无效");
//				return "error";
//			}
//			inv.setCopies(1000L);
//			inv.setCoupon(90000d); 
//			double inMoney = QwyUtil.calcNumber(inv.getCopies(), 100, "*").doubleValue();
//			Users users = categoryBean.getUsersById(usersLogin.getUsersId());
//		/*	String id = buyCarBean.saveObject(inv);
//			System.out.println("投资列表Id: "+id);*/
//			getRequest().setAttribute("product", pro);
//			getRequest().setAttribute("inv", inv);
//			//用户信息表
//			UsersInfo usersInfo = users.getUsersInfo();
//			//用户可用余额
//			double leftMoney = usersInfo.getLeftMoney();
//			leftMoney = QwyUtil.jieQuFa(leftMoney, 0);
//			request.setAttribute("leftMoney", leftMoney);
//			//本次购买可使用的红包金额
//			double useCoupon = 90000;
//			useCoupon = QwyUtil.jieQuFa(useCoupon, 0);
//			request.setAttribute("useCoupon", useCoupon);
//			//使用账户余额;(已经除去了红包之后的余额)
//			double useAccountMoney = QwyUtil.calcNumber(inMoney, useCoupon, "-").doubleValue();
//			//账户可用余额是否比支付的余额要少;是: 则显示所有的账户余额; 否:则显示支付余额;
//			useAccountMoney =  leftMoney>=useAccountMoney?useAccountMoney:leftMoney;
//			request.setAttribute("useAccountMoney", useAccountMoney);
//			//自己可以支付的总金额(包括红包)
//			double myPay = QwyUtil.calcNumber(useAccountMoney, useCoupon, "+").doubleValue();
//			//还需要支付的金额;
//			double needPay = QwyUtil.calcNumber(inMoney, myPay, "-").doubleValue();
//			request.setAttribute("needPay", needPay);
//			request.setAttribute("listBankCard", validateBean.getBindBankCard(users.getId()));
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			log.error("操作异常: ",e);
//			return "error";
//		}
//		return "payOnline";
//	}
	
	
	
	/**支付购买;确认支付;
	 * @return
	 */
	public String confirmPay(){
		String json = "";
		try {
			long st = System.currentTimeMillis();
			UsersLogin usersLogin  = (UsersLogin)getRequest().getSession().getAttribute("usersLogin");
			if(QwyUtil.isNullAndEmpty(usersLogin)){
				json = QwyUtil.getJSONString("err", "用户未登录");
				QwyUtil.printJSON(getResponse(), json);
				return null;
			}
			if(QwyUtil.isNullAndEmpty(inv)){
				json = QwyUtil.getJSONString("err", "支付失败,缺少支付数据");
				QwyUtil.printJSON(getResponse(), json);
				return null;
			}
			Users u = validateBean.getUsersById(usersLogin.getUsersId());
			if(!DESEncrypt.jieMiPassword(u.getPayPassword()).equals(payPassword)){
				//支付密码错误;
				json = QwyUtil.getJSONString("error", "支付密码错误");
				QwyUtil.printJSON(getResponse(), json);
				return null;
			}
			boolean isMoney = QwyUtil.isPrice(bankMoney);
			if(!isMoney){
				//支付失败;
				json = QwyUtil.getJSONString("err", "请不要修改参数类型");
				QwyUtil.printJSON(getResponse(), json);
				return null;
			}
			double needBankPay = Double.parseDouble(bankMoney);
			needBankPay = QwyUtil.calcNumber(needBankPay, 100, "*").doubleValue();
			if(needBankPay!=0){
				//支付失败;
				json = QwyUtil.getJSONString("err", "账户余额不足，请先去充值页面充值");
				QwyUtil.printJSON(getResponse(), json);
				return null;
			}
			/*inv = buyCarBean.createNewInvestors(inv,pro,users.getId());
			String id = buyCarBean.saveObject(inv);
			System.out.println("投资列表Id: "+id);*/
			if(QwyUtil.calcNumber(inv.getCopies(), 100, "*").doubleValue()>QwyUtil.calcNumber(inv.getInMoney(), inv.getCoupon(), "+").doubleValue()){
				json = QwyUtil.getJSONString("err", "支付金额有误，请重新填写");
			}else{
				Product pro  = categoryBean.getProductById(productId, null);
				String isPay="支付失败";
				if("1".equals(pro.getProductType())){
					//选择新手专享产品支付流程;
//					if(needBankPay>=1){
//						//银行卡支付;
// 						isPay = bean.confirmPayFreshman(usersLogin.getUsersId(), productId, inv.getCopies(),inv.getInMoney(), 0,needBankPay, QwyUtil.getIpAddr(getRequest()));
//					}else{
						isPay = bean.confirmPayFreshman(usersLogin.getUsersId(), productId, inv.getCopies(),inv.getInMoney(), 0);
//					}
				}else{
					Coupon cp=null;
					if(!QwyUtil.isNullAndEmpty(couponId)){
						cp=bean.getCouponById(couponId);
						if(QwyUtil.isNullAndEmpty(cp)){
							json = QwyUtil.getJSONString("err", "选择红包有误");
							System.out.println("购买产品返回结果"+json);
							log.info("购买产品返回结果"+json);
							QwyUtil.printJSON(getResponse(), json);
							return null;
						}else{
							if("2".equals(cp.getStatus())){
								json = QwyUtil.getJSONString("err", "选择红包已被使用");
								System.out.println("购买产品返回结果"+json);
								log.info("购买产品返回结果"+json);
								QwyUtil.printJSON(getResponse(), json);
								return null;
							}else if("3".equals(cp.getStatus())){
								json = QwyUtil.getJSONString("err", "选择红包已过期");
								System.out.println("购买产品返回结果"+json);
								log.info("购买产品返回结果"+json);
								QwyUtil.printJSON(getResponse(), json);
								return null;
							}else{
								if(cp.getMoney()<Double.valueOf(inv.getCoupon())){
									json = QwyUtil.getJSONString("err", "选择红包有误");
									System.out.println("购买产品返回结果"+json);
									log.info("购买产品返回结果"+json);
									QwyUtil.printJSON(getResponse(), json);
									return null;
								}
							}
							
						}
					}
					//选择常规产品支付流程;
					//是否使用投资券;
//					double couponCost = 0;
//					if(!QwyUtil.isNullAndEmpty(inv.getCoupon()) && inv.getCoupon().doubleValue()>0){
//						couponCost =myAccountBean.getCouponCost(usersLogin.getUsersId(),"0");
//					}
					Double cup=0D;
					if(!QwyUtil.isNullAndEmpty(cp)){
						cup=cp.getInitMoney();
					}
//					if(needBankPay>=1){
//						//银行卡支付;
//						isPay = bean.confirmPay(usersLogin.getUsersId(), productId, inv.getCopies(),inv.getInMoney(), cup,needBankPay, QwyUtil.getIpAddr(getRequest()), couponId);
//					}else{
						isPay = bean.confirmPay(usersLogin.getUsersId(), productId, inv.getCopies(),inv.getInMoney(), cup, couponId);
//					}
				}
				if(QwyUtil.isNullAndEmpty(isPay)){
					//支付成功;
					json = QwyUtil.getJSONString("ok", "支付成功");
				}else{
					//支付失败;
					json = QwyUtil.getJSONString("err", isPay);
				}
			}
			long et = System.currentTimeMillis();
			log.info("支付耗时: "+(et-st)+" ms");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.error("操作异常: ",e);
			json = QwyUtil.getJSONString("err", "支付失败");
		}
		try {
			QwyUtil.printJSON(getResponse(), json);
		} catch (IOException e) {
			log.error("操作异常: ",e);
		}
		return null;
	}

	
	public static void main(String[] args) {
		double base = 0.1;
		double money = 60;
		try {
			int i = 0;
			for (;;) {
				i++;
				double temp  = QwyUtil.calcNumber(money, base, "*").doubleValue();
				money-=temp;
				System.out.println(i+"使用: "+temp+" 剩余: "+money);
				log.info(i+"使用: "+temp+" 剩余: "+money);
				if(money<10){
					break;
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.error("操作异常: ",e);
		}
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public Investors getInv() {
		return inv;
	}

	public void setInv(Investors inv) {
		this.inv = inv;
	}

	public String getBankMoney() {
		return bankMoney;
	}

	public void setBankMoney(String bankMoney) {
		this.bankMoney = bankMoney;
	}

	public String getCouponId() {
		return couponId;
	}

	public void setCouponId(String couponId) {
		this.couponId = couponId;
	}

	public String getPayPassword() {
		return payPassword;
	}

	public void setPayPassword(String payPassword) {
		this.payPassword = payPassword;
	}
	

}
