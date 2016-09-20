package com.zfgt.admin.product.bean;

import java.util.ArrayList;
import java.util.Date;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.zfgt.admin.product.dao.RechargeDAO;
import com.zfgt.common.bean.MyWalletBean;
import com.zfgt.common.bean.YiBaoPayBean;
import com.zfgt.common.util.DESEncrypt;
import com.zfgt.common.util.PageUtil;
import com.zfgt.common.util.QwyUtil;
import com.zfgt.orm.TxRecord;
import com.zfgt.thread.action.TxRequestThread;


/**后台管理--审核提现的Bean层;
 * @author qwy
 *
 * @createTime 2015-05-28 01:33:11
 */
@Service
public class CheckTxsqBean {
	private static Logger log = Logger.getLogger(CheckTxsqBean.class);
	@Resource
	private RechargeDAO dao;
	@Resource
	private YiBaoPayBean yiBaoPayBean;
	@Resource
	private MyWalletBean myWalletBean;
	/**获得提现的记录;根据状态来查找;
	 * @param pageUtil 分页对象;
	 * @param status 状态; all:全部; 0:待审核; 1:提现失败; 2:提现成功;3:正在审核 
	 * @param isdate 是否需要与现在时间间隔1天的数据
	 * @return
	 * @throws Exception 
	 */
	public PageUtil<TxRecord> loadTxRecord(PageUtil<TxRecord> pageUtil,String status,boolean isdate) throws Exception {
		return loadTxRecord(pageUtil, status, isdate,null,null);
	}	
	
	/**获得提现的记录;根据状态来查找;
	 * @param pageUtil 分页对象;
	 * @param status 状态; all:全部; 0:待审核; 1:提现失败; 2:提现成功;3:正在审核 
	 * @param isdate 是否需要与现在时间间隔1天的数据
	 * @param name 用户名
	 * @param insertTime  提现时间
	 * @return
	 * @throws Exception 
	 */
	public PageUtil<TxRecord> loadTxRecord(PageUtil<TxRecord> pageUtil,String status,String name, String insertTime) throws Exception{
		return loadTxRecord(pageUtil, status,false, name, insertTime);
	}
	
	/**获得提现的记录;根据状态来查找;
	 * @param pageUtil 分页对象;
	 * @param status 状态; all:全部; 0:待审核; 1:提现失败; 2:提现成功;3:正在审核 
	 * @param isdate 是否需要与现在时间间隔1天的数据
	 * @param name 用户名
	 * @param insertTime  提现时间
	 * @return
	 * @throws Exception 
	 */
	@SuppressWarnings("unchecked")
	public PageUtil<TxRecord> loadTxRecord(PageUtil<TxRecord> pageUtil,String status,boolean isdate, String name, String insertTime) throws Exception{
		ArrayList<Object> ob = new ArrayList<Object>();
		StringBuffer hql = new StringBuffer();
		hql.append(" FROM TxRecord tx ");
		hql.append(" WHERE 1 = 1 ");
		if(!"all".equals(status)){
			hql.append(" AND tx.status = ? ");
			ob.add(status);
		}
		if(isdate){
			hql.append(" AND date_add_interval(DATE_FORMAT(tx.checkTime,'%Y-%m-%d'), 1, DAY) <=NOW() ");
		}
		if(!QwyUtil.isNullAndEmpty(name)){
			hql.append(" AND tx.users.username = ? ");
			ob.add(DESEncrypt.jiaMiUsername(name));
		}
		//充值时间
		if(!QwyUtil.isNullAndEmpty(insertTime)){
			String [] time=QwyUtil.splitTime(insertTime);
			if(time.length>1){
				hql.append(" AND tx.insertTime >= ? ");
				ob.add(QwyUtil.fmMMddyyyy.parse(time[0]));
				hql.append(" AND tx.insertTime <= ? ");
				ob.add(QwyUtil.fmMMddyyyy.parse(time[1]));
			}else{
				hql.append(" AND tx.insertTime >= ? ");
				ob.add(QwyUtil.fmMMddyyyyHHmmss.parse(time[0]+" 00:00:00"));
				hql.append(" AND tx.insertTime <= ? ");
				ob.add(QwyUtil.fmMMddyyyyHHmmss.parse(time[0]+" 23:59:59"));
			}
		}
		hql.append(" ORDER BY tx.insertTime DESC, tx.checkTime ASC ");
		return (PageUtil<TxRecord>)dao.getPage(pageUtil, hql.toString(), ob.toArray());
	}
	
	/**
	 * 查询6个小时前正在提现的记录
	 * @param pageUtil
	 * @param txStatus 提现状态 0:未操作,1:已操作
	 * @param isHour 是否查询6小时记录
	 * @return
	 * @throws Exception
	 */
	public PageUtil<TxRecord> loadRequestTxRecord(PageUtil<TxRecord> pageUtil,String txStatus,boolean isHour) throws Exception{
		ArrayList<Object> ob = new ArrayList<Object>();
		StringBuffer hql = new StringBuffer();
		hql.append(" FROM TxRecord tx ");
		
		hql.append(" WHERE tx.status = '0' ");
		if (isHour) {
			hql.append(" AND date_add_interval(tx.insertTime, 6, HOUR) <=NOW() ");
		}
		if(!QwyUtil.isNullAndEmpty(txStatus)){
			hql.append(" AND tx.txStatus=? ");
			ob.add(txStatus);
		}
		hql.append(" ORDER BY tx.insertTime ASC ");
		return (PageUtil<TxRecord>)dao.getPage(pageUtil, hql.toString(), ob.toArray());
	}
	
	/**
	 * 请求提现
	 * @param txId
	 * @return
	 */
	public boolean requestTx(String txId){
		try {
			if (!QwyUtil.isNullAndEmpty(txId)) {
				TxRecord txRecord = (TxRecord) dao.findById(new TxRecord(),txId);
				if (!QwyUtil.isNullAndEmpty(txId)) {
					String myjson = yiBaoPayBean.withdraw(
							txRecord.getRequestId(), txRecord.getUsersId(),
							txRecord.getMoney().intValue(),
							txRecord.getDrawType(), txRecord.getUserIp());
					log.info(txRecord.getId() + "请求提现的记录" + myjson);
					JSONObject jb = JSONObject.fromObject(myjson);
					String status = QwyUtil.get(jb, "status");
					if("SUCCESS".equalsIgnoreCase(status)){
						log.info(txRecord.getId()+"请求提现的成功记录");
						//提交提现成功;次日到账;直接扣除冻结金额;修改于2015-06-03 15:23:28;
						String yblsh = jb.getString("ybdrawflowid");
						txRecord.setYbOrderId(yblsh);
						txRecord.setTxStatus("1");
						dao.saveOrUpdate(txRecord);
						return true;
					} else {
						log.info(txRecord.getId() + "请求提现失败");
						String yblsh = QwyUtil.get(jb, "message");
						txRecord.setYbOrderId(yblsh);
						txRecord.setStatus("2");
						txRecord.setTxStatus("1");
						txRecord.setCheckTime(new Date());
						dao.saveOrUpdate(txRecord);
						myWalletBean.addTotalMoneyLeftMoney(
								txRecord.getUsersId(), txRecord.getMoney(),
								"txfk", "", "提现失败,返款提现金额到用户帐号");
						return true;
					}
				} else {
					log.info(txRecord.getId() + "请求提现的失败");
					txRecord.setStatus("2");
					txRecord.setTxStatus("1");
					txRecord.setCheckTime(new Date());
					dao.saveOrUpdate(txRecord);
					myWalletBean.addTotalMoneyLeftMoney(txRecord.getUsersId(),
									txRecord.getMoney(), "txfk", "",
									"提现失败,返款提现金额到用户帐号");
					return true;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage(), e);
		}
		return false;
	}
		
}
