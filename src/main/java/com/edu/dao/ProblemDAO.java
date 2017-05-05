package com.edu.dao;

import java.util.List;

import com.edu.model.Problem;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;

@Repository
public class ProblemDAO{
	private SessionFactory sessionFactory;


	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	@Resource
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	private Session getSession() {
		return sessionFactory.getCurrentSession();
	}


	public Problem getProblemById(int problemId) {
		return (Problem) getSession().get(Problem.class,problemId);
	}
	public void save(Problem problem) {
		getSession().save(problem);
	}


	public List getProblemBySectionId(int sectionId) {
		String hql = "from Problem where courseChapter.id=:id";
		Query query = getSession().createQuery(hql);
		query.setInteger("id", sectionId);
		return query.list();
	}

	public List getProblemsByCourseId(int courseId) {
		String hql = "from Problem where course.courseId=:id";
		Query query = getSession().createQuery(hql);
		query.setInteger("id", courseId);
		return query.list();
	}
	public List getProblemsByChapterId(int chapterId) {
		String hql = "from Problem where courseChapter.parentChapter.id=:id";
		Query query = getSession().createQuery(hql);
		query.setInteger("id", chapterId);
		return query.list();
	}


	public void update(Problem problem) {
		getSession().update(problem);
	}

	public void delete(int problemId) {
		Problem problem = (Problem) getSession().get(Problem.class,problemId);
		getSession().delete(problem);
	}


	public Long getProblemCountByChapterId(int chapterId) {
		String hql = "select count(*) from Problem where courseChapter.id=:cid";
		Query query = getSession().createQuery(hql);
		query.setInteger("cid",chapterId);
		return (Long) query.uniqueResult();
	}


}