package com.zfgt.account.bean;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.zfgt.common.dao.ObjectDAO;
import com.zfgt.common.util.DESEncrypt;
import com.zfgt.common.util.PageUtil;
import com.zfgt.common.util.QwyUtil;
import com.zfgt.orm.FundRecord;
import com.zfgt.orm.Investors;
import com.zfgt.orm.Users;


/**资金流水的Bean层;<br>
 * @method 获取用户的资金流水;
 * @author qwy
 *
 * @createTime 2015-05-06 18:42:33
 */
@Service
public class FundRecordBean {
	@Resource(name="objectDAO")
	private ObjectDAO dao;
	private static Logger log = Logger.getLogger(FundRecordBean.class); 
	/**获取用户的资金流水;
	 * @param uid 用户id
	 * @param status 状态 0:收入; 1:支出
	 * @param type 操作类别  cz:用户充值   tx:提现  zf:在线支付,buy:购买理财产品
	 * @param startDate 开始时间
	 * @param endDate 结束时间
	 * @param pageUtil 分页对象;
	 * @return 资金流水分页对象;
	 */
	@SuppressWarnings("unchecked")
	public PageUtil<FundRecord> loadFundRecord(long uid,String status,String type,String startDate,String endDate,PageUtil<FundRecord> pageUtil) {
		try {
			ArrayList<Object> ob = new ArrayList<Object>();
			StringBuffer buff = new StringBuffer();
			buff.append("FROM FundRecord fr ");
			if(0!=uid){
				buff.append("WHERE fr.usersId = ? ");
				ob.add(uid);
			}
			
			if(!QwyUtil.isNullAndEmpty(status)){
				buff.append("AND fr.status = ? ");
				ob.add(status);
			}
			if(!QwyUtil.isNullAndEmpty(type) && !"all".equalsIgnoreCase(type)){
				buff.append("AND fr.type = ? ");
				ob.add(type);
			}
			if(!QwyUtil.isNullAndEmpty(startDate)){
				buff.append("AND fr.insertTime >= ? ");
				ob.add(QwyUtil.fmyyyyMMdd.parse(startDate+" 00:00:00"));
			}
			if(!QwyUtil.isNullAndEmpty(endDate)){
				buff.append("AND fr.insertTime <= ? ");
				ob.add(QwyUtil.fmyyyyMMddHHmmss.parse(endDate+" 23:59:59"));
			}
			buff.append("ORDER BY fr.insertTime DESC,fr.id DESC ");
			return (PageUtil<FundRecord>)dao.getPage(pageUtil, buff.toString(), ob.toArray());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.error("操作异常: ",e);
			log.error("FundRecordBean.loadFundRecord.exception: ",e);
		}
		return null;
	}
	
	/**通过用户名得到用户ID;
	 * 
	 */
	public Long getUserId(String name){
		String userName=DESEncrypt.jiaMiUsername(name);
		List<Users> list=dao.LoadAll("from Users where username='"+userName+"'", null);
		Users user=list.get(0);
		Long userId=user.getId();
		return userId;
		
	}
	/**获取用户的资金流水;
	 * @param name 用户名
	 * @param status 状态 0:收入; 1:支出
	 * @param type 操作类别  cz:用户充值   tx:提现  zf:在线支付,buy:购买理财产品
	 * @param startDate 开始时间
	 * @param endDate 结束时间
	 * @param pageUtil 分页对象;
	 * @return 资金流水分页对象;
	 */
	@SuppressWarnings("unchecked")
	public PageUtil<FundRecord> loadFundRecord(String name,String status,String type,String startDate,String endDate,PageUtil<FundRecord> pageUtil) {
		try {
			ArrayList<Object> ob = new ArrayList<Object>();
			StringBuffer buff = new StringBuffer();
			buff.append("FROM FundRecord fr WHERE 1=1 ");
			if(!QwyUtil.isNullAndEmpty(name)){
				buff.append("AND fr.users.username = ? ");
				ob.add(DESEncrypt.jiaMiUsername(name));
			}
			
			if(!QwyUtil.isNullAndEmpty(status)){
				buff.append("AND fr.status = ? ");
				ob.add(status);
			}
			if(!QwyUtil.isNullAndEmpty(type) && !"all".equalsIgnoreCase(type)){
				buff.append("AND fr.type = ? ");
				ob.add(type);
			}
			if(!QwyUtil.isNullAndEmpty(startDate)){
				buff.append("AND fr.insertTime >= ? ");
				ob.add(QwyUtil.fmyyyyMMdd.parse(startDate+" 00:00:00"));
			}
			if(!QwyUtil.isNullAndEmpty(endDate)){
				buff.append("AND fr.insertTime <= ? ");
				ob.add(QwyUtil.fmyyyyMMddHHmmss.parse(endDate+" 23:59:59"));
			}
			buff.append("ORDER BY fr.insertTime DESC,fr.id DESC ");
			return (PageUtil<FundRecord>)dao.getPage(pageUtil, buff.toString(), ob.toArray());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.error("操作异常: ",e);
		}
		return null;
	}
}
