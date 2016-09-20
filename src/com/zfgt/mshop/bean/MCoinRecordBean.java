package com.zfgt.mshop.bean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.zfgt.common.util.QwyUtil;
import com.zfgt.mshop.dao.MCoinRecordDAO;
import com.zfgt.orm.MCoinRecord;

/**
 * 喵币流水记录的Bean层;<br>
 * 
 * @author 覃文勇
 * @createTime 2015-10-29下午5:24:11
 */
@Service
public class MCoinRecordBean {
	@Resource
	private MCoinRecordDAO dao;

	/**
	 * 加载喵币资金流水列表
	 * 
	 * @param uid
	 *            用户id
	 * @param pageSize
	 *            当前页数
	 * @param currentPage
	 *            当前页
	 * @param coinType
	 *            喵币类型 1：用户支出 2：用户收入
	 * @param status
	 *            0:消费成功;1:消费失败;
	 * @param startTime
	 *            开始时间
	 * @param endTime
	 *            结束时间
	 * @return 喵币资金流水对象
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<MCoinRecord> loadMCoinRecordList(long uid, Integer pageSize, Integer currentPage, String coinType, String status, String startTime, String endTime) throws Exception {
		try {
			ArrayList<Object> ob = new ArrayList<Object>();
			StringBuffer buff = new StringBuffer();
			buff.append("FROM MCoinRecord mc where 1=1 ");
			if (!QwyUtil.isNullAndEmpty(uid)) {
				buff.append(" AND mc.usersId=? ");
				ob.add(uid);
			}
			if (!QwyUtil.isNullAndEmpty(coinType)) {
				buff.append(" AND mc.coinType=? ");
				ob.add(coinType);
			}
			if (!QwyUtil.isNullAndEmpty(status)) {
				buff.append(" AND  mc.status=? ");
				ob.add(status);
			}
			if (!QwyUtil.isNullAndEmpty(startTime)) {
				buff.append(" AND mc.insertTime >= ? ");
				ob.add(QwyUtil.fmyyyyMMddHHmmss.parse(startTime));
			}
			if (!QwyUtil.isNullAndEmpty(endTime)) {
				buff.append("AND mc.insertTime <= ? ");
				ob.add(QwyUtil.fmyyyyMMddHHmmss.parse(endTime));
			}
			if (QwyUtil.isNullAndEmpty(currentPage)) {
				currentPage = 1;
			}
			if (QwyUtil.isNullAndEmpty(pageSize)) {
				pageSize = 20;
			}
			buff.append(" ORDER BY mc.insertTime DESC ");
			return dao.findAdvList(buff.toString(), ob.toArray(), currentPage, pageSize);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return null;

	}

	/**
	 * 筛选获取喵币流水字段
	 */
	public Map<String, Object> filterMCoinRecord(MCoinRecord mCoinRecord) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", mCoinRecord.getId());
		map.put("recordId", mCoinRecord.getRecordId());
		map.put("status", mCoinRecord.getStatus());
		map.put("coinType", mCoinRecord.getCoinType());
		map.put("insertTime", mCoinRecord.getInsertTime());
		map.put("coin", mCoinRecord.getCoin());
		map.put("note", mCoinRecord.getNote());
		return map;
	}

	/**
	 * 筛选获取喵币流水列表组
	 * 
	 * @param mCoinRecords
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> filterMCoinRecordGroup(List<MCoinRecord> mCoinRecords) throws Exception {
		// 以能否兑换为key,List为集合
		List<Map<String, Object>> listMap = new ArrayList<>();
		if (!QwyUtil.isNullAndEmpty(mCoinRecords)) {
			if (mCoinRecords == null || mCoinRecords.size() == 0) {
				return null;
			}
			for (int i = 0; i < mCoinRecords.size(); i++) {
				MCoinRecord mCoinRecord = mCoinRecords.get(i);
				listMap.add(filterMCoinRecord(mCoinRecord));
			}
		}
		return listMap;
	}

}
