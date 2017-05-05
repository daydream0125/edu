package com.edu.utils.model;

import com.edu.model.SubmitAnswer;

import java.util.ArrayList;
import java.util.List;

//用于封装用户提交作业的答案
public class Answer {
    //problemId 与 submitAnswer 一一对应
    private List<SubmitAnswer> answers = new ArrayList<>();
    private List<Integer> problemsId = new ArrayList<>();
    private String userId;
    private Integer exerciseId;

    public List<SubmitAnswer> getAnswers() {
        return answers;
    }

    public void setAnswers(List<SubmitAnswer> answers) {
        this.answers = answers;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Integer getExerciseId() {
        return exerciseId;
    }

    public void setExerciseId(Integer exerciseId) {
        this.exerciseId = exerciseId;
    }

    public List<Integer> getProblemsId() {
        return problemsId;
    }

    public void setProblemsId(List<Integer> problemsId) {
        this.problemsId = problemsId;
    }

    @Override
    public String toString() {
        return "AnswerUtil{" +
                "answers=" + answers +
                ", problemsId=" + problemsId +
                ", userId=" + userId +
                ", exerciseId=" + exerciseId +
                '}';
    }
}
