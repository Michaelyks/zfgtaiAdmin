/**
 * 
 */
package com.zfgt.admin.product.bean;

import java.util.Date;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.zfgt.admin.product.dao.QieDAO;
import com.zfgt.common.util.PageUtil;
import com.zfgt.common.util.QwyUtil;
import com.zfgt.orm.ShangyouQie;

/**
 * @author 曾礼强
 * 2015年9月25日上午10:22:47
 */
@Service
public class QieBean {
	@Resource
	QieDAO dao;
	
	
	/**
	 * 查询一天的记录并且未发放投资券
	 */
	public PageUtil<ShangyouQie> findPageUtilQie(PageUtil<ShangyouQie> pageUtil,Date insertTime){
		StringBuffer buffer=new StringBuffer();
		buffer.append(" FROM ShangyouQie sq WHERE sq.staus = 0 ");
		if(!QwyUtil.isNullAndEmpty(insertTime)){
			buffer.append(" AND sq.insertTime between '"+QwyUtil.fmyyyyMMdd.format(insertTime)+" 00:00:00'");
			buffer.append(" and '"+QwyUtil.fmyyyyMMdd.format(insertTime)+" 23:59:59'");
		}
		pageUtil=dao.getPage(pageUtil, buffer.toString(), null);
		return pageUtil;
	}
	
	
	
	/**
	 * 根据米数进算红包金额
	 * @param score
	 * @return
	 */
	public Double qieConPon(Double score){
		if(!QwyUtil.isNullAndEmpty(score)){
			if(score>=4000){
				return 40D; 
			}else if(score>=3000){
				return 30D; 
			}else if(score>=2000){
				return 20D; 
			}else if(score>=1000){
				return 10D; 
			}
		}
		return 0D;
		
	}
}
