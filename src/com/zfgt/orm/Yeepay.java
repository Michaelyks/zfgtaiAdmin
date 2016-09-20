package com.zfgt.orm;
/**
 * 网银支付
 * @author 曾礼强
 * 2015年6月19日 13:46:52
 */
public class Yeepay {
	private String keyValue;//商户密钥
	private String p0_Cmd;//业务类型 固定值“Buy”
	private String p1_MerId;//商户在易宝支付系统的唯一身份标识.获取方式见“如何获得商户编号”
	private String p2_Order;// 商户订单号 若不为””，提交的订单号	必须在自身账户交易中唯	一
	private String p3_Amt;// 支付金额 单位:元，精确到分.必须大	于等于 0.01
	private String p4_Cur;//交易币种 是 Max(10) 固定值 ”CNY”.
	private String p5_Pid;//商品名称 用于支付时显示在易宝支付网关的订单产品信息. 此参数如用到中文，请注意转码.商品名称如果为空,默认显示”商品名称”四个汉字.
	private String p6_Pcat;//商品种类. 此参数如用到中文，请注意转码.
	private String p7_Pdesc;//商品描述 商品描述. 此参数如用到中文，请注意转码
	private String p8_Url;// 商户接收支付成功数据的地址 支付成功后易宝支付会向该地址发送两次成功通	知，该地址可以带参数
	private String p9_SAF;// 送货地址 为“1”: 需要用户将送货地址留在易宝支付系统;为“0”: 不需要，默认为 ”	0”.（新网关去掉）
	private String pa_MP;//商户扩展信息 返回时原样返回，此参数如用到中文，请注意转码.
	private String pd_FrpId;//支付通道编码 
	private String pr_NeedResponse;//应答机制
	private String hmac;//签名数据
	private String yeepayCommonReqURL;//交易请求地址
	public String getKeyValue() {
		return keyValue;
	}
	public void setKeyValue(String keyValue) {
		this.keyValue = keyValue;
	}
	public String getP0_Cmd() {
		return p0_Cmd;
	}
	public void setP0_Cmd(String p0_Cmd) {
		this.p0_Cmd = p0_Cmd;
	}
	public String getP1_MerId() {
		return p1_MerId;
	}
	public void setP1_MerId(String p1_MerId) {
		this.p1_MerId = p1_MerId;
	}
	public String getP2_Order() {
		return p2_Order;
	}
	public void setP2_Order(String p2_Order) {
		this.p2_Order = p2_Order;
	}
	public String getP3_Amt() {
		return p3_Amt;
	}
	public void setP3_Amt(String p3_Amt) {
		this.p3_Amt = p3_Amt;
	}
	public String getP4_Cur() {
		return p4_Cur;
	}
	public void setP4_Cur(String p4_Cur) {
		this.p4_Cur = p4_Cur;
	}
	public String getP5_Pid() {
		return p5_Pid;
	}
	public void setP5_Pid(String p5_Pid) {
		this.p5_Pid = p5_Pid;
	}
	public String getP6_Pcat() {
		return p6_Pcat;
	}
	public void setP6_Pcat(String p6_Pcat) {
		this.p6_Pcat = p6_Pcat;
	}
	public String getP7_Pdesc() {
		return p7_Pdesc;
	}
	public void setP7_Pdesc(String p7_Pdesc) {
		this.p7_Pdesc = p7_Pdesc;
	}
	public String getP8_Url() {
		return p8_Url;
	}
	public void setP8_Url(String p8_Url) {
		this.p8_Url = p8_Url;
	}
	public String getP9_SAF() {
		return p9_SAF;
	}
	public void setP9_SAF(String p9_SAF) {
		this.p9_SAF = p9_SAF;
	}
	public String getPa_MP() {
		return pa_MP;
	}
	public void setPa_MP(String pa_MP) {
		this.pa_MP = pa_MP;
	}
	public String getPd_FrpId() {
		return pd_FrpId;
	}
	public void setPd_FrpId(String pd_FrpId) {
		this.pd_FrpId = pd_FrpId;
	}
	public String getPr_NeedResponse() {
		return pr_NeedResponse;
	}
	public void setPr_NeedResponse(String pr_NeedResponse) {
		this.pr_NeedResponse = pr_NeedResponse;
	}
	public String getHmac() {
		return hmac;
	}
	public void setHmac(String hmac) {
		this.hmac = hmac;
	}
	public String getYeepayCommonReqURL() {
		return yeepayCommonReqURL;
	}
	public void setYeepayCommonReqURL(String yeepayCommonReqURL) {
		this.yeepayCommonReqURL = yeepayCommonReqURL;
	}
	
}
