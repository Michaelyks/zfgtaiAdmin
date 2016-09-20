package com.zfgt.admin.product.bean;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.zfgt.account.bean.UserInfoBean;
import com.zfgt.admin.product.dao.BindCardRecordDAO;
import com.zfgt.common.bean.SystemConfigBean;
import com.zfgt.common.bean.YiBaoPayBean;
import com.zfgt.common.util.DESEncrypt;
import com.zfgt.common.util.QwyUtil;
import com.zfgt.common.util.SMSUtil;
import com.zfgt.login.bean.RegisterUserBean;
import com.zfgt.orm.Account;
import com.zfgt.orm.BindCardRecord;
import com.zfgt.orm.SmsRecord;
import com.zfgt.orm.UsersInfo;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 解绑银行操作
 * 
 * @author 曾礼强
 *
 */
@Service
public class BindCardRecordBean {
	@Resource
	YiBaoPayBean bean;
	@Resource
	BindCardRecordDAO dao;
	@Resource
	RegisterUserBean registerUserBean;
	@Resource
	private UserInfoBean infoBean;
	@Resource
	private SystemConfigBean systemConfigBean;

	/**
	 * 查询所有绑定的银行卡
	 * 
	 * @param usersId
	 *            用户ID
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<BindCardRecord> findBindBankList(long usersId) {
		List<BindCardRecord> list = new ArrayList<BindCardRecord>();
		try {
			String data = bean.bindList(usersId);
			JSONObject object = JSONObject.fromObject(data);
			JSONArray array = object.getJSONArray("cardlist");
			list = (List<BindCardRecord>) JSONArray.toCollection(array, BindCardRecord.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * 解绑银行卡
	 * 
	 * @throws Exception
	 */
	public boolean unBindBank(List<BindCardRecord> list, Long userId) {
		for (BindCardRecord bindBank : list) {
			try {
				String result = bean.unbindCard(bindBank.getBindid(), userId);
				bindBank.setIdentityid(userId);
				bindBank.setIdentitytype("2");
				bindBank.setType("1");
				bindBank.setInsertTime(new Date());
				if (QwyUtil.isNullAndEmpty(result) || result.contains("error")) {
					bindBank.setStatus("0");
				} else {
					bindBank.setStatus("1");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		List<BindCardRecord> lists = findBindBankList(userId);
		if (QwyUtil.isNullAndEmpty(lists)) {
			Account account = bean.getAccountByUsersId(userId, "0");
			UsersInfo info = infoBean.getUserInfoById(userId);
			account.setStatus("1");
			info.setIsBindBank("0");
			dao.saveOrUpdate(account);
			dao.saveOrUpdate(info);
			dao.saveList(list);
			return true;
		}
		return false;
	}

	/**
	 * 发送解绑的成功的短信
	 */
	public String sendSuc(String mobile, String tel) {
		String smsContent = SMSUtil.unbindSuc(systemConfigBean.findSystemConfig().getSmsQm(), tel);
		SmsRecord smsRecord = registerUserBean.packSmsRecord(smsContent, mobile);
		Map<String, Object> map = SMSUtil.sendYzm2(mobile, null, smsContent);
		if (map.get("error").toString().equalsIgnoreCase("0")) {
			// JSONObject json = JSONObject.fromObject(map.get("result"));
			smsRecord.setStatus("1");
			// smsRecord.setSid(QwyUtil.get(json, "sid"));
			smsRecord.setUpdateTime(new Date());
			smsRecord.setCode(map.get("message") + "");
			dao.save(smsRecord);
			return "";
		} else {
			// Object code = map.get("message");
			smsRecord.setStatus("2");
			smsRecord.setUpdateTime(new Date());
			smsRecord.setCode(map.get("code") + "");
			smsRecord.setNote(map.get("detail") + "");
			dao.save(smsRecord);
			/*
			 * if(!QwyUtil.isNullAndEmpty(code) && code.toString().equals("8")){
			 * //30秒内不能重复发送 return "发送验证码太频繁,请稍后再试"; }
			 */
			return "发送验证码失败";
		}
	}
	// public String sendSuc(String mobile,String tel){
	// String smsContent=SMSUtil.unbindSuc(tel);
	// SmsRecord smsRecord = registerUserBean.packSmsRecord(smsContent, mobile);
	// Map<String, Object> map=SMSUtil.sendYzm(mobile, null,smsContent);
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
	// }

	/**
	 * 解绑银行卡
	 * 
	 * @param userId
	 *            用户ID
	 * @param type
	 *            类型 1为解绑无对应绑卡关系 2 换卡解绑
	 * @return
	 */
	public boolean unBindBank(Long userId, String type) throws Exception {
		if (!QwyUtil.isNullAndEmpty(userId)) {
			BindCardRecord bindCardRecord = new BindCardRecord();
			Account account = bean.getAccountByUsersId(userId, null);
			UsersInfo info = infoBean.getUserInfoById(userId);
			bindCardRecord.setBankcode(account.getBankCode());
			bindCardRecord.setCard_last(account.getCardLast());
			bindCardRecord.setCard_name(account.getBankName());
			bindCardRecord.setCard_top(account.getCardTop());
			bindCardRecord.setIdentityid(userId);
			bindCardRecord.setIdentitytype("2");
			bindCardRecord.setInsertTime(new Date());
			if ("1".equals(type)) {
				bindCardRecord.setNote("解绑无对应绑卡关系");
			} else {
				bindCardRecord.setNote("换卡操作解绑");
			}
			bindCardRecord.setPhone(DESEncrypt.jieMiUsername(account.getPhone()));
			bindCardRecord.setStatus("1");
			bindCardRecord.setType("1");
			account.setStatus("1");
			info.setIsBindBank("0");
			dao.saveOrUpdate(account);
			dao.saveOrUpdate(info);
			dao.saveOrUpdate(bindCardRecord);
			return true;
		}
		return false;
	}

}
