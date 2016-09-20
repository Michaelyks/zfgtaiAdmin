package com.zfgt.admin.product.bean;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.zfgt.admin.product.dao.RolesDAO;
import com.zfgt.admin.product.dao.UsersAdminDAO;
import com.zfgt.common.util.DESEncrypt;
import com.zfgt.common.util.PageUtil;
import com.zfgt.common.util.QwyUtil;
import com.zfgt.orm.Dept;
import com.zfgt.orm.Roles;
import com.zfgt.orm.Users;
import com.zfgt.orm.UsersAdmin;

/**
 * @author 覃文勇
 * @createTime 2015-8-10下午5:57:49
 */
@Service
public class UsersAdminBean{
	private static Logger log = Logger.getLogger(UsersAdminBean.class); 
	@Resource
	UsersAdminDAO dao;
	RolesDAO rolesDao;
	/**
	 * 查询后台用户信息
	 * @param status
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public PageUtil<UsersAdmin> findListByType(PageUtil<UsersAdmin> pageUtil,
			Long userType, Long deptId) {
		try {

			ArrayList<Object> objects = new ArrayList<Object>();
			StringBuffer hql = new StringBuffer();
			hql.append(" FROM UsersAdmin u WHERE u.userType >= ? ");
			objects.add(userType);
			if (userType == 0L) {
				if (!QwyUtil.isNullAndEmpty(deptId)) {// 用户类型; -1:超级管理员
														// 0:管理员;1普通人员
					hql.append(" AND u.dept.id=?  ");
					objects.add(deptId);
				} else {
					return null;
				}
			}
			hql.append(" ORDER BY u.insertTime DESC ");
			return dao.getPage(pageUtil, hql.toString(), objects.toArray());
		} catch (Exception e) {
			// TODO: handle exception
			log.error("操作异常: ", e);
		}

		return null;
	}
	
	public List<Roles> getRolesByUsersType(UsersAdmin admin){
		try {
			
			 ArrayList<Object> objects=new ArrayList<Object>();
			   StringBuffer hql=new StringBuffer();
			   hql.append(" FROM Roles r WHERE 1=1 ");
			   if(admin.getRoles().getStatus()==1L){//二级管理员权限，获取其下属角色
				   hql.append(" AND r.status=? ");
				   objects.add(1L);
			   }
			  hql.append(" ORDER BY u.insertTime DESC ");
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}
	public UsersAdmin getAdminByUsername(String username){
		Object ob=null;
		try {
			
			StringBuffer hql = new StringBuffer();
			hql.append("FROM UsersAdmin us ");
			hql.append("WHERE us.username = ?");
			ob = dao.findJoinActive(hql.toString(), new Object[]{DESEncrypt.jiaMiUsername(username.toLowerCase())});
			if(ob!=null){
				return (UsersAdmin)ob;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.error("操作异常: ",e);
		}
		return null;
		
	}
	
	public String addUserAdmin(String username,String password,Long deptId,Long usersType,Long createrId){
		UsersAdmin usersAdmin=new UsersAdmin();
		usersAdmin.setUsername(DESEncrypt.jiaMiUsername(username));
		usersAdmin.setPassword(DESEncrypt.jiaMiPassword(password) );
		usersAdmin.setUserType(usersType);
		usersAdmin.setUserStatus(String.valueOf(0));//可用
		usersAdmin.setInsertTime(new Date());
		usersAdmin.setDeptId(deptId);
		usersAdmin.setCreaterId(createrId);
		
		return dao.saveAndReturnId(usersAdmin);
	}
	/**
	 * 根据id查找
	 * @param id
	 * @return
	 */
	public UsersAdmin findUsersAdminById(Long id){
		UsersAdmin usersAdmin=(UsersAdmin) dao.findById(new UsersAdmin(), id);
		return usersAdmin;
	}
	
	
	public String updateByStatus(UsersAdmin usersAdmin,String status){
		usersAdmin.setUserStatus(status);
		usersAdmin.setUpdateTime(new Date());	
		dao.saveOrUpdate(usersAdmin);		
		return null;
	}
	
	/**根据用户的注册时间来获取用户信息表;集合;<br>
	 * 格式为日期;最小单位为天;如: 2016-07-29
	 * @param insertTime
	 * @return
	 */
	public List<Users> loadUsersByInsertTime(String insertTime){
		StringBuffer sb = new StringBuffer();
		sb.append("FROM Users us WHERE us.insertTime >='"+insertTime+" 00:00:00'");
		sb.append(" AND us.insertTime <='"+insertTime + " 23:59:59'");
		return (List<Users>)dao.LoadAll(sb.toString(), null);
	}

}
