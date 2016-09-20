package com.zfgt.common.util;

import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;

/**
 * 配置文件获取工具类
 * 
 * @author Administrator 2014-12-30 18:06:22
 */
public class PropertiesUtil {
	private static Logger logger = Logger.getLogger(PropertiesUtil.class);

	/**获取java.properties配置文件;
	 * @param key Key值;
	 * @return
	 */
	public static Object getProperties(String key) {
		return getProperties(key, "java.properties");
	}

	/**获取属性文件的;
	 * @param key Key值;
	 * @param propertiesName 属性文件名; 以.properties后缀结尾;
	 * @return
	 */
	public static Object getProperties(String key, String propertiesName) {
		try {
			logger.debug("获取超时时间");
			Properties prop = new Properties();
			InputStream in = PropertiesUtil.class.getResourceAsStream("/"
					+ propertiesName);
			prop.load(in);
			System.out.println(prop.getProperty(key).trim());
			logger.debug("获取key为" + key + "的值为" + prop.getProperty(key).trim());
			return prop.getProperty(key);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e);
		}
		return null;
	}

	/**
	 * 将字符串转换为Integer
	 */
	public static Integer StringParseInteger(Object object) {
		try {
			if (object != null && !object.equals("")) {
				return Integer.parseInt(object.toString());
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e);
		}
		return 0;
	}

}
