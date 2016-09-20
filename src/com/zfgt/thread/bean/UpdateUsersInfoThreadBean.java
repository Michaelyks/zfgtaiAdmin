package com.zfgt.thread.bean;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.jfree.util.Log;
import org.springframework.stereotype.Service;

import com.zfgt.common.util.DESEncrypt;
import com.zfgt.common.util.PageUtil;
import com.zfgt.common.util.QwyUtil;
import com.zfgt.orm.UsersInfo;
import com.zfgt.thread.dao.ThreadDAO;

/**
 * @author 覃文勇
 * @createTime 2015-8-18下午2:54:56
 */
@Service
public class UpdateUsersInfoThreadBean {
	private Logger log = Logger.getLogger(UpdateUsersInfoThreadBean.class);
	private static SimpleDateFormat fmyyyyMM = new SimpleDateFormat("yyyyMMdd");
	@Resource
	private ThreadDAO dao;
/**
 * 获取绑定身份证的用户列表
 * @param pageUtil
 * @return
 */
	@SuppressWarnings("unchecked")
	public PageUtil<UsersInfo> queryUsersInfo(PageUtil<UsersInfo> pageUtil){		
		try {
			StringBuffer hql = new StringBuffer();
			hql.append("FROM UsersInfo ui where 1=1 and ui.idcard IS NOT NULL and ui.idcard <> '' ");
			return (PageUtil<UsersInfo>)dao.getPage(pageUtil, hql.toString(), null);
		} catch (Exception e) {
			log.error("操作异常: ",e);
			e.printStackTrace();
		}
		return null;
		
	}
/**
 * 根据注册用户的身份证插入用户性别，当前年龄，生日
 * @param usersInfo
 */
	public void updateUsersInfo(UsersInfo usersInfo){		
		try {
			Object[] obj=QwyUtil.getInfoByIDCard(DESEncrypt.jieMiIdCard(usersInfo.getIdcard()));
			usersInfo.setSex(obj[0]+"");
			System.out.println(obj[0]+"");
			usersInfo.setAge(obj[1]+"");
			usersInfo.setBirthday(fmyyyyMM.parse(obj[2]+""));
			usersInfo.setUpdateTime(new Date());
			dao.saveOrUpdate(usersInfo);

		} catch (Exception e) {
			log.error("操作异常: ",e);
			e.printStackTrace();
		}		
		
	}
	
	
}
