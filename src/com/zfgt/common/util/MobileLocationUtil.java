/**
 * 
 */
package com.zfgt.common.util;

import java.util.HashMap;
import java.util.Map;

import javax.swing.text.html.HTML;

import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import com.zfgt.login.bean.RegisterUserBean;

/**
 * 获取手机号码归属地
 * @author 曾礼强
 * 2015年7月22日下午4:11:26
 */
public class MobileLocationUtil {
	private static Logger log = Logger.getLogger(RegisterUserBean.class);
	public static Map<String,Object> getMobileLocation(String mobile){
		Map<String,Object> map=new HashMap<String, Object>();
		try {
			if(!QwyUtil.isNullAndEmpty(mobile)){
				String url="http://www.ip138.com:8080/search.asp?action=mobile&mobile="+mobile;
				Document document=Jsoup.connect(url).get();
				Elements elements=document.getElementsByClass("tdc2");
				String location=elements.get(1).html() ;
				location = location.replaceAll("&nbsp;", "@");
				if(!location.contains("手机号有误")){
					String [] str=location.split("@");
					Document doc = Jsoup.parseBodyFragment(str[0]);
					Elements el=doc.getElementsByTag("body");
					if(str.length>1){
						map.put("province",el.get(0).text());
						map.put("city",str[1]);
					}else{
						map.put("province",el.get(0).text());
						map.put("city",el.get(0).text());
					}
					String type=elements.get(2).text();
					String cardType="";
					if(type.contains("移动"))  
						cardType="移动";
					else if(type.contains("联通"))
						cardType="联通";
					else if(type.contains("电信"))
						cardType="电信";
					else
						cardType="未知";
					map.put("cardType",cardType);
				}else{
					map.put("province","未知");
					map.put("city","未知");
					map.put("cardType","未知");
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			log.error(e.getMessage(), e);
		}
		return map;
	}
	
	public static void main(String[] args) {
		System.out.println(MobileLocationUtil.getMobileLocation("15090793102"));
	}
}