package com.zfgt.admin.product.bean;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.zfgt.common.dao.ObjectDAO;
import com.zfgt.common.util.DESEncrypt;
import com.zfgt.common.util.PageUtil;
import com.zfgt.common.util.QwyUtil;
import com.zfgt.orm.Winner;
@Service
public class WinnerBean {
	@Resource(name="objectDAO")
	private ObjectDAO dao;
	private static Logger log = Logger.getLogger(WinnerBean.class); 
	
	/**
	 * 将数据转换为DateMoney
	 * @throws ParseException 
	 */
	
	
	private List<Winner> toDateMoney(List<Object [] > list) throws ParseException{
		List<Winner> platInverstors=new ArrayList<Winner>();
		 SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		if(!QwyUtil.isNullAndEmpty(list)){
			for (Object [] object : list) {
				Winner winner=new Winner();
				winner.setUserName(object[0]+"");
				winner.setInsertTime(sdf.parse( object[1].toString()));
				winner.setPrizeName(object[2]==null?"":object[2]+"");
				platInverstors.add(winner);
			}
		}
		return platInverstors;
	}

	@SuppressWarnings("unchecked")
	public PageUtil<Winner> loadWinner(String name,String insertTime,PageUtil pageUtil) {
		try {
		ArrayList<Object> ob = new ArrayList<Object>();
		StringBuffer buff = new StringBuffer();
		buff.append("SELECT fr.user_name as user_name, fr.insert_time  as insert_time , p.prize_name as prize_name FROM winner fr left join prize p on p.id =fr.prize_id    WHERE 1=1 ");
		if(!QwyUtil.isNullAndEmpty(name)){
			buff.append("AND fr.user_name = ? ");
			//hql.append(" AND ins.product.title like '%"+productTitle+"%' ");
			ob.add(DESEncrypt.jiaMiUsername(name));
		}
		if(!QwyUtil.isNullAndEmpty(insertTime)){
			String [] time=QwyUtil.splitTime(insertTime);
			if(time.length > 1)
			{
				buff.append(" AND fr.insert_time >= ? ");
				ob.add(QwyUtil.fmMMddyyyyHHmmss.parse(time[0]+" 00:00:00"));
				buff.append(" AND fr.insert_time <= ? ");
				ob.add(QwyUtil.fmMMddyyyyHHmmss.parse(time[1]+" 23:59:59"));
			}
			else{

				buff.append(" AND fr.insert_time >= ? ");
				ob.add(QwyUtil.fmMMddyyyyHHmmss.parse(time[0]+" 00:00:00"));
				buff.append(" AND fr.insert_time <= ? ");
				ob.add(QwyUtil.fmMMddyyyyHHmmss.parse(time[0]+" 23:59:59"));
			}
			

		
		}
		buff.append(" ORDER BY DATE_FORMAT( fr.insert_time, '%Y-%m-%d' ) DESC ");
		
		StringBuffer bufferCount=new StringBuffer();
		bufferCount.append(" SELECT COUNT(*)  ");
		bufferCount.append(" FROM (");
		bufferCount.append(buff);
		bufferCount.append(") t");
		//buff.append("ORDER BY fr.insert_time DESC ");
		 pageUtil=dao.getBySqlAndSqlCount(pageUtil, buff.toString(), bufferCount.toString(), ob.toArray());
		
		 List<Winner> platUsers=toDateMoney(pageUtil.getList());
			pageUtil.setList(platUsers);
			return pageUtil;
	} catch (Exception e) {
		// TODO Auto-generated catch block
		log.error("操作异常: ",e);
	}
		return null;
		}
	
	

}
