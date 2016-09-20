package com.zfgt.admin.Mcoin.bean;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.zfgt.common.dao.ObjectDAO;
import com.zfgt.common.util.PageUtil;
import com.zfgt.common.util.QwyUtil;
import com.zfgt.orm.MCoinDayDetail;
import com.zfgt.orm.SystemConfig;
@Service
public class MCoinDayDetailBean {
	private static Logger log = Logger.getLogger(MCoinDayDetailBean.class); 
	@Resource(name="objectDAO")
	private ObjectDAO dao;
	/**
	 *  以日期分组查询瞄币记录
	 * @param pageUtil 
	 * @param insertTime
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public PageUtil<MCoinDayDetail> loadMCoinDayDetail(PageUtil<MCoinDayDetail> pageUtil,String insertTime){
		try {
		ArrayList<Object> ob = new ArrayList<Object>();
		StringBuffer hql = new StringBuffer();
		hql.append(" FROM MCoinDayDetail h WHERE 1=1");
		
		if(!QwyUtil.isNullAndEmpty(insertTime)){
			String [] time=QwyUtil.splitTime(insertTime);
			if(time.length > 1)
			{
				hql.append(" AND h.insertTime  >= ? ");
				ob.add(QwyUtil.fmMMddyyyyHHmmss.parse(time[0]+" 00:00:00"));
				hql.append(" AND h.insertTime  <= ? ");
				ob.add(QwyUtil.fmMMddyyyyHHmmss.parse(time[1]+" 23:59:59"));
			}
			else{

				hql.append(" AND h.insertTime  >= ? ");
				ob.add(QwyUtil.fmMMddyyyyHHmmss.parse(time[0]+" 00:00:00"));
				hql.append(" AND h.insertTime <= ? ");
				ob.add(QwyUtil.fmMMddyyyyHHmmss.parse(time[0]+" 23:59:59"));
			}

		}
		
		hql.append(" ORDER BY h.insertTime DESC ");
		return dao.getPage(pageUtil, hql.toString(), ob.toArray());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.error("操作异常: ",e);
		}
		return null;
	}
	
	
	public SystemConfig getMCoinCleanState(){
		StringBuffer buffer=new StringBuffer();
		buffer.append(" FROM SystemConfig ast ");
		List<SystemConfig> autoShiftTos=dao.LoadAll(buffer.toString(),null);
		return autoShiftTos.get(0);
	}
	
	/**
	 * 修改清空瞄币的状态
	 * @param id
	 * @return
	 */
	public boolean startCelanAllMcoin(Long id){
		StringBuffer buffer=new StringBuffer();
		buffer.append(" FROM SystemConfig ast ");
		List<SystemConfig> autoShiftTos=dao.LoadAll(buffer.toString(),null);
		if(!QwyUtil.isNullAndEmpty(autoShiftTos)){
			SystemConfig mcoin = autoShiftTos.get(0);
			if(mcoin.getIsCleanMcoin().equals("1")){
				mcoin.setIsCleanMcoin("0");
			}
			else{
				mcoin.setIsCleanMcoin("1");
			}
			dao.saveOrUpdate(mcoin);
			return true;
		}
		return false;
	}
	
}
