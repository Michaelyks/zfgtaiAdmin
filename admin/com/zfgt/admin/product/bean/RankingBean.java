package com.zfgt.admin.product.bean;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.zfgt.account.bean.UserInfoBean;
import com.zfgt.admin.product.dao.RankingDao;
import com.zfgt.common.util.PageUtil;
import com.zfgt.common.util.QwyUtil;
import com.zfgt.orm.Rank;
import com.zfgt.orm.Region;

/**金额排行
 * @author 覃文勇
 * @createTime 2015-7-30下午4:56:58
 */
@Service
public class RankingBean {
	private static Logger log = Logger.getLogger(RankingBean.class);
	@Resource
	RankingDao dao;
	/*
	 * 投入总金额排行榜
	 */
	@SuppressWarnings("unchecked")
	public PageUtil<Rank> loadInvestorRank(PageUtil pageUtil){
		try {
			List<Rank> rankList=new ArrayList<Rank>();
			String hql="SELECT i.users_id,SUM(in_money) 'in_money',username,real_name FROM investors i,users u,users_info ui WHERE u.id=i.users_id AND i.users_id=ui.users_id AND i.investor_status IN (1,2,3) GROUP BY i.users_id ORDER BY in_money DESC";
			StringBuffer buffer=new StringBuffer();
			buffer.append( "  SELECT COUNT(t.in_money) FROM (" );
			buffer.append(hql);
			buffer.append(") t ");
			PageUtil<Object []> page=dao.getBySqlAndSqlCount(pageUtil, hql, buffer.toString(),null);
			for(int i=0;i<page.getList().size();i++){
				Object [] objects=page.getList().get(i);
				Rank rank=new Rank();
				rank.setUsersId(objects[0]+"");
				rank.setInmoney(objects[1]+"");//投资总金额
				if(!QwyUtil.isNullAndEmpty(objects[2])){
					rank.setUsersname((String) objects[2]);
				}
				if(!QwyUtil.isNullAndEmpty(objects[3])){
				rank.setRealname((String) objects[3]);
				}
				rankList.add(rank);
			}
			pageUtil.setList(rankList);
			return pageUtil;
		} catch (Exception e) {
			// TODO: handle exception
			log.error("操作异常: ",e);
		}
		return null;
	}

	/*
	 * 充值总金额排行榜
	 */
	@SuppressWarnings("unchecked")
	public PageUtil<Rank> loadCZRecordRank(PageUtil pageUtil){
		try {
			List<Rank> rankList=new ArrayList<Rank>();
			String hql="SELECT c.users_id,SUM(money) 'money',username,real_name FROM cz_record c,users u,users_info ui WHERE u.id=c.users_id AND c.users_id=ui.users_id AND STATUS IN (1) GROUP BY users_id ORDER BY money DESC";
			StringBuffer buffer=new StringBuffer();
			buffer.append( "  SELECT COUNT(t.money) FROM (" );
			buffer.append(hql);
			buffer.append(") t ");
			PageUtil<Object []> page=dao.getBySqlAndSqlCount(pageUtil, hql, buffer.toString(),null);
			for(int i=0;i<page.getList().size();i++){
				Object [] objects=page.getList().get(i);
				Rank rank=new Rank();
				rank.setUsersId(objects[0]+"");
				rank.setMoney(objects[1]+"");//充值总金额
				if(!QwyUtil.isNullAndEmpty(objects[2])){
					rank.setUsersname((String) objects[2]);
				}
				if(!QwyUtil.isNullAndEmpty(objects[3])){
				rank.setRealname((String) objects[3]);
				}
				rankList.add(rank);
			}
			pageUtil.setList(rankList);
			return pageUtil;
		} catch (Exception e) {
			// TODO: handle exception
			log.error("操作异常: ",e);
		}
		return null;
	}
	
}
