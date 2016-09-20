package com.zfgt.admin.product.action;

import java.io.IOException;

import javax.annotation.Resource;

import org.apache.struts2.config.Namespace;
import org.apache.struts2.config.ParentPackage;
import org.apache.struts2.config.Result;
import org.apache.struts2.config.Results;

import com.zfgt.common.action.BaseAction;
import com.zfgt.common.bean.YiBaoPayBean;
import com.zfgt.common.util.QwyUtil;
import com.zfgt.thread.action.FinishCouponThread;
import com.zfgt.thread.action.SendProfitThread;
import com.zfgt.thread.action.TxQueryThread;
import com.zfgt.thread.action.UpdateProductThread;

/**后台管理--启动线程
 * @author qwy
 *
 * @createTime 2015-4-27下午3:51:34
 */
@SuppressWarnings("serial")
@ParentPackage("struts-default")
@Namespace("/Product/Admin")
//发布产品页面
@Results({ @Result(name = "function", value = "/Product/Admin/functionManager/function.jsp")
})
public class FunctionAction extends BaseAction {
	private String orderid ="";
	private String usersId = "";
	/**
	 * 易宝支付第三方接口Bean层;
	 */
	@Resource
	private YiBaoPayBean yiBaoPayBean; 
	
	/**查询支付结果;充值结果;
	 * @return
	 */
	public String queryPay(){
		String json = "";
		try {
			//更新理财产品;包括:更新之后,结算常规的理财产品和新手理财产品;
			String result = yiBaoPayBean.queryPay(orderid);
			json = QwyUtil.getJSONString("ok", result.replaceAll("\"", "'"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			json = QwyUtil.getJSONString("error", "查询失败");
		}
		try {
			QwyUtil.printJSON(getResponse(), json);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	/**提现记录查询;
	 * @return
	 */
	public String withdrawQuery(){
		String json = "";
		try {
			//更新理财产品;包括:更新之后,结算常规的理财产品和新手理财产品;
			String result = yiBaoPayBean.withdrawQuery(orderid);
			json = QwyUtil.getJSONString("ok", result.replaceAll("\"", "'"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			json = QwyUtil.getJSONString("error", "查询失败");
		}
		try {
			QwyUtil.printJSON(getResponse(), json);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	/**根据用户ID,查询绑卡记录;
	 * @return
	 */
	public String bindList(){
		String json = "";
		try {
			//更新理财产品;包括:更新之后,结算常规的理财产品和新手理财产品;
			boolean isOk = QwyUtil.isOnlyNumber(usersId);
			if(isOk){
				String result = yiBaoPayBean.bindList(Long.parseLong(usersId));
				json = QwyUtil.getJSONString("ok", result.replaceAll("\"", "'"));
			}else{
				json = QwyUtil.getJSONString("error", "无绑卡记录");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			json = QwyUtil.getJSONString("error", "查询失败");
		}
		try {
			QwyUtil.printJSON(getResponse(), json);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public String getOrderid() {
		return orderid;
	}

	public void setOrderid(String orderid) {
		this.orderid = orderid;
	}

	public String getUsersId() {
		return usersId;
	}

	public void setUsersId(String usersId) {
		this.usersId = usersId;
	}



}
