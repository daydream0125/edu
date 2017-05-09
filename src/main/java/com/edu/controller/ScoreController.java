package com.edu.controller;

import com.edu.dao.SubmitExerciseDAO;
import com.edu.service.ExerciseService;
import com.edu.service.ScoreService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

@Controller
public class ScoreController {
    @Resource
    private ScoreService scoreService;
    @Resource
    private ExerciseService exerciseService;

    @RequestMapping("/teacher/score/scoreManage")
    public String scoreManage() {
        return "teacher/score/scoreManage";
    }


    @RequestMapping("/scoreTest")
    public String testScore() {
        return "score/test";
    }

    @RequestMapping("/classmateScore")
    public String classmateScore() {
        return "teacher/score/classmateScore";
    }

    @RequestMapping("/problemScore")
    public String problemScore() {
        return "teacher/score/problemScore";
    }

    @RequestMapping("/student/score")
    public String studentScore() {
        return "student/scoreManage";
    }

    @RequestMapping("/problemScores/{problemId}")
    @ResponseBody
    public List getScoresByProblemId(@PathVariable("problemId") int problemId) {
        return scoreService.getProblemScores(problemId);
    }

    @RequestMapping("/exerciseScores/{exerciseId}")
    @ResponseBody
    public List getExerciseScores(@PathVariable("exerciseId") int exerciseId) {
        return scoreService.getExerciseScores(exerciseId);
    }



    @RequestMapping("/classmates/{exerciseId}")
    @ResponseBody
    public List getClassmates(@PathVariable("exerciseId") int exerciseId) {
        return exerciseService.getClassmates(exerciseId);
    }


    @RequestMapping("/classmate/score")
    @ResponseBody
    public List getClassmateScore(int exerciseId,int classmateId) {
        return scoreService.getClassmateScore(exerciseId,classmateId);
    }

    @RequestMapping("/classmate/average")
    @ResponseBody
    public List getAverageInExercise(int exerciseId,@RequestParam("problemIds[]") int[] problemIds) {
        return scoreService.getAverageInExercise(exerciseId,problemIds);
    }

    @RequestMapping("/problem/score")
    @ResponseBody
    public List getProblemScoreCount(int problemId) {
        return scoreService.getProblemScoreCount(problemId);
    }

    @RequestMapping("problem/averageInExercise/{problemId}")
    @ResponseBody
    public List getAverageProblemScoreInExercise(@PathVariable("problemId") int problemId) {
        return scoreService.getAverageProblemScoreInExercise(problemId);
    }

    @RequestMapping("problemAverage/{problemId}")
    @ResponseBody
    public List getProblemAverageScore(@PathVariable("problemId") int problemId) {
        return scoreService.getProblemAverage(problemId);
    }

    @RequestMapping("/allProblemScore/{problemId}")
    @ResponseBody
    public List getAllProblemScore(@PathVariable("problemId") int problemId) {
        return scoreService.getAllProblemScore(problemId);
    }

    /**
     * 学生成绩模块
     */
    @RequestMapping("/student/score/exercise")
    @ResponseBody
    public Double getExerciseScore(int exerciseId,String userId) {
        return scoreService.getExerciseScoreByUser(exerciseId,userId);
    }


}
