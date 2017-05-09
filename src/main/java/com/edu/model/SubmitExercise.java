package com.edu.model;


import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.Date;

@Entity
@DynamicInsert(true)
@DynamicUpdate(true)
public class SubmitExercise {
    private int id;
    private Double score;
    private Exercise exercise;
    private Classmate classmate;
    private Date submitTime;
    private Date judgeObjectiveTime;
    private Date judgeSubjectiveTime;
    private String submitIP;
    private Boolean judgeObjective = false;
    private Boolean judgeSubjective = false;
    private Boolean countScore = false;

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getId() {
        return id;
    }

    public void setId(int scoreId) {
        this.id = scoreId;
    }


    @Basic
    @Column(name = "score", nullable = true, precision = 0)
    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    @Basic
    @Column(name = "submitTime")
    @JsonFormat(pattern = "yyyy:mm:dd HH:mm:ss")
    public Date getSubmitTime() {
        return submitTime;
    }

    public void setSubmitTime(Date submitTime) {
        this.submitTime = submitTime;
    }




    @Basic
    @Column(name = "submitIP")
    public String getSubmitIP() {
        return submitIP;
    }

    public void setSubmitIP(String submitIP) {
        this.submitIP = submitIP;
    }

    @Basic
    @Column(name="judgeObjectiveTime")
    @JsonFormat(pattern = "yyyy:mm:dd HH:mm:ss")
    public Date getJudgeObjectiveTime() {
        return judgeObjectiveTime;
    }

    public void setJudgeObjectiveTime(Date judgeObjectiveTime) {
        this.judgeObjectiveTime = judgeObjectiveTime;
    }

    @Basic
    @Column(name="judgeSubjectiveTime")
    @JsonFormat(pattern = "yyyy:mm:dd HH:mm:ss")
    public Date getJudgeSubjectiveTime() {
        return judgeSubjectiveTime;
    }

    public void setJudgeSubjectiveTime(Date judgeSubjectiveTime) {
        this.judgeSubjectiveTime = judgeSubjectiveTime;
    }

    @Basic
    @Column(name="judgeObjective")
    public Boolean getJudgeObjective() {
        return judgeObjective;
    }

    public void setJudgeObjective(Boolean judgeObjective) {
        this.judgeObjective = judgeObjective;
    }
    @Basic
    @Column(name="judgeSubjective")
    public Boolean getJudgeSubjective() {
        return judgeSubjective;
    }

    public void setJudgeSubjective(Boolean judgeSubjective) {
        this.judgeSubjective = judgeSubjective;
    }


    @Basic
    @Column(name="countScore")
    public Boolean getCountScore() {
        return countScore;
    }

    public void setCountScore(Boolean countScore) {
        this.countScore = countScore;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SubmitExercise score1 = (SubmitExercise) o;

        if (id != score1.id) return false;
        if (exercise != null ? !exercise.equals(score1.exercise) : score1.exercise != null) return false;
        if (score != null ? !score.equals(score1.score) : score1.score != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (exercise != null ? exercise.hashCode() : 0);
        result = 31 * result + (score != null ? score.hashCode() : 0);
        return result;
    }

    @ManyToOne
    @JoinColumn(name = "exerciseID", referencedColumnName = "exerciseID")
    public Exercise getExercise() {
        return exercise;
    }

    public void setExercise(Exercise exercise) {
        this.exercise = exercise;
    }

    @ManyToOne
    @JoinColumn(name = "classmateID")
    public Classmate getClassmate() {
        return classmate;
    }

    public void setClassmate(Classmate classmate) {
        this.classmate = classmate;
    }

}
