package com.zfgt.orm;


import java.util.Date;

/**喵币每天明细表
 * @author 覃文勇
 * @createTime 2015-10-26上午9:34:34
 */
public class MCoinDayDetail {
	// Fields
	
	private Long id;//主键
	private Long coinAdd;//喵币收入
	private Long coinPay;//喵币支出
	private Long leftCoin;//剩余喵币
	private Date insertTime;//插入时间
	private String note;//备注
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getCoinAdd() {
		return coinAdd;
	}
	public void setCoinAdd(Long coinAdd) {
		this.coinAdd = coinAdd;
	}
	public Long getCoinPay() {
		return coinPay;
	}
	public void setCoinPay(Long coinPay) {
		this.coinPay = coinPay;
	}
	public Long getLeftCoin() {
		return leftCoin;
	}
	public void setLeftCoin(Long leftCoin) {
		this.leftCoin = leftCoin;
	}
	public Date getInsertTime() {
		return insertTime;
	}
	public void setInsertTime(Date insertTime) {
		this.insertTime = insertTime;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	
	

	


	
}
