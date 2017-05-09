package com.edu.dao;


import com.edu.model.Course;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

@Repository
public class CourseDAO{
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

	public void save(Course course) {
		getSession().save(course);
	}


	//依外键teacherID在course中寻找特定老师开设的所有课程
	public List findCoursesByTeacherId(String teacherId) {
		String hql = "from Course where teacher.userId =:Id";
		Query query = getSession().createQuery(hql);
		query.setString("Id",teacherId);
		return query.list();
	}


	public Course getCourseById(int Id) {
		return (Course) getSession().get(Course.class,Id);
	}

	//更新课程信息，设置外键
	public void updateCourse(Course course) {
		Course c = (Course) getSession().get(Course.class,course.getCourseId());
		course.setTeacher(c.getTeacher());
		//清除取出的对象 c，避免造成相同主键的对象同时存在。
		getSession().evict(c);
		getSession().update(course);
	}

	public List getAllCourse() {
		String hql = "select c.courseId,c.courseName,c.courseDescription,c.coursePic from Course c order by c.isFinish,c.startTime";
		Query query = getSession().createQuery(hql);
		return query.list();
	}


	public String getCourseName(int courseId) {
		return getCourseById(courseId).getCourseName();
	}

	public List getSharpCourse(int courseId) {
		return  getSession()
				.createQuery("select c.courseName,c.courseDescription from Course c" +
						" where c.courseId=?")
				.setInteger(0,courseId)
				.list();
	}
}