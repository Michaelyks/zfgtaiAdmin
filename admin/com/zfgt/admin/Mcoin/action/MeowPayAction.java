package com.zfgt.admin.Mcoin.action;

import java.io.FileOutputStream;
import java.util.List;

import javax.annotation.Resource;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.struts2.config.Namespace;
import org.apache.struts2.config.ParentPackage;
import org.apache.struts2.config.Result;
import org.apache.struts2.config.Results;

import com.zfgt.admin.Mcoin.bean.MeowPayBean;
import com.zfgt.admin.Mcoin.dao.MeowPayDao;
import com.zfgt.common.action.BaseAction;
import com.zfgt.common.util.DESEncrypt;
import com.zfgt.common.util.PageUtil;
import com.zfgt.common.util.QwyUtil;
import com.zfgt.orm.UsersAdmin;

@SuppressWarnings("serial")
@ParentPackage("struts-default")
@Namespace("/Product/Admin")
@Results({
	@Result(name = "meowIncome", value = "/Product/Admin/mcoin/meowPay.jsp"),
	
})
public class MeowPayAction extends BaseAction {
	@Resource
	private MeowPayBean bean;

	private Integer currentPage = 1;
	private Integer pageSize = 50;
	private String username;
	private String insertTime;



	/**
	 * 加载扣除喵币明细
	 * @return
	 */
	public String showMeowPay(){

		try {
			request = getRequest();
			UsersAdmin users = (UsersAdmin)getRequest().getSession().getAttribute("usersAdmin");
			if(QwyUtil.isNullAndEmpty(users)){
				return "login";
			}
			
			PageUtil<MeowPayDao> pageUtil = new PageUtil<MeowPayDao>();
			pageUtil.setCurrentPage(currentPage);
			pageUtil.setPageSize(pageSize);
			
			StringBuffer url = new StringBuffer();
			url.append(getRequest().getServletContext().getContextPath());
			url.append("/Product/Admin/meowPay!showMeowPay.action?");
			if(!QwyUtil.isNullAndEmpty(insertTime)){
				url.append("&insertTime=");
				url.append(insertTime);
			}
			if(!QwyUtil.isNullAndEmpty(username)){
				url.append("&username=");
				url.append(username);
			}
	
			pageUtil.setPageUrl(url.toString());
			
			bean.loadMeowPay(username,  insertTime, pageUtil);
			if (!QwyUtil.isNullAndEmpty(pageUtil)) {
				getRequest().setAttribute("pageUtil", pageUtil);
				getRequest().setAttribute("username", username);
				getRequest().setAttribute("insertTime", insertTime);
			    return "meowIncome";
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.error("操作异常: ",e);
		}
			return null;
	}
	
	/**
	 * 导出扣除喵币明细
	 * @return
	 */
	public String exportMeowPay(){
		try {
			PageUtil<MeowPayDao> pageUtil = new PageUtil<MeowPayDao>();
	 		pageUtil.setCurrentPage(currentPage);
			pageUtil.setPageSize(999999999);
			bean.loadMeowPay(username,  insertTime, pageUtil);
			HSSFWorkbook wb = new HSSFWorkbook();  
	        HSSFSheet sheet = wb.createSheet("扣除喵币明细");  
	        HSSFRow row = sheet.createRow((int) 0);  
	        HSSFCellStyle style = wb.createCellStyle();  
	        style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式     

	        HSSFCell cell = row.createCell(0);  
	        cell.setCellValue("用户名");  
	        cell.setCellStyle(style);  
	        cell = row.createCell(1);  
	        cell.setCellValue("喵币余额");  
	        cell.setCellStyle(style);  
	        cell = row.createCell(2);  
	        cell.setCellValue("扣除量（喵币）");  
	        cell.setCellStyle(style);  
	        cell = row.createCell(3);  
	        cell.setCellValue("扣除时间");  
	        cell.setCellStyle(style); 
	        cell = row.createCell(4);  
	        cell.setCellValue("扣除原由");  
	        cell.setCellStyle(style); 
	        cell = row.createCell(5);  
			  List list = pageUtil.getList();
			  MeowPayDao report = null;
			  for (int i = 0; i < list.size(); i++) {  
		        	row = sheet.createRow((int) i + 1);
		        	report = (MeowPayDao)list.get(i);
		        	row.createCell(0).setCellValue(DESEncrypt.jieMiUsername(report.getUserName()));  
		        	row.createCell(1).setCellValue(report.getTotalCoin());  
		        	row.createCell(2).setCellValue(report.getNumber());
		        	row.createCell(3).setCellValue(QwyUtil.fmHHmmss.format(report.getInsertTime()));
		        	row.createCell(4).setCellValue(report.getType());
		        }
			  String realPath = request.getServletContext().getRealPath("/report/meowPay.xls");
		        FileOutputStream fout = new FileOutputStream(realPath);  
		        wb.write(fout);
		        fout.close();
		        response.getWriter().write("/report/meowPay.xls");
		        
		        
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;

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
}
