package com.zfgt.admin.product.action;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.ServletContext;

import org.apache.struts2.config.Namespace;
import org.apache.struts2.config.ParentPackage;
import org.apache.struts2.config.Result;
import org.apache.struts2.config.Results;

import com.zfgt.admin.product.bean.DeptBean;
import com.zfgt.admin.product.bean.DistributePermissionBean;
import com.zfgt.admin.product.bean.ModulBean;
import com.zfgt.common.action.BaseAction;
import com.zfgt.common.util.QwyUtil;
import com.zfgt.orm.Dept;
import com.zfgt.orm.Modul;
import com.zfgt.orm.RolesRight;
import com.zfgt.orm.UsersAdmin;
import com.zfgt.thread.dao.ThreadDAO;

@SuppressWarnings("serial")
@ParentPackage("struts-default")
@Namespace("/Product/Admin")
@Results({ 
	@Result(name = "noLogin", value = "/Product/loginBackground.jsp" ,type=org.apache.struts2.dispatcher.ServletRedirectResult.class),
	@Result(name = "list", value = "/Product/Admin/functionManager/deptList.jsp"),
	@Result(name = "toDistributePermission", value = "/Product/Admin/functionManager/distributePermission.jsp"),
	@Result(name = "toUpdate", value = "/Product/Admin/functionManager/deptUpdate.jsp")
})
public class DistributePermissionAction  extends BaseAction {
	@Resource
	private DeptBean bean;
	private Dept dept;
	private Integer currentPage = 1;
	private Integer pageSize = 20;
	private String id;
	private String modulIds;
	@Resource
	private ModulBean modulBean;
	@Resource
	private DistributePermissionBean distributePermissionBean;
	private Long userAdminId;
	
	@Resource
	private ThreadDAO threadDAO;
	/**
	 * 跳转到分配权限
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String toDistributePermission(){
		ServletContext servletContext = getRequest().getServletContext();
		UsersAdmin admin = (UsersAdmin) getRequest().getSession().getAttribute("usersAdmin");
		if (QwyUtil.isNullAndEmpty(admin)) {
			return "noLogin";
		}
		List<Modul> list = null;
		if(admin.getUserType()==-1){
			//获取所有权限;
			//list = threadDAO.getModul();
			list = (List<Modul>)servletContext.getAttribute("listModul");//取缓存
			 //List<Modul>  list2=modulBean.findModulsByType(null);
		}else if(admin.getUserType() == 0 ){
			// List<Modul>  list2=distributePermissionBean.findModuls(admin.getId());
			List<RolesRight> listRolesRight = (List<RolesRight>) servletContext.getAttribute("listRolesRight");
			//List<RolesRight> listRolesRight = threadDAO.getRolesRight();
			list = getModulFromRolesRight(listRolesRight, admin.getId());
			
		}
		//List<RolesRight> listRolesRight = threadDAO.getRolesRight();
		List<RolesRight> listRolesRight = (List<RolesRight>) servletContext.getAttribute("listRolesRight");
		listRolesRight = getRolesRightByUsersAdminId(listRolesRight, userAdminId);
		//List<RolesRight> rights=distributePermissionBean.findRolesRights(userAdminId, "0");
		request.setAttribute("userAdminId",userAdminId);
		request.setAttribute("list", distributePermissionBean.getArray(list,listRolesRight));
		return "toDistributePermission";
	}
	
	/**
	 * 保存权限
	 * @return
	 */
	public String savePermission(){
		String json="";
		try {
			UsersAdmin users = (UsersAdmin) getRequest().getSession().getAttribute("usersAdmin");
			if (QwyUtil.isNullAndEmpty(users)) {
				json = QwyUtil.getJSONString("err", "管理员未登录");
				QwyUtil.printJSON(getResponse(), json);
				//管理员没有登录;
				return null;
			}
			if(QwyUtil.isNullAndEmpty(modulIds)){
				json = QwyUtil.getJSONString("error", "请选择模块");
				QwyUtil.printJSON(getResponse(), json);
				return null;
			}
			if(QwyUtil.isNullAndEmpty(userAdminId)){
				json = QwyUtil.getJSONString("error", "请选择分配权限人物");
				QwyUtil.printJSON(getResponse(), json);
				return null;
			}
			if(distributePermissionBean.saveRoleRight(modulIds, users.getId(),userAdminId)){
				//获取所有权限;
				List<Modul> listModul = threadDAO.getModul();
				getRequest().getServletContext().setAttribute("listModul", listModul);
				
				//获取所有用户的一级标题权限;
				List<RolesRight> firstRolesRight = threadDAO.getFirstRolesRight();
				getRequest().getServletContext().setAttribute("firstRolesRight", firstRolesRight);
				
				//获取用户的权限;
				List<RolesRight> listRolesRight = threadDAO.getRolesRight();
				getRequest().getServletContext().setAttribute("listRolesRight", listRolesRight);
				json = QwyUtil.getJSONString("ok", "分配权限成功");
				QwyUtil.printJSON(getResponse(), json);
				return null;
			}else{
				json = QwyUtil.getJSONString("error", "分配权限失败");
				QwyUtil.printJSON(getResponse(), json);
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	/**根据RolesRight来获取Modul
	 * @param listRolesRight RolesRight的所有集合.
	 * @param usersAdminId 管理员ID
	 * @author qwy
	 * @return
	 */
	public List<Modul> getModulFromRolesRight(List<RolesRight> listRolesRight,long usersAdminId){
		if(!QwyUtil.isNullAndEmpty(listRolesRight)){
			List<Modul> list = new ArrayList<Modul>();
			for (RolesRight rolesRight : listRolesRight) {
				if(rolesRight.getUsersAdminId().longValue()==usersAdminId){
					list.add(rolesRight.getModul());
				}
			}
			return list;
		}
		return null;
	}
	
	/**根据用户ID,获取RolesRight
	 * @param listRolesRight RolesRight的所有集合.
	 * @param usersAdminId 管理员ID
	 * @author qwy
	 * @return
	 */
	public List<RolesRight> getRolesRightByUsersAdminId(List<RolesRight> listRolesRight,long usersAdminId){
		if(!QwyUtil.isNullAndEmpty(listRolesRight)){
			List<RolesRight> list = new ArrayList<RolesRight>();
			for (RolesRight rolesRight : listRolesRight) {
				if(rolesRight.getUsersAdminId().longValue()==usersAdminId){
					list.add(rolesRight);
				}
			}
			return list;
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

	public String getModulIds() {
		return modulIds;
	}

	public void setModulIds(String modulIds) {
		this.modulIds = modulIds;
	}

	public Long getUserAdminId() {
		return userAdminId;
	}

	public void setUserAdminId(Long userAdminId) {
		this.userAdminId = userAdminId;
	}
 
}
