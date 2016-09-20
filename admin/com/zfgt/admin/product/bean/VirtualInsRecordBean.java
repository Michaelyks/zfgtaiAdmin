/**
 * 
 */
package com.zfgt.admin.product.bean;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.zfgt.account.bean.ConfirmInvestBean;
import com.zfgt.admin.product.dao.VirtualInsRecordDAO;
import com.zfgt.common.bean.PlatformBean;
import com.zfgt.common.util.DESEncrypt;
import com.zfgt.common.util.PageUtil;
import com.zfgt.common.util.QwyUtil;
import com.zfgt.orm.Investors;
import com.zfgt.orm.Product;
import com.zfgt.orm.VirtualIns;
import com.zfgt.orm.VirtualInsRecord;

/**
 * @author 曾礼强
 * 2015年7月29日下午5:00:28
 * 虚拟投资记录
 */
@Service
public class VirtualInsRecordBean {
	@Resource
	VirtualInsRecordDAO dao;
	@Resource
	ConfirmInvestBean confirmInvestBean;
	@Resource
	private PlatformBean platformBean;
	private static Logger log = Logger.getLogger(VirtualInsRecordBean.class); 
	static Random random = new Random();  
    static {  
        random.setSeed(System.currentTimeMillis());  
    }  
    
    
    /**
     * 根据产品ＩＤ查询虚拟投资记录
     * @param productId
     * @param pageUtil 
     * @return
     */
    public PageUtil<VirtualInsRecord> findInsRecords(PageUtil<VirtualInsRecord> pageUtil,String productId ){
    	String hql=" FROM VirtualInsRecord vi WHERE vi.productId = ? ORDER BY vi.insertTime DESC";
    	dao.LoadAll(hql, new Object []{productId});
		return dao.getByHqlAndHqlCount(pageUtil, hql, hql,  new Object []{productId});
    }
    
    /**
     * 根据产品ID查询虚拟和真实投资记录
     * @param pageUtil
     * @param productId
     */
    public void findInvestorsRecords(PageUtil<Investors> pageUtil,String productId ){
    	try {
			StringBuffer queryListSql = new StringBuffer();
			queryListSql.append("SELECT * FROM ( ");
			queryListSql.append("SELECT u.username,tab.copies,tab.pay_time,1 FROM ");
			queryListSql.append("(SELECT * FROM Investors WHERE investor_status IN ('1','2','3') AND product_Id=?)tab ");
			queryListSql.append("LEFT JOIN users u ON tab.users_id=u.id ");
			queryListSql.append("UNION ALL ");
			queryListSql.append("SELECT username,pay_in_mony*0.01 AS copies, insert_time AS pay_time,2 FROM virtual_ins_record WHERE product_id=? ");
			queryListSql.append(")tab2 ORDER BY pay_time DESC");
			
			StringBuffer queryCountSql = new StringBuffer();
			queryCountSql.append("SELECT count(*) FROM ( ");
			queryCountSql.append("SELECT u.username,tab.copies,tab.pay_time,1 FROM ");
			queryCountSql.append("(SELECT * FROM Investors WHERE investor_status IN ('1','2','3') AND product_Id=?)tab ");
			queryCountSql.append("LEFT JOIN users u ON tab.users_id=u.id ");
			queryCountSql.append("UNION ALL ");
			queryCountSql.append("SELECT username,pay_in_mony*0.01 AS copies, insert_time AS pay_time,2 FROM virtual_ins_record WHERE product_id=? ");
			queryCountSql.append(")tab2 ");
			
			PageUtil<Object> page = dao.getBySqlAndSqlCount(pageUtil, queryListSql.toString(), queryCountSql.toString(), new String[]{productId,productId});
			if(!QwyUtil.isNullAndEmpty(page)&&!QwyUtil.isNullAndEmpty(page.getList())){
				Investors investors = null;
				List<Investors> list = new ArrayList<Investors>();
				for(int i=0;i<page.getList().size();i++){
					Object[] obj = (Object[])page.getList().get(i);
					investors = new Investors();
					//investors.setCopies(Math.round(Double.valueOf(obj[1].toString())));
					if(!QwyUtil.isNullAndEmpty(obj[1])){
						investors.setCopies(Math.round(Double.valueOf(obj[1].toString())));
					}else{
						continue;
					}
					try {
						investors.setPayTime(QwyUtil.fmyyyyMMddHHmmss.parse(obj[2].toString()));
					} catch (ParseException e) {
						e.printStackTrace();
					}
					if(obj[3].toString().equals("1")){
						investors.setUsername(QwyUtil.replaceStringToX(DESEncrypt.jieMiUsername(obj[0].toString())));
					}else {
						investors.setUsername(obj[0].toString());
					}
					
					list.add(investors);
				}
				pageUtil.setList(list);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
    
    
    /**
     * 合计投资列表
     */
    public VirtualIns sumVirtualIns(){
    	String sql=" SELECT sum(vi.in_money),sum(vi.users_count) FROM virtual_ins vi WHERE vi.status = '1' ";
    	List<Object []> list=dao.LoadAllSql(sql.toString(), null);
    	VirtualIns vi=new VirtualIns();
    	try {
			if (!QwyUtil.isNullAndEmpty(list)) {
				for (Object[] objects : list) {
					if(QwyUtil.isNullAndEmpty(objects[0])){
						vi.setInMoney(0D);
					}else{
						vi.setInMoney(Double.parseDouble(objects[0]+""));
					}
					if(QwyUtil.isNullAndEmpty(objects[1])){
						vi.setUserCount(0);
					}else{
						vi.setUserCount(Integer.parseInt(objects[1]+""));
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return vi;
    	
    }
    
    /**
     * 根据产品ＩＤ查询虚拟投资记录
     * @param productId
     * @param pageUtil 
     * @return
     */
    public List<VirtualInsRecord> findInsRecords(String productId ){
    	String hql=" FROM VirtualInsRecord vi WHERE vi.productId = ? ORDER BY vi.insertTime DESC";
    	return dao.LoadAll(hql, new Object []{productId});
    }
    
    /**
     * 根据产品ＩＤ查询虚拟投资列表
     * @param productId
     * @param pageUtil 
     * @return
     */
    public List<VirtualIns> findVirtualIns(PageUtil<VirtualIns> pageUtil,String productId ){
    	String hql=" FROM VirtualIns vi WHERE vi.productId = ? ORDER BY vi.insertTime DESC";
		return dao.LoadAll(hql, new Object []{productId});
    }
    
    /**
     * 分页查询虚拟投资记录列表
     * @return
     */
    public PageUtil<VirtualIns> findVirtualIns(PageUtil<VirtualIns> pageUtil){
    	String hql=" FROM VirtualIns vi ORDER BY vi.insertTime DESC";
		return dao.getByHqlAndHqlCount(pageUtil, hql, hql,  null);
    }
    
    /**
     * 查询产品的虚拟投资情况
     * @return
     */
    @SuppressWarnings("unchecked")
	public List<Object []> findVirtualIns(){
    	String hql=" SELECT pd.id,SUM(vi.in_money) FROM product pd LEFT JOIN virtual_ins vi ON pd.id= vi.product_id GROUP BY pd.id ";
		return dao.LoadAllSql(hql.toString(), null);
    }
	
	
    /**
     * 查询产品ID查询投资金额
     * @return
     */
	public Integer findVirtualIns(String productId){
    	String hql=" SELECT SUM(vi.users_count) FROM virtual_ins vi WHERE vi.product_id = ? AND vi.status=1 ";
    	Object object=dao.getSqlCount(hql.toString(), new Object []{productId});
    	if(!QwyUtil.isNullAndEmpty(object)){
    		return Integer.parseInt(object+"");
    	}
		return 0;
    }
    
    
    /**
     * 已产品ID为key获取投资金额
     */
    public Map<String,Double> toMapVirtual(List<Object []> list){
    	
    	Map<String,Double> map=new HashMap<String, Double>();
    	if(!QwyUtil.isNullAndEmpty(list)){
    		for (Object [] objects : list) {
    			if(!QwyUtil.isNullAndEmpty(objects[1])){
    				map.put(objects[0]+"", Double.parseDouble(objects[1]+""));
    			}else{
    				map.put(objects[0]+"", 0D);
    			}
			}
    	}
		return map;
    }
    
    
    
    /**
     * 每个产品的虚拟投资金额
     */
    public Map<String,Double> MapProductToVirtual(){
    	List<Object []> list=findVirtualIns();
    	return toMapVirtual(list);
    }
    
	/**
	 * 虚拟投资
	 * @param userCount 虚拟投资人数
	 * @param money 虚拟投资总金额
	 * @param productId 产品ID
	 * @return
	 */
	public boolean virtualInsIns(Integer userCount,Double money,String productId){
		try {
			Product pro = (Product)dao.findById(new Product(), productId);	
			//confirmInvestBean.updateProduct(pro,Double.parseDouble(QwyUtil.calcNumber(money, "100", "/")+""));
			Product cspro=pro;
			//起投金额
			Integer qtje=QwyUtil.calcNumber(pro.getQtje(), 10000, "/",0).intValue();
			List<VirtualInsRecord> list=new ArrayList<VirtualInsRecord>();
			//投资份数
			Integer inscount=QwyUtil.calcNumber(money, 10000,"/",0).intValue();
			if(userCount>1){
				if((qtje*userCount)==inscount){
					for (int i = 0; i < userCount; i++) {
						Product newPro = (Product)dao.findById(new Product(), pro.getId());
						if(newPro.getLeftCopies()<QwyUtil.calcNumber(money, 100, "/").longValue()){
							return false;
						}
						if(!QwyUtil.isNullAndEmpty(confirmInvestBean.updateProduct(pro, QwyUtil.calcNumber(qtje, 100, "*").doubleValue()))){
							return false;
						}
						VirtualInsRecord virtualInsRecord=new VirtualInsRecord();
						virtualInsRecord.setInsertTime(new Date());
						virtualInsRecord.setPayInMoney(QwyUtil.calcNumber(qtje, 10000, "*").doubleValue());
						virtualInsRecord.setProductId(productId);
						//手机头号
						Random head = new Random();
						Integer headRandom=head.nextInt(25);
						String username=getHeadMobile(headRandom)+"****"+getEndMobile();
						virtualInsRecord.setUsername(username);
						Thread.sleep(1000);
						list.add(virtualInsRecord);
					}
				}else{
					long max = QwyUtil.calcNumber(inscount, 2, "/", 0).intValue()+1;  
			        long min = qtje;  
			  
			        long[] result = generate(inscount, userCount,max, min);  
			        long total = 0;  
			        for (int i = 0; i < result.length; i++) {  
			            total += result[i];  
			        }
					//相等则买产品		        
			        if(total==inscount){
			        	for (int i = 0; i < userCount; i++) {
			        		Product newPro = (Product)dao.findById(new Product(), pro.getId());
							if(newPro.getLeftCopies()<QwyUtil.calcNumber(money, 100, "/").longValue()){
								return false;
							}
							if(!QwyUtil.isNullAndEmpty(confirmInvestBean.updateProduct(pro, QwyUtil.calcNumber(result[i], 100, "*").doubleValue()))){
								return false;
							}
							VirtualInsRecord virtualInsRecord=new VirtualInsRecord();
							virtualInsRecord.setInsertTime(new Date());
							virtualInsRecord.setPayInMoney(QwyUtil.calcNumber(result[i], 10000, "*").doubleValue());
							virtualInsRecord.setProductId(productId);
							//手机头号
							Random head = new Random();
							Integer headRandom=head.nextInt(25);
							String username=getHeadMobile(headRandom)+"****"+getEndMobile();
							virtualInsRecord.setUsername(username);
							Thread.sleep(1000);
							list.add(virtualInsRecord);
						}
			        }else{
			        	virtualInsIns(userCount, money, productId);
			        }
				}
	        }else{
	        	if(!QwyUtil.isNullAndEmpty(confirmInvestBean.updateProduct(pro,Double.parseDouble(QwyUtil.calcNumber(money, "100", "/")+"")))){
					return false;
				}
	        	VirtualInsRecord virtualInsRecord=new VirtualInsRecord();
				virtualInsRecord.setInsertTime(new Date());
				virtualInsRecord.setPayInMoney(money);
				virtualInsRecord.setProductId(productId);
				//手机头号
				Random head = new Random();
				Integer headRandom=head.nextInt(25);
				String username=getHeadMobile(headRandom)+"****"+getEndMobile();
				virtualInsRecord.setUsername(username);
				list.add(virtualInsRecord);
	        }
			Product newPro = (Product)dao.findById(new Product(), pro.getId());
			if(cspro.getLeftCopies()!=newPro.getLeftCopies()){
				if(QwyUtil.isNullAndEmpty(updateProduct(newPro, QwyUtil.calcNumber(money, "100", "/").doubleValue(), userCount))){
					dao.saveOrUpdate(newPro);
				}else{
					return false;
				}
			}else{
				dao.saveOrUpdate(pro);
			}
			platformBean.updateCollectMoney(money);
			platformBean.updateVirtualMoney(money);
			dao.saveList(list);
			return true;
		}  catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	/**更新理财产品;
	 * @param pro Product
	 * @param copies 购买份数;(一元一份)
	 */
	public String updateProduct(Product pro,double copies,Integer userCount){
		//更新产品;
		try {
			if(QwyUtil.isNullAndEmpty(pro)){
				return "支付失败:缺少支付数据;PRO";
			}
			Product newPro = (Product)dao.findById(new Product(), pro.getId());
			System.out.println("产品剩宇余额:"+newPro.getLeftCopies());
			System.out.println("购买产品的份数:"+copies);
			if(!"0".equals(pro.getProductStatus())){
				log.info("支付失败:产品不在营销中;id: "+pro.getId());
				return "支付失败:产品不在营销中";
			}
			long leftCopies = newPro.getLeftCopies();//剩余份数;1元1份;
			if(copies>leftCopies){
				//理财产品剩余的余额不足;
				log.info("理财产品余额剩余不足;id: "+pro.getId());
				return "理财产品余额剩余不足";
			}
			long newLeftCopies = QwyUtil.calcNumber(pro.getLeftCopies(), copies, "-").longValue();
			pro.setLeftCopies(newLeftCopies);//剩余未卖出的余额;
			if(newLeftCopies==0){
				pro.setProductStatus("1");
				if("1".equals(pro.getProductType())){
					pro.setClearingTime(new Date());
				}
			}
			long hasCopies = QwyUtil.calcNumber(pro.getHasCopies(), copies, "+").longValue();
			pro.setHasCopies(hasCopies);//已卖出的余额
			long usersCount = pro.getUserCount()==null?1:QwyUtil.calcNumber(pro.getUserCount(), userCount,"+").longValue();
			pro.setUserCount(usersCount);//被购买次数;一人可多次购买;
			pro.setUpdateTime(new Date());
			return "";
		} catch (Exception e) {
			log.error("操作异常: ",e);
		}
		return "操作异常";
	}
	
	/**
	 * 保存虚拟投资
	 * @param userCount 虚拟投资人数
	 * @param money 虚拟投资总金额
	 * @param productId 产品ID
	 * @param adminId 管理员ID
	 * @return
	 */
	public VirtualIns saveVirtualIns(Integer userCount,Double money,String productId,Long adminId){
		VirtualIns virtualIns=new VirtualIns();
		virtualIns.setUsersAdminId(adminId);
		virtualIns.setInMoney(money);
		virtualIns.setUserCount(userCount);
		virtualIns.setStatus("0");
		virtualIns.setInsertTime(new Date());
		virtualIns.setProductId(productId);
		String virtualInsId=dao.saveAndReturnId(virtualIns);
		virtualIns=(VirtualIns) dao.findById(new VirtualIns(), virtualInsId);
		return virtualIns;
		
	}
	
	/**
	 * 获取尾号4位
	 * @return
	 */
	public String getEndMobile(){
		String ychar = "0,1,2,3,4,5,6,7,8,9";
		int wei = 4;
		String[] ychars = ychar.split(",");
		String endMobile = "";
		Random rdm = new Random();
		for (int i = 0; i < wei; i++) {
			int j = (rdm.nextInt() >>> 1) % 10;
			if (j > 10)
				j = 0;
			endMobile = endMobile + ychars[j];
		}
		return endMobile;
		
	}
	/**
	 * 获取手机头3位
	 * @param type
	 * @return
	 */
	public String getHeadMobile(Integer type){
		switch (type) {
		case 1:
			return "130";
		case 2:
			return "131";
		case 3:
			return "132";
		case 4:
			return "133";
		case 5:
			return "134";
		case 6:
			return "135";
		case 7:
			return "136";
		case 8:
			return "137";
		case 9:
			return "138";
		case 10:
			return "139";
		case 11:
			return "150";
		case 12:
			return "151";
		case 13:
			return "152";
		case 14:
			return "153";
		case 15:
			return "155";
		case 16:
			return "156";
		case 17:
			return "157";
		case 18:
			return "158";
		case 19:
			return "159";
		case 20:
			return "177";
		case 21:
			return "186";
		case 22:
			return "183";
		case 23:
			return "187";
		case 24:
			return "188";
		case 25:
			return "189";
		default:
			return "173";
		}
	}
	
	
	 /** 
     *  
     * @param total 
     *            投资总额 
     * @param count 
     *            投资人数 
     * @param max 
     *            每个投资的最大额 
     * @param min 
     *            每个投资的最小额 
     * @return 存放生成的每个投资的值的数组 
     */  
    public static long[] generate(long total, int count, long max, long min) {  
    	
        long[] result = new long[count];  
        /**
         * 平均值
         */
        long average = total / count;  
        //平均值和最小值的差距
        long a = average - min;
        if(a==0){
        	a=1;
        }
        //最大和最小的值得差距
        long b = max - min;  
        if(b==0){
        	b=1;
        }
        //  
        //这样的随机数的概率实际改变了，产生大数的可能性要比产生小数的概率要小。  
        //这样就实现了大部分红包的值在平均数附近。大红包和小红包比较少。  
        long range1 = sqr(average - min);  
        long range2 = sqr(max - average);  
  
        for (int i = 0; i < result.length; i++) {  
            //因为小红包的数量通常是要比大红包的数量要多的，因为这里的概率要调换过来。  
            //当随机数>平均值，则产生小红包  
            //当随机数<平均值，则产生大红包  
            if (nextLong(min, max) > average) {  
                // 在平均线上减钱  
//              long temp = min + sqrt(nextLong(range1));  
                long temp = min + xRandom(min, average);  
                result[i] = temp;  
                total -= temp;  
            } else {  
                // 在平均线上加钱  
//              long temp = max - sqrt(nextLong(range2));  
                long temp = max - xRandom(average, max);  
                result[i] = temp;  
                total -= temp;  
            }  
        }  
        // 如果还有余钱，则尝试加到小红包里，如果加不进去，则尝试下一个。  
        while (total > 0) {  
            for (int i = 0; i < result.length; i++) {  
                if (total > 0 && result[i] < max) {  
                    result[i]++;  
                    total--;  
                }  
            }  
        }  
        // 如果钱是负数了，还得从已生成的小红包中抽取回来  
        while (total < 0) {  
            for (int i = 0; i < result.length; i++) {  
                if (total < 0 && result[i] > min) {  
                    result[i]--;  
                    total++;  
                }  
            }  
        }  
        return result;  
    }  
	
	 /** 
     * 生产min和max之间的随机数，但是概率不是平均的，从min到max方向概率逐渐加大。 
     * 先平方，然后产生一个平方值范围内的随机数，再开方，这样就产生了一种“膨胀”再“收缩”的效果。 
     *  
     * @param min 
     * @param max 
     * @return 
     */  
    static long xRandom(long min, long max) {  
        return sqrt(nextLong(sqr(max - min)));  
    } 
    
    static long sqrt(long n) {  
        // 改进为查表？  
        return (long) Math.sqrt(n);  
    }  
    static long sqr(long n) {  
        // 查表快，还是直接算快？  
        return n * n;  
    }  
    static long nextLong(long n) {  
    	if(n==0){
    		n=1;
    	}
        return random.nextInt((int) n);  
    }  
  
    static long nextLong(long min, long max) {  
        return random.nextInt((int) (max - min + 1)) + min;  
    }  
    
    
	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		Integer inscount=100;
		Integer userCount=1;
		long max = 51;  
	    long min = 1;  
		long[] result = generate(inscount, userCount, max, min);  
	        long total = 0;  
	        for (int i = 0; i < result.length; i++) {
	            // System.out.println("result[" + i + "]:" + result[i]);  
	            // System.out.println(result[i]);  
	            total += result[i];  
	            System.out.println("total:" + result[i]);  
	        }  
	        //检查生成的红包的总额是否正确  
	        System.out.println("total:" + total);  
	  		
	  		
	  		
//	        //统计每个钱数的红包数量，检查是否接近正态分布  
//	        int count[] = new int[(int) max + 1];  
//	        for (int i = 0; i < result.length; i++) {  
//	            count[(int) result[i]] += 1;  
//	        }  
//	  
//	        for (int i = 0; i < count.length; i++) {  
//	            System.out.println("" + i + "  " + count[i]);  
//	        } 
	}

	/**
	 * 转化为投资实体类
	 * @param virtualInsRecords
	 * @return
	 */
	public List<Investors> ToInvestors(List<VirtualInsRecord> virtualInsRecords){
		List<Investors> list=new ArrayList<Investors>();
		try {
			if(!QwyUtil.isNullAndEmpty(virtualInsRecords)){
				for (int i = 0; i < virtualInsRecords.size(); i++) {
					VirtualInsRecord virtualInsRecord = virtualInsRecords.get(i);
					Investors investors = new Investors();
					investors.setCopies(QwyUtil.calcNumber(virtualInsRecord.getPayInMoney(), 100, "/", 0).longValue());
					investors.setUsername(virtualInsRecord.getUsername());
					investors.setPayTime(virtualInsRecord.getInsertTime());
					list.add(investors);
				}
				return list;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
		
	}
}
