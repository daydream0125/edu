package com.edu.dao;

import java.util.List;

import com.edu.model.Account;
import com.edu.model.Clazz;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;

@Repository
public class ClazzDAO {
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

	public void save(Clazz clazz) {
		getSession().save(clazz);
	}

	//获取特定课程下所开设的班级
	public List getClazzByCourseId(int courseId) {
		String hql = "from Clazz where course.courseId =:Id";
		Query query = getSession().createQuery(hql);
		query.setInteger("Id",courseId);
		return query.list();
	}

	//更新isPublicRegister字段。开放注册 or 关闭注册
	public void updateIsPublicRegister(int classId,Boolean isPublicRegister) {
		Clazz clazz = (Clazz) getSession().get(Clazz.class,classId);
		clazz.setIsPublicRegister(isPublicRegister);
		getSession().saveOrUpdate(clazz);
	}

	public Clazz get(int clazzId) {
		return (Clazz) getSession().get(Clazz.class,clazzId);
	}
}