package com.edu.model;


import com.edu.utils.CustomDateSerializer;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import javax.persistence.CascadeType;
import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "class")
@DynamicInsert(true)
@DynamicUpdate(true)
public class Clazz {
    private int classId;
    private String className;
    private Date createTime;
    private Date startTime;
    private Date endTime;
    private Date examTime;
    private Boolean isPublicRegister = false;
    private Boolean isFinish = false;
    private Integer scorePercent;
    private Course course;
    private Account teacher;
    private Set<Classmate> classmates = new HashSet<>();
    private Set<Exercise> exercises = new HashSet<>();

    @Id
    @Column(name = "classID", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getClassId() {
        return classId;
    }

    public void setClassId(int classId) {
        this.classId = classId;
    }

    @Basic
    @Column(name = "className", nullable = false, length = 16)
    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    @Basic
    @Column(name = "createTime", nullable = true)
    @JsonSerialize(using = CustomDateSerializer.class)
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Basic
    @Column(name = "startTime", nullable = true)
    @JsonSerialize(using = CustomDateSerializer.class)
    public Date getStartTime() {
        return startTime;
    }
    @JsonSerialize(using = CustomDateSerializer.class)
    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    @Basic
    @Column(name = "endTime", nullable = true)
    @JsonSerialize(using = CustomDateSerializer.class)
    public Date getEndTime() {
        return endTime;
    }
    @JsonSerialize(using = CustomDateSerializer.class)
    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    @Basic
    @Column(name = "examTime", nullable = true)
    @JsonSerialize(using = CustomDateSerializer.class)
    public Date getExamTime() {
        return examTime;
    }
    @JsonSerialize(using = CustomDateSerializer.class)
    public void setExamTime(Date examTime) {
        this.examTime = examTime;
    }

    @Basic
    @Column(name = "scorePercent")
    public Integer getScorePercent() {
        return scorePercent;
    }

    public void setScorePercent(Integer scorePercent) {
        this.scorePercent = scorePercent;
    }

    @Basic
    @Column(name = "isPublicRegister", nullable = true)
    public Boolean getIsPublicRegister() {
        return isPublicRegister;
    }

    public void setIsPublicRegister(Boolean isPublicRegister) {
        this.isPublicRegister = isPublicRegister;
    }

    @Basic
    @Column(name = "isFinish")
    public Boolean getIsFinish() {
        return isFinish;
    }

    public void setIsFinish(Boolean finish) {
        isFinish = finish;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Clazz clazz = (Clazz) o;

        if (classId != clazz.classId) return false;
        if (className != null ? !className.equals(clazz.className) : clazz.className != null) return false;
        if (createTime != null ? !createTime.equals(clazz.createTime) : clazz.createTime != null) return false;
        if (startTime != null ? !startTime.equals(clazz.startTime) : clazz.startTime != null) return false;
        if (endTime != null ? !endTime.equals(clazz.endTime) : clazz.endTime != null) return false;
        if (isPublicRegister != null ? !isPublicRegister.equals(clazz.isPublicRegister) : clazz.isPublicRegister != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = classId;
        result = 31 * result + (className != null ? className.hashCode() : 0);
        result = 31 * result + (createTime != null ? createTime.hashCode() : 0);
        result = 31 * result + (startTime != null ? startTime.hashCode() : 0);
        result = 31 * result + (endTime != null ? endTime.hashCode() : 0);
        result = 31 * result + (isPublicRegister != null ? isPublicRegister.hashCode() : 0);
        return result;
    }

    @OneToOne
    @JoinColumn(name = "courseID", referencedColumnName = "courseID")
    public Course getCourse() {
        return course;
    }

    public void setCourse(Course courseByCourseId) {
        this.course = courseByCourseId;
    }

    @OneToOne
    @JoinColumn(name = "teacherID", referencedColumnName = "userID")
    public Account getTeacher() {
        return teacher;
    }

    public void setTeacher(Account accountByTeacherId) {
        this.teacher = accountByTeacherId;
    }

    @OneToMany(mappedBy = "clazz",cascade = CascadeType.ALL)
    @JsonIgnore
    public Set<Classmate> getClassmates() {
        return classmates;
    }

    public void setClassmates(Set<Classmate> classmates) {
        this.classmates = classmates;
    }

    @OneToMany(mappedBy = "clazz")
    @JsonIgnore
    public Set<Exercise> getExercises() {
        return exercises;
    }

    public void setExercises(Set<Exercise> exercises) {
        this.exercises = exercises;
    }
}
