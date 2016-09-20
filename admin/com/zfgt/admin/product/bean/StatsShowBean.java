package com.zfgt.admin.product.bean;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.zfgt.admin.product.dao.StatsShowDao;

@Service
public class StatsShowBean {

	private static Logger log = Logger.getLogger(StatsShowBean.class);

	@Resource
	private StatsShowDao dao;

	public Map<String, Object> findStatsShow() throws Exception {
		StringBuffer sb = new StringBuffer();
		sb.append(
				"SELECT s.*, ss.*, sss.*, ssss.*, sssss.* FROM ( SELECT sum(copies) dueThreeDayUserCentSum FROM investors WHERE investor_status = 1 AND DATE_ADD( DATE_FORMAT(NOW(), '%Y-%m-%d'), INTERVAL 3 DAY ) >= finish_time AND DATE_FORMAT(NOW(), '%Y-%m-%d') >= finish_time ) s JOIN ( SELECT ifnull(sum(all_copies), 0) dueThreeDayProductCentSum FROM product WHERE product_status IN (0, 1) AND DATE_ADD( DATE_FORMAT(NOW(), '%Y-%m-%d'), INTERVAL 3 DAY ) >= finish_time AND DATE_FORMAT(NOW(), '%Y-%m-%d') >= finish_time ) ss join (SELECT sum(copies) stillBalanceUserCentSum FROM investors WHERE investor_status = 1) sss join (SELECT sum(all_copies) stillBalanceProductCentSum FROM product WHERE product_status IN (0, 1)) ssss join (SELECT ifnull( sum(pay_money + pay_interest), 0 ) dueNoBalanceCentSum FROM interest_details WHERE investors_id IN ( SELECT id FROM investors WHERE investor_status IN (2, 3)) AND `STATUS` = 0) sssss");

		List<Map<String, Object>> list = dao.LoadAllListMapSql(sb.toString(), null);
		if (list == null) {
			return null;
		}

		list.get(0);

		return list.get(0);
	}
}
