package com.edu.controller;

import com.edu.model.*;
import com.edu.service.ClazzService;
import com.edu.service.CourseService;
import com.edu.service.ExerciseService;
import com.edu.utils.UploadFile;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;


@Controller
@RequestMapping("/teacher")
public class TeacherController {
    @Resource
    private CourseService courseService;
    @Resource
    private ExerciseService exerciseService;
    @Resource
    private ClazzService clazzService;

    @RequestMapping("/{teacherId}/courses")
    @ResponseBody
    public List getCoursesByTeacherId(@PathVariable("teacherId") String teacherId) {
        return courseService.getCoursesByTeacherId(teacherId);
    }
    @RequestMapping("/addCourse")
    public String addCourse() {
        return "teacher/course/addCourse";
    }

    @RequestMapping("/courseList")
    public String courseList() {
        return "teacher/course/courseList";
    }
    @RequestMapping("/saveCourse")
    public String saveCourse(Course course, String teacherId, @RequestParam(value="coursePicture",required = false)MultipartFile multipartFile) {
        course.setCoursePic(UploadFile.uploadFile(multipartFile,"course-img/"));
        courseService.saveCourse(course, teacherId);
        return "redirect:teacher/courseList";
    }


    //上传课程视频
    /*
        上传成功返回视频的相对地址,失败返回 error
     */
    @RequestMapping("/uploadChapterVideo")
    @ResponseBody
    public String uploadContentVideo(@RequestParam("video") MultipartFile multipartFile,
                                     @RequestParam("contentId") int contentId) throws IOException {

        return courseService.uploadChapterVideo(multipartFile, contentId);
    }

    @RequestMapping("/addChapter")
    public String addChapterPage(int courseId, Model model) {
        model.addAttribute("courseId", courseId);
        return "teacher/course/addChapter";
    }


    @RequestMapping(value = "/chapter", method = RequestMethod.POST)
    @ResponseBody
    public boolean addChapter(CourseChapter courseChapter, @RequestParam("sections[]") String sections[], int courseId) {
        return courseService.saveCourseChapter(courseChapter, courseId, sections);
    }


    //上传图片至 problem-img文件夹
    @RequestMapping("/uploadPicture")
    @ResponseBody
    public String uploadProblemPic(@RequestParam("pic") MultipartFile multipartFile) {
        return exerciseService.uploadPicture(multipartFile);
    }

    @RequestMapping("uploadContentPic")
    @ResponseBody
    public String uploadContentPic(@RequestParam("pic") MultipartFile multipartFile) {
        return UploadFile.uploadFile(multipartFile, "content-img/");
    }


    //保存problem
    @RequestMapping(value = "/exercise/problem", method = RequestMethod.POST)
    @ResponseBody
    public boolean addProblem(Problem problem, String createUserId, int sectionId, int courseId) {
        return exerciseService.saveProblem(problem, createUserId, sectionId, courseId);
    }

    @RequestMapping("/courseManage/{courseId}")
    public String courseManage(@PathVariable("courseId") int courseId, Model model) {
        model.addAttribute("courseId", courseId);
        return "teacher/course/courseManage";
    }

    @RequestMapping("/openRegister")
    @ResponseBody
    public boolean openRegister(int classId) {
        clazzService.openRegister(classId);
        return true;
    }

    @RequestMapping("/updateChapterContent/{sectionId}")
    public String updateChapterContentPage(@PathVariable("sectionId") int sectionId, Model model) {
        model.addAttribute("sectionId", sectionId);
        return "teacher/course/updateChapterContent";
    }


    @RequestMapping("/class")
    @ResponseBody
    public boolean addClass(Clazz clazz, String teacherId, int courseId) {
        return clazzService.addClass(clazz, teacherId, courseId);
    }

    @RequestMapping("/course/updateChapterContent")
    @ResponseBody
    public boolean updateChapterContent(int sectionId, String content) {
        return courseService.updateChapterContent(sectionId, content);
    }


    @RequestMapping("/class/{classId}/registers")
    @ResponseBody
    public List getRegisters(@PathVariable("classId") int classId) {
        return clazzService.getRegisters(classId);
    }

    @RequestMapping("/approveRegisters")
    @ResponseBody
    public boolean approveRegisters(@RequestParam("approveRegisters[]") int[] approveRegisters) {
        return clazzService.approveRegisters(approveRegisters);
    }

    @RequestMapping("/importStudent")
    @ResponseBody
    public List<String> importStudent(@RequestParam("excel") MultipartFile multipartFile,int classId) {
        return clazzService.importStudent(multipartFile,classId);
    }


    //exercise模块

    @RequestMapping("/exerciseManage")
    public String exerciseManage() {
        return "teacher/exercise/exerciseManage";
    }
    @RequestMapping(value = "/{teacherId}/exercises",method = RequestMethod.GET)
    @ResponseBody
    public List getExerciseByTeacherId(@PathVariable("teacherId") String teacherId) {
        return exerciseService.getExerciseByTeacherId(teacherId);
    }

    @RequestMapping(value = "/exercise",method = RequestMethod.POST)
    @ResponseBody
    public boolean addExercise(Exercise exercise,int courseId,int classId,String teacherId) {
        return exerciseService.saveExercise(exercise, courseId, classId, teacherId);
    }

    @RequestMapping("/releaseExercise")
    @ResponseBody
    public boolean releaseExercise(int exerciseId) {
        return exerciseService.releaseExercise(exerciseId);
    }

    @RequestMapping("/addProblemToExercise/{exerciseId}")
    public String addProblemToExercise(@PathVariable("exerciseId") int exerciseId,Model model) {
        model.addAttribute("exerciseId",exerciseId);
        return "teacher/exercise/addProblemToExercise";
    }

    @RequestMapping("/addExercise")
    public String addExercise() {
        return "teacher/exercise/addExercise";
    }


    @RequestMapping("/submitProblems")
    @ResponseBody
    public boolean saveProblemsToExercise(@RequestParam("submitProblems[]") int problemsId[],int exerciseId) {
        return exerciseService.addProbemsToExercise(problemsId,exerciseId);
    }


    //题库管理模块

    @RequestMapping("/addProblemPage")
    public String addProblemPage() {
        return "teacher/exercise/addProblem";
    }

    @RequestMapping("/problemList")
    public String problemList(Model model) {
        model.addAttribute("sectionId",0);
        return "teacher/exercise/problemList";
    }


    @RequestMapping(value = "/searchProblems",method = RequestMethod.GET)
    @ResponseBody
    public List searchProblemsByChapterName(String keywords) {
        return exerciseService.searchChapterByChapterTitle(keywords);
    }

    @RequestMapping(value = "/problemCountByChapterId",method = RequestMethod.GET)
    @ResponseBody
    public Long getProblemCountByChapterId(int id) {
        return exerciseService.getProblemCountByChapterId(id);
    }

    @RequestMapping("/filterProblems")
    @ResponseBody
    public List filterProblems(int type,int keywords) {
        return exerciseService.filterProblems(type, keywords);
    }

    @RequestMapping("/viewProblem")
    public String viewProblem(int sectionId,Model model) {
        model.addAttribute("sectionId",sectionId);
        return "teacher/exercise/problemList";
    }

    @RequestMapping("/classmateCount/{classId}")
    @ResponseBody
    public Long getClassmateCount(@PathVariable("classId") int classId) {
        return clazzService.getClassmatesCount(classId);
    }

    @RequestMapping("/exercise/submitCount/{exerciseId}")
    @ResponseBody
    public Long getSubmitCount(@PathVariable("exerciseId") int exerciseId) {
        return exerciseService.getSubmitExerciseCount(exerciseId);
    }

    @RequestMapping("/exercise/judgeCount/{exerciseId}")
    @ResponseBody
    public Long getJudgeCount(@PathVariable("exerciseId") int exerciseId) {
        return exerciseService.getJudgeCount(exerciseId);
    }

    @RequestMapping("/exercise/markExercise/{exerciseId}")
    public String markExercisePage(@PathVariable("exerciseId") int exerciseId,Model model) {
        model.addAttribute("exerciseId",exerciseId);
        return "teacher/exercise/markExercise";
    }

    @RequestMapping("/exercise/classmates/{exerciseId}")
    @ResponseBody
    public List getClassmatesByExerciseId(@PathVariable("exerciseId") int exerciseId) {
        return exerciseService.getExistUnMarkProblemClassmates(exerciseId);
    }

    @RequestMapping("/exercise/markObjectiveProblem/{exerciseId}")
    @ResponseBody
    public boolean markObjectiveProblem(@PathVariable("exerciseId") int exerciseId) {
        return exerciseService.markObjectiveProblem(exerciseId);
    }


    @RequestMapping("/exercise/markSubjectiveProblem")
    @ResponseBody
    public boolean markSubjectiveProblem(@RequestParam("scores[]") int scores[],@RequestParam("answersId[]") int answersId[]) {
        return exerciseService.markSubjectiveProblem(scores,answersId);
    }

    @RequestMapping("/subjectiveAnswers")
    @ResponseBody
    public List getSubjectiveAnswers(int exerciseId,int classmateId) {
        return exerciseService.getSubjectiveAnswers(exerciseId,classmateId);
    }





}
