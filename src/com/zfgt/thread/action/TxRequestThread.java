package com.zfgt.thread.action;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.zfgt.admin.product.bean.CheckTxsqBean;
import com.zfgt.common.bean.MyWalletBean;
import com.zfgt.common.bean.YiBaoPayBean;
import com.zfgt.common.util.PageUtil;
import com.zfgt.common.util.QwyUtil;
import com.zfgt.orm.TxRecord;
import com.zfgt.thread.bean.TxQueryThreadBean;
import com.zfgt.thread.dao.ThreadDAO;

/**查询提现接口;后台线程;<br>
 * 后台线程,自动查询提现接口;查询的都是审核后的第二天;<br>
 * @author qwy
 *
 * @createTime 2015-06-02 03:09:15
 */
@Service
public class TxRequestThread implements Runnable{

	private static Logger log = Logger.getLogger(TxRequestThread.class);
	@Resource
	private ThreadDAO dao;
	@Resource
	private TxQueryThreadBean bean;
	@Resource
	private YiBaoPayBean yiBaoPayBean;
	@Resource
	private CheckTxsqBean checkTxsqBean;
	@Resource
	private MyWalletBean myWalletBean;
	@Override
	public void run() {
		try {
			// TODO Auto-generated method stub
			PageUtil<TxRecord> pageUtil = new PageUtil<TxRecord>();
			pageUtil.setPageSize(50);
			int currentPage = 0;
			for (;;) {
				currentPage++;
				pageUtil.setCurrentPage(currentPage);
				pageUtil = checkTxsqBean.loadRequestTxRecord(pageUtil, "0",false);
				List<TxRecord> listTxRecord= pageUtil.getList();
				if(QwyUtil.isNullAndEmpty(listTxRecord)){
					log.info("TxRequestThread没有要查询的提现记录: "+currentPage);
					break;
				}
				log.info("TxRequestThread要查询的提现记录: "+listTxRecord.size());
				if(!QwyUtil.isNullAndEmpty(listTxRecord)){
					for (TxRecord txRecord : listTxRecord) {
						txRecord = (TxRecord) dao.findById(new TxRecord(),txRecord.getId());
						if("0".equals(txRecord.getStatus())&&"0".equals(txRecord.getTxStatus())){
							if(!QwyUtil.isNullAndEmpty(txRecord.getRequestId())){
								txRecord.setTxStatus("1");
								dao.saveOrUpdate(txRecord);
								String myjson=yiBaoPayBean.withdraw(txRecord.getRequestId(), txRecord.getUsersId(), txRecord.getMoney().intValue(), txRecord.getDrawType(), txRecord.getUserIp());
								log.info(txRecord.getId()+"请求提现的记录"+myjson);
								JSONObject jb = JSONObject.fromObject(myjson);
								String status = QwyUtil.get(jb, "status");
								if("SUCCESS".equalsIgnoreCase(status)){
									log.info(txRecord.getId()+"请求提现的成功记录");
									//提交提现成功;次日到账;直接扣除冻结金额;修改于2015-06-03 15:23:28;
									String yblsh = jb.getString("ybdrawflowid");
									txRecord.setYbOrderId(yblsh);
									dao.saveOrUpdate(txRecord);
									continue;
								}else{
									log.info(txRecord.getId()+"请求提现失败");
									String yblsh = QwyUtil.get(jb, "message");
									txRecord.setYbOrderId(yblsh);
									txRecord.setStatus("2");
									txRecord.setCheckTime(new Date());
									dao.saveOrUpdate(txRecord);
									myWalletBean.addTotalMoneyLeftMoney(txRecord.getUsersId(), txRecord.getMoney(), "txfk", "", "提现失败,返款提现金额到用户帐号");
									continue;
								}
							}else{
								log.info(txRecord.getId()+"请求提现的失败");
								txRecord.setStatus("2");
								txRecord.setCheckTime(new Date());
								dao.saveOrUpdate(txRecord);
								myWalletBean.addTotalMoneyLeftMoney(txRecord.getUsersId(), txRecord.getMoney(), "txfk", "", "提现失败,返款提现金额到用户帐号");
								continue;
							}
						}
					}
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.error("操作异常: ",e);
			e.printStackTrace();
		}
		
	}

}
