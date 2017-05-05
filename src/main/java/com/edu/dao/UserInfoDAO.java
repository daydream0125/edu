package com.edu.dao;

import com.edu.model.UserInfo;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import javax.annotation.Resource;

@Repository
public class UserInfoDAO {
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

    public void save(UserInfo userInfo) {
        getSession().save(userInfo);
    }

    public UserInfo findByUserId(String userId) {
        return (UserInfo) getSession().get(UserInfo.class, userId);
    }

    public void update(UserInfo userInfo) {
        this.getSession().update(userInfo);
    }
}