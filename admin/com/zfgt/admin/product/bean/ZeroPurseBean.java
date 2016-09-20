package com.zfgt.admin.product.bean;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.zfgt.common.dao.ObjectDAO;
import com.zfgt.common.util.DESEncrypt;
import com.zfgt.common.util.PageUtil;
import com.zfgt.common.util.QwyUtil;
import com.zfgt.orm.Users;
import com.zfgt.orm.UsersAdmin;
import com.zfgt.orm.ZeroPurse;
@Service
public class ZeroPurseBean {
	private static final String Users = null;
	@Resource(name="objectDAO")
	private ObjectDAO dao;
	private static Logger log = Logger.getLogger(ZeroPurseBean.class); 
	private List<ZeroPurse> toDateMoney(List<Object [] > list) throws ParseException{
		List<ZeroPurse> zeroPurse=new ArrayList<ZeroPurse>();
		if(!QwyUtil.isNullAndEmpty(list)){
			for (Object [] object : list) {
				ZeroPurse plat=new ZeroPurse();
				plat.setUsername(object[0]==null?"":object[0]+"");
				plat.setInsertTime(QwyUtil.fmyyyyMMddHHmmss.parse(QwyUtil.fmyyyyMMddHHmmss.format(object[1])));
				plat.setUpdateTime( object[2]==null?"":object[2].toString());
				plat.setLeftMoney(!QwyUtil.isNullAndEmpty(object[3])?object[3]+"":"");
			//	plat.setBankAccount(DESEncrypt.jieMiBankCard(object[4]+""));
				zeroPurse.add(plat);
			}
		}
		return zeroPurse;
	}
	public String crateJsArray(Users user){
		StringBuffer jsData = new StringBuffer();
		if(!QwyUtil.isNullAndEmpty(user)){
			jsData.append("{");
			jsData.append("\"status\":\"ok\",");
			jsData.append("\"date\":"+user.getId()+"");
			jsData.append("}");
		}
		else {
			jsData.append("{");
			jsData.append("\"status\":\"error\"");
			//jsData.append("\"date\":"+userId);
			jsData.append("}");
		}
		return jsData.toString();
	}
	
	public String loadZeroPurseId(String name,String usersId){
		try {
			
			StringBuffer hql = new StringBuffer("FROM Users us where 1=1");
			
			if(!QwyUtil.isNullAndEmpty(name)){
				String jiaName =DESEncrypt.jiaMiUsername(name);
				hql.append(" and us.username ="+"\'"+jiaName+"\'");
			}
			if(!QwyUtil.isNullAndEmpty(usersId)){
				hql.append(" and us.id =  "+usersId);
			}
	
			
			Users user = (Users)dao.findJoinActive(hql.toString(), null);
			
			return crateJsArray(user);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.error("操作异常: ",e);
		}
		return "";
	}
	
	
	@SuppressWarnings("unchecked")
	public PageUtil<ZeroPurse> loadZeroPurse(String name,String  usersId,String bankAccount,PageUtil pageUtil) {
		try {
		ArrayList<Object> ob = new ArrayList<Object>();
		StringBuffer buff = new StringBuffer();
		buff.append("select u.username as username, t.insert_time as insert_time , t.update_time as update_time ,t.left_money as left_money , a.bank_account as bank_account from  ");
		buff.append("auto_shift_to t LEFT JOIN  users u on u.id = t.users_id  LEFT JOIN account a on a.users_id = u.id   WHERE 1=1 ");
		
		if(!QwyUtil.isNullAndEmpty(name)){
			buff.append("AND u.username = ? ");
			//hql.append(" AND ins.product.title like '%"+productTitle+"%' ");
			ob.add(DESEncrypt.jiaMiUsername(name));
		}
		
		if(!QwyUtil.isNullAndEmpty(usersId)){
			buff.append("AND u.id = ? ");
			//hql.append(" AND ins.product.title like '%"+productTitle+"%' ");
			ob.add(Long.parseLong(usersId));
		}
		
		if(!QwyUtil.isNullAndEmpty(bankAccount)){
			buff.append("AND a.bank_account = ? ");
			//hql.append(" AND ins.product.title like '%"+productTitle+"%' ");
			ob.add(DESEncrypt.jiaMiBankCard(bankAccount));
		}
		//buff.append(" ORDER BY t.left_money DESC ");
		//ins.users_id
	 buff.append(" ORDER BY DATE_FORMAT(  t.update_time, '%Y-%m-%d' ) DESC ,t.left_money DESC ");
		
		StringBuffer bufferCount=new StringBuffer();
		bufferCount.append(" SELECT COUNT(t.username)  ");
		bufferCount.append(" FROM (");
		bufferCount.append(buff);
		bufferCount.append(") t");
		//buff.append("ORDER BY fr.insert_time DESC ");
		 pageUtil=dao.getBySqlAndSqlCount(pageUtil, buff.toString(), bufferCount.toString(), ob.toArray());
		
		 List<ZeroPurse> platUsers=toDateMoney(pageUtil.getList());
			pageUtil.setList(platUsers);
			return pageUtil;
	} catch (Exception e) {
		// TODO Auto-generated catch block
		log.error("操作异常: ",e);
	}
		return null;
	}
}






