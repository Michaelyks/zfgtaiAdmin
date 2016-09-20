package com.zfgt.common.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringReader;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.jfree.util.Log;
import org.xml.sax.InputSource;

import com.zfgt.common.Commons;

import net.sf.json.JSONArray;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;

/**
 * 常用的工具方法
 * 
 * @author qwy
 * 
 * @createdTime 2014-4-15下午5:20:07
 */
public class QwyUtil {
	public static final double ulendPercent = 0.10;// 借出人的利率;
	public static final double uloanPercent = 0.12;// 借款人的利率;
	public static final double leaderPercent = 0.12;// 领头人募集预付利率;
	private static Logger log = Logger.getLogger(QwyUtil.class);
	/**
	 * 时间格式: yyyy-MM-dd HH:mm:ss
	 */
	public static SimpleDateFormat fmyyyyMMddHHmmss = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	/**
	 * 时间格式: yyyy-MM-dd
	 */
	public static SimpleDateFormat fmyyyyMMdd = new SimpleDateFormat("yyyy-MM-dd");
	/**
	 * 时间格式: yyyy-MM-dd HH:mm
	 */
	public static SimpleDateFormat fmyyyyMMddHHmm = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	/**
	 * 时间格式: MM/dd/yyyy
	 */
	public static SimpleDateFormat fmMMddyyyy = new SimpleDateFormat("MM/dd/yyyy");
	/**
	 * 时间格式: MM/dd/yyyy HH:mm:ss
	 */
	public static SimpleDateFormat fmMMddyyyyHHmmss = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
	/**
	 * 时间格式: yyyy-MM-dd HH:mm:ss:SSS
	 */
	public static SimpleDateFormat yyyyMMddHHmmssSSS = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");
	/**
	 * 时间格式: HH:mm:ss
	 */
	public static SimpleDateFormat fmHHmmss = new SimpleDateFormat("HH:mm:ss");
	/**
	 * 时间格式: yyyyMMddHHmmss
	 */
	public static SimpleDateFormat fmyyyyMMddHHmmss2 = new SimpleDateFormat("yyyyMMddHHmmss");
	/**
	 * 时间格式: yyyyMMdd
	 */
	public static SimpleDateFormat fmyyyyMM = new SimpleDateFormat("yyyyMMdd");
	/**
	 * 时间格式: yyyyMMddHHmmssSSS
	 */
	public static SimpleDateFormat fmyyyyMMddHHmmss3 = new SimpleDateFormat("yyyyMMddHHmmssSSS");
	/**
	 * 时间格式: yyyy-MM
	 */
	public static SimpleDateFormat fmyyMM = new SimpleDateFormat("yyyy-MM");

	/**
	 * 判断object是否为null或者为"" true:没有数据;<br>
	 * true:没有数据;<br>
	 * false:有数据;
	 * 
	 * @param obj
	 *            Object
	 * @return boolean
	 */
	public static boolean isNullAndEmpty(Object obj) {
		if (obj != null && !"".equals(obj.toString()) && !("null".equalsIgnoreCase(obj.toString()))) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * 判断数组是否为null或者有没有数据;<br>
	 * true:没有数据;<br>
	 * false:有数据;
	 * 
	 * @param objs
	 *            数组
	 * @return boolean
	 */
	public static boolean isNullAndEmpty(Object[] objs) {
		if (objs != null && objs.length > 0) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * List集合里面是否有数据<br>
	 * true:没有数据;<br>
	 * false:有数据;
	 * 
	 * @param list
	 *            List集合
	 * @return boolean
	 */
	public static boolean isNullAndEmpty(Collection<?> list) {
		if (list != null && list.size() > 0) {
			return false;
		}
		return true;
	}

	/**
	 * 对以分隔符来作为间隔拼装字符串的.进行分割.转换为数组;
	 * 
	 * @param strs
	 *            以分隔符进行拼接的字符串,如 abc,cdd,fff,ddd
	 * @param separated
	 *            拼装的分隔符;
	 * @return String[]
	 */
	public static String[] splitString(String str, String separated) {
		String[] strs = null;
		if (!isNullAndEmpty(str)) {
			if (str.endsWith(separated)) {
				str = str.substring(0, str.length() - 1);
			}
			strs = str.split(separated);
		}
		return strs;
	}

	/**
	 * 把一个数组转化成字符串,用单引号括起来"'",多个之间用逗号隔开","如 '1','2'
	 * 
	 * @param objs
	 *            Object[] objs 要转换的数组
	 * @return String[]
	 */
	public static String packString(Object[] objs) {
		String strs = "";
		if (!isNullAndEmpty(objs)) {
			for (Object obj : objs) {
				strs += "'" + obj + "',";
			}
			if (strs.endsWith(",")) {
				strs = strs.substring(0, strs.length() - 1);
			}
		}
		return strs;
	}

	/**
	 * 对两个数进行四则运算; <strong>对于除法的运算,保留4位小数;</strong><br>
	 * result = num1+num2;<br>
	 * result = num1-num2;<br>
	 * result = num1*num2;<br>
	 * result = num1/num2;<br>
	 * 
	 * @param num1
	 *            式子中的第一个数;
	 * @param num2
	 *            式子中的第二个数;
	 * @param calcSymbol
	 *            运算符号 "+" "-" "*" "/"
	 * @return BigDecimal
	 * @throws Exception
	 *             计算异常;
	 */
	public static BigDecimal calcNumber(Object num1, Object num2, String calcSymbol) throws Exception {
		return calcNumber(num1, num2, calcSymbol, 4);

	}

	/**
	 * 对两个数进行四则运算; 自行选择保留位数;不四舍五入,直接截取; result = num1+num2;<br>
	 * result = num1-num2;<br>
	 * result = num1*num2;<br>
	 * result = num1/num2;<br>
	 * 
	 * @param num1
	 *            式子中的第一个数;
	 * @param num2
	 *            式子中的第二个数;
	 * @param calcSymbol
	 *            运算符号 "+" "-" "*" "/"
	 * @param remainNum
	 *            保留多少位小数; 如果小于0,则为0;
	 * @return BigDecimal
	 * @throws Exception
	 *             计算异常;
	 */
	public static BigDecimal calcNumber(Object num1, Object num2, String calcSymbol, int remainNum) throws Exception {
		remainNum = remainNum < 0 ? 0 : remainNum;
		if (!isNullAndEmpty(num1) && !isNullAndEmpty(num2)) {
			BigDecimal decimal = new BigDecimal(num1.toString());
			BigDecimal decima2 = new BigDecimal(num2.toString());
			if ("+".equals(calcSymbol)) {
				return decimal.add(decima2);
			} else if ("-".equals(calcSymbol)) {
				return decimal.subtract(decima2);
			} else if ("*".equals(calcSymbol)) {
				return decimal.multiply(decima2);
			} else if ("/".equals(calcSymbol)) {
				if (!num2.equals("0")) {
					return decimal.divide(decima2, remainNum, BigDecimal.ROUND_DOWN);
				} else {
					throw new Exception();
				}
			} else {
				return null;
			}
		}
		return null;
	}

	/**
	 * 把大的数字用中文的单位来替代<br>
	 * 返回的中文单位有:<b>万</b>、<b>亿</b> 不足<b>万</b>的,直接返回原数据;
	 * 
	 * @param num1
	 *            数值
	 * @return 中文单位的表式
	 */
	public static String getChineseNumber(Double num1) {
		NumberFormat format = NumberFormat.getInstance();
		format.setMaximumFractionDigits(2);
		if (num1 >= 100000000) {
			return (format.format(((num1 / 100000000))) + "亿");
		} /*
			 * else if (num1 >= 10000000) { return (format.format((num1 /
			 * 10000000)) + "千万"); } else if (num1 >= 1000000) { return
			 * (format.format((num1 / 1000000)) + "百万"); } else if (num1 >=
			 * 100000) { return (format.format((num1 / 100000)) + "十万"); }
			 */else if (num1 >= 10000) {
			return (format.format((num1 / 10000)) + "万");
		} else {
			return (num1.intValue() + "");
		}
	}

	/**
	 * 返回JSON格式的字符串;<br>
	 * 一般与getJSONString()方法搭配使用;先通过getJSONString()获得JSON格式的字符串,把返回的结果传进此方法调用输出;
	 * 
	 * @param response
	 *            HttpServletResponse
	 * @param json
	 *            JSON格式的字符串;
	 * @throws IOException
	 */
	public static void printJSON(HttpServletResponse response, String json) throws IOException {
		response.setCharacterEncoding("UTF-8");
		response.setHeader("Pragma", "No-Cache");
		response.setHeader("Cache-Control", "No-Cache");
		response.setDateHeader("Expires", 0);
		response.setContentType("text/x-json;charset=UTF-8");
		// json = json.replace("'", "\"");
		PrintWriter print = response.getWriter();
		print.print(json);
	}

	/**
	 * 根据状态返回想要的字符串;格式为:<br>
	 * {"status":"ok","json":"我成功了"}
	 * 
	 * 
	 * @param status
	 *            状态
	 * @param jsonString
	 *            要返回的字符串 如果没有填写""
	 * 
	 * @return
	 */
	public static String getJSONString(final String status, final String jsonString) {
		StringBuffer buff = new StringBuffer();
		buff.append("{\"status\":\"");
		buff.append(status);
		buff.append("\",");
		buff.append("\"json\":\"");
		buff.append(jsonString);
		buff.append("\"}");
		return buff.toString();
	}

	/**
	 * 根据状态返回想要的字符串;格式为:<br>
	 * {"status":"ok","json":[集合或对象]}
	 * 
	 * 
	 * @param status
	 *            状态
	 * @param jsonArray
	 *            经过JSONArray.fromObject(object);处理过的JSON对象;可以是一个集合
	 * @return
	 */
	public static String getJSONString(final String status, final JSONArray jsonArray) {
		StringBuffer buff = new StringBuffer();
		buff.append("{\"status\":\"");
		buff.append(status);
		buff.append("\",");
		buff.append("\"json\":");
		buff.append(jsonArray);
		buff.append("}");
		return buff.toString();
	}

	/**
	 * 字符串替换
	 * 
	 * @param str
	 *            字符串对象
	 * @param oldStr
	 *            需被替换的对象
	 * @param newStr
	 *            替换对象
	 * @return
	 */
	public static String stringReplace(String str, String oldStr, String newStr) {
		return str.replaceAll(oldStr, newStr);
	}

	/**
	 * 得到一个字符串的长度,显示的长度,一个汉字或日韩文长度为2,英文字符长度为1
	 * 
	 * @param String
	 *            s 需要得到长度的字符串
	 * @return int 得到的字符串长度
	 */
	public static int getStringLength(String s) {
		int valueLength = 0;
		String chinese = "[\u4e00-\u9fa5]";
		// 获取字段值的长度，如果含中文字符，则每个中文字符长度为2，否则为1
		for (int i = 0; i < s.length(); i++) {
			// 获取一个字符
			String temp = s.substring(i, i + 1);
			// 判断是否为中文字符
			if (temp.matches(chinese)) {
				// 中文字符长度为2
				valueLength += 2;
			} else {
				// 其他字符长度为1
				valueLength += 1;
			}
		}
		// 进位取整
		return valueLength;
	}

	/**
	 * 向一个老日期追加天数,返回一个新日期;
	 * 
	 * @param oldDate
	 *            被追加天数的日期;
	 * @param days
	 *            追加的天数;
	 * @return 返回一个日历对象,追加天数后的日期;通过Calendar.getTime()可获得;
	 */
	public static Calendar addDaysFromOldDate(Date oldDate, int days) {
		// 添加结束时间;
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(oldDate);
		calendar.add(Calendar.DATE, days);
		return calendar;
	}

	/**
	 * 向一个老日期追加月份,返回一个新日期;
	 * 
	 * @param oldDate
	 *            被追加月份的日期;
	 * @param months
	 *            追加的月份;
	 * @return 返回一个日历对象,追加月份后的日期;通过Calendar.getTime()可获得;
	 */
	public static Calendar addMonthsFromOldDate(Date oldDate, int months) {
		// 添加结束时间;
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(oldDate);
		calendar.add(Calendar.MONTH, months);
		return calendar;
	}

	/**
	 * 获取两个时间相差的天数,包含开始时间,包含结束时间;只取日期; <br>
	 * <b>算法为: 天数 = (结算日-开始日)+1</b><br>
	 * 如:2014-08-01 12:00:00 到 2014-08-01 12:00:01;也算一天;<br>
	 * 2014-08-01 12:00:00 到 2014-08-02 23:59:59; 就算两天;<br>
	 * 
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public static int getDifferDays(Date startTime, Date endTime) {
		int days = 0;
		try {
			/*
			 * if(isNullAndEmpty(startTime) || isNullAndEmpty(endTime)) return
			 * 0;
			 */
			SimpleDateFormat fdate = new SimpleDateFormat("yyyy-MM-dd");
			startTime = fdate.parse(fdate.format(startTime));
			endTime = fdate.parse(fdate.format(endTime));
			while (endTime.compareTo(startTime) >= 0) {
				Calendar ca = Calendar.getInstance();
				ca.setTime(startTime);
				ca.add(Calendar.DATE, 1);
				days++;
				startTime = fdate.parse(fdate.format(ca.getTime()));
			}
		} catch (ParseException e) {
			log.error("操作异常: ", e);
		}
		return days;
	}

	/**
	 * 获得(日)利率--借出用户的利率;
	 * 
	 * @return 日利率
	 */
	public static double getDayPercentUserLending() {
		double dayPercent = 0.0;
		try {
			// double year = ulendPercent;
			dayPercent = calcNumber(ulendPercent, 365, "/", 7).doubleValue();
		} catch (Exception e) {
			log.error("操作异常: ", e);
		}
		return dayPercent;
	}

	/**
	 * 获得(日)利率--借款用户的利率;
	 * 
	 * @return 日利率
	 */
	public static double getDayPercentUserLoan() {
		double dayPercent = 0.0;
		try {
			// double year =uloanPercent;
			dayPercent = calcNumber(uloanPercent, 365, "/", 7).doubleValue();
		} catch (Exception e) {
			log.error("操作异常: ", e);
		}
		return dayPercent;
	}

	/**
	 * 获得领头人募集---预付利息利率;
	 * 
	 * @return 日利率
	 */
	public static double getDayPercentLeader() {
		double dayPercent = 0.0;
		try {
			// double year =uloanPercent;
			dayPercent = calcNumber(leaderPercent, 365, "/", 7).doubleValue();
		} catch (Exception e) {
			log.error("操作异常: ", e);
		}
		return dayPercent;
	}

	/**
	 * 利率转换
	 * 
	 * @return
	 */
	public static String Ratetransform() {
		double dayPercent = 0;
		String day_percent = "";
		try {
			dayPercent = calcNumber(leaderPercent, 365, "/", 7).doubleValue();
		} catch (Exception e) {
			log.error("操作异常: ", e);
		}
		;
		if (0 < dayPercent && dayPercent < 0.0001) {
			day_percent = "万分之零";
		} else if (0.0001 <= dayPercent && dayPercent < 0.0002) {
			day_percent = "万分之一";
		} else if (0.0002 <= dayPercent && dayPercent < 0.0003) {
			day_percent = "万分之二";
		} else if (0.0003 <= dayPercent && dayPercent < 0.0004) {
			day_percent = "万分之三";
		} else if (0.0004 <= dayPercent && dayPercent < 0.0005) {
			day_percent = "万分之四";
		} else if (0.0005 <= dayPercent && dayPercent < 0.0006) {
			day_percent = "万分之五";
		}
		return day_percent;
	}

	/**
	 * 进一法,自行选择保留位数;<br>
	 * 把最后一位数加一;如果数值的小数位数刚好等于要保留的位数;则返回原值;<b>如:以下举例均为保留两位小数</b><br>
	 * 格式为: 新数值>=旧数值 234.24>=234.24、 234.24>=234.231、
	 * 234.24>=234.235、234.24>=234.239
	 * 
	 * @param number
	 *            数值
	 * @param digit
	 *            保留多少位小数;
	 * @return
	 */
	public static double jinYiFa(double number, int digit) throws Exception {
		double result = 0;
		if (digit < 0) {
			throw new Exception("The digit must be positive integer!");
		}
		String temp = "1";
		for (int i = 0; i < digit; i++) {
			temp += "0";
		}
		double newNum = new BigDecimal(temp).multiply(new BigDecimal(number)).doubleValue();
		result = calcNumber(Math.ceil(newNum), new BigDecimal(temp), "/", digit).doubleValue();
		return result;
	}

	/**
	 * 对一个数值,直接截取需要的位数;后面的值省略;<br>
	 * 如: 23.3245423; jieQuFa(23.3245423,2); 返回: 23.32; jieQuFa(23.3,2);
	 * 返回23.20;
	 * 
	 * @param number
	 *            数值
	 * @param digit
	 *            保留多少位小数;
	 * @return 截取后的数值;
	 * @throws Exception
	 */
	public static double jieQuFa(double number, int digit) throws Exception {
		if (digit < 0) {
			throw new Exception("The digit must be positive integer!");
		}
		String temp = "1";
		for (int i = 0; i < digit; i++) {
			temp += "0";
		}
		BigDecimal newNum = new BigDecimal(calcNumber(number, temp, "*").doubleValue()).setScale(digit, BigDecimal.ROUND_DOWN);

		return calcNumber(newNum, temp, "/", digit).doubleValue();
	}

	/**
	 * 对一个以符号隔开的字符串,进行重新封装,一般用作于SQL的 in 查询;
	 * <hr>
	 * <br>
	 * 例如:<br>
	 * String a = "1,2,3,4,5"; 调用此方法:appendSeparatorToString(a,",","'");返回的结果是:
	 * return 返回一个用逗号隔开的字符串 "'1','2','3','4','5'";
	 * 
	 * @param content
	 *            以某个符号隔开的字符串内容;
	 * @param separator
	 *            原字符串里的分隔符;
	 * @param appendChar
	 *            用以括起来的符号;
	 * @return 用符号括起来,用逗号隔开的字符串
	 */
	public static String appendSeparatorToString(String content, String separator, String appendChar) {
		if (isNullAndEmpty(content))
			return content;
		String[] strs = splitString(content, separator);
		StringBuffer buff = new StringBuffer("");
		for (String str : strs) {
			buff.append(appendChar);
			buff.append(str);
			buff.append(appendChar);
			buff.append(",");
		}
		String temp = buff.toString();
		temp = isNullAndEmpty(temp) ? temp : temp.substring(0, temp.length() - 1);
		return temp;
	}

	/**
	 * 分割以逗号隔开的字符串
	 * <hr>
	 * <br>
	 * 分割后,每个字符串用单引号括起来,并以逗号隔开;
	 * 
	 * @param content
	 *            要分割的字符串
	 * @return 用单引号括起来,用逗号隔开的字符串
	 */
	public static String appendSeparatorToString(String content) {
		return appendSeparatorToString(content, ",", "'");
	}

	/**
	 * 把字符串中间用*号代替; 字符串长度不足4位的,后面追加4个*号
	 * 
	 * @param str
	 * @return
	 */
	public static String replaceStringToX(String str) {
		String newStr = "";
		if (isNullAndEmpty(str)) {
			Log.info("投资人为null");
			return "";
		}

		if (str.length() > 4) {
			String first = str.substring(0, 3);
			String end = str.substring(str.length() - 4);
			newStr = first + "****" + end;
		} else {
			newStr = str + "****";
		}
		return newStr;
	}

	/**
	 * 获取客户端用户的真实IP;
	 * 
	 * @param request
	 *            HttpServletRequest
	 * @return 真实IP
	 */
	public static String getIpAddr(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		if (ip.indexOf("0:0") != -1)
			ip = "127.0.0.1";
		if (!QwyUtil.isNullAndEmpty(ip)) {
			String ips[] = ip.split(",");
			if (ips.length >= 2) {
				ip = ips[0];
			}
		}
		return ip;
	}

	/**
	 * 用Post的方法访问网络;
	 * 
	 * @param url
	 *            访问地址 POST访问
	 * @param values
	 *            参数 Map&lt;String,Object&gt; Key-Value的形式;如果需要传递识别码,则以这种格式传递:
	 *            <br>
	 *            &nbsp;&nbsp;&nbsp;&nbsp; map.put("authorization",
	 *            "69dfe6ff-afdb-4678-984e-b23b22f5c88c");
	 * @return Map&lt;String,Object&gt;
	 */
	public static Map<String, Object> accessIntentByPost(String url, Map<String, Object> values) {
		return accessIntent(url, values, "POST");
	}

	/**
	 * 用Post的方法访问网络;
	 * 
	 * @param url
	 *            访问地址 POST访问
	 * @param values
	 *            参数 Map&lt;String,Object&gt; Key-Value的形式;如果需要传递识别码,则以这种格式传递:
	 *            <br>
	 *            &nbsp;&nbsp;&nbsp;&nbsp; map.put("authorization",
	 *            "69dfe6ff-afdb-4678-984e-b23b22f5c88c");
	 * @return Map&lt;String,Object&gt;
	 */
	public static Map<String, Object> returnXMLAccessIntentByPost(String url, Map<String, Object> values) {
		return accessIntent(url, values, "POST", true);
	}

	/**
	 * 访问网络
	 * 
	 * @param url
	 *            访问地址 get访问,直接在地址后面带参数;
	 * @param values
	 *            参数 当POST访问时,才需要用到此参数;如果需要传递识别码,则以这种格式传递: <br>
	 *            &nbsp;&nbsp;&nbsp;&nbsp; map.put("authorization",
	 *            "69dfe6ff-afdb-4678-984e-b23b22f5c88c");
	 * @param method
	 *            访问方法;POST,GET
	 * @return Map&lt;String,Object&gt;
	 */
	private static Map<String, Object> accessIntent(String url, Map<String, Object> values, String method) {
		return accessIntent(url, values, method, false);
	}

	/**
	 * 访问网络
	 * 
	 * @param url
	 *            访问地址 get访问,直接在地址后面带参数;
	 * @param values
	 *            参数 当POST访问时,才需要用到此参数;如果需要传递识别码,则以这种格式传递: <br>
	 *            &nbsp;&nbsp;&nbsp;&nbsp; map.put("authorization",
	 *            "69dfe6ff-afdb-4678-984e-b23b22f5c88c");
	 * @param method
	 *            访问方法;POST,GET
	 * @param isXml
	 *            是否是xml文件
	 * @return Map&lt;String,Object&gt;
	 */
	private static Map<String, Object> accessIntent(String url, Map<String, Object> values, String method, boolean isXml) {
		HttpGet get = null;// get访问
		HttpPost post = null;// post访问
		HttpResponse response = null;// 响应
		try {
			DefaultHttpClient client = new DefaultHttpClient();
			client.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 10000);
			client.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 20000);
			if (method.equalsIgnoreCase("GET")) {
				// get访问
				get = new HttpGet(url);
				if (!isNullAndEmpty(values) && !isNullAndEmpty(values.get("authorization")))
					get.setHeader("Authorization", values.get("authorization").toString());
				response = client.execute(get);
			} else {
				// Post访问;
				post = new HttpPost(url);
				if (!isNullAndEmpty(values) && !isNullAndEmpty(values.get("authorization")))
					post.setHeader("Authorization", values.get("authorization").toString());
				if (!isNullAndEmpty(values)) {
					List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
					Iterator<String> keys = values.keySet().iterator();
					while (keys.hasNext()) {
						String key = keys.next();
						params.add(new BasicNameValuePair(key, values.get(key).toString()));
					}
					HttpEntity entity = new UrlEncodedFormEntity(params, "UTF-8");
					post.setEntity(entity);
				}
				response = client.execute(post);
			}
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				String str = EntityUtils.toString(response.getEntity(), "UTF-8");
				try {
					if (isXml) {
						str = str.replaceAll("\r", "");
						str = str.replaceAll("\n", "");
						return QwyUtil.XmlStringReader(str);
					} else {
						JSONObject jo = JSONObject.fromObject(str);
						return QwyUtil.jsonParseToMap(jo);
					}
				} catch (JSONException e) {
					log.error("操作异常: ", e);
				}
			}
			return Commons.mapException();
		} catch (Exception e) {
			log.error("操作异常: ", e);
		}
		return Commons.mapException();
	}

	/**
	 * 将json转换成Map格式;
	 * 
	 * @param json
	 *            JSONObject
	 * @return Map&lt;String,Object&gt;
	 * @throws JSONException
	 */
	@SuppressWarnings("unchecked")
	public static Map<String, Object> jsonParseToMap(JSONObject json) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			if (!isNullAndEmpty(json)) {
				Iterator<String> iterator = json.keys();
				while (iterator.hasNext()) {
					String key = iterator.next();
					map.put(key, json.get(key));
				}

			}
		} catch (JSONException e) {
			log.error("操作异常: ", e);
		}
		return map;
	}

	/**
	 * 是否是手机格式;<br>
	 * 只验证 13,15,17,18,19开头的11位手机号码;
	 * 
	 * @param phone
	 *            手机号;
	 * @return true:格式正确; false:格式错误;
	 */
	public static boolean verifyPhone(String phone) {
		String p_phone = "^[1][345789][0-9]{9}$";
		Pattern pattern_1 = Pattern.compile(p_phone);
		Matcher m_phone = pattern_1.matcher(phone);
		// if(!m_phone.find()){
		// return false;
		// }
		// return true;
		return m_phone.matches();
	}

	public static boolean verifyStrLength(String str, int begin, int end) {
		String reg = "^\\w{" + begin + "," + end + "}$";
		Pattern pattern = Pattern.compile(reg);
		Matcher matcher = pattern.matcher(str);
		return matcher.matches();
	}

	/**
	 * 该值是为密码;
	 * 
	 * @param money
	 *            字符串
	 * @return
	 */
	public static boolean isPassword(String userPassword) {
		Pattern pattern = Pattern.compile("^\\w{6,16}$");
		return pattern.matcher(userPassword).matches();
	}

	/**
	 * 该值是否为价格的格式; 例如:1、21.12、12.0、0.1222；
	 * 
	 * @param money
	 *            字符串
	 * @return
	 */
	public static boolean isPrice(String money) {
		Pattern pattern = Pattern.compile("^[0-9]+(\\.[0-9]+)?$");
		return pattern.matcher(money).matches();
	}

	/**
	 * 是否为纯数字
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isOnlyNumber(String str) {
		Pattern pattern = Pattern.compile("^[0-9]+$");
		return pattern.matcher(str).matches();
	}

	/**
	 * 获取JSONObect的值
	 * 
	 * @param jsonObject
	 * @param key
	 * @return
	 */
	public static String get(JSONObject jsonObject, String key) {
		try {
			if (jsonObject.containsKey(key)) {
				return jsonObject.getString(key);
			}
		} catch (Exception e) {
			log.error("操作异常: ", e);
		}
		return "";
	}

	/**
	 * 根据"-"拆分时间
	 */
	public static String[] splitTime(String time) {
		if (!QwyUtil.isNullAndEmpty(time)) {
			return time.split("-");
		}
		return null;
	}

	/**
	 * 将字符串格式化为空
	 * 
	 * @param value
	 * @return
	 */
	public static String formatString(String value) {
		if (value == null) {
			return "";
		}
		return value;
	}

	/**
	 * 将对象转换为map集合
	 * 
	 * @param obj
	 * @return
	 */
	public static Map<String, String> getValueMap(Object obj) {

		Map<String, String> map = new HashMap<String, String>();
		// System.out.println(obj.getClass());
		// 获取f对象对应类中的所有属性域
		Field[] fields = obj.getClass().getDeclaredFields();
		for (int i = 0, len = fields.length; i < len; i++) {
			String varName = fields[i].getName();
			try {
				// 获取原来的访问控制权限
				boolean accessFlag = fields[i].isAccessible();
				// 修改访问控制权限
				fields[i].setAccessible(true);
				// 获取在对象f中属性fields[i]对应的对象中的变量
				Object o = fields[i].get(obj);
				if (o != null)
					map.put(varName, o.toString());
				// System.out.println("传入的对象中包含一个如下的变量：" + varName + " = " + o);
				// 恢复访问控制权限
				fields[i].setAccessible(accessFlag);
			} catch (IllegalArgumentException ex) {
				ex.printStackTrace();
			} catch (IllegalAccessException ex) {
				ex.printStackTrace();
			}
		}
		return map;

	}

	/**
	 * 将对象转换为map集合
	 * 
	 * @param obj
	 * @return
	 */
	public static Map<String, Object> getValueObjectMap(Object obj) {

		Map<String, Object> map = new HashMap<String, Object>();
		// System.out.println(obj.getClass());
		// 获取f对象对应类中的所有属性域
		Field[] fields = obj.getClass().getDeclaredFields();
		for (int i = 0, len = fields.length; i < len; i++) {
			String varName = fields[i].getName();
			try {
				// 获取原来的访问控制权限
				boolean accessFlag = fields[i].isAccessible();
				// 修改访问控制权限
				fields[i].setAccessible(true);
				// 获取在对象f中属性fields[i]对应的对象中的变量
				Object o = fields[i].get(obj);
				if (o != null)
					map.put(varName, o.toString());
				// System.out.println("传入的对象中包含一个如下的变量：" + varName + " = " + o);
				// 恢复访问控制权限
				fields[i].setAccessible(accessFlag);
			} catch (IllegalArgumentException ex) {
				ex.printStackTrace();
			} catch (IllegalAccessException ex) {
				ex.printStackTrace();
			}
		}
		return map;
	}

	/**
	 * 将Object转换为Double类型 返回 Long
	 */
	public static Double parToDouble(Object str) throws Exception {
		Double strRetrun = 0.0;
		if (!QwyUtil.isNullAndEmpty(str)) {
			strRetrun = Double.parseDouble(str.toString());
		}
		return strRetrun;
	}

	/**
	 * 根据key获取list中所有该key的值并拼接起来用“,”隔开
	 * 
	 * @param key
	 *            属性名
	 * @param list
	 * @param isdyh是否需要单引号
	 * @return string
	 */
	public static String GetKeyByList(String key, List<?> list, boolean isdyh) {
		try {
			StringBuffer ids = new StringBuffer();
			if (!isNullAndEmpty(list)) {
				for (Object object : list) {
					Field[] fields = object.getClass().getDeclaredFields();
					for (Field field : fields) {
						field.setAccessible(true);
						if (field.getName().equalsIgnoreCase(key)) {
							if (isdyh) {
								ids.append("'");
								ids.append(field.get(object) + "");
								ids.append("',");
							} else {
								ids.append(field.get(object) + ",");
							}
						}
					}
				}
				ids = ids.deleteCharAt(ids.length() - 1);
				return ids.toString();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 将list转换为map，将某个属性设置为Key，value为对象
	 * 
	 * @param list
	 * @return map
	 */
	public static Map<String, Object> ListToMap(String key, List<?> list) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			if (!isNullAndEmpty(list)) {
				for (Object object : list) {
					Field[] fields = object.getClass().getDeclaredFields();
					for (Field field : fields) {
						field.setAccessible(true);
						if (field.getName().equalsIgnoreCase(key)) {
							map.put(field.get(object) + "", object);
						}
					}
				}
				return map;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}

	/**
	 * 将xml字符串转换为Map集合
	 * 
	 * @param str
	 */
	public static Map<String, Object> XmlStringReader(String str) {
		Map<String, Object> map = new HashMap<String, Object>();
		// 创建一个新的字符串
		// 创建一个新的字符串
		StringReader read = new StringReader(str);
		// 创建新的输入源SAX 解析器将使用 InputSource 对象来确定如何读取 XML 输入
		InputSource source = new InputSource(read);
		// 创建一个新的SAXBuilder
		SAXBuilder saxbBuilder = new SAXBuilder();
		try {
			// 通过输入源构造一个Document
			Document doc = saxbBuilder.build(source);
			// 取的根元素
			Element root = doc.getRootElement();
			System.out.println(root.getName());
			List<?> node = root.getChildren();
			for (int i = 0; i < node.size(); i++) {
				Element element = (Element) node.get(i);
				System.out.println(element.getName() + "：" + element.getValue());
				map.put(element.getName(), element.getValue());
			}
		} catch (JDOMException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return map;
	}

	/**
	 * 根据用户身份证查询性别，当前年龄，生日
	 * 
	 * @param idCard
	 * @return
	 */
	public static Object[] getInfoByIDCard(String idCard) {
		Object[] obj = null;
		String sex = null;
		try {
			if (!isNullAndEmpty(idCard)) {
				if (idCard.length() == 18) {// 二代身份证
					obj = new Object[3];
					String str = idCard.substring(16, 17);
					if (Integer.parseInt(str) % 2 == 0) {// 偶数
						sex = "女";
					}
					if (Integer.parseInt(str) % 2 != 0) {// 奇数
						sex = "男";
					}
					obj[0] = sex;// 性别
					String birthday = idCard.substring(6, 14);// 身份证的第7位到第14位是生日

					Integer age = new Date().getYear() - fmyyyyMM.parse(birthday).getYear();
					obj[1] = String.valueOf(age);// 年龄
					String birthdaystr = fmyyyyMM.format(fmyyyyMM.parse(birthday));
					obj[2] = birthdaystr;// 生日
				}
				if (idCard.length() == 15) {// 一代身份证
					obj = new Object[3];
					String str = idCard.substring(14, 15);
					if (Integer.parseInt(str) % 2 == 0) {// 偶数
						sex = "女";
					}
					if (Integer.parseInt(str) % 2 != 0) {// 奇数
						sex = "男";
					}
					obj[0] = sex;
					String birthday = ("19" + idCard.substring(6, 12)).toString();// 身份证的第7位到第12位是生日
					Integer age = new Date().getYear() - fmyyyyMM.parse(birthday).getYear();
					obj[1] = String.valueOf(age);// 年龄
					String birthdaystr = fmyyyyMM.format(fmyyyyMM.parse(birthday));
					obj[2] = birthdaystr;// 生日
				}
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return obj;
	}

	/**
	 * 获取指定时间对应的毫秒数
	 * 
	 * @param time
	 *            "HH:mm:ss"
	 * @return
	 */

	public static long getTimeMillis(String time) {
		try {
			DateFormat dateFormat = new SimpleDateFormat("yy-MM-dd HH:mm:ss");
			DateFormat dayFormat = new SimpleDateFormat("yy-MM-dd");
			Date curDate = dateFormat.parse(dayFormat.format(new Date()) + " " + time);
			return curDate.getTime();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return 0;
	}

	/**
	 * 读取request流
	 * 
	 * @param req
	 * @return
	 * @author guoyx
	 */
	public static String readReqStr(HttpServletRequest request) {
		BufferedReader reader = null;
		StringBuilder sb = new StringBuilder();
		try {
			reader = new BufferedReader(new InputStreamReader(request.getInputStream(), "utf-8"));
			String line = null;

			while ((line = reader.readLine()) != null) {
				sb.append(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (null != reader) {
					reader.close();
				}
			} catch (IOException e) {

			}
		}
		return sb.toString();
	}

	/**
	 * json对象转Map
	 * 
	 * @param jsonObject
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Map<String, Object> JsontoMap(JSONObject jsonObject) {
		Map<String, Object> result = new HashMap<String, Object>();
		Iterator<String> iterator = jsonObject.keys();
		String key = null;
		String value = null;
		while (iterator.hasNext()) {
			key = iterator.next();
			value = jsonObject.getString(key);
			result.put(key, value);
		}
		return result;
	}

	/**
	 * json字符串转Map
	 * 
	 * @param jsonObject
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Map<String, Object> JsonStrtoMap(String jsonStr) {
		JSONObject jsonObject = JSONObject.fromObject(jsonStr);
		Map<String, Object> result = new HashMap<String, Object>();
		Iterator<String> iterator = jsonObject.keys();
		String key = null;
		String value = null;
		while (iterator.hasNext()) {
			key = iterator.next();
			value = jsonObject.getString(key);
			result.put(key, value);
		}
		return result;
	}

	public static Integer converStrToInt(String str, Integer ifNullReturn) {
		Integer returnValue = ifNullReturn;
		try {
			returnValue = Integer.parseInt(str);
		} catch (Exception e) {

		}
		return returnValue;
	}

	public static Long converStrToLong(String str, Long ifNullReturn) {
		Long returnValue = ifNullReturn;
		try {
			returnValue = Long.parseLong(str);
		} catch (Exception e) {

		}
		return returnValue;
	}

	public static Double converStrToDouble(String str, Double ifNullReturn) {
		Double returnValue = ifNullReturn;
		try {
			returnValue = Double.parseDouble(str);
		} catch (Exception e) {

		}
		return returnValue;
	}

	/**
	 * 将map转换为List
	 * 
	 * @param <E>
	 * @param <E>
	 */
	public static <E> List<E> mapToList(Map<E, E> map) {
		List<E> list = new ArrayList<E>();
		try {
			if (!QwyUtil.isNullAndEmpty(map)) {
				// map转换为list
				for (Map.Entry<E, E> entry : map.entrySet()) {
					System.out.println("key= " + entry.getKey() + " and value= " + entry.getValue());
					list.add(entry.getValue());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error("map转换list", e);
		}
		return list;

	}

	/**
	 * 计算时间差(小时) 相同时间为0 1小时以内算1小时
	 * 
	 * @param start
	 *            开始时间
	 * @param end
	 *            结束时间
	 * @return
	 */
	public static Long getHours(Date start, Date end) {
		Long diff = end.getTime() - start.getTime();
		Long days = diff / (1000 * 60 * 60);
		if (start.after(end) || start.getTime() == end.getTime()) {
			return 0L;
		}
		if (diff <= 1000 * 60 * 60) {
			days += 1;
		}
		return days;
	}

}
