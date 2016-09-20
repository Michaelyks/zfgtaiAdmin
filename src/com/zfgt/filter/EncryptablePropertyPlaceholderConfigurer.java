/**
 * 
 */
package com.zfgt.filter;

import java.util.Properties;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanInitializationException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

import com.zfgt.common.util.DESEncrypt;
import com.zfgt.common.util.QwyUtil;

/**
 * @author 曾礼强
 * 2015年9月2日下午2:40:13
 */
public class EncryptablePropertyPlaceholderConfigurer extends PropertyPlaceholderConfigurer {
	
	    @SuppressWarnings("static-access")
	    @Override
		protected void processProperties(ConfigurableListableBeanFactory beanFactory, Properties props)  
	        throws BeansException {  
	            try {  
	                DESEncrypt des=new DESEncrypt();
	                String jiemi = props.getProperty("jdbc.wiki");
	                String ipPort = props.getProperty("jdbc.ipPort");  
	                String username = props.getProperty("jdbc.username");  
	                String password = props.getProperty("jdbc.password");  
	                String database = props.getProperty("jdbc.database");  
	                
	                if(!QwyUtil.isNullAndEmpty(jiemi) && "0".equals(jiemi)){
	                	//解密;
		                if (ipPort != null) {  
		                    props.setProperty("jdbc.ipPort",des.jieMiProperties(ipPort));  
		                }  
		                if (username != null) {  
		                    props.setProperty("jdbc.username",des.jieMiProperties(username));  
		                }  
		                if (password != null) {  
		                    props.setProperty("jdbc.password", des.jieMiProperties(password));  
		                }  
		                if(database != null){  
		                    props.setProperty("jdbc.database",des.jieMiProperties(database));  
		                }  
	                }else{
	                	 props.setProperty("jdbc.ipPort",ipPort);  
	                	 props.setProperty("jdbc.username",username);  
	                	 props.setProperty("jdbc.password", password);  
	                	 props.setProperty("jdbc.database",database);  
	                }
	                super.processProperties(beanFactory, props);  
	            } catch (Exception e) {  
	                e.printStackTrace();  
	                throw new BeanInitializationException(e.getMessage());  
	            }  
	        }  
}
