package com.zfgt.test.bean;

public class LoginTest {
public static void main(String[] args) {
	String abc="";
	boolean result=abc.matches("^[a-zA-Z0-9_]{1,}$");
	System.out.println(result);
}
}
