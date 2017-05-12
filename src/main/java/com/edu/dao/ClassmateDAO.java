package com.edu.dao;


import com.edu.model.Classmate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

@Repository
public class ClassmateDAO {
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


    public void save(Classmate classmate) {
        getSession().save(classmate);
    }

    public boolean isRegister(int clazzId, String userId) {
        String hql = "from Classmate where account.userId=:userId and clazz.classId=:classId";
        Query query = getSession().createQuery(hql);
        query.setString("userId", userId);
        query.setInteger("classId", clazzId);
        return query.uniqueResult() != null;
    }

    public Classmate get(int id) {
        return (Classmate) getSession().get(Classmate.class, id);
    }

    public void update(Classmate classmate) {
        getSession().update(classmate);
    }


    public List getClazzByUserId(String userId) {
        String hql = "from Classmate where account.userId=:id";
        Query query = getSession().createQuery(hql);
        query.setString("id", userId);
        return query.list();
    }

    public long getClassmatesCount(int classId) {
        String hql = "select count(*) from Classmate where clazz.classId=:id";
        Query query = getSession().createQuery(hql);
        query.setInteger("id", classId);
        return (long) query.uniqueResult();
    }

    public List getClassmates(int classId) {
        String hql = "from Classmate where clazz.classId=:id";
        Query query = getSession().createQuery(hql);
        query.setInteger("id", classId);
        return query.list();
    }

    public long getStudentsNumByCourseId(int courseId) {
        String hql = "select count(*) from Classmate where course.courseId=?";
        Query query = getSession().createQuery(hql);
        query.setInteger(0, courseId);
        return (long) query.uniqueResult();
    }

    //取出等待审核的学生
    public List getRegisters(int classId) {
        String hql = "from Classmate where clazz.classId=? and status=1";
        Query query = getSession().createQuery(hql);
        query.setInteger(0, classId);
        return query.list();
    }

    public void approveRegisters(int[] approveRegisters) {
        for (int id : approveRegisters) {
            Classmate classmate = this.get(id);
            if (classmate != null) {
                //通过审核
                classmate.setStatus((byte) 2);
                this.update(classmate);
            }
        }
    }

    //根据 exerciseId 和 userId 获取 classmate。
    /*
        因为每一 exercise 都是针对某一个班级的，依据 exerciseId 选择出班级，然后依据 userId 选择出 classmate
	 */
    public Classmate getClassmateByExerciseAndUser(int exerciseId, String userId) {
        String hql = "from Classmate where clazz.classId in (select clazz.classId from Exercise where exerciseId=?) and account.userId=?";
        Query query = getSession().createQuery(hql);
        query.setInteger(0, exerciseId);
        query.setString(1, userId);
        return (Classmate) query.uniqueResult();
    }


    /**
     * 计算每位同学的成绩
     *
     * @param classId
     */
    public void countScore(int classId) {
        Long exerciseCount = (Long) getSession().createQuery("select count(*) from Exercise e where e.clazz.id=? and e.isFinal = false")
                .setInteger(0, classId)
                .uniqueResult();
        System.out.println(exerciseCount);
        getSession()
                .createQuery("update Classmate c set c.regularScore = " +
                        "(select sum(e1.score) from SubmitExercise e1 where e1.exercise.isFinal = false and e1.classmate.id=c.id group by e1.exercise.clazz.id " +
                        "having  e1.exercise.clazz.id=?) " +
                        ", c.paperScore = (select score from SubmitExercise e2 where e2.exercise.clazz.id=? and " +
                        "e2.exercise.isFinal = true and e2.classmate.id=c.id) " +
                        "where c.clazz.id=?").setInteger(0, classId).setInteger(1, classId).setInteger(2, classId).executeUpdate();
        getSession()
                .createQuery("update Classmate c set c.regularScore = c.regularScore / ? where c.clazz.id=?")
                .setLong(0, exerciseCount)
                .setInteger(1, classId)
                .executeUpdate();

        List scorePercent = getSession().createQuery("select scorePercent from Clazz  where classId=?")
                .setInteger(0, classId)
                .list();
        System.out.println(scorePercent.get(0));
        getSession().createQuery("update Classmate c set c.ultimateScore = c.regularScore * ? / 10 " +
                "+ c.paperScore * (10 -?) / 10 where c.clazz=?")
                .setInteger(0, (Integer) scorePercent.get(0))
                .setInteger(1, (Integer) scorePercent.get(0))
                .setInteger(2, classId)
                .executeUpdate();
    }


    public List getClassScores(int classId) {
        return getSession()
                .createQuery("select c.account.name,c.regularScore,c.paperScore,c.ultimateScore from Classmate c " +
                        "where c.clazz.id=? order by c.ultimateScore")
                .setInteger(0, classId)
                .list();
    }


}