package com.zfgt.mshop.bean;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.zfgt.common.util.QwyUtil;
import com.zfgt.mshop.dao.MShareDAO;
import com.zfgt.orm.MShare;


/**分享活动的bean;<br>
 * @author 覃文勇
 * @createTime 2015-11-4下午4:53:29
 */
@Service
public class MShareBean {
	@Resource
	private MShareDAO dao;

	/**
	 * 验证用户是否分享过该活动
	 * @param uid 用户id
	 * @param activityId 活动标识符
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Boolean isValidMShare(long uid,String flag){
		Boolean isOk=false;
		StringBuffer buffer=new StringBuffer();
		ArrayList<Object> ob = new ArrayList<Object>();
		buffer.append(" FROM MShare ms where 1=1 ");
		if(!QwyUtil.isNullAndEmpty(uid)){
			buffer.append(" AND ms.usersId=? ");
			ob.add(uid);
		}		
		if(!QwyUtil.isNullAndEmpty(flag)){			
			buffer.append(" AND ms.flag=? ");
			ob.add(flag);	
		}
		List<MShare> list=dao.LoadAll(buffer.toString(), ob.toArray());
		if(QwyUtil.isNullAndEmpty(list)){
			isOk=true;
		}
		return isOk;
	}
	/**
	 * 	保存活动分享记录
	 * @param uid 用户id
	 * @param activityId 活动标识符
	 * @return
	 */
	public String addMShare(long uid,String flag){
		MShare mShare=new MShare();
		mShare.setUsersId(uid);
		mShare.setFlag(flag);
		mShare.setInsertTime(new Date());
		mShare.setType("3");
		return dao.saveAndReturnId(mShare);
	}

}
