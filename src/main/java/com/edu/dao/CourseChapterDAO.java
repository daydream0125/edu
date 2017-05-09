package com.edu.dao;

import com.edu.model.CourseChapter;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;


@Repository
public class CourseChapterDAO {
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

    public List getChapterByCourseId(int courseId) {
        String hql = "from CourseChapter where course.courseId =:Id";
        Query query = getSession().createQuery(hql);
        query.setInteger("Id", courseId);
        return query.list();
    }

    public CourseChapter getChapterById(int id) {
        return (CourseChapter) getSession().get(CourseChapter.class, id);
    }


    //根据 courseId 获取章信息，进而获得节信息
    public List getAllChapterByCourseId(int courseId) {
        String hql = "from CourseChapter where course.courseId=:cid and parentChapter.id=null order by num";
        Query query = getSession().createQuery(hql);
        query.setInteger("cid", courseId);
        return query.list();
    }

    public List getSectionsByChapterId(int chapterId) {
        String hql = "from CourseChapter where parentChapter.id=? order by num";
        Query query = getSession().createQuery(hql);
        query.setInteger(0,chapterId);
        return query.list();
    }


    public void save(CourseChapter courseChapter) {
        getSession().save(courseChapter);
    }

    public void update(CourseChapter courseChapter) {
        getSession().update(courseChapter);
    }


    //模糊查询，%%为匹配任意的中文
    public List searchByChapterTitle(String chapterTitle) {
        String hql = "select cc.title,c.course.courseName from CourseChapter c where c.title like :t";
        Query query = getSession().createQuery(hql);
        query.setString("t","%"+chapterTitle+"%");
        return query.list();
    }

    public int getCourseIdByChapterId(int chapterId) {
        return getChapterById(chapterId).getCourse().getCourseId();
    }

}