package com.zfgt.orm;

import java.io.Serializable;

public class PlatformInvestDetail implements Serializable{
	
	private String registPlatform;
	private String platformInvest;
	public String getRegistPlatform() {
		if("0".equals(registPlatform)){
			//新手产品,的预到期时间;
			return "web端注册";
		}else if("1".equals(registPlatform)){
			//新手产品,的预到期时间;
			return "Android移动端";
		}else if("2".equals(registPlatform)){
			//新手产品,的预到期时间;
			return "IOS移动端";
		}else if("3".equals(registPlatform)){
			//新手产品,的预到期时间;
			return "微信注册";
		}else{
			return registPlatform;
		}
	}
	public void setRegistPlatform(String registPlatform) {
		this.registPlatform = registPlatform;
	}
	public String getPlatformInvest() {
		return platformInvest;
	}
	public void setPlatformInvest(String platformInvest) {
		this.platformInvest = platformInvest;
	}
	
	
		
}
