package com.edu.model;


import com.edu.utils.CustomDateSerializer;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@DynamicInsert(true)
@DynamicUpdate(true)
public class Course {
    private int courseId;
    private String courseName;
    private String courseDescription;
    private String coursePic;
    private Date startTime;
    private Date endTime;
    private Date examTime;
    private Byte examType;
    private Short reserved;
    private Boolean isFinish = false;
    private Account teacher;
    private Set<Exercise> exercises = new HashSet<>();
    private Set<CourseChapter> courseChapters = new HashSet<>();

    @Id
    @Column(name = "courseID", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    @Basic
    @Column(name = "courseName", nullable = false, length = 16)
    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    @Basic
    @Column(name = "courseDescription", nullable = true, length = 2147483647)
    public String getCourseDescription() {
        return courseDescription;
    }

    public void setCourseDescription(String courseDescription) {
        this.courseDescription = courseDescription;
    }

    @Basic
    @Column(name = "coursePic")
    public String getCoursePic() {
        return coursePic;
    }
    public void setCoursePic(String coursePic) {
        this.coursePic = coursePic;
    }

    @Basic
    @Column(name = "startTime", nullable = true)
    //
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
    @Column(name = "examTime", nullable = true)
    @JsonSerialize(using = CustomDateSerializer.class)
    public Date getExamTime() {
        return examTime;
    }

    public void setExamTime(Date examTime) {
        this.examTime = examTime;
    }

    @Basic
    @Column(name = "examType", nullable = true)
    public Byte getExamType() {
        return examType;
    }

    public void setExamType(Byte examType) {
        this.examType = examType;
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
    @Column(name = "isFinish")
    public Boolean getIsFinish() {
        return isFinish;
    }

    public void setIsFinish(Boolean isFinish) {
        this.isFinish = isFinish;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Course course = (Course) o;

        if (courseId != course.courseId) return false;
        if (courseName != null ? !courseName.equals(course.courseName) : course.courseName != null) return false;
        if (courseDescription != null ? !courseDescription.equals(course.courseDescription) : course.courseDescription != null)
            return false;
        if (startTime != null ? !startTime.equals(course.startTime) : course.startTime != null) return false;
        if (endTime != null ? !endTime.equals(course.endTime) : course.endTime != null) return false;
        if (examTime != null ? !examTime.equals(course.examTime) : course.examTime != null) return false;
        if (examType != null ? !examType.equals(course.examType) : course.examType != null) return false;
        if (reserved != null ? !reserved.equals(course.reserved) : course.reserved != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = courseId;
        result = 31 * result + (courseName != null ? courseName.hashCode() : 0);
        result = 31 * result + (courseDescription != null ? courseDescription.hashCode() : 0);
        result = 31 * result + (startTime != null ? startTime.hashCode() : 0);
        result = 31 * result + (endTime != null ? endTime.hashCode() : 0);
        result = 31 * result + (examTime != null ? examTime.hashCode() : 0);
        result = 31 * result + (examType != null ? examType.hashCode() : 0);
        result = 31 * result + (reserved != null ? reserved.hashCode() : 0);
        return result;
    }
    @OneToMany(mappedBy = "course",fetch=FetchType.LAZY)
    @JsonIgnore
    public Set<Exercise> getExercises() {
        return exercises;
    }

    public void setExercises(Set<Exercise> exercises) {
        this.exercises = exercises;
    }

    @ManyToOne
    @JoinColumn(name = "teacherID", referencedColumnName = "userID")
    public Account getTeacher() {
        return teacher;
    }

    public void setTeacher(Account accountByTeacherId) {
        this.teacher = accountByTeacherId;
    }


    @OneToMany(mappedBy = "course",cascade = CascadeType.ALL)
    @JsonIgnore
    public Set<CourseChapter> getCourseChapters() {
        return courseChapters;
    }
    public void setCourseChapters(Set<CourseChapter> courseChapters) {
        this.courseChapters = courseChapters;
    }
}
