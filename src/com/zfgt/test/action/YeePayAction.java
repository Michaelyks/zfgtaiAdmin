package com.zfgt.test.action;

import java.io.IOException;
import java.util.Date;
import java.util.UUID;

import javax.annotation.Resource;

import org.apache.struts2.config.Namespace;
import org.apache.struts2.config.ParentPackage;
import org.apache.struts2.config.Result;
import org.apache.struts2.config.Results;
import org.hibernate.criterion.Order;

import com.zfgt.account.bean.MyAccountBean;
import com.zfgt.account.bean.UserRechargeBean;
import com.zfgt.account.bean.ValidateBean;
import com.zfgt.common.action.BaseAction;
import com.zfgt.common.bean.YiBaoPayBean;
import com.zfgt.common.util.QwyUtil;
import com.zfgt.common.yeepay.Configuration;
import com.zfgt.common.yeepay.PaymentForOnlineService;
import com.zfgt.common.yeepay.QueryResult;
import com.zfgt.orm.Account;
import com.zfgt.orm.CzRecord;
import com.zfgt.orm.UsersLogin;
import com.zfgt.orm.Yeepay;


/**
 * 测试易宝网银支付
 * @author 曾礼强
 *2015年6月19日 11:31:58
 */
@ParentPackage("struts-default")
@Namespace("/Product")

@Results({
	@Result(name = "login", value = "/Product/login.jsp"),
	@Result(name = "yeepay", value = "/Product/chinabankpay/reqpay.jsp")
})
public class YeePayAction extends BaseAction{
	@Resource
	private UserRechargeBean bean;
	@Resource
	private ValidateBean validateBean;
	@Resource
	private MyAccountBean myAccountBean;
	@Resource
	private YiBaoPayBean yiBaoPayBean;
	private String orderId;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String money;//以元为单位
	
	
	/**
	 * 返回地址
	 * @return
	 */
	public String callPay(){
		try {
			String keyValue = formatString(Configuration.getInstance()
					.getValue("keyValue")); // 商家密钥
			String r0_Cmd = formatString(request.getParameter("r0_Cmd")); // 业务类型
			String p1_MerId = formatString(Configuration.getInstance()
					.getValue("p1_MerId")); // 商户编号
			String r1_Code = formatString(request.getParameter("r1_Code"));// 支付结果
			String r2_TrxId = formatString(request.getParameter("r2_TrxId"));// 易宝支付交易流水号
			String r3_Amt = formatString(request.getParameter("r3_Amt"));// 支付金额
			String r4_Cur = formatString(request.getParameter("r4_Cur"));// 交易币种
			String r5_Pid = new String(formatString(
					request.getParameter("r5_Pid")).getBytes("iso-8859-1"),
					"gbk");// 商品名称
			String r6_Order = formatString(request.getParameter("r6_Order"));// 商户订单号
			String r7_Uid = formatString(request.getParameter("r7_Uid"));// 易宝支付会员ID
			String r8_MP = new String(formatString(
					request.getParameter("r8_MP")).getBytes("iso-8859-1"),
					"gbk");// 商户扩展信息
			String r9_BType = formatString(request.getParameter("r9_BType"));// 交易结果返回类型
			String hmac = formatString(request.getParameter("hmac"));// 签名数据
			boolean isOK = false;
			// 校验返回数据包
			isOK = PaymentForOnlineService.verifyCallback(hmac, p1_MerId,
					r0_Cmd, r1_Code, r2_TrxId, r3_Amt, r4_Cur, r5_Pid,
					r6_Order, r7_Uid, r8_MP, r9_BType, keyValue);
			if (isOK) {
				//在接收到支付结果通知后，判断是否进行过业务逻辑处理，不要重复进行业务逻辑处理
				if (r1_Code.equals("1")) {
					// 产品通用接口支付成功返回-浏览器重定向
					if (r9_BType.equals("1")) {
						getResponse().encodeRedirectUrl("www.bauomao.com");
						// 产品通用接口支付成功返回-服务器点对点通讯
					} else if (r9_BType.equals("2")) {
						CzRecord record=bean.getCzRecordByOrderId(r6_Order);
						if(record.getStatus().equals("0")){
							record.setStatus("1");
							record.setYbOrderId(r2_TrxId);
							record.setCheckTime(new Date());
							record.setNote("充值成功");
							bean.updateRecord(record);
							if(bean.usreRecharge(record.getUsersId(), record.getMoney(), " cz", "在线充值", "")){
								// 	如果在发起交易请求时	设置使用应答机制时，必须应答以"success"开头的字符串，大小写不敏感
								getResponse().getWriter().write("SUCCESS");
								// 产通用接口支付成功返回-电话支付返回		
							}
						}
					}
				}
			} else {
				getResponse().getWriter().write("交易签名被篡改!");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	
	/**
	 * 订单查询
	 * @return
	 */
	public String queryDetail(){
		try {
			QueryResult queryResult=PaymentForOnlineService.queryByOrder(orderId);
			if(queryResult.getR1_Code().equals("1")){
				if(queryResult.getRb_PayStatus().equalsIgnoreCase("INT")){
					QwyUtil.printJSON(getResponse(), "未支付");
					CzRecord record=bean.getCzRecordByOrderId(orderId);
					if(!record.equals("1")){
						record.setStatus("2");
						record.setYbOrderId(queryResult.getR2_TrxId());
						record.setCheckTime(new Date());
						record.setNote("充值失败");
						bean.updateRecord(record);
					}
				}else if(queryResult.getRb_PayStatus().equalsIgnoreCase("CANCELED")){
					QwyUtil.printJSON(getResponse(), "订单已取消");
				}else{
					CzRecord record=bean.getCzRecordByOrderId(orderId);
					if(!record.equals("1")){
						record.setStatus("1");
						record.setYbOrderId(queryResult.getR2_TrxId());
						record.setCheckTime(new Date());
						record.setNote("充值成功");
						bean.updateRecord(record);
						if(record.getMoney().toString().equals(QwyUtil.calcNumber(queryResult.getR3_Amt(), 100, "*"))){
							if(bean.usreRecharge(record.getUsersId(), record.getMoney(), " cz", "在线充值", "")){
								// 产通用接口支付成功返回-电话支付返回		
								QwyUtil.printJSON(getResponse(), "已支付");
							}
						}
					}
				}
			}else{
				CzRecord record=bean.getCzRecordByOrderId(orderId);
				if(!record.equals("1")){
					record.setStatus("2");
					record.setYbOrderId(queryResult.getR2_TrxId());
					record.setCheckTime(new Date());
					record.setNote("充值失败");
					bean.updateRecord(record);
				}
				QwyUtil.printJSON(getResponse(), "订单不存在");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public String getMoney() {
		return money;
	}
	public void setMoney(String money) {
		this.money = money;
	}
	
	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	String formatString(String text){ 
		if(text == null) {
			return ""; 
		}
		return text;
	}
}
