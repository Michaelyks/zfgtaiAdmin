package com.zfgt.product.bean;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.zfgt.common.util.PageUtil;
import com.zfgt.common.util.QwyUtil;
import com.zfgt.orm.Investors;
import com.zfgt.orm.Product;
import com.zfgt.product.action.ProductCategoryAction;
import com.zfgt.product.dao.IndexDAO;

/**首页Bean层,处理业务逻辑;
 * @author qwy
 *
 * @createTime 2015-4-15下午10:34:42
 */
@Service
public class IndexBean {
	private static Logger log = Logger.getLogger(IndexBean.class);
	
	@Resource
	private IndexDAO dao;
	
	/**加载理财产品,根据分页;
	 * @param pageUtil 分页对象;
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public PageUtil<Product> getProductByPageUtil(PageUtil<Product> pageUtil,String[] status){
		try {
			String st = "";
			if(QwyUtil.isNullAndEmpty(status)){
				st = "'0','1','2','3'";
			}else{
				st = QwyUtil.packString(status);
			}
			StringBuffer buff = new StringBuffer();
			buff.append("FROM Product pro ");
			buff.append("WHERE pro.productStatus IN ("+st+") ");
			//buff.append("ORDER BY cast(pro.productStatus as int) ASC, cast(pro.productType as int) DESC,pro.isRecommend DESC,pro.progress DESC, pro.insertTime DESC  ");
			buff.append("ORDER BY cast(pro.productStatus as int) ASC, cast(pro.productType as int) DESC,pro.isRecommend DESC, pro.insertTime DESC  ");
			return (PageUtil<Product>)dao.getPage(pageUtil, buff.toString(), null);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.error("操作异常: ",e);
		}
		return null;
	}
	
	/**根据产品id查找产品信息;
	 * @param productId ProductId
	 * @return {@link Product}
	 */
	public Product getProductById(String productId){
		try {
			StringBuffer hql = new StringBuffer();
			hql.append("FROM Product pro ");
			hql.append("WHERE pro.id = ? ");
			return (Product)dao.findJoinActive(hql.toString(), new Object[]{productId});
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage(), e);
		}
		return null;
	}
	
	/**根据理财产品ID,获取理财产品的所有投资记录;
	 * @param pageUtil 分页对象;
	 * @param productId 理财产品ID
	 * @return PageUtil
	 */
	@SuppressWarnings("unchecked")
	public List<Investors> loadInvestorsByProId(String productId,Integer start,Integer end){
		List<Investors> list=new ArrayList<Investors>();
		try {
			StringBuffer buff = new StringBuffer();
			buff.append(" FROM Investors ivs ");
			buff.append(" WHERE ivs.investorStatus IN ('1','2','3') ");
			ArrayList<Object> array = new ArrayList<Object>();
			if(!QwyUtil.isNullAndEmpty(productId)){
				buff.append("AND ivs.productId = ? ");
				array.add(productId);
			}
			buff.append(" ORDER BY ivs.payTime DESC,ivs.id ASC ");
			list=dao.findList(buff.toString(), array.toArray(),start,end);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.error("操作异常: ",e);
		}
		return list;
	
	}
	
	/**根据理财产品ID,获取理财产品的所有投资记录;
	 * @param pageUtil 分页对象;
	 * @param productId 理财产品ID
	 * @return PageUtil
	 */
	@SuppressWarnings("unchecked")
	public PageUtil<Investors> loadInvestorsByProId(PageUtil<Investors> pageUtil,String productId){

		try {
			StringBuffer buff = new StringBuffer();
			buff.append("FROM Investors ivs ");
			buff.append("WHERE ivs.investorStatus IN ('1','2','3') ");
			ArrayList<Object> array = new ArrayList<Object>();
			
			if(!QwyUtil.isNullAndEmpty(productId)){
				buff.append("AND ivs.productId = ? ");
				array.add(productId);
			}
			buff.append(" ORDER BY ivs.payTime DESC,ivs.id ASC");
			return (PageUtil<Investors>)dao.getPage(pageUtil, buff.toString(), array.toArray());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.error("操作异常: ",e);
		}
		return null;
	
	}
	
	/**获取新手产品,只显示第一个;
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public Product getFreshmanProduct(){
		try {
			StringBuffer buff = new StringBuffer();
			buff.append("FROM Product pro ");
			buff.append("WHERE pro.productType = '1' ");
			buff.append("AND pro.productStatus IN ('0','1','2','3') ");
			buff.append(" ORDER BY pro.productStatus ASC,pro.insertTime DESC,pro.id DESC");
			List list = dao.LoadAll(buff.toString(), null);
			if(!QwyUtil.isNullAndEmpty(list)){
				return (Product)list.get(0);
			}
		} catch (Exception e) {
			log.error("操作异常: ",e);
			e.printStackTrace();
		}
		return null;
	}
	
	/**加载新版首页的理财产品;
	 * @param pageUtil 分页对象;
	 * @param status 产品状态 -2：审核不通过 -1:未审核 0:营销中  1:已售罄  2:结算中   3:已还款 
	 * @param category 分类; 0:优选理财 1:特色理财;为null时,不分类;
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public PageUtil<Product> loadIndexProduct(PageUtil<Product> pageUtil,String[] status,String category){
		try {
			StringBuffer buff = new StringBuffer();
			buff.append("FROM Product pro ");
			buff.append("WHERE pro.productType != 1 ");
			String st = "";
			//状态;
			if(QwyUtil.isNullAndEmpty(status)){
				st = "'0','1','2','3'";
			}else{
				st = QwyUtil.packString(status);
			}
			buff.append("AND pro.productStatus IN ("+st+") ");
			//种类;
			if(!QwyUtil.isNullAndEmpty(category)){
				if("0".equals(category)){
					//1:优选理财,理财期限>31天;0:优选理财;1:特色理财
					//buff.append(" AND pro.lcqx >31 ");
					buff.append(" AND pro.module = 0 ");
				}else if("1".equals(category)){
					//2:特色理财,理财期限<=31天;
					buff.append(" AND pro.module = 1 ");
				}
			}
			//buff.append("ORDER BY cast(pro.productStatus as int) ASC, cast(pro.productType as int) DESC,pro.isRecommend DESC,pro.progress DESC, pro.insertTime DESC  ");
			buff.append("ORDER BY cast(pro.productStatus as int) ASC,pro.isRecommend DESC, pro.insertTime DESC  ");
			return (PageUtil<Product>)dao.getPage(pageUtil, buff.toString(), null);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.error("操作异常: ",e);
		}
		return null;
	}
}
