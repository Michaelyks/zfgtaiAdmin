package com.zfgt.account.bean;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.zfgt.account.dao.ValidateDAO;
import com.zfgt.common.ApplicationContexts;
import com.zfgt.common.bean.YiBaoPayBean;
import com.zfgt.common.util.DESEncrypt;
import com.zfgt.common.util.LockHolder;
import com.zfgt.common.util.QwyUtil;
import com.zfgt.orm.Account;
import com.zfgt.orm.Bank;
import com.zfgt.orm.BankCard;
import com.zfgt.orm.Users;
import com.zfgt.orm.UsersInfo;
@Service
public class ValidateBean {
	private static Logger log = Logger.getLogger(ValidateBean.class); 
	private static SimpleDateFormat fmyyyyMM = new SimpleDateFormat("yyyyMMdd");
	@Resource
	private YiBaoPayBean yiBaoPayBean;
	@Resource
	private ValidateDAO dao;
	
	
	
	/**根据用户id去查找用户绑定的银行卡;
	 * @param usersId 用户id
	 * @return null OR Account
	 */
	public Account getAccountByUsersId(long usersId,String type){
		return yiBaoPayBean.getAccountByUsersId(usersId,type);
	}
	
	/**根据用户id去查找用户绑定的银行卡;
	 * @param usersId 用户id
	 * @return null OR Account
	 * @return
	 */
	public List<Account> getAccountByUsersId(long usersId){
		return yiBaoPayBean.getAccountByUsersId(usersId);
	}
	
	
	/**检查银行卡归属地;
	 * @param cardno 卡号
	 * @return
	 */
	public String bankCardCheck(String cardno){
		String json = "";
		try {
			String bankJson = yiBaoPayBean.bankCardCheck(cardno);
			JSONObject jb = JSONObject.fromObject(bankJson);
			String isvalid = jb.get("isvalid").toString();
			if(isvalid.equals("0")){
				json = QwyUtil.getJSONString("error", "银行卡号无效");
			}else if(isvalid.equals("1")){
				//卡号找到对应的银行;
				String cardtype = jb.get("cardtype").toString();
				if(cardtype.equals("1")){
					//储蓄卡
					json = QwyUtil.getJSONString("ok", jb.get("bankname").toString());
				}else if(cardtype.equals("2")){
					//信用卡
					json = QwyUtil.getJSONString("error", "暂不支持信用卡绑定");
				}else{
					//其它;
					json = QwyUtil.getJSONString("error", "暂不支持该卡类型绑定");
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.error("操作异常: ",e);
			json = QwyUtil.getJSONString("error", "操作异常");
		}
		return json;
	}
	
	/**请求绑定银行卡;
	 * @param ac 账户表;
	 * @return JSON格式;
	 */
	public String bindCard(Account ac){
		String json = "";
		try {
			if(!QwyUtil.isNullAndEmpty(ac)){
				if(!QwyUtil.verifyPhone(ac.getPhone())){
					json = QwyUtil.getJSONString("error", "手机号码格式有误");
					return json;
				}
				ac = yiBaoPayBean.packAccount(ac.getUsersId(), ac.getBankName(), ac.getBankAccount(), ac.getBankAccountName(), ac.getIdcard(), ac.getPhone(), ac.getRegistIp(),ac.getBankCode(),"0");
				String myBankJson = yiBaoPayBean.bindBankcard(ac.getUsersId(), ac.getBankAccount(), ac.getRequestId(), ac.getIdcard(), ac.getBankAccountName(), ac.getPhone(), ac.getRegistIp(),"0");
				JSONObject jb = JSONObject.fromObject(myBankJson);
				Object ob = jb.get("error_code");
				if(!QwyUtil.isNullAndEmpty(ob)){
					if(ob.toString().equals("600020")){
						String error_msg=jb.getString("error_msg");
						if(error_msg.indexOf("idcardno")!=-1){
							json = QwyUtil.getJSONString("error", "身份证不合法");
						}else if(error_msg.indexOf("phone")!=-1){
							json = QwyUtil.getJSONString("error", "手机号码格式有误");
						}else{
							json = QwyUtil.getJSONString("error", jb.getString("error_msg"));
						}
					}else{
						json = QwyUtil.getJSONString("error", jb.getString("error_msg"));
					}
				}else{
					if(!QwyUtil.isNullAndEmpty(jb.get("end"))){
						return QwyUtil.getJSONString("error", jb.getString("message"));
					}
					String requestId = jb.getString("requestid");
					if(!QwyUtil.isNullAndEmpty(requestId)){
						json = QwyUtil.getJSONString("ok", requestId);
					}else{
						json = QwyUtil.getJSONString("error", "绑定失败,请联系客服;code:8000");
					}
				}
			}
		} catch (Exception e) {
			log.error("操作异常: ",e);
			json = QwyUtil.getJSONString("error", "绑定失败,请联系客服;code:8004");
		}
		return json;
	}
	
	/**确认绑卡;
	 * @param account 是上一个请求绑定的Account实体;
	 * @param validatecode 预留手机收到的验证码;
	 * @return JSON
	 */
	public String confirmBindCard(Account account, String validatecode){
		String json = "";
		try {
			if(QwyUtil.isNullAndEmpty(account)){
				return QwyUtil.getJSONString("error", "确认绑卡参数错误;code:8001");
			}
			//同步操作,一个用户*(用身份证来做唯一标识),不能同时操作绑定;
			synchronized (LockHolder.getLock(account.getIdcard())) {
				Object obj =yiBaoPayBean.getAccountByUsersId(account.getUsersId(),account.getType());
				if(!QwyUtil.isNullAndEmpty(obj)){
					return "{\"message\":\"该用户已绑定银行卡\",\"end\":\"error\"}";
				}
				String myBankJson = yiBaoPayBean.confirmBindBankCard(account.getRequestId(), validatecode);
				JSONObject jb = JSONObject.fromObject(myBankJson);
				Object ob = jb.get("error_code");
				if(!QwyUtil.isNullAndEmpty(ob)){
					json = QwyUtil.getJSONString("error", jb.getString("error_msg"));
				}else{
					if(!QwyUtil.isNullAndEmpty(jb.get("end"))){
						return QwyUtil.getJSONString("error", jb.getString("message"));
					}
					String bankCode = jb.getString("bankcode");
					if(!QwyUtil.isNullAndEmpty(bankCode)){
						account.setBankCode(bankCode);
						//account.setType("0");
						if(QwyUtil.isNullAndEmpty(account.getBankName())){
							Bank bank = findBankByBankCode(bankCode);
							account.setBankName(bank.getBankName());
						}
						account = yiBaoPayBean.packAccount(account.getUsersId(), account.getBankName(), account.getBankAccount(), account.getBankAccountName(), account.getIdcard(), account.getPhone(), account.getRegistIp(),account.getBankCode(),"0");
						ApplicationContext context = ApplicationContexts.getContexts();
						//SessionFactory sf = (SessionFactory) context.getBean("sessionFactory");
						PlatformTransactionManager tm = (PlatformTransactionManager) context.getBean("transactionManager");
						TransactionStatus ts = tm.getTransaction(new DefaultTransactionDefinition());
						try {
							//进入数据库前,对银行卡号,身份证号,预留手机号,进行加密;
							account.setIdcard(DESEncrypt.jiaMiIdCard(account.getIdcard()));
							account.setBankAccount(DESEncrypt.jiaMiBankCard(account.getBankAccount()));
							account.setPhone(DESEncrypt.jiaMiUsername(account.getPhone()));
							dao.save(account);
							Users us = getUsersById(account.getUsersId());
							if(!QwyUtil.isNullAndEmpty(us)){
								//绑定成功;
								json = QwyUtil.getJSONString("ok", bankCode);
								UsersInfo info = us.getUsersInfo();
								info.setIsBindBank("1");
								info.setRealName(account.getBankAccountName());
								info.setIdcard(account.getIdcard());
								Object[] objIDCard=QwyUtil.getInfoByIDCard(DESEncrypt.jieMiIdCard(account.getIdcard()));
								if(!QwyUtil.isNullAndEmpty(objIDCard)){
									info.setSex(objIDCard[0]+"");
									info.setAge(objIDCard[1]+"");
									info.setBirthday(fmyyyyMM.parse(objIDCard[2]+""));
								}
								info.setIsVerifyIdcard("1");
								dao.saveOrUpdate(info);
								tm.commit(ts);
							}else{
								//绑定失败;
								json = QwyUtil.getJSONString("error", "绑定失败,找不到用户信息;code:8002");
								tm.rollback(ts);
							}
						} catch (Exception e) {
							// TODO Auto-generated catch block
							log.error("操作异常: ",e);
							json = QwyUtil.getJSONString("error", "绑定失败,操作异常;code:8003");
							tm.rollback(ts);
						}
					}else{
						json = QwyUtil.getJSONString("error", "绑定失败,请联系客服;code:8000");
					}
				}
			}
		} catch (Exception e) {
			log.error("操作异常: ",e);
		}
		return json;
	}
	
	
	
	
	/**根据用户ID获取用户;
	 * @param usersId
	 * @return
	 */
	public Users getUsersById(Long usersId) {
		Users us = (Users)dao.findById(new Users(), usersId);
		return us;
	}


	/**
	 * 根据银行编码获取银行信息
	 * @param bankcode
	 * @return 
	 */
	public Bank findBankByBankCode(String bankcode){
		ArrayList<Object> list=new ArrayList<Object>();
		StringBuffer hql=new StringBuffer();
		hql.append(" FROM Bank ");
		hql.append(" WHERE bankCode = ? ");
		list.add(bankcode);
		Object obj=dao.findJoinActive(hql.toString(), list.toArray());
		if(QwyUtil.isNullAndEmpty(obj)){
			return null;
		}
		return (Bank)obj;
		
	}
	
	
	/**获取绑定的银行卡;
	 * @param usersId 用户Id
	 * @return
	 */
	public List<BankCard> getBindBankCard(long usersId){
		try {
			//获取最新的Users;
			Users us = getUsersById(usersId);
			UsersInfo ui = us.getUsersInfo();
			if("1".equals(ui.getIsBindBank())){
				//绑定了银行卡;并且进行了实名认证;
				List<Account> account = getAccountByUsersId(usersId);
				if(QwyUtil.isNullAndEmpty(account)){
					return null;
				}
				List<BankCard> listBankCard = new ArrayList<BankCard>();
				for (Account ac : account) {
					BankCard bc = new BankCard();
					bc.setAccountId(ac.getId());
					bc.setBankName(ac.getBankName());
					bc.setCardLast(ac.getCardLast());
					bc.setIdCard(ac.getIdcard());
					bc.setRelName(ac.getBankAccountName());
					bc.setType(ac.getType());
					listBankCard.add(bc);
				}
				return listBankCard;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	
	
	
	public static void main(String[] args) {
		//在main方法里面加载spring容器;
		ApplicationContext context = ApplicationContexts.getContexts();
		ValidateBean validateBean = (ValidateBean)context.getBean("validateBean");
		YiBaoPayBean yiBaoPayBean = (YiBaoPayBean)context.getBean("yiBaoPayBean");
		//String result = validateBean.bankCardCheck("6225380087201200");
		//Account account = yiBaoPayBean.packAccount(661399, "农业银行", "6228481164799729911", "qwy", "441723199302145632", "15112304365","");
		//Account account = yiBaoPayBean.packAccount(661399, "平安银行", "6225380087201200", "曾礼强", "360723198811233635", "15013628196","192.138.1.0");
		//String result = validateBean.bindCard(account);
		//.setRequestId("2fdc7f02-c6be-4d06-a3e1-5058929ab40e");
		//String result = validateBean.directBindPay("37501170428", 10, "中泰理财9990", "中泰理财9990___", 1021, "www.baidu.com", "d");
		//System.out.println("result: "+result);
		
	}
}
