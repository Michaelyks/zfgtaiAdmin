package com.zfgt.common.util;


import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;

import com.zfgt.common.ApplicationContexts;
import com.zfgt.common.bean.PlatformBean;
import com.zfgt.orm.Account;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**DES加密工具类
 * @author qwy
 *
 * @createTime 2015-04-17 01:03:49
 */
public class DESEncrypt {

	private static Logger log = Logger.getLogger(DESEncrypt.class); 
	
	/**
	 * 加密
	 * @param src 要加密的数据
	 * @param key 加密取用的key。八位字符串
	 * @return
	 * @throws Exception
	 */
	public static final String encrypt(String src, String key)throws Exception {
		if(src==null)
			return null;
		if("".equals(src))
			return "";
		SecureRandom sr = new SecureRandom(); 
		
		DESKeySpec dks = new DESKeySpec(key.getBytes()); 
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES"); 

        SecretKey securekey = keyFactory.generateSecret(dks); 
        
        Cipher cipher = Cipher.getInstance("DES"); 

        // 用密匙初始化Cipher对象 

        cipher.init(Cipher.ENCRYPT_MODE, securekey, sr); 
        byte bb[] = cipher.doFinal(src.getBytes());
        StringBuffer buff = new StringBuffer(bb.length);
		String sTemp;
		for(int i=0;i<bb.length;i++){
				sTemp = Integer.toHexString(0xFF &bb[i]);
				if(sTemp.length()<2){
					buff.append(0);
				}
				
				buff.append(sTemp.toUpperCase());
			
		}
        
        return buff.toString();
        
	}
	
	/**
	 * 解密
	 * @param src 要解密的数据源
	 * @param key 加密时取用的key，八位字符串
	 * @return
	 * @throws Exception
	 */
	public static final String decrypt(String src, String key)throws Exception { 
		if(src==null)
			return null;
		if("".equals(src))
			return "";
		try {
			int len = (src.length()/2);
			byte [] result = new byte[len];
			char[] achar = src.toString().toCharArray();
			for(int j=0;j<len;j++){
				int pos = j*2;
				result[j]= ((byte)(toByte(achar[pos])<<4|toByte(achar[pos+1])));
			}
			
			
			// DES算法要求有一个可信任的随机数源 

			SecureRandom sr = new SecureRandom(); 

			// 从原始密匙数据创建一个DESKeySpec对象 

			DESKeySpec dks = new DESKeySpec(key.getBytes()); 

			// 创建一个密匙工厂，然后用它把DESKeySpec对象转换成 

			// 一个SecretKey对象 

			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES"); 

			SecretKey securekey = keyFactory.generateSecret(dks); 

			// Cipher对象实际完成解密操作 

			Cipher cipher = Cipher.getInstance("DES"); 

			// 用密匙初始化Cipher对象 

			cipher.init(Cipher.DECRYPT_MODE, securekey, sr); 

			// 现在，获取数据并解密 

			// 正式执行解密操作 

			return new String(cipher.doFinal(result));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.error("操作异常: ",e);
		} 
		return src;

     } 
	
	private static byte toByte(char c){
		
		byte  b = (byte)"0123456789ABCDEF".indexOf(c);
		return b;
	}
	
	/**加密字符串;<br>
	 * 登录密码;支付密码;
	 * @param string 需要加密的字符串;
	 * @return 加密过后的字符串;
	 * @throws Exception
	 */
	public static String jiaMiPassword(String string){
		try {
			String str = encrypt(string,"bym*_*@@");
			String str2 = encrypt(str,"baiyimao");
			return str2;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.error("操作异常: ",e);
		}
		return null;
	}
	
	/**解密字符串;<br>
	 * 登录密码;支付密码;
	 * @param string 加密过后的字符串;
	 * @return 解密过后的字符串;
	 * @throws Exception
	 */
	public static String jieMiPassword(String string){
		String de2=null;
		try {
			String de = decrypt(string, "baiyimao");
			de2 = decrypt(de, "bym*_*@@");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.error("操作异常: ",e);
		}
		return de2;
	}
	
	/**加密字符串;<br>
	 * 帐号,手机号码,邮箱;
	 * @param string 需要加密的字符串;
	 * @return 加密过后的字符串;
	 * @throws Exception
	 */
	public static String jiaMiUsername(String string){
		try {
			String str = encrypt(string,"userName");
			String str2 = encrypt(str,"bym-_-@@");
			return str2;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.error("操作异常: ",e);
		}
		return null;
	}
	
	
	/**解密字符串;<br>
	 * 帐号,手机号码,邮箱;
	 * @param string 加密过后的字符串;
	 * @return 解密过后的字符串;
	 * @throws Exception
	 */
	public static String jieMiUsername(String string){
		String de2=null;
		try {
			String de = decrypt(string, "bym-_-@@");
			de2 = decrypt(de, "userName");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.error("操作异常: ",e);
		}
		return de2;
	}
	
	
	/**加密字符串;<br>
	 * 身份证号
	 * @param string 需要加密的字符串;
	 * @return 加密过后的字符串;
	 * @throws Exception
	 */
	public static String jiaMiIdCard(String string){
		try {
			String str = encrypt(string,"@*IdCard*");
			String str2 = encrypt(str,"bymCards");
			return str2;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.error("操作异常: ",e);
		}
		return null;
	}
	
	
	/**解密字符串;<br>
	 * 身份证号
	 * @param string 加密过后的字符串;
	 * @return 解密过后的字符串;
	 * @throws Exception
	 */
	public static String jieMiIdCard(String string){
		String de2=null;
		try {
			String de = decrypt(string, "bymCards");
			de2 = decrypt(de, "@*IdCard*");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.error("操作异常: ",e);
		}
		return de2;
	}
	
	
	
	/**加密字符串;<br>
	 * 银行卡
	 * @param string 需要加密的字符串;
	 * @return 加密过后的字符串;
	 * @throws Exception
	 */
	public static String jiaMiBankCard(String string){
		try {
			String str = encrypt(string,"bymBanks");
			String str2 = encrypt(str,"$_$_bank");
			return str2;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.error("操作异常: ",e);
		}
		return null;
	}
	
	
	/**解密字符串;<br>
	 * 银行卡
	 * @param string 加密过后的字符串;
	 * @return 解密过后的字符串;
	 * @throws Exception
	 */
	public static String jieMiBankCard(String string){
		String de2=null;
		try {
			String de = decrypt(string, "$_$_bank");
			de2 = decrypt(de, "bymBanks");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.error("操作异常: ",e);
		}
		return de2;
	}
	
	/**加密字符串;<br>
	 * 数据库jdbc加密;
	 * @param string 需要加密的字符串;
	 * @return 加密过后的字符串;
	 * @throws Exception
	 */
	public static String jiaMiProperties(String string){
		try {
			String str = encrypt(string,"properties");
			String str2 = encrypt(str,"baiyimao");
			return str2;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.error("操作异常: ",e);
		}
		return null;
	}
	
	/**解密字符串;<br>
	 * 数据库jdbc;
	 * @param string 加密过后的字符串;
	 * @return 解密过后的字符串;
	 * @throws Exception
	 */
	public static String jieMiProperties(String string){
		String de2=null;
		try {
			String de = decrypt(string, "baiyimao");
			de2 = decrypt(de, "properties");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.error("操作异常: ",e);
		}
		return de2;
	}
	
	
	
	public static void main(String[] args) throws Exception {
		//ApplicationContext context = ApplicationContexts.getContexts();
//		String username = "adminss";
//		String phone = "18025729778";
//		String password = "123456";
//		String idCard = "441723199302145632";
//		String bankCard = "6228481164799729911";
//		System.out.println("------------------加密---------------");
	String jUsername = DESEncrypt.jiaMiUsername("15112304365");
	System.out.println(jUsername);
//		String jphone = DESEncrypt.jiaMiUsername(phone);
//		String jpassword = DESEncrypt.jiaMiPassword(password);
//		String jidCard = DESEncrypt.jiaMiIdCard(idCard);
//		String jbankCard = DESEncrypt.jiaMiBankCard(bankCard);
//		
//		System.out.println(jUsername+"__"+jUsername.length());
//		System.out.println(jphone+"__"+jphone.length());
//		System.out.println(jpassword+"__"+jpassword.length());
//		System.out.println(jidCard+"__"+jidCard.length());
//		System.out.println(jbankCard+"__"+jbankCard.length());
//		System.out.println("------------------解密1---------------");
		//System.out.println(DESEncrypt.jieMiUsername(jUsername));
		System.out.println(DESEncrypt.jieMiUsername("74930004277A4C25374695F4F9A480B72DD412D91CB7C2DDB17D4B7355A2DDB92A1C0F45F85B0DE8"));
		System.out.println(DESEncrypt.jieMiPassword("05B72554A2C0ADCB42A0A62B5CB296C8439FCA39E3969C19505FFD5CF7568ACCF65BAFD4124144175D9F31BF4423184E42599D8BA4A8EA49"));
		/*System.out.println(DESEncrypt.jieMiIdCard("43B6CEEAF155DB0A9E09A7FCB6C1FF0FBE803134BABD15087E7AEC25B52EF8625FF50FDC12E3A378A97709650597C709FE07908B19B73E96"));
		System.out.println(DESEncrypt.jieMiBankCard("06C4141C901CD44E6FE111EBE7CF8D58A38A777B240DDC8667C46B59FDA81A7E4C6B0442E3A9ADD56208293ACC41615E240F7A7D9F471BF0"));
		System.out.println("------------------解密2---------------");
		Account ac = new Account();
		ac.setBankAccount(bankCard);
		System.out.println(ac.getBankAccount());*/
//		System.out.println(DESEncrypt.jieMiBankCard("FDEE4E4715474BC8D2053F325F8D6F3C6AC3F12B86FB47CD9D50EAD03614CB9C4C6B0442E3A9ADD56208293ACC41615E240F7A7D9F471BF0"));
	//	System.out.println(DESEncrypt.jieMiIdCard("E1129D1139E1BBCF02C280BC4A8384FB23AF7BDC91D3A4EFEA983849544AC83C836F3C4AA52EEA98C0FE835F2C3599A4FE07908B19B73E96"));
	//	System.out.println(DESEncrypt.jiaMiUsername("15112304365"));
//		System.out.println(DESEncrypt.jiaMiProperties("3e128c281ecf96ee"));
//		System.out.println(DESEncrypt.jieMiProperties("D240A71CCF84A949E40102EABE2D64419EDDE01FF43651E9B7894C4A673663BD86F31ACF145B0655B92FB9E58ABE608842599D8BA4A8EA49"));
//		
//		System.out.println(DESEncrypt.jieMiPassword("05B72554A2C0ADCB42A0A62B5CB296C8439FCA39E3969C19505FFD5CF7568ACCF65BAFD4124144175D9F31BF4423184E42599D8BA4A8EA49"));
		//System.out.println(DESEncrypt.jiaMiPassword("123456"));
		/*String url = "91E3066C9AC97982702BCC3EA1DC2079BEB5CBA4CDEB174790920C63D18C93BB42599D8BA4A8EA49";
		System.out.println(new BASE64Encoder().encode(url.getBytes()));*/
		
//		System.out.println(DESEncrypt.jiaMiProperties("115.159.83.172:3306"));
//System.out.println(DESEncrypt.jieMiProperties("91CC11A33FE1620539F7B6A39F5C6FA98DDD4F8488980A956AA6553F4B82D76660F8963A93E374ABD52C79538BF44D7842599D8BA4A8EA49"));
//		System.out.println(DESEncrypt.jieMiProperties("3E91FBD572B9C947BF4B74B8046F523A86F31ACF145B0655B92FB9E58ABE608842599D8BA4A8EA49"));
//		System.out.println(DESEncrypt.jieMiProperties("96714DE3B0EC57D136B4C2A4D1962CE8134FDE860800976C00104D20C00D211342599D8BA4A8EA49"));
//		System.out.println(DESEncrypt.jiaMiProperties("baiyimao"));
//		//System.out.println(DESEncrypt.jieMiProperties("3E91FBD572B9C947BF4B74B8046F523A86F31ACF145B0655B92FB9E58ABE608842599D8BA4A8EA49"));
//		System.out.println(DESEncrypt.jiaMiProperties("baiyimao26"));
		//System.out.println(DESEncrypt.jieMiProperties("3E91FBD572B9C947BF4B74B8046F523ACAD1A47B902D07660AD0CC9C1F163FA542599D8BA4A8EA49"));
		
	}
	
}
