package com.zfgt.thread.bean;

import java.util.Date;

import javax.annotation.Resource;


import org.springframework.stereotype.Service;

import com.zfgt.common.util.PageUtil;
import com.zfgt.common.util.QwyUtil;
import com.zfgt.orm.CzRecord;
import com.zfgt.thread.dao.ThreadDAO;


/**后台启动处理（连连）充值异步返回信息的线程的bean层
 * @author 覃文勇
 * @createTime 2015-12-7下午1:59:10
 */
@Service
public class LianLianCzThreadBean {
	@Resource
	private ThreadDAO dao;
	/**
	 * 查询状态为处理中的连连充值记录
	 * @param pageUtil 
	 * @param type 类型 1：易宝网银2:易宝快捷3:连连认证4：连连网银
	 * @param status 充值状态;0:待充值;1:充值成功;2充值失败;3:易宝充值成功,数据库插入失败;
	 * @param insertTime 插入时间
	 * @return
	 */
    @SuppressWarnings("unchecked")
	public PageUtil<CzRecord> getCzRecordList(PageUtil<CzRecord> pageUtil,String type,String status,Date insertTime){
		StringBuffer hql = new StringBuffer();
		hql.append(" FROM CzRecord cz ");
		hql.append(" WHERE 1=1 ");
		if(!QwyUtil.isNullAndEmpty(type)){
			hql.append(" AND cz.type IN ("+type+") ");
		}
		if(!QwyUtil.isNullAndEmpty(status)){
			hql.append(" AND cz.status IN ("+status+") ");
		}
		if(!QwyUtil.isNullAndEmpty(insertTime)){
			hql.append(" AND '"+QwyUtil.fmyyyyMMddHHmmss.format(insertTime)+"' >=cz.insertTime  ");
		}
		hql.append(" ORDER BY cz.insertTime ASC ");
		return (PageUtil<CzRecord>)dao.getPage(pageUtil, hql.toString(), null);
    }
	/**
	 *  修改充值记录   
	 * @param record 充值记录
	 * @param status 充值状态;0:待充值;1:充值成功;2充值失败;3:易宝充值成功,数据库插入失败;
	 * @param note 备注
	 * @return
	 * @throws Exception
	 */
    public String updateCzRecord(CzRecord record,String oid_paybill,String status,String note) throws Exception{
    	if(!QwyUtil.isNullAndEmpty(oid_paybill)){
        	record.setYbOrderId(oid_paybill);
    	}
    	record.setStatus(status);
    	record.setCheckTime(new Date());
    	record.setNote(note);
    	dao.saveOrUpdate(record);
    	return null;
    }
}
