package com.zfgt.product.action;


import java.io.FileOutputStream;
import java.util.List;
import javax.annotation.Resource;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.struts2.config.Namespace;
import org.apache.struts2.config.ParentPackage;
import org.apache.struts2.config.Result;
import org.apache.struts2.config.Results;

import com.zfgt.common.action.BaseAction;
import com.zfgt.common.util.PageUtil;
import com.zfgt.common.util.QwyUtil;
import com.zfgt.orm.CoinPurseReport;
import com.zfgt.product.bean.CoinPurseReportBean;

/**
 * 零钱包报表
 * @author liuchao
 *
 */
@SuppressWarnings("serial")
@ParentPackage("struts-default")
@Namespace("/Product/Admin")
@Results({ 
	@Result(name = "aWeekWithoutMoving", value = "/Product/Admin/operationManager/coinPurseReport.jsp"),
	@Result(name = "coinPurseDataReport", value = "/Product/Admin/operationManager/coinPurseDataReport.jsp"),
	@Result(name = "coinPurseDetailReport", value = "/Product/Admin/operationManager/coinPurseDetailReport.jsp")
	
	
})
public class CoinPurseReportAction extends BaseAction {
	
	private static Logger log = Logger.getLogger(CoinPurseReportAction.class);
	
	@Resource
	private CoinPurseReportBean coinPurseReportBean;
	private Integer currentPage = 1;
	private Integer pageSize = 20;
	private String insertTime;
	private String username;
	private String queryDate;
	private String mobileNum;

	/**
	 * 一周不可动金额报表
	 * @return
	 */
	public String aWeekWithoutMoving(){
		try {
			PageUtil<CoinPurseReport> pageUtil = new PageUtil<CoinPurseReport>();
			pageUtil.setCurrentPage(currentPage);
			pageUtil.setPageSize(pageSize);
			StringBuffer url = new StringBuffer();
			url.append(getRequest().getServletContext().getContextPath());
			url.append("/Product/Admin/coinPurseReport!aWeekWithoutMoving.action?");
			if(!QwyUtil.isNullAndEmpty(insertTime)){
				url.append("&insertTime=");
				url.append(insertTime);
			}
			pageUtil.setPageUrl(url.toString());
			getRequest().setAttribute("insertTime", insertTime);
			coinPurseReportBean.aWeekWithoutMoving(pageUtil,insertTime);
			if(!QwyUtil.isNullAndEmpty(pageUtil)){
				getRequest().setAttribute("pageUtil", pageUtil);
					
			}
		} catch (Exception e) {
			log.error("操作异常: ",e);
		}
		return "aWeekWithoutMoving";
	}
	
	@SuppressWarnings("rawtypes")
	public String importAWeekWithoutMoving(){
		try {
			PageUtil<CoinPurseReport> pageUtil = new PageUtil<CoinPurseReport>();
			pageUtil.setCurrentPage(currentPage);
			pageUtil.setPageSize(999999999);
			insertTime = queryDate;
			coinPurseReportBean.aWeekWithoutMoving(pageUtil,insertTime);
	        HSSFWorkbook wb = new HSSFWorkbook();  
	        HSSFSheet sheet = wb.createSheet("一周不可动金额报表");  
	        HSSFRow row = sheet.createRow((int) 0);  
	        HSSFCellStyle style = wb.createCellStyle();  
	        style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式  
	  
	        HSSFCell cell = row.createCell(0);  
	        cell.setCellValue("日期");  
	        cell.setCellStyle(style);  
	        cell = row.createCell(1);  
	        cell.setCellValue("转入金额");  
	        cell.setCellStyle(style);  
	        cell = row.createCell(2);  
	        cell.setCellValue("7日存留率(%)");  
	        cell.setCellStyle(style);  
	        cell = row.createCell(3);  
	        cell.setCellValue("7日不可动金额比(%)");  
	        cell.setCellStyle(style); 
	        cell = row.createCell(4);  
	        cell.setCellValue("7日不可动金额");  
	        cell.setCellStyle(style); 
	        
	        CoinPurseReport coinPurseReport = null;
	        List list = pageUtil.getList();
	        for (int i = 0; i < list.size(); i++) {  
	        	row = sheet.createRow((int) i + 1);
	        	coinPurseReport = (CoinPurseReport)list.get(i);
	        	row.createCell(0).setCellValue(coinPurseReport.getDate());  
	        	row.createCell(1).setCellValue(coinPurseReport.getInMoney());  
	        	row.createCell(2).setCellValue(coinPurseReport.getaWeekRetentionRate());
	        	row.createCell(3).setCellValue(coinPurseReport.getaWeekWithoutMovingMoneyRate());
	        	row.createCell(4).setCellValue(coinPurseReport.getaWeekWithoutMovingMoney());
	        }
	        
	    	String realPath = request.getServletContext().getRealPath("/report/aWeekWithoutMoving.xls");
	        FileOutputStream fout = new FileOutputStream(realPath);  
	        wb.write(fout);
	        fout.close();
	        response.getWriter().write("/report/aWeekWithoutMoving.xls");
	        
		} catch (Exception e) {
			log.error("操作异常: ",e);
		}
		return null;
	}
	
	public String coinPurseDataReport(){
		try {
			PageUtil<CoinPurseReport> pageUtil = new PageUtil<CoinPurseReport>();
			pageUtil.setCurrentPage(currentPage);
			pageUtil.setPageSize(pageSize);
			StringBuffer url = new StringBuffer();
			url.append(getRequest().getServletContext().getContextPath());
			url.append("/Product/Admin/coinPurseReport!coinPurseDataReport.action?");
			if(!QwyUtil.isNullAndEmpty(insertTime)){
				url.append("&insertTime=");
				url.append(insertTime);
			}
			pageUtil.setPageUrl(url.toString());
			getRequest().setAttribute("insertTime", insertTime);
			coinPurseReportBean.coinPurseDataReport(pageUtil,insertTime);
			if(!QwyUtil.isNullAndEmpty(pageUtil)){
				getRequest().setAttribute("pageUtil", pageUtil);
					
			}
		} catch (Exception e) {
			log.error("操作异常: ",e);
		}
		return "coinPurseDataReport";
	}
	
	@SuppressWarnings("rawtypes")
	public String importCoinPurseDataReport(){
		try {
			PageUtil<CoinPurseReport> pageUtil = new PageUtil<CoinPurseReport>();
			pageUtil.setCurrentPage(currentPage);
			pageUtil.setPageSize(999999999);
			insertTime = queryDate;
			coinPurseReportBean.coinPurseDataReport(pageUtil,insertTime);
	        HSSFWorkbook wb = new HSSFWorkbook();  
	        HSSFSheet sheet = wb.createSheet("零钱包数据报表");  
	        HSSFRow row = sheet.createRow((int) 0);  
	        HSSFCellStyle style = wb.createCellStyle();  
	        style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式  
	  
	        HSSFCell cell = row.createCell(0);  
	        cell.setCellValue("日期");  
	        cell.setCellStyle(style);  
	        cell = row.createCell(1);  
	        cell.setCellValue("转入");  
	        cell.setCellStyle(style);  
	        cell = row.createCell(2);  
	        cell.setCellValue("转出");  
	        cell.setCellStyle(style);  
	        cell = row.createCell(3);  
	        cell.setCellValue("存量");  
	        cell.setCellStyle(style); 
	        cell = row.createCell(4);  
	        cell.setCellValue("付息");  
	        cell.setCellStyle(style); 
	        cell = row.createCell(5);  
	        cell.setCellValue("转入金额占充值金额比重(%)");  
	        cell.setCellStyle(style); 
	        cell = row.createCell(6);  
	        cell.setCellValue("转入金额占投资金额比重(%)");  
	        cell.setCellStyle(style); 
	        cell = row.createCell(7);  
	        cell.setCellValue("一周不可动金额");  
	        cell.setCellStyle(style); 
	        
	        CoinPurseReport coinPurseReport = null;
	        List list = pageUtil.getList();
	        for (int i = 0; i < list.size(); i++) {  
	        	row = sheet.createRow((int) i + 1);
	        	coinPurseReport = (CoinPurseReport)list.get(i);
	        	row.createCell(0).setCellValue(coinPurseReport.getDate());  
	        	row.createCell(1).setCellValue(coinPurseReport.getInMoney());  
	        	row.createCell(2).setCellValue(coinPurseReport.getOutMoney());
	        	row.createCell(3).setCellValue(coinPurseReport.getCunliang());
	        	row.createCell(4).setCellValue(coinPurseReport.getFuxi());
	        	row.createCell(5).setCellValue(coinPurseReport.getChongzhiRate());
	        	row.createCell(6).setCellValue(coinPurseReport.getTouziRate());
	        	row.createCell(7).setCellValue(coinPurseReport.getaWeekWithoutMovingMoney());
	        }
	        
	    	String realPath = request.getServletContext().getRealPath("/report/coinPurseDataReport.xls");
	        FileOutputStream fout = new FileOutputStream(realPath);  
	        wb.write(fout);
	        fout.close();
	        response.getWriter().write("/report/coinPurseDataReport.xls");
	        
		} catch (Exception e) {
			log.error("操作异常: ",e);
		}
		return null;
	}
	
	public String coinPurseDetailReport(){
		try {
			PageUtil<CoinPurseReport> pageUtil = new PageUtil<CoinPurseReport>();
			pageUtil.setCurrentPage(currentPage);
			pageUtil.setPageSize(pageSize);
			StringBuffer url = new StringBuffer();
			url.append(getRequest().getServletContext().getContextPath());
			url.append("/Product/Admin/coinPurseReport!coinPurseDetailReport.action?");
			if(!QwyUtil.isNullAndEmpty(insertTime)){
				url.append("&insertTime=");
				url.append(insertTime);
			}
			if(!QwyUtil.isNullAndEmpty(username)){
				url.append("&username=");
				url.append(username);
			}
			
			pageUtil.setPageUrl(url.toString());
			getRequest().setAttribute("insertTime", insertTime);
			getRequest().setAttribute("username", username);
			coinPurseReportBean.coinPurseDetailReport(pageUtil,insertTime,username);
			if(!QwyUtil.isNullAndEmpty(pageUtil)){
				getRequest().setAttribute("pageUtil", pageUtil);
					
			}
		} catch (Exception e) {
			log.error("操作异常: ",e);
		}
		return "coinPurseDetailReport";
	}
	
	@SuppressWarnings("rawtypes")
	public String importCoinPurseDetailReport(){
		try {
			PageUtil<CoinPurseReport> pageUtil = new PageUtil<CoinPurseReport>();
			pageUtil.setCurrentPage(currentPage);
			pageUtil.setPageSize(999999999);
			insertTime = queryDate;
			username = mobileNum;
			coinPurseReportBean.coinPurseDetailReport(pageUtil,insertTime,username);
	        HSSFWorkbook wb = new HSSFWorkbook();  
	        HSSFSheet sheet = wb.createSheet("零钱包明细表");  
	        HSSFRow row = sheet.createRow((int) 0);  
	        HSSFCellStyle style = wb.createCellStyle();  
	        style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式  
	  
	        HSSFCell cell = row.createCell(0);  
	        cell.setCellValue("序号");  
	        cell.setCellStyle(style);  
	        cell = row.createCell(1);  
	        cell.setCellValue("用户名");  
	        cell.setCellStyle(style);  
	        cell = row.createCell(2);  
	        cell.setCellValue("转入金额");  
	        cell.setCellStyle(style);  
	        cell = row.createCell(3);  
	        cell.setCellValue("转入时间");  
	        cell.setCellStyle(style); 
	        cell = row.createCell(4);  
	        cell.setCellValue("转出金额");  
	        cell.setCellStyle(style); 
	        cell = row.createCell(5);  
	        cell.setCellValue("转出时间");  
	        cell.setCellStyle(style); 
	        cell = row.createCell(6);  
	        cell.setCellValue("付息金额");  
	        cell.setCellStyle(style); 
	        cell = row.createCell(7);  
	        cell.setCellValue("付息时间");  
	        cell.setCellStyle(style); 
	        
	        CoinPurseReport coinPurseReport = null;
	        List list = pageUtil.getList();
	        for (int i = 0; i < list.size(); i++) {  
	        	row = sheet.createRow((int) i + 1);
	        	coinPurseReport = (CoinPurseReport)list.get(i);
	        	row.createCell(0).setCellValue(coinPurseReport.getId());  
	        	row.createCell(1).setCellValue(coinPurseReport.getMobileNum());  
	        	row.createCell(2).setCellValue(coinPurseReport.getInMoney());
	        	row.createCell(3).setCellValue(coinPurseReport.getInTime());
	        	row.createCell(4).setCellValue(coinPurseReport.getOutMoney());
	        	row.createCell(5).setCellValue(coinPurseReport.getOutTime());
	        	row.createCell(6).setCellValue(coinPurseReport.getShouyiMoney());
	        	row.createCell(7).setCellValue(coinPurseReport.getShouyiTime());
	        }
	        
	    	String realPath = request.getServletContext().getRealPath("/report/coinPurseDetailReport.xls");
	        FileOutputStream fout = new FileOutputStream(realPath);  
	        wb.write(fout);
	        fout.close();
	        response.getWriter().write("/report/coinPurseDetailReport.xls");
	        
		} catch (Exception e) {
			log.error("操作异常: ",e);
		}
		return null;
	}
	
	public String lqbqxt(){
		try {
			StringBuffer json = coinPurseReportBean.lqbqxt(insertTime);
			QwyUtil.printJSON(response, json.toString());
		} catch (Exception e) {
			log.error("操作异常: ",e);
		}
		return null;
	}
	

	public Integer getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(Integer currentPage) {
		this.currentPage = currentPage;
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

	public String getQueryDate() {
		return queryDate;
	}

	public void setQueryDate(String queryDate) {
		this.queryDate = queryDate;
	}

	public String getMobileNum() {
		return mobileNum;
	}

	public void setMobileNum(String mobileNum) {
		this.mobileNum = mobileNum;
	}
	
}
