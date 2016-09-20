package com.zfgt.account.action;

import javax.annotation.Resource;

import org.apache.struts2.config.Namespace;
import org.apache.struts2.config.ParentPackage;
import org.apache.struts2.config.Result;
import org.apache.struts2.config.Results;

import com.zfgt.account.bean.CoinpPurseBean;
import com.zfgt.common.action.BaseAction;
import com.zfgt.common.bean.SystemConfigBean;

/**
 * 
 * @author bym
 *
 */
@ParentPackage("struts-default")
@Namespace("/Product/User")
// 理财产品
@Results({ @Result(name = "myCoinPurse", value = "/Product/User/myCoinPurse.jsp") })
public class CoinPurseAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Resource
	SystemConfigBean systemConfigBean;
	
	@Resource
	CoinpPurseBean coinpPurseBean;

	public String myCoinPurse(){
		
		return "myCoinPurse";
	}
}
