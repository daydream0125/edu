package com.edu.model;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.*;
import javax.persistence.*;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@DynamicInsert(true)
@DynamicUpdate(true)
@SelectBeforeUpdate(true)
public class Problem {
    private int problemId;
    private Date createTime;
    private Date updateTime;
    private boolean isPublic;
    private Boolean isManualJudge;
    private String title;
    private String description;
    private String solution;
    private byte type;
    private Integer acceptedSolutions = 0;
    private Integer totalSolutions = 0;
    private String problemPicPath;
    private String solutionPicPath;
    private Course course;
    private Account createUser;
    private Account updateUser;
    private CourseChapter courseChapter;
    private Set<SubmitAnswer> submitAnswers = new HashSet<>();

    @Id
    @Column(name = "problemID", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getProblemId() {
        return problemId;
    }

    public void setProblemId(int problemId) {
        this.problemId = problemId;
    }

    @Basic
    @Column(name = "createTime")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Basic
    @Column(name = "updateTime")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    @Basic
    @Column(name = "isPublic")
    public boolean getIsPublic() {
        return isPublic;
    }

    public void setIsPublic(boolean aPublic) {
        isPublic = aPublic;
    }

    @Basic
    @Column(name = "isManualJudge")
    public Boolean getIsManualJudge() {
        return isManualJudge;
    }

    public void setIsManualJudge(Boolean manualJudge) {
        isManualJudge = manualJudge;
    }

    @Basic
    @Column(name = "title", length = 2147483647)
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Basic
    @Column(name = "description", length = 2147483647)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    @Basic
    @Column(name = "solution", length = 2147483647)
    public String getSolution() {
        return solution;
    }

    public void setSolution(String solution) {
        this.solution = solution;
    }

    @Basic
    @Column(name = "type")
    public byte getType() {
        return type;
    }

    public void setType(byte type) {
        this.type = type;
    }

    @Basic
    @Column(name = "acceptedSolutions")
    public Integer getAcceptedSolutions() {
        return acceptedSolutions;
    }

    public void setAcceptedSolutions(Integer acceptedSolutions) {
        this.acceptedSolutions = acceptedSolutions;
    }

    @Basic
    @Column(name = "totalSolutions")
    public Integer getTotalSolutions() {
        return totalSolutions;
    }

    public void setTotalSolutions(Integer totalSolutions) {
        this.totalSolutions = totalSolutions;
    }

    @Basic
    @Column(name = "problemPicPath")
    public String getProblemPicPath() {
        return problemPicPath;
    }

    public void setProblemPicPath(String problemPicPath) {
        this.problemPicPath = problemPicPath;
    }

    @Basic
    @Column(name = "solutionPicPath")
    public String getSolutionPicPath() {
        return solutionPicPath;
    }

    public void setSolutionPicPath(String solutionPicPath) {
        this.solutionPicPath = solutionPicPath;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Problem problem = (Problem) o;

        if (problemId != problem.problemId) return false;
        if (isPublic != problem.isPublic) return false;
        if (type != problem.type) return false;
        if (createTime != null ? !createTime.equals(problem.createTime) : problem.createTime != null) return false;
        if (updateTime != null ? !updateTime.equals(problem.updateTime) : problem.updateTime != null) return false;
        if (isManualJudge != null ? !isManualJudge.equals(problem.isManualJudge) : problem.isManualJudge != null)
            return false;
        if (title != null ? !title.equals(problem.title) : problem.title != null) return false;
        if (description != null ? !description.equals(problem.description) : problem.description != null) return false;
        if (solution != null ? !solution.equals(problem.solution) : problem.solution != null) return false;
        if (acceptedSolutions != null ? !acceptedSolutions.equals(problem.acceptedSolutions) : problem.acceptedSolutions != null)
            return false;
        if (totalSolutions != null ? !totalSolutions.equals(problem.totalSolutions) : problem.totalSolutions != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = problemId;
        result = 31 * result + (createTime != null ? createTime.hashCode() : 0);
        result = 31 * result + (updateTime != null ? updateTime.hashCode() : 0);
        result = 31 * result + (isPublic ? 1 : 0);
        result = 31 * result + (isManualJudge != null ? isManualJudge.hashCode() : 0);
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (solution != null ? solution.hashCode() : 0);
        result = 31 * result + (int) type;
        result = 31 * result + (acceptedSolutions != null ? acceptedSolutions.hashCode() : 0);
        result = 31 * result + (totalSolutions != null ? totalSolutions.hashCode() : 0);
        return result;
    }


    @ManyToOne(cascade = CascadeType.PERSIST)
    @JsonBackReference
    @JoinColumn(name = "courseID", referencedColumnName = "courseID")
    public Course getCourse() {
        return course;
    }

    public void setCourse(Course courseByCourseId) {
        this.course = courseByCourseId;
    }

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "createUserID", referencedColumnName = "userID")
    public Account getCreateUser() {
        return createUser;
    }

    public void setCreateUser(Account accountByCreateUserId) {
        this.createUser = accountByCreateUserId;
    }

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "updateUserID", referencedColumnName = "userID")
    public Account getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(Account accountByUpdateUserId) {
        this.updateUser = accountByUpdateUserId;
    }

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "chapterId", referencedColumnName = "id")
    public CourseChapter getCourseChapter() {
        return courseChapter;
    }

    public void setCourseChapter(CourseChapter courseChapter) {
        this.courseChapter = courseChapter;
    }

    @OneToMany(mappedBy = "problem")
    @JsonIgnore
    public Set<SubmitAnswer> getSubmitAnswers() {
        return submitAnswers;
    }

    public void setSubmitAnswers(Set<SubmitAnswer> submitAnswersByProblemId) {
        this.submitAnswers = submitAnswersByProblemId;
    }
}
