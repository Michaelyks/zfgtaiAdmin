package com.zfgt.admin.product.bean;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.zfgt.admin.product.dao.StartPageDAO;
import com.zfgt.common.bean.SystemConfigBean;
import com.zfgt.common.util.QwyUtil;
import com.zfgt.orm.StartPage;

@Service
public class StartPageBean {
	@Resource
	StartPageDAO dao;
	@Resource
	private SystemConfigBean systemConfigBean;

	/**
	 * @param type
	 *            类型0网站1手机
	 * @return
	 */
	public List<StartPage> findStartPages(String type) {
		List<Object> list = new ArrayList<Object>();
		StringBuffer buffer = new StringBuffer();
		buffer.append(" FROM StartPage sp ");
		if (!QwyUtil.isNullAndEmpty(type)) {
			buffer.append(" WHERE sp.type in (" + type + ") ");
			list.add(type);
		}
		buffer.append(" ORDER BY  sp.insertTime DESC ");
		return dao.LoadAll(buffer.toString(), null);
	}

	/**
	 * 保存StartPage
	 */
	public StartPage saveStartPage(StartPage sp) {
		sp.setInsertTime(new Date());
		sp.setStatus("0");
		dao.save(sp);
		return sp;
	}

	/**
	 * 根据ID查询信息
	 * 
	 * @param id
	 * @return
	 */
	public StartPage findStartPageById(Long id) {
		return (StartPage) dao.findById(new StartPage(), id);
	}

	/**
	 * 根据ID修改状态
	 */
	public boolean updateStatusById(Long id) {
		StartPage sp = findStartPageById(id);
		if (!QwyUtil.isNullAndEmpty(sp)) {
			if (!QwyUtil.isNullAndEmpty(sp.getStatus()) && sp.getStatus().equals("1")) {
				sp.setStatus("0");
			} else {
				sp.setStatus("1");
			}
		}
		dao.saveOrUpdate(sp);
		return true;
	}
}
