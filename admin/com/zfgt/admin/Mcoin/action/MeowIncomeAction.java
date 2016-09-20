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

import com.zfgt.admin.Mcoin.bean.MeowIncomeBean;
import com.zfgt.admin.Mcoin.dao.MeowIncomeDAO;
import com.zfgt.admin.Mcoin.dao.UsersMcoin;
import com.zfgt.common.action.BaseAction;
import com.zfgt.common.util.DESEncrypt;
import com.zfgt.common.util.PageUtil;
import com.zfgt.common.util.QwyUtil;
import com.zfgt.orm.MCoinRecord;
import com.zfgt.orm.Users;
import com.zfgt.orm.UsersAdmin;
import com.zfgt.orm.UsersInfo;


@SuppressWarnings("serial")
@ParentPackage("struts-default")
@Namespace("/Product/Admin")
@Results({
	@Result(name = "meowIncome", value = "/Product/Admin/mcoin/meowIncome.jsp"),
	@Result(name = "findUsersMcoin", value = "/Product/Admin/mcoin/findUsersMcoin.jsp"),
	@Result(name = "cleanMCoin", value = "/Product/Admin/mcoin/cleanMCoin.jsp"),
	
})

public class MeowIncomeAction  extends BaseAction {
	@Resource
	private MeowIncomeBean bean;
    private String id;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	private String mcoin;
	private String note;
	
	private String username;
	private PageUtil<MeowIncomeDAO> pageUtil;
	private Integer currentPage = 1;
	private Integer pageSize = 50;
	private String insertTime;
	
	/**
	 * 加载某一天喵币发放明细
	 * @return
	 */
	public String showMeowIncome(){
		
		try {
			request = getRequest();
			UsersAdmin users = (UsersAdmin)getRequest().getSession().getAttribute("usersAdmin");
			if(QwyUtil.isNullAndEmpty(users)){
				return "login";
			}
			
			PageUtil<MeowIncomeDAO> pageUtil = new PageUtil<MeowIncomeDAO>();
			pageUtil.setCurrentPage(currentPage);
			pageUtil.setPageSize(pageSize);
			
			StringBuffer url = new StringBuffer();
			url.append(getRequest().getServletContext().getContextPath());
			url.append("/Product/Admin/meowIncome!showMeowIncome.action?");
		   if(!QwyUtil.isNullAndEmpty(username)){
				url.append("&username=");
				url.append(username);
			}
		   if(!QwyUtil.isNullAndEmpty(insertTime)){
				url.append("&insertTime=");
				url.append(insertTime);
			}
			
			pageUtil.setPageUrl(url.toString());
			
			bean.loadMeowIncome(username,  insertTime, pageUtil);
			if (!QwyUtil.isNullAndEmpty(pageUtil)) {
				getRequest().setAttribute("pageUtil", pageUtil);
				getRequest().setAttribute("name", username);
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
	 * 导出发放喵币
	 * @return
	 */
	public String exportMeowIncome(){
		try {
			PageUtil<MeowIncomeDAO> pageUtil = new PageUtil<MeowIncomeDAO>();
	 		pageUtil.setCurrentPage(currentPage);
	 		pageUtil.setPageSize(999999999);
			bean.loadMeowIncome(username,  insertTime, pageUtil);
			HSSFWorkbook wb = new HSSFWorkbook();  
	        HSSFSheet sheet = wb.createSheet("喵币发放明细");  
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
	        cell.setCellValue("发放量（喵币）");  
	        cell.setCellStyle(style);  
	        cell = row.createCell(3);  
	        cell.setCellValue("发放时间");  
	        cell.setCellStyle(style); 
	        cell = row.createCell(4);  
	        cell.setCellValue("发放原由");  
	        cell.setCellStyle(style); 
	        cell = row.createCell(5);  
			  List list = pageUtil.getList();
			  MeowIncomeDAO report = null;
			  for (int i = 0; i < list.size(); i++) {  
		        	row = sheet.createRow((int) i + 1);
		        	report = (MeowIncomeDAO)list.get(i);
		        	row.createCell(0).setCellValue(DESEncrypt.jieMiUsername(report.getUserName()));  
		        	row.createCell(1).setCellValue(report.getTotalCoin());  
		        	row.createCell(2).setCellValue(report.getNumber());
		        	row.createCell(3).setCellValue(QwyUtil.fmHHmmss.format(report.getInsertTime()));
		        	row.createCell(4).setCellValue(report.getType());
		        }
			  String realPath = request.getServletContext().getRealPath("/report/meowIncome.xls");
		        FileOutputStream fout = new FileOutputStream(realPath);  
		        wb.write(fout);
		        fout.close();
		        response.getWriter().write("/report/meowIncome.xls");
		        
		        
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;

	}
	
	/**获取用户的真实姓名;
	 * @return
	 */
	public String getRealNameByUsername(){
		String json = "";
		try {
			UsersAdmin users = (UsersAdmin)getRequest().getSession().getAttribute("usersAdmin");
			if(QwyUtil.isNullAndEmpty(users)){
				json = QwyUtil.getJSONString("err", "管理员未登录");
				QwyUtil.printJSON(getResponse(), json);
				//管理员没有登录;
				return null;
			}
			if(QwyUtil.isNullAndEmpty(username)){
				json = QwyUtil.getJSONString("err", "用户名不能为空");
				QwyUtil.printJSON(getResponse(), json);
				return null;
			}
			if(!username.contains(",")){//查询单个用户
				Users user = bean.getUsersByUsername(username);
				if(QwyUtil.isNullAndEmpty(user)){
					json = QwyUtil.getJSONString("err", "用户名不存在");
					QwyUtil.printJSON(getResponse(), json);
					return null;
				}
				UsersInfo ui = user.getUsersInfo();
				if(QwyUtil.isNullAndEmpty(ui)){
					json = QwyUtil.getJSONString("err", "用户信息不存在");
					QwyUtil.printJSON(getResponse(), json);
					return null;
				}
				String realName = ui.getRealName();
				json = QwyUtil.getJSONString("ok", realName);
			}else{
				json = QwyUtil.getJSONString("us", "填写多个用户请认真核对用户信息");
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			log.error(e.getMessage(),e);
			json = QwyUtil.getJSONString("err", "服务器异常");
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
	 * 管理员发放喵币
	 * @return
	 */
	public String sendMcoin(){
		String json = "";
		try {
			UsersAdmin users = (UsersAdmin)getRequest().getSession().getAttribute("usersAdmin");
			if(QwyUtil.isNullAndEmpty(users)){
				json = QwyUtil.getJSONString("err", "管理员未登录");
				QwyUtil.printJSON(getResponse(), json);
				//管理员没有登录;
				return null;
			}
			if(QwyUtil.isNullAndEmpty(mcoin)){
				json = QwyUtil.getJSONString("err", "请输入发放的喵币数量");
				QwyUtil.printJSON(getResponse(), json);
				return null;
			}
			if(!QwyUtil.isOnlyNumber(mcoin)){
				json = QwyUtil.getJSONString("err", "输入喵币的格式有误");
				QwyUtil.printJSON(getResponse(), json);
				return null;
			}
			if(QwyUtil.isNullAndEmpty(username)){
				json = QwyUtil.getJSONString("err", "用户名不能为空");
				QwyUtil.printJSON(getResponse(), json);
				return null;
			}
			if(QwyUtil.isNullAndEmpty(note)){
				json = QwyUtil.getJSONString("err", "备注不能为空");
				QwyUtil.printJSON(getResponse(), json);
				return null;
			}
			String[] strArray=null;
//			if(!username.contains(",")){
//				username=username+",";
//			}
			strArray=username.split(",");
		    for(int i=0; i<strArray.length; i++) {              
		    	String alert="";
		    	if(i>0){
		    		alert="。之前的用户名已成功发送！！";
		    	}
		        if(!QwyUtil.verifyPhone(strArray[i].trim())){
		         json = QwyUtil.getJSONString("error", "用户名输入有误："+strArray[i].trim()+alert);
		         break;
		        }else{
		        	Users us=bean.getUsersByUsername(strArray[i].trim());
		        	if(us==null){
		        		json = QwyUtil.getJSONString("error", "该用户名不存在："+strArray[i].trim()+alert);
		        		break;
		        	}else{
		        		bean.sengMcoin(us.getId(),note,mcoin,us.getUsersInfo().getTotalPoint());
		        	}
		        } 
		       }
		    if("".equals(json)){
		    	json = QwyUtil.getJSONString("ok", "发放喵币成功");
		    }
//			if(isSend){
//				//发送成功;
//				json = QwyUtil.getJSONString("ok", "发放喵币成功");
//			}else{
//				json = QwyUtil.getJSONString("err", "发放喵币失败");
//			}

		} catch (Exception e) {
			log.error("操作异常: ",e);
			json = QwyUtil.getJSONString("err", "发放喵币失败");
		}
		try {
			QwyUtil.printJSON(getResponse(), json);
		} catch (IOException e) {
			log.error("操作异常: ",e);
		}
		return null;
	}
	
	
	/**查询清零用户的总喵币
	 * 
	 * @return
	 */
	public String findUsersMcoin(){
		try {
			request = getRequest();
			UsersAdmin users = (UsersAdmin)getRequest().getSession().getAttribute("usersAdmin");
			if(QwyUtil.isNullAndEmpty(users)){
				return "login";
			}
		 PageUtil<MCoinRecord> pageUtil=new PageUtil<MCoinRecord>();
		 pageUtil.setCurrentPage(currentPage);
	     pageUtil.setPageSize(pageSize);
	     
	     StringBuffer url = new StringBuffer();
			url.append(getRequest().getServletContext().getContextPath());
			url.append("/Product/Admin/meowIncome!findUsersMcoin.action?");
		
			if(!QwyUtil.isNullAndEmpty(username)){
				url.append("&username=");
				url.append(username);
			}
			pageUtil.setPageUrl(url.toString());
			
	     bean.findUsersMcoin(pageUtil,username);
		if(!QwyUtil.isNullAndEmpty(pageUtil)){
				request.setAttribute("list", pageUtil.getList());
			}
		request.setAttribute("pageUtil", pageUtil);
		 return "findUsersMcoin";
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.error("操作异常: ",e);
		}
			return null;
		
	}
	
	/**
	 * 加载用户信息用清空喵币
	 * @return
	 */
	public String loadUserInfo(){
		try {
			request = getRequest();
			UsersAdmin users = (UsersAdmin)getRequest().getSession().getAttribute("usersAdmin");
			if(QwyUtil.isNullAndEmpty(users)){
				return "login";
			}
		 PageUtil<UsersMcoin> pageUtil=new PageUtil<UsersMcoin>();
		 pageUtil.setCurrentPage(currentPage);
	     pageUtil.setPageSize(pageSize);
	     
	     StringBuffer url = new StringBuffer();
		 url.append(getRequest().getServletContext().getContextPath());
		 url.append("/Product/Admin/meowIncome!loadUserInfo.action?");
		 if(!QwyUtil.isNullAndEmpty(username)){
				url.append("&username=");
				url.append(username);
			}
		 pageUtil.setPageUrl(url.toString());
			
	     bean.loadUserInfo(pageUtil,username);
		if(!QwyUtil.isNullAndEmpty(pageUtil)){
				request.setAttribute("list", pageUtil.getList());
			}
		request.setAttribute("pageUtil", pageUtil);
		 return "cleanMCoin";
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.error("操作异常: ",e);
		}
			return null;
	}
	
	
	/**
	 * 清零喵币
	 * @return
	 */
	public String cleanMcoin(){
		String json="";
		try {

		if(!QwyUtil.isNullAndEmpty(id)){
			UsersInfo users=bean.findUsersInfoId(Long.parseLong(id));
			if(!QwyUtil.isNullAndEmpty(users)){
				if(bean.cleanMcoin(users)){
					json = QwyUtil.getJSONString("ok", "修改成功");	
					}else{
						json = QwyUtil.getJSONString("error", "修改失败");
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

	public String getMcoin() {
		return mcoin;
	}
	public void setMcoin(String mcoin) {
		this.mcoin = mcoin;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public PageUtil<MeowIncomeDAO> getPageUtil() {
		return pageUtil;
	}
	public void setPageUtil(PageUtil<MeowIncomeDAO> pageUtil) {
		this.pageUtil = pageUtil;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
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
