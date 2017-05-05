package com.edu.model;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sun.org.apache.xpath.internal.operations.Bool;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.Date;


@Entity
@DynamicInsert(true)
@DynamicUpdate(true)
public class SubmitAnswer {
    private int answerId;
    private String answer;
    private String submitIp;
    private Date startTime;
    private Date endTime;
    private Double result;
    private String resultex;
    private String answerPic;
    private Boolean judge;
    private Problem problem;
    private Classmate classmate;
    private Exercise exercise;

    @Id
    @Column(name = "answerID", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    public int getAnswerId() {
        return answerId;
    }

    public void setAnswerId(int answerId) {
        this.answerId = answerId;
    }

    @Basic
    @Column(name = "answer", nullable = true, length = 2147483647)
    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    @Basic
    @Column(name="judge")
    public Boolean getJudge() {
        return judge;
    }

    public void setJudge(Boolean judge) {
        this.judge = judge;
    }

    @Basic
    @Column(name = "submitIP", nullable = true, length = 64)
    public String getSubmitIp() {
        return submitIp;
    }

    public void setSubmitIp(String submitIp) {
        this.submitIp = submitIp;
    }

    @Basic
    @Column(name = "startTime", nullable = true)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    @Basic
    @Column(name = "endTime", nullable = true)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    @Basic
    @Column(name = "result", nullable = true, precision = 0)
    public Double getResult() {
        return result;
    }

    public void setResult(Double result) {
        this.result = result;
    }

    @Basic
    @Column(name = "resultex", nullable = true, length = 2147483647)
    public String getResultex() {
        return resultex;
    }

    public void setResultex(String resultex) {
        this.resultex = resultex;
    }


    @Basic
    @Column(name = "answerPic")
    public String getAnswerPic() {
        return answerPic;
    }

    public void setAnswerPic(String answerPic) {
        this.answerPic = answerPic;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SubmitAnswer that = (SubmitAnswer) o;

        if (answerId != that.answerId) return false;
        if (answer != null ? !answer.equals(that.answer) : that.answer != null) return false;
        if (submitIp != null ? !submitIp.equals(that.submitIp) : that.submitIp != null) return false;
        if (startTime != null ? !startTime.equals(that.startTime) : that.startTime != null) return false;
        if (endTime != null ? !endTime.equals(that.endTime) : that.endTime != null) return false;
        if (result != null ? !result.equals(that.result) : that.result != null) return false;
        if (resultex != null ? !resultex.equals(that.resultex) : that.resultex != null) return false;
        return true;
    }

    @Override
    public int hashCode() {
        int result1 = answerId;
        result1 = 31 * result1 + (answer != null ? answer.hashCode() : 0);
        result1 = 31 * result1 + (submitIp != null ? submitIp.hashCode() : 0);
        result1 = 31 * result1 + (startTime != null ? startTime.hashCode() : 0);
        result1 = 31 * result1 + (endTime != null ? endTime.hashCode() : 0);
        result1 = 31 * result1 + (result != null ? result.hashCode() : 0);
        result1 = 31 * result1 + (resultex != null ? resultex.hashCode() : 0);
        return result1;
    }

    @ManyToOne
    @JoinColumn(name = "problemID", referencedColumnName = "problemID")
    public Problem getProblem() {
        return problem;
    }

    public void setProblem(Problem problemByProblemId) {
        this.problem = problemByProblemId;
    }

    @ManyToOne
    @JoinColumn(name = "classmateID")
    public Classmate getClassmate() {
        return classmate;
    }

    public void setClassmate(Classmate classmate) {
        this.classmate = classmate;
    }

    @ManyToOne
    @JoinColumn(name = "exerciseID", referencedColumnName = "exerciseID")
    public Exercise getExercise() {
        return exercise;
    }

    public void setExercise(Exercise exerciseByExerciseId) {
        this.exercise = exerciseByExerciseId;
    }
}
