package com.zfgt.thread.bean;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.zfgt.account.bean.UserRechargeBean;
import com.zfgt.common.util.PageUtil;
import com.zfgt.common.util.QwyUtil;
import com.zfgt.orm.Investors;
import com.zfgt.thread.dao.ThreadDAO;
@Service
public class SendMcoinBean {
	private static Logger log = Logger.getLogger(SendMcoinBean.class); 
	@Resource
	private UserRechargeBean userRechargeBean;
	@Resource
		private ThreadDAO dao;
	@SuppressWarnings("unchecked")
	public List<String> getInvestorsByPageUtil(PageUtil pageUtil){
		ArrayList<Object> ob = new ArrayList<Object>();
		StringBuffer buffer = new StringBuffer();
		buffer.append(" select i.id  FROM investors i  LEFT JOIN m_coin_income m  on DATE_FORMAT(i.insert_time, '%Y-%m-%d %H:%i:%S')");
		buffer.append(" <= DATE_SUB( DATE_FORMAT(m.insert_time, '%Y-%m-%d %H:%i:%S'),INTERVAL 0 SECOND) ");
		buffer.append(" and  DATE_FORMAT(i.insert_time, '%Y-%m-%d %H:%i:%S')> DATE_SUB( DATE_FORMAT(m.insert_time, '%Y-%m-%d %H:%i:%S'),INTERVAL 6 SECOND) ");
		buffer.append(" where m.type=5 and i.is_hair_mcoin !=1");
		StringBuffer bufferCount=new StringBuffer();
		bufferCount.append(" SELECT COUNT(*)  ");
		bufferCount.append(" FROM (");
		bufferCount.append(buffer);
		bufferCount.append(") t");
		 pageUtil=dao.getBySqlAndSqlCount(pageUtil, buffer.toString(), bufferCount.toString(), ob.toArray());
		 List<Object [] > list = pageUtil.getList();
		List<String> ageList=new ArrayList<String>();
		if(list.size()>0){
			for(int i=0;i<list.size();i++){
			Object cn =	list.get(i);
				ageList.add(cn.toString());
			}
			return ageList;
			}
		return null;
		
	}
	
	/** 发放瞄币
	 * @param usersId 用户id
	 * @param note 备注
	 * @param mcoin 发放瞄币数量
	 * @return
	 */
	public boolean sengMcoin(Investors inv){
		double  mcoin = (inv.getInMoney()/100)* ((double) QwyUtil.getDifferDays(inv.getStartTime(), inv.getClearTime())/30);
		Long sendMcoin = new Double(mcoin).longValue();
		if(userRechargeBean.sengMcoin(inv.getUsersId(),sendMcoin ,  "5","",Long.parseLong("0"))){
			inv.setIsHairMcoin("1");
			dao.saveOrUpdate(inv);
			return true;
		}
		return false;
	}
	
	public PageUtil<Investors> findNoSendMcoin(PageUtil<Investors> pageUtil){
		try {
			StringBuffer hql = new StringBuffer("FROM Investors mp ");
			hql.append("WHERE investor_status =3  and mp.isHairMcoin = ? ");
			Object[] ob = new Object[]{"0"};
			return (PageUtil<Investors>)dao.getPage(pageUtil, hql.toString(), ob);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.error("操作异常: ",e);
		}
		return null;
	}
	/**
	 * 根据id查询数据	
	 * @param id
	 * @return
	 */
	
	public Investors findInvestorsId(String id){
		try {
			StringBuffer hql = new StringBuffer("FROM Investors mp ");
			hql.append("WHERE mp.id = ? ");
			Object[] ob = new Object[]{id};
			return (Investors)dao.findJoinActive(hql.toString(), ob);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.error("操作异常: ",e);
		}
		return null;
	}
	public Boolean isSendMcoin(Investors inv){
		if(!QwyUtil.isNullAndEmpty(inv)){
			inv.setIsHairMcoin("1");
			dao.saveOrUpdate(inv);
			return true;
		}
     	return false;
	}
	
}
