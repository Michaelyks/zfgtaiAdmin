/**
 * 
 */
package com.zfgt.account.bean;

import java.util.Date;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.zfgt.account.dao.MyAccountDAO;
import com.zfgt.account.dao.MyObjcetDAO;
import com.zfgt.common.util.DESEncrypt;
import com.zfgt.common.util.QwyUtil;
import com.zfgt.orm.InsSucSmsRecord;
import com.zfgt.orm.Investors;
import com.zfgt.orm.Product;
import com.zfgt.orm.SmsRecord;
import com.zfgt.orm.UsersInfo;

/**
 * @author 曾礼强
 * 2016年3月11日下午4:58:37
 */
@Service
//投资成功的短信
public class InsSucSmsRecordBean {
	private static Logger log = Logger.getLogger(InsSucSmsRecordBean.class); 

	@Resource
	private MyObjcetDAO dao;
	/**
	 * 获取投资成功短信记录
	 * @param smsContent
	 * @param mobile
	 * @return
	 */
	public InsSucSmsRecord packInsSucSmsRecord(String smsContent,String mobile){
		InsSucSmsRecord smsRecord=new InsSucSmsRecord();
		smsRecord.setInsertTime(new Date());
		smsRecord.setMobile(mobile);
		smsRecord.setSmsContent(smsContent);
		smsRecord.setStatus("0");
		smsRecord.setNote("投资成功短信");
		dao.save(smsRecord);
		if(!QwyUtil.isNullAndEmpty(smsRecord.getId())){
			return (InsSucSmsRecord) dao.findById(new InsSucSmsRecord(), smsRecord.getId());
		}
		return smsRecord;
	}
	
	/**
	 * 发送短信
	 * @param usersInfo 账户信息
	 * @param pro 产品信息
	 * @param inv 投资记录
	 * @param inMoney 投资金额
	 * @param coupon 投资券
	 * @return
	 */
		public boolean sendIncSucSMS(UsersInfo usersInfo,Product pro,Investors inv,Double inMoney,Double coupon){
			 try {
				 if (!QwyUtil.isNullAndEmpty(usersInfo.getPhone())
							&& !QwyUtil.isNullAndEmpty(usersInfo.getSex()) && !QwyUtil
								.isNullAndEmpty(usersInfo.getRealName())) {
					//【中泰理财理财】尊敬的XX先生士，您于2016-02-29 15:30:30投资的贸易通NO.001已生效，其中投资本金900元，投资券100元，预计到期时间为2016-03-15，返款详情请查看中泰理财理财-投资记录。
					StringBuffer buffer = new StringBuffer();
					buffer.append("【中泰理财理财】尊敬的");
					buffer.append(usersInfo.getRealName());
					if ("男".equals(usersInfo.getSex())) {
						buffer.append("先生");
					} else {
						buffer.append("女士");
					}
					buffer.append("您于");
					buffer.append(QwyUtil.fmyyyyMMddHHmmss.format(new Date()));
					buffer.append("投资的");
					buffer.append(pro.getTitle());
					buffer.append("已生效");
					buffer.append("，其中投资本金");
					if(!QwyUtil.isNullAndEmpty(inv.getInMoney())){
						buffer.append(QwyUtil.calcNumber(inv.getInMoney(), 100, "/", 2));
					}else{
						buffer.append(QwyUtil.calcNumber(inMoney, 100, "/", 2));
					}
					buffer.append("元，投资券");
					buffer.append(QwyUtil.calcNumber(coupon, 100, "/")
							.intValue());
					buffer.append("元，预计到期时间为");
					buffer.append(QwyUtil.fmyyyyMMdd.format(inv.getFinishTime()));
					buffer.append("，返款详情请查看中泰理财理财-投资记录。");
					//String smsContent="【中泰理财理财】尊敬的XX先生士，您于2016-02-29 15:30:30投资的贸易通NO.001已生效，其中投资本金900元，投资券100元，预计到期时间为2016-03-15，返款详情请查看中泰理财理财-投资记录。";
					packInsSucSmsRecord(buffer.toString(),
							DESEncrypt.jieMiUsername(usersInfo.getPhone()));
				}
				return true;
			} catch (Exception e) {
				System.err.println(e);
				log.error("发送短信失败",e);
			}
			return false;
		}
}
