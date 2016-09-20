package com.zfgt.test.action;



import java.io.IOException;
import java.io.StringReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.xml.sax.InputSource;

import com.zfgt.common.util.QwyUtil;

public class TestMain {
	public static void main(String[] args) {
		Map<String, Object> values=new HashMap<String, Object>();
		values.put("cdkey", "6SDK-EMY-6688-JBSQL");
		values.put("password", "630459");
		values.put("phone", "15090793102");
		values.put("message", "【中泰理财】您注册帐号的验证码是239972(验证码30分钟内有效)");
		values.put("seqid", 10000L);
		values.put("smspriority", 1);
		Map<String, Object>  map=QwyUtil.returnXMLAccessIntentByPost("http://sdk4report.eucp.b2m.cn:8080/sdkproxy/sendsms.action", values);
		String str="<?xml version='1.0' encoding='UTF-8'?><response><error>0</error><message></message></response>";
		//Map<String, Object>  map=QwyUtil.XmlStringReader(str);
		System.out.println(map);
		XmlStringReader(str);		
	}
	 public static void XmlStringReader(String str){
	         //创建一个新的字符串  
	        StringReader read = new StringReader(str);  
	        //创建新的输入源SAX 解析器将使用 InputSource 对象来确定如何读取 XML 输入  
	        InputSource source = new InputSource(read);  
	        //创建一个新的SAXBuilder  
	        SAXBuilder saxbBuilder = new SAXBuilder();  
	        try {  
	            //通过输入源构造一个Document  
	            Document doc = saxbBuilder.build(source);  
	            //取的根元素  
	            Element root = doc.getRootElement();  
	            System.out.println(root.getName());
	            List<?> node = root.getChildren();  
	            for (int i = 0; i < node.size(); i++) {
	                Element element=(Element)node.get(i);
	                System.out.println(element.getName()+"："+ element.getValue());
	            }
	        } catch (JDOMException e) {  
	            e.printStackTrace();  
	        } catch (IOException e) {  
	            e.printStackTrace();  
	        }  
	    }	
}
