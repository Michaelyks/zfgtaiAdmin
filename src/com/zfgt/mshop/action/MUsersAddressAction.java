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
import com.zfgt.common.util.QwyUtil;
import com.zfgt.mshop.bean.MUsersAddressBean;
import com.zfgt.orm.MUsersAddress;
import com.zfgt.orm.UsersLogin;

/**
 * 
 * @author bym
 *
 */
@ParentPackage("struts-default")
@Namespace("/Product/User")
@Results({ @Result(name = "myMAddress", value = "/Product/MShop/myMAddress.jsp") })
public class MUsersAddressAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static Logger log = Logger.getLogger(MUsersAddressAction.class);

	@Resource
	private MUsersAddressBean bean;

	private int currentPage = 1;
	private int pageSize = 10;

	private Map<String, String> paramMap;

	/**
	 * 
	 * @return
	 */
	public String myMAddress() {
		try {
			UsersLogin usersLogin = (UsersLogin) getRequest().getSession().getAttribute("usersLogin");
			List<MUsersAddress> list = bean.loadMUsersAddressList(usersLogin.getUsersId(), null, currentPage, pageSize);
			getRequest().setAttribute("list", bean.filterMUsersAddressGroup(list));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "myMAddress";
	}

	/**
	 * 
	 * @return
	 */
	public String saveAddress() {
		String json = "";
		try {
			UsersLogin usersLogin = (UsersLogin) getRequest().getSession().getAttribute("usersLogin");

			String mUsersAddressId = paramMap.get("mUsersAddressId");
			String contractName = paramMap.get("contractName");
			String phone = paramMap.get("phone");
			String address = paramMap.get("address");
			String addressDetail = paramMap.get("addressDetail");
			String isDefault = paramMap.get("isDefault");

			if (StringUtils.isBlank(contractName)) {
				json = QwyUtil.getJSONString("err", "添加失败");
				QwyUtil.printJSON(response, json);
				return null;
			}
			if (StringUtils.isBlank(phone)) {
				json = QwyUtil.getJSONString("err", "添加失败");
				QwyUtil.printJSON(response, json);
				return null;
			}
			if (!QwyUtil.verifyPhone(phone)) {
				json = QwyUtil.getJSONString("err", "添加失败");
				QwyUtil.printJSON(response, json);
				return null;
			}
			if (StringUtils.isBlank(address)) {
				json = QwyUtil.getJSONString("err", "添加失败");
				QwyUtil.printJSON(response, json);
				return null;
			}
			if (StringUtils.isBlank(addressDetail)) {
				json = QwyUtil.getJSONString("err", "添加失败");
				QwyUtil.printJSON(response, json);
				return null;
			}

			String type = "0";
			List<MUsersAddress> list = bean.loadMUsersAddressList(usersLogin.getUsersId(), null, 1, 20);
			if (list != null) {
				if (list.size() >= 10) {
					json = QwyUtil.getJSONString("err", "联系地址最多只能添加10条");
					QwyUtil.printJSON(response, json);
					return null;
				}
				type = "1";
			}

			if (StringUtils.isNotBlank(isDefault)) {
				type = "0";
				bean.updateUsersAddressType(usersLogin.getUsersId());
			}

			if (StringUtils.isBlank(mUsersAddressId)) {

				String returnId = bean.addUsersAddress(usersLogin.getUsersId(), contractName, phone, address, addressDetail, type);

				if (StringUtils.isNotBlank(returnId)) {
					json = QwyUtil.getJSONString("ok", "添加成功");
				} else {
					json = QwyUtil.getJSONString("err", "添加失败");
				}
			} else {

				Long returnCount = bean.updateUsersAddress(Long.parseLong(mUsersAddressId), contractName, phone, address, addressDetail, type);

				if (returnCount > 0) {
					json = QwyUtil.getJSONString("ok", "修改成功");
				} else {
					json = QwyUtil.getJSONString("err", "修改失败");
				}
			}

		} catch (Exception e) {
			json = QwyUtil.getJSONString("err", "操作失败");
			e.printStackTrace();
		}
		try {
			QwyUtil.printJSON(response, json);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 
	 * @return
	 */
	public String updateAddressType() {
		String json = "";
		try {

			String mUsersAddressId = paramMap.get("mUsersAddressId");

			if (StringUtils.isBlank(mUsersAddressId)) {
				json = QwyUtil.getJSONString("err", "修改失败");
				QwyUtil.printJSON(response, json);
				return null;
			}

			UsersLogin usersLogin = (UsersLogin) getRequest().getSession().getAttribute("usersLogin");

			bean.updateUsersAddressType(usersLogin.getUsersId());

			Long returnCount = bean.updateUsersAddressType(Long.parseLong(mUsersAddressId), "0", usersLogin.getUsersId());

			if (returnCount > 0) {
				json = QwyUtil.getJSONString("ok", "修改成功");
			} else {
				json = QwyUtil.getJSONString("err", "修改失败");
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

	/**
	 * 
	 * @return
	 */
	public String deleteAddress() {

		String json = "";
		try {

			String mUsersAddressId = paramMap.get("mUsersAddressId");

			if (StringUtils.isBlank(mUsersAddressId)) {
				json = QwyUtil.getJSONString("err", "修改失败");
				QwyUtil.printJSON(response, json);
				return null;
			}

			UsersLogin usersLogin = (UsersLogin) getRequest().getSession().getAttribute("usersLogin");

			Long returnCount = bean.updateUsersAddress(Long.parseLong(mUsersAddressId), usersLogin.getUsersId());

			if (returnCount > 0) {
				json = QwyUtil.getJSONString("ok", "修改成功");
			} else {
				json = QwyUtil.getJSONString("err", "修改失败");
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

	public Map<String, String> getParamMap() {
		return paramMap;
	}

	public void setParamMap(Map<String, String> paramMap) {
		this.paramMap = paramMap;
	}

}
