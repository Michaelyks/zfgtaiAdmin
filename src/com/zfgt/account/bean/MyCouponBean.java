package com.zfgt.account.bean;

import java.util.ArrayList;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.zfgt.account.dao.MyAccountDAO;
import com.zfgt.common.util.PageUtil;
import com.zfgt.common.util.QwyUtil;
import com.zfgt.orm.Coupon;

/**我的投资券Bean层;
 * @author qwy
 *
 * @createTime 2015-05-07 13:24:56
 */
@Service
public class MyCouponBean {
	private static Logger log = Logger.getLogger(MyCouponBean.class); 
	@Resource
	private MyAccountDAO dao;
	/**获取投资券
	 * @param uid 用户id
	 * @param status 状态 0未使用,1未用完,2已用完,3已过期
	 * @param pageUtil
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public PageUtil<Coupon> loadCoupon(long uid,String status,PageUtil<Coupon> pageUtil) {
		try {
			ArrayList<Object> ob = new ArrayList<Object>();
			StringBuffer buff = new StringBuffer();
			buff.append("FROM Coupon fr ");
			buff.append("WHERE fr.usersId = ? ");
			ob.add(uid);
			if(!QwyUtil.isNullAndEmpty(status)){
				buff.append("AND fr.status = ? ");
				ob.add(status);
			}
			buff.append("ORDER BY fr.insertTime DESC ");
			return (PageUtil<Coupon>)dao.getPage(pageUtil, buff.toString(), ob.toArray());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.error("操作异常: ",e);
		}
		return null;
	}
}
