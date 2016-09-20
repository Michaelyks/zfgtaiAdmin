package com.zfgt.product.action;

import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.Logger;
import org.apache.struts2.config.Namespace;
import org.apache.struts2.config.ParentPackage;
import org.apache.struts2.config.Result;
import org.apache.struts2.config.Results;

import com.zfgt.common.util.PageUtil;
import com.zfgt.common.util.QwyUtil;
import com.zfgt.orm.Product;
import com.zfgt.orm.Users;
import com.zfgt.orm.UsersLogin;
import com.zfgt.product.bean.ProductCategoryBean;

/**理财产品Action层;<br>
 * 处理页面发过来的请求
 * @author qwy
 *
 * @createTime 2015-4-15下午10:35:44
 */
@SuppressWarnings("serial")
@ParentPackage("struts-default")
@Namespace("/Product")
//理财产品
@Results({ 
	@Result(name = "category", value = "/Product/productCategory.jsp"),
	@Result(name = "error", value = "/Product/error.jsp")
})
public class ProductCategoryAction extends IndexAction {
	private static Logger log = Logger.getLogger(ProductCategoryAction.class);
	@Resource
	private ProductCategoryBean bean;
	private Integer currentPage = 1;
	private Integer pageSize = 7;
	private Product product;
	
	/**加载产品列表;
	 * @return List&lt;{@link Product}&gt;
	 */
	public String loadProduct(){
		try {
			PageUtil<Product> pageUtil =getProductByPageUtil(1, pageSize,product);
			HttpServletRequest request = this.request;
			if(!QwyUtil.isNullAndEmpty(pageUtil)){
				List<Product> productList = pageUtil.getList();
				getRequest().setAttribute("productList",productList);
				getRequest().setAttribute("pageCount", pageUtil.getPageCount());
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.error("操作异常: ",e);
		}
		//super.setPageSize(1);
		return "category";
	}
	
	
	/**根据条件,来查询产品列表
	 * @return
	 */
	public String getProductByCondition(){
		PageUtil<Product> pageUtil = getProductByPageUtil(currentPage, pageSize, product);
		if(!QwyUtil.isNullAndEmpty(pageUtil)){
			List<Product> productList = pageUtil.getList();
			getRequest().setAttribute("productList",productList);
			getRequest().setAttribute("pageCount",pageUtil.getPageCount());
		}
		getRequest().setAttribute("currentPage",currentPage);
		getRequest().setAttribute("product",product);
		return "category";
	}
	
	
	/**根据分页对象获取产品列表
	 * @param currentPage 当前页数
	 * @param pageSize 显示条数
	 * @param status 查询状态
	 * @return
	 */
	public PageUtil<Product> getProductByPageUtil(int currentPage,int pageSize,Product product){
		PageUtil<Product> pageUtil = new PageUtil<Product>();
		pageUtil.setCurrentPage(currentPage);
		pageUtil.setPageSize(pageSize);
		pageUtil = bean.getProductByCondition(pageUtil,product);
		return pageUtil;
	}
	
	
	/**判断用户是否登录;
	 * @return
	 */
	public String isLogin(){
		try {
			UsersLogin usersLogin  = (UsersLogin)getRequest().getSession().getAttribute("usersLogin");
			String json = "";
			if(QwyUtil.isNullAndEmpty(usersLogin)){
				json = QwyUtil.getJSONString("no", "需要登录才能购买,是否立即登录?");
			}else{
				Users users = bean.getUsersById(usersLogin.getUsersId());
				////是否购买过新手产品;0:没有购买过; 1:购买过
				String isBuy = users.getBuyFreshmanProduct();
				if(product==null){
					//产品不存在
					json = QwyUtil.getJSONString("err", "理财产品不存在");
					QwyUtil.printJSON(getResponse(), json);
					return null;
				}
				Product pro = bean.getProductById(product.getId(), "1");
				if("1".equals(isBuy) && !QwyUtil.isNullAndEmpty(pro)){
					//购买过新手产品,不允许再次购买;
					json = QwyUtil.getJSONString("hasBuy", "每个用户只能购买一次新手产品");
				}else{
					json = QwyUtil.getJSONString("ok", "已登录");
				}
			}
			QwyUtil.printJSON(getResponse(), json);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			log.error("操作异常: ",e);
		}
		return null;
	}


	public Product getProduct() {
		return product;
	}


	public void setProduct(Product product) {
		this.product = product;
	}


	public Integer getCurrentPage() {
		return currentPage;
	}


	public void setCurrentPage(Integer currentPage) {
		this.currentPage = currentPage;
	}


	public Integer getPageSize() {
		return pageSize;
	}


	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

}