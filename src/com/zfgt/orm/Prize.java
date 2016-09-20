package com.zfgt.orm;

import java.io.Serializable;
import java.util.Date;

/**
 * 抽奖奖品
 * @author oi
 *
 */
public class Prize implements Serializable {
	private static final long serialVersionUID = 6650794255614435737L;
	private Long id;
	private String prizeName;
	private Date insertTime;
	private String status;
	private String type;
	private String prizeType;
	private Double prizeValue;
	private Double winningRate;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getPrizeName() {
		return prizeName;
	}
	public void setPrizeName(String prizeName) {
		this.prizeName = prizeName;
	}
	public Date getInsertTime() {
		return insertTime;
	}
	public void setInsertTime(Date insertTime) {
		this.insertTime = insertTime;
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
	public String getPrizeType() {
		return prizeType;
	}
	public void setPrizeType(String prizeType) {
		this.prizeType = prizeType;
	}
	public Double getPrizeValue() {
		return prizeValue;
	}
	public void setPrizeValue(Double prizeValue) {
		this.prizeValue = prizeValue;
	}
	public Double getWinningRate() {
		return winningRate;
	}
	public void setWinningRate(Double winningRate) {
		this.winningRate = winningRate;
	}
	
}
