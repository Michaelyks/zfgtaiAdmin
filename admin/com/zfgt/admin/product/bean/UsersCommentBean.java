package com.zfgt.admin.product.bean;

import java.util.ArrayList;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.zfgt.admin.product.dao.UsersCommentDAO;
import com.zfgt.common.util.DESEncrypt;
import com.zfgt.common.util.PageUtil;
import com.zfgt.common.util.QwyUtil;
import com.zfgt.orm.Comments;

/**
 * 用户评论
 * 
 * @author 覃文勇
 * @createTime 2015-7-21下午5:17:22
 */
@Service
public class UsersCommentBean {
	private static Logger log = Logger.getLogger(UsersCommentBean.class);
	@Resource
	UsersCommentDAO dao;

	/**
	 * 获取用户评论的数据
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public PageUtil<Comments> loadComments(PageUtil<Comments> pageUtil, String username) throws Exception {
		try {
			ArrayList<Object> ob = new ArrayList<Object>();
			// List<Object> arrayList = new ArrayList<Object>();
			StringBuffer hql = new StringBuffer();
			hql.append("FROM Comments c where 1=1 ");
			if (!QwyUtil.isNullAndEmpty(username)) {
				hql.append(" AND c.users.username = ? ");
				ob.add(DESEncrypt.jiaMiUsername(username));
			}

			hql.append(" ORDER BY  c.insertTime DESC ");

			return (PageUtil<Comments>) dao.getPage(pageUtil, hql.toString(), ob.toArray());
		} catch (Exception e) {
			log.error("操作异常: ", e);
		}
		return null;

	}

}
