package com.zfgt.product.action;

import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.config.Namespace;
import org.apache.struts2.config.ParentPackage;
import org.apache.struts2.config.Results;

import com.zfgt.common.action.BaseAction;
import com.zfgt.common.util.QwyUtil;
import com.zfgt.orm.ProductApply;
import com.zfgt.product.bean.ProductApplyBean;

/**
 * 
 * @author bym
 *
 */
@ParentPackage("struts-default")
@Namespace("/Product")
@Results({})
public class ProductApplyAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static Logger log = Logger.getLogger(ProductApplyAction.class);

	@Resource
	private ProductApplyBean bean;

	private Map<String, String> paramMap;

	public Map<String, String> getParamMap() {
		return paramMap;
	}

	public void setParamMap(Map<String, String> paramMap) {
		this.paramMap = paramMap;
	}

	/**
	 * 
	 */
	public String doProductApply() {
		String json = "";
		try {
			ProductApply productApply = new ProductApply();
			String contractName = paramMap.get("contractName");// 联系人姓名
			String phone = paramMap.get("phone");// 电话
			String address = paramMap.get("address");// 联系地址
			String sex = paramMap.get("sex");// 性别
			Integer personnelType = QwyUtil.converStrToInt(paramMap.get("personnelType"), null);// 人员类型1个人2团队3组织
			Integer productType = QwyUtil.converStrToInt(paramMap.get("productType"), null);// 项目类型1车贷2房贷3创业贷
			Long applyAmount = QwyUtil.converStrToLong(paramMap.get("applyAmount"), null);// 申请金额
			Integer deadline = QwyUtil.converStrToInt(paramMap.get("deadline"), null);// 借款期限（单位月）

			if (StringUtils.isBlank(contractName)) {
				json = QwyUtil.getJSONString("err", "操作失败");
				QwyUtil.printJSON(response, json);
				return null;
			}
			if (StringUtils.isBlank(phone)) {
				json = QwyUtil.getJSONString("err", "操作失败");
				QwyUtil.printJSON(response, json);
				return null;
			}
			if (StringUtils.isBlank(address)) {
				json = QwyUtil.getJSONString("err", "操作失败");
				QwyUtil.printJSON(response, json);
				return null;
			}
			if (StringUtils.isBlank(sex)) {
				json = QwyUtil.getJSONString("err", "操作失败");
				QwyUtil.printJSON(response, json);
				return null;
			}

			if (QwyUtil.isNullAndEmpty(personnelType) || personnelType < 1 || personnelType > 3) {
				json = QwyUtil.getJSONString("err", "操作失败");
				QwyUtil.printJSON(response, json);
				return null;
			}
			if (QwyUtil.isNullAndEmpty(productType) || productType < 1 || productType > 3) {
				json = QwyUtil.getJSONString("err", "操作失败");
				QwyUtil.printJSON(response, json);
				return null;
			}
			if (QwyUtil.isNullAndEmpty(applyAmount) || applyAmount <= 0) {
				json = QwyUtil.getJSONString("err", "操作失败");
				QwyUtil.printJSON(response, json);
				return null;
			}
			if (QwyUtil.isNullAndEmpty(deadline) || deadline <= 0) {
				json = QwyUtil.getJSONString("err", "操作失败");
				QwyUtil.printJSON(response, json);
				return null;
			}

			productApply.setContractName(contractName);
			productApply.setPhone(phone);
			productApply.setAddress(address);
			productApply.setSex(sex);
			productApply.setPersonnelType(personnelType);
			productApply.setProductType(productType);
			productApply.setApplyCentAmount(applyAmount * 100);
			productApply.setDeadline(deadline);

			bean.doProductApply(productApply);

			if (StringUtils.isNotBlank(productApply.getId())) {
				json = QwyUtil.getJSONString("ok", "操作成功");
			} else {
				json = QwyUtil.getJSONString("err", "操作失败");
			}

		} catch (Exception e) {
			e.printStackTrace();
			json = QwyUtil.getJSONString("err", "操作失败");
		}

		try {
			QwyUtil.printJSON(response, json);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
