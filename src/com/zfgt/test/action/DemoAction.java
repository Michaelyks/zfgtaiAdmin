package com.zfgt.test.action;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.TreeMap;

import javax.annotation.Resource;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.Logger;
import org.apache.struts2.config.Namespace;
import org.apache.struts2.config.ParentPackage;
import org.apache.struts2.config.Result;
import org.apache.struts2.config.Results;

import yjpay.api.service.impl.PayAPIServiceImpl;

import com.alibaba.fastjson.JSON;
import com.opensymphony.xwork2.ActionSupport;
import com.util.common.RandomUtil;
import com.util.common.YJPayUtil;
import com.util.encrypt.AES;
import com.util.encrypt.EncryUtil;
import com.util.encrypt.RSA;
import com.zfgt.common.util.QwyUtil;
import com.zfgt.orm.Users;
import com.zfgt.product.bean.ProductCategoryBean;
import com.zfgt.test.bean.DemoBean;

/**
 * @author qwy
 *
 * 2015-4-14下午3:12:15
 */
@ParentPackage("struts-default")
@Namespace("/Product")
@Results({ @Result(name = "newFile", value = "/NewFile1.jsp")
})
public class DemoAction extends ActionSupport{
	private ResourceBundle resb1 = ResourceBundle.getBundle("payapi");
	private static Logger log = Logger.getLogger(DemoAction.class);
	// 从配置文件读取易宝分配的公钥
	private String yibaoPublicKey = resb1
			.getString("payapi.paytest_yibao_publickey");

	// 从配置文件读取商户自己的私钥
	private String merchantPrivateKey = resb1
			.getString("payapi.paytest_merchant_privatekey");

	// 商户自己随机生成的AESkey
	private String merchantAesKey = RandomUtil.getRandom(16);

	// 商户账户编号
	private String merchantaccount = resb1
			.getString("payapi.paytest_merchantaccount");

	// 从配置文件读取支付API接口URL前缀
	private String urlPrefix = resb1.getString("payapi.pay_urlprefix");
	@Resource
	DemoBean bean;
	Card card;
	public String getTest(){
		System.out.println("jinlai2asdf");
		System.out.println("234234");
		System.out.println("aaa");
		List<Users> list = bean.getTestList();
		System.out.println(list.size());
		return "newFile";
	}
	
	public String bindCard(){
		try {
			//商户私钥
			//String merchantPrivateKey ="";
			//易宝公钥
			//String yibaoPublicKey="";
			// 完整的请求地址
			//String requestURL = urlPrefix + PayAPIURIList.PAYAPI_BINDCARDPAY.getValue();
			//String requestURL = urlPrefix + "/api/tzt/invokebindbankcard";
			//String requestURL = "https://ok.yeepay.com/payapi/api/tzt/invokebindbankcard";
			//System.out.println("进入绑定卡方法;银行卡号: "+card.getCardno());
			// 生成RSA签名
			Map<String, String> map = new HashMap<String, String>();
			map.put("merchantaccount", card.getMerchantaccount());
			map.put("identityid", card.getIdentityid());
			map.put("identitytype", card.getIdentitytype());
			map.put("requestid", card.getRequestid());
			map.put("cardno", card.getCardno());
			map.put("idcardtype", card.getIdcardtype());
			map.put("idcardno", card.getIdcardno());
			map.put("username", card.getUsername());
			map.put("phone", card.getPhone());
			map.put("userip", card.getUserip());
			map.put("registerip", "192.168.0.100");
			map = TZTService.bindBankcard(map);
			YJPayUtil yjpayutil = new YJPayUtil();
			//yjpayutil.checkYbResult(map);
			/*String sign = EncryUtil.handleRSA(map, merchantPrivateKey);
			System.out.println("sign: "+sign);
			map.put("sign", sign);

			// 生成data
			String info = JSON.toJSONString(map);
			String data = AES.encryptToBase64(info, merchantAesKey);

			// 使用RSA算法将商户自己随机生成的AESkey加密
			String encryptkey = RSA.encrypt(merchantAesKey, yibaoPublicKey);

			YJPayUtil yjpayutil = new YJPayUtil();

			// 请求一键支付接口
			String ybresult = yjpayutil.payAPIRequest(requestURL, data, encryptkey,
					merchantaccount, true);

			// 将一键支付返回的结果进行验签，解密并返回
			System.out.println(yjpayutil.checkYbResult(ybresult));*/
			//System.out.println(map.toString());
		}catch (Exception e) {
			log.error("操作异常: ",e);
		}
		return "newFile";
	}
	
	public String checkBankCard(){
		System.out.println("卡号: "+card.getCardno());
		try {
			PayAPIServiceImpl testPayAPIImpl = new PayAPIServiceImpl();

			String payresult = testPayAPIImpl.bankCardCheck(card.getCardno());

			System.out.println("接口返回结果：" + payresult);
		} catch (Exception e) {
			log.error("操作异常: ",e);
		}
		return null;
	}
	
	public String confirmBindBankcard(){
		Map<String, String> map = new HashMap<String, String>();
		map.put("merchantaccount", card.getMerchantaccount());
		map.put("requestid", card.getRequestid());
		map.put("validatecode", "132245");
		TZTService.confirmBindBankcard(map);
		return "newFile";
	}
	
	public String pay(){
		try {
			//商户私钥
			//String merchantPrivateKey ="";
			//易宝公钥
			//String yibaoPublicKey="";
			// 完整的请求地址
			//String requestURL = urlPrefix + PayAPIURIList.PAYAPI_BINDCARDPAY.getValue();
			String requestURL = urlPrefix + "/api/bankcard/debit/pay/request";
			//String requestURL = "https://ok.yeepay.com/payapi/api/tzt/invokebindbankcard";
			// 生成RSA签名
			TreeMap<String, Object> map =payParam();
			String sign = EncryUtil.handleRSA(map, merchantPrivateKey);
			System.out.println("sign: "+sign);
			map.put("sign", sign);

			// 生成data
			String info = JSON.toJSONString(map);
			String data = AES.encryptToBase64(info, merchantAesKey);

			// 使用RSA算法将商户自己随机生成的AESkey加密
			String encryptkey = RSA.encrypt(merchantAesKey, yibaoPublicKey);

			YJPayUtil yjpayutil = new YJPayUtil();

			// 请求一键支付接口
			String ybresult = yjpayutil.payAPIRequest(requestURL, data, encryptkey,
					merchantaccount, true);

			// 将一键支付返回的结果进行验签，解密并返回
			System.out.println(yjpayutil.checkYbResult(ybresult));
			
		} catch (IOException e) {
			log.error("操作异常: ",e);
		} catch (Exception e) {
			log.error("操作异常: ",e);
		}
		return "newFile";
	}

	public String queryCardList() {
		TreeMap<String, Object> map = new TreeMap<String, Object>();
		map.put("merchantaccount", "10000419568");
		map.put("identityid", "1003");
		map.put("identitytype", 2);
		String sign						= EncryUtil.handleRSA(map, merchantPrivateKey);
		map.put("sign", sign);
		HttpClient httpClient = new HttpClient();
		GetMethod postMethod = new GetMethod(
				"https://ok.yeepay.com/payapi/api/bankcard/authbind/list?merchantaccount=10000419568&identityid=1002&identitytype=2&sign="+sign);

		try {
			int statusCode = httpClient.executeMethod(postMethod);
			byte[] responseByte = postMethod.getResponseBody();
			String responseBody = new String(responseByte, "UTF-8");

			System.out.println("responseBody : " + responseBody);
		} catch (HttpException e) {
			// TODO Auto-generated catch block
			log.error("操作异常: ",e);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			log.error("操作异常: ",e);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			log.error("操作异常: ",e);
		}
		return "newFile";
	}
	
	
	
	public static void main(String[] args) {
		/*DemoAction d = new DemoAction();
		d.queryCardList();*/
		//Map<String, String>  params = new HashMap<String,String>();TZTService.directBindPay(params);
		
		//TZTService.queryByOrder("599068779g");
		String imei = System.getProperty("phone.imei");
		System.out.println(imei);
	}
	public String getImei(){
		System.out.println("获取IMEI值");
		System.getProperty("phone.imei");
		return null;
	}

	public Card getCard() {
		return card;
	}

	public void setCard(Card card) {
		this.card = card;
	}

	
	public static TreeMap<String, Object> payParam(){
		TreeMap<String, Object> dataMap	= new TreeMap<String, Object>();
		dataMap.put("merchantaccount", 	"YB01000000144");
		dataMap.put("orderid", 			1002);
		dataMap.put("transtime", 		System.currentTimeMillis()/1000);
		dataMap.put("currency", 		156);
		dataMap.put("amount", 			10);
		dataMap.put("productname", 		"测试支付001");
		dataMap.put("identityid", 		1002);
		dataMap.put("identitytype", 	2);
		dataMap.put("card_top", 		621483);
		dataMap.put("card_last", 		5537);
		dataMap.put("orderexpdate", 	30);
		dataMap.put("callbackurl", 		"http://192.168.0.100:8080/wgtz/NewFile1.jsp");
		dataMap.put("userip", 			"192.168.0.100");
		dataMap.put("ua", 				"dd");
		return dataMap;
	}
	
	private String phone = ""; 
	
	/**导入手机号码;
	 * @return
	 */
	public String inputPhone(){
		String[] strArray=null;
		strArray=phone.split("\r\n");
		if(strArray.length>0){
	    for(int i=0; i<strArray.length; i++) {
	    	if(QwyUtil.verifyPhone(strArray[i])){
	    		Users users=bean.getUsersByUsername(strArray[i]);
	    		if(users==null){//如果为空
	    			System.out.println(strArray[i]+"用户不存在");
	    		}else{
	    			boolean isExists = bean.isExistsHDUsers(strArray[i]);
	    			if(!isExists){
	    				bean.createUsers(users);
	    			}else{
	    				System.out.println(strArray[i]+"已经发放过投资券");
	    			}
	    		}
	    	}
	    }
		}
		return "";
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
	
}
