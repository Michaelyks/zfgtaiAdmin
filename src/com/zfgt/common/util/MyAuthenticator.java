package com.zfgt.common.util;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

/**
 * @author 曾礼强
 *@date 2015-4-22
 * 
 */
public class MyAuthenticator extends Authenticator {  
    private String username = null;  
    private String password = null;  
  
    public MyAuthenticator() {  
    };  
  
    public MyAuthenticator(String username, String password) {  
        this.username = username;  
        this.password = password;  
    }  
  
    protected PasswordAuthentication getPasswordAuthentication() {  
        return new PasswordAuthentication(username, password);  
    }  
  
		

}
