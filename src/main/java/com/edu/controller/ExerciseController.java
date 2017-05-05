package com.edu.controller;

import com.edu.dao.ProblemDAO;
import com.edu.model.Exercise;
import com.edu.model.Problem;
import com.edu.service.CourseService;
import com.edu.service.ExerciseService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/teacher/exercise")
public class ExerciseController {
    private CourseService courseService;
    private ExerciseService exerciseService;
    @Resource
    private ProblemDAO problemDAO;

    public CourseService getCourseService() {
        return courseService;
    }

    @Resource
    public void setCourseService(CourseService courseService) {
        this.courseService = courseService;
    }

    public ExerciseService getExerciseService() {
        return exerciseService;
    }

    @Resource
    public void setExerciseService(ExerciseService exerciseService) {
        this.exerciseService = exerciseService;
    }


    @RequestMapping("/problemManage")
    public String problemManage() {
        return "teacher/exercise/problemManage";
    }

    @RequestMapping("/addProblemPage")
    public String addProblemPage(String teacherId, Model model) {
        model.addAttribute("courses", courseService.getCoursesByTeacherId(teacherId));
        return "teacher/exercise/addProblem";
    }


    //处理 ajax 的异步请求，返回课程的章信息，供用户选择
    @RequestMapping("/courseChapter")
    public @ResponseBody
    List ajax(int courseId) {
        return courseService.getCourseChapterByCourseId(courseId);
    }

    //处理 ajax 的异步请求，返货章下的节信息。
    @RequestMapping("/sections")
    public @ResponseBody
    Set showSections(int chapterId) {
        return courseService.getCourseChapterById(chapterId).getChildChapters();
    }

    @RequestMapping("/exerciseManage")
    public String exerciseManage() {
        return "teacher/exercise/exerciseManage";
    }

    @RequestMapping("/viewProblem")
    public String viewProblemForm(String teacherId, Model model) {
        model.addAttribute("courses", courseService.getCoursesByTeacherId(teacherId));
        return "teacher/exercise/viewProblem";
    }

    @RequestMapping("/problemList")
    public String problemList(int chapterId, Model model) {
        model.addAttribute("problems", exerciseService.getProblemBySectionId(chapterId));
        model.addAttribute("chapter", exerciseService.getCourseChapterById(chapterId));
        return "teacher/exercise/problemList";
    }


    @RequestMapping("/updateProblemPage")
    public String updateProblemPage(int problemId, Model model) {
        model.addAttribute("problem", exerciseService.getProblemById(problemId));
        return "teacher/exercise/updateProblem";
    }


    //无法配置 Hibernate 使其不更新外键，选择先取出待更新对象，然后将修改后的数据 set 进待更新对象。
    @RequestMapping("/updateProblem")
    public String updateProblem(Problem problem, String[] choice, RedirectAttributes redirectAttributes) {
        Problem p = exerciseService.getProblemById(problem.getProblemId());
        if (choice != null) {
            StringBuilder desc = new StringBuilder("");
            for (String s : choice) {
                desc.append(s);
                desc.append("\n");
            }
            p.setDescription(desc.toString());
        }
        //待更新字段：
        p.setTitle(problem.getTitle());
        p.setUpdateTime(new Date());
        p.setIsManualJudge(problem.getIsManualJudge());
        p.setUpdateUser(p.getCreateUser());
        //防止内存中存在 id 值相同的对象
        problem.setProblemId(-1);
        redirectAttributes.addAttribute("chapterId", p.getCourseChapter().getId());
        exerciseService.updateProblem(p);
        //跳转至特定小节下的题目页面
        return "redirect:problemList";
    }

    @RequestMapping("/getProblemNum")
    public @ResponseBody
    Long getProblemNum(int chapterId) {
        return exerciseService.getProblemCountByChapterId(chapterId);
    }

    @RequestMapping("/searchProblem")
    public String searchProblemByChapterName(String chapterTitle, Model model) {
        model.addAttribute("chapters", exerciseService.searchChapterByChapterTitle(chapterTitle));
        model.addAttribute("isSearch", 1);
        return "teacher/exercise/problemManage";
    }


    @RequestMapping("/deleteProblem")
    public String deleteProblem(int problemId, int chapterId, RedirectAttributes redirectAttributes) {
        exerciseService.deleteProblem(problemId);
        redirectAttributes.addAttribute("chapterId", chapterId);
        return "redirect:problemList";
    }

    //exercise 管理


    @RequestMapping("/addExercise")
    public String addExercisePage(String teacherId, Model model) {
        model.addAttribute("courses", courseService.getCoursesByTeacherId(teacherId));
        return "teacher/exercise/addExercise";
    }


    //处理异步请求，返回特定课程下的班级
    @RequestMapping("/getClazzByCourseId")
    public @ResponseBody
    List getClazzByCourseId(int courseId) {
        return courseService.getClazzByCourseId(courseId);
    }



    @RequestMapping("/exerciseList")
    public String exerciseList(String teacherId, Model model) {
        model.addAttribute("exercises", exerciseService.getExerciseByTeacherId(teacherId));
        return "teacher/exercise/viewExercise";
    }

    @RequestMapping("/updateExercisePage")
    public String updateExercisePage(int exerciseId, Model model) {
        model.addAttribute("exercise", exerciseService.getExerciseById(exerciseId));
        return "teacher/exercise/updateExercise";
    }

    //更新 exercise，处理方法同更新 problem，解释见上。
    @RequestMapping("/updateExercise")
    public String updateExercise(Exercise exercise, String teacherId, RedirectAttributes redirectAttributes) {
        Exercise e = exerciseService.getExerciseById(exercise.getExerciseId());
        e.setExerciseName(exercise.getExerciseName());
        e.setExerciseDesc(e.getExerciseDesc());
        e.setIsFinal(exercise.getIsFinal());
        e.setStartTime(exercise.getStartTime());
        e.setEndTime(exercise.getEndTime());
        exercise.setExerciseId(-1);
        exerciseService.updateExercise(e);
        redirectAttributes.addAttribute("teacherId", teacherId);
        return "redirect:exerciseList";
    }


    @RequestMapping("/deleteExercise")
    public String deleteExercise(int eid, String tid, RedirectAttributes redirectAttributes) {
        exerciseService.deleteExerciseById(eid);
        redirectAttributes.addAttribute("teacherId", tid);
        return "redirect:exerciseList";
    }


    @RequestMapping("/releaseList")
    public String releaseExerciseList(String teacherId, Model model) {
        model.addAttribute("exercises", exerciseService.getExerciseByTeacherId(teacherId));
        return "teacher/exercise/releaseExercise";
    }

    //发布课程，ajax 的异步请求
    @RequestMapping("/releaseExercise")
    public void releaseExercise(int exerciseId) {
        exerciseService.releaseExercise(exerciseId);
    }

    @RequestMapping("/viewExercise")
    public String viewExercise(String teacherId, Model model) {
        model.addAttribute("exercises", exerciseService.getExerciseByTeacherId(teacherId));
        return "teacher/exercise/viewExercise";
    }


}
