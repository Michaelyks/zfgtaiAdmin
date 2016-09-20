package com.zfgt.thread.action;

import java.text.SimpleDateFormat;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.zfgt.common.util.DESEncrypt;
import com.zfgt.common.util.PageUtil;
import com.zfgt.common.util.QwyUtil;
import com.zfgt.orm.UsersInfo;
import com.zfgt.thread.bean.UpdateUsersInfoThreadBean;
@Service
public class UpdateUsersInfoThread implements Runnable {
	private Logger log = Logger.getLogger(UpdateUsersInfoThread.class);
	private Integer pageSize = 50;
	@Resource
	private UpdateUsersInfoThreadBean updateUsersInfoThreadBean;
	
	@SuppressWarnings("unchecked")
	@Override
	public void run() {
		try {
			log.info("=================启动更新用户信息线程.......=================");
			// TODO Auto-generated method stub
			PageUtil<UsersInfo> pageUtil = new PageUtil<UsersInfo>();
			pageUtil.setPageSize(pageSize);
				int currentPage = 0;
				for (;;) {
					currentPage++;
					pageUtil.setCurrentPage(currentPage);
					pageUtil = (PageUtil<UsersInfo>) updateUsersInfoThreadBean.queryUsersInfo(pageUtil);
					List<UsersInfo> list = pageUtil.getList();
					if(QwyUtil.isNullAndEmpty(list)){
						log.info("后台线程: 用户信息更新结束!");
						break;
					}
					//修改用户信息的性别，年龄，生日;
					for (UsersInfo usersInfo : list) {
						if(!QwyUtil.isNullAndEmpty(DESEncrypt.jieMiIdCard(usersInfo.getIdcard()))){
							updateUsersInfoThreadBean.updateUsersInfo(usersInfo);
						}

					}
				}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.error("操作异常: ",e);
			log.error("进入修改用户信息的后台线程异常: ",e);
		}
		
		
	}
}
