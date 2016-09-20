package com.zfgt.thread.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.zfgt.account.bean.AutoShiftToBean;
import com.zfgt.account.bean.CoinPurseFundRecordBean;
import com.zfgt.account.bean.CoinpPurseBean;
import com.zfgt.account.bean.SendRatesBean;
import com.zfgt.account.bean.SendRatesDetailBean;
import com.zfgt.account.bean.ShiftToBean;
import com.zfgt.account.dao.ShiftToDAO;
import com.zfgt.common.ApplicationContexts;
import com.zfgt.common.bean.MyWalletBean;
import com.zfgt.common.dao.MyWalletDAO;
import com.zfgt.common.util.QwyUtil;
import com.zfgt.login.bean.RegisterUserBean;
import com.zfgt.orm.AutoShiftTo;
import com.zfgt.orm.CoinPurse;
import com.zfgt.orm.SendRatesDetail;
import com.zfgt.orm.ShiftTo;
import com.zfgt.orm.SystemConfig;
import com.zfgt.orm.UsersInfo;

/**后台线程自动发放收益<br>
 * @author qwy
 *
 * @createTime 2015-4-28上午9:42:57
 */
@Service
public class AutoShiftTohread implements Runnable {
	private Logger log = Logger.getLogger(AutoShiftTohread.class);
	@Resource
	MyWalletBean walletBean;
	@Resource
	AutoShiftToBean autoShiftToBean;
	@Resource
	RegisterUserBean registerUserBean;
	@Resource
	CoinPurseFundRecordBean cpfrBean;
	@Resource
	CoinpPurseBean coinpPurseBean;
	@Resource
	ShiftToBean shiftToBean;
	private Integer pageSize = 50;
	
	
	@Override
	public void run() {
		
		synchronized (this) {
			/*ApplicationContext context = ApplicationContexts.getContexts();
			//SessionFactory sf = (SessionFactory) context.getBean("sessionFactory");
			PlatformTransactionManager tm = (PlatformTransactionManager) context.getBean("transactionManager");
			TransactionStatus ts = tm.getTransaction(new DefaultTransactionDefinition());*/
			//查询的50个人的ID
			if(new Date().getHours()!=0||new Date().getMinutes()>30){
				return;
			}
			try {
				int currentPage = 0;
				for (;;) {
					String usersIds="";
					currentPage++;
					List<AutoShiftTo> list=autoShiftToBean.findAutoShiftTo(currentPage, pageSize);
					if(QwyUtil.isNullAndEmpty(list)){
						log.info("退出自动转入线程");
						break;
					}
					for (AutoShiftTo autoShiftTo : list) {
						usersIds+=autoShiftTo.getUsersId()+",";
					}
					usersIds=usersIds.substring(0, usersIds.length()-1);
					List<UsersInfo> usersInfos=registerUserBean.findUsersInfosByUsersIds(usersIds);
					Map<String,Object> map=QwyUtil.ListToMap("usersId", list);
					for (UsersInfo usersInfo : usersInfos) {
						AutoShiftTo autoShiftTo=(AutoShiftTo) map.get(usersInfo.getUsersId()+"");
						//walletBean.subLeftMoney(autoShiftTo.getUsersId(), QwyUtil.calcNumber(usersInfo.getLeftMoney(), autoShiftTo.getLeftMoney(), "-",2).doubleValue(), "to",  "零钱包自动转入", "零钱包自动转入");
						Double num=QwyUtil.calcNumber(usersInfo.getLeftMoney(), autoShiftTo.getLeftMoney(), "-",2).doubleValue();
						if(num>0){
							shiftToBean.shift(autoShiftTo.getUsersId(), num);
						}
					}
					/*tm.commit(ts);*/
					list=null;
					
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				/*tm.rollback(ts);*/
				log.error("操作异常: ",e);
				log.error("进入修改产品状态的后台线程异常: ",e);
			}
		
		}
	}
	
	public static void main(String[] args) {
		System.out.println(new Date().getHours());
		if(new Date().getHours()!=0){
			System.out.println("000");
		}
		System.out.println(new Date().getMinutes());
		if(new Date().getMinutes()<5){
			System.out.println("123");
		}
		
	}

}
