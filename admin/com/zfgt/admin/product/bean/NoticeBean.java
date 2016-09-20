package com.zfgt.admin.product.bean;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.zfgt.admin.product.dao.NoticeDAO;
import com.zfgt.common.util.QwyUtil;
import com.zfgt.orm.Notice;
import com.zfgt.orm.UsersAdmin;

@Service
public class NoticeBean {
	@Resource 
	NoticeDAO dao;
	/**
	 * 保存公告
	 * @param notice
	 * @param users
	 * @return
	 */
	public String saveNotice(Notice notice, UsersAdmin users){
		notice.setUsersAdminId(users.getId());
		notice.setInsertTime(new Date());
		return dao.save(notice);
	}
	
	/**
	 * 修改公告
	 * @param notice
	 * @return
	 */
	public boolean updateNotice(Notice notice){
		Notice nt=findNoticeById(notice.getId());
		if(!QwyUtil.isNullAndEmpty(nt.getTitleUrl())){
			notice.setTitleUrl(nt.getTitleUrl());
		}
		notice.setContent(nt.getContent());
		notice.setUsersAdminId(nt.getUsersAdminId());
		notice.setInsertTime(nt.getInsertTime());
		dao.update(notice);
		return true;
	}
	
	/**
	 * 下架
	 * @param notice
	 * @return
	 */
	public boolean updateNoticeType(Notice notice){
		Notice nt=findNoticeById(notice.getId());
		nt.setType("-2");
		dao.update(nt);
		return true;
	}
	
	/**
	 * 根据ID查询公告
	 */
	public Notice findNoticeById(String id){
		return (Notice) dao.findById(new Notice(), id);
	}
	
	/**
	 * 公告展示
	 */
	public List<Notice> findNotices(){
		StringBuffer buffer=new StringBuffer();
		buffer.append(" FROM Notice n ");
		buffer.append(" ORDER BY n.insertTime DESC ");
		return dao.LoadAll(buffer.toString(),null);
	}
}
