package com.zfgt.orm;

/**
 * 借款申请
 * 
 * @author bym
 *
 */
public class ProductApply {

	private String id;// 逻辑id,UUID
	private String contractName;// 联系人姓名
	private String phone;// 电话
	private String address;// 联系地址
	private String sex;// 性别
	private Integer personnelType;// 人员类型 1个人 2团队 3组织
	private Integer productType;// 项目类型 1车贷 2房贷 3创业贷
	private Long applyCentAmount;// 申请金额
	private Integer deadline;// 借款期限 （单位月）

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public Integer getPersonnelType() {
		return personnelType;
	}

	public void setPersonnelType(Integer personnelType) {
		this.personnelType = personnelType;
	}

	public Integer getProductType() {
		return productType;
	}

	public void setProductType(Integer productType) {
		this.productType = productType;
	}

	public Long getApplyCentAmount() {
		return applyCentAmount;
	}

	public void setApplyCentAmount(Long applyCentAmount) {
		this.applyCentAmount = applyCentAmount;
	}

	public Integer getDeadline() {
		return deadline;
	}

	public void setDeadline(Integer deadline) {
		this.deadline = deadline;
	}

}
