package com.zfgt.admin.Mcoin.bean;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.zfgt.admin.Mcoin.dao.MExchangeReport;
import com.zfgt.admin.product.bean.SmsRecordBean;
import com.zfgt.common.bean.SystemConfigBean;
import com.zfgt.common.dao.ObjectDAO;
import com.zfgt.common.util.DESEncrypt;
import com.zfgt.common.util.PageUtil;
import com.zfgt.common.util.QwyUtil;
import com.zfgt.common.util.SMSUtil;
import com.zfgt.login.bean.RegisterUserBean;
import com.zfgt.orm.MCoinPay;
import com.zfgt.orm.MProduct;
import com.zfgt.orm.SystemConfig;
import com.zfgt.orm.UsersInfo;

@Service
public class MExchangeReportBean {
	private static Logger log = Logger.getLogger(MExchangeReportBean.class);
	@Resource(name = "objectDAO")
	private ObjectDAO dao;
	@Resource
	private RegisterUserBean registerUserBean;
	@Resource
	private SystemConfigBean systemConfigBean;
	@Resource
	private SmsRecordBean smsRecordBean;

	/**
	 * 根据id查询数据
	 * 
	 * @param id
	 * @return
	 */

	public MProduct findMProductById(String id) {
		try {
			StringBuffer hql = new StringBuffer("FROM MProduct mp ");
			hql.append("WHERE mp.id = ? ");
			Object[] ob = new Object[] { id };
			return (MProduct) dao.findJoinActive(hql.toString(), ob);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.error("操作异常: ", e);
		}
		return null;
	}

	/**
	 * 发送短信
	 * 
	 * @param id
	 * @return
	 */

	public Boolean modifyMCoinPayById(String id, Long adminId) {
		Boolean result = false;
		try {
			MCoinPay mCoinPay = (MCoinPay) dao.findById(new MCoinPay(), id);
			if (!QwyUtil.isNullAndEmpty(mCoinPay.getId())) {
				UsersInfo info = registerUserBean.getByUid(mCoinPay
						.getUsersId());
				if (!QwyUtil.isNullAndEmpty(info.getId())) {
					StringBuffer msg = new StringBuffer();
					msg.append("【中泰理财】");
					if ("3".equals(mCoinPay.getType())) {// 话费充值
						msg.append("您在喵商城兑换的话费已充值完毕，请注意查收。");
					}
					SystemConfig config = systemConfigBean.findSystemConfig();
					msg.append(config.getMsgCost());
					log.info("话费短信:" + msg);
					Map<String, Object> map = SMSUtil.sendYzm2(
							DESEncrypt.jieMiUsername(info.getPhone()), null,
							msg.toString());
					if (!QwyUtil.isNullAndEmpty(map)) {//!QwyUtil.isNullAndEmpty(map.get("error").toString())
						smsRecordBean.addSmsRecord(
								DESEncrypt.jieMiUsername(info.getPhone()),
								msg.toString(), map.get("error").toString(),
								adminId);
						mCoinPay.setMsgStatus("1");// 短信已发送
						dao.saveOrUpdate(mCoinPay);
						log.info("用户名"
								+ DESEncrypt.jieMiUsername(info.getPhone())
								+ ":发送成功");
						return true;
					} else {
						log.info("用户名"
								+ DESEncrypt.jieMiUsername(info.getPhone())
								+ ":发送失败");
						return result;
					}

				}
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.error("操作异常: ", e);
		}
		return result;
	}

	/**
	 * 修改状态
	 * 
	 * @param product
	 * @return
	 */
	public Boolean endExchange(MProduct pro) {
		if (!QwyUtil.isNullAndEmpty(pro.getStatus())
				&& pro.getStatus().equals("1")) {
			pro.setStatus("0");
		} else {
			pro.setStatus("1");
		}
		pro.setUpdateTime(new Date());
		dao.saveOrUpdate(pro);
		return true;
	}

	/**
	 * 分页查询瞄产品
	 * 
	 * @param pageUtil
	 * @param insertTime
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public PageUtil<MProduct> loadMProduct(PageUtil<MProduct> pageUtil,
			String insertTime) {
		try {
			ArrayList<Object> ob = new ArrayList<Object>();
			StringBuffer hql = new StringBuffer();
			hql.append(" FROM MProduct h WHERE 1=1");

			if (!QwyUtil.isNullAndEmpty(insertTime)) {
				String[] time = QwyUtil.splitTime(insertTime);
				if (time.length > 1) {
					hql.append(" AND h.insertTime  >= ? ");
					ob.add(QwyUtil.fmMMddyyyyHHmmss
							.parse(time[0] + " 00:00:00"));
					hql.append(" AND h.insertTime  <= ? ");
					ob.add(QwyUtil.fmMMddyyyyHHmmss
							.parse(time[1] + " 23:59:59"));
				} else {

					hql.append(" AND h.insertTime  >= ? ");
					ob.add(QwyUtil.fmMMddyyyyHHmmss
							.parse(time[0] + " 00:00:00"));
					hql.append(" AND h.insertTime <= ? ");
					ob.add(QwyUtil.fmMMddyyyyHHmmss
							.parse(time[0] + " 23:59:59"));
				}

			}

			// if(!QwyUtil.isNullAndEmpty(title)){
			// hql.append(" AND h.title like '%"+title+"%' ");
			// objects.add(status);
			// }

			hql.append(" ORDER BY h.insertTime DESC ");
			return dao.getPage(pageUtil, hql.toString(), ob.toArray());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.error("操作异常: ", e);
		}
		return null;
	}

	/**
	 * 将数据转换为DateMoney
	 * 
	 * @throws ParseException
	 */
	private List<MExchangeReport> toDateMoney(List<Object[]> list)
			throws ParseException {
		List<MExchangeReport> platInverstors = new ArrayList<MExchangeReport>();
		// SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		if (!QwyUtil.isNullAndEmpty(list)) {
			MExchangeReport exh = null;
			for (Object[] object : list) {
				exh = new MExchangeReport();
				exh.setInsertTime(QwyUtil.fmyyyyMMddHHmmss
						.parse(QwyUtil.fmyyyyMMddHHmmss.format(object[0])));
				exh.setInsDate(object[0] + "");
				exh.setTitle(object[1] == null ? "" : object[1] + "");
				exh.setLevel(object[2] == null ? "1" : object[2] + "");
				exh.setPrice(object[3] == null ? "0" : object[3] + "");
				exh.setUsername(object[4] == null ? "" : object[4] + "");
				exh.setCopies(object[5] == null ? "0" : object[5] + "");
				exh.setCoin(object[6] == null ? "6" : object[6] + "");
				exh.setId(object[7] == null ? "0" : object[7] + "");
				exh.setStatus(object[8] == null ? "0" : object[8] + "");
				exh.setAddress(object[9] + "");
				exh.setContractName(object[10] + "");
				exh.setAddress_detail(object[11] + "");
				if ("3".equals(object[14] + "")
						&& exh.getTitle().contains("话费")) {// 话费属于其他分类
					exh.setMsgStatus(object[12] == null ? "0" : object[12] + "");
				} else {
					exh.setMsgStatus(object[12] + "");
				}
				exh.setmCoinPayId(object[13] + "");
				exh.setType(object[14] + "");
				platInverstors.add(exh);
				// winner.setPrizeName(object[2]==null?"":object[2]+"");
			}
		}
		return platInverstors;
	}

	/**
	 * 分页查询瞄币兑换
	 * 
	 * @param name
	 * @param insertTime
	 * @param pageUtil
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public PageUtil<MExchangeReport> loadMExchange(String name,
			String insertTime, PageUtil pageUtil) {
		try {
			ArrayList<Object> ob = new ArrayList<Object>();
			StringBuffer buff = new StringBuffer();
//			buff.append("SELECT m.insert_time as insert_time, p.title as title , i.`level` as level , p.price as price ,u.username as username,m.`copies` as copies, m.`coin` as coin, p.id as productId ,p.status as status   , IFNULL(ma.address ,'') AS address, IFNULL(ma.contract_name,'') AS contract_name , IFNULL(ma.address_detail ,'')AS address_detail,m.msg_status,m.id as m_coin_pay,m.type");
//			buff.append(" FROM m_coin_pay m LEFT JOIN m_users_address ma  ON ma.id = m.m_users_address_id  LEFT JOIN m_product p ON p.id = m.`M_product_id` LEFT JOIN users u ON m.`users_id`=  u.`id` LEFT JOIN users_info i ON i.`users_id`= u.`id` where 1=1 ");
		    buff.append(" SELECT ");
					buff.append(" s.insert_time , t.title  , i.level , t.price ,i.phone,s.copies, s.coin, t.productId ,t.status , IFNULL(s.address ,''), IFNULL(s.contract_name,''), IFNULL(s.address_detail ,''),s.msg_status,s.m_id ,s.type ");
					buff.append("  FROM (");
					buff.append(" SELECT m.insert_time AS insert_time,  m.copies, m.coin, ma.address AS address , ma.contract_name, ma.address_detail , msg_status, m.type,  m.id AS m_id , m.M_product_id AS product_id ,m.users_id FROM m_coin_pay m LEFT JOIN m_users_address ma  ON ma.id = m.m_users_address_id");
					buff.append(" )s");
					buff.append(" LEFT JOIN (");
					buff.append(" SELECT p.title, p.price, p.id AS productId, p.status FROM m_product p  ");
					buff.append("   ) t");
					buff.append("  ON s.product_id = t.productId LEFT JOIN users u ON s.users_id= u.id LEFT JOIN users_info i ON i.users_id= s.users_id WHERE 1=1  ");
			if (!QwyUtil.isNullAndEmpty(name)) {
				buff.append(" AND u.username = ? ");
				ob.add(DESEncrypt.jiaMiUsername(name));
			}
			if (!QwyUtil.isNullAndEmpty(insertTime)) {
				String[] time = QwyUtil.splitTime(insertTime);
				if (time.length > 1) {
					buff.append(" AND s.insert_time  >= ? ");
					ob.add(QwyUtil.fmMMddyyyyHHmmss
							.parse(time[0] + " 00:00:00"));
					buff.append(" AND s.insert_time  <= ? ");
					ob.add(QwyUtil.fmMMddyyyyHHmmss
							.parse(time[1] + " 23:59:59"));
				} else {

					buff.append(" AND s.insert_time  >= ? ");
					ob.add(QwyUtil.fmMMddyyyyHHmmss
							.parse(time[0] + " 00:00:00"));
					buff.append(" AND s.insert_time <= ? ");
					ob.add(QwyUtil.fmMMddyyyyHHmmss
							.parse(time[0] + " 23:59:59"));
				}

			}
			buff.append(" ORDER BY  s.insert_time DESC ");

			StringBuffer bufferCount = new StringBuffer();
			bufferCount.append(" SELECT COUNT(*)  ");
			bufferCount.append(" FROM (");
			bufferCount.append(buff);
			bufferCount.append(") t");
			pageUtil = dao.getBySqlAndSqlCount(pageUtil, buff.toString(),
					bufferCount.toString(), ob.toArray());
//			List<Map<String, String>> list = dao.LoadAllListMapSql(
//					buff.toString(), null);
			List<MExchangeReport> platUsers = toDateMoney(pageUtil.getList());
			pageUtil.setList(platUsers);
			return pageUtil;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.error("操作异常: ", e);
		}
		return null;
	}
}
