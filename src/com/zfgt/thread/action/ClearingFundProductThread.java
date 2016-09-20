package com.zfgt.thread.action;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.zfgt.common.bean.SystemConfigBean;
import com.zfgt.common.util.PageUtil;
import com.zfgt.common.util.QwyUtil;
import com.zfgt.orm.Product;
import com.zfgt.orm.SystemConfig;
import com.zfgt.thread.bean.ClearingProductThreadBean;
import com.zfgt.thread.dao.ThreadDAO;

/**
 * 后台线程View层--结算理财产品(非新手专享产品,即product.productType=0的理财产品)<br>
 * 把【结算中】的产品进入到返款流程;
 * 
 * @author qwy
 *
 * @createTime 2015-05-12 17:48:04
 */
@Service
public class ClearingFundProductThread implements Runnable {
	private Logger log = Logger.getLogger(ClearingFundProductThread.class);
	private Integer pageSize = 50;
	@Resource
	private ClearingProductThreadBean bean;
	@Resource
	private ThreadDAO threadDAO;
	@Resource
	private SystemConfigBean configBean;

	@Override
	public void run() {
		try {
			log.info("=================进入后台线程....结算理财产品...=================");
			// TODO Auto-generated method stub
			PageUtil<Product> pageUtil = new PageUtil<Product>();
			pageUtil.setPageSize(pageSize);
			String[] investTypes = { "4" };// 投资类别 0:车无忧 1:贸易通;2:牛市通;3:房盈宝4:基金
			final SystemConfig systemConfig = threadDAO.getSystemConfig();
			for (int i = 0; i < investTypes.length; i++) {
				// 产品状态 0:营销中 1:已售罄 2:结算中 3:已还款
				int currentPage = 0;
				for (;;) {
					currentPage++;
					pageUtil.setCurrentPage(currentPage);
					pageUtil = bean.getProductByPageUtil(pageUtil, null, new String[] { "2" }, new String[] { "2" }, new Date());
					List<Product> listProduct = pageUtil.getList();
					if (QwyUtil.isNullAndEmpty(listProduct)) {
						log.info("后台线程~结算理财产品: 投资类别: " + investTypes[i] + "; 结算完毕!");
						break;
					}
					// 修改理财产品的状态;
					for (Product product : listProduct) {
						String temp = bean.clearingProduct(product);
						if (QwyUtil.isNullAndEmpty(temp)) {
							final Product pro = product;
							// 把理财产品进入到已还款;
							product.setProductStatus("3");
							product.setBackCashTime(new Date());
							bean.saveOrUpdate(product);
							// 线程池处发送短信
							// final ScheduledExecutorService scheduler =
							// Executors.newScheduledThreadPool(1);
							// scheduler.scheduleAtFixedRate(new Runnable() {
							// @Override
							// public void run() {
							// // TODO Auto-generated method stub
							// log.info("=================产品结算....启动发送短信线程...=================");
							// // TODO Auto-generated method stub
							// StringBuffer msg=new
							// StringBuffer(configBean.findSystemConfig().getSmsQm());
							// msg.append("尊敬的用户：");
							// msg.append("您购买的 ");
							// msg.append(pro.getTitle());
							// msg.append(" 产品于今天");
							// msg.append(QwyUtil.fmyyyyMMdd.format(pro.getBackCashTime()));
							// msg.append("到期，");
							// msg.append("资金会在");
							// msg.append(QwyUtil.fmyyyyMMdd.format(QwyUtil.addDaysFromOldDate(pro.getBackCashTime(),
							// 1).getTime()));
							// msg.append("上午发放至您的中泰理财账户，");
							// msg.append("请注意查收；");
							// msg.append(systemConfig.getSmsTip());
							// System.out.println(msg.toString());
							// log.info(msg.toString());
							// //发放收益时发送短信
							// List<String>
							// usernameList=bean.findUsersByProductId(pro.getId());
							// if(QwyUtil.isNullAndEmpty(usernameList)){
							// return;
							// }
							// if(usernameList.size()>0){
							// Date date=new Date();
							// SimpleDateFormat fmyyyyMMdd = new
							// SimpleDateFormat("yyyyMMdd");
							// String sentTime=fmyyyyMMdd.format(date)+"080000";
							// StringBuffer usernameBuffer = new StringBuffer();
							// int j=0;
							// for(int i=0;i<usernameList.size();i++){
							// j++;
							// usernameBuffer.append(DESEncrypt.jieMiUsername(usernameList.get(i)));
							// usernameBuffer.append(",");
							// if(j>=150){
							// //超过150个人购买时,分批发放;
							// if(date.getHours()>8){
							// sentTime=QwyUtil.fmyyyyMMddHHmmss2.format(date);
							// }
							// //去掉最后一个逗号
							// String phones =
							// usernameBuffer.deleteCharAt(usernameBuffer.length()-1).toString();
							// //SMSUtil.sendProfitMessage(phones, sentTime,
							// msg.toString());
							// usernameBuffer = null;
							// usernameBuffer = new StringBuffer();
							// j=0;
							// }
							// }
							//
							// System.out.println(usernameBuffer.toString());
							// log.info(usernameBuffer.toString());
							//
							// if(!QwyUtil.isNullAndEmpty(usernameBuffer.toString())){
							// //去掉最后一个逗号
							// String phones =
							// usernameBuffer.deleteCharAt(usernameBuffer.length()-1).toString();
							// //有短信内容,才发送;
							// if(date.getHours()>8){
							// sentTime=QwyUtil.fmyyyyMMddHHmmss2.format(date);
							// }
							// Object isStartThread =
							// PropertiesUtil.getProperties("isStartThread");
							// if(!QwyUtil.isNullAndEmpty(isStartThread)&&"1".equals(isStartThread.toString())){
							// if(phones.contains("15112304365")){
							// //System.out.println("15112304365");
							// //SMSUtil.sendProfitMessage(phones, sentTime,
							// msg.toString());
							// }
							// }
							// }
							//
							// }
							// scheduler.shutdown();
							// }
							// }, 0, 3, TimeUnit.SECONDS);

						} else {
							// 结算理财产品失败;
							log.info(temp);
						}
					}
				}
			}
			log.info("整个结算理财产品的后台线程结束;");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.error("操作异常: ", e);
			log.error("进入结算理财产品的后台线程异常: ", e);
		}
	}

	public static void main(String[] args) {
		StringBuffer buff = new StringBuffer();
		buff.append("156,156,156,156,");
		System.out.println(buff.deleteCharAt(buff.length() - 1).toString());
	}

}
