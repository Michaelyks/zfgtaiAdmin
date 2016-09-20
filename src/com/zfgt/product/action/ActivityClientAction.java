package com.zfgt.product.action;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.apache.struts2.config.Namespace;
import org.apache.struts2.config.ParentPackage;
import org.apache.struts2.config.Result;
import org.apache.struts2.config.Results;

import com.zfgt.common.util.QwyUtil;
import com.zfgt.product.bean.ActivityClientBean;


/**
 * 加群领取50元投资券Action层
 * @author oi
 *
 */
@SuppressWarnings("serial")
@ParentPackage("struts-default")
@Namespace("/Product")
@Results({ @Result(name = "doActivity", value = "/activity/jqltzq/jqltzq.jsp")
})
public class ActivityClientAction extends IndexAction {
	private static Logger log = Logger.getLogger(ActivityClientAction.class);
	private String mobileNum;
	private String xlhNum;
	
	@Resource
	private ActivityClientBean activityClientBean;
	
	/**
	 * 加群领取50元投资券
	 * @return
	 */
	public String doActivity(){
		try {
			String message = "";
			if(!QwyUtil.verifyPhone(mobileNum)){
				message = "请输入正确手机号";
			} else if(QwyUtil.isNullAndEmpty(xlhNum)){
				message = "请输入序列号";
			} else if(!QwyUtil.verifyStrLength(xlhNum,4,50)){
				message = "序列号格式错误";
			}
			if(message.equals("")){
				message = activityClientBean.checkMobileAndXlh(mobileNum, xlhNum);
			}
			String json = "";
			if(message.equals("")){
				json = QwyUtil.getJSONString("ok", "投资券已发放到您账户,请注意查收!");
			}else {
				json = QwyUtil.getJSONString("no", message);
			}
			QwyUtil.printJSON(getResponse(), json);
		} catch (Exception e) {
			log.error("操作异常: ",e);
		}
		return null;
	}

	/**
	 * 注册领取888元投资券
	 * @return
	 */
	public String regSendTicket(){
		try {
			String message = "";
			if(!QwyUtil.verifyPhone(mobileNum)){
				message = "请输入正确手机号";
			} 
			if(message.equals("")){
				message = activityClientBean.checkMobileAndXlhSendTicket(mobileNum);
			}
			String json = "";
			if(message.equals("")){
				json = QwyUtil.getJSONString("ok", "投资券已发放到您账户,请注意查收!");
			}else {
				json = QwyUtil.getJSONString("no", message);
			}
			QwyUtil.printJSON(getResponse(), json);
		} catch (Exception e) {
			log.error("操作异常: ",e);
		}
		return null;
	}
	
	public String getMobileNum() {
		return mobileNum;
	}

	public void setMobileNum(String mobileNum) {
		this.mobileNum = mobileNum;
	}

	public String getXlhNum() {
		return xlhNum;
	}

	public void setXlhNum(String xlhNum) {
		this.xlhNum = xlhNum;
	}
}
