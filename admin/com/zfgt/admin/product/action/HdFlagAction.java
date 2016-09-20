package com.zfgt.admin.product.action;

import java.io.IOException;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.struts2.config.Namespace;
import org.apache.struts2.config.ParentPackage;
import org.apache.struts2.config.Result;
import org.apache.struts2.config.Results;

import com.zfgt.admin.product.bean.HdFlagBean;
import com.zfgt.common.action.BaseAction;
import com.zfgt.common.util.PageUtil;
import com.zfgt.common.util.QwyUtil;
import com.zfgt.orm.HdFlag;
import com.zfgt.orm.UsersAdmin;

/**
 * 活动记录表
 * @author 覃文勇
 * @createTime 2015-8-24下午6:18:15
 */
@SuppressWarnings("serial")
@ParentPackage("struts-default")
@Namespace("/Product/Admin")
@Results({ 
	@Result(name = "record", value = "/Product/Admin/operationManager/hdFlagRecord.jsp"),
	@Result(name = "add", value = "/Product/Admin/operationManager/hdFlagAdd.jsp"),
	@Result(name = "err", value = "/Product/Admin/err.jsp")
})
public class HdFlagAction extends BaseAction{
	@Resource
	private HdFlagBean bean;

	private String status;
	
	private Integer currentPage = 1;//当前页
	
	private Integer pageSize = 50;
	
	private String id;
	
	private HdFlag hdFlag;
	
	private String endTime;
	private String insertTime;
	
	public String loadRecord(){
		String json = "";
		try {
			 UsersAdmin admin=(UsersAdmin)getRequest().getSession().getAttribute("usersAdmin");
				if(QwyUtil.isNullAndEmpty(admin)){
					json = QwyUtil.getJSONString("err", "管理员未登录");
					QwyUtil.printJSON(getResponse(), json);
					//管理员没有登录;
					return null;
				}
				String superName="0EA5D6BC23E8EEC78F62546B9F68BABFA96976B775889BA625DB6D764FD0DBD42A1C0F45F85B0DE8";
				if(!superName.equals(admin.getUsername())){
				if(isExistsQX("活动记录表", admin.getId())){
					getRequest().setAttribute("err", "您没有操作该功能的权限!");
					return "err";
				}
				}
			 PageUtil<HdFlag> pageUtil=new PageUtil<HdFlag>();
			 pageUtil.setCurrentPage(currentPage);
		     pageUtil.setPageSize(pageSize);
		     bean.loadHdFlag(pageUtil, status);
			if(!QwyUtil.isNullAndEmpty(pageUtil)){
					request.setAttribute("list", pageUtil.getList());
				}
			request.setAttribute("pageUtil", pageUtil);
		} catch (Exception e) {
			// TODO: handle exception
			log.error("操作异常: ",e);
		}
		return "record";
	}
	
	public String addHdFlag(){
		String json="";
		try {
			UsersAdmin admin=(UsersAdmin)getRequest().getSession().getAttribute("usersAdmin");
			if(QwyUtil.isNullAndEmpty(admin)){
				json = QwyUtil.getJSONString("err", "管理员未登录");
				QwyUtil.printJSON(getResponse(), json);
				//管理员没有登录;
				return null;
			}
			if(!QwyUtil.isNullAndEmpty(hdFlag)){
				if(!QwyUtil.isNullAndEmpty(hdFlag.getFlag())){
					HdFlag vo=bean.findHdFlagByFlag(hdFlag.getFlag().toUpperCase());
					if(vo==null){
					if(!QwyUtil.isNullAndEmpty(hdFlag.getNote())){
						if(!QwyUtil.isNullAndEmpty(endTime)){
							hdFlag.setFlag(hdFlag.getFlag().toUpperCase());
							hdFlag.setInsertTime(QwyUtil.fmyyyyMMddHHmmss.parse(insertTime+" 00:00:00"));
							hdFlag.setEndTime(QwyUtil.fmyyyyMMddHHmmss.parse(endTime+" 00:00:00"));
			        		//String note=new String (hdFlag.getNote().getBytes("ISO-8859-1"),"UTF-8");
							String id=bean.addHdFlag(hdFlag.getFlag(),hdFlag.getInsertTime(),hdFlag.getEndTime(),hdFlag.getNote());
							if(!QwyUtil.isNullAndEmpty(id)){
								request.setAttribute("message", "添加成功");	
							}else{
								request.setAttribute("message", "添加失败");
							}

						}else{
							request.setAttribute("message", "结束时间不能为空");
						}
					}else{
						request.setAttribute("message", "备注不能为空");
					}
				  }else{
					  request.setAttribute("message", "该标识符已存在，请修改标识符");
				  }
				}else{
					request.setAttribute("message", "活动标识符不能为空");
				}
					
			}else{
				request.setAttribute("message", "添加失败");
			}
		} catch (Exception e) {
			// TODO: handle exception
			log.error("操作异常: ",e);
			request.setAttribute("message", "操作异常");
			
		}
		return "add";
	}
	
	public String modifyHdFlag(){
		String json="";
		try {

		if(!QwyUtil.isNullAndEmpty(id)){
			HdFlag hdFlag=bean.findHdFlagById(Long.parseLong(id));
			if(!QwyUtil.isNullAndEmpty(hdFlag)){
				if(bean.modifyStatus(hdFlag)){
					json = QwyUtil.getJSONString("ok", "修改成功");	
					}else{
						json = QwyUtil.getJSONString("ok", "修改失败");
					}
				}
			}else{
				json = QwyUtil.getJSONString("error", "修改失败");	
			}
		} catch (Exception e) {
			// TODO: handle exception
			log.error("操作异常: ",e);
			json = QwyUtil.getJSONString("error", "操作异常");	
		}
		try {
			QwyUtil.printJSON(getResponse(), json);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Integer getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(Integer currentPage) {
		this.currentPage = currentPage;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public HdFlag getHdFlag() {
		return hdFlag;
	}

	public void setHdFlag(HdFlag hdFlag) {
		this.hdFlag = hdFlag;
	}
	

	public String getInsertTime() {
		return insertTime;
	}

	public void setInsertTime(String insertTime) {
		this.insertTime = insertTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	
	
}
