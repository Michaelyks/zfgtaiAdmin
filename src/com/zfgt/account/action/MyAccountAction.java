package com.zfgt.account.action;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.apache.struts2.config.Namespace;
import org.apache.struts2.config.ParentPackage;
import org.apache.struts2.config.Result;
import org.apache.struts2.config.Results;

import com.zfgt.account.bean.InvestorsRecordBean;
import com.zfgt.account.bean.MyAccountBean;
import com.zfgt.account.bean.UserInfoBean;
import com.zfgt.common.action.BaseAction;
import com.zfgt.common.util.PageUtil;
import com.zfgt.common.util.QwyUtil;
import com.zfgt.orm.Investors;
import com.zfgt.orm.Users;
import com.zfgt.orm.UsersInfo;
import com.zfgt.orm.UsersLogin;
/**我的钱包
 * @author qwy
 *
 * 2015-04-20 12:58:29
 */
@SuppressWarnings("serial")
@ParentPackage("struts-default")
@Namespace("/Product/User")
//理财产品
@Results({ 
	@Result(name = "myAccount", value = "/Product/User/myAccount.jsp"),
	@Result(name = "login", value = "/Product/login.jsp"),
	@Result(name = "error", value = "/Product/error.jsp")
})
public class MyAccountAction extends BaseAction {
	private static Logger log = Logger.getLogger(MyAccountAction.class); 
	@Resource
	private MyAccountBean bean;
	@Resource
	private UserInfoBean uibean;
	@Resource
	private InvestorsRecordBean investorsRecordBean;
	
	/**显示我的钱包;
	 * @return
	 */
	public String showMyAccount(){
		try {
			request = getRequest();
			UsersLogin usersLogin  = (UsersLogin)request.getSession().getAttribute("usersLogin");
			if(QwyUtil.isNullAndEmpty(usersLogin)){
				return "login";
			}
			Users newUsers = bean.getUsersById(usersLogin.getUsersId());
			request.getSession().setAttribute("users",newUsers);
			double coupon = bean.getCouponCost(newUsers.getId());
			coupon = QwyUtil.jieQuFa(coupon, 0);
			request.setAttribute("coupon", QwyUtil.calcNumber(coupon, 0.01, "*").doubleValue());
			double productCost = bean.getProductCost(newUsers.getId());
			productCost = QwyUtil.jieQuFa(productCost, 0);
			productCost = QwyUtil.calcNumber(productCost, 0.01, "*").doubleValue();
			double freezeMoney = QwyUtil.calcNumber(newUsers.getUsersInfo().getFreezeMoney(), 0.01, "*").doubleValue();
			request.setAttribute("freezeMoney", freezeMoney);
			request.setAttribute("free", productCost);
			UsersInfo userInfo=uibean.getUserInfoById(newUsers.getId());
			String isVerifyEmail=userInfo.getIsVerifyEmail();
			String isVerifyIdcard=userInfo.getIsVerifyIdcard();
			String isVerifyPhone=userInfo.getIsVerifyPhone();
			int safeLevel=10;
			//修改于 2015-05-22 20:29:48 邮箱验证先去掉
			/*if("1".equals(isVerifyEmail)){
				safeLevel+=10;
			}*/
			if("1".equals(isVerifyIdcard)){
					safeLevel+=10;
			}
			if("1".equals(isVerifyPhone)){
					safeLevel+=10;
			}
			request.setAttribute("safeLevel", safeLevel);
			System.out.println("显示我的钱包");
			log.info("显示我的钱包");
			String[] status={"1","2","3"};
			PageUtil<Investors> pageUtil = getInvestorsByPageUtil(1, 10, status, newUsers.getId());
			request.setAttribute("investorsList", pageUtil.getList());
		} catch (Exception e) {
			log.error("操作异常: ",e);
			//request.setAttribute("errMsg", "发生了错误");
			return "error";
		}
		return "myAccount";
	}
	
	/**根据分页对象获取投资记录
	 * @param currentPage 当前页数
	 * @param pageSize 显示条数
	 * @param status 查询状态
	 * @return
	 */
	public PageUtil<Investors> getInvestorsByPageUtil(int currentPage,int pageSize,String[] status,long uid){
		PageUtil<Investors> pageUtil = new PageUtil<Investors>();
		pageUtil.setCurrentPage(currentPage);
		pageUtil.setPageSize(pageSize);
		pageUtil = investorsRecordBean.getInvestorsByPageUtil(pageUtil,status,uid);
		return pageUtil;
	}

}
