package com.zfgt.common.action;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import net.sf.json.JSONObject;

import org.apache.struts2.config.Namespace;
import org.apache.struts2.config.ParentPackage;
import org.apache.struts2.config.Result;
import org.apache.struts2.config.Results;

import com.zfgt.common.action.BaseAction;
import com.zfgt.common.util.QwyUtil;
import com.zfgt.orm.SystemConfig;
/**
 * 上传图片
 */
@ParentPackage("struts-default")
@Namespace("/Product")
@Results(
		{ 
})
public class KindEditorAction extends BaseAction{
		/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

    
	public String keupload() throws Exception { 
		return keupload("notice");
    }
	
	public String keuploadproduct() throws Exception { 
		return keupload("product");
    }
	/**
	 * 上传图片位置
	 * @param wz位置
	 * @return
	 * @throws Exception
	 */
	public String keupload(String name) throws Exception { 
    	InputStream in = null;
    	FileOutputStream fos = null;
    	JSONObject json=null;
        try {    
        	SystemConfig systemConfig = (SystemConfig) request.getServletContext().getAttribute("systemConfig");
        	//String savePath = request.getSession().getServletContext().getRealPath("/") + "/kind_upload/";
        	String savePath = systemConfig.getFileUrl() + "/web_img/"+name+"/";
	        //文件保存目录URL  
        	//String saveUrl = request.getContextPath() + "/kind_upload/";
	    	String saveUrl = systemConfig.getHttpUrl()+systemConfig.getFileName()+ "/web_img/"+name+"/";
	        if(QwyUtil.isNullAndEmpty(imgFile)){  
	        	json= returnResult(1,"请选择文件。"); 
	        	QwyUtil.printJSON(getResponse(), json.toString());
	        	return null;  
	        }  
	        //检查目录  
	        File uploadDir = new File(savePath);  
	        if(!uploadDir.isDirectory()){  
	        	uploadDir.mkdirs();
	        }  
	        //检查目录写权限  
	        if(!uploadDir.canWrite()){  
	        	json=returnResult(1,"上传目录没有写权限。");  
	        	QwyUtil.printJSON(getResponse(), json.toString());
	        	return null;  
	        }  
	        File saveDirFile = new File(savePath);  
	        if (!saveDirFile.exists()) {  
	            saveDirFile.mkdirs();  
	        } 
	        File dirFile = new File(savePath);  
	        if (!dirFile.exists()) {  
	            dirFile.mkdirs();  
	        }  
	        if(imgFile != null && !imgFile.toString().equals("")){  
	            long fileSize = imgFile.length();  
	            //检查扩展名  
	            String fileExt = imgFileFileName.substring(imgFileFileName.lastIndexOf(".") + 1).toLowerCase();  
	  
	            SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");  
	            String newFileName = df.format(new Date()) + "_" + new Random().nextInt(1000) + "." + fileExt;  
	              
	            File uploadedFile = new File(savePath, newFileName);  
	            //获取文件输出流  
	            fos = new FileOutputStream(uploadedFile);  
	            //获取内存中当前文件输入流  
	            in = new FileInputStream(imgFile);  
	            byte[] buffer = new byte[1024];  
                int num = 0;  
                while ((num = in.read(buffer)) > 0) {  
                    fos.write(buffer, 0, num);  
                }  
                JSONObject obj = new JSONObject();
				obj.put("error", 0);
				obj.put("url", saveUrl + newFileName);
                QwyUtil.printJSON(getResponse(), obj.toString());
                return null;  
	        }else{  
	        	json=returnResult(1,"上传的文件不存在!"); 
	        	QwyUtil.printJSON(getResponse(), json.toString());
	        	return null;  
	        }  
        } catch (Exception e) {  
            log.error("kindEditor上传文件出错了！");  
            json=returnResult(1,"上传的文件不存在!");  
        } finally {  
            in.close();  
            fos.close();  
        }
        QwyUtil.printJSON(getResponse(), json.toString());
        return null;  
    }
	      
		/**
		 * 上传图片返回想要的字符串;格式为:<br>
		 * {"error":"0","message":"我成功了"}
		 * //成功时
			{
			        "error" : 0,
			        "url" : "http://www.example.com/path/to/file.ext"
			}
			//失败时
			{
			        "error" : 1,
			        "message" : "错误信息"
			}
		 * 
		 * @param status
		 *            状态
		 * @param jsonString
		 *            要返回的字符串 如果没有填写""
		 * 
		 * @return
		 */
		public JSONObject returnResult(Integer error, String message) {
			JSONObject obj = new JSONObject();
			obj.put("error", error);
			if(error==1){
				obj.put("message", message);
			}else{
				obj.put("url", message);
			}
			return obj;
		}
	    /** 
	     * 成功返回0，失败返回1 
	     */  
	    public int error;  
	      
	    /** 
	     * 成功时返回上传的文件地址 
	     */  
	    public String url;  
	      
	    /** 
	     * 失败时返回的提示信息 
	     */  
	    public String message;  
	    /** 
	     * 上传的文件类型 
	     */  
	    public String dir;  
	      
	    public File imgFile;  
	    private String imgFileFileName;  
	      
	      
	    public int getError() {  
	        return error;  
	    }  
	  
	    public void setError(int error) {  
	        this.error = error;  
	    }  
	  
	    public String getUrl() {  
	        return url;  
	    }  
	  
	    public void setUrl(String url) {  
	        this.url = url;  
	    }  
	  
	    public String getMessage() {  
	        return message;  
	    }  
	  
	    public void setMessage(String message) {  
	        this.message = message;  
	    }  
	  
	    public String getDir() {  
	        return dir;  
	    }  
	  
	    public void setDir(String dir) {  
	        this.dir = dir;  
	    }  
	  
	    public File getImgFile() {  
	        return imgFile;  
	    }  
	  
	    public void setImgFile(File imgFile) {  
	        this.imgFile = imgFile;  
	    }  
	  
	    public String getImgFileFileName() {  
	        return imgFileFileName;  
	    }  
	  
	    public void setImgFileFileName(String imgFileFileName) {  
	        this.imgFileFileName = imgFileFileName;  
	    }  
	      
}