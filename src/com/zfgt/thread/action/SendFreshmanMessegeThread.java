package com.zfgt.thread.action;

import java.util.Date;
import java.util.List;
import java.util.TimerTask;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.zfgt.common.util.PageUtil;
import com.zfgt.common.util.QwyUtil;
import com.zfgt.orm.InterestDetails;
import com.zfgt.orm.Investors;
import com.zfgt.orm.Product;
import com.zfgt.orm.SystemConfig;
import com.zfgt.thread.bean.ClearingProductFreshmanThreadBean;
import com.zfgt.thread.bean.ClearingProductThreadBean;
import com.zfgt.thread.bean.SendProfitThreadBean;
import com.zfgt.thread.dao.ThreadDAO;

/**后台线程View层--新手投资到期时发送短信线程
 * 提醒用户所投资的新手产品次日将发息
 * @author 覃文勇
 * @createTime 2015-9-24上午11:53:47
 */
@Service
public class SendFreshmanMessegeThread extends TimerTask {
	private Logger log = Logger.getLogger(SendFreshmanMessegeThread.class);
	private Integer pageSize = 50;
	@Resource
	private ClearingProductFreshmanThreadBean bean;

	@Override
	public void run() {
		try {
			log.info("进入后台线程----新手投资到期时发送短信");
			// TODO Auto-generated method stub
			PageUtil<InterestDetails> pageUtil = new PageUtil<InterestDetails>();
			pageUtil.setPageSize(pageSize);
			String[] status = {"0","1"};//状态 0未支付,1已冻结,2已支付,3已删除
			int currentPage = 0;
			for (;;) {
				currentPage++;
				pageUtil.setCurrentPage(currentPage); 
				String nowDate=QwyUtil.fmMMddyyyy.format(new Date());
				pageUtil = bean.getFreshManInterestDetails(pageUtil, status,nowDate);
				List<InterestDetails> listInterestDetails = pageUtil.getList();
				if(QwyUtil.isNullAndEmpty(listInterestDetails)){
					log.info("发送短信: "+currentPage);
					break;
				}
				if(!QwyUtil.isNullAndEmpty(listInterestDetails)){
					for (InterestDetails interestDetails : listInterestDetails) {
						//发送短信
						if(!"1".equals(interestDetails.getIsSendMessage())){
							String temp = bean.sendMessage(interestDetails,"线程定时自动发送短信");				
							log.info(interestDetails.getId()+" 发放新手投资到期短信的结果: "+temp);
                            bean.updateInterestDetails(interestDetails, String.valueOf(1));
						}
					}
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.error("操作异常: ",e);
			log.error("发送新手投资到期短信的后台线程异常: ",e);
		}
	}
	
}
