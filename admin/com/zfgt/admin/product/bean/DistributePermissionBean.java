/**
 * 
 */
package com.zfgt.admin.product.bean;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.stereotype.Service;

import com.zfgt.admin.product.dao.RolesDAO;
import com.zfgt.common.util.QwyUtil;
import com.zfgt.orm.Modul;
import com.zfgt.orm.RolesRight;

/**
 * @author 曾礼强
 * 2015年8月12日下午4:57:16
 */
@Service
public class DistributePermissionBean {
	@Resource
	RolesDAO dao;
	/**
	 * 根据usersAdminId查询它的模块
	 * @param usersAdminId
	 * @return
	 */
	public List<Modul> findModuls(Long usersAdminId){
		List<Modul> list=new ArrayList<Modul>();
		List<Object> objects=new ArrayList<Object>();
		StringBuffer hql=new StringBuffer();
		hql.append(" FROM RolesRight rr ");
		hql.append(" WHERE rr.status = 0 ");
		if(!QwyUtil.isNullAndEmpty(usersAdminId)){
			hql.append(" AND rr.usersAdminId = ? ");
			objects.add(usersAdminId);
		}
		List<RolesRight> rolesRights=dao.LoadAll(hql.toString(), objects.toArray());
		if(!QwyUtil.isNullAndEmpty(rolesRights)){
			for (RolesRight rolesRight : rolesRights) {
				list.add(rolesRight.getModul());
			}
		}
		return list;
	}
	
	/**
	 * 根据usersAdminId查询关联表
	 * @param usersAdminId
	 * @return
	 */
	public List<RolesRight> findRolesRights(Long usersAdminId,String status){
		List<Object> objects=new ArrayList<Object>();
		StringBuffer hql=new StringBuffer();
		hql.append(" FROM RolesRight WHERE 1=1 ");
		if(!QwyUtil.isNullAndEmpty(usersAdminId)){
			hql.append(" AND usersAdminId = ? ");
			objects.add(usersAdminId);
		}
		if(!QwyUtil.isNullAndEmpty(status)){
			 hql.append(" AND status = ? ");
			objects.add(status);
		}
		return dao.LoadAll(hql.toString(), objects.toArray());
	}
	
	/**
	 * 保存用户模块
	 */
	public boolean saveRoleRight(String modulIds,Long createId,Long usersAdminId){
		try {
			if (!QwyUtil.isNullAndEmpty(modulIds)) {
				List<RolesRight> rolesRights = new ArrayList<RolesRight>();
				List<RolesRight> qxrolesRights = new ArrayList<RolesRight>();
				String[] moduId = modulIds.split(",");
				if (!QwyUtil.isNullAndEmpty(moduId)) {
					List<RolesRight> list = findRolesRights(usersAdminId, null);
					if (!QwyUtil.isNullAndEmpty(list)) {
						Map<String, Object> map = QwyUtil.ListToMap("modulId", list);
						for (int i = 0; i < moduId.length; i++) {
							String mId = moduId[i];
							//不存在直接保存
							if (QwyUtil.isNullAndEmpty(map.get(mId))) {
								map.remove(mId);
								RolesRight rolesRight = new RolesRight();
								rolesRight.setCreateId(createId);
								rolesRight.setInsertTime(new Date());
								rolesRight.setModulId(Long.parseLong(mId));
								rolesRight.setStatus("0");
								rolesRight.setUsersAdminId(usersAdminId);
								rolesRights.add(rolesRight);
							}else{
								//存在判断状态
								RolesRight right=  (RolesRight) map.get(mId);
								if(right.getStatus().equals("1")){
									right.setStatus("0");
									right.setUpdateTime(new Date());
									qxrolesRights.add(right);
								}
								map.remove(mId);
								
							}
						}
						//有剩余的，就是取消权限的
						if(!QwyUtil.isNullAndEmpty(map)){
							for (String key : map.keySet()) {
								System.out.println("key= "+ key + " and value= " + map.get(key));
								RolesRight right=  (RolesRight) map.get(key);
								right.setStatus("1");
								right.setUpdateTime(new Date());
								qxrolesRights.add(right);
							}
						}
					} else {
						for (int i = 0; i < moduId.length; i++) {
							String mId = moduId[i];
							RolesRight rolesRight = new RolesRight();
							rolesRight.setCreateId(createId);
							rolesRight.setInsertTime(new Date());
							rolesRight.setModulId(Long.parseLong(mId));
							rolesRight.setStatus("0");
							rolesRight.setUsersAdminId(usersAdminId);
							rolesRights.add(rolesRight);
						}
					}
					if(!QwyUtil.isNullAndEmpty(qxrolesRights)){
						dao.updateList(qxrolesRights);
					}
					dao.saveList(rolesRights);
					return true;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
		
	}
	
	/**
	 * 转换为JSONArray对象
	 */
	public JSONArray getArray(List<Modul> list,List<RolesRight> rolesRights){
		Map<String, Object> map=new HashMap<String, Object>();
		if(!QwyUtil.isNullAndEmpty(rolesRights)){
			map = QwyUtil.ListToMap("modulId", rolesRights);
		}
		JSONArray array=new JSONArray();
		if(!QwyUtil.isNullAndEmpty(list)){
			for (Modul modul : list) {
				JSONObject object=new JSONObject();
				object.put("id", modul.getId());
				object.put("name", modul.getModulName());
				if(!QwyUtil.isNullAndEmpty(modul.getParentId()))
					object.put("pId", modul.getParentId());
				else{
					object.put("pId", "0");
				}
				if(!QwyUtil.isNullAndEmpty(map.get(modul.getId()+""))){
					object.put("checked", true);
				}
				array.add(object);
			}
		}
		return array;
		
	}
	
	/**
	 * 根据管理员ID获取它所有的权限
	 * @return map 集合
	 */
	public Map<Long,String> findModulByUserAdminId(){
		List<RolesRight> list=findRolesRights(null,"0");
		//存储权限关联表,key为管理员ID
		Map<Long,String> map=new HashMap<Long, String>();
		if(!QwyUtil.isNullAndEmpty(list)){
			for (RolesRight rolesRight : list) {
				//判断管理员在map中是否存在
				if(QwyUtil.isNullAndEmpty(map.get(rolesRight.getUsersAdminId()))){
					map.put(rolesRight.getUsersAdminId(), rolesRight.getModul().getModulName());
				}else{
					String value=map.get(rolesRight.getUsersAdminId())+"、"+rolesRight.getModul().getModulName();
					map.put(rolesRight.getUsersAdminId(), value);
				}
			}
		}
		return map;
		
	}
	
}
