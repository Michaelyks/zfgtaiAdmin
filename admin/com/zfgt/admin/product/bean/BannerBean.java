package com.zfgt.admin.product.bean;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.zfgt.admin.product.dao.BannerDAO;
import com.zfgt.common.bean.SystemConfigBean;
import com.zfgt.common.util.QwyUtil;
import com.zfgt.orm.Banner;

@Service
public class BannerBean {
	@Resource
	BannerDAO dao;
	@Resource
	private SystemConfigBean systemConfigBean;

	/**
	 * @param type
	 *            类型0网站1手机
	 * @return
	 */
	public List<Banner> findBanners(String type) {
		List<Object> list = new ArrayList<Object>();
		StringBuffer buffer = new StringBuffer();
		buffer.append(" FROM Banner b ");
		if (!QwyUtil.isNullAndEmpty(type)) {
			buffer.append(" WHERE b.type = ? ");
			list.add(type);
		}
		buffer.append(" ORDER BY  b.insertTime DESC ");
		return dao.LoadAll(buffer.toString(), null);
	}

	/**
	 * 保存Banner
	 */
	public Banner saveBanner(Banner banner) {
		banner.setInsertTime(new Date());
		banner.setStatus("0");
		if (!QwyUtil.isNullAndEmpty(banner.getNoticeId())) {
			banner.setNoticeId(banner.getNoticeId().trim());
		}
		dao.save(banner);
		return banner;
	}

	/**
	 * 根据ID查询版本信息
	 * 
	 * @param id
	 * @return
	 */
	public Banner findBannerById(Long id) {
		return (Banner) dao.findById(new Banner(), id);
	}

	/**
	 * 根据ID修改状态
	 */
	public boolean updateStatusById(Long id) {
		Banner banner = findBannerById(id);
		if (!QwyUtil.isNullAndEmpty(banner)) {
			if (!QwyUtil.isNullAndEmpty(banner.getStatus()) && banner.getStatus().equals("1")) {
				banner.setStatus("0");
			} else {
				banner.setStatus("1");
			}
		}
		dao.saveOrUpdate(banner);
		return true;
	}

	/**
	 * 根据id修改标题,公告id,活动连接
	 * 
	 * @param id
	 * @param title
	 * @param noticeId
	 * @param hdUrl
	 * @return
	 */
	public boolean modifyBanner(Long id, String title, String noticeId, String hdUrl) {
		Banner banner = findBannerById(id);
		banner.setTitle(title);
		banner.setNoticeId(noticeId);
		banner.setHdUrl(hdUrl);
		dao.saveOrUpdate(banner);
		return true;
	}
}
