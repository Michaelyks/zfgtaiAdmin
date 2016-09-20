package com.zfgt.account.action;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.apache.struts2.config.Namespace;
import org.apache.struts2.config.ParentPackage;
import org.apache.struts2.config.Result;
import org.apache.struts2.config.Results;

import com.zfgt.account.bean.CoinpPurseBean;
import com.zfgt.common.bean.SystemConfigBean;
import com.zfgt.common.util.QwyUtil;
import com.zfgt.orm.CoinPurse;
import com.zfgt.orm.SystemConfig;
import com.zfgt.orm.UsersLogin;
import com.zfgt.product.action.IndexAction;

/**
 * 零钱包Action层
 * 
 * @author qwy
 *
 *         2015-04-20 19:45:50
 */
@SuppressWarnings("serial")
@ParentPackage("struts-default")
@Namespace("/Product/User")
// 理财产品
@Results({ @Result(name = "login", value = "/Product/login.jsp"), @Result(name = "myCoinPause", value = "/Product/coin_purse/coinPurse.jsp"), })
public class CoinPauseAction extends IndexAction {
	@Resource
	CoinpPurseBean coinpPurseBean;
	@Resource
	SystemConfigBean dao;
	private static Logger log = Logger.getLogger(CoinPauseAction.class);

	/**
	 * 跳转到我的零钱包
	 */
	public String MyCoinPause() {
		try {
			UsersLogin usersLogin = (UsersLogin) getRequest().getSession().getAttribute("usersLogin");
			if (QwyUtil.isNullAndEmpty(usersLogin)) {
				return "login";
			}
			SystemConfig systemConfig = dao.findSystemConfig();
			CoinPurse coinPurse = coinpPurseBean.findCoinPurseByUsersId(usersLogin.getUsersId());
			if (QwyUtil.isNullAndEmpty(coinPurse)) {
				coinPurse = new CoinPurse();
				coinPurse.setEarnings(systemConfig.getEarnings());
				coinPurse.setInMoney(0D);
				coinPurse.setInvestDay(0L);
				coinPurse.setPayInterest(0D);
			}
			request.setAttribute("coinPurse", coinPurse);
		} catch (Exception e) {
			log.error("操作异常: ", e);
		}

		return "myCoinPause";
	}
}
