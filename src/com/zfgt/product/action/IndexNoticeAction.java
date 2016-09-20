package com.zfgt.product.action;

import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;

import net.sf.json.JSONArray;

import org.apache.log4j.Logger;
import org.apache.struts2.config.Namespace;
import org.apache.struts2.config.ParentPackage;
import org.apache.struts2.config.Result;
import org.apache.struts2.config.Results;

import com.zfgt.common.action.BaseAction;
import com.zfgt.common.util.PageUtil;
import com.zfgt.common.util.QwyUtil;
import com.zfgt.orm.Notice;
import com.zfgt.orm.Product;
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
	@Result(name = "notice", value = "/Product/notice.jsp"),
	@Result(name = "mobileNotice", value = "/wapNotice/notice.jsp"),
	@Result(name = "media", value = "/Product/media.jsp"),
	@Result(name = "aboutUs", value = "/Product/aboutUs.jsp"),
	@Result(name = "notice_detail", value = "/Product/notice_detail.jsp"),
	@Result(name = "mobile_notice_detail", value = "/wapNotice/noticeDetails.jsp"),
	@Result(name = "media_detail", value = "/Product/media_detail.jsp"),
	@Result(name = "err", value = "/Product/error.jsp")
})
public class IndexNoticeAction extends BaseAction {
	private static Logger log = Logger.getLogger(IndexNoticeAction.class);
	private Integer currentPage = 1;
	private Integer pageSize = 10;
	@Resource
	private IndexNoticeBean bean;
	
	private String noticeId;
	private String type;
	
	/**获取关于我们的公告;
	 * @return
	 */
	public String loadAboutUs(){
		loadNoticeByPageUtil(1, 6);
		return "aboutUs";
	}
	
	/**获取平台公告;
	 * @return
	 */
	public String loadNotice(){
		loadNoticeByPageUtil(currentPage, 9);
		return "notice";
	}
	
	/**获取媒体报道;
	 * @return
	 */
	public String loadMedia(){
		PageUtil<Notice> pageUtil = new PageUtil<Notice>();
		pageUtil.setCurrentPage(currentPage);
		pageUtil.setPageSize(5);
		StringBuffer url = new StringBuffer();
		url.append(getRequest().getServletContext().getContextPath());
		url.append("/Product/indexNotice!loadMedia.action");
		pageUtil.setPageUrl(url.toString());
		pageUtil = bean.loadNoticeByPageUtil(pageUtil,new String[]{"2"});
		if(!QwyUtil.isNullAndEmpty(pageUtil)){
			List<Notice> list = pageUtil.getList();
			getRequest().setAttribute("list",list);
			getRequest().setAttribute("pageUtil",pageUtil);
			getRequest().setAttribute("pageCount", pageUtil.getPageCount());
		}
		getRequest().setAttribute("anchor", "#ab_us_f2");
		return "media";
	}
	
	public void loadNoticeByPageUtil(int currentPage,int pageSize){
		PageUtil<Notice> pageUtil = new PageUtil<Notice>();
		pageUtil.setCurrentPage(currentPage);
		pageUtil.setPageSize(pageSize);
		StringBuffer url = new StringBuffer();
		url.append(getRequest().getServletContext().getContextPath());
		url.append("/Product/indexNotice!loadNotice.action");
		pageUtil.setPageUrl(url.toString());
		pageUtil = bean.loadNoticeByPageUtil(pageUtil,new String[]{"0","1"});
		getRequest().setAttribute("pageUtil", pageUtil);
		getRequest().setAttribute("anchor", "#ab_us_f2");
	}
	
	/**用于显示移动端的平台公告;
	 * @return
	 */
	public String getMobileNotice(){
		//类型;-1:隐藏的公告(不显示在界面,做seo优化);0:平台公告; 1:获奖公告;2:媒体公告; 默认为0;
		//媒体报道:2;
		PageUtil<Notice> pageMedia = new PageUtil<Notice>();
		pageMedia.setCurrentPage(1);
		pageMedia.setPageSize(50);
		bean.loadNoticeByPageUtil(pageMedia,new String[]{"2"});
		if(!QwyUtil.isNullAndEmpty(pageMedia.getList())){
			getRequest().setAttribute("mediaList",pageMedia.getList());
		}
		//平台公告;0,1;
		PageUtil<Notice> pageNotice = new PageUtil<Notice>();
		pageNotice.setCurrentPage(1);
		pageNotice.setPageSize(50);
		bean.loadNoticeByPageUtil(pageNotice,new String[]{"0","1"});
		if(!QwyUtil.isNullAndEmpty(pageNotice.getList())){
			getRequest().setAttribute("noticeList", pageNotice.getList());
		}
		getRequest().setAttribute("anchor", "#ab_us_f2");
		return "mobileNotice";
	}
	
	/**根据ajax分页获取媒体公告;
	 * @return
	 */
	public String loadMediaNoticeByAjax(){
		String json = "";
		try {
			PageUtil<Notice> pageMedia = new PageUtil<Notice>();
			pageMedia.setCurrentPage(currentPage);
			pageMedia.setPageSize(pageSize);
			bean.loadNoticeByPageUtil(pageMedia,new String[]{"2"});
			if(QwyUtil.isNullAndEmpty(pageMedia.getList())){
				//没有数据;
				json = QwyUtil.getJSONString("not", "没有更多的数据");
				QwyUtil.printJSON(getResponse(), json);
				return null;
			}
			String status = "ok";
			if(pageMedia.getList().size()<pageSize){
				//已经获取了所有数据;
				status = "ok_none";
			}
			JSONArray jsonArray = JSONArray.fromObject(pageMedia.getList());
			json = QwyUtil.getJSONString(status, jsonArray);
		} catch (Exception e) {
			log.error("操作异常: ",e);
			json = QwyUtil.getJSONString("err", "加载失败");
			log.error("WapIndexNotice.loadMediaNoticeByAjax",e);
		}
		try {
			QwyUtil.printJSON(getResponse(), json);
		} catch (IOException e) {
			log.error("操作异常: ",e);
			log.error("WapIndexNotice.loadMediaNoticeByAjax",e);
		}
		
		return null;
	}
	
	
	/**根据ajax分页获取平台公告;
	 * @return
	 */
	public String loadNoticeByAjax(){
		String json = "";
		try {
			PageUtil<Notice> pageMedia = new PageUtil<Notice>();
			pageMedia.setCurrentPage(currentPage);
			pageMedia.setPageSize(pageSize);
			bean.loadNoticeByPageUtil(pageMedia,new String[]{"0","1"});
			if(QwyUtil.isNullAndEmpty(pageMedia.getList())){
				//没有数据;
				json = QwyUtil.getJSONString("not", "没有更多的数据");
				QwyUtil.printJSON(getResponse(), json);
				return null;
			}
			String status = "ok";
			if(pageMedia.getList().size()<pageSize){
				//已经获取了所有数据;
				status = "ok_none";
			}
			JSONArray jsonArray = JSONArray.fromObject(pageMedia.getList());
			json = QwyUtil.getJSONString(status, jsonArray);
		} catch (Exception e) {
			log.error("操作异常: ",e);
			json = QwyUtil.getJSONString("err", "加载失败");
			log.error("WapIndexNotice.loadNoticeByAjax",e);
		}
		try {
			QwyUtil.printJSON(getResponse(), json);
		} catch (IOException e) {
			log.error("操作异常: ",e);
			log.error("WapIndexNotice.loadNoticeByAjax",e);
		}
		
		return null;
	}
	
	/**根据公告id,获得具体的公告内容;
	 * @return
	 */
	public String getNoticeById(){
		Notice notice = bean.getNoticeById(noticeId);
		getRequest().setAttribute("type", type);
		getRequest().setAttribute("notice", notice);
		return "notice_detail";
	}
	
	/**根据公告id,获得具体的公告内容;
	 * @return
	 */
	public String getMobileNoticeById(){
		Notice notice = bean.getNoticeById(noticeId);
		getRequest().setAttribute("type", type);
		getRequest().setAttribute("notice", notice);
		return "mobile_notice_detail";
	}
	/**根据公告id,获得具体的公告内容;
	 * @return
	 */
	public String getMediaById(){
		Notice notice = bean.getNoticeById(noticeId);
		getRequest().setAttribute("notice", notice);
		return "media_detail";
	}

	public Integer getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(Integer currentPage) {
		this.currentPage = currentPage;
	}

	public String getNoticeId() {
		return noticeId;
	}

	public void setNoticeId(String noticeId) {
		this.noticeId = noticeId;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	
}
