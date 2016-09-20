package com.zfgt.admin.product.action;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import net.sf.jasperreports.engine.JasperPrint;
import net.sf.json.JSONObject;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.struts2.config.Namespace;
import org.apache.struts2.config.ParentPackage;
import org.apache.struts2.config.Result;
import org.apache.struts2.config.Results;

import com.zfgt.account.bean.MyAccountBean;
import com.zfgt.account.bean.UserRechargeBean;
import com.zfgt.admin.product.bean.RechargeBean;
import com.zfgt.common.action.BaseAction;
import com.zfgt.common.bean.LianLianPayBean;
import com.zfgt.common.bean.YiBaoPayBean;
import com.zfgt.common.util.ObjectUtil;
import com.zfgt.common.util.PageUtil;
import com.zfgt.common.util.QwyUtil;
import com.zfgt.common.yeepay.PaymentForOnlineService;
import com.zfgt.common.yeepay.QueryResult;
import com.zfgt.login.bean.RegisterUserBean;
import com.zfgt.orm.BackStatsOperateDay;
import com.zfgt.orm.CapitalRecord;
import com.zfgt.orm.CzRecord;
import com.zfgt.orm.DateMoney;
import com.zfgt.orm.RootTxRecord;
import com.zfgt.orm.UserCzTx;
import com.zfgt.orm.Users;
import com.zfgt.orm.UsersAdmin;
import com.zfgt.orm.UsersConvert;
import com.zfgt.orm.WeekLeftMoney;

/**
 * 后台管理--充值
 * 
 * @author qwy
 *
 * @createTime 2015-4-27下午3:51:34
 */
@SuppressWarnings("serial")
@ParentPackage("struts-default")
@Namespace("/Product/Admin")
// 发布产品页面
@Results({ @Result(name = "czrecord", value = "/Product/Admin/fundsManager/czRecord.jsp"), @Result(name = "platCzMoney", value = "/Product/Admin/fundsManager/platCzMoney.jsp"),
		@Result(name = "platTxMoney", value = "/Product/Admin/fundsManager/platTxMoney.jsp"), @Result(name = "platLeftMoney", value = "/Product/Admin/fundsManager/platLeftMoney.jsp"),
		@Result(name = "loadUserCzTx", value = "/Product/Admin/operationManager/userCzTx.jsp"), @Result(name = "weekLeftMoney", value = "/Product/Admin/fundsManager/weekLeftMoney.jsp"),
		@Result(name = "capitalRecord", value = "/Product/Admin/operationManager/capitalRecord.jsp"), @Result(name = "rootTxRecord", value = "/Product/Admin/fundsManager/rootTxRecord.jsp"),
		@Result(name = "err", value = "/Product/Admin/err.jsp"),@Result(name = "weekRemainMoney", value = "/Product/Admin/fundsManager/weekRemainMoney.jsp")})
public class RechargeAction extends BaseAction {
	private String username;
	private double rechargeMoney = 0;
	@Resource
	private RechargeBean bean;
	@Resource
	private UserRechargeBean userRechargeBean;
	@Resource
	RegisterUserBean registerUserBean;
	@Resource
	MyAccountBean accountBean;
	@Resource
	YiBaoPayBean yiBaoPayBean;
	@Resource
	LianLianPayBean lianLianPayBean;
	private Integer currentPage = 1;
	private Integer pageSize = 50;
	private String status = "all";
	private String name = "";
	private String insertTime;
	private String requestId;
	private String registPlatform;// 注册
	private String cause;// 后台充值原因
	private String money;
	private String note;
	private String type;

	/**
	 * 管理员后台充值
	 * 
	 * @return
	 */
	public String rechargeMoney() {
		String json = "";
		try {
			UsersAdmin users = (UsersAdmin) getRequest().getSession().getAttribute("usersAdmin");
			if (QwyUtil.isNullAndEmpty(users)) {
				json = QwyUtil.getJSONString("err", "管理员未登录");
				QwyUtil.printJSON(getResponse(), json);
				// 管理员没有登录;
				return null;
			}
			Users us = bean.getUsersByUsername(username);
			if (QwyUtil.isNullAndEmpty(us)) {
				// 没有找到这个用户;
				json = QwyUtil.getJSONString("err", "用户名不存在");
				QwyUtil.printJSON(getResponse(), json);
				return null;
			}
			String superName = "0EA5D6BC23E8EEC78F62546B9F68BABFA96976B775889BA625DB6D764FD0DBD42A1C0F45F85B0DE8";
			if (!superName.equals(users.getUsername())) {
				// 没有找到这个用户;
				json = QwyUtil.getJSONString("err", "您没有充值权限!");
				QwyUtil.printJSON(getResponse(), json);
				return null;
			}
			String note = "管理员手动为用户充值,管理员编号: " + users.getId();
			CzRecord cz = userRechargeBean.addCzRecord2(us.getId(), rechargeMoney * 100, "", "", "", "内部人员充值", note, cause);
			boolean isSend = bean.usreRecharge(us.getId(), rechargeMoney * 100, "cz", "管理员后台充值", note);
			if (isSend) {
				cz.setStatus("1");
				System.out.println("充值成功___");
				// 发送成功;
				json = QwyUtil.getJSONString("ok", "充值成功");
			} else {
				cz.setStatus("2");
				json = QwyUtil.getJSONString("err", "充值失败");
			}
			cz.setCheckTime(new Date());
			userRechargeBean.saveOrUpdate(cz);
		} catch (Exception e) {
			log.error("操作异常: ", e);
			json = QwyUtil.getJSONString("err", "充值异常");
		}
		try {
			QwyUtil.printJSON(getResponse(), json);
		} catch (IOException e) {
			log.error("操作异常: ", e);
		}
		return null;
	}

	/**
	 * 充值记录
	 * 
	 * @return
	 */
	public String rechargeMoneyRecord() {
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
				if (isExistsQX("充值记录", users.getId())) {
					getRequest().setAttribute("err", "您没有操作该功能的权限!");
					return "err";
				}
			}
			// 根据状态来加载提现的记录;
			PageUtil<CzRecord> pageUtil = new PageUtil<CzRecord>();
			pageUtil.setCurrentPage(currentPage);
			pageUtil.setPageSize(pageSize);
			StringBuffer url = new StringBuffer();
			url.append(getRequest().getServletContext().getContextPath());
			url.append("/Product/Admin/recharge!rechargeMoneyRecord.action?name=" + name + "&status=" + status);
			if (!QwyUtil.isNullAndEmpty(insertTime)) {
				url.append("&insertTime=");
				url.append(insertTime);
			}
			pageUtil.setPageUrl(url.toString());
			pageUtil = bean.loadCzRecord(pageUtil, status, name, insertTime);
			if (!QwyUtil.isNullAndEmpty(pageUtil)) {
				getRequest().setAttribute("pageUtil", pageUtil);
				getRequest().setAttribute("name", name);
				getRequest().setAttribute("insertTime", insertTime);
				getRequest().setAttribute("czRecordList", pageUtil.getList());
				return "czrecord";
			}
		} catch (Exception e) {
			log.error("操作异常: ", e);
			json = QwyUtil.getJSONString("err", "查询记录异常");
		}
		return null;
	}

	/**
	 * 充值统计
	 * 
	 * @return
	 */
	public String platCzMoney() {
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
				if (isExistsQX("充值记录统计", users.getId())) {
					getRequest().setAttribute("err", "您没有操作该功能的权限!");
					return "err";
				}
			}
			PageUtil<DateMoney> pageUtil = new PageUtil<DateMoney>();
			pageUtil.setCurrentPage(currentPage);
			pageUtil.setPageSize(31);
			StringBuffer url = new StringBuffer();
			url.append(getRequest().getServletContext().getContextPath());
			url.append("/Product/Admin/recharge!platCzMoney.action?");
			if (!QwyUtil.isNullAndEmpty(insertTime)) {
				url.append("&insertTime=");
				url.append(insertTime);
			}
			pageUtil.setPageUrl(url.toString());
			pageUtil = bean.findCzRecordGroupByDate(pageUtil, insertTime);
			getRequest().setAttribute("insertTime", insertTime);
			if (!QwyUtil.isNullAndEmpty(pageUtil)) {
				getRequest().setAttribute("pageUtil", pageUtil);
				getRequest().setAttribute("list", pageUtil.getList());
				return "platCzMoney";
			}
		} catch (Exception e) {
			log.error("操作异常: ", e);
			json = QwyUtil.getJSONString("err", "查询记录异常");
		}
		return "platCzMoney";
	}

	/**
	 * 提现统计
	 * 
	 * @return
	 */
	public String platTxMoney() {
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
				if (isExistsQX("提现记录统计", users.getId())) {
					getRequest().setAttribute("err", "您没有操作该功能的权限!");
					return "err";
				}
			}
			PageUtil<DateMoney> pageUtil = new PageUtil<DateMoney>();
			pageUtil.setCurrentPage(currentPage);
			pageUtil.setPageSize(31);
			StringBuffer url = new StringBuffer();
			url.append(getRequest().getServletContext().getContextPath());
			url.append("/Product/Admin/recharge!platTxMoney.action?");
			if (!QwyUtil.isNullAndEmpty(insertTime)) {
				url.append("&insertTime=");
				url.append(insertTime);
			}
			pageUtil.setPageUrl(url.toString());
			pageUtil = bean.findTxRecordGroupByDate(pageUtil, insertTime);
			getRequest().setAttribute("insertTime", insertTime);
			if (!QwyUtil.isNullAndEmpty(pageUtil)) {
				getRequest().setAttribute("pageUtil", pageUtil);
				getRequest().setAttribute("list", pageUtil.getList());
				return "platTxMoney";
			}
		} catch (Exception e) {
			log.error("操作异常: ", e);
			json = QwyUtil.getJSONString("err", "查询记录异常");
		}
		return "platTxMoney";
	}

	/**
	 * 资金流水
	 * 
	 * @return
	 */
	public String CapitalRecord() {
		String json = "";
		try {
			UsersAdmin users = (UsersAdmin) getRequest().getSession().getAttribute("usersAdmin");
			if (QwyUtil.isNullAndEmpty(users)) {
				json = QwyUtil.getJSONString("err", "管理员未登录");
				QwyUtil.printJSON(getResponse(), json);
				// 管理员没有登录;
				return null;
			}
			// String
			// superName="0EA5D6BC23E8EEC78F62546B9F68BABFA96976B775889BA625DB6D764FD0DBD42A1C0F45F85B0DE8";
			// if(!superName.equals(users.getUsername())){
			// if(isExistsQX("资金流水", users.getId())){
			// getRequest().setAttribute("err", "您没有操作该功能的权限!");
			// return "err";
			// }
			// }
			PageUtil<CapitalRecord> pageUtil = new PageUtil<CapitalRecord>();
			pageUtil.setCurrentPage(currentPage);
			pageUtil.setPageSize(31);
			StringBuffer url = new StringBuffer();
			url.append(getRequest().getServletContext().getContextPath());
			url.append("/Product/Admin/recharge!CapitalRecord.action?");
			if (!QwyUtil.isNullAndEmpty(insertTime)) {
				url.append("&insertTime=");
				url.append(insertTime);
			}
			pageUtil.setPageUrl(url.toString());
			pageUtil = bean.findCapitalRecordGroupByDate(pageUtil, insertTime);
			getRequest().setAttribute("insertTime", insertTime);
			if (!QwyUtil.isNullAndEmpty(pageUtil)) {
				getRequest().setAttribute("pageUtil", pageUtil);
				getRequest().setAttribute("tj", bean.tjCapital(insertTime));
				return "capitalRecord";
			}
		} catch (Exception e) {
			log.error("操作异常: ", e);
			json = QwyUtil.getJSONString("err", "查询记录异常");
		}
		return "capitalRecord";
	}

	/**
	 * 提现查询
	 * 
	 * @return
	 */
	public String TxMoneyArrive() {
		String json = "";
		try {
			UsersAdmin ua = (UsersAdmin) getRequest().getSession().getAttribute("usersAdmin");
			if (QwyUtil.isNullAndEmpty(ua)) {
				json = QwyUtil.getJSONString("err", "管理员未登录");
				QwyUtil.printJSON(getResponse(), json);
				// 管理员没有登录;
				return null;
			}
			// 得到用户数据
			Users users = registerUserBean.getUsersByUsername(name);

			json = yiBaoPayBean.withdrawQuery(requestId);
			QwyUtil.printJSON(getResponse(), json);
			return null;
		} catch (Exception e) {
			log.error("操作异常: ", e);
			json = QwyUtil.getJSONString("err", "查询记录异常");
		}
		return null;
	}

	/**
	 * 充值查询（易宝）
	 * 
	 * @return
	 */
	public String CzMoneyArrive() {
		String json = "";
		try {
			UsersAdmin ua = (UsersAdmin) getRequest().getSession().getAttribute("usersAdmin");
			if (QwyUtil.isNullAndEmpty(ua)) {
				json = QwyUtil.getJSONString("err", "管理员未登录");
				QwyUtil.printJSON(getResponse(), json);
				// 管理员没有登录;
				return null;
			}
			if (!"1".equals(type)) {
				// 得到用户数据
				json = yiBaoPayBean.queryPay(requestId);
				JSONObject jsonObject = JSONObject.fromObject(json);
				if (!QwyUtil.isNullAndEmpty(jsonObject)) {
					if (!QwyUtil.isNullAndEmpty(jsonObject.get("end"))) {
						json = QwyUtil.getJSONString("error", jsonObject.get("message") + "");
						QwyUtil.printJSON(getResponse(), json);
						return null;
					}
					if (!QwyUtil.isNullAndEmpty(jsonObject.get("error_code"))) {
						json = QwyUtil.getJSONString("error", jsonObject.get("error_msg") + "");
						QwyUtil.printJSON(getResponse(), json);
						return null;
					}
					Object st = jsonObject.get("status");
					if (!QwyUtil.isNullAndEmpty(st)) {
						String status = st.toString();
						if ("0".equals(status)) {
							json = QwyUtil.getJSONString("error", "待付款");
						} else if ("1".equals(status)) {
							json = QwyUtil.getJSONString("ok", "充值成功");
						} else if ("2".equals(status)) {
							json = QwyUtil.getJSONString("ok", "订单已取消");
						} else if ("3".equals(status)) {
							json = QwyUtil.getJSONString("error", "阻断交易，该交易存在高风险");
						}
						QwyUtil.printJSON(getResponse(), json);
						return null;
					}
					QwyUtil.printJSON(getResponse(), json);
					return null;
				}
			} else {
				QueryResult queryResult = PaymentForOnlineService.queryByOrder(requestId);
				log.info("进入网银后台线程----充值结算结果" + queryResult);
				if (queryResult.getR1_Code().equals("1")) {
					if (queryResult.getRb_PayStatus().equalsIgnoreCase("INIT")) {
						/*
						 * CzRecord
						 * record=bean.getCzRecordByOrderId(czRecord.getOrderId(
						 * )); if(record.getStatus().equals("0")){
						 * record.setStatus("2");
						 * record.setYbOrderId(queryResult.getR2_TrxId());
						 * record.setCheckTime(new Date());
						 * record.setNote("订单未支付"); bean.updateRecord(record);
						 * continue; }
						 */
						json = QwyUtil.getJSONString("error", "订单未支付");
						QwyUtil.printJSON(getResponse(), json);
						return null;
					} else if (queryResult.getRb_PayStatus().equalsIgnoreCase("CANCELED")) {
						json = QwyUtil.getJSONString("error", "订单被取消");
						QwyUtil.printJSON(getResponse(), json);
						return null;
					} else if (queryResult.getRb_PayStatus().equalsIgnoreCase("SUCCESS")) {
						json = QwyUtil.getJSONString("error", "充值成功");
						QwyUtil.printJSON(getResponse(), json);
						return null;
					}
				} else if (queryResult.getR1_Code().equals("50")) {
					json = QwyUtil.getJSONString("error", "订单不存在");
					QwyUtil.printJSON(getResponse(), json);
					return null;
				}

			}
		} catch (Exception e) {
			log.error("操作异常: ", e);
		}
		json = QwyUtil.getJSONString("err", "查询记录异常");
		try {
			QwyUtil.printJSON(getResponse(), json);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			log.error("操作异常: ", e);
		}
		return null;
	}

	/**
	 * 当前平台可用余额
	 * 
	 * @return
	 */
	public String platLeftMoney() {
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
				if (isExistsQX("平台可用余额", users.getId())) {
					getRequest().setAttribute("err", "您没有操作该功能的权限!");
					return "err";
				}
			}
			String leftMoney = bean.findAllLeftMoney();
			getRequest().setAttribute("leftMoney", leftMoney);
			String inShiftMoney = bean.findInShiftMoney();
			getRequest().setAttribute("inShiftMoney", inShiftMoney);
			String outShiftMoney = bean.findOutShiftMoney();
			getRequest().setAttribute("outShiftMoney", outShiftMoney);
			double shiftMoney = Double.valueOf(inShiftMoney) + Double.valueOf(outShiftMoney);
			getRequest().setAttribute("shiftMoney", shiftMoney);
		} catch (Exception e) {
			log.error("操作异常: ", e);
			json = QwyUtil.getJSONString("err", "查询记录异常");
		}
		return "platLeftMoney";
	}

	/**
	 * 一周可用余额
	 * 
	 * @return
	 */
	public String weekLeftMoney() {
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
				if (isExistsQX("一周不可动金额", users.getId())) {
					getRequest().setAttribute("err", "您没有操作该功能的权限!");
					return "err";
				}
			}
			List<WeekLeftMoney> list = bean.findWeekLeftMoneys();
			// String allCzMoney=bean.allCzRecord();
			// String allDay=bean.allDay();
			// String leftMoney=bean.findAllLeftMoney();
			// String allLeftMoney=bean.allLeftMoney();
			// String txMaxCount=bean.txMaxCount();
			// getRequest().setAttribute("allDay", allDay);
			// getRequest().setAttribute("allCzMoney", allCzMoney);
			// getRequest().setAttribute("leftMoney", leftMoney);
			// getRequest().setAttribute("allLeftMoney", allLeftMoney);
			// getRequest().setAttribute("txMaxCount", txMaxCount);
			getRequest().setAttribute("list", list);
		} catch (Exception e) {
			log.error("操作异常: ", e);
			json = QwyUtil.getJSONString("err", "查询记录异常");
		}
		return "weekLeftMoney";
	}
	
	/**
	 * 一周预留资金
	 * 
	 * @return
	 */
	public String weekRemainMoney() {
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
				if (isExistsQX("预留金额", users.getId())) {
					getRequest().setAttribute("err", "您没有操作该功能的权限!");
					return "err";
				}
			}
			List<WeekLeftMoney> list = bean.findWeekRemainMoneys();
			// String allCzMoney=bean.allCzRecord();
			// String allDay=bean.allDay();
			// String leftMoney=bean.findAllLeftMoney();
			// String allLeftMoney=bean.allLeftMoney();
			// String txMaxCount=bean.txMaxCount();
			// getRequest().setAttribute("allDay", allDay);
			// getRequest().setAttribute("allCzMoney", allCzMoney);
			// getRequest().setAttribute("leftMoney", leftMoney);
			// getRequest().setAttribute("allLeftMoney", allLeftMoney);
			// getRequest().setAttribute("txMaxCount", txMaxCount);
			getRequest().setAttribute("list", list);
		} catch (Exception e) {
			log.error("操作异常: ", e);
			json = QwyUtil.getJSONString("err", "查询记录异常");
		}
		return "weekRemainMoney";
	}
	

	/**
	 * 导出喵币报表
	 * 
	 * @return
	 */
	public String exportWeekLeftMoney() {

		try {
			List<WeekLeftMoney> list = bean.findWeekLeftMoneys();
			HSSFWorkbook wb = new HSSFWorkbook();
			HSSFSheet sheet = wb.createSheet("一周不可动金额");
			HSSFRow row = sheet.createRow((int) 0);
			HSSFCellStyle style = wb.createCellStyle();
			style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式

			HSSFCell cell = row.createCell(0);
			cell.setCellValue("日期");
			cell.setCellStyle(style);
			cell = row.createCell(1);
			cell.setCellValue("预期充值金额手续费");
			cell.setCellStyle(style);
			cell = row.createCell(2);
			cell.setCellValue("预期还本付息金额");
			cell.setCellStyle(style);
			cell = row.createCell(3);
			cell.setCellValue("账户余额总和");
			cell.setCellStyle(style);
			cell = row.createCell(4);
			cell.setCellValue("预计提现手续费");
			cell.setCellStyle(style);
			cell = row.createCell(5);

			cell.setCellValue("合计");
			cell.setCellStyle(style);
			WeekLeftMoney report = null;
			for (int i = 0; i < list.size(); i++) {
				row = sheet.createRow((int) i + 1);
				report = (WeekLeftMoney) list.get(i);
				row.createCell(0).setCellValue(report.getDate());
				// row.createCell(1).setCellValue(report.getInsertTime());
				row.createCell(1).setCellValue(QwyUtil.calcNumber(report.getYjczsxf(), 1, "/", 2) + "");
				row.createCell(2).setCellValue(QwyUtil.calcNumber(report.getDqhbfxje(), 1, "/", 2) + "");
				row.createCell(3).setCellValue(QwyUtil.calcNumber(report.getAllAccountLeftMoney(), 1, "/", 2) + "");
				row.createCell(4).setCellValue(QwyUtil.calcNumber(report.getYjtxsxf(), 1, "/", 2) + "");
				row.createCell(5).setCellValue(QwyUtil.calcNumber(report.getSum(), 1, "/", 2) + "");

			}
			String realPath = request.getServletContext().getRealPath("/report/weekLeftMoney.xls");
			FileOutputStream fout = new FileOutputStream(realPath);
			wb.write(fout);
			fout.close();
			response.getWriter().write("/report/weekLeftMoney.xls");

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 充值提现记录
	 * 
	 * @return
	 */
	public String loadUserCzTx() {
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
				if (isExistsQX("充值提现数据报表", users.getId())) {
					getRequest().setAttribute("err", "您没有操作该功能的权限!");
					return "err";
				}
			}
			PageUtil<BackStatsOperateDay> pageUtil = new PageUtil<BackStatsOperateDay>();
			pageUtil.setCurrentPage(currentPage);
			pageUtil.setPageSize(pageSize);
			StringBuffer url = new StringBuffer();
			url.append(getRequest().getServletContext().getContextPath());
			url.append("/Product/Admin/recharge!loadUserCzTx.action?");
			if (!QwyUtil.isNullAndEmpty(insertTime)) {
				url.append("&insertTime=");
				url.append(insertTime);
			}

			if (!QwyUtil.isNullAndEmpty(registPlatform)) {
				url.append("&registPlatform=");
				url.append(registPlatform);
			}

			pageUtil.setPageUrl(url.toString());
			pageUtil = bean.findUserCzTx(pageUtil, insertTime, registPlatform);
			getRequest().setAttribute("insertTime", insertTime);
			getRequest().setAttribute("registPlatform", registPlatform);
			if (!QwyUtil.isNullAndEmpty(pageUtil)) {
				getRequest().setAttribute("pageUtil", pageUtil);
				if (pageUtil.getCount() > 0)
					getRequest().setAttribute("table", "1");
				getRequest().setAttribute("tj", totalAllCzTx());
				return "loadUserCzTx";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "loadUsersConvert";
	}

	/**
	 * 全部合计
	 * 
	 * @return
	 */
	private BackStatsOperateDay totalAllCzTx() {
		BackStatsOperateDay returnBackStatsOperateDay = new BackStatsOperateDay();
		try {

			for (Map<String, Object> map : bean.findCzTxTotal(registPlatform, insertTime)) {
				returnBackStatsOperateDay = (BackStatsOperateDay) ObjectUtil.mapToObject(map, returnBackStatsOperateDay);
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
	 * 导出产品表格
	 */
	public String iportTable() {
		try {
			PageUtil<UserCzTx> pageUtil = new PageUtil<UserCzTx>();
			pageUtil.setCurrentPage(currentPage);
			pageUtil.setPageSize(999999);
			bean.findUserCzTx(pageUtil, insertTime, registPlatform);
			HSSFWorkbook wb = new HSSFWorkbook();
			HSSFSheet sheet = wb.createSheet("充值提现数据报表");
			HSSFRow row = sheet.createRow((int) 0);
			HSSFCellStyle style = wb.createCellStyle();
			style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式

			row = sheet.createRow(0);
			row.createCell(0).setCellValue("日期");
			row.createCell(1).setCellValue("充值金额");
			row.createCell(2).setCellValue("充值次数");
			row.createCell(3).setCellValue("提现金额");
			row.createCell(4).setCellValue("提现次数");
			row.createCell(5).setCellValue("成功提现金额");
			row.createCell(6).setCellValue("成功提现次数");

			row.createCell(7).setCellValue("当日成功提现金额");
			row.createCell(8).setCellValue("当日成功提现次数");
			row.createCell(9).setCellValue("当天提现没到账的金额");
			row.createCell(10).setCellValue("当天提现未到账的次数");
			row.createCell(11).setCellValue("昨天提现今天到账金额");
			row.createCell(12).setCellValue("昨天提现今天到账次数");
			row.createCell(13).setCellValue("前天及更早提现但今天到账金额");
			row.createCell(14).setCellValue("前天及更早提现但今天到账次数");

			 row.createCell(15).setCellValue("非今天提现但今天到账金额");
			 row.createCell(16).setCellValue("非今天提现但今天到账次数");

			BackStatsOperateDay backStatsOperateDay = totalAllCzTx();
			row = sheet.createRow(1);
			row.createCell(0).setCellValue("合计");
			row.createCell(1).setCellValue(backStatsOperateDay.getRechargeCentSum() / 100);
			row.createCell(2).setCellValue(backStatsOperateDay.getRechargeCount());
			row.createCell(3).setCellValue(backStatsOperateDay.getWithdrawCentSum() / 100);
			row.createCell(4).setCellValue(backStatsOperateDay.getWithdrawCount());
			row.createCell(5).setCellValue(backStatsOperateDay.getSuccessWithdrawCentSum() / 100);
			row.createCell(6).setCellValue(backStatsOperateDay.getSuccessWithdrawCount());

			row.createCell(7).setCellValue(backStatsOperateDay.getSameDaySuccessWithdrawCentSum() / 100);
			row.createCell(8).setCellValue(backStatsOperateDay.getSameDaySuccessWithdrawCount());
			row.createCell(9).setCellValue(backStatsOperateDay.getTodayWithdrawNoArrivalCentSum() / 100);
			row.createCell(10).setCellValue(backStatsOperateDay.getTodayWithdrawNoArrivalCount());
			row.createCell(11).setCellValue(backStatsOperateDay.getYesterdayWithdrawTodayArrivalCentSum() / 100);
			row.createCell(12).setCellValue(backStatsOperateDay.getYesterdayWithdrawTodayArrivalCount());
			row.createCell(13).setCellValue((backStatsOperateDay.getNotSameDayWithdrawTodayArrivalCentSum() - backStatsOperateDay.getYesterdayWithdrawTodayArrivalCentSum()) / 100);
			row.createCell(14).setCellValue(backStatsOperateDay.getNotSameDayWithdrawTodayArrivalCount() - backStatsOperateDay.getYesterdayWithdrawTodayArrivalCount());
			row.createCell(15).setCellValue(backStatsOperateDay.getNotSameDayWithdrawTodayArrivalCentSum() / 100);
			row.createCell(16).setCellValue(backStatsOperateDay.getNotSameDayWithdrawTodayArrivalCount());
			List list = pageUtil.getList();
			BackStatsOperateDay report = null;
			for (int i = 0; i < list.size(); i++) {
				row = sheet.createRow((int) i + 2);
				report = (BackStatsOperateDay) list.get(i);
				row.createCell(0).setCellValue(report.getDates());
				row.createCell(1).setCellValue(report.getRechargeCentSum() / 100);
				row.createCell(2).setCellValue(report.getRechargeCount());
				row.createCell(3).setCellValue(report.getWithdrawCentSum() / 100);
				row.createCell(4).setCellValue(report.getWithdrawCount());
				row.createCell(5).setCellValue(report.getSuccessWithdrawCentSum() / 100);
				row.createCell(6).setCellValue(report.getSuccessWithdrawCount());

				row.createCell(7).setCellValue(report.getSameDaySuccessWithdrawCentSum() / 100);
				row.createCell(8).setCellValue(report.getSameDaySuccessWithdrawCount());
				row.createCell(9).setCellValue(report.getTodayWithdrawNoArrivalCentSum() / 100);
				row.createCell(10).setCellValue(report.getTodayWithdrawNoArrivalCount());
				row.createCell(11).setCellValue(report.getYesterdayWithdrawTodayArrivalCentSum() / 100);
				row.createCell(12).setCellValue(report.getYesterdayWithdrawTodayArrivalCount());
				row.createCell(13).setCellValue((report.getNotSameDayWithdrawTodayArrivalCentSum() - report.getYesterdayWithdrawTodayArrivalCentSum()) / 100);
				row.createCell(14).setCellValue(report.getNotSameDayWithdrawTodayArrivalCount() - report.getYesterdayWithdrawTodayArrivalCount());

				row.createCell(15).setCellValue(report.getNotSameDayWithdrawTodayArrivalCentSum() / 100);
				row.createCell(16).setCellValue(report.getNotSameDayWithdrawTodayArrivalCount());
			}

			String realPath = request.getServletContext().getRealPath("/report/cztxRecord.xls");
			FileOutputStream fout = new FileOutputStream(realPath);
			wb.write(fout);
			fout.close();
			response.getWriter().write("/report/cztxRecord.xls");

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 导出充值记录表
	 */
	public String iportCzTable() {
		List<JasperPrint> list = null;
		String pathname = QwyUtil.fmyyyyMMddHHmmss3.format(new Date()) + "_czRecord";
		try {
			PageUtil<CzRecord> pageUtil = new PageUtil<CzRecord>();
			pageUtil.setCurrentPage(currentPage);
			pageUtil.setPageSize(pageSize);
			String filePath = request.getServletContext().getRealPath("/WEB-INF/classes/cz_record.jasper");
			System.out.println("iportTable报表路径: " + filePath);
			list = bean.getUserCzJasperPrintList(name, insertTime, status, pageUtil, filePath);
			doIreport(list, pathname);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 导出提现记录表
	 */
	public String iportTxTable() {
		List<JasperPrint> list = null;
		String pathname = QwyUtil.fmyyyyMMddHHmmss3.format(new Date()) + "_txRecord";
		try {
			PageUtil<CzRecord> pageUtil = new PageUtil<CzRecord>();
			pageUtil.setCurrentPage(currentPage);
			pageUtil.setPageSize(pageSize);
			String filePath = request.getServletContext().getRealPath("/WEB-INF/classes/tx_record.jasper");
			System.out.println("iportTable报表路径: " + filePath);
			list = bean.getUserTxJasperPrintList(name, insertTime, status, pageUtil, filePath);
			doIreport(list, pathname);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 申请提现
	 * 
	 * @return
	 */
	public String rootWithdraw() {
		String json = "";
		try {
			request = getRequest();
			UsersAdmin users = (UsersAdmin) getRequest().getSession().getAttribute("usersAdmin");
			if (QwyUtil.isNullAndEmpty(users)) {
				json = QwyUtil.getJSONString("err", "管理员未登录");
				QwyUtil.printJSON(getResponse(), json);
				// 管理员没有登录;
				return null;
			}
			Pattern pattern = Pattern.compile("^[0-9]+(\\.[0-9]{1,2})?$");// 带有小数的金钱格式;
			boolean isTrue = pattern.matcher(money).matches();
			if (isTrue) {
				Users u = registerUserBean.getUsersByUsername(username);
				if (!QwyUtil.isNullAndEmpty(u)) {
					json = bean.withdraw(u.getId(), Integer.parseInt(money), QwyUtil.getIpAddr(request), users.getId(), note);
				} else {
					json = QwyUtil.getJSONString("error", "用户不存在");
				}
			} else {
				json = QwyUtil.getJSONString("error", "金额格式有误");
			}

		} catch (Exception e) {
			log.error("操作异常: ", e);
			json = QwyUtil.getJSONString("error", "充值异常,请联系客服;code:8889");
		}
		try {
			QwyUtil.printJSON(getResponse(), json);
		} catch (IOException e) {
			log.error("操作异常: ", e);
		}
		return null;
	}

	/**
	 * 申请提现
	 * 
	 * @return
	 */
	public String rootTxRecord() {
		String json = "";
		try {
			request = getRequest();
			UsersAdmin users = (UsersAdmin) getRequest().getSession().getAttribute("usersAdmin");
			if (QwyUtil.isNullAndEmpty(users)) {
				json = QwyUtil.getJSONString("err", "管理员未登录");
				QwyUtil.printJSON(getResponse(), json);
				// 管理员没有登录;
				return null;
			}
			PageUtil<RootTxRecord> pageUtil = new PageUtil<RootTxRecord>();
			pageUtil.setCurrentPage(currentPage);
			pageUtil.setPageSize(pageSize);
			StringBuffer url = new StringBuffer();
			url.append(getRequest().getServletContext().getContextPath());
			url.append("/Product/Admin/recharge!rootTxRecord.action?");
			if (!QwyUtil.isNullAndEmpty(insertTime)) {
				url.append("&insertTime=");
				url.append(insertTime);
			}
			pageUtil.setPageUrl(url.toString());
			pageUtil = bean.findPageUtil(pageUtil, insertTime);
			getRequest().setAttribute("insertTime", insertTime);
			if (!QwyUtil.isNullAndEmpty(pageUtil)) {
				getRequest().setAttribute("pageUtil", pageUtil);
			}
		} catch (Exception e) {
			log.error("操作异常: ", e);
			json = QwyUtil.getJSONString("error", "充值异常,请联系客服;code:8889");
		}
		return "rootTxRecord";
	}

	/**
	 * 提现查询（连连支付）
	 * 
	 * @return
	 */
	public String TxMoneyQuery() {
		String json = "";
		try {
			UsersAdmin ua = (UsersAdmin) getRequest().getSession().getAttribute("usersAdmin");
			if (QwyUtil.isNullAndEmpty(ua)) {
				json = QwyUtil.getJSONString("err", "管理员未登录");
				QwyUtil.printJSON(getResponse(), json);
				// 管理员没有登录;
				return null;
			}

			if (!QwyUtil.isNullAndEmpty(insertTime)) {
				// dt_order=QwyUtil.fmyyyyMMddHHmmss2.format(insertTime);
				// 得到用户数据
				json = lianLianPayBean.queryPay(requestId, insertTime, "1");
				JSONObject jsonObject = JSONObject.fromObject(json);
				if (!QwyUtil.isNullAndEmpty(jsonObject)) {
					String ret_code = jsonObject.get("ret_code").toString();
					if ("0000".equals(ret_code)) {// 正常返回用户信息
						if ("SUCCESS".equals(jsonObject.get("result_pay").toString())) {
							json = QwyUtil.getJSONString("ok", "提现成功");
						}
						if ("WAITING".equals(jsonObject.get("result_pay").toString())) {
							json = QwyUtil.getJSONString("error", "等待提现");
						}
						if ("PROCESSING".equals(jsonObject.get("result_pay").toString())) {
							json = QwyUtil.getJSONString("error", "银行处理中");
						}
						if ("REFUND".equals(jsonObject.get("result_pay").toString())) {
							json = QwyUtil.getJSONString("ok", "退款");
						}
						if ("FAILURE".equals(jsonObject.get("result_pay").toString())) {
							json = QwyUtil.getJSONString("error", "提现失败");
						}
						QwyUtil.printJSON(getResponse(), json);
						return null;
					} else {
						if (!QwyUtil.isNullAndEmpty(jsonObject.get("ret_msg"))) {
							json = QwyUtil.getJSONString("error", jsonObject.get("ret_msg").toString());
							QwyUtil.printJSON(getResponse(), json);
							return null;
						}
					}
				}
			}
		} catch (Exception e) {
			log.error("操作异常: ", e);
		}
		json = QwyUtil.getJSONString("err", "查询记录异常");
		try {
			QwyUtil.printJSON(getResponse(), json);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			log.error("操作异常: ", e);
		}
		return null;
	}

	/**
	 * 充值查询（连连支付）
	 * 
	 * @return
	 */
	public String CzMoneyQuery() {
		String json = "";
		try {
			UsersAdmin ua = (UsersAdmin) getRequest().getSession().getAttribute("usersAdmin");
			if (QwyUtil.isNullAndEmpty(ua)) {
				json = QwyUtil.getJSONString("err", "管理员未登录");
				QwyUtil.printJSON(getResponse(), json);
				// 管理员没有登录;
				return null;
			}
			if ("3".equals(type)) {// 连连认证

				if (!QwyUtil.isNullAndEmpty(insertTime)) {
					// dt_order=QwyUtil.fmyyyyMMddHHmmss2.format(insertTime);
					// 得到用户数据
					json = lianLianPayBean.queryPay(requestId, insertTime, "0");
					JSONObject jsonObject = JSONObject.fromObject(json);
					if (!QwyUtil.isNullAndEmpty(jsonObject)) {
						String ret_code = jsonObject.get("ret_code").toString();
						if ("0000".equals(ret_code)) {// 正常返回用户信息
							if ("SUCCESS".equals(jsonObject.get("result_pay").toString())) {
								json = QwyUtil.getJSONString("ok", "支付成功");
								QwyUtil.printJSON(getResponse(), json);
								return null;
							}
							if ("WAITING".equals(jsonObject.get("result_pay").toString())) {
								json = "等待支付";
							}
							if ("PROCESSING".equals(jsonObject.get("result_pay").toString())) {
								json = "银行支付处理中";
							}
							if ("REFUND".equals(jsonObject.get("result_pay").toString())) {
								json = "退款";
							}
							if ("FAILURE".equals(jsonObject.get("result_pay").toString())) {
								json = "支付失败";
							}
							if (!QwyUtil.isNullAndEmpty(QwyUtil.get(jsonObject, "memo"))) {
								json += QwyUtil.get(jsonObject, "memo");
							}
							json = QwyUtil.getJSONString("error", json);
							QwyUtil.printJSON(getResponse(), json);
							return null;
						} else {
							if (!QwyUtil.isNullAndEmpty(jsonObject.get("ret_msg"))) {
								json = QwyUtil.getJSONString("error", jsonObject.get("ret_msg").toString());
								QwyUtil.printJSON(getResponse(), json);
								return null;
							}
						}
					}
				}
			}
		} catch (Exception e) {
			log.error("操作异常: ", e);
		}
		json = QwyUtil.getJSONString("err", "查询记录异常");
		try {
			QwyUtil.printJSON(getResponse(), json);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			log.error("操作异常: ", e);
		}
		return null;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public double getRechargeMoney() {
		return rechargeMoney;
	}

	public void setRechargeMoney(double rechargeMoney) {
		this.rechargeMoney = rechargeMoney;
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getInsertTime() {
		return insertTime;
	}

	public void setInsertTime(String insertTime) {
		this.insertTime = insertTime;
	}

	public String getRequestId() {
		return requestId;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}

	public String getRegistPlatform() {
		return registPlatform;
	}

	public void setRegistPlatform(String registPlatform) {
		this.registPlatform = registPlatform;
	}

	public String getCause() {
		return cause;
	}

	public void setCause(String cause) {
		this.cause = cause;
	}

	public String getMoney() {
		return money;
	}

	public void setMoney(String money) {
		this.money = money;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public static void main(String[] args) {
		Map<String, String> map = null;
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		list.add(map);
		System.out.println(list);

	}
}
