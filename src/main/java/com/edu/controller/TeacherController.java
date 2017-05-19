package com.edu.controller;

import com.edu.annotation.SystemControllerLog;
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

    @SystemControllerLog("教师获取自己的开设课程")
    @RequestMapping("/{teacherId}/courses")
    @ResponseBody
    public List getCoursesByTeacherId(@PathVariable("teacherId") String teacherId) {
        return courseService.getCoursesByTeacherId(teacherId);
    }
    @SystemControllerLog("访问添加课程页面")
    @RequestMapping("/addCourse")
    public String addCourse() {
        return "teacher/course/addCourse";
    }

    @SystemControllerLog("获取教师的全部课程")
    @RequestMapping("/courseList")
    public String courseList() {
        return "teacher/course/courseList";
    }

    @SystemControllerLog("添加课程")
    @RequestMapping("/saveCourse")
    public String saveCourse(Course course, String teacherId, @RequestParam(value="coursePicture",required = false)MultipartFile multipartFile) {
        course.setCoursePic(UploadFile.uploadFile(multipartFile,"course-img/"));
        courseService.saveCourse(course, teacherId);
        return "redirect:teacher/courseList";
    }

    @RequestMapping("/problemManage")
    public String problemManagePage() {
        return "teacher/exercise/problemManage";
    }

    //上传课程视频
    /*
        上传成功返回视频的相对地址,失败返回 error
     */
    @SystemControllerLog("上传教学视频")
    @RequestMapping("/uploadChapterVideo")
    @ResponseBody
    public String uploadContentVideo(@RequestParam("video") MultipartFile multipartFile,
                                     @RequestParam("contentId") int contentId) throws IOException {
        return courseService.uploadChapterVideo(multipartFile, contentId);
    }

    @SystemControllerLog("访问添加章节信息页面")
    @RequestMapping("/addChapter")
    public String addChapterPage(int courseId, Model model) {
        model.addAttribute("courseId", courseId);
        return "teacher/course/addChapter";
    }


    @SystemControllerLog("添加章节信息")
    @RequestMapping(value = "/chapter", method = RequestMethod.POST)
    @ResponseBody
    public boolean addChapter(CourseChapter courseChapter, @RequestParam("sections[]") String sections[], int courseId) {
        return courseService.saveCourseChapter(courseChapter, courseId, sections);
    }


    //上传图片至 problem-img文件夹
    @SystemControllerLog("上传题目图片")
    @RequestMapping("/uploadPicture")
    @ResponseBody
    public String uploadProblemPic(@RequestParam("pic") MultipartFile multipartFile) {
        return exerciseService.uploadPicture(multipartFile);
    }

    @SystemControllerLog("上传课程内容图片")
    @RequestMapping("uploadContentPic")
    @ResponseBody
    public String uploadContentPic(@RequestParam("pic") MultipartFile multipartFile) {
        return UploadFile.uploadFile(multipartFile, "content-img/");
    }


    //保存problem
    @SystemControllerLog("添加题目")
    @RequestMapping(value = "/exercise/problem", method = RequestMethod.POST)
    @ResponseBody
    public boolean addProblem(Problem problem, String createUserId, int sectionId, int courseId) {
        return exerciseService.saveProblem(problem, createUserId, sectionId, courseId);
    }

    @SystemControllerLog("访问课程管理页面")
    @RequestMapping("/courseManage/{courseId}")
    public String courseManage(@PathVariable("courseId") int courseId, Model model) {
        model.addAttribute("courseId", courseId);
        return "teacher/course/courseManage";
    }

    @SystemControllerLog("开放班级注册")
    @RequestMapping("/openRegister")
    @ResponseBody
    public boolean openRegister(int classId) {
        clazzService.openRegister(classId);
        return true;
    }

    @SystemControllerLog("访问更新课程内容页面")
    @RequestMapping("/updateChapterContent/{sectionId}")
    public String updateChapterContentPage(@PathVariable("sectionId") int sectionId, Model model) {
        model.addAttribute("sectionId", sectionId);
        return "teacher/course/updateChapterContent";
    }


    @SystemControllerLog("添加班级")
    @RequestMapping("/class")
    @ResponseBody
    public boolean addClass(Clazz clazz, String teacherId, int courseId) {
        return clazzService.addClass(clazz, teacherId, courseId);
    }

    @SystemControllerLog("更新课程内容")
    @RequestMapping("/course/updateChapterContent")
    @ResponseBody
    public boolean updateChapterContent(int sectionId, String content) {
        return courseService.updateChapterContent(sectionId, content);
    }


    @SystemControllerLog("获取审核注册学生")
    @RequestMapping("/class/{classId}/registers")
    @ResponseBody
    public List getRegisters(@PathVariable("classId") int classId) {
        return clazzService.getRegisters(classId);
    }

    @SystemControllerLog("学生通过审核")
    @RequestMapping("/approveRegisters")
    @ResponseBody
    public boolean approveRegisters(@RequestParam("approveRegisters[]") int[] approveRegisters) {
        return clazzService.approveRegisters(approveRegisters);
    }

    @SystemControllerLog("导入学生名单")
    @RequestMapping("/importStudent")
    @ResponseBody
    public List<String> importStudent(@RequestParam("excel") MultipartFile multipartFile,int classId) {
        return clazzService.importStudent(multipartFile,classId);
    }


    //exercise模块
    @SystemControllerLog("教师访问练习管理页面")
    @RequestMapping("/exerciseManage")
    public String exerciseManage() {
        return "teacher/exercise/exerciseManage";
    }

    @SystemControllerLog("获取所有练习")
    @RequestMapping(value = "/{teacherId}/exercises",method = RequestMethod.GET)
    @ResponseBody
    public List getExerciseByTeacherId(@PathVariable("teacherId") String teacherId) {
        return exerciseService.getExerciseByTeacherId(teacherId);
    }

    @SystemControllerLog("添加练习")
    @RequestMapping(value = "/exercise",method = RequestMethod.POST)
    @ResponseBody
    public boolean addExercise(Exercise exercise,int courseId,int classId,String teacherId) {
        return exerciseService.saveExercise(exercise, courseId, classId, teacherId);
    }

    @SystemControllerLog("发布练习")
    @RequestMapping("/releaseExercise")
    @ResponseBody
    public boolean releaseExercise(int exerciseId) {
        return exerciseService.releaseExercise(exerciseId);
    }

    @SystemControllerLog("访问添加题目至练习页面")
    @RequestMapping("/addProblemToExercise/{exerciseId}")
    public String addProblemToExercise(@PathVariable("exerciseId") int exerciseId,Model model) {
        model.addAttribute("exerciseId",exerciseId);
        return "teacher/exercise/addProblemToExercise";
    }

    @SystemControllerLog("访问添加练习页面")
    @RequestMapping("/addExercise")
    public String addExercise() {
        return "teacher/exercise/addExercise";
    }


    @SystemControllerLog("添加题目至练习")
    @RequestMapping("/submitProblems")
    @ResponseBody
    public boolean saveProblemsToExercise(@RequestParam("submitProblems[]") int problemsId[],int exerciseId) {
        return exerciseService.addProblemsToExercise(problemsId,exerciseId);
    }


    //题库管理模块

    @SystemControllerLog("访问添加题目页面")
    @RequestMapping("/addProblemPage")
    public String addProblemPage() {
        return "teacher/exercise/addProblem";
    }

    @SystemControllerLog("访问题库页面")
    @RequestMapping("/problemList")
    public String problemList(Model model) {
        model.addAttribute("sectionId",0);
        return "teacher/exercise/problemList";
    }


    @SystemControllerLog("检索题目")
    @RequestMapping(value = "/searchProblems",method = RequestMethod.GET)
    @ResponseBody
    public List searchProblemsByChapterName(String keywords) {
        return exerciseService.searchChapterByChapterTitle(keywords);
    }

    @SystemControllerLog("获取小节题目数量")
    @RequestMapping(value = "/problemCountByChapterId",method = RequestMethod.GET)
    @ResponseBody
    public List getProblemCountByChapterId(@RequestParam("chapterIds[]") int[] chapterIds) {
        return exerciseService.getProblemCountByChapterIds(chapterIds);
    }

    @SystemControllerLog("检索题目")
    @RequestMapping("/filterProblems")
    @ResponseBody
    public List filterProblems(int type,int keywords) {
        return exerciseService.filterProblems(type, keywords);
    }

    @SystemControllerLog("访问题目列表页面")
    @RequestMapping("/viewProblem")
    public String viewProblem(int sectionId,Model model) {
        model.addAttribute("sectionId",sectionId);
        return "teacher/exercise/problemList";
    }

    @SystemControllerLog("获取班级人数")
    @RequestMapping("/classmateCount/{classId}")
    @ResponseBody
    public Long getClassmateCount(@PathVariable("classId") int classId) {
        return clazzService.getClassmatesCount(classId);
    }

    @SystemControllerLog("获取练习提交人数")
    @RequestMapping("/exercise/submitCount/{exerciseId}")
    @ResponseBody
    public Long getSubmitCount(@PathVariable("exerciseId") int exerciseId) {
        return exerciseService.getSubmitExerciseCount(exerciseId);
    }

    @SystemControllerLog("获取已批改练习数")
    @RequestMapping("/exercise/judgeCount/{exerciseId}")
    @ResponseBody
    public Long getJudgeCount(@PathVariable("exerciseId") int exerciseId) {
        return exerciseService.getJudgeCount(exerciseId);
    }

    @SystemControllerLog("批改练习")
    @RequestMapping("/exercise/markExercise/{exerciseId}")
    public String markExercisePage(@PathVariable("exerciseId") int exerciseId,Model model) {
        model.addAttribute("exerciseId",exerciseId);
        return "teacher/exercise/markExercise";
    }

    @SystemControllerLog("获取未批改学生")
    @RequestMapping("/exercise/classmates/{exerciseId}")
    @ResponseBody
    public List getClassmatesByExerciseId(@PathVariable("exerciseId") int exerciseId) {
        return exerciseService.getExistUnMarkProblemClassmates(exerciseId);
    }

    @SystemControllerLog("批改客观题")
    @RequestMapping("/exercise/markObjectiveProblem/{exerciseId}")
    @ResponseBody
    public boolean markObjectiveProblem(@PathVariable("exerciseId") int exerciseId) {
        return exerciseService.markObjectiveProblem(exerciseId);
    }


    @SystemControllerLog("批改主观题")
    @RequestMapping("/exercise/markSubjectiveProblem")
    @ResponseBody
    public boolean markSubjectiveProblem(@RequestParam("scores[]") int scores[],@RequestParam("answersId[]") int answersId[]) {
        return exerciseService.markSubjectiveProblem(scores,answersId);
    }

    @SystemControllerLog("获取主观题")
    @RequestMapping("/subjectiveAnswers")
    @ResponseBody
    public List getSubjectiveAnswers(int exerciseId,int classmateId) {
        return exerciseService.getSubjectiveAnswers(exerciseId,classmateId);
    }

    @SystemControllerLog("分数计算")
    @RequestMapping("/exercise/countScore")
    @ResponseBody
    public boolean countScore(int exerciseId) {
        return exerciseService.countScore(exerciseId);
    }


    @RequestMapping("/existsUnMarkExercise/{classId}")
    @ResponseBody
    public boolean existsUnMarkExercise(@PathVariable("classId") int classId) {
        return exerciseService.existsUnMarkExercise(classId);
    }

    @RequestMapping("/existFinalExercise/{classId}")
    @ResponseBody
    public boolean existFinalExercise(@PathVariable("classId") int classId) {
        return exerciseService.existFinalExercise(classId);

    }


    @SystemControllerLog("班级结课，计算成绩")
    @RequestMapping("/finishClass/{classId}")
    @ResponseBody
    public boolean finishClass(@PathVariable("classId") int classId) {
        return clazzService.finishClass(classId);
    }



    @RequestMapping("courses/sharp/{teacherId}")
    @ResponseBody
    public List getSharpCourseByTeacherId(@PathVariable("teacherId") String teacherId) {
        return courseService.getSharpCourseByTeacherId(teacherId);
    }


    @RequestMapping("classes/sharp/{courseId}")
    @ResponseBody
    public List getSharpClassesByCourseId(@PathVariable("courseId") int courseId) {
        return clazzService.getSharpClassesByCourseId(courseId);
    }

}
