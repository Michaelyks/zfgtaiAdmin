package com.zfgt.product.bean;

import java.util.Date;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.zfgt.common.util.QwyUtil;
import com.zfgt.orm.Contract;
import com.zfgt.orm.Investors;
import com.zfgt.orm.Product;
import com.zfgt.orm.UsersInfo;
import com.zfgt.product.dao.ContractDAO;

/**合同bean
 * @author 覃文勇
 * @createTime 2015-9-14下午4:53:55
 */
@Service
public class ContractBean {
	private static Logger log = Logger.getLogger(ContractBean.class); 
	@Resource
	private ContractDAO dao;
	private static String title="出资份额收益权转让及回购合同";
/**
 * 生成合同数据	
 * @param in 投资列表实体;
 * @return 生成好的合同实体;
 */
	public Contract saveContract(Investors in,Product pro,UsersInfo usersInfo){
		String id=null;
		try {
			if(in!=null&&!QwyUtil.isNullAndEmpty(in.getProductId())){
				if(QwyUtil.isNullAndEmpty(pro)){
					return null;
				}
				if(QwyUtil.isNullAndEmpty(usersInfo)){
					return null;
				}
				Contract contract=new Contract();
				
				contract.setInvestorsId(in.getId());
				
				contract.setTitle(title);
				
				contract.setProductId(in.getProductId());
				
				contract.setProductTitle(pro.getTitle());
				
				contract.setStartTime(in.getInsertTime());
				
				contract.setEndTime(in.getFinishTime());
				
				contract.setDays(Long.parseLong(pro.getTzqx()+""));
				
				contract.setInsertTime(new Date());
				
				contract.setUsersId(in.getUsersId());
				
				contract.setUsername(usersInfo.getPhone());
				
				contract.setIdcard(usersInfo.getIdcard());
				
				contract.setStatus(String.valueOf(0));//状态 0 生效；1 失效；2已删除
				
				if("0".equals(pro.getProductType()) || "1".equals(pro.getProductType())){//类别 默认为0; 0为普通项目,1为:新手专享;2:基金产品
					
					contract.setType(String.valueOf(0));//类型 0理财产品；1 基金产品,
					
				}else if("2".equals(pro.getProductType())){
					
					contract.setType(String.valueOf(1));//类型 0 理财产品；1 基金产品,
				}
				
				contract.setCopies(in.getCopies());
				
				contract.setInMoney(in.getInMoney());
				
				contract.setCoupon(in.getCoupon());
				
				id=dao.saveAndReturnId(contract);
				
				if(!QwyUtil.isNullAndEmpty(id)){
					
					in.setContractId(id);
					
				    in.setUpdateTime(new Date());
				    
				    dao.saveOrUpdate(in);
				    
				    return contract;					
				}else{
					return null;
				}
				
			}
        
			
		} catch (Exception e) {
			// TODO: handle exception
			log.error("操作异常",e);
		}

		
		return null;
	}

}
