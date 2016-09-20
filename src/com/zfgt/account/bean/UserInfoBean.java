package com.zfgt.account.bean;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.zfgt.account.dao.UserInfoDAO;
import com.zfgt.admin.Mcoin.dao.MeowPayDao;
import com.zfgt.common.util.DESEncrypt;
import com.zfgt.common.util.PageUtil;
import com.zfgt.common.util.QwyUtil;
import com.zfgt.orm.Age;
import com.zfgt.orm.Bank;
import com.zfgt.orm.PlatUser;
import com.zfgt.orm.Region;
import com.zfgt.orm.UserInfoList;
import com.zfgt.orm.Users;
import com.zfgt.orm.UsersInfo;
import com.zfgt.orm.UsersStat;
import com.zfgt.orm.Winner;

/**
 * 用户信息Bean层
 * 
 * @author qwy
 *
 * @createTime 2015-4-20下午1:02:35
 */
@Service
public class UserInfoBean {

	private static Logger log = Logger.getLogger(UserInfoBean.class);
	@Resource
	private UserInfoDAO dao;

	/**
	 * 根据用户id查找用户
	 * 
	 * @param id
	 *            用户id;
	 * @return
	 */
	public UsersInfo getUserInfoById(long id) {
		StringBuffer buff = new StringBuffer();
		buff.append("FROM UsersInfo userInfo ");
		buff.append("WHERE userInfo.usersId= ? ");
		UsersInfo userInfo = (UsersInfo) dao.findJoinActive(buff.toString(), new Object[] { id });
		return userInfo;
	}

	/**
	 * 根据修改用户资料
	 */
	public UsersInfo saveOrUpdateUserInfo(UsersInfo userInfo) {
		// userInfoDao.update(userInfo);
		dao.saveOrUpdate(userInfo);
		return userInfo;
	}

	/**
	 * 判断邮箱是否被绑定
	 */
	public UsersInfo isEmailband(String email) {
		Object ob = null;
		try {
			if (QwyUtil.isNullAndEmpty(email))
				return null;
			StringBuffer hql = new StringBuffer();
			hql.append("FROM UsersInfo ui ");
			hql.append("WHERE ui.email = ?");
			ob = dao.findJoinActive(hql.toString(), new Object[] { email });
			if (ob != null) {
				return (UsersInfo) ob;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.error("操作异常: ", e);
		}
		return null;
	}

	/**
	 * 根据用户id查找用户
	 * 
	 * @param id
	 *            用户id;
	 * @return
	 */
	public Users getUserById(long id) {
		StringBuffer buff = new StringBuffer();
		buff.append("FROM Users user ");
		buff.append("WHERE user.id= ? ");
		Users users = (Users) dao.findJoinActive(buff.toString(), new Object[] { id });
		return users;
	}

	/**
	 * 统计平台注册人数
	 * 
	 * @param insertTime
	 *            注册时间
	 * @param id
	 *            用户id;
	 * @return
	 */
	public List<UsersStat> findUsersCount(String insertTime) throws Exception {
		List<Object> arrayList = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT us.regist_platform,COUNT(DISTINCT us.id) FROM Users as us ");
		sql.append(" WHERE us.regist_platform is not NULL ");
		// 发布时间
		if (!QwyUtil.isNullAndEmpty(insertTime)) {
			String[] time = QwyUtil.splitTime(insertTime);
			if (time.length > 1) {
				sql.append(" AND us.insert_time >= ? ");
				arrayList.add(QwyUtil.fmMMddyyyy.parse(time[0]));
				sql.append(" AND us.insert_time <= ? ");
				arrayList.add(QwyUtil.fmMMddyyyy.parse(time[1]));
			} else {
				sql.append(" AND us.insert_time >= ? ");
				arrayList.add(QwyUtil.fmMMddyyyyHHmmss.parse(time[0] + " 00:00:00"));
				sql.append(" AND us.insert_time <= ? ");
				arrayList.add(QwyUtil.fmMMddyyyyHHmmss.parse(time[0] + " 23:59:59"));
			}
		}
		sql.append(" GROUP BY us.regist_platform ");
		List<Object[]> list = dao.LoadAllSql(sql.toString(), arrayList.toArray());
		return parseUsersStat(list);
	}

	/**
	 * 统计平台注册总人数
	 * 
	 * @param insertTime
	 *            注册时间
	 * @return 总注册人数
	 */
	public String findAllUsersCount(String insertTime) throws Exception {
		List<Object> arrayList = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT COUNT(DISTINCT us.id) FROM Users as us ");
		sql.append(" WHERE us.regist_platform is not NULL ");
		// 发布时间
		if (!QwyUtil.isNullAndEmpty(insertTime)) {
			String[] time = QwyUtil.splitTime(insertTime);
			if (time.length > 1) {
				sql.append(" AND us.insert_time >= ? ");
				arrayList.add(QwyUtil.fmMMddyyyy.parse(time[0]));
				sql.append(" AND us.insert_time <= ? ");
				arrayList.add(QwyUtil.fmMMddyyyy.parse(time[1]));
			} else {
				sql.append(" AND us.insert_time >= ? ");
				arrayList.add(QwyUtil.fmMMddyyyyHHmmss.parse(time[0] + " 00:00:00"));
				sql.append(" AND us.insert_time <= ? ");
				arrayList.add(QwyUtil.fmMMddyyyyHHmmss.parse(time[0] + " 23:59:59"));
			}
		}
		Object object = dao.getSqlCount(sql.toString(), arrayList.toArray());
		if (!QwyUtil.isNullAndEmpty(object)) {
			return object + "";
		}
		return "0";
	}

	/**
	 * 将数据转换为UsersStat
	 * 
	 * @param list
	 * @return
	 */
	public List<UsersStat> parseUsersStat(List<Object[]> list) throws Exception {
		List<UsersStat> usersStats = new ArrayList<UsersStat>();
		if (!QwyUtil.isNullAndEmpty(list)) {
			for (Object[] object : list) {
				UsersStat usersStat = new UsersStat();
				usersStat.setRegistPlatorm(object[0] + "");
				usersStat.setRegistPlatormCount(object[1] + "");
				usersStats.add(usersStat);
			}
		}
		return usersStats;
	}

	/**
	 * 根据日期统计用户注册人数
	 * 
	 * @param insertTime
	 *            注册时间
	 * @return
	 */
	public PageUtil<PlatUser> findUsersCountByDate(PageUtil pageUtil, String insertTime) throws Exception {
		ArrayList<Object> list = new ArrayList<Object>();
		StringBuffer buffer = new StringBuffer();
		buffer.append(" SELECT DATE_FORMAT( dd.insert_time, '%Y-%m-%d' ) as date ,    ");
		buffer.append(" (  ");
		buffer.append(" SELECT COUNT(*) as userscount FROM  users  us  ");
		buffer.append(" WHERE us.regist_platform is not NULL ");
		buffer.append(" AND DATE_FORMAT( dd.insert_time, '%Y-%m-%d' ) = DATE_FORMAT( us.insert_time, '%Y-%m-%d' ) ");
		buffer.append(" ) as 'userscount'  ");
		buffer.append(" FROM dateday dd ");
		buffer.append(" WHERE 1=1 ");
		// 充值时间
		if (!QwyUtil.isNullAndEmpty(insertTime)) {
			String[] time = QwyUtil.splitTime(insertTime);
			if (time.length > 1) {
				buffer.append(" AND dd.insert_time >= ? ");
				list.add(QwyUtil.fmMMddyyyy.parse(time[0]));
				buffer.append(" AND dd.insert_time <= ? ");
				list.add(QwyUtil.fmMMddyyyy.parse(time[1]));
			} else {
				buffer.append(" AND dd.insert_time >= ? ");
				list.add(QwyUtil.fmMMddyyyyHHmmss.parse(time[0] + " 00:00:00"));
				buffer.append(" AND dd.insert_time <= ? ");
				list.add(QwyUtil.fmMMddyyyyHHmmss.parse(time[0] + " 23:59:59"));
			}
		}
		buffer.append(" GROUP BY DATE_FORMAT( dd.insert_time, '%Y-%m-%d' ) ");
		buffer.append(" ORDER BY DATE_FORMAT( dd.insert_time, '%Y-%m-%d' ) DESC ");
		StringBuffer bufferCount = new StringBuffer();
		bufferCount.append(" SELECT COUNT(t.date)  ");
		bufferCount.append(" FROM (");
		bufferCount.append(buffer);
		bufferCount.append(") t");
		pageUtil = dao.getBySqlAndSqlCount(pageUtil, buffer.toString(), bufferCount.toString(), list.toArray());
		List<PlatUser> platUsers = toPlatUser(pageUtil.getList());
		pageUtil.setList(platUsers);
		return pageUtil;
	}

	/**
	 * 将数据转换为platUser形式
	 * 
	 * @param list
	 * @return
	 */
	private List<PlatUser> toPlatUser(List<Object[]> list) {
		List<PlatUser> platUsers = new ArrayList<PlatUser>();
		if (!QwyUtil.isNullAndEmpty(list)) {
			for (Object[] object : list) {
				PlatUser platUser = new PlatUser();
				platUser.setDate(object[0] + "");
				platUser.setUserscount(object[1] + "");
				platUsers.add(platUser);
			}
		}
		return platUsers;
	}

	/**
	 * 根据渠道去获取用户信息
	 * 
	 * @param channel
	 *            入口渠道
	 * @param pageUtil
	 * @param username
	 *            用户名
	 * @param insertTime
	 *            插入时间
	 * @param isbindbank
	 *            是否绑定银行卡
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	
	public  PageUtil<UserInfoList>  findUsersByChannel(PageUtil pageUtil, String channel, String username,
		String insertTime, String isbindbank,String level,String inMoney1,String inMoney2){
		try {
		List<Object> list = new ArrayList<Object>();
		StringBuffer buff = new StringBuffer();
		buff.append(" SELECT	a.id AS id,	a.username AS username,	a.province AS province,");
		buff.append(" a.city AS city,	a.card_type AS card_type,a.insert_time as insert_time ,");
		buff.append(" a.regist_platform as regist_platform,	b.real_name as real_name,	b.sex AS sex,");
		buff.append(" b.age AS age,	b.birthday AS birthday,	b.level as level,");
		buff.append(" b.is_bind_bank as is_bind_bank,t.in_money as in_money");
		buff.append(" FROM	users a JOIN users_info b ON a.id = b.users_id left join");
		buff.append(" (select SUM(i.in_money/100) as in_money , i.users_id as users_id  from investors i where 1=1 and i.investor_status in ('1','2','3')  GROUP BY i.users_id )t");
		buff.append("  on t.users_id = b.users_id  WHERE 1=1" );
		if (!QwyUtil.isNullAndEmpty(channel)) {
			buff.append(" AND a.registChannel = ? ");
		    list.add(channel);
	      }
		if (!QwyUtil.isNullAndEmpty(username)) {
			buff.append(" AND (");
			buff.append("  a.username = ? ");
			list.add(DESEncrypt.jiaMiUsername(username));
			buff.append(" OR a.id = '" + username + "' ");
			buff.append(" OR b.real_name = ? ");
			list.add(username);
			buff.append(" )");
	     }
		if (!QwyUtil.isNullAndEmpty(isbindbank)) {
			buff.append(" AND b.is_bind_bank = ? ");
		list.add(isbindbank);
	   }
		
		// 发布时间
				if (!QwyUtil.isNullAndEmpty(insertTime)) {
					String[] time = QwyUtil.splitTime(insertTime);
					if (time.length > 1) {
						buff.append(" AND a.insert_time >= ? ");
						list.add(QwyUtil.fmMMddyyyyHHmmss.parse(time[0]+" 00:00:00"));
						buff.append(" AND a.insert_time <= ? ");
						list.add(QwyUtil.fmMMddyyyyHHmmss.parse(time[1]+" 23:59:59"));
					} else {
						buff.append(" AND a.insert_time >= ? ");
						list.add(QwyUtil.fmMMddyyyyHHmmss.parse(time[0] + " 00:00:00"));
						buff.append(" AND a.insert_time <= ? ");
						list.add(QwyUtil.fmMMddyyyyHHmmss.parse(time[0] + " 23:59:59"));
					}
				}
				
				
				if (!QwyUtil.isNullAndEmpty(level)) {
					buff.append(" AND b.level = ? ");
				    list.add(level);
			      }
				
				
				if (!QwyUtil.isNullAndEmpty(inMoney1)&& !QwyUtil.isNullAndEmpty(inMoney2)) {
					buff.append(" AND  t.in_money >= ? ");
				    list.add(Double.parseDouble(inMoney1)*10000);
				    buff.append(" AND  t.in_money <= ? ");
				    list.add(Double.parseDouble(inMoney2)*10000);
			      }
				else if (!QwyUtil.isNullAndEmpty(inMoney1)) {
					buff.append(" AND  t.in_money >= ? ");
					 list.add(Double.parseDouble(inMoney1)*10000);
				 }
				else if (!QwyUtil.isNullAndEmpty(inMoney2)) {
					buff.append(" AND  t.in_money <= ? ");
					 list.add(Double.parseDouble(inMoney2)*10000);
				 }
				
				buff.append(" ORDER BY t.in_money DESC ");
				
				StringBuffer bufferCount=new StringBuffer();
				bufferCount.append(" SELECT COUNT(*)  ");
				bufferCount.append(" FROM (");
				bufferCount.append(buff);
				bufferCount.append(") t");
				pageUtil=dao.getBySqlAndSqlCount(pageUtil, buff.toString(), bufferCount.toString(), list.toArray());
				List<UserInfoList> platUsers=toDateMoney(pageUtil.getList());
				pageUtil.setList(platUsers);
				return pageUtil;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.error("操作异常: ",e);
		}
		return null;
	}
	
	private List<UserInfoList> toDateMoney(List<Object [] > list) throws ParseException{
		List<UserInfoList> meowPay=new ArrayList<UserInfoList>();
		Double money = 0.0;
		if(!QwyUtil.isNullAndEmpty(list)){
			for (Object [] object : list) {
				UserInfoList plat=new UserInfoList();
			    plat.setId(object[0]==null?"":object[0]+"");
				plat.setUsername(object[1]==null?"":object[1]+"");
				plat.setProvince(object[2]==null?"":object[2]+"");
				plat.setCity(object[3]==null?"":object[3]+"");
				plat.setCardType(object[4]==null?"":object[4]+"");
				plat.setInsertTime(QwyUtil.fmyyyyMMddHHmmss.parse(QwyUtil.fmyyyyMMddHHmmss.format(object[5])));
				plat.setRegistPlatform(object[6]==null?"":object[6]+"");
				plat.setRealName(object[7]==null?"":object[7]+"");
				plat.setSex(object[8]==null?"":object[8]+"");
				plat.setAge(object[9]==null?"":object[9]+"");
				plat.setBirthday(object[10]==null?"":object[10].toString().trim().substring(object[10].toString().indexOf("-")+1, 10)+"");
			    if(object[13]==null){
			    	plat.setLevel("0");
			    }
			    else{
			    	money = Double.parseDouble(object[13]+"");
					plat.setLevel(userLevel(money/10000));
			    }

				plat.setIsBindBank(object[12]==null?"":object[12]+"");
				plat.setInMoney(object[13]==null?"":object[13]+"");
				meowPay.add(plat);

			}
		}
		return meowPay;
	}
	public String userLevel(double money){
		if(money >5 && money <=10)
			return "1";
		else if(money>10 && money <=50)
			return "2";
		else if(money>50 && money <=150)
			return "3";
		else if(money>150 && money <=300)
			return "4";
		else if(money>300 && money <=500)
			return "5";
		else if(money>500)
			return "6";
		else
			return "0";
		
	}
//	@SuppressWarnings("unchecked")
//	public PageUtil<Users> findUsersByChannel(PageUtil<Users> pageUtil, String channel, String username,
//			String insertTime, String isbindbank) {
//		try {
//		List<Object> list = new ArrayList<Object>();
//		StringBuffer buffer = new StringBuffer();
//		buffer.append(" FROM Users us");
//		buffer.append(" WHERE 1=1");
//		if (!QwyUtil.isNullAndEmpty(channel)) {
//			// if(channel.equals("0")){
//			// /*buffer.append(" AND ( us.registChannel = 0 ");
//			// buffer.append(" OR us.registChannel = '' ");
//			// buffer.append(" OR us.registChannel is null ) ");*/
//			// }else{
//			buffer.append(" AND us.registChannel = ? ");
//			list.add(channel);
//			// }
//		}
//		if (!QwyUtil.isNullAndEmpty(username)) {
//			buffer.append(" AND (");
//			buffer.append("  us.username = ? ");
//			list.add(DESEncrypt.jiaMiUsername(username));
//			buffer.append(" OR us.id = '" + username + "' ");
//			buffer.append(" OR us.usersInfo.realName = ? ");
//			list.add(username);
//			buffer.append(" )");
//		}
//		// 发布时间
//		if (!QwyUtil.isNullAndEmpty(insertTime)) {
//			String[] time = QwyUtil.splitTime(insertTime);
//			if (time.length > 1) {
//				buffer.append(" AND us.insertTime >= ? ");
//				list.add(QwyUtil.fmMMddyyyy.parse(time[0]));
//				buffer.append(" AND us.insertTime <= ? ");
//				list.add(QwyUtil.fmMMddyyyy.parse(time[1]));
//			} else {
//				buffer.append(" AND us.insertTime >= ? ");
//				list.add(QwyUtil.fmMMddyyyyHHmmss.parse(time[0] + " 00:00:00"));
//				buffer.append(" AND us.insertTime <= ? ");
//				list.add(QwyUtil.fmMMddyyyyHHmmss.parse(time[0] + " 23:59:59"));
//			}
//		}
//		if (!QwyUtil.isNullAndEmpty(isbindbank)) {
//			buffer.append(" AND us.usersInfo.isBindBank = ? ");
//			list.add(isbindbank);
//		}
//		buffer.append(" ORDER BY us.insertTime DESC ");
//		return dao.getByHqlAndHqlCount(pageUtil, buffer.toString(), buffer.toString(), list.toArray());
//	}

	/**
	 * 统计各省份人数
	 * 
	 * @param pageUtil
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public PageUtil<Region> loadProvince(PageUtil pageUtil) throws Exception {
		try {
			List<Region> regionList = new ArrayList<Region>();

			String hql = "select province,COUNT(province) from users where 1=1 GROUP BY province HAVING province IS NOT NULL ORDER BY COUNT(province) DESC ";
			StringBuffer buffer = new StringBuffer();
			buffer.append("  SELECT COUNT(t.province) FROM (");
			buffer.append(hql);
			buffer.append(") t ");
			PageUtil<Object[]> page = dao.getBySqlAndSqlCount(pageUtil, hql, buffer.toString(), null);
			// list=dao.LoadAllSql("select province,COUNT(province) from users
			// where 1=1 GROUP BY province HAVING province IS NOT NULL ORDER BY
			// COUNT(province) DESC ", null);
			for (int i = 0; i < page.getList().size(); i++) {
				Object[] objects = page.getList().get(i);
				Region region = new Region();
				region.setProvince(objects[0] + "");
				region.setUsersCount(objects[1] + "");
				regionList.add(region);
			}
			pageUtil.setList(regionList);
			return pageUtil;
		} catch (Exception e) {
			// TODO: handle exception
			log.error("操作异常: ", e);
		}
		return null;

	}

	/**
	 * 统计省份下属各城市人数
	 * 
	 * @param pageUtil
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public PageUtil<Region> loadCity(String province, PageUtil pageUtil) throws Exception {
		try {
			List<Region> regionList = new ArrayList<Region>();
			String hql = "select city,COUNT(city) from users where province = '" + province
					+ "' GROUP BY city HAVING city IS NOT NULL ORDER BY COUNT(city) DESC ";
			StringBuffer buffer = new StringBuffer();
			buffer.append("  SELECT COUNT(t.city) FROM (");
			buffer.append(hql);
			buffer.append(") t ");
			PageUtil<Object[]> page = dao.getBySqlAndSqlCount(pageUtil, hql, buffer.toString(), null);
			for (int i = 0; i < page.getList().size(); i++) {
				Object[] objects = page.getList().get(i);
				Region region = new Region();
				region.setCity(objects[0] + "");
				region.setUsersCount(objects[1] + "");
				regionList.add(region);
			}
			pageUtil.setList(regionList);
			return pageUtil;
		} catch (Exception e) {
			// TODO: handle exception
			log.error("操作异常: ", e);
		}
		return null;

	}

	/**
	 * 统计异常省份或城市的人数
	 * 
	 * @param pageUtil
	 * @return
	 * @throws Exception
	 */
	public String getOthsers(String province, String city) throws Exception {
		try {
			String count;
			StringBuffer buffer = new StringBuffer();
			buffer.append("  SELECT count(*) FROM users where 1=1");
			if (!QwyUtil.isNullAndEmpty(province)) {
				buffer.append(" and province= '" + province + "'");
			} else if (QwyUtil.isNullAndEmpty(province)) {
				buffer.append(" and province is null");
			}
			if (!QwyUtil.isNullAndEmpty(city)) {
				buffer.append(" and city= '" + city + "'");
			}

			count = String.valueOf(dao.getSqlCount(buffer.toString(), null));
			return count;
		} catch (Exception e) {
			// TODO: handle exception
			log.error("操作异常: ", e);
		}
		return null;

	}

	public List<Age> loadSex() throws Exception {
		try {
			StringBuffer buffer = new StringBuffer();
			buffer.append("SELECT ");
			buffer.append("(SELECT COUNT(*) FROM users_info WHERE sex IN('男','女')), ");
			buffer.append(
					"(SELECT SUM(in_money) FROM investors i LEFT JOIN users_info  ui ON ui.users_id=i.users_id WHERE sex IN('男','女') AND i.investor_status BETWEEN 1 AND 3),  ");
			buffer.append(
					"(SELECT COUNT(*) FROM investors i LEFT JOIN users_info  ui ON ui.users_id=i.users_id WHERE sex IN('男','女') AND i.investor_status BETWEEN 1 AND 3) ");
			buffer.append(" UNION ALL  ");
			buffer.append("SELECT ");
			buffer.append("(SELECT COUNT(*) FROM users_info WHERE sex='男'), ");
			buffer.append(
					"(SELECT SUM(in_money) FROM investors i LEFT JOIN users_info  ui ON ui.users_id=i.users_id WHERE sex='男' AND i.investor_status BETWEEN 1 AND 3),  ");
			buffer.append(
					"(SELECT COUNT(*) FROM investors i LEFT JOIN users_info  ui ON ui.users_id=i.users_id WHERE sex='男'AND i.investor_status BETWEEN 1 AND 3) ");
			buffer.append(" UNION ALL  ");
			buffer.append("SELECT ");
			buffer.append("(SELECT COUNT(*) FROM users_info WHERE sex='女'),");
			buffer.append(
					"(SELECT SUM(in_money) FROM investors i LEFT JOIN users_info  ui ON ui.users_id=i.users_id WHERE sex='女' AND i.investor_status BETWEEN 1 AND 3), ");
			buffer.append(
					"(SELECT COUNT(*) FROM investors i LEFT JOIN users_info  ui ON ui.users_id=i.users_id WHERE sex='女' AND i.investor_status BETWEEN 1 AND 3)  ");

			List<Object[]> list = dao.LoadAllSql(buffer.toString(), null);
			List<Age> ageList = new ArrayList<Age>();
			String titleCount = "";
			if (list.size() > 0) {
				for (int i = 0; i < list.size(); i++) {
					Object[] objects = list.get(i);
					Age age = new Age();
					age.setRsCount(objects[0] + "");
					if (i == 0) {
						age.setSexChina("所有");
						age.setRate(1f);
						titleCount = age.getRsCount();
					} else if (i == 1) {
						age.setSexChina("男");
						float maleRate = Float.valueOf(age.getRsCount()).floatValue()
								/ Float.valueOf(titleCount).floatValue();
						age.setRate(maleRate);
					} else if (i == 2) {
						age.setSexChina("女");
						float femaleRate = Float.valueOf(age.getRsCount()).floatValue()
								/ Float.valueOf(titleCount).floatValue();
						age.setRate(femaleRate);
					}
					if (QwyUtil.isNullAndEmpty(objects[1])) {
						objects[1] = 0;
					}
					age.setJeCount(objects[1] + "");
					age.setCsCount(objects[2] + "");
					ageList.add(age);
				}
			}
			return ageList;
		} catch (Exception e) {
			// TODO: handle exception
			log.error("操作异常: ", e);
		}
		return null;
	}

	/**
	 * 获取用户年龄分布信息
	 * 
	 * @param registPlatform
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<Age> loadAge(String registPlatform) throws Exception {
		try {
			StringBuffer buffer = new StringBuffer();
			buffer.append("select ");
			// 人数
			buffer.append("(SELECT COUNT(*) FROM users_info ui LEFT JOIN users u on ui.users_id=u.id  ");
			buffer.append("  WHERE age>0 ");
			if (!QwyUtil.isNullAndEmpty(registPlatform) && !"all".equals(registPlatform)) {
				buffer.append(" AND u.regist_platform='" + registPlatform + "'");
			}
			buffer.append("),");
			// 投资总额
			buffer.append(
					"(SELECT SUM(in_money) FROM investors i , users_info  ui LEFT JOIN users u ON ui.users_id=u.id ");
			buffer.append(" WHERE ui.users_id=i.users_id AND age>0 AND i.investor_status BETWEEN 1 AND 3 ");
			if (!QwyUtil.isNullAndEmpty(registPlatform) && !"all".equals(registPlatform)) {
				buffer.append(" AND u.regist_platform='" + registPlatform + "'");
			}
			buffer.append("),");
			// 投资总数
			buffer.append("(SELECT COUNT(*) FROM investors i , users_info  ui LEFT JOIN users u ON ui.users_id=u.id ");
			buffer.append(" WHERE ui.users_id=i.users_id AND age>0 AND i.investor_status BETWEEN 1 AND 3 ");
			if (!QwyUtil.isNullAndEmpty(registPlatform) && !"all".equals(registPlatform)) {
				buffer.append(" AND u.regist_platform='" + registPlatform + "'");
			}
			buffer.append(")");
			buffer.append(" UNION ALL ");
			buffer.append("select ");
			// [1,20)，人数
			buffer.append("(SELECT COUNT(*) FROM users_info ui LEFT JOIN users u on ui.users_id=u.id  ");
			buffer.append("  WHERE age>0 AND age<20");
			if (!QwyUtil.isNullAndEmpty(registPlatform) && !"all".equals(registPlatform)) {
				buffer.append(" AND u.regist_platform='" + registPlatform + "'");
			}
			buffer.append("),");
			// [1,20)，投资总额
			buffer.append(
					"(SELECT SUM(in_money) FROM investors i , users_info  ui LEFT JOIN users u ON ui.users_id=u.id ");
			buffer.append(" WHERE ui.users_id=i.users_id AND age>0 AND age<20 AND i.investor_status BETWEEN 1 AND 3");
			if (!QwyUtil.isNullAndEmpty(registPlatform) && !"all".equals(registPlatform)) {
				buffer.append(" AND u.regist_platform='" + registPlatform + "' ");
			}
			buffer.append("),");
			// [1,20)，投资总数
			buffer.append("(SELECT COUNT(*) FROM investors i , users_info  ui LEFT JOIN users u ON ui.users_id=u.id ");
			buffer.append(" WHERE ui.users_id=i.users_id AND age>0 AND age<20 AND i.investor_status BETWEEN 1 AND 3 ");
			if (!QwyUtil.isNullAndEmpty(registPlatform) && !"all".equals(registPlatform)) {
				buffer.append(" AND u.regist_platform='" + registPlatform + "'");
			}
			buffer.append(")");
			buffer.append(" UNION ALL ");
			buffer.append("select ");
			// [20,30)，人数
			buffer.append("(SELECT COUNT(*) FROM users_info ui LEFT JOIN users u on ui.users_id=u.id  ");
			buffer.append("  WHERE age>19 AND age<30");
			if (!QwyUtil.isNullAndEmpty(registPlatform) && !"all".equals(registPlatform)) {
				buffer.append(" AND u.regist_platform='" + registPlatform + "'");
			}
			buffer.append("),");
			// [20,30)，投资总额
			buffer.append(
					"(SELECT SUM(in_money) FROM investors i , users_info  ui LEFT JOIN users u ON ui.users_id=u.id ");
			buffer.append(" WHERE ui.users_id=i.users_id AND age>19 AND age<30 AND i.investor_status BETWEEN 1 AND 3");
			if (!QwyUtil.isNullAndEmpty(registPlatform) && !"all".equals(registPlatform)) {
				buffer.append(" AND u.regist_platform='" + registPlatform + "' ");
			}
			buffer.append("),");
			// [20,30)，投资总数
			buffer.append("(SELECT COUNT(*) FROM investors i , users_info  ui LEFT JOIN users u ON ui.users_id=u.id ");
			buffer.append(" WHERE ui.users_id=i.users_id AND age>19 AND age<30 AND i.investor_status BETWEEN 1 AND 3 ");
			if (!QwyUtil.isNullAndEmpty(registPlatform) && !"all".equals(registPlatform)) {
				buffer.append(" AND u.regist_platform='" + registPlatform + "'");
			}
			buffer.append(")");
			buffer.append(" UNION ALL ");
			buffer.append("select ");
			// [30,40)，人数
			buffer.append("(SELECT COUNT(*) FROM users_info ui LEFT JOIN users u on ui.users_id=u.id  ");
			buffer.append("  WHERE age>29 AND age<40");
			if (!QwyUtil.isNullAndEmpty(registPlatform) && !"all".equals(registPlatform)) {
				buffer.append(" AND u.regist_platform='" + registPlatform + "'");
			}
			buffer.append("),");
			// [30,40)，投资总额
			buffer.append(
					"(SELECT SUM(in_money) FROM investors i , users_info  ui LEFT JOIN users u ON ui.users_id=u.id ");
			buffer.append(" WHERE ui.users_id=i.users_id AND age>29 AND age<40 AND i.investor_status BETWEEN 1 AND 3");
			if (!QwyUtil.isNullAndEmpty(registPlatform) && !"all".equals(registPlatform)) {
				buffer.append(" AND u.regist_platform='" + registPlatform + "' ");
			}
			buffer.append("),");
			// [30,40)，投资总数
			buffer.append("(SELECT COUNT(*) FROM investors i , users_info  ui LEFT JOIN users u ON ui.users_id=u.id ");
			buffer.append(" WHERE ui.users_id=i.users_id AND age>29 AND age<40 AND i.investor_status BETWEEN 1 AND 3 ");
			if (!QwyUtil.isNullAndEmpty(registPlatform) && !"all".equals(registPlatform)) {
				buffer.append(" AND u.regist_platform='" + registPlatform + "'");
			}
			buffer.append(")");
			buffer.append(" UNION ALL ");
			buffer.append("select ");
			// [40,50)，人数
			buffer.append("(SELECT COUNT(*) FROM users_info ui LEFT JOIN users u on ui.users_id=u.id  ");
			buffer.append("  WHERE age>39 AND age<50");
			if (!QwyUtil.isNullAndEmpty(registPlatform) && !"all".equals(registPlatform)) {
				buffer.append(" AND u.regist_platform='" + registPlatform + "'");
			}
			buffer.append("),");
			// [40,50)，投资总额
			buffer.append(
					"(SELECT SUM(in_money) FROM investors i , users_info  ui LEFT JOIN users u ON ui.users_id=u.id ");
			buffer.append(" WHERE ui.users_id=i.users_id AND age>39 AND age<50 AND i.investor_status BETWEEN 1 AND 3 ");
			if (!QwyUtil.isNullAndEmpty(registPlatform) && !"all".equals(registPlatform)) {
				buffer.append(" AND u.regist_platform='" + registPlatform + "'");
			}
			buffer.append("),");
			// [40,50)，投资总数
			buffer.append("(SELECT COUNT(*) FROM investors i , users_info  ui LEFT JOIN users u ON ui.users_id=u.id ");
			buffer.append(" WHERE ui.users_id=i.users_id AND age>39 AND age<50 AND i.investor_status BETWEEN 1 AND 3 ");
			if (!QwyUtil.isNullAndEmpty(registPlatform) && !"all".equals(registPlatform)) {
				buffer.append(" AND u.regist_platform='" + registPlatform + "'");
			}
			buffer.append(")");
			buffer.append(" UNION ALL ");
			buffer.append("select ");
			// [50,60)，人数
			buffer.append("(SELECT COUNT(*) FROM users_info ui LEFT JOIN users u on ui.users_id=u.id  ");
			buffer.append("  WHERE age>49 AND age<60");
			if (!QwyUtil.isNullAndEmpty(registPlatform) && !"all".equals(registPlatform)) {
				buffer.append(" AND u.regist_platform='" + registPlatform + "'");
			}
			buffer.append("),");
			// [50,60)，投资总额
			buffer.append(
					"(SELECT SUM(in_money) FROM investors i , users_info  ui LEFT JOIN users u ON ui.users_id=u.id ");
			buffer.append(" WHERE ui.users_id=i.users_id AND age>49 AND age<60 AND i.investor_status BETWEEN 1 AND 3 ");
			if (!QwyUtil.isNullAndEmpty(registPlatform) && !"all".equals(registPlatform)) {
				buffer.append(" AND u.regist_platform='" + registPlatform + "'");
			}
			buffer.append("),");
			// [50,60)，投资总数
			buffer.append("(SELECT COUNT(*) FROM investors i , users_info  ui LEFT JOIN users u ON ui.users_id=u.id ");
			buffer.append(" WHERE ui.users_id=i.users_id AND age>49 AND age<60 AND i.investor_status BETWEEN 1 AND 3 ");
			if (!QwyUtil.isNullAndEmpty(registPlatform) && !"all".equals(registPlatform)) {
				buffer.append(" AND u.regist_platform='" + registPlatform + "'");
			}
			buffer.append(")");
			buffer.append(" UNION ALL ");
			buffer.append("select ");
			// 大于60岁，人数
			buffer.append("(SELECT COUNT(*) FROM users_info ui LEFT JOIN users u on ui.users_id=u.id  ");
			buffer.append("  WHERE age>59");
			if (!QwyUtil.isNullAndEmpty(registPlatform) && !"all".equals(registPlatform)) {
				buffer.append(" AND u.regist_platform='" + registPlatform + "'");
			}
			buffer.append("),");
			// 大于60岁，投资总额
			buffer.append(
					"(SELECT SUM(in_money) FROM investors i , users_info  ui LEFT JOIN users u ON ui.users_id=u.id ");
			buffer.append(" WHERE ui.users_id=i.users_id AND age>59 AND i.investor_status BETWEEN 1 AND 3");
			if (!QwyUtil.isNullAndEmpty(registPlatform) && !"all".equals(registPlatform)) {
				buffer.append(" AND u.regist_platform='" + registPlatform + "'");
			}
			buffer.append("),");
			// 大于60岁，投资总数
			buffer.append("(SELECT COUNT(*) FROM investors i , users_info  ui LEFT JOIN users u ON ui.users_id=u.id ");
			buffer.append(" WHERE ui.users_id=i.users_id AND age>59 AND i.investor_status BETWEEN 1 AND 3");
			if (!QwyUtil.isNullAndEmpty(registPlatform) && !"all".equals(registPlatform)) {
				buffer.append(" AND u.regist_platform='" + registPlatform + "'");
			}
			buffer.append(")");
			List<Object[]> list = dao.LoadAllSql(buffer.toString(), null);
			List<Age> ageList = new ArrayList<Age>();
			Object[] objects = null;
			if (list.size() > 0) {
				for (int i = 0; i < list.size(); i++) {
					objects = list.get(i);
					Age age = new Age();
					if (i == 0) {
						age.setAgeCeng("所有年龄");
					} else if (i == 1) {
						age.setAgeCeng("0到20岁");
					} else if (i == 2) {
						age.setAgeCeng("20到30岁（包括20）");
					} else if (i == 3) {
						age.setAgeCeng("30到40岁（包括30）");
					} else if (i == 4) {
						age.setAgeCeng("40到50岁（包括40）");
					} else if (i == 5) {
						age.setAgeCeng("50到60岁（包括50）");
					} else if (i == 6) {
						age.setAgeCeng("60岁以上（包括60）");
					}
					age.setRsCount(objects[0] + "");
					if (QwyUtil.isNullAndEmpty(objects[1])) {
						objects[1] = 0;
					}
					age.setJeCount(objects[1] + "");
					age.setCsCount(objects[2] + "");
					ageList.add(age);
				}
			}
			return ageList;
		} catch (Exception e) {
			// TODO: handle exception
			log.error("操作异常: ", e);
		}
		return null;
	}

	/**
	 * 根据用户名查询用户信息
	 * 
	 * @param username
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Users> searchUsersInfo(String username) {
		List<Object> list = new ArrayList<Object>();
		StringBuffer buffer = new StringBuffer();
		buffer.append(" FROM Users us");
		buffer.append(" WHERE 1=1");
		if (!QwyUtil.isNullAndEmpty(username)) {
			buffer.append(" AND ");
			buffer.append("  us.username = ? ");
			list.add(DESEncrypt.jiaMiUsername(username));
		}
		return dao.LoadAll(buffer.toString(), list.toArray());
	}

	@SuppressWarnings("unchecked")
	public List<Bank> findBankList() {
		List<Bank> list = (List<Bank>) dao.LoadAll(" FROM Bank ", null);
		return list;
	}

	/**
	 * 银行投资统计
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Age> loadbank() {

		StringBuffer buffer = new StringBuffer();

		List<Bank> bankList = findBankList();

		if (bankList.size() > 0) {
			for (int i = 0; i < bankList.size(); i++) {
				buffer.append("SELECT ");
				buffer.append("'" + bankList.get(i).getBankName() + "' ,");
				buffer.append("(SELECT COUNT(*) FROM  account a WHERE a.status=0 AND a.bank_name='"
						+ bankList.get(i).getBankName() + "'), ");
				buffer.append(
						"(SELECT SUM(money) FROM  cz_record cz LEFT JOIN account a ON cz.account_id=a.id WHERE cz.status=1 AND cz.order_id IS NOT NULL AND a.bank_name='"
								+ bankList.get(i).getBankName() + "'),  ");
				buffer.append(
						"(SELECT COUNT(*) FROM  cz_record cz LEFT JOIN account a ON cz.account_id=a.id WHERE cz.status=1 AND cz.order_id IS NOT NULL AND a.bank_name='"
								+ bankList.get(i).getBankName() + "'), ");
				buffer.append(
						"(SELECT COUNT(*) FROM  cz_record cz LEFT JOIN account a ON cz.account_id=a.id WHERE cz.status=2 AND a.bank_name='"
								+ bankList.get(i).getBankName() + "')");
				if (i < bankList.size() - 1) {
					buffer.append(" UNION ALL  ");
				}

			}
		}

		List<Object[]> list = dao.LoadAllSql(buffer.toString(), null);
		List<Age> ageList = new ArrayList<Age>();
		if (list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				Object[] objects = list.get(i);
				Age age = new Age();
				age.setBankName(objects[0] + "");
				age.setRsCount(objects[1] + "");
				age.setJeCount(objects[2] + "");
				age.setCgCount(objects[3] + "");
				age.setSbCount(objects[4] + "");
				ageList.add(age);
			}

		}
		return ageList;

	}

}
