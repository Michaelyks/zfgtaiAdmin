package com.zfgt.admin.product.bean;

import java.util.ArrayList;
import java.util.Date;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.zfgt.common.dao.ObjectDAO;
import com.zfgt.common.util.DESEncrypt;
import com.zfgt.common.util.PageUtil;
import com.zfgt.common.util.QwyUtil;
import com.zfgt.orm.Reflect;
import com.zfgt.orm.TxRecord;
import com.zfgt.orm.Users;
import com.zfgt.orm.WithdrawLog;

@Service
public class ReflectBean {
	@Resource(name = "objectDAO")
	private ObjectDAO dao;
	private static Logger log = Logger.getLogger(ReflectBean.class);

	/**
	 * 获取用户的真实姓名;
	 * 
	 * @param username
	 * @return
	 */
	public Users getUsersByUsername(String username) {
		Object ob = null;
		try {
			if (QwyUtil.isNullAndEmpty(username))
				return null;
			StringBuffer hql = new StringBuffer();
			hql.append("FROM Users us ");
			hql.append("WHERE us.username = ? ");
			ob = dao.findJoinActive(hql.toString(), new Object[] { DESEncrypt
					.jiaMiUsername(username.toLowerCase()) });
			if (ob != null) {
				return (Users) ob;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.error("操作异常: ", e);
		}
		return null;

	}

	public boolean updateTx(TxRecord tx, Reflect reflect) {
		try {
			if (!QwyUtil.isNullAndEmpty(tx)) {
				tx.setStatus("1");
				tx.setInsertTime(new Date());
				tx.setCheckTime(new Date());
				tx.setRequestId(reflect.getOrderId());
				tx.setYbOrderId(reflect.getAccounId());
				tx.setNote("提现成功");
				dao.saveOrUpdate(tx);
				return true;

			}

		} catch (Exception e) {
			// TODO: handle exception
		}
		return false;
	}

	@SuppressWarnings("unchecked")
	public TxRecord findTxRecord(String requestId) {
		try {
			Object ob = null;
			ArrayList<Object> objects = new ArrayList<Object>();
			StringBuffer hql = new StringBuffer();
			hql.append("FROM TxRecord d WHERE 1=1 ");
			if (!QwyUtil.isNullAndEmpty(requestId)) {
				hql.append(" AND d.requestId=?  ");
				objects.add(requestId);
			}
			ob = dao.findJoinActive(hql.toString(), objects.toArray());
			if (ob != null) {
				return (TxRecord) ob;
			}
		} catch (Exception e) {
			// TODO: handle exception
		}

		return null;
	}

	/**
	 * 添加手动提现记录
	 * 
	 * @param usersAdminId
	 * @param reflect
	 * @param tx
	 * @return
	 */
	public String addLog(Long usersAdminId, Reflect reflect, TxRecord tx) {
		try {
			WithdrawLog withdrawLog = new WithdrawLog();
			withdrawLog.setRequestId(reflect.getRequestId());// 原平台订单号
			withdrawLog.setRecordNumber(tx.getRecordNumber());// 原流水号
			withdrawLog.setUsersId(tx.getUsersId());// 用户id
			withdrawLog.setMoney(tx.getMoney());
			withdrawLog.setOldTime(tx.getInsertTime());// 原始时间
			withdrawLog.setStatus(tx.getStatus());
			withdrawLog.setType(tx.getType());
			withdrawLog.setErrorCode(tx.getErrorCode() + ":"
					+ tx.getYbOrderId());
			withdrawLog.setNote(tx.getNote());
			withdrawLog.setOrderId(reflect.getOrderId());// 商业订单号
			withdrawLog.setAccountNumber(reflect.getAccounId());// 银通订单号
			withdrawLog.setUsersAdminId(usersAdminId);// 操作人id
			withdrawLog.setInsertTime(new Date());// 插入时间
			return dao.saveAndReturnId(withdrawLog);
		} catch (Exception e) {
			// TODO: handle exception
			log.error("操作异常: ", e);
		}
		return null;
	}

	/**
	 * 查询手动提现记录表
	 * @param pageUtil
	 * @param userType
	 * @param deptId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public PageUtil<WithdrawLog> findList(PageUtil<WithdrawLog> pageUtil,
			String name,String insertTime) {
		try {

			ArrayList<Object> objects = new ArrayList<Object>();
			StringBuffer hql = new StringBuffer();
			hql.append(" FROM WithdrawLog w WHERE 1=1 ");
			
			if(!QwyUtil.isNullAndEmpty(name)){
				hql.append(" AND w.users.username = ? ");
				objects.add(DESEncrypt.jiaMiUsername(name));
			}
			if(!QwyUtil.isNullAndEmpty(insertTime)){
				String [] time=QwyUtil.splitTime(insertTime);
				if(time.length>1){
					hql.append(" AND w.insertTime >= ? ");
					objects.add(QwyUtil.fmMMddyyyy.parse(time[0]));
					hql.append(" AND w.insertTime <= ? ");
					objects.add(QwyUtil.fmMMddyyyyHHmmss.parse(time[1]+" 23:59:59"));
				}else{
					hql.append(" AND w.insertTime >= ? ");
					objects.add(QwyUtil.fmMMddyyyyHHmmss.parse(time[0]+" 00:00:00"));
					hql.append(" AND w.insertTime <= ? ");
					objects.add(QwyUtil.fmMMddyyyyHHmmss.parse(time[0]+" 23:59:59"));
				}
			}
			hql.append(" ORDER BY w.insertTime DESC ");
			return (PageUtil<WithdrawLog>)dao.getPage(pageUtil, hql.toString(), objects.toArray());
		} catch (Exception e) {
			// TODO: handle exception
			log.error("操作异常: ", e);
		}

		return null;
	}

}
