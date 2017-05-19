package com.edu.controller;

import com.edu.annotation.SystemControllerLog;
import com.edu.service.ExerciseService;
import com.edu.service.ScoreService;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
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

    @SystemControllerLog("访问教师成绩管理页面")
    @RequestMapping("/teacher/score/scoreManage")
    public String scoreManage() {
        return "teacher/score/scoreManage";
    }


    @Secured({"ROLE_TEACHER", "ROLE_ADMIN"})
    @SystemControllerLog("查看学生成绩")
    @RequestMapping("/classmateScore")
    public String classmateScore() {
        return "teacher/score/classmateScore";
    }

    @Secured("ROLE_TEACHER")
    @SystemControllerLog("访问题目分析页面")
    @RequestMapping("/problemScore")
    public String problemScore() {
        return "teacher/score/problemScore";
    }

    @SystemControllerLog("访问学生成绩页面")
    @RequestMapping("/student/score")
    public String studentScore() {
        return "student/scoreManage";
    }

    @Secured({"ROLE_TEACHER", "ROLE_ADMIN", "ROLE_STUDENT"})
    @SystemControllerLog("查看题目得分情况")
    @RequestMapping("/problemScores/{problemId}")
    @ResponseBody
    public List getScoresByProblemId(@PathVariable("problemId") int problemId) {
        return scoreService.getProblemScores(problemId);
    }

    @RequestMapping("teacher/viewClassScore")
    public String classScore() {
        return "teacher/score/classScore";
    }

    @Secured({"ROLE_TEACHER", "ROLE_ADMIN", "ROLE_STUDENT"})
    @SystemControllerLog("查看练习成绩")
    @RequestMapping("/exerciseScores/{exerciseId}")
    @ResponseBody
    public List getExerciseScores(@PathVariable("exerciseId") int exerciseId) {
        return scoreService.getExerciseScores(exerciseId);
    }

    @Secured({"ROLE_TEACHER", "ROLE_ADMIN", "ROLE_STUDENT"})
    @SystemControllerLog("查看练习是否批改完成")
    @RequestMapping("exercise/{exerciseId}/isJudge")
    @ResponseBody
    public boolean isExerciseFinishJudge(@PathVariable("exerciseId") int exerciseId) {
        return exerciseService.isExerciseFinishJudge(exerciseId);
    }

    @Secured({"ROLE_TEACHER", "ROLE_ADMIN", "ROLE_STUDENT"})
    @SystemControllerLog("获取提交练习的学生")
    @RequestMapping("/classmates/{exerciseId}")
    @ResponseBody
    public List getClassmates(@PathVariable("exerciseId") int exerciseId) {
        return exerciseService.getClassmates(exerciseId);
    }

    @Secured({"ROLE_TEACHER", "ROLE_ADMIN", "ROLE_STUDENT"})
    @SystemControllerLog("获取所有学生的练习成绩")
    @RequestMapping("/classmate/score")
    @ResponseBody
    public List getClassmateScore(int exerciseId, int classmateId) {
        return scoreService.getClassmateScore(exerciseId, classmateId);
    }

    @Secured({"ROLE_TEACHER", "ROLE_ADMIN", "ROLE_STUDENT"})
    @SystemControllerLog("获取练习的平均成绩")
    @RequestMapping("/classmate/average")
    @ResponseBody
    public List getAverageInExercise(int exerciseId, @RequestParam("problemIds[]") int[] problemIds) {
        return scoreService.getAverageInExercise(exerciseId, problemIds);
    }

    @Secured({"ROLE_TEACHER", "ROLE_ADMIN", "ROLE_STUDENT"})
    @SystemControllerLog("获取题目得分统计情况")
    @RequestMapping("/problem/score")
    @ResponseBody
    public List getProblemScoreCount(int problemId) {
        return scoreService.getProblemScoreCount(problemId);
    }

    @Secured({"ROLE_TEACHER", "ROLE_ADMIN", "ROLE_STUDENT"})
    @SystemControllerLog("获取题目在练习中的平均成绩")
    @RequestMapping("problem/averageInExercise/{problemId}")
    @ResponseBody
    public List getAverageProblemScoreInExercise(@PathVariable("problemId") int problemId) {
        return scoreService.getAverageProblemScoreInExercise(problemId);
    }

    @Secured({"ROLE_TEACHER", "ROLE_ADMIN", "ROLE_STUDENT"})
    @SystemControllerLog("获取题目的总平均成绩")
    @RequestMapping("problemAverage/{problemId}")
    @ResponseBody
    public List getProblemAverageScore(@PathVariable("problemId") int problemId) {
        return scoreService.getProblemAverage(problemId);
    }

    @Secured({"ROLE_TEACHER", "ROLE_ADMIN", "ROLE_STUDENT"})
    @SystemControllerLog("获取题目的所有成绩")
    @RequestMapping("/allProblemScore/{problemId}")
    @ResponseBody
    public List getAllProblemScore(@PathVariable("problemId") int problemId) {
        return scoreService.getAllProblemScore(problemId);
    }

    /**
     * 学生成绩模块
     */
    @Secured({"ROLE_TEACHER", "ROLE_ADMIN", "ROLE_STUDENT"})
    @SystemControllerLog("学生查看练习成绩")
    @RequestMapping("/student/score/exercise")
    @ResponseBody
    public Double getExerciseScore(int exerciseId, String userId) {
        return scoreService.getExerciseScoreByUser(exerciseId, userId);
    }


    @RequestMapping("class/score/{classId}")
    @ResponseBody
    public List getClassScores(@PathVariable("classId") int classId) {
        return scoreService.getClassScores(classId);
    }

}
