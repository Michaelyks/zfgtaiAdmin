
package com.zfgt.common.util;

import java.util.Date;
import java.util.Properties;
import javax.mail.Address;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

/**
 * 邮件发送类
 * @author 曾礼强
 *@date 2015-4-22
 * 
 */
@Service
public class EmailUtil {
	private static Logger log = Logger.getLogger(EmailUtil.class); 
	public static boolean sendHtmlMail(MailInfo mailInfo) {       
        //判断是否需要身份认证  
		MyAuthenticator authenticator = null;  
        Properties properties = mailInfo.getProperties();  
        if (mailInfo.isValidate()) {  
            // 如果需要身份认证，则创建一个密码验证器  
            authenticator = new MyAuthenticator(mailInfo.getUsername(), mailInfo.getPassword());  
            System.out.println(mailInfo.getUsername()+""+mailInfo.getPassword());
        }  
  
        // 根据邮件会话属性和密码验证器构造一个发送邮件的session  
        Session sendMailSession = Session.getDefaultInstance(properties, authenticator);  
        try{  
            Message mailMessage = new MimeMessage(sendMailSession);//根据session创建一个邮件消息   
            Address from = new InternetAddress(mailInfo.getFromAddress());//创建邮件发送者地址  
            mailMessage.setFrom(from);//设置邮件消息的发送者  
            Address to = new InternetAddress(mailInfo.getToAddress());//创建邮件的接收者地址  
            mailMessage.setRecipient(Message.RecipientType.TO, to);//设置邮件消息的接收者  
            mailMessage.setSubject(mailInfo.getSubject());//设置邮件消息的主题  
            mailMessage.setSentDate(new Date());//设置邮件消息发送的时间  
              
            //MimeMultipart类是一个容器类，包含MimeBodyPart类型的对象  
            Multipart mainPart = new MimeMultipart();  
            MimeBodyPart messageBodyPart = new MimeBodyPart();//创建一个包含HTML内容的MimeBodyPart  
            //设置HTML内容  
            messageBodyPart.setContent(mailInfo.getContent(),"text/html; charset=utf-8");  
            mainPart.addBodyPart(messageBodyPart);  
            //将MimeMultipart对象设置为邮件内容     
            mailMessage.setContent(mainPart);  
            Transport.send(mailMessage);//发送邮件  
            return true;  
        }catch (Exception e) {  
            log.error("操作异常: ",e); 
            System.out.println("发送失败");
        }  
        return false;  
    }  
  
	 	/*String host = "";
	    String user = "";
	    String password = "";
	    public void setHost(String host) {
	        this.host = host;
	    }
	    public void setAccount(String user, String password) {
	        this.user = user;
	        this.password = password;
	    }
	    public void send(String from, String to, String subject, String content) {
	        Properties props = new Properties();
	        props.put("mail.smtp.host", host); // 指定SMTP服务器
	        props.put("mail.smtp.auth", "true"); // 指定是否需要SMTP验证
	        try {
	        	MailAuthenticator mat=new MailAuthenticator(user, password);
	            Session mailSession = Session.getDefaultInstance(props,mat);
	            Message message = new MimeMessage(mailSession);
	            message.setFrom(new InternetAddress(from)); // 发件人
	            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to)); // 收件人
	            message.setSubject(subject); // 邮件主题
	            //MimeMultipart类是一个容器类，包含MimeBodyPart类型的对象  
	            Multipart mainPart = new MimeMultipart();  
	            MimeBodyPart messageBodyPart = new MimeBodyPart();//创建一个包含HTML内容的MimeBodyPart  
	            //设置HTML内容  
	            messageBodyPart.setContent(mailInfo.getContent(),"text/html; charset=utf-8");  
	            mainPart.addBodyPart(messageBodyPart);  
	            message.setContent(mainPart); 
	           // message.setText(content); // 邮件内容
	            message.saveChanges();
	            Transport transport = mailSession.getTransport("smtp");
	            transport.connect(host, user, password);
	            transport.sendMessage(message, message.getAllRecipients());
	            transport.close();
	        } catch(Exception e) {
	            System.out.println(e);
	            System.out.println("网络异常请重新点击发送");
	        }
	    }*/
	    public static void main(String[] args) {
	    	//EmailUtil sm = new EmailUtil();
	       // sm.setHost("smtp.163.com"); // 指定要使用的邮件服务器
	       // sm.setAccount("15013628196@163.com", "Iloveyou851"); // 指定帐号和密码
	        /*
	         * @param String 发件人的地址
	         * 
	         * @param String 收件人地址
	         * 
	         * @param String 邮件标题
	         * 
	         * @param String 邮件正文
	         */
	
	        //String code= (int)((Math.random()*9+1)*100000)+"";  
	        //String hello="尊敬的用户你好，欢迎来到中泰理财平台，您只需要输入后面的验证码即可绑定邮箱：";
	        //sm.send("15013628196@163.com", "675499778@qq.com", "中泰理财账号激活", hello+code+",输入激活码即可验证账号");
	        //System.out.println("发送成功");
	    	 MailInfo mailInfo = new MailInfo();  
	         mailInfo.setMailServerHost("smtp.163.com");  
	         mailInfo.setMailServerPort("25");  
	         mailInfo.setValidate(true);  
	         mailInfo.setUsername("15013628196@163.com");  
	         mailInfo.setPassword("Iloveyou851");// 您的邮箱密码  
	         mailInfo.setFromAddress("15013628196@163.com");  
	         mailInfo.setToAddress("675499778@qq.com");  
	         mailInfo.setSubject("设置邮箱标题");  
	         StringBuffer demo = new StringBuffer();  
	         demo.append("<!DOCTYPE html PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\" \"http://www.w3.org/TR/html4/loose.dtd\">")  
	        .append("<html>")  
	        .append("<head>")  
	        .append("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">")  
	        .append("<title>测试邮件</title>")  
	        .append("<style type=\"text/css\">")  
	        .append(".test{font-family:\"Microsoft Yahei\";font-size: 18px;color: red;}")  
	        .append("</style>")  
	        .append("</head>")  
	        .append("<body>")  
	        .append("<body>")
	        .append("<a>欢迎来到中泰理财邮箱验证,点击链接激活邮箱:</a><a href='http://www.baidu.com'>http://www.baidu.com</a>")  
	        .append("</body>")  
	        .append("</html>");
	         mailInfo.setContent(demo.toString());
	        EmailUtil.sendHtmlMail(mailInfo);// 发送html格式  
	        System.out.println("发送成功");
	    }  
	    
}
