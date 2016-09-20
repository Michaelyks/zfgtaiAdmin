package com.zfgt.admin.product.action;

import java.io.FileOutputStream;
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

import com.zfgt.admin.product.bean.ActivityBean;
import com.zfgt.common.action.BaseAction;
import com.zfgt.common.util.PageUtil;
import com.zfgt.common.util.QwyUtil;
import com.zfgt.orm.ActivityStat;
import com.zfgt.orm.BackStatsOperateDay;
import com.zfgt.orm.ChannelOperateDay;
import com.zfgt.orm.PlatChannel;
import com.zfgt.orm.Qdcb;
import com.zfgt.orm.Qdtj;
import com.zfgt.orm.UsersAdmin;

/**入口;
 * @author qwy
 *
 * @createTime 2015-4-27下午3:51:34
 */
@SuppressWarnings("serial")
@ParentPackage("struts-default")
@Namespace("/Product/Admin")
	//发布产品页面
	@Results({ 
		@Result(name = "activityStats", value = "/Product/Admin/operationManager/ActivityStat.jsp"),
		@Result(name = "loadQdtj", value = "/Product/Admin/operationManager/qdtj.jsp"),
		@Result(name = "loadQdcb", value = "/Product/Admin/operationManager/qdcb.jsp"),
		@Result(name = "err", value = "/Product/Admin/err.jsp"),
		@Result(name = "loadQdtjDetails", value = "/Product/Admin/operationManager/qdtjDetails.jsp"),
})
public class ActivityAction extends BaseAction {
	@Resource
	ActivityBean bean;
	private String insertTime;
	private String registChannel;
	private Integer currentPage = 1;
	private Integer pageSize = 50;

	
	/**
	 * 入口统计
	 * @return
	 */
	public String loadActivityStat() {
		try {
			 List<ActivityStat> activityStats= bean.findTjActivitys(insertTime);
			 getRequest().setAttribute("list", activityStats);
			 getRequest().setAttribute("insertTime", insertTime);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("操作异常: ",e);
		}
		return "activityStats";
	}
	
	/**
	 *  渠道统计（安卓）
	 * @return
	 */
	public String loadQdtj(){
		String json="";
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
				if(isExistsQX("渠道统计汇总表", users.getId())){
					getRequest().setAttribute("err", "您没有操作该功能的权限!");
					return "err";
				}
			}
			getRequest().setAttribute("insertTime", insertTime);
			List<Qdtj> list=bean.getQdtj(insertTime);
			if(!QwyUtil.isNullAndEmpty(list)){
				getRequest().setAttribute("list", list);
				getRequest().setAttribute("table", "1");
				getRequest().setAttribute("tj", bean.tjQdtj(list));
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "loadQdtj";
	}
	
	/**
	 * 导出渠道统计（安卓）
	 */
	public String iportQdtjTable(){
		List<JasperPrint> list = null;
		String name = QwyUtil.fmyyyyMMddHHmmss3.format(new Date())+"_qdtj";
		try {
			String filePath = request.getServletContext().getRealPath("/WEB-INF/classes/qdtj.jasper");
			System.out.println("iportTable报表路径: "+filePath);
			list=bean.getQdtjJasperPrintList( insertTime,filePath);
			doIreport(list, name);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 *  渠道成本（安卓）
	 * @return
	 */
	public String loadQdcb(){
		String json="";
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
			if(isExistsQX("渠道成本", users.getId())){
				getRequest().setAttribute("err", "您没有操作该功能的权限!");
				return "err";
			}
			}
			getRequest().setAttribute("insertTime", insertTime);
			PageUtil<BackStatsOperateDay> pageUtil = new PageUtil<BackStatsOperateDay>();
		//	PageUtil<BackStatsOperateDay> pageUtil = new PageUtil<Qdcb>();
			pageUtil.setCurrentPage(currentPage);
			pageUtil.setPageSize(31);
			StringBuffer url = new StringBuffer();
			url.append(getRequest().getServletContext().getContextPath());
			url.append("/Product/Admin/activity!loadQdcb.action?");
			if(!QwyUtil.isNullAndEmpty(insertTime)){
				url.append("&insertTime=");
				url.append(insertTime);
			}
			pageUtil.setPageUrl(url.toString());
			pageUtil=bean.findQdcb(pageUtil,insertTime);
			if(!QwyUtil.isNullAndEmpty(pageUtil)&&!QwyUtil.isNullAndEmpty(pageUtil.getList())){
				getRequest().setAttribute("pageUtil", pageUtil);
				getRequest().setAttribute("list", pageUtil.getList());
				getRequest().setAttribute("table", "1");
				getRequest().setAttribute("tj", bean.tjQdcb(insertTime));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "loadQdcb";
	}
	/**
	 * 导出渠道成本（安卓）
	 */

    
	public String iportQdcbTable(){
		try {
			String name = QwyUtil.fmyyyyMMddHHmmss3.format(new Date())+"_qdcb";
			PageUtil<BackStatsOperateDay> pageUtil = new PageUtil<BackStatsOperateDay>();
				pageUtil.setCurrentPage(currentPage);
				pageUtil.setPageSize(999999);
				HSSFWorkbook wb = new HSSFWorkbook();  
			    HSSFSheet sheet = wb.createSheet("Android渠道统计汇总表");  
			    HSSFRow row = sheet.createRow((int) 1); 
			    HSSFCellStyle style = wb.createCellStyle();  
			    style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式 
			    row = sheet.createRow(0);
			    row.createCell(0).setCellValue("日期");  
			    row.createCell(1).setCellValue("注册人数");  
			    row.createCell(2).setCellValue("投资人数");  
			    row.createCell(3).setCellValue("投资金额");  
			    row.createCell(4).setCellValue("首投人数");  
			    row.createCell(5).setCellValue("首投金额");  
		        Qdcb cn =  bean.tjQdcb(insertTime);
		        if(!QwyUtil.isNullAndEmpty(cn)){
		        	row = sheet.createRow(1);
		        	row.createCell(0).setCellValue("合计");  
		        	row.createCell(1).setCellValue(cn.getRegUsersCount());  
		        	row.createCell(2).setCellValue(cn.getInsUsersCount());  
		        	row.createCell(3).setCellValue(QwyUtil.calcNumber(cn.getInsMoney(),100, "/", 2)+"");  
		        	row.createCell(4).setCellValue(cn.getStrs());  
		        	row.createCell(5).setCellValue(QwyUtil.calcNumber(cn.getStje(),100, "/", 2)+"");  
		        }
			    pageUtil=bean.findQdcb(pageUtil,insertTime);
				  List list = pageUtil.getList();
				  BackStatsOperateDay report = null;
				  if(!QwyUtil.isNullAndEmpty(cn)){
				  for (int i = 0; i < list.size(); i++) {  
			        	row = sheet.createRow((int) i + 2);
			        	report = (BackStatsOperateDay)list.get(i);
			        	row.createCell(0).setCellValue(report.getDate()+"");
			        	row.createCell(1).setCellValue(report.getRegUserSum());
			        	row.createCell(2).setCellValue(report.getInvestUserSum());
			        	row.createCell(3).setCellValue(QwyUtil.calcNumber(report.getInvestCentSum(),100, "/", 2)+"");  
			        	row.createCell(4).setCellValue(report.getFirstInvestUserSum());  
			        	row.createCell(5).setCellValue(QwyUtil.calcNumber(report.getFirstInvestCentSum(),100, "/", 2)+"");  
				  }
				  }
				  String realPath = request.getServletContext().getRealPath("/report/qdtj.xls");
			        FileOutputStream fout = new FileOutputStream(realPath);  
			        wb.write(fout);
			        fout.close();
			        response.getWriter().write("/report/qdtj.xls");
			    
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public String loadQdtjDetails(){
		String json="";
		try {
			UsersAdmin users = (UsersAdmin)getRequest().getSession().getAttribute("usersAdmin");
			if(QwyUtil.isNullAndEmpty(users)){
				json = QwyUtil.getJSONString("err", "管理员未登录");
				QwyUtil.printJSON(getResponse(), json);
				//管理员没有登录;
				return null;
			}
//			String superName="0EA5D6BC23E8EEC78F62546B9F68BABFA96976B775889BA625DB6D764FD0DBD42A1C0F45F85B0DE8";
//			if(!superName.equals(users.getUsername())){
//				if(isExistsQX("单个渠道统计详情", users.getId())){
//					getRequest().setAttribute("err", "您没有操作该功能的权限!");
//					return "err";
//				}
//			}
			PageUtil<ChannelOperateDay> pageUtil = new PageUtil<ChannelOperateDay>();
			pageUtil.setCurrentPage(currentPage);
			pageUtil.setPageSize(pageSize);
			StringBuffer url = new StringBuffer();
			url.append(getRequest().getServletContext().getContextPath());
			url.append("/Product/Admin/activity!loadQdtjDetails.action?");
			if(!QwyUtil.isNullAndEmpty(insertTime)){
				url.append("&insertTime=");
				url.append(insertTime);
			}
			if(!QwyUtil.isNullAndEmpty(registChannel)){
				url.append("&registChannel=");
				url.append(registChannel);
			}
			pageUtil.setPageUrl(url.toString());
			
			PlatChannel platChannel=bean.loadPlatChannel(registChannel);
			getRequest().setAttribute("platChannel", platChannel);
			getRequest().setAttribute("insertTime", insertTime);
			pageUtil=bean.getQdtjDetail(pageUtil,registChannel,insertTime);
			if(!QwyUtil.isNullAndEmpty(pageUtil)&&!QwyUtil.isNullAndEmpty(pageUtil.getList())){
				getRequest().setAttribute("pageUtil", pageUtil);
				getRequest().setAttribute("list", pageUtil.getList());
				getRequest().setAttribute("table", "1");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "loadQdtjDetails";
	}
	
	/**
	 * 导出单个渠道统计详情（安卓）
	 */
	public String iportQdtjDetailTable(){
		List<JasperPrint> list = null;
		String name = QwyUtil.fmyyyyMMddHHmmss3.format(new Date())+"_qdtjDetails";
		try {
			String filePath = request.getServletContext().getRealPath("/WEB-INF/classes/qdtjDetails.jasper");
			System.out.println("iportTable报表路径: "+filePath);
			list=bean.getQdtjDetailJasperPrintList(registChannel, insertTime,filePath);
			doIreport(list, name);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	
	public String getInsertTime() {
		return insertTime;
	}
	public void setInsertTime(String insertTime) {
		this.insertTime = insertTime;
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

	public String getRegistChannel() {
		return registChannel;
	}

	public void setRegistChannel(String registChannel) {
		this.registChannel = registChannel;
	}


	
	
}
