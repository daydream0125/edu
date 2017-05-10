package com.edu.controller.rest;

import com.edu.annotation.SystemControllerLog;
import com.edu.model.Exercise;
import com.edu.model.Problem;
import com.edu.service.ClazzService;
import com.edu.service.CourseService;
import com.edu.service.ExerciseService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

@org.springframework.web.bind.annotation.RestController
public class RestController {
    @Resource
    private CourseService courseService;
    @Resource
    private ExerciseService exerciseService;

    @Resource
    private ClazzService clazzService;

    @SystemControllerLog("查看课程章")
    @RequestMapping("/chapters/{courseId}")
    public List getChaptersByCourseId(@PathVariable("courseId") int courseId) {
        return courseService.getCourseChapterByCourseId(courseId);
    }

    @SystemControllerLog("查看课程节")
    @RequestMapping("/sections/{chapterId}")
    public List getSectionsByChapterId(@PathVariable("chapterId") int chapterId) {
        return courseService.getSectionsByChapterId(chapterId);
    }


    @SystemControllerLog("获取练习下题目数量")
    @RequestMapping("/exercise/{exerciseId}/problemCount")
    public Long getProblemCount(@PathVariable("exerciseId") int exerciseId) {
        return exerciseService.getProblemCountByExerciseId(exerciseId);
    }

    @SystemControllerLog("获取练习")
    @RequestMapping("/exercise/{exerciseId}")
    public Exercise getExercise(@PathVariable("exerciseId") int exerciseId) {
        return exerciseService.getExerciseById(exerciseId);
    }




    @SystemControllerLog("查看练习下的题目")
    @RequestMapping("/exercise/{exerciseId}/problems")
    public List getProblemsByExerciseId(@PathVariable("exerciseId") int exerciseId) {
        return exerciseService.getProblemsByExerciseId(exerciseId);
    }

    @SystemControllerLog("通过节获取题目")
    @RequestMapping("/problems")
    public List getProblemsBySectionId(int sectionId) {
        return exerciseService.getProblemBySectionId(sectionId);
    }

    @RequestMapping(value = "/problem/{problemId}",method = RequestMethod.GET)
    public Problem getProblemById(@PathVariable("problemId") int problemId) {
        return exerciseService.getProblemById(problemId);
    }

    /**
     * 辅助查询
     */


    @RequestMapping("userToClassmate")
    public int userToClassmate(int exerciseId,String userId) {
        return exerciseService.getClassmateIdByExerciseAndUser(exerciseId,userId);
    }
}
