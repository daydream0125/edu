package com.edu.controller;

import com.edu.model.*;
import com.edu.service.AccountMsgService;
import com.edu.service.ClazzService;
import com.edu.service.CourseService;
import com.edu.service.ExerciseService;
import com.edu.utils.HttpUtils;
import com.edu.utils.UploadFile;
import com.edu.utils.model.Answer;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/student")
public class StudentController {
    @Resource
    private CourseService courseService;
    @Resource
    private AccountMsgService accountMsgService;
    @Resource
    private ClazzService clazzService;
    @Resource
    private ExerciseService exerciseService;
    public static final String SUCCESS = "success";

    @RequestMapping("/courseChapterInfo")
    public String courseContentInfo(int courseId, Model model) {
        List chapters = courseService.getCourseChapterByCourseId(courseId);
        model.addAttribute("chapters", chapters);
        //model.addAttribute("courseId", courseId);
        return "student/courseChapterInfo";
    }

    @RequestMapping("/exercise/{exerciseId}/answers")
    public String viewAnswers(@PathVariable("exerciseId") int exerciseId,Model model) {
        model.addAttribute("exerciseId",exerciseId);
        return "student/viewAnswer";
    }

    //查询学生是否已注册过班级
    @RequestMapping("/isRegister")
    @ResponseBody
    public boolean isRegister(int clazzId, String userId) {
        return clazzService.isRegister(clazzId, userId);
    }

    //学生注册班级
    @RequestMapping("/registerClazz")
    public @ResponseBody
    boolean registerClazz(int clazzId, String userId, int courseId) {
        Account account = accountMsgService.getAccountById(userId);
        Course course = courseService.getCourseByCourseId(courseId);
        Clazz clazz = clazzService.getClazzById(clazzId);
        Classmate classmate = new Classmate();
        classmate.setAccount(account);
        classmate.setClazz(clazz);
        classmate.setCourse(course);
        clazzService.saveClassMate(classmate);
        return true;
    }

    @RequestMapping("/chapterContent")
    public String chapterContent(int chapterId, Model model) {
        model.addAttribute("chapter", courseService.getCourseChapterById(chapterId));
        return "student/chapterContent";
    }

    @RequestMapping("/courseManage")
    public String courseManage() {
        return "student/courseManage";
    }


    @RequestMapping(value = "/clazz/{userId}", method = RequestMethod.GET)
    @ResponseBody
    public List getClazzByUserId(@PathVariable("userId") String userId) {
        return clazzService.getClazzByUserId(userId);
    }

    @RequestMapping("/classmatesCount/{classId}")
    @ResponseBody
    public Long getClassmatesCount(@PathVariable("classId") int classId) {
        return clazzService.getClassmatesCount(classId);
    }


    //获取班级学生
    @RequestMapping("/classmates/{classId}")
    @ResponseBody
    public List getClassmatesByClassId(@PathVariable("classId") int classId) {
        return clazzService.getClassmatesByClassId(classId);
    }

    @RequestMapping("/exerciseManage")
    public String exerciseManage() {
        return "student/exerciseManage";
    }


    //获取user下的练习
    @RequestMapping(value = "/exercisesByUserId/{userId}", method = RequestMethod.GET)
    @ResponseBody
    public List getExerciseByUserId(@PathVariable("userId") String userId) {
        return exerciseService.getExercisesByUserId(userId);
    }

    @RequestMapping(value = "/exercise/problemCount", method = RequestMethod.GET)
    @ResponseBody
    public Long getProblemCountByExerciseId(int exerciseId) {
        return exerciseService.getProblemCountByExerciseId(exerciseId);
    }

    @RequestMapping("/checkSubmit")
    @ResponseBody
    public boolean checkSubmit(String userId, int exerciseId) {
        return exerciseService.checkIsSubmit(userId, exerciseId);
    }

    @RequestMapping("/doExercise/{exerciseId}")
    public String getProblemsByExerciseId(@PathVariable("exerciseId") int exerciseId, Model model) {
        model.addAttribute("exerciseId", exerciseId);
        return "student/doExercise";
    }

    @RequestMapping("/exercise/{exerciseId}/problems")
    @ResponseBody
    public List getProblemsByExerciseId(@PathVariable("exerciseId") int exerciseId) {
        return exerciseService.getProblemsByExerciseId(exerciseId);
    }

    @RequestMapping(value = "/exercise/submitAnswers", method = RequestMethod.POST)
    @ResponseBody
    public boolean saveSubmitAnswers(@RequestBody Answer answer, HttpServletRequest request) {
        return exerciseService.batchSaveAnswers(answer, HttpUtils.getRealIP(request));
    }

    @RequestMapping("/exercise/existAnswers")
    @ResponseBody
    public boolean checkIsExistsAnswers(int exerciseId,String userId) {
        return exerciseService.checkIsExistsAnswers(exerciseId,userId);
    }

    @RequestMapping("/exercise/answers")
    @ResponseBody
    public List getSubmitAnswers(int exerciseId,String userId) {
        return exerciseService.getAnswers(exerciseId,userId);
    }


    /**
     * 只返回必要信息，避免 hibernate 级联取出过多无用数据，降低效率
     * @param exerciseId
     * @return
     */
    @RequestMapping("/exercise/sharp/{exerciseId}")
    @ResponseBody
    public List getSharpExercise(@PathVariable("exerciseId") int exerciseId) {
        return exerciseService.getSharpExercise(exerciseId);
    }


    /**
     * 上传答案图片
     * @param multipartFile
     * @return
     */
    @RequestMapping("/upload/answerPic")
    @ResponseBody
    public String uploadAnswerPic(@RequestParam("pic") MultipartFile multipartFile) {
        return UploadFile.uploadFile(multipartFile,"answer-img/");
    }
}

