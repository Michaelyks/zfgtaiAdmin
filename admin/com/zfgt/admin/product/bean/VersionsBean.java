package com.zfgt.admin.product.bean;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.zfgt.admin.product.dao.VersionsDAO;
import com.zfgt.common.ApplicationContexts;
import com.zfgt.common.Commons;
import com.zfgt.common.bean.YiBaoPayBean;
import com.zfgt.common.util.QwyUtil;
import com.zfgt.orm.Versions;

@Service
public class VersionsBean {
	@Resource
	VersionsDAO dao;
	private static Logger log = Logger.getLogger(VersionsBean.class); 
	/**
	 * 查找IOS版本信息
	 * @param type  0 iOS 1 Android 
	 * @return
	 */
	public List<Versions> findVersions(String type,String status) throws Exception{
		List<Object> list=new ArrayList<Object>();
		StringBuffer hql=new StringBuffer();
		hql.append(" FROM Versions v ");
		hql.append(" WHERE 1 = 1 ");
		if(!QwyUtil.isNullAndEmpty(type)){
			hql.append(" AND v.type = ? ");
			list.add(type);
		}
		if(!QwyUtil.isNullAndEmpty(status)){
			hql.append(" AND v.status = ? ");
			list.add(status);
		}
		hql.append(" ORDER BY v.insertTime DESC");
		
		return dao.LoadAll(hql.toString(), list.toArray());
	}
	
	/**
	 * 修改版本为过期状态
	 * @param type 0 IOS 1 Android
	 */
	public boolean updateStatus(String type){
		List<Object> list=new ArrayList<Object>();
		StringBuffer sql=new StringBuffer();
		sql.append(" update versions set status = '1' ");
		if(!QwyUtil.isNullAndEmpty(type)){
			sql.append("where type = ? ");
			list.add(type);
		}
		dao.updateBySql(sql.toString(), list.toArray());
		return true;
	}
	
	/**
	 * 根据ID查询版本信息
	 * @param id
	 * @return
	 */
	public Versions findVersionsById(String id){
		return (Versions) dao.findById(new Versions(), id);
	}
	
	/**
	 * 根据ID修改版本信息
	 */
	public boolean updateStatusById(String id){
		Versions versions=findVersionsById(id);
		if(!QwyUtil.isNullAndEmpty(versions)){
			if(!QwyUtil.isNullAndEmpty(versions.getStatus())&&versions.getStatus().equals("1")){
				versions.setStatus("0");
			}else{
				versions.setStatus("1");
			}
		}
		dao.saveOrUpdate(versions);	
		return true;
	}
	/**
	 * 保存版本信息
	 * @param versions
	 * @return
	 */
	public Versions saveVersions(Versions versions){
		ApplicationContext context = ApplicationContexts.getContexts();
		PlatformTransactionManager tm = (PlatformTransactionManager) context.getBean("transactionManager");
		TransactionStatus ts = tm.getTransaction(new DefaultTransactionDefinition());
		try{
			if(updateStatus(versions.getType())){
				versions.setInsertTime(new Date());
				versions.setStatus("0");
				dao.saveOrUpdate(versions);	
				return versions;
			}
		} catch (Exception e) {
			log.info("回滚事务");
			tm.rollback(ts);
			e.printStackTrace();
			log.error(e.getMessage(),e);
		}
		return null;
	}
	
	
}
