package com.zfgt.admin.product.bean;

import java.util.Date;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.zfgt.admin.product.dao.AdminLogDAO;
import com.zfgt.orm.AdminLog;

/**
 * @author 覃文勇
 * @createTime 2015-8-12下午3:43:41
 */
  @Service
public class AdminLogBean {
	//类型 1：后台用户表 2：部门表 3：模块表
  @Resource
  private AdminLogDAO dao;
/**
 * 操作后台用户表记录
 * @param usersAdminId
 * @return
 */
 public String saveUsersLog(Long usersAdminId,Long id){
	 
	 return saveAdminLog("1",id, usersAdminId,"后台用户表");
 }
 /**
  * 操作部门表记录
  * @param usersAdminId
  * @return
  */
 public String saveDeptLog(Long usersAdminId,Long id){
	 return saveAdminLog("2",id,usersAdminId,"部门表");
 }
 /**
  * 操作模块表记录
  * @param usersAdminId
  * @return
  */
 public String saveModulLog(Long usersAdminId,Long id){
	 return saveAdminLog("3",id,usersAdminId,"模块表");
 }
 /**
  * 操作用户申请表记录
  * @param usersAdminId
  * @param id
  * @return
  */
 public String saveApplyLog(Long usersAdminId,Long id){
	 return saveAdminLog("4",id,usersAdminId,"用户申请表");
 }

 
 /**
  * 插入后台操作记录
  * @param type
  * @param usersAdminId
  * @return
  */
  public String saveAdminLog(String type,Long typeId,Long usersAdminId,String note){
	  AdminLog adminLog=new AdminLog();
	  adminLog.setInsertTime(new Date());
	  adminLog.setType(type);
	  adminLog.setTypeId(typeId);
	  adminLog.setUsersAdminId(usersAdminId);
	  adminLog.setNote(note);
	  return dao.saveAndReturnId(adminLog);
  }

}
