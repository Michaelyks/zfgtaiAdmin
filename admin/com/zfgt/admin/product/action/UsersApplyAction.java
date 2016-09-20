package com.zfgt.admin.product.action;

import java.io.IOException;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.apache.struts2.config.Namespace;
import org.apache.struts2.config.ParentPackage;
import org.apache.struts2.config.Result;
import org.apache.struts2.config.Results;

import com.zfgt.admin.product.bean.AdminLogBean;
import com.zfgt.admin.product.bean.UsersApplyBean;
import com.zfgt.common.action.BaseAction;
import com.zfgt.common.util.PageUtil;
import com.zfgt.common.util.QwyUtil;
import com.zfgt.login.bean.RegisterUserBean;
import com.zfgt.orm.Account;
import com.zfgt.orm.SystemConfig;
import com.zfgt.orm.Users;
import com.zfgt.orm.UsersAdmin;
import com.zfgt.orm.UsersApply;
import com.zfgt.orm.UsersInfo;

/**后台账户管理——用户申请记录
 * @author 覃文勇
 * @createTime 2015-8-14下午3:34:12
 */
@ParentPackage("struts-default")
@Namespace("/Product/Admin")
	@Results({ 
		@Result(name = "usersApplyList", value = "/Product/Admin/bankCard/usersApplyList.jsp"),
		@Result(name = "toModifyUsers", value = "/Product/Admin/bankCard/modifyUsers.jsp"),
		@Result(name = "err", value = "/Product/Admin/err.jsp")
})
public class UsersApplyAction  extends BaseAction{
	private Logger log=Logger.getLogger(getClass());
	@Resource
	UsersApplyBean bean;
	@Resource
	AdminLogBean adminLogBean;
	@Resource 
	RegisterUserBean registerUserBean;
	
	private String name;
	private String insertTime;
	private String status;//1:待处理  2：已处理
	private Integer currentPage = 1;
	private Integer pageSize = 20;
	private Users users;
	
	private String applyId;
	private String vcode;
	
	private String userName;
	
	
	/**查询用户申请列表
     * @author 覃文勇
	 * @return
	 */
	public String loadUsersApplyList(){
		String json = "";
		try {
			UsersAdmin users = (UsersAdmin)getRequest().getSession().getAttribute("usersAdmin");
			if(QwyUtil.isNullAndEmpty(users)){
				json = QwyUtil.getJSONString("err", "管理员未登录");
				QwyUtil.printJSON(getResponse(), json);
				//管理员没有登录;
				return null;
			}
			String superName="0EA5D6BC23E8EEC78F62546B9F68BABFA96976B775889BA625DB6D764FD0DBD42A1C0F45F85B0DE8";
			if(!superName.equals(users.getUsername())){
			if(isExistsQX("账号变更列表", users.getId())){
				getRequest().setAttribute("err", "您没有操作该功能的权限!");
				return "err";
			}
			}
			PageUtil<UsersApply> pageUtil = new PageUtil<UsersApply>();
			pageUtil.setCurrentPage(currentPage);
			pageUtil.setPageSize(pageSize);
			StringBuffer url = new StringBuffer();
			url.append(getRequest().getServletContext().getContextPath());
			url.append("/Product/Admin/usersApply!loadUsersApplyList.action?name="+name+"&status="+status);
			if(!QwyUtil.isNullAndEmpty(insertTime)){
				url.append("&insertTime=");
				url.append(insertTime);
			}
			pageUtil.setPageUrl(url.toString());
			pageUtil = bean.findUsersApplyList(pageUtil, insertTime,name,status);
			getRequest().setAttribute("insertTime", insertTime);
			if(!QwyUtil.isNullAndEmpty(pageUtil)){
				getRequest().setAttribute("pageUtil", pageUtil);
				getRequest().setAttribute("list", pageUtil.getList());
				return "usersApplyList";
			}
		} catch (Exception e) {
			log.error("操作异常: ",e);
			json = QwyUtil.getJSONString("err", "查询记录异常");
		}
		return null;
	}

	/**
	 * 请求更改帐号，并且发送验证码
	 * @return
	 */
	public String sendYzm(){
		String json="";
		try {
			UsersAdmin us = (UsersAdmin) getRequest().getSession()
					.getAttribute("usersAdmin");
			if (QwyUtil.isNullAndEmpty(us)) {
				json = QwyUtil.getJSONString("err", "管理员未登录");
				QwyUtil.printJSON(getResponse(), json);
				//管理员没有登录;
				return null;
			}
			if(!QwyUtil.isNullAndEmpty(name)){
				Users onlyusers=bean.findUsersByUsername(name);//判断用户帐号的唯一性
				if(onlyusers!=null){
					json = QwyUtil.getJSONString("error", "该用户帐号已被占用");
				}else{
					json=registerUserBean.sendYZM(name, "5");//发送验证码
					if(QwyUtil.isNullAndEmpty(json)){
						json = QwyUtil.getJSONString("ok", "发送验证码成功");
					}else{
						json = QwyUtil.getJSONString("error", json);
					}
				}
			}				
				QwyUtil.printJSON(getResponse(), json);
				return null;
			
		} catch (Exception e) {
			e.printStackTrace();
			log.error("操作异常: ",e);
			json = QwyUtil.getJSONString("error", "系统错误");
		}
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
	 * 验证码发送成功，进入验证页面
	 * @return
	 */
	public String toModifyUsers(){
		String json = "";
		try {
			UsersAdmin admin = (UsersAdmin)getRequest().getSession().getAttribute("usersAdmin");
			if(QwyUtil.isNullAndEmpty(admin)){
				json = QwyUtil.getJSONString("err", "管理员未登录");
				QwyUtil.printJSON(getResponse(), json);
				//管理员没有登录;
				return null;
			}
			if(!QwyUtil.isNullAndEmpty(applyId)){
				UsersApply  usersApply=bean.findById(Long.parseLong(applyId));
				users=bean.findUsersByUsersId(usersApply.getUsersId());
			    getRequest().setAttribute("users", users);
				getRequest().setAttribute("applyId", usersApply.getId());
				getRequest().setAttribute("name", name);
				return "toModifyUsers";
			 }
		} catch (Exception e) {
			// TODO: handle exception
			log.error("操作异常: ",e);
			json = QwyUtil.getJSONString("err", "该用户异常，请联系后台管理员");
		}

		return null;
	}

	/**修改用户帐号
	 * @author 覃文勇
	 * @return
	 */
	public String modifyUsers(){
		String json="";
		try {
			UsersAdmin admin=(UsersAdmin)getRequest().getSession().getAttribute("usersAdmin");
			if(QwyUtil.isNullAndEmpty(admin)){
				json = QwyUtil.getJSONString("err", "管理员未登录");
				QwyUtil.printJSON(getResponse(), json);
				//管理员没有登录;
				return null;
			}						
				if(!QwyUtil.isNullAndEmpty(name)){
					String code = registerUserBean.getYzm(name, "5");
					if (code.equals(vcode)) {//验证成功，更新用户帐号
						if(!QwyUtil.isNullAndEmpty(applyId)){
							UsersApply usersApply=bean.findById(Long.parseLong(applyId));
							if(usersApply!=null){
								Users oldUsers=bean.findUsersByUsersId(usersApply.getUsersId());
								UsersInfo usersInfo=bean.getUserInfoById(usersApply.getUsersId());
								if(oldUsers!=null&&usersInfo!=null){
									if(bean.modifyUsers(oldUsers, name)){//更新用户帐号	
										bean.modifyUsersApply(usersApply,admin.getId());//修改申请记录
										bean.modifyUsersInfo(usersInfo, name);//更新用户信息手机号
										bean.modifyContract(usersInfo.getId(),name);//更新合同用户名
										registerUserBean.setMobile(oldUsers.getId());//修改号码所在地	
										adminLogBean.saveApplyLog(usersApply.getId(), admin.getId());//保存操作记录
										json = QwyUtil.getJSONString("ok", "修改成功");
									}									
								}else{
									json = QwyUtil.getJSONString("error", "用户信息异常");		
								}
							}else{
								json = QwyUtil.getJSONString("error", "申请信息异常");								
							}
						}else{
							json = QwyUtil.getJSONString("error", "申请信息异常");							
						}
						
					}else{
						json = QwyUtil.getJSONString("error", "验证不成功,请核对验证码");							
						}
				}else{
					json = QwyUtil.getJSONString("error", "用户名不能为空");
				}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			json = QwyUtil.getJSONString("error", "操作异常");
		}
		try {
			QwyUtil.printJSON(getResponse(), json);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			log.error("操作异常: ",e);
		}
		return null;
	}
	
	public String findUsersInfoByUsersName(){
		String json="";
		try {
			if(!QwyUtil.isNullAndEmpty(userName)){
			Users oldUsers=bean.findUsersByUsername(userName);
			if(oldUsers==null){
				json = QwyUtil.getJSONString("err", "该用户名不存在");				
			}else{
				UsersInfo usersInfo=bean.getUserInfoById(oldUsers.getId());
				if(usersInfo!=null){
					json = QwyUtil.getJSONString("ok", usersInfo.getRealName());
				}
			}
		 }
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			log.error("操作异常: ",e);
			json = QwyUtil.getJSONString("error", "操作异常");
		}
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
	 * 无验证变更帐号（只有超级管理员有权限）
	 * @return
	 */
	public String modifyUsersBySuperAdmin(){
		String json="";
		try {
			UsersAdmin admin=(UsersAdmin)getRequest().getSession().getAttribute("usersAdmin");
			if(QwyUtil.isNullAndEmpty(admin)){
				json = QwyUtil.getJSONString("err", "管理员未登录");
				QwyUtil.printJSON(getResponse(), json);
				//管理员没有登录;
				return null;
			}
			String superName="0EA5D6BC23E8EEC78F62546B9F68BABFA96976B775889BA625DB6D764FD0DBD42A1C0F45F85B0DE8";
			if(!superName.equals(admin.getUsername())){
				if(isExistsQX("无验证变更帐号", admin.getId())){
					json = QwyUtil.getJSONString("error", "您没有操作该功能的权限!");
					return null;
				}
			}
			if(!QwyUtil.isNullAndEmpty(userName)&&!QwyUtil.isNullAndEmpty(name)){
				if(QwyUtil.verifyPhone(userName)){
					Users oldUsers=bean.findUsersByUsername(userName);
					if(oldUsers!=null){
						if(QwyUtil.verifyPhone(name)){
							Users checkUsers=bean.findUsersByUsername(name);
							if(checkUsers==null){
								if(bean.modifyUsers(oldUsers, name)){//更新用户帐号
									UsersInfo usersInfo=bean.getUserInfoById(oldUsers.getId());
									bean.modifyUsersInfo(usersInfo, name);//更新用户信息手机号
									bean.modifyContract(usersInfo.getId(),name);//更新合同用户名
									registerUserBean.setMobile(oldUsers.getId());//修改号码所在地
									json = QwyUtil.getJSONString("ok", "修改成功");
								}else{
									json = QwyUtil.getJSONString("error", "修改失败");
								}
							}else{
								json = QwyUtil.getJSONString("error", "该新帐号已被占用");
							}

						}else{
							json = QwyUtil.getJSONString("error", "该新帐号格式不正确");
						}

					}
				}else{
					json = QwyUtil.getJSONString("error", "该旧帐号格式不正确");
				}
			}else{
				json = QwyUtil.getJSONString("error", "新旧帐号都不能为空");
			}
			
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			json = QwyUtil.getJSONString("error", "操作异常");
		}
		try {
			QwyUtil.printJSON(getResponse(), json);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			log.error("操作异常: ",e);
		}
		return null;
	}
	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getInsertTime() {
		return insertTime;
	}
	public void setInsertTime(String insertTime) {
		this.insertTime = insertTime;
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
	public Users getUsers() {
		return users;
	}
	public void setUsers(Users users) {
		this.users = users;
	}

	public String getApplyId() {
		return applyId;
	}

	public void setApplyId(String applyId) {
		this.applyId = applyId;
	}

	public String getVcode() {
		return vcode;
	}

	public void setVcode(String vcode) {
		this.vcode = vcode;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	
}
