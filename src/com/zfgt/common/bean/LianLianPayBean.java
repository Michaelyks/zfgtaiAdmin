package com.zfgt.common.bean;

import java.util.Date;


import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.zfgt.common.lianlian.pay.utils.EnvConstants;
import com.zfgt.common.lianlian.pay.utils.LLPayUtil;
import com.zfgt.common.lianlian.pay.utils.PayOrder;
import com.zfgt.common.lianlian.pay.utils.conn.HttpRequestSimple;
import com.zfgt.common.util.QwyUtil;


/**连连支付的Bean层（充值提现）
 * @author 覃文勇
 * @createTime 2015-11-30下午5:05:43
 */
@Service
public class LianLianPayBean {
	private static Logger log = Logger.getLogger(LianLianPayBean.class); 

	/**
	 * 连连支付结果查询接口
	 * @param no_order 平台订单号
	 * @param dt_order 订单生成时间
	 * @param type_dc 收付标识(0:充值  1：提现)
	 * @return
	 * @throws Exception
	 */
	public String queryPay(String no_order,String dt_order,String type_dc) throws Exception{
		log.info("连连支付结果查询接口,订单号: " + no_order);
		
		if(QwyUtil.isNullAndEmpty(no_order)||QwyUtil.isNullAndEmpty(dt_order)){
			return paramException();
		}
		JSONObject json =new JSONObject();
        //商户编号
		json.put("oid_partner", EnvConstants.PARTNER);
        //商户唯一订单号
		json.put("query_version", "1.1");
		json.put("no_order", no_order);
        //商户订单时间
		json.put("dt_order", dt_order);
        //收付标识(0:充值  1：提现)
		json.put("type_dc", type_dc);
        //签名方式
		json.put("sign_type",PayOrder.SIGN_TYPE_MD5);
        //生成签名(MD5)
        String sign=LLPayUtil.addSign(json, null, EnvConstants.MD5_KEY);        
        json.put("sign", sign);
        log.info("连连支付结果查询接口请求报文[" + json.toString() + "]");
//        String resJson=HttpRequestSimple.getInstance().postSendHttp(EnvConstants.ORDER_QUERY_URL, json.toString());
//        log.info("连连支付结果查询接口返回数据[" + resJson + "]");
        String resJson=HttpRequestSimple.getInstance().postSendHttp(EnvConstants.ORDER_QUERY_URL, json.toString());
        log.info("连连支付结果查询接口返回数据[" + resJson + "]");
		return resJson;
	}
/**
 * 生成充值报文（json格式）	
 * @param no_order
 * @param users_id
 * @param dt_order
 * @param name_goods
 * @param money_order
 * @param returnUrl
 * @param notifyUrl
 * @param pay_type
 * @param id_no
 * @param acct_name
 * @param card_no
 * @param phone
 * @param register_time
 * @return
 * @throws Exception
 */
	public String authPay(String no_order,Long users_id,String dt_order,String name_goods,
			Double money_order,String returnUrl,String notifyUrl,String pay_type,String id_no,
			String acct_name,String card_no,String phone,String register_time,String bank_code,
			String userip) throws Exception{
		log.info("连连支付充值接口,订单号: " + no_order);
		
		if(QwyUtil.isNullAndEmpty(no_order)||QwyUtil.isNullAndEmpty(dt_order)){
			return null;
		}
		PayOrder payOrder=new PayOrder();
		//版本号
		payOrder.setVersion("1.0");
        //商户编号
		payOrder.setOid_partner(EnvConstants.PARTNER);
		//商户用户唯一编号
		payOrder.setUser_id(users_id+"");
		String timestamp=QwyUtil.fmyyyyMMddHHmmss2.format(new Date());
		//时间戳
		payOrder.setTimestamp(timestamp);
        //签名方式
		payOrder.setSign_type(PayOrder.SIGN_TYPE_MD5);
		//商户业务类型	
		payOrder.setBusi_partner("101001");
        //商户唯一订单号
		payOrder.setNo_order(no_order);
        //商户订单时间
		payOrder.setDt_order(dt_order);
		//商品名称
		payOrder.setName_goods(name_goods);
		//交易金额
		payOrder.setMoney_order(money_order+"");
		//服务器同步通知地址
		payOrder.setUrl_return(returnUrl);
		//服务器异步通知地址
		payOrder.setNotify_url(notifyUrl);
		//支付方式
		payOrder.setPay_type(pay_type);
		//2009:P2P 小额贷款
		String risk_item=constructRiskItem(users_id, phone, register_time, acct_name, id_no);
		//风险控制参数
		payOrder.setRisk_item(risk_item);
		//证件类型(0:身份证)
		payOrder.setId_type("0");
		//证件号码
		payOrder.setId_no(id_no);
		//银行账号姓名
		payOrder.setAcct_name(acct_name);
		//银行卡号
		payOrder.setCard_no(card_no);
		//订单有效期(分钟为单位)
		payOrder.setValid_order("10080");
		//银行编号
		payOrder.setBank_code(bank_code);
		//订单信息
		payOrder.setInfo_order(name_goods);
		//
		payOrder.setUserreq_ip(userip);
        JSONObject json=JSONObject.fromObject(payOrder);
        //生成签名(MD5)
        String sign=LLPayUtil.addSign(json, null, EnvConstants.MD5_KEY);        
        payOrder.setSign(sign);
        //请求地址
        payOrder.setAuth_pay_url(EnvConstants.AUTH_PAY_URL);

		JSONObject reqJson=JSONObject.fromObject(payOrder);
		return reqJson.toString();
	}
	
	/**
	 * 风控参数
	 * @param usersId 用户id
	 * @param username 用户名
	 * @param regTime 用户在平台的注册时间
	 * @param accountName 银行卡绑定姓名
	 * @param idcard 身份证号
	 * @return
	 */
	private String constructRiskItem(Long usersId,String username,String regTime, String accountName, String idcard) {
		
		JSONObject mRiskItem = new JSONObject();
        try {//字段顺序必须一致，否则连连解析不成功
            mRiskItem.put("frms_ware_category", "2009");
            mRiskItem.put("user_info_bind_phone", username);
            mRiskItem.put("user_info_dt_register", regTime);
            mRiskItem.put("user_info_full_name",accountName);
            mRiskItem.put("user_info_id_no",idcard);
            mRiskItem.put("user_info_identify_state","0");
            mRiskItem.put("user_info_identify_type","1");
            mRiskItem.put("user_info_mercht_userno",usersId+"");



        } catch (Exception e) {
            e.printStackTrace();
            log.error("异常消息", e);
        }

        return mRiskItem.toString();
    }
	
	/**
     * 查询签约卡信息
     * @param req
     * @return
     */
    public String userBankCard(Long usersId)
    {
        JSONObject reqObj = new JSONObject();
        reqObj.put("oid_partner", EnvConstants.PARTNER);
        reqObj.put("user_id", usersId);
        reqObj.put("pay_type", "D");
        reqObj.put("sign_type", PayOrder.SIGN_TYPE_MD5);
        reqObj.put("offset", "0");
        String sign = LLPayUtil.addSign(reqObj, null,
        		EnvConstants.MD5_KEY);
        reqObj.put("sign", sign);
        String reqJSON = reqObj.toString();
        System.out.println("用户签约信息查询 API请求报文[" + reqJSON + "]");
        log.info("用户签约信息查询 API请求报文[" + reqJSON + "]");
        String resJSON = HttpRequestSimple.getInstance().postSendHttp(EnvConstants.QUERY_USERBANKCARD_URL, reqJSON);
        System.out.println("用户签约信息查询 API响应报文[" + resJSON + "]");
        log.info("用户签约信息查询 API响应报文[" + resJSON + "]");
        return resJSON;
    }
	
	/**
     * 银行卡卡bin信息查询
     * @param req
     * @return
     */
    public String queryCardBin(String cardNo)
    {
        JSONObject reqObj = new JSONObject();
        reqObj.put("oid_partner", EnvConstants.PARTNER);
        reqObj.put("card_no", cardNo);
        reqObj.put("sign_type", PayOrder.SIGN_TYPE_MD5);
        String sign = LLPayUtil.addSign(reqObj, null,EnvConstants.MD5_KEY);
        reqObj.put("sign", sign);
        String reqJSON = reqObj.toString();
        System.out.println("银行卡卡bin信息查询请求报文[" + reqJSON + "]");
        log.info("银行卡卡bin信息查询请求报文[" + reqJSON + "]");
        String resJSON = HttpRequestSimple.getInstance().postSendHttp(EnvConstants.QUERY_BANKCARD_URL, reqJSON);
        System.out.println("银行卡卡bin信息查询响应报文[" + resJSON + "]");
        log.info("银行卡卡bin信息查询响应报文[" + resJSON + "]");
        return resJSON;
    }
    
	public String paramException(){
		return "{\"ret_msg\":\"参数错误\",\"ret_code\":\"error\"}";
	}
	

}
