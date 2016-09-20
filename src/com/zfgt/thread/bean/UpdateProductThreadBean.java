package com.zfgt.thread.bean;

import java.text.ParseException;
import java.util.Date;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.jfree.util.Log;
import org.springframework.stereotype.Service;

import com.zfgt.common.util.PageUtil;
import com.zfgt.common.util.QwyUtil;
import com.zfgt.orm.Product;
import com.zfgt.thread.action.UpdateProductThread;
import com.zfgt.thread.dao.ThreadDAO;

/**后台线程--更新理财产品Bean层;
 * @author qwy
 *
 * @createTime 2015-4-28上午9:54:11
 */
@Service
public class UpdateProductThreadBean {
	private Logger log = Logger.getLogger(UpdateProductThreadBean.class);
	@Resource
	private ThreadDAO dao;
	
	/**查找理财产品;分页;<br>
	 * 按照 ORDER BY pro.insertTime ASC, pro.id DESC 来排序
	 * @param pageUtil 分页对象;
	 * @param investType 投资类别  0:车无忧 1:贸易通
	 * @param status 产品状态 0:营销中  1:已售罄  2:结算中   3:已还款 
	 * @param type 类别  0为普通项目,1为:新手专享;
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public PageUtil<Product> getProductByPageUtil(PageUtil<Product> pageUtil,String[] investType,String[] status,String[] type,Date finishTime){
		StringBuffer hql = new StringBuffer();
		hql.append(" FROM Product pro ");
		hql.append(" WHERE 1=1 ");
		if(!QwyUtil.isNullAndEmpty(investType)){
			String myInvestType = QwyUtil.packString(investType);
			hql.append(" AND pro.investType IN ("+myInvestType+") ");
		}
		if(!QwyUtil.isNullAndEmpty(status)){
			String myStatus = QwyUtil.packString(status);
			hql.append(" AND pro.productStatus IN ("+myStatus+") ");
		}
		if(!QwyUtil.isNullAndEmpty(type)){
			String myType = QwyUtil.packString(type);
			hql.append(" AND pro.productType IN ("+myType+") ");
		}
		if(!QwyUtil.isNullAndEmpty(finishTime)){
			hql.append(" AND '"+QwyUtil.fmyyyyMMdd.format(finishTime)+"' >=pro.finishTime  ");
		}
		hql.append(" ORDER BY pro.insertTime ASC, pro.id DESC ");
		return (PageUtil<Product>)dao.getPage(pageUtil, hql.toString(), null);
		
	}
	
	
	/**更新理财产品状态;(常规产品)
	 * @param product
	 */
	public void updateProductStatus(Product product){
		if(QwyUtil.isNullAndEmpty(product)){
			return;
		}
		try {
			//项目结束时间;日期格式yyyy-MM-dd
			Date finishTime = QwyUtil.fmyyyyMMdd.parse(QwyUtil.fmyyyyMMdd.format(product.getFinishTime()));
			//当前时间;日期格式yyyy-MM-dd
			Date nowDate = QwyUtil.fmyyyyMMdd.parse(QwyUtil.fmyyyyMMdd.format(new Date()));
			String status = product.getProductStatus();
			boolean isUpdate = false;
			
			if("0".equals(status) && product.getLeftCopies().longValue()==0){
				isUpdate = true;
				//产品卖光了;
				product.setProductStatus("1");
				product.setIsRecommend("0");//已售罄的时候,把推荐的属性给去掉;
				log.info("产品: "+product.getTitle()+"已卖光,进入已售罄; id: "+product.getId());
			}
			if(nowDate.getTime()>=finishTime.getTime() && ("0".equals(status) || "1".equals(status))){
				isUpdate = true;
				//项目的结束时间已经到了;把项目进入"结算中"状态;
				product.setProductStatus("2");
				product.setIsRecommend("0");
				product.setClearingTime(new Date());
				log.info("产品: "+product.getTitle()+"进入结算中; id: "+product.getId());
			}
			if(isUpdate){
				dao.saveOrUpdate(product);
			}
			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			log.error("操作异常: ",e);
		}
		
	}
	
	/**更新理财产品状态(新手专享);
	 * @param product
	 */
	public void updateProductFreshmanStatus(Product product){

		if(QwyUtil.isNullAndEmpty(product)){
			return;
		}
		try {
			//项目结束时间;日期格式yyyy-MM-dd
			Date finishTime = QwyUtil.fmyyyyMMdd.parse(QwyUtil.fmyyyyMMdd.format(product.getFinishTime()));
			//当前时间;日期格式yyyy-MM-dd
			Date nowDate = QwyUtil.fmyyyyMMdd.parse(QwyUtil.fmyyyyMMdd.format(new Date()));
			String status = product.getProductStatus();
			boolean isUpdate = false;
			
			if("0".equals(status) && product.getLeftCopies().longValue()==0){
				isUpdate = true;
				//产品卖光了;
				product.setProductStatus("1");
				product.setIsRecommend("0");
				log.info("产品: "+product.getTitle()+"已卖光,进入已售罄; id: "+product.getId());
			}
			if(nowDate.getTime()>=finishTime.getTime() && "0".equals(status)){
				isUpdate = true;
				//新手专享产品的结束时间已经到了;把项目进入"已售罄"状态;
				product.setProductStatus("1");
				product.setIsRecommend("0");
				log.info("新手专享产品: "+product.getTitle()+"进入已售罄; id: "+product.getId());
			}
			if(isUpdate){
				product.setClearingTime(new Date());
				dao.saveOrUpdate(product);
			}
			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			log.error("操作异常: ",e);
		}
		
	
	}
}
