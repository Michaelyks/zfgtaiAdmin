package com.zfgt.mshop.bean;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.zfgt.common.bean.SystemConfigBean;
import com.zfgt.common.util.PageUtil;
import com.zfgt.common.util.QwyUtil;
import com.zfgt.mshop.dao.MProductDAO;
import com.zfgt.orm.Banner;
import com.zfgt.orm.MProduct;
import com.zfgt.orm.Product;
import com.zfgt.orm.SystemConfig;
import com.zfgt.product.bean.ProductCategoryBean;

/**
 * 喵商城的Bean层;<br>
 * 
 * @author 覃文勇
 * @createTime 2015-10-28上午9:27:18
 */
@Service
public class MProductBean {
	private static Logger log = Logger.getLogger(MProductBean.class);

	@Resource
	private MProductDAO dao;
	@Resource
	private SystemConfigBean systemConfigBean;

	@SuppressWarnings("unchecked")
	public List<MProduct> loadmProductList(Integer pageSize, Integer currentPage, String type) throws Exception {
		try {
			ArrayList<Object> ob = new ArrayList<Object>();
			StringBuffer buff = new StringBuffer();
			buff.append("FROM MProduct mp where 1=1 ");

			if (!(QwyUtil.isNullAndEmpty(type) || "all".equalsIgnoreCase(type))) {
				buff.append("AND mp.type = ? ");
				ob.add(type);
			}

			if (QwyUtil.isNullAndEmpty(currentPage)) {
				currentPage = 1;
			}
			if (QwyUtil.isNullAndEmpty(pageSize)) {
				pageSize = 20;
			}
			buff.append(" ORDER BY mp.status ASC,mp.insertTime DESC");
			return dao.findAdvList(buff.toString(), ob.toArray(), currentPage, pageSize);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return null;

	}

	/**
	 * 筛选获取喵商品列表组
	 */
	public List<Map<String, Object>> filterMProductListGroup(List<MProduct> mProducts) throws Exception {
		// 以能否兑换为key,List为集合
		List<Map<String, Object>> listMap = new ArrayList<>();
		if (!QwyUtil.isNullAndEmpty(mProducts)) {
			if (mProducts == null || mProducts.size() == 0) {
				return null;
			}
			SystemConfig config = systemConfigBean.findSystemConfig();
			for (int i = 0; i < mProducts.size(); i++) {
				MProduct mProduct = mProducts.get(i);
				mProduct.setIndex(i);
				listMap.add(filterMProductList(mProduct, config));
			}
		}
		return listMap;
	}

	/**
	 * 筛选获取喵商品字段
	 */
	public Map<String, Object> filterMProductList(MProduct mProduct, SystemConfig config) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("productId", mProduct.getId());
		map.put("title", mProduct.getTitle());
		map.put("status", mProduct.getStatus());
		map.put("type", mProduct.getType());
		map.put("price", mProduct.getPrice());
		map.put("leftStock", mProduct.getLeftStock());
		map.put("vip", mProduct.getVip());
		map.put("img", config.getHttpUrl() + config.getFileName() + "/mobile_img/mShop/" + mProduct.getImg());
		map.put("postage", new BigDecimal(mProduct.getPostage()));
		map.put("detailURL", mProduct.getDetailURL() + "");
		if (!QwyUtil.isNullAndEmpty(mProduct.getIndex())) {
			map.put("index", mProduct.getIndex());
		}
		return map;
	}

	/**
	 * 根据id查询喵商品
	 * 
	 * @param id
	 *            喵商品id
	 * @return
	 * @throws Exception
	 */
	public MProduct findById(String id) throws Exception {
		return (MProduct) dao.findById(new MProduct(), id);
	}

	/**
	 * 加载喵商城banner列表
	 * 
	 * @param pageSize
	 * @param currentPage
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<Banner> loadBannerList(Integer pageSize, Integer currentPage) throws Exception {
		StringBuffer buff = new StringBuffer();
		buff.append("FROM Banner b where b.type=2 and b.status=0 ORDER BY b.insertTime DESC");
		return dao.findAdvList(buff.toString(), null, currentPage, pageSize);
	}

	/**
	 * 筛选获取喵商城banner列表组
	 */
	public List<Map<String, Object>> filterBannerListGroup(List<Banner> banners) throws Exception {
		// 以能否兑换为key,List为集合
		List<Map<String, Object>> listMap = new ArrayList<>();
		if (!QwyUtil.isNullAndEmpty(banners)) {
			if (banners == null || banners.size() == 0) {
				return null;
			}
			SystemConfig config = systemConfigBean.findSystemConfig();
			for (int i = 0; i < banners.size(); i++) {
				Banner banner = banners.get(i);
				listMap.add(filterBannerList(banner, config));
			}
		}
		return listMap;
	}

	/**
	 * 筛选获取喵商城banner字段
	 */
	public Map<String, Object> filterBannerList(Banner banner, SystemConfig config) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", banner.getId());
		map.put("title", banner.getTitle() + "");
		map.put("description", banner.getDescription() + "");
		map.put("imgURL", config.getHttpUrl() + config.getFileName() + "/mobile_img/banner/" + banner.getImgURL());
		map.put("hdUrl", banner.getHdUrl() + "");
		return map;
	}

	/**
	 * 扣除商品剩余库存
	 * 
	 * @param mProduct
	 *            购买商品
	 * @param copies
	 *            购买份数
	 * @return
	 */
	public Boolean updateByleftStock(MProduct mProduct, long copies) {
		Boolean isOk = false;
		try {
			long leftStock = mProduct.getLeftStock() - copies;
			if (leftStock < 0) {
				return isOk;
			} else {
				mProduct.setLeftStock(leftStock);
				if (leftStock == 0l) {
					mProduct.setStatus("1");// 不可用
				}
				mProduct.setUpdateTime(new Date());
				dao.saveOrUpdate(mProduct);
				return true;
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return isOk;
	}

	/**
	 * 
	 * @param pageUtil
	 * @param mode
	 * @param mcoinStart
	 * @param mcoinEnd
	 * @return
	 */
	public PageUtil<MProduct> getMProductByCondition(PageUtil<MProduct> pageUtil, int mode, int mcoinStart, int mcoinEnd) {
		try {
			StringBuffer sb = new StringBuffer();
			sb.append("FROM MProduct mp where 1 = 1 ");
			if (mcoinStart > 0) {
				sb.append(" and mp.price >= ");
				sb.append(mcoinStart);
			}
			if (mcoinEnd > 0) {
				sb.append(" and mp.price <= ");
				sb.append(mcoinEnd);
			}

			switch (mode) {
			case 1:// 人气高到低
				sb.append(" order by mp.leftStock asc");// 随意用的
				break;
			case 2:// 人气低到高
				sb.append(" order by mp.leftStock desc");
				break;
			case 3:// 价格高到低
				sb.append(" order by mp.price*1 desc");
				break;
			case 4:// 价格低到高
				sb.append(" order by mp.price*1 asc");
				break;
			case 5:// 上架时间高到低
				sb.append(" order by mp.insertTime desc");
				break;
			case 6:// 上架时间低到高
				sb.append(" order by mp.insertTime asc");
				break;
			default:
				sb.append(" order by mp.status asc, mp.insertTime DESC");
				break;
			}

			return (PageUtil<MProduct>) dao.getPage(pageUtil, sb.toString(), null);
		} catch (Exception e) {
			log.error("操作异常: ", e);
		}
		return null;
	}

	public Long getAllCountByCondition( int mcoinStart, int mcoinEnd) {
		try {
			StringBuffer sb = new StringBuffer();
			sb.append("select count(*) FROM MProduct mp where 1 = 1 ");
			if (mcoinStart > 0) {
				sb.append(" and mp.price >= ");
				sb.append(mcoinStart);
			}
			if (mcoinEnd > 0) {
				sb.append(" and mp.price <= ");
				sb.append(mcoinEnd);
			}
			return (Long) dao.findObjectByHql(sb.toString(), null);
		} catch (Exception e) {
			log.error("操作异常: ", e);
		}
		return 0L;
	}

}
