package com.zfgt.thread.bean;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.zfgt.common.bean.MyWalletBean;
import com.zfgt.common.bean.SystemConfigBean;
import com.zfgt.common.util.DESEncrypt;
import com.zfgt.common.util.PageUtil;
import com.zfgt.common.util.PropertiesUtil;
import com.zfgt.common.util.QwyUtil;
import com.zfgt.common.util.SMSUtil;
import com.zfgt.orm.InterestDetails;
import com.zfgt.orm.Investors;
import com.zfgt.orm.Product;
import com.zfgt.orm.SystemConfig;
import com.zfgt.thread.dao.ThreadDAO;

/**
 * 后台线程Bean层--结算理财产品(非新手专享产品,即product.productType=0的理财产品)
 * 
 * @author qwy
 *
 * @createTime 2015-4-28上午9:54:11
 */
@Service
public class ClearingProductFreshmanThreadBean {
	private Logger log = Logger.getLogger(ClearingProductFreshmanThreadBean.class);
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
	 * 查找新手专享理财产品;【已售罄】状态的; 分页;<br>
	 * <b>新手专享项目</b> 按照 ORDER BY pro.insertTime ASC, pro.id DESC 来排序
	 * 
	 * @param pageUtil
	 *            分页对象;
	 * @param investType
	 *            投资类别 0:车无忧 1:贸易通
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public PageUtil<Product> getProductByPageUtil(PageUtil<Product> pageUtil, String investType) {
		StringBuffer hql = new StringBuffer();
		hql.append(" FROM Product pro ");
		hql.append(" WHERE ");
		hql.append(" pro.investType = ? ");
		hql.append(" AND pro.productStatus = '1' ");// 产品状态 0:营销中 1:已售罄 2:结算中
													// 3:已还款
		hql.append(" AND pro.productType = '1' ");// 类别 0为普通项目,1为:新手专享;
		hql.append(" AND date_add_interval(DATE_FORMAT(pro.clearingTime,'%Y-%m-%d'), 10, DAY) <=NOW() ");
		// hql.append(" AND DATE_FORMAT(pro.clearingTime,'%Y-%m-%d') <=
		// pro.finishTime ");
		hql.append(" ORDER BY pro.clearingTime ASC, pro.id DESC ");
		return (PageUtil<Product>) dao.getPage(pageUtil, hql.toString(), new Object[] { investType });

	}

	/**
	 * 获取理财产品下的所有投资记录;
	 * 
	 * @param productId
	 *            理财产品ID
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Investors> getInvestorsByProductId(String productId) {
		StringBuffer hql = new StringBuffer();
		hql.append("FROM Investors inv ");
		hql.append("WHERE inv.productId = ? ");
		hql.append(" AND inv.investorStatus IN ('1') ");// 1为已付款
		hql.append(" ORDER BY inv.payTime ASC, inv.id DESC ");
		return (List<Investors>) dao.LoadAll(hql.toString(), new Object[] { productId });
	}

	/**
	 * 根据理财产品ID,投资列表ID,用户ID;来获取利息表的数据;
	 * 
	 * @param productId
	 *            理财产品ID
	 * @param investorsId
	 *            投资列表ID
	 * @param usersId
	 *            用户ID
	 * @return 利息表集合
	 */
	public List<InterestDetails> getInterestDetails(String productId, String investorsId, long usersId) {
		StringBuffer hql = new StringBuffer();
		hql.append("FROM InterestDetails inde ");
		hql.append("WHERE inde.productId = ?  ");
		hql.append("AND inde.investorsId = ? ");
		hql.append("AND inde.usersId = ? ");
		hql.append("AND inde.status IN ('0','1') ");
		hql.append("ORDER BY inde.orders ASC ");
		return (List<InterestDetails>) dao.LoadAll(hql.toString(), new Object[] { productId, investorsId, usersId });

	}

	/**
	 * 更新理财产品状态;
	 * 
	 * @param product
	 */
	public String clearingProductFreshman(Product product) {
		log.info("ClearingProductFreshmanThreadBean.clearingProduct 进入理财产品结算");
		if (QwyUtil.isNullAndEmpty(product)) {
			return "ClearingProductFreshmanThreadBean.clearingProduct 找不到理财产品";
		}
		// 修改于2015-06-11 00:00:49 结算产品时,不发放收益,收益在结算后的隔天发放;
		/*
		 * try { List<Investors> listInvestors =
		 * getInvestorsByProductId(product.getId());
		 * if(!QwyUtil.isNullAndEmpty(listInvestors)){ for (Investors inv :
		 * listInvestors) { inv.setInvestorStatus("2"); inv.setClearTime(new
		 * Date()); dao.saveOrUpdate(inv); List<InterestDetails>
		 * listInterestDetails = getInterestDetails(inv.getProductId(),
		 * inv.getId(), inv.getUsersId());
		 * if(!QwyUtil.isNullAndEmpty(listInterestDetails)){ for
		 * (InterestDetails inde : listInterestDetails) { String temp =
		 * sendProfitThreadBean.sendProfit(inde,"线程结算产品时支付");
		 * if(!QwyUtil.isNullAndEmpty(temp)){ log.info(inde.getId()+
		 * "clearingProductFreshman 线程结算产品  发放收益的结果: "+temp); break; } } } } } }
		 * catch (Exception e) { // TODO Auto-generated catch block log.error(
		 * "操作异常: ",e); log.error(
		 * "ClearingProductFreshmanThreadBean.clearingProduct 数据回滚: 结算理财产品异常;"
		 * ,e); }
		 */
		return "";
	}

	/**
	 * 查找当日投资到期新手投资列表;<br>
	 * 按照 ORDER BY insertTime ASC, orders ASC ,productId ASC 来排序
	 * 
	 * @param pageUtil
	 *            分页对象;
	 * @param status
	 *            状态 0未支付,1已冻结,2已支付,3已删除
	 * @return
	 * @throws ParseException
	 */
	@SuppressWarnings("unchecked")
	public PageUtil<InterestDetails> getFreshManInterestDetails(PageUtil<InterestDetails> pageUtil, String[] status, String nowDate) throws Exception {
		StringBuffer hql = new StringBuffer();
		List<Object> list = new ArrayList<Object>();
		hql.append(" FROM InterestDetails inde ");
		hql.append(" WHERE inde.product.productType IN ('1') ");
		String[] time = QwyUtil.splitTime(nowDate);
		if (time.length > 1) {
			hql.append(" AND inde.finishTime >= ? ");
			list.add(QwyUtil.fmMMddyyyy.parse(time[0]));
			hql.append(" AND inde.finishTime <= ? ");
			list.add(QwyUtil.fmMMddyyyy.parse(time[1]));
		} else {
			hql.append(" AND inde.finishTime >= ? ");
			list.add(QwyUtil.fmMMddyyyyHHmmss.parse(time[0] + " 00:00:00"));
			hql.append(" AND inde.finishTime <= ? ");
			list.add(QwyUtil.fmMMddyyyyHHmmss.parse(time[0] + " 23:59:59"));
		}
		if (!QwyUtil.isNullAndEmpty(status)) {
			String myStatus = QwyUtil.packString(status);
			hql.append(" AND inde.status IN (" + myStatus + ") ");
		}

		hql.append(" ORDER BY inde.finishTime ASC, orders ASC ,inde.productId ASC ");
		System.out.println("sql语句" + hql);
		return (PageUtil<InterestDetails>) dao.getPage(pageUtil, hql.toString(), list.toArray());

	}

	/**
	 * 发送新手投资到期提醒短信
	 * 
	 * @param inde
	 * @param interestDetailsNote
	 * @param smsTip
	 * @return
	 */
	public String sendMessage(InterestDetails inde, String interestDetailsNote) {
		try {
			final SystemConfig systemConfig = dao.getSystemConfig();
			if (!QwyUtil.isNullAndEmpty(inde)) {
				Date date = new Date();
				String sentTime = QwyUtil.fmyyyyMMddHHmmss2.format(date);
				if (date.getHours() < 8) {// 判断发短信时间是否小于八点
					SimpleDateFormat fmyyyyMMdd = new SimpleDateFormat("yyyyMMdd");
					sentTime = fmyyyyMMdd.format(date) + "080000";
				}
				// 发送短信
				Product pro = inde.getProduct();
				StringBuffer msg = new StringBuffer(configBean.findSystemConfig().getSmsQm());
				msg.append("尊敬的用户：");
				msg.append("您购买的  ");
				msg.append(pro.getTitle());
				msg.append(" 产品于今天");
				msg.append(QwyUtil.fmyyyyMMdd.format(inde.getFinishTime()));
				msg.append("到期，");
				msg.append("资金会在");
				msg.append(QwyUtil.fmyyyyMMdd.format(QwyUtil.addDaysFromOldDate(inde.getFinishTime(), 1).getTime()));
				msg.append("上午发放至您的中泰理财账户，");
				msg.append("请注意查收；");
				msg.append(systemConfig.getSmsTip());
				System.out.println(msg.toString());
				log.info(msg.toString());
				// 发送短信
				Object isStartThread = PropertiesUtil.getProperties("isStartThread");
				if (!QwyUtil.isNullAndEmpty(isStartThread) && "1".equals(isStartThread.toString())) {
					// if("13410927638".equals(DESEncrypt.jieMiUsername(inde.getUsers().getPhone()))){
					SMSUtil.sendProfitMessage(DESEncrypt.jieMiUsername(inde.getUsers().getPhone()), sentTime, msg.toString());
					// }
				}
				return "";
			}
		} catch (Exception e) {
			log.error("操作异常: ", e);

		}
		return "";
	}

	/**
	 * 修改状态
	 * 
	 * @param isSendMessage
	 *            ( 发送短信 0:未发送 1：已发送 )
	 * @return
	 */
	public String updateInterestDetails(InterestDetails interestDetails, String isSendMessage) {
		interestDetails.setIsSendMessage(isSendMessage);
		interestDetails.setUpdateTime(new Date());
		dao.saveOrUpdate(interestDetails);
		return null;
	}

	public void saveOrUpdate(Object obj) {
		dao.saveOrUpdate(obj);
	}
}
