package com.edu.model;


import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Entity
@DynamicInsert(true)
@DynamicUpdate(true)
public class Classmate{
    private int classmateId;
    private Byte status = 1;
    private Float regularScore;
    private Float paperScore;
    private Float ultimateScore;
    private Course course;
    private Account account;
    private Clazz clazz;

    @Id
    @Column(name = "classmateID", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getClassmateId() {
        return classmateId;
    }

    public void setClassmateId(int classmateId) {
        this.classmateId = classmateId;
    }

    @Basic
    @Column(name = "status", nullable = true)
    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    @Basic
    @Column(name = "regularScore", nullable = true)
    public Float getRegularScore() {
        return regularScore;
    }

    public void setRegularScore(Float regularScore) {
        this.regularScore = regularScore;
    }

    @Basic
    @Column(name = "paperScore", nullable = true)
    public Float getPaperScore() {
        return paperScore;
    }

    public void setPaperScore(Float paperScore) {
        this.paperScore = paperScore;
    }


    @Basic
    @Column(name = "ultimateScore", nullable = true)
    public Float getUltimateScore() {
        return ultimateScore;
    }

    public void setUltimateScore(Float ultimateScore) {
        this.ultimateScore = ultimateScore;
    }


    @OneToOne
    @JoinColumn(name = "courseID", referencedColumnName = "courseID")
    public Course getCourse() {
        return course;
    }

    public void setCourse(Course courseByCourseId) {
        this.course = courseByCourseId;
    }

    @ManyToOne
    @JoinColumn(name = "userID", referencedColumnName = "userID")
    public Account getAccount() {
        return account;
    }

    public void setAccount(Account accountByUserId) {
        this.account = accountByUserId;
    }

    @ManyToOne
    @JoinColumn(name = "classID", referencedColumnName = "classID")
    public Clazz getClazz() {
        return clazz;
    }

    public void setClazz(Clazz clazzByClassId) {
        this.clazz = clazzByClassId;
    }

    @Override
    public String toString() {
        return "Classmate{" +
                "classmateId=" + classmateId +
                ", status=" + status +
                ", regularScore=" + regularScore +
                ", paperScore=" + paperScore +
                ", ultimateScore=" + ultimateScore +
                ", course=" + course +
                ", account=" + account +
                ", clazz=" + clazz +
                '}';
    }
}
