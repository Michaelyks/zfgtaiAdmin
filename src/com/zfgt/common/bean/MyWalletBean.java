package com.zfgt.common.bean;

import java.util.Date;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.zfgt.common.dao.MyWalletDAO;
import com.zfgt.common.util.QwyUtil;
import com.zfgt.orm.FundRecord;
import com.zfgt.orm.Investors;
import com.zfgt.orm.Users;
import com.zfgt.orm.UsersInfo;

/**对钱包的操作;<br>
 * 对用户的资金进行操作;
 * @author qwy
 *
 * @createTime 2015-05-06 16:49:52
 */
@Service
public class MyWalletBean{
	private Logger log = Logger.getLogger(MyWalletBean.class);
	@Resource
	private MyWalletDAO dao;
	@Resource
	private PlatformBean platformBean;
	/**用户减少可用余额;增加冻结金额(总资产不变)<br>
	 * 可用余额减少,更新冻结中的金额;
	 * 一般用于账户购买产品、提现申请;
	 * @param uid 用户id
	 * @param subMoney 支付的金额
	 * @param type 支付类型;cz:用户充值   tx:提现  zf:在线支付,buy:购买理财产品
	 * @param operatedWay 操作途径;"管理员后台充值","在线充值","在线提现","购买理财产品"
	 * @param note 备注; 例如:购买xxx理财产品xx元;
	 * @return true:成功; false:失败
	 */
	public boolean subLeftMoneyToFreezeMoney(long uid,double subMoney,String type,String operatedWay,String note){
		Users users = isUsersOrUsersInfoNull(uid);
		if(users==null)
			return false;
		UsersInfo uin = users.getUsersInfo();
		boolean isOk = subLeftMoneyToFreezeMoney(uin, subMoney);
		if(!isOk)
			return false;
		FundRecord fund = packFundRecord(uid, subMoney, "1", type, operatedWay, note, uin.getLeftMoney());
		dao.save(fund);
		return true;
	}
	
	/**减少用户的可用余额,增加到冻结金额;(总资产不变)
	 * @param usersInfo 用户信息表;
	 * @param payMoney 支付金额;(分)
	 * @return boolean 
	 * @throws Exception
	 */
	private boolean subLeftMoneyToFreezeMoney(UsersInfo usersInfo,double payMoney){
		try {
			if(QwyUtil.isNullAndEmpty(usersInfo))
				return false;
			//用户资金状况;
			double leftMoney = usersInfo.getLeftMoney();//可用余额
			double freezeMoney = usersInfo.getFreezeMoney();//冻结资金;
			if(payMoney>leftMoney){
				log.info("用户账户余额不足");
				return false;
			}
			//更新可用余额
			usersInfo.setLeftMoney(QwyUtil.calcNumber(leftMoney, payMoney, "-").doubleValue());
			//更新冻结金额
			usersInfo.setFreezeMoney(QwyUtil.calcNumber(freezeMoney, payMoney, "+").doubleValue());
			usersInfo.setUpdateTime(new Date());
			dao.saveOrUpdate(usersInfo);
			return true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.error("操作异常: ",e);
		}
		return false;
	}
	
	/**减少用户的可用余额,同时减少用户总资产;(总资产改变)<br>
	 * 一般用于用户提现
	 * @param uid 用户id
	 * @param subMoney 支付的金额
	 * @param type 支付类型;cz:用户充值   tx:提现  zf:在线支付,buy:购买理财产品
	 * @param operatedWay 操作途径;"管理员后台充值","在线充值","在线提现","购买理财产品"
	 * @param note 备注; 例如:用户提现;
	 * @return true:成功; false:失败
	 */
	public boolean subTotalMoneyLeftMoney(long uid,double subMoney,String type,String operatedWay,String note){
		Users users = isUsersOrUsersInfoNull(uid);
		if(users==null)
			return false;
		UsersInfo uin = users.getUsersInfo();
		boolean isOk = subTotalMoneyLeftMoney(uin, subMoney);
		if(!isOk)
			return false;
		FundRecord fund = packFundRecord(uid, subMoney, "1", type, operatedWay, note, uin.getLeftMoney());
		dao.saveOrUpdate(fund);
		return true;
	}
	
	/**同时减少总资产和冻结金额;(总资产改变,冻结金额发生改变)<br>
	 * 一般用于用户提现成功;从冻结金额里面扣掉;
	 * @param uid 用户id
	 * @param subMoney 支付的金额
	 * @param type 支付类型;cz:用户充值   tx:提现  zf:在线支付,buy:购买理财产品
	 * @param operatedWay 操作途径;"管理员后台充值","在线充值","在线提现","购买理财产品"
	 * @param note 备注; 例如:用户提现;
	 * @return true:成功; false:失败
	 */
	public boolean subTotalMoneyFreezeMoney(long uid,double subMoney,String type,String operatedWay,String note){
		Users users = isUsersOrUsersInfoNull(uid);
		if(users==null)
			return false;
		UsersInfo uin = users.getUsersInfo();
		boolean isOk = subTotalMoneyFreezeMoney(uin, subMoney);
		if(!isOk)
			return false;
		FundRecord fund = packFundRecord(uid, subMoney, "1", type, operatedWay, note, uin.getLeftMoney());
		dao.save(fund);
		return true;
	}
	
	/**减少用户的可用余额,同时减少用户总资产;(总资产改变)<br>
	 * @param usersInfo 用户信息表;
	 * @param payMoney 减少金额;(分)
	 * @return boolean 
	 * @throws Exception
	 */
	private boolean subTotalMoneyLeftMoney(UsersInfo usersInfo,double subMoney){
		try {
			if(QwyUtil.isNullAndEmpty(usersInfo))
				return false;
			//用户资金状况;
			double leftMoney = usersInfo.getLeftMoney();//可用余额
			double totalMoney = usersInfo.getTotalMoney();//总资产
			if(subMoney>leftMoney){
				log.info("用户账户余额不足");
				return false;
			}
			//更新可用余额
			usersInfo.setLeftMoney(QwyUtil.calcNumber(leftMoney, subMoney, "-").doubleValue());
			//更新总资产
			usersInfo.setTotalMoney(QwyUtil.calcNumber(totalMoney, subMoney, "-").doubleValue());
			usersInfo.setUpdateTime(new Date());
			dao.saveOrUpdate(usersInfo);
			return true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.error("操作异常: ",e);
		}
		return false;
	}
	
	/**同时减少总资产和冻结金额;(总资产改变,冻结金额发生改变)<br>
	 * 一般用于提现成功
	 * @param usersInfo 用户信息表;
	 * @param payMoney 减少金额;(分)
	 * @return boolean 
	 * @throws Exception
	 */
	private boolean subTotalMoneyFreezeMoney(UsersInfo usersInfo,double subMoney){
		try {
			if(QwyUtil.isNullAndEmpty(usersInfo))
				return false;
			//用户资金状况;
			double freezeMoney = usersInfo.getFreezeMoney();//冻结金额
			double totalMoney = usersInfo.getTotalMoney();//总资产
			if(subMoney>freezeMoney){
				log.info("用户冻结金额不足");
				return false;
			}
			//更新冻结金额
			usersInfo.setFreezeMoney(QwyUtil.calcNumber(freezeMoney, subMoney, "-").doubleValue());
			//更新总资产
			usersInfo.setTotalMoney(QwyUtil.calcNumber(totalMoney, subMoney, "-").doubleValue());
			usersInfo.setUpdateTime(new Date());
			dao.saveOrUpdate(usersInfo);
			return true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.error("操作异常: ",e);
		}
		return false;
	}
	
	/**减少用户的冻结金额,增加用户的可用余额;(总资产不改变)<br>
	 * 一般用户,解冻用户的冻结金额;
	 * @param usersInfo 用户信息表;
	 * @param payMoney 减少金额;(分)
	 * @return boolean 
	 * @throws Exception
	 */
	private boolean subFreezeMoneyToLeftMoney(UsersInfo usersInfo,double subMoney){
		try {
			if(QwyUtil.isNullAndEmpty(usersInfo))
				return false;
			//用户资金状况;
			double leftMoney = usersInfo.getLeftMoney();//可用余额
			double freezeMoney = usersInfo.getFreezeMoney();//冻结金额;
			if(subMoney>freezeMoney){
				log.info("用户冻结中的余额不足");
				return false;
			}
			//更新可用余额
			usersInfo.setLeftMoney(QwyUtil.calcNumber(leftMoney, subMoney, "+").doubleValue());
			//更新冻结金额
			usersInfo.setFreezeMoney(QwyUtil.calcNumber(freezeMoney, subMoney, "-").doubleValue());
			usersInfo.setUpdateTime(new Date());
			dao.saveOrUpdate(usersInfo);
			return true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.error("操作异常: ",e);
		}
		return false;
	}
	
	/**增加用户的可用余额(总资产改变)<br>
	 * 一般用于账户充值;
	 * @param uid 用户id
	 * @param addMoney 增加的金额(分)
	 * @param type 支付类型;cz:用户充值   tx:提现  zf:在线支付,buy:购买理财产品
	 * @param operatedWay 操作途径;"管理员后台充值","在线充值","在线提现","购买理财产品"
	 * @param note 备注; 例如:购买xxx理财产品xx元;
	 * @return true:成功; false:失败
	 */
	public boolean addTotalMoneyLeftMoney(long uid,double addMoney,String type,String operatedWay,String note){
		Users users = isUsersOrUsersInfoNull(uid);
		if(users==null)
			return false;
		UsersInfo uin = users.getUsersInfo();
		boolean isOk = addTotalMoneyLeftMoney(uin, addMoney);
		if(!isOk)
			return false;
		FundRecord fund = packFundRecord(uid, addMoney, "0", type, operatedWay, note, uin.getLeftMoney());
		dao.save(fund);
		return true;
	}
	/**增加用户的可用余额以及奖励金额(总资产改变)<br>
	 * 一般用于邀请好友投资获得奖励;
	 * @param uid 用户id
	 * @param addMoney 增加的金额(分)
	 * @param type 支付类型;cz:用户充值   tx:提现  zf:在线支付,buy:购买理财产品,
	 * @param operatedWay 操作途径;"管理员后台充值","在线充值","在线提现","购买理财产品"
	 * @param note 备注; 例如:购买xxx理财产品xx元;
	 * @return true:成功; false:失败
	 */
	public boolean addTotalLeftEarnMoney(long uid,double addMoney,String type,String operatedWay,String note){
		Users users = isUsersOrUsersInfoNull(uid);
		if(users==null)
			return false;
		UsersInfo uin = users.getUsersInfo();
		boolean isOk = addTotalLeftEarnMoney(uin, addMoney);
		if(!isOk)
			return false;
		FundRecord fund = packFundRecord(uid, addMoney, "0", type, operatedWay, note, uin.getLeftMoney());
		dao.save(fund);
		return true;
	}
	/**增加用户的可用余额(总资产发生改变)
	 * @param usersInfo 用户信息表;
	 * @param addMoney 增加金额;(分)
	 * @return boolean
	 */
	private boolean addTotalMoneyLeftMoney(UsersInfo usersInfo,double addMoney){
		try {
			if(QwyUtil.isNullAndEmpty(usersInfo))
				return false;
			//用户资金状况;
			double totalMoney = usersInfo.getTotalMoney();
			double leftMoney = usersInfo.getLeftMoney();//可用余额
			//更新可用余额
			usersInfo.setLeftMoney(QwyUtil.calcNumber(leftMoney, addMoney, "+").doubleValue());
			//更新总资产
			usersInfo.setTotalMoney(QwyUtil.calcNumber(totalMoney, addMoney, "+").doubleValue());
			usersInfo.setUpdateTime(new Date());
			dao.saveOrUpdate(usersInfo);
			return true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.error("操作异常: ",e);
		}
		return false;
	}
	
	/**增加用户的可用余额(总资产发生改变)
	 * @param usersInfo 用户信息表;
	 * @param earnMoney 奖励金额;(分)
	 * @return boolean
	 */
	private boolean addTotalLeftEarnMoney(UsersInfo usersInfo,double earnMoney){
		try {
			if(QwyUtil.isNullAndEmpty(usersInfo))
				return false;
			//用户资金状况;
			double totalMoney = usersInfo.getTotalMoney();
			double leftMoney = usersInfo.getLeftMoney();//可用余额
			double allEarnMoney = QwyUtil.isNullAndEmpty(usersInfo.getInviteEarnMoney())? 0d : usersInfo.getInviteEarnMoney();//奖励总金额
			//更新可用余额
			usersInfo.setLeftMoney(QwyUtil.calcNumber(leftMoney, earnMoney, "+").doubleValue());
			//更新总资产
			usersInfo.setTotalMoney(QwyUtil.calcNumber(totalMoney, earnMoney, "+").doubleValue());
			//更新总奖励金额
			usersInfo.setInviteEarnMoney(QwyUtil.calcNumber(allEarnMoney, earnMoney, "+").doubleValue());
			usersInfo.setUpdateTime(new Date());
			dao.saveOrUpdate(usersInfo);
			return true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.error("操作异常: ",e);
		}
		return false;
	}

	/**支付购买理财产品的金额;(总资产不变)
	 * @param uid 用户id
	 * @param payMoney 购买金额
	 * @param note 备注
	 * @return true:成功; false:失败
	 */
	public boolean buyProduct(long uid,double payMoney,String note){
		Users users = isUsersOrUsersInfoNull(uid);
		if(users==null)
			return false;
		UsersInfo uin = users.getUsersInfo();
		//增加用户的投资次数;
		Long investCount = uin.getInvestCount();
		investCount = QwyUtil.isNullAndEmpty(investCount)?1L:++investCount;
		uin.setInvestCount(investCount);
		dao.saveOrUpdate(uin);
		return subLeftMoneyToFreezeMoney(uid, payMoney, "buy", "购买理财产品", note);
	}
	
	/**理财产品结算,对用户还款操作;
	 * @param uid 用户id
	 * @param investorsId 投资列表的ID
	 * @param note 备注
	 * @return
	 */
	public boolean backMoney(long uid,String investorsId,String note){
		Investors inv = (Investors)dao.findById(new Investors(), investorsId);
		if(inv==null || !"2".equals(inv.getInvestorStatus())){
			return false;
		}
		Users users = isUsersOrUsersInfoNull(uid);
		if(users==null)
			return false;
		UsersInfo uin = users.getUsersInfo();
		boolean isOk = subFreezeMoneyToLeftMoney(uin, inv.getInMoney());
		if(!isOk)
			return false;
		//添加返款记录;
		FundRecord fund = packFundRecord(uid, inv.getInMoney(), "0", "back", "理财产品结算还款", note, uin.getLeftMoney());
		dao.save(fund);
		return true;
	}
	
	/**发放收益;用户总资产增加,可用余额增加;
	 * @param uid 用户id
	 * @param interestMoney 收益金额(分)
	 * @param note 备注;
	 * @return true:发放成功; false:发放失败;
	 */
	public boolean sendInterest(long uid,double interestMoney,String note){
		boolean isOk = addTotalMoneyLeftMoney(uid, interestMoney, "lx", "发放利息", note);
		try {
			if(isOk){
				Users users = isUsersOrUsersInfoNull(uid);
				UsersInfo uin = users.getUsersInfo();
				double newProfit = QwyUtil.calcNumber(QwyUtil.isNullAndEmpty(uin.getTotalProfit())?0:uin.getTotalProfit(), interestMoney, "+").doubleValue();
				uin.setTotalProfit(newProfit);//投资总收益;
				dao.saveOrUpdate(uin);
				platformBean.updateTotalProfit(interestMoney);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return isOk;
	}
	/**发放邀请投资奖励;用户总资产增加,可用余额增加，总奖励增加;
	 * @param inviteId 邀请人id
	 * @param earnMoney 奖励金额(分)
	 * @param note 备注;
	 * @return true:发放成功; false:发放失败;
	 */
	public boolean sendInviteEarn(long inviteId,double earnMoney,String note){
		boolean isOk = addTotalLeftEarnMoney(inviteId, earnMoney, "jl", "发放邀请好友投资获得的奖励", note);
		return isOk;
	}
	
	/**封装FundRecord;
	 * @param usersId 用户Id
	 * @param money 操作金额  单位为(分) 
	 * @param status 状态 0:收入; 1:支出
	 * @param operatedType '操作类别  cz:用户充值   tx:提现  zf:在线支付'
	 * @param operatedWay '操作途径'
	 * @param note 备注
	 * @return {@link FundRecord}
	 */
	public FundRecord packFundRecord(long usersId,double money,String status, String operatedType, String operatedWay, String note,double usersCost){
		FundRecord fund = new FundRecord();
		fund.setUsersId(usersId);//用户Id
		fund.setMoney(money);//充值的金额
		fund.setStatus(status);//0:收入 || 1:支付
		fund.setType(operatedType);//操作类型;
		fund.setOperatedWay(operatedWay);//操作途径
		fund.setNote(note);//备注;
		fund.setInsertTime(new Date());
		fund.setUsersCost(usersCost);
		return fund;
		
	}
	
	/**判断是否存在用户和用户信息;
	 * @param uid 用户ID
	 * @return 返回Users或者null
	 */
	public Users isUsersOrUsersInfoNull(long uid){
		Users users = (Users)dao.findById(new Users(), uid);
		if(users==null){
			log.info("MyWalletBean: Users不存在;usersId: "+uid);
			return null;
		}
		UsersInfo uin = users.getUsersInfo();
		if(uin==null){
			log.info("MyWalletBean: UsersInfo不存在;usersId: "+uid);
			return null;
		}
		return users;
	}
	
	/**减少用户的可用余额,用户总资产不变;<br>
	 * 零钱包
	 * @param uid 用户id
	 * @param subMoney 支付的金额
	 * @param type 支付类型;cz:用户充值   tx:提现  zf:在线支付,buy:购买理财产品,to:转入到零钱包,out:从零钱包
	 * @param operatedWay 操作途径;"管理员后台充值","在线充值","在线提现","购买理财产品"
	 * @param note 备注; 例如:用户提现;
	 * @return true:成功; false:失败
	 */
	public boolean subLeftMoney(long uid,double subMoney,String type,String operatedWay,String note){
		Users users = isUsersOrUsersInfoNull(uid);
		if(users==null)
			return false;
		UsersInfo uin = users.getUsersInfo();
		boolean isOk = subLeftMoney2(uin, subMoney);
		if(!isOk)
			return false;
		FundRecord fund = packFundRecord(uid, subMoney, "1", type, operatedWay, note, uin.getLeftMoney());
		dao.save(fund);
		return true;
	}
	
	/**减少用户的可用余额(总资产不变)
	 * @param usersInfo 用户信息表;
	 * @param payMoney 支付金额;(分)
	 * @return boolean 
	 * @throws Exception
	 */
	private boolean subLeftMoney2(UsersInfo usersInfo,double payMoney){
		try {
			if(QwyUtil.isNullAndEmpty(usersInfo))
				return false;
			//用户资金状况;
			double leftMoney = usersInfo.getLeftMoney();//可用余额
			if(payMoney>leftMoney){
				log.info("用户账户余额不足");
				return false;
			}
			//更新可用余额
			usersInfo.setLeftMoney(QwyUtil.calcNumber(leftMoney, payMoney, "-").doubleValue());
			usersInfo.setUpdateTime(new Date());
			dao.saveOrUpdate(usersInfo);
			return true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.error("操作异常: ",e);
		}
		return false;
	}
	
	
	/**增加用户的可用余额(总资产不变)<br>
	 * 一般用于账户充值;
	 * @param uid 用户id
	 * @param addMoney 增加的金额(分)
	 * @param type 支付类型;cz:用户充值   tx:提现  zf:在线支付,buy:购买理财产品,to:转入到零钱包,out:从零钱包
	 * @param operatedWay 操作途径;"管理员后台充值","在线充值","在线提现","购买理财产品"
	 * @param note 备注; 例如:购买xxx理财产品xx元;
	 * @return true:成功; false:失败
	 */
	public boolean addLeftMoney(long uid,double addMoney,String type,String operatedWay,String note){
		Users users = isUsersOrUsersInfoNull(uid);
		if(users==null)
			return false;
		UsersInfo uin = users.getUsersInfo();
		boolean isOk = addLeftMoney(uin, addMoney);
		if(!isOk)
			return false;
		FundRecord fund = packFundRecord(uid, addMoney, "0", type, operatedWay, note, uin.getLeftMoney());
		dao.save(fund);
		return true;
	}
	
	/**增加用户的可用余额(总资产发生不变)
	 * @param usersInfo 用户信息表;
	 * @param addMoney 增加金额;(分)
	 * @return boolean
	 */
	private boolean addLeftMoney(UsersInfo usersInfo,double addMoney){
		try {
			if(QwyUtil.isNullAndEmpty(usersInfo))
				return false;
			double leftMoney = usersInfo.getLeftMoney();//可用余额
			//更新可用余额
			usersInfo.setLeftMoney(QwyUtil.calcNumber(leftMoney, addMoney, "+").doubleValue());
			usersInfo.setUpdateTime(new Date());
			dao.saveOrUpdate(usersInfo);
			return true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.error("操作异常: ",e);
		}
		return false;
	}
	
	/**增加用户的总资产(总资产发生改变)
	 * @param usersId 用户ID;
	 * @param addMoney 增加金额;(分)
	 * @return boolean
	 */
	public boolean addTotalMoney(Long usersId,double addMoney){
		try {
			Users users = isUsersOrUsersInfoNull(usersId);
			if(users==null)
				return false;
			UsersInfo uin = users.getUsersInfo();
			//用户资金状况;
			double totalMoney = uin.getTotalMoney();
			//更新总资产
			uin.setTotalMoney(QwyUtil.calcNumber(totalMoney, addMoney, "+").doubleValue());
			uin.setUpdateTime(new Date());
			dao.saveOrUpdate(uin);
			return true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.error("操作异常: ",e);
		}
		return false;
	}
	
	
	
}
