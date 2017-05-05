package com.edu.dao;

import java.util.List;

import com.edu.model.ChapterContent;
import com.edu.model.CourseChapter;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;

@Repository
public class ChapterContentDAO {
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

	public void save(ChapterContent chapterContent) {
		getSession().save(chapterContent);
	}

	public ChapterContent getChapterContentByChapterId(int chapterId) {
		String hql = "from ChapterContent where courseChapter.id=:Id";
		Query query = getSession().createQuery(hql);
		query.setInteger("Id",chapterId);
		return (ChapterContent) query.uniqueResult();
	}

	public ChapterContent getChapterContentById(int id) {
		return (ChapterContent) getSession().get(ChapterContent.class,id);
	}


	public void update(ChapterContent chapterContent) {
		getSession().update(chapterContent);
	}
}