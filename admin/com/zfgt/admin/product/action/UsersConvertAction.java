package com.zfgt.admin.product.action;

import java.io.FileOutputStream;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.struts2.config.Namespace;
import org.apache.struts2.config.ParentPackage;
import org.apache.struts2.config.Result;
import org.apache.struts2.config.Results;

import com.zfgt.admin.product.bean.UsersConvertBean;
import com.zfgt.common.action.BaseAction;
import com.zfgt.common.util.ObjectUtil;
import com.zfgt.common.util.PageUtil;
import com.zfgt.common.util.QwyUtil;
import com.zfgt.orm.BackStatsOperateDay;
import com.zfgt.orm.Users;
import com.zfgt.orm.UsersAdmin;
import com.zfgt.orm.UsersConvert;

import net.sf.jasperreports.engine.JasperPrint;

/**
 * 后台管理--注册人数;
 * 
 * @author qwy
 *
 * @createTime 2015-4-27下午3:51:34
 */
@SuppressWarnings("serial")
@ParentPackage("struts-default")
@Namespace("/Product/Admin")
@Results({ @Result(name = "loadUsersConvert", value = "/Product/Admin/operationManager/usersconvert.jsp"),
		@Result(name = "loadOperation", value = "/Product/Admin/operationManager/operation.jsp"),
		@Result(name = "userInfo", value = "/Product/Admin/operationManager/ydUserinfo.jsp"),
		@Result(name = "allMobile", value = "/Product/Admin/operationManager/mobileUserinfo.jsp"),
		@Result(name = "allUserInfo", value = "/Product/Admin/operationManager/mobileUserinfo2.jsp"),
		@Result(name = "err", value = "/Product/Admin/err.jsp") })
public class UsersConvertAction extends BaseAction {
	@Resource
	UsersConvertBean bean;
	private Integer currentPage = 1;
	private Integer pageSize = 50;
	private String insertTime;
	private String registPlatform; // 注册平台
	private String type; // 是否投资
	private String bindBank; // 是否绑定银行卡

	/**
	 * 注册人转换率
	 * 
	 * @return
	 */
	public String loadUsersConvert() {
		String json = "";
		try {
			UsersAdmin users = (UsersAdmin) getRequest().getSession().getAttribute("usersAdmin");
			if (QwyUtil.isNullAndEmpty(users)) {
				json = QwyUtil.getJSONString("err", "管理员未登录");
				QwyUtil.printJSON(getResponse(), json);
				// 管理员没有登录;
				return null;
			}
			String superName = "0EA5D6BC23E8EEC78F62546B9F68BABFA96976B775889BA625DB6D764FD0DBD42A1C0F45F85B0DE8";
			if (!superName.equals(users.getUsername())) {
				if (isExistsQX("用户转换数据", users.getId())) {
					getRequest().setAttribute("err", "您没有操作该功能的权限!");
					return "err";
				}
			}
			PageUtil<UsersConvert> pageUtil = new PageUtil<UsersConvert>();
			pageUtil.setCurrentPage(currentPage);
			pageUtil.setPageSize(pageSize);
			StringBuffer url = new StringBuffer();
			url.append(getRequest().getServletContext().getContextPath());
			url.append("/Product/Admin/usersConvert!loadUsersConvert.action?");
			if (!QwyUtil.isNullAndEmpty(insertTime)) {
				url.append("&insertTime=");
				url.append(insertTime);
			}
			if (!QwyUtil.isNullAndEmpty(registPlatform)) {
				url.append("&registPlatform=");
				url.append(registPlatform);
			}
			pageUtil.setPageUrl(url.toString());
			bean.findUsersCountByDate(pageUtil, insertTime, registPlatform);
			getRequest().setAttribute("insertTime", insertTime);
			getRequest().setAttribute("registPlatform", registPlatform);
			if (!QwyUtil.isNullAndEmpty(pageUtil)) {
				getRequest().setAttribute("pageUtil", pageUtil);
				getRequest().setAttribute("list", pageUtil.getList());
				if (pageUtil.getCount() > 0)
					getRequest().setAttribute("table", "1");
				getRequest().setAttribute("tj", bean.tjUsersConvert(insertTime, registPlatform));
				return "loadUsersConvert";
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "loadUsersConvert";
	}

	/**
	 * 导出产品表格
	 */
	public String iportTable() {
		try {

			PageUtil<UsersConvert> pageUtil = new PageUtil<UsersConvert>();
			pageUtil.setCurrentPage(currentPage);
			pageUtil.setPageSize(999999);
			bean.findUsersCountByDate(pageUtil, insertTime, registPlatform);
			HSSFWorkbook wb = new HSSFWorkbook();
			HSSFSheet sheet = wb.createSheet("用户转换数据报表");
			HSSFRow row = sheet.createRow((int) 0);
			HSSFCellStyle style = wb.createCellStyle();
			style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式

			row = sheet.createRow(0);
			row.createCell(0).setCellValue("日期");
			row.createCell(1).setCellValue("注册人数");
			row.createCell(2).setCellValue("绑定人数");
			row.createCell(3).setCellValue("投资人数");
			row.createCell(4).setCellValue("投资金额(元)");
			row.createCell(5).setCellValue("人均投资金额(元)");
			row.createCell(6).setCellValue("投资次数");
			row.createCell(7).setCellValue("复投次数");
			row.createCell(8).setCellValue("注册投资人数");
			row.createCell(9).setCellValue("注册投资金额(元)");
			row.createCell(10).setCellValue("转换率(%)");
			row.createCell(11).setCellValue("首投人数");
			row.createCell(12).setCellValue("复投人数");
			row.createCell(13).setCellValue("复投率(%)");
			UsersConvert qj = bean.tjUsersConvert(insertTime, registPlatform);
			row = sheet.createRow(1);
			row.createCell(0).setCellValue("合计");
			row.createCell(1).setCellValue(qj.getReuserscount());
			row.createCell(2).setCellValue(qj.getBindcount());
			row.createCell(3).setCellValue(qj.getInsusercount());
			row.createCell(4).setCellValue(!QwyUtil.isNullAndEmpty(qj.getAllcopies())
					? QwyUtil.calcNumber(qj.getAllcopies(), 100, "/", 2) + "" : "0");
			row.createCell(5).setCellValue(
					!QwyUtil.isNullAndEmpty(qj.getRjtz()) ? QwyUtil.calcNumber(qj.getRjtz(), 100, "/", 2) + "" : "0");
			row.createCell(6).setCellValue(qj.getInscount());
			row.createCell(7).setCellValue(qj.getFtinscs());
			row.createCell(8).setCellValue(qj.getReginscount());
			row.createCell(9).setCellValue(!QwyUtil.isNullAndEmpty(qj.getRegcopies())
					? QwyUtil.calcNumber(qj.getRegcopies(), 100, "/", 2) + "" : "0");
			row.createCell(10).setCellValue(qj.getZhl());
			row.createCell(11).setCellValue(qj.getStinsrs());
			row.createCell(12).setCellValue(qj.getFtinsrs());
			row.createCell(13).setCellValue(qj.getFtl());
			List list = pageUtil.getList();
			UsersConvert report = null;
			for (int i = 0; i < list.size(); i++) {
				row = sheet.createRow((int) i + 2);
				report = (UsersConvert) list.get(i);
				row.createCell(0).setCellValue(report.getDate());
				row.createCell(1).setCellValue(report.getReuserscount());
				row.createCell(2).setCellValue(report.getBindcount());
				row.createCell(3).setCellValue(report.getInsusercount());
				row.createCell(4).setCellValue(report.getAllcopies());
				row.createCell(5).setCellValue(report.getRjtz());
				row.createCell(6).setCellValue(report.getInscount());
				row.createCell(7).setCellValue(report.getFtinscs());
				row.createCell(8).setCellValue(report.getReginscount());
				row.createCell(9).setCellValue(report.getRegcopies());
				row.createCell(10).setCellValue(report.getZhl());
				row.createCell(11).setCellValue(report.getStinsrs());
				row.createCell(12).setCellValue(report.getFtinsrs());
				row.createCell(13).setCellValue(report.getFtl());
			}

			String realPath = request.getServletContext().getRealPath("/report/UsersConvert.xls");
			FileOutputStream fout = new FileOutputStream(realPath);
			wb.write(fout);
			fout.close();
			response.getWriter().write("/report/UsersConvert.xls");

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 运营数据
	 * 
	 * @return
	 */
	public String loadOperation() {
		String json = "";
		try {
			UsersAdmin users = (UsersAdmin) getRequest().getSession().getAttribute("usersAdmin");
			if (QwyUtil.isNullAndEmpty(users)) {
				json = QwyUtil.getJSONString("err", "管理员未登录");
				QwyUtil.printJSON(getResponse(), json);
				// 管理员没有登录;
				return null;
			}
			String superName = "0EA5D6BC23E8EEC78F62546B9F68BABFA96976B775889BA625DB6D764FD0DBD42A1C0F45F85B0DE8";
			if (!superName.equals(users.getUsername())) {
				if (isExistsQX("运营数据", users.getId())) {
					getRequest().setAttribute("err", "您没有操作该功能的权限!");
					return "err";
				}
			}
			PageUtil<BackStatsOperateDay> pageUtil = new PageUtil<BackStatsOperateDay>();
			pageUtil.setCurrentPage(currentPage);
			pageUtil.setPageSize(pageSize);
			StringBuffer url = new StringBuffer();
			url.append(getRequest().getServletContext().getContextPath());
			url.append("/Product/Admin/usersConvert!loadOperation.action?");
			if (!QwyUtil.isNullAndEmpty(insertTime)) {
				url.append("&insertTime=");
				url.append(insertTime);
			}
			pageUtil.setPageUrl(url.toString());
			pageUtil = bean.findOperationByDate(pageUtil, registPlatform, insertTime);
			getRequest().setAttribute("insertTime", insertTime);
			getRequest().setAttribute("registPlatform", registPlatform);

			if (!QwyUtil.isNullAndEmpty(pageUtil)) {
				getRequest().setAttribute("pageUtil", pageUtil);
				if (pageUtil.getCount() > 0)
					getRequest().setAttribute("table", "1");

				// BackStatsOperateDay totalPageSum =
				// totalPageOperate(pageUtil.getList());
				// getRequest().setAttribute("totalPageSum", totalPageSum);

				BackStatsOperateDay totalAllSum = totalAllOperate();
				getRequest().setAttribute("totalAllSum", totalAllSum);
				return "loadOperation";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "loadOperation";
	}

	/**
	 * 本页合计
	 * 
	 * @return
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 */
	private BackStatsOperateDay totalPageOperate(List<BackStatsOperateDay> list) {
		BackStatsOperateDay returnBackStatsOperateDay = new BackStatsOperateDay();

		try {
			for (BackStatsOperateDay backStatsOperateDay : list) {
				ObjectUtil.accumulationObject(returnBackStatsOperateDay, backStatsOperateDay);
			}
			// 人均投资
			returnBackStatsOperateDay.setAvgInvestCentSum();
			// 新增二次投资率
			returnBackStatsOperateDay.setNewTwoInvestRate();
			// 复投
			returnBackStatsOperateDay.setReInvestRate();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return returnBackStatsOperateDay;
	}

	/**
	 * 全部合计
	 * 
	 * @return
	 */
	private BackStatsOperateDay totalAllOperate() {
		BackStatsOperateDay returnBackStatsOperateDay = new BackStatsOperateDay();
		try {

			for (Map<String, Object> map : bean.findOperationTotal(registPlatform, insertTime)) {
				returnBackStatsOperateDay = (BackStatsOperateDay) ObjectUtil.mapToObject(map,
						returnBackStatsOperateDay);
			}
			// 人均投资
			returnBackStatsOperateDay.setAvgInvestCentSum();
			// 新增二次投资率
			returnBackStatsOperateDay.setNewTwoInvestRate();
			// 复投
			returnBackStatsOperateDay.setReInvestRate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return returnBackStatsOperateDay;
	}

	/**
	 * 导出运营表格
	 */
	public String iportOperationTable() {
		List<JasperPrint> list = null;
		String name = QwyUtil.fmyyyyMMddHHmmss3.format(new Date()) + "_operation";
		try {
			String filePath = request.getServletContext().getRealPath("/WEB-INF/classes/operation.jasper");
			System.out.println("iportTable报表路径: " + filePath);
			list = bean.getOperationJasperPrintList(insertTime, registPlatform, filePath);
			doIreport(list, name);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 移动用户信息
	 * 
	 * @return
	 */
	public String userInfo() {
		PageUtil<Users> pageUtil = new PageUtil<Users>();
		pageUtil.setCurrentPage(currentPage);
		pageUtil.setPageSize(1000);
		pageUtil = bean.findByUsers(pageUtil);
		getRequest().setAttribute("pageUtil", pageUtil);
		getRequest().setAttribute("list", pageUtil.getList());
		return "userInfo";

	}

	/**
	 * 所有手机号码
	 * 
	 * @return
	 */
	public String allMobile() {
		List<String> list = bean.findByUsers(insertTime, type, bindBank);
		getRequest().setAttribute("list", list);
		return "allMobile";

	}

	/**
	 * 信息
	 * 
	 * @return
	 */
	public String allUserInfo() {
		List<Object[]> list = bean.findByUsersInfo(insertTime, type, bindBank);
		getRequest().setAttribute("list", list);
		return "allUserInfo";

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

	public String getRegistPlatform() {
		return registPlatform;
	}

	public void setRegistPlatform(String registPlatform) {
		this.registPlatform = registPlatform;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getBindBank() {
		return bindBank;
	}

	public void setBindBank(String bindBank) {
		this.bindBank = bindBank;
	}

}
