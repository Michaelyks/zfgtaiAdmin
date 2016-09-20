package com.zfgt.admin.Mcoin.bean;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.zfgt.admin.Mcoin.dao.MeowPayDao;
import com.zfgt.common.dao.ObjectDAO;
import com.zfgt.common.util.DESEncrypt;
import com.zfgt.common.util.PageUtil;
import com.zfgt.common.util.QwyUtil;
@Service
public class MeowPayBean {
	@Resource(name="objectDAO")
	private ObjectDAO dao;
	private static Logger log = Logger.getLogger(MeowPayBean.class); 
	/**
	 * 将数据转换为DateMoney
	 */
	private List<MeowPayDao> toDateMoney(List<Object [] > list) throws ParseException{
		List<MeowPayDao> meowPay=new ArrayList<MeowPayDao>();
		if(!QwyUtil.isNullAndEmpty(list)){
			for (Object [] object : list) {
				MeowPayDao plat=new MeowPayDao();
				
				plat.setInsertTime(QwyUtil.fmyyyyMMddHHmmss.parse(QwyUtil.fmyyyyMMddHHmmss.format(object[0])));
				plat.setUserName(object[1]==null?"":object[1]+"");
				plat.setNumber(object[2]==null?"":object[2]+"");				
				plat.setType(getType(object[3]+""));
				plat.setTotalCoin(object[4]+"");
				meowPay.add(plat);

			}
		}
		return meowPay;
	}
	
	/**区别瞄币的消费用途
	 * @param type
	 * @return
	 */
	public String getType(String type){
		String str="";
           if(type.equals("2"))
			str="投资券";
		else if(type.equals("3"))
			str="其他";
		return str;
	}
	/**
	 * 分页查询瞄币的支出
	 * @param name
	 * @param insertTime
	 * @param pageUtil
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public PageUtil<MeowPayDao> loadMeowPay(String name,String insertTime,PageUtil pageUtil) {
		
		
		try {
		ArrayList<Object> ob = new ArrayList<Object>();
		StringBuffer buff = new StringBuffer();
		buff.append("SELECT  r.`insert_time` ,   u.`username` AS username,  m.`coin` AS coin,  m.type as type , IFNULL(r.total_coin,'0') AS total_coin FROM ");
		buff.append("users u   JOIN `m_coin_pay` m  ON u.id = m.`users_id`JOIN `m_coin_record` r ON  r.`record_id` = m.record_number     WHERE 1 = 1 AND r.`coin_type`=1 ");
		
		if(!QwyUtil.isNullAndEmpty(name)){
			buff.append("AND u.username = ? ");
			ob.add(DESEncrypt.jiaMiUsername(name));
		}
		

		if(!QwyUtil.isNullAndEmpty(insertTime)){
			String [] time=QwyUtil.splitTime(insertTime);
			if(time.length > 1)
			{
				buff.append(" AND r.insert_time >= ? ");
				ob.add(QwyUtil.fmMMddyyyyHHmmss.parse(time[0]+" 00:00:00"));
				buff.append(" AND r.insert_time <= ? ");
				ob.add(QwyUtil.fmMMddyyyyHHmmss.parse(time[1]+" 23:59:59"));
			}
			else{

				buff.append(" AND r.insert_time >= ? ");
				ob.add(QwyUtil.fmMMddyyyyHHmmss.parse(time[0]+" 00:00:00"));
				buff.append(" AND r.insert_time <= ? ");
				ob.add(QwyUtil.fmMMddyyyyHHmmss.parse(time[0]+" 23:59:59"));
			}
			

		
		}
		
		//if(!QwyUtil.isNullAndEmpty(insertTime)){
			
			//buff.append(" AND DATE_FORMAT(  m.insert_time, '%Y-%m-%d' ) = "+"DATE_FORMAT('"+insertTime +"','%Y-%m-%d' )");
		
	   // }
		
		 buff.append(" ORDER BY m.insert_time DESC ");
			
			StringBuffer bufferCount=new StringBuffer();
			bufferCount.append(" SELECT COUNT(*)  ");
			bufferCount.append(" FROM (");
			bufferCount.append(buff);
			bufferCount.append(") t");
			//buff.append("ORDER BY fr.insert_time DESC ");
			 pageUtil=dao.getBySqlAndSqlCount(pageUtil, buff.toString(), bufferCount.toString(), ob.toArray());
			
			 List<MeowPayDao> platUsers=toDateMoney(pageUtil.getList());
				pageUtil.setList(platUsers);
				return pageUtil;
		} catch (Exception e) {
			//TODO Auto-generated catch block
			log.error("操作异常: ",e);
		}
			return null;
		
	}
}
