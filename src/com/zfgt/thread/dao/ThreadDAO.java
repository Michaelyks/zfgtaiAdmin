package com.zfgt.thread.dao;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.zfgt.common.dao.ObjectDAO;
import com.zfgt.orm.Modul;
import com.zfgt.orm.RolesRight;
import com.zfgt.orm.SystemConfig;
/**后台线程--DAO
 * @author qwy
 *
 * @createTime 2015-04-28 09:52:25
 */
@Service
public class ThreadDAO extends ObjectDAO{
	private Logger log = Logger.getLogger(ThreadDAO.class);
	/**获取系统配置表
	 * @return
	 */
	public SystemConfig getSystemConfig(){
		return (SystemConfig)findById(new SystemConfig(), 1L);
	}
	
	/**获取所有权限;
	 * @return List&lt;Modul&gt;
	 */
	@SuppressWarnings("unchecked")
	public List<Modul> getModul(){
		StringBuffer buffer = new StringBuffer("FROM Modul md ");
		buffer.append(" ORDER BY md.type ASC, md.sort ASC");
		return (List<Modul>)LoadAll(buffer.toString(),null);
	}
	
	
	/**获取用户的权限;
	 * @return List&lt;RolesRight&gt;
	 */
	@SuppressWarnings("unchecked")
	public List<RolesRight> getRolesRight(){
		StringBuffer buffer = new StringBuffer("FROM RolesRight rr ");
		buffer.append("WHERE rr.status = 0 ");
		buffer.append("ORDER BY rr.modul.sort ASC ");
		return (List<RolesRight>)LoadAll(buffer.toString(), null);
	}
	
	/**获取所有用户的一级标题权限;
	 * @return List&lt;RolesRight&gt;
	 */
	@SuppressWarnings("unchecked")
	public List<RolesRight> getFirstRolesRight(){
		StringBuffer buffer = new StringBuffer("FROM RolesRight rr ");
		buffer.append("WHERE rr.status = 0 ");
		buffer.append("AND rr.modul.type = 1 ");
		buffer.append("ORDER BY rr.modul.sort ASC ");
		return (List<RolesRight>)LoadAll(buffer.toString(),null);
	}
	
	@SuppressWarnings("unchecked")
	public List<SystemConfig> getStartCleanMcoin(){
		StringBuffer buffer = new StringBuffer("FROM SystemConfig rr ");
		return (List<SystemConfig>)LoadAll(buffer.toString(),null);
	}
}
