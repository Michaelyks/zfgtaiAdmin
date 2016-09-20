package com.zfgt.thread.action;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import yjpay.api.service.impl.PayAPIServiceImpl;

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
public class TxArriveQueryThread implements Runnable{

	private static Logger log = Logger.getLogger(TxArriveQueryThread.class);
	private  PayAPIServiceImpl payAPIService = new PayAPIServiceImpl();
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
				pageUtil = checkTxsqBean.loadRequestTxRecord(pageUtil, "1",true);
				List<TxRecord> listTxRecord= pageUtil.getList();
				if(QwyUtil.isNullAndEmpty(listTxRecord)){
					log.info("TxQueryThread没有要查询的提现记录: "+currentPage);
					break;
				}
				log.info("TxQueryThread要查询的提现记录: "+listTxRecord.size());
				if(!QwyUtil.isNullAndEmpty(listTxRecord)){
					for (TxRecord txRecord : listTxRecord) {
						if(!QwyUtil.isNullAndEmpty(txRecord.getRequestId())){
							String myBankJson=payAPIService.drawrecord(txRecord.getRequestId(), null);
							log.info("查询提现的记录"+myBankJson);
							JSONObject jb = JSONObject.fromObject(myBankJson);
							Object errorObj = jb.get("error_msg");
							if(!QwyUtil.isNullAndEmpty(errorObj)){
								continue;
//								txRecord.setStatus("2");
//								txRecord.setNote(errorObj.toString());
//								dao.saveOrUpdate(txRecord);
//								myWalletBean.addTotalMoneyLeftMoney(txRecord.getUsersId(), txRecord.getMoney(), "txfk", "", "提现失败,返款提现金额到用户帐号");
							}
							Object objStatus = jb.get("status");
							if(!QwyUtil.isNullAndEmpty(objStatus)){
								log.info(txRecord.getId()+objStatus);
								String status = objStatus.toString();
								if("DOING".equalsIgnoreCase(status)){
									log.info(txRecord.getId()+"查询提现正在处理中");
									//正在审核
									continue;
								}else if("FAILURE".equalsIgnoreCase(status)){
									log.info(txRecord.getId()+"查询提现失败");
									txRecord.setStatus("2");
									txRecord.setCheckTime(new Date());
									txRecord.setNote("提现失败");
									dao.saveOrUpdate(txRecord);
									log.info("状态"+txRecord.getStatus());
									myWalletBean.addTotalMoneyLeftMoney(txRecord.getUsersId(), txRecord.getMoney(), "txfk", "", "提现失败,返款提现金额到用户帐号");
									continue;
								}else if("REFUND".equalsIgnoreCase(status)){
									log.info(txRecord.getId()+"查询提现提现退回");
									//提现退回
									txRecord.setStatus("2");
									txRecord.setNote("提现退回");
									txRecord.setCheckTime(new Date());
									dao.saveOrUpdate(txRecord);
									log.info("状态"+txRecord.getStatus());
									myWalletBean.addTotalMoneyLeftMoney(txRecord.getUsersId(), txRecord.getMoney(), "txfk", "", "提现失败,返款提现金额到用户帐号");
									continue;
								}else if("SUCCESS".equalsIgnoreCase(status)){
									log.info(txRecord.getId()+"查询提现提现成功");
									//提现已到账
									//提现失败
									String yblsh = jb.getString("ybdrawflowid");
									if(QwyUtil.isNullAndEmpty(txRecord.getYbOrderId())){
										txRecord.setYbOrderId(yblsh);
									}
									txRecord.setStatus("1");
									txRecord.setNote("提现成功");
									txRecord.setCheckTime(new Date());
									dao.saveOrUpdate(txRecord);
									log.info("状态"+txRecord.getStatus());
									log.info(txRecord.getId()+"修改提现提现成功");
									continue;
								}else if("UNKNOW".equalsIgnoreCase(status)){
									log.info(txRecord.getId()+"查询提现提现异常");
									//未知
									txRecord.setStatus("2");
									txRecord.setNote("提现异常");
									txRecord.setCheckTime(new Date());
									dao.saveOrUpdate(txRecord);
									log.info("状态"+txRecord.getStatus());
									myWalletBean.addTotalMoneyLeftMoney(txRecord.getUsersId(), txRecord.getMoney(), "txfk", "", "提现失败,返款提现金额到用户帐号");
									continue;
								}
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
