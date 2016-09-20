/**
 * 
 */
package com.zfgt.admin.product.bean;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.zfgt.admin.product.dao.ModulDAO;
import com.zfgt.common.util.QwyUtil;
import com.zfgt.orm.Modul;

/**
 * @author 曾礼强
 * 2015年8月8日下午3:16:15
 */
@Service
public class ModulBean{
	private static Logger log = Logger.getLogger(ModulBean.class);
	@Resource 
	ModulDAO dao;
	@Resource
	private AdminLogBean logBean;
	/**
	 * 保存modul
	 * @param modul
	 * @param usersAdminId 创建人ID
	 * @return
	 */
	public Modul saveModul(Modul modul, Long usersAdminId){
		if(modul.getType().equals("1")){
			modul.setParentId(null);
			modul.setModulPath(null);
		}
		String id=dao.saveAndReturnId(modul);
		logBean.saveModulLog(usersAdminId, Long.parseLong(id));
		modul=(Modul) dao.findById(new Modul(), Long.parseLong(id));
		return modul;
	}
	
	
	/**
	 * 根据类型查询
	 * @param type 类型1. 第一级2. 第二级 3. 第三级
	 * @return
	 */
	public List<Modul> findModulsByType(String type){
		List<Object> objects=new ArrayList<Object>();
		StringBuffer hql=new StringBuffer();
		hql.append(" FROM Modul md  ");
		if(!QwyUtil.isNullAndEmpty(type)){
			hql.append(" WHERE md.type = ?  ");
			objects.add(type);
		}
		hql.append(" ORDER BY md.type ASC, md.parentId ASC, md.sort ASC, md.id DESC ");
		return dao.LoadAll(hql.toString(), objects.toArray());
		
	}
	
	/**
	 * 转换为JSONArray对象
	 */
	public JSONArray getArray(List<Modul> list){
		JSONArray array=new JSONArray();
		if(!QwyUtil.isNullAndEmpty(list)){
			for (Modul modul : list) {
				JSONObject object=new JSONObject();
				object.put("id", modul.getId());
				object.put("modulName", modul.getModulName());
				object.put("modulPath", modul.getModulPath());
				if(!QwyUtil.isNullAndEmpty(modul.getParentId()))
					object.put("parentId", modul.getParentId());
				else{
					object.put("parentId", "0");
				}
				object.put("note", modul.getNote());
				object.put("modulCode", modul.getModulCode());
				object.put("type", modul.getType());
				object.put("sort", modul.getSort());
				object.put("modulType", modul.getModulType());
				array.add(object);
			}
		}
		return array;
		
	}
	
	/**根据模块ID来查找模块;
	 * @param id Modul.ID
	 * @return
	 */
	public Modul getModulById(long id){
		return (Modul)dao.findById(new Modul(), id);
	}
	
	public boolean modifyModul(Modul oldModul){
		Modul myModul = getModulById(oldModul.getId());
		try {
			if(QwyUtil.isNullAndEmpty(myModul)){
				log.info("修改权限__找不到Modul表");
				return false;
			}else{
				myModul.setModulName(oldModul.getModulName());
				myModul.setModulPath(oldModul.getModulPath());
				myModul.setType(oldModul.getType());
				myModul.setParentId(oldModul.getParentId());
				myModul.setSort(oldModul.getSort());
				myModul.setNote(oldModul.getNote());
				myModul.setUpdateTime(new Date());
				dao.update(myModul);
				return true;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			log.error("修改权限__出错"+e.getMessage(),e);
			return false;
		}
	}
	
}
