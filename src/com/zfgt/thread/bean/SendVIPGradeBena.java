package com.zfgt.thread.bean;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.zfgt.account.dao.SendRatesDAO;
import com.zfgt.admin.Mcoin.dao.UserVIP;
import com.zfgt.admin.product.dao.CZProcedures;
import com.zfgt.common.util.LockHolder;
import com.zfgt.common.util.PageUtil;
import com.zfgt.common.util.QwyUtil;
import com.zfgt.orm.Age;
import com.zfgt.orm.InterestDetails;
import com.zfgt.orm.Investors;
import com.zfgt.orm.Product;
import com.zfgt.orm.Users;
import com.zfgt.orm.UsersInfo;
import com.zfgt.thread.dao.ThreadDAO;

@Service
public class SendVIPGradeBena {
	@Resource
	private ThreadDAO dao;
	public String sendProfit(UserVIP VIP,String level){
		synchronized (LockHolder.getLock(VIP.getUserId())) {
			if(QwyUtil.isNullAndEmpty(VIP)){
				return "用户不在状态";
			}
			else{
				
				UsersInfo users = (UsersInfo)dao.findById(new UsersInfo(), Long.valueOf(VIP.getUserId()));
				if(QwyUtil.isNullAndEmpty(users)){
					return "用户不在状态";
				}
				else{
					users.setUpdateTime(new Date());
					users.setLevel(level);
					dao.saveOrUpdate(users);
					
				}
			}

		}
		return "";
	}
	@SuppressWarnings("unchecked")
	public List<UserVIP> getInvestorsByPageUtil(PageUtil pageUtil){
		ArrayList<Object> ob = new ArrayList<Object>();
		StringBuffer buffer = new StringBuffer();
		
		buffer.append("SELECT t.in_money AS in_money , u.id FROM users_info u  JOIN (SELECT  SUM(i.in_money) AS in_money, i.`users_id` AS users_id  FROM `investors` i where i.investor_status in ('1','2','3')  GROUP BY i.`users_id`) t ON u.`users_id` = t.users_id");
		//buffer.append("SELECT fr.user_name as user_name, fr.insert_time  as insert_time , p.prize_name as prize_name FROM winner fr left join prize p on p.id =fr.prize_id    WHERE 1=1 ");
		StringBuffer bufferCount=new StringBuffer();
		bufferCount.append(" SELECT COUNT(*)  ");
		bufferCount.append(" FROM (");
		bufferCount.append(buffer);
		bufferCount.append(") t");
		 pageUtil=dao.getBySqlAndSqlCount(pageUtil, buffer.toString(), bufferCount.toString(), ob.toArray());
		 List<Object [] > list = pageUtil.getList();
		UserVIP vip= null;
		List<UserVIP> ageList=new ArrayList<UserVIP>();
		if(list.size()>0){
			for(int i=0;i<list.size();i++){
				Object [] objects=list.get(i);
				 vip=new UserVIP();
				vip.setJeCount(objects[0]==null?"0":objects[0]+"");
				vip.setUserId(objects[1]+"");
				ageList.add(vip);
			}
			return ageList;
			}
		return null;
		
	}

}
