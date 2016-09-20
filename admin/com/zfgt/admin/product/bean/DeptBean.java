package com.zfgt.admin.product.bean;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.zfgt.admin.product.dao.DeptDAO;
import com.zfgt.common.util.PageUtil;
import com.zfgt.common.util.QwyUtil;
import com.zfgt.orm.Dept;
import com.zfgt.orm.Test;

/**
 * @author 覃文勇
 * @createTime 2015-8-11下午5:19:28
 */
@Service
public class DeptBean {
	private static Logger log = Logger.getLogger(DeptBean.class); 
	@Resource
	DeptDAO dao;
	@SuppressWarnings("unchecked")
	public PageUtil<Dept> loadDeptList(PageUtil<Dept> pageUtil,Long userType,Long deptId){
		try {

			   ArrayList<Object> objects=new ArrayList<Object>();
			   StringBuffer hql=new StringBuffer();
			   hql.append(" FROM Dept d WHERE 1=1 ");
			if(userType==0L){
			if(!QwyUtil.isNullAndEmpty(deptId)){
			   hql.append(" AND d.id=?  ");
			   objects.add(deptId);
			  }else{
				return null;
			  }
			}
			  hql.append(" ORDER BY d.insertTime DESC ");
			  return dao.getPage(pageUtil, hql.toString(), objects.toArray());
			} catch (Exception e) {
				// TODO: handle exception
				log.error("操作异常: ",e);
			}
			  
		return null;
	}
/**
 * 添加新部门
 * @param deptName
 * @param note
 * @return
 */
	public String addDept(String deptName,String note){
		Dept dept=new Dept();
		dept.setDeptName(deptName);
		dept.setNote(note);
		dept.setStatus(String.valueOf(0));
		dept.setInsertTime(new Date());					
		return dao.saveAndReturnId(dept);
	}
	
	public Dept findDeptById(Long id){
		Dept dept=(Dept) dao.findById(new Dept(), id);
		return dept;
	}
	/**
	 * 修改部门
	 * @param id
	 * @param deptName
	 * @param note
	 * @return
	 */
	public String updateDept(Long id,String deptName,String note){
		
		Dept dept=(Dept) dao.findById(new Dept(), id);
		dept.setDeptName(deptName);
		dept.setNote(note);
		dept.setUpdateTime(new Date());	
		dao.saveOrUpdate(dept);		
		return null;
	}
	/**
	 * 获取部门列表，超级管理员的deptId为空，查询出所有部门
	 * @param deptId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Dept> findDeptList(Long deptId){
		try {
			  ArrayList<Object> objects=new ArrayList<Object>();
			  StringBuffer hql=new StringBuffer();
			   hql.append("FROM Dept d WHERE 1=1 ");			
			   hql.append(" AND d.status=0  ");
			   if(!QwyUtil.isNullAndEmpty(deptId)){
				   hql.append(" AND d.id=?  ");
				   objects.add(deptId) ;
			   }
			   hql.append(" ORDER BY d.insertTime DESC ");
			  return dao.LoadAll(hql.toString(),objects.toArray());
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		return null;
	}
	
	/**
	 * 修改部门状态  0：可用  1： 禁用
	 * @param id
	 * @param deptName
	 * @param note
	 * @return
	 */
	public String updateDeptByStatus(Dept dept,String status){
		dept.setStatus(status);
		dept.setUpdateTime(new Date());	
		dao.saveOrUpdate(dept);		
		return null;
	}
}
