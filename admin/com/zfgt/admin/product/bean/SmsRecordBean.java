package com.zfgt.admin.product.bean;

import java.util.Date;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.zfgt.admin.product.dao.SmsRecordDAO;
import com.zfgt.orm.SmsRecord;
	
/**
 * @author 覃文勇
 * @createTime 2015-8-17上午11:14:50
 */
@Service
public class SmsRecordBean {
	@Resource
	private SmsRecordDAO dao;
	public String addSmsRecord(String mobileString,String smsContent,String status,Long usersAdminId){
		SmsRecord smsRecord=new SmsRecord();
		smsRecord.setInsertTime(new Date());
		smsRecord.setMobile(mobileString);
		smsRecord.setSmsContent(smsContent);
		smsRecord.setUsersAdminId(usersAdminId);
		if("0".equals(status)){//短信接口返回值为0，表示成功，为1表示失败
			smsRecord.setStatus(String.valueOf(1L)) ;//状态：0  未发送   1 已发送   2  发送失败
		}
		if("1".equals(status)){
			smsRecord.setStatus(String.valueOf(2L)) ;
		}
				
		return dao.saveAndReturnId(smsRecord);
		
	}

}
