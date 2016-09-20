package com.zfgt.admin.product.bean;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.zfgt.account.dao.UserInfoDAO;
import com.zfgt.admin.product.dao.UsersApplyDAO;
import com.zfgt.common.util.DESEncrypt;
import com.zfgt.common.util.PageUtil;
import com.zfgt.common.util.QwyUtil;
import com.zfgt.login.dao.UsersLoginDAO;
import com.zfgt.orm.Contract;
import com.zfgt.orm.Users;
import com.zfgt.orm.UsersApply;
import com.zfgt.orm.UsersInfo;

/**用户申请
 * @author 覃文勇
 * @createTime 2015-8-14下午12:56:25
 */
@Service
public class UsersApplyBean {
	private static Logger log = Logger.getLogger(UsersApplyBean.class); 
	@Resource
	private UsersApplyDAO dao;
	@Resource
	private UsersLoginDAO usersLoginDao;
	@Resource
	private UserInfoDAO userInfoDAO;
	
	public String addUsersApply(Long usersId,String note){
		UsersApply usersApply=new UsersApply();
		usersApply.setUsersId(usersId);
		usersApply.setInsertTime(new Date());
		usersApply.setNote(note);
		usersApply.setStatus(String.valueOf(1));
		return dao.saveAndReturnId(usersApply);
	}
	@SuppressWarnings("unchecked")
	public PageUtil<UsersApply> findUsersApplyList(PageUtil<UsersApply> pageUtil,String insertTime,String name,String status){
		try {

			   ArrayList<Object> objects=new ArrayList<Object>();
			   StringBuffer hql=new StringBuffer();
			   hql.append(" FROM UsersApply u WHERE 1=1 ");
			   if(!QwyUtil.isNullAndEmpty(name)){
					hql.append(" AND u.users.username= ? ");
					objects.add(DESEncrypt.jiaMiUsername(name));
			   }
			   if(!"all".equals(status)&&!QwyUtil.isNullAndEmpty(status)){
					hql.append(" AND u.status= ? ");
					objects.add(status);
			   }
			 //申请时间
				if(!QwyUtil.isNullAndEmpty(insertTime)){
					String [] time=QwyUtil.splitTime(insertTime);
					if(time.length>1){
						hql.append(" AND u.insertTime >= ? ");
						objects.add(QwyUtil.fmMMddyyyy.parse(time[0]));
						hql.append(" AND u.insertTime <= ? ");
						objects.add(QwyUtil.fmMMddyyyy.parse(time[1]));
					}else{
						hql.append(" AND u.insertTime >= ? ");
						objects.add(QwyUtil.fmMMddyyyyHHmmss.parse(time[0]+" 00:00:00"));
						hql.append(" AND u.insertTime <= ? ");
						objects.add(QwyUtil.fmMMddyyyyHHmmss.parse(time[0]+" 23:59:59"));
					}
				}

				hql.append(" ORDER BY  u.insertTime DESC, u.updateTime DESC  ");
			  hql.append(" ORDER BY u.insertTime DESC ");
			  return (PageUtil<UsersApply>)dao.getPage(pageUtil, hql.toString(), objects.toArray());
			} catch (Exception e) {
				// TODO: handle exception
				log.error("操作异常: ",e);
			}
			  
		return null;
	}
	
	/**
	 * 根据用户id查找用户
	 * @param id
	 * @return
	 */
	public Users findUsersByUsersId(Long usersId){
		Users users=(Users) usersLoginDao.findById(new Users(), usersId); 
		return users;
	}
	/**
	 * 根据用户名查找用户
	 * @param username
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Users findUsersByUsername(String username){
		Users result=null;
		String hql="FROM Users t where t.username ='"+DESEncrypt.jiaMiUsername(username)+"'";
		List<Users> list = (List<Users>)usersLoginDao.LoadAll(hql,null);
		if(list.size()>0){
			result=list.get(0);
		}
		return result;
	}
	/**
	 * 修改用户表(用户名，手机号)
	 * @param oldUsers 
	 * @param newUsers
	 * @return
	 */
	public Boolean modifyUsers(Users oldUsers,String username){
		if(!QwyUtil.isNullAndEmpty(username)){
			oldUsers.setUsername(DESEncrypt.jiaMiUsername(username));
			oldUsers.setPhone(DESEncrypt.jiaMiUsername(username));
			oldUsers.setUpdateTime(new Date());
			usersLoginDao.saveOrUpdate(oldUsers);
			return true;
		}
		return false;
	}
	
	/**根据用户id查找用户
	 * @param id 用户id;
	 * @return
	 */
	public UsersInfo getUserInfoById(long usersId) {
		StringBuffer buff = new StringBuffer();
		buff.append("FROM UsersInfo userInfo ");
		buff.append("WHERE userInfo.usersId= ? ");
		UsersInfo userInfo=(UsersInfo) dao.findJoinActive(buff.toString(), new Object[]{usersId});
		return userInfo;
	}
	
	/**
	 * 修改用户信息(用户名，手机号)
	 * @param oldUsers 
	 * @param newUsers
	 * @return
	 */
	public String modifyUsersInfo(UsersInfo usersInfo,String phone){		
		usersInfo.setPhone(DESEncrypt.jiaMiUsername(phone));
		usersInfo.setUpdateTime(new Date());
		userInfoDAO.saveOrUpdate(usersInfo);
		return null;
	}
	/**
	 * 根据申请id查找申请信息
	 * @param id
	 * @return
	 */
	public UsersApply findById(Long id){
		UsersApply usersApply=(UsersApply) dao.findById(new UsersApply(),id);
		return usersApply;
	}
	/**
	 * 处理完成后，修改申请表信息
	 * @return
	 */
	public String modifyUsersApply(UsersApply usersApply,Long adminId){		
		usersApply.setStatus(String.valueOf(2L));//1：待处理 2：已处理
		usersApply.setUpdateTime(new Date());
		usersApply.setUsersAdminId(adminId);
		dao.saveOrUpdate(usersApply);
		return null;
	}
	/**
	 * 修改合同——用户名
	 * @param usersInfo
	 * @param phone
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String modifyContract(Long usersId,String username){
		StringBuffer buff = new StringBuffer();
		buff.append("FROM Contract c ");
		buff.append("WHERE c.usersId= ? ");
		List<Contract> ContractList=dao.LoadAll(buff.toString(), new Object[]{usersId});
		if(ContractList!=null&&ContractList.size()>0){
			for(Object object:ContractList){
				Contract contract=(Contract)object;
				contract.setUsername(username);
				dao.saveOrUpdate(contract);
			}
		}
		return null;
	}
}
