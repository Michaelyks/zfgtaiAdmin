package com.zfgt.admin.product.action;

import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;

import org.apache.struts2.config.Namespace;
import org.apache.struts2.config.ParentPackage;
import org.apache.struts2.config.Result;
import org.apache.struts2.config.Results;

import com.zfgt.admin.product.bean.AdminLogBean;
import com.zfgt.admin.product.bean.DeptBean;
import com.zfgt.common.action.BaseAction;
import com.zfgt.common.util.PageUtil;
import com.zfgt.common.util.QwyUtil;
import com.zfgt.orm.AdminLog;
import com.zfgt.orm.Dept;
import com.zfgt.orm.UsersAdmin;
import com.zfgt.orm.UsersLogin;

@SuppressWarnings("serial")
@ParentPackage("struts-default")
@Namespace("/Product/Admin")
@Results({ 
	@Result(name = "list", value = "/Product/Admin/functionManager/deptList.jsp"),
	@Result(name = "toAdd", value = "/Product/Admin/functionManager/deptAdd.jsp"),
	@Result(name = "toUpdate", value = "/Product/Admin/functionManager/deptUpdate.jsp"),
	@Result(name = "err", value = "/Product/Admin/err.jsp")
})
public class DeptAction  extends BaseAction {
	@Resource
	private DeptBean bean;
	@Resource 
	private AdminLogBean adminLogBean;
	private Dept dept;
	private Integer currentPage = 1;
	private Integer pageSize = 20;
	private String id;
	public String findDeptList(){
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
				if(isExistsQX("部门列表", admin.getId())){
					getRequest().setAttribute("err", "您没有操作该功能的权限!");
					return "err";
				}
				}
			 PageUtil<Dept> pageUtil=new PageUtil<Dept>();
			 pageUtil.setCurrentPage(currentPage);
		     pageUtil.setPageSize(pageSize);
		     if("0".equals(admin.getUserStatus())){//状态;0:帐号可用; 1:帐号不可用;
			    bean.loadDeptList(pageUtil,admin.getUserType(),admin.getDeptId());
			    request.setAttribute("pageUtil", pageUtil);
		     } 
			if(!QwyUtil.isNullAndEmpty(pageUtil)){
				request.setAttribute("list", pageUtil.getList());
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return "list";
	}
	
	/**
	 * 添加新部门
	 * @return
	 */
	public String deptAdd(){
		String json="";
		try {
			UsersAdmin admin=(UsersAdmin)getRequest().getSession().getAttribute("usersAdmin");
			if(QwyUtil.isNullAndEmpty(admin)){
				json = QwyUtil.getJSONString("err", "管理员未登录");
				QwyUtil.printJSON(getResponse(), json);
				//管理员没有登录;
				return null;
			}
//			String  deptName = new String (dept.getDeptName().getBytes("ISO-8859-1"),"UTF-8");
//			String  note=new String (dept.getNote().getBytes("ISO-8859-1"),"UTF-8");
			//String id=bean.addDept(deptName,note);
			String id=bean.addDept(dept.getDeptName(),dept.getNote());
			if(!QwyUtil.isNullAndEmpty(id)){
				adminLogBean.saveDeptLog(admin.getId(),Long.parseLong(id));
				request.setAttribute("message", "添加成功");		
			}else{
				request.setAttribute("message", "添加失败");			
			}
		
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return "toAdd";
	}
	public String toDeptUpdate(){
		try {
			//String id=request.getParameter("id");
			if(!QwyUtil.isNullAndEmpty(id)){
				dept=bean.findDeptById(Long.parseLong(id));
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return "toUpdate";
	}
	/**
	 * 修改部门
	 * @return
	 */
	public String deptUpdate(){
		String json="";
		try {
			UsersAdmin admin=(UsersAdmin)getRequest().getSession().getAttribute("usersAdmin");
			if(QwyUtil.isNullAndEmpty(admin)){
				json = QwyUtil.getJSONString("err", "管理员未登录");
				QwyUtil.printJSON(getResponse(), json);
				//管理员没有登录;
				return null;
			}
//			String  roleName = new String (dept.getDeptName().getBytes("ISO-8859-1"),"UTF-8");
//			String  note=new String (dept.getNote().getBytes("ISO-8859-1"),"UTF-8");
			bean.updateDept(dept.getId(),dept.getDeptName(),dept.getNote());
//			bean.updateDept(dept.getId(),roleName,note);
			request.setAttribute("message", "修改成功");		
		    adminLogBean.saveDeptLog(admin.getId(),dept.getId());
		} catch (Exception e) {
			// TODO: handle exception
			request.setAttribute("message", "修改失败");
			e.printStackTrace();
		}
		return "toUpdate";
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
			    Dept dept=bean.findDeptById(Long.parseLong(id));
			    if(!QwyUtil.isNullAndEmpty(dept)){
			    	if("0".equals(dept.getStatus())){//当前状态为0（可用）,该为1（禁用）
			    		bean.updateDeptByStatus(dept,"1");
			    		json = QwyUtil.getJSONString("ok", "已禁用");
			    	}else{//当前状态为0（可用）,该为1（禁用）
			    		bean.updateDeptByStatus(dept,"0");
			    		json = QwyUtil.getJSONString("ok", "已可用");			    		
			    	}
			    	adminLogBean.saveDeptLog(admin.getId(),Long.parseLong(id));
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
	public Dept getDept() {
		return dept;
	}
	public void setDept(Dept dept) {
		this.dept = dept;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

}
