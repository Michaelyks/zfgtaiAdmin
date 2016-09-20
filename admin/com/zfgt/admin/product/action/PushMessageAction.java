package com.zfgt.admin.product.action;

import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;

import org.apache.struts2.config.Namespace;
import org.apache.struts2.config.ParentPackage;
import org.apache.struts2.config.Result;
import org.apache.struts2.config.Results;

import com.zfgt.admin.product.bean.PushMessageBean;
import com.zfgt.common.action.BaseAction;
import com.zfgt.common.util.QwyUtil;
import com.zfgt.orm.PushMessage;
import com.zfgt.orm.UsersAdmin;

/**
 * 客户端推送消息管理;
 * @author qwy
 *
 * @createTime 2015-4-27下午3:51:34
 */
@SuppressWarnings("serial")
@ParentPackage("struts-default")
@Namespace("/Product/Admin")
	//发布产品页面
	@Results({ 
		@Result(name = "loadPushMessage", value = "/Product/Admin/pushMessage/message.jsp"),
		@Result(name = "PushMessage", value = "/Product/Admin/pushMessage/pushMessage.jsp"),
		@Result(name = "err", value = "/Product/Admin/err.jsp")
})
public class PushMessageAction extends BaseAction {
	@Resource
	PushMessageBean bean;
	private PushMessage pushMessage;
	public String topic="";
	/**
	 * 加载推送消息
	 * @return
	 */
	public String loadPushMessage(){
		String json = "";
		try {
			UsersAdmin users = (UsersAdmin) getRequest().getSession()
					.getAttribute("usersAdmin");
			if (QwyUtil.isNullAndEmpty(users)) {
				json = QwyUtil.getJSONString("err", "管理员未登录");
				QwyUtil.printJSON(getResponse(), json);
				//管理员没有登录;
				return null;
			}
			String superName="0EA5D6BC23E8EEC78F62546B9F68BABFA96976B775889BA625DB6D764FD0DBD42A1C0F45F85B0DE8";
			if(!superName.equals(users.getUsername())){
			if(isExistsQX("推送消息展示", users.getId())){
				getRequest().setAttribute("err", "您没有操作该功能的权限!");
				return "err";
			}
			}
			if(QwyUtil.isNullAndEmpty(pushMessage)){
				pushMessage=new PushMessage();
			}
			List<PushMessage> list=bean.findPushMessage(null,null);
			getRequest().setAttribute("list", list);
			getRequest().setAttribute("PushMessages", pushMessage);
			return "loadPushMessage";
		} catch (Exception e) {
			log.error("操作异常: ",e);
			json = QwyUtil.getJSONString("err", "查询记录异常");
		}
		return "loadPushMessage";
	}
	
	/**
	 * 保存推送消息信息
	 * @return
	 */
	public String savePushMessage() {
		try {
			String json = "";
			UsersAdmin users = (UsersAdmin) getRequest().getSession()
					.getAttribute("usersAdmin");
			if (QwyUtil.isNullAndEmpty(users)) {
				json = QwyUtil.getJSONString("err", "管理员未登录");
				QwyUtil.printJSON(getResponse(), json);
				//管理员没有登录;
				return null;
			}
			if(!QwyUtil.isNullAndEmpty(pushMessage)){
				PushMessage PushMessage = bean.savePushMessage(pushMessage,topic);
				if(!QwyUtil.isNullAndEmpty(PushMessage)){
					System.out.println("发布成功!");
					request.setAttribute("isOk", "ok");
					return "PushMessage";
				}
			}
			System.out.println("发布失败!");
		} catch (Exception e) {
			e.printStackTrace();
			log.error("操作异常: ",e);
		}
		request.setAttribute("isOk", "no");
		return "PushMessage";
	}
	
	/**
	 * 立即发生推送消息
	 */
	public String nowSend() {
		String json = "";
		try {
			UsersAdmin users = (UsersAdmin) getRequest().getSession()
					.getAttribute("usersAdmin");
			if (QwyUtil.isNullAndEmpty(users)) {
				json = QwyUtil.getJSONString("err", "管理员未登录");
				QwyUtil.printJSON(getResponse(), json);
				//管理员没有登录;
				return null;
			}
			topic = QwyUtil.isNullAndEmpty(topic)?"baiyimao":topic;
			if(bean.push(pushMessage.getId())){
				request.setAttribute("update", "ok");
				json = QwyUtil.getJSONString("ok", "成功");
				QwyUtil.printJSON(getResponse(), json);
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error("操作异常: ",e);
		}
		json = QwyUtil.getJSONString("error", "失败");
		try {
			QwyUtil.printJSON(getResponse(), json);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			log.error("操作异常: ",e);
		}
		return null;	
	}
	/**
	 * 根据id修改状态
	 * @return
	 */
	public String updateStatuById() {
		String json = "";
		try {
			UsersAdmin users = (UsersAdmin) getRequest().getSession()
					.getAttribute("usersAdmin");
			if (QwyUtil.isNullAndEmpty(users)) {
				json = QwyUtil.getJSONString("err", "管理员未登录");
				QwyUtil.printJSON(getResponse(), json);
				//管理员没有登录;
				return null;
			}
			if(bean.updateStatus(pushMessage.getId(),"3")){
				request.setAttribute("update", "ok");
				json = QwyUtil.getJSONString("ok", "成功");
				QwyUtil.printJSON(getResponse(), json);
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error("操作异常: ",e);
		}
		json = QwyUtil.getJSONString("error", "失败");
		try {
			QwyUtil.printJSON(getResponse(), json);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			log.error("操作异常: ",e);
		}
		return null;	
	}

	public PushMessage getPushMessage() {
		return pushMessage;
	}

	public void setPushMessage(PushMessage pushMessage) {
		this.pushMessage = pushMessage;
	}

	public String getTopic() {
		return topic;
	}

	public void setTopic(String topic) {
		topic = QwyUtil.isNullAndEmpty(topic)?"baiyimao":topic;
		this.topic = topic;
	}
	

	
	
	
}
