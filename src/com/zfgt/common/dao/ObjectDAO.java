package com.zfgt.common.dao;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.transform.Transformers;
import org.hibernate.type.IntegerType;
import org.hibernate.type.Type;
import org.springframework.context.ApplicationContext;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

import com.zfgt.common.util.Page;
import com.zfgt.common.util.PageUtil;

/**
 * Data access object (DAO) for domain model class SystemPresent.
 * 
 * @see net.wwwyibu.orm.SystemPresent
 * @author MyEclipse - Hibernate Tools
 */
@Repository
public class ObjectDAO extends HibernateDaoSupport {

	private static final Logger log = Logger.getLogger(ObjectDAO.class);

	// property constants
	@Resource(name = "sessionFactory") // sessionFactory的注解
	public void setSuperSessionFactory(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}

	@Override
	protected void initDao() {
		// do nothing
	}

	/**
	 * 保存或者更新实体;
	 * 
	 * @param transientInstance
	 */
	public void saveOrUpdate(Object transientInstance) {
		log.debug("saving SystemPresent instance");
		try {
			getHibernateTemplate().saveOrUpdate(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public Object load(Object entity, Serializable id) {
		log.debug("saving SystemPresent instance");
		try {
			log.debug("save successful");
			return getHibernateTemplate().get(entity.getClass(), id);

		} catch (RuntimeException re) {
			log.error("save failed", re);

			re.printStackTrace();
			return null;
		}
	}

	public void saveList(final List list) {
		HibernateCallback selectCallback = new HibernateCallback() {
			public Object doInHibernate(Session session) {
				Transaction tx = session.beginTransaction();
				for (int i = 0; i < list.size(); i++) {

					session.saveOrUpdate(list.get(i));
					if (i % 50 == 0) // ��ÿ50�������Ϊһ�����?Ԫ
					{
						session.flush(); // ��������ݿ���ݵ�ͬ��
						session.clear(); // ����ڲ������ȫ����ݣ���ʱ�ͷų�ռ�õ��ڴ�
					}
				}
				tx.commit();
				session.close();
				return null;
			}
		};
		getHibernateTemplate().executeFind(selectCallback);
	}

	public void updateList(final List list) {
		HibernateCallback selectCallback = new HibernateCallback() {
			public Object doInHibernate(Session session) {
				Transaction tx = session.beginTransaction();
				for (int i = 0; i < list.size(); i++) {

					session.update(list.get(i));
					if (i % 50 == 0) // ��ÿ50�������Ϊһ�����?Ԫ
					{
						session.flush(); // ��������ݿ���ݵ�ͬ��
						session.clear(); // ����ڲ������ȫ����ݣ���ʱ�ͷų�ռ�õ��ڴ�
					}
				}
				tx.commit();
				session.close();
				return null;
			}
		};
		getHibernateTemplate().executeFind(selectCallback);
	}

	public void mergeList(final List list) {
		HibernateCallback selectCallback = new HibernateCallback() {
			public Object doInHibernate(Session session) {
				Transaction tx = session.beginTransaction();
				for (int i = 0; i < list.size(); i++) {

					session.merge(list.get(i));
					if (i % 50 == 0) // ��ÿ50�������Ϊһ�����?Ԫ
					{
						session.flush(); // ��������ݿ���ݵ�ͬ��
						session.clear(); // ����ڲ������ȫ����ݣ���ʱ�ͷų�ռ�õ��ڴ�
					}
				}
				tx.commit();
				session.close();
				return null;
			}
		};
		getHibernateTemplate().executeFind(selectCallback);
	}

	public String saveAndReturnId(Object transientInstance) {
		log.debug("saving SystemPresent instance");
		try {
			return getHibernateTemplate().save(transientInstance) + "";
		} catch (RuntimeException re) {
			log.error("saveAndReturnId failed", re);
			throw re;
		}
	}

	/**
	 * 保存实体
	 * 
	 * @param transientInstance
	 * @return
	 */
	public String save(Object transientInstance) {
		log.debug("saving SystemPresent instance");
		try {
			return getHibernateTemplate().save("ActivitiesCancel", transientInstance) + "";
			// log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	/**
	 * 删除实体;
	 * 
	 * @param persistentInstance
	 */
	public void delete(Object persistentInstance) {
		log.debug("deleting SystemPresent instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	/**
	 * 获取分页对象;
	 * 
	 * @param pageUtil
	 *            分页对象,填充分页显示的条数,和当前第几页;
	 * @param hql
	 *            hibernate 语句查询;
	 * @param o
	 *            分页查询的查询数据;跟问号"?"一一对应;
	 * @return
	 */
	public PageUtil getPage(PageUtil pageUtil, String hql, final Object[] o) {
		int startIndex, endIndex;
		int pageSize = pageUtil.getPageSize();
		pageUtil.setCount(this.findCountByHql(hql, o));
		List list = this.findAdvList(hql, o, pageUtil.getCurrentPage(), pageSize);
		pageUtil.setList(list);
		List listCount = new ArrayList();
		for (int i = 1; i <= pageUtil.getPageCount(); i++) {
			listCount.add(i);
		}
		pageUtil.setListCount(listCount);
		return pageUtil;
	}

	public PageUtil getByHqlAndHqlCount(PageUtil pageUtil, String hql, String hqlCount, final Object[] ob) {
		int pageSize = pageUtil.getPageSize();
		pageUtil.setCount(this.findCountByHql(hqlCount, ob));
		List list = this.findAdvList(hql, ob, pageUtil.getCurrentPage(), pageSize);
		pageUtil.setList(list);
		List listCount = new ArrayList();
		for (int i = 1; i <= pageUtil.getPageCount(); i++) {
			listCount.add(i);
		}
		pageUtil.setListCount(listCount);
		return pageUtil;
	}

	public PageUtil getByHqlAndHqlCount(PageUtil pageUtil, String hql, String hqlCount, final Object[] ob, final Object[] obCount) {
		int pageSize = pageUtil.getPageSize();
		pageUtil.setCount(this.findCountByHql(hqlCount, obCount));
		List list = this.findAdvList(hql, ob, pageUtil.getCurrentPage(), pageSize);
		pageUtil.setList(list);
		List listCount = new ArrayList();
		for (int i = 1; i <= pageUtil.getPageCount(); i++) {
			listCount.add(i);
		}
		pageUtil.setListCount(listCount);
		return pageUtil;
	}

	public PageUtil getBySqlAndSqlCount(PageUtil pageUtil, String sql, String sqlCount, final Object[] ob) {
		int pageSize = pageUtil.getPageSize();
		pageUtil.setCount(((Number) this.getSqlCount(sqlCount, ob)).intValue());
		List list = this.findAdvListSql(sql, ob, pageUtil.getCurrentPage(), pageSize);
		pageUtil.setList(list);
		List listCount = new ArrayList();
		for (int i = 1; i <= pageUtil.getPageCount(); i++) {
			listCount.add(i);
		}
		pageUtil.setListCount(listCount);
		return pageUtil;
	}

	/**
	 * 根据hibernate的机制,查询整表;<br>
	 * 
	 * @param object
	 *            映射ORM的实体;
	 * @return 返回一个List
	 */
	public List LoadAll(String object) {
		log.debug("deleting SystemPresent instance");
		try {
			List list = getHibernateTemplate().find("from " + object + " ob");
			log.debug("delete successful");
			return list;
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	/**
	 * 根据id查找实体;
	 * 
	 * @param object
	 *            映射ORM的实体;
	 * @param id
	 *            Long类型的id;
	 * @return 实体
	 */
	public Object findById(Object object, java.lang.Long id) {
		log.debug("getting SystemPresent instance with id: " + id);
		try {
			Object instance = getHibernateTemplate().get(object.getClass(), id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			return null;
		}
	}

	/**
	 * 根据id查找实体;
	 * 
	 * @param object
	 *            映射ORM的实体;
	 * @param id
	 *            String类型的id;
	 * @return 实体
	 */
	public Object findById(Object object, String id) {
		log.debug("getting SystemPresent instance with id: " + id);
		try {
			Object instance = getHibernateTemplate().get(object.getClass(), id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			return null;
		}
	}

	public List findByExample(Object instance) {
		log.debug("finding SystemPresent instance by example");
		try {
			List results = getHibernateTemplate().findByExample(instance);
			log.debug("find by example successful, result size: " + results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}

	public Object merge(Object detachedInstance) {
		log.debug("merging SystemPresent instance");
		try {
			Object result = getHibernateTemplate().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(Object instance) {
		log.debug("attaching dirty SystemPresent instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	/**
	 * 更新实体;
	 * 
	 * @param instance
	 *            映射ORM的实体;
	 */
	public void update(Object instance) {
		log.debug("attaching dirty SystemPresent instance");
		try {

			getHibernateTemplate().update(instance);
			getHibernateTemplate().flush();
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(Object instance) {
		log.debug("attaching clean SystemPresent instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	/**
	 * 使用Hql更新实体;
	 * 
	 * @param instance
	 * @param hql
	 */
	public void updateByHql(Object[] instance, String hql) {
		log.debug("attaching dirty SystemPresent instance");
		try {
			getHibernateTemplate().bulkUpdate(hql, instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	/**
	 * 根据hql语句来查询总数
	 * 
	 * @param hql
	 *            hql语句
	 * @param ob
	 * @return
	 */
	public int findCountByHql(String hql, final Object[] ob) {

		log.debug("findCountByHql");
		final String findMemberaAdvCou = "SELECT count(*) " + hql;
		try {
			List list = null;
			HibernateCallback selectCallback = new HibernateCallback() {
				public Object doInHibernate(Session session) {
					Query query = session.createQuery(findMemberaAdvCou);
					if (ob != null) {
						for (int i = 0; i < ob.length; i++)
							query.setParameter(i, ob[i]);
					}
					return query.list();
				}
			};
			list = getHibernateTemplate().executeFind(selectCallback);
			int count = 0;
			if (list != null && list.size() > 0 && list.get(0) != null)
				count = ((Number) list.get(0)).intValue();
			return count;
		} catch (DataAccessException e) {
			log.error("操作异常: ", e);
			return -1;
		}

	}

	/**
	 * 返回查找实体的唯一结果,结果大于1的时候,返回-1;
	 * 
	 * @param hql
	 *            hql语句;
	 * @param ob
	 *            参数;
	 * @return Object
	 */
	public Object findUniqueResult(final String hql, final Object[] ob) {
		try {
			HibernateCallback selectCallback = new HibernateCallback() {
				public Object doInHibernate(Session session) {
					Query query = session.createQuery(hql);
					// Query query = session.createSQLQuery(hql);
					if (ob != null) {
						for (int i = 0; i < ob.length; i++)
							query.setParameter(i, ob[i]);
					}
					return query.uniqueResult();
				}
			};
			Object list = getHibernateTemplate().execute(selectCallback);
			return list;
		} catch (DataAccessException e) {
			log.error("操作异常: ", e);
			return -1;
		}

	}

	/**
	 * 返回结果集第一条记录;如果结果集为null,则返回null;异常返回-1;
	 * 
	 * @param hql
	 *            hql语句;
	 * @param ob
	 *            参数;
	 * @return Object
	 */
	public Object findJoinActive(String hql, final Object[] ob) {
		final String findMemberaAdvCou = hql;
		try {
			List list = null;
			HibernateCallback selectCallback = new HibernateCallback() {
				public Object doInHibernate(Session session) {
					Query query = session.createQuery(findMemberaAdvCou);
					if (ob != null) {
						for (int i = 0; i < ob.length; i++)
							query.setParameter(i, ob[i]);
					}
					return query.list();
				}
			};
			list = getHibernateTemplate().executeFind(selectCallback);
			if (list != null && list.size() > 0) {
				return list.get(0);
			}
			return null;
		} catch (DataAccessException e) {
			logger.debug(e.getMessage(), e);
			log.error("操作异常: ", e);
			return -1;
		}

	}

	public List findAdvList(final String hql, final Object[] ob, final int page, final int length) {

		log.debug("findAdvList");

		try {
			List list = null;
			HibernateCallback selectCallback = new HibernateCallback() {
				public Object doInHibernate(Session session) {
					Query query = session.createQuery(hql);
					if (ob != null) {
						for (int i = 0; i < ob.length; i++)
							query.setParameter(i, ob[i]);
					}
					return query.setFirstResult((page - 1) * length).setMaxResults(length).list();
				}
			};
			list = getHibernateTemplate().executeFind(selectCallback);

			return list;
		} catch (DataAccessException e) {
			log.error("操作异常: ", e);
			return null;
		}

	}

	/**
	 * limt
	 * 
	 * @param hql
	 * @param ob
	 * @param start
	 *            开始
	 * @param end
	 *            结束
	 * @return
	 */
	public List findList(final String hql, final Object[] ob, final int start, final int end) {

		log.debug("findAdvList");

		try {
			List list = null;
			HibernateCallback selectCallback = new HibernateCallback() {
				public Object doInHibernate(Session session) {
					Query query = session.createQuery(hql);
					if (ob != null) {
						for (int i = 0; i < ob.length; i++)
							query.setParameter(i, ob[i]);
					}
					return query.setFirstResult(start).setMaxResults(end).list();
				}
			};
			list = getHibernateTemplate().executeFind(selectCallback);

			return list;
		} catch (DataAccessException e) {
			log.error("操作异常: ", e);
			return null;
		}

	}

	public List findAdvListSql(final String hql, final Object[] ob, final int page, final int length) {

		log.debug("findAdvListSql");

		try {
			List list = null;
			HibernateCallback selectCallback = new HibernateCallback() {
				public Object doInHibernate(Session session) {
					Query query = session.createSQLQuery(hql);
					if (ob != null) {
						for (int i = 0; i < ob.length; i++)
							query.setParameter(i, ob[i]);
					}
					return query.setFirstResult((page - 1) * length).setMaxResults(length).list();
				}
			};
			list = getHibernateTemplate().executeFind(selectCallback);

			return list;
		} catch (DataAccessException e) {
			log.error("操作异常: ", e);
			return null;
		}

	}

	/**
	 * 根据sql语句来查询count(*)
	 * 
	 * @param sql
	 *            原生的sql语句;
	 * @param ob
	 *            参数
	 * @return 结果集为null时,返回0; 异常返回-1;
	 */
	public Object getSqlCount(String sql, final Object[] ob) {
		final String findMemberaAdvCou = sql;
		try {
			List list = null;
			HibernateCallback selectCallback = new HibernateCallback() {
				public Object doInHibernate(Session session) {
					Query query = session.createSQLQuery(findMemberaAdvCou);
					if (ob != null) {
						for (int i = 0; i < ob.length; i++)
							query.setParameter(i, ob[i]);
					}
					return query.list();
				}
			};
			list = getHibernateTemplate().executeFind(selectCallback);
			if (list != null && list.size() > 0) {
				return list.get(0);
			}
			return 0;
		} catch (DataAccessException e) {
			log.error("操作异常: ", e);
			return -1;
		}

	}

	/**
	 * Prepared for sub-class for convenience. Query for list and also return
	 * total results' number.
	 * 
	 * @param selectCount
	 *            HQL for "select count(*) from ..." and should return a Long.
	 * @param select
	 *            HQL for "select * from ..." and should return object list.
	 * @param values
	 *            For prepared statements.
	 * @param page
	 *            Page object for store page information.
	 */
	protected List queryForList(final String selectCount, final String select, final Object[] values, final Page page) {
		// Integer intcout=(Integer)queryForObject(selectCount, values);
		Long count = ((Number) queryForObject(selectCount, values)).longValue();
		page.setTotalCount(count.intValue());
		if (page.isEmpty())
			return Collections.EMPTY_LIST;
		return queryForList(select, values, page);
	}

	public List queryForLists(final String selectCount, final String select, final Object[] values, final Page page) {
		// Integer intcout=(Integer)queryForObject(selectCount, values);
		Long count = ((Number) queryForObject(selectCount, values)).longValue();
		page.setTotalCount(count.intValue());
		if (page.isEmpty())
			return Collections.EMPTY_LIST;
		return queryForList(select, values, page);
	}

	public List queryForListsSql(final String selectCount, final String select, final Object[] values, final Page page) {
		// Integer intcout=(Integer)queryForObject(selectCount, values);
		Long count = ((Number) queryForObjectSql(selectCount, values)).longValue();
		page.setTotalCount(count.intValue());
		if (page.isEmpty())
			return Collections.EMPTY_LIST;
		return queryForListSql(select, values, page);
	}

	/**
	 * Prepared for sub-class for convenience. Query for list but do not return
	 * total results' number.
	 * 
	 * @param select
	 *            HQL for "select * from ..." and should return object list.
	 * @param values
	 *            For prepared statements.
	 * @param page
	 *            Page object for store page information.
	 */
	protected List queryForList(final String select, final Object[] values, final Page page) {
		// select:
		HibernateCallback selectCallback = new HibernateCallback() {
			public Object doInHibernate(Session session) {
				Query query = session.createQuery(select);
				if (values != null) {
					for (int i = 0; i < values.length; i++)
						query.setParameter(i, values[i]);
				}
				return query.setFirstResult(page.getFirstResult()).setMaxResults(page.getPageSize()).list();
			}
		};
		return getHibernateTemplate().executeFind(selectCallback);
	}

	protected List queryForListSql(final String select, final Object[] values, final Page page) {
		// select:
		HibernateCallback selectCallback = new HibernateCallback() {
			public Object doInHibernate(Session session) {
				Query query = session.createSQLQuery(select);
				if (values != null) {
					for (int i = 0; i < values.length; i++)
						query.setParameter(i, values[i]);
				}
				return query.setFirstResult(page.getFirstResult()).setMaxResults(page.getPageSize()).list();
			}
		};
		return getHibernateTemplate().executeFind(selectCallback);
	}

	/**
	 * Prepared for sub-class for convenience. Query for unique result.
	 * 
	 * @param select
	 *            HQL for "select * from ..." and should return unique object.
	 * @param values
	 *            For prepared statements.
	 */
	protected Object queryForObject(final String select, final Object[] values) {
		HibernateCallback selectCallback = new HibernateCallback() {
			public Object doInHibernate(Session session) {
				Query query = session.createQuery(select);
				if (values != null) {
					for (int i = 0; i < values.length; i++)
						query.setParameter(i, values[i]);
				}
				return query.uniqueResult();
			}
		};
		return getHibernateTemplate().execute(selectCallback);
	}

	public Object queryForObjectSql(final String select, final Object[] values) {
		HibernateCallback selectCallback = new HibernateCallback() {
			public Object doInHibernate(Session session) {
				Query query = session.createSQLQuery(select);
				if (values != null) {
					for (int i = 0; i < values.length; i++)
						query.setParameter(i, values[i]);
				}
				return query.uniqueResult();
			}
		};
		return getHibernateTemplate().execute(selectCallback);
	}

	public static ObjectDAO getFromApplicationContext(ApplicationContext ctx) {
		return (ObjectDAO) ctx.getBean("objectDAO");
	}

	/**
	 * 根据hql查找集合;
	 * 
	 * @param hql
	 *            hql语句;
	 * @param ob
	 *            参数;
	 * @return 返回一个list集合,根据需要,转换类型;
	 */
	public List LoadAll(String hql, Object[] ob) {
		return getHibernateTemplate().find(hql, ob);

	}

	/**
	 * 根据sql查找集合;
	 * 
	 * @param sql
	 *            sql语句;
	 * @param ob
	 *            参数;
	 * @return 返回一个list;
	 */
	public List LoadAllSql(final String sql, final Object[] ob) {

		log.debug("LoadAllSql");

		try {
			List list = null;
			HibernateCallback selectCallback = new HibernateCallback() {
				public Object doInHibernate(Session session) {
					Query query = session.createSQLQuery(sql);
					if (ob != null) {
						for (int i = 0; i < ob.length; i++)
							query.setParameter(i, ob[i]);
					}
					return query.list();
				}
			};
			list = getHibernateTemplate().executeFind(selectCallback);

			return list;
		} catch (DataAccessException e) {
			log.error("操作异常: ", e);
			return null;
		}

	}

	/**
	 * 根据sql语句更新;
	 * 
	 * @param sql
	 *            sql语句;
	 * @param o
	 *            参数
	 */
	public void updateBySql(final String sql, final Object[] o) {

		/*
		 * Session session=this.getSession(); Query
		 * query=session.createQuery(hql);
		 * 
		 * Transaction trans=session.beginTransaction(); int
		 * result=session.merge(arg0) System.out.println(result);
		 * trans.commit(); session.flush();
		 */

		HibernateCallback updateCallback = new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				PreparedStatement pst = session.connection().prepareStatement(sql);
				if (o != null && o.length != 0) {
					for (int i = 0; i < o.length; i++) {
						pst.setObject(i + 1, o[i]);
					}
				}

				pst.executeUpdate();

				return null;
			}

		};
		this.getHibernateTemplate().execute(updateCallback);
		this.getHibernateTemplate().flush();
	}

	/**
	 * 
	 * @param pageUtil
	 * @param sql
	 * @param sqlCount
	 * @param params
	 * @param paramsType
	 * @param o
	 * @return
	 */
	public PageUtil getPageBySQL(PageUtil pageUtil, final String sql, final String sqlCount, final String params[], final Type paramsType[], final Object[] o) {
		HibernateCallback callBackCount = new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				// TODO Auto-generated method stub
				SQLQuery query = session.createSQLQuery(sqlCount);
				query.addScalar("COUNT", new IntegerType());
				if (o != null && o.length != 0) {
					for (int i = 0; i < o.length; i++) {
						query.setParameter(i, o[i]);
					}
				}
				return query.uniqueResult();
			}

		};
		Object oCount = this.getHibernateTemplate().execute(callBackCount);
		int count = 0;
		if (oCount != null) {
			count = Integer.parseInt(oCount.toString());
		}
		final int pageSize = pageUtil.getPageSize();
		pageUtil.setCount(count);
		final int currrentPage = pageUtil.getCurrentPage();
		HibernateCallback callbackList = new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				// TODO Auto-generated method stub
				SQLQuery query = session.createSQLQuery(sql);
				if (params != null && params.length > 0) {
					for (int i = 0; i < params.length; i++) {
						query.addScalar(params[i], paramsType[i]);
					}
				}
				if (o != null && o.length > 0) {
					for (int i = 0; i < o.length; i++) {
						query.setParameter(i, o[i]);
					}
				}

				return query.setFirstResult((currrentPage - 1) * pageSize).setMaxResults(pageSize).list();
			}
		};
		List list = this.getHibernateTemplate().executeFind(callbackList);
		pageUtil.setList(list);
		return pageUtil;

	}

	/**
	 * 
	 * @param hql
	 * @param count
	 * @param o
	 * @return
	 */
	public List getByHqlAndCount(final String hql, final int count, final Object o[]) {
		HibernateCallback callback = new HibernateCallback() {

			@Override
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				// TODO Auto-generated method stub
				Query query = session.createQuery(hql);
				if (o != null && o.length > 0) {
					for (int i = 0; i < o.length; i++) {
						query.setParameter(i, o[i]);
					}
				}
				return query.setFirstResult(0).setMaxResults(count).list();
			}

		};
		return this.getHibernateTemplate().executeFind(callback);
	}

	/**
	 * 
	 * @param sql
	 * @param o
	 * @return
	 */
	public Object excuteSql(final String sql, final Object o[]) {
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session) throws SQLException {
				Query query = session.createSQLQuery(sql);
				if (o != null && o.length != 0) {
					for (int i = 0; i < o.length; i++) {
						query.setParameter(i, o[i]);
					}
				}
				int ret = query.executeUpdate();
				// session.connection().commit();// test
				return ret;
			}
		};
		return getHibernateTemplate().execute(callback);
	}

	/**
	 * 
	 * @param pageUtil
	 * @param hql
	 * @param hqlCount
	 * @param ob
	 * @return
	 */
	public PageUtil getListMapBySqlAndSqlCount(PageUtil pageUtil, String hql, String hqlCount, final Object[] ob) {
		int pageSize = pageUtil.getPageSize();
		pageUtil.setCount(((Number) this.getSqlCount(hqlCount, ob)).intValue());
		List list = this.findAdvListMapSql(hql, ob, pageUtil.getCurrentPage(), pageSize);
		pageUtil.setList(list);
		List listCount = new ArrayList();
		for (int i = 1; i <= pageUtil.getPageCount(); i++) {
			listCount.add(i);
		}
		pageUtil.setListCount(listCount);
		return pageUtil;
	}

	/**
	 * 
	 * @param sql
	 * @param ob
	 * @param page
	 * @param length
	 * @return
	 */
	public List findAdvListMapSql(final String sql, final Object[] ob, final int page, final int length) {
		try {
			List list = null;
			HibernateCallback selectCallback = new HibernateCallback() {
				public Object doInHibernate(Session session) {
					Query query = session.createSQLQuery(sql);
					if (ob != null) {
						for (int i = 0; i < ob.length; i++)
							query.setParameter(i, ob[i]);
					}
					return query.setFirstResult((page - 1) * length).setMaxResults(length).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
				}
			};
			list = getHibernateTemplate().executeFind(selectCallback);

			return list;
		} catch (DataAccessException e) {
			log.error("操作异常: ", e);
			return null;
		}
	}

	/**
	 * 
	 * @param sql
	 * @param ob
	 * @return
	 */
	public List LoadAllListMapSql(final String sql, final Object[] ob) {
		try {
			List list = null;
			HibernateCallback selectCallback = new HibernateCallback() {
				public Object doInHibernate(Session session) {
					Query query = session.createSQLQuery(sql);
					if (ob != null) {
						for (int i = 0; i < ob.length; i++)
							query.setParameter(i, ob[i]);
					}
					return query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
				}
			};
			list = getHibernateTemplate().executeFind(selectCallback);

			return list;
		} catch (DataAccessException e) {
			log.error("操作异常: ", e);
			return null;
		}

	}

	/**
	 * 
	 * @param hql
	 * @param ob
	 * @return
	 */
	public Object findObjectByHql(final String hql, final Object[] ob) {
		try {
			HibernateCallback selectCallback = new HibernateCallback() {
				public Object doInHibernate(Session session) {
					Query query = session.createQuery(hql);
					if (ob != null) {
						for (int i = 0; i < ob.length; i++)
							query.setParameter(i, ob[i]);
					}
					return query.uniqueResult();
				}
			};
			return getHibernateTemplate().execute(selectCallback);
		} catch (Exception e) {
			log.error("操作异常: ", e);
		}
		return -1;
	}
}