package com.zfgt.thread.bean;

import java.util.Date;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.zfgt.common.ApplicationContexts;
import com.zfgt.common.bean.MyWalletBean;
import com.zfgt.common.bean.SystemConfigBean;
import com.zfgt.common.util.DESEncrypt;
import com.zfgt.common.util.LockHolder;
import com.zfgt.common.util.PageUtil;
import com.zfgt.common.util.QwyUtil;
import com.zfgt.orm.InterestDetails;
import com.zfgt.orm.Investors;
import com.zfgt.orm.InviteEarn;
import com.zfgt.orm.Product;
import com.zfgt.thread.dao.ThreadDAO;

/**
 * 后台线程Bean层--结算理财产品(非新手专享产品,即product.productType=0的理财产品)
 * 
 * @author zlq
 *
 * @createTime 2016-09-11
 */
@Service
public class SendInviteEarnThreadBean {
	private Logger log = Logger.getLogger(SendInviteEarnThreadBean.class);
	@Resource
	private ThreadDAO dao;
	@Resource
	private MyWalletBean myWalletBean;
	@Resource
	private SystemConfigBean configBean;
	
	/**加载邀请奖励记录,根据分页 用户;
	 * @param pageUtil 分页对象;
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public PageUtil<InviteEarn> getInviteEarnByPageUtil(
			PageUtil<InviteEarn> pageUtil, String[] status,String Type,String insertTime) {
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
			buff.append(" AND inviteEarn.insertTime <'"+insertTime+"'" );
			buff.append(" ORDER BY  inviteEarn.insertTime DESC");
			return (PageUtil<InviteEarn>)dao.getPage(pageUtil, buff.toString(), null);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 根据邀请投资奖励表发放奖励;
	 * 
	 * @param inviteEarn
	 *            InviteEarn 邀请投资奖励表
	 * @param DetailsNote
	 *            备注
	 * @return
	 */
	public String sendInviteEarn(InviteEarn inviteEarn, String DetailsNote) {
		synchronized (LockHolder.getLock(inviteEarn.getBeInvitedId())) {
			log.info("SendInviteEarnThreadBean.sendInviteEarn 进入邀请投资奖励发放线程");
			if (QwyUtil.isNullAndEmpty(inviteEarn)) {
				return "SendInviteEarnThreadBean.sendInviteEarn 找不到邀请投资奖励记录";
			}
			if (!QwyUtil.isNullAndEmpty(inviteEarn)) {
				Investors inv = null;
				inviteEarn = (InviteEarn) dao.findById(new InviteEarn(), inviteEarn.getId());
				if(inviteEarn==null){
					return "查不到该笔邀请投资奖励记录";
				}
				if (!"0".equals(inviteEarn.getStatus())) {
					return "邀请投资记录为非待发放状态,InviteEarn.id: " + inviteEarn.getStatus();
				}
				inv = (Investors) dao.findById(new Investors(), inviteEarn.getInvestorsId());
				if (QwyUtil.isNullAndEmpty(inv)) {
					return "该邀请投资奖励对应的投资记录不存在";
				}
				ApplicationContext context = ApplicationContexts.getContexts();
				PlatformTransactionManager tm = (PlatformTransactionManager) context.getBean("transactionManager");
				TransactionStatus ts = tm.getTransaction(new DefaultTransactionDefinition());
				try {
					// 更新邀请投资奖励记录状态
					inviteEarn.setUpdateTime(new Date());
					inviteEarn.setReturnTime(new Date());
					inviteEarn.setStatus("1");// 已发放;
					StringBuffer note = new StringBuffer();
					note.append("邀请");
					note.append(QwyUtil.isNullAndEmpty(inviteEarn.getBeInvitePhone())? "未知号码" : DESEncrypt.jieMiUsername(inviteEarn.getBeInvitePhone()));
					note.append("投资了"+inviteEarn.getCopies()+"元"+inv.getProduct().getTitle()+",获得的相应奖励");
					boolean isOk = myWalletBean.sendInviteEarn(inviteEarn.getInviteId(), inviteEarn.getEarnMoney(), note.toString());
					if (!isOk) {
						tm.rollback(ts);
						log.info("SendInviteEarnThreadBean.sendInviteEarn 数据回滚: 发放奖励失败");
						return "SendInviteEarnThreadBean.sendInviteEarn 数据回滚: 发放奖励失败";
					}
					dao.saveOrUpdate(inviteEarn);
					tm.commit(ts);
					return "发放成功";
				} catch (Exception e) {
					log.error("操作异常: ", e);
					tm.rollback(ts);
					log.info("SendInviteEarnThreadBean.sendInviteEarn 数据回滚: 发放奖励异常;");
					return "SendInviteEarnThreadBean.sendInviteEarn 数据回滚: 发放奖励异常;";
				}
			}
		}
		return "";
	}

	public void saveOrUpdate(Object obj) {
		dao.saveOrUpdate(obj);
	}
}
