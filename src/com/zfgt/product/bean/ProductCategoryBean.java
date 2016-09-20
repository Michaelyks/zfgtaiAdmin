package com.zfgt.product.bean;

import java.util.ArrayList;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.zfgt.common.util.PageUtil;
import com.zfgt.common.util.QwyUtil;
import com.zfgt.orm.Product;
import com.zfgt.orm.Users;
import com.zfgt.product.dao.ProductCategoryDAO;

/**理财产品Bean层,处理业务逻辑;
 * @author qwy
 *
 * @createTime 2015-04-20 00:57:26
 */
@Service
public class ProductCategoryBean {
	
	private static Logger log = Logger.getLogger(ProductCategoryBean.class);
	@Resource
	private ProductCategoryDAO dao;
	
	/**加载理财产品,根据分页;
	 * @param pageUtil 分页对象;
	 * @return
	 */
	public PageUtil<Product> getProductByCondition(PageUtil<Product> pageUtil,Product product){
		try {
			String status = "";
			String type = "";
			if(QwyUtil.isNullAndEmpty(product) || "all".equalsIgnoreCase(product.getProductStatus())){
				status = "'0','1','2','3'";
			}else{
				status ="'"+product.getProductStatus()+"'";
			}
			
			if(QwyUtil.isNullAndEmpty(product) || "all".equalsIgnoreCase(product.getInvestType())){
				type = "'0','1','2','3'";
			}else{
				type ="'"+product.getInvestType()+"'";
			}
			StringBuffer buff = new StringBuffer();
			buff.append("FROM Product pro ");
			buff.append("WHERE pro.productType !=1 AND pro.productStatus IN ("+status+") ");
			buff.append("AND pro.investType IN ("+type+") ");
			buff.append("ORDER BY cast(pro.productStatus as int) ASC, cast(pro.productType as int) DESC,pro.isRecommend DESC,  pro.insertTime DESC  ");
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
	public Product getProductById(String productId,String productType){
		try {
			StringBuffer hql = new StringBuffer();
			hql.append("FROM Product pro ");
			hql.append("WHERE pro.id = ? ");
			ArrayList<Object> array = new ArrayList<Object>();
			array.add(productId);
			if (!QwyUtil.isNullAndEmpty(productType)) {
				hql.append(" AND pro.productType = ? ");
				array.add(productType);
			}
			return (Product) dao
					.findJoinActive(hql.toString(), array.toArray());
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage(), e);
		}
		return null;
	}
	
	public Users getUsersById(long usersId){
		return (Users)dao.findById(new Users(), usersId);
	}
}
