package com.zfgt.admin.product.bean;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.zfgt.admin.product.dao.ReleaseProductDAO;
import com.zfgt.common.ApplicationContexts;
import com.zfgt.common.util.PageUtil;
import com.zfgt.common.util.QwyUtil;
import com.zfgt.orm.Contract;
import com.zfgt.orm.Fxzb;
import com.zfgt.orm.Investors;
import com.zfgt.orm.Product;
import com.zfgt.orm.ProductTable;
import com.zfgt.orm.Zjsumx;
import com.zfgt.product.bean.IndexBean;

/**发布产品Bean层
 * @author qwy
 *
 * @createTime 2015-04-16 13:49:00
 */
@Service
public class ReleaseFundProductBean {
	private static Logger log = Logger.getLogger(ReleaseFundProductBean.class);
	@Resource
	ReleaseProductDAO dao;
	@Resource
	IndexBean bean;
	@Resource
	private VirtualInsRecordBean virtualInsRecordBean;
	
	/**添加新基金产品
	 * @param ProductFund productFund 实体;
	 * @return 返回新生成的id;
	 */
	public String saveProductFund(Product product){
		try {
			product.setAllCopies(QwyUtil.calcNumber(product.getFinancingAmount(), 100, "/").longValue());
			product.setHasCopies(0L);
			product.setJiangLiEarnings((double) 0);
			product.setLeftCopies(product.getAllCopies());
			product.setProductType("2");//2:基金产品
			product.setInvestType("4");
			product.setProductStatus("0");
			product.setUserCount(0L);
			product.setInsertTime(new Date());
			product.setProgress(0d);
		} catch (Exception e) {
			log.error("操作异常: ",e);
		}
		return dao.save(product);
	}
	
	/**
	 * 
	 */
	/**
	 * 得到JASPER
	 * @param product 产品
	 * @param finishTime 项目到期时间
	 * @param insertTime 项目插入时间 
	 * @param username 用户名
	 * @param productStatus 产品状态
	 * @param sourceFileName 路径
	 * @return
	 * @throws Exception
	 */
	public List<JasperPrint> getProductJasperPrintList(Product product, String finishTime, String insertTime, String username, String productStatus,String sourceFileName) throws Exception{
		List<JasperPrint> list=new ArrayList<JasperPrint>();
		List<Product> products=findProductPageUtil(product, finishTime, insertTime, username, productStatus);
		List<ProductTable> productTables=productTables(products);
		JRBeanCollectionDataSource ds=new JRBeanCollectionDataSource(productTables);	
		//JasperPrint 	 js=JasperFillManager.fillReport(context.getRealPath(path) +File.separator+getJxmlStr(), map, ds);"D:\\table"+File.separator+"releaseProduct.jasper"
		JasperPrint js=JasperFillManager.fillReport(sourceFileName, null, ds);
		list.add(js);
		return list;
	}
	
	/**
	 * 得到利息总表JASPER
	 * @param product 产品
	 * @param finishTime 项目到期时间
	 * @param sourceFileName 路径
	 */
	public List<JasperPrint> getFXJasperPrintList(Product product, String finishTime,String sourceFileName) throws Exception{
		List<JasperPrint> list=new ArrayList<JasperPrint>();
		List<Product> products=findProductPageUtil(product, finishTime, null, null, null,"finishTime");
		List<Fxzb> fxzbTables=FxzbTables(products);
		JRBeanCollectionDataSource ds=new JRBeanCollectionDataSource(fxzbTables);	
		//JasperPrint 	 js=JasperFillManager.fillReport(context.getRealPath(path) +File.separator+getJxmlStr(), map, ds);"D:\\table"+File.separator+"releaseProduct.jasper"
		JasperPrint js=JasperFillManager.fillReport(sourceFileName, null, ds);
		list.add(js);
		return list;
	}
	
	/**
	 * 转换为付息表格bean
	 */
	public List<Fxzb> FxzbTables(List<Product> products){
		Map<String, String> map=findInvestorsByProductId();
		List<Fxzb>  fxzbTables=new ArrayList<Fxzb>();
		try {
			if (!QwyUtil.isNullAndEmpty(products)) {
				for (int i = 0; i < products.size(); i++) {
					Product product = products.get(i);
					Fxzb fxzb = new Fxzb();
					fxzb.setAllCopies(product.getAllCopies() + "");
					fxzb.setAnnualEarnings((product.getAnnualEarnings())+ "%");
					fxzb.setCplx(product.getCplx());
					fxzb.setFinishTime(QwyUtil.fmyyyyMMdd.format(product.getFinishTime()));
					fxzb.setQtje(QwyUtil.calcNumber(product.getQtje(),
							0.01, "*",2) + "");
					fxzb.setTitle(product.getTitle());
					fxzb.setSflx(QwyUtil.calcNumber(map.get(product.getId()),100, "/",2)+"");
					fxzb.setFxfs(product.getFxfs());
					fxzb.setJxfs(product.getJxfs());
					fxzb.setInsertTime(QwyUtil.fmyyyyMMddHHmmss.format(product.getInsertTime()));
					fxzbTables.add(fxzb);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return  fxzbTables;	
	}
	
	
	/**
	 * 转换为表格bean
	 */
	public List<ProductTable> productTables(List<Product> products){
		Map<String, String> map=findInvestorsByProductId();
		List<ProductTable>  productTables=new ArrayList<ProductTable>();
		Map<String,Double> mapProductToVirtual=virtualInsRecordBean.MapProductToVirtual();
		try {
			if (!QwyUtil.isNullAndEmpty(products)) {
				for (int i = 0; i < products.size(); i++) {
					Product product = products.get(i);
					ProductTable productTable = new ProductTable();
					productTable.setIndex(i + "");
					productTable.setAllCopies(product.getAllCopies() + "");
					productTable
							.setAnnualEarnings((product.getAnnualEarnings())
									+ "%");
					productTable.setCplx(product.getCplx());
					productTable.setCpzt(product.getCpzt());
					productTable.setFinishTime(QwyUtil.fmyyyyMMdd
							.format(product.getFinishTime()));
					productTable.setInsertTime(QwyUtil.fmyyyyMMddHHmmss
							.format(product.getInsertTime()));
					productTable.setLcqx(product.getLcqx() + "");
					productTable.setLeftCopies(product.getLeftCopies() + "");
					productTable.setQtje(QwyUtil.calcNumber(product.getQtje(),
							0.01, "*",2) + "");
					productTable.setTitle(product.getTitle());
					productTable.setTzqx(product.getTzqx() + "");
					productTable.setWcjd((product.getWcjd() * 100) + "%");
					productTable.setHasCopies(product.getHasCopies() + "");
					productTable.setSflx(QwyUtil.calcNumber(map.get(product.getId()),100, "/",2)+"");
					productTable.setYflx(product.getYflx().toString());
					productTable.setSjHashCopies((product.getHasCopies()-QwyUtil.calcNumber(mapProductToVirtual.get(product.getId()),100, "/",0).longValue())+"");
					System.out.println(product.getId()+"虚拟募资金额"+QwyUtil.calcNumber(mapProductToVirtual.get(product.getId()),100, "/",2));
					productTable.setXnCopies(QwyUtil.calcNumber(mapProductToVirtual.get(product.getId()),100, "/",2)+"");
					productTables.add(productTable);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return  productTables;	
	}
/**
 * 转换单个实体
 */
	/*public ProductTable productTable(Product product){
		productTable.
		return o;
		
	}*/
	
	
	/**获取已发布的产品总数;(包含新手产品)
	 * @param type   0为普通项目,1为:新手专享;
	 * @return
	 */
	public int getProductCount(String[] type){
		String myType = QwyUtil.packString(type);
		StringBuffer hql = new StringBuffer();
		hql.append("SELECT COUNT(*) FROM Product pro ");
		hql.append("WHERE pro.productStatus IN ('0','1','2','3') ");
		if(!QwyUtil.isNullAndEmpty(myType)){
			hql.append("AND pro.productType IN ("+myType+")");
		}
		Object obj = dao.findJoinActive(hql.toString(), null);
		return obj==null?0:Integer.parseInt(obj.toString())>0?Integer.parseInt(obj.toString()):0;
	}
	
	/**获取已发布的产品的金额;(包含新手产品)
	 * @param type   0为普通项目,1为:新手专享;
	 * @return
	 */
	public Double getProductAllMoney(String[] type){
		String myType = QwyUtil.packString(type);
		StringBuffer hql = new StringBuffer();
		hql.append("SELECT SUM(pro.financingAmount) FROM Product pro ");
		hql.append("WHERE pro.productStatus IN ('0','1','2','3') ");
		if(!QwyUtil.isNullAndEmpty(myType)){
			hql.append("AND pro.productType IN ("+myType+")");
		}
		Object obj = dao.findJoinActive(hql.toString(), null);
		return obj==null?0:Double.parseDouble(obj.toString())>0?Double.parseDouble(obj.toString()):0L;
	}
	
	
	
	/**获取已发布的产品总数;(包含新手产品)
	 * @param type   0为普通项目,1为:新手专享;
	 * @param status 产品状态 0:营销中  1:已售罄  2:结算中   3:已还款 
	 * @return
	 */
	public int getProductCount(String[] type, String [] status){
		String myType = QwyUtil.packString(type);
		String mystatus = QwyUtil.packString(status);
		StringBuffer hql = new StringBuffer();
		hql.append("SELECT COUNT(*) FROM Product pro ");
		hql.append("WHERE pro.productStatus IN ("+mystatus+") ");
		if(!QwyUtil.isNullAndEmpty(myType)){
			hql.append("AND pro.productType IN ("+myType+")");
		}
		Object obj = dao.findJoinActive(hql.toString(), null);
		return obj==null?0:Integer.parseInt(obj.toString())>0?Integer.parseInt(obj.toString()):0;
	}
	
	/**
	 * 修改状态
	 * id 产品ID
	 * status 状态
	 */
	public boolean updateProductStatus(String id,String status){
		Product product=bean.getProductById(id);
		if(!QwyUtil.isNullAndEmpty(product)){
			product.setProductStatus(status);
			if("1".equals(product.getProductType())){
				//如果是新手产品主动进入售罄的话,则需要更新进入售罄时间;
				product.setClearingTime(new Date());
			}
			product.setRecommend("0");
			product.setUpdateTime(new Date());
			dao.update(product);
			return true;
		}
		return false;	
	}
	
	
	
	/**
	 * 分页获取产品记录
	 * @param pageUtil 分页工具类
 	 * @param product
	 * @param username 用户名
	 * @param insertTime 插入时间
	 * @param finishTime  结束时间
	 * @param productStatus  产品状态 -1:未审核 0:营销中  1:已售罄  2:结算中   3:已还款 
	 * @return
	 * @throws Exception 
	 */
	public PageUtil<Product> findProductPageUtil(PageUtil<Product> pageUtil,Product product, String finishTime, String insertTime, String username, String productStatus) throws Exception{
		return findProductPageUtil(pageUtil, product, finishTime, insertTime, username, productStatus,"finishTime");
		
	}
	
	
	/**
	 * 分页获取产品记录
	 * @param pageUtil 分页工具类
 	 * @param product
	 * @param username 用户名
	 * @param insertTime 插入时间
	 * @param finishTime  结束时间
	 * @param productStatus  产品状态 -1:未审核 0:营销中  1:已售罄  2:结算中   3:已还款
	 * @param DESCName  排序名称
	 * @return 
	 * @throws Exception 
	 */
	public PageUtil<Product> findProductPageUtil(PageUtil<Product> pageUtil,Product product, String finishTime, String insertTime, String username, String productStatus, String DESCName) throws Exception{
		List<Object> list=new ArrayList<Object>();
		StringBuffer hql=new StringBuffer();
		hql.append(" FROM Product pro WHERE 1=1 "); 
		if(!QwyUtil.isNullAndEmpty(product)){
			//项目总额
			if(!QwyUtil.isNullAndEmpty(product.getFinancingAmount())){
				hql.append(" AND pro.financingAmount = ? ");
				list.add(product.getFinancingAmount()*100);
			}
			//产品名称
			if(!QwyUtil.isNullAndEmpty(product.getTitle())){
				hql.append(" AND pro.title like '%"+product.getTitle()+"%' ");
//				list.add("%"+product.getTitle()+"%");
			}
			//年化收益
			if(!QwyUtil.isNullAndEmpty(product.getAnnualEarnings())){
				hql.append(" AND pro.annualEarnings = ? ");
				list.add(product.getAnnualEarnings());
			}
			//项目类型
			if(!QwyUtil.isNullAndEmpty(product.getInvestType())){
				hql.append(" AND pro.investType = ? ");
				list.add(product.getInvestType());
			}
		}	
		//发布时间
		if(!QwyUtil.isNullAndEmpty(insertTime)){
			String [] time=QwyUtil.splitTime(insertTime);
			if(time.length>1){
				hql.append(" AND pro.insertTime >= ? ");
				list.add(QwyUtil.fmMMddyyyy.parse(time[0]));
				hql.append(" AND pro.insertTime < ? ");
				list.add(QwyUtil.fmMMddyyyy.parse(time[1]));
			}else{
				hql.append(" AND pro.insertTime >= ? ");
				list.add(QwyUtil.fmMMddyyyyHHmmss.parse(time[0]+" 00:00:00"));
				hql.append(" AND pro.insertTime <= ? ");
				list.add(QwyUtil.fmMMddyyyyHHmmss.parse(time[0]+" 23:59:59"));
			}
		}
		//到期时间
		if(!QwyUtil.isNullAndEmpty(finishTime)){
			String [] time=QwyUtil.splitTime(finishTime);
			if(time.length>1){
				hql.append(" AND pro.finishTime >= ? ");
				list.add(QwyUtil.fmMMddyyyy.parse(time[0]));
				hql.append(" AND pro.finishTime <= ? ");
				list.add(QwyUtil.fmMMddyyyy.parse(time[1]));
			}else{
				hql.append(" AND pro.finishTime >= ? ");
				list.add(QwyUtil.fmMMddyyyyHHmmss.parse(time[0]+" 00:00:00"));
				hql.append(" AND pro.finishTime <= ? ");
				list.add(QwyUtil.fmMMddyyyyHHmmss.parse(time[0]+" 23:59:59"));
			}
		}
		//产品状态
		if(!QwyUtil.isNullAndEmpty(productStatus)){
			hql.append(" AND pro.productStatus = ? ");
			list.add(productStatus);
		}
		if(!QwyUtil.isNullAndEmpty(DESCName)){
			hql.append(" ORDER By  pro."+DESCName+" DESC ");
		}else{
			hql.append(" ORDER By  pro.insertTime DESC ");
		}
		return dao.getByHqlAndHqlCount(pageUtil, hql.toString(), hql.toString(), list.toArray());
	}
	
	
	/**
	 * 根据产品ID获取投资记录
	 */
	
	public Map<String, String> findInvestorsByProductId(){
		Map<String, String> map=new HashMap<String, String>();
		StringBuffer buffer=new StringBuffer();
		buffer.append(" SELECT  pd.id  , SUM(ivs.all_shouyi) FROM product pd LEFT JOIN  investors ivs on pd.id = ivs.product_id GROUP BY pd.id ");
		List<Object []> list=dao.LoadAllSql(buffer.toString(), null);
		if(!QwyUtil.isNullAndEmpty(list)){
			for (Object [] objects : list) {
				map.put(objects[0]+"",!QwyUtil.isNullAndEmpty(objects[1])?objects[1]+"":"0" );
			}
		}
		return map;
	}
	
//	public List<Product> findProductPageUtil(Product product, String finishTime, String insertTime, String username, String productStatus) throws Exception{
//		return findProductPageUtil(Product product, String finishTime, String insertTime, String username, String productStatus,null)
//	}
	/**
	 * 分页获取产品记录
	 * @param pageUtil 分页工具类	
 	 * @param product
	 * @param username 用户名
	 * @param insertTime 插入时间
	 * @param finishTime  结束时间
	 * @param productStatus  产品状态 -1:未审核 0:营销中  1:已售罄  2:结算中   3:已还款 
	 * @param DESCName 排序列名
	 * @return
	 * @throws Exception 
	 */
	@SuppressWarnings("unchecked")
	public List<Product> findProductPageUtil(Product product, String finishTime, String insertTime, String username, String productStatus) throws Exception{
		return findProductPageUtil(product,finishTime,insertTime,username,productStatus,"finishTime");
	}
	
	/**
	 * 分页获取产品记录
	 * @param pageUtil 分页工具类	
 	 * @param product
	 * @param username 用户名
	 * @param insertTime 插入时间
	 * @param finishTime  结束时间
	 * @param productStatus  产品状态 -1:未审核 0:营销中  1:已售罄  2:结算中   3:已还款 
	 * @param DESCName 排序列名
	 * @return
	 * @throws Exception 
	 */
	@SuppressWarnings("unchecked")
	public List<Product> findProductPageUtil(Product product, String finishTime, String insertTime, String username, String productStatus, String DESCName) throws Exception{
		List<Object> list=new ArrayList<Object>();
		StringBuffer hql=new StringBuffer();
		hql.append(" FROM Product pro WHERE 1=1 "); 
		if(!QwyUtil.isNullAndEmpty(product)){
			//项目总额
			if(!QwyUtil.isNullAndEmpty(product.getFinancingAmount())){
				hql.append(" AND pro.financingAmount = ? ");
				list.add(product.getFinancingAmount()*100);
			}
			//产品名称
			if(!QwyUtil.isNullAndEmpty(product.getTitle())){
				hql.append(" AND pro.title like '%"+product.getTitle()+"%' ");
//				list.add("%"+product.getTitle()+"%");
			}
			//年化收益
			if(!QwyUtil.isNullAndEmpty(product.getAnnualEarnings())){
				hql.append(" AND pro.annualEarnings = ? ");
				list.add(product.getAnnualEarnings());
			}
			//项目类型
			if(!QwyUtil.isNullAndEmpty(product.getInvestType())){
				hql.append(" AND pro.investType = ? ");
				list.add(product.getInvestType());
			}
			
		}	
		//发布时间
		if(!QwyUtil.isNullAndEmpty(insertTime)){
			String [] time=QwyUtil.splitTime(insertTime);
			if(time.length>1){
				hql.append(" AND pro.insertTime >= ? ");
				list.add(QwyUtil.fmMMddyyyy.parse(time[0]));
				hql.append(" AND pro.insertTime <= ? ");
				list.add(QwyUtil.fmMMddyyyy.parse(time[1]));
			}else{
				hql.append(" AND pro.insertTime >= ? ");
				list.add(QwyUtil.fmMMddyyyyHHmmss.parse(time[0]+" 00:00:00"));
				hql.append(" AND pro.insertTime <= ? ");
				list.add(QwyUtil.fmMMddyyyyHHmmss.parse(time[0]+" 23:59:59"));
			}
		}
		//到期时间
		if(!QwyUtil.isNullAndEmpty(finishTime)){
			String [] time=QwyUtil.splitTime(finishTime);
			if(time.length>1){
				hql.append(" AND pro.finishTime >= ? ");
				list.add(QwyUtil.fmMMddyyyy.parse(time[0]));
				hql.append(" AND pro.finishTime <= ? ");
				list.add(QwyUtil.fmMMddyyyy.parse(time[1]));
			}else{
				hql.append(" AND pro.finishTime >= ? ");
				list.add(QwyUtil.fmMMddyyyyHHmmss.parse(time[0]+" 00:00:00"));
				hql.append(" AND pro.finishTime <= ? ");
				list.add(QwyUtil.fmMMddyyyyHHmmss.parse(time[0]+" 23:59:59"));
			}
		}
		//产品状态
		if(!QwyUtil.isNullAndEmpty(productStatus)){
			hql.append(" AND pro.productStatus = ? ");
			list.add(productStatus);
		}
		if(!QwyUtil.isNullAndEmpty(DESCName)){
			hql.append(" ORDER By  pro."+DESCName+" DESC ");
		}else{
			hql.append(" ORDER By  pro.insertTime DESC ");
		}
		return dao.LoadAll(hql.toString(), list.toArray());
	}
	
	
	
	
	/**
	 * 分页资金速动明细表
	 * @param pageUtil 分页工具类
	 * @param insertTime 插入时间
	 * @return 
	 * @throws Exception 
	 */
	public PageUtil<Zjsumx> findZjsdmxPageUtil(PageUtil pageUtil,String insertTime) throws Exception{
		List<Object> list=new ArrayList<Object>();
		StringBuffer hql=new StringBuffer();
		hql.append(" SELECT DATE_FORMAT(dd.insert_time,'%Y-%m-%d') as date,  "); 
		hql.append(" ( SELECT SUM(pd.financing_amount) FROM product pd WHERE DATE_FORMAT(pd.insert_time,'%Y-%m-%d')=DATE_FORMAT(dd.insert_time,'%Y-%m-%d') AND pd.product_status in('0','1','2','3')  GROUP BY DATE_FORMAT(pd.insert_time,'%Y-%m-%d') ), "); 
		hql.append(" ( SELECT (SUM(ids.pay_interest)+SUM(ids.pay_money)) FROM interest_details ids WHERE DATE_FORMAT(ids.return_time,'%Y-%m-%d')=DATE_FORMAT(dd.insert_time,'%Y-%m-%d') "); 
		hql.append(" GROUP BY DATE_FORMAT(ids.return_time,'%Y-%m-%d') ) "); 
		hql.append(" FROM dateday dd  "); 
		if(!QwyUtil.isNullAndEmpty(insertTime)){
			String [] time=QwyUtil.splitTime(insertTime);
			if(time.length>1){
				hql.append(" WHERE dd.insert_time >= ? ");
				list.add(QwyUtil.fmMMddyyyy.parse(time[0]));
				hql.append(" AND dd.insert_time <= ? ");
				list.add(QwyUtil.fmMMddyyyy.parse(time[1]));
			}else{
				hql.append(" WHERE dd.insert_time >= ? ");
				list.add(QwyUtil.fmMMddyyyyHHmmss.parse(time[0]+" 00:00:00"));
				hql.append(" AND dd.insert_time <= ? ");
				list.add(QwyUtil.fmMMddyyyyHHmmss.parse(time[0]+" 23:59:59"));
			}
		}
		hql.append(" GROUP BY DATE_FORMAT(dd.insert_time,'%Y-%m-%d') ");
		hql.append(" ORDER BY DATE_FORMAT(dd.insert_time,'%Y-%m-%d') DESC ");
		StringBuffer bufferCount=new StringBuffer();
		bufferCount.append(" SELECT COUNT(t.date)  ");
		bufferCount.append(" FROM (");
		bufferCount.append(hql);
		bufferCount.append(") t");
		pageUtil=dao.getBySqlAndSqlCount(pageUtil, hql.toString(), bufferCount.toString(), list.toArray());
		pageUtil.setList(toZjsumxs(pageUtil.getList()));
		return pageUtil;
	}
	
	/**
	 * 分页资金速动明细表
	 * @param pageUtil 分页工具类
	 * @param insertTime 插入时间
	 * @return 
	 * @throws Exception 
	 */
	public List<Zjsumx> findZjsdmx(String insertTime) throws Exception{
		List<Object> list=new ArrayList<Object>();
		StringBuffer hql=new StringBuffer();
		hql.append(" SELECT DATE_FORMAT(dd.insert_time,'%Y-%m-%d') as date,  "); 
		hql.append(" ( SELECT SUM(pd.financing_amount) FROM product pd WHERE DATE_FORMAT(pd.insert_time,'%Y-%m-%d')=DATE_FORMAT(dd.insert_time,'%Y-%m-%d') AND pd.product_status in('0','1','2','3')  GROUP BY DATE_FORMAT(pd.insert_time,'%Y-%m-%d') ), "); 
		hql.append(" ( SELECT (SUM(ids.pay_interest)+SUM(ids.pay_money)) FROM interest_details ids WHERE DATE_FORMAT(ids.return_time,'%Y-%m-%d')=DATE_FORMAT(dd.insert_time,'%Y-%m-%d') "); 
		hql.append(" GROUP BY DATE_FORMAT(ids.return_time,'%Y-%m-%d') ) "); 
		hql.append(" FROM dateday dd  "); 
		if(!QwyUtil.isNullAndEmpty(insertTime)){
			String [] time=QwyUtil.splitTime(insertTime);
			if(time.length>1){
				hql.append(" WHERE dd.insert_time >= ? ");
				list.add(QwyUtil.fmMMddyyyy.parse(time[0]));
				hql.append(" AND dd.insert_time <= ? ");
				list.add(QwyUtil.fmMMddyyyy.parse(time[1]));
			}else{
				hql.append(" WHERE dd.insert_time >= ? ");
				list.add(QwyUtil.fmMMddyyyyHHmmss.parse(time[0]+" 00:00:00"));
				hql.append(" AND dd.insert_time <= ? ");
				list.add(QwyUtil.fmMMddyyyyHHmmss.parse(time[0]+" 23:59:59"));
			}
		}
		hql.append(" GROUP BY DATE_FORMAT(dd.insert_time,'%Y-%m-%d') ");
		hql.append(" ORDER BY DATE_FORMAT(dd.insert_time,'%Y-%m-%d') DESC ");
		List<Object []> objects=dao.LoadAllSql(hql.toString(), list.toArray());
		return toZjsumxs(objects);
	}
	/**
	 * 转换为付息表格bean
	 */
	public List<Zjsumx> toZjsumxs(List<Object []> list){
		List<Zjsumx>  zjsumxs=new ArrayList<Zjsumx>();
		try {
			if (!QwyUtil.isNullAndEmpty(list)) {
				/*Object [] obj=*/
				for (Object [] objects : list) {
					Zjsumx zjsumx=new Zjsumx();
					zjsumx.setDate(objects[0]+"");	
					zjsumx.setProductAllMoney(!QwyUtil.isNullAndEmpty(objects[1])?QwyUtil.calcNumber(objects[1], 100, "/", 2)+"":"0");
					zjsumx.setPayIns(!QwyUtil.isNullAndEmpty(objects[2])?QwyUtil.calcNumber(objects[2], 100, "/", 2)+"":"0");
					zjsumxs.add(zjsumx);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return  zjsumxs;	
	}
	/**
	 * 得到JASPER
	 * @param insertTime 项目插入时间 
	 * @param sourceFileName 路径
	 * @return
	 * @throws Exception
	 */
	public List<JasperPrint> getZjsumxJasperPrintList(String insertTime,String sourceFileName) throws Exception{
		List<JasperPrint> list=new ArrayList<JasperPrint>();
		List<Zjsumx> zjsumxs=findZjsdmx(insertTime);
		JRBeanCollectionDataSource ds=new JRBeanCollectionDataSource(zjsumxs);	
		JasperPrint js=JasperFillManager.fillReport(sourceFileName, null, ds);
		list.add(js);
		return list;
	}
/**
 * 根据id查询产品	
 * @param id
 * @return
 */
	public Product findProductById(String id){
		return (Product)dao.findById(new Product(), id);
	}
/**
 * 修改基金产品的浮动收益
 */
	public Boolean updateProductFund(Product newProduct){
		Product product=findProductById(newProduct.getId());
		Double baseEarnings=(double) 0;	
		if(!QwyUtil.isNullAndEmpty(product.getBaseEarnings())){
			baseEarnings=product.getBaseEarnings();
		}
		Double annualEarnings=newProduct.getJiangLiEarnings()+baseEarnings;
		
		product.setAnnualEarnings(annualEarnings);
		product.setJiangLiEarnings(newProduct.getJiangLiEarnings());
		product.setTitle(newProduct.getTitle());
		
		product.setFdsysmUrl(product.getFdsysmUrl());
		product.setFdsysmUrlWeb(newProduct.getFdsysmUrlWeb());
		
		product.setZrgzUrl(newProduct.getZrgzUrl());
		product.setZrgzUrlWeb(newProduct.getZrgzUrlWeb());
		product.setHdby(newProduct.getHdby());
		product.setHdlj(newProduct.getHdlj());
		product.setHkly(newProduct.getHkly());
		product.setZjbz(newProduct.getZjbz());
		product.setCplxjs(newProduct.getCplxjs());
		product.setUpdateTime(new Date());
		dao.saveOrUpdate(product);
		return true;
	}
/**
 * 修改合同表的用户标题	
 * @param productId
 * @return
 */
	@SuppressWarnings("unchecked")
	public String modifyContractByProductId(String productId,String productTitle){
	  List<Contract> contractList=null;
		StringBuffer buff = new StringBuffer();
		buff.append("FROM Contract c ");
		buff.append("WHERE c.productId= ? ");
		contractList=dao.LoadAll(buff.toString(), new Object[]{productId});
		if(contractList!=null&&contractList.size()>0){
			for(Object object:contractList){
				Contract contract=(Contract)object;
				if(!productTitle.equals(contract.getProductTitle())){
					contract.setProductTitle(productTitle);
					contract.setUpdateTime(new Date());
					dao.saveOrUpdate(contract);
				}

			}
		}
	   return null;
	}

}
