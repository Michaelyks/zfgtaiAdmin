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
import com.zfgt.thread.bean.ClearingProductFreshmanThreadBean;
import com.zfgt.thread.bean.ClearingProductThreadBean;

/**后台线程View层--结算新手专享产品理财产品(新手专享产品,即product.productType=1的理财产品)<br>
 * 把【已售罄】的新手专享产品进入到返款流程;<br>
 * 最后新手专享产品会进入到【已还款】状态
 * @author qwy
 *
 * @createTime 2015-05-12 17:48:04
 */
@Service
public class ClearingProductFreshmanThread extends TimerTask {
	private Logger log = Logger.getLogger(ClearingProductFreshmanThread.class);
	private Integer pageSize = 50;
	@Resource
	private ClearingProductFreshmanThreadBean bean;

	@Override
	public void run() {
		try {
			log.info("进入后台线程----结算新手专享理财产品");
			// TODO Auto-generated method stub
			PageUtil<Product> pageUtil = new PageUtil<Product>();
			pageUtil.setPageSize(pageSize);
			String[] investTypes = {"0","1","2","3"};//投资类别  0:车无忧 1:贸易通;2:牛市通;3:房盈宝
			for (int i = 0; i < investTypes.length; i++) {
				//产品状态 0:营销中  1:已售罄  2:结算中   3:已还款 
				int currentPage = 0;
				for (;;) {
					currentPage++;
					pageUtil.setCurrentPage(currentPage);
					pageUtil = bean.getProductByPageUtil(pageUtil, investTypes[i]);
					List<Product> listProduct = pageUtil.getList();
					if(QwyUtil.isNullAndEmpty(listProduct)){
						log.info("后台线程~结算新手专享产品: 投资类别: "+investTypes[i]+"; 结算完毕!");
						break;
					}
					//修改理财产品的状态;
					for (Product product : listProduct) {
						String temp = bean.clearingProductFreshman(product);
						if(QwyUtil.isNullAndEmpty(temp)){
							//把理财产品进入到已还款;
							product.setProductStatus("3");
							product.setBackCashTime(new Date());
							bean.saveOrUpdate(product);
						}else{
							//结算理财产品失败;
							log.info(temp);
						}
					}
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.error("操作异常: ",e);
			log.error("进入结算新手专享理财产品的后台线程异常: ",e);
		}
	}
	
	

}
