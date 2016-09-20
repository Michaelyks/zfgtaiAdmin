package com.zfgt.admin.product.bean;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.zfgt.admin.product.dao.HdFlagDAO;
import com.zfgt.common.util.PageUtil;
import com.zfgt.common.util.QwyUtil;
import com.zfgt.orm.HdFlag;

/**
 * @author 覃文勇
 * @createTime 2015-8-24下午6:09:31
 */
@Service
public class HdFlagBean {
	private static Logger log = Logger.getLogger(HdFlagBean.class);
	@Resource
	HdFlagDAO dao;
	/**
	 * 获取活动记录表
	 * @param pageUtil
	 * @param status
	 * @return
	 */

	@SuppressWarnings("unchecked")
	public PageUtil<HdFlag> loadHdFlag(PageUtil<HdFlag> pageUtil,String status){
		ArrayList<Object> objects = new ArrayList<Object>();
		StringBuffer hql = new StringBuffer();
		hql.append(" FROM HdFlag h WHERE 1=1");
		if(!QwyUtil.isNullAndEmpty(status)){
			hql.append(" and h.status >= ?");	
			objects.add(status);
		}
		
		hql.append(" ORDER BY h.insertTime DESC ");
		return dao.getPage(pageUtil, hql.toString(), objects.toArray());
	}
	
	
	public String addHdFlag(String flag, Date insertTime,Date endTime,String note){
		HdFlag hdFlag=new HdFlag();
		hdFlag.setFlag(flag);
		hdFlag.setEndTime(endTime);
		hdFlag.setInsertTime(insertTime);
		hdFlag.setNote(note);
		hdFlag.setStatus(String.valueOf(0L));
		return dao.saveAndReturnId(hdFlag);
	}
	/**
	 * 根据id查询数据	
	 * @param id
	 * @return
	 */
	public HdFlag findHdFlagById(Long id){
		return (HdFlag) dao.findById(new HdFlag(), id);
	}
	/**
	 * 根据flag获取数据
	 * @param id
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public HdFlag findHdFlagByFlag(String flag){
		HdFlag result=null;
		String hql="FROM HdFlag where flag='"+flag+"'";
		List<HdFlag> list=dao.LoadAll(hql, null);
		if(list.size()>0){
			for(int i=0;i<list.size();i++){
				result=list.get(0);
			}
		}
		return result;
	}
/**
 * 修改状态	
 * @param hdFlag
 * @return
 */
	public Boolean modifyStatus(HdFlag hdFlag){
		hdFlag.setStatus(String.valueOf(1));
		hdFlag.setUpdateTime(new Date());
		dao.saveOrUpdate(hdFlag);
		return true;
	}
	
}
