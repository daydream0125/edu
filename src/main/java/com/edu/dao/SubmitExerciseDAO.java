package com.edu.dao;

import com.edu.model.SubmitExercise;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

@Repository
public class SubmitExerciseDAO {
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

    public void save(SubmitExercise submitExercise) {
        getSession().save(submitExercise);
    }

    public boolean checkIsSubmit(String userId, int exerciseId) {
        String hql = "select count(*) from SubmitExercise where classmate.account.userId=? and exercise.exerciseId=?";
        Query query = getSession().createQuery(hql);
        query.setString(0, userId);
        query.setInteger(1, exerciseId);
        return (Long) query.uniqueResult() > 0;
    }

    public Long getSubmitCount(int exerciseId) {
        String hql = "select count(*) from SubmitExercise where exercise.exerciseId=?";
        Query query = getSession().createQuery(hql);
        query.setInteger(0, exerciseId);
        return (Long) query.uniqueResult();

    }

    //todo 有关已经批改的作业数不能简单的如此统计
    public Long getJudgeCount(int exerciseId) {
        String hql = "select count(*) from SubmitExercise where exercise.exerciseId=? and judgeObjective = ?";
        Query query = getSession().createQuery(hql);
        query.setInteger(0, exerciseId);
        query.setBoolean(1, true);
        return (Long) query.uniqueResult();
    }

    public SubmitExercise get(int exerciseId, int classmateId) {
        String hql = "from SubmitExercise where exercise.exerciseId = ? and classmate.classmateId=?";
        Query query = getSession().createQuery(hql);
        query.setInteger(0, exerciseId);
        query.setInteger(1, classmateId);
        return (SubmitExercise) query.uniqueResult();
    }

    public List get(int exerciseId) {
        Query query = getSession().createQuery("from SubmitExercise where exercise.exerciseId=?");
        query.setInteger(0, exerciseId);
        return query.list();
    }

    public void update(SubmitExercise submitExercise) {
        getSession().update(submitExercise);
    }

    public List getClassmatesByExerciseId(int exerciseId) {
        String hql = "select classmate from SubmitExercise s where s.exercise.exerciseId=?";
        Query query = getSession().createQuery(hql);
        query.setInteger(0, exerciseId);
        return query.list();
    }

    //todo 目前做法是先根据submitExercise中的judgeSubjective拿出未批改主观题的学生，需要改进（前台只显示存在题目未被修改的学生，而不是显示所有学生）
    //根据exerciseId拿出存在题目未被批改的学生
    public List getExistUnMarkProblemClassmates(int exerciseId) {
        String hql = "select classmate from SubmitExercise e where e.exercise.exerciseId=? " +
                "and e.judgeSubjective=?";
        Query query = getSession().createQuery(hql);
        query.setInteger(0,exerciseId);
        query.setBoolean(1,false);
        return query.list();
    }

    public SubmitExercise getByExerciseAndClassmate(int exerciseId,int classmateId) {
        String hql = "from SubmitExercise where exercise.exerciseId=? and classmate.classmateId=?";
        Query query = getSession().createQuery(hql);
        query.setInteger(0,exerciseId);
        query.setInteger(1,classmateId);
        return (SubmitExercise) query.uniqueResult();
    }

    public List getExerciseScores(int exerciseId) {
        String hql = "select s.classmate.account.name,s.score from SubmitExercise s where s.exercise.exerciseId=? order by s.score";
        Query query = getSession().createQuery(hql);
        query.setInteger(0,exerciseId);
        return query.list();

    }


    /**
     * 与上面的方法相比，该方法只返回classmateId，name 字段(前段获取数据慢，卡！！！！！)
     * @param exerciseId
     * @return list
     */
    public List getClassmates(int exerciseId) {
        String hql = "select s.classmate.classmateId, s.classmate.account.name from SubmitExercise s where s.exercise.exerciseId=?";
        return getSession().createQuery(hql).setInteger(0,exerciseId).list();
    }


    /**
     * 根据用户名和练习名获得某一用户在某一练习下的成绩
     * @param exerciseId
     * @param userId
     * @return
     */
    public Double getExerciseScoreByUser(int exerciseId, String userId) {
         return (Double) getSession()
                 .createQuery("select s.score from SubmitExercise s where s.exercise.exerciseId=?" +
                         "and s.classmate.account.userId=?")
                 .setInteger(0,exerciseId)
                 .setString(1,userId).uniqueResult();

    }


    /**
     * 根据已提交的记录，从 exerciseID和 userID 中筛选出 classmateId。该 id 唯一，即每个 classmate 只
     * 允许提交一次。
     * @param exerciseId
     * @param userId
     * @return
     */
    public int getClassmateByExerciseAndUser(int exerciseId, String userId) {
        return (int) getSession()
                .createQuery("select s.classmate.classmateId from SubmitExercise s " +
                        "where s.exercise.exerciseId=? and s.classmate.account.userId=?")
                .setInteger(0,exerciseId)
                .setString(1,userId).uniqueResult();
    }

    public SubmitExercise getByExerciseAndUser(int exerciseId, String userId) {
        return (SubmitExercise) getSession().
                createQuery("from SubmitExercise where exercise.exerciseId=? and classmate.account.userId=?")
                .setInteger(0,exerciseId)
                .setString(1,userId)
                .uniqueResult();
    }
}