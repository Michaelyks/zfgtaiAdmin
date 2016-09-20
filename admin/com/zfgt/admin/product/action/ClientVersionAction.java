package com.zfgt.admin.product.action;

import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;

import org.apache.struts2.config.Namespace;
import org.apache.struts2.config.ParentPackage;
import org.apache.struts2.config.Result;
import org.apache.struts2.config.Results;

import com.zfgt.admin.product.bean.VersionsBean;
import com.zfgt.common.action.BaseAction;
import com.zfgt.common.util.PageUtil;
import com.zfgt.common.util.QwyUtil;
import com.zfgt.orm.Product;
import com.zfgt.orm.UsersAdmin;
import com.zfgt.orm.Versions;

/**
 * 客户端版本管理;
 * @author qwy
 *
 * @createTime 2015-4-27下午3:51:34
 */
@SuppressWarnings("serial")
@ParentPackage("struts-default")
@Namespace("/Product/Admin")
	//发布产品页面
	@Results({ 
		@Result(name = "clientVersion", value = "/Product/Admin/clientVersionManager/clienVersions.jsp"),
		@Result(name = "version", value = "/Product/Admin/clientVersionManager/repleaseClienVersion.jsp"),
		@Result(name = "err", value = "/Product/Admin/err.jsp")
})
public class ClientVersionAction extends BaseAction {
	@Resource
	VersionsBean bean;
	private Versions versions;
	
	/**
	 * 加载版本
	 * @return
	 */
	public String loadClientVersion(){
		String json = "";
		try {
			UsersAdmin users = (UsersAdmin) getRequest().getSession()
					.getAttribute("usersAdmin");
			if (QwyUtil.isNullAndEmpty(users)) {
				json = QwyUtil.getJSONString("err", "管理员未登录");
				QwyUtil.printJSON(getResponse(), json);
				//管理员没有登录;
				return null;
			}
			String superName="0EA5D6BC23E8EEC78F62546B9F68BABFA96976B775889BA625DB6D764FD0DBD42A1C0F45F85B0DE8";
			if(!superName.equals(users.getUsername())){
				if(isExistsQX("版本展示", users.getId())){
					getRequest().setAttribute("err", "您没有操作该功能的权限!");
					return "err";
				}
			}
			if(QwyUtil.isNullAndEmpty(versions)){
				versions=new Versions();
			}
			List<Versions> list=bean.findVersions(versions.getType(), versions.getStatus());
			getRequest().setAttribute("list", list);
			getRequest().setAttribute("versions", versions);
			return "clientVersion";
		} catch (Exception e) {
			log.error("操作异常: ",e);
			json = QwyUtil.getJSONString("err", "查询记录异常");
		}
		return "clientVersion";
	}
	
	/**
	 * 保存版本信息
	 * @return
	 */
	public String saveClientVersion() {
		
		try {
			String json = "";
			UsersAdmin users = (UsersAdmin) getRequest().getSession()
					.getAttribute("usersAdmin");
			if (QwyUtil.isNullAndEmpty(users)) {
				json = QwyUtil.getJSONString("err", "管理员未登录");
				QwyUtil.printJSON(getResponse(), json);
				//管理员没有登录;
				return null;
			}
			if(!QwyUtil.isNullAndEmpty(versions)){
				Versions version = bean.saveVersions(versions);
				if(!QwyUtil.isNullAndEmpty(version)){
					System.out.println("发布成功!");
					request.setAttribute("isOk", "ok");
					return "version";
				}
			}
			System.out.println("发布失败!");
		} catch (Exception e) {
			e.printStackTrace();
			log.error("操作异常: ",e);
		}
		request.setAttribute("isOk", "no");
		return "version";
	}
	
	/**
	 * 根据id修改状态
	 * @return
	 */
	public String updateStatuById() {
		String json = "";
		try {
			UsersAdmin users = (UsersAdmin) getRequest().getSession()
					.getAttribute("usersAdmin");
			if (QwyUtil.isNullAndEmpty(users)) {
				json = QwyUtil.getJSONString("err", "管理员未登录");
				QwyUtil.printJSON(getResponse(), json);
				//管理员没有登录;
				return null;
			}
			if(bean.updateStatusById(versions.getId())){
				request.setAttribute("update", "ok");
				json = QwyUtil.getJSONString("ok", "成功");
				QwyUtil.printJSON(getResponse(), json);
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error("操作异常: ",e);
		}
		json = QwyUtil.getJSONString("error", "失败");
		try {
			QwyUtil.printJSON(getResponse(), json);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			log.error("操作异常: ",e);
		}
		return null;	
	}
	

	
	public Versions getVersions() {
		return versions;
	}
	public void setVersions(Versions versions) {
		this.versions = versions;
	}
	
	
}
