package com.zfgt.product.action;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.apache.struts2.config.Namespace;
import org.apache.struts2.config.ParentPackage;
import org.apache.struts2.config.Result;
import org.apache.struts2.config.Results;

import com.zfgt.common.action.BaseAction;
import com.zfgt.common.util.PageUtil;
import com.zfgt.common.util.QwyUtil;
import com.zfgt.orm.Comments;
import com.zfgt.orm.Notice;
import com.zfgt.product.bean.CommentsBean;
import com.zfgt.product.bean.IndexNoticeBean;

/**公告栏<br>
 * 显示网站公告
 * @author qwy
 *
 * @createTime 2015-06-06 09:48:46
 */
@SuppressWarnings("serial")
@ParentPackage("struts-default")
@Namespace("/Product")
@Results({ 
	@Result(name = "comments", value = "/Product/about_us.jsp"),
	@Result(name = "err", value = "/Product/error.jsp")
})
public class CommentsAction extends BaseAction {
	private static Logger log = Logger.getLogger(CommentsAction.class);
	@Resource
	private CommentsBean bean;
	
	private Comments comments ;
	
	/**获取关于我们的公告;
	 * @return
	 */
	public String saveComments(){
		String json="";
		try{
		String ip=QwyUtil.getIpAddr(getRequest());
		if(comments==null){
			json = QwyUtil.getJSONString("err", "请填写留言");
		}else{      
        if(QwyUtil.isNullAndEmpty(comments.getName())){//判断用户名是否为空
        	json = QwyUtil.getJSONString("err", "姓名不能为空");
        	QwyUtil.printJSON(getResponse(), json);
        	return null;
        }
        if(comments.getName().length()>10){//判断用户名是否为空
        	json = QwyUtil.getJSONString("err", "请填写正确的姓名");
        	QwyUtil.printJSON(getResponse(), json);
        	return null;
        }
        if(QwyUtil.isNullAndEmpty(comments.getContact())){//判断联系方式是否为空
        	json = QwyUtil.getJSONString("err", "请填写联系方式");
        	QwyUtil.printJSON(getResponse(), json);
        	return null;
        }
        if(comments.getContact().length()<6||comments.getContact().length()>30){//判断联系方式是否为空
        	json = QwyUtil.getJSONString("err", "请填写正确的联系方式");
        	QwyUtil.printJSON(getResponse(), json);
        	return null;
        }
//		String p_email = "^([a-zA-Z0-9]+[-|_|\\_|\\.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[-|_|\\_|\\.]?)*[a-zA-Z0-9]+\\.[a-zA-Z]{2,3}$";
//		Pattern pattern_1 = Pattern.compile(p_email);
//		Matcher m_email = pattern_1.matcher(comments.getContact());
//		if(!m_email.find()&&!QwyUtil.verifyPhone(comments.getContact())){
//			json = QwyUtil.getJSONString("err", "请填写正确的联系方式");
//        	QwyUtil.printJSON(getResponse(), json);
//        	return null;
//		}
        if(QwyUtil.isNullAndEmpty(comments.getContent())){//判断留言内容是否为空
        	json = QwyUtil.getJSONString("err", "请填写留言内容");
        	QwyUtil.printJSON(getResponse(), json);
        	return null;
        }
       String id =  bean.addComments(comments.getName(),comments.getContact(), comments.getContent(), ip);
       if(!QwyUtil.isNullAndEmpty(id)){
    	   json = QwyUtil.getJSONString("ok", "留言成功");
       }else{
    	   json = QwyUtil.getJSONString("err", "留言失败");
       }
		
		}
	  }catch (Exception e) {
		// TODO: handle exception
			log.error("操作异常: ",e);
			json = QwyUtil.getJSONString("err", "留言失败");
	}
		try {
			System.out.println(json);
			QwyUtil.printJSON(getResponse(), json);
		} catch (IOException e) {
			log.error("操作异常: ",e);
		}
		return null;
	}

	public Comments getComments() {
		return comments;
	}

	public void setComments(Comments comments) {
		this.comments = comments;
	}
	
	
	
}
