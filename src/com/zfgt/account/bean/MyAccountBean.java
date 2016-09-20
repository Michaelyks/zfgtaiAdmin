package com.zfgt.account.bean;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.zfgt.account.dao.MyAccountDAO;
import com.zfgt.common.util.DESEncrypt;
import com.zfgt.common.util.QwyUtil;
import com.zfgt.orm.Account;
import com.zfgt.orm.Users;

/**我的账户Bean层,钱包的业务逻辑处理
 * @author qwy
 *
 * @createTime 2015-04-24 14:46:59
 */
@Service
public class MyAccountBean {

	
	private static Logger log = Logger.getLogger(MyAccountBean.class); 
	@Resource
	private MyAccountDAO dao;
	@Resource
	private UserRechargeBean userRechargeBean;
	
	/**获取可用红包的余额;
	 * @param usersId 用户ID
	 * @return 可用红包的余额;
	 */
	public double getCouponCost(long usersId)  throws Exception{
		return userRechargeBean.getCouponCost(usersId);
	}
	/**获取可用红包的余额;
	 * @param usersId 用户ID
	  * @param type 类别 如:0:常规投资券; 1:新手投资券
	 * @return 可用红包的余额;
	 */
	public double getCouponCost(long usersId,String type)  throws Exception{
		return userRechargeBean.getCouponCost(usersId,type);
	}
	
	/**根据用户id来获取用户
	 * @param usersId
	 * @return
	 */
	public Users getUsersById(long usersId)  throws Exception{
		return (Users)dao.findById(new Users(), usersId);
	}
	
	/**获取投资中的产品的金额
	 * @param usersId 用户ID
	 * @return 获取投资中的产品的金额
	 */
	public double getProductCost(long usersId)  throws Exception{
		StringBuffer hql = new StringBuffer();
		hql.append("SELECT SUM(inv.inMoney) FROM Investors inv ");
		hql.append("WHERE inv.investorStatus IN ('1','2') ");
		hql.append("AND inv.usersId = ? ");
		Double cost = (Double)dao.findJoinActive(hql.toString(), new Object[]{usersId});
		cost = cost==null?0:cost;
		return cost>=0?cost:0;
	}
	
	
	/**根据用户名去查找用户绑定的银行卡;
	 * @param usersId 用户id
	 * @param type 支付类型 第三方支付; 0:易宝支付; 1:连连支付
	 * @return null OR Account
	 * @return
	 */
	public Account getAccountByUsersName(String username,String type)  throws Exception{
		String hql = "FROM Account acc WHERE acc.users.username = ? AND acc.status = '0' ";
		ArrayList<Object> ob = new ArrayList<Object>();
		ob.add(DESEncrypt.jiaMiUsername(username));
		Object obj = dao.findJoinActive(hql, ob.toArray());
		if(!QwyUtil.isNullAndEmpty(type)){
			ob.add(type);
			hql+=" AND acc.type = ? ";
		}
		if(QwyUtil.isNullAndEmpty(obj)){
			return null;
		}else{
			Account ac = (Account)obj;
			return ac;
		}
	}
	/**根据用户名去查找用户绑定的银行卡;
	 * @param usersId 用户id
	 * @param type 支付类型 第三方支付; 0:易宝支付; 1:连连支付
	 * @return list
	 */
	public List<Account> findAccountByUsersName(String username) throws Exception{
		String hql = "FROM Account acc WHERE acc.users.username = ? ";
		ArrayList<Object> ob = new ArrayList<Object>();
		ob.add(DESEncrypt.jiaMiUsername(username));
		List<Account> list=dao.LoadAll(hql.toString(), ob.toArray());
		return list;
	}
	
	/**根据用户id去查找用户绑定的银行卡;
	 * @param usersId 用户id
	 * @param type 支付类型 第三方支付; 0:易宝支付; 1:连连支付
	 * @return null OR Account
	 * @return
	 */
	public Account getAccountByUsersId(long usersId,String type){
		String hql = "FROM Account acc WHERE acc.usersId = ? AND acc.status = '0' ";
		ArrayList<Object> ob = new ArrayList<Object>();
		ob.add(usersId);
		if(!QwyUtil.isNullAndEmpty(type)){
			ob.add(type);
			hql+=" AND acc.type = ? ";
		}
		Object obj = dao.findJoinActive(hql, ob.toArray());
		if(QwyUtil.isNullAndEmpty(obj)){
			return null;
		}else{
			Account ac = (Account)obj;
			return ac;
		}
	}
	
	
	/**根据身份证去查找用户绑定的银行卡;
	 * @param usersId 用户id
	 * @param type 
	 * @return
	 */
	public Account getAccountByIdCard(String idcard,String type) throws Exception{
		ArrayList<Object> list=new ArrayList<Object>();
		StringBuffer hql=new StringBuffer();
		hql .append( "FROM Account acc WHERE acc.idcard = ? AND acc.status = '0'  ");
		list.add(DESEncrypt.jiaMiIdCard(idcard));
		if(!QwyUtil.isNullAndEmpty(type)){
			hql .append( "AND type = ? ");
			list.add(type);
		}
		Object obj = dao.findJoinActive(hql.toString(), list.toArray());
		if(QwyUtil.isNullAndEmpty(obj)){
			return null;
		}else{
			return (Account)obj;
		}
	}
	
	/**根据用户id去查找用户绑定的银行卡;
	 * @param id id
	 * @return
	 */
	public Account getAccountById(String id) throws Exception{
		Object obj=dao.findById(new Account(), id);
		if(QwyUtil.isNullAndEmpty(obj)){
			return null;
		}else{
			return (Account)obj;
		}
	}
	
}
