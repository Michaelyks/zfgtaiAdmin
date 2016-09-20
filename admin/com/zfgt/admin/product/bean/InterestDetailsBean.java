package com.zfgt.admin.product.bean;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.zfgt.admin.product.dao.InterestDetailsDAO;
import com.zfgt.common.util.DESEncrypt;
import com.zfgt.common.util.PageUtil;
import com.zfgt.common.util.QwyUtil;
import com.zfgt.login.bean.RegisterUserBean;
import com.zfgt.orm.Fxmx;
import com.zfgt.orm.InterestDetails;

import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 * @author Administrator
 *
 */

/**
 * @author Administrator
 *
 */
/**
 * @author Administrator
 *
 */
@Service
public class InterestDetailsBean {
	@Resource
	InterestDetailsDAO dao;
	@Resource
	RegisterUserBean bean;

	/**
	 * 分页获取个人结算记录
	 * 
	 * @param pageUtil
	 *            分页工具类
	 * @param username
	 *            用户名
	 * @param productStatus
	 *            投资状态
	 * @return
	 * @throws Exception
	 */
	public PageUtil<InterestDetails> finInterestDetailses(PageUtil<InterestDetails> pageUtil, String username, String productStatus) throws Exception {
		return finInterestDetailses(pageUtil, username, productStatus, null, null);
	}

	/**
	 * 分页获取发息详细
	 * 
	 * @param pageUtil
	 *            分页工具类
	 * @param username
	 *            用户名
	 * @param productId
	 *            产品ID
	 * @param returnTime
	 *            结算时间
	 * @return
	 * @throws Exception
	 */
	public PageUtil<InterestDetails> finInterestDetailses(PageUtil<InterestDetails> pageUtil, String username, String productId, String returnTime) throws Exception {
		return finInterestDetailses(pageUtil, username, null, productId, returnTime);
	}

	/**
	 * 分页获取个人结算记录
	 * 
	 * @param pageUtil
	 *            分页工具类
	 * @param username
	 *            用户名
	 * @param productStatus
	 *            投资状态
	 * @param productId
	 *            产品ID
	 * @param returnTime
	 *            结算时间
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public PageUtil<InterestDetails> finInterestDetailses(PageUtil<InterestDetails> pageUtil, String username, String productStatus, String productId, String returnTime) throws Exception {
		List<Object> list = new ArrayList<Object>();
		StringBuffer hql = new StringBuffer();
		hql.append(" FROM InterestDetails ids WHERE ids.status in('0','1','2') ");
		// hql.append(" AND in.users.usersInfo.realName = ?");
		// list.add(realName);
		if (!QwyUtil.isNullAndEmpty(username)) {
			hql.append(" AND ids.users.username = ?");
			list.add(DESEncrypt.jiaMiUsername(username));
		}
		if (!QwyUtil.isNullAndEmpty(productId)) {
			hql.append(" AND ids.productId = ?");
			list.add(productId);
		}
		if (!QwyUtil.isNullAndEmpty(productStatus)) {
			hql.append(" AND ids.product.productStatus = ?");
			list.add(productStatus);
		}
		if (!QwyUtil.isNullAndEmpty(returnTime)) {
			String[] time = QwyUtil.splitTime(returnTime);
			if (time.length > 1) {
				hql.append(" AND ids.returnTime >= ? ");
				list.add(QwyUtil.fmMMddyyyy.parse(time[0]));
				hql.append(" AND ids.returnTime < ? ");
				list.add(QwyUtil.fmMMddyyyy.parse(time[1]));
			} else {
				hql.append(" AND ids.returnTime >= ? ");
				list.add(QwyUtil.fmMMddyyyyHHmmss.parse(time[0] + " 00:00:00"));
				hql.append(" AND ids.returnTime <= ? ");
				list.add(QwyUtil.fmMMddyyyyHHmmss.parse(time[0] + " 23:59:59"));
			}
		}
		hql.append(" ORDER BY ids.returnTime DESC ");
		return dao.getByHqlAndHqlCount(pageUtil, hql.toString(), hql.toString(), list.toArray());
	}

	/**
	 * 分页获取个人结算记录
	 * 
	 * @param username
	 *            用户名
	 * @param productId
	 *            产品ID
	 * @param returnTime
	 *            结算时间
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<InterestDetails> finInterestDetailses(String username, String productId, String returnTime) throws Exception {
		List<Object> list = new ArrayList<Object>();
		StringBuffer hql = new StringBuffer();
		hql.append(" FROM InterestDetails ids WHERE ids.status in('0','1','2') ");
		// hql.append(" AND in.users.usersInfo.realName = ?");
		// list.add(realName);
		if (!QwyUtil.isNullAndEmpty(username)) {
			hql.append(" AND ids.users.username = ?");
			list.add(DESEncrypt.jiaMiUsername(username));
		}
		if (!QwyUtil.isNullAndEmpty(productId)) {
			hql.append(" AND ids.productId = ?");
			list.add(productId);
		}
		if (!QwyUtil.isNullAndEmpty(returnTime)) {
			String[] time = QwyUtil.splitTime(returnTime);
			if (time.length > 1) {
				hql.append(" AND ids.returnTime >= ? ");
				list.add(QwyUtil.fmMMddyyyy.parse(time[0]));
				hql.append(" AND ids.returnTime <= ? ");
				list.add(QwyUtil.fmMMddyyyy.parse(time[1]));
			} else {
				hql.append(" AND ids.returnTime >= ? ");
				list.add(QwyUtil.fmMMddyyyyHHmmss.parse(time[0] + " 00:00:00"));
				hql.append(" AND ids.returnTime <= ? ");
				list.add(QwyUtil.fmMMddyyyyHHmmss.parse(time[0] + " 23:59:59"));
			}
		}
		hql.append(" ORDER BY ids.returnTime DESC ");
		return dao.LoadAll(hql.toString(), list.toArray());
	}

	/**
	 * 
	 * @param username
	 *            用户名
	 * @param productId
	 *            产品ID
	 * @param returnTime
	 *            结算时间
	 * @param sourceFileName
	 *            报表文件地址
	 * @return
	 * @throws Exception
	 */
	public List<JasperPrint> getFXMXJasperPrintList(String username, String productId, String returnTime, String sourceFileName) throws Exception {
		List<JasperPrint> list = new ArrayList<JasperPrint>();
		List<InterestDetails> interestDetails = finInterestDetailses(username, productId, returnTime);
		List<Fxmx> fxzbTables = FxzbTables(interestDetails);
		JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(fxzbTables);
		// JasperPrint js=JasperFillManager.fillReport(context.getRealPath(path)
		// +File.separator+getJxmlStr(), map,
		// ds);"D:\\table"+File.separator+"releaseProduct.jasper"
		JasperPrint js = JasperFillManager.fillReport(sourceFileName, null, ds);
		list.add(js);
		return list;
	}

	// /**
	// * 根据ID获取用户
	// */
	// public List<Users> findUsers(String ids){
	// StringBuffer buffer=new StringBuffer();
	// buffer.append(" FROM Users u ");
	// buffer.append(" WHERE u.id in ("+ids+") ");
	// return dao.LoadAll(buffer.toString(),null);
	// }

	/**
	 * 转换为付息表格bean
	 */
	public List<Fxmx> FxzbTables(List<InterestDetails> interestDetails) {
		List<Fxmx> fxmxTables = new ArrayList<Fxmx>();
		// String userids=QwyUtil.GetKeyByList("id", interestDetails, true);
		// List<Users> users=findUsers(userids);
		// Map<String, Object> map=QwyUtil.ListToMap("id", users);
		try {
			if (!QwyUtil.isNullAndEmpty(interestDetails)) {
				for (int i = 0; i < interestDetails.size(); i++) {
					InterestDetails ids = interestDetails.get(i);
					Fxmx fxmx = new Fxmx();
					fxmx.setTitle(ids.getProduct().getTitle());
					if (!QwyUtil.isNullAndEmpty(ids.getUsers())) {
						fxmx.setUsername(DESEncrypt.jieMiUsername(ids.getUsers().getUsername()));
						fxmx.setRealName(ids.getUsers().getUsersInfo().getRealName());
					}
					// Users u=(Users) map.get(ids.getUsersId()+"");
					// if(!QwyUtil.isNullAndEmpty(u)){
					// fxmx.setUsername(u.getUsername());
					// fxmx.setRealName(u.getUsersInfo().getRealName());
					// }
					fxmx.setProjectAnnualEarnings((ids.getProduct().getAnnualEarnings() == null ? 0 : ids.getProduct().getAnnualEarnings()) + "%");
					fxmx.setAnnualEarnings((ids.getInvestors().getAnnualEarnings() == null ? 0 : ids.getInvestors().getAnnualEarnings()) + "%");
					fxmx.setCouponAnnualEarnings((ids.getInvestors().getCouponAnnualRate() == null ? 0 : ids.getInvestors().getCouponAnnualRate()) + "%");
					fxmx.setBcsy(QwyUtil.calcNumber(QwyUtil.calcNumber(ids.getPayMoney(), ids.getPayInterest(), "+"), "100", "/", 2) + "");
					fxmx.setInMoney(QwyUtil.calcNumber(ids.getInMoney(), "100", "/", 2) + "");
					fxmx.setFx(QwyUtil.calcNumber(ids.getPayInterest(), "100", "/", 2) + "");
					fxmx.setInsertTime(QwyUtil.fmyyyyMMddHHmmss.format(ids.getProduct().getInsertTime()));
					fxmx.setPayTime(QwyUtil.fmyyyyMMddHHmmss.format(ids.getInsertTime()));
					fxmx.setReturnTime(QwyUtil.fmyyyyMMdd.format(ids.getReturnTime()));
					fxmxTables.add(fxmx);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return fxmxTables;
	}
}
