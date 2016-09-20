package com.zfgt.admin.Mcoin.action;

import java.io.FileOutputStream;
import java.io.IOException;
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

import com.zfgt.admin.Mcoin.bean.MCoinDayDetailBean;
import com.zfgt.common.action.BaseAction;
import com.zfgt.common.util.PageUtil;
import com.zfgt.common.util.QwyUtil;
import com.zfgt.orm.MCoinDayDetail;
import com.zfgt.orm.UsersAdmin;

@SuppressWarnings("serial")
@ParentPackage("struts-default")
@Namespace("/Product/Admin")
@Results({
	@Result(name = "mCoinDayDetail", value = "/Product/Admin/mcoin/mCoinDayDetail.jsp"),
	
})
public class MCoinDayDetailAction  extends BaseAction{
	@Resource
	private MCoinDayDetailBean bean;
	
	private Integer currentPage = 1;
	private Integer pageSize = 50;
	private String insertTime;
	/**
	 *  加载喵币报表
	 * @return
	 */
	public String loadMCoinDayDetail(){
		try {
			request = getRequest();
			UsersAdmin users = (UsersAdmin)getRequest().getSession().getAttribute("usersAdmin");
			if(QwyUtil.isNullAndEmpty(users)){
				return "login";
			}
		 PageUtil<MCoinDayDetail> pageUtil=new PageUtil<MCoinDayDetail>();
		 pageUtil.setCurrentPage(currentPage);
	     pageUtil.setPageSize(pageSize);
	     
	     StringBuffer url = new StringBuffer();
			url.append(getRequest().getServletContext().getContextPath());
			url.append("/Product/Admin/mCoinDayDetail!loadMCoinDayDetail.action?");
		
			if(!QwyUtil.isNullAndEmpty(insertTime)){
				url.append("&insertTime=");
				url.append(insertTime);
			}
			pageUtil.setPageUrl(url.toString());
			
	     bean.loadMCoinDayDetail(pageUtil,insertTime);
		if(!QwyUtil.isNullAndEmpty(pageUtil)){
				request.setAttribute("list", pageUtil.getList());
				getRequest().setAttribute("insertTime", insertTime);
			}
		request.setAttribute("pageUtil", pageUtil);
		 return "mCoinDayDetail";
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.error("操作异常: ",e);
		}
			return null;
	}
	
	/**
	 * 导出喵币报表
	 * @return
	 */
	public String exportCoinDayDetail(){
	
	try {
		PageUtil<MCoinDayDetail> pageUtil = new PageUtil<MCoinDayDetail>();
 		pageUtil.setCurrentPage(currentPage);
		pageUtil.setPageSize(999999999);
	    bean.loadMCoinDayDetail(pageUtil,insertTime);
		HSSFWorkbook wb = new HSSFWorkbook();  
        HSSFSheet sheet = wb.createSheet("喵币报表");  
        HSSFRow row = sheet.createRow((int) 0);  
        HSSFCellStyle style = wb.createCellStyle();  
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式     

        HSSFCell cell = row.createCell(0);  
        cell.setCellValue("日期");  
        cell.setCellStyle(style);  
        cell = row.createCell(1);  
        cell.setCellValue("发放喵币");  
        cell.setCellStyle(style);  
        cell = row.createCell(2);  
        cell.setCellValue("扣除喵币");  
        cell.setCellStyle(style);  
        cell = row.createCell(3);  
        cell.setCellValue("剩余喵币");  
        cell.setCellStyle(style);  
        cell = row.createCell(4);  
		  List list = pageUtil.getList();
		  MCoinDayDetail report = null;
		  for (int i = 0; i < list.size(); i++) {  
	        	row = sheet.createRow((int) i + 1);
	        	report = (MCoinDayDetail)list.get(i);
	        	row.createCell(0).setCellValue(QwyUtil.fmyyyyMMdd.format(report.getInsertTime()));  
	        	//row.createCell(1).setCellValue(report.getInsertTime());  
	        	row.createCell(1).setCellValue(report.getCoinAdd());
	        	row.createCell(2).setCellValue(report.getCoinPay());
	        	row.createCell(3).setCellValue(report.getLeftCoin());
	        
	        }
		  String realPath = request.getServletContext().getRealPath("/report/coinDayDetail.xls");
	        FileOutputStream fout = new FileOutputStream(realPath);  
	        wb.write(fout);
	        fout.close();
	        response.getWriter().write("/report/coinDayDetail.xls");
	        
	        
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 获取清空瞄币的默认状态
	 * @return
	 */
	public String getMCoinCleanState(){
		String json ="";
		try {
		request = getRequest();
		UsersAdmin users = (UsersAdmin)getRequest().getSession().getAttribute("usersAdmin");
		if(QwyUtil.isNullAndEmpty(users)){
			return "login";
		}
		String isState = bean.getMCoinCleanState().getIsCleanMcoin();
		json = QwyUtil.getJSONString("ok", isState);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			json = QwyUtil.getJSONString("error", "启动失败");
		}
		try {
			QwyUtil.printJSON(getResponse(), json);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 操作是否开启或关闭瞄币一年清空线程
	 * @return
	 */
	public String startCelanAllMcoin(){
		request = getRequest();
		UsersAdmin users = (UsersAdmin)getRequest().getSession().getAttribute("usersAdmin");
		if(QwyUtil.isNullAndEmpty(users)){
			return "login";
		}
		String json ="";
		boolean isStart = bean.startCelanAllMcoin(users.getId());
		if(isStart){
			json = QwyUtil.getJSONString("ok", "操作成功");
		}
	else 
		{
			json = QwyUtil.getJSONString("error", "操作失败");
		}
		
		try {
			QwyUtil.printJSON(getResponse(), json);
		} catch (IOException e) {
			// TODO Auto-generated catch block
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
	public String getInsertTime() {
		return insertTime;
	}
	public void setInsertTime(String insertTime) {
		this.insertTime = insertTime;
	}
}
