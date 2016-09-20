package com.zfgt.admin.Mcoin.bean;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.zfgt.account.bean.UserRechargeBean;
import com.zfgt.admin.Mcoin.dao.MeowIncomeDAO;
import com.zfgt.admin.Mcoin.dao.MeowPayDao;
import com.zfgt.admin.Mcoin.dao.UsersMcoin;
import com.zfgt.common.dao.ObjectDAO;
import com.zfgt.common.util.DESEncrypt;
import com.zfgt.common.util.PageUtil;
import com.zfgt.common.util.QwyUtil;
import com.zfgt.orm.MCoinRecord;
import com.zfgt.orm.Users;
import com.zfgt.orm.UsersInfo;

/**
 * @author Administrator
 *
 */
/**
 * @author Administrator
 *
 */
@Service
public class MeowIncomeBean {
	@Resource(name="objectDAO")
	private ObjectDAO dao;
	@Resource
	private UserRechargeBean userRechargeBean;
	private static Logger log = Logger.getLogger(MeowIncomeBean.class); 
	/**
	 * 将数据转换为DateMoney
	 */
	private List<MeowIncomeDAO> toDateMoney(List<Object [] > list) throws ParseException{
		List<MeowIncomeDAO> meowIncome=new ArrayList<MeowIncomeDAO>();
		if(!QwyUtil.isNullAndEmpty(list)){
			for (Object [] object : list) {
				MeowIncomeDAO plat=new MeowIncomeDAO();
				plat.setInsertTime(QwyUtil.fmyyyyMMddHHmmss.parse(QwyUtil.fmyyyyMMddHHmmss.format(object[0])));
				plat.setUserName(object[1]==null?"":object[1]+"");
				plat.setNumber(object[2]==null?"":object[2]+"");
				plat.setType(getType(object[3]+""));
				plat.setTotalCoin(object[4]+"");
				plat.setNote(object[5]==null?"":object[5]+"");
				meowIncome.add(plat);
			}
		}
		return meowIncome;
	}
	
	/**
	 * @param type 区别用户获取瞄币的途径
	 * @return
	 */
	public String getType(String type){
		String str ="";
		if(type.equals("1"))
			str="签到";
		if(type.equals("2"))
			str="邀请好友";
		if(type.equals("3"))
			str="被邀请(注册)";
		if(type.equals("4"))
			str="被邀请(第一笔投资)";
		if(type.equals("5"))
			str="投资获得";
		if(type.equals("6"))
			str="分享活动";
		if(type.equals("7"))
			str="手动赠送";
			return str;
	}
	
	/**
	 * 以日期分组查询瞄币发放明细
	 * @param name 用户名
	 * @param insertTime  时间段
	 * @param pageUtil 分页
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public PageUtil<MeowIncomeDAO> loadMeowIncome(String name,String insertTime,PageUtil pageUtil) {
		
		
		try {
		ArrayList<Object> ob = new ArrayList<Object>();
		StringBuffer buff = new StringBuffer();
		buff.append("SELECT  r.`insert_time` ,  u.`username` AS username , m.`coin` AS coin ,m.type as type , IFNULL(r.total_coin,'0') AS total_coin , m.note as note");
		buff.append(" FROM  users u  LEFT JOIN  m_coin_income m ON u.id = m.`users_id` LEFT JOIN `m_coin_record` r ON r.`record_id` = m.record_number   WHERE 1 = 1 AND r.`coin_type`=2 ");
		
		if(!QwyUtil.isNullAndEmpty(name)){
			buff.append(" AND u.username = ? ");
			ob.add(DESEncrypt.jiaMiUsername(name));
		}
		
		if(!QwyUtil.isNullAndEmpty(insertTime)){
			String [] time=QwyUtil.splitTime(insertTime);
			if(time.length > 1)
			{
				buff.append(" AND r.insert_time >= ? ");
				ob.add(QwyUtil.fmMMddyyyyHHmmss.parse(time[0]+" 00:00:00"));
				buff.append(" AND r.insert_time <= ? ");
				ob.add(QwyUtil.fmMMddyyyyHHmmss.parse(time[1]+" 23:59:59"));
			}
			else{

				buff.append(" AND r.insert_time >= ? ");
				ob.add(QwyUtil.fmMMddyyyyHHmmss.parse(time[0]+" 00:00:00"));
				buff.append(" AND r.insert_time <= ? ");
				ob.add(QwyUtil.fmMMddyyyyHHmmss.parse(time[0]+" 23:59:59"));
			}
			

		
		}
		
		//if(!QwyUtil.isNullAndEmpty(insertTime)){
		
			//buff.append(" AND DATE_FORMAT(  m.insert_time, '%Y-%m-%d' ) = "+"DATE_FORMAT('"+insertTime +"','%Y-%m-%d' )");
		//}
		 buff.append(" ORDER BY  m.insert_time DESC ");
			
			StringBuffer bufferCount=new StringBuffer();
			bufferCount.append(" SELECT COUNT(*)  ");
			bufferCount.append(" FROM (");
			bufferCount.append(buff);
			bufferCount.append(") t");
			//buff.append("ORDER BY fr.insert_time DESC ");
			 pageUtil=dao.getBySqlAndSqlCount(pageUtil, buff.toString(), bufferCount.toString(), ob.toArray());
			
			 List<MeowIncomeDAO> platUsers=toDateMoney(pageUtil.getList());
				pageUtil.setList(platUsers);
				return pageUtil;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.error("操作异常: ",e);
		}
			return null;
		
	}
	
	/**
	 * 查询用户总瞄币
	 * @param pageUtil
	 * @param usersname
	 * @return
	 */
	public PageUtil<MCoinRecord>  findUsersMcoin(PageUtil pageUtil,String usersname){
		try {
		ArrayList<Object> list=new ArrayList<Object>();
		StringBuffer hql = new StringBuffer();
		hql.append("FROM UsersInfo m WHERE 1=1 ");
		if(!QwyUtil.isNullAndEmpty(usersname)){
			hql.append(" AND m.users.username = ? ");
			list.add(DESEncrypt.jiaMiUsername(usersname));
		}
		hql.append(" ORDER BY m.insertTime DESC ");
		return dao.getByHqlAndHqlCount(pageUtil, hql.toString(), hql.toString(), list.toArray());
		} catch (Exception e) {
			// TODO: handle exception
			log.error("操作异常: ",e);
		}
		return null;
	}
	/** 发放瞄币
	 * @param usersId 用户id
	 * @param note 备注
	 * @param mcoin 发放瞄币数量
	 * @return
	 */
	public boolean sengMcoin(Long usersId, String note,String mcoin,Long totalMcoin){
		return userRechargeBean.sengMcoin(usersId,Long.parseLong(mcoin) ,  "7",note,totalMcoin);
	}
	
	/**
	 * 获取用户的真实姓名;
	 * @param username
	 * @return
	 */
	public Users getUsersByUsername(String username){
		Object ob=null;
		try {
			if(QwyUtil.isNullAndEmpty(username))
				return null;
			StringBuffer hql = new StringBuffer();
			hql.append("FROM Users us ");
			hql.append("WHERE us.username = ? or us.phone=?");
			ob = dao.findJoinActive(hql.toString(), new Object[]{DESEncrypt.jiaMiUsername(username.toLowerCase()),DESEncrypt.jiaMiUsername(username.toLowerCase())});
			if(ob!=null){
				return (Users)ob;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.error("操作异常: ",e);
		}
		return null;
		
	}
	/**
	 * 将数据转换为UsersMcoin
	 */
	private List<UsersMcoin> toUsersMcoin(List<Object [] > list) throws ParseException{
		List<UsersMcoin> mcoin=new ArrayList<UsersMcoin>();
		if(!QwyUtil.isNullAndEmpty(list)){
			for (Object [] object : list) {
				UsersMcoin plat=new UsersMcoin();
				
				plat.setUsername(object[0]+"");
				plat.setUsersId(object[1]+"");
				plat.setReal_name(object[2]+"");
				plat.setTotal_point(object[3]+"");
				plat.setCoin(object[4]+"");
				//plat.setAddMCoin(object[5]+"");
				plat.setAddMCoin((Long.parseLong(object[4]+"")+Long.parseLong(object[3]+""))+"");
				if((Long.parseLong(object[4]+"")+Long.parseLong(object[3]+"")) >0){
					Double cn =Double.parseDouble(object[4]+"")/(Double.parseDouble(object[4]+"")+Double.parseDouble(object[3]+""));
					plat.setUsage_rate(cn*100);
				}
				else{
					plat.setUsage_rate(Double.parseDouble(0+""));
				}

				mcoin.add(plat);

			}
		}
		return mcoin;
	}
	
	/**
	 * 加载所有用户信息
	 * @param pageUtil
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public PageUtil<UsersMcoin> loadUserInfo(PageUtil pageUtil,String username){
		try {
		ArrayList<Object> ob = new ArrayList<Object>();
		StringBuffer hql = new StringBuffer();
		//hql.append(" SELECT IFNULL(s.username,'') AS username , IFNULL(u.users_id,'') AS users_id , IFNULL(u.real_name,'') AS real_name ,IFNULL(u.total_point,'0') AS total_point ,  ");
		//hql.append(" IFNULL(m.coin,'0') AS coin FROM (SELECT SUM(m.coin)  AS coin  , m.users_id FROM m_coin_pay m GROUP BY m.users_id) m  LEFT JOIN");
		//hql.append(" users s ON m.users_id  =  s.id   LEFT JOIN  users_info u ON u.users_id = s.id");
		hql.append(" select IFNULL(s.username,'') AS username , IFNULL(u.users_id,'') AS users_id , IFNULL(u.real_name,'') AS real_name ,IFNULL(u.total_point,'0') AS total_point ,");
		hql.append(" IFNULL(m.coin,'0') AS coin , i. icoin as icoin from   (SELECT SUM(c.coin) as icoin , c.users_id as users_id from m_coin_income c  GROUP BY c.users_id) i");
		hql.append("  LEFT JOIN users s ON i.users_id  =  s.id  LEFT JOIN  users_info u ON u.users_id = s.id");
		hql.append(" LEFT JOIN (SELECT SUM(m.coin)  AS coin  , m.users_id FROM m_coin_pay m GROUP BY m.users_id) m on s.id = m.users_id");
		hql.append(" where 1=1 ");
		if(!QwyUtil.isNullAndEmpty(username)){
			hql.append(" AND s.username = ? ");
			ob.add(DESEncrypt.jiaMiUsername(username));
		}
		
		hql.append(" ORDER BY m.coin/(m.coin+u.total_point) DESC , u.total_point DESC "); 
		StringBuffer bufferCount=new StringBuffer();
		bufferCount.append(" SELECT COUNT(*)  ");
		bufferCount.append(" FROM (");
		bufferCount.append(hql);
		bufferCount.append(") t");
		
		 pageUtil=dao.getBySqlAndSqlCount(pageUtil, hql.toString(), bufferCount.toString(), ob.toArray());
		 List<UsersMcoin> platUsers=toUsersMcoin(pageUtil.getList());
			pageUtil.setList(platUsers);
			return pageUtil;
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
	
	public UsersInfo findUsersInfoId(long id){
		try {
			StringBuffer hql = new StringBuffer("FROM UsersInfo mp ");
			hql.append("WHERE mp.id = ? ");
			Object[] ob = new Object[]{id};
			return (UsersInfo)dao.findJoinActive(hql.toString(), ob);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.error("操作异常: ",e);
		}
		return null;
	}

	/**
	 * 清零瞄币	
	 * @param product
	 * @return
	 */
		public Boolean cleanMcoin(UsersInfo users){
			if(!QwyUtil.isNullAndEmpty(users)){
				users.setUpdateTime(new Date());
				users.setTotalPoint(Long.parseLong("0"));
				dao.saveOrUpdate(users);
				return true;
			}
	     	return false;
		}
}
