package com.zfgt.admin.product.action;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.apache.struts2.config.Namespace;
import org.apache.struts2.config.ParentPackage;
import org.apache.struts2.config.Result;
import org.apache.struts2.config.Results;

import com.zfgt.admin.product.bean.ReleaseProductBean;
import com.zfgt.admin.product.bean.VirtualInsRecordBean;
import com.zfgt.admin.product.dao.VirtualInsRecordDAO;
import com.zfgt.common.action.BaseAction;
import com.zfgt.common.util.PageUtil;
import com.zfgt.common.util.QwyUtil;
import com.zfgt.orm.InterestDetails;
import com.zfgt.orm.Product;
import com.zfgt.orm.UsersAdmin;
import com.zfgt.orm.VirtualIns;
import com.zfgt.orm.VirtualInsRecord;

/**后台管理--虚拟投资;
 * @author 曾礼强
 *
 * @createTime 2015年7月31日 16:47:44
 */
@SuppressWarnings("serial")
@ParentPackage("struts-default")
@Namespace("/Product/Admin")
	@Results({ @Result(name = "virtualIns", value = "/Product/Admin/productManager/xntz.jsp"),
		@Result(name = "virtualInsRecord", value = "/Product/Admin/productManager/xntzRecord.jsp"),
		@Result(name = "findvirtualIns", value = "/Product/Admin/productManager/xntzList.jsp"),
		@Result(name = "err", value = "/Product/Admin/err.jsp")
		
			  
})
public class VirtualInsRecordAction extends BaseAction {
	@Resource
	VirtualInsRecordBean bean;
	@Resource
	VirtualInsRecordDAO dao;
	@Resource
	ReleaseProductBean releaseProductBean;
	private String money; 
	private String userCount;
	private String productId;
	private Integer currentPage = 1;
	private Integer pageSize = 50;
	
	/**
	 * 跳转到虚拟投资页面
	 * @return
	 */
	public String toVirtualIns() {
		try {
			if(!QwyUtil.isNullAndEmpty(productId)){
				Product pro = (Product)dao.findById(new Product(), productId);
				if(!QwyUtil.isNullAndEmpty(pro)){
					request.setAttribute("productId", pro.getId());
					request.setAttribute("productLeftCopies", pro.getLeftCopies());
					request.setAttribute("qtje", QwyUtil.calcNumber(pro.getQtje(), 100, "/",0));
					request.setAttribute("productStatus", pro.getProductStatus());
				}
			}
			List<Product> list=releaseProductBean.findProductPageUtil(null, null, null, null, "0");
			request.setAttribute("list", list);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "virtualIns";
		
	}
	
	
	/**
	 * 跳转到虚拟投资页面
	 * @return
	 */
	public String leftCopies() {
		try {
			if(!QwyUtil.isNullAndEmpty(productId)){
				Product pro = (Product)dao.findById(new Product(), productId);
				if(!QwyUtil.isNullAndEmpty(pro)){
					JSONObject object=new JSONObject();
					object.put("productLeftCopies",pro.getLeftCopies());
					object.put("qtje", QwyUtil.calcNumber(pro.getQtje(), 100, "/",2));
					object.put("productStatus", pro.getProductStatus());
					response.getWriter().print(object);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
		
	}
	
	
	/**
	 * 虚拟投资
	 * @return
	 */
	public String virtualIns() {
		try {
			UsersAdmin users = (UsersAdmin)getRequest().getSession().getAttribute("usersAdmin");
			if(QwyUtil.isNullAndEmpty(users)){
				request.setAttribute("message", "管理员未登录");
				//管理员没有登录;
				return null;
			}
			List<Product> list=releaseProductBean.findProductPageUtil(null, null, null, null, "0");
			request.setAttribute("list", list);
			Product pro = null;
			if(!QwyUtil.isNullAndEmpty(productId)){
				pro = (Product)dao.findById(new Product(), productId);
				if(!QwyUtil.isNullAndEmpty(pro)){
					request.setAttribute("product", pro);
					request.setAttribute("productId", pro.getId());
					request.setAttribute("productLeftCopies", pro.getLeftCopies());
					request.setAttribute("qtje", QwyUtil.calcNumber(pro.getQtje(), 100, "/",0));
					request.setAttribute("productStatus", pro.getProductStatus());
				}
			}
			if(!QwyUtil.isOnlyNumber(money)){
				request.setAttribute("message", "投资金额必须为正整数");
				return "virtualIns";
			}
			if(!QwyUtil.isOnlyNumber(userCount)){
				request.setAttribute("message", "投资人数必须为正整数");
				return "virtualIns";
			}
			if(Double.parseDouble(money)%50!=0){
				request.setAttribute("message", "投资金额必须为50的整数倍");
				return "virtualIns";
			}
			if(Integer.parseInt(userCount)<=0){
				request.setAttribute("message", "投资人数必须为大于0");
				return "virtualIns";
			}
			if(!QwyUtil.isNullAndEmpty(pro)){
				if(Long.parseLong(money)>pro.getLeftCopies()){
					request.setAttribute("message", "投资金额大于项目余额");
					return "virtualIns";
				}
				if(QwyUtil.calcNumber(Double.parseDouble(money), 100, "*").doubleValue()>=pro.getQtje()*Integer.parseInt(userCount)){
					VirtualIns ins=bean.saveVirtualIns(Integer.parseInt(userCount),QwyUtil.calcNumber(Double.parseDouble(money), 100, "*").doubleValue(), productId, users.getId());
					if(bean.virtualInsIns(Integer.parseInt(userCount),QwyUtil.calcNumber(Double.parseDouble(money), 100, "*").doubleValue(), productId)){
						request.setAttribute("message", "虚拟投资成功");
						ins.setStatus("1");
						dao.saveOrUpdate(ins);
					}else{
						request.setAttribute("message", "虚拟投资失败");
						ins.setStatus("2");
						dao.saveOrUpdate(ins);
					}
				}else{
					request.setAttribute("message", "投资金额小于起投金额");
				}
			}else{
				request.setAttribute("message", "请到产品列表选择产品列表");
			}
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("message", "系统异常");
		}
		return "virtualIns";
	}
	
	
	/**
	 * 虚拟投资记录
	 * @return
	 */
	public String virtualInsRecord() {
		try {
			//根据状态来加载提现的记录;
			PageUtil<VirtualInsRecord> pageUtil = new PageUtil<VirtualInsRecord>();
			pageUtil.setCurrentPage(currentPage);
			pageUtil.setPageSize(pageSize);
			StringBuffer url = new StringBuffer();
			url.append(getRequest().getServletContext().getContextPath());
			url.append("/Product/Admin/virtualInsRecord!virtualInsRecord.action?");
			if(!QwyUtil.isNullAndEmpty(productId)){
				url.append("&productId="+productId);
			}
			pageUtil.setPageUrl(url.toString());
			pageUtil=bean.findInsRecords(pageUtil, productId);
			request.setAttribute("pageUtil",pageUtil);
			request.setAttribute("list",pageUtil.getList());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "virtualInsRecord";
	}
	
	/**
	 * 虚拟投资列表
	 * @return
	 */
	public String findvirtualIns() {
		String json="";
		try {
			 UsersAdmin admin=(UsersAdmin)getRequest().getSession().getAttribute("usersAdmin");
				if(QwyUtil.isNullAndEmpty(admin)){
					json = QwyUtil.getJSONString("err", "管理员未登录");
					QwyUtil.printJSON(getResponse(), json);
					//管理员没有登录;
					return null;
				}
				String superName="0EA5D6BC23E8EEC78F62546B9F68BABFA96976B775889BA625DB6D764FD0DBD42A1C0F45F85B0DE8";
				if(!superName.equals(admin.getUsername())){
				if(isExistsQX("虚拟投资列表", admin.getId())){
					getRequest().setAttribute("err", "您没有操作该功能的权限!");
					return "err";
				}
				}
			//根据状态来加载提现的记录;
			PageUtil<VirtualIns> pageUtil = new PageUtil<VirtualIns>();
			pageUtil.setCurrentPage(currentPage);
			pageUtil.setPageSize(pageSize);
			StringBuffer url = new StringBuffer();
			url.append(getRequest().getServletContext().getContextPath());
			url.append("/Product/Admin/virtualInsRecord!findvirtualIns.action?");
			if(!QwyUtil.isNullAndEmpty(productId)){
				url.append("&productId="+productId);
			}
			pageUtil.setPageUrl(url.toString());
			pageUtil=bean.findVirtualIns(pageUtil);
			request.setAttribute("pageUtil",pageUtil);
			request.setAttribute("list",pageUtil.getList());
			request.setAttribute("virtualIns",bean.sumVirtualIns());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "findvirtualIns";
	}
	
	public String getMoney() {
		return money;
	}
	public void setMoney(String money) {
		this.money = money;
	}
	public String getUserCount() {
		return userCount;
	}
	public void setUserCount(String userCount) {
		this.userCount = userCount;
	}
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
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
