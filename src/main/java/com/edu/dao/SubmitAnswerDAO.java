package com.edu.dao;

import java.util.List;

import com.edu.model.SubmitAnswer;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;

@Repository
public class SubmitAnswerDAO {
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

    public void save(SubmitAnswer submitAnswer) {
        getSession().save(submitAnswer);
    }


    public List getObjectiveAnswer(int exerciseId) {
        String hql = "from SubmitAnswer where exercise.exerciseId = ? and problem.isManualJudge=?";
        Query query = getSession().createQuery(hql);
        query.setInteger(0, exerciseId);
        query.setBoolean(1,true);
        return query.list();
    }

    public void update(SubmitAnswer answer) {
        getSession().update(answer);
    }


    //取出某一学生的主观题
    public List getAnswersByClassmateAndExercise(int exerciseId, int classmateId) {
        String hql = "from SubmitAnswer where exercise.exerciseId=? and classmate.classmateId=? and problem.isManualJudge = ?";
        Query query = getSession().createQuery(hql);
        query.setInteger(0, exerciseId);
        query.setInteger(1, classmateId);
        query.setBoolean(2, false);
        return query.list();
    }

    public List getObjectiveResults(int exerciseId, int classmateId) {
        String hql = "select result from SubmitAnswer where exercise.exerciseId=? and classmate.classmateId=? and problem.isManualJudge=?";
        Query query = getSession().createQuery(hql);
        query.setInteger(0, exerciseId);
        query.setInteger(1, classmateId);
        query.setBoolean(2,true);
        return query.list();
    }

    public SubmitAnswer get(int id) {
        return (SubmitAnswer) getSession().get(SubmitAnswer.class,id);
    }
}