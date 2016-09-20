/**
 * 
 */
package com.zfgt.account.bean;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.zfgt.account.dao.AutoShiftToDAO;
import com.zfgt.common.util.QwyUtil;
import com.zfgt.orm.AutoShiftTo;

/**
 * @author 曾礼强
 * 2015年8月17日下午3:07:41
 * 自动转入
 */
@Service
public class AutoShiftToBean{
	@Resource 
	AutoShiftToDAO dao;
	/**
	 * 保存自动转入信息
	 * @param type 状态 0:启动1：关闭
	 * @param leftMoney 账户剩余金额
	 * @param usersId 用户ID
	 * @return
	 */
	public AutoShiftTo saveAutoShiftTo(String type,Double leftMoney,Long usersId){
		AutoShiftTo as=findAutoShiftToByUsersId(usersId);
		String id="";
		//如果存在,修改记录
		if(!QwyUtil.isNullAndEmpty(as)){
			id=as.getId();
			as.setUpdateTime(new Date());
			as.setStatus("0");
			as.setType(type);
			as.setUsersId(usersId);
			as.setLeftMoney(leftMoney);
			dao.update(as);
		}else{
			as=new AutoShiftTo();
			as.setStatus("0");
			as.setUsersId(usersId);
			as.setType(type);
			as.setLeftMoney(leftMoney);
			as.setInsertTime(new Date());
			id=dao.saveAndReturnId(as);
		}
		
		return findAutoShiftToById(id);
	}
	
	
	/**
	 * 根据用户ID去查询自动转入记录
	 * @param usersId 用户ID
	 * @return
	 */
	public AutoShiftTo findAutoShiftToById(String id){
		Object object=dao.findById(new AutoShiftTo(), id);
		if(!QwyUtil.isNullAndEmpty(object)){
			return (AutoShiftTo) object;
		}
		return null;
	}
	
	/**
	 * 根据用户ID去查询自动转入记录
	 * @param usersId 用户ID
	 * @return
	 */
	public AutoShiftTo findAutoShiftToByUsersId(Long usersId){
		List<Object> objects=new ArrayList<Object>();
		StringBuffer buffer=new StringBuffer();
		buffer.append(" FROM AutoShiftTo ast WHERE ast.usersId = ? ");
		objects.add(usersId);
		List<AutoShiftTo> autoShiftTos=dao.LoadAll(buffer.toString(), objects.toArray());
		if(!QwyUtil.isNullAndEmpty(autoShiftTos)){
			return autoShiftTos.get(0);
		}
		return null;
	}
	
	/**
	 * 查询自动转入记录
	 * @param usersId 用户ID
	 * @return
	 */
	public List<AutoShiftTo> findAutoShiftTo(Integer currentPage,Integer pageSize){
		StringBuffer buffer=new StringBuffer();
		buffer.append(" FROM AutoShiftTo asf WHERE asf.type = '0' AND asf.updateTime <=  '"+QwyUtil.fmyyyyMM.format(new Date())+"'");
		List<AutoShiftTo> autoShiftTos=dao.findAdvList(buffer.toString(), null, currentPage, pageSize);
		return autoShiftTos;
	}
	
	
	
	
}
