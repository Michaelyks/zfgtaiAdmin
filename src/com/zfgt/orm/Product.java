package com.zfgt.orm;

import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.Logger;

import com.zfgt.common.util.QwyUtil;
import com.zfgt.login.bean.UsersLoginBean;



/**产品表
 * @author qwy
 *
 * @createTime 2015-04-15 15:28:43
 */
@SuppressWarnings("serial")
public class Product implements java.io.Serializable {
	private static Logger log = Logger.getLogger(Product.class);
	// Fields

	private String id;//逻辑id,UUID
	private String isCleanErr;//结算时是否失败! 0:失败;1:成功;
	private String productImageUrl;//产品图片路径
	private String introduction;//产品宣传 广告标语
	private String recommend;//推荐描述
	private String endType;//结束类型; 到期结束,已售罄;
	private String title;//产品名称
	private String productType;//类别 默认为0; 0为普通项目,1为:新手专享;2:基金产品
	private String productStatus;//产品状态 -2：审核不通过 -1:未审核 0:营销中  1:已售罄  2:结算中   3:已还款 
	private String investType;//投资类别  0:车无忧 1:贸易通;2:牛市通;3房盈宝;4基金产品
	private Long lcqx;//理财期限,以天为单位,项目结束时间-项目开始时间
	private String payInterestWay;//还款方式; 0: 按月付息到期还本 1:到期还本付息  2:按季付息到期还本、3:按年付息到期还本;
	private String calcInterestWay;//计息方式; 0: T+0  1:T+1 
	private Double annualEarnings;//年化收益
	private String isRecommend;//是否推荐项目 1为是推荐项目，0为普通项目;默认为0
	private String leadInvestorId;//领投人id
	private String realName;//发起人真实姓名
	private String idcard;//发起人身份证号
	private String phone;//发起人联系电话
	private String address;//发起人联系地址
	private Long usersId;//创建人id
	private Long financingAmount;//项目总金额
	private Long earnings;//预计收益率
	private String earningsType;//收益类别 10%以内:0 10%-20%:1 20%-30%:2 30%-50%:3  50%以上:4
	private Long userCount;//参加人数
	private String costType;//资金类别 10万以下:0 10-50万:1 50-100万:2 100-500万
	private Long attentionCount;//关注总数
	private Long allCopies;//总份数
	private Long hasCopies;//跟投份数(跟投份数=总份数-剩余份数)
	private Long leftCopies;//剩余份数(当前一元等于1份)
	private Long atleastMoney;//起投金额
	private Long maxBuyNum;//最大购买份数
	private Double profit;//盈利金额
	private Double initialPrice;//初始价格
	private Double nowPrice;//当前价格
	private Double endPrice;//结束价格
	private Double baseEarnings;//基础年化收益（对于基金产品来说是固定收益）
	private String isJiangLi;//是否奖励产品   0 ：否  1： 是
	private Double jiangLiEarnings;//奖励年化收益（ 对于基金产品来说是浮动收益）
	private Double actualEarnings;//实际收益率
	private Double actualEarningsCost;//实际收益金额
	private Double stops;//止损比例
	private Date insertTime;//插入时间
	private Date startTime;//营销中的开始时间 也是融资的时间
	private Date endTime;//购买截至时间
	private Date recommendTime;//推荐时间
	private Date updateTime;//更新时间
	private Date productPassTime;//项目审核通过时间
	private Date clearingTime;//已经进入结算中的时间;普通项目的话:为结算中的时间; 新手专属项目:进入已售罄的时间;
	private Date finishTime;//项目预计结束时间;普通项目的话:为项目预计结束时间; 新手专属项目:预计进入已售罄的时间; 
	private Date backCashTime;//进入已返款状态的时间;
	private Users users;//用户;
	private UsersAdmin usersAdmin;//用户;
	private LeadInvestor leadInvestor;//领投人;
	private String description;//产品描述;
	private Double progress;//项目进度;
	private String infoImg;//信息披露
	private String lawImg;//法律意见书
	private String module;//模块;0:优选理财;1:特色理财
	private String hdby;//活动标语（对于基金产品来说是进度通知）
	private String hdlj;//手机活动连接
	private String hdljWeb;//网站活动连接
	private String hkly;//还款来源（对于基金产品来说是还款保障）
	private String zjbz; //资金保障（对于基金产品来说是资金用途）
	private String cplxjs; //产品类型介绍（对于基金产品来说是项目亮点）
	//基金拓展字段
	private String fundType;//基金类型  0:A类; 1:B类; 2:C类,
	private String fundInvestType;//基金投资类别 0:基金乐,
	private Long closeMonth;//封闭期;1代表一个月;2代表2个月,如此类推
	private String progressStatus;//进度状态
	private String fdsysmUrl;//浮动收益说明链接
	private String fdsysmUrlWeb;//浮动收益说明链接;web
	private String zrgzUrl;//转让规则链接
	private String zrgzUrlWeb;//转让规则链接;web
	private String lcqxNote;//理财期限描述  例   2年：18个月+6个月
	private String fdsyqj;//浮动收益区间（字符串以“，”隔开）
	private String contractName;//合同文件名;
	private String fundQuestions;//基金问答链接
	private String label;// 产品标签（用于移动端显示）
	private String cgcpType;//常规产品类型：普通，爆款，活动

	//临时字段,没有ORM映射
	private Double wcjd;//完成进度;
	private String cplx;//产品类型(中文)
	private String cpzt;//产品状态(中文)
	private String jxfs;//计息方式(中文)
	private String fxfs;//付息方式(中文)
	private int tzqx;//动态更新投资天数;
	private long qtje;//动态的起头金额;
	private Double yflx;//预发利息;
	private String jjlx;//基金类型
	
	
	
	
	public String getContractName() {
		return contractName;
	}
	public void setContractName(String contractName) {
		this.contractName = contractName;
	}
	public static void main(String[] args) {
		try {
			System.out.println(QwyUtil.jinYiFa(12.23, 1));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.error("操作异常: ",e);
		}
	}
	public Double getYflx() {
		try {
			if(!QwyUtil.isNullAndEmpty(allCopies)&&!QwyUtil.isNullAndEmpty(annualEarnings)&&!QwyUtil.isNullAndEmpty(lcqx)){
				Double alllx = QwyUtil.calcNumber(allCopies, annualEarnings*0.01, "*").doubleValue();
				Double everydaylx = QwyUtil.calcNumber(alllx, 365, "/").doubleValue();
				Double yflx = QwyUtil.calcNumber(everydaylx, lcqx, "*").doubleValue();
				yflx = 	QwyUtil.jieQuFa(yflx, 2);
				return yflx;
			}
		} catch (Exception e) {
			log.error("操作异常: ",e);
		}
		return 0.00;
	}

	public long getQtje() {
		try {
			if(QwyUtil.isNullAndEmpty(productStatus)){
				productStatus="-1";
			}
			if("0".equals(productStatus)){
				long newAtleastMoney = QwyUtil.calcNumber(leftCopies, 100, "*").longValue();
				if(newAtleastMoney<atleastMoney.doubleValue())
					return newAtleastMoney;
			}
		} catch (Exception e) {
			log.error("操作异常: ",e);
		}
		return atleastMoney;
	}


	public int getTzqx() {
		if(finishTime==null)
			return 0;
		int days = QwyUtil.getDifferDays(new Date(), finishTime);
		if("1".equals(calcInterestWay)){
			days = days-1;
		}
		return days<=0?0:days;
	}


	public void setTzqx(int tzqx) {
		this.tzqx = tzqx;
	}
    public String getJjlx(){
    	if(!QwyUtil.isNullAndEmpty(fundType)){
    	if("0".equals(fundType)){
    		return "A类";
    	}
    	if("1".equals(fundType)){
			return "B类";
		}
    	if("2".equals(fundType)){
			return "C类";
		}
    	}
    	return fundType;
    }

	public String getCplx() {
		if("0".equals(fundInvestType)){
			return "基金乐";
		}		
		switch(Integer.parseInt(investType)){
		case 0:
			return "车无忧";
		case 1:
			return "贸易通";
		case 2:
			return "牛市通";
		case 3:
			return "房盈宝";
		case 4:
			return "基金";
		default:
			return "其它";
			
		}

	}


	public void setCplx(String cplx) {
		this.cplx = cplx;
	}


	public String getCpzt() {
		if(QwyUtil.isNullAndEmpty(productStatus)){
			productStatus="-1";
		}
		switch(Integer.parseInt(productStatus)){
		case -2:
			return "审核未通过";
		case -1:
			return "待审核";
		case 0:
			return "营销中";
		case 1:
			return "已售罄";
		case 2:
			return "结算中";
		case 3:
			return "已还款";
		default:
			return "其它";
			
		}
	}


	public void setCpzt(String cpzt) {
		this.cpzt = cpzt;
	}


	public String getJxfs() {
		switch(Integer.parseInt(calcInterestWay)){
		case 0:
			return "T(成交日) + 0";
		case 1:
			return "T(成交日) + 1";
		default:
			return "其它";
			
		}
	}


	public void setJxfs(String jxfs) {
		this.jxfs = jxfs;
	}


	public String getFxfs() {
		switch(Integer.parseInt(payInterestWay)){
		case 0:
			return "按月付息到期还本";
		case 1:
			return "到期还本付息";
		case 2:
			return "按季付息到期还本";
		case 3:
			return "按年付息到期还本";
		default:
			return "其它";
			
		}
	}


	public void setFxfs(String fxfs) {
		this.fxfs = fxfs;
	}


	public Double getProgress() {
		return progress;
	}

	public void setProgress(Double progress) {
		this.progress = progress;
	}

	public Double getWcjd() {
		double wcjd = 0;
		try {
			double jd = QwyUtil.calcNumber(this.hasCopies, this.allCopies, "/").doubleValue();
			long onePercent = QwyUtil.calcNumber(this.allCopies, 0.01, "*").longValue();
			if(0<this.hasCopies && this.hasCopies<onePercent){
				jd = 0.01;
			}
			wcjd = QwyUtil.jieQuFa(jd, 2);
			if(productStatus.equals("3") || productStatus.equals("1"))
				wcjd = 1;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.error("操作异常: ",e);
		}
		
		return wcjd;
	}

	public void setWcjd(Double wcjd) {
		this.wcjd = wcjd;
	}

	public Users getUsers() {
		return users;
	}

	public void setUsers(Users users) {
		this.users = users;
	}

	public LeadInvestor getLeadInvestor() {
		return leadInvestor;
	}

	public void setLeadInvestor(LeadInvestor leadInvestor) {
		this.leadInvestor = leadInvestor;
	}

	/** default constructor */
	public Product() {
	}

	/** minimal constructor */
	public Product(String payInterestWay, String calcInterestWay,
			Long allCopies, Long hasCopies, Long leftCopies, Long atleastMoney,
			Date insertTime) {
		this.payInterestWay = payInterestWay;
		this.calcInterestWay = calcInterestWay;
		this.allCopies = allCopies;
		this.hasCopies = hasCopies;
		this.leftCopies = leftCopies;
		this.atleastMoney = atleastMoney;
		this.insertTime = insertTime;
	}

	/** full constructor */
	public Product(String isCleanErr, String productImageUrl,
			String realName,String phone,String address,String idcard,
			String introduction, String recommend, String endType,
			String title, String productType, String productStatus,
			String investType, Long lcqx, String payInterestWay,
			String calcInterestWay, Double annualEarnings, String isRecommend,
			String leadInvestorId, Long usersId, Long financingAmount,
			Long earnings, String earningsType, Long userCount,
			String costType, Long attentionCount, Long allCopies,
			Long hasCopies, Long leftCopies, Long atleastMoney, Long maxBuyNum,
			Double profit, Double initialPrice, Double nowPrice,
			Double endPrice, Double actualEarnings, Double actualEarningsCost,
			Double stops, Date insertTime, Date startTime,
			Date endTime, Date recommendTime, Date updateTime,
			Date productPassTime, Date clearingTime, Date finishTime,
			String hdby,String hdlj,String hdljWeb,String hkly,String zjbz,String cplxjs,
			Double baseEarnings, String isJiangLi,Double jiangLiEarnings,
			String progressStatus,String lcqxNote,String fdsyqj,
			String 	contractName,String fundQuestions,String label,String cgcpType) {
		this.isCleanErr = isCleanErr;
		this.productImageUrl = productImageUrl;
		this.realName=realName;
		this.phone=phone;
		this.address=address;
		this.idcard=idcard;
		this.introduction = introduction;
		this.recommend = recommend;
		this.endType = endType;
		this.title = title;
		this.productType = productType;
		this.productStatus = productStatus;
		this.investType = investType;
		this.lcqx = lcqx;
		this.payInterestWay = payInterestWay;
		this.calcInterestWay = calcInterestWay;
		this.annualEarnings = annualEarnings;
		this.isRecommend = isRecommend;
		this.leadInvestorId = leadInvestorId;
		this.usersId = usersId;
		this.financingAmount = financingAmount;
		this.earnings = earnings;
		this.earningsType = earningsType;
		this.userCount = userCount;
		this.costType = costType;
		this.attentionCount = attentionCount;
		this.allCopies = allCopies;
		this.hasCopies = hasCopies;
		this.leftCopies = leftCopies;
		this.atleastMoney = atleastMoney;
		this.maxBuyNum = maxBuyNum;
		this.profit = profit;
		this.initialPrice = initialPrice;
		this.nowPrice = nowPrice;
		this.endPrice = endPrice;
		this.actualEarnings = actualEarnings;
		this.actualEarningsCost = actualEarningsCost;
		this.stops = stops;
		this.insertTime = insertTime;
		this.startTime = startTime;
		this.endTime = endTime;
		this.recommendTime = recommendTime;
		this.updateTime = updateTime;
		this.productPassTime = productPassTime;
		this.clearingTime = clearingTime;
		this.finishTime = finishTime;
		this.hdby=hdby;
		this.hdlj=hdlj;
		this.hdljWeb=hdljWeb;
		this.hkly=hkly;
		this.zjbz=zjbz; 
		this.cplxjs=cplxjs;
		this.baseEarnings=baseEarnings;
		this.isJiangLi=isJiangLi;
		this.jiangLiEarnings=jiangLiEarnings;
		this.progressStatus=progressStatus;
		this.lcqxNote=lcqxNote;
		this.fdsyqj=fdsyqj;
		this.contractName=contractName;
		this.fundQuestions=fundQuestions;
		this.label=label;
		this.cgcpType=cgcpType;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getRealName() {
		return realName;
	}
	public void setRealName(String realName) {
		this.realName = realName;
	}
	public String getIdcard() {
		return idcard;
	}
	public void setIdcard(String idcard) {
		this.idcard = idcard;
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
	public String getIsCleanErr() {
		return this.isCleanErr;
	}

	public void setIsCleanErr(String isCleanErr) {
		this.isCleanErr = isCleanErr;
	}

	public String getProductImageUrl() {
		return this.productImageUrl;
	}

	public void setProductImageUrl(String productImageUrl) {
		this.productImageUrl = productImageUrl;
	}

	public String getIntroduction() {
		return this.introduction;
	}

	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}

	public String getRecommend() {
		return this.recommend;
	}

	public void setRecommend(String recommend) {
		this.recommend = recommend;
	}

	public String getEndType() {
		return this.endType;
	}

	public void setEndType(String endType) {
		this.endType = endType;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getProductType() {
		return this.productType;
	}

	public void setProductType(String productType) {
		this.productType = productType;
	}

	public String getProductStatus() {
		return this.productStatus;
	}

	public void setProductStatus(String productStatus) {
		this.productStatus = productStatus;
	}

	public String getInvestType() {
		return this.investType;
	}

	public void setInvestType(String investType) {
		this.investType = investType;
	}

	public Long getLcqx() {
		return this.lcqx;
	}

	public void setLcqx(Long lcqx) {
		this.lcqx = lcqx;
	}

	public String getPayInterestWay() {
		return this.payInterestWay;
	}

	public void setPayInterestWay(String payInterestWay) {
		this.payInterestWay = payInterestWay;
	}

	public String getCalcInterestWay() {
		return this.calcInterestWay;
	}

	public void setCalcInterestWay(String calcInterestWay) {
		this.calcInterestWay = calcInterestWay;
	}

	public Double getAnnualEarnings() {
		return this.annualEarnings;
	}

	public void setAnnualEarnings(Double annualEarnings) {
		this.annualEarnings = annualEarnings;
	}

	public String getIsRecommend() {
		return this.isRecommend;
	}

	public void setIsRecommend(String isRecommend) {
		this.isRecommend = isRecommend;
	}

	public String getLeadInvestorId() {
		return this.leadInvestorId;
	}

	public void setLeadInvestorId(String leadInvestorId) {
		this.leadInvestorId = leadInvestorId;
	}

	public Long getUsersId() {
		return this.usersId;
	}

	public void setUsersId(Long usersId) {
		this.usersId = usersId;
	}

	public Long getFinancingAmount() {
		return this.financingAmount;
	}

	public void setFinancingAmount(Long financingAmount) {
		this.financingAmount = financingAmount;
	}

	public Long getEarnings() {
		return this.earnings;
	}

	public void setEarnings(Long earnings) {
		this.earnings = earnings;
	}

	public String getEarningsType() {
		return this.earningsType;
	}

	public void setEarningsType(String earningsType) {
		this.earningsType = earningsType;
	}

	public Long getUserCount() {
		return this.userCount;
	}

	public void setUserCount(Long userCount) {
		this.userCount = userCount;
	}

	public String getCostType() {
		return this.costType;
	}

	public void setCostType(String costType) {
		this.costType = costType;
	}

	public Long getAttentionCount() {
		return this.attentionCount;
	}

	public void setAttentionCount(Long attentionCount) {
		this.attentionCount = attentionCount;
	}

	public Long getAllCopies() {
		return this.allCopies;
	}

	public void setAllCopies(Long allCopies) {
		this.allCopies = allCopies;
	}

	public Long getHasCopies() {
		return this.hasCopies;
	}

	public void setHasCopies(Long hasCopies) {
		this.hasCopies = hasCopies;
	}

	public Long getLeftCopies() {
		return this.leftCopies;
	}

	public void setLeftCopies(Long leftCopies) {
		this.leftCopies = leftCopies;
	}

	public Long getAtleastMoney() {
		return this.atleastMoney;
	}

	public void setAtleastMoney(Long atleastMoney) {
		this.atleastMoney = atleastMoney;
	}

	public Long getMaxBuyNum() {
		return this.maxBuyNum;
	}

	public void setMaxBuyNum(Long maxBuyNum) {
		this.maxBuyNum = maxBuyNum;
	}

	public Double getProfit() {
		return this.profit;
	}

	public void setProfit(Double profit) {
		this.profit = profit;
	}

	public Double getInitialPrice() {
		return this.initialPrice;
	}

	public void setInitialPrice(Double initialPrice) {
		this.initialPrice = initialPrice;
	}

	public Double getNowPrice() {
		return this.nowPrice;
	}

	public void setNowPrice(Double nowPrice) {
		this.nowPrice = nowPrice;
	}

	public Double getEndPrice() {
		return this.endPrice;
	}

	public void setEndPrice(Double endPrice) {
		this.endPrice = endPrice;
	}

	public Double getActualEarnings() {
		return this.actualEarnings;
	}

	public void setActualEarnings(Double actualEarnings) {
		this.actualEarnings = actualEarnings;
	}

	public Double getActualEarningsCost() {
		return this.actualEarningsCost;
	}

	public void setActualEarningsCost(Double actualEarningsCost) {
		this.actualEarningsCost = actualEarningsCost;
	}

	public Double getStops() {
		return this.stops;
	}

	public void setStops(Double stops) {
		this.stops = stops;
	}

	public Date getInsertTime() {
		return this.insertTime;
	}

	public void setInsertTime(Date insertTime) {
		this.insertTime = insertTime;
	}

	public Date getStartTime() {
		return this.startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return this.endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public Date getRecommendTime() {
		return this.recommendTime;
	}

	public void setRecommendTime(Date recommendTime) {
		this.recommendTime = recommendTime;
	}

	public Date getUpdateTime() {
		return this.updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Date getProductPassTime() {
		return this.productPassTime;
	}

	public void setProductPassTime(Date productPassTime) {
		this.productPassTime = productPassTime;
	}

	public Date getClearingTime() {
		return this.clearingTime;
	}

	public void setClearingTime(Date clearingTime) {
		this.clearingTime = clearingTime;
	}

	public Date getFinishTime() {
		return finishTime;
	}

	public void setFinishTime(Date finishTime) {
		this.finishTime = finishTime;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}


	public Date getBackCashTime() {
		return backCashTime;
	}


	public void setBackCashTime(Date backCashTime) {
		this.backCashTime = backCashTime;
	}


	public String getInfoImg() {
		return infoImg;
	}


	public void setInfoImg(String infoImg) {
		this.infoImg = infoImg;
	}


	public String getLawImg() {
		return lawImg;
	}


	public void setLawImg(String lawImg) {
		this.lawImg = lawImg;
	}


	public UsersAdmin getUsersAdmin() {
		return usersAdmin;
	}


	public void setUsersAdmin(UsersAdmin usersAdmin) {
		this.usersAdmin = usersAdmin;
	}


	public String getModule() {
		return module;
	}


	public void setModule(String module) {
		this.module = module;
	}
	public Double getBaseEarnings() {
		return baseEarnings;
	}
	public void setBaseEarnings(Double baseEarnings) {
		this.baseEarnings = baseEarnings;
	}
	public Double getJiangLiEarnings() {
		return jiangLiEarnings;
	}
	public void setJiangLiEarnings(Double jiangLiEarnings) {
		this.jiangLiEarnings = jiangLiEarnings;
	}
	public String getHdby() {
		return hdby;
	}
	public void setHdby(String hdby) {
		this.hdby = hdby;
	}
	public String getHdlj() {
		return hdlj;
	}
	public void setHdlj(String hdlj) {
		this.hdlj = hdlj;
	}
	public String getHkly() {
		return hkly;
	}
	public void setHkly(String hkly) {
		this.hkly = hkly;
	}
	public String getZjbz() {
		return zjbz;
	}
	public void setZjbz(String zjbz) {
		this.zjbz = zjbz;
	}
	public String getCplxjs() {
		return cplxjs;
	}
	public void setCplxjs(String cplxjs) {
		this.cplxjs = cplxjs;
	}
	public String getIsJiangLi() {
		return isJiangLi;
	}
	public void setIsJiangLi(String isJiangLi) {
		this.isJiangLi = isJiangLi;
	}
	public String getHdljWeb() {
		return hdljWeb;
	}
	public void setHdljWeb(String hdljWeb) {
		this.hdljWeb = hdljWeb;
	}
	public String getFundType() {
		return fundType;
	}
	public void setFundType(String fundType) {
		this.fundType = fundType;
	}
	public String getFundInvestType() {
		return fundInvestType;
	}
	public void setFundInvestType(String fundInvestType) {
		this.fundInvestType = fundInvestType;
	}
	public Long getCloseMonth() {
		return closeMonth;
	}
	public void setCloseMonth(Long closeMonth) {
		this.closeMonth = closeMonth;
	}
	public String getFdsysmUrl() {
		return fdsysmUrl;
	}
	public void setFdsysmUrl(String fdsysmUrl) {
		this.fdsysmUrl = fdsysmUrl;
	}
	public String getFdsysmUrlWeb() {
		return fdsysmUrlWeb;
	}
	public void setFdsysmUrlWeb(String fdsysmUrlWeb) {
		this.fdsysmUrlWeb = fdsysmUrlWeb;
	}
	public String getZrgzUrl() {
		return zrgzUrl;
	}
	public void setZrgzUrl(String zrgzUrl) {
		this.zrgzUrl = zrgzUrl;
	}
	public String getZrgzUrlWeb() {
		return zrgzUrlWeb;
	}
	public void setZrgzUrlWeb(String zrgzUrlWeb) {
		this.zrgzUrlWeb = zrgzUrlWeb;
	}
	public String getProgressStatus() {
		return progressStatus;
	}
	public void setProgressStatus(String progressStatus) {
		this.progressStatus = progressStatus;
	}
	public String getLcqxNote() {
		return lcqxNote;
	}
	public void setLcqxNote(String lcqxNote) {
		this.lcqxNote = lcqxNote;
	}
	public String getFdsyqj() {
		return fdsyqj;
	}
	public void setFdsyqj(String fdsyqj) {
		this.fdsyqj = fdsyqj;
	}
	public String getFundQuestions() {
		return fundQuestions;
	}
	public void setFundQuestions(String fundQuestions) {
		this.fundQuestions = fundQuestions;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public String getCgcpType() {
		return cgcpType;
	}
	public void setCgcpType(String cgcpType) {
		this.cgcpType = cgcpType;
	}

	
}