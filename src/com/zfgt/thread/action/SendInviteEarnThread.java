package com.zfgt.thread.action;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.zfgt.common.bean.InviteBean;
import com.zfgt.common.util.PageUtil;
import com.zfgt.common.util.QwyUtil;
import com.zfgt.orm.InterestDetails;
import com.zfgt.orm.Investors;
import com.zfgt.orm.InviteEarn;
import com.zfgt.orm.Product;
import com.zfgt.orm.SystemConfig;
import com.zfgt.thread.bean.ClearingProductFreshmanThreadBean;
import com.zfgt.thread.bean.ClearingProductThreadBean;
import com.zfgt.thread.bean.SendInviteEarnThreadBean;
import com.zfgt.thread.bean.SendProfitThreadBean;
import com.zfgt.thread.dao.ThreadDAO;

/**后台线程View层--对用户进行邀请投资奖励的发放;<br>
 * 对邀请投资奖励表根据条件发放奖励;
 * @author zlq
 *
 * @createTime 2016-09-11 
 */
@Service
public class SendInviteEarnThread implements Runnable {
	private Logger log = Logger.getLogger(SendInviteEarnThread.class);
	private Integer pageSize = 50;
	@Resource
	private SendInviteEarnThreadBean bean;
	@Resource
	private ThreadDAO threadDAO;
	@Override
	public void run() {
		try {
			log.info("进入后台线程----对邀请投资奖励表进行按要求发放");
			Calendar now = Calendar.getInstance();  
			int year= now.get(Calendar.YEAR);//得到年
			int month=now.get(Calendar.MONTH)+1;//得到月
			int day=now.get(Calendar.DAY_OF_MONTH);//得到天
			//每月10号发放上个月的邀请投资奖励
			if(day!=10){
				log.info("未到指定发放奖励日期");
				return;
			}
			String insertTime =year+ "-"+month+"-01"+" 00:00:00"; 
			//String insertTime ="2016-09-12 09:57:50"; 
			PageUtil<InviteEarn> pageUtil = new PageUtil<InviteEarn>();
			pageUtil.setPageSize(pageSize);
			String[] status = {"0"};//状态 0未发放,1已发放
			int currentPage = 0;
			for (;;) {
				currentPage++;
				pageUtil.setCurrentPage(currentPage);
				pageUtil = bean.getInviteEarnByPageUtil(pageUtil, status,"1",insertTime);
				List<InviteEarn> inviteEarns = pageUtil.getList();
				if(QwyUtil.isNullAndEmpty(inviteEarns)){
					log.info("发放奖励结束: "+currentPage);
					break;
				}
				if(!QwyUtil.isNullAndEmpty(inviteEarns)){
					for (InviteEarn inviteEarn : inviteEarns) {
						//发放收益
						String temp = bean.sendInviteEarn(inviteEarn,"线程定时自动发放邀请好友投资奖励");
						log.info(inviteEarn.getId()+" 发放奖励的结果: "+temp);
					}
				}
			}
		} catch (Exception e) {
			log.error("操作异常: ",e);
			log.error("发放奖励的后台线程异常: ",e);
		}
	}
	
	

}
