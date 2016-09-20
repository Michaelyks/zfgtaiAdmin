package com.zfgt.admin.product.action;

import java.util.List;

import javax.annotation.Resource;

import org.apache.struts2.config.Namespace;
import org.apache.struts2.config.ParentPackage;
import org.apache.struts2.config.Result;
import org.apache.struts2.config.Results;

import com.zfgt.admin.product.bean.RolesBean;
import com.zfgt.common.action.BaseAction;
import com.zfgt.common.util.QwyUtil;
import com.zfgt.orm.Roles;
import com.zfgt.orm.UsersAdmin;

@SuppressWarnings("serial")
@ParentPackage("struts-default")
@Namespace("/Product/Admin")
	@Results({ @Result(name = "list", value = "/Product/Admin/functionManager/rolesList.jsp"),
		       @Result(name = "add", value = "/Product/Admin/functionManager/rolesAdd.jsp"),
		       @Result(name = "err", value = "/Product/Admin/err.jsp")
		
			  
})
public class RolesAction extends BaseAction{
	@Resource
	private RolesBean bean;
	private Roles role;
	private String status;
	
	public String findRoles(){
		String json="";
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
			if(isExistsQX("角色列表展示", users.getId())){
				getRequest().setAttribute("err", "您没有操作该功能的权限!");
				return "err";
			}
			}
			List<Roles> list=bean.findListByStatus(null);
			if(!QwyUtil.isNullAndEmpty(list)){
				request.setAttribute("list", list);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return "list";
	}
	public String rolesAdd(){
		String json="";
		try {
			UsersAdmin admin=(UsersAdmin)getRequest().getSession().getAttribute("usersAdmin");
			if(QwyUtil.isNullAndEmpty(admin)){
				json = QwyUtil.getJSONString("err", "管理员未登录");
				QwyUtil.printJSON(getResponse(), json);
				//管理员没有登录;
				return null;
			}
			if(!QwyUtil.isNullAndEmpty(bean.saveRoles(role,admin))){
				request.setAttribute("message", "添加成功");
			}else{
				request.setAttribute("message", "添加失败");
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return  "add";
	}
	public RolesBean getBean() {
		return bean;
	}
	public void setBean(RolesBean bean) {
		this.bean = bean;
	}
	public Roles getRole() {
		return role;
	}
	public void setRole(Roles role) {
		this.role = role;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	
      
}
