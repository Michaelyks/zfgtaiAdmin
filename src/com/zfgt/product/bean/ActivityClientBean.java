package com.zfgt.product.bean;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.zfgt.account.bean.UserInfoBean;
import com.zfgt.account.bean.UserRechargeBean;
import com.zfgt.common.util.DESEncrypt;
import com.zfgt.common.util.LockHolder;
import com.zfgt.common.util.QwyUtil;
import com.zfgt.login.bean.RegisterUserBean;
import com.zfgt.orm.HdFlag;
import com.zfgt.orm.HdUsers;
import com.zfgt.orm.Users;
import com.zfgt.orm.UsersInfo;
import com.zfgt.product.dao.ProductCategoryDAO;

/**
 * 
 * @author oi
 * 活动业务层
 */
@Service
public class ActivityClientBean {
	private static Logger log = Logger.getLogger(ActivityClientBean.class);
	
	@Resource
	private ProductCategoryDAO dao;
	@Resource
	private UserRechargeBean userRechargeBean;
	@Resource
	private RegisterUserBean registerUserBean;
	
	/**
	 * 判断是否可参与活动
	 * @param mobileNum
	 * @param xlhNum
	 * @return
	 */
	public String checkMobileAndXlh(String mobileNum,String xlhNum){
		try {
			//防止刷投资券,根据用户名进行同步锁
			synchronized(LockHolder.getLock(mobileNum)){
				String sql = "select id from users WHERE username=?";
				String mobile = DESEncrypt.jiaMiUsername(mobileNum);
				List<Object> usersList = dao.LoadAllSql(sql, new String[]{mobile});
				String userId;
				if(usersList==null || usersList.size()==0){
					return "您还没有注册，请先注册！";
				}else {
					userId = usersList.get(0).toString();
				}
				
				sql = "select * from hd_flag WHERE status=0 AND NOW() BETWEEN insert_time AND end_time AND uniq='1' AND flag=?";
				List<Object> flagList = dao.LoadAllSql(sql, new String[]{xlhNum.toUpperCase()});
				String flagId = "";
				if(flagList==null || flagList.size()==0){
					return "请输入正确的序列号";
				}else {
					Object[] data = (Object[])flagList.get(0);
					flagId = data[0].toString();
				}
				
				sql = "select * from hd_users WHERE hd_flag_id=? and username=?";
				List<Object> hdUsersList = dao.LoadAllSql(sql, new String[]{flagId,mobile});
				if(hdUsersList!=null && hdUsersList.size()>0){
					return "您已经参与过该活动！";
				}
				boolean boo = userRechargeBean.sendHongBao(Long.valueOf(userId),5000,QwyUtil.addDaysFromOldDate(new Date(), 10).getTime(),"0",10001,"加群领取50元投资券");
				if(boo){
					HdUsers hdUsers = new HdUsers();
					hdUsers.setHdFlagId(flagId);
					hdUsers.setInsertTime(new Date());
					hdUsers.setNote("加群领取50元投资券");
					hdUsers.setUserId(Long.parseLong(userId));
					hdUsers.setUsername(mobile);
					insertHdUsers(hdUsers);
				}
			}
		} catch (Exception e) {
			log.error("操作异常: ",e);
			return "系统异常";
		}
		return "";
	}
	
	
	/**
	 * 判断是否可参与360注册送投资券活动
	 * @param mobileNum
	 * @param xlhNum
	 * @return
	 */
	public String checkMobileAndXlhSendTicket(String mobileNum){
		try {
			
			//防止刷投资券,根据用户名进行同步锁
			synchronized(LockHolder.getLock(mobileNum)){
				
				Users users=registerUserBean.getUsersByUsername(mobileNum);
//				String sql = "select id from users WHERE username=?";
//				String mobile = DESEncrypt.jiaMiUsername(mobileNum);
//				List<Object> usersList = dao.LoadAllSql(sql, new String[]{mobile});
//				String userId;
//				if(usersList==null || usersList.size()==0){
//					return "您还没有注册，请先注册！";
//				}else {
//					userId = usersList.get(0).toString();
//				}
				if(QwyUtil.isNullAndEmpty(users)){
					return "您还没有注册，请先注册！";
				}
				StringBuffer buffer=new StringBuffer();
				buffer.append(" FROM HdFlag hd ");
				buffer.append(" WHERE hd.flag = '360' ");
				List<HdFlag> hdFlags=dao.LoadAll(buffer.toString(), null);
				if(QwyUtil.isNullAndEmpty(hdFlags)){
					return "该活动！已结束";
				}
				HdFlag hdFlag=hdFlags.get(0);
				if(!(users.getInsertTime().after(hdFlag.getInsertTime())&&users.getInsertTime().before(hdFlag.getEndTime()))){
					return "亲，暂无资格哦!";
				}
				//if
//				sql = "select * from hd_flag WHERE status=0 AND NOW() BETWEEN insert_time AND end_time AND uniq='1' AND flag='360'";
//				List<Object> flagList = dao.LoadAllSql(sql, new String[]{xlhNum.toUpperCase()});
//				String flagId = "";
//				if(flagList==null || flagList.size()==0){
//					return "请输入正确的序列号";
//				}else {
//					Object[] data = (Object[])flagList.get(0);
//					flagId = data[0].toString();
//				}
				
//				sql = "select * from hd_users WHERE hd_flag_id=? and username=?";
//				List<Object> hdUsersList = dao.LoadAllSql(sql, new String[]{flagId,mobile});
//				if(hdUsersList!=null && hdUsersList.size()>0){
//					return "您已经参与过该活动！";
//				}
				mobileNum=DESEncrypt.jiaMiUsername(mobileNum);
				StringBuffer hdUsersBuffer=new StringBuffer();
				hdUsersBuffer.append(" FROM HdUsers hd ");
				hdUsersBuffer.append(" WHERE hd.hdFlagId = ? ");
				hdUsersBuffer.append(" AND hd.username = ? ");
				
				List<HdUsers> hdUsersList=dao.LoadAll(hdUsersBuffer.toString(), new String[]{hdFlag.getId()+"",mobileNum});
				if(!QwyUtil.isNullAndEmpty(hdUsersList)){
					return "您已经参与过该活动！";
				}
				boolean boo = userRechargeBean.sendHongBao(users.getId(),88800,QwyUtil.addDaysFromOldDate(new Date(), 10).getTime(),"0",10001,"360应用市场注册送888投资券");
				if(boo){
					HdUsers hdUsers = new HdUsers();
					hdUsers.setHdFlagId(hdFlag.getId()+"");
					hdUsers.setInsertTime(new Date());
					hdUsers.setNote("360应用市场注册送888投资券");
					hdUsers.setUserId(users.getId());
					hdUsers.setUsername(mobileNum);
					insertHdUsers(hdUsers);
				}
			}
		} catch (Exception e) {
			log.error("操作异常: ",e);
			return "系统异常";
		}
		return "";
	}
	
	/**
	 * hd_users表插入参与活动用户数据
	 * @param hdUsers
	 * @return
	 */
	public String insertHdUsers(HdUsers hdUsers){
		try{
			dao.save(hdUsers);
		}catch (Exception e) {
			log.error("操作异常: ",e);
			return "系统异常";
		}
		return "";
	}
	
}
