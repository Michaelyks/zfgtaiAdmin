package com.zfgt.admin.Mcoin.bean;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.zfgt.common.dao.ObjectDAO;
import com.zfgt.common.util.QwyUtil;
import com.zfgt.orm.MProduct;
import com.zfgt.orm.Users;
@Service
public class ReleaseMeowBean {
	private static Logger log = Logger.getLogger(ReleaseMeowBean.class); 
	@Resource(name="objectDAO")
	private ObjectDAO dao;
	
	/**
	 * 获取用户的真实姓名;
	 * @param username
	 * @return
	 */
	public MProduct findTitle(String title){
		Object ob=null;
		try {
			if(QwyUtil.isNullAndEmpty(title))
				return null;
			StringBuffer hql = new StringBuffer();
			hql.append("FROM MProduct us ");
			hql.append("WHERE us.title = ? ");
			ob = dao.findJoinActive(hql.toString(), new Object[]{title});
			if(ob!=null){
				return (MProduct)ob;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.error("操作异常: ",e);
		}
		return null;
		
	}
}
