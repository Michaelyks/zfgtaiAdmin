package com.zfgt.admin.Mcoin.action;

import java.io.IOException;
import javax.annotation.Resource;

import org.apache.struts2.config.Namespace;
import org.apache.struts2.config.ParentPackage;
import org.apache.struts2.config.Result;
import org.apache.struts2.config.Results;

import com.zfgt.common.action.BaseAction;
import com.zfgt.common.util.QwyUtil;
import com.zfgt.thread.action.CleanAllMCoinThread;
@SuppressWarnings("serial")
@ParentPackage("struts-default")
@Namespace("/Product/Admin")
//发布产品页面
@Results({ @Result(name = "cleanMCoin", value = "/Product/Admin/operationManager/mCoinDayDetail.jsp"),
})
public class CleanMCoinAction   extends BaseAction  {
	private Integer pageSize = 50;

	@Resource
	private CleanAllMCoinThread cleanAllMCoinThread; 
	
	public String cleanMCoinThread(){
		String json = "";
		try {
			new Thread(cleanAllMCoinThread).start();
			json = QwyUtil.getJSONString("ok", "启动成功");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			json = QwyUtil.getJSONString("error", "启动失败");
		}
		try {
			QwyUtil.printJSON(getResponse(), json);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
		
	}

	public Integer getPageSize() {
		return pageSize;
	}


	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

}
