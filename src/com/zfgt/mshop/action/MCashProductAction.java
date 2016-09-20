package com.zfgt.mshop.action;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.config.Namespace;
import org.apache.struts2.config.ParentPackage;
import org.apache.struts2.config.Result;
import org.apache.struts2.config.Results;

import com.zfgt.common.action.BaseAction;
import com.zfgt.common.bean.SystemConfigBean;
import com.zfgt.common.util.QwyUtil;
import com.zfgt.login.bean.RegisterUserBean;
import com.zfgt.mshop.bean.MCoinPayBean;
import com.zfgt.mshop.bean.MProductBean;
import com.zfgt.mshop.bean.MUsersAddressBean;
import com.zfgt.orm.MProduct;
import com.zfgt.orm.MUsersAddress;
import com.zfgt.orm.SystemConfig;
import com.zfgt.orm.UsersInfo;
import com.zfgt.orm.UsersLogin;

/**
 * 
 * @author bym
 *
 */
@ParentPackage("struts-default")
@Namespace("/Product/User")
@Results({ @Result(name = "mCashProduct", value = "/Product/MShop/mCashProduct.jsp") })
public class MCashProductAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static Logger log = Logger.getLogger(MCashProductAction.class);

	@Resource
	private MProductBean bean;

	@Resource
	private SystemConfigBean systemConfigBean;

	@Resource
	private MUsersAddressBean mUsersAddressBean;

	@Resource
	private RegisterUserBean registerUserBean;

	@Resource
	private MCoinPayBean mCoinPayBean;

	private Map<String, String> paramMap;

	/**
	 * 
	 * @return
	 */
	public String cashProduct() {
		try {
			String id = getRequest().getParameter("id");
			MProduct mProduct = bean.findById(id);

			if (mProduct == null || StringUtils.isBlank(mProduct.getId())) {
				// 失败
				return null;
			}

			if (mProduct.getStatus().equals("1")) {
				// 失败
				return null;
			}

			if (mProduct.getLeftStock() <= 0) {
				// 失败
				return null;
			}

			getRequest().setAttribute("mProduct", mProduct);
			SystemConfig config = systemConfigBean.findSystemConfig();
			getRequest().setAttribute("mProductMap", bean.filterMProductList(mProduct, config));

			UsersLogin usersLogin = (UsersLogin) getRequest().getSession().getAttribute("usersLogin");
			List<MUsersAddress> list = mUsersAddressBean.loadMUsersAddressList(usersLogin.getUsersId(), null, 1, 10);
			getRequest().setAttribute("addressList", mUsersAddressBean.filterMUsersAddressGroup(list));

			return "mCashProduct";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 
	 * @return
	 */
	public String doCashProduct() {
		String json = "";
		try {

			// 喵商品id
			String productId = paramMap.get("productId");
			// 购买份数
			String copies = paramMap.get("copies");
			// 收货地址id
			String mUsersAddressId = paramMap.get("mUsersAddressId");

			if (StringUtils.isBlank(productId)) {
				json = QwyUtil.getJSONString("err", "参数1异常");
				QwyUtil.printJSON(response, json);
				return null;
			}
			if (StringUtils.isBlank(copies) || !QwyUtil.isOnlyNumber(copies) || Long.parseLong(copies) <= 0) {
				json = QwyUtil.getJSONString("err", "参数2异常");
				QwyUtil.printJSON(response, json);
				return null;
			}
			if (StringUtils.isBlank(mUsersAddressId)) {
				json = QwyUtil.getJSONString("err", "参数3异常");
				QwyUtil.printJSON(response, json);
				return null;
			}

			Long addressId = null;
			// 判断参数格式是否正确
			if (QwyUtil.isOnlyNumber(mUsersAddressId)) {
				addressId = Long.valueOf(mUsersAddressId);
			} else {
				json = QwyUtil.getJSONString("err", "收货地址有误");
				QwyUtil.printJSON(response, json);
				return null;
			}

			MProduct mProduct = (MProduct) bean.findById(productId);
			if (QwyUtil.isNullAndEmpty(mProduct)) {
				json = QwyUtil.getJSONString("err", "该喵商品不存在");
				QwyUtil.printJSON(response, json);
				return null;
			}
			if ("1".equals(mProduct.getStatus())) {
				json = QwyUtil.getJSONString("err", "该喵商品不能兑换");
				QwyUtil.printJSON(response, json);
				return null;
			}

			Long coin = Long.parseLong(copies) * mProduct.getPrice();
			if (mProduct.getLeftStock() < Long.parseLong(copies)) {
				json = QwyUtil.getJSONString("err", "购买份数超过喵商品剩余库存");
				QwyUtil.printJSON(response, json);
				return null;
			}

			UsersLogin usersLogin = (UsersLogin) getRequest().getSession().getAttribute("usersLogin");
			UsersInfo usersInfo = registerUserBean.getByUid(usersLogin.getUsersId());

			if (QwyUtil.isNullAndEmpty(usersInfo)) {
				json = QwyUtil.getJSONString("err", "操作异常");
				QwyUtil.printJSON(response, json);
				return null;
			}

			if (QwyUtil.isNullAndEmpty(usersInfo.getTotalPoint())) {
				usersInfo.setTotalPoint(0l);
			}
			if (coin > usersInfo.getTotalPoint() || usersInfo.getTotalPoint() < 0l) {
				json = QwyUtil.getJSONString("err", "兑换失败，您共有" + usersInfo.getTotalPoint() + "个喵币，不足以完成此次兑换");
				QwyUtil.printJSON(response, json);
				return null;
			}
			if (QwyUtil.isNullAndEmpty(usersInfo.getLevel())) {
				usersInfo.setLevel("0");
			}
			if (Long.parseLong(usersInfo.getLevel()) < mProduct.getVip()) {
				json = QwyUtil.getJSONString("err", "兑换失败，您的VIP等级不够兑换该商品");
				QwyUtil.printJSON(response, json);
				return null;
			}

			Map<String, Object> map1 = mCoinPayBean.SaveMCoinPay(usersLogin.getUsersId(), mProduct, Long.valueOf(copies), coin, addressId);
			if (!QwyUtil.isNullAndEmpty(map1)) {
				log.info(map1);
				json = QwyUtil.getJSONString("ok", "商品兑换成功，请前往我的资产查看");
				QwyUtil.printJSON(response, json);
				return null;
			} else {
				json = QwyUtil.getJSONString("err", "兑换失败");
				QwyUtil.printJSON(response, json);
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
			json = QwyUtil.getJSONString("err", "操作失败");
		}

		try {
			QwyUtil.printJSON(response, json);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public Map<String, String> getParamMap() {
		return paramMap;
	}

	public void setParamMap(Map<String, String> paramMap) {
		this.paramMap = paramMap;
	}

}
