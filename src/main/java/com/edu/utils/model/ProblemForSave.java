package com.edu.utils.model;

import com.edu.model.Problem;

public class ProblemForSave {
    private Problem problem;
    private String[] choose;
    private Integer sectionId;
    private String createUserId;
    private Integer courseId;

    public Problem getProblem() {
        return problem;
    }

    public void setProblem(Problem problem) {
        this.problem = problem;
    }

    public String[] getChoose() {
        return choose;
    }

    public void setChoose(String[] choose) {
        this.choose = choose;
    }

    public Integer getSectionId() {
        return sectionId;
    }

    public void setSectionId(Integer sectionId) {
        this.sectionId = sectionId;
    }

    public String getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(String createUserId) {
        this.createUserId = createUserId;
    }

    public Integer getCourseId() {
        return courseId;
    }

    public void setCourseId(Integer courseId) {
        this.courseId = courseId;
    }
}
