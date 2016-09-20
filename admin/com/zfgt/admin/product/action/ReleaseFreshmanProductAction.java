package com.zfgt.admin.product.action;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;

import org.apache.struts2.config.Namespace;
import org.apache.struts2.config.ParentPackage;
import org.apache.struts2.config.Result;
import org.apache.struts2.config.Results;

import com.zfgt.admin.product.bean.ReleaseFreshmanProductBean;
import com.zfgt.admin.product.bean.ReleaseProductBean;
import com.zfgt.common.action.BaseAction;
import com.zfgt.common.bean.PlatformBean;
import com.zfgt.common.util.DESEncrypt;
import com.zfgt.common.util.QwyUtil;
import com.zfgt.orm.Product;
import com.zfgt.orm.UsersAdmin;
/**后台发布产品Action层<br>
 * 管理员进行产品的发布,对页面的值进行判断;
 * @author qwy
 *
 * 2015-4-16下午11:52:17
 */
@SuppressWarnings("serial")
@ParentPackage("struts-default")
@Namespace("/Product/Admin")
//发布产品页面
@Results({ 
	@Result(name = "sendFreshman", value = "/Product/Admin/productManager/releaseFreshmanProduct.jsp"),
	@Result(name = "err", value = "/Product/Admin/err.jsp")
})
public class ReleaseFreshmanProductAction extends BaseAction {
	
	/**
	 * 产品
	 */
	private Product product;
	@Resource
	private ReleaseFreshmanProductBean bean;
	@Resource
	private ReleaseProductBean releaseProductBean;
	@Resource
	private PlatformBean platformBean;
	private String endTime;
	
	private String finishTime;

	 private List<File> file;
    private List<String> fileContentType;
    private List<String> fileFileName;
	
	

	

	/**发布新手产品
	 * @return
	 */
	public String releaseProduct(){
		try {
			request = getRequest();
			if(!QwyUtil.isNullAndEmpty(product)){
				Long newFinancialAmount = QwyUtil.calcNumber(product.getFinancingAmount(), 100, "*").longValue();
				Long atleastMoney = QwyUtil.calcNumber(product.getAtleastMoney(), 100, "*").longValue();
				product.setFinancingAmount(newFinancialAmount);
				product.setAtleastMoney(atleastMoney);
				product.setEndTime(QwyUtil.fmyyyyMMdd.parse(endTime));
				product.setFinishTime((QwyUtil.fmyyyyMMdd.parse(finishTime)));
				if(QwyUtil.isNullAndEmpty(product.getRealName())){
					System.out.println("姓名不正确!");
					request.setAttribute("isOk", "no");
					return "release";
				}
				
				if(QwyUtil.isNullAndEmpty(product.getPhone())){
					System.out.println("联系号码不正确!");
					request.setAttribute("isOk", "no");
					return "release";
				}
				product.setPhone(DESEncrypt.jiaMiUsername(product.getPhone()));
				if(QwyUtil.isNullAndEmpty(product.getIdcard())){
					System.out.println("身份证号不正确!");
					request.setAttribute("isOk", "no");
					return "release";
				}
				product.setIdcard(DESEncrypt.jiaMiIdCard(product.getIdcard()));
				if(QwyUtil.isNullAndEmpty(product.getAddress())){
					System.out.println("联系地址不正确!");
					request.setAttribute("isOk", "no");
					return "release";
				}
				String id = bean.saveProduct(product);
				if(!QwyUtil.isNullAndEmpty(id)){
					platformBean.updateTotalMoney(product.getFinancingAmount());
					System.out.println("发布成功!");
					request.setAttribute("isOk", "ok");
					return "sendFreshman";
				}
				System.out.println("发布失败!");
			}
		} catch (Exception e) {
			log.error("操作异常: ",e);
		}
		request.setAttribute("isOk", "no");
		request.setAttribute("product", product);
		return "sendFreshman";
	}
	
	/**加载发布新手产品的页面；
	 * @return
	 */
	public String sendProduct(){
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
		if(isExistsQX("发布新手产品", users.getId())){
			getRequest().setAttribute("err", "您没有操作该功能的权限!");
			return "err";
		}
		}
		int productCount = releaseProductBean.getProductCount(new String[]{"1"});
		getRequest().setAttribute("productCount", productCount);
		return "sendFreshman";
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	

	/**上传图片
	 * @return
	 */
	public String uploadImage(){
		System.out.println("上传图片");
		System.out.println(file);
		request = getRequest();
		return null;
	}
	



	public List<File> getFile() {
		return file;
	}




	public void setFile(List<File> file) {
		this.file = file;
	}




	public List<String> getFileContentType() {
		return fileContentType;
	}




	public void setFileContentType(List<String> fileContentType) {
		this.fileContentType = fileContentType;
	}




	public List<String> getFileFileName() {
		return fileFileName;
	}




	public void setFileFileName(List<String> fileFileName) {
		this.fileFileName = fileFileName;
	}




	public Product getProduct() {
		return product;
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


public String test(){
	System.out.println(product.getDescription());
	return "";
}

	
}
