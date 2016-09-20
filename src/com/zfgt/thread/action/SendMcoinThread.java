package com.zfgt.thread.action;

import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.zfgt.admin.Mcoin.dao.UserVIP;
import com.zfgt.common.util.PageUtil;
import com.zfgt.common.util.QwyUtil;
import com.zfgt.orm.Investors;
import com.zfgt.orm.UsersInfo;
import com.zfgt.thread.bean.SendMcoinBean;
@Service
public class SendMcoinThread implements Runnable {
	private Logger log = Logger.getLogger(SendMcoinThread.class);
	private Integer pageSize = 50;
	@Resource
	private SendMcoinBean bean;
	@Override
	public void run() {
		PageUtil<Investors> pageUtil = new PageUtil<Investors>();
		pageUtil.setPageSize(pageSize);
		int currentPage = 0;
		for (;;) {
			currentPage++;
			pageUtil.setCurrentPage(currentPage);
			 List<String> listInterestDetails  = bean.getInvestorsByPageUtil(pageUtil);
			 if(QwyUtil.isNullAndEmpty(listInterestDetails)){
					log.info("后台线程: 发放修改已经发放瞄币的状态结束!");
					break;
				}
			 for(int i = 0 ; i < listInterestDetails.size();i++){
				Investors in = bean.findInvestorsId(listInterestDetails.get(i));
				bean.isSendMcoin(in);
			 }
	    }
		currentPage = 0;
		for(;;){
			currentPage++;
			
			pageUtil.setPageSize(pageSize);
			pageUtil.setCurrentPage(currentPage);
			pageUtil = bean.findNoSendMcoin(pageUtil);
			List<Investors> list = pageUtil.getList();
			if(QwyUtil.isNullAndEmpty(list)){
				log.info("后台线程: 发放用户瞄币结束!");
				break;
			}
			for(int i = 0 ; i < list.size();i++){
				bean.sengMcoin(list.get(i));
			}
		
		}
		
	}
}
