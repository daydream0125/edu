package com.edu.model;



import com.edu.utils.CustomDateSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SelectBeforeUpdate;

import javax.persistence.*;
import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@DynamicInsert(true)
@DynamicUpdate(true)
@SelectBeforeUpdate(true)
public class Exercise {
    private int exerciseId;
    private String exerciseName;
    private String exerciseDesc;
    private Boolean isFinal;
    private Date startTime;
    private Date endTime;
    private Date examTime;
    private Short reserved;
    private Boolean isRelease;
    private Course course;
    private Account teacher;
    private Clazz clazz;
    private Set<Problem> problems = new HashSet<>();

    @Id
    @Column(name = "exerciseID", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getExerciseId() {
        return exerciseId;
    }

    public void setExerciseId(int exerciseId) {
        this.exerciseId = exerciseId;
    }

    @Basic
    @Column(name = "exerciseName", nullable = false, length = 16)
    public String getExerciseName() {
        return exerciseName;
    }

    public void setExerciseName(String exerciseName) {
        this.exerciseName = exerciseName;
    }

    @Basic
    @Column(name = "exerciseDesc")
    public String getExerciseDesc() {
        return exerciseDesc;
    }

    public void setExerciseDesc(String exerciseDesc) {
        this.exerciseDesc = exerciseDesc;
    }

    @Basic
    @Column(name = "isFinal", nullable = true)
    public Boolean getIsFinal() {
        return isFinal;
    }

    public void setIsFinal(Boolean isFinal) {
        this.isFinal = isFinal;
    }

    @Basic
    @Column(name = "startTime", nullable = true)
    @JsonSerialize(using = CustomDateSerializer.class)
    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    @Basic
    @Column(name = "endTime", nullable = true)
    @JsonSerialize(using = CustomDateSerializer.class)
    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    @Basic
    @Column(name = "examTime",nullable = true)
    @JsonSerialize(using = CustomDateSerializer.class)
    public Date getExamTime() {
        return examTime;
    }

    public void setExamTime(Date examTime) {
        this.examTime = examTime;
    }

    @Basic
    @Column(name = "reserved", nullable = true)
    public Short getReserved() {
        return reserved;
    }

    public void setReserved(Short reserved) {
        this.reserved = reserved;
    }

    @Basic
    @Column(name = "isRelease")
    public Boolean getIsRelease() {
        return isRelease;
    }

    public void setIsRelease(Boolean release) {
        isRelease = release;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Exercise exercise = (Exercise) o;

        if (exerciseId != exercise.exerciseId) return false;
        if (exerciseName != null ? !exerciseName.equals(exercise.exerciseName) : exercise.exerciseName != null)
            return false;
        if (isFinal != null ? !isFinal.equals(exercise.isFinal) : exercise.isFinal != null) return false;
        if (startTime != null ? !startTime.equals(exercise.startTime) : exercise.startTime != null) return false;
        if (endTime != null ? !endTime.equals(exercise.endTime) : exercise.endTime != null) return false;
        if (examTime != null ? !examTime.equals(exercise.examTime) : exercise.examTime != null) return false;
        if (reserved != null ? !reserved.equals(exercise.reserved) : exercise.reserved != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = exerciseId;
        result = 31 * result + (exerciseName != null ? exerciseName.hashCode() : 0);
        result = 31 * result + (isFinal != null ? isFinal.hashCode() : 0);
        result = 31 * result + (startTime != null ? startTime.hashCode() : 0);
        result = 31 * result + (endTime != null ? endTime.hashCode() : 0);
        result = 31 * result + (examTime != null ? examTime.hashCode() : 0);
        result = 31 * result + (reserved != null ? reserved.hashCode() : 0);
        return result;
    }

    @ManyToOne
    @JoinColumn(name = "courseID", referencedColumnName = "courseID")
    public Course getCourse() {
        return course;
    }

    public void setCourse(Course courseByCourseId) {
        this.course = courseByCourseId;
    }


    @ManyToOne
    @JoinColumn(name = "classId")
    public Clazz getClazz() {
        return clazz;
    }

    public void setClazz(Clazz clazz) {
        this.clazz = clazz;
    }

    @ManyToOne
    @JoinColumn(name = "teacherID", referencedColumnName = "userID")
    public Account getTeacher() {
        return teacher;
    }

    public void setTeacher(Account accountByTeacherId) {
        this.teacher = accountByTeacherId;
    }


    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "exercise_problem",joinColumns = {@JoinColumn(name = "exerciseID")},
            inverseJoinColumns = @JoinColumn(name = "problemID"))
    public Set<Problem> getProblems() {
        return problems;
    }

    public void setProblems(Set<Problem> problems) {
        this.problems = problems;
    }


}
