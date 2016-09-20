package com.zfgt.admin.product.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

import net.sf.jasperreports.engine.JasperPrint;

import org.apache.struts2.config.Namespace;
import org.apache.struts2.config.ParentPackage;
import org.apache.struts2.config.Result;
import org.apache.struts2.config.Results;

import com.zfgt.admin.product.bean.ReleaseFundProductBean;
import com.zfgt.admin.product.bean.ReleaseProductBean;
import com.zfgt.admin.product.bean.VirtualInsRecordBean;
import com.zfgt.common.action.BaseAction;
import com.zfgt.common.bean.PlatformBean;
import com.zfgt.common.util.CompressPicDemo;
import com.zfgt.common.util.DESEncrypt;
import com.zfgt.common.util.PageUtil;
import com.zfgt.common.util.QwyUtil;
import com.zfgt.orm.Platform;
import com.zfgt.orm.Product;
import com.zfgt.orm.SystemConfig;
import com.zfgt.orm.UsersAdmin;
import com.zfgt.orm.Zjsumx;
import com.zfgt.product.bean.IndexBean;



/**
 * 后台发布基金产品
 * @author 覃文勇
 * @createTime 2015-8-27上午11:14:13
 */
@SuppressWarnings("serial")
@ParentPackage("struts-default")
@Namespace("/Product/Admin")
// 发布产品页面
@Results({
		@Result(name = "release", value = "/Product/Admin/productManager/releaseProductFund.jsp"),
		@Result(name = "preview", value = "/Product/Admin/productManager/productDetails.jsp"),
		@Result(name = "productSend", value = "/Product/Admin/productManager/product.jsp"),
		@Result(name = "modifyFundProduct", value = "/Product/Admin/productManager/modifyFundProduct.jsp"),
		@Result(name = "err", value = "/Product/Admin/err.jsp")
		
})
public class ReleaseFundProductAction extends BaseAction {

	/**
	 * 产品
	 */
	private Product product;
	@Resource
	private ReleaseFundProductBean bean;
	@Resource
	IndexBean indexBean;
	@Resource
	private PlatformBean platformBean;

	private String endTime;
	private String productStatus;

	private String finishTime;
	private String insertTime;
	private String username;
	private String productId;
	
	
	private String title;
	private String annualEarnings;
	private String financingAmount;
	
	private File file;
	private String fileContentType;
	private String fileFileName;
	private String removeId;
	private Integer currentPage = 1;
	private Integer pageSize = 50;	
    //浮动收益区间字段	
	private String fdsy1;
	private String fdsy2;
	private String fdsy3;
	private String fdsy4;
	private String fdsy5;
	private String fdsy6;
	private String fdsy7;
	private String fdsy8;
	
	
	
	/**
	 * 加载发布基金产品的页面；
	 * 
	 * @return
	 */
	public String sendFundProduct() {
		String json = "";
		try {
		UsersAdmin users = (UsersAdmin)getRequest().getSession().getAttribute("usersAdmin");
		if(QwyUtil.isNullAndEmpty(users)){
			json = QwyUtil.getJSONString("err", "管理员未登录");
			QwyUtil.printJSON(getResponse(), json);
			//管理员没有登录;
			return null;
		}
		String superName="0EA5D6BC23E8EEC78F62546B9F68BABFA96976B775889BA625DB6D764FD0DBD42A1C0F45F85B0DE8";
		if(!superName.equals(users.getUsername())){
		if(isExistsQX("发布基金产品", users.getId())){
			getRequest().setAttribute("err", "您没有操作该功能的权限!");
			return "err";
		}
		}
		int productCount = bean.getProductCount(new String[] { "0", "1" ,"2"});
		getRequest().setAttribute("productCount", productCount);
		return "release";
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	

	
	/**
	 * 得到预览产品实体
	 * @param product
	 * @return
	 * @throws Exception
	 */
	public Product getPvProduct(Product product) throws Exception{
		if (!QwyUtil.isNullAndEmpty(product)) {
			Long newFinancialAmount = QwyUtil.calcNumber(
					product.getFinancingAmount(), 100, "*").longValue();
			Long atleastMoney = QwyUtil.calcNumber(
					product.getAtleastMoney(), 100, "*").longValue();
			product.setFinancingAmount(newFinancialAmount);
			product.setAtleastMoney(atleastMoney);
			product.setEndTime(QwyUtil.fmyyyyMMdd.parse(endTime));
			product.setFinishTime((QwyUtil.fmyyyyMMdd.parse(finishTime)));
			product.setAllCopies(QwyUtil.calcNumber(product.getFinancingAmount(), 100, "/").longValue());
			product.setHasCopies(0L);
			product.setLeftCopies(product.getAllCopies());
			product.setProductType("2");
			product.setProductStatus("-1");
			product.setUserCount(0L);
			product.setInsertTime(new Date());
			product.setProgress(0d);
		}
		return product;
	}
	/**预览基金产品;<br>
	 * 根据产品id查找产品;
	 * @return
	 */
	public String showFundProductDetails(){
		try {
			if(!QwyUtil.isNullAndEmpty(productId)&&!QwyUtil.isNullAndEmpty(productId)){
				product=indexBean.getProductById(productId);
			}else{
				product=getPvProduct(product);
				
			}
			getRequest().setAttribute("product",product );
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.error("操作异常: ",e);
			log.error("ReleaseFund.showFundProductDetails",e);
		}
		return "preview";
	}

	/**
	 * 发布基金产品
	 * 
	 * @return
	 */
	public String releaseFundProduct() {
		try {

			request = getRequest();
			if (!QwyUtil.isNullAndEmpty(product)) {
				if(QwyUtil.isNullAndEmpty(product.getId())){//为空则为添加新基金产品
					Long newFinancialAmount = QwyUtil.calcNumber(
							product.getFinancingAmount(), 100, "*").longValue();
					Long atleastMoney = QwyUtil.calcNumber(
							product.getAtleastMoney(), 100, "*").longValue();
					product.setFinancingAmount(newFinancialAmount);
					product.setAtleastMoney(atleastMoney);
					product.setEndTime(QwyUtil.fmyyyyMMdd.parse(endTime));
					product.setFinishTime((QwyUtil.fmyyyyMMdd.parse(finishTime)));
					if(QwyUtil.isNullAndEmpty(product.getRealName())){
						System.out.println("姓名不正确!");
						request.setAttribute("isOk", "姓名不正确!");
						return "release";
					}
					
					if(QwyUtil.isNullAndEmpty(product.getPhone())){
						System.out.println("联系号码不正确!");
						request.setAttribute("isOk", "联系号码不正确!");
						return "release";
					}
					product.setPhone(DESEncrypt.jiaMiUsername(product.getPhone()));
					if(QwyUtil.isNullAndEmpty(product.getIdcard())){
						System.out.println("身份证号不正确!");
						request.setAttribute("isOk", "身份证号不正确!");
						return "release";
					}
					product.setIdcard(DESEncrypt.jiaMiIdCard(product.getIdcard()));
					if(QwyUtil.isNullAndEmpty(product.getAddress())){
						System.out.println("联系地址不正确!");
						request.setAttribute("isOk", "联系地址不正确!");
						return "release";
					}
					StringBuffer fdsyqj=new StringBuffer();
					if(!QwyUtil.isNullAndEmpty(fdsy1)){
						fdsyqj.append(fdsy1);
						fdsyqj.append(",");
					}
					if(!QwyUtil.isNullAndEmpty(fdsy2)){
						fdsyqj.append(fdsy2);
						fdsyqj.append(",");
					}
					if(!QwyUtil.isNullAndEmpty(fdsy3)){
						fdsyqj.append(fdsy3);
						fdsyqj.append(",");
					}
					if(!QwyUtil.isNullAndEmpty(fdsy4)){
						fdsyqj.append(fdsy4);
						fdsyqj.append(",");
					}
					if(!QwyUtil.isNullAndEmpty(fdsy5)){
						fdsyqj.append(fdsy5);
						fdsyqj.append(",");
					}
					if(!QwyUtil.isNullAndEmpty(fdsy6)){
						fdsyqj.append(fdsy6);
						fdsyqj.append(",");
					}
					if(!QwyUtil.isNullAndEmpty(fdsy7)){
						fdsyqj.append(fdsy7);
						fdsyqj.append(",");
					}
					if(!QwyUtil.isNullAndEmpty(fdsy8)){
						fdsyqj.append(fdsy8);
						fdsyqj.append(",");
					}
					if(!QwyUtil.isNullAndEmpty(fdsyqj)){
					   fdsyqj.delete(fdsyqj.length()-1, fdsyqj.length());
					   product.setFdsyqj(fdsyqj.toString());
					}					
					String id = bean.saveProductFund(product);
					int productCount = bean.getProductCount(new String[] { "0", "1","2" });
					request.setAttribute("productCount", productCount);
					if (!QwyUtil.isNullAndEmpty(id)) {
						platformBean.updateTotalMoney(product.getFinancingAmount());
						System.out.println("发布成功!");
						request.setAttribute("isOk", "ok");
						return "release";
					}	
				}else{
					if(bean.updateProductFund(product)){
						bean.modifyContractByProductId(product.getId(), product.getTitle());
						System.out.println("修改成功!");
						request.setAttribute("isOk", "ok");
						return "modifyFundProduct";
					}else{
						System.out.println("修改失败!");
						request.setAttribute("isOk", "no");
						return "modifyFundProduct";
					}
				}

				System.out.println("发布失败!");
			}
		} catch (Exception e) {
			log.error("操作异常: ", e);
			log.error("ReleaseFundProduct.releaseFundProduct",e);
		}
		request.setAttribute("isOk", "no");
		request.setAttribute("product", product);
		return "release";
	}
	/**
	 * 进入修改产品页面	
	 * @return
	 */
	public String toModifyFundProduct(){
		String json="";
		try {
			UsersAdmin users = (UsersAdmin)getRequest().getSession().getAttribute("usersAdmin");
			if(QwyUtil.isNullAndEmpty(users)){
				json = QwyUtil.getJSONString("err", "管理员未登录");
				QwyUtil.printJSON(getResponse(), json);
				//管理员没有登录;
				return null;
			}
			if(!QwyUtil.isNullAndEmpty(productId)){
				product=bean.findProductById(productId);
				request.setAttribute("product", product);
				return "modifyFundProduct";
			}
		} catch (Exception e) {
			// TODO: handle exception
			log.error("操作异常: ",e);
			log.error("ReleaseFund.toModifyFundProduct",e);
		}
		return "modifyFundProduct";
	}
	
	/**
	 * 预览修改的基金产品
	 * @return
	 */
	public String showModifyFundProduct(){
		try {
			if(!QwyUtil.isNullAndEmpty(product)){
				Product oldProduct=bean.findProductById(product.getId());
	            oldProduct.setTitle(product.getTitle());
	            oldProduct.setHdby(product.getHdby());
	            oldProduct.setFdsysmUrlWeb(product.getFdsysmUrlWeb());
	            oldProduct.setZrgzUrlWeb(product.getZrgzUrlWeb());
	            oldProduct.setJiangLiEarnings(product.getJiangLiEarnings());
	            oldProduct.setHkly(product.getHkly());
	            oldProduct.setZjbz(product.getZjbz());
	            oldProduct.setCplxjs(product.getCplxjs());
				getRequest().setAttribute("product",oldProduct );
				return "preview";
			}
		} catch (Exception e) {
			// TODO: handle exception
			log.error("操作异常: ",e);
			log.error("ReleaseFund.showModifyFundProduct",e);
		}
		return "preview";
	}
	
	
	public Product getProduct() {
		return product;
	}

	public String getFileFileName() {
		return fileFileName;
	}

	public void setFileFileName(String fileFileName) {
		this.fileFileName = fileFileName;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public String getFinishTime() {
		return finishTime;
	}

	public void setFinishTime(String finishTime) {
		this.finishTime = finishTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public String getFileContentType() {
		return fileContentType;
	}

	public void setFileContentType(String fileContentType) {
		this.fileContentType = fileContentType;
	}

	public String getRemoveId() {
		return removeId;
	}

	public void setRemoveId(String removeId) {
		this.removeId = removeId;
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


	public static void main(String[] args) {
		String folder =  "Y:\\/web_img/law/";
		File imgFile = new File(folder);
		imgFile.setExecutable(true);
		imgFile.setWritable(true);
		boolean isMk = imgFile.mkdirs();
		System.out.println(isMk);
	}
	public static synchronized String getSuperPath() {
	    File file = new File("");
	    String path = new File(file.getAbsolutePath()).getParent();
	    path = path.replace('\\', '/');
	    path += "/你的webapps的名字/"; 
	    return path;
	  }


	public String getInsertTime() {
		return insertTime;
	}


	public void setInsertTime(String insertTime) {
		this.insertTime = insertTime;
	}


	public String getUsername() {
		return username;
	}


	public void setUsername(String username) {
		this.username = username;
	}


	public String getProductStatus() {
		return productStatus;
	}


	public void setProductStatus(String productStatus) {
		this.productStatus = productStatus;
	}


	public String getProductId() {
		return productId;
	}


	public void setProductId(String productId) {
		this.productId = productId;
	}


	public String getTitle() {
		return title;
	}


	public void setTitle(String title) {
		this.title = title;
	}


	public String getAnnualEarnings() {
		return annualEarnings;
	}


	public void setAnnualEarnings(String annualEarnings) {
		this.annualEarnings = annualEarnings;
	}


	public String getFinancingAmount() {
		return financingAmount;
	}


	public void setFinancingAmount(String financingAmount) {
		this.financingAmount = financingAmount;
	}



	public String getFdsy1() {
		return fdsy1;
	}



	public void setFdsy1(String fdsy1) {
		this.fdsy1 = fdsy1;
	}



	public String getFdsy2() {
		return fdsy2;
	}



	public void setFdsy2(String fdsy2) {
		this.fdsy2 = fdsy2;
	}



	public String getFdsy3() {
		return fdsy3;
	}



	public void setFdsy3(String fdsy3) {
		this.fdsy3 = fdsy3;
	}



	public String getFdsy4() {
		return fdsy4;
	}



	public void setFdsy4(String fdsy4) {
		this.fdsy4 = fdsy4;
	}



	public String getFdsy5() {
		return fdsy5;
	}



	public void setFdsy5(String fdsy5) {
		this.fdsy5 = fdsy5;
	}



	public String getFdsy6() {
		return fdsy6;
	}



	public void setFdsy6(String fdsy6) {
		this.fdsy6 = fdsy6;
	}



	public String getFdsy7() {
		return fdsy7;
	}



	public void setFdsy7(String fdsy7) {
		this.fdsy7 = fdsy7;
	}



	public String getFdsy8() {
		return fdsy8;
	}



	public void setFdsy8(String fdsy8) {
		this.fdsy8 = fdsy8;
	}


	
	
}
