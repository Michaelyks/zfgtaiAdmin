package com.zfgt.product.bean;

import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.zfgt.common.util.PageUtil;
import com.zfgt.common.util.QwyUtil;
import com.zfgt.orm.Notice;
import com.zfgt.product.dao.IndexDAO;

/**公告栏Bean层;
 * @author qwy
 *
 * @createTime 2015-6-6上午9:52:08
 */
@Service
public class IndexNoticeBean {
	private static Logger log = Logger.getLogger(IndexNoticeBean.class);
	@Resource
	private IndexDAO dao;
	
	/**获取公告
	 * @param pageUtil
	 * @param types 类型;-1:隐藏的公告(不显示在界面,做seo优化);0:平台公告; 1:获奖公告;2:媒体公告; 默认为0;
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public PageUtil<Notice> loadNoticeByPageUtil(PageUtil<Notice> pageUtil, String[] types){
		String type = QwyUtil.packString(types);
		StringBuffer hql = new StringBuffer();
		hql.append("FROM Notice nt ");
		if(!QwyUtil.isNullAndEmpty(type))
			hql.append(" WHERE nt.type IN ("+type+") ");
		hql.append(" ORDER BY nt.isTop DESC,nt.insertTime DESC ");
		return (PageUtil<Notice>)dao.getPage(pageUtil, hql.toString(), null);
	}
	
	/**根据公告id,获得具体的公告内容;
	 * @param id 公告 id
	 * @return
	 */
	public Notice getNoticeById(String id){
		if(QwyUtil.isNullAndEmpty(id))
			return null;
		return (Notice)dao.findById(new Notice(), id);
	}
}
