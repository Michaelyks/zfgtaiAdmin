package com.zfgt.admin.Mcoin.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;

import javax.annotation.Resource;

import com.zfgt.account.bean.UserRechargeBean;
import com.zfgt.admin.Mcoin.bean.ReleaseMeowBean;
import com.zfgt.common.action.BaseAction;
import com.zfgt.common.util.QwyUtil;
import com.zfgt.orm.MProduct;
import com.zfgt.orm.SystemConfig;
import com.zfgt.orm.UsersAdmin;

public class ReleaseMeowAction  extends BaseAction{
	private MProduct mProduct;
	
	public MProduct getmProduct() {
		return mProduct;
	}

	public void setmProduct(MProduct mProduct) {
		this.mProduct = mProduct;
	}

	private File file;
	private String removeId;
	@Resource
	private ReleaseMeowBean bean;
	@Resource
	private UserRechargeBean userRechargeBean;
	public String releaseMeowProduct(){
		
		String json = "";
		try {
			UsersAdmin users = (UsersAdmin)getRequest().getSession().getAttribute("usersAdmin");
			if(QwyUtil.isNullAndEmpty(users)){
				json = QwyUtil.getJSONString("err", "管理员未登录");
				QwyUtil.printJSON(getResponse(), json);
				//管理员没有登录;
				return null;
			}
			
			if(QwyUtil.isNullAndEmpty(mProduct.getTitle())){
				json=QwyUtil.getJSONString("err", "请填写商品名称!");
				QwyUtil.printJSON(getResponse(), json);
				return null;
			}
			
			if(QwyUtil.isNullAndEmpty(mProduct.getPrice())){
				json=QwyUtil.getJSONString("err","请填写商品价值");
				QwyUtil.printJSON(getResponse(), json);
				return null;
			}
			
			if(QwyUtil.isNullAndEmpty(mProduct.getStock())){
				json=QwyUtil.getJSONString("err", "请填写库存量");
				QwyUtil.printJSON(getResponse(), json);
			}
			
			if(QwyUtil.isNullAndEmpty(mProduct.getDescription())){
				json=QwyUtil.getJSONString("err", "请填商品描述");
				QwyUtil.printJSON(getResponse(), json);
				return null;
			}
			if(mProduct.getType().equals("5")&&QwyUtil.isNullAndEmpty(mProduct.getMoney())){
				json=QwyUtil.getJSONString("err", "请填写卡卷金额");
				QwyUtil.printJSON(getResponse(), json);
				return null;
				
			}
			if(mProduct.getType().equals("4")&&QwyUtil.isNullAndEmpty(mProduct.getMarketPrice())){
				json=QwyUtil.getJSONString("err", "请填写市场价格");
				QwyUtil.printJSON(getResponse(), json);
				return null;
				
			}
			MProduct mProduct1 = userRechargeBean.addMProduct(users.getId(), mProduct);
			if(!QwyUtil.isNullAndEmpty(mProduct1)){
				json = QwyUtil.getJSONString("ok","发布成功");
			}else{
				json = QwyUtil.getJSONString("err","发布失败");
			}
			QwyUtil.printJSON(getResponse(), json);
		} catch (Exception e) {
			log.error("操作异常: ",e);
			json = QwyUtil.getJSONString("err", "充值异常");
		}
		return null;
		
	}
	
	public String findTitle(){
		String json = "";
		try {
			UsersAdmin users = (UsersAdmin)getRequest().getSession().getAttribute("usersAdmin");
			if(QwyUtil.isNullAndEmpty(users)){
				json = QwyUtil.getJSONString("err", "管理员未登录");
				QwyUtil.printJSON(getResponse(), json);
				//管理员没有登录;
				return null;
			}
			if(QwyUtil.isNullAndEmpty(mProduct.getTitle())){
				json = QwyUtil.getJSONString("err", "标题不能为空");
				QwyUtil.printJSON(getResponse(), json);
				return null;
			}
			
			if(!QwyUtil.isNullAndEmpty(bean.findTitle(mProduct.getTitle()))){
				json = QwyUtil.getJSONString("err", "标题不能重复");
				QwyUtil.printJSON(getResponse(), json);
				return null;
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			log.error(e.getMessage(),e);
			json = QwyUtil.getJSONString("err", "服务器异常");
		}
		return null;
	}
	
	/**
	 * 上传网站图片
	 * @return
	 */
	public String removeWebMeowProductImage(){
		return removeImage("0");
	}
	
	/**
	 * 上传手机图片
	 * @return
	 */
	public String removeMobileMeowProductImage(){
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
			String folder = null;
			if(!QwyUtil.isNullAndEmpty(status)&&status.equals("0")){
				folder = systemConfig.getFileUrl() + "/web_img/mShop/";
			}else if(!QwyUtil.isNullAndEmpty(status)&&status.equals("1")){
				folder = systemConfig.getFileUrl() + "/mobile_img/mShop/";
			}
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
	
	
	/**
	 * 上传网站图片
	 * @return
	 */
	public String uploadWebMeowProductImage(){
		return uploadMeowProductImage("0");
	}
	
	/**
	 * 上传手机图片
	 * @return
	 */
	public String uploadMobileMeowProductImage(){
		return uploadMeowProductImage("1");
	}
	
	/**
	 * 上传图片
	 * @param status 
	 * 
	 * @return
	 */
	public String uploadMeowProductImage(String status) {
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
				String folder =null;
				if(!QwyUtil.isNullAndEmpty(status)&&status.equals("0")){
					folder = systemConfig.getFileUrl() + "/web_img/mShop/";
				}else if(!QwyUtil.isNullAndEmpty(status)&&status.equals("1")){
					folder = systemConfig.getFileUrl() + "/mobile_img/mShop/";
				}
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



	public String getRemoveId() {
		return removeId;
	}



	public void setRemoveId(String removeId) {
		this.removeId = removeId;
	}



	public File getFile() {
		return file;
	}

	private String fileContentType;

	public String getFileContentType() {
		return fileContentType;
	}



	public void setFileContentType(String fileContentType) {
		this.fileContentType = fileContentType;
	}



	public void setFile(File file) {
		this.file = file;
	}
}
