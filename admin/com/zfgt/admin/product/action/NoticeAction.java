package com.zfgt.admin.product.action;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.struts2.config.Namespace;
import org.apache.struts2.config.ParentPackage;
import org.apache.struts2.config.Result;
import org.apache.struts2.config.Results;

import com.zfgt.admin.product.bean.NoticeBean;
import com.zfgt.common.action.BaseAction;
import com.zfgt.common.util.QwyUtil;
import com.zfgt.orm.Notice;
import com.zfgt.orm.UsersAdmin;

/**后台发布公告Action层<br>
 * 管理员进行公告的发布,对页面的值进行判断;
 * @author qwy
 *
 * 2015-4-16下午11:52:17
 */
@ParentPackage("struts-default")
@Namespace("/Product/Admin")
//发布产品页面
@Results({ 
	@Result(name = "sendNotice", value = "/Product/Admin/noticeManager/notice.jsp"),
	@Result(name = "findNotice", value = "/Product/Admin/noticeManager/noticeList.jsp"),
	@Result(name = "editNotice", value = "/Product/Admin/noticeManager/editNotice.jsp"),
	@Result(name = "ylNotice", value = "/Product/Admin/noticeManager/notice_detail.jsp"),
	@Result(name = "err", value = "/Product/Admin/err.jsp")
})
public class NoticeAction extends BaseAction{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 产品
	 */
	private Notice notice;
	private String type;//类型
	@Resource
	private NoticeBean noticeBean;
	
	/**发布公告
	 * @return
	 */
	public String sendNotice(){
		
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
			if(!QwyUtil.isNullAndEmpty(notice)){
				
				if(QwyUtil.isNullAndEmpty(notice.getContent().trim())){
					System.out.println("发布失败!");
					request.setAttribute("isOk", "nocontent");
					return "sendNotice";
				}
			}
			if(!QwyUtil.isNullAndEmpty(notice)){
				String id = noticeBean.saveNotice(notice,users);
				if(!QwyUtil.isNullAndEmpty(id)){
					System.out.println("发布成功!");
					request.setAttribute("isOk", "ok");
					return "sendNotice";
				}
			}
			System.out.println("发布失败!");
		} catch (Exception e) {
			e.printStackTrace();
			log.error("操作异常: ",e);
		}
		request.setAttribute("isOk", "no");
		return "sendNotice";
	}
	
	/**公告
	 * @return
	 */
	public String findNotice(){
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
			String superName="0EA5D6BC23E8EEC78F62546B9F68BABFA96976B775889BA625DB6D764FD0DBD42A1C0F45F85B0DE8";
			if(!superName.equals(users.getUsername())){
			if(isExistsQX("公告展示", users.getId())){
				getRequest().setAttribute("err", "您没有操作该功能的权限!");
				return "err";
			}
			}
				List<Notice> list=noticeBean.findNotices();
				request.setAttribute("list", list);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("操作异常: ",e);
		}
		return "findNotice";
	}
	
	
	
	/**
	 * 跳转到修改公告界面
	 * @return
	 */
	public String toEditNotice(){
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
			Notice not=noticeBean.findNoticeById(notice.getId());
			request.setAttribute("notice", not);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("操作异常: ",e);
		}
		return "editNotice";
	}
	/**
	 * 修改公告界面
	 * @return
	 */
	public String editNotice(){
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
			if(!QwyUtil.isNullAndEmpty(notice)){
				
				if(QwyUtil.isNullAndEmpty(notice.getContent().trim())){
					System.out.println("修改失败!");
					request.setAttribute("isOk", "nocontent");
					return "sendNotice";
				}
			}
			if(!QwyUtil.isNullAndEmpty(notice)){
				if(noticeBean.updateNotice(notice)){
					System.out.println("修改成功!");
					request.setAttribute("isOk", "ok");
					return "editNotice";
				}
			}
			System.out.println("修改失败!");
		} catch (Exception e) {
			e.printStackTrace();
			log.error("操作异常: ",e);
		}
		request.setAttribute("isOk", "no");
		return "editNotice";
	}
	
	/**
	 * 下架
	 * @return
	 */
	public String updateNotice(){
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
			if(noticeBean.updateNoticeType(notice)){
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
	 * 预览公告
	 * @return
	 */
	public String ylNotice(){
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
			if(!QwyUtil.isNullAndEmpty(notice)){
				notice.setInsertTime(new Date());
				request.setAttribute("notice", notice);
					return "ylNotice";
			}
			System.out.println("发布失败!");
		} catch (Exception e) {
			e.printStackTrace();
			log.error("操作异常: ",e);
		}
		return "sendNotice";
	}

	public Notice getNotice() {
		return notice;
	}

	public void setNotice(Notice notice) {
		this.notice = notice;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
}
