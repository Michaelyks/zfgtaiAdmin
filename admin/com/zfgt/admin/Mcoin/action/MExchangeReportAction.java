package com.zfgt.admin.Mcoin.action;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;

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

import com.zfgt.admin.Mcoin.bean.MExchangeReportBean;
import com.zfgt.admin.Mcoin.dao.MExchangeReport;
import com.zfgt.common.action.BaseAction;
import com.zfgt.common.util.DESEncrypt;
import com.zfgt.common.util.PageUtil;
import com.zfgt.common.util.QwyUtil;
import com.zfgt.orm.MProduct;
import com.zfgt.orm.UsersAdmin;
import com.zfgt.orm.UsersLogin;

@SuppressWarnings("serial")
@ParentPackage("struts-default")
@Namespace("/Product/Admin")
@Results({
	@Result(name = "exchangeReport", value = "/Product/Admin/mcoin/exchangeReport.jsp"),
	@Result(name = "mProduct", value = "/Product/Admin/mcoin/mProduct.jsp"),
	@Result(name = "login", value = "/Product/loginBackground.jsp" ,type=org.apache.struts2.dispatcher.ServletRedirectResult.class)
	
})
public class MExchangeReportAction  extends BaseAction{
	@Resource
	private MExchangeReportBean bean;
	

	private Integer currentPage = 1;
	private Integer pageSize = 50;
	private String username;
	private String insertTime;
	private String id;
	private String mCoinPayId;
	/**
	 * 加载瞄产品报表
	 * @return
	 */
	public String loadProduct(){
		try {
			request = getRequest();
			UsersAdmin users = (UsersAdmin)getRequest().getSession().getAttribute("usersAdmin");
			if(QwyUtil.isNullAndEmpty(users)){
				return "login";
			}
		 PageUtil<MProduct> pageUtil=new PageUtil<MProduct>();
		 pageUtil.setCurrentPage(currentPage);
	     pageUtil.setPageSize(pageSize);
	     
	     StringBuffer url = new StringBuffer();
			url.append(getRequest().getServletContext().getContextPath());
			url.append("/Product/Admin/mExchangeReport!loadProduct.action?");
			if(!QwyUtil.isNullAndEmpty(username)){
				url.append("username=");
				url.append(username);
			}
			if(!QwyUtil.isNullAndEmpty(insertTime)){
				url.append("&insertTime=");
				url.append(insertTime);
			}
			pageUtil.setPageUrl(url.toString());
			
	     bean.loadMProduct(pageUtil,insertTime);
		if(!QwyUtil.isNullAndEmpty(pageUtil)){
				request.setAttribute("list", pageUtil.getList());
				getRequest().setAttribute("insertTime", insertTime);
			}
		request.setAttribute("pageUtil", pageUtil);
		 return "mProduct";
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.error("操作异常: ",e);
		}
			return null;
	}
	/**
	 * 加载商品兑换报表
	 * @return
	 */
	public String loadMExchangeReport(){

		try {
			request = getRequest();
			UsersAdmin users = (UsersAdmin)getRequest().getSession().getAttribute("usersAdmin");
			if(QwyUtil.isNullAndEmpty(users)){
				return "login";
			}
			
			PageUtil<MExchangeReport> pageUtil = new PageUtil<MExchangeReport>();
			pageUtil.setCurrentPage(currentPage);
			pageUtil.setPageSize(pageSize);
			
			StringBuffer url = new StringBuffer();
			url.append(getRequest().getServletContext().getContextPath());
			url.append("/Product/Admin/mExchangeReport!loadMExchangeReport.action?");
			if(!QwyUtil.isNullAndEmpty(insertTime)){
				url.append("&insertTime=");
				url.append(insertTime);
			}
			if(!QwyUtil.isNullAndEmpty(username)){
				url.append("&username=");
				url.append(username);
			}
			pageUtil.setPageUrl(url.toString());
			
			bean.loadMExchange(username, insertTime, pageUtil);
			if (!QwyUtil.isNullAndEmpty(pageUtil)) {
				getRequest().setAttribute("pageUtil", pageUtil);
				getRequest().setAttribute("username", username);
				getRequest().setAttribute("insertTime", insertTime);
			    return "exchangeReport";
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.error("操作异常: ",e);
		}
			return null;
	}
	/**
	 * 导出瞄产品保表
	 * @return
	 */
	public String exportProduct(){
		try {
		PageUtil<MProduct> pageUtil = new PageUtil<MProduct>();
 		pageUtil.setCurrentPage(currentPage);
		pageUtil.setPageSize(999999999);
	    bean.loadMProduct(pageUtil,insertTime);
		HSSFWorkbook wb = new HSSFWorkbook();  
        HSSFSheet sheet = wb.createSheet("商品产品报表");  
        HSSFRow row = sheet.createRow((int) 0);  
        HSSFCellStyle style = wb.createCellStyle();  
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式     

        HSSFCell cell = row.createCell(0);  
        cell.setCellValue("发布时间");  
        cell.setCellStyle(style);  
        cell = row.createCell(1);  
        cell.setCellValue("商品名称");  
        cell.setCellStyle(style);  
        cell = row.createCell(2);  
        cell.setCellValue("商品价值（喵币）");  
        cell.setCellStyle(style);  
        cell = row.createCell(3);  
        cell.setCellValue("商品发布数量");  
        cell.setCellStyle(style); 
        cell = row.createCell(4);  
        cell.setCellValue("商品剩余数量");  
        cell.setCellStyle(style); 
        cell = row.createCell(5);  
        cell.setCellValue("VIP等级");  
        cell.setCellStyle(style); 
    
        cell = row.createCell(6);  
        cell.setCellValue("邮费");  
        cell.setCellStyle(style); 
        
		  List list = pageUtil.getList();
		  MProduct report = null;
		  for (int i = 0; i < list.size(); i++) {  
	        	row = sheet.createRow((int) i + 1);
	        	report = (MProduct)list.get(i);
	        	row.createCell(0).setCellValue(QwyUtil.fmyyyyMMddHHmm.format(report.getInsertTime()));  
	        	row.createCell(1).setCellValue(report.getTitle());  
	        	row.createCell(2).setCellValue(report.getPrice());
	        	row.createCell(3).setCellValue(report.getStock());
	        	row.createCell(4).setCellValue(report.getLeftStock());
	        	row.createCell(5).setCellValue(report.getVip());
	        	row.createCell(6).setCellValue(report.getPostage()==0.0?"包邮":"不包邮");
	        }
		  String realPath = request.getServletContext().getRealPath("/report/exportProduct.xls");
	        FileOutputStream fout = new FileOutputStream(realPath);  
	        wb.write(fout);
	        fout.close();
	        response.getWriter().write("/report/exportProduct.xls");
	        
	        
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	/**导出商品兑换报表
	 * @return
	 */
	public String iportFXTable(){
		try {
			PageUtil<MExchangeReport> pageUtil = new PageUtil<MExchangeReport>();
     		pageUtil.setCurrentPage(currentPage);
			pageUtil.setPageSize(999999999);
			bean.loadMExchange(username, insertTime, pageUtil);
			HSSFWorkbook wb = new HSSFWorkbook();  
	        HSSFSheet sheet = wb.createSheet("商品兑换报表");  
	        HSSFRow row = sheet.createRow((int) 0);  
	        HSSFCellStyle style = wb.createCellStyle();  
	        style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式  
	  
	        HSSFCell cell = row.createCell(0);  
	        cell.setCellValue("兑换日期");  
	        cell.setCellStyle(style);  
	        cell = row.createCell(1);  
	        cell.setCellValue("商品名称");  
	        cell.setCellStyle(style);  
	        cell = row.createCell(2);  
	        cell.setCellValue("Vip等级");  
	        cell.setCellStyle(style);  
	        cell = row.createCell(3);  
	        cell.setCellValue("单价(瞄币)");  
	        cell.setCellStyle(style); 
	        cell = row.createCell(4);  
	        cell.setCellValue("兑换用户");  
	        cell.setCellStyle(style); 
	        cell = row.createCell(5);  
	        cell.setCellValue("兑换数量");  
	        cell.setCellStyle(style); 
	    //。（收货地址和联系人）
	        cell = row.createCell(6);  
	        cell.setCellValue("价格(瞄币)");  
	        cell.setCellStyle(style); 
	        cell = row.createCell(7);  
	        cell.setCellValue("收货地址");  
	        cell.setCellStyle(style); 
	        cell = row.createCell(8);  
	        cell.setCellValue("联系人");  
	        cell.setCellStyle(style); 
	        
			  List list = pageUtil.getList();
			  MExchangeReport report = null;
			  for (int i = 0; i < list.size(); i++) {  
		        	row = sheet.createRow((int) i + 1);
		        	report = (MExchangeReport)list.get(i);
		        	row.createCell(0).setCellValue(report.getInsDate());  
		        	row.createCell(1).setCellValue(report.getTitle());  
		        	row.createCell(2).setCellValue(report.getLevel());
		        	row.createCell(3).setCellValue(report.getPrice());
		        	row.createCell(4).setCellValue(DESEncrypt.jieMiUsername(report.getUsername()));
		        	row.createCell(5).setCellValue(report.getCopies());
		        	row.createCell(6).setCellValue(report.getCoin());
		        	row.createCell(7).setCellValue(report.getAddress());
		        	row.createCell(8).setCellValue(report.getContractName());		        	
		        }
			  String realPath = request.getServletContext().getRealPath("/report/exchangeReport.xls");
		        FileOutputStream fout = new FileOutputStream(realPath);  
		        wb.write(fout);
		        fout.close();
		        response.getWriter().write("/report/exchangeReport.xls");
			  
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	
	public String endExchange(){
		String json="";
		try {

		if(!QwyUtil.isNullAndEmpty(id)){
			MProduct pro=bean.findMProductById(id);
			if(!QwyUtil.isNullAndEmpty(pro)){
				if(bean.endExchange(pro)){
					json = QwyUtil.getJSONString("ok", "修改成功");	
					}else{
						json = QwyUtil.getJSONString("ok", "修改失败");
					}
				}
			}else{
				json = QwyUtil.getJSONString("error", "修改失败");	
			}
		} catch (Exception e) {
			// TODO: handle exception
			log.error("操作异常: ",e);
			json = QwyUtil.getJSONString("error", "操作异常");	
		}
		try {
			QwyUtil.printJSON(getResponse(), json);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public String modifyMCoinPay(){
		String json="";
		try {
			request = getRequest();
			UsersAdmin users = (UsersAdmin)getRequest().getSession().getAttribute("usersAdmin");
			if(QwyUtil.isNullAndEmpty(users)){
				//管理员没有登录;
				return "login";
			}
			if(QwyUtil.isNullAndEmpty(mCoinPayId)){
				//发送失败
				getRequest().setAttribute("msg", "no");
			}else{
				if(bean.modifyMCoinPayById(mCoinPayId,users.getId())){
					//发送成功
					json = QwyUtil.getJSONString("ok", "短信发送成功");
				}else{
					//发送失败
					json = QwyUtil.getJSONString("no", "短信发送失败");
				}
				
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			json = QwyUtil.getJSONString("error", "操作异常");
		}
		try {
			QwyUtil.printJSON(getResponse(), json);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

	}

	
	
	public String getId() {
	return id;
}

public void setId(String id) {
	this.id = id;
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

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getInsertTime() {
		return insertTime;
	}

	public void setInsertTime(String insertTime) {
		this.insertTime = insertTime;
	}
	public String getMCoinPayId() {
		return mCoinPayId;
	}
	public void setMCoinPayId(String mCoinPayId) {
		this.mCoinPayId = mCoinPayId;
	}
	
}
