package com.zfgt.admin.product.action;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.struts2.config.Namespace;
import org.apache.struts2.config.ParentPackage;
import org.apache.struts2.config.Result;
import org.apache.struts2.config.Results;

import com.zfgt.admin.product.bean.AdminLogBean;
import com.zfgt.admin.product.bean.DeptBean;
import com.zfgt.admin.product.bean.DistributePermissionBean;
import com.zfgt.admin.product.bean.UsersAdminBean;
import com.zfgt.common.action.BaseAction;
import com.zfgt.common.util.PageUtil;
import com.zfgt.common.util.QwyUtil;
import com.zfgt.orm.Dept;
import com.zfgt.orm.UsersAdmin;

/**
 * @author 覃文勇
 * @createTime 2015-8-11上午9:04:45
 */
@SuppressWarnings("serial")
@ParentPackage("struts-default")
@Namespace("/Product/Admin")
@Results({ @Result(name = "list", value = "/Product/Admin/functionManager/usersAdminList.jsp"),
	       @Result(name = "toAdd", value = "/Product/Admin/functionManager/usersAdminAdd.jsp"),
	       @Result(name = "toDeptAdd", value = "/Product/Admin/functionManager/deptAdd.jsp"),
	   	   @Result(name = "err", value = "/Product/Admin/err.jsp")
})
public class UsersAdminAction extends BaseAction{
	@Resource
	private UsersAdminBean bean;
	@Resource
	private DeptBean deptBean;
	@Resource
	private AdminLogBean adminLogBean;
	@Resource
	private DistributePermissionBean distributePermissionBean;
	private UsersAdmin admin;
	private Integer currentPage = 1;
	private Integer pageSize = 20;
	private String password2;
	private String id;
/**
 * 查询后台用户列表
 * @return
 */
 public String findUsersAdminList(){
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
			if(isExistsQX("管理员列表", admin.getId())){
				getRequest().setAttribute("err", "您没有操作该功能的权限!");
				return "err";
			}
			}
		 PageUtil<UsersAdmin> pageUtil=new PageUtil<UsersAdmin>();
		 pageUtil.setCurrentPage(currentPage);
	     pageUtil.setPageSize(pageSize);
	     if("0".equals(admin.getUserStatus())){//状态;0:帐号可用; 1:帐号不可用;
			 bean.findListByType(pageUtil,admin.getUserType(),admin.getDeptId());
			 request.setAttribute("pageUtil", pageUtil);
	     }
		if(!QwyUtil.isNullAndEmpty(pageUtil)){
			Map<Long, String> map=distributePermissionBean.findModulByUserAdminId();
			request.setAttribute("list", pageUtil.getList());
			request.setAttribute("map", map);
		}
	} catch (Exception e) {
		// TODO: handle exception
		e.printStackTrace();
	}
	 return "list"; 
 }
 
 public String toUsersAdminAdd(){
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
			if(isExistsQX("添加管理员", admin.getId())){
				getRequest().setAttribute("err", "您没有操作该功能的权限!");
				return "err";
			}
			}
			List<Dept> deptList=deptBean.findDeptList(admin.getDeptId());
			if(!QwyUtil.isNullAndEmpty(deptList)){
			   request.setAttribute("deptList", deptList);   
			}else{
				if(admin.getUserType()==-1L){
					 request.setAttribute("message", "部门表为空，请先添加部门");
					 return "toDeptAdd";
				}
			}
	} catch (Exception e) {
		// TODO: handle exception
		e.printStackTrace();
	}
	 return "toAdd";
 }
 
 public String usersAdminAdd(){
	
	 try {
		 UsersAdmin usersAdmin=(UsersAdmin)getRequest().getSession().getAttribute("usersAdmin");
			if(admin==null){
				request.setAttribute("message", "请填写信息");
			}else{
				String userName = admin.getUsername();
				if (userName == null || userName.length() < 6) {
					request.setAttribute("message", "用户名不能少于6位");
				}
				if(!QwyUtil.isNullAndEmpty(userName)){
					//userName = new String (userName.getBytes("ISO-8859-1"),"UTF-8");
					if(userName.length()<6){							
						request.setAttribute("message", "用户名长度至不能少于6位");							
					} else if(userName.length()>16){						
						request.setAttribute("message", "用户名长度不能大于16位");
						
					 }else{
					UsersAdmin newAdmin=bean.getAdminByUsername(userName);
					if(newAdmin!=null){
						request.setAttribute("message", "用户名已存在");	
					}else{
						//String passWord=new String (admin.getPassword().getBytes("ISO-8859-1"),"UTF-8");
						String passWord=admin.getPassword();
						if(passWord.length()<6){							
							request.setAttribute("message", "密码长度至不能少于6位");							
						} else if(passWord.length()>16){						
							request.setAttribute("message", "密码长度不能大于16位");
							
						 }else{
						if(!password2.equals(passWord)){							
							request.setAttribute("message", "两次密码不一致");		
						}else{//确认密码成功
							if(usersAdmin.getUserType().equals(admin.getUserType())){
								request.setAttribute("message", "没有权限添加相同等级管理员");
							}else{
							String id=bean.addUserAdmin(userName,passWord,admin.getDeptId(),admin.getUserType(),usersAdmin.getId());						
							if(!QwyUtil.isNullAndEmpty(id)){
								adminLogBean.saveUsersLog(usersAdmin.getId(),Long.parseLong(id));
								request.setAttribute("message", "添加成功");		
							}else{
								request.setAttribute("message", "添加失败");			
							}	
						  }
						}
					  }
					}
				 }
				}				
			}
			
			
	} catch (Exception e) {
		// TODO: handle exception
		e.printStackTrace();
	}
	 return toUsersAdminAdd();
 }
	/**修改状态，禁用或启用
	 * @return
	 */
	public String changeStatus(){
		String json = "";
		try {
			UsersAdmin admin=(UsersAdmin)getRequest().getSession().getAttribute("usersAdmin");
			if(QwyUtil.isNullAndEmpty(admin)){
				json = QwyUtil.getJSONString("err", "管理员未登录");
				QwyUtil.printJSON(getResponse(), json);
				//管理员没有登录;
				return null;
			}
			if(!QwyUtil.isNullAndEmpty(id)){
			    UsersAdmin user=bean.findUsersAdminById(Long.parseLong(id));
			    if(!QwyUtil.isNullAndEmpty(user)){
			    	if("0".equals(user.getUserStatus())){//当前状态为0（可用）,该为1（禁用）
			    		bean.updateByStatus(user,"1");
			    		json = QwyUtil.getJSONString("ok", "已禁用");
			    	}else{//当前状态为0（可用）,该为1（禁用）
			    		bean.updateByStatus(user,"0");
			    		json = QwyUtil.getJSONString("ok", "已可用");			    		
			    	}
			    	adminLogBean.saveUsersLog(admin.getId(),Long.parseLong(id));
			    }
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			json = QwyUtil.getJSONString("error", "操作异常");
		}
		try {
			QwyUtil.printJSON(getResponse(), json);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
public UsersAdminBean getBean() {
	return bean;
}
public void setBean(UsersAdminBean bean) {
	this.bean = bean;
}

public UsersAdmin getAdmin() {
	return admin;
}

public void setAdmin(UsersAdmin admin) {
	this.admin = admin;
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

public String getPassword2() {
	return password2;
}

public void setPassword2(String password2) {
	this.password2 = password2;
}

public String getId() {
	return id;
}

public void setId(String id) {
	this.id = id;
}
 
}
