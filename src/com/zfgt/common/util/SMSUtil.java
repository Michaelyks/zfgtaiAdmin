package com.zfgt.common.util;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.zfgt.common.Commons;

/**
 * 短信工具类
 * @author 曾礼强
 *2015年5月12日 10:22:45
 */
public class SMSUtil {
	private static Logger log = Logger.getLogger(SMSUtil.class); 
   /**
    * 发送验证码
    * @param mobile 手机号码
    * @param code 验证码
    * @param smsContent 短信内容
    * @return
    */
	public static Map<String, Object> sendYzm(String mobile, String code, String smsContent){
		try {
			Map<String, Object> values=new HashMap<String, Object>();
			values.put("apikey", SMSProperties.APIKEY);
			values.put("mobile", mobile);
			values.put("text", smsContent);
			return QwyUtil.accessIntentByPost(SMSProperties.YP_URL, values);
		} catch (Exception e) {
			log.error("操作异常: ",e);
		}
		return Commons.mapException();
	}
	/**
	    * 发送验证码
	    * @param mobile 手机号码
	    * @param code 验证码
	    * @param smsContent 短信内容
	    * @return
	    */
		public static Map<String, Object> sendYzm2(String mobile, String code, String smsContent){
			try {
				Map<String, Object> values=new HashMap<String, Object>();
				values.put("cdkey", SMSProperties.YM_CDKEY);
				values.put("password", SMSProperties.YM_PASSWORD);
				values.put("phone", mobile);
				values.put("message", smsContent);
				values.put("seqid", 10000L);
				values.put("smspriority", 1);
				return QwyUtil.returnXMLAccessIntentByPost(SMSProperties.YM_URL, values);
			} catch (Exception e) {
				log.error("操作异常: ",e);
			}
			return Commons.mapException();
		}
			/**
			 * 发放利息当天发送定时短信
			 * @param mobile
			 * @param sendtime
			 * @param smsContent
			 * @return
			 */
			public static Map<String, Object> sendProfitMessage(String mobile, String sendtime, String message){
				try {
					Map<String, Object> values=new HashMap<String, Object>();
					values.put("cdkey", SMSProperties.YM_CDKEY);
					values.put("password", SMSProperties.YM_PASSWORD);
					values.put("phone", mobile);
					values.put("message", message);
					values.put("sendtime", sendtime);
					values.put("seqid", 10000L);
					values.put("smspriority", 1);
					return QwyUtil.returnXMLAccessIntentByPost(SMSProperties.YM_TIME_URL, values);
				} catch (Exception e) {
					log.error("操作异常: ",e);
				}
				return Commons.mapException();
			}
		
		
	
	/**
	 * 解绑银行卡成功短信内容
	 * @param tel 客户电话
	 */
	public static String unbindSuc(String smsQm,String tel){
		return  smsQm+"解绑银行卡成功，感谢您使用国内领先的金融理财服务！"+tel;
	}
	
	/**
	 * 短信内容	
	 * @param code 验证码
	 * @param company 公司名
	 * @param type 发送类型;
	 * @param wh 尾号
	 * @return
	 */
	public static  String smsContent(String smsQm,String code,String type, String wh){
		
		if(type.equals("1")){
			return  smsQm+"您注册帐号的验证码是"+code+"(验证码30分钟内有效)";
		}else if(type.equals("2")){
			return  smsQm+"您找回登录密码的验证码是"+code+"(验证码30分钟内有效)";
		} else if(type.equals("3")){
			return  smsQm+"您设置修改交易密码的验证码是"+code+"(验证码30分钟内有效)";
		}else if(type.equals("4")){
			return  smsQm+"您正在进行解绑银行卡尾号为"+wh+"的操作，如非本人操作，请尽快修改账户密码。验证码:"+code+"(验证码30分钟有效)";
		}else if(type.equals("5")){
			return  smsQm+"您正在更换绑定的手机号码,验证码:"+code+"(验证码30分钟有效)";
		}else{
			return "";
		}
	}
	
	
	
	
	   /**
	    * 查询亿美余额
	    * @return
	    */
		public static Map<String, Object> queryBalance(){
			try {
				Map<String, Object> values=new HashMap<String, Object>();
				values.put("cdkey", SMSProperties.YM_CDKEY);
				values.put("password", SMSProperties.YM_PASSWORD);
				return QwyUtil.returnXMLAccessIntentByPost(SMSProperties.YM_QUERY_URL, values);
			} catch (Exception e) {
				log.error("操作异常: ",e);
			}
			return Commons.mapException();
		}
		
		public static void main(String[] args) {
			/*System.out.println(sendYzm2("13798308126,15800819051,13223936222,17889980989,18696188856,13588031697,13193095867", null, "亲爱的中泰理财用户您好！由于刚才第三方支付平台支付临时受限，导致您充值失败。现已恢复可以正常充值，非常抱歉，为表达我们的歉意，我们特别送出50元投资券到您中泰理财账户。感谢您一直以来对中泰理财的支持与厚爱。祝您投资愉快。"));*/
			//String str = "【中泰理财】亲爱的用户您好！由于刚才第三方支付平台支付临时受限，导致您充值失败。现已恢复。感谢您一直以来对中泰理财的支持。祝您生活愉快。";
			String str = "【中泰理财】亲爱的中泰理财用户您好！由于刚才第三方支付平台支付临时受限，导致您充值失败。现已恢复可以正常充值，非常抱歉，为表达我们的歉意，我们特别送出50元投资券到您中泰理财账户。感谢您一直以来对中泰理财的支持与厚爱。祝您投资愉快。";
			System.out.println(str.length());
			//System.out.println(sendYzm2("15112304365,13798308126,15800819051,13223936222,17889980989,18696188856,13588031697,13193095867", null, str));
		}
}
