package com.zfgt.login.bean;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.zfgt.account.bean.UserRechargeBean;
import com.zfgt.common.ApplicationContexts;
import com.zfgt.common.bean.MyWalletBean;
import com.zfgt.common.bean.PlatformBean;
import com.zfgt.common.bean.SystemConfigBean;
import com.zfgt.common.util.DESEncrypt;
import com.zfgt.common.util.LockHolder;
import com.zfgt.common.util.MobileLocationUtil;
import com.zfgt.common.util.MyRedis;
import com.zfgt.common.util.PropertiesUtil;
import com.zfgt.common.util.QwyUtil;
import com.zfgt.common.util.SMSUtil;
import com.zfgt.login.dao.RegisterUserDAO;
import com.zfgt.orm.FundRecord;
import com.zfgt.orm.Product;
import com.zfgt.orm.RedPackets;
import com.zfgt.orm.RedPacketsRecord;
import com.zfgt.orm.SendRedPackets;
import com.zfgt.orm.SmsRecord;
import com.zfgt.orm.Users;
import com.zfgt.orm.UsersInfo;

import net.sf.json.JSONObject;

/**
 * 注册用户Bean层;
 * 
 * @author qwy
 *
 *         2015-4-18上午3:50:01
 */
@Service
public class RegisterUserBean {
	private Logger log = Logger.getLogger(RegisterUserBean.class);
	@Resource
	private RegisterUserDAO dao;

	@Resource
	private PlatformBean platformBean;
	@Resource
	private UsersLoginBean loginBean;
	@Resource
	private UserRechargeBean userRechargeBean;
	@Resource
	private SystemConfigBean systemConfigBean;
	@Resource
	private MyWalletBean myWalletBean;

	/**
	 * 根据用户名查找Users<br>
	 * 传进来的参数不需要加密;内部已做加密处理;
	 * 
	 * @param username
	 *            用户名;
	 * @return {@link Users}
	 */
	@SuppressWarnings("unchecked")
	public Users getUsersByUsername(String username) {
		try {
			if (QwyUtil.isNullAndEmpty(username))
				return null;
			StringBuffer hql = new StringBuffer();
			hql.append("FROM Users us ");
			hql.append("WHERE us.username = ? or us.phone=?");
			List<Users> list = dao.LoadAll(hql.toString(), new Object[] { DESEncrypt.jiaMiUsername(username.toLowerCase()), DESEncrypt.jiaMiUsername(username.toLowerCase()) });
			if (!QwyUtil.isNullAndEmpty(list)) {
				return list.get(0);
			}
		} catch (Exception e) {
			log.error("操作异常: ", e);
		}
		return null;
	}

	/**
	 * 注册新用户;
	 * 
	 * @param user
	 *            带有用户名和密码的用户实体;
	 * @param type
	 *            注册类型,0:手机注册; 1:邮箱注册;2:其它; 默认为0
	 * @return
	 */
	public String registerNewUser(Users newUser, String type) {
		if (QwyUtil.isNullAndEmpty(newUser))
			return "";
		log.info("进入注册新用户方法...");
		String id = "";
		synchronized (LockHolder.getLock(newUser.getUsername())) {

			/*
			 * Users u =
			 * getUsersByUsername(DESEncrypt.jiaMiUsername(user.getUsername().
			 * toLowerCase())); if(!QwyUtil.isNullAndEmpty(u)){
			 * log.info("用户名已存在"); tm.rollback(ts); return id; }
			 */
			ApplicationContext context = ApplicationContexts.getContexts();
			// SessionFactory sf = (SessionFactory)
			// context.getBean("sessionFactory");
			PlatformTransactionManager tm = (PlatformTransactionManager) context.getBean("transactionManager");
			TransactionStatus ts = tm.getTransaction(new DefaultTransactionDefinition());
			try {
				log.info("进入注册新用户方法...事务开启");
				// Users newUser = new Users();
				newUser.setUsername(DESEncrypt.jiaMiUsername(newUser.getUsername().toLowerCase()));
				newUser.setPassword(DESEncrypt.jiaMiPassword(newUser.getPassword()));
				newUser.setUserType(0L);// 0为普通用户; 详情见Users.userType的说明;
				newUser.setUserStatus("0");
				newUser.setInsertTime(new Date());
				newUser.setUpdateTime(newUser.getInsertTime());
				newUser.setBuyFreshmanProduct("0");
				// newUser.setPayPassword(newUser.getPassword());
				newUser.setRegistType(type);
				if ("0".equals(type)) {
					newUser.setPhone(newUser.getUsername());
				}
				newUser.setRegistPlatform("0");
				dao.save(newUser);
				id = newUser.getId().toString();
				String userInfoId = registerNewUserInfo(newUser, type);
				newUser.setUserInfoId(Long.parseLong(userInfoId));
				dao.saveOrUpdate(newUser);
				platformBean.updateRegisterCount();
				tm.commit(ts);
				log.info("进入注册新用户方法...事务提交");
				final Long userId = newUser.getId();
				userRechargeBean.sendHongBao(userId, 5000D, null, "0", 10001, "注册送投资券50元");
				final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
				// 5秒查询一次结果,查询有状态返回后,则结束线程;
				scheduler.scheduleAtFixedRate(new Runnable() {
					@Override
					public void run() {
						try {
							if (setMobile(userId)) {
								// 查询归属地
								System.out.println("查询归属地");
								log.info("查询归属地");
								scheduler.shutdown();
							}
						} catch (Exception e) {
							log.error("操作异常: ", e);
						}
					}
				}, 0, 3, TimeUnit.SECONDS);
				final RedPackets red =	getPhone(newUser.getUsername());
				final ScheduledExecutorService redScheduler = Executors.newScheduledThreadPool(1);
				// 3秒查询一次结果,发放用户注册红包
				redScheduler.scheduleAtFixedRate(new Runnable() {
					@Override
					public void run() {
						try {
							if(!QwyUtil.isNullAndEmpty(red)){
								UsersInfo info = getByUid(userId);
								//增加总资产
								info.setTotalMoney(QwyUtil.calcNumber(info.getTotalMoney(),red.getInMoney(), "+").doubleValue());
								//增加可用余额
								info.setLeftMoney(QwyUtil.calcNumber(info.getLeftMoney(),red.getInMoney(), "+").doubleValue());
								UsersInfo invites = null;
								SendRedPackets send = new SendRedPackets();//添加发放红包记录
								RedPacketsRecord record = new RedPacketsRecord();//添加发放红包的流水记录
								FundRecord currUserRecord =	myWalletBean.packFundRecord(info.getUsersId(), red.getInMoney(), "0", "cz", "拆红包活动", "红包活动注册奖励", info.getLeftMoney());
								FundRecord invitesRecord =null;
								if(!QwyUtil.isNullAndEmpty(red.getInvitesId())){
									
									//邀请人资金流水
									send.setInviteInMoney(QwyUtil.calcNumber(red.getInMoney(),0.3,"*").doubleValue());
									send.setInvitesId(red.getInvitesId());
									record.setInvitesId(red.getInvitesId());
									record.setInviteInMoney(QwyUtil.calcNumber(red.getInMoney(),0.3,"*").doubleValue());
									
									invites = getByUid(red.getInvitesId());
									invites.setTotalMoney(QwyUtil.calcNumber(invites.getTotalMoney(),QwyUtil.calcNumber(red.getInMoney(),0.3,"*").doubleValue(), "+").doubleValue());
									invites.setLeftMoney(QwyUtil.calcNumber(invites.getLeftMoney(), QwyUtil.calcNumber(red.getInMoney(),0.3,"*").doubleValue(), "+").doubleValue());
									invitesRecord = myWalletBean.packFundRecord(invites.getUsersId(),QwyUtil.calcNumber(red.getInMoney(),0.3,"*").doubleValue(),  "0", "cz", "拆红包活动", "红包活动邀好友返利", invites.getLeftMoney());
								}else{
									send.setInviteInMoney(0D);
									send.setInvitesId(null);
									record.setInvitesId(null);
									record.setInviteInMoney(0D);
								}
								red.setIsHongBao("1");
								//添加发放红包记录
								send.setInMoney(red.getInMoney());
								send.setInsertTime(new Date());
								send.setUpdateTime(new Date());
								send.setStatus("0");
								send.setRedId(red.getId());
								send.setUsersId(userId);
								//添加发放红包的流水记录
								
								record.setInMoney(red.getInMoney());
								record.setInsertTime(new Date());
								record.setUpdateTime(new Date());
								record.setStatus("0");
								record.setRedId(red.getId());
								record.setUsersId(userId);
								record.setType("0");
								sengHongBao(info,invites,red,send,record,currUserRecord,invitesRecord);
								redScheduler.shutdown();
							}
						} catch (Exception e) {
							log.error("操作异常: ", e);
						}
					}
				}, 0, 1, TimeUnit.SECONDS);
				return id;
			} catch (NumberFormatException e) {
				log.error("操作异常: ", e);
				tm.rollback(ts);
			} catch (Exception e) {
				log.error("操作异常: ", e);
				tm.rollback(ts);
			}
			log.info("进入注册新用户方法...事务回滚");
		}
		return id;
	}

	/**
	 * 设置手机号码的归属地;
	 * 
	 * @param usersId
	 *            用户ID
	 * @return
	 */
	public boolean setMobile(Long usersId) {
		Users users = getUsersById(usersId);
		if (!QwyUtil.isNullAndEmpty(users)) {
			if (!QwyUtil.isNullAndEmpty(users.getPhone())) {
				Map<String, Object> map = MobileLocationUtil.getMobileLocation(DESEncrypt.jieMiUsername(users.getPhone()));
				users.setProvince(map.get("province") + "");
				users.setCity(map.get("city") + "");
				users.setCardType(map.get("cardType") + "");
				dao.saveOrUpdate(users);
				return true;
			}
		}
		return false;

	}

	/**
	 * 注册用户信息表;
	 * 
	 * @param user
	 *            带有usersId的Users实体
	 * @param type
	 *            注册类型,0:手机注册; 1:邮箱注册;2:其它; 默认为0
	 * @return
	 */
	public String registerNewUserInfo(Users user, String type) {
		if (null == user)
			return "";
		UsersInfo ui = new UsersInfo();
		ui.setUsersId(user.getId());
		ui.setTotalMoney(0d);
		ui.setFreezeMoney(0d);
		ui.setLeftMoney(0d);
		ui.setIsVerifyPhone("0");
		ui.setIsVerifyEmail("0");
		if ("0".equals(type)) {
			// 如果是用手机注册的,则是已经通过短信验证码验证过的,则已绑定;
			ui.setPhone(user.getPhone());
			ui.setIsVerifyPhone("1");
		} else if ("1".equals(type)) {
			// 如果是用邮箱注册,邮箱的认证是在注册成功后,再进行绑定;
			ui.setEmail(user.getUsername().toLowerCase());
		}
		ui.setIsVerifyIdcard("0");
		ui.setInsertTime(user.getInsertTime());
		ui.setUpdateTime(user.getUpdateTime());
		ui.setNickName("投资用户");
		ui.setIsBindBank("0");
		ui.setInvestCount(0L);
		ui.setTotalProfit(0D);
		dao.save(ui);
		return ui.getId().toString();
	}

	/**
	 * 更改账号密码;
	 * 
	 * @param usersId
	 *            用户ID
	 * @param newPassword
	 *            新密码
	 * @return
	 */
	public boolean modifyPassword(long usersId, String newPassword) {
		Users user = getUsersById(usersId);
		if (null == user)
			return false;
		user.setUpdatePasswordTime(new Date());
		user.setPassword(DESEncrypt.jiaMiPassword(newPassword));
		dao.update(user);
		return true;
	}

	/**
	 * 更改用户的支付密码;
	 * 
	 * @param usersId
	 *            用户ID
	 * @param newPayPassword
	 *            新支付密码
	 * @return
	 */
	public boolean modifyPayPassword(long usersId, String newPayPassword) {
		Users user = getUsersById(usersId);
		if (null == user)
			return false;
		user.setPayPassword(DESEncrypt.jiaMiPassword(newPayPassword));
		dao.update(user);
		return true;
	}

	/**
	 * 根据用户ID查找用户;
	 * 
	 * @param usersId
	 *            用户id
	 * @return
	 */
	public Users getUsersById(long usersId) {
		return (Users) dao.findById(new Users(), usersId);
	}

	/**
	 * 生成验证码并保存到缓存中;
	 * 
	 * @param username
	 *            用户名
	 * @param type
	 *            类型 1为注册，2为找回登录密码, 3找回交易密码 ，4 解绑银行卡，5 更换账号
	 * @return
	 */
	public String sendYZM(String username, String type) {
		return sendYZM(username, type, null);
		// return "";
	}

	/**
	 * 生成验证码并保存到缓存中;
	 * 
	 * @param username
	 *            用户名
	 * @param type
	 *            类型 1为注册，2为找回登录密码, 3找回交易密码 ，4 解绑银行卡
	 * @param wh
	 *            尾号
	 * @return
	 */
	public String sendYZM(String username, String type, String wh) {
		String ychar = "0,1,2,3,4,5,6,7,8,9";
		int wei = 6;
		String[] ychars = ychar.split(",");
		String authentication = "";
		Random rdm = new Random();
		for (int i = 0; i < wei; i++) {
			int j = (rdm.nextInt() >>> 1) % 10;
			if (j > 10)
				j = 0;
			authentication = authentication + ychars[j];
		}

		MyRedis yibu = new MyRedis();
		System.out.println("验证码:" + authentication);
		yibu.setex(username + "bym" + type, 1 * 60 * 30, authentication);
		Object isSendMessage = PropertiesUtil.getProperties("isSendMessage");
		// #是否开启发送短信;0:否;1:是;
		if (!QwyUtil.isNullAndEmpty(isSendMessage) && "1".equals(isSendMessage.toString())) {
			// 发送短信;
			System.out.println("发送短信");
			return sendSms2(username, authentication, type, wh);
		} else {
			System.out.println("不发送短信");
			return "";
		}
	}

	/**
	 * 生成验证码并保存到手机中
	 * 
	 * @param username
	 *            用户名
	 * @param type
	 *            1为注册，2为找回登录密码, 3找回交易密码
	 * @return
	 */
	public String getYzm(String username, String type) {
		MyRedis yibu = new MyRedis();
		return yibu.get(username + "bym" + type);
	}

	/**
	 * 发送短信验证码;
	 * 
	 * @param mobile
	 *            手机号;
	 * @param validateCode
	 *            验证码
	 * @param type
	 *            类型; 1:注册; 2:找回登录密码; 3:找回交易密码;4 解绑银行卡
	 * @return
	 */
	public String sendSms2(String mobile, String validateCode, String type, String wh) {
		try {
			String smsContent = SMSUtil.smsContent(systemConfigBean.findSystemConfig().getSmsQm(), validateCode, type, wh);
			SmsRecord smsRecord = packSmsRecord(smsContent, mobile);
			Map<String, Object> map = SMSUtil.sendYzm2(mobile, validateCode, smsContent);
			if (map.get("error").toString().equalsIgnoreCase("0")) {
				JSONObject json = JSONObject.fromObject(map.get("result"));
				smsRecord.setStatus("1");
				smsRecord.setSid(QwyUtil.get(json, "sid"));
				smsRecord.setUpdateTime(new Date());
				smsRecord.setCode(map.get("message") + "");
				dao.update(smsRecord);
				return "";
			} else {
				// Object code = map.get("message");
				smsRecord.setStatus("2");
				smsRecord.setUpdateTime(new Date());
				smsRecord.setCode(map.get("code") + "");
				smsRecord.setNote(map.get("detail") + "");
				dao.update(smsRecord);
				/*
				 * if(!QwyUtil.isNullAndEmpty(code) &&
				 * code.toString().equals("8")){ //30秒内不能重复发送 return
				 * "发送验证码太频繁,请稍后再试"; }
				 */
				return "发送验证码失败";
			}

		} catch (Exception e) {
			log.error("操作异常: ", e);
		}
		return "发送验证码失败";

	}

	/**
	 * 发送短信验证码;
	 * 
	 * @param mobile
	 *            手机号;
	 * @param validateCode
	 *            验证码
	 * @param type
	 *            类型; 1:注册; 2:找回登录密码; 3:找回交易密码;4 解绑银行卡
	 * @return
	 */
	// public String sendSms(String mobile,String validateCode,String
	// type,String wh){
	// try {
	// String smsContent = SMSUtil.smsContent(validateCode, type,wh);
	// SmsRecord smsRecord = packSmsRecord(smsContent, mobile);
	// Map<String, Object> map = SMSUtil.sendYzm(mobile, validateCode,
	// smsContent);
	// if (map.get("msg").toString().equalsIgnoreCase("ok")) {
	// JSONObject json = JSONObject.fromObject(map.get("result"));
	// smsRecord.setStatus("1");
	// smsRecord.setSid(QwyUtil.get(json, "sid"));
	// smsRecord.setUpdateTime(new Date());
	// smsRecord.setCode(map.get("code")+"");
	// dao.save(smsRecord);
	// return "";
	// } else {
	// Object code = map.get("code");
	// smsRecord.setStatus("2");
	// smsRecord.setUpdateTime(new Date());
	// smsRecord.setCode(map.get("code")+"");
	// smsRecord.setNote(map.get("detail")+"");
	// dao.save(smsRecord);
	// if(!QwyUtil.isNullAndEmpty(code) && code.toString().equals("8")){
	// //30秒内不能重复发送
	// return "发送验证码太频繁,请稍后再试";
	// }
	// return "发送验证码失败";
	// }
	//
	// } catch (Exception e) {
	// log.error("操作异常: ",e);
	// }
	// return "发送验证码失败";
	//
	// }

	/**
	 * 获取新手产品的理财ID;
	 * 
	 * @return
	 */
	public String getFreshmanProductId() {
		StringBuffer hql = new StringBuffer();
		hql.append("FROM Product pro ");
		hql.append(" WHERE pro.productStatus = '0' ");
		hql.append(" AND pro.productType = '1' ");
		Object obj = dao.findJoinActive(hql.toString(), null);
		if (QwyUtil.isNullAndEmpty(obj))
			return "";
		Product pro = (Product) obj;
		return pro.getId();
	}

	/**
	 * 获取短信记录
	 * 
	 * @param smsContent
	 * @param mobile
	 * @return
	 */
	public SmsRecord packSmsRecord(String smsContent, String mobile) {
		SmsRecord smsRecord = new SmsRecord();
		smsRecord.setInsertTime(new Date());
		smsRecord.setMobile(mobile);
		smsRecord.setSmsContent(smsContent);
		smsRecord.setStatus("0");
		dao.save(smsRecord);
		if (!QwyUtil.isNullAndEmpty(smsRecord.getId())) {
			return (SmsRecord) dao.findById(new SmsRecord(), smsRecord.getId());
		}
		return smsRecord;
	}

	/**
	 * 获取验证码时,设置120秒的生效时间;
	 * 
	 * @param username
	 *            用户名
	 * @return
	 */
	public String setYmYzm(String username) {

		MyRedis yibu = new MyRedis();
		String time = new Date().getTime() + "";
		yibu.setex(username, 120, time);
		return time;
	}

	/**
	 * 获取验证码;
	 * 
	 * @param username
	 *            用户名
	 * @return
	 */
	public String getYmYzm(String username) {
		MyRedis yibu = new MyRedis();
		return yibu.get(username);
	}

	/**
	 * 查询归属地是否存在的用户
	 * 
	 * @param isAll
	 *            true 查询不存在的用户信息 ，false 查询所有的用户信息
	 * @param pageSize
	 *            一页大小
	 * @param currentPage
	 *            当前页
	 * @return
	 */
	public List<Users> findUsers(boolean isAll, Integer currentPage, Integer pageSize) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append(" FROM  Users ");
		if (isAll) {
			sql.append(" WHERE province is  null ");
			sql.append(" OR province = '' ");
			sql.append(" OR province = 'null' ");
			sql.append(" OR city is  null ");
			sql.append(" OR city = '' ");
			sql.append(" OR city = 'null' ");
			sql.append(" OR cardType is  null ");
			sql.append(" OR cardType = '' ");
			sql.append(" OR cardType = 'null' ");
		}
		sql.append(" ORDER BY insertTime DESC ");
		return dao.findAdvList(sql.toString(), null, currentPage, pageSize);
	}

	/**
	 * 修改归属地及卡类型
	 * 
	 * @param list
	 *            用户集合
	 * @return
	 */
	public List<Users> setMobileLocation(List<Users> list) throws Exception {
		if (!QwyUtil.isNullAndEmpty(list)) {
			for (Users users : list) {
				if (!QwyUtil.isNullAndEmpty(users.getPhone())) {
					Map<String, Object> map = MobileLocationUtil.getMobileLocation(DESEncrypt.jieMiUsername(users.getPhone()));
					if (!QwyUtil.isNullAndEmpty(users)) {
						users.setProvince(map.get("province") + "");
						users.setCity(map.get("city") + "");
						users.setCardType(map.get("cardType") + "");
					}
				} else {
					users.setProvince("未知");
					users.setCity("未知");
					users.setCardType("未知");
				}
			}
			dao.updateList(list);
		}
		return list;
	}

	/**
	 * 设置归属地及卡类型
	 */
	public boolean updateMobileLocation() throws Exception {
		while (true) {
			List<Users> users = findUsers(true, 1, 100);
			if (!QwyUtil.isNullAndEmpty(users)) {
				setMobileLocation(users);
			} else {
				break;
			}
		}
		return true;
	}

	/**
	 * 根据用户ID获取账户信息
	 * 
	 * @param usersIds
	 *            用户ID
	 * @return
	 */
	public List<UsersInfo> findUsersInfosByUsersIds(String usersIds) {
		StringBuffer buffer = new StringBuffer();
		buffer.append(" FROM UsersInfo ui WHERE ui.usersId in (" + usersIds + ") ");
		return dao.LoadAll(buffer.toString(), null);

	}

	/**
	 * 
	 * 发送短信
	 * 
	 * @param mobile
	 *            手机号码
	 * @param smsContent
	 *            短信内容
	 * @return
	 */
	public boolean sendSms(String mobile, String smsContent) {
		try {
			// String smsContent = SMSUtil.smsContent(authentication,
			// SMSProperties.COMPANY, type);
			SmsRecord smsRecord = packSmsRecord(smsContent, mobile);
			Map<String, Object> map = SMSUtil.sendYzm2(mobile, null, smsContent);
			if (map.get("error").toString().equalsIgnoreCase("0")) {
				// JSONObject json = JSONObject.fromObject(map.get("result"));
				smsRecord.setStatus("1");
				// smsRecord.setSid(QwyUtil.get(json, "sid"));
				smsRecord.setUpdateTime(new Date());
				smsRecord.setNote(map.get("message") + "");
				dao.save(smsRecord);
				return true;
			} else {
				smsRecord.setStatus("2");
				smsRecord.setUpdateTime(new Date());
				smsRecord.setNote(map.get("message") + "");
				dao.save(smsRecord);
				return false;
			}

		} catch (Exception e) {
			log.error("操作异常: ", e);
			e.printStackTrace();
		}
		return false;

	}

	/**
	 * 修改用户总喵币
	 * 
	 * @param uid
	 * @param coin
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public UsersInfo updateCoinByUid(long uid, Long coin) throws Exception {
		UsersInfo usersInfo = null;
		StringBuffer buff = new StringBuffer();
		buff.append(" FROM UsersInfo ui where ui.usersId= " + uid);
		List<UsersInfo> list = dao.LoadAll(buff.toString(), null);
		if (list != null && list.size() > 0) {
			usersInfo = list.get(0);
		}
		if (!QwyUtil.isNullAndEmpty(usersInfo.getId())) {
			if (QwyUtil.isNullAndEmpty(usersInfo.getTotalPoint())) {
				usersInfo.setTotalPoint(0l);
			}
			Long totalPoint = (usersInfo.getTotalPoint() + coin);
			if (totalPoint >= 0l) {
				usersInfo.setTotalPoint(totalPoint);
				usersInfo.setUpdateTime(new Date());
				dao.saveOrUpdate(usersInfo);
				return usersInfo;
			}
		}
		return null;
	}

	/**
	 * 查询用户信息
	 * 
	 * @param uid
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public UsersInfo getByUid(long uid) throws Exception {
		UsersInfo usersInfo = null;
		StringBuffer buff = new StringBuffer();
		buff.append(" FROM UsersInfo ui where ui.usersId= " + uid);
		List<UsersInfo> list = dao.LoadAll(buff.toString(), null);
		if (list != null && list.size() > 0) {
			usersInfo = list.get(0);
		}
		return usersInfo;
	}
	
	
	/**
	 * 发放红包
	 * @param currUser 注册用户
	 * @param invitesUser邀请用户
	 * @param red 修改红包是否发放红包
	 * @param send 发放红包记录
	 * @param record发放红包记录流水
	 * @param currUserRecord 当前用户资金流水
	 * @param invitesRecord邀请用户资金流水
	 * @return
	 */
	public boolean sengHongBao(UsersInfo currUser, UsersInfo invitesUser,RedPackets red ,SendRedPackets send,RedPacketsRecord record,FundRecord currUserRecord,FundRecord invitesRecord) {
		synchronized (LockHolder.getLock(currUser.getUsersId())) {
			ApplicationContext context = ApplicationContexts.getContexts();
			SessionFactory sf = (SessionFactory) context.getBean("sessionFactory");
			PlatformTransactionManager tm = (PlatformTransactionManager) context.getBean("transactionManager");
			TransactionStatus ts = tm.getTransaction(new DefaultTransactionDefinition());
			try {
				dao.saveOrUpdate(currUser);
				if(!QwyUtil.isNullAndEmpty(invitesUser)) {
					dao.saveOrUpdate(invitesUser);
					dao.saveOrUpdate(invitesRecord);
				}
				dao.saveOrUpdate(currUserRecord);
				dao.saveOrUpdate(red);
				dao.saveOrUpdate(send);
				dao.saveOrUpdate(record);
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
	 * 用户注册查看是否拆红包
	 * phone 电话号码
	 * @param 
	 * @return
	 * @throws Exception
	 */
	public RedPackets getPhone(String phone){
		try{
			StringBuffer hql = new StringBuffer();
			hql.append("FROM RedPackets us ");
			hql.append("where  us.phone = ? ");
			return (RedPackets) dao.findUniqueResult(hql.toString(), new Object[] {phone}); 
		}
		catch (Exception e) {
			log.error("操作异常: ", e);
		}
		return null;
	}
}
