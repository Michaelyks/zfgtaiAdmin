package com.zfgt.test.action;


import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSON;
import com.util.encrypt.AES;
import com.util.encrypt.EncryUtil;
import com.util.encrypt.RSA;
import com.zfgt.common.util.QwyUtil;
import com.zfgt.common.util.RandomUtil;

/**
 * 投资通接口范例--API版
 * @author	：yingjie.wang    
 * @since	：2015-05-13 
 */

public class TZTService {
	private static Logger log = Logger.getLogger(TZTService.class);
	/**
	 * 取得商户编号
	 */
	public static String getMerchantAccount() {
		//return Configuration.getInstance().getValue("merchantAccount");
		String merchantAccount = "10000419568";
		return merchantAccount;
	}
	
	/**
	 * 取得商户公玥
	 */
	public static String getMerchantPublicKey() {
		//return Configuration.getInstance().getValue("merchantPublicKey");
		String merchantPublicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCKxayKB/TqDXtcKaObOPPzVL3r++ghEP45nai9cjG0JQt9m0F5+F8RVygizxS83iBTHd5bJbrMPLDh3GvjGm1bbJhzO4m2bF2fQm2uJ0C3ckdm9AZK8fqzcncpu2dy1zFyucFyHhKIgZryqfW5PS3G9UohS4698qA5j4dceWf5PwIDAQAB";
		return merchantPublicKey;
	}

	/**
	 * 取得商户私钥
	 */
	public static String getMerchantPrivateKey() {
		//return Configuration.getInstance().getValue("merchantPrivateKey");
		String merchantPrivateKey = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAIrFrIoH9OoNe1wpo5s48/NUvev76CEQ/jmdqL1yMbQlC32bQXn4XxFXKCLPFLzeIFMd3lslusw8sOHca+MabVtsmHM7ibZsXZ9Cba4nQLdyR2b0Bkrx+rNydym7Z3LXMXK5wXIeEoiBmvKp9bk9Lcb1SiFLjr3yoDmPh1x5Z/k/AgMBAAECgYEAgAjVohypOPDraiL40hP/7/e1qu6mQyvcgugVcYTUmvK64U7HYHNpsyQI4eTRq1f91vHt34a2DA3K3Phzifst/RoonlMmugXg/Klr5nOXNBZhVO6i5XQ3945dUeEq7LhiJTTv0cokiCmezgdmrW8n1STZ/b5y5MIOut8Y1rwOkAECQQC+an4ako+nPNw72kM6osRT/qC589AyOav60F1bHonK6NWzWOMiFekGuvtpybgwt4jbpQxXXRPxvJkgBq873fwBAkEAupGaEcuqXtO2j0hJFOG5t+nwwnOaJF49LypboN0RX5v8nop301//P16Bs/irj5F/mAs9lFR4GZ3bxL8zs5r1PwJBALa1MDMHFlf+CcRUddW5gHCoDkjfLZJDzEVp0WoxLz5Hk2X3kFmQdHxExiCHsfjs4qD/CYx6fzyhHrygLVxgcAECQAT8z3maUDuovUCnVgzQ2/4mquEH5h8Cxe/02e46+rPrn509ZmaoMlKnXCBLjYqRATA3XLYSbAODTNS9p8wtYFECQHa/xgB+nYWoevPC/geObOLAP9HMdNVcIAJq2rgeXVI4P7cFXvksRborHmjuy1fltoR0003qlSg82mxzABbzYUs=";
		return merchantPrivateKey;
		
	}

	/**
	 * 取得商户AESKey
	 */
	public static String getMerchantAESKey() {
		return (RandomUtil.getRandom(16));
	}

	/**
	 * 取得易宝公玥
	 */
	public static String getYeepayPublicKey() {
		//return Configuration.getInstance().getValue("yeepayPublicKey");
		String yeepayPublicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCj4k0oTc05UzrvBB6g24wsKawTlIX5995q3CQYrgM5un9mKEQc/NQIsJqqG2RUHyXUIBogMaMqG1F1QPoKMaXeVfVUSYa8ZU7bV9rOMDUT20BxAmPbtLlWdTSXDxXKXQxwkyfUAih1ZgTLI3vYg3flHeUA6cZRdbwDPLqXle8SIwIDAQAB";
		return yeepayPublicKey;
	}

	/**
	 * 格式化字符串
	 */
	public static String formatString(String text) {
		return (text == null ? "" : text.trim());
	}

	/**
	 * String2Integer
	 */
	public static int String2Int(String text) throws NumberFormatException {
		return text == null ? 0 : Integer.valueOf(text);
	}

	/**
	 * 编码格式转换
	 */
	public static String encodingConversion(String text) {
		String temp = "";
		try {
			temp = new String(text.getBytes("ISO-8859-1"), "UTF-8");
		} catch(Exception e) {
			log.error("操作异常: ",e);
		}
		return (temp);
	}

	/**
	 * 绑卡请求接口请求地址
	 */
	public static String getBindBankcardURL() {
		//return Configuration.getInstance().getValue("bindBankcardURL");
		String bindBankcardURL = "https://ok.yeepay.com/payapi/api/tzt/invokebindbankcard";
		return bindBankcardURL;
	}

	/**
	 * 绑卡确认接口请求地址
	 */
	public static String getConfirmBindBankcardURL() {
		//return Configuration.getInstance().getValue("confirmBindBankcardURL");
		String confirmBindBankcardURL = "https://ok.yeepay.com/payapi/api/tzt/confirmbindbankcard";
		return confirmBindBankcardURL;
	}

	/**
	 * 支付接口请求地址
	 */
	public static String getDirectBindPayURL() {
		//return Configuration.getInstance().getValue("directBindPayURL");
		String directBindPayURL = "https://ok.yeepay.com/payapi/api/tzt/directbindpay";
		return directBindPayURL;
	}

	/**
	 * 订单查询请求地址
	 */
	public static String getPaymentQueryURL() {
		//return Configuration.getInstance().getValue("paymentQueryURL");
		String paymentQueryURL = "https://ok.yeepay.com/merchant/query_server/pay_single";
		return paymentQueryURL;
	}


	/**
	 * bindBankcard() : 绑卡请求接口
	 */

	public static Map<String, String> bindBankcard(Map<String, String> params) {

		System.out.println("##### bindBankcard() #####");
				
		String merchantaccount		= getMerchantAccount();
		String merchantPublicKey	= getMerchantPublicKey();
		String merchantPrivateKey	= getMerchantPrivateKey();
		String merchantAESKey		= getMerchantAESKey();
		String yeepayPublicKey		= getYeepayPublicKey();
		String bindBankcardURL		= getBindBankcardURL();

		String requestid            = formatString(params.get("requestid"));
		//String identitytype         = formatString(params.get("identitytype"));
		String identityid           = formatString(params.get("identityid"));
		String cardno               = formatString(params.get("cardno"));
		String idcardtype           = formatString(params.get("idcardtype"));
		String idcardno             = formatString(params.get("idcardno"));
		String username             = formatString(params.get("username"));
		String phone                = formatString(params.get("phone"));
		String registerphone        = formatString(params.get("registerphone"));
		String registerdate         = formatString(params.get("registerdate"));
		String registerip           = formatString(params.get("registerip"));
		String registeridcardno     = formatString(params.get("registeridcardno"));
		String registercontact      = formatString(params.get("registercontact"));
		String os                   = formatString(params.get("os"));
		String imei                 = formatString(params.get("imei"));
		String userip               = formatString(params.get("userip"));
		String ua                   = formatString(params.get("ua"));
		String registeridcardtype   = formatString(params.get("registeridcardtype"));

		int identitytype			= 0;

		try {
			if(params.get("identitytype") == null) {
				throw new Exception("identitytype is null!!!!!");
			} else {
				identitytype        	= String2Int(params.get("identitytype"));
			}
		} catch(Exception e) {
			StringBuffer buffer		= new StringBuffer();
			buffer.append("error - NumberFormatException or Int parameters enter error : ");
			buffer.append("[identitytype = " + formatString(params.get("identitytype")) + "]. ");
			System.out.println(buffer.toString());
			log.error("操作异常: ",e);
		}

		TreeMap<String, Object> dataMap	= new TreeMap<String, Object>();
		dataMap.put("merchantaccount", 	merchantaccount);
		dataMap.put("requestid", 		requestid);
		dataMap.put("identitytype", 	identitytype);
		dataMap.put("identityid", 		identityid);
		dataMap.put("cardno", 			cardno);
		dataMap.put("idcardtype", 		idcardtype);
		dataMap.put("idcardno", 		idcardno);
		dataMap.put("username", 		username);
		dataMap.put("phone", 			phone);
		dataMap.put("registerphone", 	registerphone);
		dataMap.put("registerdate", 	registerdate);
		dataMap.put("registerip", 		registerip);
		dataMap.put("registeridcardno", registeridcardno);
		dataMap.put("registercontact", 	registercontact);
		dataMap.put("os", 				os);
		dataMap.put("imei", 			imei);
		dataMap.put("userip", 			userip);
		dataMap.put("ua", 				ua);
		dataMap.put("registeridcardtype", registeridcardtype);
						
		String sign						= EncryUtil.handleRSA(dataMap, merchantPrivateKey);
		dataMap.put("sign", sign);

		System.out.println("merchantaccount : " + merchantaccount);
		System.out.println("merchantPublicKey : " + merchantPublicKey);
		System.out.println("merchantPrivateKey : " + merchantPrivateKey);
		System.out.println("yeepayPublicKey : " + yeepayPublicKey);
		System.out.println("bindBankcardURL : " + bindBankcardURL);
		System.out.println("dataMap : " + dataMap);

		Map<String, String> result			= new HashMap<String, String>();
		String merchantaccountFromYeepay	= "";
        String requestidFromYeepay 	   		= "";
        String codesender 	   				= "";
        String signFromYeepay 		   		= "";
        String error_code	   				= "";
        String error_msg	   				= "";
        String signError	   				= "";

		HttpClient httpClient			= new HttpClient();
		PostMethod postMethod			= new PostMethod(bindBankcardURL);

		try {
			String jsonStr				= JSON.toJSONString(dataMap);
			String data					= AES.encryptToBase64(jsonStr, merchantAESKey);
			String encryptkey			= RSA.encrypt(merchantAESKey, yeepayPublicKey);

			System.out.println("jsonStr : " + jsonStr);
			System.out.println("data : " + data);
			System.out.println("encryptkey : " + encryptkey);

			NameValuePair[] datas		= {new NameValuePair("merchantaccount", merchantaccount),
										   new NameValuePair("data", data),
										   new NameValuePair("encryptkey", encryptkey)};

			postMethod.setRequestBody(datas);

			int statusCode				= httpClient.executeMethod(postMethod);
			byte[] responseByte			= postMethod.getResponseBody();
			String responseBody			= new String(responseByte, "UTF-8");

			System.out.println("responseBody : " + responseBody);

			Map<String, Object> jsonMap	= JSON.parseObject(responseBody, TreeMap.class);

			if(jsonMap.containsKey("error_code")) {
				error_code		= formatString((String)jsonMap.get("error_code"));
				error_msg		= formatString((String)jsonMap.get("error_msg"));
			} else {
				String dataFromYeepay		= formatString((String)jsonMap.get("data"));
				String encryptkeyFromYeepay	= formatString((String)jsonMap.get("encryptkey"));

				String yeepayAESKey					= RSA.decrypt(encryptkeyFromYeepay, merchantPrivateKey);
				String decryptData					= AES.decryptFromBase64(dataFromYeepay, yeepayAESKey);
				Map<String, Object> decryptDataMap	= JSON.parseObject(decryptData, TreeMap.class);

				System.out.println("dataFromYeepay : " + dataFromYeepay);
				System.out.println("encryptkeyFromYeepay : " + encryptkeyFromYeepay);
				System.out.println("yeepayAESKey : " + yeepayAESKey);
				System.out.println("decryptData : " + decryptData);
				System.out.println("decryptDataMap : " + decryptDataMap);
				
				if(decryptDataMap.containsKey("error_code")) {
					error_code		= formatString((String)decryptDataMap.get("error_code"));
					error_msg		= formatString((String)decryptDataMap.get("error_msg"));
				} else {
					merchantaccountFromYeepay	= formatString((String)decryptDataMap.get("merchantaccount"));
					requestidFromYeepay		 	= formatString((String)decryptDataMap.get("requestid"));
					codesender				 	= formatString((String)decryptDataMap.get("codesender"));
					signFromYeepay			 	= formatString((String)decryptDataMap.get("sign"));

					TreeMap<String, String> treeMap = new TreeMap<String, String>();
					treeMap.put("merchantaccount", 	merchantaccountFromYeepay);
					treeMap.put("requestid", 		requestidFromYeepay);
					treeMap.put("codesender", 		codesender);

					StringBuffer localDatas			= new StringBuffer();
					for(String value : treeMap.values()) {
						localDatas.append(value);
					}

					System.out.println("localDatas : " + localDatas);

					boolean checkSign	= RSA.checkSign(localDatas.toString(), signFromYeepay, yeepayPublicKey);
					if(!checkSign) {
						signError		=  "signError! + datas : [" + localDatas + "]";
					}
				}
			}
		} catch(Exception e) {
			log.error("操作异常: ",e);
		} finally {
			postMethod.releaseConnection();
		}

		result.put("merchantaccount", 	merchantaccountFromYeepay);
		result.put("requestid", 		requestidFromYeepay);
		result.put("codesender", 		codesender);
		result.put("sign", 				signFromYeepay);
		result.put("error_code", 		error_code);
		result.put("error_msg", 		error_msg);
		result.put("signError", 		signError);

		System.out.println("result : " + result);

		return (result);
	}

	
	/**
	 * cofirmBindBankcard() : 绑卡确认接口
	 */

	public static Map<String, String> confirmBindBankcard(Map<String, String> params) {

		System.out.println("##### confirmBindBankcard() #####");
				
		String merchantaccount			= getMerchantAccount();
		String merchantPublicKey		= getMerchantPublicKey();
		String merchantPrivateKey		= getMerchantPrivateKey();
		String merchantAESKey			= getMerchantAESKey();
		String yeepayPublicKey			= getYeepayPublicKey();
		String confirmBindBankcardURL	= getConfirmBindBankcardURL();

		String requestid            	= formatString(params.get("requestid"));
		String validatecode      		= formatString(params.get("validatecode"));

		TreeMap<String, Object> dataMap	= new TreeMap<String, Object>();
		dataMap.put("merchantaccount", 	merchantaccount);
		dataMap.put("requestid", 		requestid);
		dataMap.put("validatecode", 	validatecode);
						
		String sign						= EncryUtil.handleRSA(dataMap, merchantPrivateKey);
		dataMap.put("sign", sign);

		System.out.println("merchantaccount : " + merchantaccount);
		System.out.println("merchantPublicKey : " + merchantPublicKey);
		System.out.println("merchantPrivateKey : " + merchantPrivateKey);
		System.out.println("yeepayPublicKey : " + yeepayPublicKey);
		System.out.println("confirmBindBankcardURL : " + confirmBindBankcardURL);
		System.out.println("dataMap : " + dataMap);

		Map<String, String> result			= new HashMap<String, String>();
		String merchantaccountFromYeepay	= "";
        String requestidFromYeepay 	   		= "";
        String bankcode 	   				= "";
        String card_top 	   				= "";
        String card_last 	   				= "";
        String signFromYeepay 		   		= "";
        String error_code	   				= "";
        String error_msg	   				= "";
        String signError	   				= "";

		HttpClient httpClient			= new HttpClient();
		PostMethod postMethod			= new PostMethod(confirmBindBankcardURL);

		try {
			String jsonStr				= JSON.toJSONString(dataMap);
			String data					= AES.encryptToBase64(jsonStr, merchantAESKey);
			String encryptkey			= RSA.encrypt(merchantAESKey, yeepayPublicKey);

			System.out.println("jsonStr : " + jsonStr);
			System.out.println("data : " + data);
			System.out.println("encryptkey : " + encryptkey);

			NameValuePair[] datas		= {new NameValuePair("merchantaccount", merchantaccount),
										   new NameValuePair("data", data),
										   new NameValuePair("encryptkey", encryptkey)};

			postMethod.setRequestBody(datas);

			int statusCode				= httpClient.executeMethod(postMethod);
			byte[] responseByte			= postMethod.getResponseBody();
			String responseBody			= new String(responseByte, "UTF-8");

			System.out.println("responseBody : " + responseBody);

			Map<String, Object> jsonMap	= JSON.parseObject(responseBody, TreeMap.class);

			if(jsonMap.containsKey("error_code")) {
				error_code		= formatString((String)jsonMap.get("error_code"));
				error_msg		= formatString((String)jsonMap.get("error_msg"));
			} else {
				String dataFromYeepay		= formatString((String)jsonMap.get("data"));
				String encryptkeyFromYeepay	= formatString((String)jsonMap.get("encryptkey"));

				String yeepayAESKey					= RSA.decrypt(encryptkeyFromYeepay, merchantPrivateKey);
				String decryptData					= AES.decryptFromBase64(dataFromYeepay, yeepayAESKey);
				Map<String, Object> decryptDataMap	= JSON.parseObject(decryptData, TreeMap.class);

				System.out.println("dataFromYeepay : " + dataFromYeepay);
				System.out.println("encryptkeyFromYeepay : " + encryptkeyFromYeepay);
				System.out.println("yeepayAESKey : " + yeepayAESKey);
				System.out.println("decryptData : " + decryptData);
				System.out.println("decryptDataMap : " + decryptDataMap);
				
				if(decryptDataMap.containsKey("error_code")) {
					error_code		= formatString((String)decryptDataMap.get("error_code"));
					error_msg		= formatString((String)decryptDataMap.get("error_msg"));
				} else {
					merchantaccountFromYeepay	= formatString((String)decryptDataMap.get("merchantaccount"));
					requestidFromYeepay		 	= formatString((String)decryptDataMap.get("requestid"));
					bankcode				 	= formatString((String)decryptDataMap.get("bankcode"));
					card_top				 	= formatString((String)decryptDataMap.get("card_top"));
					card_last				 	= formatString((String)decryptDataMap.get("card_last"));
					signFromYeepay			 	= formatString((String)decryptDataMap.get("sign"));

					TreeMap<String, String> treeMap = new TreeMap<String, String>();
					treeMap.put("merchantaccount", 	merchantaccountFromYeepay);
					treeMap.put("requestid", 		requestidFromYeepay);
					treeMap.put("bankcode", 		bankcode);
					treeMap.put("card_top", 		card_top);
					treeMap.put("card_last", 		card_last);

					StringBuffer localDatas			= new StringBuffer();
					for(String value : treeMap.values()) {
						localDatas.append(value);
					}

					System.out.println("localDatas : " + localDatas);

					boolean checkSign	= RSA.checkSign(localDatas.toString(), signFromYeepay, yeepayPublicKey);
					if(!checkSign) {
						signError		=  "signError! + datas : [" + localDatas + "]";
					}
				}
			}
		} catch(Exception e) {
			log.error("操作异常: ",e);
		} finally {
			postMethod.releaseConnection();
		}

		result.put("merchantaccount", 	merchantaccountFromYeepay);
		result.put("requestid", 		requestidFromYeepay);
		result.put("bankcode", 			bankcode);
		result.put("card_top", 			card_top);
		result.put("card_last", 		card_last);
		result.put("sign", 				signFromYeepay);
		result.put("error_code", 		error_code);
		result.put("error_msg", 		error_msg);
		result.put("signError", 		signError);

		System.out.println("result : " + result);

		return (result);
	}


		
	/**
	 * directBindPay() : 支付接口
	 */

	public static Map<String, String> directBindPay(Map<String, String> params) {

		System.out.println("##### directBindPay() #####");

		Map<String, String> result			= new HashMap<String, String>();
		String merchantaccountFromYeepay	= "";
        String orderidFromYeepay 	   		= "";
        String yborderid 	   				= "";
        String amountFromYeepay	 	 		= "";
        String signFromYeepay 		   		= "";
        String error_code	   				= "";
        String error_msg	   				= "";
        String signError	   				= "";
				
		String merchantaccount				= getMerchantAccount();
		String merchantPublicKey			= getMerchantPublicKey();
		String merchantPrivateKey			= getMerchantPrivateKey();
		String merchantAESKey				= getMerchantAESKey();
		String yeepayPublicKey				= getYeepayPublicKey();
		String directBindPayURL				= getDirectBindPayURL();

		String orderid              		= formatString(params.get("orderid"));
		String productname          		= formatString(params.get("productname"));
		String productdesc          		= formatString(params.get("productdesc"));
		String identityid           		= formatString(params.get("identityid"));
		String card_top             		= formatString(params.get("card_top"));
		String card_last            		= formatString(params.get("card_last"));
		String callbackurl          		= formatString(params.get("callbackurl"));
		String imei                 		= formatString(params.get("imei"));
		String userip               		= formatString(params.get("userip"));
		String ua                   		= formatString(params.get("ua"));

		int transtime						= 0; 
		int amount              			= 0; 
		int identitytype        			= 0; 
		int orderexpdate        			= 0; 
		int currency	        			= 0; 
		
		/*try {
			//transtime、identitytype、amount是必填参数
			if(params.get("transtime") == null) {
				throw new Exception("transtime is null!!!!!");
			} else {
				transtime			= String2Int(params.get("transtime"));
			}

			if(params.get("identitytype") == null) {
				throw new Exception("identitytype is null!!!!!");
			} else {
				identitytype        	= String2Int(params.get("identitytype"));
			}

			if(params.get("amount") == null) {
				throw new Exception("amount is null!!!!!");
			} else {
				amount              	= String2Int(params.get("amount"));
			}

			orderexpdate        	= String2Int(params.get("orderexpdate"));
			currency	        	= String2Int(params.get("currency"));

		} catch(Exception e) {
			StringBuffer buffer		= new StringBuffer();
			buffer.append("error - the following parameters must be int  : ");
			buffer.append("[transtime = " + formatString(params.get("transtime")) + "], ");
			buffer.append("[amount = " + formatString(params.get("amount")) + "], ");
			buffer.append("[identitytype = " + formatString(params.get("identitytype")) + "], ");
			buffer.append("[orderexpdate = " + formatString(params.get("orderexpdate")) + "], ");
			buffer.append("[currency = " + formatString(params.get("currency")) + "]. ");
			error_code = "ZIDINGYI";
			error_msg = buffer.toString();
			System.out.println(buffer);
			log.error("操作异常: ",e);
			return (result);
		}*/

		/*TreeMap<String, Object> dataMap	= new TreeMap<String, Object>();
		dataMap.put("merchantaccount", 	merchantaccount);
		dataMap.put("orderid", 			orderid);
		dataMap.put("transtime", 		transtime);
		dataMap.put("currency", 		currency);
		dataMap.put("amount", 			amount);
		dataMap.put("productname", 		productname);
		dataMap.put("productdesc", 		productdesc);
		dataMap.put("identityid", 		identityid);
		dataMap.put("identitytype", 	identitytype);
		dataMap.put("card_top", 		card_top);
		dataMap.put("card_last", 		card_last);
		dataMap.put("orderexpdate", 	orderexpdate);
		dataMap.put("callbackurl", 		callbackurl);
		dataMap.put("imei", 			imei);
		dataMap.put("userip", 			userip);
		dataMap.put("ua", 				ua);*/
		
		TreeMap<String, Object> dataMap	= new TreeMap<String, Object>();
		dataMap.put("merchantaccount", 	merchantaccount);
		dataMap.put("orderid", 			"59906877901");
		dataMap.put("transtime", 		(int)System.currentTimeMillis()/1000);
		dataMap.put("currency", 		156);
		dataMap.put("amount", 			10);
		dataMap.put("productname", 		"中泰理财001");
		dataMap.put("productdesc", 		"中泰理财001描述");
		dataMap.put("identityid", 		"1003");
		dataMap.put("identitytype", 	2);
		dataMap.put("card_top", 		"621483");
		dataMap.put("card_last", 		"5537");
		dataMap.put("orderexpdate", 	orderexpdate);
		dataMap.put("callbackurl", 		"http://192.168.0.100:8088/baiYiMaoMobile/Product/login");
		dataMap.put("imei", 			imei);
		dataMap.put("userip", 			"192.168.0.100");
		dataMap.put("ua", 				ua);
						
		String sign						= EncryUtil.handleRSA(dataMap, merchantPrivateKey);
		dataMap.put("sign", sign);

		System.out.println("merchantaccount : " + merchantaccount);
		System.out.println("merchantPublicKey : " + merchantPublicKey);
		System.out.println("merchantPrivateKey : " + merchantPrivateKey);
		System.out.println("yeepayPublicKey : " + yeepayPublicKey);
		System.out.println("directBindPayURL : " + directBindPayURL);
		System.out.println("dataMap : " + dataMap);


		HttpClient httpClient			= new HttpClient();
		PostMethod postMethod			= new PostMethod(directBindPayURL);

		try {
			String jsonStr				= JSON.toJSONString(dataMap);
			String data					= AES.encryptToBase64(jsonStr, merchantAESKey);
			String encryptkey			= RSA.encrypt(merchantAESKey, yeepayPublicKey);

			System.out.println("jsonStr : " + jsonStr);
			System.out.println("data : " + data);
			System.out.println("encryptkey : " + encryptkey);

			NameValuePair[] datas		= {new NameValuePair("merchantaccount", merchantaccount),
										   new NameValuePair("data", data),
										   new NameValuePair("encryptkey", encryptkey)};

			postMethod.setRequestBody(datas);

			int statusCode				= httpClient.executeMethod(postMethod);
			byte[] responseByte			= postMethod.getResponseBody();
			String responseBody			= new String(responseByte, "UTF-8");

			System.out.println("responseBody : " + responseBody);

			Map<String, Object> jsonMap	= JSON.parseObject(responseBody, TreeMap.class);

			if(jsonMap.containsKey("error_code")) {
				error_code		= formatString((String)jsonMap.get("error_code"));
				error_msg		= formatString((String)jsonMap.get("error_msg"));
			} else {
				String dataFromYeepay		= formatString((String)jsonMap.get("data"));
				String encryptkeyFromYeepay	= formatString((String)jsonMap.get("encryptkey"));

				String yeepayAESKey					= RSA.decrypt(encryptkeyFromYeepay, merchantPrivateKey);
				String decryptData					= AES.decryptFromBase64(dataFromYeepay, yeepayAESKey);
				Map<String, Object> decryptDataMap	= JSON.parseObject(decryptData, TreeMap.class);

				System.out.println("dataFromYeepay : " + dataFromYeepay);
				System.out.println("encryptkeyFromYeepay : " + encryptkeyFromYeepay);
				System.out.println("yeepayAESKey : " + yeepayAESKey);
				System.out.println("decryptData : " + decryptData);
				System.out.println("decryptDataMap : " + decryptDataMap);
				
				if(decryptDataMap.containsKey("error_code")) {
					error_code		= formatString((String)decryptDataMap.get("error_code"));
					error_msg		= formatString((String)decryptDataMap.get("error_msg"));
				} else {
					merchantaccountFromYeepay	= formatString((String)decryptDataMap.get("merchantaccount"));
					orderidFromYeepay		 	= formatString((String)decryptDataMap.get("orderid"));
					yborderid				 	= formatString((String)decryptDataMap.get("yborderid"));
					amountFromYeepay			= formatString(String.valueOf((Integer)decryptDataMap.get("amount")));
					signFromYeepay			 	= formatString((String)decryptDataMap.get("sign"));

					TreeMap<String, String> treeMap = new TreeMap<String, String>();
					treeMap.put("merchantaccount", 	merchantaccountFromYeepay);
					treeMap.put("orderid", 			orderidFromYeepay);
					treeMap.put("yborderid", 		yborderid);
					treeMap.put("amount", 			amountFromYeepay);

					StringBuffer localDatas			= new StringBuffer();
					for(String value : treeMap.values()) {
						localDatas.append(value);
					}

					System.out.println("localDatas : " + localDatas);

					boolean checkSign	= RSA.checkSign(localDatas.toString(), signFromYeepay, yeepayPublicKey);
					if(!checkSign) {
						signError		=  "signError! + datas : [" + localDatas + "]";
					}
				}
			}
		} catch(Exception e) {
			log.error("操作异常: ",e);
		} finally {
			postMethod.releaseConnection();
		}

		result.put("merchantaccount", 	merchantaccountFromYeepay);
		result.put("orderid", 			orderidFromYeepay);
		result.put("yborderid", 		yborderid);
		result.put("amount", 			amountFromYeepay);
		result.put("sign", 				signFromYeepay);
		result.put("error_code", 		error_code);
		result.put("error_msg", 		error_msg);
		result.put("signError", 		signError);

		System.out.println("result : " + result);

		return (result);
	}


	/**
	 * queryByOrder() : 查询接口
	 */
	
	public static Map<String, String> queryByOrder(String orderid) {
		
		System.out.println("##### queryByOrder() #####");
		
		String merchantaccount		= getMerchantAccount();
		String merchantPublicKey	= getMerchantPublicKey();
		String merchantPrivateKey	= getMerchantPrivateKey();
		String merchantAESKey		= getMerchantAESKey();
		String yeepayPublicKey		= getYeepayPublicKey();
		String paymentQueryURL		= getPaymentQueryURL();
		
		TreeMap<String, Object> dataMap	= new TreeMap<String, Object>();
		dataMap.put("merchantaccount", 	merchantaccount);
		dataMap.put("orderid",			orderid);

		String sign					= EncryUtil.handleRSA(dataMap, merchantPrivateKey);
		dataMap.put("sign", sign);
				
		System.out.println("merchantaccount : " + merchantaccount);
		System.out.println("merchantPublicKey : " + merchantPublicKey);
		System.out.println("merchantPrivateKey : " + merchantPrivateKey);
		System.out.println("yeepayPublicKey : " + yeepayPublicKey);
		System.out.println("paymentQueryURL : " + paymentQueryURL);
		System.out.println("dataMap : " + dataMap);
		
		Map<String, String> queryResult		= new HashMap<String, String>();
		String merchantaccountFromYeepay	= "";
		String orderidFromYeepay            = "";
		String yborderid                    = "";
		String amount                       = "";
		String currency                     = "";
		String sourcefee                    = "";
		String targetfee                    = "";
		String sourceamount                 = "";
		String targetamount                 = "";
		String ordertime                    = "";
		String closetime                    = "";
		String type                         = "";
		String status                       = "";
		String refundtotal                  = "";
		String productcatalog               = "";
		String productname                  = "";
		String productdesc                  = "";
		String signFromYeepay               = "";
		String error_code                   = "";
		String error                        = "";
		String signError                    = "";

		HttpClient httpClient		= new HttpClient();
		GetMethod getMethod			= new GetMethod();
		
		try {
			String jsonStr				= JSON.toJSONString(dataMap);
			String data					= AES.encryptToBase64(jsonStr, merchantAESKey);
			String encryptkey			= RSA.encrypt(merchantAESKey, yeepayPublicKey);

			String url					= paymentQueryURL + 
										  "?merchantaccount=" + URLEncoder.encode(merchantaccount, "UTF-8") + 
										  "&data=" + URLEncoder.encode(data, "UTF-8") +
										  "&encryptkey=" + URLEncoder.encode(encryptkey, "UTF-8");

			getMethod					= new GetMethod(url);
			int statusCode				= httpClient.executeMethod(getMethod);
			String responseBody			= getMethod.getResponseBodyAsString();

			Map<String, Object> jsonMap	= JSON.parseObject(responseBody, TreeMap.class);

			System.out.println("url	 : " + url);
			System.out.println("data : " + data);
			System.out.println("encryptkey : " + encryptkey);
			System.out.println("responseBody : " + responseBody);
			System.out.println("jsonMap : " + jsonMap);

			if(jsonMap.containsKey("error_code")) {
				error_code		= formatString((String)jsonMap.get("error_code"));
				error			= formatString((String)jsonMap.get("error"));
			} else {
				String dataFromYeepay		= formatString((String)jsonMap.get("data"));
				String encryptkeyFromYeepay	= formatString((String)jsonMap.get("encryptkey"));

				String yeepayAESKey					= RSA.decrypt(encryptkeyFromYeepay, merchantPrivateKey);
				String decryptData					= AES.decryptFromBase64(dataFromYeepay, yeepayAESKey);
				Map<String, Object> decryptDataMap	= JSON.parseObject(decryptData, TreeMap.class);

				System.out.println("dataFromYeepay : " + dataFromYeepay);
				System.out.println("encryptkeyFromYeepay : " + encryptkeyFromYeepay);
				System.out.println("yeepayAESKey : " + yeepayAESKey);
				System.out.println("decryptData : " + decryptData);
				System.out.println("decryptDataMap : " + decryptDataMap);
				
				if(decryptDataMap.containsKey("error_code")) {
					error_code		= formatString((String)decryptDataMap.get("error_code"));
					error			= formatString((String)decryptDataMap.get("error"));
					signFromYeepay	= formatString((String)decryptDataMap.get("sign"));

					TreeMap<String, String> treeMap	= new TreeMap<String, String>();
					treeMap.put("error_code", error_code);
					treeMap.put("error", error);

					StringBuffer localDatas		= new StringBuffer();
					for(String value : treeMap.values()) {
						localDatas.append(value);
					}

					System.out.println("localDatas : " + localDatas);

					boolean checkSign	= RSA.checkSign(localDatas.toString(), signFromYeepay, yeepayPublicKey);
					if(!checkSign) {
						signError		= "signError! + datas : [" + localDatas + "]";
					}
				} else {
					merchantaccountFromYeepay =  formatString((String)decryptDataMap.get("merchantaccount"));
					orderidFromYeepay         =  formatString((String)decryptDataMap.get("orderid"));
					yborderid                 =  formatString((String)decryptDataMap.get("yborderid"));
					amount                    =  formatString(String.valueOf((Integer)decryptDataMap.get("amount")));
					currency                  =  formatString(String.valueOf((Integer)decryptDataMap.get("currency")));
					sourcefee                 =  formatString(String.valueOf((Integer)decryptDataMap.get("sourcefee")));
					targetfee                 =  formatString(String.valueOf((Integer)decryptDataMap.get("targetfee")));
					sourceamount              =  formatString(String.valueOf((Integer)decryptDataMap.get("sourceamount")));
					targetamount              =  formatString(String.valueOf((Integer)decryptDataMap.get("targetamount")));
					ordertime                 =  formatString(String.valueOf((Integer)decryptDataMap.get("ordertime")));
					closetime                 =  formatString(String.valueOf((Integer)decryptDataMap.get("closetime")));
					type                      =  formatString(String.valueOf((Integer)decryptDataMap.get("type")));
					status                    =  formatString(String.valueOf((Integer)decryptDataMap.get("status")));
					refundtotal               =  formatString(String.valueOf((Integer)decryptDataMap.get("refundtotal")));
					productcatalog            =  formatString(String.valueOf((Integer)decryptDataMap.get("productcatalog")));
					productname               =  formatString((String)decryptDataMap.get("productname"));
					productdesc               =  formatString((String)decryptDataMap.get("productdesc"));
					signFromYeepay            =  formatString((String)decryptDataMap.get("sign"));

					TreeMap<String, String> treeMap = new TreeMap<String, String>();
					treeMap.put("merchantaccount",	merchantaccountFromYeepay);
					treeMap.put("orderid",			orderidFromYeepay);
					treeMap.put("yborderid", 		yborderid);
					treeMap.put("amount", 			amount);
					treeMap.put("currency", 		currency);
					treeMap.put("sourcefee", 		sourcefee);
					treeMap.put("targetfee", 		targetfee);
					treeMap.put("sourceamount", 	sourceamount);
					treeMap.put("targetamount", 	targetamount);
					treeMap.put("ordertime", 		ordertime);
					treeMap.put("closetime", 		closetime);
					treeMap.put("type", 			type);
					treeMap.put("status", 			status);
					treeMap.put("refundtotal", 		refundtotal);
					treeMap.put("productcatalog", 	productcatalog);
					treeMap.put("productname", 		productname);
					treeMap.put("productdesc", 		productdesc);

					StringBuffer localDatas			= new StringBuffer();
					for(String value : treeMap.values()) {
						localDatas.append(value);
					}

					System.out.println("localDatas : " + localDatas);

					boolean checkSign	= RSA.checkSign(localDatas.toString(), signFromYeepay, yeepayPublicKey);
					if(!checkSign) {
						signError		=  "signError! + datas : [" + localDatas + "]";
					}
				}
			}
		} catch(Exception e) {
			log.error("操作异常: ",e);
		} finally {
			getMethod.releaseConnection();
		}
		
		queryResult.put("merchantaccount",	merchantaccountFromYeepay);
		queryResult.put("orderid",			orderidFromYeepay);
		queryResult.put("yborderid", 		yborderid);
		queryResult.put("amount", 			amount);
		queryResult.put("currency", 		currency);
		queryResult.put("sourcefee", 		sourcefee);
		queryResult.put("targetfee", 		targetfee);
		queryResult.put("sourceamount", 	sourceamount);
		queryResult.put("targetamount", 	targetamount);
		queryResult.put("ordertime", 		ordertime);
		queryResult.put("closetime", 		closetime);
		queryResult.put("type", 			type);
		queryResult.put("status", 			status);
		queryResult.put("refundtotal", 		refundtotal);
		queryResult.put("productcatalog", 	productcatalog);
		queryResult.put("productname", 		productname);
		queryResult.put("productdesc", 		productdesc);
		queryResult.put("sign", 			signFromYeepay);
		queryResult.put("error_code", 		error_code);
		queryResult.put("error", 			error);
		queryResult.put("signError", 		signError);

		System.out.println("queryResult : " + queryResult);

		return (queryResult);
	}
	
	public static void main(String[] args) {
	while (true) {
			QwyUtil.accessIntentByPost("http://www.baiyimao.com/Product/productCategory!loadProduct.action", null);
			QwyUtil.accessIntentByPost("http://www.baiyimao.com/Product/productCategory!loadProduct.action", null);
			QwyUtil.accessIntentByPost("http://www.baiyimao.com/Product/productCategory!loadProduct.action", null);
			QwyUtil.accessIntentByPost("http://www.baiyimao.com/Product/productCategory!loadProduct.action", null);
			QwyUtil.accessIntentByPost("http://www.baiyimao.com/Product/productCategory!loadProduct.action", null);
			QwyUtil.accessIntentByPost("http://www.baiyimao.com/Product/productCategory!loadProduct.action", null);
			QwyUtil.accessIntentByPost("http://www.baiyimao.com/Product/productCategory!loadProduct.action", null);
			QwyUtil.accessIntentByPost("http://www.baiyimao.com/Product/productCategory!loadProduct.action", null);
			QwyUtil.accessIntentByPost("http://www.baiyimao.com/Product/productCategory!loadProduct.action", null);
			QwyUtil.accessIntentByPost("http://www.baiyimao.com/Product/productCategory!loadProduct.action", null);
			QwyUtil.accessIntentByPost("http://www.baiyimao.com/Product/productCategory!loadProduct.action", null);
			QwyUtil.accessIntentByPost("http://www.baiyimao.com/Product/productCategory!loadProduct.action", null);
			QwyUtil.accessIntentByPost("http://www.baiyimao.com/Product/productCategory!loadProduct.action", null);
			QwyUtil.accessIntentByPost("http://www.baiyimao.com/Product/productCategory!loadProduct.action", null);
			QwyUtil.accessIntentByPost("http://www.baiyimao.com/Product/productCategory!loadProduct.action", null);
			QwyUtil.accessIntentByPost("http://www.baiyimao.com/Product/productCategory!loadProduct.action", null);
			QwyUtil.accessIntentByPost("http://www.baiyimao.com/Product/productCategory!loadProduct.action", null);
			QwyUtil.accessIntentByPost("http://www.baiyimao.com/Product/productCategory!loadProduct.action", null);
			QwyUtil.accessIntentByPost("http://www.baiyimao.com/Product/productCategory!loadProduct.action", null);
			QwyUtil.accessIntentByPost("http://www.baiyimao.com/Product/productCategory!loadProduct.action", null);
			QwyUtil.accessIntentByPost("http://www.baiyimao.com/Product/productCategory!loadProduct.action", null);
			QwyUtil.accessIntentByPost("http://www.baiyimao.com/Product/productCategory!loadProduct.action", null);
			QwyUtil.accessIntentByPost("http://www.baiyimao.com/Product/productCategory!loadProduct.action", null);
			QwyUtil.accessIntentByPost("http://www.baiyimao.com/Product/productCategory!loadProduct.action", null);
			QwyUtil.accessIntentByPost("http://www.baiyimao.com/Product/productCategory!loadProduct.action", null);
			QwyUtil.accessIntentByPost("http://www.baiyimao.com/Product/productCategory!loadProduct.action", null);
			QwyUtil.accessIntentByPost("http://www.baiyimao.com/Product/productCategory!loadProduct.action", null);
			QwyUtil.accessIntentByPost("http://www.baiyimao.com/Product/productCategory!loadProduct.action", null);
			QwyUtil.accessIntentByPost("http://www.baiyimao.com/Product/productCategory!loadProduct.action", null);
			QwyUtil.accessIntentByPost("http://www.baiyimao.com/Product/productCategory!loadProduct.action", null);
			QwyUtil.accessIntentByPost("http://www.baiyimao.com/Product/productCategory!loadProduct.action", null);
			QwyUtil.accessIntentByPost("http://www.baiyimao.com/Product/productCategory!loadProduct.action", null);
			QwyUtil.accessIntentByPost("http://www.baiyimao.com/Product/productCategory!loadProduct.action", null);
			QwyUtil.accessIntentByPost("http://www.baiyimao.com/Product/productCategory!loadProduct.action", null);
			QwyUtil.accessIntentByPost("http://www.baiyimao.com/Product/productCategory!loadProduct.action", null);
			QwyUtil.accessIntentByPost("http://www.baiyimao.com/Product/productCategory!loadProduct.action", null);
			QwyUtil.accessIntentByPost("http://www.baiyimao.com/Product/productCategory!loadProduct.action", null);
			QwyUtil.accessIntentByPost("http://www.baiyimao.com/Product/productCategory!loadProduct.action", null);
			QwyUtil.accessIntentByPost("http://www.baiyimao.com/Product/productCategory!loadProduct.action", null);
			QwyUtil.accessIntentByPost("http://www.baiyimao.com/Product/productCategory!loadProduct.action", null);
			QwyUtil.accessIntentByPost("http://www.baiyimao.com/Product/productCategory!loadProduct.action", null);
			QwyUtil.accessIntentByPost("http://www.baiyimao.com/Product/productCategory!loadProduct.action", null);
			QwyUtil.accessIntentByPost("http://www.baiyimao.com/Product/productCategory!loadProduct.action", null);
			QwyUtil.accessIntentByPost("http://www.baiyimao.com/Product/productCategory!loadProduct.action", null);
			QwyUtil.accessIntentByPost("http://www.baiyimao.com/Product/productCategory!loadProduct.action", null);
			QwyUtil.accessIntentByPost("http://www.baiyimao.com/Product/productCategory!loadProduct.action", null);
			QwyUtil.accessIntentByPost("http://www.baiyimao.com/Product/productCategory!loadProduct.action", null);
			QwyUtil.accessIntentByPost("http://www.baiyimao.com/Product/productCategory!loadProduct.action", null);
			QwyUtil.accessIntentByPost("http://www.baiyimao.com/Product/productCategory!loadProduct.action", null);
			QwyUtil.accessIntentByPost("http://www.baiyimao.com/Product/productCategory!loadProduct.action", null);
			QwyUtil.accessIntentByPost("http://www.baiyimao.com/Product/productCategory!loadProduct.action", null);
			QwyUtil.accessIntentByPost("http://www.baiyimao.com/Product/productCategory!loadProduct.action", null);
			QwyUtil.accessIntentByPost("http://www.baiyimao.com/Product/productCategory!loadProduct.action", null);
			QwyUtil.accessIntentByPost("http://www.baiyimao.com/Product/productCategory!loadProduct.action", null);
			QwyUtil.accessIntentByPost("http://www.baiyimao.com/Product/productCategory!loadProduct.action", null);
			QwyUtil.accessIntentByPost("http://www.baiyimao.com/Product/productCategory!loadProduct.action", null);
			QwyUtil.accessIntentByPost("http://www.baiyimao.com/Product/productCategory!loadProduct.action", null);
			QwyUtil.accessIntentByPost("http://www.baiyimao.com/Product/productCategory!loadProduct.action", null);
			QwyUtil.accessIntentByPost("http://www.baiyimao.com/Product/productCategory!loadProduct.action", null);
			QwyUtil.accessIntentByPost("http://www.baiyimao.com/Product/productCategory!loadProduct.action", null);
			QwyUtil.accessIntentByPost("http://www.baiyimao.com/Product/productCategory!loadProduct.action", null);
			QwyUtil.accessIntentByPost("http://www.baiyimao.com/Product/productCategory!loadProduct.action", null);
			QwyUtil.accessIntentByPost("http://www.baiyimao.com/Product/productCategory!loadProduct.action", null);
			QwyUtil.accessIntentByPost("http://www.baiyimao.com/Product/productCategory!loadProduct.action", null);
			QwyUtil.accessIntentByPost("http://www.baiyimao.com/Product/productCategory!loadProduct.action", null);
			QwyUtil.accessIntentByPost("http://www.baiyimao.com/Product/productCategory!loadProduct.action", null);
			QwyUtil.accessIntentByPost("http://www.baiyimao.com/Product/productCategory!loadProduct.action", null);
			QwyUtil.accessIntentByPost("http://www.baiyimao.com/Product/productCategory!loadProduct.action", null);
			QwyUtil.accessIntentByPost("http://www.baiyimao.com/Product/productCategory!loadProduct.action", null);
			QwyUtil.accessIntentByPost("http://www.baiyimao.com/Product/productCategory!loadProduct.action", null);
			QwyUtil.accessIntentByPost("http://www.baiyimao.com/Product/productCategory!loadProduct.action", null);
			QwyUtil.accessIntentByPost("http://www.baiyimao.com/Product/productCategory!loadProduct.action", null);
			QwyUtil.accessIntentByPost("http://www.baiyimao.com/Product/productCategory!loadProduct.action", null);
			QwyUtil.accessIntentByPost("http://www.baiyimao.com/Product/productCategory!loadProduct.action", null);
			QwyUtil.accessIntentByPost("http://www.baiyimao.com/Product/productCategory!loadProduct.action", null);
			QwyUtil.accessIntentByPost("http://www.baiyimao.com/Product/productCategory!loadProduct.action", null);
			QwyUtil.accessIntentByPost("http://www.baiyimao.com/Product/productCategory!loadProduct.action", null);
			QwyUtil.accessIntentByPost("http://www.baiyimao.com/Product/productCategory!loadProduct.action", null);
			QwyUtil.accessIntentByPost("http://www.baiyimao.com/Product/productCategory!loadProduct.action", null);
			QwyUtil.accessIntentByPost("http://www.baiyimao.com/Product/productCategory!loadProduct.action", null);
			QwyUtil.accessIntentByPost("http://www.baiyimao.com/Product/productCategory!loadProduct.action", null);
			QwyUtil.accessIntentByPost("http://www.baiyimao.com/Product/productCategory!loadProduct.action", null);
			QwyUtil.accessIntentByPost("http://www.baiyimao.com/Product/productCategory!loadProduct.action", null);
			QwyUtil.accessIntentByPost("http://www.baiyimao.com/Product/productCategory!loadProduct.action", null);
			QwyUtil.accessIntentByPost("http://www.baiyimao.com/Product/productCategory!loadProduct.action", null);
			QwyUtil.accessIntentByPost("http://www.baiyimao.com/Product/productCategory!loadProduct.action", null);
			QwyUtil.accessIntentByPost("http://www.baiyimao.com/Product/productCategory!loadProduct.action", null);
			QwyUtil.accessIntentByPost("http://www.baiyimao.com/Product/productCategory!loadProduct.action", null);
			QwyUtil.accessIntentByPost("http://www.baiyimao.com/Product/productCategory!loadProduct.action", null);
			QwyUtil.accessIntentByPost("http://www.baiyimao.com/Product/productCategory!loadProduct.action", null);
			
			QwyUtil.accessIntentByPost("http://www.baiyimao.com/Product/productCategory!loadProduct.action", null);
			QwyUtil.accessIntentByPost("http://www.baiyimao.com/Product/productCategory!loadProduct.action", null);
			QwyUtil.accessIntentByPost("http://www.baiyimao.com/Product/productCategory!loadProduct.action", null);
			QwyUtil.accessIntentByPost("http://www.baiyimao.com/Product/productCategory!loadProduct.action", null);
			QwyUtil.accessIntentByPost("http://www.baiyimao.com/Product/productCategory!loadProduct.action", null);
			QwyUtil.accessIntentByPost("http://www.baiyimao.com/Product/productCategory!loadProduct.action", null);
			QwyUtil.accessIntentByPost("http://www.baiyimao.com/Product/productCategory!loadProduct.action", null);
			QwyUtil.accessIntentByPost("http://www.baiyimao.com/Product/productCategory!loadProduct.action", null);
			QwyUtil.accessIntentByPost("http://www.baiyimao.com/Product/productCategory!loadProduct.action", null);
			QwyUtil.accessIntentByPost("http://www.baiyimao.com/Product/productCategory!loadProduct.action", null);
			QwyUtil.accessIntentByPost("http://www.baiyimao.com/Product/productCategory!loadProduct.action", null);
			QwyUtil.accessIntentByPost("http://www.baiyimao.com/Product/productCategory!loadProduct.action", null);
			QwyUtil.accessIntentByPost("http://www.baiyimao.com/Product/productCategory!loadProduct.action", null);
			QwyUtil.accessIntentByPost("http://www.baiyimao.com/Product/productCategory!loadProduct.action", null);
			QwyUtil.accessIntentByPost("http://www.baiyimao.com/Product/productCategory!loadProduct.action", null);
			QwyUtil.accessIntentByPost("http://www.baiyimao.com/Product/productCategory!loadProduct.action", null);
			QwyUtil.accessIntentByPost("http://www.baiyimao.com/Product/productCategory!loadProduct.action", null);
			QwyUtil.accessIntentByPost("http://www.baiyimao.com/Product/productCategory!loadProduct.action", null);
			QwyUtil.accessIntentByPost("http://www.baiyimao.com/Product/productCategory!loadProduct.action", null);
			QwyUtil.accessIntentByPost("http://www.baiyimao.com/Product/productCategory!loadProduct.action", null);
			QwyUtil.accessIntentByPost("http://www.baiyimao.com/Product/productCategory!loadProduct.action", null);
			QwyUtil.accessIntentByPost("http://www.baiyimao.com/Product/productCategory!loadProduct.action", null);
			QwyUtil.accessIntentByPost("http://www.baiyimao.com/Product/productCategory!loadProduct.action", null);
			QwyUtil.accessIntentByPost("http://www.baiyimao.com/Product/productCategory!loadProduct.action", null);
			QwyUtil.accessIntentByPost("http://www.baiyimao.com/Product/productCategory!loadProduct.action", null);
			QwyUtil.accessIntentByPost("http://www.baiyimao.com/Product/productCategory!loadProduct.action", null);
			QwyUtil.accessIntentByPost("http://www.baiyimao.com/Product/productCategory!loadProduct.action", null);
			QwyUtil.accessIntentByPost("http://www.baiyimao.com/Product/productCategory!loadProduct.action", null);
			QwyUtil.accessIntentByPost("http://www.baiyimao.com/Product/productCategory!loadProduct.action", null);
			QwyUtil.accessIntentByPost("http://www.baiyimao.com/Product/productCategory!loadProduct.action", null);
			QwyUtil.accessIntentByPost("http://www.baiyimao.com/Product/productCategory!loadProduct.action", null);
			QwyUtil.accessIntentByPost("http://www.baiyimao.com/Product/productCategory!loadProduct.action", null);
			QwyUtil.accessIntentByPost("http://www.baiyimao.com/Product/productCategory!loadProduct.action", null);
			QwyUtil.accessIntentByPost("http://www.baiyimao.com/Product/productCategory!loadProduct.action", null);
			QwyUtil.accessIntentByPost("http://www.baiyimao.com/Product/productCategory!loadProduct.action", null);
			QwyUtil.accessIntentByPost("http://www.baiyimao.com/Product/productCategory!loadProduct.action", null);
			QwyUtil.accessIntentByPost("http://www.baiyimao.com/Product/productCategory!loadProduct.action", null);
			QwyUtil.accessIntentByPost("http://www.baiyimao.com/Product/productCategory!loadProduct.action", null);
			QwyUtil.accessIntentByPost("http://www.baiyimao.com/Product/productCategory!loadProduct.action", null);
			QwyUtil.accessIntentByPost("http://www.baiyimao.com/Product/productCategory!loadProduct.action", null);
			QwyUtil.accessIntentByPost("http://www.baiyimao.com/Product/productCategory!loadProduct.action", null);
			QwyUtil.accessIntentByPost("http://www.baiyimao.com/Product/productCategory!loadProduct.action", null);
			QwyUtil.accessIntentByPost("http://www.baiyimao.com/Product/productCategory!loadProduct.action", null);
			QwyUtil.accessIntentByPost("http://www.baiyimao.com/Product/productCategory!loadProduct.action", null);
			QwyUtil.accessIntentByPost("http://www.baiyimao.com/Product/productCategory!loadProduct.action", null);
			QwyUtil.accessIntentByPost("http://www.baiyimao.com/Product/productCategory!loadProduct.action", null);
			QwyUtil.accessIntentByPost("http://www.baiyimao.com/Product/productCategory!loadProduct.action", null);
			QwyUtil.accessIntentByPost("http://www.baiyimao.com/Product/productCategory!loadProduct.action", null);
			QwyUtil.accessIntentByPost("http://www.baiyimao.com/Product/productCategory!loadProduct.action", null);
			QwyUtil.accessIntentByPost("http://www.baiyimao.com/Product/productCategory!loadProduct.action", null);
			QwyUtil.accessIntentByPost("http://www.baiyimao.com/Product/productCategory!loadProduct.action", null);
			QwyUtil.accessIntentByPost("http://www.baiyimao.com/Product/productCategory!loadProduct.action", null);
			QwyUtil.accessIntentByPost("http://www.baiyimao.com/Product/productCategory!loadProduct.action", null);
			QwyUtil.accessIntentByPost("http://www.baiyimao.com/Product/productCategory!loadProduct.action", null);
			QwyUtil.accessIntentByPost("http://www.baiyimao.com/Product/productCategory!loadProduct.action", null);
			QwyUtil.accessIntentByPost("http://www.baiyimao.com/Product/productCategory!loadProduct.action", null);
			QwyUtil.accessIntentByPost("http://www.baiyimao.com/Product/productCategory!loadProduct.action", null);
			QwyUtil.accessIntentByPost("http://www.baiyimao.com/Product/productCategory!loadProduct.action", null);
			QwyUtil.accessIntentByPost("http://www.baiyimao.com/Product/productCategory!loadProduct.action", null);
			QwyUtil.accessIntentByPost("http://www.baiyimao.com/Product/productCategory!loadProduct.action", null);
			QwyUtil.accessIntentByPost("http://www.baiyimao.com/Product/productCategory!loadProduct.action", null);
			QwyUtil.accessIntentByPost("http://www.baiyimao.com/Product/productCategory!loadProduct.action", null);
			QwyUtil.accessIntentByPost("http://www.baiyimao.com/Product/productCategory!loadProduct.action", null);
			QwyUtil.accessIntentByPost("http://www.baiyimao.com/Product/productCategory!loadProduct.action", null);
			QwyUtil.accessIntentByPost("http://www.baiyimao.com/Product/productCategory!loadProduct.action", null);
			QwyUtil.accessIntentByPost("http://www.baiyimao.com/Product/productCategory!loadProduct.action", null);
			QwyUtil.accessIntentByPost("http://www.baiyimao.com/Product/productCategory!loadProduct.action", null);
			QwyUtil.accessIntentByPost("http://www.baiyimao.com/Product/productCategory!loadProduct.action", null);
			QwyUtil.accessIntentByPost("http://www.baiyimao.com/Product/productCategory!loadProduct.action", null);
			QwyUtil.accessIntentByPost("http://www.baiyimao.com/Product/productCategory!loadProduct.action", null);
			QwyUtil.accessIntentByPost("http://www.baiyimao.com/Product/productCategory!loadProduct.action", null);
			QwyUtil.accessIntentByPost("http://www.baiyimao.com/Product/productCategory!loadProduct.action", null);
			QwyUtil.accessIntentByPost("http://www.baiyimao.com/Product/productCategory!loadProduct.action", null);
			QwyUtil.accessIntentByPost("http://www.baiyimao.com/Product/productCategory!loadProduct.action", null);
			QwyUtil.accessIntentByPost("http://www.baiyimao.com/Product/productCategory!loadProduct.action", null);
			QwyUtil.accessIntentByPost("http://www.baiyimao.com/Product/productCategory!loadProduct.action", null);
			QwyUtil.accessIntentByPost("http://www.baiyimao.com/Product/productCategory!loadProduct.action", null);
			QwyUtil.accessIntentByPost("http://www.baiyimao.com/Product/productCategory!loadProduct.action", null);
			QwyUtil.accessIntentByPost("http://www.baiyimao.com/Product/productCategory!loadProduct.action", null);
			QwyUtil.accessIntentByPost("http://www.baiyimao.com/Product/productCategory!loadProduct.action", null);
			QwyUtil.accessIntentByPost("http://www.baiyimao.com/Product/productCategory!loadProduct.action", null);
			QwyUtil.accessIntentByPost("http://www.baiyimao.com/Product/productCategory!loadProduct.action", null);
			QwyUtil.accessIntentByPost("http://www.baiyimao.com/Product/productCategory!loadProduct.action", null);
			QwyUtil.accessIntentByPost("http://www.baiyimao.com/Product/productCategory!loadProduct.action", null);
			QwyUtil.accessIntentByPost("http://www.baiyimao.com/Product/productCategory!loadProduct.action", null);
			QwyUtil.accessIntentByPost("http://www.baiyimao.com/Product/productCategory!loadProduct.action", null);
			QwyUtil.accessIntentByPost("http://www.baiyimao.com/Product/productCategory!loadProduct.action", null);
			QwyUtil.accessIntentByPost("http://www.baiyimao.com/Product/productCategory!loadProduct.action", null);
			QwyUtil.accessIntentByPost("http://www.baiyimao.com/Product/productCategory!loadProduct.action", null);
			QwyUtil.accessIntentByPost("http://www.baiyimao.com/Product/productCategory!loadProduct.action", null);
			QwyUtil.accessIntentByPost("http://www.baiyimao.com/Product/productCategory!loadProduct.action", null);
			QwyUtil.accessIntentByPost("http://www.baiyimao.com/Product/productCategory!loadProduct.action", null);
			QwyUtil.accessIntentByPost("http://www.baiyimao.com/Product/productCategory!loadProduct.action", null);
			QwyUtil.accessIntentByPost("http://www.baiyimao.com/Product/productCategory!loadProduct.action", null);
			QwyUtil.accessIntentByPost("http://www.baiyimao.com/Product/productCategory!loadProduct.action", null);
			QwyUtil.accessIntentByPost("http://www.baiyimao.com/Product/productCategory!loadProduct.action", null);
			QwyUtil.accessIntentByPost("http://www.baiyimao.com/Product/productCategory!loadProduct.action", null);
			QwyUtil.accessIntentByPost("http://www.baiyimao.com/Product/productCategory!loadProduct.action", null);
			QwyUtil.accessIntentByPost("http://www.baiyimao.com/Product/productCategory!loadProduct.action", null);
			QwyUtil.accessIntentByPost("http://www.baiyimao.com/Product/productCategory!loadProduct.action", null);
			QwyUtil.accessIntentByPost("http://www.baiyimao.com/Product/productCategory!loadProduct.action", null);
			QwyUtil.accessIntentByPost("http://www.baiyimao.com/Product/productCategory!loadProduct.action", null);
			QwyUtil.accessIntentByPost("http://www.baiyimao.com/Product/productCategory!loadProduct.action", null);
			QwyUtil.accessIntentByPost("http://www.baiyimao.com/Product/productCategory!loadProduct.action", null);
			QwyUtil.accessIntentByPost("http://www.baiyimao.com/Product/productCategory!loadProduct.action", null);
			QwyUtil.accessIntentByPost("http://www.baiyimao.com/Product/productCategory!loadProduct.action", null);
			QwyUtil.accessIntentByPost("http://www.baiyimao.com/Product/productCategory!loadProduct.action", null);
			QwyUtil.accessIntentByPost("http://www.baiyimao.com/Product/productCategory!loadProduct.action", null);
			QwyUtil.accessIntentByPost("http://www.baiyimao.com/Product/productCategory!loadProduct.action", null);
			QwyUtil.accessIntentByPost("http://www.baiyimao.com/Product/productCategory!loadProduct.action", null);
			QwyUtil.accessIntentByPost("http://www.baiyimao.com/Product/productCategory!loadProduct.action", null);
			QwyUtil.accessIntentByPost("http://www.baiyimao.com/Product/productCategory!loadProduct.action", null);
			QwyUtil.accessIntentByPost("http://www.baiyimao.com/Product/productCategory!loadProduct.action", null);
			QwyUtil.accessIntentByPost("http://www.baiyimao.com/Product/productCategory!loadProduct.action", null);
			QwyUtil.accessIntentByPost("http://www.baiyimao.com/Product/productCategory!loadProduct.action", null);
			QwyUtil.accessIntentByPost("http://www.baiyimao.com/Product/productCategory!loadProduct.action", null);
			QwyUtil.accessIntentByPost("http://www.baiyimao.com/Product/productCategory!loadProduct.action", null);
			QwyUtil.accessIntentByPost("http://www.baiyimao.com/Product/productCategory!loadProduct.action", null);
			QwyUtil.accessIntentByPost("http://www.baiyimao.com/Product/productCategory!loadProduct.action", null);
			QwyUtil.accessIntentByPost("http://www.baiyimao.com/Product/productCategory!loadProduct.action", null);
			QwyUtil.accessIntentByPost("http://www.baiyimao.com/Product/productCategory!loadProduct.action", null);
			QwyUtil.accessIntentByPost("http://www.baiyimao.com/Product/productCategory!loadProduct.action", null);
			QwyUtil.accessIntentByPost("http://www.baiyimao.com/Product/productCategory!loadProduct.action", null);
			QwyUtil.accessIntentByPost("http://www.baiyimao.com/Product/productCategory!loadProduct.action", null);
			QwyUtil.accessIntentByPost("http://www.baiyimao.com/Product/productCategory!loadProduct.action", null);
			QwyUtil.accessIntentByPost("http://www.baiyimao.com/Product/productCategory!loadProduct.action", null);
			QwyUtil.accessIntentByPost("http://www.baiyimao.com/Product/productCategory!loadProduct.action", null);
			QwyUtil.accessIntentByPost("http://www.baiyimao.com/Product/productCategory!loadProduct.action", null);
			QwyUtil.accessIntentByPost("http://www.baiyimao.com/Product/productCategory!loadProduct.action", null);
			QwyUtil.accessIntentByPost("http://www.baiyimao.com/Product/productCategory!loadProduct.action", null);
			QwyUtil.accessIntentByPost("http://www.baiyimao.com/Product/productCategory!loadProduct.action", null);
			QwyUtil.accessIntentByPost("http://www.baiyimao.com/Product/productCategory!loadProduct.action", null);
			QwyUtil.accessIntentByPost("http://www.baiyimao.com/Product/productCategory!loadProduct.action", null);
			QwyUtil.accessIntentByPost("http://www.baiyimao.com/Product/productCategory!loadProduct.action", null);
			QwyUtil.accessIntentByPost("http://www.baiyimao.com/Product/productCategory!loadProduct.action", null);
			QwyUtil.accessIntentByPost("http://www.baiyimao.com/Product/productCategory!loadProduct.action", null);
			QwyUtil.accessIntentByPost("http://www.baiyimao.com/Product/productCategory!loadProduct.action", null);
			QwyUtil.accessIntentByPost("http://www.baiyimao.com/Product/productCategory!loadProduct.action", null);
			QwyUtil.accessIntentByPost("http://www.baiyimao.com/Product/productCategory!loadProduct.action", null);
			QwyUtil.accessIntentByPost("http://www.baiyimao.com/Product/productCategory!loadProduct.action", null);
			QwyUtil.accessIntentByPost("http://www.baiyimao.com/Product/productCategory!loadProduct.action", null);
			QwyUtil.accessIntentByPost("http://www.baiyimao.com/Product/productCategory!loadProduct.action", null);
			QwyUtil.accessIntentByPost("http://www.baiyimao.com/Product/productCategory!loadProduct.action", null);
			QwyUtil.accessIntentByPost("http://www.baiyimao.com/Product/productCategory!loadProduct.action", null);
			QwyUtil.accessIntentByPost("http://www.baiyimao.com/Product/productCategory!loadProduct.action", null);
			QwyUtil.accessIntentByPost("http://www.baiyimao.com/Product/productCategory!loadProduct.action", null);
			QwyUtil.accessIntentByPost("http://www.baiyimao.com/Product/productCategory!loadProduct.action", null);
			QwyUtil.accessIntentByPost("http://www.baiyimao.com/Product/productCategory!loadProduct.action", null);
			QwyUtil.accessIntentByPost("http://www.baiyimao.com/Product/productCategory!loadProduct.action", null);
			QwyUtil.accessIntentByPost("http://www.baiyimao.com/Product/productCategory!loadProduct.action", null);
			QwyUtil.accessIntentByPost("http://www.baiyimao.com/Product/productCategory!loadProduct.action", null);
			QwyUtil.accessIntentByPost("http://www.baiyimao.com/Product/productCategory!loadProduct.action", null);
			QwyUtil.accessIntentByPost("http://www.baiyimao.com/Product/productCategory!loadProduct.action", null);
			QwyUtil.accessIntentByPost("http://www.baiyimao.com/Product/productCategory!loadProduct.action", null);
			QwyUtil.accessIntentByPost("http://www.baiyimao.com/Product/productCategory!loadProduct.action", null);
			QwyUtil.accessIntentByPost("http://www.baiyimao.com/Product/productCategory!loadProduct.action", null);
			QwyUtil.accessIntentByPost("http://www.baiyimao.com/Product/productCategory!loadProduct.action", null);
			QwyUtil.accessIntentByPost("http://www.baiyimao.com/Product/productCategory!loadProduct.action", null);
			QwyUtil.accessIntentByPost("http://www.baiyimao.com/Product/productCategory!loadProduct.action", null);
			QwyUtil.accessIntentByPost("http://www.baiyimao.com/Product/productCategory!loadProduct.action", null);
			QwyUtil.accessIntentByPost("http://www.baiyimao.com/Product/productCategory!loadProduct.action", null);
			QwyUtil.accessIntentByPost("http://www.baiyimao.com/Product/productCategory!loadProduct.action", null);
			QwyUtil.accessIntentByPost("http://www.baiyimao.com/Product/productCategory!loadProduct.action", null);
			QwyUtil.accessIntentByPost("http://www.baiyimao.com/Product/productCategory!loadProduct.action", null);
			QwyUtil.accessIntentByPost("http://www.baiyimao.com/Product/productCategory!loadProduct.action", null);
			QwyUtil.accessIntentByPost("http://www.baiyimao.com/Product/productCategory!loadProduct.action", null);
			QwyUtil.accessIntentByPost("http://www.baiyimao.com/Product/productCategory!loadProduct.action", null);
			QwyUtil.accessIntentByPost("http://www.baiyimao.com/Product/productCategory!loadProduct.action", null);
			QwyUtil.accessIntentByPost("http://www.baiyimao.com/Product/productCategory!loadProduct.action", null);
			QwyUtil.accessIntentByPost("http://www.baiyimao.com/Product/productCategory!loadProduct.action", null);
			QwyUtil.accessIntentByPost("http://www.baiyimao.com/Product/productCategory!loadProduct.action", null);
			QwyUtil.accessIntentByPost("http://www.baiyimao.com/Product/productCategory!loadProduct.action", null);
			QwyUtil.accessIntentByPost("http://www.baiyimao.com/Product/productCategory!loadProduct.action", null);
			QwyUtil.accessIntentByPost("http://www.baiyimao.com/Product/productCategory!loadProduct.action", null);
			QwyUtil.accessIntentByPost("http://www.baiyimao.com/Product/productCategory!loadProduct.action", null);
			QwyUtil.accessIntentByPost("http://www.baiyimao.com/Product/productCategory!loadProduct.action", null);
			QwyUtil.accessIntentByPost("http://www.baiyimao.com/Product/productCategory!loadProduct.action", null);
			QwyUtil.accessIntentByPost("http://www.baiyimao.com/Product/productCategory!loadProduct.action", null);
			QwyUtil.accessIntentByPost("http://www.baiyimao.com/Product/productCategory!loadProduct.action", null);
			QwyUtil.accessIntentByPost("http://www.baiyimao.com/Product/productCategory!loadProduct.action", null);
			QwyUtil.accessIntentByPost("http://www.baiyimao.com/Product/productCategory!loadProduct.action", null);
			QwyUtil.accessIntentByPost("http://www.baiyimao.com/Product/productCategory!loadProduct.action", null);
			QwyUtil.accessIntentByPost("http://www.baiyimao.com/Product/productCategory!loadProduct.action", null);
			QwyUtil.accessIntentByPost("http://www.baiyimao.com/Product/productCategory!loadProduct.action", null);
			QwyUtil.accessIntentByPost("http://www.baiyimao.com/Product/productCategory!loadProduct.action", null);
			QwyUtil.accessIntentByPost("http://www.baiyimao.com/Product/productCategory!loadProduct.action", null);
			QwyUtil.accessIntentByPost("http://www.baiyimao.com/Product/productCategory!loadProduct.action", null);
			QwyUtil.accessIntentByPost("http://www.baiyimao.com/Product/productCategory!loadProduct.action", null);
			QwyUtil.accessIntentByPost("http://www.baiyimao.com/Product/productCategory!loadProduct.action", null);
			QwyUtil.accessIntentByPost("http://www.baiyimao.com/Product/productCategory!loadProduct.action", null);
			QwyUtil.accessIntentByPost("http://www.baiyimao.com/Product/productCategory!loadProduct.action", null);
			QwyUtil.accessIntentByPost("http://www.baiyimao.com/Product/productCategory!loadProduct.action", null);
			QwyUtil.accessIntentByPost("http://www.baiyimao.com/Product/productCategory!loadProduct.action", null);
			QwyUtil.accessIntentByPost("http://www.baiyimao.com/Product/productCategory!loadProduct.action", null);
			QwyUtil.accessIntentByPost("http://www.baiyimao.com/Product/productCategory!loadProduct.action", null);
			QwyUtil.accessIntentByPost("http://www.baiyimao.com/Product/productCategory!loadProduct.action", null);
			QwyUtil.accessIntentByPost("http://www.baiyimao.com/Product/productCategory!loadProduct.action", null);
			QwyUtil.accessIntentByPost("http://www.baiyimao.com/Product/productCategory!loadProduct.action", null);
			QwyUtil.accessIntentByPost("http://www.baiyimao.com/Product/productCategory!loadProduct.action", null);
			QwyUtil.accessIntentByPost("http://www.baiyimao.com/Product/productCategory!loadProduct.action", null);
			QwyUtil.accessIntentByPost("http://www.baiyimao.com/Product/productCategory!loadProduct.action", null);
			QwyUtil.accessIntentByPost("http://www.baiyimao.com/Product/productCategory!loadProduct.action", null);
			QwyUtil.accessIntentByPost("http://www.baiyimao.com/Product/productCategory!loadProduct.action", null);
			QwyUtil.accessIntentByPost("http://www.baiyimao.com/Product/productCategory!loadProduct.action", null);
			QwyUtil.accessIntentByPost("http://www.baiyimao.com/Product/productCategory!loadProduct.action", null);
			QwyUtil.accessIntentByPost("http://www.baiyimao.com/Product/productCategory!loadProduct.action", null);
			QwyUtil.accessIntentByPost("http://www.baiyimao.com/Product/productCategory!loadProduct.action", null);
			QwyUtil.accessIntentByPost("http://www.baiyimao.com/Product/productCategory!loadProduct.action", null);
			QwyUtil.accessIntentByPost("http://www.baiyimao.com/Product/productCategory!loadProduct.action", null);
			QwyUtil.accessIntentByPost("http://www.baiyimao.com/Product/productCategory!loadProduct.action", null);
			QwyUtil.accessIntentByPost("http://www.baiyimao.com/Product/productCategory!loadProduct.action", null);
			QwyUtil.accessIntentByPost("http://www.baiyimao.com/Product/productCategory!loadProduct.action", null);
			QwyUtil.accessIntentByPost("http://www.baiyimao.com/Product/productCategory!loadProduct.action", null);
			QwyUtil.accessIntentByPost("http://www.baiyimao.com/Product/productCategory!loadProduct.action", null);
			QwyUtil.accessIntentByPost("http://www.baiyimao.com/Product/productCategory!loadProduct.action", null);
			QwyUtil.accessIntentByPost("http://www.baiyimao.com/Product/productCategory!loadProduct.action", null);
			QwyUtil.accessIntentByPost("http://www.baiyimao.com/Product/productCategory!loadProduct.action", null);
			QwyUtil.accessIntentByPost("http://www.baiyimao.com/Product/productCategory!loadProduct.action", null);
			QwyUtil.accessIntentByPost("http://www.baiyimao.com/Product/productCategory!loadProduct.action", null);
			QwyUtil.accessIntentByPost("http://www.baiyimao.com/Product/productCategory!loadProduct.action", null);
			QwyUtil.accessIntentByPost("http://www.baiyimao.com/Product/productCategory!loadProduct.action", null);
			QwyUtil.accessIntentByPost("http://www.baiyimao.com/Product/productCategory!loadProduct.action", null);
			QwyUtil.accessIntentByPost("http://www.baiyimao.com/Product/productCategory!loadProduct.action", null);
			QwyUtil.accessIntentByPost("http://www.baiyimao.com/Product/productCategory!loadProduct.action", null);
			QwyUtil.accessIntentByPost("http://www.baiyimao.com/Product/productCategory!loadProduct.action", null);
			QwyUtil.accessIntentByPost("http://www.baiyimao.com/Product/productCategory!loadProduct.action", null);
			QwyUtil.accessIntentByPost("http://www.baiyimao.com/Product/productCategory!loadProduct.action", null);
			QwyUtil.accessIntentByPost("http://www.baiyimao.com/Product/productCategory!loadProduct.action", null);
			QwyUtil.accessIntentByPost("http://www.baiyimao.com/Product/productCategory!loadProduct.action", null);
			QwyUtil.accessIntentByPost("http://www.baiyimao.com/Product/productCategory!loadProduct.action", null);
			QwyUtil.accessIntentByPost("http://www.baiyimao.com/Product/productCategory!loadProduct.action", null);
			QwyUtil.accessIntentByPost("http://www.baiyimao.com/Product/productCategory!loadProduct.action", null);
			QwyUtil.accessIntentByPost("http://www.baiyimao.com/Product/productCategory!loadProduct.action", null);
			QwyUtil.accessIntentByPost("http://www.baiyimao.com/Product/productCategory!loadProduct.action", null);
			QwyUtil.accessIntentByPost("http://www.baiyimao.com/Product/productCategory!loadProduct.action", null);
			QwyUtil.accessIntentByPost("http://www.baiyimao.com/Product/productCategory!loadProduct.action", null);
			QwyUtil.accessIntentByPost("http://www.baiyimao.com/Product/productCategory!loadProduct.action", null);
			QwyUtil.accessIntentByPost("http://www.baiyimao.com/Product/productCategory!loadProduct.action", null);
			QwyUtil.accessIntentByPost("http://www.baiyimao.com/Product/productCategory!loadProduct.action", null);
			QwyUtil.accessIntentByPost("http://www.baiyimao.com/Product/productCategory!loadProduct.action", null);
			
			
		}
	}
}
