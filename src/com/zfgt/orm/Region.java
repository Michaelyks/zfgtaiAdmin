package com.zfgt.orm;

/**
 * 地区表
 * 无映射实体类
 * @author 覃文勇
 * @createTime 2015-7-23上午9:37:44
 */
public class Region {
	
	private String province;//省份
	
	private String city;//城市
	
	private String usersCount;//人数统计

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getUsersCount() {
		return usersCount;
	}

	public void setUsersCount(String usersCount) {
		this.usersCount = usersCount;
	}
	
	

}
