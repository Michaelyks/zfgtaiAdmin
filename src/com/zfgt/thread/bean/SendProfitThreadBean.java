package com.zfgt.thread.bean;

import java.util.Date;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.zfgt.common.ApplicationContexts;
import com.zfgt.common.bean.MyWalletBean;
import com.zfgt.common.bean.SystemConfigBean;
import com.zfgt.common.util.LockHolder;
import com.zfgt.common.util.PageUtil;
import com.zfgt.common.util.QwyUtil;
import com.zfgt.orm.InterestDetails;
import com.zfgt.orm.Investors;
import com.zfgt.orm.Product;
import com.zfgt.thread.dao.ThreadDAO;

/**
 * 后台线程Bean层--结算理财产品(非新手专享产品,即product.productType=0的理财产品)
 * 
 * @author qwy
 *
 * @createTime 2015-4-28上午9:54:11
 */
@Service
public class SendProfitThreadBean {
	private Logger log = Logger.getLogger(SendProfitThreadBean.class);
	@Resource
	private ThreadDAO dao;
	@Resource
	private MyWalletBean myWalletBean;
	@Resource
	private SystemConfigBean configBean;
	/**
	 * 更新理财产品Bean层;
	 */
	@Resource
	private UpdateProductThreadBean updateProductThreadBean;

	/**
	 * 查找理财产品;分页;<br>
	 * 按照 ORDER BY insertTime ASC, orders ASC ,productId ASC 来排序
	 * 
	 * @param pageUtil
	 *            分页对象;
	 * @param status
	 *            状态 0未支付,1已冻结,2已支付,3已删除
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public PageUtil<InterestDetails> getInterestDetailsByPageUtil(PageUtil<InterestDetails> pageUtil, String[] status) {
		StringBuffer hql = new StringBuffer();
		hql.append(" FROM InterestDetails inde ");
		hql.append(" WHERE inde.returnTime<=now() ");
		if (!QwyUtil.isNullAndEmpty(status)) {
			String myStatus = QwyUtil.packString(status);
			hql.append(" AND inde.status IN (" + myStatus + ") ");
		}
		hql.append(" ORDER BY insertTime ASC, orders ASC ,productId ASC ");
		return (PageUtil<InterestDetails>) dao.getPage(pageUtil, hql.toString(), null);

	}

	/**
	 * 根据利息表发放收益;
	 * 
	 * @param inde
	 *            InterestDetails 利息表
	 * @param interestDetailsNote
	 *            备注
	 * @param smsTip
	 *            短信广告语
	 * @return
	 */
	public String sendProfit(InterestDetails inde, String interestDetailsNote, String smsTip) {
		synchronized (LockHolder.getLock(inde.getUsersId())) {
			log.info("sendProfitFreshmanThreadBean.sendProfit 进入理财产品结算");
			if (QwyUtil.isNullAndEmpty(inde)) {
				return "sendProfitFreshmanThreadBean.sendProfit 找不到理财产品";
			}
			if (!QwyUtil.isNullAndEmpty(inde)) {
				Investors inv = null;
				inde = (InterestDetails) dao.findById(new InterestDetails(), inde.getId());
				if (!"0".equals(inde.getStatus())) {
					return "利息表不在支付状态,InterestDetails.id: " + inde.getId();
				}
				if (new Date().getTime() < inde.getReturnTime().getTime()) {
					log.info("还没到发放收益的时间" + QwyUtil.fmyyyyMMddHHmmss.format(inde.getReturnTime()));
					return "";
				}
				inv = (Investors) dao.findById(new Investors(), inde.getInvestorsId());

				if ("1".equals(inv.getIsTransfer())) {
					return "转让中不能发收益";
				}

				if (inde.getPayMoney().doubleValue() > 0) {
					if (inv != null && "1".equals(inv.getInvestorStatus())) {
						inv.setInvestorStatus("2");
						inv.setClearTime(new Date());
						dao.saveOrUpdate(inv);
					}
				}
				ApplicationContext context = ApplicationContexts.getContexts();
				// SessionFactory sf = (SessionFactory)
				// context.getBean("sessionFactory");
				PlatformTransactionManager tm = (PlatformTransactionManager) context.getBean("transactionManager");
				TransactionStatus ts = tm.getTransaction(new DefaultTransactionDefinition());
				Product product = inde.getProduct();
				try {
					// 需要支付的金额;
					inde.setAlreadyPay(QwyUtil.calcNumber(inde.getPayMoney(), inde.getPayInterest(), "+").doubleValue());
					inde.setAlreadyPayDay(inde.getPayDay());
					inde.setStatus("2");// 已支付;
					inde.setNote(interestDetailsNote);
					inde.setUpdateTime(new Date());
					StringBuffer note = new StringBuffer();
					note.append("购买 " + product.getTitle());
					note.append(" 理财产品获得的收益");
					boolean isOk = myWalletBean.sendInterest(inde.getUsersId(), inde.getPayInterest(), note.toString());
					if (!isOk) {
						tm.rollback(ts);
						log.info("sendProfitFreshmanThreadBean.sendProfit 数据回滚: 发放收益失败");
						return "sendProfitFreshmanThreadBean.sendProfit 数据回滚: 发放收益失败";
					}
					inv = (Investors) dao.findById(new Investors(), inde.getInvestorsId());
					double shouyi = QwyUtil.isNullAndEmpty(inv.getAllShouyi()) ? 0d : inv.getAllShouyi();
					inv.setAllShouyi(QwyUtil.calcNumber(shouyi, inde.getPayInterest(), "+").doubleValue());
					inv.setClearMoney(inv.getAllShouyi());
					if (inde.getPayMoney().doubleValue() > 0) {
						if (inv == null || !("2".equals(inv.getInvestorStatus()))) {
							tm.rollback(ts);
							log.info("sendProfitFreshmanThreadBean.sendProfit 数据回滚: 发放收益失败;投资列表状态不在[1]或[2].ID: " + inv.getId());
							return "sendProfitFreshmanThreadBean.sendProfit 数据回滚: 发放收益失败;投资列表状态不在[1]或[2].ID: " + inv.getId();
						}
						// 需要归还本金;
						StringBuffer noteInv = new StringBuffer();
						noteInv.append("返款购买 " + product.getTitle());
						noteInv.append(" 理财产品时的本金");
						boolean isOkInv = myWalletBean.backMoney(inv.getUsersId(), inv.getId(), noteInv.toString());
						if (isOkInv) {
							inv.setInvestorStatus("3");
						} else {
							tm.rollback(ts);
							log.info("sendProfitFreshmanThreadBean.sendProfit 数据回滚: 还款失败");
							return "sendProfitFreshmanThreadBean.sendProfit 数据回滚: 还款失败";
						}
					} else if (inv.getInMoney().doubleValue() == 0) {
						// 针对只使用投资券来购买产品的条件;
						if (new Date().getTime() >= inde.getReturnTime().getTime()) {
							log.info("sendProfitFreshmanThreadBean.sendProfit 只使用投资券来购买产品__已还款");
							// 证明此购买记录已经达到还款日期;
							inv.setInvestorStatus("3");
						}
					}
					Date date = new Date();
					dao.saveOrUpdate(inv);
					dao.saveOrUpdate(inde);
					tm.commit(ts);
					// if(inde.getPayMoney().doubleValue()>0){
					// //归还本金;
					// msg.append("于");
					// msg.append(QwyUtil.fmyyyyMMdd.format(pro.getFinishTime()));
					// msg.append("到期,资金于");
					// msg.append(QwyUtil.fmyyyyMMddHHmmss.format(date));
					// msg.append("发放到您的中泰理财账户，本息合计");
					// msg.append(QwyUtil.calcNumber(inde.getAlreadyPay(), 0.01,
					// "*").doubleValue());
					// msg.append("元，请注意查收；");
					// }else{
					// 只发息时发送短信
					// if(inde.getPayMoney().doubleValue()==0){
					// //只发利息;分期还没结束;
					// Product pro = inde.getProduct();
					//
					// StringBuffer msg = new
					// StringBuffer(configBean.findSystemConfig().getSmsQm());
					// msg.append(" 尊敬的用户:");
					// msg.append("您购买的 ");
					// msg.append(pro.getTitle());
					// msg.append(" 产品获得");
					// msg.append("利息（按月付息还本）");
					// msg.append(QwyUtil.calcNumber(inde.getAlreadyPay(), 0.01,
					// "*").doubleValue());
					// msg.append("元，利息于");
					//
					// String sentTime=QwyUtil.fmyyyyMMddHHmmss2.format(date);
					// if(date.getHours()<8){//判断发息时间是否小于八点
					// SimpleDateFormat fmyyyyMMdd = new
					// SimpleDateFormat("yyyyMMdd");
					// sentTime=fmyyyyMMdd.format(date)+"080000";
					// msg.append(QwyUtil.fmyyyyMMdd.format(date)+"上午8点前");
					// }else{
					// msg.append(QwyUtil.fmyyyyMMddHHmm.format(date));
					// }
					//
					// msg.append("发放至您的中泰理财账户，请注意查收；");
					// msg.append(smsTip);
					// System.out.println(msg.toString());
					// log.info(msg.toString());
					// //发放收益时发送短信
					// Object isStartThread =
					// PropertiesUtil.getProperties("isStartThread");
					// if(!QwyUtil.isNullAndEmpty(isStartThread)&&"1".equals(isStartThread.toString())){
					//// if("15112304365".equals(DESEncrypt.jieMiUsername(inv.getUsers().getPhone()))){
					//// SMSUtil.sendProfitMessage(DESEncrypt.jieMiUsername(inv.getUsers().getPhone()),
					// sentTime, msg.toString());
					//// }
					// //SMSUtil.sendProfitMessage(DESEncrypt.jieMiUsername(inv.getUsers().getPhone()),
					// sentTime, msg.toString());
					// }
					// }
					return "";
				} catch (Exception e) {
					// TODO Auto-generated catch block
					log.error("操作异常: ", e);
					tm.rollback(ts);
					log.info("sendProfitFreshmanThreadBean.sendProfit 数据回滚: 结算理财产品异常;");
					return "sendProfitFreshmanThreadBean.sendProfit 数据回滚: 结算理财产品异常;";
				}
			}
		}
		return "";
	}

	public void saveOrUpdate(Object obj) {
		dao.saveOrUpdate(obj);
	}
}
