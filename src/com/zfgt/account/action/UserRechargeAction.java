package com.zfgt.account.action;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Pattern;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts2.config.Namespace;
import org.apache.struts2.config.ParentPackage;
import org.apache.struts2.config.Result;
import org.apache.struts2.config.Results;



import com.alibaba.fastjson.JSON;
import com.zfgt.account.bean.MyAccountBean;
import com.zfgt.account.bean.UserRechargeBean;
import com.zfgt.account.bean.ValidateBean;
import com.zfgt.account.dao.UserRechargeDAO;
import com.zfgt.common.action.BaseAction;
import com.zfgt.common.lianlian.pay.utils.PayOrder;
import com.zfgt.common.util.DESEncrypt;
import com.zfgt.common.util.PageUtil;
import com.zfgt.common.util.QwyUtil;
import com.zfgt.common.yeepay.Configuration;
import com.zfgt.common.yeepay.PaymentForOnlineService;
import com.zfgt.login.bean.RegisterUserBean;
import com.zfgt.orm.Account;
import com.zfgt.orm.BankCard;
import com.zfgt.orm.CzRecord;
import com.zfgt.orm.TxRecord;
import com.zfgt.orm.Users;
import com.zfgt.orm.UsersInfo;
import com.zfgt.orm.UsersLogin;
import com.zfgt.orm.Yeepay;

/**用户充值提现Action层;<br>
 * 接收充值的金额;接收提现的金额;
 * @author qwy
 *
 * @createTime 2015-4-27上午9:58:44
 */
@SuppressWarnings("serial")
@ParentPackage("struts-default")
@Namespace("/Product/User")
//理财产品
@Results({ 
	@Result(name = "myAccount", value = "/Product/User/myAccount.jsp"),
//	@Result(name = "recharge", value = "/Product/User/recharge.jsp"),
	@Result(name = "recharge", value = "/Product/User/recharge__LianPay.jsp"),
//	@Result(name = "getMoney", value = "/Product/User/getMoney.jsp"),
	@Result(name = "getMoney", value = "/Product/User/getMoney__LLpay.jsp"),
	@Result(name = "login", value = "/Product/login.jsp"),
	@Result(name = "error", value = "/Product/page_404.jsp"),
	@Result(name = "yeepay", value = "/Product/chinabankpay/reqpay.jsp"),
	@Result(name = "llreqpay", value = "/Product/chinabankpay/llreqpay.jsp"),
	@Result(name = "SUCCESS", value = "/Product/User/myAccount!showMyAccount.action", type=org.apache.struts2.dispatcher.ServletRedirectResult.class),
	@Result(name = "showUsersMoney", value = "/Product/User/userRecharge!showUsersMoney.action", type=org.apache.struts2.dispatcher.ServletRedirectResult.class)
	
})
public class UserRechargeAction extends BaseAction{
	@Resource
	private UserRechargeBean bean;
	@Resource
	private UserRechargeDAO dao;
	@Resource
	private ValidateBean validateBean;
	@Resource
	private MyAccountBean myAccountBean;
	@Resource
	private RegisterUserBean rubean;
	
//	@Resource
//	private YiBaoPayBean yiBaoPayBean;
//	private ResourceBundle resb1 = ResourceBundle.getBundle("payapi");
//	private String myMerchantaccount = resb1.getString("payapi.paytest_merchantaccount");
//	// 从配置文件读取易宝分配的公钥
//	private String yibaoPublicKey = resb1.getString("payapi.paytest_yibao_publickey");
//	// 从配置文件读取商户自己的私钥
//	private String merchantPrivateKey = resb1.getString("payapi.paytest_merchant_privatekey");
	
	private String rechargeMoney;
	private String payType="0"; 
	private Integer currentPage = 1;
	private Integer pageSize = 25;
	private String payPassword;
	private String pd_FrpId;
	private String recordType;
	
	private String idcard;
	
	private String cardNo;
	
	private String name;
	
	private String msg;
	
	
	private PayOrder payOrder;
	private String idcardno;
	private String cardno;
	private String realName;
	private String username;
	private String money;
	private String province;
	private String city;
	private String braBankName;
	private Account account;
	private Users user;
	//回调地址
	private String call_url = "https://yintong.com.cn/traderapi/cardandpay.htm";
	
	
//	/**进入充值页面,显示用户金额(易宝支付);
//	 * @return
//	 */
//	public String showUsersMoney(){
//		try {
//			request = getRequest();
//			UsersLogin usersLogin  = (UsersLogin)request.getSession().getAttribute("usersLogin");
//			if(QwyUtil.isNullAndEmpty(usersLogin)){
//				return "login";
//			}
//			Users users = (Users)bean.findObjectById(new Users(), usersLogin.getUsersId());
//			request.setAttribute("users", users);
//			double coupons = bean.getCouponCost(users.getId());
//			request.setAttribute("coupons", coupons);
//			
//			UsersInfo ui = users.getUsersInfo();
//			if("1".equals(ui.getIsBindBank())){
//				//绑定了银行卡;并且进行了实名认证;
//				List<BankCard> listBankCard = validateBean.getBindBankCard(users.getId());
//				request.setAttribute("listBankCard", listBankCard);
//				request.setAttribute("isBindBank", QwyUtil.isNullAndEmpty(listBankCard)?0:1);
//			}
//			PageUtil<CzRecord> pageUtil = new PageUtil<CzRecord>();
//			pageUtil.setCurrentPage(currentPage);
//			pageUtil.setPageSize(pageSize);
//			String url= request.getServletContext().getContextPath()+"/Product/User/userRecharge!showUsersMoney.action?recordType="+recordType;
//			pageUtil.setPageUrl(url);
//			PageUtil<CzRecord> listCz = bean.getCzRecordByUserId(pageUtil,users.getId(),recordType);
//			//if(!QwyUtil.isNullAndEmpty(recordType)){
//				request.setAttribute("anchor", "#cz_record");
//			//}
//			request.setAttribute("recordType", recordType);
//			request.setAttribute("listCzRecord", listCz.getList());
//			request.setAttribute("pageUtil", pageUtil);
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			log.error("操作异常: ",e);
//			return "error";
//		}
//		return "recharge";
//	}
	/**进入充值页面,显示用户金额（连连支付）;
	 * @return
	 */
	public String showUsersMoney(){
		try {
			request = getRequest();
			UsersLogin usersLogin  = (UsersLogin)request.getSession().getAttribute("usersLogin");
			if(QwyUtil.isNullAndEmpty(usersLogin)){
				return "login";
			}
			Users users = rubean.getUsersById(usersLogin.getUsersId());
			request.setAttribute("users", users);
			double coupons = bean.getCouponCost(users.getId());
			request.setAttribute("coupons", coupons);
			
			UsersInfo ui = users.getUsersInfo();
			if("1".equals(ui.getIsBindBank())){
				//绑定了银行卡;并且进行了实名认证;
				List<BankCard> listBankCard = validateBean.getBindBankCard(users.getId());
				request.setAttribute("isBindBank", QwyUtil.isNullAndEmpty(listBankCard)?0:1);
			}
			PageUtil<CzRecord> pageUtil = new PageUtil<CzRecord>();
			pageUtil.setCurrentPage(currentPage);
			pageUtil.setPageSize(pageSize);
			String url= request.getServletContext().getContextPath()+"/Product/User/userRecharge!showUsersMoney.action?recordType="+recordType;
			pageUtil.setPageUrl(url);
			PageUtil<CzRecord> listCz = bean.getCzRecordByUserId(pageUtil,users.getId(),recordType);
			request.setAttribute("anchor", "#cz_record");
			request.setAttribute("recordType", recordType);
			request.setAttribute("listCzRecord", listCz.getList());
			request.setAttribute("pageUtil", pageUtil);
			if(!QwyUtil.isNullAndEmpty(msg)){
				request.setAttribute("msg", "");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.error("操作异常: ",e);
			return "error";
		}
		return "recharge";
	}
	
	/**充值验证（连连支付）
	 * @return
	 */
	public String directBindPay(){
		String json = "";
		try {
			long st = System.currentTimeMillis();
			request = getRequest();
			UsersLogin usersLogin  = (UsersLogin)request.getSession().getAttribute("usersLogin");
			if(QwyUtil.isNullAndEmpty(usersLogin)){
				return "login";
			}else{
//				Users user = validateBean.getUsersById(usersLogin.getUsersId());
				//绑定了银行卡;并且进行了实名认证;
				if(QwyUtil.isNullAndEmpty(usersLogin.getUsersId())){//连接池未释放导致usersLogin.getUsersId()为null
					return "showUsersMoney";
				}
				List<BankCard> listBankCard = validateBean.getBindBankCard(usersLogin.getUsersId());
				request.setAttribute("isBindBank", QwyUtil.isNullAndEmpty(listBankCard)?0:1);
				if(!QwyUtil.isNullAndEmpty(rechargeMoney)){
					Pattern pattern = Pattern.compile("^[0-9]+(\\.[0-9]{1,2})?$");//带有小数的金钱格式;			
					boolean isTrue = pattern.matcher(rechargeMoney).matches();	
					if(isTrue){
						String userip=QwyUtil.getIpAddr(request);
						double money = Double.parseDouble(rechargeMoney);
						double trueMoney = QwyUtil.calcNumber(money, 100, "*").doubleValue();
						if(trueMoney<100){
							json = QwyUtil.getJSONString("error", "充值金额必须1元起");
						}else{
						    String returnUrl=Configuration.getInstance().getValue("call_pay_url")+request.getServletContext().getContextPath()+"/Product/callBack!CzReturnUrl.action";
						    System.out.println("充值同步回调地址"+returnUrl);
							log.info("充值同步回调地址"+returnUrl);
							String callUrl=Configuration.getInstance().getValue("call_pay_url")+request.getServletContext().getContextPath()+"/Product/callBack!CzCallbackurl.action";
							System.out.println("充值异步回调地址"+callUrl);
							log.info("充值异步回调地址"+callUrl);
							json = bean.directBindPay2(trueMoney, "中泰理财在线充值", "中泰理财在线充值", idcard,cardNo, name, userip, usersLogin.getUsersId(), callUrl,returnUrl);
						}
					}else{
						json = QwyUtil.getJSONString("error", "充值金额最多两位小数");
					}	
				}else{
					return "showUsersMoney";
				}

			}
			
			long et = System.currentTimeMillis();
			System.out.println("充值耗时: "+(et-st)+" ms");
		} catch (Exception e) {
			log.error("操作异常: ",e);
			json = QwyUtil.getJSONString("error", "充值异常,请联系客服;");
		}
		JSONObject reqJson=JSONObject.fromObject(json);
		
		if("ok".equals(reqJson.get("status"))){	
			String payOrderJson=reqJson.get("json").toString();
	        // 解析请求对象
//			PayOrder payOrder = JSON.parseObject(payOrderJson, PayOrder.class);
			JSONObject object=JSONObject.fromObject(payOrderJson);
			//Object payOrder = JSONObject.toBean(object, PayOrder.class);

			getRequest().setAttribute("payOrder", object);
			return "llreqpay";
		}else{
			String msg=reqJson.get("json").toString();
			getRequest().setAttribute("msg", msg);
		}
		return "recharge";
	}	
	/**进入提现页面,显示用户金额;
	 * @return
	 */
	public String getMoney(){
		try {
			request = getRequest();
			UsersLogin usersLogin  = (UsersLogin)request.getSession().getAttribute("usersLogin");
			if(QwyUtil.isNullAndEmpty(usersLogin)){
				return "login";
			}			
			//Users users = (Users)bean.findObjectById(new Users(), usersLogin.getUsersId());
			Users users = rubean.getUsersById(usersLogin.getUsersId());
			request.setAttribute("users", users);
			double coupons = bean.getCouponCost(users.getId());
			request.setAttribute("coupons", coupons);
			double productCost = myAccountBean.getProductCost(users.getId());
			productCost = QwyUtil.jieQuFa(productCost, 0);
			request.setAttribute("productCost", QwyUtil.calcNumber(productCost, 0.01, "*").doubleValue());
			
			account = myAccountBean.getAccountByUsersName(DESEncrypt.jieMiUsername(users.getUsername()), "1");
			if(QwyUtil.isNullAndEmpty(account)){
				return "recharge";
			}
			String isFirstCZ="1";
			if(!QwyUtil.isNullAndEmpty(account)){
				if(!QwyUtil.isNullAndEmpty(account.getProvinceCode())&& !QwyUtil.isNullAndEmpty(account.getCityCode()) && !QwyUtil.isNullAndEmpty(account.getBraBankName())){
				isFirstCZ="0";
			}
			}
			request.setAttribute("isFirstCZ", isFirstCZ);
			//查询是否设置了交易密码
			String isPwd="no";
			if(!QwyUtil.isNullAndEmpty(users.getPayPassword())){
				isPwd="ok";
			}
			getRequest().setAttribute("isPwd", isPwd);
			UsersInfo ui = users.getUsersInfo();
			if("1".equals(ui.getIsBindBank())){
				//绑定了银行卡;并且进行了实名认证;
				List<BankCard> listBankCard = validateBean.getBindBankCard(users.getId());
				PageUtil<TxRecord> pageUtil = new PageUtil<TxRecord>();
				pageUtil.setCurrentPage(currentPage);
				pageUtil.setPageSize(pageSize);
				String url= request.getServletContext().getContextPath()+"/Product/User/userRecharge!getMoney.action";
				pageUtil.setPageUrl(url);
				PageUtil<TxRecord> listCz = bean.getTxRecordByUserId(pageUtil,users.getId());
				request.setAttribute("listTxRecord", listCz.getList());
				request.setAttribute("pageUtil", pageUtil);
				request.setAttribute("listBankCard", listBankCard);

			}
			return "getMoney";
//			else{
//				request.setAttribute("msg", "您未绑卡，充值即可完成绑卡");
//				return "recharge";
//			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.error("操作异常: ",e);
			return "error";
		}

	}
	
	/**
	 * 网银支付
	 * @return
	 */
	public String chinabankPay(){
		String json = "";
		try {
			long st = System.currentTimeMillis();
			request = getRequest();
			UsersLogin usersLogin  = (UsersLogin)request.getSession().getAttribute("usersLogin");
			if(QwyUtil.isNullAndEmpty(usersLogin)){
				json = QwyUtil.getJSONString("noLogin", "请先登录");
			}else{
				Users u = validateBean.getUsersById(usersLogin.getUsersId());
				if(!DESEncrypt.jieMiPassword(u.getPayPassword()).equals(payPassword)){
					//支付密码错误;
					json = QwyUtil.getJSONString("error", "支付密码错误");
					QwyUtil.printJSON(getResponse(), json);
					return null;
				}
				Pattern pattern = Pattern.compile("^[0-9]+(\\.[0-9]{1,2})?$");//带有小数的金钱格式;
				//Pattern pattern = Pattern.compile("^[0-9]+$");//
				boolean isTrue = pattern.matcher(rechargeMoney).matches();	
				if(isTrue){
					double money = Double.parseDouble(rechargeMoney);
					Double trueMoney = QwyUtil.calcNumber(money, 100, "*").doubleValue();
					boolean is = false;
					if(trueMoney<100){
						json = QwyUtil.getJSONString("error", "充值金额必须1元起");
					}else{
						String uuid=UUID.randomUUID().toString();
						CzRecord czrecord=bean.addCzRecord(usersLogin.getUsersId(),trueMoney.intValue(), null, uuid, null, "中泰理财网银在线充值",null,"1");
						Yeepay yeepay=new Yeepay();
						yeepay.setKeyValue(Configuration.getInstance().getValue("keyValue"));
						yeepay.setP0_Cmd("Buy");
						yeepay.setP1_MerId(Configuration.getInstance().getValue("p1_MerId"));
						yeepay.setP2_Order(uuid);
						yeepay.setP3_Amt(rechargeMoney);
						yeepay.setP4_Cur("CNY");
						yeepay.setP5_Pid("baiyimao");
						yeepay.setP6_Pcat("baiyimao");
						yeepay.setP7_Pdesc("baiyimao");
						yeepay.setP8_Url(Configuration.getInstance().getValue("call_pay_url")+request.getServletContext().getContextPath()+"/Product/callBack!callPay.action");
						yeepay.setP9_SAF("0");
						yeepay.setPa_MP("");
						yeepay.setPd_FrpId(pd_FrpId);
						yeepay.setPr_NeedResponse("1");
						yeepay.setHmac(
								PaymentForOnlineService.getReqMd5HmacForOnlinePayment(
										yeepay.getP0_Cmd(),
										yeepay.getP1_MerId(),
										yeepay.getP2_Order(),
										yeepay.getP3_Amt(),
										yeepay.getP4_Cur(),
										yeepay.getP5_Pid(),
										yeepay.getP6_Pcat(),
										yeepay.getP7_Pdesc(),
										yeepay.getP8_Url(),
										yeepay.getP9_SAF(),
										yeepay.getPa_MP(),
										yeepay.getPd_FrpId(),
										yeepay.getPr_NeedResponse(),
										yeepay.getKeyValue()));
						yeepay.setYeepayCommonReqURL(Configuration.getInstance().getValue("yeepayCommonReqURL"));
						System.out.println(yeepay.getPr_NeedResponse());
						log.info(yeepay.getPr_NeedResponse());
						log.info(uuid);
						getRequest().setAttribute("yeepay", yeepay);
						return "yeepay";
					}
				}else{
					json = QwyUtil.getJSONString("error", "充值金额只能是整元");
				}
			 }
			} catch (Exception e) {
				log.error("操作异常: ",e);
				json = QwyUtil.getJSONString("error", "充值异常,请联系客服;code:8889");
			}
		try {
			QwyUtil.printJSON(getResponse(), json);
		} catch (IOException e) {
			log.error("操作异常: ",e);
		}
		return null;
	}
	
	
	/**网银支付，验证充值
	 * @return
	 */
	public String validateDate(){
		String json = "";
		try {
			request = getRequest();
			UsersLogin usersLogin  = (UsersLogin)request.getSession().getAttribute("usersLogin");
			if(QwyUtil.isNullAndEmpty(usersLogin)){
				json = QwyUtil.getJSONString("noLogin", "请先登录");
			}else{
				Users u = validateBean.getUsersById(usersLogin.getUsersId());
				if(!DESEncrypt.jieMiPassword(u.getPayPassword()).equals(payPassword)){
					//支付密码错误;
					json = QwyUtil.getJSONString("error", "支付密码错误");
					QwyUtil.printJSON(getResponse(), json);
					return null;
				}
				Pattern pattern = Pattern.compile("^[0-9]+(\\.[0-9]{1,2})?$");//带有小数的金钱格式;
				//Pattern pattern = Pattern.compile("^[0-9]+$");//
				boolean isTrue = pattern.matcher(rechargeMoney).matches();	
				if(isTrue){
					double money = Double.parseDouble(rechargeMoney);
					double trueMoney = QwyUtil.calcNumber(money, 100, "*").doubleValue();
					
					//trueMoney<100
					if(trueMoney<100){
						json = QwyUtil.getJSONString("error", "充值金额必须1元起");
					}else{
						json = QwyUtil.getJSONString("ok", "验证通过");
					}
				}else{
					json = QwyUtil.getJSONString("error", "充值金额只能是整元");
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			json = QwyUtil.getJSONString("error", "充值异常");
		}
		try {
			QwyUtil.printJSON(getResponse(), json);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	
	
//	/**充值
//	 * @return
//	 */
//	public String directBindPay(){
//		String json = "";
//		try {
//			long st = System.currentTimeMillis();
//			request = getRequest();
//			UsersLogin usersLogin  = (UsersLogin)request.getSession().getAttribute("usersLogin");
//			if(QwyUtil.isNullAndEmpty(usersLogin)){
//				json = QwyUtil.getJSONString("noLogin", "请先登录");
//			}else{
//				Users u = validateBean.getUsersById(usersLogin.getUsersId());
//				if(!DESEncrypt.jieMiPassword(u.getPayPassword()).equals(payPassword)){
//					//支付密码错误;
//					json = QwyUtil.getJSONString("error", "支付密码错误");
//					QwyUtil.printJSON(getResponse(), json);
//					return null;
//				}
//				Pattern pattern = Pattern.compile("^[0-9]+(\\.[0-9]{1,2})?$");//带有小数的金钱格式;
//				//Pattern pattern = Pattern.compile("^[0-9]+$");//
//				boolean isTrue = pattern.matcher(rechargeMoney).matches();	
//				if(isTrue){
//					double money = Double.parseDouble(rechargeMoney);
//					double trueMoney = QwyUtil.calcNumber(money, 100, "*").doubleValue();
//					boolean is = false;
//					//trueMoney<100
//					if(trueMoney<100){
//						json = QwyUtil.getJSONString("error", "充值金额必须1元起");
//					}else{
//						String callUrl=Configuration.getInstance().getValue("call_pay_url")+request.getServletContext().getContextPath()+"/Product/callBack!directCallbackurl.action";
//						System.out.println("充值回调地址"+callUrl);
//						log.info("充值回调地址"+callUrl);
//						json = bean.directBindPay2(UUID.randomUUID().toString(), (int)trueMoney, "中泰理财在线充值", "中泰理财在线充值", usersLogin.getUsersId(), callUrl, QwyUtil.getIpAddr(request));
//					}
//				}else{
//					json = QwyUtil.getJSONString("error", "充值金额只能是整元");
//				}
//			}
//			long et = System.currentTimeMillis();
//			System.out.println("充值耗时: "+(et-st)+" ms");
//		} catch (Exception e) {
//			log.error("操作异常: ",e);
//			json = QwyUtil.getJSONString("error", "充值异常,请联系客服;code:8889");
//		}
//		try {
//			QwyUtil.printJSON(getResponse(), json);
//		} catch (IOException e) {
//			log.error("操作异常: ",e);
//		}
//		return null;
//	}
	
	
	/**充值回调接口（易宝支付）
	 * @return
	 */
//	public String directCallbackurl(){
//		try {
//			System.out.println("充值回调接口");
//			log.info("充值回调接口");
//			request = getRequest();
//				if(!QwyUtil.isNullAndEmpty(request)){
//					// 对易宝返回的结果进行验签
//					String yb_data = QwyUtil.formatString(request.getParameter("data ")); // 易宝支付返回的业务数据密文
//					String yb_encryptkey = QwyUtil.formatString(request.getParameter("encryptkey ")); // 易宝支付返回的对ybAesKey加密后的密文
//					boolean passSign = EncryUtil.checkDecryptAndSign(yb_data,
//							yb_encryptkey, yibaoPublicKey, merchantPrivateKey);
//
//					if (passSign) {
//						// 验签通过
//						String yb_aeskey = RSA.decrypt(yb_encryptkey,
//								merchantPrivateKey);
//						System.out.println("易宝支付给商户" + myMerchantaccount
//								+ "返回结果中使用的aeskey为：" + yb_aeskey);
//
//						String payresult_view = AES.decryptFromBase64(yb_data,
//								yb_aeskey);
//						System.out.println("易宝支付给商户" + myMerchantaccount
//								+ "结果（aes解密后的明文）：" + payresult_view);
//					} else {
//						System.out.println("验签未通过");
//					}
//					System.out.println(request.getAttribute("merchantaccount"));
//					log.info("请求不为空充值回调接口");
//					System.out.println(request.getParameter("merchantaccount "));
//					String merchantaccount = QwyUtil.formatString(request.getParameter("merchantaccount ")); // 商户编号
//					String orderid = QwyUtil.formatString(request.getParameter("orderid ")); // 商户生成的唯一订单号，最长 50 位
//					String yborderid = QwyUtil.formatString(request.getParameter("yborderid ")); // 易宝交易流水号
//					String amount = QwyUtil.formatString(request.getParameter("amount ")); // 交易金额
//					String identityid = QwyUtil.formatString(request.getParameter("identityid ")); // 用户标识
//					String card_top = QwyUtil.formatString(request.getParameter("card_top ")); // 卡号前 6 位
//					String card_last = QwyUtil.formatString(request.getParameter("card_last ")); // 卡号后 4 位
//					String status = QwyUtil.formatString(request.getParameter("status ")); // 支付状态
//					String errorcode = QwyUtil.formatString(request.getParameter("errorcode ")); // 错误码
//					String errormsg = QwyUtil.formatString(request.getParameter("errormsg ")); // 错误信息
//					String sign = QwyUtil.formatString(request.getParameter("sign ")); // 业务类型
//					System.out.println("回调订单merchantaccount"+merchantaccount);
//					log.info("回调订单merchantaccount"+merchantaccount);
//					System.out.println("回调订单orderid"+orderid);
//					log.info("回调订单orderid"+orderid);
//					System.out.println("回调订单yborderid"+yborderid);
//					log.info("回调订单yborderid"+yborderid);
//					System.out.println("回调订单amount"+amount);
//					log.info("回调订单amount"+amount);
//					System.out.println("回调订单identityid"+identityid);
//					log.info("回调订单identityid"+identityid);
//					System.out.println("回调订单card_top"+card_top);
//					log.info("回调订单card_top"+card_top);
//					System.out.println("回调订单card_last"+card_last);
//					log.info("回调订单card_last"+card_last);
//					System.out.println("回调订单status"+status);
//					log.info("回调订单status"+status);
//					System.out.println("回调订单errorcode"+errorcode);
//					log.info("回调订单errorcode"+errorcode);
//					System.out.println("回调订单errormsg"+errormsg);
//					log.info("回调订单errormsg"+errormsg);
//					if(merchantaccount.equals(myMerchantaccount)){
//						log.info("编号相等");
//						CzRecord record=bean.getCzRecordByOrderId(orderid);
//						log.info("获取充值记录");
//						if(!QwyUtil.isNullAndEmpty(record)){
//							log.info("充值记录不为空"+record.getId());
//							if("0".equals(record.getStatus())){
//								if("1".equals(status)){
//									boolean isRecharge = bean.usreRecharge(Long.parseLong(identityid), Double.parseDouble(amount), "cz", "第三方支付", "通过【易宝支付】进行充值");
//									if(isRecharge){
//										record.setStatus("1");
//										record.setNote("充值成功");
//									}else{
//										record.setStatus("0");
//										record.setNote("易宝支付已充值成功,数据库插入失败!");
//									}
//									record.setCheckTime(new Date());
//									dao.saveOrUpdate(record);
//									log.info("充值成功");
//									response.getWriter().print("”SUCCESS");
//								}else if("0".equals(status)){
//									record.setStatus("2");
//									record.setErrorCode(errorcode);
//									record.setNote(errormsg);
//									record.setCheckTime(new Date());
//									log.info("充值失败");
//									dao.saveOrUpdate(record);
//								}
//							}
//						}
//					}
//				}
//		} catch (Exception e) {
//			log.error("操作异常: ",e);
//		}
//		return null;
//	}
	
	
	/**申请提现
	 * @return
	 */
	public String withdraw(){
		String json = "";
		try {
			request = getRequest();
			UsersLogin usersLogin  = (UsersLogin)request.getSession().getAttribute("usersLogin");
			if(QwyUtil.isNullAndEmpty(usersLogin)){
				json = QwyUtil.getJSONString("noLogin", "请先登录");
			}else{
				Pattern pattern = Pattern.compile("^[0-9]+(\\.[0-9]{1,2})?$");//带有小数的金钱格式;
				boolean isTrue = pattern.matcher(rechargeMoney).matches();	
				if(isTrue){
					double money = Double.parseDouble(rechargeMoney);
					double trueMoney = QwyUtil.calcNumber(money, 100, "*").doubleValue();
					
					//trueMoney<100
					if(trueMoney<100){
						json = QwyUtil.getJSONString("error", "提现金额必须1元起");
					}else{
						String callUrl=Configuration.getInstance().getValue("call_pay_url")+request.getServletContext().getContextPath()+"/Product/callBack!withdrawCallPay.action";
						log.info("提现回调地址:"+callUrl);
						json = bean.withdraw(usersLogin.getUsersId(), (int)trueMoney, QwyUtil.getIpAddr(getRequest()), callUrl);
					}
				}else{
					json = QwyUtil.getJSONString("error", "充值金额只能是整元");
				}
			}
		} catch (Exception e) {
			log.error("操作异常: ",e);
			json = QwyUtil.getJSONString("error", "充值异常,请联系客服;code:8889");
		}
		try {
			QwyUtil.printJSON(getResponse(), json);
		} catch (IOException e) {
			log.error("操作异常: ",e);
		}
		return null;
	}
	
	/**
	 * 连连提现
	 * @return
	 */
	public String llWithdrawCash(){
		String returnData = "";
		try {
			UsersLogin usersLogin  = (UsersLogin)request.getSession().getAttribute("usersLogin");
			//验证是否登录
			if(QwyUtil.isNullAndEmpty(usersLogin)){
				returnData = QwyUtil.getJSONString("noLogin", "请先登录");
				QwyUtil.printJSON(response, returnData);
				return null;
			}
			
			//验证参数是否为空
			if(QwyUtil.isNullAndEmpty(money) || QwyUtil.isNullAndEmpty(payPassword) ){
				returnData = QwyUtil.getJSONString("error", "参数错误");
				QwyUtil.printJSON(response, returnData);
				return null;
			}
			
			//获取用户IP
			String userIp=QwyUtil.getIpAddr(request);
			
			//获取用户信息
			Users users = rubean.getUsersById(usersLogin.getUsersId());
			
			
			//验证提现金额是否正确
			if(!QwyUtil.isPrice(money)){
				returnData = QwyUtil.getJSONString("error", "输入金额格式有误");
				QwyUtil.printJSON(response, returnData);
				return null;
			}
			Pattern pattern = Pattern.compile("^[0-9]+(\\.[0-9]{1,2})?$");//带有小数的金钱格式;			
			boolean isTrue = pattern.matcher(money).matches();
			if(!isTrue){
				returnData = QwyUtil.getJSONString("error", "【提现金额】不能超过2位小数");
				QwyUtil.printJSON(response, returnData);
				return null;
			}
			
			//验证用户是否绑定银行卡
			Account account = myAccountBean.getAccountByUsersName(DESEncrypt.jieMiUsername(users.getUsername()), "1");
			if(QwyUtil.isNullAndEmpty(account)){
				returnData = QwyUtil.getJSONString("error", "请绑定银行卡");
				QwyUtil.printJSON(response, returnData);
				return null;
			}
			
			boolean isUpdateAccount = false;
			if(QwyUtil.isNullAndEmpty(account.getProvinceCode()) || QwyUtil.isNullAndEmpty(account.getCityCode()) || QwyUtil.isNullAndEmpty(account.getBraBankName())){
				if(QwyUtil.isNullAndEmpty(province)){
					returnData = QwyUtil.getJSONString("error", "请选择开户省份");
					QwyUtil.printJSON(response, returnData);
					return null;
				} else if(QwyUtil.isNullAndEmpty(city)){
					returnData = QwyUtil.getJSONString("error", "请选择开户城市");
					QwyUtil.printJSON(response, returnData);
					return null;
				} else if(QwyUtil.isNullAndEmpty(braBankName)){
					returnData = QwyUtil.getJSONString("error", "请选择开户支行");
					QwyUtil.printJSON(response, returnData);
					return null;
				}
				account.setProvinceCode(province);
				account.setCityCode(city);
				account.setBraBankName(braBankName);
				isUpdateAccount = true;
			}
			
			//验证交易密码
			Users usersByPayPassword=bean.findUsersByUsernameAndPayPassword(DESEncrypt.jieMiUsername(users.getUsername()), payPassword);
			if(QwyUtil.isNullAndEmpty(usersByPayPassword)){
				returnData = QwyUtil.getJSONString("error", "交易密码错误");
				QwyUtil.printJSON(response, returnData);
				return null;
			}
			
			//验证当天提现次数
			List<TxRecord> records=bean.findRecordsByUid(users.getId(), 1, 20, true, true);
			if(!QwyUtil.isNullAndEmpty(records)){
				returnData = QwyUtil.getJSONString("error", "一天只能提现一次");
				QwyUtil.printJSON(response, returnData);
				return null;
			}
			if(QwyUtil.parToDouble(money).intValue()<1){
				returnData = QwyUtil.getJSONString("error", "至少提现1元！");
				QwyUtil.printJSON(response, returnData);
				return null;
			}
			
			//验证账户余额是否充足
			UsersInfo usersInfo=users.getUsersInfo();
			if(usersInfo.getLeftMoney()<QwyUtil.calcNumber(money, 100, "*").doubleValue()){
				returnData = QwyUtil.getJSONString("error", "账户余额不足！");
				QwyUtil.printJSON(response, returnData);
				return null;
			}
			
			if(QwyUtil.isPrice(money)){
//				String callUrl=call_url+request.getServletContext().getContextPath()+"/Product/llwithdrawCallPay";
				String callUrl=Configuration.getInstance().getValue("call_pay_url")+request.getServletContext().getContextPath()+"/Product/callBack!llwithdrawCallPay.action";
				System.out.println("提现回调地址"+callUrl);
				log.info("提现回调地址"+callUrl);
				String message = bean.llCashPay(users.getId(), QwyUtil.parToDouble(money), userIp, callUrl,account,isUpdateAccount);
				QwyUtil.printJSON(response, message);
				return null;
			}else{
				returnData = QwyUtil.getJSONString("error", "只能输入数值");
				QwyUtil.printJSON(response, returnData);
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage(), e);
		} catch (Error e) {
				e.printStackTrace();
				log.error(e.getMessage(), e);
		}
		try {
			QwyUtil.printJSON(response, QwyUtil.getJSONString("error","提现失败,请联系客服"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	/**
	 * 查询省份
	 * @return
	 */
	public String queryProvince(){
		try {
			List<Map<String,String>> list = bean.queryProvince();
			JSONArray jsonArray = JSONArray.fromObject(list);
			QwyUtil.printJSON(response, jsonArray.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 通过省份查询地市
	 * @return
	 */
	public String queryCity(){
		try {
//			province = "广东省";
			List<Map<String,String>> list = bean.queryCity(province);
			JSONArray jsonArray = JSONArray.fromObject(list);
			QwyUtil.printJSON(response, jsonArray.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 查询支行
	 * @return
	 */
	public String querySubbranch(){
		try {
			UsersLogin usersLogin  = (UsersLogin)request.getSession().getAttribute("usersLogin");
			//验证是否登录
			if(QwyUtil.isNullAndEmpty(usersLogin)){
				return "";
			}
			user = rubean.getUsersById(usersLogin.getUsersId());
			account = myAccountBean.getAccountByUsersName(DESEncrypt.jieMiUsername(user.getUsername()), "1");
			if(QwyUtil.isNullAndEmpty(account)){
				QwyUtil.printJSON(response, QwyUtil.getJSONString("error","您未绑卡，充值即可完成绑卡"));
				return null;
			}
			String jsonStr = bean.findCodeByBankInfo(DESEncrypt.jieMiBankCard(account.getBankAccount()),city);
			if(!jsonStr.contains("大额行号查询失败")){
				JSONObject reqObj = JSONObject.fromObject(jsonStr);
				QwyUtil.printJSON(response, reqObj.get("card_list").toString());
			}else{
				QwyUtil.printJSON(response, QwyUtil.getJSONString("error","请重新选择开户行"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static void main(String[] args) throws Exception {
		//1、21.12、12.0、0.1222
		/*Pattern pattern = Pattern.compile("^[0-9]+(\\.[0-9]{1,2})?$");
		Matcher matcher = pattern.matcher("21");
		System.out.println(matcher.matches());*/
		double d = 8103000000d*0.01;
		System.out.println(d);
		System.out.println(QwyUtil.calcNumber(d, 0.01, "*").toString());
		System.out.println(new BigDecimal(QwyUtil.calcNumber(d, 0.01, "*").doubleValue()).toString());
	}


	public String getRechargeMoney() {
		return rechargeMoney;
	}


	public void setRechargeMoney(String rechargeMoney) {
		this.rechargeMoney = rechargeMoney;
	}


	public String getPayType() {
		return payType;
	}


	public void setPayType(String payType) {
		this.payType = payType;
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


	public String getPayPassword() {
		return payPassword;
	}


	public void setPayPassword(String payPassword) {
		this.payPassword = payPassword;
	}


	public String getPd_FrpId() {
		return pd_FrpId;
	}


	public void setPd_FrpId(String pd_FrpId) {
		this.pd_FrpId = pd_FrpId;
	}


	public String getRecordType() {
		return recordType;
	}


	public void setRecordType(String recordType) {
		this.recordType = recordType;
	}

	public String getIdcard() {
		return idcard;
	}

	public void setIdcard(String idcard) {
		this.idcard = idcard;
	}


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public PayOrder getPayOrder() {
		return payOrder;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getBraBankName() {
		return braBankName;
	}

	public void setBraBankName(String braBankName) {
		this.braBankName = braBankName;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public Users getUser() {
		return user;
	}

	public void setUser(Users user) {
		this.user = user;
	}

	public String getIdcardno() {
		return idcardno;
	}

	public void setIdcardno(String idcardno) {
		this.idcardno = idcardno;
	}

	public String getCardno() {
		return cardno;
	}

	public void setCardno(String cardno) {
		this.cardno = cardno;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setMoney(String money) {
		this.money = money;
	}

	public void setPayOrder(PayOrder payOrder) {
		this.payOrder = payOrder;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
	
	
}
