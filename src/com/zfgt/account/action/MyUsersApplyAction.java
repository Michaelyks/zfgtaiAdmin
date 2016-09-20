package com.zfgt.account.action;

import java.io.IOException;

import javax.annotation.Resource;

import org.apache.log4j.Logger;

import com.zfgt.admin.product.bean.UsersApplyBean;
import com.zfgt.common.action.BaseAction;
import com.zfgt.common.util.QwyUtil;
import com.zfgt.orm.UsersApply;

@SuppressWarnings("serial")
public class MyUsersApplyAction  extends BaseAction {
	private static Logger log = Logger.getLogger(MyUsersApplyAction.class); 
	@Resource
	UsersApplyBean bean;
	private UsersApply usersApply;
	
	public String saveUsersApply(){
		String json="";
		try {
			if(usersApply==null){
				json = QwyUtil.getJSONString("err", "请填写申请");
			}else{
				if(QwyUtil.isNullAndEmpty(usersApply.getUsersId())){//判断用户id是否为空
		        	json = QwyUtil.getJSONString("err", "请先登录！");
		        	QwyUtil.printJSON(getResponse(), json);
		        	return null;
		        }
			    String id =  bean.addUsersApply(usersApply.getUsersId(),usersApply.getNote());
			    if(!QwyUtil.isNullAndEmpty(id)){
		        	json = QwyUtil.getJSONString("err", "申请成功！");
		        	QwyUtil.printJSON(getResponse(), json);
			    }else{
		        	json = QwyUtil.getJSONString("err", "申请失败！");
		        	QwyUtil.printJSON(getResponse(), json);
			    }
			}
		} catch (Exception e) {
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

}
