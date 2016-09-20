package com.zfgt.common.bean;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.zfgt.common.dao.InviteDAO;
import com.zfgt.common.dao.MyWalletDAO;
import com.zfgt.common.dao.ObjectDAO;
import com.zfgt.common.util.DESEncrypt;
import com.zfgt.common.util.PageUtil;
import com.zfgt.common.util.PropertiesUtil;
import com.zfgt.common.util.QwyUtil;
import com.zfgt.orm.InterestDetails;
import com.zfgt.orm.Investors;
import com.zfgt.orm.Invite;
import com.zfgt.orm.InviteEarn;
import com.zfgt.orm.Platform;

/**
 * @author 覃文勇
 * @createTime 2015-11-6上午10:14:59
 */
@Service
public class InviteBean {
	@Resource
	private InviteDAO dao;

	/**
	 * 
	 * @param inviteId
	 *            邀请人id
	 * @param beInvitedId
	 *            被邀请人id
	 * @param type
	 *            类型 1：邀请送积分
	 * @return
	 * @throws Exception
	 */
	public String saveInvite(long inviteId, long beInvitedId, String type) throws Exception {
		Invite invite = new Invite();
		invite.setBeInvitedId(beInvitedId);
		invite.setInviteId(inviteId);
		invite.setInsertTime(new Date());
		invite.setStatus("0");
		invite.setType(type);// 邀请好友
		dao.save(invite);
		return null;

	}

	// 每个自然月内有效奖励注册用户最多15个
	@SuppressWarnings("unchecked")
	public Boolean isValidInvite(long inviteId) {
		Boolean isOk = false;
		try {
			ArrayList<Object> ob = new ArrayList<Object>();
			StringBuffer buff = new StringBuffer();
			buff.append("select * from invite i where 1=1 ");
			if (!QwyUtil.isNullAndEmpty(inviteId)) {
				buff.append(" and i.invite_id=? ");
				ob.add(inviteId);
			}
			String yymm = QwyUtil.fmyyMM.format(new Date());
			buff.append(" and DATE_FORMAT(i.insert_time,'%Y-%m')='" + yymm + "'");
			buff.append(" ORDER BY i.insert_time DESC ");
			List<Invite> inviteList = dao.findAdvListSql(buff.toString(), ob.toArray(), 1, 20);
			if (!QwyUtil.isNullAndEmpty(inviteList)) {
				if (inviteList.size() > 0 && inviteList.size() < 15) {
					isOk = true;
				}
			} else {
				isOk = true;
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return isOk;
	}

	/**
	 * 获取邀请记录
	 * 
	 * @param beInvitedId
	 *            被邀请人id
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Invite findByBeInvitedId(long beInvitedId) {
		Invite result = null;
		try {
			StringBuffer buff = new StringBuffer();
			buff.append(" FROM Invite i WHERE 1=1 ");
			buff.append(" AND i.beInvitedId= " + beInvitedId);
			buff.append(" AND i.type=1 ");
			List<Invite> list = dao.LoadAll(buff.toString(), null);
			if (!QwyUtil.isNullAndEmpty(list)) {
				result = list.get(0);
			}
			return result;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		return null;

	}

	/**
	 * 修改邀请记录状态
	 * 
	 * @param invite
	 * @return
	 */
	public String updatInvite(Invite invite) {
		try {
			if ("1".equals(invite.getType())) {// 邀请好友
				invite.setStatus("1");// 已投资
				invite.setUpdateTime(new Date());
				dao.saveOrUpdate(invite);
			}
			return null;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return null;
	}

	public double countEarn(long copies, String productName) {
		// 计算奖励(单位分)
		double earn = 0d;
		try {
			if (productName.contains("双季丰")) {
				earn = QwyUtil.calcNumber(copies, 0.3, "*", 2).doubleValue();
			} else if (productName.contains("单季丰")) {
				earn = QwyUtil.calcNumber(copies, 0.2, "*", 2).doubleValue();
			} else if (productName.contains("月月赢")) {
				earn = QwyUtil.calcNumber(copies, 0.1, "*", 2).doubleValue();
			} else if (productName.contains("周周利")) {
				earn = QwyUtil.calcNumber(copies, 0.05, "*", 2).doubleValue();
			} else if (productName.contains("新手")) {
				earn = QwyUtil.calcNumber(copies, 0.03, "*", 2).doubleValue();
			}
			return earn;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0d;
	}

	/**
	 * 获取邀请好友的总人数
	 * 
	 * @param uid
	 * @return
	 */

	public int getInviteCount(Long uid) {
		StringBuffer hql = new StringBuffer();
		hql.append("FROM Invite invite ");
		hql.append("WHERE invite.inviteId = ? ");
		hql.append("AND invite.status = ? ");
		int num = dao.findCountByHql(hql.toString(), new Object[] { uid, "0" });
		return num > 0 ? num : 0;
	}

	/**加载邀请奖励记录,根据分页 用户;
	 * @param pageUtil 分页对象;
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public PageUtil<InviteEarn> getInviteEarnByPageUtil(
			PageUtil<InviteEarn> pageUtil, String[] status,long inviteId,String Type) {
		try {
			String st = "";
			if(QwyUtil.isNullAndEmpty(status)){
				st = "'0','1'";
			}else{
				st = QwyUtil.packString(status);
			}
			StringBuffer buff = new StringBuffer();
			buff.append("FROM InviteEarn inviteEarn ");
			buff.append("WHERE inviteEarn.status IN ("+st+") ");
			if(!QwyUtil.isNullAndEmpty(inviteId)){
				buff.append("AND inviteEarn.inviteId = "+inviteId );
			}
			if(!QwyUtil.isNullAndEmpty(Type)){
				buff.append(" AND inviteEarn.type ='"+Type+"'" );
			}
			buff.append(" ORDER BY  inviteEarn.insertTime DESC");
			return (PageUtil<InviteEarn>)dao.getPage(pageUtil, buff.toString(), null);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	/**加载邀请奖励记录,根据分页 用户;
	 * @param pageUtil 分页对象;
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public PageUtil<InviteEarn> getInviteEarnByPageUtil(
			PageUtil<InviteEarn> pageUtil, String[] status,String Type) {
		try {
			String st = "";
			if(QwyUtil.isNullAndEmpty(status)){
				st = "'0','1'";
			}else{
				st = QwyUtil.packString(status);
			}
			StringBuffer buff = new StringBuffer();
			buff.append("FROM InviteEarn inviteEarn ");
			buff.append("WHERE inviteEarn.status IN ("+st+") ");
			if(!QwyUtil.isNullAndEmpty(Type)){
				buff.append(" AND inviteEarn.type ='"+Type+"'" );
			}
			buff.append(" ORDER BY  inviteEarn.insertTime DESC");
			return (PageUtil<InviteEarn>)dao.getPage(pageUtil, buff.toString(), null);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	/**封装邀请投资奖励列表;
	 * @param 
	 * @param 
	 * @param 
	 * @return
	 */
	public Map<String,Object> getInviteEarnMap(InviteEarn inviteEarn) throws Exception{
		Map<String,Object> map = new HashMap<String,Object>();
		if(QwyUtil.isNullAndEmpty(inviteEarn)){
			return null;
		}
		map.put("inviteEarnId", inviteEarn.getId());
		map.put("beInvitePhone",QwyUtil.isNullAndEmpty(inviteEarn.getBeInvitePhone())? "未知号码" : DESEncrypt.jieMiUsername(inviteEarn.getBeInvitePhone()));
		map.put("earnTime", inviteEarn.getInsertTime());
		map.put("earnMoney", new BigDecimal(inviteEarn.getEarnMoney()).longValue());
		map.put("status", inviteEarn.getStatus());
		return map;
	}
	
}