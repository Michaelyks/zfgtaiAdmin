package com.zfgt.login.bean;


import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import com.zfgt.common.ApplicationContexts;
import com.zfgt.common.action.BaseAction;
import com.zfgt.common.util.DESEncrypt;
import com.zfgt.common.util.QwyUtil;
import com.zfgt.login.dao.UsersLoginDAO;
import com.zfgt.orm.Account;
import com.zfgt.orm.Users;
import com.zfgt.orm.UsersInfo;
import com.zfgt.thread.action.UpdateProductThread;

/**用户登录Bean层;
 * @author qwy
 *
 * @createTime 2015-4-18下午4:47:36
 */
@Service
public class UsersLoginBean {
	private static Logger log = Logger.getLogger(UsersLoginBean.class);
	@Resource
	private UsersLoginDAO dao;
	
	/**根据用户名和密码查找用户;
	 * @param users Users
	 * @return Users
	 */
	public Users findUsersByUsernameAndPassword(Users users){
		StringBuffer buff;
		try {
			buff = new StringBuffer();
			buff.append("FROM Users us ");
			buff.append("WHERE (us.username =? or us.phone=?)");
			buff.append("AND us.password = ? ");
			buff.append("AND us.userType >= 0 ");
			String username =users.getUsername();
			String jmUsername = DESEncrypt.jiaMiUsername(username.toLowerCase());
			Object[] ob = new Object[]{jmUsername,jmUsername,DESEncrypt.jiaMiPassword(users.getPassword())};
			Object obj = dao.findJoinActive(buff.toString(),ob );
			if(!QwyUtil.isNullAndEmpty(obj)){
				users = (Users)obj;
				//users.setUsername(DESEncrypt.jieMiUsername(users.getUsername()));
				//users.setPhone(DESEncrypt.jieMiUsername(users.getPhone()));
				return users;
			}
			return null;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.error("操作异常: ",e);
		}
		return null;
	}
	
	/**保存用户;
	 * @param users
	 */
	public void saveUsers(Users users){
		dao.saveOrUpdate(users);
	}
	
	@SuppressWarnings("unchecked")
	public List<Users> getUsers(){
		return dao.LoadAll("Users");
	}
	
	@SuppressWarnings("unchecked")
	public List<UsersInfo> getUsersInfo(){
		return dao.LoadAll("UsersInfo");
	}
	
	@SuppressWarnings("unchecked")
	public List<Account> getAccount(){
		return dao.LoadAll("Account");
	}
	
	private void updateUsers(){
		List<Users> listUsers = getUsers();
		for (Users users : listUsers) {
			try {
				users.setPassword(users.getPassword());
				users.setPayPassword(users.getPassword());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				log.error("操作异常: ",e);
			}
		}
		dao.updateList(listUsers);
	}
	
	private void updateUsersInfo(){
		List<UsersInfo> listUsersInfo = getUsersInfo();
		for (UsersInfo users : listUsersInfo) {
			try {
				String email = DESEncrypt.jiaMiUsername(users.getEmail());
				String phone = DESEncrypt.jiaMiUsername(users.getPhone());
				String idCard = DESEncrypt.jiaMiIdCard(users.getIdcard());
				users.setEmail(email);
				users.setPhone(phone);
				users.setIdcard(idCard);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				log.error("操作异常: ",e);
			}
		}
		dao.updateList(listUsersInfo);
	}
	
	
	private void updateAccount(){
		List<Account> listAccount= getAccount();
		for (Account ac : listAccount) {
			try {
				String email = DESEncrypt.jiaMiBankCard(ac.getBankAccount());
				String phone = DESEncrypt.jiaMiUsername(ac.getPhone());
				String idCard = DESEncrypt.jiaMiIdCard(ac.getIdcard());
				ac.setBankAccount(email);
				ac.setPhone(phone);
				ac.setIdcard(idCard);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				log.error("操作异常: ",e);
			}
		}
		dao.updateList(listAccount);
	}
	
	public static void main(String[] args) {
		ApplicationContext context = ApplicationContexts.getContexts();
		UsersLoginBean usersLoginBean = (UsersLoginBean) context.getBean("usersLoginBean");
		usersLoginBean.updateAccount();
		//usersLoginBean.updateUsersInfo();
	}

}
