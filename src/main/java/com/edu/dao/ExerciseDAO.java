package com.edu.dao;

import com.edu.model.Exercise;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

@Repository
public class ExerciseDAO  {
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

	public Exercise getById(int exerciseId) {
		return (Exercise) getSession().get(Exercise.class,exerciseId);
	}

	public void save(Exercise exercise) {
		getSession().save(exercise);
	}

	public void delete(int exerciseId) {
		getSession().delete(getById(exerciseId));
	}

	public void update(Exercise exercise) {
		getSession().update(exercise);
	}

	public List getExerciseByTeacherId(String teacherId) {
		String hql = "from Exercise where teacher.userId=:id order by startTime desc";
		Query query = getSession().createQuery(hql);
		query.setString("id",teacherId);
		return query.list();
	}

	public void updateIsRelease(int exerciseId) {
		String hql = "update Exercise set isRelease=true where exerciseId=?";
		Query query = getSession().createQuery(hql);
		query.setInteger(0,exerciseId);
		query.executeUpdate();
	}

	public List getExercisesByUserId(String userId) {
		String hql = "from Exercise where isRelease=? and clazz.classId in (select clazz.classId from Classmate where account.userId=?)";
		Query query = getSession().createQuery(hql);
		query.setBoolean(0,true);
		query.setString(1,userId);
		return query.list();

	}

	/**
	 * 获取题目信息，包括ID，title，type，description，problemPicPath
	 * @param exerciseId
	 * @return
	 */
	public List getProlems(int exerciseId) {
		String hql = "select p.id,p.title,p.type,p.description,p.problemPicPath from Exercise e,Problem p where e.exerciseId=? and p in elements(e.problems)";
		Query query = getSession().createQuery(hql);
		query.setInteger(0,exerciseId);
		return query.list();
	}
	public Long getProblemCount(int exerciseId) {
		String hql = "select count(*) from Exercise  e,Problem p  where e.exerciseId=:id and p in elements(e.problems)";
		Query query = getSession().createQuery(hql);
		query.setInteger("id",exerciseId);
		return (Long) query.uniqueResult();
	}

	public boolean getJudge(int exerciseId) {
		return (boolean) getSession().createQuery("select judge from Exercise e where e.exerciseId=?").setInteger(0,exerciseId).uniqueResult();
	}

	public List getSharpExercise(int exerciseId) {
		return getSession()
				.createQuery("select e.exerciseName from Exercise  e where e.exerciseId=?")
				.setInteger(0,exerciseId)
				.list();
	}

	public boolean existFinalExercise(int classId) {
		return (Long) getSession()
				.createQuery("select count(*) from Exercise  e where e.clazz.classId=? and e.isFinal = true")
				.setInteger(0,classId)
				.uniqueResult() != 0;
	}
}