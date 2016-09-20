package com.zfgt.thread.action;

import java.util.List;

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

/**后台线程View层--对用户进行收益的发放;<br>
 * 对利息表进行按要求发放收益;
 * @author qwy
 *
 * @createTime 2015-05-18 04:44:39
 */
@Service
public class SendProfitThread implements Runnable {
	private Logger log = Logger.getLogger(SendProfitThread.class);
	private Integer pageSize = 50;
	@Resource
	private SendProfitThreadBean bean;
	@Resource
	private ThreadDAO threadDAO;
	@Override
	public void run() {
		try {
			log.info("进入后台线程----对收益表进行按要求发放");
			// TODO Auto-generated method stub
			PageUtil<InterestDetails> pageUtil = new PageUtil<InterestDetails>();
			pageUtil.setPageSize(pageSize);
			String[] status = {"0","1"};//状态 0未支付,1已冻结,2已支付,3已删除
			int currentPage = 0;
			final SystemConfig systemConfig = threadDAO.getSystemConfig();
			for (;;) {
				currentPage++;
				pageUtil.setCurrentPage(currentPage);
				pageUtil = bean.getInterestDetailsByPageUtil(pageUtil, status);
				List<InterestDetails> listInterestDetails = pageUtil.getList();
				if(QwyUtil.isNullAndEmpty(listInterestDetails)){
					log.info("发放收益结束: "+currentPage);
					break;
				}
				if(!QwyUtil.isNullAndEmpty(listInterestDetails)){
					for (InterestDetails interestDetails : listInterestDetails) {
						//发放收益
						String temp = bean.sendProfit(interestDetails,"线程定时自动发放收益",systemConfig.getSmsTip());
						log.info(interestDetails.getId()+" 发放收益的结果: "+temp);
					}
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.error("操作异常: ",e);
			log.error("发放收益的后台线程异常: ",e);
		}
	}
	
	

}
