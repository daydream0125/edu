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
        query.setBoolean(1, true);
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
        query.setBoolean(2, true);
        return query.list();
    }

    public SubmitAnswer get(int id) {
        return (SubmitAnswer) getSession().get(SubmitAnswer.class, id);
    }

    public List getScoresByProblemId(int problemId) {
        String hql = "select result from SubmitAnswer s where s.problem.problemId=?";
        Query query = getSession().createQuery(hql);
        query.setInteger(0, problemId);
        return query.list();
    }

    public List getClassmateScore(int exerciseId, int classmateId) {
        String hql = "select s.result,time_to_sec(timediff(s.endTime,s.startTime)),s.problem.problemId from SubmitAnswer s where s.exercise.exerciseId=? and s.classmate.classmateId=? order by s.problem.problemId";
        return getSession().createQuery(hql).setInteger(0, exerciseId).setInteger(1, classmateId).list();
    }


    /**
     * 查询某一练习下特定题目的平均值（平均时间、平均分数）
     *
     * @param exerciseId
     * @param problemId
     * @return
     */

    public List getAverageInExercise(int exerciseId, int problemId) {
        return getSession().createQuery("select avg(s.result),avg(time_to_sec(timediff(s.endTime,s.startTime))) from SubmitAnswer s " +
                "where s.exercise.exerciseId = ? and s.problem.problemId = ? order by s.problem.problemId").setInteger(0, exerciseId).setInteger(1, problemId).list();
    }


    /**
     * 查询特定题目 特定分数的数量
     *
     * @param problemId
     * @param result
     * @return
     */
    public Long getProblemScoreCount(int problemId, double result) {
        return (Long) getSession().createQuery("select count(*) from SubmitAnswer where problem.problemId=? and result=?")
                .setInteger(0, problemId).setDouble(1, result).uniqueResult();
    }

    //todo 练习名称太长，前台不易显示怎么办？？？

    /**
     * 取出某题在各个练习中的平均分数和平均时间
     * 返回 0-平均分数 1-平均时间 2-练习名称
     *
     * @param problemId
     * @return
     */
    public List getAverageProblemScoreInExercise(int problemId) {
        return getSession().createQuery("select avg(s.result),avg(time_to_sec(timediff(s.endTime,s.startTime)))," +
                "s.exercise.exerciseName " +
                "from SubmitAnswer s where s.problem.problemId=? group by s.exercise.exerciseId")
                .setInteger(0, problemId).list();
    }

    public List getProblemAverage(int problemId) {
        return getSession().createQuery("select avg(s.result),avg(time_to_sec(timediff(s.endTime,s.startTime))) from SubmitAnswer s where problem.problemId=?")
                .setInteger(0, problemId).list();
    }

    public List getAllProblemScore(int problemId) {
        return getSession().createQuery("select s.result from SubmitAnswer s where  s.problem.problemId=? order by s.startTime")
                .setInteger(0, problemId)
                .list();
    }

    public List getAnswersByUserAndExercise(int exerciseId, String userId) {
        return getSession()
                .createQuery("from SubmitAnswer where exercise.exerciseId=? and classmate.account.userId=?")
                .setInteger(0,exerciseId)
                .setString(1,userId)
                .list();
    }
}