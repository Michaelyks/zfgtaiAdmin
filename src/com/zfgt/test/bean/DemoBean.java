package com.zfgt.test.bean;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.zfgt.common.util.DESEncrypt;
import com.zfgt.common.util.QwyUtil;
import com.zfgt.orm.HdUsers;
import com.zfgt.orm.Users;
import com.zfgt.test.dao.TestDAO;

@Service
public class DemoBean{
	@Resource
	TestDAO dao;
	
	public List<Users> getTestList(){
		System.out.println("jinlai");
		List<Users> list = (List<Users>)dao.LoadAll("FROM Users", null);
		return list;
	}
	
	/**根据用户名查找用户;
	 * @param phone
	 * @return
	 */
	public Users getUsersByUsername(String phone){
		StringBuffer hql = new StringBuffer();
		hql.append("FROM Users us WHERE us.username = ? ");
		return (Users)dao.findJoinActive(hql.toString(), new Object[]{DESEncrypt.jiaMiUsername(phone)});
	}
	
	public boolean isExistsHDUsers(String username){
		StringBuffer hql = new StringBuffer();
		hql.append("FROM HdUsers us WHERE us.username = ? ");
		Object ob = dao.findJoinActive(hql.toString(), new Object[]{DESEncrypt.jiaMiUsername(username)});
		if(QwyUtil.isNullAndEmpty(ob)){
			return false;
		}
		return true;
	}
	
	public void createUsers(Users user){
		HdUsers hd = new HdUsers();
		hd.setHdFlagId("1");
		hd.setInsertTime(new Date());
		hd.setNote("加群领取50元投资券");
		hd.setUserId(user.getId());
		hd.setUsername(user.getUsername());
		dao.save(hd);
		
	}
}
