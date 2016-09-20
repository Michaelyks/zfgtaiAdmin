package com.zfgt.orm;

/**
 * Bank entity. @author MyEclipse Persistence Tools
 */

public class Bank implements java.io.Serializable {

	// Fields

	private Long id;
	private String bankName;
	private String bankCode;
	private String bankUri;

	// Constructors

	/** default constructor */
	public Bank() {
	}

	/** minimal constructor */
	public Bank(String bankName, String bankCode) {
		this.bankName = bankName;
		this.bankCode = bankCode;
	}

	/** full constructor */
	public Bank(String bankName, String bankCode, String bankUri) {
		this.bankName = bankName;
		this.bankCode = bankCode;
		this.bankUri = bankUri;
	}

	// Property accessors

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getBankName() {
		return this.bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getBankCode() {
		return this.bankCode;
	}

	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}

	public String getBankUri() {
		return this.bankUri;
	}

	public void setBankUri(String bankUri) {
		this.bankUri = bankUri;
	}

}