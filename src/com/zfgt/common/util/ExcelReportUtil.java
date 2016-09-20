package com.zfgt.common.util;

import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

/**
 * Excel报表封装类
 * @author liuchao
 *
 */
public class ExcelReportUtil {
	private String title = "";
	private String[] columnNames;
	private List<Object> data;
	private HSSFWorkbook wb;
	private HSSFSheet sheet;
	
	public ExcelReportUtil (){
		
	}
	
	public ExcelReportUtil (String[] columnNames,List<Object> data){
		this.data = data;
		this.columnNames = columnNames;
		CreateSheet();
	}
	
	public void createHSSFWorkbook(){
		this.wb = new HSSFWorkbook();
	}
	
	public void CreateSheet(){
        this.sheet = this.wb.createSheet(this.title);
	}
	
	public void createCell(){
		HSSFRow row = this.sheet.createRow((int) 0);  
        HSSFCellStyle style = this.wb.createCellStyle();  
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式
        row.createCell(0);
	}
	
	public void setTitle(String title){
		this.title= title;
	}
}
