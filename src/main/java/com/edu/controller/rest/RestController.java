package com.edu.controller.rest;

import com.edu.model.Exercise;
import com.edu.model.Problem;
import com.edu.service.ClazzService;
import com.edu.service.CourseService;
import com.edu.service.ExerciseService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

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

    @RequestMapping("/chapters/{courseId}")
    public List getChaptersByCourseId(@PathVariable("courseId") int courseId) {
        return courseService.getCourseChapterByCourseId(courseId);
    }

    @RequestMapping("/sections/{chapterId}")
    public List getSectionsByChapterId(@PathVariable("chapterId") int chapterId) {
        return courseService.getSectionsByChapterId(chapterId);
    }


    @RequestMapping("/exercise/{exerciseId}/problemCount")
    public Long getProblemCount(@PathVariable("exerciseId") int exerciseId) {
        return exerciseService.getProblemCountByExerciseId(exerciseId);
    }

    @RequestMapping("/exercise/{exerciseId}")
    public Exercise getExercise(@PathVariable("exerciseId") int exerciseId) {
        return exerciseService.getExerciseById(exerciseId);
    }


    @RequestMapping("/exercise/{exerciseId}/problems")
    public List getProblemsByExerciseId(@PathVariable("exerciseId") int exerciseId) {
        return exerciseService.getProblemsByExerciseId(exerciseId);
    }

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
