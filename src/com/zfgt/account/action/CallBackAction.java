package com.zfgt.account.action;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.apache.struts2.config.Namespace;
import org.apache.struts2.config.ParentPackage;
import org.apache.struts2.config.Result;
import org.apache.struts2.config.Results;

import com.alibaba.fastjson.JSON;
import com.util.encrypt.AES;
import com.util.encrypt.EncryUtil;
import com.util.encrypt.RSA;
import com.zfgt.account.bean.MyAccountBean;
import com.zfgt.account.bean.UserRechargeBean;
import com.zfgt.account.bean.ValidateBean;
import com.zfgt.account.dao.UserRechargeDAO;
import com.zfgt.common.action.BaseAction;
import com.zfgt.common.bean.SystemConfigBean;
import com.zfgt.common.bean.YiBaoPayBean;
import com.zfgt.common.lianlian.pay.utils.EnvConstants;
import com.zfgt.common.lianlian.pay.utils.LLPayUtil;
import com.zfgt.common.lianlian.pay.utils.PayDataBean;
import com.zfgt.common.lianlian.pay.utils.RetBean;
import com.zfgt.common.util.DESEncrypt;
import com.zfgt.common.util.LockHolder;
import com.zfgt.common.util.MyRedis;
import com.zfgt.common.util.MySynchronized;
import com.zfgt.common.util.QwyUtil;
import com.zfgt.common.yeepay.Configuration;
import com.zfgt.common.yeepay.PaymentForOnlineService;
import com.zfgt.login.bean.RegisterUserBean;
import com.zfgt.orm.Account;
import com.zfgt.orm.CzRecord;
import com.zfgt.orm.SystemConfig;
import com.zfgt.orm.TxRecord;
import com.zfgt.orm.Users;
import com.zfgt.orm.UsersInfo;

import net.sf.json.JSONObject;

/**
 * 用户充值提现Action层;<br>
 * 接收充值的金额;接收提现的金额;
 * 
 * @author qwy
 *
 * @createTime 2015-4-27上午9:58:44
 */
@SuppressWarnings("serial")
@ParentPackage("struts-default")
@Namespace("/Product")
// 理财产品
@Results({ @Result(name = "myAccount", value = "/Product/User/myAccount.jsp"), @Result(name = "recharge", value = "/Product/User/recharge.jsp"),
		@Result(name = "getMoney", value = "/Product/User/getMoney.jsp"), @Result(name = "login", value = "/Product/login.jsp"), @Result(name = "error", value = "/Product/error.jsp"),
		@Result(name = "yeepay", value = "/Product/chinabankpay/reqpay.jsp"),
		@Result(name = "SUCCESS", value = "/Product/User/myAccount!showMyAccount.action", type = org.apache.struts2.dispatcher.ServletRedirectResult.class)

})
public class CallBackAction extends BaseAction {
	private static Logger log = Logger.getLogger(CallBackAction.class);

	private static SimpleDateFormat fmyyyyMM = new SimpleDateFormat("yyyyMMdd");
	@Resource
	private UserRechargeBean bean;
	@Resource
	private UserRechargeDAO dao;
	@Resource
	private ValidateBean validateBean;
	@Resource
	private MyAccountBean myAccountBean;
	@Resource
	private YiBaoPayBean yiBaoPayBean;
	@Resource
	private SystemConfigBean systemConfigBean;
	private ResourceBundle resb1 = ResourceBundle.getBundle("payapi");
	private String myMerchantaccount = resb1.getString("payapi.paytest_merchantaccount");
	// 从配置文件读取易宝分配的公钥
	private String yibaoPublicKey = resb1.getString("payapi.paytest_yibao_publickey");
	// 从配置文件读取商户自己的私钥
	private String merchantPrivateKey = resb1.getString("payapi.paytest_merchant_privatekey");

	// private HttpServletRequest request;

	private String rechargeMoney;
	private String payType = "0";
	private Integer currentPage = 1;
	private Integer pageSize = 25;
	private String payPassword;
	private String pd_FrpId;
	private String recordType;
	@Resource
	private RegisterUserBean registerUserBean;
	@Resource
	private UserRechargeBean userRechargeBean;

	/**
	 * 返回地址
	 * 
	 * @return
	 */
	public String callPay() {
		try {
			log.info("接收回调...");
			request = getRequest();
			String keyValue = QwyUtil.formatString(Configuration.getInstance().getValue("keyValue")); // 商家密钥
			if (!QwyUtil.isNullAndEmpty(request)) {
				String r0_Cmd = QwyUtil.formatString(request.getParameter("r0_Cmd")); // 业务类型
				String p1_MerId = QwyUtil.formatString(Configuration.getInstance().getValue("p1_MerId")); // 商户编号
				String r1_Code = QwyUtil.formatString(request.getParameter("r1_Code"));// 支付结果
				String r2_TrxId = QwyUtil.formatString(request.getParameter("r2_TrxId"));// 易宝支付交易流水号
				String r3_Amt = QwyUtil.formatString(request.getParameter("r3_Amt"));// 支付金额
				String r4_Cur = QwyUtil.formatString(request.getParameter("r4_Cur"));// 交易币种
				String r5_Pid = new String(QwyUtil.formatString(request.getParameter("r5_Pid")).getBytes("iso-8859-1"), "gbk");// 商品名称
				String r6_Order = QwyUtil.formatString(request.getParameter("r6_Order"));// 商户订单号
				String r7_Uid = QwyUtil.formatString(request.getParameter("r7_Uid"));// 易宝支付会员ID
				String r8_MP = new String(QwyUtil.formatString(request.getParameter("r8_MP")).getBytes("iso-8859-1"), "gbk");// 商户扩展信息
				String r9_BType = QwyUtil.formatString(request.getParameter("r9_BType"));// 交易结果返回类型
				String hmac = QwyUtil.formatString(request.getParameter("hmac"));// 签名数据
				boolean isOK = false;
				// 校验返回数据包
				isOK = PaymentForOnlineService.verifyCallback(hmac, p1_MerId, r0_Cmd, r1_Code, r2_TrxId, r3_Amt, r4_Cur, r5_Pid, r6_Order, r7_Uid, r8_MP, r9_BType, keyValue);
				log.info("接收回调...数据检验:  " + isOK);
				if (isOK) {
					// 在接收到支付结果通知后，判断是否进行过业务逻辑处理，不要重复进行业务逻辑处理
					if (r1_Code.equals("1")) {
						log.info("接收回调...回调编码:  " + r1_Code);
						// 产品通用接口支付成功返回-浏览器重定向
						/*
						 * if (r9_BType.equals("1")) {
						 * getResponse().encodeRedirectUrl(
						 * "http://www.baiyimao.com:9090"); //
						 * 产品通用接口支付成功返回-服务器点对点通讯 } else if
						 * (r9_BType.equals("2")) {
						 */
						CzRecord record = bean.getCzRecordByOrderId(r6_Order);
						log.info("接收回调...充值记录状态:  " + record.getStatus());
						if (record.getStatus().equals("0")) {
							log.info("接收回调...准备插入记录:  ");
							record.setStatus("1");
							record.setYbOrderId(r2_TrxId);
							record.setCheckTime(new Date());
							record.setNote("充值成功");
							bean.updateRecord(record);
							if (bean.usreRecharge(record.getUsersId(), record.getMoney(), " cz", "网银支付", "网银充值")) {
								// 如果在发起交易请求时
								// 设置使用应答机制时，必须应答以"success"开头的字符串，大小写不敏感
								log.info("接收回调...插入数据成功:  ");
								getResponse().getWriter().write("SUCCESS");
								// 产通用接口支付成功返回-电话支付返回
							}
						}
						// }
					}
				} else {
					getResponse().getWriter().write("交易签名被篡改!");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error("操作异常: ", e);
		}
		return "SUCCESS";
	}

	/**
	 * 充值回调接口
	 * 
	 * @return
	 */
	public String directCallbackurl() {
		try {
			System.out.println("充值回调接口");
			log.info("充值回调接口");
			request = getRequest();
			if (!QwyUtil.isNullAndEmpty(request)) {
				// 对易宝返回的结果进行验签
				String yb_data = QwyUtil.formatString(request.getParameter("data")); // 易宝支付返回的业务数据密文
				log.info("yb_data" + yb_data);
				String yb_encryptkey = QwyUtil.formatString(request.getParameter("encryptkey")); // 易宝支付返回的对ybAesKey加密后的密文
				log.info("yb_encryptkey" + yb_encryptkey);
				boolean passSign = EncryUtil.checkDecryptAndSign(yb_data, yb_encryptkey, yibaoPublicKey, merchantPrivateKey);

				if (passSign) {
					// 验签通过
					String yb_aeskey = RSA.decrypt(yb_encryptkey, merchantPrivateKey);
					System.out.println("易宝支付给商户" + myMerchantaccount + "返回结果中使用的aeskey为：" + yb_aeskey);
					log.info("yb_aeskey" + yb_aeskey);
					String payresult_view = AES.decryptFromBase64(yb_data, yb_aeskey);
					System.out.println("易宝支付给商户" + myMerchantaccount + "结果（aes解密后的明文）：" + payresult_view);
					log.info("payresult_view" + payresult_view);
					JSONObject obj = JSONObject.fromObject(payresult_view);
					System.out.println("转换为ＪＳＯＮ对象" + JSONObject.fromObject(obj));
					log.info("转换为ＪＳＯＮ对象" + JSONObject.fromObject(obj));
					// CallBack callBack=(CallBack) JSONObject.toBean(obj,
					// CallBack.class);
					// System.out.println("转换为实体类"+JSONObject.toBean(obj,
					// CallBack.class));
					// log.info("转换为实体类"+JSONObject.toBean(obj,
					// CallBack.class));
					if (!QwyUtil.isNullAndEmpty(obj)) {
						if (myMerchantaccount.equals(QwyUtil.get(obj, "merchantaccount"))) {
							log.info("编号相等");
							CzRecord record = bean.getCzRecordByOrderId(QwyUtil.get(obj, "orderid"));
							log.info("获取充值记录");
							if (!QwyUtil.isNullAndEmpty(record)) {
								log.info("充值记录不为空" + record.getId());
								if ("0".equals(record.getStatus())) {
									if ("1".equals(QwyUtil.get(obj, "status"))) {
										boolean isRecharge = bean.usreRecharge(Long.parseLong(QwyUtil.get(obj, "identityid")), Double.parseDouble(QwyUtil.get(obj, "amount")), "cz", "第三方支付",
												"通过【易宝支付】进行充值");
										if (isRecharge) {
											record.setStatus("1");
											record.setNote("充值成功");
										} else {
											record.setStatus("0");
											record.setNote("易宝支付已充值成功,数据库插入失败!");
										}
										record.setCheckTime(new Date());
										dao.saveOrUpdate(record);
										log.info("充值成功");
										response.getWriter().print("”SUCCESS");
									} else if ("0".equals(QwyUtil.get(obj, "status"))) {
										record.setStatus("2");
										record.setErrorCode(QwyUtil.get(obj, "errorcode"));
										record.setNote(QwyUtil.get(obj, "errormsg"));
										record.setCheckTime(new Date());
										log.info("充值失败");
										dao.saveOrUpdate(record);
									}
								}
							}
						}
					}
				} else {
					System.out.println("验签未通过");
					log.info("验签未通过");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error("操作异常: ", e);
		}
		return null;
	}

	/**
	 * 提现回调地址
	 * 
	 * @return
	 */
	public String withdrawCallPay() {
		try {
			System.out.println("提现回调接口");
			log.info("提现回调接口");
			request = getRequest();
			if (!QwyUtil.isNullAndEmpty(request)) {
				// 对易宝返回的结果进行验签
				String yb_data = QwyUtil.formatString(request.getParameter("data")); // 易宝支付返回的业务数据密文
				log.info("yb_data" + yb_data);
				String yb_encryptkey = QwyUtil.formatString(request.getParameter("encryptkey")); // 易宝支付返回的对ybAesKey加密后的密文
				log.info("yb_encryptkey" + yb_encryptkey);
				boolean passSign = EncryUtil.checkDecryptAndSign(yb_data, yb_encryptkey, yibaoPublicKey, merchantPrivateKey);

				if (passSign) {
					// 验签通过
					String yb_aeskey = RSA.decrypt(yb_encryptkey, merchantPrivateKey);
					System.out.println("易宝支付给商户" + myMerchantaccount + "返回结果中使用的aeskey为：" + yb_aeskey);
					log.info("yb_aeskey" + yb_aeskey);
					String payresult_view = AES.decryptFromBase64(yb_data, yb_aeskey);
					System.out.println("易宝支付给商户" + myMerchantaccount + "结果（aes解密后的明文）：" + payresult_view);
					log.info("payresult_view" + payresult_view);
					JSONObject obj = JSONObject.fromObject(payresult_view);
					System.out.println("转换为ＪＳＯＮ对象" + JSONObject.fromObject(obj));
					log.info("转换为ＪＳＯＮ对象" + JSONObject.fromObject(obj));
					// CallBack callBack=(CallBack) JSONObject.toBean(obj,
					// CallBack.class);
					// System.out.println("转换为实体类"+JSONObject.toBean(obj,
					// CallBack.class));
					// log.info("转换为实体类"+JSONObject.toBean(obj,
					// CallBack.class));
					if (!QwyUtil.isNullAndEmpty(obj)) {
						if (myMerchantaccount.equals(QwyUtil.get(obj, "merchantaccount"))) {
							log.info("编号相等");
							TxRecord record = bean.getTxRecordByRequestId(QwyUtil.get(obj, "requestid"));
							log.info("获取提现记录");
							if (!QwyUtil.isNullAndEmpty(record)) {
								log.info("提现不为空" + record.getId());
								if ("0".equals(record.getStatus())) {
									if ("SUCCESS".equals(QwyUtil.get(obj, "status"))) {
										String yblsh = obj.getString("ybdrawflowid");
										if (QwyUtil.isNullAndEmpty(record.getYbOrderId())) {
											record.setYbOrderId(yblsh);
										}
										record.setStatus("1");
										record.setNote("pc提现成功");
										record.setCheckTime(new Date());
										dao.saveOrUpdate(record);
										log.info("状态" + record.getStatus());
										log.info("提现成功");
										StringBuffer buffer = new StringBuffer();
										buffer.append(systemConfigBean.findSystemConfig().getSmsQm() + "尊敬的中泰理财用户，您于");
										buffer.append(QwyUtil.fmyyyyMMddHHmmss.format(record.getInsertTime()));
										buffer.append("提现");
										buffer.append(QwyUtil.calcNumber(record.getMoney(), 100, "/", 2).toString());
										buffer.append("元申请已成功提交银行，资金将于24小时内到达您的银行卡账户");
										buffer.append("（节假日顺延，含周六日）");
										buffer.append("，请注意查收。如有任何疑问，请致电中泰理财唯一官方客服：");
										buffer.append("400-806-5993。");
										log.info(buffer.toString());
										log.info("手机号码" + DESEncrypt.jieMiUsername(record.getUsers().getPhone()));
										registerUserBean.sendSms(DESEncrypt.jieMiUsername(record.getUsers().getPhone()), buffer.toString());
										response.getWriter().print("SUCCESS");
									} else if ("FAILURE".equals(QwyUtil.get(obj, "status"))) {
										record.setStatus("2");
										record.setErrorCode("FAILURE");
										record.setNote("提现失败");
										record.setCheckTime(new Date());
										log.info("提现失败");
										dao.saveOrUpdate(record);
									} else if ("REFUND".equals(QwyUtil.get(obj, "status"))) {
										record.setStatus("2");
										record.setErrorCode("REFUND");
										record.setNote("提现失败");
										record.setCheckTime(new Date());
										log.info("REFUND");
										dao.saveOrUpdate(record);
									} else if ("UNKNOW".equals(QwyUtil.get(obj, "status"))) {
										record.setStatus("2");
										record.setErrorCode("UNKNOW");
										record.setNote("提现失败");
										record.setCheckTime(new Date());
										log.info("提现失败");
										dao.saveOrUpdate(record);
									}
								}
							}
						}
					}
				} else {
					System.out.println("验签未通过");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error("操作异常: ", e);
		}
		return null;
	}

	/**
	 * 充值同步回调接口(连连支付)
	 * 
	 * @return
	 */
	public String CzReturnUrl() {
		String json = "";
		String czCall = "";
		try {
			MyRedis redis = new MyRedis();
			log.info("进入连连充值同步通知数据接收处理");
			RetBean retBean = new RetBean();
			request = getRequest();
			String result_pay = request.getParameter("result_pay");
			if (QwyUtil.isNullAndEmpty(result_pay)) {
				retBean.setRet_code("9999");
				retBean.setRet_msg("交易失败");
				log.info(JSONObject.fromObject(retBean));
				json = QwyUtil.getJSONString("error", "充值异常,请联系客服;");
				QwyUtil.printJSON(getResponse(), json);
				return "SUCCESS";

			}
			log.info("接收连连充值同步通知数据：【" + result_pay + "】");

			PayDataBean payDataBean = new PayDataBean();
			try {
				// 获取post请求参数
				String oid_partner = request.getParameter("oid_partner");
				String sign_type = request.getParameter("sign_type");
				String sign = request.getParameter("sign");
				String dt_order = request.getParameter("dt_order");
				String no_order = request.getParameter("no_order");
				String oid_paybill = request.getParameter("oid_paybill");
				String money_order = request.getParameter("money_order");
				String settle_date = request.getParameter("settle_date");
				String info_order = request.getParameter("info_order");
				String pay_type = request.getParameter("pay_type");
				String bank_code = request.getParameter("bank_code");
				// 处理同步通知对象
				payDataBean.setOid_partner(oid_partner);
				payDataBean.setSign_type(sign_type);
				payDataBean.setDt_order(dt_order);
				payDataBean.setNo_order(no_order);
				payDataBean.setOid_paybill(oid_paybill);
				payDataBean.setMoney_order(money_order);
				payDataBean.setSettle_date(settle_date);
				payDataBean.setInfo_order(info_order);
				payDataBean.setPay_type(pay_type);
				payDataBean.setBank_code(bank_code);

				payDataBean.setResult_pay(result_pay);

				payDataBean.setSign(sign);
				System.out.println("连连返回的sign:" + sign);
				JSONObject reqJson = JSONObject.fromObject(payDataBean);

				if (!LLPayUtil.checkSign(reqJson.toString(), EnvConstants.RSA_YT_PUBLIC, EnvConstants.MD5_KEY)) {
					retBean.setRet_code("9999");
					retBean.setRet_msg("交易失败");
					log.info(JSONObject.fromObject(retBean));
					log.info("连连充值同步通知验签失败");
					log.info(JSONObject.fromObject(retBean));
					json = QwyUtil.getJSONString("error", "充值异常,请联系客服;");
					QwyUtil.printJSON(getResponse(), json);
					return "SUCCESS";
				}
			} catch (Exception e) {
				log.info("连连充值同步通知报文解析异常：" + e);
				retBean.setRet_code("9999");
				retBean.setRet_msg("交易失败");
				json = QwyUtil.getJSONString("error", "充值异常,请联系客服;");
				QwyUtil.printJSON(getResponse(), json);
				return "SUCCESS";
			}

			log.info("连连充值同步通知数据接收处理成功");

			// TODO:更新订单，发货等后续处理
			if (!QwyUtil.isNullAndEmpty(payDataBean)) {
				czCall = payDataBean.getNo_order();
				MySynchronized.lock("czCall" + czCall);
				synchronized (LockHolder.getLock(payDataBean.getNo_order())) {
					CzRecord record = userRechargeBean.getCzRecordByOrderId(payDataBean.getNo_order());
					log.info("获取充值记录");
					if (!QwyUtil.isNullAndEmpty(record)) {
						log.info("充值记录不为空" + record.getId());
						log.info("充值记录状态" + record.getStatus());
						if ("0".equals(record.getStatus())) {
							if ("SUCCESS".equals(payDataBean.getResult_pay())) {
								String accountId = redis.get(payDataBean.getNo_order());
								if (!QwyUtil.isNullAndEmpty(accountId)) {
									// 修改用户信息
									Account account = synchronizedAccount(accountId);
									redis.del(payDataBean.getNo_order());
									redis.del(account.getUsersId() + "");
								}
								if (QwyUtil.isNullAndEmpty(payDataBean.getMoney_order())) {
									json = QwyUtil.getJSONString("error", "充值异常,请联系客服;");
									QwyUtil.printJSON(getResponse(), json);
									return "SUCCESS";
								}

								boolean isRecharge = userRechargeBean.usreRecharge(record.getUsersId(), QwyUtil.calcNumber(payDataBean.getMoney_order(), 100, "*").doubleValue(), "cz", "第三方支付",
										"通过【连连支付】进行充值");
								if (isRecharge) {
									record.setStatus("1");
									record.setNote("充值成功");
								} else {
									record.setStatus("0");
									record.setNote("连连支付已充值成功,数据库插入失败!");
								}
								record.setCheckTime(new Date());
								dao.saveOrUpdate(record);
								log.info("充值成功");
								retBean.setRet_code("0000");
								retBean.setRet_msg("交易成功");
								log.info("支付同步通知数据接收处理成功");
								json = QwyUtil.getJSONString("ok", "充值成功;");
								QwyUtil.printJSON(getResponse(), json);
								return "SUCCESS";
							} else {
								record.setStatus("2");
								record.setErrorCode(payDataBean.getResult_pay());
								record.setNote(payDataBean.getInfo_order());
								record.setCheckTime(new Date());
								log.info("充值失败");
								dao.saveOrUpdate(record);
								retBean.setRet_code("9999");
								retBean.setRet_msg("交易失败");
								json = QwyUtil.getJSONString("no", "充值失败;");
								QwyUtil.printJSON(getResponse(), json);
								return "SUCCESS";
							}
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error("操作异常: ", e);
		} finally {
			// 解锁
			MySynchronized.unLock("czCall" + czCall);

		}
		return "SUCCESS";

	}

	// (同步锁处理)修改用户帐号信息
	public Account synchronizedAccount(String accountId) {
		Account account = null;
		try {
			account = myAccountBean.getAccountById(accountId);
			if (QwyUtil.isNullAndEmpty(account)) {
				log.info("帐号" + accountId + "不存在");
				return account;
			}
			synchronized (LockHolder.getLock(account.getUsersId())) {
				log.info("锁定充值用户id: " + account.getUsersId());
				// 判断是否该用户拥有已绑定的帐号
				Account checkAccount = myAccountBean.getAccountByUsersId(account.getUsersId(), "1");
				if (QwyUtil.isNullAndEmpty(checkAccount)) {
					Users users = account.getUsers();
					UsersInfo usersInfo = users.getUsersInfo();
					account.setStatus("0");
					usersInfo.setIsBindBank("1");

					if (!QwyUtil.isNullAndEmpty(account.getIdcard())) {
						usersInfo.setIdcard(account.getIdcard());
						Object[] objIDCard = QwyUtil.getInfoByIDCard(DESEncrypt.jieMiIdCard(account.getIdcard()));
						if (!QwyUtil.isNullAndEmpty(objIDCard)) {
							usersInfo.setSex(objIDCard[0] + "");
							usersInfo.setAge(objIDCard[1] + "");
							usersInfo.setBirthday(fmyyyyMM.parse(objIDCard[2] + ""));
						}
						usersInfo.setIsVerifyIdcard("1");

					}
					usersInfo.setRealName(account.getBankAccountName());
					dao.saveOrUpdate(usersInfo);
					dao.saveOrUpdate(account);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error("充值同步回调处理同步安全异常");
		}
		return account;
	}

	/**
	 * 充值异步回调接口(连连支付)
	 * 
	 * @return
	 */
	public String CzCallbackurl() {
		String czCall = "";
		try {
			MyRedis redis = new MyRedis();
			log.info("进入连连充值异步通知数据接收处理");
			RetBean retBean = new RetBean();
			String reqStr = QwyUtil.readReqStr(request);
			if (QwyUtil.isNullAndEmpty(reqStr)) {
				retBean.setRet_code("9999");
				retBean.setRet_msg("交易失败");
				log.info(JSONObject.fromObject(retBean));
				response.getWriter().write(JSON.toJSONString(retBean));
				response.getWriter().flush();
				return null;
			}
			log.info("接收连连充值异步通知数据：【" + reqStr + "】");
			try {

				if (!LLPayUtil.checkSign(reqStr, EnvConstants.RSA_YT_PUBLIC, EnvConstants.MD5_KEY)) {
					retBean.setRet_code("9999");
					retBean.setRet_msg("交易失败");
					response.getWriter().write(JSON.toJSONString(retBean));
					response.getWriter().flush();
					log.info(JSONObject.fromObject(retBean));
					log.info("连连充值异步通知验签失败");
					log.info(JSONObject.fromObject(retBean));
					return null;
				}
			} catch (Exception e) {
				log.info("连连充值异步通知报文解析异常：" + e);
				retBean.setRet_code("9999");
				retBean.setRet_msg("交易失败");
				response.getWriter().write(JSON.toJSONString(retBean));
				response.getWriter().flush();
				return null;
			}

			log.info("连连充值异步通知数据接收处理成功");
			// 解析异步通知对象
			PayDataBean payDataBean = JSON.parseObject(reqStr, PayDataBean.class);
			if (!QwyUtil.isNullAndEmpty(payDataBean)) {
				czCall = payDataBean.getNo_order();
				MySynchronized.lock("czCall" + czCall);
				synchronized (LockHolder.getLock(payDataBean.getNo_order())) {
					CzRecord record = userRechargeBean.getCzRecordByOrderId(payDataBean.getNo_order());
					log.info("获取充值记录");
					if (!QwyUtil.isNullAndEmpty(record)) {
						log.info("充值记录不为空" + record.getId());
						log.info("充值记录状态" + record.getStatus());
						if ("0".equals(record.getStatus())) {
							if ("SUCCESS".equals(payDataBean.getResult_pay())) {
								String accountId = redis.get(payDataBean.getNo_order());
								if (!QwyUtil.isNullAndEmpty(accountId)) {
									Account account = synchronizedAccount(accountId);
									redis.del(payDataBean.getNo_order());
									redis.del(account.getUsersId() + "");
								}
								if (QwyUtil.isNullAndEmpty(payDataBean.getMoney_order())) {
									return null;
								}

								boolean isRecharge = userRechargeBean.usreRecharge(record.getUsersId(), QwyUtil.calcNumber(payDataBean.getMoney_order(), 100, "*").doubleValue(), "cz", "第三方支付",
										"通过【连连支付】进行充值");
								if (isRecharge) {
									record.setStatus("1");
									record.setNote("充值成功");
								} else {
									record.setStatus("0");
									record.setNote("连连支付已充值成功,数据库插入失败!");
								}
								record.setCheckTime(new Date());
								dao.saveOrUpdate(record);
								log.info("充值成功");
								retBean.setRet_code("0000");
								retBean.setRet_msg("交易成功");
								response.getWriter().write(JSON.toJSONString(retBean));
								response.getWriter().flush();
								log.info("支付异步通知数据接收处理成功");
								return null;
							} else {
								record.setStatus("2");
								record.setErrorCode(payDataBean.getResult_pay());
								record.setNote(payDataBean.getInfo_order());
								record.setCheckTime(new Date());
								log.info("充值失败");
								dao.saveOrUpdate(record);
								retBean.setRet_code("9999");
								retBean.setRet_msg("交易失败");
								response.getWriter().write(JSON.toJSONString(retBean));
								response.getWriter().flush();
								return null;
							}
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error("操作异常: ", e);
		} finally {
			// 解锁
			MySynchronized.unLock("czCall" + czCall);

		}
		return null;

	}

	/**
	 * 提现回调接口(连连支付)
	 * 
	 * @return
	 */
	public String TxCallbackurl() {
		try {
			log.info("进入连连提现异步通知数据接收处理");
			RetBean retBean = new RetBean();
			String reqStr = QwyUtil.readReqStr(request);
			if (QwyUtil.isNullAndEmpty(reqStr)) {
				retBean.setRet_code("9999");
				retBean.setRet_msg("交易失败");
				log.info(JSONObject.fromObject(retBean));
				response.getWriter().write(JSON.toJSONString(retBean));
				response.getWriter().flush();
				return null;
			}
			log.info("接收连连提现异步通知数据：【" + reqStr + "】");
			log.info("接收连连提现异步通知数据：【" + reqStr + "】");
			try {

				if (!LLPayUtil.checkSign(reqStr, EnvConstants.RSA_YT_PUBLIC, EnvConstants.MD5_KEY)) {
					retBean.setRet_code("9999");
					retBean.setRet_msg("交易失败");
					response.getWriter().write(JSON.toJSONString(retBean));
					response.getWriter().flush();
					log.info(JSONObject.fromObject(retBean));
					log.info("连连提现异步通知验签失败");
					log.info(JSONObject.fromObject(retBean));
					return null;
				}
			} catch (Exception e) {
				log.info("连连提现异步通知报文解析异常：" + e);
				retBean.setRet_code("9999");
				retBean.setRet_msg("交易失败");
				response.getWriter().write(JSON.toJSONString(retBean));
				response.getWriter().flush();
				return null;
			}

			log.info("连连提现异步通知数据接收处理成功");
			// 解析异步通知对象
			PayDataBean payDataBean = JSON.parseObject(reqStr, PayDataBean.class);
			if (!QwyUtil.isNullAndEmpty(payDataBean)) {
				synchronized (payDataBean.getNo_order()) {
					TxRecord record = bean.getTxRecordByRequestId(payDataBean.getNo_order());
					log.info("获取提现记录");
					if (!QwyUtil.isNullAndEmpty(record)) {
						log.info("提现不为空" + record.getId());
						if ("0".equals(record.getStatus())) {
							if ("SUCCESS".equals(payDataBean.getResult_pay())) {
								String yblsh = payDataBean.getOid_paybill();
								if (QwyUtil.isNullAndEmpty(record.getYbOrderId())) {
									record.setYbOrderId(yblsh);
								}
								record.setStatus("1");
								record.setNote("手机提现成功");
								record.setCheckTime(new Date());
								dao.saveOrUpdate(record);
								log.info("状态" + record.getStatus());
								log.info("提现成功");
								StringBuffer buffer = new StringBuffer();
								buffer.append(systemConfigBean.findSystemConfig().getSmsQm() + "尊敬的中泰理财用户，您于");
								buffer.append(QwyUtil.fmyyyyMMddHHmmss.format(record.getInsertTime()));
								buffer.append("提现");
								buffer.append(QwyUtil.calcNumber(record.getMoney(), 100, "/", 2).toString());
								buffer.append("元申请已成功提交银行，资金将于24小时内到达您的银行卡账户");
								buffer.append("（节假日顺延，含周六日）");
								buffer.append("，请注意查收。如有任何疑问，请致电中泰理财唯一官方客服：");
								buffer.append("400-806-5993。");
								// SMSUtil.sendYzm2(DESEncrypt.jieMiUsername(record.getUsers().getPhone()),
								// null, buffer.toString());
								log.info(buffer.toString());
								log.info("手机号码" + DESEncrypt.jieMiUsername(record.getUsers().getPhone()));
								registerUserBean.sendSms(DESEncrypt.jieMiUsername(record.getUsers().getPhone()), buffer.toString());
								retBean.setRet_code("0000");
								retBean.setRet_msg("交易成功");
								response.getWriter().write(JSON.toJSONString(retBean));
								response.getWriter().flush();
								log.info("支付异步通知数据接收处理成功");
							} else {
								record.setStatus("2");
								record.setErrorCode(payDataBean.getResult_pay());
								record.setNote(payDataBean.getInfo_order());
								record.setCheckTime(new Date());
								log.info("提现失败");
								dao.saveOrUpdate(record);
								retBean.setRet_code("9999");
								retBean.setRet_msg("交易失败");
								response.getWriter().write(JSON.toJSONString(retBean));
								response.getWriter().flush();
								return null;
							}
						}

					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage(), e);
		} catch (Error e) {
			e.printStackTrace();
			log.error(e.getMessage(), e);
		}
		return null;
	}

	/**
	 * 连连提现记录回调
	 * 
	 * @param request
	 * @param responsen
	 * @return
	 */

	public String llwithdrawCallPay() {
		try {
			log.info("进入连连提现异步通知数据接收处理");
			RetBean retBean = new RetBean();
			String reqStr = QwyUtil.readReqStr(request);
			if (QwyUtil.isNullAndEmpty(reqStr)) {
				retBean.setRet_code("9999");
				retBean.setRet_msg("交易失败");
				log.info(JSONObject.fromObject(retBean));
				response.getWriter().write(JSON.toJSONString(retBean));
				response.getWriter().flush();
				return null;
			}
			log.info("接收连连提现异步通知数据：【" + reqStr + "】");
			log.info("接收连连提现异步通知数据：【" + reqStr + "】");
			try {
				if (!LLPayUtil.checkSign(reqStr, EnvConstants.RSA_YT_PUBLIC, EnvConstants.MD5_KEY)) {
					retBean.setRet_code("9999");
					retBean.setRet_msg("交易失败");
					response.getWriter().write(JSON.toJSONString(retBean));
					response.getWriter().flush();
					log.info(JSONObject.fromObject(retBean));
					log.info("连连提现异步通知验签失败");
					log.info(JSONObject.fromObject(retBean));
					return null;
				}
			} catch (Exception e) {
				log.info("连连提现异步通知报文解析异常：" + e);
				retBean.setRet_code("9999");
				retBean.setRet_msg("交易失败");
				response.getWriter().write(JSON.toJSONString(retBean));
				response.getWriter().flush();
				return null;
			}
			/*
			 * retBean.setRet_code("0000"); retBean.setRet_msg("交易成功");
			 * response.getWriter().write(JSON.toJSONString(retBean));
			 * response.getWriter().flush();
			 */
			log.info("连连提现异步通知数据接收处理成功");
			// 解析异步通知对象
			PayDataBean payDataBean = JSON.parseObject(reqStr, PayDataBean.class);
			if (!QwyUtil.isNullAndEmpty(payDataBean)) {
				synchronized (payDataBean.getNo_order()) {
					TxRecord record = bean.getTxRecordByRequestId(payDataBean.getNo_order());
					log.info("获取提现记录");
					if (!QwyUtil.isNullAndEmpty(record)) {
						log.info("提现不为空" + record.getId());
						if ("0".equals(record.getStatus())) {
							if ("SUCCESS".equals(payDataBean.getResult_pay())) {
								String yblsh = payDataBean.getOid_paybill();
								if (QwyUtil.isNullAndEmpty(record.getYbOrderId())) {
									record.setYbOrderId(yblsh);
								}
								record.setStatus("1");
								record.setNote("手机提现成功");
								record.setCheckTime(new Date());
								dao.saveOrUpdate(record);
								log.info("状态" + record.getStatus());
								log.info("提现成功");
								StringBuffer buffer = new StringBuffer();
								SystemConfig systemConfig = (SystemConfig) dao.findById(new SystemConfig(), 1L);
								buffer.append(systemConfig.getSmsQm() + "尊敬的中泰理财用户，您于");
								buffer.append(QwyUtil.fmyyyyMMddHHmmss.format(record.getInsertTime()));
								buffer.append("提现");
								buffer.append(QwyUtil.calcNumber(record.getMoney(), 100, "/", 2).toString());
								buffer.append("元申请已成功提交银行，资金将于24小时内到达您的银行卡账户");
								buffer.append("（节假日顺延，含周六日）");
								buffer.append("，请注意查收。如有任何疑问，请致电中泰理财唯一官方客服：");
								buffer.append("400-806-5993。");
								// SMSUtil.sendYzm2(DESEncrypt.jieMiUsername(record.getUsers().getPhone()),
								// null, buffer.toString());
								log.info(buffer.toString());
								log.info("手机号码" + DESEncrypt.jieMiUsername(record.getUsers().getPhone()));
								registerUserBean.sendSms(DESEncrypt.jieMiUsername(record.getUsers().getPhone()), buffer.toString());
								retBean.setRet_code("0000");
								retBean.setRet_msg("交易成功");
								response.getWriter().write(JSON.toJSONString(retBean));
								response.getWriter().flush();
								log.info("支付异步通知数据接收处理成功");
							} else {
								record.setStatus("2");
								record.setErrorCode(payDataBean.getResult_pay());
								record.setNote(payDataBean.getInfo_order());
								record.setCheckTime(new Date());
								log.info("提现失败");
								dao.saveOrUpdate(record);
								retBean.setRet_code("9999");
								retBean.setRet_msg("交易失败");
								response.getWriter().write(JSON.toJSONString(retBean));
								response.getWriter().flush();
								return null;
							}
						}

					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage(), e);
		} catch (Error e) {
			e.printStackTrace();
			log.error(e.getMessage(), e);
		}
		return null;
	}

	public static void main(String[] args) throws Exception {
		// 1、21.12、12.0、0.1222
		/*
		 * Pattern pattern = Pattern.compile("^[0-9]+(\\.[0-9]{1,2})?$");
		 * Matcher matcher = pattern.matcher("21");
		 * System.out.println(matcher.matches());
		 */
		double d = 8103000000d * 0.01;
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

}
