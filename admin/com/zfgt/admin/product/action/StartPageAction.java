package com.zfgt.admin.product.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;

import org.apache.struts2.config.Namespace;
import org.apache.struts2.config.ParentPackage;
import org.apache.struts2.config.Result;
import org.apache.struts2.config.Results;

import com.zfgt.admin.product.bean.StartPageBean;
import com.zfgt.common.action.BaseAction;
import com.zfgt.common.util.QwyUtil;
import com.zfgt.orm.StartPage;
import com.zfgt.orm.SystemConfig;
import com.zfgt.orm.UsersAdmin;

/**
 * StartPage管理;
 * @author qwy
 *
 * @createTime 2015-4-27下午3:51:34
 */
@SuppressWarnings("serial")
@ParentPackage("struts-default")
@Namespace("/Product/Admin")
	//发布产品页面
	@Results({ 
		@Result(name = "loadStartPage", value = "/Product/Admin/startPageManager/startPageList.jsp"),
		@Result(name = "StartPage", value = "/Product/Admin/startPageManager/startPage.jsp"),
		@Result(name = "err", value = "/Product/Admin/err.jsp")
})
public class StartPageAction extends BaseAction {
	@Resource
	StartPageBean bean;
	private StartPage startPage;
	private File file;
	private String fileContentType;
	private String removeId;
	
	/**
	 * 加载版本
	 * @return
	 */
	public String loadStartPage(){
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
			if(isExistsQX("启动页展示", users.getId())){
				getRequest().setAttribute("err", "您没有操作该功能的权限!");
				return "err";
			}
			}
			if(QwyUtil.isNullAndEmpty(startPage)){
				startPage=new StartPage();
			}
			List<StartPage> list=bean.findStartPages(null);
			getRequest().setAttribute("list", list);
			return "loadStartPage";
		} catch (Exception e) {
			log.error("操作异常: ",e);
			json = QwyUtil.getJSONString("err", "查询记录异常");
		}
		return "loadStartPage";
	}
	
	/**
	 * 保存版本信息
	 * @return
	 */
	public String saveStartPage() {
		
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
			if(!QwyUtil.isNullAndEmpty(startPage)){
				if(!QwyUtil.isNullAndEmpty(bean.saveStartPage(startPage))){
					System.out.println("发布成功!");
					request.setAttribute("isOk", "ok");
					return "StartPage";
				}
			}
			System.out.println("发布失败!");
		} catch (Exception e) {
			e.printStackTrace();
			log.error("操作异常: ",e);
		}
		request.setAttribute("isOk", "no");
		return "StartPage";
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
			if(bean.updateStatusById(startPage.getId())){
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
	
	
	/**
	 * 上传手机图片
	 * @return
	 */
	public String uploadMobileStartPageImage(){
		return uploadStartPageImage("1");
	}
	
	/**
	 * 上传图片
	 * @param status 
	 * 
	 * @return
	 */
	public String uploadStartPageImage(String status) {
		System.out.println("上传图片");
		System.out.println(file);
		request = getRequest();
		SystemConfig systemConfig = (SystemConfig) request.getServletContext().getAttribute("systemConfig");
		String json="";
		String imageName = "";
		if (!QwyUtil.isNullAndEmpty(file)) {
			InputStream ois = null;
			OutputStream oos = null;
			try {
				String sux = fileContentType.split("/")[1];
				String fileName = UUID.randomUUID().toString() + "."+sux;
				imageName += fileName + ";";
				String folder = systemConfig.getFileUrl() + "/mobile_img/StartPage/";
				File imgFile = new File(folder);
				boolean isMk = imgFile.mkdirs();
				String newFile = folder + fileName;
				ois = new FileInputStream(file);
				oos = new FileOutputStream(newFile);
				byte[] b = new byte[1024];
				int num = -1;
				while ((num = ois.read(b)) != -1) {
					oos.write(b, 0, num);
					oos.flush();
				}  
				json = QwyUtil.getJSONString("ok", imageName);
			}catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				json = QwyUtil.getJSONString("error", "上传图片失败");

			}finally{
				if(!QwyUtil.isNullAndEmpty(ois)){
					try {
						oos.close();
						ois.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				
			}
		}
		try {
			QwyUtil.printJSON(getResponse(), json);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	
	/**
	 * 上传手机图片
	 * @return
	 */
	public String removeMobileStartPageImage(){
		return removeImage("1");
	}
	
	/**
	 * 移除上传的图片
	 * 
	 * @return
	 */
	public String removeImage(String status) {
		request = getRequest();
		SystemConfig systemConfig = (SystemConfig) request.getServletContext().getAttribute("systemConfig");
		String json="";
		try {
			String[] removeIds = removeId.split(";");
			String fileName = removeIds[0];
			String folder= systemConfig.getFileUrl() + "/mobile_img/StartPage/";
			String newFile = folder + fileName;
			File imgFile = new File(newFile);
			boolean isDel = false;
			if(imgFile.exists()){
				//删除上传的图片;
				isDel = imgFile.delete();
			}
			if(isDel){
				json = QwyUtil.getJSONString("ok","");
			}else{
				json = QwyUtil.getJSONString("error","删除图片失败: 图片不存在或被占用");
			}
		}catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			json = QwyUtil.getJSONString("error", "删除图片失败");

		}
		try {
			QwyUtil.printJSON(getResponse(), json);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	
	

	public StartPage getStartPage() {
		return startPage;
	}

	public void setStartPage(StartPage startPage) {
		this.startPage = startPage;
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public String getFileContentType() {
		return fileContentType;
	}

	public void setFileContentType(String fileContentType) {
		this.fileContentType = fileContentType;
	}

	public String getRemoveId() {
		return removeId;
	}

	public void setRemoveId(String removeId) {
		this.removeId = removeId;
	}
	
	
}
