package com.zfgt.orm;

import java.io.Serializable;

/**
 * 统计平台注册
 */
public class UsersStat implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String registPlatorm;
	private String registPlatormCount;
	public String getRegistPlatorm() {
		return registPlatorm;
	}
	public void setRegistPlatorm(String registPlatorm) {
		this.registPlatorm = registPlatorm;
	}
	public String getRegistPlatormCount() {
		return registPlatormCount;
	}
	public void setRegistPlatormCount(String registPlatormCount) {
		this.registPlatormCount = registPlatormCount;
	}
}
