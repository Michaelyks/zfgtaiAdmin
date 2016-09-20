package com.zfgt.admin.product.action;

import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.apache.struts2.config.Namespace;
import org.apache.struts2.config.ParentPackage;
import org.apache.struts2.config.Result;
import org.apache.struts2.config.Results;

import com.zfgt.account.bean.InvestorsRecordBean;
import com.zfgt.account.bean.MyAccountBean;
import com.zfgt.admin.product.bean.BindCardRecordBean;
import com.zfgt.admin.product.bean.InterestDetailsBean;
import com.zfgt.common.action.BaseAction;
import com.zfgt.common.bean.SystemConfigBean;
import com.zfgt.common.bean.YiBaoPayBean;
import com.zfgt.common.util.PageUtil;
import com.zfgt.common.util.QwyUtil;
import com.zfgt.login.bean.RegisterUserBean;
import com.zfgt.orm.Account;
import com.zfgt.orm.InterestDetails;
import com.zfgt.orm.Investors;
import com.zfgt.orm.SystemConfig;
import com.zfgt.orm.Users;
import com.zfgt.orm.UsersAdmin;
import com.zfgt.orm.UsersInfo;

/**
 * 解绑银行操作
 * 
 * @author 曾礼强
 *
 */
@ParentPackage("struts-default")
@Namespace("/Product/Admin")
// 发布产品页面
@Results({ @Result(name = "SUCCESS", value = "/Product/Admin/bankCard/unbindBankCard.jsp"), @Result(name = "findAccountByUserName", value = "/Product/Admin/bankCard/unbindBankCard2.jsp"),
		@Result(name = "findAccount", value = "/Product/Admin/operationManager/bindInfo.jsp") })
public class BindCardRecordAction extends BaseAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Logger log = Logger.getLogger(getClass());
	@Resource
	BindCardRecordBean bean;
	@Resource
	RegisterUserBean registerUserBean;
	@Resource
	MyAccountBean accountBean;
	@Resource
	YiBaoPayBean yiBaoPayBean;
	@Resource
	SystemConfigBean systemConfigBean;
	@Resource
	InvestorsRecordBean investorsRecordBean;
	@Resource
	InterestDetailsBean interestDetailsBean;
	private String name;
	private String vcode;
	private String wh;

	/**
	 * 查询需要解绑人的信息
	 */
	public String findAccountByName() {
		Account account;
		try {
			account = accountBean.getAccountByUsersName(name, "0");
			getRequest().setAttribute("account", account);
			getRequest().setAttribute("name", name);
			if (QwyUtil.isNullAndEmpty(account)) {
				getRequest().setAttribute("message", "此号未绑定银行卡或者已经解绑");
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error("查询解绑人", e);
		}

		return "SUCCESS";
	}

	/**
	 * 查询需要解绑人的信息
	 */
	public String findAccountByUserName() {
		Account account;
		try {
			account = accountBean.getAccountByUsersName(name, "0");
			getRequest().setAttribute("account", account);
			getRequest().setAttribute("name", name);
			if (QwyUtil.isNullAndEmpty(account)) {
				getRequest().setAttribute("message", "此号未绑定银行卡或者已经解绑");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			log.error("查询解绑人2", e);
		}
		return "findAccountByUserName";
	}

	/**
	 * 查询绑卡人的信息
	 */
	public String findAccount() {
		List<Account> list;
		try {
			list = accountBean.findAccountByUsersName(name);
			getRequest().setAttribute("list", list);
			getRequest().setAttribute("username", name);
			if (QwyUtil.isNullAndEmpty(list)) {
				getRequest().setAttribute("message", "此号未绑定银行卡或者已经解绑");
				getRequest().setAttribute("isbindbank", "0");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			log.error("查询绑卡人信息", e);
		}
		return "findAccount";
	}

	/**
	 * 请求解绑，并且发送验证码
	 * 
	 * @return
	 */
	public String sendYzm() {
		String json = "";
		try {
			UsersAdmin us = (UsersAdmin) getRequest().getSession().getAttribute("usersAdmin");
			if (QwyUtil.isNullAndEmpty(us)) {
				json = QwyUtil.getJSONString("err", "管理员未登录");
				QwyUtil.printJSON(getResponse(), json);
				// 管理员没有登录;
				return null;
			}
			json = verifyUser();
			if (QwyUtil.isNullAndEmpty(verifyUser())) {
				json = registerUserBean.sendYZM(name, "4", wh);
				if (QwyUtil.isNullAndEmpty(json)) {
					json = QwyUtil.getJSONString("ok", "发送验证码成功");
				} else {
					json = QwyUtil.getJSONString("error", json);
				}
				QwyUtil.printJSON(getResponse(), json);
				return null;
			} else {
				QwyUtil.printJSON(getResponse(), json);
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error("操作异常: ", e);
			json = QwyUtil.getJSONString("error", "系统错误");
		}
		try {
			QwyUtil.printJSON(getResponse(), json);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			log.error("操作异常: ", e);
		}
		return null;
	}

	public String verifyUser() {
		try {
			String json = "";
			// 得到用户数据
			Users users = registerUserBean.getUsersByUsername(name);
			// 投资记录
			PageUtil<Investors> pageUtil = new PageUtil<Investors>();
			pageUtil.setCurrentPage(1);
			pageUtil.setPageSize(100);
			// 投资详情
			pageUtil = investorsRecordBean.getInvestorsByPageUtil(pageUtil, new String[] { "2" }, users.getId());
			PageUtil<InterestDetails> pageUtil2 = new PageUtil<InterestDetails>();
			pageUtil2.setCurrentPage(1);
			pageUtil2.setPageSize(100);
			pageUtil2 = interestDetailsBean.finInterestDetailses(pageUtil2, name, "0", null);
			if (!QwyUtil.isNullAndEmpty(pageUtil.getList()) || !QwyUtil.isNullAndEmpty(pageUtil2.getList())) {
				json = QwyUtil.getJSONString("investors", "还有在投的项目");
				return json;
			}
			UsersInfo usersInfo = users.getUsersInfo();
			if (!QwyUtil.isNullAndEmpty(usersInfo) && (usersInfo.getTotalMoney() > 0 || usersInfo.getLeftMoney() > 0 || usersInfo.getFreezeMoney() > 0)) {
				json = QwyUtil.getJSONString("totalMoney", "账号里剩余的金额不为0");
				return json;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}

	/**
	 * 解绑银行卡
	 * 
	 * @return
	 */
	public String unbind() {
		String json = "";
		try {
			Users users = registerUserBean.getUsersByUsername(name);
			Account account = yiBaoPayBean.getAccountByUsersId(users.getId(), null);
			if (QwyUtil.isNullAndEmpty(account)) {
				json = QwyUtil.getJSONString("error", "无绑定银行卡");
				QwyUtil.printJSON(getResponse(), json);
				return null;
			}
			if (bean.unBindBank(users.getId(), "1")) {
				json = QwyUtil.getJSONString("ok", "解绑银行卡成功");
				QwyUtil.printJSON(getResponse(), json);
				return null;
			} else {
				json = QwyUtil.getJSONString("error", "解绑银行卡失败");
				QwyUtil.printJSON(getResponse(), json);
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error("操作异常: ", e);
			json = QwyUtil.getJSONString("error", "系统错误");
		}
		try {
			QwyUtil.printJSON(getResponse(), json);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			log.error("操作异常: ", e);
		}
		return null;
	}

	/**
	 * 请求解绑，并且发送验证码
	 * 
	 * @return
	 */
	public String verifyYzmAndUnbind() {
		String json = "";
		try {
			String code = registerUserBean.getYzm(name, "4");
			if (code.equals(vcode)) {
				json = verifyUser();
				if (QwyUtil.isNullAndEmpty(verifyUser())) {
					Users users = registerUserBean.getUsersByUsername(name);
					Account account = yiBaoPayBean.getAccountByUsersId(users.getId(), "0");
					if (QwyUtil.isNullAndEmpty(account)) {
						json = QwyUtil.getJSONString("error", "无绑定银行卡");
						QwyUtil.printJSON(getResponse(), json);
						return null;
					}
					// List<BindCardRecord>
					// list=bean.findBindBankList(users.getId());
					SystemConfig systemConfig = systemConfigBean.findSystemConfig();
					if (bean.unBindBank(users.getId(), "0")) {
						json = QwyUtil.getJSONString("ok", "解绑银行卡成功");
						bean.sendSuc(name, systemConfig.getCompanyTel());
						QwyUtil.printJSON(getResponse(), json);
						return null;
					} else {
						json = QwyUtil.getJSONString("error", "解绑银行卡失败");
						QwyUtil.printJSON(getResponse(), json);
						return null;
					}
				} else {
					QwyUtil.printJSON(getResponse(), json);
					return null;
				}
			} else {
				json = QwyUtil.getJSONString("error", "验证码错误");
				QwyUtil.printJSON(getResponse(), json);
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error("操作异常: ", e);
			json = QwyUtil.getJSONString("error", "系统错误");
		}
		try {
			QwyUtil.printJSON(getResponse(), json);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			log.error("操作异常: ", e);
		}
		return null;
	}
	// public String verifyYzmAndUnbind(){
	// String json="";
	// try {
	// String code = registerUserBean.getYzm(name, "4");
	// if (code.equals(vcode)) {
	// json=verifyUser();
	// if(QwyUtil.isNullAndEmpty(verifyUser())){
	// Users users = registerUserBean.getUsersByUsername(name);
	// Account account = yiBaoPayBean.getAccountByUsersId(
	// users.getId(), "0");
	// if (QwyUtil.isNullAndEmpty(account)) {
	// json = QwyUtil.getJSONString("error", "无绑定银行卡");
	// QwyUtil.printJSON(getResponse(), json);
	// return null;
	// }
	// List<BindCardRecord> list=bean.findBindBankList(users.getId());
	// SystemConfig systemConfig=systemConfigBean.findSystemConfig();
	// if(bean.unBindBank(list, users.getId())){
	// json = QwyUtil.getJSONString("ok", "解绑银行卡成功");
	// bean.sendSuc(name, systemConfig.getCompanyTel());
	// QwyUtil.printJSON(getResponse(), json);
	// return null;
	// }else{
	// json = QwyUtil.getJSONString("error", "解绑银行卡失败");
	// QwyUtil.printJSON(getResponse(), json);
	// return null;
	// }
	// }else{
	// QwyUtil.printJSON(getResponse(), json);
	// return null;
	// }
	// }else{
	// json = QwyUtil.getJSONString("error", "验证码错误");
	// QwyUtil.printJSON(getResponse(), json);
	// return null;
	// }
	// } catch (Exception e) {
	// e.printStackTrace();
	// log.error("操作异常: ",e);
	// json = QwyUtil.getJSONString("error", "系统错误");
	// }
	// try {
	// QwyUtil.printJSON(getResponse(), json);
	// } catch (IOException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// log.error("操作异常: ",e);
	// }
	// return null;
	// }

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getVcode() {
		return vcode;
	}

	public void setVcode(String vcode) {
		this.vcode = vcode;
	}

	public String getWh() {
		return wh;
	}

	public void setWh(String wh) {
		this.wh = wh;
	}

}
