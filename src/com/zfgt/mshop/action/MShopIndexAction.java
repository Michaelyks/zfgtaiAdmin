package com.zfgt.mshop.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.apache.struts2.config.Namespace;
import org.apache.struts2.config.ParentPackage;
import org.apache.struts2.config.Result;
import org.apache.struts2.config.Results;

import com.zfgt.common.action.BaseAction;
import com.zfgt.common.util.PageUtil;
import com.zfgt.common.util.QwyUtil;
import com.zfgt.mshop.bean.MProductBean;
import com.zfgt.mshop.bean.MSignInBean;
import com.zfgt.orm.MProduct;
import com.zfgt.orm.UsersLogin;

/**
 * 
 * @author bym
 *
 */
@ParentPackage("struts-default")
@Namespace("/Product")
@Results({ @Result(name = "index", value = "/Product/MShop/mShopIndex.jsp") })
public class MShopIndexAction extends BaseAction {

	@Resource
	private MProductBean bean;

	@Resource
	private MSignInBean mSignInBean;

	private Integer pageSize = 6;
	private Integer currentPage = 1;

	private Map<String, String> paramMap = null;

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static Logger log = Logger.getLogger(MShopIndexAction.class);

	/**
	 * 加载商品列表;
	 */
	public String loadProduct() {
		try {

			PageUtil<MProduct> pageUtil = new PageUtil<MProduct>();
			pageUtil.setCurrentPage(currentPage);
			pageUtil.setPageSize(pageSize);

			int mode = QwyUtil.converStrToInt(getRequest().getAttribute("paramMap.mode") + "", 0);
			int mcoinStart = QwyUtil.converStrToInt(getRequest().getAttribute("paramMap.mcoinStart") + "", 0);
			int mcoinEnd = QwyUtil.converStrToInt(getRequest().getAttribute("paramMap.mcoinEnd") + "", 0);

			pageUtil = bean.getMProductByCondition(pageUtil, mode, mcoinStart, mcoinEnd);
			Long count = bean.getAllCountByCondition(mcoinStart, mcoinEnd);
			getRequest().setAttribute("count", count);

			if (!QwyUtil.isNullAndEmpty(pageUtil)) {
				List<MProduct> productList = pageUtil.getList();
				getRequest().setAttribute("mproductList", productList);
				getRequest().setAttribute("pageCount", pageUtil.getPageCount());
			}

			// 查询喵币状态
			UsersLogin usersLogin = (UsersLogin) getRequest().getSession().getAttribute("usersLogin");
			if (usersLogin != null) {
				Map<String, Object> coinMap = mSignInBean.findMSignInAndMCoinByUid(usersLogin.getUsersId());
				getRequest().setAttribute("coinMap", coinMap);
			} else {
				getRequest().setAttribute("coinMap", new HashMap<String, Object>());
			}

		} catch (Exception e) {
			log.error("操作异常: ", e);
			log.error("MShopIndexAction.loadProduct", e);
		}
		return "index";
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

	public Map<String, String> getParamMap() {
		return paramMap;
	}

	public void setParamMap(Map<String, String> paramMap) {
		this.paramMap = paramMap;
	}

}
