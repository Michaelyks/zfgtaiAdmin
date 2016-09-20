package com.zfgt.account.bean;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.hibernate.SessionFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.zfgt.account.dao.UserRechargeDAO;
import com.zfgt.admin.product.bean.BindCardRecordBean;
import com.zfgt.common.ApplicationContexts;
import com.zfgt.common.bean.LianLianPayBean;
import com.zfgt.common.bean.MyWalletBean;
import com.zfgt.common.bean.PlatformBean;
import com.zfgt.common.bean.YiBaoPayBean;
import com.zfgt.common.lianlian.pay.utils.EnvConstants;
import com.zfgt.common.lianlian.pay.utils.LLPayUtil;
import com.zfgt.common.lianlian.pay.utils.Md5Algorithm;
import com.zfgt.common.lianlian.pay.utils.conn.HttpRequestSimple;
import com.zfgt.common.lianlian.pay.utils.security.TraderRSAUtil;
import com.zfgt.common.lianlian.pay.utils.tx.CashBean;
import com.zfgt.common.util.DESEncrypt;
import com.zfgt.common.util.LockHolder;
import com.zfgt.common.util.MyRedis;
import com.zfgt.common.util.OrderNumerUtil;
import com.zfgt.common.util.PageUtil;
import com.zfgt.common.util.QwyUtil;
import com.zfgt.login.bean.RegisterUserBean;
import com.zfgt.orm.Account;
import com.zfgt.orm.Coupon;
import com.zfgt.orm.CzRecord;
import com.zfgt.orm.MCoinIncome;
import com.zfgt.orm.MCoinRecord;
import com.zfgt.orm.MProduct;
import com.zfgt.orm.TxRecord;
import com.zfgt.orm.Users;
import com.zfgt.orm.UsersInfo;

import net.sf.json.JSONObject;

/**
 * 用户充值Bean层;
 * 
 * @author qwy
 *
 * @createTime 2015-04-27 10:06:51
 */
@Service
public class UserRechargeBean {
	private static Logger log = Logger.getLogger(UserRechargeBean.class);
	@Resource
	private UserRechargeDAO dao;
	@Resource
	private MyWalletBean myWalletBean;
	@Resource
	private RegisterUserBean registerUserBean;
	@Resource
	private PlatformBean platformBean;
	// @Resource
	// private SystemConfigBean systemConfigBean;
	@Resource
	private YiBaoPayBean yiBaoPayBean;
	@Resource
	private LianLianPayBean lianLianPayBean;
	@Resource
	private BindCardRecordBean bean;
	@Resource
	private MyAccountBean myAccountBean;
	@Resource
	private UserRechargeBean userRechargeBean;

	/**
	 * 用户充值;
	 * 
	 * @param usersId
	 *            用户id
	 * @param money
	 *            操作金额;单位(分)
	 * @param operatedType
	 *            操作类别 cz:用户充值 tx:提现 zf:在线支付
	 * @param operatedWay
	 *            操作途径; 第三方支付;借记卡
	 * @param note
	 *            如: "用户从第三方充值金额到平台账户"
	 * @return true:充值成功; false:充值失败;
	 */
	public boolean usreRecharge(long usersId, double money, String operatedType, String operatedWay, String note) {
		log.info("UserRechargeBean.usreRecharge____用户充值,usersId:" + usersId);
		synchronized (LockHolder.getLock(usersId)) {
			ApplicationContext context = ApplicationContexts.getContexts();
			// SessionFactory sf = (SessionFactory)
			// context.getBean("sessionFactory");
			PlatformTransactionManager tm = (PlatformTransactionManager) context.getBean("transactionManager");
			TransactionStatus ts = tm.getTransaction(new DefaultTransactionDefinition());
			try {
				boolean isOk = myWalletBean.addTotalMoneyLeftMoney(usersId, money, "cz", operatedWay, note);
				if (isOk) {
					tm.commit(ts);
					log.info("UserRechargeBean.usreRecharge____用户充值成功");
					return true;
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				log.error("操作异常: ", e);
				log.info("UserRechargeBean.usreRecharge____用户充值失败");
			}
			tm.rollback(ts);
			return false;
		}

	}

	/**
	 * 用户提现;
	 * 
	 * @param usersId
	 *            用户id
	 * @param money
	 *            操作金额;单位(分)
	 * @param operatedType
	 *            操作类别 cz:用户充值 tx:提现 zf:在线支付
	 * @param operatedWay
	 *            操作途径; 第三方支付;借记卡
	 * @return true:充值成功; false:充值失败;
	 */
	public String usreGetCash(long usersId, double money, String operatedType, String operatedWay, String txRecordId) {
		log.info("UserRechargeBean.usreRecharge____用户提现,usersId:" + usersId);
		synchronized (LockHolder.getLock(usersId)) {
			TxRecord txRecord = (TxRecord) dao.findById(new TxRecord(), txRecordId);
			if (txRecord == null) {
				log.info("提现失败:没有找到提现记录");
				return "提现失败:没有找到提现记录";
			}
			Users users = (Users) dao.findById(new Users(), usersId);
			if (users == null) {
				log.info("提现失败:没有找到提现用户");
				return "提现失败:没有找到提现用户";
			}
			UsersInfo usersInfo = users.getUsersInfo();
			if (usersInfo == null) {
				log.info("提现失败:没有找到用户信息表");
				return "提现失败:没有找到用户信息表";
			}
			double leftMoney = usersInfo.getLeftMoney();// 可用余额
			if (money > leftMoney) {
				log.info("提现失败:用户账户余额不足");
				return "提现失败:用户账户余额不足";
			}
			ApplicationContext context = ApplicationContexts.getContexts();
			// SessionFactory sf = (SessionFactory)
			// context.getBean("sessionFactory");
			PlatformTransactionManager tm = (PlatformTransactionManager) context.getBean("transactionManager");
			TransactionStatus ts = tm.getTransaction(new DefaultTransactionDefinition());
			try {
				// 开启事务;
				boolean isOk = myWalletBean.subTotalMoneyFreezeMoney(usersId, money, "tx", operatedWay, "用户提现");
				if (isOk) {
					txRecord.setStatus("1");
					txRecord.setCheckTime(new Date());
					dao.saveOrUpdate(txRecord);
					HttpServletRequest request = ServletActionContext.getRequest();
					Users newUsers = (Users) dao.findById(new Users(), usersId);
					request.getSession().setAttribute("users", newUsers);
					// 事务提交;
					tm.commit(ts);
					return "";
				} else {
					log.info("提现失败:保存失败,事务回滚");
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				log.error("操作异常: ", e);
				log.info("UserRechargeBean.usreGetCash____用户提现失败");
			}
			tm.rollback(ts);
			txRecord.setStatus("2");
			txRecord.setCheckTime(new Date());
			dao.saveOrUpdate(txRecord);
			return "提现失败:提现异常";
		}

	}

	/**
	 * 获取可用红包的余额;
	 * 
	 * @param usersId
	 *            用户ID
	 * @return 可用红包的余额;
	 */
	public double getCouponCost(long usersId) {
		return getCouponCost(usersId, null);
	}

	/**
	 * 获取可用红包的余额;
	 * 
	 * @param usersId
	 *            用户ID
	 * @param type
	 *            类别 如:0:常规投资券; 1:新手投资券
	 * @return 可用红包的余额;
	 */
	public double getCouponCost(long usersId, String type) {
		StringBuffer hql = new StringBuffer();
		hql.append("SELECT SUM(cp.money) FROM Coupon cp ");
		hql.append("WHERE cp.status = '0' ");
		hql.append("AND cp.usersId = ? ");
		if (!QwyUtil.isNullAndEmpty(type)) {
			hql.append(" AND cp.type = '" + type + "' ");
		}
		Double cost = (Double) dao.findJoinActive(hql.toString(), new Object[] { usersId });
		cost = cost == null ? 0 : cost;
		return cost >= 0 ? cost : 0;
	}

	/**
	 * 封装红包类;
	 * 
	 * @param usersId
	 *            红包所属用户
	 * @param money
	 *            红包的金额 单位(分)
	 * @param overTime
	 *            过期时间;//如果过期时间为null,则红包属于长期有效;否则,有有效时间限制;
	 * @param type
	 *            类别 "新手红包"
	 * @param fromId
	 *            发红包者的id;一般为管理员账户的id; -1为线程自动发放id;
	 * @param note
	 *            备注
	 * @return Coupon;没有id的
	 */
	public Coupon packCoupon(long usersId, double money, Date overTime, String type, long fromId, String note) {
		Coupon cop = new Coupon();
		cop.setUsersId(usersId);
		cop.setInitMoney(money);
		cop.setMoney(money);
		cop.setOverTime(overTime);
		// 如果过期时间为null,则红包属于长期有效;否则,有有效时间限制;
		cop.setIsAlways(QwyUtil.isNullAndEmpty(overTime) ? "1" : "0");
		cop.setInsertTime(new Date());
		cop.setStatus("0");
		cop.setType(type);
		cop.setFromId(fromId);
		cop.setNote(note);
		return cop;
	}

	/**
	 * 添加充值记录;状态为(充值中);<br>
	 * 数据插入数据库; 充值成功后,要修改充值记录的状态;
	 * 
	 * @param usersId
	 *            用户id
	 * @param money
	 *            充值金额(分)
	 * @param accountId
	 *            账户id
	 * @param orderId
	 *            订单号
	 * @param ybOrderId
	 *            易宝订单号
	 * @param productName
	 *            产品名称
	 * @param note
	 *            备注
	 * @param cause
	 *            失败原因
	 * @return
	 */
	public CzRecord addCzRecord2(long usersId, double money, String accountId, String orderId, String ybOrderId, String productName, String note, String cause) {
		return addCzRecord(usersId, money, accountId, orderId, ybOrderId, productName, note, "0", cause);
	}

	/**
	 * 添加充值记录;状态为(充值中);<br>
	 * 数据插入数据库; 充值成功后,要修改充值记录的状态;
	 * 
	 * @param usersId
	 *            用户id
	 * @param money
	 *            充值金额(分)
	 * @param accountId
	 *            账户id
	 * @param orderId
	 *            订单号
	 * @param ybOrderId
	 *            连连订单号
	 * @param productName
	 *            产品名称
	 * @param note
	 *            备注
	 * @return
	 */
	public CzRecord addCzRecord(long usersId, double money, String accountId, String orderId, String ybOrderId, String productName, String note) {
		return addCzRecord(usersId, money, accountId, orderId, ybOrderId, productName, note, "0");
	}

	/**
	 * 添加充值记录;状态为(充值中);<br>
	 * 数据插入数据库; 充值成功后,要修改充值记录的状态;
	 * 
	 * @param usersId
	 *            用户id
	 * @param money
	 *            充值金额(分)
	 * @param accountId
	 *            账户id
	 * @param orderId
	 *            订单号
	 * @param ybOrderId
	 *            连连订单号
	 * @param productName
	 *            产品名称
	 * @param note
	 *            备注
	 * @param type
	 *            1为网银
	 * @return
	 */
	public CzRecord addCzRecord(long usersId, double money, String accountId, String orderId, String ybOrderId, String productName, String note, String type) {
		return addCzRecord(usersId, money, accountId, orderId, ybOrderId, productName, note, type, null);
	}

	/**
	 * 添加充值记录;状态为(充值中);<br>
	 * 数据插入数据库; 充值成功后,要修改充值记录的状态;
	 * 
	 * @param usersId
	 *            用户id
	 * @param money
	 *            充值金额(分)
	 * @param accountId
	 *            账户id
	 * @param orderId
	 *            订单号
	 * @param ybOrderId
	 *            易宝订单号
	 * @param productName
	 *            产品名称
	 * @param note
	 *            备注
	 * @param type
	 *            1为网银
	 * @param cause
	 *            失败原因
	 * @return
	 */
	public CzRecord addCzRecord(long usersId, double money, String accountId, String orderId, String ybOrderId, String productName, String note, String type, String cause) {
		CzRecord cz = new CzRecord();
		cz.setUsersId(usersId);
		cz.setMoney(money);
		cz.setInsertTime(new Date());
		cz.setStatus("0");
		cz.setAccountId(accountId);
		cz.setOrderId(orderId);
		cz.setYbOrderId(ybOrderId);
		cz.setProductName(productName);
		cz.setNote(note);
		cz.setType(type);
		cz.setErrCause(cause);
		dao.save(cz);
		return (CzRecord) dao.findById(new CzRecord(), cz.getId());
	}

	/**
	 * 获得喵币收入
	 * 
	 * @param uid
	 *            用户id
	 * @param type
	 *            类型 1:签到获得 2:邀请好友(注册) 3:被邀请(注册) 4:被邀请(第一笔投资) 5:投资获得 6:分享活动获得
	 *            7:手动赠送 //如果该用户被邀请注册的,那么首次购买产品,则会赠送2次喵币
	 * @param note
	 *            备注
	 * @param coin
	 *            喵币
	 * @return
	 * @throws Exception
	 */
	public MCoinIncome saveMCoinIncome(long uid, String type, String note, long coin) throws Exception {
		MCoinIncome mCoinIncome = new MCoinIncome();
		mCoinIncome.setUsersId(uid);
		mCoinIncome.setRecordNumber(OrderNumerUtil.generateRequestId());// 生成流水号
		mCoinIncome.setNote(note);
		mCoinIncome.setCoin(coin);
		mCoinIncome.setType(type);
		mCoinIncome.setStatus("0");
		mCoinIncome.setInsertTime(new Date());
		dao.save(mCoinIncome);
		// 修改用户的总瞄币
		updateUsersTotalMcoin(uid, coin);
		return mCoinIncome;
	}

	/**
	 * 保存用户收入流水记录
	 * 
	 * @param mCoinIncome
	 * @return
	 */
	public String saveMCoinRecord(MCoinIncome mCoinIncome, long totalCoin, Long totalMcoin) {
		MCoinRecord record = new MCoinRecord();
		record.setCoinType("2");// 用户收入
		record.setRecordId(mCoinIncome.getRecordNumber());
		record.setType(mCoinIncome.getType());
		record.setStatus(mCoinIncome.getStatus());
		record.setCoin(mCoinIncome.getCoin());
		record.setUsersId(mCoinIncome.getUsersId());
		record.setNote(mCoinIncome.getNote());
		record.setTotalCoin(totalCoin + totalMcoin);
		record.setInsertTime(mCoinIncome.getInsertTime());
		// 修改平台发放总喵币
		platformBean.updatePayCoin(totalCoin);
		return dao.saveAndReturnId(record);
	}

	/**
	 * 修改用户总瞄币
	 * 
	 * @param userId
	 * @param mcoin
	 */
	public void updateUsersTotalMcoin(Long userId, long mcoin) {
		try {
			// Object [] ob=new Object[]{};
			ArrayList<Object> list = new ArrayList<Object>();
			StringBuffer hql = new StringBuffer();
			hql.append("FROM UsersInfo m WHERE 1=1 ");
			if (!QwyUtil.isNullAndEmpty(userId)) {
				hql.append(" AND m.usersId =  " + userId);
				// list.add(userId);
			}
			hql.append(" ORDER BY m.insertTime DESC ");
			Object obj = dao.findJoinActive(hql.toString(), null);
			if (obj != null) {
				UsersInfo info = (UsersInfo) obj;
				Long oldTotalMcoin = info.getTotalPoint();
				if (QwyUtil.isNullAndEmpty(oldTotalMcoin)) {
					oldTotalMcoin = 0L;
				}
				info.setTotalPoint(QwyUtil.calcNumber(oldTotalMcoin, mcoin, "+").longValue());
				info.setUpdateTime(new Date());
				dao.saveOrUpdate(info);
			}

		} catch (Exception e) {
			// TODO: handle exception
			log.error("操作异常: ", e);
		}
		// return null;
	}

	/**
	 * 发放瞄币
	 * 
	 * @param usersId
	 *            瞄币所属用户
	 * @param mcoin
	 *            瞄币数量,单位(瞄币)
	 * @param type
	 *            //1:签到获得 2:邀请好友(注册) 3:被邀请(注册) 4:被邀请(第一笔投资) 5:投资获得 6:分享活动获得
	 *            7:手动赠送
	 * @param note
	 *            备注
	 * @return boolean; true:发放成功; false:发放失败;
	 */

	public boolean sengMcoin(Long usersId, long mcoin, String type, String note, Long totalMcoin) {
		synchronized (LockHolder.getLock(usersId)) {
			ApplicationContext context = ApplicationContexts.getContexts();
			SessionFactory sf = (SessionFactory) context.getBean("sessionFactory");
			PlatformTransactionManager tm = (PlatformTransactionManager) context.getBean("transactionManager");
			TransactionStatus ts = tm.getTransaction(new DefaultTransactionDefinition());
			try {
				MCoinIncome m = saveMCoinIncome(usersId, type, note, mcoin);
				saveMCoinRecord(m, mcoin, totalMcoin);
				tm.commit(ts);
				return true;
			} catch (Exception e) {
				// TODO Auto-generated catch block
				log.error("操作异常: ", e);
			}
			tm.rollback(ts);
			return false;
		}
	}

	/**
	 * 添加瞄产品
	 * 
	 * @param usersId
	 *            用户用户
	 * @param title
	 *            瞄产品标题
	 * @param price
	 *            单价
	 * @param stock
	 *            库存
	 * @param vip
	 * @param price
	 *            单价
	 * @param stock
	 *            库存
	 * @return vip; 等级
	 */
	public MProduct addMProduct1(long usersId, MProduct m) {

		//MProduct m = new MProduct();
		m.setUsersAdminId(usersId);
		m.setStatus("0");
		//m.setTitle(title);
		//m.setPrice(price);
		//m.setStock(stock);
		//m.setType(type);
		m.setLeftStock(m.getStock());
		//m.setVip(vip);
		//m.setImg(img);
		//m.setPostage(postage);
		//m.setDetailURL(detailURL);
		if (m.getType().equals("5")) {
			m.setMoney(m.getMoney());
		}
		dao.save(m);
		return (MProduct) dao.findById(new MProduct(), m.getId());

	}

	public MProduct addMProduct(long usersId,MProduct m) {
		return addMProduct1(usersId, m);
	}

	/**
	 * 添加提现记录;状态为(待审核);<br>
	 * 数据插入数据库; 充值成功后,要修改充值记录的状态;
	 * 
	 * @param usersId
	 *            用户id
	 * @param money
	 *            充值金额(分)
	 * @param type
	 *            提现类型;0:银行卡提现;1:支付宝提现;
	 * @param note
	 *            备注;
	 * @param accountId
	 *            {@link Account}.id
	 * @param drawType
	 *            //提现类型; 0:T+0到账; 1:T+1到账;
	 * @param userIp
	 *            用户提现时的IP
	 * @return String ""为字符串;有字符串为提现失败;
	 */
	public String addTxRecord(long usersId, double money, String type, String note, String accountId, String drawType, String userIp) {
		if (QwyUtil.isNullAndEmpty(accountId)) {
			log.info("提交提现申请失败,账户ID为 null ");
			return QwyUtil.getJSONString("error", "提交提现申请失败,账户ID为 null ");
		}
		// Account ac =(Account) dao.findById(new Account(), accountId);
		Users users = (Users) dao.findById(new Users(), usersId);
		if (money > users.getUsersInfo().getLeftMoney().doubleValue()) {
			return QwyUtil.getJSONString("error", "提现失败,可提现金额不足");
		}
		// 修改于2015-06-03 15:37:37 提现直接扣除可用余额;暂不放进冻结金额
		// boolean isOk = myWalletBean.subLeftMoneyToFreezeMoney(usersId, money,
		// "txsq", "提现申请", note);
		boolean isOk = myWalletBean.subTotalMoneyLeftMoney(usersId, money, "tx", "提现", note);
		if (isOk) {
			TxRecord tx = new TxRecord();
			tx.setUsersId(usersId);
			tx.setMoney(money);
			tx.setStatus("1");
			tx.setType(type);
			tx.setAccountId(accountId);
			tx.setInsertTime(new Date());
			tx.setDrawType(drawType);
			tx.setUserIp(userIp);
			/// tx.setRequestId(UUID.randomUUID().toString());请求ID,由客服审核通过之后,再填写进去
			tx.setNote(note);
			dao.save(tx);
			return QwyUtil.getJSONString("ok", tx.getId());
		}
		return QwyUtil.getJSONString("error", "提现申请失败,请联系客服,code: 8845");
	}

	public Object findObjectById(Object obj, String id) {
		return dao.findById(obj, id);
	}

	public Object findObjectById(Object obj, long id) {
		return dao.findById(obj, id);
	}

	public void saveOrUpdate(Object obj) {
		dao.saveOrUpdate(obj);
	}

	/**
	 * 发放投资券
	 * 
	 * @param usersId
	 *            红包所属用户
	 * @param money
	 *            红包金额,单位(分)
	 * @param overTime
	 *            过期时间;//如果过期时间为null,则红包属于长期有效;否则,有有效时间限制;
	 * @param type
	 *            类别 如:0:常规投资券; 1:新手投资券(PS:新手投资券已弃用);直接填写0;
	 * @param fromId
	 *            发红包者的id;一般为管理员账户的id; -1为线程自动发放id;
	 * @param note
	 *            备注
	 * @return boolean; true:发放成功; false:发放失败;
	 */
	public boolean sendHongBao(long usersId, double money, Date overTime, String type, long fromId, String note) {
		synchronized (LockHolder.getLock(usersId)) {
			ApplicationContext context = ApplicationContexts.getContexts();
			SessionFactory sf = (SessionFactory) context.getBean("sessionFactory");
			PlatformTransactionManager tm = (PlatformTransactionManager) context.getBean("transactionManager");
			TransactionStatus ts = tm.getTransaction(new DefaultTransactionDefinition());
			try {
				Coupon cop = packCoupon(usersId, money, overTime, type, fromId, note);
				dao.saveOrUpdate(cop);
				platformBean.updateTotalCoupon(money);
				tm.commit(ts);
				return true;
			} catch (Exception e) {
				// TODO Auto-generated catch block
				log.error("操作异常: ", e);
			}
			tm.rollback(ts);
			return false;
		}
	}

	/**
	 * 调用支付API 支付接口 使用回调
	 * 
	 * @param orderid
	 *            商户订单号 商户生成的唯一订单号，最长50位
	 * @param amount
	 *            交易金额 以"分"为单位的整型
	 * @param productname
	 *            商品名称 最长50位
	 * @param productdesc
	 *            商品描述 最长200位
	 * @param usersId
	 *            用户标识 最长50位，商户生成的用户唯一标识
	 * @param callbackurl
	 *            回调地址 用来通知商户支付结果
	 * @param userip
	 *            用户请求ip 用户支付时使用的网络终端IP
	 * @return JOSN格式;
	 */
	// public String directBindPay2(final String orderid, final int amount,
	// String productname,
	// String productdesc, final long usersId, String callbackurl, String
	// userip){
	// String json = "";
	// try {
	// synchronized (LockHolder.getLock(usersId)) {
	// Object obj =yiBaoPayBean.getAccountByUsersId(usersId,"0");
	// if(QwyUtil.isNullAndEmpty(obj)){
	// return "{\"message\":\"该用户没有绑定银行卡\",\"end\":\"error\"}";
	// }
	// Account account = yiBaoPayBean.getAccountByUsersId(usersId, "0");
	// CzRecord cz = addCzRecord(usersId, amount, account.getId(), orderid,
	// "",productname, productdesc);
	// String myBankJson = yiBaoPayBean.directBindPay(orderid, amount,
	// productname, productdesc, usersId, callbackurl, userip,"0");
	// System.err.println("订单提交时间: "+new Date());
	// JSONObject jb = JSONObject.fromObject(myBankJson);
	// Object ob = jb.get("error_code");
	// if(!QwyUtil.isNullAndEmpty(ob)){
	// if(ob.toString().equals("600156")){
	// json = QwyUtil.getJSONString("error", "该用户没有绑定银行卡");
	// cz.setStatus("2");
	// cz.setErrorCode(ob+"");
	// cz.setNote("该用户没有绑定银行卡");
	// dao.saveOrUpdate(cz);
	// if(QwyUtil.isNullAndEmpty(bean.findBindBankList(usersId))){
	// bean.unBindBank(usersId,"1");
	// }
	// }else{
	// if(jb.toString().indexOf("amount")!=-1){
	// json = QwyUtil.getJSONString("error", "充值金额不能为0");
	// }else{
	// json = QwyUtil.getJSONString("error", jb.getString("error_msg"));
	// }
	// cz.setStatus("2");
	// cz.setErrorCode(ob+"");
	// cz.setNote(jb.getString("error_msg"));
	// dao.saveOrUpdate(cz);
	// }
	// }else{
	// if(!QwyUtil.isNullAndEmpty(jb.get("end"))){
	// return QwyUtil.getJSONString("error", jb.getString("message"));
	// }
	// Object yborderid = jb.get("yborderid");
	// cz.setYbOrderId(yborderid.toString());
	// dao.saveOrUpdate(cz);
	// json = QwyUtil.getJSONString("error","支付结果将在1分钟之内到,请稍后查看");
	// }
	// }
	// } catch (Exception e) {
	// // TODO Auto-generated catch block
	// log.error("操作异常: ",e);
	// json = QwyUtil.getJSONString("error", "提交订单失败,操作异常;code:8004");
	// }
	// return json;
	// }
	public String directBindPay2(final Double money, String productname, String productdesc, String idcard, String cardNo, String accountName, String userip, final long usersId, String callbackurl,
			String returnUrl) {
		String tmps = "";
		String json = "";
		try {
			synchronized (LockHolder.getLock(usersId)) {
				log.info("锁定用户：" + usersId);
				MyRedis redis = new MyRedis();

				log.info("进入连连支付接口");

				Users users = registerUserBean.getUsersById(usersId);

				Account account = myAccountBean.getAccountByUsersId(usersId, null);

				String uuid = UUID.randomUUID().toString();
				// 验证身份证
				if (!QwyUtil.isNullAndEmpty(myAccountBean.getAccountByIdCard(idcard, ""))) {
					tmps = "该身份证已绑定";
					return getJsonString("error", tmps);
				}
				if (QwyUtil.isNullAndEmpty(account)) {// 如果为空，表示首次充值

					String obj = lianLianPayBean.userBankCard(usersId);
					JSONObject resjson = JSONObject.fromObject(obj);
					// 验证帐号
					if ("0000".equals(resjson.get("ret_code"))) {
						String accountId = redis.get(usersId + "");
						if (!QwyUtil.isNullAndEmpty(accountId)) {
							Account acc = myAccountBean.getAccountById(accountId);
							if (!QwyUtil.isNullAndEmpty(acc)) {
								if (!cardNo.equals(DESEncrypt.jieMiBankCard(acc.getBankAccount()))) {
									tmps = "该账号已绑定，请勿重复操作";
									return getJsonString("error", tmps);
								}
							}
						}
					}

					account = new Account();
					// 验证银行卡号
					String dataJson = lianLianPayBean.queryCardBin(cardNo);
					if (!QwyUtil.isNullAndEmpty(dataJson)) {
						JSONObject object = JSONObject.fromObject(dataJson);
						if ("0000".equals(QwyUtil.get(object, "ret_code"))) {
							if (!QwyUtil.isNullAndEmpty(object)) {
								account.setBankCode(QwyUtil.get(object, "bank_code"));
								account.setBankName(QwyUtil.get(object, "bank_name"));
							}
						} else {
							tmps = "卡信息有错，请重新填写";
							return getJsonString("error", tmps);
						}
					}
					account.setCardLast(cardNo.substring(cardNo.length() - 4, cardNo.length()));
					account.setCardTop(cardNo.substring(0, 6));
					account.setBankAccount(DESEncrypt.jiaMiBankCard(cardNo));
					account.setBankAccountName(accountName);
					account.setIdcard(DESEncrypt.jiaMiIdCard(idcard));
					account.setIdentityType(0);
					account.setInsertTime(new Date());
					account.setNote("连连充值绑定");
					account.setPhone(users.getPhone());
					account.setRegistIp(userip);
					account.setRequestId(uuid);// 绑定请求ID（用平台订单号）
					account.setUsersId(usersId);
					account.setStatus("1");
					account.setType("1");
					dao.saveOrUpdate(account);
					redis.setex(uuid, 60 * 60, account.getId());
					redis.setex(account.getUsersId() + "", 60 * 60, account.getId());
				}

				CzRecord cz = addCzRecord(usersId, money, account.getId(), uuid, "", productname, productdesc, "3");
				log.info("成功生成订单");
				// 订单生成时间（时间格式: yyyyMMddHHmmss）
				String dtTime = QwyUtil.fmyyyyMMddHHmmss2.format(cz.getInsertTime());
				// 用户注册时间
				String registerTime = QwyUtil.fmyyyyMMddHHmmss2.format(users.getInsertTime());
				// 连连支付金额以元为单位
				double trueMoney = QwyUtil.calcNumber(money, 0.01, "*").doubleValue();
				// 请将 IP 中的“ .”替换为“ _”
				String userreq_ip = userip.replace(".", "_");
				// 连连充值请求
				String reqJson = lianLianPayBean.authPay(cz.getOrderId(), usersId, dtTime, productname, trueMoney, returnUrl, callbackurl, "D", DESEncrypt.jieMiIdCard(account.getIdcard()),
						account.getBankAccountName(), DESEncrypt.jieMiBankCard(account.getBankAccount()), DESEncrypt.jieMiUsername(users.getUsername()), registerTime, account.getBankCode(),
						userreq_ip);
				System.err.println("充值表单提交时间: " + new Date());

				log.info("成功生成充值订单报文");
				return getJsonString("ok", reqJson);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.error("操作异常: ", e);
			json = getJsonString("ok", "银行异常,请联系客服");
		}
		return json;
	}

	public String getJsonString(String status, String tmp) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("status", status);
		jsonObject.put("json", tmp);
		return jsonObject.toString();
	}

	/**
	 * 调用支付API 支付接口
	 * 
	 * @param orderid
	 *            商户订单号 商户生成的唯一订单号，最长50位
	 * @param amount
	 *            交易金额 以"分"为单位的整型
	 * @param productname
	 *            商品名称 最长50位
	 * @param productdesc
	 *            商品描述 最长200位
	 * @param usersId
	 *            用户标识 最长50位，商户生成的用户唯一标识
	 * @param callbackurl
	 *            回调地址 用来通知商户支付结果
	 * @param userip
	 *            用户请求ip 用户支付时使用的网络终端IP
	 * @return JOSN格式;
	 */
	public String directBindPay(final String orderid, final int amount, String productname, String productdesc, final long usersId, String callbackurl, String userip) {
		String json = "";
		try {
			synchronized (LockHolder.getLock(usersId)) {
				Object obj = yiBaoPayBean.getAccountByUsersId(usersId, "0");
				if (QwyUtil.isNullAndEmpty(obj)) {
					return "{\"message\":\"该用户没有绑定银行卡\",\"end\":\"error\"}";
				}
				Account account = yiBaoPayBean.getAccountByUsersId(usersId, "0");
				CzRecord cz = addCzRecord(usersId, amount, account.getId(), orderid, "", productname, productdesc);
				String myBankJson = yiBaoPayBean.directBindPay(orderid, amount, productname, productdesc, usersId, callbackurl, userip, "0");
				System.err.println("订单提交时间: " + new Date());
				JSONObject jb = JSONObject.fromObject(myBankJson);
				Object ob = jb.get("error_code");
				if (!QwyUtil.isNullAndEmpty(ob)) {
					if (ob.toString().equals("600156")) {
						json = QwyUtil.getJSONString("error", "该用户没有绑定银行卡");
						if (QwyUtil.isNullAndEmpty(bean.findBindBankList(usersId))) {
							bean.unBindBank(usersId, "1");
						}
					} else {
						if (jb.toString().indexOf("amount") != -1) {
							json = QwyUtil.getJSONString("error", "充值金额不能为0");
						} else {
							json = QwyUtil.getJSONString("error", jb.getString("error_msg"));
						}
					}
				} else {
					if (!QwyUtil.isNullAndEmpty(jb.get("end"))) {
						return QwyUtil.getJSONString("error", jb.getString("message"));
					}
					Object yborderid = jb.get("yborderid");
					if (!QwyUtil.isNullAndEmpty(yborderid)) {
						cz.setYbOrderId(yborderid.toString());
						final String czId = cz.getId();
						// Thread.sleep(2000);//延迟2秒去查询支付结果;
						System.err.println("查询支付结果");
						String queryJson = yiBaoPayBean.queryPay(orderid);
						JSONObject jsonObject = JSONObject.fromObject(queryJson);
						Object st = jsonObject.get("status");
						if (!QwyUtil.isNullAndEmpty(st)) {
							String status = st.toString();
							cz.setCheckTime(new Date());
							if ("0".equals(status)) {
								System.err.println("查询支付结果0");
								Object msg = jsonObject.get("errormsg");
								String errorcode = jsonObject.getString("errorcode");
								String message = QwyUtil.isNullAndEmpty(msg) ? "支付失败" : msg.toString();
								json = QwyUtil.getJSONString("error", message);
								cz.setStatus("2");
								cz.setErrorCode(errorcode);
								cz.setNote(message);
							} else if ("1".equals(status)) {
								System.err.println("查询支付结果1");
								System.err.println("直接___订单充值成功时间: " + new Date());
								boolean isRecharge = usreRecharge(usersId, amount, "cz", "第三方支付", "通过【易宝支付】进行充值");
								if (isRecharge) {
									json = QwyUtil.getJSONString("ok", yborderid.toString());
									cz.setStatus("1");
									cz.setNote("充值成功");
								} else {
									json = QwyUtil.getJSONString("error", "充值失败,请联系客服,流水号为: " + cz.getRecordNumber());
									cz.setStatus("0");
									cz.setNote("易宝支付已充值成功,数据库插入失败!");
								}
							} else if ("2".equals(status)) {
								System.err.println("查询支付结果2");
								Object msg = jsonObject.get("errormsg");
								String errorcode = jsonObject.getString("errorcode");
								String message = QwyUtil.isNullAndEmpty(msg) ? "支付失败" : msg.toString();
								json = QwyUtil.getJSONString("error", QwyUtil.isNullAndEmpty(msg) ? "支付失败" : msg.toString());
								cz.setStatus("2");
								cz.setErrorCode(errorcode);
								cz.setNote(message);
							} else if ("3".equals(status)) {
								System.err.println("查询支付结果3");
								// 用定时器去重新查;
								json = QwyUtil.getJSONString("error", "支付结果将在1分钟之内到账,请稍后查看");
								cz.setStatus("0");
								// 执行多线程计划,创建1个大小的线程池;
								final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
								// 5秒查询一次结果,查询有状态返回后,则结束线程;
								scheduler.scheduleAtFixedRate(new Runnable() {

									@Override
									public void run() {
										// TODO Auto-generated method stub
										try {
											String queryJson = yiBaoPayBean.queryPay(orderid);
											JSONObject jsonObject = JSONObject.fromObject(queryJson);
											Object st = jsonObject.get("status");
											CzRecord newCz = getCzRecordById(czId);
											System.err.println("线程查询结果......");
											if (!QwyUtil.isNullAndEmpty(st)) {
												String status = st.toString();
												newCz.setCheckTime(new Date());
												if ("0".equals(status)) {
													System.err.println("线程查询结果......0");
													Object msg = jsonObject.get("errormsg");
													String message = QwyUtil.isNullAndEmpty(msg) ? "支付失败" : msg.toString();
													String errorcode = jsonObject.getString("errorcode");
													// 充值失败;
													newCz.setStatus("2");
													newCz.setErrorCode(errorcode);
													newCz.setNote(message);
													dao.saveOrUpdate(newCz);
													scheduler.shutdown();
												} else if ("1".equals(status)) {
													System.err.println("线程查询结果......1");
													System.err.println("线程___订单充值成功时间: " + new Date());
													boolean isRecharge = usreRecharge(usersId, amount, "cz", "第三方支付", "通过【易宝支付】进行充值");
													if (isRecharge) {
														newCz.setStatus("1");
														newCz.setNote("充值成功");
													} else {
														newCz.setStatus("0");
														newCz.setNote("易宝支付已充值成功,数据库插入失败!");
													}
													// 充值成功;
													dao.saveOrUpdate(newCz);
													scheduler.shutdown();
												} else if ("2".equals(status)) {
													System.err.println("线程查询结果......2");
													String errorcode = jsonObject.getString("errorcode");
													Object msg = jsonObject.get("errormsg");
													String message = QwyUtil.isNullAndEmpty(msg) ? "支付失败" : msg.toString();
													// 充值失败;
													newCz.setStatus("2");
													newCz.setNote(message);
													newCz.setErrorCode(errorcode);
													dao.saveOrUpdate(newCz);
													scheduler.shutdown();
												} else {
													System.err.println("线程查询支付结果status: " + status + "; 继续查询订单消息;");
												}

											}
										} catch (Exception e) {
											// TODO Auto-generated catch block
											log.error("操作异常: ", e);
										}
									}
								}, 0, 3, TimeUnit.SECONDS);
							} else {
								System.err.println("支付失败:___");
								json = QwyUtil.getJSONString("error", "支付失败");
								cz.setStatus("2");
							}
						} else {
							json = QwyUtil.getJSONString("error", jsonObject.getString("error_msg"));
							cz.setStatus("2");
						}
						dao.saveOrUpdate(cz);
					} else {
						json = QwyUtil.getJSONString("error", "创建订单失败;code:8000");
					}
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.error("操作异常: ", e);
			json = QwyUtil.getJSONString("error", "提交订单失败,操作异常;code:8004");
		}
		return json;
	}

	/**
	 * 充值接口,此接口是同步接口,需要等待查询结果回来再做操作;
	 * 
	 * @param orderid
	 *            商户订单号 商户生成的唯一订单号，最长50位
	 * @param amount
	 *            交易金额 以"分"为单位的整型
	 * @param productname
	 *            商品名称 最长50位
	 * @param productdesc
	 *            商品描述 最长200位
	 * @param usersId
	 *            用户标识 最长50位，商户生成的用户唯一标识
	 * @param callbackurl
	 *            回调地址 用来通知商户支付结果
	 * @param userip
	 *            用户请求ip 用户支付时使用的网络终端IP
	 * @return JOSN格式;
	 */
	public String directBindPayToBuyProduct(final String orderid, final int amount, String productname, String productdesc, final long usersId, String callbackurl, String userip) {
		String json = "";
		CzRecord cz = null;
		try {
			synchronized (LockHolder.getLock(usersId)) {
				Object obj = yiBaoPayBean.getAccountByUsersId(usersId, "0");
				if (QwyUtil.isNullAndEmpty(obj)) {
					return "{\"message\":\"该用户没有绑定银行卡\",\"end\":\"error\"}";
				}
				Account account = yiBaoPayBean.getAccountByUsersId(usersId, "0");
				cz = addCzRecord(usersId, amount, account.getId(), orderid, "", productname, productdesc);
				String myBankJson = yiBaoPayBean.directBindPay(orderid, amount, productname, productdesc, usersId, callbackurl, userip, "0");
				System.err.println("订单提交时间: " + new Date());
				JSONObject jb = JSONObject.fromObject(myBankJson);
				Object ob = jb.get("error_code");
				String failStr = "";
				if (!QwyUtil.isNullAndEmpty(ob)) {
					if (ob.toString().equals("600156")) {
						failStr = "该用户没有绑定银行卡";
						json = QwyUtil.getJSONString("error", failStr);
						if (QwyUtil.isNullAndEmpty(bean.findBindBankList(usersId))) {
							bean.unBindBank(usersId, "1");
						}
					} else {
						if (jb.toString().indexOf("amount") != -1) {
							failStr = "充值金额不能为0";
							json = QwyUtil.getJSONString("error", failStr);
						} else {
							failStr = jb.getString("error_msg");
							json = QwyUtil.getJSONString("error", failStr);
						}
					}
					cz.setStatus("2");
					cz.setErrorCode(ob.toString());
					cz.setNote(failStr);
					dao.saveOrUpdate(cz);
				} else {
					if (!QwyUtil.isNullAndEmpty(jb.get("end"))) {
						failStr = jb.getString("message");
						cz.setStatus("2");
						cz.setErrorCode(ob.toString());
						cz.setNote(failStr);
						dao.saveOrUpdate(cz);
						return QwyUtil.getJSONString("error", failStr);
					}
					Object yborderid = jb.get("yborderid");
					if (!QwyUtil.isNullAndEmpty(yborderid)) {
						cz.setYbOrderId(yborderid.toString());
						// Thread.sleep(2000);//延迟2秒去查询支付结果;
						System.err.println("查询支付结果");
						json = queryPayResult(orderid, cz);
					} else {
						json = QwyUtil.getJSONString("error", "创建订单失败;code:8000");
						cz.setStatus("2");
						cz.setErrorCode(ob.toString());
						cz.setNote(failStr);
						dao.saveOrUpdate(cz);
					}
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.error("操作异常: ", e);
			json = QwyUtil.getJSONString("error", "提交订单失败,操作异常;code:8004");
			cz.setStatus("2");
			cz.setErrorCode("code:8004");
			cz.setNote("提交订单失败,操作异常;code:8004");
			dao.saveOrUpdate(cz);
		}
		return json;
	}

	/**
	 * 查询支付的结果;递归调用;1.5秒一次;查询的有结果返回为止;<br>
	 * 用于同步支付;需要等到支付结果,才做操作;
	 * 
	 * @param orderid
	 *            订单id;
	 * @param cz
	 *            CzRecord实体;
	 * @return
	 */
	public String queryPayResult(final String orderid, CzRecord cz) {
		String json = "";
		try {
			String queryJson = yiBaoPayBean.queryPay(orderid);
			JSONObject jsonObject = JSONObject.fromObject(queryJson);
			Object st = jsonObject.get("status");
			if (!QwyUtil.isNullAndEmpty(st)) {
				String status = st.toString();
				cz.setCheckTime(new Date());
				if ("0".equals(status)) {
					System.err.println("查询支付结果0");
					Object msg = jsonObject.get("errormsg");
					String errorcode = jsonObject.getString("errorcode");
					String message = QwyUtil.isNullAndEmpty(msg) ? "支付失败" : msg.toString();
					json = QwyUtil.getJSONString("error", message);
					cz.setStatus("2");
					cz.setErrorCode(errorcode);
					cz.setNote(message);
				} else if ("1".equals(status)) {
					System.err.println("查询支付结果1");
					System.err.println("支付___订单充值成功时间: " + new Date());
					boolean isRecharge = usreRecharge(cz.getUsersId(), cz.getMoney(), "cz", "第三方支付", "购买产品,通过【易宝支付】进行支付");
					if (isRecharge) {
						json = QwyUtil.getJSONString("ok", cz.getYbOrderId());
						cz.setStatus("1");
						cz.setNote("充值成功");
					} else {
						json = QwyUtil.getJSONString("error", "充值失败,请联系客服,流水号为: " + cz.getRecordNumber());
						cz.setStatus("0");
						cz.setNote("易宝支付已充值成功,数据库插入失败!");
					}
				} else if ("2".equals(status)) {
					System.err.println("查询支付结果2");
					Object msg = jsonObject.get("errormsg");
					String errorcode = jsonObject.getString("errorcode");
					String message = QwyUtil.isNullAndEmpty(msg) ? "支付失败" : msg.toString();
					json = QwyUtil.getJSONString("error", QwyUtil.isNullAndEmpty(msg) ? "支付失败" : msg.toString());
					cz.setStatus("2");
					cz.setErrorCode(errorcode);
					cz.setNote(message);
				} else if ("3".equals(status)) {
					System.err.println("查询支付结果3");
					// 用定时器去重新查;
					// json =
					// QwyUtil.getJSONString("error","支付结果将在1分钟之内到账,请稍后查看");
					cz.setStatus("0");
					// 1.5秒去查询一次支付结果;
					Thread.sleep(2000);
					json = queryPayResult(orderid, cz);
				} else {
					Object msg = jsonObject.get("errormsg");
					String message = QwyUtil.isNullAndEmpty(msg) ? "支付失败" : msg.toString();
					json = QwyUtil.getJSONString("error", jsonObject.getString("error_msg"));
					cz.setNote(message);
					cz.setStatus("2");
				}
			} else {
				System.err.println("支付失败:___");
				json = QwyUtil.getJSONString("error", "支付失败");
				cz.setStatus("2");
			}
			dao.saveOrUpdate(cz);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.error("操作异常: ", e);
		}
		return json;
	}

	/**
	 * 用户提现;
	 * 
	 * @param usersId
	 *            用户id
	 * @param amount
	 *            提现金额(分)
	 * @param userip
	 *            用户支付时使用的网络终端IP
	 * @param callBackUrl
	 *            回调地址
	 * @return JOSN格式;
	 * @throws Exception
	 */
	public String withdraw(long usersId, int amount, String userip, String callBackUrl) {
		String json = "";
		try {
			Account account = yiBaoPayBean.getAccountByUsersId(usersId, "0");
			if (QwyUtil.isNullAndEmpty(account)) {
				json = QwyUtil.getJSONString("error", "提现失败,没有找到绑定的银行卡.");
			} else {
				String drawtype = "1";
				String ybdrawType = "NORMAL";
				int week = Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
				// 工作日开始时间
				Date startTime = QwyUtil.fmyyyyMMddHHmmss.parse(QwyUtil.fmyyyyMMdd.format(new Date()) + " 09:00:00");
				// 工作日结束时间
				Date endTime = QwyUtil.fmyyyyMMddHHmmss.parse(QwyUtil.fmyyyyMMdd.format(new Date()) + " 18:00:00");
				if (week > 1 && week < 7) {
					if (new Date().after(startTime) && new Date().before(endTime)) {
						drawtype = "0";
						ybdrawType = "NATRALDAY_URGENT";
					}
				}
				TxRecord oldTx = findRecordsByUid(usersId);
				if (!QwyUtil.isNullAndEmpty(oldTx)) {
					// 已经有在审核的提现记录或者当天已经提现成功;每天只能申请提现一次;
					return QwyUtil.getJSONString("error", "为防止恶意提现,每天只能提现一次");
				}
				String tempJson = addTxRecord(usersId, amount, "0", "用户提现,卡号尾号为: " + account.getCardLast(), account.getId(), drawtype, userip, UUID.randomUUID().toString());
				JSONObject tempJb = JSONObject.fromObject(tempJson);
				String tempStatus = tempJb.getString("status");
				if ("ok".equals(tempStatus)) {
					String txId = tempJb.getString("json");
					TxRecord tx = (TxRecord) dao.findById(new TxRecord(), txId);
					json = QwyUtil.getJSONString("ok", "提现请求成功，正在处理中");
					tx.setTxStatus("1");
					dao.saveOrUpdate(tx);

					String myjson = yiBaoPayBean.withdraw(tx.getRequestId(), tx.getUsersId() + "", 2, account.getCardTop(), account.getCardLast(), 156, amount, ybdrawType, "", userip, "",
							callBackUrl);
					JSONObject jb = JSONObject.fromObject(myjson);
					String status = QwyUtil.get(jb, "status");
					if ("SUCCESS".equalsIgnoreCase(status)) {
						log.info(tx.getId() + "请求提现的成功记录");
						// 提交提现成功;次日到账;直接扣除冻结金额;修改于2015-06-03 15:23:28;
						String yblsh = jb.getString("ybdrawflowid");
						tx.setYbOrderId(yblsh);
						dao.saveOrUpdate(tx);
					} else {
						log.info(tx.getId() + "请求提现失败");
						String yblsh = QwyUtil.get(jb, "message");
						tx.setYbOrderId(yblsh);
						tx.setStatus("2");
						tx.setErrorCode(jb.get("error_code") + "");
						tx.setNote(tx.getNote() + "\t" + QwyUtil.get(jb, "error_msg"));
						tx.setCheckTime(new Date());
						dao.saveOrUpdate(tx);
						myWalletBean.addTotalMoneyLeftMoney(tx.getUsersId(), tx.getMoney(), "txfk", "", "提现失败,返款提现金额到用户帐号");
						if (!QwyUtil.isNullAndEmpty(jb)) {
							Object obj = jb.get("error_msg");
							Object ob = jb.get("error_code");
							if ("600156".equals(ob.toString())) {
								json = QwyUtil.getJSONString("error", "该用户没有绑定银行卡");
								if (QwyUtil.isNullAndEmpty(bean.findBindBankList(usersId))) {
									bean.unBindBank(usersId, "1");
								}
							} else {
								json = QwyUtil.getJSONString("error", QwyUtil.isNullAndEmpty(obj) ? "提现失败" : obj.toString());
							}
						} else {
							json = QwyUtil.getJSONString("error", "提现失败,请联系客服");
						}
						return json;
					}
				} else {
					json = tempJson;
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.error("操作异常: ", e);
			json = QwyUtil.getJSONString("error", "提现失败,请联系客服,code: 8889");
		}
		return json;
	}

	// public String withdraw(long usersId,int amount, String userip){
	// String json = "";
	// try {
	// Account account = yiBaoPayBean.getAccountByUsersId(usersId, "0");
	// if(QwyUtil.isNullAndEmpty(account)){
	// json = QwyUtil.getJSONString("error", "提现失败,没有找到绑定的银行卡.");
	// }else{
	// TxRecord oldTx =findRecordsByUid(usersId);
	// if(!QwyUtil.isNullAndEmpty(oldTx)){
	// //已经有在审核的提现记录或者当天已经提现成功;每天只能申请提现一次;
	// return QwyUtil.getJSONString("error", "为防止恶意提现,每天只能提现一次");
	// }
	// String tempJson = addTxRecord(usersId, amount, "0", "用户提现,卡号尾号为:
	// "+account.getCardLast(),account.getId(),"1",userip,UUID.randomUUID().toString());
	// JSONObject tempJb = JSONObject.fromObject(tempJson);
	// String tempStatus = tempJb.getString("status");
	// if("ok".equals(tempStatus)){
	// json = QwyUtil.getJSONString("ok", "提现请求成功，正在处理中");
	// }
	// else{
	// json = tempJson;
	// }
	// }
	// } catch (Exception e) {
	// // TODO Auto-generated catch block
	// log.error("操作异常: ",e);
	// json = QwyUtil.getJSONString("error", "提现失败,请联系客服,code: 8889");
	// }
	// return json;
	// }
	// public String withdraw(long usersId,int amount, String userip){
	// String json = "";
	// try {
	// Account account = yiBaoPayBean.getAccountByUsersId(usersId, "0");
	// if(QwyUtil.isNullAndEmpty(account)){
	// json = QwyUtil.getJSONString("error", "提现失败,没有找到绑定的银行卡.");
	// }else{
	// TxRecord oldTx =findRecordsByUid(usersId);
	// if(!QwyUtil.isNullAndEmpty(oldTx)){
	// //已经有在审核的提现记录或者当天已经提现成功;每天只能申请提现一次;
	// return QwyUtil.getJSONString("error", "为防止恶意提现,每天只能提现一次");
	// }
	// String tempJson = addTxRecord(usersId, amount, "0", "用户提现,卡号尾号为:
	// "+account.getCardLast(),account.getId(),"1",userip);
	// JSONObject tempJb = JSONObject.fromObject(tempJson);
	// String tempStatus = tempJb.getString("status");
	// if("ok".equals(tempStatus)){
	// String txId = tempJb.getString("json");
	// TxRecord tx = (TxRecord)dao.findById(new TxRecord(), txId);
	// tx.setRequestId(UUID.randomUUID().toString());
	// String myjson = withdraw(tx.getRequestId(), tx.getUsersId(),
	// tx.getMoney().intValue(), tx.getDrawType(), tx.getUserIp());
	// JSONObject jb = JSONObject.fromObject(myjson);
	// String status = jb.getString("status");
	// if("ok".equals(status)){
	// //提交提现成功;次日到账;直接扣除冻结金额;修改于2015-06-03 15:23:28;
	// String yblsh = jb.getString("json");
	// tx.setYbOrderId(yblsh);
	// tx.setStatus("1");
	// tx.setCheckTime(new Date());
	// dao.saveOrUpdate(tx);
	// json = QwyUtil.getJSONString("ok", "提现成功,次日到账");
	// }else{
	// //提交提现失败; 冻结金额回滚到用户的可用余额;
	// myWalletBean.addTotalMoneyLeftMoney(tx.getUsersId(), tx.getMoney(),
	// "txfk", "", "提现失败,返款提现金额到用户帐号");
	// tx.setStatus("2");
	// tx.setCheckTime(new Date());
	// if(!QwyUtil.isNullAndEmpty(QwyUtil.get(jb, "error_code"))){
	// tx.setErrorCode(QwyUtil.get(jb, "error_code"));
	// tx.setNote(tx.getNote()+"\t"+QwyUtil.get(jb, "error_msg"));
	// }
	// dao.saveOrUpdate(tx);
	// return myjson;
	// }
	// }
	// else{
	// json = tempJson;
	// }
	// }
	// } catch (Exception e) {
	// // TODO Auto-generated catch block
	// log.error("操作异常: ",e);
	// json = QwyUtil.getJSONString("error", "提现失败,请联系客服,code: 8889");
	// }
	// return json;
	// }
	/**
	 * 用户提现;
	 * 
	 * @param requestid
	 *            请求id
	 * @param usersId
	 *            用户id
	 * @param amount
	 *            提现金额(分)
	 * @param drawtype
	 *            提现类型; 0:T+0到账; 1:T+1到账;
	 * @param userip
	 *            用户支付时使用的网络终端IP
	 * @return JOSN格式;
	 * @throws Exception
	 */
	private String withdraw(String requestid, long usersId, int amount, String drawtype, String userip) throws Exception {
		String json = "";
		String myBankJson = yiBaoPayBean.withdraw(requestid, usersId, amount, drawtype, userip);
		JSONObject jb = JSONObject.fromObject(myBankJson);
		Object ob = jb.get("error_code");
		Object ybdrawflowid = jb.get("ybdrawflowid");
		if (!QwyUtil.isNullAndEmpty(ob)) {
			Object obj = jb.get("error_msg");
			json = QwyUtil.getJSONString("error", QwyUtil.isNullAndEmpty(obj) ? "提现失败" : obj.toString());
		} else if (!QwyUtil.isNullAndEmpty(ybdrawflowid)) {
			String yblsh = ybdrawflowid.toString();
			json = QwyUtil.getJSONString("ok", yblsh);
		} else {
			json = QwyUtil.getJSONString("error", "提现失败,提现接口访问失败");
		}
		return json;
	}

	/**
	 * 根据用户id,查找用户提现记录;
	 * 
	 * @param usersId
	 * @return
	 * @throws Exception
	 */
	public TxRecord findRecordsByUid(long usersId) throws Exception {
		StringBuffer hql = new StringBuffer();
		hql.append(" FROM TxRecord tx ");
		hql.append(" WHERE tx.usersId = ? ");
		hql.append(" AND tx.status in ('0','1') ");
		hql.append(" AND tx.insertTime >= '" + QwyUtil.fmyyyyMMdd.format(new Date()) + "' ");
		System.out.println(QwyUtil.fmyyyyMMdd.format(new Date()));
		return (TxRecord) dao.findJoinActive(hql.toString(), new Object[] { usersId });
	}

	/**
	 * 根据用户id,获取充值的流水记录;
	 * 
	 * @param pageUtil
	 *            分页对象;
	 * @param usersId
	 *            用户id
	 * @return
	 */
	public PageUtil<CzRecord> getCzRecordByUserId(PageUtil<CzRecord> pageUtil, long usersId, String status) {
		ArrayList<Object> ob = new ArrayList<Object>();
		StringBuffer hql = new StringBuffer();
		hql.append("FROM CzRecord cz ");
		hql.append(" WHERE cz.usersId = ? ");
		ob.add(usersId);
		if (!QwyUtil.isNullAndEmpty(status) && !"all".equals(status)) {
			hql.append("AND cz.status = ?");
			ob.add(status);
		}
		hql.append(" ORDER BY cz.insertTime DESC ");
		return (PageUtil<CzRecord>) dao.getPage(pageUtil, hql.toString(), ob.toArray());

	}

	/**
	 * 根据用户id,获取提现的流水记录;
	 * 
	 * @param pageUtil
	 *            分页对象;
	 * @param usersId
	 *            用户id
	 * @return
	 */
	public PageUtil<TxRecord> getTxRecordByUserId(PageUtil<TxRecord> pageUtil, long usersId) {
		return getTxRecordByUserId(pageUtil, usersId, null, null, false);
	}

	/**
	 * 根据用户id,获取提现的流水记录;
	 * 
	 * @param pageUtil
	 *            分页对象;
	 * @param usersId
	 *            用户id
	 * @param status
	 *            提现状态;0:待审核;1:审核通过;2审核未通过
	 * @return
	 */
	public PageUtil<TxRecord> getTxRecordByUserId(PageUtil<TxRecord> pageUtil, long usersId, String status) {
		return getTxRecordByUserId(pageUtil, usersId, status, null, false);
	}

	/**
	 * 根据用户id,获取提现的流水记录;
	 * 
	 * @param pageUtil
	 *            分页对象;
	 * @param usersId
	 *            用户id
	 * @param isRequesId
	 *            是否需要请求ID 1为需要 2为不需要
	 * @return
	 */
	public PageUtil<TxRecord> getTxRecordByUserId(PageUtil<TxRecord> pageUtil, long usersId, String status, String isRequesId) {
		return getTxRecordByUserId(pageUtil, usersId, status, isRequesId, false);
	}

	/**
	 * 根据用户id,获取提现的流水记录;
	 * 
	 * @param pageUtil
	 *            分页对象;
	 * @param usersId
	 *            用户id
	 * @param status
	 *            提现状态;0:待审核;1:审核通过;2审核未通过
	 * @param isRequesId
	 *            是否需要请求ID 1为需要 2为不需要
	 * @param isdate
	 *            true 当前时间>=审核时间+1 的记录
	 * @return
	 */
	public PageUtil<TxRecord> getTxRecordByUserId(PageUtil<TxRecord> pageUtil, long usersId, String status, String isRequesId, boolean isdate) {
		StringBuffer hql = new StringBuffer();
		ArrayList<Object> list = new ArrayList<Object>();
		hql.append("FROM TxRecord cz ");
		hql.append(" WHERE 1= 1 ");
		if (!QwyUtil.isNullAndEmpty(usersId)) {
			hql.append(" AND cz.usersId = ? ");
			list.add(usersId);
		}
		if (!QwyUtil.isNullAndEmpty(status)) {
			hql.append(" AND cz.status = ? ");
			list.add(status);
		}
		if (isdate) {
			hql.append(" AND date_add_interval(DATE_FORMAT(cz.checkTime,'%Y-%m-%d'), 1, DAY) <=NOW() ");
		}
		if (!QwyUtil.isNullAndEmpty(isRequesId) && "1".equals(isRequesId)) {
			hql.append(" AND (cz.requestId is not null  and cz.requestId != null and cz.requestId != '' and cz.requestId != 'NULL')");
		} else if (!QwyUtil.isNullAndEmpty(isRequesId) && "2".equals(isRequesId)) {
			hql.append(" AND (cz.requestId is  null  or cz.requestId = null or cz.requestId = '' or cz.requestId = 'NULL')");
		}
		hql.append(" ORDER BY cz.insertTime DESC ");
		return (PageUtil<TxRecord>) dao.getPage(pageUtil, hql.toString(), list.toArray());

	}

	/**
	 * 根据充值ID,获取充值记录
	 * 
	 * @param id
	 * @return
	 */
	public CzRecord getCzRecordById(String id) {
		StringBuffer hql = new StringBuffer();
		hql.append("FROM CzRecord cz ");
		hql.append(" WHERE cz.id = ? ");
		return (CzRecord) dao.findJoinActive(hql.toString(), new Object[] { id });
	}

	/**
	 * 用户提现;
	 * 
	 * @param usersId
	 *            用户id
	 * @param amount
	 *            提现金额(元)
	 * @param userip
	 *            用户支付时使用的网络终端IP
	 * @param callBackUrl
	 *            回调地址
	 * @return JOSN格式;
	 * @throws Exception
	 */
	public String llCashPay(long usersId, Double amount, String userip, String callBackUrl, Account account, boolean isUpdateAccount) {
		synchronized (LockHolder.getLock(usersId)) {
			log.info("进入连连提现" + usersId);
			String json = "";
			try {
				String uuid = UUID.randomUUID().toString();
				// Account account =
				// lianLianPayBean.getAccountByUsersId(usersId, "1");
				if (QwyUtil.isNullAndEmpty(account)) {
					json = QwyUtil.getJSONString("error", "提现失败,没有找到绑定的银行卡.");
				} else {

					List<TxRecord> records = findRecordsByUid(usersId, 1, 20, true, true);
					if (!QwyUtil.isNullAndEmpty(records)) {
						// 已经有在审核的提现记录或者当天已经提现成功;每天只能申请提现一次;
						return QwyUtil.getJSONString("error", "为防止恶意提现,每天只能提现一次");
					}
					String timeString = QwyUtil.fmyyyyMMddHHmmss2.format(new Date());
					CashBean reqBean = new CashBean();
					reqBean.setApi_version("1.2");
					reqBean.setOid_partner(EnvConstants.PARTNER);
					reqBean.setSign_type("RSA");
					reqBean.setUser_id(account.getUsersId() + "");
					reqBean.setAcct_name(account.getBankAccountName());
					reqBean.setCard_no(DESEncrypt.jieMiBankCard(account.getBankAccount()));
					reqBean.setNo_order(uuid);
					reqBean.setProvince_code(account.getProvinceCode());
					reqBean.setCity_code(account.getCityCode());
					reqBean.setBrabank_name(account.getBraBankName());
					// reqBean.setPlatform("mobile");
					reqBean.setInfo_order("提现");
					reqBean.setDt_order(timeString);
					reqBean.setMoney_order(amount + "");
					reqBean.setFlag_card("0");
					reqBean.setNotify_url(callBackUrl);
					reqBean.setSign(genSign(JSONObject.fromObject(reqBean)));
					String reqJson = JSONObject.fromObject(reqBean).toString();
					log.info("提现请求的值" + reqJson);
					String jsonStr = userRechargeBean.addTxRecord(usersId, QwyUtil.calcNumber(amount, 100, "*").doubleValue(), "2", "用户提现,卡号尾号为: " + account.getCardLast(), account.getId(), "0",
							userip, uuid);
					Map<String, Object> tempJson = QwyUtil.JsonStrtoMap(jsonStr);
					if (!QwyUtil.isNullAndEmpty(tempJson) && "ok".equals(tempJson.get("status"))) {
						String txId = tempJson.get("json") + "";
						TxRecord tx = (TxRecord) dao.findById(new TxRecord(), txId);
						HttpRequestSimple httpclent = HttpRequestSimple.getInstance();
						String resJson = httpclent.postSendHttp(EnvConstants.CASH_PAY, reqJson);
						log.info("结果报文为:" + resJson);
						JSONObject obj = JSONObject.fromObject(resJson);
						String tempStatus = QwyUtil.get(obj, "ret_code");
						if ("0000".equalsIgnoreCase(tempStatus)) {
							if (isUpdateAccount) {
								dao.saveOrUpdate(account);
							}
							log.info(tx.getId() + "请求提现的成功记录");
							return QwyUtil.getJSONString("ok", "申请提现成功");
						} else {
							log.info(tx.getId() + "请求提现失败");
							String msg = QwyUtil.get(obj, "ret_msg");
							tx.setYbOrderId(msg);
							tx.setStatus("2");
							tx.setErrorCode(tempStatus);
							tx.setCheckTime(new Date());
							dao.saveOrUpdate(tx);
							myWalletBean.addTotalMoneyLeftMoney(tx.getUsersId(), tx.getMoney(), "txfk", "", "提现失败,返款提现金额到用户帐号");
							json = QwyUtil.getJSONString("error", "提现失败,请联系客服");
							return json;
						}
					} else {
						json = jsonStr;
					}
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				log.error("操作异常: ", e);
				json = QwyUtil.getJSONString("error", "提现失败,请联系客服,code: 8889");
			}
			return json;
		}
	}

	private static String genSign(JSONObject reqObj) {
		String sign = reqObj.getString("sign");
		String sign_type = reqObj.getString("sign_type");
		// // 生成待签名串
		String sign_src = genSignData(reqObj);
		log.info("商户[" + reqObj.getString("oid_partner") + "]待签名原串" + sign_src);
		if ("MD5".equals(sign_type)) {
			sign_src += "&key=" + EnvConstants.MD5_KEY;
			return signMD5(sign_src);
		}
		if ("RSA".equals(sign_type)) {
			return getSignRSA(sign_src);
		}
		return null;
	}

	private static String signMD5(String signSrc) {
		try {
			return Md5Algorithm.getInstance().md5Digest(signSrc.getBytes("utf-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * RSA签名验证
	 * 
	 * @param reqObj
	 * @return
	 */
	public static String getSignRSA(String sign_src) {
		return TraderRSAUtil.sign(EnvConstants.RSA_PRIVATE, sign_src);

	}

	/**
	 * 生成待签名串
	 * 
	 * @param paramMap
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static String genSignData(JSONObject jsonObject) {
		StringBuffer content = new StringBuffer();

		// 按照key做首字母升序排列
		List<String> keys = new ArrayList<String>(jsonObject.keySet());
		Collections.sort(keys, String.CASE_INSENSITIVE_ORDER);
		for (int i = 0; i < keys.size(); i++) {
			String key = (String) keys.get(i);
			// sign 和ip_client 不参与签名
			if ("sign".equals(key)) {
				continue;
			}
			String value = (String) jsonObject.getString(key);
			// 空串不参与签名
			if (null == value) {
				continue;
			}
			content.append((i == 0 ? "" : "&") + key + "=" + value);
		}
		String signSrc = content.toString();
		if (signSrc.startsWith("&")) {
			signSrc = signSrc.replaceFirst("&", "");
		}
		return signSrc;
	}

	/**
	 * 根据用户名和交易密码查找用户;
	 * 
	 * @param users
	 *            Users
	 * @return Users
	 */
	public Users findUsersByUsernameAndPayPassword(String userName, String payPassword) {
		StringBuffer buff = new StringBuffer();
		buff.append("FROM Users us ");
		buff.append("WHERE (us.username =? or us.phone=?)");
		buff.append("AND us.payPassword = ? ");
		buff.append("AND us.userType >= 0 ");
		return (Users) dao.findJoinActive(buff.toString(),
				new Object[] { DESEncrypt.jiaMiUsername(userName.toLowerCase()), DESEncrypt.jiaMiUsername(userName.toLowerCase()), DESEncrypt.jiaMiPassword(payPassword) });
	}

	/**
	 * 根据用户ID获取提现记录
	 * 
	 * @param uid
	 *            用户ID
	 * @param currentPage
	 *            当前页
	 * @param pageSize
	 *            一页条数
	 * @param isInsertTime
	 *            是否需要当前日期
	 * @return
	 */
	public List<TxRecord> findRecordsByUid(Long uid, Integer currentPage, Integer pageSize, boolean isDSH, boolean isInsertTime) throws Exception {
		StringBuffer hql = new StringBuffer();
		hql.append(" FROM TxRecord tx ");
		hql.append(" WHERE tx.usersId = ? ");
		if (isDSH) {
			hql.append(" AND tx.status in ('0','1','3') ");
		}
		if (isInsertTime) {
			hql.append(" AND tx.insertTime >= '" + QwyUtil.fmyyyyMMdd.format(new Date()) + "' ");
		}
		hql.append(" ORDER BY tx.insertTime DESC ");
		log.info(QwyUtil.fmyyyyMMdd.format(new Date()));
		return dao.findAdvList(hql.toString(), new Object[] { uid }, currentPage, pageSize);
	}

	/**
	 * 根据 订单ID,获取充值记录
	 * 
	 * @param id
	 * @return
	 */
	public CzRecord getCzRecordByOrderId(String orderid) {
		StringBuffer hql = new StringBuffer();
		hql.append("FROM CzRecord cz ");
		hql.append(" WHERE cz.orderId = ? ");
		return (CzRecord) dao.findJoinActive(hql.toString(), new Object[] { orderid });
	}

	/**
	 * 根据 订单ID,获取充值记录
	 * 
	 * @param id
	 * @return
	 */
	public CzRecord updateRecord(CzRecord record) {
		dao.update(record);
		return record;
	}

	/**
	 * 查询半小时以外的网银充值的数据
	 * 
	 * @param args
	 */
	public PageUtil findWyCzRecord(PageUtil pageUtil) {
		List<Object> ob = new ArrayList<Object>();
		StringBuffer buffer = new StringBuffer();
		buffer.append(" FROM CzRecord cz ");
		buffer.append(" WHERE cz.type = '1' ");
		buffer.append(" AND cz.status = '0' ");
		buffer.append(" AND cz.insertTime <= ? ");
		ob.add(new Date(new Date().getTime() - 1000 * 60 * 15));
		return dao.getByHqlAndHqlCount(pageUtil, buffer.toString(), buffer.toString(), ob.toArray());
	}

	/**
	 * 添加提现记录;状态为(待审核);<br>
	 * 数据插入数据库; 充值成功后,要修改充值记录的状态;
	 * 
	 * @param usersId
	 *            用户id
	 * @param money
	 *            充值金额(分)
	 * @param type
	 *            提现类型;0:银行卡提现;1:支付宝提现;
	 * @param note
	 *            备注;
	 * @param accountId
	 *            {@link Account}.id
	 * @param drawType
	 *            //提现类型; 0:T+0到账; 1:T+1到账;
	 * @param userIp
	 *            用户提现时的IP
	 * @param requestId
	 *            用户提现时的请求的ID
	 * @return String ""为字符串;有字符串为提现失败;
	 */
	public String addTxRecord(long usersId, double money, String type, String note, String accountId, String drawType, String userIp, String requestId) {
		ApplicationContext context = ApplicationContexts.getContexts();
		// SessionFactory sf = (SessionFactory)
		// context.getBean("sessionFactory");
		PlatformTransactionManager tm = (PlatformTransactionManager) context.getBean("transactionManager");
		TransactionStatus ts = tm.getTransaction(new DefaultTransactionDefinition());
		try {
			if (QwyUtil.isNullAndEmpty(accountId)) {
				log.info("提交提现申请失败,账户ID为 null ");
				return QwyUtil.getJSONString("error", "提交提现申请失败,账户ID为 null ");
			}
			// Account ac =(Account) dao.findById(new Account(), accountId);
			Users users = (Users) dao.findById(new Users(), usersId);
			if (money > users.getUsersInfo().getLeftMoney().doubleValue()) {
				return QwyUtil.getJSONString("error", "提现失败,可提现金额不足");
			}
			// 修改于2015-06-03 15:37:37 提现直接扣除可用余额;暂不放进冻结金额
			// boolean isOk = myWalletBean.subLeftMoneyToFreezeMoney(usersId,
			// money, "txsq", "提现申请", note);
			boolean isOk = myWalletBean.subTotalMoneyLeftMoney(usersId, money, "tx", "提现", note);
			if (isOk) {
				TxRecord tx = new TxRecord();
				tx.setUsersId(usersId);
				tx.setMoney(money);
				tx.setStatus("0");
				tx.setType(type);
				tx.setAccountId(accountId);
				tx.setInsertTime(new Date());
				tx.setDrawType(drawType);
				tx.setRequestId(requestId);
				tx.setUserIp(userIp);
				tx.setTxStatus("0");
				/// tx.setRequestId(UUID.randomUUID().toString());请求ID,由客服审核通过之后,再填写进去
				tx.setNote(note);
				dao.saveOrUpdate(tx);
				tm.commit(ts);
				return QwyUtil.getJSONString("ok", tx.getId());
			}
		} catch (Exception e) {
			log.error("提现异常", e);
			tm.rollback(ts);
		}
		return QwyUtil.getJSONString("error", "提现申请失败,请联系客服,code: 8845");
	}

	/**
	 * 根据请求ID,获取提现记录
	 * 
	 * @param id
	 * @return
	 */
	public TxRecord getTxRecordByRequestId(String requestId) {
		StringBuffer hql = new StringBuffer();
		hql.append("FROM TxRecord tx ");
		hql.append(" WHERE tx.requestId = ? ");
		return (TxRecord) dao.findJoinActive(hql.toString(), new Object[] { requestId });
	}

	/**
	 * 查询省份
	 * 
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public List<Map<String, String>> queryProvince() {
		List list = dao.LoadAllSql("SELECT city_code,province FROM city WHERE city=''", null);
		Object[] obj = null;
		Map<String, String> map = null;
		List<Map<String, String>> returnList = new ArrayList<Map<String, String>>();
		for (int i = 0; i < list.size(); i++) {
			obj = (Object[]) list.get(i);
			map = new HashMap<String, String>();
			map.put("city_code", obj[0].toString());
			map.put("province", obj[1].toString());
			returnList.add(map);
		}
		return returnList;
	}

	/**
	 * 查询地市
	 * 
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public List<Map<String, String>> queryCity(String province) {
		List list = dao.LoadAllSql("SELECT city,city_code FROM city WHERE province=? AND city!=''", new Object[] { province });
		Object[] obj = null;
		Map<String, String> map = null;
		List<Map<String, String>> returnList = new ArrayList<Map<String, String>>();
		for (int i = 0; i < list.size(); i++) {
			obj = (Object[]) list.get(i);
			map = new HashMap<String, String>();
			map.put("city", obj[0].toString());
			map.put("city_code", obj[1].toString());
			returnList.add(map);
		}
		return returnList;
	}

	/**
	 * 根据银行卡号，city_code查询连连大额行号信息
	 * 
	 * @param cardNo
	 *            卡号
	 * @param cityCode
	 *            城市编号
	 */
	public String findCodeByBankInfo(String cardNo, String cityCode) {
		JSONObject reqObj = new JSONObject();
		reqObj.put("oid_partner", EnvConstants.PARTNER);
		reqObj.put("card_no", cardNo);
		reqObj.put("brabank_name", "行");
		reqObj.put("city_code", cityCode);
		reqObj.put("sign_type", "MD5");
		String sign = LLPayUtil.addSign(reqObj, EnvConstants.RSA_PRIVATE, EnvConstants.MD5_KEY);
		reqObj.put("sign", sign);
		String reqJSON = reqObj.toString();
		System.out.println("用户签约信息查询 API请求报文[" + reqJSON + "]");
		log.info("用户签约信息查询 API请求报文[" + reqJSON + "]");
		String resJSON = HttpRequestSimple.getInstance().postSendHttp(EnvConstants.QUERY_CNAPSCODE_URL, reqJSON);
		System.out.println("用户签约信息查询 API响应报文[" + resJSON + "]");
		log.info("用户签约信息查询 API响应报文[" + resJSON + "]");
		return resJSON;
	}

	public static void main(String[] args) {
		// System.out.println(new Date(new Date().getTime() - 1000*60*15));
		// System.out.println((int)(System.currentTimeMillis() / 1000));
		// System.out.println(Calendar.getInstance().get(Calendar.DAY_OF_WEEK)-1);

		UserRechargeBean bean = new UserRechargeBean();
		System.out.println(bean.findCodeByBankInfo("6214837804778177", "440300"));
	}

}
