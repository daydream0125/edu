package com.edu.dao;


import com.edu.model.Account;
import com.edu.model.Notice;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;


@Repository
public class NoticeDAO {
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

    public List getAllNotice() {
        return getSession().createQuery("select n.id,n.title,n.issueTime from Notice n order by n.issueTime desc").list();
    }

    public void save(Notice notice) {
        getSession().save(notice);
    }

    public List get(int id) {
        return  getSession()
                .createQuery("select n.title,n.issueTime,n.content,n.account.name from Notice n " +
                        "where n.id=?")
                .setInteger(0,id)
                .list();
    }
    public void delete(int id) {
        getSession().createQuery("delete from Notice where id=?")
                .setInteger(0,id)
                .executeUpdate();
    }

    public void update(Notice notice) {
        getSession().update(notice);
    }



}