package com.zfgt.orm;

import java.util.Date;

/**合同表
 * @author 覃文勇
 * @createTime 2015-9-14下午2:45:13
 */
@SuppressWarnings("serial")
public class Contract implements java.io.Serializable {
	private String id;
	
	private String recordNumber ;//合同编号；唯一
	
	private String investorsId;//投资id
	
	private String title;//合同标题
	
	private String productId;//产品id
	
	private String productTitle;//产品标题
	
	private Date startTime;//开始时间
	
	private Date endTime;//结束时间
	
	private Long days;//合同时长，天数
	
	private Date insertTime;//插入时间
	
	private Date updateTime;//修改时间
	
	private Long usersId;//用户id
	
	private String username;//用户名
	
	private String idcard;//身份证号
	
	private String status ;//状态 0 生效；1 失效；2已删除,
	
	private String type;//类型 0 常规产品；1 基金产品,
	
	private Long copies ;//购买份数,
	
	private Double inMoney;//本金,
	
	private Double coupon;//投资券,
	
	private String note;//备注,
	
	private Investors investors;
	
	private Product product;

	private Users users;
	
	//无映射字段
	private String statusChina;//状态中文
	
	private String typeChina;//类型中文
	
	private Double bxze;//本息总额
	
	public Double getBxze(){
		if(investors!=null){
			bxze=investors.getInMoney()+investors.getExpectEarnings();
			return bxze;
		}
		return 0D;
	}

	public String getStatusChina() {
		if("0".equals(status)){
			return "生效";
		}else if("1".equals(status)){
			return "失效";
		}else if("2".equals(status)){
			return "已删除";
		}
		return "其他";
	}


	public String getTypeChina() {
		if("0".equals(type)){
			return "常规产品";
		}else if("1".equals(type)){
			return "基金产品";
		}

		return "其他";
	}



	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getRecordNumber() {
		return recordNumber;
	}

	public void setRecordNumber(String recordNumber) {
		this.recordNumber = recordNumber;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getProductTitle() {
		return productTitle;
	}

	public void setProductTitle(String productTitle) {
		this.productTitle = productTitle;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public Long getDays() {
		return days;
	}

	public void setDays(Long days) {
		this.days = days;
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

	public Long getUsersId() {
		return usersId;
	}

	public void setUsersId(Long usersId) {
		this.usersId = usersId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
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

	public Long getCopies() {
		return copies;
	}

	public void setCopies(Long copies) {
		this.copies = copies;
	}

	public Double getInMoney() {
		return inMoney;
	}

	public void setInMoney(Double inMoney) {
		this.inMoney = inMoney;
	}

	public Double getCoupon() {
		return coupon;
	}

	public void setCoupon(Double coupon) {
		this.coupon = coupon;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}	
	
	public String getInvestorsId() {
		return investorsId;
	}


	public void setInvestorsId(String investorsId) {
		this.investorsId = investorsId;
	}
	

	public String getIdcard() {
		return idcard;
	}


	public void setIdcard(String idcard) {
		this.idcard = idcard;
	}

	public Investors getInvestors() {
		return investors;
	}


	public void setInvestors(Investors investors) {
		this.investors = investors;
	}


	public Users getUsers() {
		return users;
	}


	public void setUsers(Users users) {
		this.users = users;
	}


	public Product getProduct() {
		return product;
	}


	public void setProduct(Product product) {
		this.product = product;
	}


	/** full constructor */
	public Contract(String id,String recordNumber,String title,String productId,
			String productTitle,Date startTime,Date endTime,Long usersId,Long days,
			String username,String status,String type,Long copies,
			Double inMoney,Double coupon,String note,String idcard){
		this.id=id;
		this.recordNumber=recordNumber;
		this.title=title;
		this.productId=productId;
		this.productTitle=productTitle;
		this.startTime=startTime;
		this.endTime=endTime;
		this.usersId=usersId;
		this.idcard=idcard;
		this.days=days;
		this.username=username;
		this.status=status;
		this.type=type;
		this.copies=copies;
		this.inMoney=inMoney;
		this.coupon=coupon;
		this.note=note;
		
	}

	/** default constructor */
	public Contract() {
		// TODO Auto-generated constructor stub
	}
	
}
