package com.zfgt.admin.login.bean;

import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.zfgt.admin.login.dao.LoginBackgroundDAO;
import com.zfgt.common.util.DESEncrypt;
import com.zfgt.common.util.QwyUtil;
import com.zfgt.orm.Modul;
import com.zfgt.orm.RolesRight;
import com.zfgt.orm.Users;
import com.zfgt.orm.UsersAdmin;

/**后台管理员登录Bean层
 * @author qwy
 *
 * @createTime 2015-04-16 23:53:24
 */

@Service
public class LoginBackgroundBean {
	private static Logger log = Logger.getLogger(LoginBackgroundBean.class);
	@Resource
	private LoginBackgroundDAO dao;
	
	/**查找管理员用户
	 * @param users UsersAdmin
	 * @return {@link UsersAdmin}
	 */
	public UsersAdmin getUsers(UsersAdmin user){
		try {
			StringBuffer hql = new StringBuffer("FROM UsersAdmin us ");
			hql.append("WHERE us.username = ? ");
			hql.append("AND  us.password = ? ");
			hql.append("AND  us.userStatus = '0' ");
			Object[] ob = new Object[]{DESEncrypt.jiaMiUsername(user.getUsername()),
					DESEncrypt.jiaMiPassword(user.getPassword())};
			return (UsersAdmin)dao.findJoinActive(hql.toString(), ob);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.error("操作异常: ",e);
		}
		return null;
	}

	public Users getUserById(long usersId) {
	
		return null;
	}

	/**更改账号密码;
	 * @param usersId 用户ID
	 * @param newPassword 新密码
	 * @return
	 */
	public boolean modifyPassword(UsersAdmin user,String newPassword){
		if(null == user)
			return false;
		//user.setUpdatePasswordTime(new Date());
		user.setPassword(DESEncrypt.jiaMiPassword(newPassword));
		dao.update(user);
		return true;
	}
	
	/**获取用户的权限;
	 * @return List&lt;RolesRight&gt;
	 */
	@SuppressWarnings("unchecked")
	public List<RolesRight> getRolesRight(long usersId){
		StringBuffer buffer = new StringBuffer("FROM RolesRight rr ");
		buffer.append("WHERE rr.usersAdminId = ? ");
		buffer.append("AND rr.status = 0 ");
		buffer.append("ORDER BY rr.modul.sort ASC ");
		return (List<RolesRight>)dao.LoadAll(buffer.toString(), new Object[]{usersId});
	}
	
	/**获取用户的一级标题权限;
	 * @return List&lt;RolesRight&gt;
	 */
	@SuppressWarnings("unchecked")
	public List<RolesRight> getFirstRolesRight(long usersId){
		StringBuffer buffer = new StringBuffer("FROM RolesRight rr ");
		buffer.append("WHERE rr.status = 0 ");
		buffer.append("AND rr.usersAdminId = ? ");
		buffer.append("AND rr.modul.type = 1 ");
		buffer.append("ORDER BY rr.modul.sort ASC ");
		return (List<RolesRight>)dao.LoadAll(buffer.toString(), new Object[]{usersId});
	}
	
	
	/**获取所有权限;
	 * @return List&lt;Modul&gt;
	 */
	@SuppressWarnings("unchecked")
	public List<Modul> getModul(){
		StringBuffer buffer = new StringBuffer("FROM Modul md ");
		buffer.append(" ORDER BY md.type ASC, md.sort ASC");
		return (List<Modul>)dao.LoadAll(buffer.toString(),null);
	}
	 
		/**
		* @author 覃文勇
	    * @createTime 2015-8-13上午11:52:36
		 * 查询后台用户（包括状态为不可用的）
		 * @param user
		 * @return
		 */
	public UsersAdmin getUsersAdmin(UsersAdmin user){
		try {
			StringBuffer hql = new StringBuffer("FROM UsersAdmin us ");
			hql.append("WHERE us.username = ? ");
			hql.append("AND  us.password = ? ");			
			Object[] ob = new Object[]{DESEncrypt.jiaMiUsername(user.getUsername()),
					DESEncrypt.jiaMiPassword(user.getPassword())};
			return (UsersAdmin)dao.findJoinActive(hql.toString(), ob);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.error("操作异常: ",e);
		}
		return null;
	}
}
