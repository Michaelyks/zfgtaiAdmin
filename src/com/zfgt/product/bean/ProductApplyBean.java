package com.zfgt.product.bean;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.zfgt.common.util.DESEncrypt;
import com.zfgt.orm.ProductApply;
import com.zfgt.product.dao.ProductApplyDao;

/**
 * 
 * @author bym
 *
 */
@Service
public class ProductApplyBean {
	private static Logger log = Logger.getLogger(ProductApplyBean.class);

	@Resource
	private ProductApplyDao dao;

	public void doProductApply(ProductApply productApply) {
		productApply.setContractName(DESEncrypt.jiaMiUsername(productApply.getContractName().toLowerCase()));
		productApply.setPhone(DESEncrypt.jiaMiUsername(productApply.getPhone().toLowerCase()));
		dao.saveOrUpdate(productApply);
	}
}
