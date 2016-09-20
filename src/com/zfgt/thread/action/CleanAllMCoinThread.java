package com.zfgt.thread.action;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.zfgt.common.ApplicationContexts;
import com.zfgt.common.dao.ObjectDAO;
import com.zfgt.common.util.PageUtil;
import com.zfgt.common.util.QwyUtil;
import com.zfgt.orm.UsersInfo;
import com.zfgt.thread.bean.CleanAllMCoinThreadBean;
@Service
public class CleanAllMCoinThread  implements Runnable{
	private Logger log = Logger.getLogger(CleanAllMCoinThread.class);
	@Resource
	private CleanAllMCoinThreadBean bean;
	@Resource(name="objectDAO")
	private ObjectDAO dao;
	private Integer pageSize = 50;
	@Override
	public void run() {
	int currentPage = 0;
		
		for (;;) {
			ApplicationContext context = ApplicationContexts.getContexts();
			PlatformTransactionManager tm = (PlatformTransactionManager) context.getBean("transactionManager");
			TransactionStatus ts = tm.getTransaction(new DefaultTransactionDefinition());
			synchronized (this) {
			currentPage++;
			PageUtil<UsersInfo> pageUtil = new PageUtil<UsersInfo>();
			pageUtil.setCurrentPage(currentPage);
			pageUtil.setPageSize(pageSize);
			bean.loadUserInfo(pageUtil);
			List<UsersInfo> list=pageUtil.getList();
			if(!QwyUtil.isNullAndEmpty(pageUtil)&&!QwyUtil.isNullAndEmpty(list)){
	
					for (UsersInfo m : list) {
					m.setTotalPoint(Long.parseLong("0"));
					m.setUpdateTime(new Date());
					dao.saveOrUpdate(m);
				   
			}
		  if(!QwyUtil.isNullAndEmpty(list)){
				tm.commit(ts);
		 }

		}	     
		     else{
					tm.commit(ts);
					log.info("退出用户瞄币清零");
					return;
				}
		 }
		}
	}



}
