package com.zfgt.orm;

import java.io.Serializable;

/**
 * 一周不动金额
 * @author Administrator
 *
 */
public class WeekLeftMoney implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String date;//日期
	private String yjczsxf;//预计充值手续费
	private String yjtxsxf;//预计提现手续费
	private String dqhbfxje;//到期付息还本金额
	private String dqfxje;//到期付息金额
	private String allAccountLeftMoney;//账户余额
	private String lqgMoney;//零钱罐总金额
	private String sum;//小计
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getYjczsxf() {
		return yjczsxf;
	}
	public void setYjczsxf(String yjczsxf) {
		this.yjczsxf = yjczsxf;
	}
	public String getYjtxsxf() {
		return yjtxsxf;
	}
	public void setYjtxsxf(String yjtxsxf) {
		this.yjtxsxf = yjtxsxf;
	}
	public String getDqhbfxje() {
		return dqhbfxje;
	}
	public void setDqhbfxje(String dqhbfxje) {
		this.dqhbfxje = dqhbfxje;
	}
	public String getAllAccountLeftMoney() {
		return allAccountLeftMoney;
	}
	public void setAllAccountLeftMoney(String allAccountLeftMoney) {
		this.allAccountLeftMoney = allAccountLeftMoney;
	}
	public String getSum() {
		return sum;
	}
	public void setSum(String sum) {
		this.sum = sum;
	}
	public String getLqgMoney() {
		return lqgMoney;
	}
	public void setLqgMoney(String lqgMoney) {
		this.lqgMoney = lqgMoney;
	}
	public String getDqfxje() {
		return dqfxje;
	}
	public void setDqfxje(String dqfxje) {
		this.dqfxje = dqfxje;
	}
	
}
