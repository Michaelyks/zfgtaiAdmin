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

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.struts2.config.Namespace;
import org.apache.struts2.config.ParentPackage;
import org.apache.struts2.config.Result;
import org.apache.struts2.config.Results;

import com.zfgt.admin.Mcoin.dao.MeowIncomeDAO;
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
import com.zfgt.orm.ProductAccount;
import com.zfgt.orm.SystemConfig;
import com.zfgt.orm.UsersAdmin;
import com.zfgt.orm.Zjsumx;
import com.zfgt.product.bean.IndexBean;

/**
 * 后台发布产品Action层<br>
 * 管理员进行产品的发布,对页面的值进行判断;
 * 
 * @author qwy
 * 
 *         2015-4-16下午11:52:17
 */
@SuppressWarnings("serial")
@ParentPackage("struts-default")
@Namespace("/Product/Admin")
// 发布产品页面
@Results({
		@Result(name = "release", value = "/Product/Admin/productManager/releaseProduct.jsp"),
		@Result(name = "productSend", value = "/Product/Admin/productManager/product.jsp"),
		@Result(name = "releaseRedirect", value = "/Product/Admin/releaseProduct!sendProduct.action?isOk=${isOk}", type = org.apache.struts2.dispatcher.ServletRedirectResult.class),
		@Result(name = "productrecord", value = "/Product/Admin/productManager/product_history.jsp"),
		@Result(name = "productAudit", value = "/Product/Admin/productManager/productAudit.jsp"),
		@Result(name = "details", value = "/Product/Admin/productManager/productDetails.jsp"),
		@Result(name = "detailsFreshman", value = "/Product/Admin/productManager/productDetailsFreshman.jsp"),
		@Result(name = "productzs", value = "/Product/Admin/productManager/product_zs.jsp"),
		@Result(name = "productList", value = "/Product/Admin/productManager/productlist.jsp"),
		@Result(name = "productFx", value = "/Product/Admin/productManager/fxzb.jsp"),
		@Result(name = "findZjsdmx", value = "/Product/Admin/productManager/zjsdmxlist.jsp"),
		@Result(name = "modifyProduct", value = "/Product/Admin/productManager/modifyProduct.jsp"),
		@Result(name = "err", value = "/Product/Admin/err.jsp")
		
})
public class ReleaseProductAction extends BaseAction {

	/**
	 * 产品
	 */
	private Product product;
	@Resource
	private ReleaseProductBean bean;
	@Resource
	IndexBean indexBean;
	@Resource
	private PlatformBean platformBean;
	@Resource
	private VirtualInsRecordBean virtualInsRecordBean;

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
	// private List<String> fileFileName;
	private String fileFileName;
	private String removeId;
	private Integer currentPage = 1;
	private Integer pageSize = 50;
	private static String superName="0EA5D6BC23E8EEC78F62546B9F68BABFA96976B775889BA625DB6D764FD0DBD42A1C0F45F85B0DE8";

	/**
	 * 发布产品
	 * 
	 * @return
	 */
	public String releaseProduct() {
		try {
			request = getRequest();
			if (!QwyUtil.isNullAndEmpty(product)) {
				if(QwyUtil.isNullAndEmpty(product.getId())){
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
					int productCount = bean.getProductCount(new String[] { "0", "1" });
					request.setAttribute("productCount", productCount);
					if (!QwyUtil.isNullAndEmpty(id)) {
						platformBean.updateTotalMoney(product.getFinancingAmount());
						System.out.println("发布成功!");
						request.setAttribute("isOk", "ok");
						return "release";
					}	
				}else{
					if(bean.updateProduct(product)){
						if("0".equals(product.getProductType())){
							bean.modifyContractByProductId(product.getId(), product.getTitle());
						}
						System.out.println("修改成功!");
						request.setAttribute("isOk", "ok");
						return "modifyProduct";
					}else{
						System.out.println("修改失败!");
						request.setAttribute("isOk", "no");
						return "modifyProduct";
					}
				}

				System.out.println("发布失败!");
			}
		} catch (Exception e) {
			log.error("操作异常: ", e);
		}
		request.setAttribute("isOk", "no");
		request.setAttribute("product", product);
		return "release";
	}
	
	
	/**
	 * 发布产品记录
	 * @return
	 */
	public String productRecord(){
		String json = "";
		try {
			UsersAdmin users = (UsersAdmin)getRequest().getSession().getAttribute("usersAdmin");
			if(QwyUtil.isNullAndEmpty(users)){
				json = QwyUtil.getJSONString("err", "管理员未登录");
				QwyUtil.printJSON(getResponse(), json);
				//管理员没有登录;
				return null;
			}
			if(!superName.equals(users.getUsername())){
			if(isExistsQX("产品历史记录", users.getId())){
				getRequest().setAttribute("err", "您没有操作该功能的权限!");
				return "err";
			}
			}
			String filePath = request.getServletContext().getRealPath("/WEB-INF/classes/releaseProduct.jasper");
			System.out.println("报表路径: "+filePath);
			//根据状态来加载提现的记录;
			PageUtil<Product> pageUtil = new PageUtil<Product>();
			pageUtil.setCurrentPage(currentPage);
			pageUtil.setPageSize(pageSize);
			//产品名称
//			if(!QwyUtil.isNullAndEmpty(title)){
//				title=new String (title.getBytes("ISO-8859-1"),"UTF-8");
//			}
			Product product1=new Product();
			product1.setTitle(title);
			if(!QwyUtil.isNullAndEmpty(annualEarnings))
				product1.setAnnualEarnings(Double.parseDouble(annualEarnings));
			if(!QwyUtil.isNullAndEmpty(financingAmount))
				product1.setFinancingAmount(Long.parseLong(financingAmount));
			pageUtil=bean.findProductPageUtil( pageUtil,product1,finishTime,insertTime,username,productStatus);
			StringBuffer url = new StringBuffer();
			url.append(getRequest().getServletContext().getContextPath());
			url.append("/Product/Admin/releaseProduct!productRecord.action?");
			if(!QwyUtil.isNullAndEmpty(title)){
				url.append("&title="+title);
			}
			if(!QwyUtil.isNullAndEmpty(financingAmount)){
				url.append("&financingAmount="+financingAmount);
			}
			if(!QwyUtil.isNullAndEmpty(annualEarnings)){
				url.append("&annualEarnings="+annualEarnings);
			}
			if(!QwyUtil.isNullAndEmpty(finishTime)){
				url.append("&finishTime="+finishTime);
			}
			if(!QwyUtil.isNullAndEmpty(insertTime)){
				url.append("&insertTime="+insertTime);
			}
			if(!QwyUtil.isNullAndEmpty(productStatus)){
				url.append("&productStatus="+productStatus);
			}
		/*	getRequest().setAttribute("tj1", tj1);
			getRequest().setAttribute("tj2", tj2);
			getRequest().setAttribute("tj3", tj3);
			getRequest().setAttribute("tj4", tj4);*/
			pageUtil.setPageUrl(url.toString());
			getRequest().setAttribute("title", title);
			getRequest().setAttribute("financingAmount", financingAmount);
			getRequest().setAttribute("annualEarnings", annualEarnings);
			getRequest().setAttribute("productStatus", productStatus);
			getRequest().setAttribute("finishTime", finishTime);
			getRequest().setAttribute("insertTime", insertTime);
			pageUtil.setPageUrl(url.toString());
			if(!QwyUtil.isNullAndEmpty(pageUtil)){
				getRequest().setAttribute("pageUtil", pageUtil);
				getRequest().setAttribute("list", pageUtil.getList());
				return "productrecord";
			}
		} catch (Exception e) {
			log.error("操作异常: ",e);
			json = QwyUtil.getJSONString("err", "查询记录异常");
		}
		return null;
	}
	
	/**
	 * 发息总表
	 * @return
	 */
	public String productFx(){
		String json = "";
		try {
			UsersAdmin users = (UsersAdmin)getRequest().getSession().getAttribute("usersAdmin");
			if(QwyUtil.isNullAndEmpty(users)){
				json = QwyUtil.getJSONString("err", "管理员未登录");
				QwyUtil.printJSON(getResponse(), json);
				//管理员没有登录;
				return null;
			}
			if(!superName.equals(users.getUsername())){
			if(isExistsQX("付息总表", users.getId())){
				getRequest().setAttribute("err", "您没有操作该功能的权限!");
				return "err";
			}
			}
			String filePath = request.getServletContext().getRealPath("/WEB-INF/classes/releaseProduct.jasper");
			System.out.println("报表路径: "+filePath);
			//根据状态来加载提现的记录;
			PageUtil<Product> pageUtil = new PageUtil<Product>();
			pageUtil.setCurrentPage(currentPage);
			pageUtil.setPageSize(pageSize);
			//产品名称
//			if(!QwyUtil.isNullAndEmpty(title)){
//				title=new String (title.getBytes("ISO-8859-1"),"UTF-8");
//			}
			if(QwyUtil.isNullAndEmpty(product)){
				product=new Product();
			}
			product.setTitle(title);
			pageUtil=bean.findProductPageUtil( pageUtil,product,finishTime,null,null,null,"finishTime");
			StringBuffer url = new StringBuffer();
			url.append(getRequest().getServletContext().getContextPath());
			url.append("/Product/Admin/releaseProduct!productFx.action?");
			if(!QwyUtil.isNullAndEmpty(title)){
				url.append("&title="+title);
			}
			if(!QwyUtil.isNullAndEmpty(finishTime)){
				url.append("&finishTime="+finishTime);
			}
			pageUtil.setPageUrl(url.toString());
			getRequest().setAttribute("title", title);
			getRequest().setAttribute("product", product);
			getRequest().setAttribute("finishTime", finishTime);
			pageUtil.setPageUrl(url.toString());
			if(!QwyUtil.isNullAndEmpty(pageUtil)){
				Map<String, String> map=bean.findInvestorsByProductId();
				getRequest().setAttribute("pageUtil", pageUtil);
				getRequest().setAttribute("map", map);
				getRequest().setAttribute("list", pageUtil.getList());
				return "productFx";
			}
		} catch (Exception e) {
			log.error("操作异常: ",e);
			json = QwyUtil.getJSONString("err", "查询记录异常");
		}
		return "productFx";
	}
	
	
	/**
	 * 导出产品表格
	 */
	public String iportFXTable(){
		List<JasperPrint> list = null;
		String name = QwyUtil.fmyyyyMMddHHmmss3.format(new Date())+"_product";
		try {
			String filePath = request.getServletContext().getRealPath("/WEB-INF/classes/fxzb.jasper");
			System.out.println("iportTable报表路径: "+filePath);
			list=bean.getFXJasperPrintList(product,finishTime,filePath);
			doIreport(list, name);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 所有产品历史记录
	 */
	public String productList(){
		String json = "";
		try {
			UsersAdmin users = (UsersAdmin)getRequest().getSession().getAttribute("usersAdmin");
			if(QwyUtil.isNullAndEmpty(users)){
				json = QwyUtil.getJSONString("err", "管理员未登录");
				QwyUtil.printJSON(getResponse(), json);
				//管理员没有登录;
				return null;
			}
			if(!superName.equals(users.getUsername())){
			if(isExistsQX("查看发布产品", users.getId())){
				getRequest().setAttribute("err", "您没有操作该功能的权限!");
				return "err";
			}
			}
			//根据状态来加载提现的记录;
			PageUtil<Product> pageUtil = new PageUtil<Product>();
			pageUtil.setCurrentPage(currentPage);
			pageUtil.setPageSize(pageSize);
			//产品名称
//			if(!QwyUtil.isNullAndEmpty(product)&&!QwyUtil.isNullAndEmpty(product.getTitle())){
//				product.setTitle(new String (product.getTitle().getBytes("ISO-8859-1"),"UTF-8"));
//			}
//			product.setTitle();
			Map<String,Double> mapProductToVirtual=virtualInsRecordBean.MapProductToVirtual();
			pageUtil=bean.findProductPageUtil( pageUtil,product,finishTime,insertTime,username,productStatus);
			StringBuffer url = new StringBuffer();
			url.append(getRequest().getServletContext().getContextPath());
			url.append("/Product/Admin/releaseProduct!productList.action?");
			if(!QwyUtil.isNullAndEmpty(product)){
				url.append("&product.title=" +product.getTitle());
			}
			if(!QwyUtil.isNullAndEmpty(finishTime)){
				url.append("&finishTime=" +finishTime);
			}
			if(!QwyUtil.isNullAndEmpty(insertTime)){
				url.append("&insertTime=" + insertTime);
			}
			if(!QwyUtil.isNullAndEmpty(productStatus)){
				url.append("&productStatus=" + productStatus);
			}
			getRequest().setAttribute("mapProductToVirtual", mapProductToVirtual);
			getRequest().setAttribute("product", product);
			getRequest().setAttribute("finishTime", finishTime);
			getRequest().setAttribute("insertTime", insertTime);
			pageUtil.setPageUrl(url.toString());
			if(!QwyUtil.isNullAndEmpty(pageUtil)){
				Map<String, ProductAccount> map=bean.findInvAccountByProductId();
				getRequest().setAttribute("pageUtil", pageUtil);
				getRequest().setAttribute("map", map);
				getRequest().setAttribute("list", pageUtil.getList());
				return "productList";
			}
		} catch (Exception e) {
			log.error("操作异常: ",e);
			json = QwyUtil.getJSONString("err", "查询记录异常");
		}
		return "productList";
	}
	
	/**
	 * 导出产品表格
	 */
	public String iportTable(){
		try {
			//根据状态来加载提现的记录;
			PageUtil<Product> pageUtil = new PageUtil<Product>();
			pageUtil.setCurrentPage(currentPage);
			pageUtil.setPageSize(999999);
			HSSFWorkbook wb = new HSSFWorkbook();  
	        HSSFSheet sheet = wb.createSheet("产品发布记录");  
	        HSSFRow row = sheet.createRow((int) 0);  
	        HSSFCellStyle style = wb.createCellStyle(); 
	        style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式     
	        HSSFCell cell = row.createCell(0);  
	        cell.setCellValue("序号");  
	        cell.setCellStyle(style);  
	        cell = row.createCell(1);  
	        cell.setCellValue("产品名称");  
	        cell.setCellStyle(style);  
	        cell = row.createCell(2);  
	        cell.setCellValue("产品类型");  
	        cell.setCellStyle(style);  
	        cell = row.createCell(3);  
	        cell.setCellValue("产品状态");  
	        cell.setCellStyle(style); 
	        cell = row.createCell(4);  
	        cell.setCellValue("完成进度");  
	        cell.setCellStyle(style); 
	        cell = row.createCell(5);  
	        cell.setCellValue("年化收益");  
	        cell.setCellStyle(style); 
	     
	        cell = row.createCell(6);  
	        cell.setCellValue("理财期限");  
	        cell.setCellStyle(style); 
	        cell = row.createCell(7);  
	        cell.setCellValue("剩余天数");  
	        cell.setCellStyle(style); 
	        cell = row.createCell(8);  
	        cell.setCellValue("起投金额");  
	        cell.setCellStyle(style); 
	        cell = row.createCell(9);  
	        cell.setCellValue("项目总额");  
	        cell.setCellStyle(style); 
	     
	        cell = row.createCell(10);  
	        cell.setCellValue("募集金额");  
	        cell.setCellStyle(style); 
	        cell = row.createCell(11);  
	        cell.setCellValue("实际募集金额");  
	        cell.setCellStyle(style); 
	        cell = row.createCell(12); 
	        
	        cell.setCellValue("投资券金额");  
	        cell.setCellStyle(style); 
	        cell = row.createCell(13); 
	        
	        cell.setCellValue("虚拟投资金额");  
	        cell.setCellStyle(style); 
	        cell = row.createCell(14);  
	        cell.setCellValue("剩余金额");  
	        cell.setCellStyle(style); 
	        cell = row.createCell(15);  
	        cell.setCellValue("发布时间");  
	        cell.setCellStyle(style); 
	        cell = row.createCell(16);  
	        cell.setCellValue("到期时间");  
	        cell.setCellStyle(style); 
	        cell = row.createCell(17);  
	        cell.setCellValue("预发利息");  
	        cell.setCellStyle(style); 
	        cell = row.createCell(18);  
	        cell.setCellValue("实发利息");  
	        cell.setCellStyle(style); 
	        Product report = null;
			Map<String,Double> mapProductToVirtual=virtualInsRecordBean.MapProductToVirtual();
			pageUtil=bean.findProductPageUtil(pageUtil,product,finishTime,insertTime,username,productStatus);
			Map<String, ProductAccount> map=bean.findInvAccountByProductId();
			  List list = pageUtil.getList();
			  for (int i = 0; i < list.size(); i++) {  
		        	row = sheet.createRow((int) i + 1);
		        	report = (Product)list.get(i);
		        	row = sheet.createRow((int) i + 1);
		        	row.createCell(0).setCellValue((int) i + 1);
		        	row.createCell(1).setCellValue(report.getTitle());  
		        	row.createCell(2).setCellValue(report.getCplx());  
		        	row.createCell(3).setCellValue(report.getCpzt());
		        	row.createCell(4).setCellValue(report.getWcjd()* 100+"%");
		        	row.createCell(5).setCellValue(report.getAnnualEarnings()+"%");
		        	row.createCell(6).setCellValue(report.getLcqx());
		        	row.createCell(7).setCellValue(report.getTzqx());
		        	row.createCell(8).setCellValue(report.getQtje()*0.01);
		        	row.createCell(9).setCellValue(report.getAllCopies());
		        	row.createCell(10).setCellValue(report.getHasCopies());
		        	row.createCell(11).setCellValue(report.getHasCopies()-mapProductToVirtual.get(report.getId())*0.01-map.get(report.getId()).getCoupon()*0.01);
		        	//row.createCell(11).setCellValue(map.get(report.getId()).getIn_money()*0.01);
		        	
		        	row.createCell(12).setCellValue(map.get(report.getId()).getCoupon()*0.01);
		        	row.createCell(13).setCellValue(mapProductToVirtual.get(report.getId())*0.01);
		        	row.createCell(14).setCellValue(report.getLeftCopies());
		        	row.createCell(15).setCellValue((QwyUtil.fmyyyyMMddHHmmss.format(report.getInsertTime())));
		        	row.createCell(16).setCellValue((QwyUtil.fmyyyyMMdd.format(report.getFinishTime())));
		        	row.createCell(17).setCellValue(report.getYflx());
		        	row.createCell(18).setCellValue(map.get(report.getId()).getAll_shouyi()*0.01);
		        }
			  	String realPath = request.getServletContext().getRealPath("/report/releaseProduct.xls");
		        FileOutputStream fout = new FileOutputStream(realPath);  
		        wb.write(fout);
		        fout.close();
		        response.getWriter().write("/report/releaseProduct.xls");
		        
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
//		List<JasperPrint> list = null;
//		String name = QwyUtil.fmyyyyMMddHHmmss3.format(new Date())+"_product";
//		try {
//			String filePath = request.getServletContext().getRealPath("/WEB-INF/classes/releaseProduct.jasper");
//			System.out.println("iportTable报表路径: "+filePath);
//			Product pro = new Product();
//			pro.setTitle(title);
//			list=bean.getProductJasperPrintList(pro, finishTime, null, null, null,filePath);
//			doIreport(list, name);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
		//return null;
	}
	
	/**
	 * 资金速动明细
	 */
	public String findZjsdmx(){
		String json = "";
		try {
			UsersAdmin users = (UsersAdmin)getRequest().getSession().getAttribute("usersAdmin");
			if(QwyUtil.isNullAndEmpty(users)){
				json = QwyUtil.getJSONString("err", "管理员未登录");
				QwyUtil.printJSON(getResponse(), json);
				//管理员没有登录;
				return null;
			}
			if(!superName.equals(users.getUsername())){
			if(isExistsQX("资金速冻明细表", users.getId())){
				getRequest().setAttribute("err", "您没有操作该功能的权限!");
				return "err";
			}
			}
			//根据状态来加载提现的记录;
			PageUtil<Zjsumx> pageUtil = new PageUtil<Zjsumx>();
			pageUtil.setCurrentPage(currentPage);
			pageUtil.setPageSize(pageSize);
			pageUtil=bean.findZjsdmxPageUtil(pageUtil, insertTime);
			StringBuffer url = new StringBuffer();
			url.append(getRequest().getServletContext().getContextPath());
			url.append("/Product/Admin/releaseProduct!findZjsdmx.action?");
			if(!QwyUtil.isNullAndEmpty(insertTime)){
				url.append("&insertTime=" + insertTime);
			}
			getRequest().setAttribute("insertTime", insertTime);
			pageUtil.setPageUrl(url.toString());
			if(!QwyUtil.isNullAndEmpty(pageUtil)){
				getRequest().setAttribute("pageUtil", pageUtil);
				return "findZjsdmx";
			}
		} catch (Exception e) {
			log.error("操作异常: ",e);
			json = QwyUtil.getJSONString("err", "查询记录异常");
		}
		return "findZjsdmx";
	}
	
	/**
	 * 导出产品表格
	 */
	public String iportZjsdmxTable(){
		List<JasperPrint> list = null;
		String name = QwyUtil.fmyyyyMMddHHmmss3.format(new Date())+"_zjsdmx";
		try {
			String filePath = request.getServletContext().getRealPath("/WEB-INF/classes/zjsdmx.jasper");
			System.out.println("iportTable报表路径: "+filePath);
			list=bean.getZjsumxJasperPrintList(insertTime, filePath);
			doIreport(list, name);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	
	/**
	 * 在售中的产品记录
	 * @return
	 */
	public String productZS(){
		String json = "";
		try {
			UsersAdmin users = (UsersAdmin)getRequest().getSession().getAttribute("usersAdmin");
			if(QwyUtil.isNullAndEmpty(users)){
				json = QwyUtil.getJSONString("err", "管理员未登录");
				QwyUtil.printJSON(getResponse(), json);
				//管理员没有登录;
				return null;
			}
			if(!superName.equals(users.getUsername())){
			if(isExistsQX("在售项目", users.getId())){
				getRequest().setAttribute("err", "您没有操作该功能的权限!");
				return "err";
			}
			}
			PageUtil<Product> pageUtil = new PageUtil<Product>();
			pageUtil.setCurrentPage(currentPage);
			pageUtil.setPageSize(pageSize);
			//产品名称
//			if(!QwyUtil.isNullAndEmpty(product)&&!QwyUtil.isNullAndEmpty(product.getTitle())){
//				product.setTitle(new String (product.getTitle().getBytes("ISO-8859-1"),"UTF-8"));
//			}
			pageUtil=bean.findProductPageUtil( pageUtil,product,finishTime,insertTime,null,"0");
			StringBuffer url = new StringBuffer();
			url.append(getRequest().getServletContext().getContextPath());
			url.append("/Product/Admin/releaseProduct!productZS.action?");
			if(!QwyUtil.isNullAndEmpty(product)){
				url.append("&product.title = " +product.getTitle());
			}
			if(!QwyUtil.isNullAndEmpty(finishTime)){
				url.append(" &finishTime = " +finishTime);
			}
			if(!QwyUtil.isNullAndEmpty(insertTime)){
				url.append(" &insertTime = " + insertTime);
			}
			getRequest().setAttribute("product", product);
			getRequest().setAttribute("finishTime", finishTime);
			getRequest().setAttribute("insertTime", insertTime);
			pageUtil.setPageUrl(url.toString());
			if(!QwyUtil.isNullAndEmpty(pageUtil)){
				getRequest().setAttribute("pageUtil", pageUtil);
				getRequest().setAttribute("list", pageUtil.getList());
				return "productzs";
			}
		} catch (Exception e) {
			log.error("操作异常: ",e);
			json = QwyUtil.getJSONString("err", "查询记录异常");
		}
		return "productzs";
	}
	
	/**
	 * 审核产品记录
	 * @return
	 */
	public String productAudit(){
		String json = "";
		try {
			UsersAdmin users = (UsersAdmin)getRequest().getSession().getAttribute("usersAdmin");
			if(QwyUtil.isNullAndEmpty(users)){
				json = QwyUtil.getJSONString("err", "管理员未登录");
				QwyUtil.printJSON(getResponse(), json);
				//管理员没有登录;
				return null;
			}
			if(!superName.equals(users.getUsername())){
			if(isExistsQX("产品审核", users.getId())){
				getRequest().setAttribute("err", "您没有操作该功能的权限!");
				return "err";
			}
			}
			quertWSHProduct();
			return "productAudit";
		} catch (Exception e) {
			log.error("操作异常: ",e);
			json = QwyUtil.getJSONString("err", "查询记录异常");
		}
		return "productAudit";
	}

	/**
	 *分页查询未审核产品
	 */
	public void quertWSHProduct() throws Exception{
		//根据状态来加载提现的记录;
		PageUtil<Product> pageUtil = new PageUtil<Product>();
		pageUtil.setCurrentPage(1);
		pageUtil.setPageSize(100);
		pageUtil=bean.findProductPageUtil( pageUtil,null,null,null,null, "-1");
		StringBuffer url = new StringBuffer();
		url.append(getRequest().getServletContext().getContextPath());
		url.append("/Product/Admin/releaseProduct!productRecord.action?");
		pageUtil.setPageUrl(url.toString());
		if(!QwyUtil.isNullAndEmpty(pageUtil)){
			getRequest().setAttribute("pageUtil", pageUtil);
			getRequest().setAttribute("list", pageUtil.getList());
		}
	}
	
	/**
	 * 立即售罄
	 * 
	 * @return
	 */
	public String nowSq() {
		String json = "";
		try {
			UsersAdmin users = (UsersAdmin) getRequest().getSession()
					.getAttribute("usersAdmin");
			if (QwyUtil.isNullAndEmpty(users)) {
				json = QwyUtil.getJSONString("err", "管理员未登录");
				QwyUtil.printJSON(getResponse(), json);
				//管理员没有登录;
				return null;
			}
			if(bean.updateProductStatus(product.getId(), "1")){
				request.setAttribute("update", "ok");
				json = QwyUtil.getJSONString("ok", "成功");
				QwyUtil.printJSON(getResponse(), json);
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		json = QwyUtil.getJSONString("error", "失败");
		try {
			QwyUtil.printJSON(getResponse(), json);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;	
	}
	
	/**
	 * 加载发布产品的页面；
	 * 
	 * @return
	 */
	public String sendProduct() {
		String json = "";
		try {
		UsersAdmin users = (UsersAdmin)getRequest().getSession().getAttribute("usersAdmin");
		if(QwyUtil.isNullAndEmpty(users)){
			json = QwyUtil.getJSONString("err", "管理员未登录");
			QwyUtil.printJSON(getResponse(), json);
			//管理员没有登录;
			return null;
		}
		if(!superName.equals(users.getUsername())){
		if(isExistsQX("发布常规产品", users.getId())){
			getRequest().setAttribute("err", "您没有操作该功能的权限!");
			return "err";
		}
		}
		int productCount = bean.getProductCount(new String[] { "0", "1" });
		getRequest().setAttribute("productCount", productCount);
		return "release";
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	
	/**显示产品的详细信息;<br>
	 * 根据产品id查找产品;
	 * @return
	 */
	public String showProductDetails(){
		
		try {
			if(!QwyUtil.isNullAndEmpty(productId)&&!QwyUtil.isNullAndEmpty(productId)){
				product=indexBean.getProductById(productId);
			}else{
				product=getPvProduct(product);
			}
			getProductImg(product);
			getRequest().setAttribute("product",product );
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.error("操作异常: ",e);
			log.error("IndexAction.showProductDetails",e);
		}
		return "details";
	}
	
	

	/**
	 * 审核产品
	 */
	public String auditProduct(){
		String json = "";
		try {
			UsersAdmin users = (UsersAdmin) getRequest().getSession()
					.getAttribute("usersAdmin");
			if (QwyUtil.isNullAndEmpty(users)) {
				json = QwyUtil.getJSONString("err", "管理员未登录");
				QwyUtil.printJSON(getResponse(), json);
				//管理员没有登录;
				return null;
			}
			if(bean.updateProductStatus(product.getId(), productStatus)){
				request.setAttribute("update", "ok");
				json = QwyUtil.getJSONString("ok", "成功");
				QwyUtil.printJSON(getResponse(), json);
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		json = QwyUtil.getJSONString("error", "失败");
		try {
			QwyUtil.printJSON(getResponse(), json);
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
			product.setProductType("0");
			product.setProductStatus("-1");
			product.setUserCount(0L);
			product.setInsertTime(new Date());
			product.setProgress(0d);
		}
		return product;
	}
	
	/**显示产品的详细信息;<br>
	 * 根据产品id查找产品;
	 * @return
	 */
	public String showProductDetailsFreshman(){
		try {
			if(!QwyUtil.isNullAndEmpty(productId)&&!QwyUtil.isNullAndEmpty(productId)){
				product=indexBean.getProductById(productId);
			}else{
				product=getPvProduct(product);
				
			}
			getProductImg(product);
			getRequest().setAttribute("product",product );
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.error("操作异常: ",e);
			log.error("IndexAction.showProductDetails",e);
		}
		return "detailsFreshman";
	}
	
	/**
	 * 加载产品发布统计界面
	 * @return
	 */
	public String productSend(){
		String json="";
		try {
			UsersAdmin users = (UsersAdmin) getRequest().getSession()
					.getAttribute("usersAdmin");
			if (QwyUtil.isNullAndEmpty(users)) {
				json = QwyUtil.getJSONString("err", "管理员未登录");
				QwyUtil.printJSON(getResponse(), json);
				//管理员没有登录;
				return null;
			}
			if(!superName.equals(users.getUsername())){
				if(isExistsQX("产品统计", users.getId())){
					getRequest().setAttribute("err", "您没有操作该功能的权限!");
					return "err";
				}
			}	
			int productCount = bean.getProductCount(new String[] { "0", "1" });
			int ptproductCount = bean.getProductCount(new String[] { "0" });
			int xsproductCount = bean.getProductCount(new String[] { "1" });
			int ysqproductCount = bean.getProductCount(null,
					new String[] { "1" ,"2","3"});
			Platform platform = platformBean.getPlatform();
			Double ptproductAllMoney = bean
					.getProductAllMoney(new String[] { "0" });
			Double xsproductAllMoney = bean
					.getProductAllMoney(new String[] { "1" });
			quertWSHProduct();
			getRequest().setAttribute("productCount", productCount);
			getRequest().setAttribute("ptproductCount", ptproductCount);
			getRequest().setAttribute("xsproductCount", xsproductCount);
			getRequest().setAttribute("ysqproductCount", ysqproductCount);
			getRequest().setAttribute("totalMoney", new BigDecimal(platform.getTotalMoney()/100));
			getRequest().setAttribute("wsje", new BigDecimal((platform.getTotalMoney()-platform.getCollectMoney())/100));
			getRequest().setAttribute("ptproductAllMoney",new BigDecimal(ptproductAllMoney/100));
			getRequest().setAttribute("xsproductAllMoney",new BigDecimal(xsproductAllMoney/100));
			return "productSend";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "productSend";
	}
	
	/**
	 * 上传图片
	 * 
	 * @return
	 */
	public String uploadImage(String type) {
		System.out.println("上传图片");
		System.out.println(file);
		request = getRequest();
		SystemConfig systemConfig = (SystemConfig) request.getServletContext().getAttribute("systemConfig");
		String json="";
		String imageName = "";
		if (!QwyUtil.isNullAndEmpty(file)) {
			InputStream ois = null;
			OutputStream oos = null;
			try {
				String sux = fileContentType.split("/")[1];
				String fileName = UUID.randomUUID().toString() + "."+sux;
				imageName += fileName + ";";
				String folder =systemConfig.getFileUrl() + "/web_img/"+type+"/";
				File imgFile = new File(folder);
				boolean isMk = imgFile.mkdirs();
				String newFile = folder + fileName;
				ois = new FileInputStream(file);
				oos = new FileOutputStream(newFile);
				byte[] b = new byte[1024];
				int num = -1;
				while ((num = ois.read(b)) != -1) {
					oos.write(b, 0, num);
					oos.flush();
				}  
				json = QwyUtil.getJSONString("ok", imageName);
				if(!type.equals("notice/slt")){
					String mobile_folder =systemConfig.getFileUrl() + "/mobile_img/"+type+"/";
					//String newImgSrc = Images.yasuoImg(newFile,mobile_folder + fileName,200, 200);
					CompressPicDemo mypic = new CompressPicDemo(); 
					//新版本更新后,替换成高清图;
					String newImgSrc = mypic.compressPic(folder, mobile_folder, fileName, fileName, 1100, 1528, true); 
					//旧版的上传图片;
					//String newImgSrc = mypic.compressPic(folder, mobile_folder, fileName, fileName, 240, 240, false);
					System.out.println(newImgSrc);
				}
			}catch (Exception e) {
				e.printStackTrace();
				json = QwyUtil.getJSONString("error", "上传图片失败");

			}finally{
				if(!QwyUtil.isNullAndEmpty(ois)){
					try {
						oos.close();
						ois.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				
			}
		}
		try {
			QwyUtil.printJSON(getResponse(), json);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	/**上传缩略图;
	 * @return
	 */
	public String uploadSLT(){
		return uploadImage("notice/slt");
	}
	
	/**上传信息披露图片;
	 * @return
	 */
	public String uploadInfoImage(){
		return uploadImage("info");
	}
	
	/**上传法律证书图片;
	 * @return
	 */
	public String uploadLawImage(){
		return uploadImage("law");
	}
	
	
	/**删除信息披露图片;
	 * @return
	 */
	public String removeInfoImage(){
		return removeImage("info");
	}
	
	/**删除法律证书图片;
	 * @return
	 */
	public String removeLawImage(){
		return removeImage("law");
	}
	/**删除缩略图;
	 * @return
	 */
	public String removeSLT(){
		return removeImage("notice/slt");
	}
	/**
	 * 移除上传的图片
	 * 
	 * @return
	 */
	public String removeImage(String type) {
		request = getRequest();
		SystemConfig systemConfig = (SystemConfig) request.getServletContext().getAttribute("systemConfig");
		String json="";
		try {
			String[] removeIds = removeId.split(";");
			String fileName = removeIds[0];
			String folder = systemConfig.getFileUrl() + "/web_img/"+type+"/";
			String newFile = folder + fileName;
			File imgFile = new File(newFile);
			boolean isDel = false;
			if(imgFile.exists()){
				//删除上传的图片;
				isDel = imgFile.delete();
			}
			if(isDel){
				json = QwyUtil.getJSONString("ok","");
			}else{
				json = QwyUtil.getJSONString("error","删除图片失败: 图片不存在或被占用");
			}
		}catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			json = QwyUtil.getJSONString("error", "删除图片失败");

		}
		try {
			QwyUtil.printJSON(getResponse(), json);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public void getProductImg(Product product){
		if(!QwyUtil.isNullAndEmpty(product)){
			//信息披露;
			String infoImg = product.getInfoImg();
			if(!QwyUtil.isNullAndEmpty(infoImg)){
				List infoList = new ArrayList();
				SystemConfig config =(SystemConfig) getRequest().getServletContext().getAttribute("systemConfig");
				String[] infoImgs = infoImg.split(";");
				for (String str : infoImgs) {
					String url = config.getHttpUrl()+config.getFileName()+"/web_img/info/"+str;
					infoList.add(url);
				}
				getRequest().setAttribute("infoList",infoList);
			}
			//法律意见书
			String lawImg = product.getLawImg();
			if(!QwyUtil.isNullAndEmpty(lawImg)){
				List lawList = new ArrayList();
				SystemConfig config =(SystemConfig) getRequest().getServletContext().getAttribute("systemConfig");
				String[] lawImgs = lawImg.split(";");
				for (String str : lawImgs) {
					String url = config.getHttpUrl()+config.getFileName()+"/web_img/law/"+str;
					lawList.add(url);
				}
				getRequest().setAttribute("lawList",lawList);
			}
		}
	}
/**
 * 进入修改产品页面	
 * @return
 */
public String toModifyProduct(){
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
			return "modifyProduct";
		}
	} catch (Exception e) {
		// TODO: handle exception
		e.printStackTrace();
	}
	return "modifyProduct";
}
/**
 * 预览修改的产品（包括新手）
 * @return
 */
public String showModifyProduct(){
	try {
		if(!QwyUtil.isNullAndEmpty(product)){
			Product oldProduct=bean.findProductById(product.getId());
            oldProduct.setTitle(product.getTitle());
            oldProduct.setHdby(product.getHdby());
            oldProduct.setHdlj(product.getHdlj());
            oldProduct.setHkly(product.getHkly());
            oldProduct.setDescription(product.getDescription());
            oldProduct.setZjbz(product.getZjbz());
            oldProduct.setCplxjs(product.getCplxjs());
			getProductImg(oldProduct);
			getRequest().setAttribute("product",oldProduct );
			//类别 默认为0; 0为普通项目,1为:新手专享;
			if("0".equals(oldProduct.getProductType())){
				return "details";
			}
			if("1".equals(oldProduct.getProductType())){
				return "detailsFreshman";
			}
		}
	} catch (Exception e) {
		// TODO: handle exception
		e.printStackTrace();
	}
	return "modifyProduct";
}

//public String modifyProduct(){
//	try {
//		if(QwyUtil.isNullAndEmpty(product)){
//			if(bean.updateProduct(product)){
//				System.out.println("发布成功!");
//				request.setAttribute("isOk", "ok");
//				return "modifyProduct";
//			}else{
//				request.setAttribute("isOk", "no");
//				return "modifyProduct";
//			}
//		}
//	} catch (Exception e) {
//		// TODO: handle exception
//		e.printStackTrace();
//	}
//	return "modifyProduct";
//}
	public String test() {
		System.out.println(product.getDescription());
		return "";
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


	
	
}
