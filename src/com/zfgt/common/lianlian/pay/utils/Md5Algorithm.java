package com.zfgt.common.lianlian.pay.utils;



import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.log4j.Logger;
import org.jfree.util.Log;

/**
 * 32位MD5摘要算法
 * @author shmily
 * @date 2012-6-28 下午02:34:45
 */
public class Md5Algorithm {
	private static Logger log = Logger.getLogger(Md5Algorithm.class);
	private static Md5Algorithm instance;
	
	private final static String[] hexDigits = { "0", "1", "2", "3", "4", "5",
			"6", "7", "8", "9", "a", "b", "c", "d", "e", "f" };

	private Md5Algorithm(){
		
	}
	
	public static Md5Algorithm getInstance(){
		if(null == instance)
			return new Md5Algorithm();
		return instance;
	}
	
	/**
	 * 转换字节数组为16进制字串
	 * @param b 字节数组
	 * @return 16进制字串
	 */
	private String byteArrayToHexString(byte[] b) {
		StringBuffer resultSb = new StringBuffer();
		for (int i = 0; i < b.length; i++) {
			resultSb.append(byteToHexString(b[i]));
		}
		return resultSb.toString();
	}

	/**
	 * 转换字节数组为高位字符串
	 * @param b 字节数组
	 * @return
	 */
	private String byteToHexString(byte b) {
		int n = b;
		if (n < 0)
			n = 256 + n;
		int d1 = n / 16;
		int d2 = n % 16;
		return hexDigits[d1] + hexDigits[d2];
	}

	/**
	 * MD5 摘要计算(byte[]).
	 * @param src byte[]
	 * @throws Exception
	 * @return String
	 */
	public String md5Digest(byte[] src) {
		MessageDigest alg;
		try {
			// MD5 is 32 bit message digest
			alg = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			return null;
		} 
		return byteArrayToHexString(alg.digest(src));
	}
	
	public static void main(String[] args) {
		try {
			String reqString="";
			System.out.println("返回结果："+Md5Algorithm.getInstance().md5Digest(reqString.getBytes("utf-8")));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public String sign(String content, String md5Key) {
		content = content + "&key=" + md5Key;
		try {
			log.info("待签名串【"+content+"】");
			return md5Digest(content.getBytes("UTF-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public boolean doCheck(String content, String sign, String md5Key) {
		content = content + "&key=" + md5Key;
		try {
			return md5Digest(content.getBytes("UTF-8")).equals(sign);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return false;
	}
}
