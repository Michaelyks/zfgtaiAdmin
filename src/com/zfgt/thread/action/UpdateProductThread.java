package com.zfgt.thread.action;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.zfgt.common.util.PageUtil;
import com.zfgt.common.util.QwyUtil;
import com.zfgt.orm.Product;
import com.zfgt.thread.bean.UpdateProductThreadBean;

/**后台线程自动更新理财产品;改变理财产品的状态;<br>
 * 使项目(常规产品,product.productType == 0)进入【已售罄】或【结算中】<br>
 * 使项目(新手专享产品,product.productType == 1)进入【已售罄】<br>
 * @author qwy
 *
 * @createTime 2015-4-28上午9:42:57
 */
@Service
public class UpdateProductThread implements Runnable {
	private Logger log = Logger.getLogger(UpdateProductThread.class);
	private Integer pageSize = 50;
	@Resource
	private UpdateProductThreadBean updateProductThreadBean;
	
	@Resource
	private ClearingProductThread clearingProductThread;
	@Resource
	private ClearingFundProductThread clearingFundProductThread;
	@Resource
	private ClearingProductFreshmanThread clearingProductFreshmanThread;

	@Override
	public void run() {
		try {
			log.info("=================启动更新理财产品线程....进入结算状态...=================");
			// TODO Auto-generated method stub
			PageUtil<Product> pageUtil = new PageUtil<Product>();
			pageUtil.setPageSize(pageSize);
			String[] investTypes = {"0","1","2","3","4"};//投资类别  0:车无忧 1:贸易通;2:牛市通;3:房盈宝;4:基金产品
			for (int i = 0; i < investTypes.length; i++) {
				//产品状态 0:营销中  1:已售罄  2:结算中   3:已还款 
				int currentPage = 0;
				for (;;) {
					currentPage++;
					pageUtil.setCurrentPage(currentPage);
					pageUtil = updateProductThreadBean.getProductByPageUtil(pageUtil, new String[]{investTypes[i]}, new String[]{"0","1"},null,null);
					List<Product> listProduct = pageUtil.getList();
					if(QwyUtil.isNullAndEmpty(listProduct)){
						log.info("后台线程: 投资类别: "+investTypes[i]+"; 更新结束!");
						break;
					}
					//修改理财产品的状态;
					for (Product product : listProduct) {
						if("1".equals(product.getProductType())){
							updateProductThreadBean.updateProductFreshmanStatus(product);
						}else{
							updateProductThreadBean.updateProductStatus(product);
						}
					}
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.error("操作异常: ",e);	
			log.error("进入修改产品状态的后台线程异常: ",e);
		}finally{
			//启动结算常规产品的线程;
			new Thread(clearingProductThread).run();
			//启动结算新手产品的线程;
			new Thread(clearingProductFreshmanThread).run();
			//启动结算基金产品的线程;
			new Thread(clearingFundProductThread).run();
		}
	}
	
	

}
