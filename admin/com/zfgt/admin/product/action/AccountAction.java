package com.zfgt.admin.product.action;

import javax.annotation.Resource;

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
import com.zfgt.login.bean.RegisterUserBean;
import com.zfgt.orm.Investors;
import com.zfgt.orm.Users;
import com.zfgt.orm.UsersAdmin;
import com.zfgt.orm.UsersInfo;
/**我的钱包
 * @author qwy
 *
 * 2015-04-20 12:58:29
 */
@SuppressWarnings("serial")
@ParentPackage("struts-default")
@Namespace("/Product/Admin")
//理财产品
@Results({ 
	@Result(name = "myAccount", value = "/Product/Admin/fundsManager/myAccount.jsp"),
	@Result(name = "login", value = "/Product/loginBackground.jsp" ,type=org.apache.struts2.dispatcher.ServletRedirectResult.class),
	@Result(name = "error", value = "/Product/error.jsp"),
	@Result(name = "bindInfo", value = "/Product/Admin/operationManager/bindInfo.jsp")
})
public class AccountAction extends BaseAction {
	
	@Resource
	private MyAccountBean bean;
	@Resource
	private UserInfoBean uibean;
	@Resource
	private InvestorsRecordBean investorsRecordBean;
	@Resource
	private RegisterUserBean registerUserBean;
	
	private String username;
	
	/**显示我的钱包;
	 * @return
	 */
	public String showMyAccount(){
		try {
			request = getRequest();
			UsersAdmin users = (UsersAdmin)getRequest().getSession().getAttribute("usersAdmin");
			if(QwyUtil.isNullAndEmpty(users)){
				//管理员没有登录;
				return "login";
			}
			if(!QwyUtil.isNullAndEmpty(username)){
				Users newUsers = registerUserBean.getUsersByUsername(username);
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
				String[] status={"1","2","3"};
				PageUtil<Investors> pageUtil = getInvestorsByPageUtil(1, 10, status, newUsers.getId());
				request.setAttribute("investorsList", pageUtil.getList());
			}
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
	/**
	 * 查看帮卡人信息
	 * @return
	 */
	public String showBindInfo(){
		if(!QwyUtil.isNullAndEmpty(username)){
			request.setAttribute("username", username);
		}
		return "bindInfo";
				
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

}
