package com.edu.controller;

import com.edu.annotation.SystemControllerLog;
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

    @SystemControllerLog("访问练习答案页面")
    @RequestMapping("/exercise/{exerciseId}/answers")
    public String viewAnswers(@PathVariable("exerciseId") int exerciseId,Model model) {
        model.addAttribute("exerciseId",exerciseId);
        return "student/viewAnswer";
    }

    //查询学生是否已注册过班级
    @SystemControllerLog("查询学生是否注册班级")
    @RequestMapping("/isRegister")
    @ResponseBody
    public boolean isRegister(int clazzId, String userId) {
        return clazzService.isRegister(clazzId, userId);
    }

    //学生注册班级
    @SystemControllerLog("学生注册班级")
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


    @SystemControllerLog("学生访问我的课程页面")
    @RequestMapping("/courseManage")
    public String courseManage() {
        return "student/courseManage";
    }


    @SystemControllerLog("获取学生参见的班级")
    @RequestMapping(value = "/clazz/{userId}", method = RequestMethod.GET)
    @ResponseBody
    public List getClazzByUserId(@PathVariable("userId") String userId) {
        return clazzService.getClazzByUserId(userId);
    }

    @SystemControllerLog("获取班级学生人数")
    @RequestMapping("/classmatesCount/{classId}")
    @ResponseBody
    public Long getClassmatesCount(@PathVariable("classId") int classId) {
        return clazzService.getClassmatesCount(classId);
    }


    //获取班级学生
    @SystemControllerLog("获取班级中的全部学生")
    @RequestMapping("/classmates/{classId}")
    @ResponseBody
    public List getClassmatesByClassId(@PathVariable("classId") int classId) {
        return clazzService.getClassmatesByClassId(classId);
    }

    @SystemControllerLog("学生访问我的作业页面")
    @RequestMapping("/exerciseManage")
    public String exerciseManage() {
        return "student/exerciseManage";
    }


    //获取user下的练习
    @SystemControllerLog("学生获取所有练习")
    @RequestMapping(value = "/exercisesByUserId/{userId}", method = RequestMethod.GET)
    @ResponseBody
    public List getExerciseByUserId(@PathVariable("userId") String userId) {
        return exerciseService.getExercisesByUserId(userId);
    }

    @SystemControllerLog("学生获取练习下的题目数量")
    @RequestMapping(value = "/exercise/problemCount", method = RequestMethod.GET)
    @ResponseBody
    public Long getProblemCountByExerciseId(int exerciseId) {
        return exerciseService.getProblemCountByExerciseId(exerciseId);
    }
    @SystemControllerLog("获取是否提交过练习")
    @RequestMapping("/checkSubmit")
    @ResponseBody
    public boolean checkSubmit(String userId, int exerciseId) {
        return exerciseService.checkIsSubmit(userId, exerciseId);
    }

    @SystemControllerLog("开始练习")
    @RequestMapping("/doExercise/{exerciseId}")
    public String getProblemsByExerciseId(@PathVariable("exerciseId") int exerciseId, Model model) {
        model.addAttribute("exerciseId", exerciseId);
        return "student/doExercise";
    }

    @SystemControllerLog("获取练习下的题目")
    @RequestMapping("/exercise/{exerciseId}/problems")
    @ResponseBody
    public List getProblemsByExerciseId(@PathVariable("exerciseId") int exerciseId) {
        return exerciseService.getProblemsByExerciseId(exerciseId);
    }

    @SystemControllerLog("提交练习答案")
    @RequestMapping(value = "/exercise/submitAnswers", method = RequestMethod.POST)
    @ResponseBody
    public boolean saveSubmitAnswers(@RequestBody Answer answer, HttpServletRequest request) {
        return exerciseService.batchSaveAnswers(answer, HttpUtils.getRealIP(request));
    }

    @SystemControllerLog("查看是否提交过答案")
    @RequestMapping("/exercise/existAnswers")
    @ResponseBody
    public boolean checkIsExistsAnswers(int exerciseId,String userId) {
        return exerciseService.checkIsExistsAnswers(exerciseId,userId);
    }

    @SystemControllerLog("获取提交答案")
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
    @SystemControllerLog("学生上传答案图片")
    @RequestMapping("/upload/answerPic")
    @ResponseBody
    public String uploadAnswerPic(@RequestParam("pic") MultipartFile multipartFile) {
        return UploadFile.uploadFile(multipartFile,"answer-img/");
    }
}

