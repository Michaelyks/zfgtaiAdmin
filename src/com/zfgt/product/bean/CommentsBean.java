package com.zfgt.product.bean;



import java.util.Date;

import javax.annotation.Resource;


import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.zfgt.common.util.PageUtil;
import com.zfgt.common.util.QwyUtil;
import com.zfgt.orm.Comments;
import com.zfgt.orm.Product;
import com.zfgt.product.dao.CommentsDAO;

/**
 * 首页Bean层,处理业务逻辑;
 * @author 覃文勇
 * @createTime 2015-7-27下午5:05:26
 */
@Service
public class CommentsBean {
	private static Logger log = Logger.getLogger(CommentsBean.class);
	
	@Resource
	private CommentsDAO dao;
	
	/**首页用户评论;
	 * @param ip //客户IP  
	 * @param name //用户名
	 * @param contact //联系方式
	 * @param content //评论内容
	 * @return
	 */

	public String addComments(String name,String contact,String content, String ip ){
		try {			
			Comments comments=new Comments();
			comments.setName(name);
			comments.setContact(contact);
			comments.setContent(content);
			comments.setInsertTime(new Date());
			comments.setType(String.valueOf(0));
			comments.setIp(ip);
			return dao.save(comments);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.error("操作异常: ",e);
		}
		return null;
	}
	

}
