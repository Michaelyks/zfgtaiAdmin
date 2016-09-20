package com.zfgt.orm;

import java.util.Date;
//用户拆红包金额
public class RedPackets {
		private Long id;
		private Long invitesId;//推荐人Id
		private String phone;//拆红包的手机号码
		private Double inMoney;//金额拆红包的金额
		private Date insertTime;//拆红包时间
		private Date updateTime;//更新时间
		private String isHongBao; //是否发放拆红包 0:未发放 1:已发放
		private String isInvestors; //是否发放投资金额 0:未发放 1:已发放
		private String status;// 状态;0:成功;1:失败;
		private String note;//备注
		public Long getId() {
			return id;
		}
		public void setId(Long id) {
			this.id = id;
		}
		public Long getInvitesId() {
			return invitesId;
		}
		public void setInvitesId(Long invitesId) {
			this.invitesId = invitesId;
		}
		public String getPhone() {
			return phone;
		}
		public void setPhone(String phone) {
			this.phone = phone;
		}
		public Double getInMoney() {
			return inMoney;
		}
		public void setInMoney(Double inMoney) {
			this.inMoney = inMoney;
		}
		public Date getInsertTime() {
			return insertTime;
		}
		public void setInsertTime(Date insertTime) {
			this.insertTime = insertTime;
		}
		public String getIsHongBao() {
			return isHongBao;
		}
		public void setIsHongBao(String isHongBao) {
			this.isHongBao = isHongBao;
		}
		public String getIsInvestors() {
			return isInvestors;
		}
		public void setIsInvestors(String isInvestors) {
			this.isInvestors = isInvestors;
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
		public String getNote() {
			return note;
		}
		public void setNote(String note) {
			this.note = note;
		}
		

}
