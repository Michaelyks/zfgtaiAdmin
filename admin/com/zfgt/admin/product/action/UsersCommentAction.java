package com.zfgt.admin.product.action;

import javax.annotation.Resource;

import org.apache.struts2.config.Namespace;
import org.apache.struts2.config.ParentPackage;
import org.apache.struts2.config.Result;
import org.apache.struts2.config.Results;

import com.zfgt.account.bean.InvestorsRecordBean;
import com.zfgt.account.bean.MyAccountBean;
import com.zfgt.account.bean.UserInfoBean;
import com.zfgt.admin.product.bean.UsersCommentBean;
import com.zfgt.common.action.BaseAction;
import com.zfgt.common.util.PageUtil;
import com.zfgt.common.util.QwyUtil;
import com.zfgt.login.bean.RegisterUserBean;
import com.zfgt.orm.Comments;
import com.zfgt.orm.Coupon;
import com.zfgt.orm.Investors;
import com.zfgt.orm.Users;
import com.zfgt.orm.UsersAdmin;
import com.zfgt.orm.UsersInfo;
/**用户评论
 * @author qwy
 *
 * 2015-04-20 12:58:29
 */
@SuppressWarnings("serial")
@ParentPackage("struts-default")
@Namespace("/Product/Admin")
//理财产品
@Results({ 
	@Result(name = "comment", value = "/Product/Admin/operationManager/usersComments.jsp"),
	@Result(name = "login", value = "/Product/loginBackground.jsp" ,type=org.apache.struts2.dispatcher.ServletRedirectResult.class),
	@Result(name = "error", value = "/Product/error.jsp"),
    @Result(name = "err", value = "/Product/Admin/err.jsp")
	
})
public class UsersCommentAction extends BaseAction {
	@Resource
	private UsersCommentBean bean;
	private Integer currentPage = 1;//当前页
	private Integer pageCount;//总页数
	private Integer pageSize = 50;
	
	/**显示用户评论;
	 * @return
	 */
	public String showUsersComment(){
		try {
			request = getRequest();
			UsersAdmin users = (UsersAdmin)getRequest().getSession().getAttribute("usersAdmin");
			if(QwyUtil.isNullAndEmpty(users)){
				//管理员没有登录;
				return "login";
			}
			String superName="0EA5D6BC23E8EEC78F62546B9F68BABFA96976B775889BA625DB6D764FD0DBD42A1C0F45F85B0DE8";
			if(!superName.equals(users.getUsername())){
			if(isExistsQX("用户评论", users.getId())){
				getRequest().setAttribute("err", "您没有操作该功能的权限!");
				return "err";
			}
			}
			System.out.println("显示用户评论");
			PageUtil<Comments> pageUtil = new PageUtil<Comments>(pageSize,currentPage);
			bean.loadComments(pageUtil,null);
			request.setAttribute("pageUtil", pageUtil);
			if (!QwyUtil.isNullAndEmpty(pageUtil)) {
			request.setAttribute("list", pageUtil.getList());
			}
		} catch (Exception e) {
			log.error("操作异常: ",e);
			//request.setAttribute("errMsg", "发生了错误");
			return "error";
		}
		return "comment";
	}

	public Integer getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(Integer currentPage) {
		this.currentPage = currentPage;
	}

	public Integer getPageCount() {
		return pageCount;
	}

	public void setPageCount(Integer pageCount) {
		this.pageCount = pageCount;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	

}
