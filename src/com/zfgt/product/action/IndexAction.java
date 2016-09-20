package com.zfgt.product.action;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;

import net.sf.json.JSONArray;

import org.apache.log4j.Logger;
import org.apache.struts2.config.Namespace;
import org.apache.struts2.config.ParentPackage;
import org.apache.struts2.config.Result;
import org.apache.struts2.config.Results;

import com.zfgt.account.bean.BuyCarBean;
import com.zfgt.account.bean.UserInfoBean;
import com.zfgt.admin.product.bean.VirtualInsRecordBean;
import com.zfgt.common.action.BaseAction;
import com.zfgt.common.bean.PlatformBean;
import com.zfgt.common.util.PageUtil;
import com.zfgt.common.util.PropertiesUtil;
import com.zfgt.common.util.QwyUtil;
import com.zfgt.login.bean.RegisterUserBean;
import com.zfgt.orm.Investors;
import com.zfgt.orm.Notice;
import com.zfgt.orm.Product;
import com.zfgt.orm.SystemConfig;
import com.zfgt.orm.Users;
import com.zfgt.orm.UsersInfo;
import com.zfgt.orm.VirtualInsRecord;
import com.zfgt.product.bean.IndexBean;
import com.zfgt.product.bean.IndexNoticeBean;
/**首页Action层;<br>
 * 处理页面发过来的请求
 * @author qwy
 *
 * @createTime 2015-4-15下午10:35:44
 */
@SuppressWarnings("serial")
@ParentPackage("struts-default")
@Namespace("/Product")
@Results({ @Result(name = "index", value = "/Product/index.jsp"),
	@Result(name = "details", value = "/Product/productDetails.jsp"),
	@Result(name = "detailsFreshman", value = "/Product/productDetailsFreshman.jsp"),
	@Result(name = "old_detatils", value = "/Product/productDetails_old.jsp"),
	@Result(name = "old_detailsFreshman", value = "/Product/productDetailsFreshman_old.jsp"),
	@Result(name = "checkEmail", value = "/Product/tishi_success.jsp"),
	@Result(name = "err", value = "/Product/tishi_error.jsp")

})
public class IndexAction extends BaseAction {
	
	private static Logger log = Logger.getLogger(IndexAction.class);
	@Resource
	private PlatformBean platformBean;
	
	@Resource
	private IndexBean bean;
	@Resource
	private UserInfoBean uibean;
	@Resource
	private RegisterUserBean rubean;
	@Resource
	private IndexNoticeBean indexNoticeBean;
	private Integer currentPage = 1;
	private Integer pageSize = 6;
	private String productId;
	private String email;
	private Long zlq;
	@Resource
	private VirtualInsRecordBean virtualInsRecordBean;
	@Resource
	private BuyCarBean buyCarBean;
	
	/**加载产品列表;
	 * @return List&lt;{@link Product}&gt;
	 */
	public String loadProduct(){
		try {
			PageUtil<Product> page = getProductPageUtil(1, 200);
			//********************优选理财********************//
			PageUtil<Product> pageUtil = page;
			//0:优选理财,理财期限>31天;
			pageUtil = bean.loadIndexProduct(pageUtil, new String[]{"0","1","3"}, "0");			
			if(!QwyUtil.isNullAndEmpty(pageUtil)){
				List<Product> productList1 = pageUtil.getList();
				List<Product> productList11 = chooseProduc(productList1, "1", "1");
				List<Product> productList13 = chooseProduc(productList1, "3", "1");
				getRequest().setAttribute("productList1",productList1);
				getRequest().setAttribute("productList11",productList11);
				getRequest().setAttribute("productList13", productList13);
			}
			//********************优选理财--end********************//
			
			
			//********************特色理财********************//
			PageUtil<Product> pageUtil2 = page;
			//1:特色理财,理财期限<=31天;
			pageUtil2 = bean.loadIndexProduct(pageUtil2, new String[]{"0","1","3"}, "1");			
			if(!QwyUtil.isNullAndEmpty(pageUtil2)){
				List<Product> productList2 = pageUtil2.getList();
				List<Product> productList21 = chooseProduc(productList2, "1", "2");
				List<Product> productList23 = chooseProduc(productList2, "3", "2");
				getRequest().setAttribute("productList2",productList2);
				getRequest().setAttribute("productList21",productList21);
				getRequest().setAttribute("productList23", productList23);
			}
			
			//********************特色理财--end********************//
			//他们正在理财
			PageUtil<Investors> pageInv = new PageUtil<Investors>();
			pageInv.setCurrentPage(1);
			pageInv.setPageSize(20);
			pageInv = bean.loadInvestorsByProId(pageInv, null);
			if(!QwyUtil.isNullAndEmpty(pageInv)){
				getRequest().setAttribute("investorsList",pageInv.getList());
			}
			//新手专区
			Product freshmanProduct = bean.getFreshmanProduct();
			getRequest().setAttribute("freshmanProduct", freshmanProduct);
			//数据统计
			getRequest().setAttribute("platform", platformBean.getPlatform());
			System.out.println("首页请求IP: "+QwyUtil.getIpAddr(getRequest()));
			//类型;-1:隐藏的公告(不显示在界面,做seo优化);0:平台公告; 1:获奖公告;2:媒体公告; 默认为0;
			//媒体报道:2;
			PageUtil<Notice> pageMedia = new PageUtil<Notice>();
			pageMedia.setCurrentPage(1);
			pageMedia.setPageSize(5);
			indexNoticeBean.loadNoticeByPageUtil(pageMedia,new String[]{"2"});
			if(!QwyUtil.isNullAndEmpty(pageMedia.getList())){
				getRequest().setAttribute("mediaList",getMediaList(pageMedia.getList()));
				getRequest().setAttribute("media", pageMedia.getList().get(0));
			}
			//平台公告;0,1;
			PageUtil<Notice> pageNotice = new PageUtil<Notice>();
			pageNotice.setCurrentPage(1);
			pageNotice.setPageSize(6);
			indexNoticeBean.loadNoticeByPageUtil(pageNotice,new String[]{"0","1"});
			if(!QwyUtil.isNullAndEmpty(pageNotice.getList())){
				getRequest().setAttribute("noticeList", pageNotice.getList());
			}
			
			//隐藏内容;-1;
			PageUtil<Notice> pageHide = new PageUtil<Notice>();
			pageHide.setCurrentPage(1);
			pageHide.setPageSize(1000);
			indexNoticeBean.loadNoticeByPageUtil(pageHide,new String[]{"-1"});
			getRequest().setAttribute("hideList", pageHide.getList());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.error("操作异常: ",e);
			log.error("IndexAction.loadProduct",e);
		}
		return "index";
	}
	
	
	/**根据分页对象获取产品列表
	 * @param currentPage 当前页数
	 * @param pageSize 显示条数
	 * @param status 查询状态
	 * @return
	 */
	public PageUtil<Product> getProductPageUtil(int currentPage,int pageSize){
		PageUtil<Product> pageUtil = new PageUtil<Product>();
		pageUtil.setCurrentPage(currentPage);
		pageUtil.setPageSize(pageSize);
		return pageUtil;
	}
	
	/**筛选理财产品;
	 * @param status 产品状态 -2：审核不通过 -1:未审核 0:营销中  1:已售罄  2:结算中   3:已还款 
	 * @param category 分类; 1:优选理财,理财期限大于31天; 2:特色理财,理财期限小于等于31天;为null时,不分类;
	 * @return
	 */
	public List<Product> chooseProduc(List<Product> oldList,String status,String category){
		if(!QwyUtil.isNullAndEmpty(oldList)){
			List<Product> list = new ArrayList<Product>();
			for (Product product : oldList) {
				//筛选分类;
				if(!QwyUtil.isNullAndEmpty(category)){
					if("1".equals(category)){
						//1:优选理财,理财期限>31天;
						/*if(!(product.getLcqx().longValue()>31))
							continue;*/
					}else if("2".equals(category)){
						//2:特色理财,理财期限<=31天;
						/*if(!(product.getLcqx().longValue()<=31))
							continue;*/
					}
				}
				//筛选状态;
				if(!QwyUtil.isNullAndEmpty(status)){
					if(!status.equals(product.getProductStatus()))
						continue;
				}
				list.add(product);
				if(list.size()==6)
					break;
				
			}
			return list;
		}
		return null;
	}
	
	/**根据ajax分页;
	 * @return
	 */
	public String loadProductByAjax(){
		String json = "";
		try {
			PageUtil<Product> pageUtil = getProductPageUtil(currentPage, pageSize);
			pageUtil = bean.getProductByPageUtil(pageUtil, new String[]{"0","1","2","3"});
			List<Product> productList = pageUtil.getList();
			if(QwyUtil.isNullAndEmpty(productList)){
				//没有数据;
				//log.info("没有更多的数据");
				json = QwyUtil.getJSONString("not", "没有更多的数据");
				QwyUtil.printJSON(getResponse(), json);
				return null;
			}
			String status = "ok";
			if(productList.size()<pageSize){
				//已经获取了所有数据;
				status = "ok_none";
			}
			//log.info("IndexAction.loadProductByAjax____加载更多成功");
			JSONArray jsonArray = JSONArray.fromObject(productList);
			
			json = QwyUtil.getJSONString(status, jsonArray);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.error("操作异常: ",e);
			json = QwyUtil.getJSONString("err", "加载失败");
			log.error("IndexAction.loadProductByAjax",e);
		}
		try {
			QwyUtil.printJSON(getResponse(), json);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			log.error("操作异常: ",e);
			log.error("IndexAction.loadProductByAjax_printJSON",e);
		}
		
		return null;
	}
	
	public void getProductImg(Product product){
		//信息披露;
		String infoImg = product.getInfoImg();
		if(!QwyUtil.isNullAndEmpty(infoImg)){
			List infoList = new ArrayList();
			SystemConfig config =(SystemConfig) getRequest().getServletContext().getAttribute("systemConfig");
			String[] infoImgs = infoImg.split(";");
			for (String str : infoImgs) {
				String url = config.getHttpUrl()+config.getFileName()+"/web_img/info/"+str;
				infoList.add(url);
			}
			getRequest().setAttribute("infoList",infoList);
		}
		//法律意见书
		String lawImg = product.getLawImg();
		if(!QwyUtil.isNullAndEmpty(lawImg)){
			List lawList = new ArrayList();
			SystemConfig config =(SystemConfig) getRequest().getServletContext().getAttribute("systemConfig");
			String[] lawImgs = lawImg.split(";");
			for (String str : lawImgs) {
				String url = config.getHttpUrl()+config.getFileName()+"/web_img/law/"+str;
				lawList.add(url);
			}
			getRequest().setAttribute("lawList",lawList);
		}
	}
	
	/**显示产品的详细信息;<br>
	 * 根据产品id查找产品;
	 * @return
	 */
	public String showProductDetails(){
		
		try {
			if(QwyUtil.isNullAndEmpty(productId)){
				//没有产品id;
				return loadProduct();
			}
			Product product = bean.getProductById(productId);
			if(QwyUtil.isNullAndEmpty(product) || !"0".equals(product.getProductType())){
				//产品id无效
				return loadProduct();
			}
			getProductImg(product);
			
			/*Integer usercount=virtualInsRecordBean.findVirtualIns(productId);
			Long insUserCount=product.getUserCount();
			List<Investors> list=new ArrayList<Investors>();
			List<VirtualInsRecord> virtualInsRecords=null;
			PageUtil pageUtil=null;
			if(usercount>=20){
				pageUtil = new PageUtil<VirtualInsRecord>();
				pageUtil.setCurrentPage(currentPage);
				pageUtil.setPageSize(20);
				virtualInsRecordBean.findInsRecords(pageUtil, productId);
			}else{
				if(currentPage==1){
					virtualInsRecords=virtualInsRecordBean.findInsRecords(productId);
				}
			}
			//如果分页大于
			if(!QwyUtil.isNullAndEmpty(pageUtil)&&pageUtil.getList().size()>=20){
				list.addAll(virtualInsRecordBean.ToInvestors(pageUtil.getList()));
			}else{
				Integer size=0;
				Integer mo=0;
				Integer page=QwyUtil.calcNumber(usercount, 20, "/").intValue();
				if(QwyUtil.isNullAndEmpty(virtualInsRecords)&&!QwyUtil.isNullAndEmpty(pageUtil)){
					virtualInsRecords=pageUtil.getList();
				}
				if(!QwyUtil.isNullAndEmpty(virtualInsRecords)&&currentPage<=page){
					size=virtualInsRecords.size();
				}
				if(!QwyUtil.isNullAndEmpty(virtualInsRecords)){
					list.addAll(virtualInsRecordBean.ToInvestors(virtualInsRecords));
				}
				//剩余的条数
				mo=usercount%20;
				Integer start=(currentPage-page-1)*(20-size);
				if(start>0){
					start-=mo;
				}
				
				//结束条数
				Integer end=((currentPage-page)*(20-size))-mo;
				list.addAll(bean.loadInvestorsByProId(productId, start, end));
			}
			Integer pageCount;
			if(insUserCount%20!=0){
				pageCount=QwyUtil.calcNumber(insUserCount, 20, "/").intValue()+1;
			}else{
				pageCount=QwyUtil.calcNumber(insUserCount, 20, "/").intValue();
			}
			if(!QwyUtil.isNullAndEmpty(list)){
				//replaceInvestors(list);
				Collections.sort(list,new MyComparator());
				getRequest().setAttribute("currentPage", currentPage);
				getRequest().setAttribute("pageCount",pageCount );
				getRequest().setAttribute("investorsList", list);
				getRequest().setAttribute("anchor", "#details");
			}*/
			PageUtil pageUtil=null;
			pageUtil = new PageUtil<VirtualInsRecord>();
			pageUtil.setCurrentPage(currentPage);
			pageUtil.setPageSize(20);
			virtualInsRecordBean.findInvestorsRecords(pageUtil, productId);
			getRequest().setAttribute("currentPage", currentPage);
			getRequest().setAttribute("pageCount",pageUtil.getPageCount() );
			getRequest().setAttribute("investorsList", pageUtil.getList());
			getRequest().setAttribute("anchor", "#details");
			
			
			getRequest().setAttribute("product", product);
			//SystemConfig config =(SystemConfig) getRequest().getServletContext().getAttribute("systemConfig");
			String url="/Product/index!showProductDetails.action?myCss=2&productId="+productId;
			getRequest().setAttribute("url",url );
			if (product.getInsertTime().before(QwyUtil.fmyyyyMMddHHmmss.parse(PropertiesUtil.getProperties("time") + ""))) {
				url="/Product/index!showProductDetails.action?productId="+productId;
				getRequest().setAttribute("url",url );
				return "old_detatils";
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.error("操作异常: ",e);
			log.error("IndexAction.showProductDetails",e);
		}
		return "details";
	}
//	public String showProductDetails(){
//		
//		try {
//			if(QwyUtil.isNullAndEmpty(productId)){
//				//没有产品id;
//				return loadProduct();
//			}
//			Product product = bean.getProductById(productId);
//			if(QwyUtil.isNullAndEmpty(product) || !"0".equals(product.getProductType())){
//				//产品id无效
//				return loadProduct();
//			}
//			getProductImg(product);
//			
//			PageUtil<Investors> pageUtil = new PageUtil<Investors>(20,currentPage);
//			pageUtil = bean.loadInvestorsByProId(pageUtil, productId);
//			pageUtil.setPageUrl(getRequest().getServletContext().getContextPath()+"/Product/index!showProductDetails.action?productId="+productId);
//			if(!QwyUtil.isNullAndEmpty(pageUtil)){
//				List<Investors> list = pageUtil.getList();
//				if(!QwyUtil.isNullAndEmpty(list)){
//					//replaceInvestors(list);
//					getRequest().setAttribute("investorsList", list);
//					getRequest().setAttribute("pageUtil", pageUtil);
//					getRequest().setAttribute("anchor", "#details");
//				}
//			}
//			getRequest().setAttribute("product", product);
//			//SystemConfig config =(SystemConfig) getRequest().getServletContext().getAttribute("systemConfig");
//			String url="/Product/index!showProductDetails.action?productId="+productId;
//			getRequest().setAttribute("url",url );
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			log.error("操作异常: ",e);
//			log.error("IndexAction.showProductDetails",e);
//		}
//		return "details";
//	}
	
	/**显示产品的详细信息;<br>
	 * 根据产品id查找产品;
	 * @return
	 */
	public String showProductDetailsFreshman(){
		
		try {
			if(QwyUtil.isNullAndEmpty(productId)){
				//没有产品id;
				return loadProduct();
			}
			Product product = bean.getProductById(productId);
			if(QwyUtil.isNullAndEmpty(product) || !"1".equals(product.getProductType())){
				//产品id无效
				return loadProduct();
			}
			getProductImg(product);
			Integer usercount=virtualInsRecordBean.findVirtualIns(productId);
			Long insUserCount=product.getUserCount();
			List<Investors> list=new ArrayList<Investors>();
			List<VirtualInsRecord> virtualInsRecords=null;
			PageUtil pageUtil=null;
			if(usercount>=20){
				pageUtil = new PageUtil<VirtualInsRecord>();
				pageUtil.setCurrentPage(currentPage);
				pageUtil.setPageSize(20);
				virtualInsRecordBean.findInsRecords(pageUtil, productId);
			}else{
				if(currentPage==1){
					virtualInsRecords=virtualInsRecordBean.findInsRecords(productId);
				}
			}
			//如果分页大于
			if(!QwyUtil.isNullAndEmpty(pageUtil)&&pageUtil.getList().size()>=20){
				list.addAll(virtualInsRecordBean.ToInvestors(pageUtil.getList()));
			}else{
				Integer size=0;
				Integer mo=0;
				Integer page=QwyUtil.calcNumber(usercount, 20, "/").intValue();
				if(QwyUtil.isNullAndEmpty(virtualInsRecords)&&!QwyUtil.isNullAndEmpty(pageUtil)){
					virtualInsRecords=pageUtil.getList();
				}
				if(!QwyUtil.isNullAndEmpty(virtualInsRecords)&&currentPage<=page){
					size=virtualInsRecords.size();
				}
				if(!QwyUtil.isNullAndEmpty(virtualInsRecords)){
					list.addAll(virtualInsRecordBean.ToInvestors(virtualInsRecords));
				}
				//剩余的条数
				mo=usercount%20;	
				Integer start=(currentPage-page-1)*(20-size);
				if(start>0){
					start-=mo;
				}
				if(start<=0){
					start=0;
				}
				
				//结束条数
				Integer end=((currentPage-page)*(20-size))-mo;
				if(end<=0){
					end=20-size;
				}
				if(end>20){
					end=20;
				}
				list.addAll(bean.loadInvestorsByProId(productId, start, end));
			}
//			PageUtil<Investors> pageUtil = new PageUtil<Investors>(20,currentPage);
//			pageUtil = bean.loadInvestorsByProId(pageUtil, productId);
//			pageUtil.setPageUrl(getRequest().getServletContext().getContextPath()+"/Product/index!showProductDetailsFreshman.action?productId="+productId);
//			if(!QwyUtil.isNullAndEmpty(pageUtil)){
//				List<Investors> list = pageUtil.getList();
//				if(!QwyUtil.isNullAndEmpty(list)){
//					//replaceInvestors(list);
//					getRequest().setAttribute("investorsList", list);
//					getRequest().setAttribute("pageUtil", pageUtil);
//					getRequest().setAttribute("anchor", "#details");
//				}
//			}
			Integer pageCount;
			if(insUserCount%20!=0){
				pageCount=QwyUtil.calcNumber(insUserCount, 20, "/").intValue()+1;
			}else{
				pageCount=QwyUtil.calcNumber(insUserCount, 20, "/").intValue();
			}
			if(!QwyUtil.isNullAndEmpty(list)){
				//replaceInvestors(list);
				Collections.sort(list,new MyComparator());
				getRequest().setAttribute("currentPage", currentPage);
				getRequest().setAttribute("pageCount",pageCount );
				getRequest().setAttribute("investorsList", list);
				getRequest().setAttribute("anchor", "#details");
			}
			getRequest().setAttribute("product", product);
			String url="/Product/index!showProductDetailsFreshman.action?myCss=2&productId="+productId;
			getRequest().setAttribute("url",url );
			if (product.getInsertTime().before(QwyUtil.fmyyyyMMddHHmmss.parse(PropertiesUtil.getProperties("time") + ""))) {
				url="/Product/index!showProductDetails.action?productId="+productId;
				getRequest().setAttribute("url",url );
				return "old_detailsFreshman";
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.error("操作异常: ",e);
			log.error("IndexAction.showProductDetails",e);
		}
		return "detailsFreshman";
	}
	/**
	 * 点击邮件进行激活
	 * @return
	 */
	public String checkEmail(){
		System.out.println("进入邮箱验证");
		String json="";
		try {
			Users user=uibean.getUserById(zlq);
			System.out.println(user==null);
			if(user!=null){
				UsersInfo userInfo=user.getUsersInfo();
				userInfo.setIsVerifyEmail("1");
				userInfo.setEmail(email);
				uibean.saveOrUpdateUserInfo(userInfo);
				if(QwyUtil.isNullAndEmpty(user.getUsername())){
					user.setUsername(email);
					System.out.println("手机注册 绑定邮箱");
				}
				json = QwyUtil.getJSONString("success", "邮箱验证通过");
			}
		} catch (Exception e) {
			log.error("操作异常: ",e);
			log.error("UserInfoAction.checkEmail",e);
			json = QwyUtil.getJSONString("err", "邮箱验证未通过");
			return "err";
		}finally{
			try {
				QwyUtil.printJSON(getResponse(), json);
				System.out.println("结束");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				log.error("操作异常: ",e);
			}
		}
		return "checkEmail";
		
	}

	
	/**把用户名中间替换成星号
	 * @param list
	 */
	public void replaceInvestors(List<Investors> list){
		if(!QwyUtil.isNullAndEmpty(list)){
			for (Investors investors : list) {
				/*Users u = investors.getUsers();
				String username = QwyUtil.isNullAndEmpty(u.getUsername())?u.getPhone():u.getUsername();
				username = QwyUtil.replaceStringToX(DESEncrypt.jieMiUsername(username));
				u.setUsername(username);
				investors.setUsers(u);*/
			}
		}
	}
	
	/**获取媒体报道;除去第一个;
	 * @param list
	 * @return
	 */
	public List<Notice> getMediaList(List<Notice> list){
		List<Notice> newList = new ArrayList<Notice>();
		if(!QwyUtil.isNullAndEmpty(list)){
			for(int i = 0; i<list.size();i++) {
				if(i==0)
					continue;
				newList.add(list.get(i));
			}
		}
		return newList;
	}
	

	public String getProductId() {
		return productId;
	}


	public void setProductId(String productId) {
		this.productId = productId;
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
	




	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public Long getZlq() {
		return zlq;
	}


	public void setZlq(Long zlq) {
		this.zlq = zlq;
	}


	/**退出登录
	 * @return
	 */
	public String exitLogin(){
		getRequest().getSession().setAttribute("usersLogin", null);
		Cookie[] cookies=request.getCookies();				
		for(Cookie cookie : cookies){ 
			if("url".equals(cookie.getName())){		    
				cookie.setMaxAge(0); //删除该Cookie 
				cookie.setPath("/"); 
				response.addCookie(cookie); 
			}
		}
		
		return loadProduct();
	}
	class MyComparator implements Comparator<Investors>{

		/* (non-Javadoc)
		 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
		 */
		@Override
		public int compare(Investors o1, Investors o2) {
			// TODO Auto-generated method stub
			if(o1.getPayTime().before(o2.getPayTime())){
				return 1;
			}else{
				return -1;
			}
		}
	}
	public static void main(String[] args) {
		try {
			System.out.println(QwyUtil.calcNumber(39, 20, "/").intValue());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
