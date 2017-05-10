package com.edu.dao;

import com.edu.model.Log;
import com.edu.utils.Page;
import com.edu.utils.PageUtil;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;


@Repository
public class LogDAO {
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

    public void save(Log log) {
        getSession().save(log);
    }

    public List getAllLog() {
        String hql = "from Log";
        Query query = getSession().createQuery(hql);
        return query.list();
    }

    public List getLogs(Page page) {
        return getSession().createQuery("from Log order by operTime desc")
                .setFirstResult(PageUtil.getFirst(page))
                .setMaxResults(page.getPageSize()).list();
    }

    public Long getLogsCount() {
        return (Long) getSession().createQuery("select count(*) from Log").uniqueResult();
    }

}
