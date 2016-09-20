package com.zfgt.mshop.bean;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.zfgt.common.util.DESEncrypt;
import com.zfgt.common.util.QwyUtil;
import com.zfgt.mshop.dao.MUsersAddressDAO;
import com.zfgt.orm.MUsersAddress;

/**
 * 喵币流水记录的Bean层;<br>
 * 
 * @author 覃文勇
 * @createTime 2015-10-29下午5:24:11
 */
@Service
public class MUsersAddressBean {
	@Resource
	private MUsersAddressDAO dao;

	/**
	 * 获取收货地址
	 * 
	 * @param uid
	 * @param type
	 *            地址类型（0： 默认 1 ：备用）
	 * @param currentPage
	 *            当前页
	 * @param pageSize
	 *            当前页数
	 * @return 用户收货地址列表
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<MUsersAddress> loadMUsersAddressList(long uid, String type, int currentPage, int pageSize) throws Exception {
		try {
			ArrayList<Object> ob = new ArrayList<Object>();
			StringBuffer buff = new StringBuffer();
			buff.append("FROM MUsersAddress mu where 1=1 ");
			buff.append(" AND mu.status='0' ");// 可用
			if (!QwyUtil.isNullAndEmpty(uid)) {
				buff.append(" AND mu.usersId=? ");
				ob.add(uid);
			}
			if (!QwyUtil.isNullAndEmpty(type)) {
				buff.append(" AND mu.type=? ");
				ob.add(type);
			}
			if (QwyUtil.isNullAndEmpty(currentPage)) {
				currentPage = 1;
			}
			if (QwyUtil.isNullAndEmpty(pageSize)) {
				pageSize = 20;
			}
			buff.append(" ORDER BY mu.type, mu.insertTime DESC ");
			return dao.findAdvList(buff.toString(), ob.toArray(), currentPage, pageSize);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}

	/**
	 * 筛选获取用户收货地址字段
	 */
	public Map<String, Object> filterMUsersAddress(MUsersAddress mUsersAddress) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", mUsersAddress.getId());
		map.put("insertTime", mUsersAddress.getInsertTime());
		map.put("contractName", mUsersAddress.getContractName());
		map.put("phone", DESEncrypt.jieMiUsername(mUsersAddress.getPhone()));
		map.put("address", mUsersAddress.getAddress());
		map.put("addressDetail", mUsersAddress.getAddressDetail());
		map.put("index", mUsersAddress.getIndex());
		map.put("type", mUsersAddress.getType());
		return map;
	}

	/**
	 * 筛选获取用户收货地址字段列表组
	 */
	public List<Map<String, Object>> filterMUsersAddressGroup(List<MUsersAddress> AddressList) throws Exception {
		List<Map<String, Object>> listMap = new ArrayList<>();
		if (!QwyUtil.isNullAndEmpty(AddressList)) {
			if (AddressList == null || AddressList.size() == 0) {
				return null;
			}
			for (int i = 0; i < AddressList.size(); i++) {
				MUsersAddress mUsersAddress = AddressList.get(i);
				mUsersAddress.setIndex(i);// 添加索引
				listMap.add(filterMUsersAddress(mUsersAddress));
			}
		}
		return listMap;
	}

	/**
	 * 添加用户收货地址
	 * 
	 * @param uid
	 *            用户id
	 * @param contractName
	 *            联系人
	 * @param phone
	 *            联系电话
	 * @param address
	 *            联系地址
	 * @param addressDetail
	 *            详细地址
	 * @return
	 * @throws Exception
	 */
	public String addUsersAddress(long uid, String contractName, String phone, String address, String addressDetail, String type) throws Exception {
		MUsersAddress mUsersAddress = new MUsersAddress();
		mUsersAddress.setUsersId(uid);
		mUsersAddress.setContractName(contractName);
		mUsersAddress.setPhone(DESEncrypt.jiaMiUsername(phone));
		mUsersAddress.setAddress(address);
		mUsersAddress.setAddressDetail(addressDetail);
		mUsersAddress.setInsertTime(new Date());
		mUsersAddress.setType(type);
		mUsersAddress.setStatus("0");
		return dao.saveAndReturnId(mUsersAddress);
	}

	/**
	 * 删除用户收货地址（即修改status）
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public Long updateUsersAddress(long id, long usersId) throws Exception {
		try {
			MUsersAddress mUsersAddress = (MUsersAddress) dao.findById(new MUsersAddress(), id);
			if (!QwyUtil.isNullAndEmpty(mUsersAddress.getId()) && mUsersAddress.getUsersId() == usersId) {
				mUsersAddress.setStatus("1");
				mUsersAddress.setUpdateTime(new Date());
				dao.update(mUsersAddress);
				return mUsersAddress.getId();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 修改地址
	 * 
	 * @param id
	 *            地址id
	 * @param contractName
	 *            联系人
	 * @param phone
	 *            联系电话
	 * @param address
	 *            联系地址
	 * @param addressDetail
	 *            联系地址详情
	 * @return id
	 * @throws Exception
	 */
	public Long updateUsersAddress(long mUsersAddressId, String contractName, String phone, String address, String addressDetail, String type) throws Exception {
		try {
			MUsersAddress mUsersAddress = (MUsersAddress) dao.findById(new MUsersAddress(), mUsersAddressId);
			if (!QwyUtil.isNullAndEmpty(mUsersAddress.getId())) {
				mUsersAddress.setContractName(contractName);
				mUsersAddress.setPhone(DESEncrypt.jiaMiUsername(phone));
				mUsersAddress.setAddress(address);
				mUsersAddress.setAddressDetail(addressDetail);
				mUsersAddress.setUpdateTime(new Date());
				mUsersAddress.setType(type);
				dao.saveOrUpdate(mUsersAddress);
				return mUsersAddress.getId();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public MUsersAddress findById(Long id) throws Exception {
		return (MUsersAddress) dao.findById(new MUsersAddress(), id);
	}

	/**
	 * 
	 * @param usersId
	 * @return
	 */
	public Object updateUsersAddressType(long usersId) {
		StringBuffer sb = new StringBuffer();
		sb.append("update m_users_address set type = 1 where users_id = ");
		sb.append(usersId);
		return dao.excuteSql(sb.toString(), null);
	}

	/**
	 * 
	 * @param mUsersAddressId
	 * @param type
	 * @return
	 * @throws Exception
	 */
	public Long updateUsersAddressType(long mUsersAddressId, String type, long usersId) throws Exception {
		try {
			MUsersAddress mUsersAddress = (MUsersAddress) dao.findById(new MUsersAddress(), mUsersAddressId);
			if (!QwyUtil.isNullAndEmpty(mUsersAddress.getId()) && mUsersAddress.getUsersId() == usersId) {
				mUsersAddress.setUpdateTime(new Date());
				mUsersAddress.setType(type);
				dao.saveOrUpdate(mUsersAddress);
				return mUsersAddress.getId();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
