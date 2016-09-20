package com.zfgt.thread.bean;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.zfgt.common.ApplicationContexts;
import com.zfgt.common.bean.MyWalletBean;
import com.zfgt.common.util.LockHolder;
import com.zfgt.common.util.PageUtil;
import com.zfgt.common.util.QwyUtil;
import com.zfgt.orm.InterestDetails;
import com.zfgt.orm.Investors;
import com.zfgt.orm.Product;
import com.zfgt.thread.dao.ThreadDAO;

/**后台线程Bean层--结算理财产品(非新手专享产品,即product.productType=0的理财产品)
 * @author qwy
 *
 * @createTime 2015-4-28上午9:54:11
 */
@Service
public class ClearingProductThreadBean {
	private Logger log = Logger.getLogger(ClearingProductThreadBean.class);
	@Resource
	private ThreadDAO dao;
	@Resource
	private MyWalletBean myWalletBean;
	@Resource
	private SendProfitThreadBean sendProfitThreadBean;
	/**
	 * 更新理财产品Bean层;
	 */
	@Resource
	private UpdateProductThreadBean updateProductThreadBean;
	
	/**查找理财产品;分页;<br>
	 * 按照 ORDER BY pro.insertTime ASC, pro.id DESC 来排序
	 * @param pageUtil 分页对象;
	 * @param investType 投资类别  0:车无忧 1:贸易通
	 * @param status 产品状态 0:营销中  1:已售罄  2:结算中   3:已还款 
	 * @param type 类别  0为普通项目,1为:新手专享;
	 * @return
	 */
	public PageUtil<Product> getProductByPageUtil(PageUtil<Product> pageUtil,String[] investType,String[] status,String[] type,Date finishTime){
		return updateProductThreadBean.getProductByPageUtil(pageUtil, investType, status, type,finishTime);
		
	}
	
	/**获取理财产品下的所有投资记录;
	 * @param productId 理财产品ID
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Investors> getInvestorsByProductId(String productId){
		StringBuffer hql = new StringBuffer();
		hql.append("FROM Investors inv ");
		hql.append("WHERE inv.productId = ? ");
		hql.append(" AND inv.investorStatus IN ('1') ");
		hql.append(" ORDER BY inv.payTime ASC, inv.id DESC ");
		return (List<Investors>)dao.LoadAll(hql.toString(), new Object[]{productId});
	}
	
	/**根据理财产品ID,投资列表ID,用户ID;来获取利息表的数据;
	 * @param productId 理财产品ID
	 * @param investorsId 投资列表ID
	 * @param usersId 用户ID
	 * @return 利息表集合
	 */
	public List<InterestDetails> getInterestDetails(String productId,String investorsId,long usersId){
		StringBuffer hql = new StringBuffer();
		hql.append("FROM InterestDetails inde ");
		hql.append("WHERE inde.productId = ?  ");
		hql.append("AND inde.investorsId = ? ");
		hql.append("AND inde.usersId = ? ");
		hql.append("AND inde.status IN ('0','1') ");
		hql.append("ORDER BY inde.orders ASC ");
		return (List<InterestDetails>)dao.LoadAll(hql.toString(), new Object[]{productId,investorsId,usersId});
		
	}
	
	
	/**结算常规理财产品;
	 * @param product
	 */
	public String clearingProduct(Product product){
		log.info("ClearingProductThreadBean.clearingProduct 进入理财产品结算");
		if(QwyUtil.isNullAndEmpty(product)){
			return "ClearingProductThreadBean.clearingProduct 找不到理财产品";
		}
		synchronized (LockHolder.getLock(product.getId())) {
			//修改于2015-06-11 00:00:49 结算产品时,不发放收益,收益在结算后的隔天发放;
			/*try {
				List<Investors> listInvestors = getInvestorsByProductId(product.getId());
				if(!QwyUtil.isNullAndEmpty(listInvestors)){
					for (Investors inv : listInvestors) {
						try {
							List<InterestDetails> listInterestDetails = getInterestDetails(inv.getProductId(), inv.getId(), inv.getUsersId());
							if(!QwyUtil.isNullAndEmpty(listInterestDetails)){
								//线程结算产品;
								log.info("后台线程结算产品,发放收益;");
								for (InterestDetails inde : listInterestDetails) {
									String temp = sendProfitThreadBean.sendProfit(inde,"线程结算产品时支付");
									if(!QwyUtil.isNullAndEmpty(temp)){
										log.info(inde.getId()+"线程结算产品  发放收益的结果: "+temp);
										return temp;
									}
								}
								inv.setInvestorStatus("3");
								dao.saveOrUpdate(inv);
							}
						} catch (Exception e) {
							// TODO Auto-generated catch block
							log.error("操作异常: ",e);
							log.info("ClearingProductThreadBean.clearingProduct 数据回滚: 结算理财产品异常;");
							return "ClearingProductThreadBean.clearingProduct 数据回滚: 结算理财产品异常;";
						}
					}
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				log.error("操作异常: ",e);
				log.error("ClearingProductThreadBean.clearingProduct 数据回滚: 结算理财产品异常;",e);
			}	*/
		}
		return "";
	}
	/**
	 * 获取购买产品的用户名列表
	 * @param productId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<String> findUsersByProductId(String productId){
		if(!QwyUtil.isNullAndEmpty(productId)){
			StringBuffer hql = new StringBuffer();		
			hql.append("select username from users ");
			hql.append("WHERE id in(select users_Id from investors where product_id = ? and investor_status in ('1','2','3'))  ");
			
			return (List<String>)dao.LoadAllSql(hql.toString(), new Object[]{productId});
		}else{
			return null;
		}
		
	}
	public void saveOrUpdate(Object obj){
		dao.saveOrUpdate(obj);
	}
}
