package com.zfgt.common.bean;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.zfgt.common.dao.SystemConfigDAO;
import com.zfgt.orm.SystemConfig;
/**
 * 获取系统配置表
 * @author 曾礼强
 *2015年5月19日 15:31:38
 */
@Service
public class SystemConfigBean {
	@Resource 
	SystemConfigDAO dao;

	/**
	 * 修改配置文件
	 * 
	 * @param config
	 * @return
	 * @throws Exception
	 */
	public boolean updateSystemconfig(SystemConfig config) throws Exception {
		// config.setCompanyName(new String
		// (config.getCompanyName().getBytes("ISO-8859-1"),"UTF-8"));
		// config.setTjy(new String
		// (config.getTjy().getBytes("ISO-8859-1"),"UTF-8"));
		// config.setHdby(new String
		// (config.getTjy().getBytes("ISO-8859-1"),"UTF-8"));
		dao.saveOrUpdate(config);
		return true;
	}

	/**
	 * 查询系统配置信息
	 * 
	 * @return
	 */
	public SystemConfig findSystemConfig(){
		return (SystemConfig) dao.findById(new SystemConfig(), 1L);
	}
}
