package com.edu.dao;

import java.util.List;

import com.edu.model.AreaInfo;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

@Repository
public class AreaInfoDAO extends HibernateDaoSupport {
	private static final Log log = LogFactory.getLog(AreaInfoDAO.class);
	// property constants
	public static final String AREA_NAME = "areaName";
	public static final String PARENT_ID = "parentId";

	//为HibernateDaoSupport注入sessionFactory
	@Autowired
	public void setSuperSessionFactory(SessionFactory sessionFactory){
		super.setSessionFactory(sessionFactory);
	}
	protected void initDao() {
		// do nothing
	}

	public void save(AreaInfo transientInstance) {
		log.debug("saving AreaInfo instance");
		try {
			getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(AreaInfo persistentInstance) {
		log.debug("deleting AreaInfo instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public AreaInfo findById(Integer id) {
		log.debug("getting AreaInfo instance with id: " + id);
		try {
			AreaInfo instance = (AreaInfo) getHibernateTemplate().get(
					"com.edu.model.AreaInfo", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(AreaInfo instance) {
		log.debug("finding AreaInfo instance by example");
		try {
			List results = getHibernateTemplate().findByExample(instance);
			log.debug("find by example successful, result size: "
					+ results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}

	public List findByProperty(String propertyName, Object value) {
		log.debug("finding AreaInfo instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from AreaInfo as model where model."
					+ propertyName + "= ?";
			return getHibernateTemplate().find(queryString, value);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByAreaName(Object areaName) {
		return findByProperty(AREA_NAME, areaName);
	}

	public List findByParentId(Object parentId) {
		return findByProperty(PARENT_ID, parentId);
	}

	public List findAll() {
		log.debug("finding all AreaInfo instances");
		try {
			String queryString = "from AreaInfo";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public AreaInfo merge(AreaInfo detachedInstance) {
		log.debug("merging AreaInfo instance");
		try {
			AreaInfo result = (AreaInfo) getHibernateTemplate().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(AreaInfo instance) {
		log.debug("attaching dirty AreaInfo instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(AreaInfo instance) {
		log.debug("attaching clean AreaInfo instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static AreaInfoDAO getFromApplicationContext(ApplicationContext ctx) {
		return (AreaInfoDAO) ctx.getBean("AreaInfoDAO");
	}
}