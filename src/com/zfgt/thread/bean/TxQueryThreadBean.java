package com.zfgt.thread.bean;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.jfree.util.Log;
import org.springframework.stereotype.Service;

import com.zfgt.admin.product.bean.CheckTxsqBean;
import com.zfgt.common.bean.YiBaoPayBean;
import com.zfgt.common.util.PageUtil;
import com.zfgt.orm.TxRecord;
import com.zfgt.thread.dao.ThreadDAO;

/**提现查询接口;<br>
 * 用于查询提现是否成功,修改数据库的状态;
 * @author qwy
 *
 * @createTime 2015-06-02 01:47:36
 */
@Service
public class TxQueryThreadBean {
	private Logger log = Logger.getLogger(TxQueryThreadBean.class);
	@Resource
	private ThreadDAO dao;
	@Resource
	private CheckTxsqBean checkTxsqBean;
	@Resource
	private YiBaoPayBean yiBaoPayBean;
	
	/**查找正在审核的提现记录;<br>
	 * 查找超过审核一天的记录,因为平台的提现是T+1的,所以,当天提现的,要到第二天再去查询,该提现的结果;是否成功;
	 * @param pageUtil TxRecord
	 * @return
	 * @throws Exception 
	 */
	public PageUtil<TxRecord> getTxRecordByChecking(PageUtil<TxRecord> pageUtil) throws Exception{
		return checkTxsqBean.loadTxRecord(pageUtil, "3", true);
	}
	
	/**查询提现记录接口;
	 * @param usersId 用户id
	 * @param requestId 请求ID;
	 * @return
	 */
	public String queryTxByRequestId(long usersId,String requestId){
		String json = "";
		try {
			json = yiBaoPayBean.withdrawQuery(usersId, requestId, null);
			Log.info(json);
		} catch (Exception e) {
			log.error("操作异常: ",e);
			e.printStackTrace();
		}
		return json;
		
	}

}
