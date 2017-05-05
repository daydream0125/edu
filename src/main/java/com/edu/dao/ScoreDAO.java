package com.edu.dao;

import java.util.List;

import com.edu.model.SubmitExercise;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

@Repository
public class ScoreDAO extends HibernateDaoSupport {
	private static final Log log = LogFactory.getLog(ScoreDAO.class);
	// property constants
	public static final String EXERCISE_ID = "exerciseId";
	public static final String SCORE = "score";


	//为HibernateDaoSupport注入sessionFactory
	@Autowired
	public void setSuperSessionFactory(SessionFactory sessionFactory){
		super.setSessionFactory(sessionFactory);
	}

	protected void initDao() {
		// do nothing
	}

	public void save(SubmitExercise transientInstance) {
		log.debug("saving SubmitExercise instance");
		try {
			getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(SubmitExercise persistentInstance) {
		log.debug("deleting SubmitExercise instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public SubmitExercise findById(Integer id) {
		log.debug("getting SubmitExercise instance with id: " + id);
		try {
			SubmitExercise instance = (SubmitExercise) getHibernateTemplate().get(
					"com.edu.model.SubmitExercise", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(SubmitExercise instance) {
		log.debug("finding SubmitExercise instance by example");
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
		log.debug("finding SubmitExercise instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from SubmitExercise as model where model."
					+ propertyName + "= ?";
			return getHibernateTemplate().find(queryString, value);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByExerciseId(Object exerciseId) {
		return findByProperty(EXERCISE_ID, exerciseId);
	}

	public List findByScore(Object score) {
		return findByProperty(SCORE, score);
	}

	public List findAll() {
		log.debug("finding all SubmitExercise instances");
		try {
			String queryString = "from SubmitExercise";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public SubmitExercise merge(SubmitExercise detachedInstance) {
		log.debug("merging SubmitExercise instance");
		try {
			SubmitExercise result = (SubmitExercise) getHibernateTemplate().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(SubmitExercise instance) {
		log.debug("attaching dirty SubmitExercise instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(SubmitExercise instance) {
		log.debug("attaching clean SubmitExercise instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static ScoreDAO getFromApplicationContext(ApplicationContext ctx) {
		return (ScoreDAO) ctx.getBean("ScoreDAO");
	}
}