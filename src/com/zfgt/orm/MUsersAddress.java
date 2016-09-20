package com.zfgt.orm;

import java.util.Date;

/**
 * 用户地址表
 * 
 * @author 覃文勇
 * @createTime 2015-10-26上午9:34:34
 */
public class MUsersAddress {
	// Fields

	private Long id;// 主键
	private Long usersId;// 用户id
	private Date insertTime;// 插入时间
	private Date updateTime;// 修改时间
	private String status;// 状态 0：可用1：不可用（删除）
	private String type;// 类型 0：默认 1： 备用
	private String contractName;// 联系人
	private String phone;// 联系电话
	private String address;// 联系地址
	private String addressDetail;// 地址详情
	private Users users;

	// 无映射
	private int index;// 索引

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getUsersId() {
		return usersId;
	}

	public void setUsersId(Long usersId) {
		this.usersId = usersId;
	}

	public Date getInsertTime() {
		return insertTime;
	}

	public void setInsertTime(Date insertTime) {
		this.insertTime = insertTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getContractName() {
		return contractName;
	}

	public void setContractName(String contractName) {
		this.contractName = contractName;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Users getUsers() {
		return users;
	}

	public void setUsers(Users users) {
		this.users = users;
	}

	public String getAddressDetail() {
		return addressDetail;
	}

	public void setAddressDetail(String addressDetail) {
		this.addressDetail = addressDetail;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

}
