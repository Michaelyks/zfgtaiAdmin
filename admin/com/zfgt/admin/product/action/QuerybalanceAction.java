package com.zfgt.admin.product.action;

import java.util.Map;

import javax.annotation.Resource;

import org.apache.struts2.config.Namespace;
import org.apache.struts2.config.ParentPackage;
import org.apache.struts2.config.Result;
import org.apache.struts2.config.Results;

import com.zfgt.admin.product.bean.SmsRecordBean;
import com.zfgt.common.action.BaseAction;
import com.zfgt.common.bean.SystemConfigBean;
import com.zfgt.common.util.QwyUtil;
import com.zfgt.common.util.SMSUtil;
import com.zfgt.orm.SmsRecord;
import com.zfgt.orm.UsersAdmin;

/**
 * 亿美短信余额
 * 
 * @author qwy
 *
 *         2015-04-20 12:58:29
 */
@SuppressWarnings("serial")
@ParentPackage("struts-default")
@Namespace("/Product/Admin")
// 理财产品
@Results({ @Result(name = "queryBalance", value = "/Product/Admin/fundsManager/ymSMSCount.jsp"), @Result(name = "err", value = "/Product/Admin/err.jsp") })
public class QuerybalanceAction extends BaseAction {
	@Resource
	SmsRecordBean bean;
	@Resource
	SystemConfigBean systemConfigBean;
	private SmsRecord smsRecord;

	/**
	 * 查询亿美余额;
	 * 
	 * @return
	 */
	public String queryBalance() {
		String json = "";
		try {
			UsersAdmin users = (UsersAdmin) getRequest().getSession().getAttribute("usersAdmin");
			if (QwyUtil.isNullAndEmpty(users)) {
				json = QwyUtil.getJSONString("err", "管理员未登录");
				QwyUtil.printJSON(getResponse(), json);
				// 管理员没有登录;
				return null;
			}
			String superName = "0EA5D6BC23E8EEC78F62546B9F68BABFA96976B775889BA625DB6D764FD0DBD42A1C0F45F85B0DE8";
			if (!superName.equals(users.getUsername())) {
				if (isExistsQX("发送短信", users.getId())) {
					getRequest().setAttribute("err", "您没有操作该功能的权限!");
					return "err";
				}
			}
			Map<String, Object> map = SMSUtil.queryBalance();
			request.setAttribute("map", map);
			request.setAttribute("message", map.get("message"));
		} catch (Exception e) {
			log.error("操作异常: ", e);
			// request.setAttribute("errMsg", "发生了错误");
			return "error";
		}
		return "queryBalance";
	}

	// 发送短信
	public String sendMessage() {
		String json = "";
		try {
			UsersAdmin admin = (UsersAdmin) getRequest().getSession().getAttribute("usersAdmin");
			if (QwyUtil.isNullAndEmpty(admin)) {
				json = QwyUtil.getJSONString("err", "管理员未登录");
				QwyUtil.printJSON(getResponse(), json);
				// 管理员没有登录;
				return null;
			}
			if (!QwyUtil.isNullAndEmpty(smsRecord)) {
				String mobileString = smsRecord.getMobile();
				String[] strArray = null;
				strArray = smsRecord.getMobile().split(","); // 拆分字符为","
																// ,然后把结果交给数组strArray
				for (int i = 0; i < strArray.length; i++) {
					if (!QwyUtil.verifyPhone(strArray[i])) {
						json = QwyUtil.getJSONString("error", "手机号有误：" + strArray[i]);
						break;
					}
				}
				if (QwyUtil.isNullAndEmpty(json)) {// 判断手机格式
					String topContent = systemConfigBean.findSystemConfig().getSmsQm();// 一条短信长度是67
					if (!QwyUtil.isNullAndEmpty(smsRecord.getSmsContent())) {
						if (smsRecord.getSmsContent().length() > 500) {
							json = QwyUtil.getJSONString("error", "短信内容过长");
						} else {
							String content = topContent + smsRecord.getSmsContent();
							Map<String, Object> map = SMSUtil.sendYzm2(mobileString, null, content);
							if (!QwyUtil.isNullAndEmpty(map)) {
								bean.addSmsRecord(mobileString, content, map.get("error").toString(), admin.getId());
								json = QwyUtil.getJSONString("ok", "发送短信成功");
							} else {
								json = QwyUtil.getJSONString("error", "发送短信异常");
							}
						}
					} else {
						json = QwyUtil.getJSONString("error", "短信内容不能为空");
					}
				}
			} else {
				json = QwyUtil.getJSONString("error", "请填写表单不能为空");
			}
		} catch (Exception e) {
			e.printStackTrace();
			json = QwyUtil.getJSONString("error", "操作异常");
		}
		try {
			QwyUtil.printJSON(getResponse(), json);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public SmsRecord getSmsRecord() {
		return smsRecord;
	}

	public void setSmsRecord(SmsRecord smsRecord) {
		this.smsRecord = smsRecord;
	}

}
